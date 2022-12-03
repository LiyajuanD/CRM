package com.briup.crmcustomer.entity.ext;

import com.briup.crmcustomer.entity.Activity;
import com.briup.crmcustomer.entity.Customer;
import lombok.Data;

/**
 * @author dell
 */
@Data
public class ActivityExt extends Activity {
    private Customer customer;
}
