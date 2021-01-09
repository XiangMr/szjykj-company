package com.dome.szjykjcompany.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class applicationUtils {

    @Value("${pwd.encryption.hashAlgorithmName}")
    public  String gethashAlgorithmName;

    @Value("${pwd.encryption.hashIterations}")
    public  int gethashIterations;

    @Value("${setredis.ceche}")
    public boolean getredisSwitch;
}
