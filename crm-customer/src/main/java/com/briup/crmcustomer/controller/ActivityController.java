package com.briup.crmcustomer.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.briup.crmcommon.Exception.ServiceException;
import com.briup.crmcommon.utils.Result;
import com.briup.crmcustomer.entity.Activity;
import com.briup.crmcustomer.entity.Linkman;
import com.briup.crmcustomer.entity.ext.ActivityExt;
import com.briup.crmcustomer.service.IActivityService;
import com.briup.crmcustomer.service.ILinkmanService;
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
 * @since 2022-11-22
 */
@Api(tags ="交往记录管理模块")
@Controller
@RequestMapping("/auth/activity")
public class ActivityController {
    @Autowired
    private IActivityService service;

    @ApiOperation("交往记录信息展示")
    @GetMapping("/getActivityByPage")
    @ResponseBody
    public Result getUserByPage(Integer pageNum, Integer pageSize) throws ServiceException {
//        分页查询用户信息
        if (pageNum <=0){
            return Result.failure(2222,"页数异常，请重新输入");
        }else {
            Page<Activity> page = service.page(new Page<>(pageNum, pageSize));
            return Result.success(page.getRecords());
        }
    }

    @ApiOperation("交往记录信息展示")
    @GetMapping("/getall")
    @ResponseBody
    public Result getall(Long id) throws ServiceException {

        List<ActivityExt> all = service.getAll(id);
        return Result.success(all);
    }

    @ApiOperation("新增交往记录")
    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public Result saveOrUpdateUser(Activity activity){
        service.saveOrUpdate(activity);
        return Result.success();
    }
    @DeleteMapping("/removeById/{id}")
    @ApiOperation("删除单个交往记录信息")
    @ResponseBody
    public Result deleteById(@PathVariable long id){
        boolean b = service.removeById(id);
        if(b){
            return Result.success();
        }
        return Result.failure(22,"操作失败");
    }

    @DeleteMapping("/remove")
    @ApiOperation("批量删除交往记录信息")
    @ResponseBody
    public Result deleteUser(@RequestParam List<Integer> ids){
        boolean b = service.removeByIds(ids);
        if(b) {
            return Result.success();
        }
        return Result.failure(22,"操作失败");
    }
}
