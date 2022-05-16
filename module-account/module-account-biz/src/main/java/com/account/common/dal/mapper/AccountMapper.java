package com.account.common.dal.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.account.common.dal.dao.Account;

@Component
@Mapper
public interface AccountMapper {

    Account selectById(Long id);
}
