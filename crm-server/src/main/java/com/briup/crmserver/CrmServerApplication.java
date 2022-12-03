package com.briup.crmserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CrmServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmServerApplication.class, args);

    }

}
