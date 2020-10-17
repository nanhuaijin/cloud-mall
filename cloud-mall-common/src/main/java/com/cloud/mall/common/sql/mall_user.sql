/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : cloud_mall

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 16/10/2020 17:05:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mall_user
-- ----------------------------
DROP TABLE IF EXISTS `mall_user`;
CREATE TABLE `mall_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `openid` varchar(255) DEFAULT NULL COMMENT '微信openid',
  `session_key` varchar(255) DEFAULT NULL COMMENT '微信会话密钥',
  `code` varchar(255) DEFAULT NULL COMMENT '验证微信登录的code，前端传递',
  `nick_name` varchar(32) DEFAULT NULL COMMENT '微信昵称',
  `gender` int(1) DEFAULT '1' COMMENT '性别 0-女 1-男',
  `phone` char(11) DEFAULT NULL COMMENT '手机号码',
  `city` varchar(32) DEFAULT NULL COMMENT '城市',
  `province` varchar(32) DEFAULT NULL COMMENT '省份',
  `country` varchar(32) DEFAULT NULL COMMENT '国家',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `del` int(1) DEFAULT '0' COMMENT '逻辑删除 0-未删除 1-删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

SET FOREIGN_KEY_CHECKS = 1;
