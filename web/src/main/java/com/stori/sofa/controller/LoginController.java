package com.stori.sofa.controller;

import com.account.common.dal.dao.RbacAccount;
import com.account.common.facade.RbacAccountInternalFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    private RbacAccountInternalFacade rbacAccountInternalFacade;


    @PostMapping("/toLogin")
    public String doLogin(@RequestParam("name") String name,
                          @RequestParam("password") String password,
                          HttpSession session){

        RbacAccount rbacAccount = rbacAccountInternalFacade.getAccount(name);

        if (rbacAccount !=null && password.equals(rbacAccount.getPassword())){
            session.setAttribute("loginUser", name);
            session.setAttribute("permissionId", rbacAccount.getPermission());
            if(rbacAccount.getPermission() == 3) {
                return "redirect:/toAdmin";
            } else if (rbacAccount.getPermission() == 2){
                return "redirect:/toUser";
            }else {
                return "redirect:/toDefault";
            }
        }else{
            System.out.println("Account login fail");
            return "/login";
        }
    }

    @RequestMapping("/toRegister")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class)
    public String toRegister(RbacAccount rbacAccount){

        if(rbacAccount.getRoleName().equals("default")) rbacAccount.setRoleId(1);
        else if(rbacAccount.getRoleName().equals("user")) rbacAccount.setRoleId(2);
        else if(rbacAccount.getRoleName().equals("admin")) rbacAccount.setRoleId(3);
        else {
            System.out.println("getRoleName fail");
            return "register";
        }
        System.out.println(rbacAccount.getName()+rbacAccount.getPassword()+rbacAccount.getRoleName());
        try {
            rbacAccountInternalFacade.addAccount(rbacAccount);
            return "login";
        }catch (Exception e){
            System.out.println("Add account fail");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "register";
        }

    }

    @RequestMapping("/toAdmin")
    public String toAdmin(HttpSession session){
        int permissionId = (int) session.getAttribute("permissionId");
        int permissionRank = 3;

        if(permissionId >= permissionRank){
            return "adminPage";
        }else{
            return "main";
        }
    }

    @RequestMapping("/toUser")
    public String toUser(HttpSession session){
        int permissionId = (int) session.getAttribute("permissionId");
        int permissionRank = 2;

        if(permissionId >= permissionRank){
            return "userPage";
        }else{
            return "main";
        }
    }
    @RequestMapping("/toDefault")
    public String toDefault(HttpSession session){
        int permissionId = (int) session.getAttribute("permissionId");
        int permissionRank = 1;

        if(permissionId >= permissionRank){
            return "defaultPage";
        }else{
            return "main";
        }
    }

    @RequestMapping("/logout")
    public String doLogout(HttpSession session){
        session.setAttribute("permissionId", -1);
        return "main";
    }



}
