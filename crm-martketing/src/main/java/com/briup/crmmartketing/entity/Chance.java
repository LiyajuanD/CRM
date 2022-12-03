package com.briup.crmmartketing.entity;

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
@TableName("sal_chance")
public class Chance implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "chc_id", type = IdType.AUTO)
      private Long chcId;

    private String chcSource;

    private String chcCustName;

    private String chcTitle;

    private Integer chcRate;

    private String chcLinkman;

    private String chcTel;

    private String chcAddr;

    private String chcDesc;

    private String chcCreateBy;

    private String chcDueTo;

    private Integer chcStatus;
    
    public Long getChcId() {
        return chcId;
    }

      public void setChcId(Long chcId) {
          this.chcId = chcId;
      }
    
    public String getChcSource() {
        return chcSource;
    }

      public void setChcSource(String chcSource) {
          this.chcSource = chcSource;
      }
    
    public String getChcCustName() {
        return chcCustName;
    }

      public void setChcCustName(String chcCustName) {
          this.chcCustName = chcCustName;
      }
    
    public String getChcTitle() {
        return chcTitle;
    }

      public void setChcTitle(String chcTitle) {
          this.chcTitle = chcTitle;
      }
    
    public Integer getChcRate() {
        return chcRate;
    }

      public void setChcRate(Integer chcRate) {
          this.chcRate = chcRate;
      }
    
    public String getChcLinkman() {
        return chcLinkman;
    }

      public void setChcLinkman(String chcLinkman) {
          this.chcLinkman = chcLinkman;
      }
    
    public String getChcTel() {
        return chcTel;
    }

      public void setChcTel(String chcTel) {
          this.chcTel = chcTel;
      }
    
    public String getChcAddr() {
        return chcAddr;
    }

      public void setChcAddr(String chcAddr) {
          this.chcAddr = chcAddr;
      }
    
    public String getChcDesc() {
        return chcDesc;
    }

      public void setChcDesc(String chcDesc) {
          this.chcDesc = chcDesc;
      }
    
    public String getChcCreateBy() {
        return chcCreateBy;
    }

      public void setChcCreateBy(String chcCreateBy) {
          this.chcCreateBy = chcCreateBy;
      }
    
    public String getChcDueTo() {
        return chcDueTo;
    }

      public void setChcDueTo(String chcDueTo) {
          this.chcDueTo = chcDueTo;
      }
    
    public Integer getChcStatus() {
        return chcStatus;
    }

      public void setChcStatus(Integer chcStatus) {
          this.chcStatus = chcStatus;
      }

    @Override
    public String toString() {
        return "Chance{" +
              "chcId = " + chcId +
                  ", chcSource = " + chcSource +
                  ", chcCustName = " + chcCustName +
                  ", chcTitle = " + chcTitle +
                  ", chcRate = " + chcRate +
                  ", chcLinkman = " + chcLinkman +
                  ", chcTel = " + chcTel +
                  ", chcAddr = " + chcAddr +
                  ", chcDesc = " + chcDesc +
                  ", chcCreateBy = " + chcCreateBy +
                  ", chcDueTo = " + chcDueTo +
                  ", chcStatus = " + chcStatus +
              "}";
    }
}
