package com.briup.crmservice;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.briup" )
@EnableDiscoveryClient
public class CrmServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmServiceApplication.class, args);
    }

}
