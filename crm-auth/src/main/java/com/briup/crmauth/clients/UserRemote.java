package com.briup.crmauth.clients;


import com.briup.crmcommon.entity.User;
import com.briup.crmcommon.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * system服务的远程调用
 */
@FeignClient("crm-system")
public interface UserRemote {


    @PostMapping("/auth/system/findUserByName")
    User selectUserByUsername(@RequestBody String name);

    @PostMapping("/auth/role/findRoleById")
    String findByRoleId(@RequestBody Long id);

    @PostMapping("/auth/system/deleteById")
    String deleteByUserId(@RequestParam(value = "需要删除的用户id") Long id,@RequestParam String name);
}