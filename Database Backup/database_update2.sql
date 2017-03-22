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
  `word` varchar(255) NOT NULL,
  `position` bigint(8) NOT NULL,
  `key` bigint(8) NOT NULL AUTO_INCREMENT,
  `status` varchar(20) NOT NULL,
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
  `Url` varchar(255) NOT NULL,
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
  `page_content` varchar(5000) NOT NULL,
  `Url` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `downloaded_page`
--

LOCK TABLES `downloaded_page` WRITE;
/*!40000 ALTER TABLE `downloaded_page` DISABLE KEYS */;
INSERT INTO `downloaded_page` VALUES (1,'Hello 2','http://stackoverflow.com/questions'),(2,'Hello','http://stackoverflow.com'),(3,'Hello 2','http://stackoverflow.com/questions'),(4,'Hello','http://stackoverflow.com'),(5,'Hello 2','http://stackoverflow.com/questions'),(6,'Hello','http://stackoverflow.com'),(7,'Hello 2','http://stackoverflow.com/questions'),(8,'Hello','http://stackoverflow.com'),(9,'Hello 2','http://stackoverflow.com/questions'),(10,'Hello','http://stackoverflow.com'),(11,'Hello 2','http://stackoverflow.com/questions'),(12,'Hello','http://stackoverflow.com'),(13,'Hello 2','http://stackoverflow.com/questions'),(14,'Hello','http://stackoverflow.com'),(15,'Hello 2','http://stackoverflow.com/questions'),(16,'Hello','http://stackoverflow.com'),(17,'Hello 2','http://stackoverflow.com/questions'),(18,'Hello','http://stackoverflow.com'),(19,'Hello 2','http://stackoverflow.com/questions'),(20,'Hello','http://stackoverflow.com'),(21,'Hello 2','http://stackoverflow.com/questions'),(22,'Hello','http://stackoverflow.com'),(23,'Hello 2','http://stackoverflow.com/questions'),(24,'Hello','http://stackoverflow.com'),(25,'Hello 2','http://stackoverflow.com/questions'),(26,'Hello','http://stackoverflow.com'),(27,'Hello 2','http://stackoverflow.com/questions'),(28,'Hello','http://stackoverflow.com'),(29,'Hello 2','http://stackoverflow.com/questions'),(30,'Hello','http://stackoverflow.com'),(31,'Hello 2','http://stackoverflow.com/questions'),(32,'Hello','http://stackoverflow.com'),(33,'Hello 2','http://stackoverflow.com/questions'),(34,'Hello','http://stackoverflow.com'),(35,'Hello 2','http://stackoverflow.com/questions'),(36,'Hello','http://stackoverflow.com'),(37,'Hello 2','http://stackoverflow.com/questions'),(38,'Hello','http://stackoverflow.com'),(39,'Hello 2','http://stackoverflow.com/questions'),(40,'Hello','http://stackoverflow.com'),(41,'Hello 2','http://stackoverflow.com/questions'),(42,'Hello','http://stackoverflow.com'),(43,'Hello 2','http://stackoverflow.com/questions'),(44,'Hello','http://stackoverflow.com'),(45,'Hello 2','http://stackoverflow.com/questions'),(46,'Hello','http://stackoverflow.com'),(47,'Hello 2','http://stackoverflow.com/questions'),(48,'Hello','http://stackoverflow.com'),(49,'Hello 2','http://stackoverflow.com/questions'),(50,'Hello','http://stackoverflow.com'),(51,'Hello 2','http://stackoverflow.com/questions'),(52,'Hello','http://stackoverflow.com'),(53,'Hello 2','http://stackoverflow.com/questions'),(54,'Hello','http://stackoverflow.com'),(55,'Hello 2','http://stackoverflow.com/questions'),(56,'Hello','http://stackoverflow.com'),(57,'Hello 2','http://stackoverflow.com/questions'),(58,'Hello','http://stackoverflow.com'),(59,'Hello 2','http://stackoverflow.com/questions'),(60,'Hello','http://stackoverflow.com'),(61,'Hello 2','http://stackoverflow.com/questions'),(62,'Hello','http://stackoverflow.com'),(63,'Hello 2','http://stackoverflow.com/questions'),(64,'Hello','http://stackoverflow.com'),(65,'Hello 2','http://stackoverflow.com/questions'),(66,'Hello','http://stackoverflow.com'),(67,'Hello 2','http://stackoverflow.com/questions'),(68,'Hello','http://stackoverflow.com'),(69,'Hello 2','http://stackoverflow.com/questions'),(70,'Hello','http://stackoverflow.com'),(71,'Hello 2','http://stackoverflow.com/questions'),(72,'Hello','http://stackoverflow.com'),(73,'Hello 2','http://stackoverflow.com/questions'),(74,'Hello','http://stackoverflow.com'),(75,'Hello 2','http://stackoverflow.com/questions'),(76,'Hello','http://stackoverflow.com'),(77,'Hello 2','http://stackoverflow.com/questions'),(78,'Hello','http://stackoverflow.com'),(79,'Hello 2','http://stackoverflow.com/questions'),(80,'Hello','http://stackoverflow.com'),(81,'Hello 2','http://stackoverflow.com/questions'),(82,'Hello','http://stackoverflow.com'),(83,'Hello 2','http://stackoverflow.com/questions'),(84,'Hello','http://stackoverflow.com'),(85,'Hello 2','http://stackoverflow.com/questions'),(86,'Hello','http://stackoverflow.com'),(87,'Hello 2','http://stackoverflow.com/questions'),(88,'Hello','http://stackoverflow.com'),(89,'Hello 2','http://stackoverflow.com/questions'),(90,'Hello','http://stackoverflow.com'),(91,'Hello 2','http://stackoverflow.com/questions'),(92,'Hello','http://stackoverflow.com'),(93,'Hello 2','http://stackoverflow.com/questions'),(94,'Hello','http://stackoverflow.com'),(95,'Hello 2','http://stackoverflow.com/questions'),(96,'Hello','http://stackoverflow.com'),(97,'Hello 2','http://stackoverflow.com/questions'),(98,'Hello','http://stackoverflow.com'),(99,'Hello 2','http://stackoverflow.com/questions'),(100,'Hello','http://stackoverflow.com'),(101,'Hello 2','http://stackoverflow.com/questions'),(102,'Hello','http://stackoverflow.com'),(103,'Hello 2','http://stackoverflow.com/questions'),(104,'Hello','http://stackoverflow.com'),(105,'Hello 2','http://stackoverflow.com/questions'),(106,'Hello','http://stackoverflow.com'),(107,'Hello 2','http://stackoverflow.com/questions'),(108,'Hello','http://stackoverflow.com'),(109,'Hello 2','http://stackoverflow.com/questions'),(110,'Hello','http://stackoverflow.com'),(111,'Hello 2','http://stackoverflow.com/questions'),(112,'Hello','http://stackoverflow.com'),(113,'Hello 2','http://stackoverflow.com/questions'),(114,'Hello','http://stackoverflow.com'),(115,'Hello 2','http://stackoverflow.com/questions'),(116,'Hello','http://stackoverflow.com'),(117,'Hello 2','http://stackoverflow.com/questions'),(118,'Hello','http://stackoverflow.com'),(119,'Hello 2','http://stackoverflow.com/questions'),(120,'Hello','http://stackoverflow.com'),(121,'Hello 2','http://stackoverflow.com/questions'),(122,'Hello','http://stackoverflow.com'),(123,'Hello 2','http://stackoverflow.com/questions'),(124,'Hello','http://stackoverflow.com'),(125,'Hello 2','http://stackoverflow.com/questions'),(126,'Hello','http://stackoverflow.com'),(127,'Hello 2','http://stackoverflow.com/questions'),(128,'Hello','http://stackoverflow.com'),(129,'Hello 2','http://stackoverflow.com/questions'),(130,'Hello','http://stackoverflow.com'),(131,'Hello 2','http://stackoverflow.com/questions'),(132,'Hello','http://stackoverflow.com'),(133,'Hello 2','http://stackoverflow.com/questions'),(134,'Hello','http://stackoverflow.com'),(135,'Hello 2','http://stackoverflow.com/questions'),(136,'Hello','http://stackoverflow.com'),(137,'Hello 2','http://stackoverflow.com/questions'),(138,'Hello','http://stackoverflow.com'),(139,'Hello 2','http://stackoverflow.com/questions'),(140,'Hello','http://stackoverflow.com'),(141,'Hello 2','http://stackoverflow.com/questions'),(142,'Hello','http://stackoverflow.com'),(143,'Hello 2','http://stackoverflow.com/questions'),(144,'Hello','http://stackoverflow.com'),(145,'Hello 2','http://stackoverflow.com/questions'),(146,'Hello','http://stackoverflow.com'),(147,'Hello 2','http://stackoverflow.com/questions'),(148,'Hello','http://stackoverflow.com'),(149,'Hello 2','http://stackoverflow.com/questions'),(150,'Hello','http://stackoverflow.com'),(151,'Hello 2','http://stackoverflow.com/questions'),(152,'Hello','http://stackoverflow.com');
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
  `host` varchar(255) NOT NULL,
  `robot_handler` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `host_robot_handler`
