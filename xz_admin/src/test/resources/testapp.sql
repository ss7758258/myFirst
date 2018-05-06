/*
Navicat MySQL Data Transfer

Source Server         : 10.202.95.20
Source Server Version : 50631
Source Host           : 10.202.95.20:3306
Source Database       : testapp

Target Server Type    : MYSQL
Target Server Version : 50631
File Encoding         : 65001

Date: 2017-03-02 15:12:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for access
-- ----------------------------
DROP TABLE IF EXISTS `access`;
CREATE TABLE `access` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限',
  `resource_id` int(11) DEFAULT NULL COMMENT '@1-资源ID',
  `resource_name` varchar(30) DEFAULT NULL COMMENT '@1-资源名称',
  `resource_code` varchar(30) DEFAULT NULL COMMENT '@1-资源代码',
  `status` int(11) DEFAULT NULL COMMENT '@4-状态-1=启用,0=锁定',
  `userid` int(11) DEFAULT NULL COMMENT '@9-用户ID',
  `username` varchar(100) DEFAULT NULL COMMENT '@1-用户名称',
  `addFlag` bit(1) DEFAULT NULL COMMENT '@3-增加权限-开启,锁定',
  `delFlag` bit(1) DEFAULT NULL COMMENT '@3-删除权限-开启,锁定',
  `updFlag` bit(1) DEFAULT NULL COMMENT '@3-更新权限-开启,锁定',
  `viewFlag` bit(1) DEFAULT NULL COMMENT '@3-查看权限-开启,锁定',
  `create_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-创建时间-DATETIME',
  `update_timestamp` varchar(20) DEFAULT NULL COMMENT '@0-更新时间-DATETIME',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '后台管理员',
  `username` varchar(30) DEFAULT NULL COMMENT '@1-用户名称',
  `password` varchar(30) DEFAULT NULL COMMENT '@9-用户密码',
  `status` int(11) DEFAULT NULL COMMENT '@4-状态-1=启用,0=锁定',
  `sex` bit(1) DEFAULT NULL COMMENT '@3-性别-男,女',
  `contact` varchar(100) DEFAULT NULL COMMENT '@1-联系电话',
  `title` varchar(100) DEFAULT NULL COMMENT '@5-级别-admin=管理员,others=文员',
  `create_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-创建时间-DATETIME',
  `update_timestamp` varchar(20) DEFAULT NULL COMMENT '@0-更新时间-DATETIME',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for example
-- ----------------------------
DROP TABLE IF EXISTS `example`;
CREATE TABLE `example` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '实例表',
  `username` varchar(30) DEFAULT NULL COMMENT '@1-用户名称',
  `password` varchar(30) DEFAULT NULL COMMENT '@9-用户密码',
  `age` int(11) DEFAULT NULL COMMENT '@2-年龄',
  `status` int(11) DEFAULT NULL COMMENT '@4-状态-1=启用,0=锁定',
  `sex` bit(1) DEFAULT NULL COMMENT '@3-性别-男,女',
  `brif` varchar(100) DEFAULT NULL COMMENT '@1-简介',
  `url` varchar(600) DEFAULT NULL COMMENT '@A-头像',
  `title` varchar(100) DEFAULT NULL COMMENT '@5-头衔-admin=管理员,others=文员',
  `create_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-创建时间-DATETIME',
  `update_timestamp` varchar(20) DEFAULT NULL COMMENT '@0-更新时间-DATETIME',
  `file` varchar(600) DEFAULT NULL COMMENT '@7-文件',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源',
  `resource_name` varchar(30) DEFAULT NULL COMMENT '@1-资源名称',
  `resource_code` varchar(30) DEFAULT NULL COMMENT '@1-资源代码',
  `status` int(11) DEFAULT NULL COMMENT '@4-状态-1=启用,0=锁定',
  `create_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-创建时间-DATETIME',
  `update_timestamp` varchar(20) DEFAULT NULL COMMENT '@0-更新时间-DATETIME',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=229 DEFAULT CHARSET=utf8;
