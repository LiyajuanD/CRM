package com.briup.crmreport.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.briup.crmcommon.entity.Customer;
import com.briup.crmcommon.utils.Result;
import com.briup.crmreport.Clients.CustomerClient;
import com.briup.crmreport.Entity.myVm;
import com.briup.crmreport.service.ICustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author dell
 */
@Api(tags = "统计报表实现")
@RestController
@RequestMapping("/auth/report")
public class reportController1 {

    @Autowired
    private ICustomerService service;

    @Autowired
    private CustomerClient customerClient;

    @ApiOperation("客户构成分析(分析每个地区的客户占比)")
    @GetMapping("/composeCustomer")
    public Result getCompose(){
        List<myVm> custCompose = customerClient.getCustCompose();
        return Result.success(custCompose);
    }

    //2.客户贡献分析：2.1按照客户地区分布展示；2.2按照客户等级展示；2.2按照客户满意度分布
    @ApiOperation("按照贡献统计客户信息")
    @GetMapping("/contribution")
    public Result contributionAanalysis(@RequestParam(required = false) List<String> regions,
                                        @RequestParam(required = false) List<String> levelLabels,
                                        @RequestParam(required = false) Integer satisfy){
        //分组查询
        QueryWrapper<Customer>like=new QueryWrapper<>();
        System.out.println("1:"+regions);
        System.out.println("2:"+levelLabels);
        System.out.println("3:"+satisfy);
        like.select("cust_level_label,count(*) count");
        if(regions!=null){
            like.and(s->s.in("cust_region",regions));
        }
        if(levelLabels!=null){
            like.and(s->s.in("cust_level_label",levelLabels));
        }

        if(satisfy!=null){
            like.and(s->s.gt("cust_satisfy",satisfy));
        }
        //分组语句，贡献度划分的依据，拟定为客户等级
        like.groupBy("cust_level_label");

        // List<Map<String,Object>>list=service.selcetByComponent(regions, levelLables, satisfy);
        List<Map<String,Object>>list=service.listMaps(like);
        return Result.success(list);
    }
}
