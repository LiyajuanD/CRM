package com.briup.crmsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.briup.crmcommon.Exception.ServiceException;
import com.briup.crmcommon.utils.Result;
import com.briup.crmsystem.entity.Role;
import com.briup.crmsystem.entity.User;
import com.briup.crmsystem.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author briup
 * @since 2022-11-24
 */
@Api(tags = "系统角色管理模块")
@Controller
@RequestMapping("/auth/role")
public class RoleController {
    @Autowired
    private IRoleService service;

    //这个接口暂时没用，因为数据库中直接包含了用户的角色名称，不用再去两表联查了
    @ApiOperation("根据用户名查询单个用户对象,这是auth远程调用的接口")
    @PostMapping("/ findRoleById")
    @ResponseBody
    String findByRoleId(@RequestBody Long id){
        Role byId = service.getById(id);
        return byId.getRoleName();
    }


    @ApiOperation("角色信息展示")
    @GetMapping("/getRoleByPage")
    @ResponseBody
    public Result getUserByPage(Integer pageNum, Integer pageSize) throws ServiceException {
//        分页查询用户信息
        if (pageNum <=0){
            return Result.failure(2222,"页数异常，请重新输入");
        }else {
            Page<Role> page = service.page(new Page<>(pageNum, pageSize));
            return Result.success(page.getRecords());
        }
    }
    @ApiOperation("新增role")
    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public Result saveOrUpdateUser(Role role){
        service.saveOrUpdate(role);
        return Result.success();
    }
    @DeleteMapping("/removeById/{id}")
    @ApiOperation("删除单个role信息")
    @ResponseBody
    public Result deleteById(@PathVariable long id){
        boolean b = service.removeById(id);
        if(b){
            return Result.success();
        }
        return Result.failure(22,"操作失败");
    }

    @DeleteMapping("/remove")
    @ApiOperation("批量删除role信息")
    @ResponseBody
    public Result deleteUser(@RequestParam List<Integer> ids){
        boolean b = service.removeByIds(ids);
        if(b) {
            return Result.success();
        }
        return Result.failure(22,"操作失败");
    }
}
