package com.briup.crmsystem.service.impl;

import com.briup.crmcommon.Exception.ServiceException;
import com.briup.crmcommon.utils.JwtUtil;
import com.briup.crmcommon.utils.Result;
import com.briup.crmcommon.utils.ResultCode;
import com.briup.crmsystem.config.userInfo;
import com.briup.crmsystem.controller.UserController;
import com.briup.crmsystem.entity.User;
import com.briup.crmsystem.mapper.UserMapper;
import com.briup.crmsystem.service.IRoleService;
import com.briup.crmsystem.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author briup
 * @since 2022-11-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper mapper;
    @Autowired
    private IRoleService service;
    @Override
    public String login(String username, String password) throws RuntimeException {
        User user = mapper.findByUsername(username);
        //用户不存在
        if (user==null){
           // throw new ServiceException(ResultCode.USER_NOT_EXIST);
            return "用户不存在";
        }
        if(!password.equals(user.getUsrPassword())){
            //throw new ServiceException(ResultCode.USER_LOGIN_ERROR);
            return "密码错误";
        }
        //账号的状态信息（0：正常，1：禁用）
        if(userInfo.USER_STATUS_NO.equals(user.getUsrFlag())){
            //throw new ServiceException(ResultCode.USER_ACCOUNT_FORBIDDEN);
            return "状态异常";
        }
        //执行成功，根据jwtutils生成唯一的token返回
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid",user.getUsrId());
        return JwtUtil.sign(username, map);
    }

    @Override
    public User getUserByNameAndPwd(String username, String password) {
        return mapper.findBynameAndPwd(username,password);
    }

    @Override
    public boolean saveOrUpdate(User entity) {
        //做一些新的逻辑上的增强，这里是给一些默认值，比如状态以及密码的默认值
        if(entity.getUsrId() == null) {
            String rolename = service.getNameById(entity.getUsrRoleId());
            //用到了三目运算符，在正式调用提供的方法之前，新增一些操作；
            String roleName = entity.getUsrRoleName() == null ? rolename : entity.getUsrRoleName();
            Integer flag = entity.getUsrFlag() == null ? 1 : entity.getUsrFlag();
            String password = entity.getUsrPassword() == null ? "123456" : entity.getUsrPassword();
            entity.setUsrRoleName(roleName);
            entity.setUsrFlag(flag);
            entity.setUsrPassword(password);
        }
        return super.saveOrUpdate(entity);

    }
}
