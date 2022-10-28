package com.stori.sofa.controller;

import com.account.common.dal.dao.RbacUser;
import com.account.common.facade.RbacAccountInternalFacade;
import com.stori.sofa.utils.EncodeByMd5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.ResourceBundle;

/**
 *
 * @author Harvey Lu
 * @date 2022/05/18
 */
@Controller
public class RbacLoginController {

    @Autowired
    private RbacAccountInternalFacade rbacAccountInternalFacade;

    private EncodeByMd5 encodeByMd5 = new EncodeByMd5();

    @RequestMapping("/toLogin")
    public String toLogin(HttpSession session, String userName, String userPassword){

        RbacUser rbacUser = rbacAccountInternalFacade.getUserByName(userName);

        if(rbacUser != null){
            String passwordSalt = userPassword + rbacUser.getSalt();
            // 如果用户成功登陆，获取用户权限存入session,返回main页面，显示有权限的访问项
            if (encodeByMd5.encodeByMd5(passwordSalt).toUpperCase().equals(rbacUser.getPasswordMd5()))
            {
                HashSet<String> permissionList= rbacAccountInternalFacade.getPermissionNameByUserName(rbacUser.getUserName());
                session.setAttribute("loginUser", rbacUser.getUserName());
                session.setAttribute("permissionList",permissionList);
                return "/main";
            }
        }
        return "/login";
    }

    @RequestMapping("/main")
    public String toMain(HttpSession session){
        String userName = (String)session.getAttribute("loginUser");
        // 如果用户未登陆，返回登陆页面
        if (userName == null){
            return "/login";
        }
        return "/main";
    }

    @RequestMapping("/user/index")
    public String toUserIndex(HttpSession session){
        return "/user/index";
    }

    @RequestMapping("/role/index")
    public String toRoleIndex(HttpSession session){
        return "/role/index";
    }

    @RequestMapping("/permission/index")
    public String toPermissionIndex(HttpSession session){
        return "/permission/index";
    }

    @RequestMapping("/default/index")
    public String toDefaultIndex(HttpSession session){
        return "/default/index";
    }

    @RequestMapping("/test/cookies")
    @ResponseBody
    public String testCookies(HttpSession session, HttpServletResponse response){
        response.setContentType("text/html;charset=UTF-8");
        Cookie cookie = new Cookie("username", "hhh");
        response.addCookie(cookie);
        return "testCookies";
    }

    @RequestMapping("/test/readcookies")
    @ResponseBody
    public String readCookies(HttpSession session, HttpServletRequest request, HttpServletResponse response){

        System.out.println(request.getRequestedSessionId());
        Cookie[] cookies = request.getCookies();
        for (Cookie each:cookies) {
            System.out.println(each.getName() + " " + each.getValue());
        }
        return request.getRequestedSessionId();
    }

}
