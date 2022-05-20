package com.stori.sofa.controller;

import com.account.common.dal.dao.RbacUser;
import com.account.common.facade.RbacAccountInternalFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

/**
 * User Controller
 * The class is used to edit user info
 * @author Harvey Lu
 * @date 2022/05/18
 * */
@Controller
@RequestMapping("/user")
public class RbacUserController {

    @Autowired
    private RbacAccountInternalFacade rbacAccountInternalFacade;

    @RequestMapping("/queryUserAll")
    public String queryUserAll(HttpSession session){
        List<RbacUser> userList =  rbacAccountInternalFacade.queryUserAll();
        session.setAttribute("userList",userList);
        return "/user/index";
    }

    @RequestMapping("/addUser")
    public String addUser(HttpSession session,
                          String userName, Integer userId, String password) throws ParseException {
        RbacUser rbacUser = new RbacUser();
        rbacUser.setUserId(userId);
        rbacUser.setUserName(userName);
        rbacAccountInternalFacade.addUser(rbacUser, password);
        session.setAttribute("addUserMsg","添加用户成功!");
        return "/user/index";
    }

    @RequestMapping("/updateUser")
    public String updateUser(HttpSession session,
                          String userName, Integer userId, String password) throws ParseException {
        RbacUser rbacUser = new RbacUser();
        rbacUser.setUserId(userId);
        rbacUser.setUserName(userName);
        rbacAccountInternalFacade.updateUser(rbacUser, password);
        session.setAttribute("updateUserMsg","修改用户成功!");
        return "/user/index";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(HttpSession session, int userId){
        rbacAccountInternalFacade.deleteUser(userId);

        session.setAttribute("deleteUserMsg","删除用户成功!");
        return "/user/index";
    }


}
