/*
 Navicat MySQL Data Transfer

 Source Server         : admin
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : employee

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 04/10/2020 19:32:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for staff_level
-- ----------------------------
DROP TABLE IF EXISTS `staff_level`;
CREATE TABLE `staff_level` (
  `id` int NOT NULL AUTO_INCREMENT,
  `s_name` varchar(20) NOT NULL,
  `s_level` int NOT NULL,
  `s_type` char(1) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `constrait_level` CHECK ((`s_level` in (3,4,5,6,7,8,9,10))),
  CONSTRAINT `staff_level_chk_1` CHECK ((`s_type` in (_utf8mb3'P',_utf8mb3'M')))
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
