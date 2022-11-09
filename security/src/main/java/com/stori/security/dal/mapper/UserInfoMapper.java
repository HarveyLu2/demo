package com.stori.security.dal.mapper;
/*
 * Copyright (c) Powerup Technology Inc. All rights reserved.
 */

import com.stori.security.dal.dao.UserInfoDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author Harvey Lu
 * @date 2022/11/02 11:49
 **/
@Mapper
@Component
public interface UserInfoMapper {
    UserInfoDo getUserByName(String userName);
}
