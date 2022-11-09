package com.stori.sso.model;
/*
    Copyright (c) Powerup Technology Inc. All rights reserved.
*/

import lombok.Data;

/**
 * @author Harvey Lu
 * @date 2022/11/07 21:04
 **/
@Data
public class GoogleAccountInfo {
    private String userId;
    private String email;
    private boolean emailVerified;
    private String hostDomain;
}