package com.briup.crmauth.vm;

import com.briup.crmcommon.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 登录信息模型
 */
@Data
@Getter
@Setter
public class UserVM {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
