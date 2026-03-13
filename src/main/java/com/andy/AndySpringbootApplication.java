package com.andy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.andy.mapper")
public class AndySpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AndySpringbootApplication.class, args);
    }
}
