package com.ramostear.unaboot;

import com.ramostear.unaboot.repository.support.UnaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class},
                       scanBasePackages = "com.ramostear.unaboot")
@EnableJpaRepositories(basePackages = {"com.ramostear.unaboot.repository"},
                       repositoryBaseClass = UnaRepositoryImpl.class)
public class UnaBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnaBootApplication.class, args);
    }

}
