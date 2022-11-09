package com.stori.security.realm;
/*
 * Copyright (c) Powerup Technology Inc. All rights reserved.
 */

import com.stori.security.model.UserInfoDTO;
import com.stori.security.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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
        String userName = (String)principalCollection.getPrimaryPrincipal();
        Set<String> permissions = userService.getPermissionsByUserName(userName);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
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