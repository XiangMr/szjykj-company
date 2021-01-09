package com.dome.szjykjcompany.shiro.advice;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    /**
     * 未授权
     * 程序一旦抛出UnauthorizedException就会触发该方法
     */
    @ExceptionHandler(value = UnauthorizedException.class )
    public String error(){
        return "/unauthorize.html";
    }
}
