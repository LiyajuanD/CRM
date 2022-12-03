package com.briup.crmsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author briup
 * @since 2022-11-21
 */
@Data
@Getter
@Setter
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "usr_id", type = IdType.AUTO)
      private Long usrId;

    private String usrName;

    private String usrPassword;

    private String usrEmail;

    private String usrRoleName;

    private Integer usrFlag;

    private Long usrRoleId;
    
    public Long getUsrId() {
        return usrId;
    }

    public void setUsrId(Long usrId) {
          this.usrId = usrId;
      }
    
    public String getUsrName() {
        return usrName;
    }

      public void setUsrName(String usrName) {
          this.usrName = usrName;
      }
    
    public String getUsrPassword() {
        return usrPassword;
    }

      public void setUsrPassword(String usrPassword) {
          this.usrPassword = usrPassword;
      }
    
    public String getUsrEmail() {
        return usrEmail;
    }

      public void setUsrEmail(String usrEmail) {
          this.usrEmail = usrEmail;
      }
    
    public String getUsrRoleName() {
        return usrRoleName;
    }

      public void setUsrRoleName(String usrRoleName) {
          this.usrRoleName = usrRoleName;
      }
    
    public Integer getUsrFlag() {
        return usrFlag;
    }

      public void setUsrFlag(Integer usrFlag) {
          this.usrFlag = usrFlag;
      }
    
    public Long getUsrRoleId() {
        return usrRoleId;
    }

      public void setUsrRoleId(Long usrRoleId) {
          this.usrRoleId = usrRoleId;
      }

    @Override
    public String toString() {
        return "User{" +
              "usrId = " + usrId +
                  ", usrName = " + usrName +
                  ", usrPassword = " + usrPassword +
                  ", usrEmail = " + usrEmail +
                  ", usrRoleName = " + usrRoleName +
                  ", usrFlag = " + usrFlag +
                  ", usrRoleId = " + usrRoleId +
              "}";
    }
}
