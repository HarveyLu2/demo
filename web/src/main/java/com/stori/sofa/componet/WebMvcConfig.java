package com.stori.sofa.componet;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置页面访问拦截器
 * @author Harvey Lu
 * @date 2022/05/18
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/", "/login","/toLogin","/main");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/login").setViewName("login");

    }
}
