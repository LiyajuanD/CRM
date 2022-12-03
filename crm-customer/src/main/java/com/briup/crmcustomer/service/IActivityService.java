package com.briup.crmcustomer.service;

import com.briup.crmcustomer.entity.Activity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.briup.crmcustomer.entity.ext.ActivityExt;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author briup
 * @since 2022-11-22
 */
public interface IActivityService extends IService<Activity> {
    List<ActivityExt> getAll(Long id);
}
