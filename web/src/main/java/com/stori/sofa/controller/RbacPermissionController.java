package com.stori.sofa.controller;

import com.account.common.dal.dao.*;
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
@RequestMapping("/permission")
public class RbacPermissionController {

    @Autowired
    private RbacAccountInternalFacade rbacAccountInternalFacade;

    @RequestMapping("/addPermissionPage")
    public String addPermissionPage(int permissionId, String permissionName, HttpSession session) throws ParseException {

        rbacAccountInternalFacade.addPermissionPage(permissionId, permissionName);
        session.setAttribute("addPermissionMsg", "新增权限页成功");
        return "permission/index";
    }

    @RequestMapping("/queryRoleAll")
    public String queryRoleAll(HttpSession session){
        List<RbacRole> roleList =  rbacAccountInternalFacade.queryRoleAll();
        session.setAttribute("roleList",roleList);
        return "/permission/index";
    }

    @RequestMapping("/addRolePermission")
    public String addRolePermission(HttpSession session, int roleId, String assignPermission) throws ParseException {

        String[] permissions = assignPermission.split(";");
        if (permissions != null && permissions.length != 0){
            for (String permissionName:permissions){
                rbacAccountInternalFacade.addRolePermission(roleId, permissionName);
            }
        }
        session.setAttribute("addRolePermissionMsg","角色权限分配成功!");
        return "permission/index";
    }

    @RequestMapping("/queryPermission")
    public String queryPermission(HttpSession session, int roleId, String permissionName) throws ParseException {
        List<RbacPermission> permissionList = rbacAccountInternalFacade.queryPermissionAll();
        List<RbacRolePermission> rolePermissionList = rbacAccountInternalFacade.getPermissionIdByRoleId(roleId);
        List<Integer> rolePermission = new ArrayList<>();
        if(rolePermissionList != null){
            for(RbacRolePermission each:rolePermissionList){
                rolePermission.add(each.getPermissionId());
            }
        }
        session.setAttribute("permissions",permissionList);
        session.setAttribute("rolePermission",rolePermission);
        return "permission/index";
    }

    @RequestMapping("/queryPermissionAll")
    public String queryPermissionAll(HttpSession session){
        List<RbacPermission> permissionList =  rbacAccountInternalFacade.queryPermissionAll();
        session.setAttribute("permissionList",permissionList);
        return "/permission/index";
    }

}
