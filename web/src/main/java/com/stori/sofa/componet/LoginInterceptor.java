package com.stori.sofa.componet;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

/**
 *
 * 实现访问控制器功能
 * @author Harvey Lu
 * @date 2022/05/18
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 请求URI之前进行处理
     * 用户已登录，且拥有访问权限则放行
     * 如果用户已登录，没有权限，返回用户主页面
     * 如果用户未登录，返回登录页面
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        Object account = request.getSession().getAttribute("loginUser");
        String requestUri = request.getRequestURI();
        String[] uris = requestUri.split("/");
        HashSet<String> permissionList = (HashSet<String>) request.getSession().getAttribute("permissionList");
        if (account == null){
            request.getSession().setAttribute("msg", "Please Login");
            response.sendRedirect("/login");
            return false;
        }
        if (permissionList.contains(uris[1])){
            return true;
        }else{
            request.getSession().setAttribute("msg", "You don't have permission");
            response.sendRedirect("/main");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws
            Exception{

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws
            Exception {

    }
}
