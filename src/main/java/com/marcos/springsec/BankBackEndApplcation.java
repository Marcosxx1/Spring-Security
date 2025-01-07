package com.marcos.springsec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.marcos.springsec.repository")
public class BankBackEndApplcation {

    public static void main(String[] args) {
        SpringApplication.run(BankBackEndApplcation.class, args);
    }

}

