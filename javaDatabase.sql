-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: cw1_database
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `productID` varchar(100) DEFAULT NULL,
  `productname` varchar(100) DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  `stock` int DEFAULT NULL,
  `price` double DEFAULT NULL,
  `image` varchar(10000) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (20,'1','Burger','Meals',19,12.9,'C:\\\\Users\\\\User\\\\IdeaProjects\\\\cw1\\\\src\\\\main\\\\resources\\\\images\\\\burger.jpg','2025-04-28'),(21,'2','sprite','Drinks',21,3,'C:\\\\Users\\\\User\\\\IdeaProjects\\\\cw1\\\\src\\\\main\\\\resources\\\\images\\\\sprite.jpg','2025-04-28'),(22,'3','fried rice','Meals',15,12,'C:\\\\Users\\\\User\\\\IdeaProjects\\\\cw1\\\\src\\\\main\\\\resources\\\\images\\\\friedRice.jpg','2025-04-28');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int DEFAULT NULL,
  `prod_id` varchar(45) DEFAULT NULL,
  `prod_name` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `price` double DEFAULT NULL,
  `date` date DEFAULT NULL,
  `image` varchar(10000) DEFAULT NULL,
  `em_username` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase`
--

LOCK TABLES `purchase` WRITE;
/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
INSERT INTO `purchase` VALUES (61,1,'1','Burger','Meals',2,25.8,'2025-04-28','C:\\\\Users\\\\User\\\\IdeaProjects\\\\cw1\\\\src\\\\main\\\\resources\\\\images\\\\burger.jpg','chris');
/*!40000 ALTER TABLE `purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipt`
--

DROP TABLE IF EXISTS `receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipt` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int DEFAULT NULL,
  `total` double DEFAULT NULL,
  `date` date DEFAULT NULL,
  `em_username` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt`
--

LOCK TABLES `receipt` WRITE;
/*!40000 ALTER TABLE `receipt` DISABLE KEYS */;
INSERT INTO `receipt` VALUES (1,1,70,'2025-04-28','Chris'),(2,2,246,'2025-04-28','chris'),(3,3,22,'2025-04-28','Chris'),(4,4,34,'2025-04-28','chris'),(5,3,46,'2025-04-28','chris'),(6,2,246,'2025-04-28','Chris'),(7,1,13,'2025-04-28','chris'),(8,1,13,'2025-04-28','chris'),(9,1,28,'2025-04-28','chris'),(10,1,28,'2025-04-28','chris'),(11,1,30,'2025-04-28','chris'),(12,1,52,'2025-04-28','chris'),(13,1,26,'2025-04-28','Chris'),(14,1,15,'2025-04-28','chris'),(15,1,13,'2025-04-28','chris'),(16,1,11,'2025-04-28','chris'),(17,1,2,'2025-04-28','chris'),(18,1,13,'2025-04-28','chris'),(19,1,13,'2025-04-28','chris'),(20,1,2,'2025-04-28','chris'),(21,1,10,'2025-04-28','Chris'),(22,1,10,'2025-04-28','Chris'),(23,1,60,'2025-04-28','chris'),(24,1,40.8,'2025-04-28','Chris'),(25,1,15,'2025-04-28','Chris'),(26,1,70.8,'2025-04-28','chris');
/*!40000 ALTER TABLE `receipt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipt_items`
--

DROP TABLE IF EXISTS `receipt_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipt_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `receipt_id` int NOT NULL,
  `prod_id` varchar(50) NOT NULL,
  `prod_name` varchar(100) NOT NULL,
  `type` varchar(50) DEFAULT NULL,
  `quantity` int NOT NULL,
  `price` double NOT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `receipt_id` (`receipt_id`),
  CONSTRAINT `receipt_items_ibfk_1` FOREIGN KEY (`receipt_id`) REFERENCES `receipt` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt_items`
--

LOCK TABLES `receipt_items` WRITE;
/*!40000 ALTER TABLE `receipt_items` DISABLE KEYS */;
INSERT INTO `receipt_items` VALUES (1,13,'11','pizza','Meals',2,22,'2025-04-28'),(2,13,'20','choc','Meals',2,4,'2025-04-28'),(3,14,'11','pizza','Meals',1,11,'2025-04-28'),(4,14,'20','choc','Meals',2,4,'2025-04-28'),(5,15,'11','pizza','Meals',1,11,'2025-04-28'),(6,15,'20','choc','Meals',1,2,'2025-04-28'),(7,16,'11','pizza','Meals',1,11,'2025-04-28'),(8,17,'20','choc','Meals',1,2,'2025-04-28'),(9,18,'11','pizza','Meals',1,11,'2025-04-28'),(10,18,'20','choc','Meals',1,2,'2025-04-28'),(11,19,'11','pizza','Meals',1,11,'2025-04-28'),(12,19,'20','choc','Meals',1,2,'2025-04-28'),(13,20,'20','choc','Meals',1,2,'2025-04-28'),(14,21,'11','pizza','Meals',1,10,'2025-04-28'),(15,22,'11','pizza','Meals',1,10,'2025-04-28'),(16,23,'12','burger','Meals',5,60,'2025-04-28'),(17,24,'1','Burger','Meals',2,25.8,'2025-04-28'),(18,24,'2','sprite','Drinks',1,3,'2025-04-28'),(19,24,'3','fried rice','Meals',1,12,'2025-04-28'),(20,25,'2','sprite','Drinks',5,15,'2025-04-28'),(21,26,'1','Burger','Meals',2,25.8,'2025-04-28'),(22,26,'2','sprite','Drinks',3,9,'2025-04-28'),(23,26,'3','fried rice','Meals',3,36,'2025-04-28');
/*!40000 ALTER TABLE `receipt_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_acc`
--

DROP TABLE IF EXISTS `user_acc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_acc` (
  `acc_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(100) NOT NULL,
  PRIMARY KEY (`acc_id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `acc_id_UNIQUE` (`acc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_acc`
--

LOCK TABLES `user_acc` WRITE;
/*!40000 ALTER TABLE `user_acc` DISABLE KEYS */;
INSERT INTO `user_acc` VALUES (1,'Adam','1235','Admin'),(6,'Lush','123','Admin'),(7,'Chris','111','User'),(9,'lee','1123','User'),(10,'jacky','jack11','Admin'),(11,'alexa','12344','Admin'),(12,'jucks','12333','User');
/*!40000 ALTER TABLE `user_acc` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-28 12:52:46
