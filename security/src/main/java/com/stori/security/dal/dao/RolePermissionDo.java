package com.stori.security.dal.dao;
/*
    Copyright (c) Powerup Technology Inc. All rights reserved.
*/


import lombok.Data;

import java.util.Date;

/**
 * @author Harvey Lu
 * @date 2022/11/03 10:17
 **/
@Data
public class RolePermissionDo {
    private Long id;
    private String permission;
    private String role;
    private Date createTimeLocal;
    private Date createTimeUtc;
    private Date updateTimeLocal;
    private Date updateTimeUtc;
}