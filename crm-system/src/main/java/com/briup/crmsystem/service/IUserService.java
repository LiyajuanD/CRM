package com.briup.crmsystem.service;

import com.briup.crmcommon.Exception.ServiceException;
import com.briup.crmsystem.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author briup
 * @since 2022-11-21
 */
public interface IUserService extends IService<User> {
    String login(String username,String password) throws RuntimeException;
    User getUserByNameAndPwd(String username,String password);
}
