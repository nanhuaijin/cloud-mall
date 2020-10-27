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

 Date: 27/10/2020 18:03:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mall_role
-- ----------------------------
DROP TABLE IF EXISTS `mall_role`;
CREATE TABLE `mall_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(32) DEFAULT NULL COMMENT '角色',
  `handler_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `handler` varchar(32) DEFAULT NULL COMMENT '创建人昵称',
  `del` int(1) DEFAULT '0' COMMENT '0-未删除 1-删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Records of mall_role
-- ----------------------------
BEGIN;
INSERT INTO `mall_role` VALUES (1, 'ADMIN', 1, 'XXX', 0, '2020-10-27 15:01:22', '2020-10-27 15:01:25');
INSERT INTO `mall_role` VALUES (2, 'CONSUMER', 1, 'XXX', 0, '2020-10-27 15:04:30', '2020-10-27 15:04:33');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
