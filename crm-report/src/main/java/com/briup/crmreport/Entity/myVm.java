package com.briup.crmreport.Entity;

import lombok.Data;

/**
 * @author dell
 * 这是统计报表模块返回时封装的实体类
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
