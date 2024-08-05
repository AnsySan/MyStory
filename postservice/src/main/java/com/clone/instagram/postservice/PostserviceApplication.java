package com.clone.instagram.postservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.clone.instagram.client")
public class PostserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostserviceApplication.class, args);
    }

}
