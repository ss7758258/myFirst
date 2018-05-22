/*
Navicat MySQL Data Transfer

Source Server         : 193.112.130.148
Source Server Version : 50556
Source Host           : 193.112.130.148:3306
Source Database       : constellation

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-05-22 19:04:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for access
-- ----------------------------
DROP TABLE IF EXISTS `access`;
CREATE TABLE `access` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '权限',
  `resource_id` int(11) DEFAULT NULL COMMENT '@1-资源ID',
  `resource_name` varchar(30) DEFAULT NULL COMMENT '@1-资源名称',
  `resource_code` varchar(30) DEFAULT NULL COMMENT '@1-资源代码',
  `status` int(11) DEFAULT NULL COMMENT '@4-状态-1=启用,0=锁定',
  `userid` int(11) DEFAULT NULL COMMENT '@9-用户ID',
  `username` varchar(100) DEFAULT NULL COMMENT '@1-用户名称',
  `add_flag` bit(1) DEFAULT NULL COMMENT '@3-增加权限-开启,锁定',
  `del_flag` bit(1) DEFAULT NULL COMMENT '@3-删除权限-开启,锁定',
  `upd_flag` bit(1) DEFAULT NULL COMMENT '@3-更新权限-开启,锁定',
  `view_flag` bit(1) DEFAULT NULL COMMENT '@3-查看权限-开启,锁定',
  `create_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-创建时间-DATETIME',
  `update_timestamp` varchar(20) DEFAULT NULL COMMENT '@0-更新时间-DATETIME',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=378 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of access
-- ----------------------------
INSERT INTO `access` VALUES ('365', '601', 'access', '权限', '1', '48', 'admin', '', '', '', '', '2018-05-13 16:14:24', '2018-05-13 16:14:24');
INSERT INTO `access` VALUES ('366', '602', 'admin', '后台管理员', '1', '48', 'admin', '', '', '', '', '2018-05-13 16:14:24', '2018-05-13 16:14:24');
INSERT INTO `access` VALUES ('367', '603', 'example', '实例表', '1', '48', 'admin', '', '', '', '', '2018-05-13 16:14:25', '2018-05-13 16:14:25');
INSERT INTO `access` VALUES ('368', '604', 'resource', '资源', '1', '48', 'admin', '', '', '', '', '2018-05-13 16:14:25', '2018-05-13 16:14:25');
INSERT INTO `access` VALUES ('369', '605', 'tcConstellation', '星座表', '1', '48', 'admin', '', '', '', '', '2018-05-13 16:14:26', '2018-05-13 16:14:26');
INSERT INTO `access` VALUES ('370', '606', 'tcQianYanUrl', '', '1', '48', 'admin', '', '', '', '', '2018-05-13 16:14:26', '2018-05-13 16:14:26');
INSERT INTO `access` VALUES ('371', '607', 'tiAdmin', '后台管理员', '1', '48', 'admin', '', '', '', '', '2018-05-13 16:14:26', '2018-05-13 16:14:26');
INSERT INTO `access` VALUES ('372', '608', 'tiLucky', '运势', '1', '48', 'admin', '', '', '', '', '2018-05-13 16:14:27', '2018-05-13 16:14:27');
INSERT INTO `access` VALUES ('373', '609', 'tiQianLib', '一签库', '1', '48', 'admin', '', '', '', '', '2018-05-13 16:14:27', '2018-05-13 16:14:27');
INSERT INTO `access` VALUES ('374', '610', 'tiQianList', '一签表', '1', '48', 'admin', '', '', '', '', '2018-05-13 16:14:28', '2018-05-13 16:14:28');
INSERT INTO `access` VALUES ('375', '611', 'tiUserQianList', '一言', '1', '48', 'admin', '', '', '', '', '2018-05-13 16:14:28', '2018-05-13 16:14:28');
INSERT INTO `access` VALUES ('376', '612', 'tiYanList', '一言', '1', '48', 'admin', '', '', '', '', '2018-05-13 16:14:29', '2018-05-13 16:14:29');
INSERT INTO `access` VALUES ('377', '613', 'weixinUser', '微信用户id', '1', '48', 'admin', '', '', '', '', '2018-05-13 16:14:29', '2018-05-13 16:14:29');

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '后台管理员',
  `username` varchar(30) DEFAULT NULL COMMENT '@1-用户名称',
  `password` varchar(32) DEFAULT NULL COMMENT '@9-用户密码',
  `status` int(11) DEFAULT NULL COMMENT '@4-状态-1=启用,0=锁定',
  `sex` bit(1) DEFAULT NULL COMMENT '@3-性别-男,女',
  `contact` varchar(100) DEFAULT NULL COMMENT '@1-联系电话',
  `title` varchar(100) DEFAULT NULL COMMENT '@5-级别-admin=管理员,others=文员',
  `create_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-创建时间-DATETIME',
  `update_timestamp` varchar(20) DEFAULT NULL COMMENT '@0-更新时间-DATETIME',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('48', 'admin', 'admin', '1', '', null, '系统管理员', '2018-05-13 16:14:23', '2018-05-13 16:14:23');

-- ----------------------------
-- Table structure for example
-- ----------------------------
DROP TABLE IF EXISTS `example`;
CREATE TABLE `example` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '实例表',
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
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of example
-- ----------------------------

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '资源',
  `resource_name` varchar(30) DEFAULT NULL COMMENT '@1-资源名称',
  `resource_code` varchar(30) DEFAULT NULL COMMENT '@1-资源代码',
  `status` int(11) DEFAULT NULL COMMENT '@4-状态-1=启用,0=锁定',
  `create_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-创建时间-DATETIME',
  `update_timestamp` varchar(20) DEFAULT NULL COMMENT '@0-更新时间-DATETIME',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=614 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('601', 'access', '权限', '1', '2018-05-13 16:14:23', '2018-05-13 16:14:23');
INSERT INTO `resource` VALUES ('602', 'admin', '后台管理员', '1', '2018-05-13 16:14:24', '2018-05-13 16:14:24');
INSERT INTO `resource` VALUES ('603', 'example', '实例表', '1', '2018-05-13 16:14:24', '2018-05-13 16:14:24');
INSERT INTO `resource` VALUES ('604', 'resource', '资源', '1', '2018-05-13 16:14:25', '2018-05-13 16:14:25');
INSERT INTO `resource` VALUES ('605', 'tcConstellation', '星座表', '1', '2018-05-13 16:14:25', '2018-05-13 16:14:25');
INSERT INTO `resource` VALUES ('606', 'tcQianYanUrl', '', '1', '2018-05-13 16:14:26', '2018-05-13 16:14:26');
INSERT INTO `resource` VALUES ('607', 'tiAdmin', '后台管理员', '1', '2018-05-13 16:14:26', '2018-05-13 16:14:26');
INSERT INTO `resource` VALUES ('608', 'tiLucky', '运势', '1', '2018-05-13 16:14:26', '2018-05-13 16:14:26');
INSERT INTO `resource` VALUES ('609', 'tiQianLib', '一签库', '1', '2018-05-13 16:14:27', '2018-05-13 16:14:27');
INSERT INTO `resource` VALUES ('610', 'tiQianList', '一签表', '1', '2018-05-13 16:14:27', '2018-05-13 16:14:27');
INSERT INTO `resource` VALUES ('611', 'tiUserQianList', '一言', '1', '2018-05-13 16:14:28', '2018-05-13 16:14:28');
INSERT INTO `resource` VALUES ('612', 'tiYanList', '一言', '1', '2018-05-13 16:14:29', '2018-05-13 16:14:29');
INSERT INTO `resource` VALUES ('613', 'weixinUser', '微信用户id', '1', '2018-05-13 16:14:29', '2018-05-13 16:14:29');

-- ----------------------------
-- Table structure for tc_constellation
-- ----------------------------
DROP TABLE IF EXISTS `tc_constellation`;
CREATE TABLE `tc_constellation` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '星座表',
  `constellation_name` varchar(10) DEFAULT NULL COMMENT '@1-星座',
  `start_date` varchar(5) DEFAULT NULL COMMENT '@1-月份开始日期',
  `end_date` varchar(5) DEFAULT NULL COMMENT '@1-结束日期',
  `picture_url` varchar(100) DEFAULT NULL COMMENT '@7-背景图片',
  `remark` varchar(50) DEFAULT NULL COMMENT '@1-备注',
  `create_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-创建时间-DATETIME',
  `update_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-更新时间-DATETIME',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='星座';

-- ----------------------------
-- Records of tc_constellation
-- ----------------------------
INSERT INTO `tc_constellation` VALUES ('1', '白羊座', '3月21日', '4月19日', '', '', '2018-5-17 15:16:44', '2018-5-17 15:16:46');
INSERT INTO `tc_constellation` VALUES ('2', '金牛座', '4月20日', '5月20日', '', '', '2018-5-17 15:16:40', '2018-5-17 15:16:42');
INSERT INTO `tc_constellation` VALUES ('3', '双子座', '5月21日', '6月21日', '', '', '2018-5-17 15:16:34', '2018-5-17 15:16:36');
INSERT INTO `tc_constellation` VALUES ('4', '巨蟹座', '6月22日', '7月22日', '', '', '2018-5-17 15:16:30', '2018-5-17 15:16:32');
INSERT INTO `tc_constellation` VALUES ('5', '狮子座', '7月23日', '8月22日', '', '', '2018-5-17 15:16:25', '2018-5-17 15:16:27');
INSERT INTO `tc_constellation` VALUES ('6', '处女座', '8月23日', '9月22日', '', '', '2018-5-17 15:16:19', '2018-5-17 15:16:22');
INSERT INTO `tc_constellation` VALUES ('7', '天秤座', '9-23', '10-22', '', '', '2018-5-17 15:15:57', '2018-5-17 15:15:59');
INSERT INTO `tc_constellation` VALUES ('8', '天蝎座', '10-23', '11-22', '', '', '2018-5-17 15:15:52', '2018-5-17 15:15:55');
INSERT INTO `tc_constellation` VALUES ('9', '射手座', '11-23', '12-21', '', '', '2018-5-17 15:15:48', '2018-5-17 15:15:50');
INSERT INTO `tc_constellation` VALUES ('10', '魔羯座', '12-22', '01-19', '', '', '2018-5-17 15:15:43', '2018-5-17 15:15:46');
INSERT INTO `tc_constellation` VALUES ('11', '水瓶座', '1月20日', '2月19日', '', '', '2018-5-17 15:15:38', '2018-5-17 15:15:40');
INSERT INTO `tc_constellation` VALUES ('12', '双鱼座', '02-19', '03-20', '', '', '2018-5-17 15:14:21', '2018-5-17 15:14:23');

-- ----------------------------
-- Table structure for tc_qian_yan_url
-- ----------------------------
DROP TABLE IF EXISTS `tc_qian_yan_url`;
CREATE TABLE `tc_qian_yan_url` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `yan_url` varchar(100) DEFAULT NULL COMMENT '@1-一言url',
  `qian_url` varchar(100) DEFAULT NULL COMMENT '@1-一签url',
  `create_timestamp` varchar(32) DEFAULT NULL COMMENT '@9-创建时间-DATETIME',
  `update_timestamp` varchar(32) DEFAULT NULL COMMENT '@9-更新时间-DATETIME',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tc_qian_yan_url
-- ----------------------------

-- ----------------------------
-- Table structure for ti_admin
-- ----------------------------
DROP TABLE IF EXISTS `ti_admin`;
CREATE TABLE `ti_admin` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '后台管理员',
  `username` varchar(30) DEFAULT NULL COMMENT '@1-用户名称',
  `password` varchar(30) DEFAULT NULL COMMENT '@9-用户密码',
  `status` int(3) DEFAULT NULL COMMENT '@4-状态-1=启用,0=锁定,-1=删除',
  `create_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-创建时间-DATETIME',
  `update_timestamp` varchar(20) DEFAULT NULL COMMENT '@0-更新时间-DATETIME',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of ti_admin
-- ----------------------------
INSERT INTO `ti_admin` VALUES ('20', 'dev', null, null, '2018-05-13 19:06:46', '2018-05-13 20:22:45');
INSERT INTO `ti_admin` VALUES ('21', 'admin', '123456', '1', '2018-05-14 09:20:13', '2018-05-14 09:20:13');

-- ----------------------------
-- Table structure for ti_lucky
-- ----------------------------
DROP TABLE IF EXISTS `ti_lucky`;
CREATE TABLE `ti_lucky` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '运势',
  `constellation_id` bigint(10) DEFAULT NULL COMMENT '@2-星座id',
  `lucky_type1` varchar(100) DEFAULT NULL COMMENT '@1-首页运势类型1',
  `lucky_type2` varchar(50) DEFAULT NULL COMMENT '@1-首页运势类型2',
  `lucky_type3` varchar(50) DEFAULT NULL COMMENT '@1-首页运势类型3',
  `lucky_type4` varchar(50) DEFAULT NULL COMMENT '@1-首页运势类型4',
  `lucky_score1` int(11) DEFAULT NULL COMMENT '@2-运势评分1（百分比）',
  `lucky_score2` int(11) DEFAULT NULL COMMENT '@2-运势评分2（百分比）',
  `lucky_score3` int(11) DEFAULT NULL COMMENT '@2-运势评分3（百分比）',
  `lucky_score4` int(11) DEFAULT NULL COMMENT '@2-运势评分4（百分比）',
  `remind_today` varchar(50) DEFAULT NULL COMMENT '@1-今日星座提醒',
  `lucky_type_more1` varchar(50) DEFAULT NULL COMMENT '@1-更多运势类型1',
  `lucky_type_more2` varchar(50) DEFAULT NULL COMMENT '@1-更多运势类型2',
  `lucky_type_more3` varchar(50) DEFAULT NULL COMMENT '@1-更多运势类型3',
  `lucky_type_more4` varchar(50) DEFAULT NULL COMMENT '@1-更多运势类型4',
  `lucky_score_more1` int(11) DEFAULT NULL COMMENT '@2-运势评分1（星值）',
  `lucky_score_more2` int(11) DEFAULT NULL COMMENT '@2-运势评分2（星值）',
  `lucky_score_more3` int(11) DEFAULT NULL COMMENT '@2-运势评分3（星值）',
  `lucky_score_more4` int(11) DEFAULT NULL COMMENT '@2-运势评分4（星值）',
  `lucky_words1` varchar(50) DEFAULT NULL COMMENT '@1-运势寄语1',
  `lucky_words2` varchar(50) DEFAULT NULL COMMENT '@1-运势寄语2',
  `lucky_words3` varchar(50) DEFAULT NULL COMMENT '@1-运势寄语3',
  `lucky_words4` varchar(50) DEFAULT NULL COMMENT '@1-运势寄语4',
  `to_do` varchar(50) DEFAULT NULL COMMENT '@1-今日去做',
  `not_do` varchar(50) DEFAULT NULL COMMENT '@1-今日不做',
  `publish_name` varchar(50) DEFAULT NULL COMMENT '@1-发布人员',
  `publish_time` varchar(20) DEFAULT NULL COMMENT '@1-预计发布时间',
  `status` int(11) DEFAULT NULL COMMENT '@4-状态-1=启用,0=草稿',
  `lucky_date` varchar(20) DEFAULT NULL COMMENT '@1-当日日期',
  `create_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-创建时间-DATETIME',
  `update_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-更新时间-DATETIME',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='运势表';

-- ----------------------------
-- Records of ti_lucky
-- ----------------------------
INSERT INTO `ti_lucky` VALUES ('13', '5', '1', '1', '1', '1', '1', '12', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', null, '2001-02-09', '1', '2', '2018-05-13 15:31:16', '2018-05-13 15:31:16');
INSERT INTO `ti_lucky` VALUES ('14', '5', '1', '1', '1', '1', '1', '12', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', null, '2001-02-16', '1', null, '2018-05-13 15:35:28', '2018-05-13 15:35:28');
INSERT INTO `ti_lucky` VALUES ('17', '8', '2', '3', '3', '3', '3', '3', '3', '3', 'dfd', '1', 'df', 'fd', 'df', '1', '2', '1', '1', 'dsaf', 'dfs', 'da', 'df', 'afdasgf', 'adfasff', 'XVxZ', '2018-05-15', '1', null, '2018-05-13 18:35:06', '2018-05-13 18:35:06');
INSERT INTO `ti_lucky` VALUES ('18', '12', '2', '23', 'df', 'dd', '2', '3', '3', '2', 'dafs', '23', 'df', 'adf', 'afd', '1', '1', '2', '34', 'adf', 'dsaf', 'dfa', 'asdf', 'adfsf', 'adfas', 'gfds', '2018-05-08', '1', null, '2018-05-13 18:36:36', '2018-05-14 18:25:30');
INSERT INTO `ti_lucky` VALUES ('19', '12', '2', '23', 'df', 'dd', '2', '3', '3', '2', 'dafs', '23', 'df', 'adf', 'afd', '1', '1', '2', '34', 'adf', 'dsaf', 'dfa', 'asdf', 'adfsf', 'adfas', 'qa', '2018-05-08', '1', null, '2018-05-13 18:37:36', '2018-05-14 18:25:30');
INSERT INTO `ti_lucky` VALUES ('20', '6', '2', '1', '2', '2', '2', '2', '2', '2', 'fsdf', '2', '2', '2', '2', '2', '2', '2', '2', '12', '12', '2', '12', '12', 'eqwf', 'aaa', '2018-05-08', '1', null, '2018-05-13 19:09:54', '2018-05-13 19:09:54');
INSERT INTO `ti_lucky` VALUES ('21', '12', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '1', '1', '2', '2', '2', '2', '22', '2', '2', '2018-05-07', '1', null, '2018-05-13 19:14:52', '2018-05-13 19:14:52');
INSERT INTO `ti_lucky` VALUES ('22', '12', '1', '2', '2', '2', '12', '-3', '-1', '1', 'asdf', 'df', 'af', 'af', 'fda', '-1', '-1', '-1', '1', 'ds', 'd', 'd', 'f', 'dfas', 'afads', 'sdf', '2018-05-07', '1', null, '2018-05-13 19:28:08', '2018-05-13 19:28:08');
INSERT INTO `ti_lucky` VALUES ('23', '12', '2', '23', 'df', 'dd', '2', '3', '3', '2', 'dafs', '23', 'df', 'adf', 'afd', '1', '1', '2', '34', 'adf', 'dsaf', 'dfa', 'asdf', 'adfsf', 'adfas', '123', '2018-05-08', '1', null, '2018-05-13 19:28:46', '2018-05-14 18:25:30');
INSERT INTO `ti_lucky` VALUES ('24', '12', '2', '2', '2', '23', '2', '3', '3', '3', '3', '34', '34', '34', '34', '3', '6', '6', '5', '3', '56', '56', '36', '56', '356', 'df', '2018-05-08', '1', null, '2018-05-13 20:12:57', '2018-05-14 18:24:30');
INSERT INTO `ti_lucky` VALUES ('25', '12', '2', '2', '2', '23', '2', '3', '3', '3', '3', '34', '34', '34', '34', '3', '6', '6', '5', '3', '56', '56', '36', '56', 'sgfdg', 'df', '2018-05-08', '1', null, '2018-05-13 20:13:34', '2018-05-14 18:24:30');
INSERT INTO `ti_lucky` VALUES ('26', '12', '2', '2', '2', '23', '2', '3', '3', '3', 'sdfgsfgfs', '34', '34', '34', '34', '3', '6', '6', '5', '3', '56', '56', '36', '56', 'sgfdg', 'df', '2018-05-08', '1', null, '2018-05-13 20:13:49', '2018-05-14 18:24:30');
INSERT INTO `ti_lucky` VALUES ('27', '12', '2', '2', '2', '23', '2', '3', '3', '3', '3', '34', '34', '34', '34', '3', '6', '6', '5', '3', '56', '56', '36', '5656', 'sgfdg', 'df', '2018-05-08', '1', null, '2018-05-13 20:14:29', '2018-05-14 18:24:30');
INSERT INTO `ti_lucky` VALUES ('28', '12', '2', '2', '2', '23', '2', '3', '3', '3', '322', '34', '34', '34', '34', '3', '6', '6', '5', '3', '56', '56', '36', '5656', 'sgfdg', 'df', '2018-05-08', '1', null, '2018-05-13 20:15:08', '2018-05-14 18:24:30');
INSERT INTO `ti_lucky` VALUES ('29', '12', '2', '2', '2', '23', '2', '3', '3', '3', '3', '34', '34', '34', '34', '3', '6', '6', '5', '3', '56', '56', '36', '56562', 'sgfdg', 'df', '2018-05-08', '1', null, '2018-05-13 20:15:26', '2018-05-14 18:24:30');
INSERT INTO `ti_lucky` VALUES ('30', '12', '2', '2', '2', '23', '2', '3', '3', '3', '3', '34', '34', '34', '34', '3', '6', '6', '5', '3', '56', '56', '36', '56562', 'sgfdg', 'df2', '2018-05-08', '1', null, '2018-05-13 20:15:46', '2018-05-14 18:24:30');
INSERT INTO `ti_lucky` VALUES ('31', '11', '1', '6', '67', '6', '-1', '-1', '-1', '-1', 'adsf', 'df', 'das', 'df', 'ads', '1', '0', '0', '1', 'ads', 'adf', 'dsaf', 'dasf', 'asdf', 'asdf', 'qwe', '2018-05-01', '1', null, '2018-05-13 20:23:43', '2018-05-14 18:24:30');
INSERT INTO `ti_lucky` VALUES ('32', '12', 'a', 's', 's', 'd', '2', '4', '4', '4', 'dfhg', 'df', 'dfg', 'cfg', 'cfg', '0', '1', '1', '1', 'fhg', 'fgh', 'fgh', 'cvgh', 'cghb', 'ghgh', 'pengjinchegn', '2018-05-07', '1', null, '2018-05-13 20:27:29', '2018-05-14 18:24:30');
INSERT INTO `ti_lucky` VALUES ('33', '11', 'hgg', 'gh', 'jgh', 'gh', '1', '1', '2', '1', 'chg', 'fgh', 'gh', 'gh', 'fg', '6', '5', '5', '5', '5', '5', 'f', 'vg', 'ghfg23', 'gh23', 'peng ruinan', '2018-05-08', '1', null, '2018-05-13 20:28:41', '2018-05-14 18:24:30');
INSERT INTO `ti_lucky` VALUES ('35', '12', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2018-05-17 20:18:16', '0', null, '2018-05-15 15:22:15', '2018-05-15 15:22:15');
INSERT INTO `ti_lucky` VALUES ('37', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', 'admin', '2018-05-19 11:26:58', '0', null, '2018-05-19 11:26:07', '2018-05-19 11:26:07');
INSERT INTO `ti_lucky` VALUES ('40', '2', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', 'admin', '2018-05-20 11:56:22', '0', null, '2018-05-20 11:55:30', '2018-05-20 12:03:44');
INSERT INTO `ti_lucky` VALUES ('41', '4', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2018-05-20 15:22:46', '0', null, '2018-05-20 15:23:07', '2018-05-21 18:29:03');
INSERT INTO `ti_lucky` VALUES ('42', '2', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2018-05-21 18:27:00', '0', null, '2018-05-21 18:27:24', '2018-05-21 18:27:24');
INSERT INTO `ti_lucky` VALUES ('43', '4', '1', '1', '1', '1', '2', '2', '2', '2', '2', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', 'q', '2018-05-21 18:30:00', '0', null, '2018-05-21 18:30:23', '2018-05-21 18:30:23');
INSERT INTO `ti_lucky` VALUES ('44', '2', '1', '1', '1', '1', '2', '2', '2', '2', '2', '1', '1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', 's', '2018-05-21 18:31:00', '0', null, '2018-05-21 18:31:23', '2018-05-21 18:34:09');

-- ----------------------------
-- Table structure for ti_qian_lib
-- ----------------------------
DROP TABLE IF EXISTS `ti_qian_lib`;
CREATE TABLE `ti_qian_lib` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '一签库',
  `pic` varchar(100) DEFAULT NULL COMMENT '@7-图片',
  `name` varchar(20) DEFAULT NULL COMMENT '@1-签库名',
  `status` int(11) DEFAULT NULL COMMENT '@4-状态-1=启用,0=暂停',
  `publish_time` varchar(20) DEFAULT NULL COMMENT '@1-发布时间',
  `create_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-创建时间-DATETIME',
  `update_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-更新时间-DATETIME',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='一签库';

-- ----------------------------
-- Records of ti_qian_lib
-- ----------------------------
INSERT INTO `ti_qian_lib` VALUES ('2', '/568d2b26dd3242a8931d2ec0db1d48d3_7baa33f70d3a4447b8443aefeb990cfc.jpg', '库1', '1', '2018-05-14', '2018-05-12 23:42:26', '2018-05-14 14:34:17');
INSERT INTO `ti_qian_lib` VALUES ('9', '/707e9209b4ea415499228057a214a988_4101c4b89ade429f88180020bcbe0943.jpg', '123', '1', '2018-05-20 17:28:38', '2018-05-20 17:23:49', '2018-05-20 17:52:35');

-- ----------------------------
-- Table structure for ti_qian_list
-- ----------------------------
DROP TABLE IF EXISTS `ti_qian_list`;
CREATE TABLE `ti_qian_list` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '一签表',
  `qian_lib_id` bigint(11) DEFAULT NULL COMMENT '@1-签库ID',
  `name` varchar(20) DEFAULT NULL COMMENT '@1-签名',
  `content` varchar(255) DEFAULT NULL COMMENT '@1-内容',
  `create_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-创建时间-DATETIME',
  `update_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-更新时间-DATETIME',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 COMMENT='一签库   注解';

-- ----------------------------
-- Records of ti_qian_list
-- ----------------------------
INSERT INTO `ti_qian_list` VALUES ('1', '2', '1231', '123', '2018-05-12 18:34:37', '2018-05-12 18:34:37');
INSERT INTO `ti_qian_list` VALUES ('3', '2', '123', '阿斯顿', '2018-05-13 10:27:12', '2018-05-13 10:27:12');
INSERT INTO `ti_qian_list` VALUES ('4', '2', '321', '阿斯顿', '2018-05-13 10:27:19', '2018-05-13 10:27:19');
INSERT INTO `ti_qian_list` VALUES ('5', '2', '54231', '阿斯顿', '2018-05-13 10:27:24', '2018-05-13 10:27:24');
INSERT INTO `ti_qian_list` VALUES ('6', '2', '54231', '阿斯顿', '2018-05-13 10:27:26', '2018-05-13 10:27:26');
INSERT INTO `ti_qian_list` VALUES ('7', '2', '54231', '阿斯顿', '2018-05-13 10:27:27', '2018-05-13 10:27:27');
INSERT INTO `ti_qian_list` VALUES ('8', '2', '54231', '阿斯顿', '2018-05-13 10:27:28', '2018-05-13 10:27:28');
INSERT INTO `ti_qian_list` VALUES ('9', '2', '54231', '阿斯顿', '2018-05-13 10:27:29', '2018-05-13 10:27:29');
INSERT INTO `ti_qian_list` VALUES ('10', '2', '54231', '阿斯顿', '2018-05-13 10:27:30', '2018-05-13 10:27:30');
INSERT INTO `ti_qian_list` VALUES ('11', '2', '54231', '阿斯顿', '2018-05-13 10:27:31', '2018-05-13 10:27:31');
INSERT INTO `ti_qian_list` VALUES ('12', '5', '54231', '123', '2018-05-13 10:27:33', '2018-05-13 19:38:15');
INSERT INTO `ti_qian_list` VALUES ('13', '5', '123', '123', '2018-05-13 10:27:34', '2018-05-13 19:38:17');
INSERT INTO `ti_qian_list` VALUES ('21', '2', '22', '22', '2018-05-13 18:02:38', '2018-05-13 18:02:38');
INSERT INTO `ti_qian_list` VALUES ('22', '2', '33', '33', '2018-05-13 18:03:09', '2018-05-13 18:03:09');
INSERT INTO `ti_qian_list` VALUES ('23', '2', '33', '33', '2018-05-13 18:03:09', '2018-05-13 18:03:09');
INSERT INTO `ti_qian_list` VALUES ('24', '2', '44', '44', '2018-05-13 18:03:09', '2018-05-13 18:03:09');
INSERT INTO `ti_qian_list` VALUES ('25', '2', '33', '33', '2018-05-13 18:05:43', '2018-05-13 18:05:43');
INSERT INTO `ti_qian_list` VALUES ('26', '2', '11', '11', '2018-05-13 18:05:43', '2018-05-13 18:05:43');
INSERT INTO `ti_qian_list` VALUES ('27', '2', '22', '22', '2018-05-13 18:05:43', '2018-05-13 18:05:43');
INSERT INTO `ti_qian_list` VALUES ('28', '2', '22', '22', '2018-05-13 18:07:25', '2018-05-13 18:07:25');
INSERT INTO `ti_qian_list` VALUES ('29', '2', '44', '44', '2018-05-13 18:07:25', '2018-05-13 18:07:25');
INSERT INTO `ti_qian_list` VALUES ('30', '2', '3312', '331', '2018-05-13 18:07:25', '2018-05-13 19:22:18');
INSERT INTO `ti_qian_list` VALUES ('31', null, '123', null, '2018-05-13 18:52:56', '2018-05-13 18:52:56');
INSERT INTO `ti_qian_list` VALUES ('32', null, '1234', '1234', '2018-05-13 18:53:11', '2018-05-13 18:53:11');
INSERT INTO `ti_qian_list` VALUES ('33', null, '123', null, '2018-05-13 18:55:15', '2018-05-13 18:55:15');
INSERT INTO `ti_qian_list` VALUES ('34', '6', '123', '132', '2018-05-13 18:55:43', '2018-05-13 18:55:43');
INSERT INTO `ti_qian_list` VALUES ('35', null, '123', null, '2018-05-13 18:56:09', '2018-05-13 18:56:09');
INSERT INTO `ti_qian_list` VALUES ('36', '6', 'a', 'a', '2018-05-13 18:59:44', '2018-05-13 18:59:44');
INSERT INTO `ti_qian_list` VALUES ('37', '6', 'a', 'a', '2018-05-13 19:01:02', '2018-05-13 19:01:02');
INSERT INTO `ti_qian_list` VALUES ('38', '6', 'a', 'a', '2018-05-13 19:01:53', '2018-05-13 19:01:53');
INSERT INTO `ti_qian_list` VALUES ('39', '6', 'a1', 'a1', '2018-05-13 19:02:53', '2018-05-13 19:05:23');
INSERT INTO `ti_qian_list` VALUES ('41', '6', '22', '22', '2018-05-13 19:15:27', '2018-05-13 19:15:34');
INSERT INTO `ti_qian_list` VALUES ('43', '5', '11', '22', '2018-05-13 19:43:40', '2018-05-13 19:43:40');
INSERT INTO `ti_qian_list` VALUES ('44', '5', '22', '33', '2018-05-13 19:44:26', '2018-05-13 19:44:26');
INSERT INTO `ti_qian_list` VALUES ('45', '5', '23', '43', '2018-05-13 19:44:56', '2018-05-13 19:44:56');
INSERT INTO `ti_qian_list` VALUES ('46', '2', '11', '11', '2018-05-13 19:45:09', '2018-05-13 19:45:09');
INSERT INTO `ti_qian_list` VALUES ('47', '2', '22', '22', '2018-05-13 19:45:47', '2018-05-13 19:45:47');
INSERT INTO `ti_qian_list` VALUES ('48', '5', '33', '12', '2018-05-13 19:46:24', '2018-05-13 19:46:24');
INSERT INTO `ti_qian_list` VALUES ('49', '5', '33', '33', '2018-05-13 19:46:51', '2018-05-13 19:46:51');
INSERT INTO `ti_qian_list` VALUES ('50', '5', 'asd1', 'asd1', '2018-05-13 20:21:05', '2018-05-13 20:21:16');
INSERT INTO `ti_qian_list` VALUES ('51', '2', 'wer', 'wre', '2018-05-13 20:35:11', '2018-05-13 20:35:11');
INSERT INTO `ti_qian_list` VALUES ('52', '6', 'wer2', 'wre2', '2018-05-13 20:36:24', '2018-05-13 20:36:24');
INSERT INTO `ti_qian_list` VALUES ('61', '9', '123123', '123123', '2018-05-21 11:15:13', '2018-05-21 11:15:13');
INSERT INTO `ti_qian_list` VALUES ('62', '9', '123123', '123', '2018-05-21 11:15:13', '2018-05-21 11:15:13');
INSERT INTO `ti_qian_list` VALUES ('63', '9', '123', '123', '2018-05-21 11:15:13', '2018-05-21 11:15:13');
INSERT INTO `ti_qian_list` VALUES ('64', '9', '123123', '123123', '2018-05-21 11:15:13', '2018-05-21 11:15:13');
INSERT INTO `ti_qian_list` VALUES ('65', '9', '123', '123', '2018-05-21 11:16:41', '2018-05-21 11:16:41');
INSERT INTO `ti_qian_list` VALUES ('66', '9', '312', '123', '2018-05-21 11:16:41', '2018-05-21 11:16:41');
INSERT INTO `ti_qian_list` VALUES ('67', '9', '3123', '1231123', '2018-05-21 11:16:41', '2018-05-21 11:16:41');
INSERT INTO `ti_qian_list` VALUES ('68', '9', '123123123', '123123', '2018-05-21 11:16:41', '2018-05-21 11:16:41');
INSERT INTO `ti_qian_list` VALUES ('69', '9', '123', '123\n123', '2018-05-22 16:57:12', '2018-05-22 16:57:12');

-- ----------------------------
-- Table structure for ti_user_qian_list
-- ----------------------------
DROP TABLE IF EXISTS `ti_user_qian_list`;
CREATE TABLE `ti_user_qian_list` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '一言',
  `qian_date` varchar(20) DEFAULT NULL COMMENT '@2-抽签时间',
  `status` int(11) DEFAULT NULL COMMENT '@4-状态-1=已拆,0=拆迁中',
  `qian_name` varchar(10) DEFAULT NULL COMMENT '@1-抽签人',
  `qian_content` varchar(50) DEFAULT NULL COMMENT '@1-抽签内容',
  `user_id` bigint(11) DEFAULT NULL COMMENT '@1-抽签人ID',
  `friend_open_id1` varchar(32) DEFAULT NULL COMMENT '@1-好友openId',
  `friend_open_id2` varchar(32) DEFAULT NULL COMMENT '@1-好友openId',
  `friend_open_id3` varchar(32) DEFAULT NULL COMMENT '@1-好友openId',
  `friend_open_id4` varchar(32) DEFAULT NULL COMMENT '@1-好友openId',
  `friend_open_id5` varchar(32) DEFAULT NULL COMMENT '@1-好友openId',
  `create_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-创建时间-DATETIME',
  `update_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-更新时间-DATETIME',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='一言';

-- ----------------------------
-- Records of ti_user_qian_list
-- ----------------------------

-- ----------------------------
-- Table structure for ti_yan_list
-- ----------------------------
DROP TABLE IF EXISTS `ti_yan_list`;
CREATE TABLE `ti_yan_list` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '一言',
  `constellation_id` bigint(10) DEFAULT NULL COMMENT '@2-星座id',
  `prev_pic` varchar(100) DEFAULT NULL COMMENT '@7-预览图',
  `speech` varchar(200) DEFAULT NULL COMMENT '@1-每日一言',
  `publish_person` varchar(10) DEFAULT NULL COMMENT '@1-发布人',
  `publish_status` varchar(10) DEFAULT NULL COMMENT '@1-发布状态',
  `publish_time` varchar(20) DEFAULT NULL COMMENT '@1-发布时间',
  `create_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-创建时间-DATETIME',
  `update_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-更新时间-DATETIME',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='一言';

-- ----------------------------
-- Records of ti_yan_list
-- ----------------------------
INSERT INTO `ti_yan_list` VALUES ('2', '12', '', null, 'admin', '1', '2018-5-12 23:07:51', '2018-05-12 23:07:57', '2018-05-12 23:07:57');
INSERT INTO `ti_yan_list` VALUES ('3', '13', '', null, 'admin', '1', '2018-5-12 23:08:22', '2018-05-12 23:08:30', '2018-05-12 23:08:30');
INSERT INTO `ti_yan_list` VALUES ('4', '8', '', null, 'admin', '1', '2018-5-12 23:08:49', '2018-05-12 23:08:57', '2018-05-12 23:08:57');
INSERT INTO `ti_yan_list` VALUES ('8', '12', '', null, 'admin', '0', '2018-5-12 23:30:53', '2018-05-12 23:30:59', '2018-05-12 23:30:59');
INSERT INTO `ti_yan_list` VALUES ('17', '10', null, null, 'admin', '1', '2018-05-16', '2018-05-13 15:25:55', '2018-05-13 15:25:55');
INSERT INTO `ti_yan_list` VALUES ('18', '10', null, null, 'admin2', '1', '2018-05-16', '2018-05-13 15:27:02', '2018-05-13 15:27:02');
INSERT INTO `ti_yan_list` VALUES ('19', '10', null, null, 'admin', '1', '2018-05-16', '2018-05-13 15:40:17', '2018-05-13 15:40:17');
INSERT INTO `ti_yan_list` VALUES ('20', '10', null, null, 'admin', '1', '2018-05-16', '2018-05-13 15:40:27', '2018-05-13 15:40:27');
INSERT INTO `ti_yan_list` VALUES ('21', '10', null, null, 'admin', '1', '2018-05-16', '2018-05-13 15:44:41', '2018-05-13 15:44:41');
INSERT INTO `ti_yan_list` VALUES ('22', '10', null, null, 'admin', '1', '2018-05-16', '2018-05-13 15:44:55', '2018-05-13 15:44:55');
INSERT INTO `ti_yan_list` VALUES ('24', '12', 'upload/tiYanList/2018/5/13/fc1b1a2f61f9412999393c4039d03162.jpg', null, 'admin', '1', '2018-5-12 23:30:53', '2018-05-13 15:46:51', '2018-05-13 15:46:51');
INSERT INTO `ti_yan_list` VALUES ('25', '8', 'upload/tiYanList/2018/5/13/38a4f65650dd4e1ea1338900e467194b.jpg', null, 'aa', '1', '2018-05-14', '2018-05-13 18:42:00', '2018-05-13 18:42:00');
INSERT INTO `ti_yan_list` VALUES ('26', '10', 'upload/tiYanList/2018/5/13/9dfb54bc863d48d9a7d99b45037f8aff.jpg', null, 'admin', '1', '2018-05-16', '2018-05-13 18:42:36', '2018-05-13 18:42:36');
INSERT INTO `ti_yan_list` VALUES ('27', '10', 'upload/tiYanList/2018/5/13/bac3a59f28d84cffbfd9742160a6977a.jpg', null, 'admin', '1', '2018-05-16', '2018-05-13 18:44:29', '2018-05-13 18:44:29');
INSERT INTO `ti_yan_list` VALUES ('28', '10', null, null, 'admin', '1', '2018-05-01', '2018-05-13 18:45:00', '2018-05-13 18:45:00');
INSERT INTO `ti_yan_list` VALUES ('29', '12', 'upload/tiYanList/2018/5/13/312e658eb1184d7d971b2656277c8edb.jpg', null, 'admin', '1', '2018-5-12 23:30:53', '2018-05-13 18:49:40', '2018-05-13 18:49:40');
INSERT INTO `ti_yan_list` VALUES ('30', '8', 'upload/tiYanList/2018/5/13/3dba2117d2ad427aba763bfef2987610.jpg', null, 'admin', '1', '2018-05-31', '2018-05-13 18:51:40', '2018-05-13 18:51:40');
INSERT INTO `ti_yan_list` VALUES ('31', '12', 'upload/tiYanList/2018/5/13/19fc700b3bb14ab4aecd885d06d1e984.jpg', null, 'ss', '1', '2018-05-07', '2018-05-13 19:10:48', '2018-05-13 19:10:48');
INSERT INTO `ti_yan_list` VALUES ('32', '12', 'upload/tiYanList/2018/5/13/6522a16bec3e4529bd83e22cc701bc1d.jpg', null, 'admin', '1', '2018-5-12 23:30:53', '2018-05-13 19:31:14', '2018-05-13 19:31:14');
INSERT INTO `ti_yan_list` VALUES ('33', '10', 'upload/tiYanList/2018/5/13/4af00bd55659464fa930ee64b0daed3a.jpg', null, 'admin', '1', '2018-05-08', '2018-05-13 19:31:43', '2018-05-13 19:31:43');
INSERT INTO `ti_yan_list` VALUES ('34', '9', 'upload/tiYanList/2018/5/13/21aae402ec8d42af8f1ecc66c99b989b.jpg', null, 'ddd', '1', '2018-05-01', '2018-05-13 19:32:10', '2018-05-13 19:32:10');
INSERT INTO `ti_yan_list` VALUES ('35', '11', 'upload/tiYanList/2018/5/13/811d0ee9e2fd46f2a1de1c1eab718630.png', null, 'pengjin', '1', '2018-05-01', '2018-05-13 20:30:35', '2018-05-13 20:30:35');
INSERT INTO `ti_yan_list` VALUES ('36', '12', 'upload/tiYanList/2018/5/13/525f5e36a50f41298e2b8fa92b1018be.jpg', null, '814', '1', '2018-05-07', '2018-05-13 20:31:21', '2018-05-13 20:31:21');
INSERT INTO `ti_yan_list` VALUES ('37', '11', '/3ca1ad2f23ee4cb29de213e5e6c72aed_6b9a156931634585bee70800c59906c4.jpg', null, 'admin', '1', '2018-05-17', '2018-05-13 23:30:51', '2018-05-14 14:42:37');
INSERT INTO `ti_yan_list` VALUES ('38', '10', '/cb5e7bae779e4797b5c773891548391c_31693e85e05f4688ab5150e765f7bb04.jpg', null, 'admin', '1', '2018-05-16', '2018-05-14 14:41:50', '2018-05-14 14:41:50');
INSERT INTO `ti_yan_list` VALUES ('40', '9', '/b3aec26856904729a41e4bb6be651aae_8c5cc7dc9e55453883ab4e74e92f94bb.jpg', null, 'admin', '0', '2018-05-16 04:02:02', '2018-05-15 15:23:39', '2018-05-15 15:23:39');
INSERT INTO `ti_yan_list` VALUES ('41', '2', '/ce818cdb10de4b9da0e90173d219d158_e00fa09c29174caf880a43c7d6314700.jpg', null, 'admin', '0', '2018-05-20 14:23:02', '2018-05-20 14:23:28', '2018-05-20 14:23:28');
INSERT INTO `ti_yan_list` VALUES ('42', '2', '/f41533df60c14e5bbf7e7c1f4d787f42_f0de427a658c45a680e511a3e4b9dde4.jpg', null, '123', '0', '2018-05-20 15:08:06', '2018-05-20 15:08:38', '2018-05-20 15:08:38');
INSERT INTO `ti_yan_list` VALUES ('43', '1', '/7e94fde4e5fb4dc4bab5cc55250f01c7_0a50cb9e53f34920a4b7e240520aada8.jpg', null, 'admin', '0', '2018-05-20 15:55:25', '2018-05-20 15:55:54', '2018-05-20 15:55:54');
INSERT INTO `ti_yan_list` VALUES ('44', '4', '/5b143500596148e59923f07c190a080c_cb749d91871545209bd266306c5adaee.jpg', null, 'admin', '0', '2018-05-20 15:55:38', '2018-05-20 15:56:05', '2018-05-20 15:56:05');
INSERT INTO `ti_yan_list` VALUES ('45', '6', '/11bd5476839544cb8df3b178d10092bf_03cdac12c18c40f49b4dd9ea5ae6149d.jpg', null, 'admin', '0', '2018-05-20 15:55:49', '2018-05-20 15:56:17', '2018-05-20 15:56:17');
INSERT INTO `ti_yan_list` VALUES ('46', '10', '/0852cdba44b245d6b3c8cc4150dedb69_875b72a1d2164627a30497af3b064cd2.jpg', null, 'asd', '0', '2018-05-20 15:56:02', '2018-05-20 15:56:28', '2018-05-20 15:56:28');
INSERT INTO `ti_yan_list` VALUES ('47', '10', '/5b16c4402b2b4df1bfc2fee443389317_27671dee4774425e958bcb011b97a2b4.jpg', null, 'admin', '0', '2018-05-20 15:56:12', '2018-05-20 15:56:39', '2018-05-20 15:56:39');
INSERT INTO `ti_yan_list` VALUES ('48', '6', '/2abad72a08ca4cd8a5d9d29ce58bcb43_5511071706e94a91b9335619eef01107.jpg', null, 'asd', '0', '2018-05-20 15:57:19', '2018-05-20 15:57:44', '2018-05-20 15:57:44');
INSERT INTO `ti_yan_list` VALUES ('49', '6', '/b2ff2d57a3484833add750ccf8b54925_282402bd74384f94be5821542cc50c67.jpg', null, 'asd', '0', '2018-05-20 15:57:27', '2018-05-20 15:57:53', '2018-05-20 15:57:53');
INSERT INTO `ti_yan_list` VALUES ('50', '6', '/55df253155e6434cb1ef87fc7ddf58cf_3f633e31c06d43b280d22f93e6b1f536.jpg', null, 'asd', '0', '2018-05-20 15:57:36', '2018-05-20 15:58:01', '2018-05-20 15:58:01');
INSERT INTO `ti_yan_list` VALUES ('51', '4', '/9a5f0df30cf34af99ca0e5670f83858c_6c5b61b174914709818cf56d6b06608d.jpg', null, 'asd', '0', '2018-05-20 15:58:16', '2018-05-20 15:58:40', '2018-05-20 15:58:40');

-- ----------------------------
-- Table structure for weixin_user
-- ----------------------------
DROP TABLE IF EXISTS `weixin_user`;
CREATE TABLE `weixin_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '微信用户id',
  `constellation_id` bigint(20) DEFAULT NULL COMMENT '@2-星座id',
  `open_id` varchar(32) DEFAULT '' COMMENT '@1-微信openId',
  `user_name` varchar(100) DEFAULT '' COMMENT '@1-真实姓名',
  `nick_name` varchar(100) DEFAULT '' COMMENT '@1-昵称',
  `phone_no` varchar(20) DEFAULT '' COMMENT '@1-电话号码',
  `is_disabled` varchar(1) DEFAULT '' COMMENT '@1-是否可用 是 否',
  `head_image` varchar(200) DEFAULT '' COMMENT '@1-头像',
  `gender` varchar(1) DEFAULT '' COMMENT '@1-性别男 女',
  `passwd` varchar(32) DEFAULT '' COMMENT '@1-密码',
  `address` varchar(200) DEFAULT '' COMMENT '@1-地址',
  `create_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-创建时间-DATETIME',
  `update_timestamp` varchar(20) DEFAULT NULL COMMENT '@9-更新时间-DATETIME',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COMMENT='微信用户';

-- ----------------------------
-- Records of weixin_user
-- ----------------------------
INSERT INTO `weixin_user` VALUES ('1', '12', 'asd1231wdazb23!@$', '张三', '张三', '13112312322', null, 'xxxzx.jpg', '0', '13123', '111', '2018-05-13 14:48:50', '2018-05-13 14:48:50');
INSERT INTO `weixin_user` VALUES ('2', '12', 'asd1231wdazb23!@$', '张三', '张三', '13112312322', null, 'xxxzx.jpg', '0', '13123', '111', '2018-05-13 14:49:01', '2018-05-13 14:49:01');
INSERT INTO `weixin_user` VALUES ('3', '12', 'asd1231wdazb23!@$', '张三', '张三', '13112312322', null, 'xxxzx.jpg', '0', '13123', '111', '2018-05-13 14:49:03', '2018-05-13 14:49:03');
INSERT INTO `weixin_user` VALUES ('5', '12', 'asd1231wdazb23!@$', '张三', '张三', '13112312322', null, 'xxxzx.jpg', '0', '13123', '111', '2018-05-13 14:50:10', '2018-05-13 14:50:10');
INSERT INTO `weixin_user` VALUES ('7', '12', 'asd1231wdazb23!@$', '张三', '张三', '13112312322', null, 'xxxzx.jpg', '0', '13123', '111', '2018-05-13 14:50:12', '2018-05-13 14:50:12');
INSERT INTO `weixin_user` VALUES ('8', '12', 'asd1231wdazb23!@$', '张三', '张三', '13112312322', null, 'xxxzx.jpg', '0', '13123', '111', '2018-05-13 14:50:13', '2018-05-13 14:50:13');
INSERT INTO `weixin_user` VALUES ('9', '12', 'asd1231wdazb23!@$', '张三', '张三', '13112312322', null, 'xxxzx.jpg', '0', '13123', '111', '2018-05-13 14:50:14', '2018-05-13 14:50:14');
INSERT INTO `weixin_user` VALUES ('10', '12', 'asd1231wdazb23!@$', '张三', '张三', '13112312322', null, 'xxxzx.jpg', '0', '13123', '111', '2018-05-13 14:50:15', '2018-05-13 14:50:15');
INSERT INTO `weixin_user` VALUES ('11', '12', 'asd1231wdazb23!@$', '张三', '张三', '13112312322', null, 'xxxzx.jpg', '0', '13123', '111', '2018-05-13 14:50:16', '2018-05-13 14:50:16');
INSERT INTO `weixin_user` VALUES ('12', '12', 'asd1231wdazb23!@$', '张三', '张三', '13112312322', null, 'xxxzx.jpg', '0', '13123', '111', '2018-05-13 14:50:17', '2018-05-13 14:50:17');
INSERT INTO `weixin_user` VALUES ('13', '12', 'asd1231wdazb23!@$', '张三', '张三', '13112312322', null, 'xxxzx.jpg', '0', '13123', '111', '2018-05-13 14:50:18', '2018-05-13 14:50:18');
INSERT INTO `weixin_user` VALUES ('14', '12', 'asd1231wdazb23!@$', '张三', '张三', '13112312322', null, 'xxxzx.jpg', '0', '13123', '111', '2018-05-13 14:50:19', '2018-05-13 14:50:19');
INSERT INTO `weixin_user` VALUES ('15', '12', 'asd1231wdazb23!@$', '张三', '张三', '13112312322', null, 'xxxzx.jpg', '0', '13123', '111', '2018-05-13 14:50:20', '2018-05-13 14:50:20');
INSERT INTO `weixin_user` VALUES ('16', '12', 'asd1231wdazb23!@$', '张三', '张三', '13112312322', null, 'xxxzx.jpg', '0', '13123', '111', '2018-05-13 14:50:21', '2018-05-13 14:50:21');
INSERT INTO `weixin_user` VALUES ('17', '12', 'asd1231wdazb23!@$', '张三', '张三', '13112312322', null, 'xxxzx.jpg', '0', '13123', '111', '2018-05-13 14:50:22', '2018-05-13 14:50:22');
INSERT INTO `weixin_user` VALUES ('18', '12', 'asd1231wdazb23!@$', '张三', '张三', '13112312322', null, 'xxxzx.jpg', '0', '13123', '111', '2018-05-13 14:50:24', '2018-05-13 14:50:24');
INSERT INTO `weixin_user` VALUES ('19', '12', 'asd1231wdazb23!@$', '张三', '张三', '13112312322', null, 'xxxzx.jpg', '0', '13123', '111', '2018-05-13 14:50:25', '2018-05-13 14:50:25');
INSERT INTO `weixin_user` VALUES ('20', '12', 'asd1231wdazb23!@$', '张三', '张三', '13112312322', null, 'xxxzx.jpg', '0', '13123', '111', '2018-05-13 14:50:26', '2018-05-13 14:50:26');
INSERT INTO `weixin_user` VALUES ('21', '12', 'asd1231wdazb23!@$', '张三', '张三', '13112312322', null, 'xxxzx.jpg', '0', '13123', '111', '2018-05-13 14:50:27', '2018-05-13 14:50:27');
INSERT INTO `weixin_user` VALUES ('28', '8', 'omONY5MOQNKO5BOz3nP_NADuRMms', null, '5aWH4L286K+64L288J+kk+Wwj+C9vFHgvbw=', null, '0', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIdYEvEncYDIwIPq6O7X0NjtQCnGt5LjAnnLRXWbickqNTHWgnYSqibmWBhk1aQeUC3P8PzbcOHVtZA/132', null, null, null, '2018-05-14 09:50:30', '2018-05-14 17:33:06');
