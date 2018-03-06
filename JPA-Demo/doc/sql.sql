/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.6.24 : Database - test
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`test` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `test`;

/*Table structure for table `t_city` */

CREATE TABLE `t_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `state` varchar(30) DEFAULT NULL,
  `country` varchar(30) DEFAULT NULL,
  `map` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `t_city` */

LOCK TABLES `t_city` WRITE;

insert  into `t_city`(`id`,`name`,`state`,`country`,`map`) values (2,'ShangHai','China.ShangHai','China','1'),(3,'NanJing','China.NanJing','China','1'),(4,'BeiJing','China.BeiJing','China','1'),(5,'GuangDong','China.GuangDong','China','1'),(6,'ShanDong','China.ShanDong','China','0');

UNLOCK TABLES;

/*Table structure for table `t_hotel` */

CREATE TABLE `t_hotel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `city` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Data for the table `t_hotel` */

LOCK TABLES `t_hotel` WRITE;

insert  into `t_hotel`(`id`,`name`,`address`,`city`) values (1,'ShangHai.HutaiLu','Seven.Day',2),(2,'ShangHai.HongQiao','MoTai',2),(3,'ShangHai.JiangXi','HanTing',2),(4,'ShangHai.RenMin','Eight.Day',2),(5,'ShangHai','Nine.Day',2),(8,'BeiJing','Seven.Day',3),(9,'BeiJing','Nine.Day',3),(10,'NanJing','MoTai',4),(11,'NanJing','HanTing',4),(12,'GuangDong','Nine.Day',5),(13,'GuangDong','Eight.Day',5);

UNLOCK TABLES;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `age` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'zhangsan', '18', '1111');
INSERT INTO `t_user` VALUES ('2', 'lisi', '20', '2222');
INSERT INTO `t_user` VALUES ('3', 'wangwu', '19', '2222');
INSERT INTO `t_user` VALUES ('4', 'bao1', '16', '1111');
INSERT INTO `t_user` VALUES ('5', 'bao2', '19', '1111');
INSERT INTO `t_user` VALUES ('7', 'bao4', '88', '1111');

-- ----------------------------
-- Table structure for t_idcard
-- ----------------------------
DROP TABLE IF EXISTS `t_idcard`;
CREATE TABLE `t_idcard` (
  `id` int(11) NOT NULL,
  `idNo` varchar(255) DEFAULT NULL,
  `person_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfl8133nq66cp2fjnc7u7vullo` (`person_id`),
  CONSTRAINT `FKfl8133nq66cp2fjnc7u7vullo` FOREIGN KEY (`person_id`) REFERENCES `t_person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_idcard
-- ----------------------------
INSERT INTO `t_idcard` VALUES ('1', '111', '1');
INSERT INTO `t_idcard` VALUES ('2', '222', '2');
INSERT INTO `t_idcard` VALUES ('3', '333', '3');


-- ----------------------------
-- Table structure for t_person
-- ----------------------------
DROP TABLE IF EXISTS `t_person`;
CREATE TABLE `t_person` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_person
-- ----------------------------
INSERT INTO `t_person` VALUES ('1', 'bao1', 'aaa');
INSERT INTO `t_person` VALUES ('2', 'bao2', 'bbb');
INSERT INTO `t_person` VALUES ('3', 'bao3', 'ccc');

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ORDER_NAME` varchar(20) DEFAULT NULL,
  `COUNT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('1', 'CN111', '111');
INSERT INTO `t_order` VALUES ('2', 'CN222', '222');
INSERT INTO `t_order` VALUES ('3', 'CN333', '333');

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ITEM_NAME` varchar(20) DEFAULT NULL,
  `PRICE` int(11) DEFAULT NULL,
  `ORDER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FKtq1wmoawe8voyf02thxxcnwgo` (`ORDER_ID`),
  CONSTRAINT `FKtq1wmoawe8voyf02thxxcnwgo` FOREIGN KEY (`ORDER_ID`) REFERENCES `t_order` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_item
-- ----------------------------
INSERT INTO `order_item` VALUES ('1', 'OI111', '111', '1');
INSERT INTO `order_item` VALUES ('2', 'OI222', '222', '2');
INSERT INTO `order_item` VALUES ('3', 'OI333', '333', '3');
INSERT INTO `order_item` VALUES ('4', 'OI444', '444', '1');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
