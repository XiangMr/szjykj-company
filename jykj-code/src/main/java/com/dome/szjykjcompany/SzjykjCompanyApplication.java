package com.dome.szjykjcompany;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dome.szjykjcompany.mapper")
public class SzjykjCompanyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SzjykjCompanyApplication.class, args);
    }

}
