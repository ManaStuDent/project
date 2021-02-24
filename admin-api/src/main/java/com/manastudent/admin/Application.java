package com.manastudent.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.manastudent.admin", "com.manastudent.core"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}