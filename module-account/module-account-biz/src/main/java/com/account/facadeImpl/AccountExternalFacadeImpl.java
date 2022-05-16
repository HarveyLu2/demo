package com.account.facadeImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.account.common.dal.dao.Account;
import com.account.common.dal.mapper.AccountMapper;
import com.account.common.facade.AccountExternalFacade;
import com.account.common.service.ModuleService;
import com.alibaba.fastjson.JSON;
import com.stori.sofa.model.Result;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * External Facade Implement
 *
 * @author king
 * @date 2022/05/07 13:54
 **/
@Service("accountExternalFacade")
public class AccountExternalFacadeImpl implements AccountExternalFacade {
    private static final Logger logger = LoggerFactory.getLogger(AccountExternalFacadeImpl.class);

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private MeterRegistry registry;

    /**
     * getAccount
     *
     * @date 2022/05/07 13:58
     * @return com.stori.sofa.model.Result<java.lang.String>
     */
    @Override
    public Result<String> getAccount() {
        registry.counter("AccountExternalFacade.getAccount.count").increment();

        String response = moduleService.sayHello();
        logger.info("AccountExternalFacade getAccount, from : {}.", response);

        Account account = accountMapper.selectById(1L);
        return Result.ok(JSON.toJSONString(account));
    }
}
