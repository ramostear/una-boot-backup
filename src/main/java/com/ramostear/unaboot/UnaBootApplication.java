package com.ramostear.unaboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UnaBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnaBootApplication.class, args);
    }

}
