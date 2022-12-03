package com.briup.crmreport.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.briup.crmcommon.entity.Customer;
import com.briup.crmcommon.utils.Result;
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
/*@Api(tags = "统计报表实现")
@RestController
@RequestMapping("/auth/report")*/
public class reportController {

    @Autowired
    private ICustomerService service;

    @ApiOperation("客户构成分析(分析每个地区的客户占比)")
    @GetMapping("/compose")
    public Result getCustCompose(){
        //查询总共有几个地区
        QueryWrapper<Customer> wrappercount = new QueryWrapper<Customer>().select("DISTINCT cust_region");
        List<Customer> list = service.list(wrappercount);
        //用来存储地区信息
        List<String> listAddress = new ArrayList<>();
        //遍历查询出来的客户后获取地区信息并存放在对应的集合中
        for (Customer customer : list) {
            listAddress.add(customer.getCustRegion());
        }
        //查看总共有多少个用户
        double sum = service.count();
        System.out.println("总数："+sum);
        //用来存放运算完的占比数据
        List<myVm> myVmList = new ArrayList<>();
        //遍历地区信息
        for (String s : listAddress) {
            //查看在这个地区的人有多少个
            QueryWrapper<Customer> wrapper = new QueryWrapper<Customer>()
                    .eq("cust_region", s);
            //查看符合条件的有多少个客户
            double count = service.count(wrapper);
            //封装为对象
            myVm myVm = new myVm();
            //算出占比并且封装为对象存储到集合中
            double pre = Double.parseDouble(String.format("%.2f", (count / sum)*100 ));
            System.out.println(pre);
            myVm.setPercent(pre+"%");
            myVm.setAddress(s);
            myVmList.add(myVm);
        }
       //返回处理完的集合
        return Result.success(myVmList);
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
