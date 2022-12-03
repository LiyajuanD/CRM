package com.briup.crmreport.Clients;

import com.briup.crmreport.Entity.myVm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author dell
 */
@FeignClient("crm-customer")
public interface CustomerClient {

    @GetMapping("/auth/customer/compose")
    List<myVm> getCustCompose();
}
