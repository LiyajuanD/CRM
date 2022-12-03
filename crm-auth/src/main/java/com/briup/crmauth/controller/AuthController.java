//package com.briup.crmauth.controller;
//
//
//import com.briup.crmauth.clients.UserRemote;
//import com.briup.crmauth.vm.UserVM;
//import com.briup.crmcommon.entity.User;
//import com.briup.crmcommon.utils.CodeStatus;
//import com.briup.crmcommon.utils.JwtTokenUtils;
//import com.briup.crmcommon.utils.Result;
//import io.swagger.annotations.ApiOperation;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("")
//public class AuthController {
//    @Autowired
//    private UserRemote userRemote;
//    @Autowired
//    private JwtTokenUtils jwtTokenUtils;
//
//    @ApiOperation("用户登录")
//    @PostMapping("/login")
//    public Result userLogin(@RequestBody UserVM user){
//        Map<String,String> map = new HashMap<>();
//        map.put("username",user.getUsername());
//        map.put("password",user.getPassword());
//        // 验证账号密码为空可以交给前端，直接使用user进行查找
//        User user1 = userRemote.selectUserByUsernameAndPassword(map);
//        // 判断rsp是否存在
//        if(user1 != null){
//            // 返回token
//            Map<String, Object> token = jwtTokenUtils.generateTokenAndRefreshToken(String.valueOf(user1.getUsrId()), user.getUsername());
//            return Result.success(token);
//        }
//        // 返回登录失败
//        return Result.failure(222,"登录失败");
//    }
//
//    @ApiOperation("刷新令牌")
//    @GetMapping("/refreshToken")
//    public Result refresh(@RequestHeader("token") String token){
//        // 后续可以封装一个权限工具
//        // token = SecurityUtils.replaceTokenPrefix(token);
//
//        if (StringUtils.isEmpty(token)) {
//            return Result.failure(555,"刷新令牌失败");
//        }
//
//        // 对Token解签名，并验证Token是否过期
//        boolean isJwtNotValid = jwtTokenUtils.isTokenExpired(token);
//        if(isJwtNotValid){
//            return Result.failure(555,"失败");
//        }
//        // 验证 token 里面的 userId 是否为空
//
//        String userId = jwtTokenUtils.getUserIdFromToken(token);
//        String username = jwtTokenUtils.getUserNameFromToken(token);
//        if (StringUtils.isEmpty(userId)) {
//            return Result.failure(555,"token 信息验证失败");
//        }
//
//        // 这里为了保证 refreshToken 只能用一次，刷新后，会从 redis 中删除。
//        // 如果用的不是 redis 中的 refreshToken 进行刷新令牌，则不能刷新。
//        // 如果使用 redis 中已过期的 refreshToken 也不能刷新令牌。
//        boolean isRefreshTokenNotExisted = jwtTokenUtils.isRefreshTokenNotExistCache(token);
//        if(isRefreshTokenNotExisted){
//            return Result.failure(555,"refreshToken 无效");
//        }
//
//        String us = jwtTokenUtils.getUserIdFromToken(token);
//        Map<String, Object> tokenMap = jwtTokenUtils.refreshTokenAndGenerateToken(userId, username);
//
//        return Result.success(tokenMap);
//    }
//
//    @ApiOperation("用户登出")
//    @GetMapping("/logout")
//    public Result userLogout(@RequestParam String userId){
//        boolean b = jwtTokenUtils.removeToken(userId);
//        if(b){
//            return Result.success("登出成功");
//        }
//        return Result.failure(55,"登出失败");
//    }
//}
