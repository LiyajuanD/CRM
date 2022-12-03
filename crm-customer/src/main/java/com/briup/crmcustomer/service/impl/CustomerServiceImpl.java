package com.briup.crmcustomer.service.impl;

import com.briup.crmcustomer.entity.Customer;
import com.briup.crmcustomer.mapper.CustomerMapper;
import com.briup.crmcustomer.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author briup
 * @since 2022-11-18
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {
/*    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public List<Customer> FindByCustName(String name) {
        return customerMapper.FindByCustName(name);
    }*/
}
