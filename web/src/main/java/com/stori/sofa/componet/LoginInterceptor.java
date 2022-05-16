package com.stori.sofa.componet;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        Object account = request.getSession().getAttribute("loginUser");
        Object permissionId = request.getSession().getAttribute("permissionId");
        if (account == null){
            request.setAttribute("msg", "you can't access");
            System.out.println(request.getSession().getAttribute("loginUser"));
            response.sendRedirect("main");
            return false;
        }else{
            System.out.println((String)account + " " + permissionId);
            return true;
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
