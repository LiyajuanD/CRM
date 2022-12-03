package com.briup.crmcustomer.entity;

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
 * @since 2022-11-22
 */
@TableName("cst_activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "atv_id", type = IdType.AUTO)
      private Long atvId;

    private String atvCustName;

    private String atvPlace;

    private String atvTitle;

    private String atvMemo;

    private Long atvCustId;
    
    public Long getAtvId() {
        return atvId;
    }

      public void setAtvId(Long atvId) {
          this.atvId = atvId;
      }
    
    public String getAtvCustName() {
        return atvCustName;
    }

      public void setAtvCustName(String atvCustName) {
          this.atvCustName = atvCustName;
      }
    
    public String getAtvPlace() {
        return atvPlace;
    }

      public void setAtvPlace(String atvPlace) {
          this.atvPlace = atvPlace;
      }
    
    public String getAtvTitle() {
        return atvTitle;
    }

      public void setAtvTitle(String atvTitle) {
          this.atvTitle = atvTitle;
      }
    
    public String getAtvMemo() {
        return atvMemo;
    }

      public void setAtvMemo(String atvMemo) {
          this.atvMemo = atvMemo;
      }
    
    public Long getAtvCustId() {
        return atvCustId;
    }

      public void setAtvCustId(Long atvCustId) {
          this.atvCustId = atvCustId;
      }

    @Override
    public String toString() {
        return "Activity{" +
              "atvId = " + atvId +
                  ", atvCustName = " + atvCustName +
                  ", atvPlace = " + atvPlace +
                  ", atvTitle = " + atvTitle +
                  ", atvMemo = " + atvMemo +
                  ", atvCustId = " + atvCustId +
              "}";
    }
}
