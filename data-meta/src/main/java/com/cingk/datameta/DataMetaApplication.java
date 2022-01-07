package com.cingk.datameta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.cingk")
public class DataMetaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataMetaApplication.class, args);
    }

}
