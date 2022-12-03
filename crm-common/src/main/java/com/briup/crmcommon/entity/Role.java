package com.briup.crmcommon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author briup
 * @since 2022-11-24
 */
@TableName("sys_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "role_id", type = IdType.AUTO)
      private Long roleId;

    private String roleName;

    private String roleDesc;

    private Integer roleFlag;
    
    public Long getRoleId() {
        return roleId;
    }

      public void setRoleId(Long roleId) {
          this.roleId = roleId;
      }
    
    public String getRoleName() {
        return roleName;
    }

      public void setRoleName(String roleName) {
          this.roleName = roleName;
      }
    
    public String getRoleDesc() {
        return roleDesc;
    }

      public void setRoleDesc(String roleDesc) {
          this.roleDesc = roleDesc;
      }
    
    public Integer getRoleFlag() {
        return roleFlag;
    }

      public void setRoleFlag(Integer roleFlag) {
          this.roleFlag = roleFlag;
      }

    @Override
    public String toString() {
        return "Role{" +
              "roleId = " + roleId +
                  ", roleName = " + roleName +
                  ", roleDesc = " + roleDesc +
                  ", roleFlag = " + roleFlag +
              "}";
    }
}
