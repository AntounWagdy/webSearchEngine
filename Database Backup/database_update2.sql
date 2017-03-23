-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: search_engine
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `doc_words`
--

DROP TABLE IF EXISTS `doc_words`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doc_words` (
  `ID_doc` int(11) NOT NULL,
  `word` longtext NOT NULL,
  `position` bigint(8) NOT NULL,
  `key` bigint(8) NOT NULL AUTO_INCREMENT,
  `status` longtext NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=39012 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doc_words`
--

LOCK TABLES `doc_words` WRITE;
/*!40000 ALTER TABLE `doc_words` DISABLE KEYS */;
/*!40000 ALTER TABLE `doc_words` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `document`
--

DROP TABLE IF EXISTS `document`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `document` (
  `docId` int(11) NOT NULL AUTO_INCREMENT,
  `Url` longtext NOT NULL,
  PRIMARY KEY (`docId`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `document`
--

LOCK TABLES `document` WRITE;
/*!40000 ALTER TABLE `document` DISABLE KEYS */;
/*!40000 ALTER TABLE `document` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `downloaded_page`
--

DROP TABLE IF EXISTS `downloaded_page`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `downloaded_page` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `page_content` longtext NOT NULL,
  `Url` longtext NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=553 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `downloaded_page`
--

LOCK TABLES `downloaded_page` WRITE;
/*!40000 ALTER TABLE `downloaded_page` DISABLE KEYS */;
/*!40000 ALTER TABLE `downloaded_page` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `host_robot_handler`
--

DROP TABLE IF EXISTS `host_robot_handler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `host_robot_handler` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `host` longtext NOT NULL,
  `robot_handler` longtext NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `host_robot_handler`
--

LOCK TABLES `host_robot_handler` WRITE;
/*!40000 ALTER TABLE `host_robot_handler` DISABLE KEYS */;
/*!40000 ALTER TABLE `host_robot_handler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `robot_handler_1`
--

DROP TABLE IF EXISTS `robot_handler_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `robot_handler_1` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `host` longtext NOT NULL,
  `crawl_delay` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `robot_handler_1`
--

LOCK TABLES `robot_handler_1` WRITE;
/*!40000 ALTER TABLE `robot_handler_1` DISABLE KEYS */;
/*!40000 ALTER TABLE `robot_handler_1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `robot_handler_2`
--

DROP TABLE IF EXISTS `robot_handler_2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `robot_handler_2` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `host` varchar(1000) NOT NULL,
  `url_disallowed` varchar(2000) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `host_idx` (`host`)
) ENGINE=InnoDB AUTO_INCREMENT=2665 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `robot_handler_2`
--

LOCK TABLES `robot_handler_2` WRITE;
/*!40000 ALTER TABLE `robot_handler_2` DISABLE KEYS */;
/*!40000 ALTER TABLE `robot_handler_2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `to_visit`
--

DROP TABLE IF EXISTS `to_visit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `to_visit` (
  `doc_url` longtext NOT NULL,
  `docID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`docID`)
) ENGINE=InnoDB AUTO_INCREMENT=4740 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `to_visit`
--

LOCK TABLES `to_visit` WRITE;
/*!40000 ALTER TABLE `to_visit` DISABLE KEYS */;
/*!40000 ALTER TABLE `to_visit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visited`
--

DROP TABLE IF EXISTS `visited`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `visited` (
  `Url_Id` int(11) NOT NULL AUTO_INCREMENT,
  `Url` longtext NOT NULL,
  PRIMARY KEY (`Url_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=525 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visited`
--

LOCK TABLES `visited` WRITE;
/*!40000 ALTER TABLE `visited` DISABLE KEYS */;
/*!40000 ALTER TABLE `visited` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-23 17:38:34
