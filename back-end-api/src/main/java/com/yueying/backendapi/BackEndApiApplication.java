package com.yueying.backendapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yueying.backendapi.mapper")
public class BackEndApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackEndApiApplication.class, args);
    }

}
