package com.dome.szjykjcompany;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class WarStartApplication  extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        //  指向Application 这个启动类
        return builder.sources(SzjykjCompanyApplication.class);
    }
}
