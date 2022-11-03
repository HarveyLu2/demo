package com.stori.sofa.service;
/*
 * Copyright (c) Powerup Technology Inc. All rights reserved.
 */

import com.stori.sofa.dal.dao.RolePermissionDo;
import com.stori.sofa.dal.dao.UserInfoDo;
import com.stori.sofa.dal.dao.UserRoleDo;
import com.stori.sofa.dal.mapper.RolePermissionMapper;
import com.stori.sofa.dal.mapper.UserInfoMapper;
import com.stori.sofa.dal.mapper.UserRoleMapper;
import com.stori.sofa.model.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Harvey Lu
 * @date 2022/11/02 14:03
 **/
@Component
public class UserService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    RolePermissionMapper rolePermissionMapper;

    public UserInfoDTO getUserDTO(String userName) {
        UserInfoDo userInfoDo = userInfoMapper.getUserByName(userName);
        if (null == userInfoDo) {
            return null;
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserName(userInfoDo.getUserName());
        userInfoDTO.setUserPassword(userInfoDo.getUserPassword());
        return userInfoDTO;
    }

    public Set<String> getPermissionsByUserName(String userName) {
        List<String> roles =
            userRoleMapper.getRoleByUser(userName).stream().map(UserRoleDo::getRole).collect(Collectors.toList());
        HashSet<String> permissions = new HashSet<>();
        for (String role : roles) {
            permissions.addAll(rolePermissionMapper.getPermissionsByRole(role).stream()
                .map(RolePermissionDo::getPermission).collect(Collectors.toSet()));
        }
        return permissions;
    }
}