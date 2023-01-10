-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: localhost    Database: ZeppelinUMMartinezMierzwa
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `ZeppelinUMMartinezMierzwa`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `ZeppelinUMMartinezMierzwa` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `ZeppelinUMMartinezMierzwa`;

--
-- Table structure for table `categoriaRestaurante`
--

DROP TABLE IF EXISTS `categoriaRestaurante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoriaRestaurante` (
  `id` int NOT NULL AUTO_INCREMENT,
  `categoria` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoriaRestaurante`
--

LOCK TABLES `categoriaRestaurante` WRITE;
/*!40000 ALTER TABLE `categoriaRestaurante` DISABLE KEYS */;
/*!40000 ALTER TABLE `categoriaRestaurante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incidencia`
--

DROP TABLE IF EXISTS `incidencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `incidencia` (
  `id` int NOT NULL AUTO_INCREMENT,
  `comentario_cierre` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_cierre` date DEFAULT NULL,
  `fecha_creacion` date DEFAULT NULL,
  `restaurante` int DEFAULT NULL,
  `usuario` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_incidencia_restaurante` (`restaurante`),
  KEY `FK_incidencia_usuario` (`usuario`),
  CONSTRAINT `FK_incidencia_restaurante` FOREIGN KEY (`restaurante`) REFERENCES `restaurante` (`id`),
  CONSTRAINT `FK_incidencia_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incidencia`
--

LOCK TABLES `incidencia` WRITE;
/*!40000 ALTER TABLE `incidencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `incidencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plato`
--

DROP TABLE IF EXISTS `plato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plato` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descripcion` longtext,
  `disponibilidad` tinyint(1) DEFAULT '0',
  `precio` double DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `restaurante` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_plato_restaurante` (`restaurante`),
  CONSTRAINT `FK_plato_restaurante` FOREIGN KEY (`restaurante`) REFERENCES `restaurante` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plato`
--

LOCK TABLES `plato` WRITE;
/*!40000 ALTER TABLE `plato` DISABLE KEYS */;
INSERT INTO `plato` VALUES (1,'Yummy Pizza',1,6,'Pizza',1),(2,'Yummy Pizza',1,3,'Pizza with pineapple',1),(3,'Yummy Pizza',0,12,'Pizza with pineapple and ketchup',1);
/*!40000 ALTER TABLE `plato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurante`
--

DROP TABLE IF EXISTS `restaurante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurante` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fecha_alta` date DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `num_penalizaciones` int DEFAULT NULL,
  `num_valoraciones` int DEFAULT NULL,
  `valoracion_global` double DEFAULT NULL,
  `responsable` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_restaurante_responsable` (`responsable`),
  CONSTRAINT `FK_restaurante_responsable` FOREIGN KEY (`responsable`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurante`
--

LOCK TABLES `restaurante` WRITE;
/*!40000 ALTER TABLE `restaurante` DISABLE KEYS */;
INSERT INTO `restaurante` VALUES (1,'2023-01-04','restaurant 1',0,0,0,3),(2,'2023-01-04','Mano a mano no platos',0,0,0,3);
/*!40000 ALTER TABLE `restaurante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurante_categoria`
--

DROP TABLE IF EXISTS `restaurante_categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurante_categoria` (
  `restaurante_id` int NOT NULL,
  `categoria_restaurante_id` int NOT NULL,
  PRIMARY KEY (`restaurante_id`,`categoria_restaurante_id`),
  KEY `FK_restaurante_categoria_categoria_restaurante_id` (`categoria_restaurante_id`),
  CONSTRAINT `FK_restaurante_categoria_categoria_restaurante_id` FOREIGN KEY (`categoria_restaurante_id`) REFERENCES `categoriaRestaurante` (`id`),
  CONSTRAINT `FK_restaurante_categoria_restaurante_id` FOREIGN KEY (`restaurante_id`) REFERENCES `restaurante` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurante_categoria`
--

LOCK TABLES `restaurante_categoria` WRITE;
/*!40000 ALTER TABLE `restaurante_categoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `restaurante_categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(255) DEFAULT NULL,
  `clave` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `validado` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'cliente','cliente','cliente','1998-12-24','cliente','CLIENTE',1),(2,'rider','rider','rider','1998-12-24','rider','RIDER',0),(3,'rest','rest','rest','1998-12-24','rest','RESTAURANTE',1),(4,'admin','admin','admin','1998-12-24','admin','ADMIN',0),(5,'rest1','rest1','rest1','1998-12-24','rest1','RESTAURANTE',0);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-04 19:18:14
