package com.stori.sofa.dal.dao;
/*
    Copyright (c) Powerup Technology Inc. All rights reserved.
*/

import lombok.Data;

import java.util.Date;

/**
 * @author Harvey Lu
 * @date 2022/11/03 11:03
 **/
@Data
public class UserRoleDo {
    private Long id;
    private String userName;
    private String role;
    private Date createTimeLocal;
    private Date createTimeUtc;
    private Date updateTimeLocal;
    private Date updateTimeUtc;
}