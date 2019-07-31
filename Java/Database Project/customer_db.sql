CREATE DATABASE  IF NOT EXISTS `customer_db`;
USE `customer_db`;

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `family` varchar(45) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `address` varchar(400) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `userName` varchar(45) DEFAULT NULL,
  `password` varchar(25) DEFAULT NULL,
  `carType` varchar(10) DEFAULT NULL,
  `brand` varchar(15) DEFAULT NULL,
  `startYear` char(4) DEFAULT NULL,
  `startMonth` varchar(2) DEFAULT NULL,
  `startDay` varchar(2) DEFAULT NULL,
  `numberOfDays` smallint DEFAULT NULL,
  `rate` smallint DEFAULT NULL,
  `total` smallint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

LOCK TABLES `customer` WRITE;
INSERT INTO `customer` VALUES (1,'Nihan','Ni', '123-456-7892', '123 JAC street', 'Nihan@google.com', '', '', 'Small', '', '' ,'' ,'', 2, 50, 100),(2,'Tieda','Ti','123-456-7892', '123 JAC street', 'Nihan@google.com', '', '', 'SUV', '', '' ,'' ,'', 2, 60, 120),(3,'Vish','Vi','123-456-7892', '123 JAC street', 'Nihan@google.com', '', '', 'VAN', '', '' ,'' ,'', 3, 50, 150),(4,'William','Wi','123-456-7892', '123 JAC street', 'Nihan@google.com', '', '', 'Small', '', '' ,'' ,'', 4, 50, 200),(5,'Dimitri','Di','123-456-7892', '123 JAC street', 'Nihan@google.com', '', '', 'SUv', '', '' ,'' ,'', 1, 50, 50);
UNLOCK TABLES;

select*
from customer;