--

LOCK TABLES `host_robot_handler` WRITE;
/*!40000 ALTER TABLE `host_robot_handler` DISABLE KEYS */;
INSERT INTO `host_robot_handler` VALUES (1,'Google','handlergoogle'),(2,'facebook','handlerface'),(3,'Google','handlergoogle'),(4,'facebook','handlerface'),(5,'Google','handlergoogle'),(6,'facebook','handlerface'),(7,'Google','handlergoogle'),(8,'facebook','handlerface'),(9,'Google','handlergoogle'),(10,'facebook','handlerface'),(11,'Google','handlergoogle'),(12,'facebook','handlerface'),(13,'Google','handlergoogle'),(14,'facebook','handlerface'),(15,'Google','handlergoogle'),(16,'facebook','handlerface'),(17,'Google','handlergoogle'),(18,'facebook','handlerface'),(19,'Google','handlergoogle'),(20,'facebook','handlerface'),(21,'Google','handlergoogle'),(22,'facebook','handlerface'),(23,'Google','handlergoogle'),(24,'facebook','handlerface'),(25,'Google','handlergoogle'),(26,'facebook','handlerface'),(27,'Google','handlergoogle'),(28,'facebook','handlerface'),(29,'Google','handlergoogle'),(30,'facebook','handlerface'),(31,'Google','handlergoogle'),(32,'facebook','handlerface'),(33,'Google','handlergoogle'),(34,'facebook','handlerface'),(35,'Google','handlergoogle'),(36,'facebook','handlerface'),(37,'Google','handlergoogle'),(38,'facebook','handlerface'),(39,'Google','handlergoogle'),(40,'facebook','handlerface'),(41,'Google','handlergoogle'),(42,'facebook','handlerface'),(43,'Google','handlergoogle'),(44,'facebook','handlerface'),(45,'Google','handlergoogle'),(46,'facebook','handlerface'),(47,'Google','handlergoogle'),(48,'facebook','handlerface'),(49,'Google','handlergoogle'),(50,'facebook','handlerface'),(51,'Google','handlergoogle'),(52,'facebook','handlerface'),(53,'Google','handlergoogle'),(54,'facebook','handlerface'),(55,'Google','handlergoogle'),(56,'facebook','handlerface'),(57,'Google','handlergoogle'),(58,'facebook','handlerface'),(59,'Google','handlergoogle'),(60,'facebook','handlerface'),(61,'Google','handlergoogle'),(62,'facebook','handlerface'),(63,'Google','handlergoogle'),(64,'facebook','handlerface'),(65,'Google','handlergoogle'),(66,'facebook','handlerface'),(67,'Google','handlergoogle'),(68,'facebook','handlerface'),(69,'Google','handlergoogle'),(70,'facebook','handlerface'),(71,'Google','handlergoogle'),(72,'facebook','handlerface'),(73,'Google','handlergoogle'),(74,'facebook','handlerface'),(75,'Google','handlergoogle'),(76,'facebook','handlerface'),(77,'Google','handlergoogle'),(78,'facebook','handlerface'),(79,'Google','handlergoogle'),(80,'facebook','handlerface'),(81,'Google','handlergoogle'),(82,'facebook','handlerface'),(83,'Google','handlergoogle'),(84,'facebook','handlerface'),(85,'Google','handlergoogle'),(86,'facebook','handlerface');
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
  `host` varchar(255) NOT NULL,
  `crawl_delay` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `robot_handler_1`
