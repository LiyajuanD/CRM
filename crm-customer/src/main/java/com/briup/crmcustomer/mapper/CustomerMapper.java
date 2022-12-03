package com.briup.crmcustomer.mapper;

import com.briup.crmcustomer.entity.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author briup
 * @since 2022-11-18
 */
public interface CustomerMapper extends BaseMapper<Customer> {
        //List<Customer> FindByCustName(String name );
}
