package com.briup.crmreport.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.briup.crmcommon.entity.Customer;
import com.briup.crmreport.mapper.CustomerMapper;
import com.briup.crmreport.service.ICustomerService;
import org.springframework.stereotype.Service;

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
