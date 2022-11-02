package com.stori.sofa.dal.mapper;
/*
 * Copyright (c) Powerup Technology Inc. All rights reserved.
 */

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.stori.sofa.dal.dao.UserInfoDo;

/**
 * @author Harvey Lu
 * @date 2022/11/02 11:49
 **/
@Mapper
@Component
public interface UserInfoMapper {
    UserInfoDo getUserByName(String userName);
}
