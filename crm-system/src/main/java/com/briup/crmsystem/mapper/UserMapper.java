package com.briup.crmsystem.mapper;

import com.briup.crmsystem.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author briup
 * @since 2022-11-21
 */

public interface UserMapper extends BaseMapper<User> {


    User findByUsername(String name);
    User findBynameAndPwd(String name,String pwd);
}
