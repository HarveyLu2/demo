/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : corebanking

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 02/11/2022 18:01:59
*/


-- ----------------------------
-- Table structure for bo_security_permission_info
-- ----------------------------
CREATE TABLE IF NOT EXISTS `bo_security_permission_info` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  `create_time_local` datetime NOT NULL,
  `create_time_utc` datetime NOT NULL,
  `update_time_local` datetime NOT NULL,
  `update_time_utc` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


-- ----------------------------
-- Table structure for bo_security_role_info
-- ----------------------------
CREATE TABLE IF NOT EXISTS `bo_security_role_info` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  `create_time_local` datetime NOT NULL,
  `create_time_utc` datetime NOT NULL,
  `update_time_local` datetime NOT NULL,
  `update_time_utc` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;


-- ----------------------------
-- Table structure for bo_security_role_permission
-- ----------------------------
CREATE TABLE IF NOT EXISTS `bo_security_role_permission` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  `permission` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL DEFAULT 'DEFAULT_ROLE',
  `create_time_local` datetime NOT NULL,
  `create_time_utc` datetime NOT NULL,
  `update_time_local` datetime NOT NULL,
  `update_time_utc` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


-- ----------------------------
-- Table structure for bo_security_user_info
-- ----------------------------
CREATE TABLE IF NOT EXISTS `bo_security_user_info` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `create_time_local` datetime NOT NULL,
  `create_time_utc` datetime NOT NULL,
  `update_time_local` datetime NOT NULL,
  `update_time_utc` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of bo_security_user_info
-- ----------------------------
BEGIN;
INSERT INTO `bo_security_user_info` (`id`, `user_name`, `user_password`, `create_time_local`, `create_time_utc`, `update_time_local`, `update_time_utc`) VALUES (1, 'harvey', '12345', '2022-11-02 11:42:10', '2022-11-02 11:42:13', '2022-11-02 11:42:15', '2022-11-02 11:42:17');
COMMIT;

-- ----------------------------
-- Table structure for bo_security_user_role
-- ----------------------------
CREATE TABLE IF NOT EXISTS `bo_security_user_role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL,
  `role` varchar(32) NOT NULL DEFAULT 'DEFAULT_ROLE',
  `create_time_local` datetime NOT NULL,
  `create_time_utc` datetime NOT NULL,
  `update_time_local` datetime NOT NULL,
  `update_time_utc` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

