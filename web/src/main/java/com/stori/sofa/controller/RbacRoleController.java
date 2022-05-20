package com.stori.sofa.controller;

import com.account.common.dal.dao.RbacRole;
import com.account.common.dal.dao.RbacUser;
import com.account.common.dal.dao.RbacUserRole;
import com.account.common.facade.RbacAccountInternalFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Harvey Lu
 * @date 2022/05/18
 */
@Controller
@RequestMapping("/role")
public class RbacRoleController {

    @Autowired
    private RbacAccountInternalFacade rbacAccountInternalFacade;


    @RequestMapping("/queryUserAll")
    public String queryUserAll(HttpSession session){
        List<RbacUser> userList =  rbacAccountInternalFacade.queryUserAll();
        session.setAttribute("userList",userList);
        return "/role/index";
    }

    @RequestMapping("/queryRole")
    public String queryRole(HttpSession session, int userId, String roleName) throws ParseException {
        List<RbacRole> roleList = rbacAccountInternalFacade.queryRoleAll();
        List<RbacUserRole> userRoleList = rbacAccountInternalFacade.getRoleByUserId(userId);
        List<Integer> userRole = new ArrayList<>();
        if(userRoleList != null){
            for(RbacUserRole each:userRoleList){
                userRole.add(each.getRoleId());
            }
        }
        session.setAttribute("roles",roleList);
        session.setAttribute("userRole",userRole);
        return "/role/index";
    }

    @RequestMapping("/addUserRole")
    public String addUserRole(HttpSession session, int userId, String assignRole) throws ParseException {

        String[] roles = assignRole.split(";");
        if (roles != null && roles.length != 0){
            for (String roleName:roles){
                rbacAccountInternalFacade.addUserRole(userId, roleName);
            }
        }
        session.setAttribute("addUserRoleMsg","用户角色分配成功!");
        return "/role/index";
    }

    @RequestMapping("/addRole")
    public String addRole(HttpSession session, int roleId, String roleName) throws ParseException {
        rbacAccountInternalFacade.addRole(roleId, roleName);
        session.setAttribute("addRoleMsg","添加角色成功!");
        return "/role/index";
    }


}
