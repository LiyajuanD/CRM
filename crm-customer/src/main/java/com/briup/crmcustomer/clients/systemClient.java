package com.briup.crmcustomer.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author dell
 */
@FeignClient(name="crm-system")
public interface systemClient {


}
