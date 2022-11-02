package com.stori.sofa.controller;
/*
 * Copyright (c) Powerup Technology Inc. All rights reserved.
 */

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Harvey Lu
 * @date 2022/10/28 11:17
 **/
@Controller
public class WebController {

    @ResponseBody
    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("toLogin")
    public String login() {
        return "login";
    }

    @ResponseBody
    @RequestMapping("doLogin")
    public String doLogin(String userName, String userPassword) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, userPassword);
        subject.login(token);
        return "login success";
    }

    @ExceptionHandler({ShiroException.class})
    @ResponseBody
    public String handleException(ShiroException e) {
        String errMsg = "";
        if (e instanceof UnknownAccountException) {
            errMsg = "user name is not exist";
        } else if (e instanceof IncorrectCredentialsException) {
            errMsg = "user password is not correct";
        } else if (e instanceof AuthorizationException) {
            errMsg = "don't have permission";
        } else {
            errMsg = "system error";
        }
        return errMsg;
    }
}