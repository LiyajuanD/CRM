package com.briup.crmsystem.mapper;

import com.briup.crmsystem.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author briup
 * @since 2022-11-24
 */
public interface RoleMapper extends BaseMapper<Role> {
    public Role getNameById(Long id);
}
