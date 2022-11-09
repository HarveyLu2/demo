package com.stori.security.dal.mapper;
/*
    Copyright (c) Powerup Technology Inc. All rights reserved.
*/

import com.stori.security.dal.dao.RolePermissionDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Harvey Lu
 * @date 2022/11/03 10:54
 **/
@Mapper
@Component
public interface RolePermissionMapper {
    List<RolePermissionDo> getPermissionsByRole(String role);
}
