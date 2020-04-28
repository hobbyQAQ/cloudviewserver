package com.example.cloudviewserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.cloudviewserver.dao")
public class CloudviewserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudviewserverApplication.class, args);
    }

}
