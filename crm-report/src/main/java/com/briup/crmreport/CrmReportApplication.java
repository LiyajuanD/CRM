package com.briup.crmreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.briup")
@EnableDiscoveryClient
@EnableFeignClients
public class CrmReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmReportApplication.class, args);
    }

}
