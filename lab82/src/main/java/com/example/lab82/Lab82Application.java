package com.example.lab82;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Lab82Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab82Application.class, args);
    }

}
