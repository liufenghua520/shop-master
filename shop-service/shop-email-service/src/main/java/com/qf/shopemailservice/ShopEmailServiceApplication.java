package com.qf.shopemailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.qf")
public class ShopEmailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopEmailServiceApplication.class, args);
    }

}
