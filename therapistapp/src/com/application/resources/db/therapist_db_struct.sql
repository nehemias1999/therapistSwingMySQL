
--
-- Database `therapist_db`
--

CREATE DATABASE IF NOT EXISTS therapist_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE therapist_db;

--
-- Table structure for table `tbl_city`
--

DROP TABLE IF EXISTS `tbl_city`;
CREATE TABLE `tbl_city` (
  `city_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT (uuid()),
  `city_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `city_zip_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_active` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `tbl_city`
--

LOCK TABLES `tbl_city` WRITE;
INSERT INTO `tbl_city` VALUES ('0036699d-5ca1-4ad7-b001-20edb82b70c7','pilar','1027',1),('11bc59a0-c215-4064-b12f-29c16ef5c4a4','san miguel','1047',1),('d30929a8-2e52-4ce4-bf66-50430b0ce507','lujan','1023',1),('f09146fe-0dc5-430a-8a7a-efef8096092d','moreno','1025',1);
UNLOCK TABLES;

--
-- Table structure for table `tbl_consultation`
--

DROP TABLE IF EXISTS `tbl_consultation`;
CREATE TABLE `tbl_consultation` (
  `consultation_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT (uuid()),
  `consultation_date` date NOT NULL,
  `consultation_start_time` time NOT NULL,
  `consultation_end_time` time NOT NULL,
  `consultation_amount` decimal(10,2) NOT NULL DEFAULT '0.00',
  `consultation_status` enum('SCHEDULED','COMPLETED','CANCELLED') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'SCHEDULED',
  `is_active` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`consultation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Table structure for table `tbl_patient`
--

DROP TABLE IF EXISTS `tbl_patient`;
CREATE TABLE `tbl_patient` (
  `patient_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT (uuid()),
  `patient_dni` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `patient_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `patient_last_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `patient_birth_date` date NOT NULL,
  `patient_occupation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `patient_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `patient_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `city_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `patient_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `patient_address_number` int NOT NULL,
  `patient_address_floor` smallint DEFAULT NULL,
  `patient_address_department` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_active` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`patient_id`),
  UNIQUE KEY `uk_patient_dni` (`patient_dni`),
  UNIQUE KEY `uk_patient_email` (`patient_email`),
  KEY `city_id` (`city_id`),
  CONSTRAINT `tbl_patient_ibfk_1` FOREIGN KEY (`city_id`) REFERENCES `tbl_city` (`city_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Table structure for table `tbl_consultation_patient`
--

DROP TABLE IF EXISTS `tbl_consultation_patient`;
CREATE TABLE `tbl_consultation_patient` (
  `consultation_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `patient_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_paid` tinyint(1) NOT NULL DEFAULT '0',
  `is_active` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`consultation_id`,`patient_id`),
  KEY `patient_id` (`patient_id`),
  CONSTRAINT `tbl_consultation_patient_ibfk_1` FOREIGN KEY (`consultation_id`) REFERENCES `tbl_consultation` (`consultation_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tbl_consultation_patient_ibfk_2` FOREIGN KEY (`patient_id`) REFERENCES `tbl_patient` (`patient_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;