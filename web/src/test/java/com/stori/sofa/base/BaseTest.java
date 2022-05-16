package com.stori.sofa.base;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.account.common.facade.AccountExternalFacade;
import com.account.common.facade.AccountInternalFacade;
import com.bill.common.facade.BillExternalFacade;
import com.bill.common.facade.BillInternalFacade;

/**
 * @author king
 * @date 2022/05/05 20:37
 **/
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTest {

    public MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    public AccountInternalFacade accountInternalFacade;

    @MockBean
    public AccountExternalFacade accountExternalFacade;

    @MockBean
    public BillInternalFacade billInternalFacade;

    @MockBean
    public BillExternalFacade billExternalFacade;

    /**
     * 前置步骤
     */
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
}
