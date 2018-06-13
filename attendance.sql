-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: att
-- ------------------------------------------------------
-- Server version	5.7.9-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `absence`
--

DROP TABLE IF EXISTS `absence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `absence` (
  `student_id` int(11) NOT NULL,
  `course_id` varchar(45) NOT NULL,
  `week1` int(1) DEFAULT NULL,
  `week2` int(1) DEFAULT NULL,
  `week3` int(1) DEFAULT NULL,
  `week4` int(1) DEFAULT NULL,
  `week5` int(1) DEFAULT NULL,
  `week6` int(1) DEFAULT NULL,
  `week7` int(1) DEFAULT NULL,
  `week8` int(1) DEFAULT NULL,
  `week9` int(1) DEFAULT NULL,
  `week10` int(1) DEFAULT NULL,
  `week11` int(1) DEFAULT NULL,
  `week12` int(1) DEFAULT NULL,
  `week13` int(1) DEFAULT NULL,
  `week14` int(1) DEFAULT NULL,
  `week15` int(1) DEFAULT NULL,
  `week16` int(1) DEFAULT NULL,
  `week17` int(1) DEFAULT NULL,
  `week18` int(1) DEFAULT NULL,
  `total` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`student_id`,`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `absence`
--

LOCK TABLES `absence` WRITE;
/*!40000 ALTER TABLE `absence` DISABLE KEYS */;
INSERT INTO `absence` VALUES (110256987,'BBC6096',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,0),(110256987,'EBU6072',NULL,NULL,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(110256987,'EBU6073',NULL,NULL,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(110256987,'EBU6074',NULL,NULL,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(110256987,'EBU6215',NULL,NULL,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(120746726,'BBC5012',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,0),(120746726,'BBC5014',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,0),(120746726,'EBU5082',NULL,NULL,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(120746726,'EBU5083',NULL,NULL,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(120746726,'EBU5084',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(120746726,'EBU5464',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(120777764,'BBC5014',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,0),(120777764,'EBU5082',NULL,NULL,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(120777764,'EBU5083',NULL,NULL,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(120777764,'EBU5084',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(120777764,'EBU5464',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(120796120,'BBC5014',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,0),(120796120,'EBU5082',NULL,NULL,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(120796120,'EBU5083',NULL,NULL,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(120796120,'EBU5084',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(120796120,'EBU5464',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(130192168,'BBC4096',0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(130192168,'BBC4098',0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(130192168,'BBC4100',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,0),(130192168,'EBC4652',NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,0),(130192168,'EBU4201',NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,0),(130192168,'EBU4202',NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,0),(130192168,'EBU4204',0,0,NULL,NULL,NULL,NULL,0,0,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,0),(130192168,'EBU4206',0,0,NULL,NULL,NULL,NULL,0,0,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,0),(130598465,'BBC4096',0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(130598465,'BBC4098',0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(130598465,'EBU4201',NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,0),(130598465,'EBU4202',NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,0),(130598465,'EBU4204',0,0,NULL,NULL,NULL,NULL,0,0,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,0),(130598465,'EBU4206',0,0,NULL,NULL,NULL,NULL,0,0,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,0),(140456789,'BBC3042',NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,NULL,0),(140456789,'BBC3506',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(140456789,'BBC3507',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(140456789,'BBC3513',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,0),(140456789,'EBU3059',NULL,NULL,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(140456789,'EBU3084',NULL,NULL,0,0,0,0,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(140456789,'EBU3605',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(140456789,'EBU3607',NULL,0,0,0,NULL,0,0,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0),(140456789,'EBU3608',NULL,NULL,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(140458007,'BBC3042',NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,NULL,0),(140458007,'BBC3506',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(140458007,'BBC3507',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(140458007,'EBU3059',NULL,NULL,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(140458007,'EBU3084',NULL,NULL,0,0,0,0,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(140458007,'EBU3605',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(140458007,'EBU3607',NULL,0,0,0,NULL,0,0,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0),(140746321,'BBC3042',NULL,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,NULL,0),(140746321,'BBC3506',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(140746321,'BBC3507',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(140746321,'BBC3513',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,0),(140746321,'EBU3059',NULL,NULL,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(140746321,'EBU3084',NULL,NULL,0,0,0,0,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(140746321,'EBU3605',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(140746321,'EBU3607',NULL,0,0,0,NULL,0,0,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0),(140746321,'EBU3608',NULL,NULL,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0);
/*!40000 ALTER TABLE `absence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attendance` (
  `student_id` int(11) NOT NULL,
  `course_id` varchar(45) NOT NULL,
  `att` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`student_id`,`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES (110256987,'BBC6096',0),(110256987,'EBU6072',0),(110256987,'EBU6073',0),(110256987,'EBU6074',0),(110256987,'EBU6215',0),(120746726,'BBC5012',0),(120746726,'BBC5014',0),(120746726,'EBU5082',0),(120746726,'EBU5083',0),(120746726,'EBU5084',0),(120746726,'EBU5464',0),(120777764,'BBC5014',0),(120777764,'EBU5082',0),(120777764,'EBU5083',0),(120777764,'EBU5084',0),(120777764,'EBU5464',0),(120796120,'BBC5014',0),(120796120,'EBU5082',0),(120796120,'EBU5083',0),(120796120,'EBU5084',0),(120796120,'EBU5464',0),(130192168,'BBC4096',0),(130192168,'BBC4098',0),(130192168,'BBC4100',0),(130192168,'EBC4652',0),(130192168,'EBU4201',0),(130192168,'EBU4202',0),(130192168,'EBU4204',0),(130192168,'EBU4206',0),(130598465,'BBC4096',0),(130598465,'BBC4098',0),(130598465,'EBU4201',0),(130598465,'EBU4202',0),(130598465,'EBU4204',0),(130598465,'EBU4206',0),(140456789,'BBC3042',0),(140456789,'BBC3506',0),(140456789,'BBC3507',0),(140456789,'BBC3513',0),(140456789,'EBU3059',0),(140456789,'EBU3084',0),(140456789,'EBU3605',0),(140456789,'EBU3607',1),(140456789,'EBU3608',0),(140458007,'BBC3042',0),(140458007,'BBC3506',0),(140458007,'BBC3507',0),(140458007,'EBU3059',0),(140458007,'EBU3084',0),(140458007,'EBU3605',0),(140458007,'EBU3607',2),(140746321,'BBC3042',0),(140746321,'BBC3506',0),(140746321,'BBC3507',0),(140746321,'BBC3513',0),(140746321,'EBU3059',0),(140746321,'EBU3084',0),(140746321,'EBU3605',0),(140746321,'EBU3607',0),(140746321,'EBU3608',0);
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lateness`
--

DROP TABLE IF EXISTS `lateness`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lateness` (
  `student_id` int(11) NOT NULL,
  `course_id` varchar(45) NOT NULL,
  `week1` int(11) DEFAULT NULL,
  `week2` int(11) DEFAULT NULL,
  `week3` int(11) DEFAULT NULL,
  `week4` int(11) DEFAULT NULL,
  `week5` int(11) DEFAULT NULL,
  `week6` int(11) DEFAULT NULL,
  `week7` int(11) DEFAULT NULL,
  `week8` int(11) DEFAULT NULL,
  `week9` int(11) DEFAULT NULL,
  `week10` int(11) DEFAULT NULL,
  `week11` int(11) DEFAULT NULL,
  `week12` int(11) DEFAULT NULL,
  `week13` int(11) DEFAULT NULL,
  `week14` int(11) DEFAULT NULL,
  `week15` int(11) DEFAULT NULL,
  `week16` int(11) DEFAULT NULL,
  `week17` int(11) DEFAULT NULL,
  `week18` int(11) DEFAULT NULL,
  `total` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`student_id`,`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lateness`
--

LOCK TABLES `lateness` WRITE;
/*!40000 ALTER TABLE `lateness` DISABLE KEYS */;
INSERT INTO `lateness` VALUES (110256987,'BBC6096',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,0),(110256987,'EBU6072',NULL,NULL,0,0,1,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,1),(110256987,'EBU6073',NULL,NULL,0,0,1,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,1),(110256987,'EBU6074',NULL,NULL,0,0,1,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,1),(110256987,'EBU6215',NULL,NULL,0,0,1,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,1),(120746726,'BBC5012',0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,1),(120746726,'BBC5014',0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,1),(120746726,'EBU5082',NULL,NULL,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(120746726,'EBU5083',NULL,NULL,0,0,1,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,1),(120746726,'EBU5084',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(120746726,'EBU5464',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(120777764,'BBC5014',0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,1),(120777764,'EBU5082',NULL,NULL,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(120777764,'EBU5083',NULL,NULL,0,0,1,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,1),(120777764,'EBU5084',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(120777764,'EBU5464',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(120796120,'BBC5014',0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,1),(120796120,'EBU5082',NULL,NULL,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(120796120,'EBU5083',NULL,NULL,0,0,1,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,1),(120796120,'EBU5084',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(120796120,'EBU5464',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(130192168,'BBC4096',0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(130192168,'BBC4098',0,0,0,0,1,0,0,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,1),(130192168,'BBC4100',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,0),(130192168,'EBC4652',NULL,NULL,0,0,1,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,1),(130192168,'EBU4201',NULL,NULL,0,0,1,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,1),(130192168,'EBU4202',NULL,NULL,0,0,1,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,1),(130192168,'EBU4204',0,0,NULL,NULL,NULL,NULL,0,0,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,0),(130192168,'EBU4206',0,0,NULL,NULL,NULL,NULL,0,0,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,0),(130598465,'BBC4096',0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,0),(130598465,'BBC4098',0,0,0,0,1,0,0,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,1),(130598465,'EBU4201',NULL,NULL,0,0,1,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,1),(130598465,'EBU4202',NULL,NULL,0,0,1,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,1),(130598465,'EBU4204',0,0,NULL,NULL,NULL,NULL,0,0,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,0),(130598465,'EBU4206',0,0,NULL,NULL,NULL,NULL,0,0,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,0),(140456789,'BBC3042',NULL,0,0,0,2,0,0,0,0,0,0,0,0,0,0,NULL,NULL,NULL,2),(140456789,'BBC3506',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(140456789,'BBC3507',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(140456789,'BBC3513',0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,1),(140456789,'EBU3059',NULL,NULL,0,0,1,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,1),(140456789,'EBU3084',NULL,NULL,0,0,1,0,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,1),(140456789,'EBU3605',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(140456789,'EBU3607',NULL,0,0,0,NULL,0,0,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0),(140456789,'EBU3608',NULL,NULL,0,0,1,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,1),(140458007,'BBC3042',NULL,0,0,0,2,0,0,0,0,0,0,0,0,0,0,NULL,NULL,NULL,2),(140458007,'BBC3506',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(140458007,'BBC3507',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(140458007,'EBU3059',NULL,NULL,0,0,1,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,1),(140458007,'EBU3084',NULL,NULL,0,0,1,0,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,1),(140458007,'EBU3605',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(140458007,'EBU3607',NULL,0,0,0,NULL,0,0,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0),(140746321,'BBC3042',NULL,0,0,0,2,0,0,0,0,0,0,0,0,0,0,NULL,NULL,NULL,2),(140746321,'BBC3506',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(140746321,'BBC3507',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(140746321,'BBC3513',0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,NULL,NULL,1),(140746321,'EBU3059',NULL,NULL,0,0,1,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,1),(140746321,'EBU3084',NULL,NULL,0,0,1,0,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,1),(140746321,'EBU3605',0,0,0,0,NULL,NULL,0,0,0,0,NULL,NULL,NULL,NULL,0,0,NULL,NULL,0),(140746321,'EBU3607',NULL,0,0,0,NULL,0,0,0,NULL,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0),(140746321,'EBU3608',NULL,NULL,0,0,1,NULL,NULL,0,0,0,0,NULL,NULL,NULL,0,0,NULL,NULL,1);
/*!40000 ALTER TABLE `lateness` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `id` int(9) NOT NULL,
  `password` char(8) NOT NULL,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `user_type` int(1) NOT NULL,
  `device` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `student_id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (110256987,'3507','Zhuoran','Li',0,NULL),(120120120,'1234','Zhongshan','Sun',1,'35436007021481'),(120746726,'2018','Francis','Fitzgerald',0,'35436007021580'),(120777764,'4321','Yining','Teng',0,'02864128041460'),(120796120,'4208','Lily','Smith',0,NULL),(130192168,'6094','Qiangdong','Liu',0,'35436007021481'),(130598465,'3608','John','Watson',0,NULL),(140456789,'0318','Sumail','Huang',0,NULL),(140458007,'1994','Albert','Spock',0,NULL),(140746321,'0319','Sherlock','Holmes',0,NULL);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `time_table`
--

DROP TABLE IF EXISTS `time_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `time_table` (
  `course_id` char(7) NOT NULL,
  `weeks` varchar(45) DEFAULT NULL,
  `monday` time DEFAULT NULL,
  `dur1` int(1) DEFAULT NULL,
  `tuesday` time DEFAULT NULL,
  `dur2` int(1) DEFAULT NULL,
  `wednesday` time DEFAULT NULL,
  `dur3` int(1) DEFAULT NULL,
  `thursday` time DEFAULT NULL,
  `dur4` int(1) DEFAULT NULL,
  `friday` time DEFAULT NULL,
  `dur5` int(1) DEFAULT NULL,
  `location` int(4) DEFAULT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `time_table`
--

LOCK TABLES `time_table` WRITE;
/*!40000 ALTER TABLE `time_table` DISABLE KEYS */;
INSERT INTO `time_table` VALUES ('BBC3042','2,3,4,5,6,7,8,9,10,11,12,13,14,15','13:00:00',2,'08:00:00',2,'10:00:00',2,'13:00:00',2,'10:00:00',2,2417),('BBC3506','1,2,3,4,7,8,9,10,15,16','13:00:00',1,NULL,NULL,'08:00:00',1,NULL,NULL,'13:00:00',1,2406),('BBC3507','1,2,3,4,7,8,9,10,15,16','08:00:00',2,'10:00:00',2,'10:00:00',2,'15:00:00',2,'15:00:00',2,2306),('BBC3513','1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16','08:00:00',1,'08:00:00',1,'08:00:00',1,'10:00:00',1,'10:00:00',1,2313),('BBC4096','1,2,3,4,5,6,7,8,9,10,11,15,16',NULL,NULL,'10:00:00',2,'10:00:00',2,NULL,NULL,'08:00:00',2,2406),('BBC4098','1,2,3,4,5,6,7,8,9,10,11,15,16','08:00:00',2,'15:00:00',2,NULL,NULL,'13:00:00',2,NULL,NULL,2406),('BBC4100','1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16','13:00:00',1,NULL,NULL,'15:00:00',1,NULL,NULL,'15:00:00',1,2517),('BBC5012','1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16','13:00:00',2,'10:00:00',2,'13:00:00',2,'08:00:00',2,'13:00:00',2,1309),('BBC5014','1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16','10:00:00',2,'10:00:00',2,'13:00:00',2,'08:00:00',2,'13:00:00',2,2409),('BBC6096','1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16','13:00:00',2,NULL,NULL,'08:00:00',2,NULL,NULL,'08:00:00',2,2418),('EBC4652','3,4,5,6,10,11,12,13,17,18','10:00:00',2,'13:00:00',2,'10:00:00',2,'15:00:00',2,'13:00:00',2,2313),('EBU3059','3,4,5,8,9,10,11,15,16','13:00:00',2,'08:00:00',2,'08:00:00',2,'10:00:00',2,'08:00:00',2,2325),('EBU3084','3,4,5,6,8,9,10,11,15,16','08:00:00',2,'13:00:00',2,'08:00:00',2,'13:00:00',2,'08:00:00',2,2308),('EBU3605','1,2,3,4,7,8,9,10,15,16','10:00:00',2,'08:00:00',2,'13:00:00',2,'15:00:00',2,'10:00:00',2,2406),('EBU3607','2,3,4,6,7,8,12,13,14,15','08:00:00',2,'10:00:00',2,'08:00:00',2,'10:00:00',2,'10:00:00',2,2517),('EBU3608','3,4,5,8,9,10,11,15,16','10:00:00',2,'10:00:00',2,'13:00:00',2,'08:00:00',2,'13:00:00',2,2417),('EBU4201','3,4,5,6,10,11,12,13,17,18','13:00:00',2,'15:00:00',2,'13:00:00',2,'08:00:00',2,'10:00:00',2,2306),('EBU4202','3,4,5,6,10,11,12,13,17,18','10:00:00',2,'08:00:00',2,'15:00:00',2,'10:00:00',2,'08:00:00',2,2306),('EBU4204','1,2,7,8,9,13,14,15,16','15:00:00',2,'13:00:00',2,'08:00:00',2,'13:00:00',2,'13:00:00',2,2306),('EBU4206','1,2,7,8,9,13,14,15,16','08:00:00',2,'10:00:00',2,'13:00:00',2,'08:00:00',2,'10:00:00',2,2325),('EBU5082','3,4,5,8,9,10,11,15,16','08:00:00',1,'08:00:00',1,NULL,NULL,NULL,NULL,'15:00:00',1,2532),('EBU5083','3,4,5,8,9,10,11,15,16','10:00:00',2,'13:00:00',2,'10:00:00',2,'13:00:00',2,'08:00:00',2,2532),('EBU5084','1,2,3,4,7,8,9,10,15,16','08:00:00',2,'13:00:00',2,'10:00:00',2,'08:00:00',2,'10:00:00',2,2528),('EBU5464','1,2,3,4,7,8,9,10,15,16','10:00:00',2,'08:00:00',2,'13:00:00',2,'10:00:00',2,'08:00:00',2,2528),('EBU6072','3,4,5,8,9,10,11,15,16','08:00:00',2,'08:00:00',2,'13:00:00',2,'10:00:00',2,'10:00:00',2,2432),('EBU6073','3,4,5,8,9,10,11,15,16','15:00:00',2,'10:00:00',2,'08:00:00',2,'08:00:00',2,'13:00:00',2,2532),('EBU6074','3,4,5,8,9,10,11,15,16','10:00:00',2,'08:00:00',2,'10:00:00',2,'15:00:00',2,'15:00:00',2,2526),('EBU6215','3,4,5,8,9,10,11,15,16','15:00:00',2,'10:00:00',2,'15:00:00',2,'08:00:00',2,'10:00:00',2,2526);
/*!40000 ALTER TABLE `time_table` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-16  7:32:48