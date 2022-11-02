package com.stori.sofa.security;
/*
 * Copyright (c) Powerup Technology Inc. All rights reserved.
 */

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stori.sofa.model.UserInfoDTO;
import com.stori.sofa.service.UserService;

/**
 * @author Harvey Lu
 * @date 2022/11/02 11:22
 **/
@Service
public class ShiroUserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
        throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String userName = token.getUsername();
        UserInfoDTO userInfoDTO = userService.getUserDTO(userName);
        if (null == userInfoDTO) {
            throw new UnknownAccountException();
        }

        return new SimpleAuthenticationInfo(userName, userInfoDTO.getUserPassword(), getName());
    }


}