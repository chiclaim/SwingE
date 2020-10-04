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

 Date: 04/10/2020 19:32:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `e_name` varchar(50) NOT NULL,
  `nickname` varchar(10) NOT NULL,
  `birthday` date DEFAULT NULL,
  `gender` tinyint DEFAULT '1',
  `department_id` int DEFAULT NULL,
  `staff_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nickname` (`nickname`),
  KEY `fk_depart_id` (`department_id`),
  KEY `constraint_staff` (`staff_id`),
  CONSTRAINT `constraint_staff` FOREIGN KEY (`staff_id`) REFERENCES `staff_level` (`id`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`),
  CONSTRAINT `constrait_gender` CHECK ((`gender` in (0,1)))
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
