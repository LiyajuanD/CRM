package com.briup.crmcustomer.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.briup.crmcommon.Exception.ServiceException;
import com.briup.crmcommon.utils.Result;
import com.briup.crmcustomer.entity.Linkman;
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
 * @since 2022-11-21
 */
@Controller
@RequestMapping("/auth/linkman")
@Api(tags = "联系人管理")
public class LinkmanController {
    @Autowired
    private ILinkmanService service;

    @ApiOperation("联系人信息展示")
    @GetMapping("/getUserByPage")
    @ResponseBody
    public Result getUserByPage(Integer pageNum, Integer pageSize) throws ServiceException {
//        分页查询用户信息
        if (pageNum <=0){
            return Result.failure(2222,"页数异常，请重新输入");
        }else {
            Page<Linkman> page = service.page(new Page<>(pageNum, pageSize));
            return Result.success(page.getRecords());
        }
    }
    @ApiOperation("新增联系人")
    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public Result saveOrUpdateUser(Linkman linkman){
        service.saveOrUpdate(linkman);
        return Result.success();
    }
    @DeleteMapping("/removeById/{id}")
    @ApiOperation("删除单个联系人信息")
    @ResponseBody
    public Result deleteById(@PathVariable long id){
        boolean b = service.removeById(id);
        if(b){
            return Result.success();
        }
        return Result.failure(22,"操作失败");
    }

    @DeleteMapping("/remove")
    @ApiOperation("批量删除联系人信息")
    @ResponseBody
    public Result deleteUser(@RequestParam List<Integer> ids){
        boolean b = service.removeByIds(ids);
        if(b) {
            return Result.success();
        }
        return Result.failure(22,"操作失败");
    }
}
