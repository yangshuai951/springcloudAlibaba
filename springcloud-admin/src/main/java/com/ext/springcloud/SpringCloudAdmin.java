package com.ext.springcloud;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableAdminServer
public class SpringCloudAdmin {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudAdmin.class, args);
    }
}
