package com.briup.crmsystem.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.briup.crmcommon.Exception.ServiceException;
import com.briup.crmcommon.utils.JwtUtil;
import com.briup.crmcommon.utils.Result;
import com.briup.crmsystem.entity.User;
import com.briup.crmsystem.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.StreamInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author briup
 * @since 2022-11-21
 * //这里的方法在测试过程中数据库可以正常操作，但是前端swagger测试接口显示not found  ：解决方法是给方法加上@ResponeBody注解
 */
@Api(tags = "系统用户管理模块")
@Controller
@RequestMapping("/auth/system")
public class UserController {

    @Autowired
    private IUserService service;


    @ApiOperation("根据用户名查询单个用户对象,这是auth远程调用的接口")
    @PostMapping("/findUserByName")
    @ResponseBody
    public User selectUserByUsername(@RequestBody String name){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        QueryWrapper<User> usr_name = queryWrapper.eq("usr_name", name);
        User one = service.getOne(usr_name);
        System.out.println("查询的用户为："+one);
        return one;
    }





    @ApiOperation("用户信息展示")
    @GetMapping("/getUserByPage")
    @ResponseBody
    public Result getUserByPage(Integer pageNum, Integer pageSize) throws ServiceException {
//        分页查询用户信息
        if (pageNum <=0){
            return Result.failure(2222,"页数异常，请重新输入");
        }else {
            Page<User> page = service.page(new Page<>(pageNum, pageSize));
            return Result.success(page.getRecords());
        }
    }
    @ApiOperation("新增user")
    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public Result saveOrUpdateUser(User user){
        service.saveOrUpdate(user);
        return Result.success();
    }
    @DeleteMapping("/removeById/{id}")
    @ApiOperation("删除单个user信息")
    @ResponseBody
    public Result deleteById(@PathVariable long id){
        boolean b = service.removeById(id);
        if(b){
            return Result.success();
        }
        return Result.failure(22,"操作失败");
    }

    @DeleteMapping("/remove")
    @ApiOperation("批量删除user信息")
    @ResponseBody
    public Result deleteUser(@RequestParam List<Integer> ids){
        boolean b = service.removeByIds(ids);
        if(b) {
            return Result.success();
        }
        return Result.failure(22,"操作失败");
    }

    @PostMapping("/deleteById")
    @ApiOperation("注销user信息")
    @ResponseBody
    public String deleteByUserId(@RequestParam(value = "需要删除的用户id") Long id,@RequestParam String name){
        System.out.println("远程调用成功");

        //查询当前操作的用户信息
        User usrName = service.getOne(new QueryWrapper<User>().eq("usr_name", name));

        //获取需要删除的用户信息
        User usr = service.getOne(new QueryWrapper<User>().eq("usr_id", id));
        //如果当前用户和需要删除的用户是同一个用户的话删除成功
        if (usr.getUsrName().equals(usrName.getUsrName())){
            service.removeById(id);
            return "自我注销成功";
        }
        if("管理员".equals(usrName.getUsrRoleName())){
            service.removeById(id);
            return "管理员注销成功";
        }
        return "注销失败，权限不足";
    }
}
