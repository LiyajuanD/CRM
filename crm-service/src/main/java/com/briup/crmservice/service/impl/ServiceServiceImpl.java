package com.briup.crmservice.service.impl;

import com.briup.crmservice.entity.MyService;

import com.briup.crmservice.mapper.ServiceMapper;
import com.briup.crmservice.service.IServiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author briup
 * @since 2022-11-28
 */
@Service
public class ServiceServiceImpl extends ServiceImpl<ServiceMapper, MyService> implements IServiceService {

}
