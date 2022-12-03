package com.briup.crmservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.briup.crmcommon.Exception.ServiceException;
import com.briup.crmcommon.utils.Result;

import com.briup.crmservice.entity.MyService;
import com.briup.crmservice.service.IServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author briup
 * @since 2022-11-28
 */
@Api(tags = "服务处理模块")
@RestController
@RequestMapping("/auth/myservice")
public class ServiceController {
    @Autowired
    private IServiceService service;


    @ApiOperation("服务信息展示")
    @GetMapping("/getServiceByPage")
    @ResponseBody
    public Result getUserByPage(Integer pageNum, Integer pageSize) throws ServiceException {
//        分页查询用户信息
        if (pageNum <=0){
            return Result.failure(2222,"页数异常，请重新输入");
        }else {
            Page<MyService> page = service.page(new Page<>(pageNum, pageSize));
            return Result.success(page.getRecords());
        }
    }
    @ApiOperation("模糊查询服务信息")
    @GetMapping("/serviceLike")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "type",value = "需要查询的服务类型",required = false)
    })
    public Result FindBysreviceType(String type){

        QueryWrapper<MyService> wrapper = new QueryWrapper<MyService>()
                .like("svr_type", type);
        List<MyService> list = null;
        if(type != null){
            list = service.list(wrapper);
        }
        return Result.success(list);

    }
    @ApiOperation("新增服务或编辑服务")
    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public Result saveOrUpdateUser(MyService ser){
        service.saveOrUpdate(ser);
        return Result.success();
    }
    @DeleteMapping("/removeById/{id}")
    @ApiOperation("删除单个服务信息")
    @ResponseBody
    public Result deleteById(@PathVariable long id){
        boolean b = service.removeById(id);
        if(b){
            return Result.success();
        }
        return Result.failure(22,"操作失败");
    }

    @DeleteMapping("/remove")
    @ApiOperation("批量删除服务信息")
    @ResponseBody
    public Result deleteUser(@RequestParam List<Integer> ids){
        boolean b = service.removeByIds(ids);
        if(b) {
            return Result.success();
        }
        return Result.failure(22,"操作失败");
    }



    @ApiOperation("反馈服务")
    @PostMapping("/back")
    @ResponseBody
    public Result serviceBack(Long id,String handle,String result){
        //反馈服务时修改的应该是服务的ser_handle，就是对这个进行处理
        MyService ser = service.getById(id);
        if("已反馈".equals(ser.getSvrStatus())){
            return Result.failure(222,"服务已反馈");
        }
        if(handle== null){
            return Result.failure(222,"请输入反馈信息");
        }
        if(result ==null){
            return Result.failure(222,"请输入反馈结果");
        }
        //将接收的信息封装为一个新的对象，再调用方法，这个时候就会是修改操作，
        MyService myService = new MyService();
        myService.setSvrId(id);
        myService.setSvrHandle(handle);
        myService.setSvrResult(result);
        //反馈之后的这条记录的状态应该是已反馈，下次就不用重复反馈
        myService.setSvrStatus("已反馈");
        service.saveOrUpdate(myService);

        return Result.success();
    }
}
