package com.briup.crmsystem.service;

import com.briup.crmsystem.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author briup
 * @since 2022-11-24
 */
public interface IRoleService extends IService<Role> {
    public String getNameById(Long id);
}
