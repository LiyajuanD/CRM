package com.briup.crmcustomer.service.impl;

import com.briup.crmcustomer.entity.Activity;
import com.briup.crmcustomer.entity.ext.ActivityExt;
import com.briup.crmcustomer.mapper.ActivityMapper;
import com.briup.crmcustomer.service.IActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author briup
 * @since 2022-11-22
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Autowired
    private  ActivityMapper mapper;

    @Override
    public List<ActivityExt> getAll(Long id) {
        return  mapper.getAll(id);
    }
}
