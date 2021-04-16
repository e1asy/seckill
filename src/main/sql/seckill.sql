/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : 127.0.0.1:3306
 Source Schema         : seckill

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 16/04/2021 23:32:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(64) NOT NULL,
  `price` decimal(10,0) NOT NULL,
  `description` varchar(500) NOT NULL,
  `sales` int NOT NULL DEFAULT '0',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for item_stock
-- ----------------------------
DROP TABLE IF EXISTS `item_stock`;
CREATE TABLE `item_stock` (
  `id` int NOT NULL AUTO_INCREMENT,
  `stock` int NOT NULL DEFAULT '0',
  `item_id` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` varchar(32) NOT NULL,
  `user_id` int NOT NULL DEFAULT '0',
  `item_id` int NOT NULL,
  `item_price` double NOT NULL DEFAULT '0',
  `amount` int NOT NULL DEFAULT '0',
  `order_price` double NOT NULL DEFAULT '0',
  `promo_id` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for promo
-- ----------------------------
DROP TABLE IF EXISTS `promo`;
CREATE TABLE `promo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `promo_name` varchar(64) NOT NULL,
  `start_date` datetime NOT NULL DEFAULT '1000-01-01 00:00:00',
  `item_id` int NOT NULL DEFAULT '0',
  `promo_item_price` double NOT NULL DEFAULT '0',
  `end_date` datetime NOT NULL DEFAULT '1000-01-01 00:00:00',
  `promo_id` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sequence_info
-- ----------------------------
DROP TABLE IF EXISTS `sequence_info`;
CREATE TABLE `sequence_info` (
  `name` varchar(255) NOT NULL,
  `current_value` int NOT NULL DEFAULT '0',
  `step` int NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `gender` tinyint NOT NULL,
  `age` int NOT NULL,
  `telphone` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `register_mode` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `third_part_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `telphone_unique_index` (`telphone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_password
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password` (
  `id` int NOT NULL AUTO_INCREMENT,
  `encrpt_password` varchar(128) NOT NULL,
  `user_id` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
