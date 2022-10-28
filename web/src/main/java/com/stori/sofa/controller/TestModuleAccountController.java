package com.stori.sofa.controller;

import com.account.common.facade.AccountExternalFacade;
import com.account.common.facade.AccountInternalFacade;
import com.stori.sofa.model.Result;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * mysql test controller
 * 
 * @author king
 * @date 2022/05/05 18:34
 **/
@RestController
public class TestModuleAccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestModuleAccountController.class);

    @Autowired
    private AccountInternalFacade accountInternalFacade;

    @Autowired
    private AccountExternalFacade accountExternalFacade;

    @Autowired
    private MeterRegistry registry;

    /**
     * module-account test function
     *
     * @date 2022/05/05 19:25
     * @return java.lang.String
     */
    @GetMapping("/test/module/account")
    public String testModuleAccount() throws IOException {
        registry.counter("TestModuleAccountController.ModuleAccount.count").increment();

        Result<String> accountInternal = accountInternalFacade.getAccount();
        Result<String> accountExternal = accountExternalFacade.getAccount();
        return "accountInternal is: " + accountInternal.getData() + ", accountExternal is: "
            + accountExternal.getData();
//        return "get account success";
    }
}
