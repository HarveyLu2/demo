package com.account.common.facade;

import com.account.common.dal.dao.*;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;

/**
 * Internal Facade
 *
 * @author Harvey Lu
 * @date 2022/05/18
 **/
public interface RbacAccountInternalFacade {

    /**
     * Get user info by user name
     * @param userName user name
     * @return com.account.common.dal.dao.RbacUser
     * */
    RbacUser getUserByName(String userName);

    /**
     * Get user info by user id
     * @param userId user id
     * @return com.account.common.dal.dao.RbacUser
     * */
    RbacUser getUserById(Integer userId);

    /**
     *  Add user
     * @param user user info
     * @param password user password
     * */
    void addUser(RbacUser user, String password) throws ParseException;

    /**
     *  Update user info
     * @param rbacUser user info
     * @param password user password
     * */
    void updateUser(RbacUser rbacUser, String password) throws ParseException;

    /**
     * Query all users' info
     * @return List<com.account.common.dal.dao.RbacUser>
     * */
    List<RbacUser> queryUserAll();

    /**
     * Add a new role
     * @param roleId role id
     * @param roleName role name
     * */
    void addRole(int roleId, String roleName) throws ParseException;

    /**
     * Query all roles' info
     * @return List<com.account.common.dal.dao.RbacRole>
     * */
    List<RbacRole> queryRoleAll();

    /**
     * Get user's role by user's id
     * @param userId user id
     * @return List<com.account.common.dal.dao.RbacUserRole>
     * */
    List<RbacUserRole> getRoleByUserId(int userId);

    /**
     * Assign user's role by user id
     * @param userId user id
     * @param roleName the role name need to be assigned
     * */
    void addUserRole(int userId, String roleName) throws ParseException;

    /**
     * Add new permissions page
     * @param permissionId permission id
     * @param permissionName permission page's name
     * */
    void addPermissionPage(int permissionId, String permissionName) throws ParseException;

    /**
     * Assign role's permission by role id and permission name
     * @param roleId role id
     * @param permissionName permission name
     * */
    void addRolePermission(int roleId, String permissionName) throws ParseException;

    /**
     * Query all permissions
     * @return List<com.account.common.dal.dao.RbacPermission>
     * */
    List<RbacPermission> queryPermissionAll();

    /**
     * Get permission id by role id
     * @param roleId role id
     * @return List<com.account.common.dal.dao.RbacRolePermission>
     * */
    List<RbacRolePermission> getPermissionIdByRoleId(int roleId);

    /**
     * Get user's permissions
     * @param userName user name
     * @return java.util.HashSet<java.lang.String>
     * */
    HashSet<String> getPermissionNameByUserName(String userName);

    /**
     * Delete user from sql by user's id
     * @param userId user id
     * */
    void deleteUser(int userId);
}
