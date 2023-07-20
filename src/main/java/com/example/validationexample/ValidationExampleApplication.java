package com.example.validationexample;

import com.example.validationexample.configuration.AiriConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ValidationExampleApplication implements CommandLineRunner {
    @Autowired
    private AiriConfig airiConfig;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ValidationExampleApplication.class);
        app.run();
    }

    public void run(String... args) throws Exception {
        System.out.println("host: " + airiConfig.getHost());
        System.out.println("port: " + airiConfig.getPort());
    }

}
