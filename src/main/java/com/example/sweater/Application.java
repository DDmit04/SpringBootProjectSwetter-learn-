package com.example.sweater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration
//@ComponentScan(basePackages = { 
//	  "com.example.sweater.config" ,
//	  "com.example.sweater.controller" ,
//	  "com.example.sweater.domain" ,
//	  "com.example.sweater.repos" ,
//	  "com.example.sweater.service"
//	})
//@EnableJpaRepositories(basePackages = {"com.example.sweater.repos"})
//@EnableTransactionManagement
//@EntityScan(basePackages = {"com.example.sweater.domain"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
