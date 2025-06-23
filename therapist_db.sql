CREATE DATABASE  IF NOT EXISTS `therapist_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `therapist_db`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: therapist_db
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Dumping data for table `tbl_city`
--

LOCK TABLES `tbl_city` WRITE;
/*!40000 ALTER TABLE `tbl_city` DISABLE KEYS */;
INSERT INTO `tbl_city` VALUES ('6172c6f4-f7b0-4f8a-96ab-2d8bacb06f08','general rodrigues','1748',1),('7f0082b5-ba80-4ff0-8f85-4942ec6391b2','pilar','9000',1),('ac5ea49c-6446-4929-9dcb-220bee1119c4','tandil','7000',1),('b4ba6484-157e-4304-9689-e9de3793dca6','olavarria','8000',1);
/*!40000 ALTER TABLE `tbl_city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tbl_consultation`
--

LOCK TABLES `tbl_consultation` WRITE;
/*!40000 ALTER TABLE `tbl_consultation` DISABLE KEYS */;
INSERT INTO `tbl_consultation` VALUES ('195d7293-9f95-41ad-99a9-11e0c2c75d56','2025-07-04','09:00:00','10:00:00',10000.00,'SCHEDULED',1),('21f40969-9701-475a-8abd-9e1ef6968f56','2025-07-03','08:00:00','09:00:00',10000.00,'SCHEDULED',1);
/*!40000 ALTER TABLE `tbl_consultation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tbl_consultation_patient`
--

LOCK TABLES `tbl_consultation_patient` WRITE;
/*!40000 ALTER TABLE `tbl_consultation_patient` DISABLE KEYS */;
INSERT INTO `tbl_consultation_patient` VALUES ('195d7293-9f95-41ad-99a9-11e0c2c75d56','3f12fbbc-66b9-4274-80d7-53dfe98c1844',0,1),('195d7293-9f95-41ad-99a9-11e0c2c75d56','5fc28139-bf0e-41b8-87ca-5013133d4398',0,0),('195d7293-9f95-41ad-99a9-11e0c2c75d56','c2d92a63-a07f-47b5-8995-bf582773d97b',0,1),('21f40969-9701-475a-8abd-9e1ef6968f56','5ee946a2-8cca-4afd-96eb-2a35e429d8e5',0,1);
/*!40000 ALTER TABLE `tbl_consultation_patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tbl_patient`
--

LOCK TABLES `tbl_patient` WRITE;
/*!40000 ALTER TABLE `tbl_patient` DISABLE KEYS */;
INSERT INTO `tbl_patient` VALUES ('1064700c-8e87-4b50-89fe-822ac77c131d','12345600','juan martin','gutierrez','1990-08-22','desempleado','11123456','jgutierrez@gmail.com','b4ba6484-157e-4304-9689-e9de3793dca6','alem',2324,0,'',1),('2063af17-fb8b-4bad-8cf1-14fa784273e2','12345678','maria antonela','garcia','1988-04-23','maestra','11123456','mgarcia@gmail.com','7f0082b5-ba80-4ff0-8f85-4942ec6391b2','mitre',25,0,'',1),('3f12fbbc-66b9-4274-80d7-53dfe98c1844','12345877','federico','martinez','1994-08-17','maestro','11123456','fmartinez@gmail.com','7f0082b5-ba80-4ff0-8f85-4942ec6391b2','moreno',6583,0,'',1),('5ee946a2-8cca-4afd-96eb-2a35e429d8e5','12345604','martina alejandra','gonzalez','2006-05-27','estudiante','11123456','mgonzales@gmail.com','b4ba6484-157e-4304-9689-e9de3793dca6','pinto',24,0,'',1),('5fc28139-bf0e-41b8-87ca-5013133d4398','12345876','pedro','sancez','1985-02-06','periodista','11123456','psanchez@gmail.com','6172c6f4-f7b0-4f8a-96ab-2d8bacb06f08','mitre',45,0,'',1),('6214c951-909d-41fd-8356-62354583bf07','87654321','franco matias','martinez','1986-09-18','cantante','11123456','fmmartinez@gmail.com','b4ba6484-157e-4304-9689-e9de3793dca6','pinto',486,0,'',1),('7b8372c2-0656-49f5-aa52-74cde738952b','12345679','federico marcos','martinez','1998-06-27','contador','11123456','fgarcia@gmail.com','7f0082b5-ba80-4ff0-8f85-4942ec6391b2','saavedra',2456,0,'',1),('87c660ca-ed31-4682-9eba-d25003ae5b3c','12345603','martin gonzalo','almeida','1986-09-26','profesor de educadion fisica','11123456','jalmeida@gmail.com','7f0082b5-ba80-4ff0-8f85-4942ec6391b2','alberdi',54,0,'',1),('bca53da8-4e50-41dc-ab77-b73ca61fcc4e','12345602','florencia alejandra','tomazzi','1989-07-25','arquitecta','11123456','ftomazzi@gmail.com','ac5ea49c-6446-4929-9dcb-220bee1119c4','pinto',34,0,'',1),('c2d92a63-a07f-47b5-8995-bf582773d97b','20044031','gustavo cesar','salazar','1968-01-07','relacion de dependencia','2494589726','gsalazar@gmial.com','ac5ea49c-6446-4929-9dcb-220bee1119c4','moreno',851,0,'',1),('ca23bc0c-b62b-4e67-aa75-3446a62a4d7e','41545496','nehemias','salazar','1999-01-21','desarrollador','2494555928','nsalazar@gmail.com','6172c6f4-f7b0-4f8a-96ab-2d8bacb06f08','cangallo',564,0,'',1);
/*!40000 ALTER TABLE `tbl_patient` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-20 13:27:55
