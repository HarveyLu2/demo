package com.stori.sofa.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.stori.sofa.base.BaseTest;
import com.stori.sofa.model.Result;

/**
 * 测试用例
 *
 * @author king
 * @date 2022/05/05 19:32
 **/
public class TestModuleAccountControllerTest extends BaseTest {

    /**
     * 前置步骤
     */
    @Before
    public void setUp() {
        init();
    }

    @Test
    public void testModuleAccount() throws Exception {
        // 测试桩
        Mockito.when(accountInternalFacade.getAccount()).thenReturn(Result.ok("true"));
        Mockito.when(accountExternalFacade.getAccount()).thenReturn(Result.ok("true"));

        MvcResult mvcResult =
            mockMvc.perform(MockMvcRequestBuilders.get("/test/module/account").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        Assert.assertEquals(200, mvcResult.getResponse().getStatus());
        String result = mvcResult.getResponse().getContentAsString();
        // Result<String> responseWrapper = new ObjectMapper().readValue(result, Result.class);
        Assert.assertEquals("accountInternal is: true, accountExternal is: true", result);
    }
}