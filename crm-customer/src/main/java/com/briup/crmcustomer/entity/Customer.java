package com.briup.crmcustomer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 自动生成的代码只能生成一般基本数据类型的字段，需要自己创建一个列表示当前基础列进行扩展的操作
 */
@TableName("cst_customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "cust_id", type = IdType.AUTO)
      private Long custId;

    private String custName;

    private String custRegion;

    private Long custManagerId;

    private String custManagerName;

    private String custLevelLabel;

    private Integer custSatisfy;

    private Integer custCredit;

    private String custAddr;

    private String custZip;

    private String custTel;

    private String custFax;

    private String custWebsite;

    private String custLicenceNo;

    private String custChieftain;

    private Double custBankroll;

    private Double custTurnover;

    private String custBank;

    private String custBankAccount;

    private String custLocalTaxNo;

    private String custNationalTaxNo;

    private String custStatus;
    
    public Long getCustId() {
        return custId;
    }

      public void setCustId(Long custId) {
          this.custId = custId;
      }
    
    public String getCustName() {
        return custName;
    }

      public void setCustName(String custName) {
          this.custName = custName;
      }
    
    public String getCustRegion() {
        return custRegion;
    }

      public void setCustRegion(String custRegion) {
          this.custRegion = custRegion;
      }
    
    public Long getCustManagerId() {
        return custManagerId;
    }

      public void setCustManagerId(Long custManagerId) {
          this.custManagerId = custManagerId;
      }
    
    public String getCustManagerName() {
        return custManagerName;
    }

      public void setCustManagerName(String custManagerName) {
          this.custManagerName = custManagerName;
      }
    
    public String getCustLevelLabel() {
        return custLevelLabel;
    }

      public void setCustLevelLabel(String custLevelLabel) {
          this.custLevelLabel = custLevelLabel;
      }
    
    public Integer getCustSatisfy() {
        return custSatisfy;
    }

      public void setCustSatisfy(Integer custSatisfy) {
          this.custSatisfy = custSatisfy;
      }
    
    public Integer getCustCredit() {
        return custCredit;
    }

      public void setCustCredit(Integer custCredit) {
          this.custCredit = custCredit;
      }
    
    public String getCustAddr() {
        return custAddr;
    }

      public void setCustAddr(String custAddr) {
          this.custAddr = custAddr;
      }
    
    public String getCustZip() {
        return custZip;
    }

      public void setCustZip(String custZip) {
          this.custZip = custZip;
      }
    
    public String getCustTel() {
        return custTel;
    }

      public void setCustTel(String custTel) {
          this.custTel = custTel;
      }
    
    public String getCustFax() {
        return custFax;
    }

      public void setCustFax(String custFax) {
          this.custFax = custFax;
      }
    
    public String getCustWebsite() {
        return custWebsite;
    }

      public void setCustWebsite(String custWebsite) {
          this.custWebsite = custWebsite;
      }
    
    public String getCustLicenceNo() {
        return custLicenceNo;
    }

      public void setCustLicenceNo(String custLicenceNo) {
          this.custLicenceNo = custLicenceNo;
      }
    
    public String getCustChieftain() {
        return custChieftain;
    }

      public void setCustChieftain(String custChieftain) {
          this.custChieftain = custChieftain;
      }
    
    public Double getCustBankroll() {
        return custBankroll;
    }

      public void setCustBankroll(Double custBankroll) {
          this.custBankroll = custBankroll;
      }
    
    public Double getCustTurnover() {
        return custTurnover;
    }

      public void setCustTurnover(Double custTurnover) {
          this.custTurnover = custTurnover;
      }
    
    public String getCustBank() {
        return custBank;
    }

      public void setCustBank(String custBank) {
          this.custBank = custBank;
      }
    
    public String getCustBankAccount() {
        return custBankAccount;
    }

      public void setCustBankAccount(String custBankAccount) {
          this.custBankAccount = custBankAccount;
      }
    
    public String getCustLocalTaxNo() {
        return custLocalTaxNo;
    }

      public void setCustLocalTaxNo(String custLocalTaxNo) {
          this.custLocalTaxNo = custLocalTaxNo;
      }
    
    public String getCustNationalTaxNo() {
        return custNationalTaxNo;
    }

      public void setCustNationalTaxNo(String custNationalTaxNo) {
          this.custNationalTaxNo = custNationalTaxNo;
      }
    
    public String getCustStatus() {
        return custStatus;
    }

      public void setCustStatus(String custStatus) {
          this.custStatus = custStatus;
      }

    @Override
    public String toString() {
        return "Customer{" +
              "custId = " + custId +
                  ", custName = " + custName +
                  ", custRegion = " + custRegion +
                  ", custManagerId = " + custManagerId +
                  ", custManagerName = " + custManagerName +
                  ", custLevelLabel = " + custLevelLabel +
                  ", custSatisfy = " + custSatisfy +
                  ", custCredit = " + custCredit +
                  ", custAddr = " + custAddr +
                  ", custZip = " + custZip +
                  ", custTel = " + custTel +
                  ", custFax = " + custFax +
                  ", custWebsite = " + custWebsite +
                  ", custLicenceNo = " + custLicenceNo +
                  ", custChieftain = " + custChieftain +
                  ", custBankroll = " + custBankroll +
                  ", custTurnover = " + custTurnover +
                  ", custBank = " + custBank +
                  ", custBankAccount = " + custBankAccount +
                  ", custLocalTaxNo = " + custLocalTaxNo +
                  ", custNationalTaxNo = " + custNationalTaxNo +
                  ", custStatus = " + custStatus +
              "}";
    }
}
