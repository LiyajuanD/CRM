package com.briup.crmsystem.service.impl;

import com.briup.crmsystem.entity.Role;
import com.briup.crmsystem.mapper.RoleMapper;
import com.briup.crmsystem.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author briup
 * @since 2022-11-24
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper mapper;
    @Override
    public String getNameById(Long id) {
        return mapper.getNameById(id).getRoleName();
    }
}