--

LOCK TABLES `robot_handler_1` WRITE;
/*!40000 ALTER TABLE `robot_handler_1` DISABLE KEYS */;
INSERT INTO `robot_handler_1` VALUES (1,'Facebook',2),(2,'Facebook',2),(3,'Google',3);
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
  `host` varchar(255) NOT NULL,
  `url_disallowed` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `host_idx` (`host`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `robot_handler_2`
--

LOCK TABLES `robot_handler_2` WRITE;
/*!40000 ALTER TABLE `robot_handler_2` DISABLE KEYS */;
INSERT INTO `robot_handler_2` VALUES (1,'Facebook','http://stackoverflow.com'),(2,'stackoverflow','http://www.stackoverflow.com'),(3,'stackoverflow','http://www.stackoverflow.comaaaaaaa');
/*!40000 ALTER TABLE `robot_handler_2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `to_visit`
--

DROP TABLE IF EXISTS `to_visit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `to_visit` (
  `doc_url` varchar(255) NOT NULL,
  `docID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`docID`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `to_visit`
--

LOCK TABLES `to_visit` WRITE;
/*!40000 ALTER TABLE `to_visit` DISABLE KEYS */;
INSERT INTO `to_visit` VALUES ('http://stackoverflow.com',53),('http://www.stackoverflow.comaaaaaaa',54);
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
  `Url` varchar(255) NOT NULL,
  PRIMARY KEY (`Url_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visited`
--

LOCK TABLES `visited` WRITE;
/*!40000 ALTER TABLE `visited` DISABLE KEYS */;
INSERT INTO `visited` VALUES (1,'http://www.stackoverflow.com'),(2,'http://www.stackoverflow.comaaa'),(87,'http://stackoverflow.comssss'),(88,'http://stackoverflow.comssss'),(89,'http://stackoverflow.comssss');
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

-- Dump completed on 2017-03-23  1:23:31
