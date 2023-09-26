package com.example.lab81;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class Lab81Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab81Application.class, args);
    }

}
