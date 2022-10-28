package com.account.common.dal.mapper;

import com.account.common.dal.dao.Account;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface AccountMapper {

    Account selectById(Long id);
}
