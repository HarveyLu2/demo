package com.stori.security.dal.mapper;
/*
    Copyright (c) Powerup Technology Inc. All rights reserved.
*/

import com.stori.security.dal.dao.UserRoleDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Harvey Lu
 * @date 2022/11/03 11:02
 **/
@Mapper
@Component
public interface UserRoleMapper {
    List<UserRoleDo> getRoleByUser(String user);
}
