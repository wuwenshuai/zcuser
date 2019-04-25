package com.carry;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.carry.mapper") // 扫描mapper包
public class ZcUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZcUserApplication.class,args);
    }
}
