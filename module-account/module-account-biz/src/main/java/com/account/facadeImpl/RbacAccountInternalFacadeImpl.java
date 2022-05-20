package com.account.facadeImpl;

import com.account.common.dal.dao.*;
import com.account.common.dal.mapper.RbacMapper;
import com.account.common.facade.RbacAccountInternalFacade;
import com.stori.sofa.utils.EncodeByMd5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


/**
 * Internal Facade Implement
 * @author Harvey Lu
 * @date 2022/05/18
 * */
@Service("rbacAccountFacade")
public class RbacAccountInternalFacadeImpl implements RbacAccountInternalFacade {

    @Autowired
    private RbacMapper rbacMapper;

    private EncodeByMd5 encodeByMd5 = new EncodeByMd5();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public RbacUser getUserByName(String userName) {

        return rbacMapper.getUserByName(userName);
    }

    @Override
    public RbacUser getUserById(Integer userId) {

        return rbacMapper.getUserById(userId);

    }

    @Override
    public void addUser(RbacUser user, String password) throws ParseException {

        //记录当前时间
        String dateTime= dateFormat.format(new Date());
        Date date = dateFormat.parse(dateTime);
        //密码转换加盐
        String salt = encodeByMd5.salt();

        user.setSalt(salt);
        user.setPasswordMd5(encodeByMd5.encodeByMd5(password + salt).toUpperCase());
        user.setGmtCreate(date);
        user.setGmtModified(date);

        rbacMapper.insertUser(user);
    }

    @Override
    public void updateUser(RbacUser rbacUser, String password) throws ParseException {

        //记录当前时间
        String dateTime= dateFormat.format(new Date());
        Date date = dateFormat.parse(dateTime);

        RbacUser updateUser = rbacMapper.getUserById(rbacUser.getUserId());
        if(updateUser != null){
            // 密码不匹配则更新passwordMd5和salt
            String passwordSalt = password + updateUser.getSalt();
            if(!encodeByMd5.encodeByMd5(passwordSalt).toUpperCase().equals(updateUser.getPasswordMd5())){
                String salt = encodeByMd5.salt();
                updateUser.setSalt(salt);
                updateUser.setPasswordMd5(encodeByMd5.encodeByMd5(password + salt).toUpperCase());
            }
            updateUser.setUserName(rbacUser.getUserName());
            updateUser.setGmtModified(date);
            rbacMapper.updateUserById(updateUser);
        }
    }

    @Override
    public List<RbacUser> queryUserAll() {
        return rbacMapper.queryUserAll();
    }

    @Override
    public void addRole(int roleId, String roleName) throws ParseException {
        String dateTime= dateFormat.format(new Date());
        Date date = dateFormat.parse(dateTime);

        RbacRole rbacRole = new RbacRole();
        rbacRole.setRoleId(roleId);
        rbacRole.setRoleName(roleName);
        rbacRole.setGmtCreate(date);
        rbacRole.setGmtModified(date);
        rbacMapper.addRole(rbacRole);
    }

    @Override
    public List<RbacRole> queryRoleAll() {
        return rbacMapper.queryRoleAll();
    }

    @Override
    public List<RbacUserRole> getRoleByUserId(int userId) {
        return rbacMapper.getRoleByUserId(userId);
    }

    @Override
    public void addUserRole(int userId, String roleName) throws ParseException {

        String dateTime= dateFormat.format(new Date());
        Date date = dateFormat.parse(dateTime);
        Integer roleId = rbacMapper.getRoleIdByRoleName(roleName);

        RbacUserRole rbacUserRole = new RbacUserRole();
        rbacUserRole.setRoleId(roleId);
        rbacUserRole.setUserId(userId);
        rbacUserRole.setGmtCreate(date);
        rbacUserRole.setGmtModified(date);
        rbacMapper.insertUserRole(rbacUserRole);
    }

    @Override
    public void addPermissionPage(int permissionId, String permissionName) throws ParseException {
        String dateTime= dateFormat.format(new Date());
        Date date = dateFormat.parse(dateTime);

        RbacPermission rbacPermission = new RbacPermission();
        rbacPermission.setPermissionId(permissionId);
        rbacPermission.setPermissionName(permissionName);
        rbacPermission.setGmtCreate(date);
        rbacPermission.setGmtModified(date);

        rbacMapper.insertPermission(rbacPermission);

    }

    @Override
    public void addRolePermission(int roleId, String permissionName) throws ParseException {
        String dateTime= dateFormat.format(new Date());
        Date date = dateFormat.parse(dateTime);
        Integer permissionId = rbacMapper.getPermissionIdByPermissionName(permissionName);

        RbacRolePermission rbacRolePermission = new RbacRolePermission();
        rbacRolePermission.setRoleId(roleId);
        rbacRolePermission.setPermissionId(permissionId);
        rbacRolePermission.setGmtCreate(date);
        rbacRolePermission.setGmtModified(date);
        rbacMapper.insertRolePermission(rbacRolePermission);
    }

    @Override
    public List<RbacPermission> queryPermissionAll() {
        return rbacMapper.queryPermissionAll();
    }

    @Override
    public List<RbacRolePermission> getPermissionIdByRoleId(int roleId) {
        return rbacMapper.getPermissionIdByRoleId(roleId);
    }

    @Override
    public HashSet<String> getPermissionNameByUserName(String userName) {
        int userId = rbacMapper.getUserByName(userName).getUserId();
        return rbacMapper.getPermissionNameByUserId(userId);
    }

    @Override
    public void deleteUser(int userId) {
        rbacMapper.deleteUserByUserId(userId);
        rbacMapper.deleteUserRoleByUserId(userId);
    }


}
