package com.stori.sofa.service;
/*
 * Copyright (c) Powerup Technology Inc. All rights reserved.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stori.sofa.dal.dao.UserInfoDo;
import com.stori.sofa.dal.mapper.UserInfoMapper;
import com.stori.sofa.model.UserInfoDTO;

/**
 * @author Harvey Lu
 * @date 2022/11/02 14:03
 **/
@Component
public class UserService {
    @Autowired
    UserInfoMapper userInfoMapper;

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
}