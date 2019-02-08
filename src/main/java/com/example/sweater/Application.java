package com.example.sweater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = { 
	  "com.example.sweater.config" ,
	  "com.example.sweater.controller" ,
	  "com.example.sweater.domain" ,
	  "com.example.sweater.repos" ,
	  "com.example.sweater.service"
	})
@EnableJpaRepositories(basePackages = {"com.example.sweater.repos"})
@EnableTransactionManagement
@EntityScan(basePackages = {"com.example.sweater.domain"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
