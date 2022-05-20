package com.account.common.dal.mapper;

import com.account.common.dal.dao.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

/**
 * @author Harvey Lu
 */
@Component
@Mapper
public interface RbacMapper {

    /**
     * Get user info by user name from user_table
     * @param userName user name
     * @return com.account.common.dal.dao.RbacUser
     * */
    RbacUser getUserByName(String userName);

    /**
     *  Insert a new user to user_table
     * @param user com.account.common.dal.dao.RbacUser
     * */
    void insertUser(RbacUser user);

    /**
     * Get user info by user's id from user_table
     * @param userId user id
     * @return com.account.common.dal.dao.RbacUser
     * */
    RbacUser getUserById(Integer userId);

    /**
     *  Update user info in user_table
     * @param user com.account.common.dal.dao.RbacUser
     * */
    void updateUserById(RbacUser user);

    /**
     * Query all users' info from user_table
     * @return List<com.account.common.dal.dao.RbacUser>
     * */
    List<RbacUser> queryUserAll();

    /**
     * Add a new role into role_table
     * @param role com.account.common.dal.dao.RbacRole
     * */
    void addRole(RbacRole role);

    /**
     * Query all roles' info from role_table
     * @return List<com.account.common.dal.dao.RbacRole>
     * */
    List<RbacRole> queryRoleAll();

    /**
     * Get role info by user's id from user_role_table
     * @param userId user id
     * @return List<com.account.common.dal.dao.RbacUserRole>
     * */
    List<RbacUserRole> getRoleByUserId(Integer userId);

    /**
     * Insert user id and role id into user_role_table
     * @param rbacUserRole com.account.common.dal.dao.RbacUserRole
     * */
    void insertUserRole(RbacUserRole rbacUserRole);

    /**
     * Get role's id by role'name from role_table
     * @param roleName role name
     * @return Integer role id
     * */
    Integer getRoleIdByRoleName(String roleName);

    /**
     * Insert a new permission into permission_table
     * @param rbacPermission com.account.common.dal.dao.RbacPermission
     * */
    void insertPermission(RbacPermission rbacPermission);

    /**
     * Get permission's id by permission's name from permission_table
     * @param permissionName permission name
     * @return Integer permission id
     * */
    Integer getPermissionIdByPermissionName(String permissionName);

    /**
     * Insert role's permission into role_permission_table
     * @param rbacRolePermission com.account.common.dal.dao.RbacRolePermission
     * */
    void insertRolePermission(RbacRolePermission rbacRolePermission);

    /**
     * Query all permissions from permission_table
     * @return List<com.account.common.dal.dao.RbacPermission>
     **/
    List<RbacPermission> queryPermissionAll();

    /**
     * Get permission id by role id from role_permission_table
     * @param roleId role id
     * @return List<com.account.common.dal.dao.RbacRolePermission>
     * */
    List<RbacRolePermission> getPermissionIdByRoleId(int roleId);

    /**
     * Get permission name by user id from (user_role_table,role_permission_table, permission_table)
     * @param userId user id
     * @return java.util.HashSet<java.lang.String> permission name
     * */
    HashSet<String> getPermissionNameByUserId(int userId);

    /**
     * Delete user by user id from user_table
     * @param userId user id
     * */
    void deleteUserByUserId(int userId);

    /**
     * Delete user's role by user id from user_role_table
     * @param userId user id
     * */
    void deleteUserRoleByUserId(int userId);
}
