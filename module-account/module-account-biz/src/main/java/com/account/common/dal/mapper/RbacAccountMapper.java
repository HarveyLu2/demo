package com.account.common.dal.mapper;

import com.account.common.dal.dao.RbacAccount;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface RbacAccountMapper {

    RbacAccount selectById(int id);

    RbacAccount selectByName(String name);

    void insertAccount(RbacAccount rbacAccount);

    void insertRole(RbacAccount rbacAccount);
}
