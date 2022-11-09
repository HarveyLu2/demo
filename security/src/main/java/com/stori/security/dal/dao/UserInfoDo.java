package com.stori.security.dal.dao;
/*
 * Copyright (c) Powerup Technology Inc. All rights reserved.
 */

import lombok.Data;

import java.util.Date;

/**
 * @author Harvey Lu
 * @date 2022/11/02 11:43
 **/
@Data
public class UserInfoDo {
    private Long id;
    private String userName;
    private String userPassword;

    private Date createTimeLocal;
    private Date createTimeUtc;
    private Date updateTimeLocal;
    private Date updateTimeUtc;
}