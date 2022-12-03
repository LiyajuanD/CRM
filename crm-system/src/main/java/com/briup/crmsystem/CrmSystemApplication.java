package com.briup.crmsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CrmSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmSystemApplication.class, args);
    }

}
