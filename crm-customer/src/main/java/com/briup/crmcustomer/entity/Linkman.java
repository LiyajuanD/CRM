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
 * @since 2022-11-21
 */
@TableName("cst_linkman")
public class Linkman implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "lkm_id", type = IdType.AUTO)
      private Long lkmId;

    private Long lkmCustId;

    private String lkmName;

    private String lkmSex;

    private String lkmPostion;

    private String lkmTel;

    private String lkmMobile;

    private String lkmMemo;
    
    public Long getLkmId() {
        return lkmId;
    }

    public void setLkmId(Long lkmId) {
          this.lkmId = lkmId;
      }
    
    public Long getLkmCustId() {
        return lkmCustId;
    }

      public void setLkmCustId(Long lkmCustId) {
          this.lkmCustId = lkmCustId;
      }
    
    public String getLkmName() {
        return lkmName;
    }

      public void setLkmName(String lkmName) {
          this.lkmName = lkmName;
      }
    
    public String getLkmSex() {
        return lkmSex;
    }

      public void setLkmSex(String lkmSex) {
          this.lkmSex = lkmSex;
      }
    
    public String getLkmPostion() {
        return lkmPostion;
    }

      public void setLkmPostion(String lkmPostion) {
          this.lkmPostion = lkmPostion;
      }
    
    public String getLkmTel() {
        return lkmTel;
    }

      public void setLkmTel(String lkmTel) {
          this.lkmTel = lkmTel;
      }
    
    public String getLkmMobile() {
        return lkmMobile;
    }

      public void setLkmMobile(String lkmMobile) {
          this.lkmMobile = lkmMobile;
      }
    
    public String getLkmMemo() {
        return lkmMemo;
    }

      public void setLkmMemo(String lkmMemo) {
          this.lkmMemo = lkmMemo;
      }

    @Override
    public String toString() {
        return "Linkman{" +
              "lkmId = " + lkmId +
                  ", lkmCustId = " + lkmCustId +
                  ", lkmName = " + lkmName +
                  ", lkmSex = " + lkmSex +
                  ", lkmPostion = " + lkmPostion +
                  ", lkmTel = " + lkmTel +
                  ", lkmMobile = " + lkmMobile +
                  ", lkmMemo = " + lkmMemo +
              "}";
    }
}
