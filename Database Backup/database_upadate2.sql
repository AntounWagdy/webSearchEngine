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
  PRIMARY KEY (`key`),
  KEY `docId_idx` (`ID_doc`),
  CONSTRAINT `docId` FOREIGN KEY (`ID_doc`) REFERENCES `document` (`docId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1043376 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doc_words`
--

LOCK TABLES `doc_words` WRITE;
/*!40000 ALTER TABLE `doc_words` DISABLE KEYS */;
/*!40000 ALTER TABLE `doc_words` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doc_words2`
--

DROP TABLE IF EXISTS `doc_words2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doc_words2` (
  `ID_doc` int(11) NOT NULL,
  `word` longtext NOT NULL,
  `position` bigint(8) NOT NULL,
  `key` bigint(8) NOT NULL AUTO_INCREMENT,
  `status` longtext NOT NULL,
  PRIMARY KEY (`key`),
  KEY `docId2_idx` (`ID_doc`),
  CONSTRAINT `docId2` FOREIGN KEY (`ID_doc`) REFERENCES `document2` (`docId2`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=61626 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doc_words2`
--

LOCK TABLES `doc_words2` WRITE;
/*!40000 ALTER TABLE `doc_words2` DISABLE KEYS */;
/*!40000 ALTER TABLE `doc_words2` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=1152 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `document`
--

LOCK TABLES `document` WRITE;
/*!40000 ALTER TABLE `document` DISABLE KEYS */;
/*!40000 ALTER TABLE `document` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `document2`
--

DROP TABLE IF EXISTS `document2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `document2` (
  `docId2` int(11) NOT NULL,
  `Url` longtext NOT NULL,
  PRIMARY KEY (`docId2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `document2`
--

LOCK TABLES `document2` WRITE;
/*!40000 ALTER TABLE `document2` DISABLE KEYS */;
/*!40000 ALTER TABLE `document2` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=7263 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `downloaded_page`
--

LOCK TABLES `downloaded_page` WRITE;
/*!40000 ALTER TABLE `downloaded_page` DISABLE KEYS */;
/*!40000 ALTER TABLE `downloaded_page` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `edge`
--

DROP TABLE IF EXISTS `edge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `edge` (
  `from_url` mediumtext NOT NULL,
  `to_url` mediumtext NOT NULL,
  `pk` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `edge`
--

LOCK TABLES `edge` WRITE;
/*!40000 ALTER TABLE `edge` DISABLE KEYS */;
/*!40000 ALTER TABLE `edge` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `host_robot_handler`
--

LOCK TABLES `host_robot_handler` WRITE;
/*!40000 ALTER TABLE `host_robot_handler` DISABLE KEYS */;
/*!40000 ALTER TABLE `host_robot_handler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `in_links`
--

DROP TABLE IF EXISTS `in_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `in_links` (
  `url` mediumtext NOT NULL,
  `count` int(11) DEFAULT '0',
  `pk` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`pk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `in_links`
--

LOCK TABLES `in_links` WRITE;
/*!40000 ALTER TABLE `in_links` DISABLE KEYS */;
/*!40000 ALTER TABLE `in_links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phrases`
--

DROP TABLE IF EXISTS `phrases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phrases` (
  `key` bigint(8) NOT NULL AUTO_INCREMENT,
  `phrase` longtext NOT NULL,
  `webID` int(11) NOT NULL,
  PRIMARY KEY (`key`),
  KEY `fk_phrases_ref_document_idx` (`webID`),
  CONSTRAINT `fk_phrases_ref_document` FOREIGN KEY (`webID`) REFERENCES `document` (`docId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phrases`
--

LOCK TABLES `phrases` WRITE;
/*!40000 ALTER TABLE `phrases` DISABLE KEYS */;
/*!40000 ALTER TABLE `phrases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phrases2`
--

DROP TABLE IF EXISTS `phrases2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phrases2` (
  `key` bigint(8) NOT NULL AUTO_INCREMENT,
  `phrase` longtext NOT NULL,
  `webID` int(11) NOT NULL,
  PRIMARY KEY (`key`),
  KEY `fk_phrases2_ref_document2_idx` (`webID`),
  CONSTRAINT `fk_phrases2_ref_document2` FOREIGN KEY (`webID`) REFERENCES `document2` (`docId2`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phrases2`
--

LOCK TABLES `phrases2` WRITE;
/*!40000 ALTER TABLE `phrases2` DISABLE KEYS */;
/*!40000 ALTER TABLE `phrases2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rank`
--

DROP TABLE IF EXISTS `rank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rank` (
  `docId` int(11) NOT NULL,
  `page_rank` double NOT NULL,
  PRIMARY KEY (`docId`),
  KEY `fk_rank_ref_document_idx` (`docId`),
  CONSTRAINT `fk_rank_ref_document` FOREIGN KEY (`docId`) REFERENCES `document` (`docId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rank`
--

LOCK TABLES `rank` WRITE;
/*!40000 ALTER TABLE `rank` DISABLE KEYS */;
/*!40000 ALTER TABLE `rank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rank2`
--

DROP TABLE IF EXISTS `rank2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rank2` (
  `docId` int(11) NOT NULL,
  `page_rank` double NOT NULL,
  PRIMARY KEY (`docId`),
  CONSTRAINT `fk_rank2_ref_document2` FOREIGN KEY (`docId`) REFERENCES `document2` (`docId2`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rank2`
--

LOCK TABLES `rank2` WRITE;
/*!40000 ALTER TABLE `rank2` DISABLE KEYS */;
/*!40000 ALTER TABLE `rank2` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=10348 DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=19792 DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=924 DEFAULT CHARSET=utf8;
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

-- Dump completed on 2017-05-04 22:46:49
