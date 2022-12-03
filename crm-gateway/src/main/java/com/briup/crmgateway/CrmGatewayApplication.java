package com.briup.crmgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.briup")
@EnableFeignClients
@EnableDiscoveryClient
public class CrmGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmGatewayApplication.class, args);
    }

}
