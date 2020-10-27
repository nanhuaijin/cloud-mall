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

 Date: 27/10/2020 18:02:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mall_address
-- ----------------------------
DROP TABLE IF EXISTS `mall_address`;
CREATE TABLE `mall_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '关联mall_user表id',
  `name` varchar(32) DEFAULT NULL COMMENT '收货姓名',
  `sex` int(1) DEFAULT '0' COMMENT '0-女 1-男',
  `phone` char(11) DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `house_number` varchar(255) DEFAULT NULL COMMENT '详细地址-门牌号',
  `tag` int(1) DEFAULT '0' COMMENT '0-无 1-家 2-公司 3-父母家',
  `default_address` int(1) DEFAULT '0' COMMENT '0-不是 1-默认收货地址',
  `del` int(1) DEFAULT '0' COMMENT '0-正常 1-删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='收货地址表';

SET FOREIGN_KEY_CHECKS = 1;
