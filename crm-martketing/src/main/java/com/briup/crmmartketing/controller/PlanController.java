package com.briup.crmmartketing.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.briup.crmcommon.Exception.ServiceException;
import com.briup.crmcommon.utils.Result;
import com.briup.crmmartketing.entity.Chance;
import com.briup.crmmartketing.entity.Plan;
import com.briup.crmmartketing.service.IChanceService;
import com.briup.crmmartketing.service.IPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
 * @since 2022-11-28
 */
@Api(tags = "客户开发管理")
@RestController
@RequestMapping("/auth/plan")
public class PlanController {

    @Autowired
    private IPlanService service;

    @ApiOperation("客户开发计划展示")
    @GetMapping("/getPlanByPage")
    @ResponseBody
    public Result getUserByPage(Integer pageNum, Integer pageSize) throws ServiceException {
//        分页查询用户信息
        if (pageNum <=0){
            return Result.failure(2222,"页数异常，请重新输入");
        }else {
            Page<Plan> page = service.page(new Page<>(pageNum, pageSize));
            return Result.success(page.getRecords());
        }
    }

    @ApiOperation("模糊客户开发信息")
    @GetMapping("/PlanLike")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "todo",value = "需要查询的商机标题",required = false)
    })
    public Result FindByCustRegion(String todo){
        //这里使用了service的list方法，用条件构造器的方法查询
        QueryWrapper<Plan> wrapper = new QueryWrapper<Plan>()
                .like("pla_todo", todo);
        List<Plan> list = null;
        if(todo != null){
            list = service.list(wrapper);
        }
        return Result.success(list);

    }
    @ApiOperation("新增或编辑客户开发计划")
    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public Result saveOrUpdateUser(Plan plan){
        service.saveOrUpdate(plan);
        return Result.success();
    }

    @DeleteMapping("/removeById/{id}")
    @ApiOperation("删除单个客户开发计划")
    @ResponseBody
    public Result deleteById(@PathVariable long id){
        boolean b = service.removeById(id);
        if(b){
            return Result.success();
        }
        return Result.failure(22,"操作失败");
    }

    @DeleteMapping("/remove")
    @ApiOperation("批量删除客户开发计划")
    @ResponseBody
    public Result deleteUser(@RequestParam List<Integer> ids){
        boolean b = service.removeByIds(ids);
        if(b) {
            return Result.success();
        }
        return Result.failure(22,"操作失败");
    }

}
