package com.briup.crmservice.mapper;

import com.briup.crmservice.entity.MyService;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author briup
 * @since 2022-11-28
 */
@Mapper
public interface ServiceMapper extends BaseMapper<MyService> {

}
