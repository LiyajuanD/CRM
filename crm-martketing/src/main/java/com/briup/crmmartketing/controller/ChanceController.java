package com.briup.crmmartketing.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.briup.crmcommon.Exception.ServiceException;
import com.briup.crmcommon.entity.Customer;
import com.briup.crmcommon.utils.Result;
import com.briup.crmmartketing.entity.Chance;
import com.briup.crmmartketing.service.IChanceService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author briup
 * @since 2022-11-28
 */
@RestController
@RequestMapping("/auth/chance")
public class ChanceController {

    @Autowired
    private IChanceService service;

    @ApiOperation("商机信息展示")
    @GetMapping("/getchanceByPage")
    @ResponseBody
    public Result getUserByPage(Integer pageNum, Integer pageSize) throws ServiceException {
//        分页查询用户信息
        if (pageNum <=0){
            return Result.failure(2222,"页数异常，请重新输入");
        }else {
            Page<Chance> page = service.page(new Page<>(pageNum, pageSize));
            return Result.success(page.getRecords());
        }
    }

    @ApiOperation("模糊查询商机信息")
    @GetMapping("/chanceLike")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "title",value = "需要查询的商机标题",required = false)
    })
    public Result FindByCustRegion(String title){
        //这里使用了service的list方法，用条件构造器的方法查询
        //select * from cust where cust_name like %name% and cust_region like %region% and cust_level_label like %level%
        QueryWrapper<Chance> wrapper = new QueryWrapper<Chance>()
                .like("chc_title", title);
        List<Chance> list = null;
        if(title != null){
            list = service.list(wrapper);
        }
        return Result.success(list);

      /*  List<Customer> customers1 = customerService.FindByCustName(name);
        return Result.success(customers1);*/
    }
    @ApiOperation("新增商机")
    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public Result saveOrUpdateUser(Chance chance){
        service.saveOrUpdate(chance);
        return Result.success();
    }

    @DeleteMapping("/removeById/{id}")
    @ApiOperation("删除单个商机信息")
    @ResponseBody
    public Result deleteById(@PathVariable long id){
        boolean b = service.removeById(id);
        if(b){
            return Result.success();
        }
        return Result.failure(22,"操作失败");
    }

    @DeleteMapping("/remove")
    @ApiOperation("批量删除商机信息")
    @ResponseBody
    public Result deleteUser(@RequestParam List<Integer> ids){
        boolean b = service.removeByIds(ids);
        if(b) {
            return Result.success();
        }
        return Result.failure(22,"操作失败");
    }

}
