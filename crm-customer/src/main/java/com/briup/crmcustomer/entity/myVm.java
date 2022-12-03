package com.briup.crmcustomer.entity;

import lombok.Data;

/**
 * @author dell
 */
@Data
public class myVm {
    private String address;
    private String percent;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
