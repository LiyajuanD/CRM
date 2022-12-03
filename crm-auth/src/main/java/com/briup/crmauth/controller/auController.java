package com.briup.crmauth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.briup.crmauth.clients.UserRemote;
import com.briup.crmauth.config.userInfo;
import com.briup.crmauth.vm.UserVM;
import com.briup.crmcommon.entity.User;
import com.briup.crmcommon.utils.JwtTokenUtils;
import com.briup.crmcommon.utils.JwtUtil;
import com.briup.crmcommon.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dell
 */
@Api(tags ="登录管理模块")
@RestController("/auth")
public class auController {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserRemote remote;

   /* @GetMapping("/test")
    public void getToken(@RequestParam String name,String pwd){
        String url="http://crm-system/auto/system/login?name="+name+"&password="+pwd;
        String token = restTemplate.getForObject(url, String.class);
        String userId = JwtUtil.getUserId(token);
        if(token !=null && userId!=null) {
            //redisTemplate.opsForValue().set(userId, token);
            System.out.println(userId+" 获取到的jwt令牌"+token);
        }
    }*/

    //用户登录分为几种状态
    /*
    1.用户账户名密码均合适，状态也合适，登陆成功，----成功返回token字符串
    2.用户名称查询不到，---即用户不存在
    3.用户账户名密码均合适，状态为0，-----即状态异常不允许登录
    4.用户输入的用户名和密码不匹配，-------即密码错误
     */
   @ApiOperation("用户登录")
   @PostMapping("/login")
   @ResponseBody
   public String login(String name, String pwd) throws RuntimeException {

       //每次登录先去redis里面查，如果有这个数据就直接返回redis里面存储的token
       User user = remote.selectUserByUsername(name);

       System.out.println(user);
       //用户不存在
       if (user==null){
           // throw new ServiceException(ResultCode.USER_NOT_EXIST);
           return "用户不存在";
       }

       if(!pwd.equals(user.getUsrPassword())){
           //throw new ServiceException(ResultCode.USER_LOGIN_ERROR);
           return "密码错误";
       }
       //账号的状态信息（0：正常，1：禁用）
       if(userInfo.USER_STATUS_OK.equals(String.valueOf(user.getUsrFlag()))) {
           //throw new ServiceException(ResultCode.USER_ACCOUNT_FORBIDDEN);
           return "用户状态异常不允许登录";
       }
       String s = redisTemplate.opsForValue().get(user.getUsrName());
       if (s!=null){
           return s;
       }
       //执行成功，根据jwtutils生成唯一的token返回
       HashMap<String, Object> map1 = new HashMap<>();
       map1.put("userid",user.getUsrId());
       String token = JwtUtil.sign(user.getUsrName(), map1);

       System.out.println("获取到的token为："+token);
       redisTemplate.opsForValue().append(user.getUsrName(),token);
       return token;
   }
    @ApiOperation("用户登出")
    @GetMapping("/auth/logout")
    public Result userLogout( HttpServletRequest request){
        String token = request.getHeader("token");
        String userId = JwtUtil.getUserId(token);

        boolean b = Boolean.TRUE.equals(redisTemplate.delete(userId));
        if(b){
            return Result.success("登出成功");
        }
        return Result.failure(55,"登出失败");

    }


    //权限验证
    @ApiOperation("查看当前用户的角色（远程调用system的接口）")
    @GetMapping("/auth/roleName")
    public Result getRoleName( HttpServletRequest request){
        String token = request.getHeader("token");
        String userId = JwtUtil.getUserId(token);
        //获取当前登录用户的用户信息
        User user = remote.selectUserByUsername(userId);
        String usrRoleName = user.getUsrRoleName();
        return Result.success("当前用户的角色为:"+usrRoleName);
    }


    @ApiOperation("注销当前用户,远程调用的接口")
    @GetMapping("/auth/removeUser")
    @ResponseBody
    public Result removeUser(@RequestParam(value = "需要删除的用户id") Long id,HttpServletRequest request){
        String token = request.getHeader("token");
        String userId = JwtUtil.getUserId(token);

        String s = remote.deleteByUserId(id, userId);
        System.out.println("远程调用获取到的字符串为"+s);
        return Result.success(s);
    }
}

