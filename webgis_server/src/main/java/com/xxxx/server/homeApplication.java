package com.xxxx.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

/*
启动类
 */
@SpringBootApplication
@MapperScan("com.xxxx.server.mapper")
public class homeApplication {
    public static void main(String[] args){
        SpringApplication.run(homeApplication.class,args);
    }
}
