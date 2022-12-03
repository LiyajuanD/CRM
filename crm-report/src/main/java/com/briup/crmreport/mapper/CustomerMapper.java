package com.briup.crmreport.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.briup.crmcommon.entity.Customer;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author briup
 * @since 2022-11-18
 */
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
        //List<Customer> FindByCustName(String name );
}
