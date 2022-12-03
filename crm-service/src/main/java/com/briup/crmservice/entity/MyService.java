package com.briup.crmservice.entity;

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
 * @since 2022-11-28
 */
@TableName("cst_service")
public class MyService implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "svr_id", type = IdType.AUTO)
      private Long svrId;

    private String svrType;

    private String svrTitle;

    private String svrCustName;

    private String svrStatus;

    private String svrRequest;

    private String svrDispose;

    private String svrHandle;

    private String svrResult;

    private String svrSatisfy;
    
    public Long getSvrId() {
        return svrId;
    }

      public void setSvrId(Long svrId) {
          this.svrId = svrId;
      }
    
    public String getSvrType() {
        return svrType;
    }

      public void setSvrType(String svrType) {
          this.svrType = svrType;
      }
    
    public String getSvrTitle() {
        return svrTitle;
    }

      public void setSvrTitle(String svrTitle) {
          this.svrTitle = svrTitle;
      }
    
    public String getSvrCustName() {
        return svrCustName;
    }

      public void setSvrCustName(String svrCustName) {
          this.svrCustName = svrCustName;
      }
    
    public String getSvrStatus() {
        return svrStatus;
    }

      public void setSvrStatus(String svrStatus) {
          this.svrStatus = svrStatus;
      }
    
    public String getSvrRequest() {
        return svrRequest;
    }

      public void setSvrRequest(String svrRequest) {
          this.svrRequest = svrRequest;
      }
    
    public String getSvrDispose() {
        return svrDispose;
    }

      public void setSvrDispose(String svrDispose) {
          this.svrDispose = svrDispose;
      }
    
    public String getSvrHandle() {
        return svrHandle;
    }

      public void setSvrHandle(String svrHandle) {
          this.svrHandle = svrHandle;
      }
    
    public String getSvrResult() {
        return svrResult;
    }

      public void setSvrResult(String svrResult) {
          this.svrResult = svrResult;
      }
    
    public String getSvrSatisfy() {
        return svrSatisfy;
    }

      public void setSvrSatisfy(String svrSatisfy) {
          this.svrSatisfy = svrSatisfy;
      }

    @Override
    public String toString() {
        return "Service{" +
              "svrId = " + svrId +
                  ", svrType = " + svrType +
                  ", svrTitle = " + svrTitle +
                  ", svrCustName = " + svrCustName +
                  ", svrStatus = " + svrStatus +
                  ", svrRequest = " + svrRequest +
                  ", svrDispose = " + svrDispose +
                  ", svrHandle = " + svrHandle +
                  ", svrResult = " + svrResult +
                  ", svrSatisfy = " + svrSatisfy +
              "}";
    }
}
