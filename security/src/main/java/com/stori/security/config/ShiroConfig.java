package com.stori.security.config;
/*
 * Copyright (c) Powerup Technology Inc. All rights reserved.
 */

import com.stori.security.filter.SsoAuthenticationFilter;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;

/**
 * @author Harvey Lu
 * @date 2022/11/02 10:38
 **/
@Configuration
public class ShiroConfig {

    @Bean(name = "securityManager")
    public SecurityManager securityManager(Realm realm, CacheManager shiroCacheManager,
        SessionManager shiroSessionManager) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm);
        manager.setCacheManager(shiroCacheManager);
        manager.setSessionManager(shiroSessionManager);
        return manager;
    }

    @Bean(name = "shiroFilterFactory")
    public ShiroFilterFactoryBean shiroFilterFactory(SecurityManager securityManager,
                                                     SsoAuthenticationFilter authenticationFilter) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        shiroFilterFactoryBean.setLoginUrl("http://localhost:8085/sso/index");

        LinkedHashMap<String, Filter> myFilter = new LinkedHashMap<>();
        myFilter.put("user", authenticationFilter);
        shiroFilterFactoryBean.setFilters(myFilter);

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("/sso/**", "anon");
        map.put("/hello", "user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public CacheManager shiroCacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public SessionManager shiroSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(30 * 1000);
        return sessionManager;
    }

    @Bean
    public SsoAuthenticationFilter ssoAuthenticationFilter() {
        SsoAuthenticationFilter filter = new SsoAuthenticationFilter();
        return filter;
    }


}