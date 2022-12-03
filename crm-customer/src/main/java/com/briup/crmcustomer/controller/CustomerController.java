package com.briup.crmcustomer.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.briup.crmcustomer.entity.Customer;

import com.briup.crmcustomer.entity.myVm;
import com.briup.crmcustomer.mapper.CustomerMapper;
import com.briup.crmcustomer.service.ICustomerService;
import com.briup.crmcustomer.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author briup
 * @since 2022-11-18
 */
@Api(tags ="客户管理模块")
@RestController
@RequestMapping("/auth/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private CustomerMapper mapper;

    //客户信息展示
    @ApiOperation("分页查询用户信息")
    @GetMapping("/page")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNum",value = "页数",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页的数量",required = true),
    })
    public Result FindByPage(Integer pageNum,Integer pageSize){
        if (pageNum <=0){
            return Result.failure(2222,"页数异常，请重新输入，输入的页数应该大于0");
        }else {
            Page<Customer> page = customerService.page(new Page<>(pageNum, pageSize));
            return Result.success(page.getRecords());
        }
    }

    //客户信息展示
    @ApiOperation("模糊查询用户信息")
    @GetMapping("/custlike")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name",value = "需要查询的客户名称",required = false),
            @ApiImplicitParam(name = "region",value = "需要查询的客户所在地区",required = false),
            @ApiImplicitParam(name = "level",value = "需要查询的客户等级",required = false),
    })
    public Result FindByCustRegion(String name,String region,String level){
        //这里使用了service的list方法，用条件构造器的方法查询
        //select * from cust where cust_name like %name% and cust_region like %region% and cust_level_label like %level%
        QueryWrapper<Customer> wrapper = new QueryWrapper<Customer>()
                .like("cust_name", name)
                .and(i -> i.like("cust_region", region).like("cust_level_label", level));
        List<Customer> list = null;

        //如果名字不为空就优先使用名字查询，名字为空优先使用地区查询，地区为空优先使用级别查询
        if(name != null){
            list = customerService.list(new QueryWrapper<Customer>().like("cust_name", name));
        }
        if(region !=null){
            list = customerService.list(new QueryWrapper<Customer>().like("cust_region", region));
        }
        if(level !=null){
            list = customerService.list(new QueryWrapper<Customer>().like("cust_level_label", level));
        }
        //三个都不为空，就三个一起查询
        if(name != null && region !=null &&  level !=null ){
            list = customerService.list(wrapper);
        }
        return Result.success(list);

      /*  List<Customer> customers1 = customerService.FindByCustName(name);
        return Result.success(customers1);*/
    }

    @ApiOperation("新增用户")
    @PostMapping("/save")
    public Result saveCustomer(@RequestBody Customer customer){
        customerService.save(customer);
        return Result.success();
        /*
        {
      "custName": "hua",
      "custRegion": "华东",
      "custManagerId": 4,
      "custManagerName": "jingli",
      "custLevelLabel": "大客户",
      "custSatisfy": 2,
      "custCredit": 1,
      "custAddr": "华北",
      "custZip": "226400",
      "custTel": "132423432",
      "custFax": "56ggsre3",
      "custWebsite": "298@163.com",
      "custLicenceNo": "3423",
      "custChieftain": "ls",
      "custBankroll": 30,
      "custTurnover": 20,
      "custBank": "农行",
      "custBankAccount": "333",
      "custLocalTaxNo": "222",
      "custNationalTaxNo": "111",
      "custStatus": "1"
    }
         */
    }
    @PutMapping("/update")
    @ApiOperation("新增或修改用户信息")
    public Result updateCust(@RequestBody Customer customer){
        //这里使用mybatispuls的saveorupdate方法，会自动根据传入的信息执行操作，
        //先判断是否存在id值，若存在，则执行更新操作，若不存在，则执行新增操作
        customerService.saveOrUpdate(customer);
        return Result.success();
    }

    @DeleteMapping("/remove")
    @ApiOperation("批量删除用户信息")
    public Result deleteCust(@RequestParam List<Integer> ids){
        customerService.removeByIds(ids);
        return Result.success();
    }

    @ApiOperation("客户构成分析(分析每个地区的客户占比)")
    @GetMapping("/compose")
    public List<myVm> getCustCompose(){
        System.out.println("远程调用成功");
        //查询总共有几个地区
        QueryWrapper<Customer> wrappercount = new QueryWrapper<Customer>().select("DISTINCT cust_region");
        List<Customer> list = customerService.list(wrappercount);
        //用来存储地区信息
        List<String> listAddress = new ArrayList<>();
        //遍历查询出来的客户后获取地区信息并存放在对应的集合中
        for (Customer customer : list) {
            listAddress.add(customer.getCustRegion());
        }
        //查看总共有多少个用户
        double sum = customerService.count();
        System.out.println("总数："+sum);
        //用来存放运算完的占比数据
        List<myVm> myVmList = new ArrayList<>();
        //遍历地区信息
        for (String s : listAddress) {
            //查看在这个地区的人有多少个
            QueryWrapper<Customer> wrapper = new QueryWrapper<Customer>()
                    .eq("cust_region", s);
            //查看符合条件的有多少个客户
            double count = customerService.count(wrapper);
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
        return myVmList ;
    }
}
