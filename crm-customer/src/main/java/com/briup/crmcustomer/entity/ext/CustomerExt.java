package com.briup.crmcustomer.entity.ext;

import com.briup.crmcustomer.entity.Activity;
import com.briup.crmcustomer.entity.Customer;
import com.briup.crmcustomer.entity.Linkman;
import com.briup.crmcommon.entity.User;

import java.util.List;

/**
 * @author dell
 * 扩展类，表示类与类之间的关系
 */
public class CustomerExt extends Customer {
    private List<Linkman> linkmen;
    private List<Activity> activities;
    private User user;
}
