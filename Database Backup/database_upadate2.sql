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
  `key` bigint(8) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`key`),
  KEY `docId_idx` (`ID_doc`),
  CONSTRAINT `docId` FOREIGN KEY (`ID_doc`) REFERENCES `document` (`docId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5391844 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `doc_words2`
--

DROP TABLE IF EXISTS `doc_words2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doc_words2` (
  `ID_doc` int(11) NOT NULL,
  `word` longtext NOT NULL,
  `key` bigint(8) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`key`),
  KEY `docId2_idx` (`ID_doc`),
  CONSTRAINT `docId2` FOREIGN KEY (`ID_doc`) REFERENCES `document2` (`docId2`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=5001 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=11951 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=576219 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=493660 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `titles`
--

DROP TABLE IF EXISTS `titles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `titles` (
  `doc_id` int(11) NOT NULL,
  `title` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`doc_id`),
  CONSTRAINT `something4` FOREIGN KEY (`doc_id`) REFERENCES `document` (`docId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `titles2`
--

DROP TABLE IF EXISTS `titles2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `titles2` (
  `doc_id` int(11) NOT NULL,
  `title` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`doc_id`),
  CONSTRAINT `something3` FOREIGN KEY (`doc_id`) REFERENCES `document2` (`docId2`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `working_db`
--

DROP TABLE IF EXISTS `working_db`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `working_db` (
  `DB` varchar(10) NOT NULL,
  `num` int(11) DEFAULT NULL,
  PRIMARY KEY (`DB`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-13 12:32:21
