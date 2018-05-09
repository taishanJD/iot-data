CREATE DATABASE  IF NOT EXISTS `one_data` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `one_data`;
-- MySQL dump 10.13  Distrib 5.5.58, for debian-linux-gnu (x86_64)
--
-- Host: 192.168.204.26    Database: one_data
-- ------------------------------------------------------
-- Server version	5.6.21

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
-- Table structure for table `dataset`
--

DROP TABLE IF EXISTS `dataset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dataset` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `datasource_id` bigint(20) NOT NULL COMMENT '数据源id',
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `dataset_name` varchar(50) NOT NULL COMMENT '数据集名称',
  `table_name` varchar(100) NOT NULL COMMENT '对应数据源表名（mysql table、MongoDB collection等）',
  `description` longtext COMMENT '描述',
  `is_write` char(1) NOT NULL COMMENT '是否可写（0：否、1：是）',
  `is_sync` char(1) NOT NULL DEFAULT '0' COMMENT '是否需要同步数据（0：否、1：是），默认0',
  `is_float_to_int` char(1) NOT NULL DEFAULT '0' COMMENT '是否浮点型转整型（0：否、1：是），默认0',
  `sample_type` char(1) NOT NULL DEFAULT '0' COMMENT '采样方式（0：前n条记录、1：随机n条记录（按个数）、2：随机n条记录（按比例））默认0',
  `sample_type_value` int(11) NOT NULL DEFAULT '10000' COMMENT '采样方式对应值（采样个数、采样比例）默认采样个数10000',
  `is_sample_filter` char(1) NOT NULL DEFAULT '0' COMMENT '是否采样过滤（0：否、1：是）默认0',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人id',
  `update_user` bigint(20) NOT NULL COMMENT '更新人id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_datasource_name` (`datasource_id`,`dataset_name`) USING BTREE,
  UNIQUE KEY `uni_project_name` (`project_id`,`dataset_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据集';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dataset`
--

LOCK TABLES `dataset` WRITE;
/*!40000 ALTER TABLE `dataset` DISABLE KEYS */;
/*!40000 ALTER TABLE `dataset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dataset_schema`
--

DROP TABLE IF EXISTS `dataset_schema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dataset_schema` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `dataset_id` bigint(20) NOT NULL COMMENT '数据集id',
  `column_name` varchar(50) NOT NULL COMMENT '列名',
  `column_type` char(2) NOT NULL COMMENT '列类型（）',
  `column_length` int(10) DEFAULT NULL COMMENT '列长度',
  `column_comment` varchar(200) DEFAULT NULL COMMENT '列描述',
  `meaning` char(2) NOT NULL COMMENT '列语义',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_dataset_column` (`dataset_id`,`column_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据集结构';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dataset_schema`
--

LOCK TABLES `dataset_schema` WRITE;
/*!40000 ALTER TABLE `dataset_schema` DISABLE KEYS */;
/*!40000 ALTER TABLE `dataset_schema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `datasource`
--

DROP TABLE IF EXISTS `datasource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `datasource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户id',
  `datasource_type` char(1) NOT NULL COMMENT '数据源类型（0：SQL、1：NoSQL、2：Hadoop、3：Files、4：Cloud）',
  `data_type` char(2) NOT NULL COMMENT '数据类型（0：MySQL、1：Oracle、2：PostgreSQL、3：MS SQL Server、4：HANA、5：MongoDB、6：Cassandra、7：ElasticSearch、8：Hive、9：Hbase、10：HDFS、11：HTTP、12：FTP、13：SFTP）',
  `conn_name` varchar(50) NOT NULL COMMENT '连接名称',
  `host` varchar(200) NOT NULL COMMENT '服务器或集群配置',
  `port` int(10) NOT NULL COMMENT '端口',
  `db` varchar(20) NOT NULL COMMENT '数据库',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `is_write` char(1) NOT NULL COMMENT '是否允许对此连接的数据集进行写操作（0：否、1：是）',
  `is_create_dataset` char(1) NOT NULL COMMENT '是否允许在此连接中创建新的数据集（0：否、1：是）\n\n允许在此连接中创建新的数据集\n是否允许在此连接中创建新的数据集（0：否、1：是）',
  `is_all_user_create_dataset` char(1) NOT NULL COMMENT '是否允许所有用户使用此连接创建新的数据集（0：否、1：是）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人id',
  `update_user` bigint(20) NOT NULL COMMENT '更新人id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_tenant_name` (`tenant_id`,`conn_name`) USING BTREE,
  KEY `nor_tenant_type` (`tenant_id`,`datasource_type`,`data_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据源';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `datasource`
--

LOCK TABLES `datasource` WRITE;
/*!40000 ALTER TABLE `datasource` DISABLE KEYS */;
/*!40000 ALTER TABLE `datasource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键 自增',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户id',
  `project_name` varchar(50) NOT NULL COMMENT '项目名称',
  `project_key` varchar(20) NOT NULL COMMENT '项目标识（用于在项目之间引用数据集，项目一旦创建，标识将不能更改）',
  `project_status` char(1) NOT NULL COMMENT '项目状态（0：沙箱、1：草稿、2：开发中、3：使用中、4：已归档）',
  `project_info` varchar(255) NOT NULL COMMENT '项目简述：项目xxx由xxx于xxxx-xx-xx创建',
  `project_desc` longtext COMMENT '项目描述',
  `project_picture` blob COMMENT '项目图片',
  `is_del` char(1) NOT NULL COMMENT '是否已删除（0：否、1：是）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人id',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_tenant_name` (`tenant_id`,`project_name`) USING BTREE,
  KEY `nor_tenant_user` (`tenant_id`,`create_user`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='项目';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,1,'顺丰','shuynfeng','0','项目 顺丰 由 1 于 Thu May 03 18:24:35 CST 2018 创建',NULL,NULL,'0','2018-05-03 18:24:35',NULL,1,NULL),(3,1,'test','test1','0','跑加个我问问','哇啦哇啦',NULL,'0','2018-05-03 18:26:40','2018-05-07 13:44:06',1,1),(4,1,'test2','test2','0','项目 test2 由 1 于 Thu May 03 18:29:17 CST 2018 创建',NULL,NULL,'0','2018-05-03 18:29:18',NULL,1,NULL),(5,1,'update','TEST3','0','nonoooooo','kkkkkkkk',NULL,'0','2018-05-04 14:05:07','2018-05-09 18:21:54',1,1),(6,1,'test4','TEST4','0','项目 test4 由 1 于 Fri May 04 14:07:35 CST 2018 创建',NULL,NULL,'0','2018-05-04 14:07:35',NULL,1,NULL),(7,1,'test5','TEST5','0','项目 test5 由 1 于 Fri May 04 14:21:34 CST 2018 创建',NULL,NULL,'0','2018-05-04 14:21:34',NULL,1,NULL),(8,1,'test6','TEST6','0','项目 test6 由 1 于 Fri May 04 14:26:47 CST 2018 创建',NULL,NULL,'0','2018-05-04 14:26:48',NULL,1,NULL),(9,1,'test7','TEST7','0','项目 test7 由 1 于 Fri May 04 17:46:19 CST 2018 创建',NULL,'iVBORw0KGgoAAAANSUhEUgAAASwAAACWCAYAAABkW7XSAAAEYklEQVR4Xu3UAQkAAAwCwdm/9HI83BLIOdw5AgQIRAQWySkmAQIEzmB5AgIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlACBB1YxAJfjJb2jAAAAAElFTkSuQmCC','0','2018-05-04 17:46:19',NULL,1,NULL),(10,1,'test8','TEST8','0','项目 test8 由 1 于 Fri May 04 18:08:32 CST 2018 创建',NULL,'iVBORw0KGgoAAAANSUhEUgAAASwAAACWCAYAAABkW7XSAAAEYklEQVR4Xu3UAQkAAAwCwdm/9HI83BLIOdw5AgQIRAQWySkmAQIEzmB5AgIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlACBB1YxAJfjJb2jAAAAAElFTkSuQmCC','0','2018-05-04 18:08:32',NULL,1,NULL),(11,1,'test9','TEST9','0','项目 test9 由 1 于 Fri May 04 18:14:04 CST 2018 创建',NULL,'iVBORw0KGgoAAAANSUhEUgAAASwAAACWCAYAAABkW7XSAAAEYklEQVR4Xu3UAQkAAAwCwdm/9HI83BLIOdw5AgQIRAQWySkmAQIEzmB5AgIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlACBB1YxAJfjJb2jAAAAAElFTkSuQmCC','0','2018-05-04 18:14:04',NULL,1,NULL),(12,1,'test10','TEST10','0','项目 test10 由 1 于 2018-21-04 创建',NULL,'iVBORw0KGgoAAAANSUhEUgAAASwAAACWCAYAAABkW7XSAAAEYklEQVR4Xu3UAQkAAAwCwdm/9HI83BLIOdw5AgQIRAQWySkmAQIEzmB5AgIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlACBB1YxAJfjJb2jAAAAAElFTkSuQmCC','0','2018-05-04 18:21:20',NULL,1,NULL),(13,1,'aaa','TEST','0','项目 aaa 由 1 于 2018-18-07 创建',NULL,'iVBORw0KGgoAAAANSUhEUgAAASwAAACWCAYAAABkW7XSAAAEYklEQVR4Xu3UAQkAAAwCwdm/9HI83BLIOdw5AgQIRAQWySkmAQIEzmB5AgIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlACBB1YxAJfjJb2jAAAAAElFTkSuQmCC','0','2018-05-07 14:18:48',NULL,1,NULL),(15,1,'asg','sTEST','0','项目 asg 由 1 于 2018-21-09 创建',NULL,'iVBORw0KGgoAAAANSUhEUgAAASwAAACWCAYAAABkW7XSAAAEYklEQVR4Xu3UAQkAAAwCwdm/9HI83BLIOdw5AgQIRAQWySkmAQIEzmB5AgIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlAABg+UHCBDICBisTFWCEiBgsPwAAQIZAYOVqUpQAgQMlh8gQCAjYLAyVQlKgIDB8gMECGQEDFamKkEJEDBYfoAAgYyAwcpUJSgBAgbLDxAgkBEwWJmqBCVAwGD5AQIEMgIGK1OVoAQIGCw/QIBARsBgZaoSlACBB1YxAJfjJb2jAAAAAElFTkSuQmCC','0','2018-05-09 18:21:51',NULL,1,NULL);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_comment`
--

DROP TABLE IF EXISTS `project_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `tenant_id` bigint(20) NOT NULL,
  `comment` longtext NOT NULL COMMENT '评论内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` bigint(20) NOT NULL COMMENT '评论人id',
  PRIMARY KEY (`id`),
  KEY `nor_project` (`project_id`) USING BTREE,
  KEY `nor_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='项目评论';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_comment`
--

LOCK TABLES `project_comment` WRITE;
/*!40000 ALTER TABLE `project_comment` DISABLE KEYS */;
INSERT INTO `project_comment` VALUES (1,3,1,'sdagerhgdh','2018-05-08 15:27:13',1),(2,3,1,'asDGvsdgvx','2018-05-09 15:27:20',1),(3,3,1,'SDGVvcb c','2018-05-09 15:27:23',1),(4,3,1,'sasssss','2018-05-09 15:27:26',1),(5,3,1,'ttttbgbvbvbvvbvbv','2018-05-09 15:27:31',1),(7,3,1,'ttttbgbvbvbvvbvbv','2018-05-09 18:31:19',1);
/*!40000 ALTER TABLE `project_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_tag`
--

DROP TABLE IF EXISTS `project_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户id',
  `tag_name` varchar(20) NOT NULL COMMENT '标签名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_tenant_tag` (`tenant_id`,`tag_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目标签';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_tag`
--

LOCK TABLES `project_tag` WRITE;
/*!40000 ALTER TABLE `project_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_tag_rel`
--

DROP TABLE IF EXISTS `project_tag_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_tag_rel` (
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `tag_id` bigint(20) NOT NULL COMMENT '标签id',
  PRIMARY KEY (`project_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目标签关联';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_tag_rel`
--

LOCK TABLES `project_tag_rel` WRITE;
/*!40000 ALTER TABLE `project_tag_rel` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_tag_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_timeline`
--

DROP TABLE IF EXISTS `project_timeline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_timeline` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `tenant_id` bigint(20) NOT NULL,
  `operate_type` char(1) NOT NULL COMMENT '操作类型（0：created 创建、1： edited 编辑、2： deleted 删除、3：execute 执行、4：stop 停止、5：cancel 取消、6：commented 评论）',
  `operate_object_parent_type` char(1) NOT NULL COMMENT '操作对象父类型（0：project 项目、1：workflow 工作流、2：workflowNode 工作流处理节点(不包括dataset节点)、3：dataset 数据集、4：chart 图表、5：dashboard 仪表板、6：comment 评论、7：job 作业、8：task 任务）',
  `operate_object_type` char(1) DEFAULT NULL COMMENT '操作对象子类型（0：同步 、1：预处理、2：分组、3：Cassandra、4：MySQL、5：Hive）',
  `operate_object_id` bigint(20) NOT NULL COMMENT '操作对象id',
  `operate_object_name` varchar(20) DEFAULT NULL COMMENT '名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人id',
  PRIMARY KEY (`id`),
  KEY `nor_project` (`project_id`) USING BTREE,
  KEY `nor_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='项目时间轴';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_timeline`
--

LOCK TABLES `project_timeline` WRITE;
/*!40000 ALTER TABLE `project_timeline` DISABLE KEYS */;
INSERT INTO `project_timeline` VALUES (1,1,0,'0','0',NULL,2,NULL,'2018-05-04 14:26:54',1001),(2,1,0,'0','6',NULL,2,NULL,'2018-05-04 14:45:43',3),(3,1,1,'0','6',NULL,2,NULL,'2018-05-04 14:45:43',3),(4,1,1,'0','6',NULL,2,NULL,'2018-05-04 14:45:43',3),(5,15,1,'0','0',NULL,15,'asg','2018-05-09 18:21:51',1),(6,5,1,'1','0',NULL,5,'update','2018-05-09 18:21:54',1),(7,3,1,'6','0',NULL,3,'test','2018-05-09 18:31:19',1);
/*!40000 ALTER TABLE `project_timeline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scheduler_job`
--

DROP TABLE IF EXISTS `scheduler_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scheduler_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户id',
  `job_name` varchar(50) NOT NULL,
  `job_type` char(1) NOT NULL COMMENT '作业类型（0：周期性、1：手动）',
  `job_biz_type` char(1) NOT NULL COMMENT '作业业务类型（0：工作流、1：脚本、2：其他）',
  `job_biz_id` bigint(20) DEFAULT NULL,
  `is_frozen` char(1) NOT NULL COMMENT '是否冻结（0：否、1：是）',
  `is_notify` char(1) NOT NULL COMMENT '是否通知（0：否、1：是）',
  `is_publish` char(1) NOT NULL COMMENT '是否已发布（0：否、1：是）默认0',
  `interval_type` char(1) DEFAULT NULL COMMENT '调度作业周期类型（0：年、1：月、2：周、3：天、4：小时、5：分钟）',
  `interval_values` varchar(200) DEFAULT NULL COMMENT '调度周期值（年：每年几月几日 例：1-20,2-12,3-2 ；月：每月几日 例：1,8,15 ；周：每周几 例：1,3,5 ；天：每天几时几分 为null ；小时：每间隔几小时 例：2；分钟：每间隔几分钟 例：5）',
  `interval_hour` int(10) DEFAULT NULL COMMENT '具体时间：小时',
  `interval_minute` int(10) DEFAULT NULL COMMENT '具体时间：分钟',
  `valid_startdate` datetime DEFAULT NULL COMMENT '生效开始日期（当天00：00：00）',
  `valid_enddate` datetime DEFAULT NULL COMMENT '生效结束日期（当天23：59：59）',
  `start_hour` int(11) DEFAULT NULL COMMENT '开始时间：时',
  `start_minute` int(11) DEFAULT NULL COMMENT '开始时间：分',
  `end_hour` int(11) DEFAULT NULL COMMENT '结束时间：时',
  `end_minute` int(11) DEFAULT NULL COMMENT '结束时间：分',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人id',
  `update_user` bigint(20) NOT NULL COMMENT '更新人id',
  PRIMARY KEY (`id`),
  KEY `nor_interval_hour_minute` (`interval_type`,`interval_hour`,`interval_minute`) USING BTREE,
  KEY `nor_valid_startdate` (`valid_startdate`) USING BTREE,
  KEY `nor_valid_enddate` (`valid_enddate`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='调度作业';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scheduler_job`
--

LOCK TABLES `scheduler_job` WRITE;
/*!40000 ALTER TABLE `scheduler_job` DISABLE KEYS */;
INSERT INTO `scheduler_job` VALUES (1,1,'dddd','0','0',1,'0','0','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-05-03 11:28:08','2018-05-03 11:28:16',1,1);
/*!40000 ALTER TABLE `scheduler_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scheduler_job_responsible`
--

DROP TABLE IF EXISTS `scheduler_job_responsible`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scheduler_job_responsible` (
  `scheduler_job_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`scheduler_job_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='job责任人表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scheduler_job_responsible`
--

LOCK TABLES `scheduler_job_responsible` WRITE;
/*!40000 ALTER TABLE `scheduler_job_responsible` DISABLE KEYS */;
INSERT INTO `scheduler_job_responsible` VALUES (1,1);
/*!40000 ALTER TABLE `scheduler_job_responsible` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scheduler_notify`
--

DROP TABLE IF EXISTS `scheduler_notify`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scheduler_notify` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `scheduler_job_id` bigint(20) NOT NULL COMMENT '调度作业id',
  `receive_user_id` bigint(20) NOT NULL COMMENT '接收人id',
  `notice_type` char(1) NOT NULL COMMENT '通知类型（0：邮件、1：短信、2：邮件和短信）',
  PRIMARY KEY (`id`),
  KEY `nor_scheduler_job` (`scheduler_job_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='调度通知';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scheduler_notify`
--

LOCK TABLES `scheduler_notify` WRITE;
/*!40000 ALTER TABLE `scheduler_notify` DISABLE KEYS */;
/*!40000 ALTER TABLE `scheduler_notify` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scheduler_task`
--

DROP TABLE IF EXISTS `scheduler_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scheduler_task` (
  `id` bigint(20) NOT NULL COMMENT '主键自增',
  `scheduler_job_id` bigint(20) NOT NULL COMMENT '调度作业id',
  `project_id` bigint(20) NOT NULL,
  `task_status` char(1) NOT NULL COMMENT '调度任务状态（0：待执行、1：已取消、2：执行中、3：已停止、4：执行成功、5：执行失败）',
  `start_time` datetime NOT NULL COMMENT '开始执行时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束执行时间',
  `retry_times` int(11) NOT NULL DEFAULT '0' COMMENT '重试次数（默认最多重试3次）',
  `is_manual` char(1) NOT NULL COMMENT '是否手动执行（0：否、1：是）',
  `biz_date_param` varchar(20) NOT NULL COMMENT '业务日期参数，格式：yyyy-MM-dd ，值为任务执行时间的前一天，执行任务时作为参数传给具体任务去使用',
  `sys_time_param` varchar(20) NOT NULL COMMENT '任务周期运行参数，为开始执行任务的时间，和start_time相同，格式为yyyy-MM-dd hh:mm:ss，执行任务时作为参数传给具体任务去使用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `nor_scheduler_job` (`scheduler_job_id`,`task_status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='调度任务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scheduler_task`
--

LOCK TABLES `scheduler_task` WRITE;
/*!40000 ALTER TABLE `scheduler_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `scheduler_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict`
--

DROP TABLE IF EXISTS `sys_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `value` varchar(100) NOT NULL COMMENT '数据值',
  `label` varchar(100) NOT NULL COMMENT '标签名',
  `type` varchar(100) NOT NULL COMMENT '类型 0:所属行业 1:接入方式 2:接入协议 3:鉴权方式',
  `description` varchar(100) NOT NULL COMMENT '描述',
  `sort` decimal(10,0) NOT NULL COMMENT '排序（升序）',
  `parent_id` varchar(64) DEFAULT '0' COMMENT '父级编号',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记(0:正常;1:已删除)',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`),
  KEY `sys_dict_label` (`label`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict`
--

LOCK TABLES `sys_dict` WRITE;
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workflow`
--

DROP TABLE IF EXISTS `workflow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workflow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `tenant_id` bigint(20) NOT NULL COMMENT '租户id',
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `scheduler_job_id` bigint(20) NOT NULL COMMENT '调度作业id',
  `workflow_name` varchar(100) NOT NULL COMMENT '工作流名称',
  `workflow_desc` longtext COMMENT '工作流描述',
  `is_depend_parent` char(1) NOT NULL DEFAULT '' COMMENT '是否依赖父节点执行状态（0：否、1：是）默认1',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint(20) NOT NULL COMMENT '创建人id',
  `update_user` bigint(20) NOT NULL COMMENT '更新人id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_scheduler` (`scheduler_job_id`) USING BTREE,
  UNIQUE KEY `uni_project_name` (`project_id`,`workflow_name`) USING BTREE,
  KEY `nor_project_time` (`project_id`,`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='工作流';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflow`
--

LOCK TABLES `workflow` WRITE;
/*!40000 ALTER TABLE `workflow` DISABLE KEYS */;
INSERT INTO `workflow` VALUES (1,1,1,1,'锅炉每日最高温度','锅炉每日最高温度desc','','2018-05-03 11:29:50','2018-05-03 11:29:56',1,1);
/*!40000 ALTER TABLE `workflow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workflow_inst`
--

DROP TABLE IF EXISTS `workflow_inst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workflow_inst` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `workflow_id` bigint(20) NOT NULL COMMENT '工作流id',
  `scheduler_task_id` bigint(20) NOT NULL COMMENT '调度任务id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_scheduler_task` (`scheduler_task_id`) USING BTREE,
  KEY `nor_workflow` (`workflow_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作流实例';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflow_inst`
--

LOCK TABLES `workflow_inst` WRITE;
/*!40000 ALTER TABLE `workflow_inst` DISABLE KEYS */;
/*!40000 ALTER TABLE `workflow_inst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workflow_node`
--

DROP TABLE IF EXISTS `workflow_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workflow_node` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `workflow_id` bigint(20) NOT NULL COMMENT '工作流id',
  `node_name` varchar(100) NOT NULL COMMENT '节点名称',
  `node_type` char(1) NOT NULL COMMENT '节点类型（ 0：dataset 数据集节点、1：process 处理节点 ）值为1时，node_process_type、node_process_sub_type才有值',
  `node_process_type` char(1) DEFAULT NULL COMMENT '节点处理类型（0：visual 可视化处理、1：code 编码处理、2：other 其他处理 ）',
  `node_process_sub_type` char(1) DEFAULT NULL COMMENT '节点处理子类型（0：pre 预处理、1：sync 数据同步、2：SQL）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_workflow_name` (`workflow_id`,`node_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作流节点';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflow_node`
--

LOCK TABLES `workflow_node` WRITE;
/*!40000 ALTER TABLE `workflow_node` DISABLE KEYS */;
/*!40000 ALTER TABLE `workflow_node` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workflow_node_inst`
--

DROP TABLE IF EXISTS `workflow_node_inst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workflow_node_inst` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `workflow_inst_id` bigint(20) NOT NULL COMMENT '工作流实例id',
  `workflow_node_id` bigint(20) NOT NULL COMMENT '工作流节点id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `start_time` datetime DEFAULT NULL COMMENT '执行开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '执行结束时间',
  `node_status` char(1) NOT NULL COMMENT '节点执行状态（0：未执行、1：执行中、2：执行成功、3：执行失败）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_workflow_inst_node` (`workflow_inst_id`,`workflow_node_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作流节点实例';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflow_node_inst`
--

LOCK TABLES `workflow_node_inst` WRITE;
/*!40000 ALTER TABLE `workflow_node_inst` DISABLE KEYS */;
/*!40000 ALTER TABLE `workflow_node_inst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workflow_node_rel`
--

DROP TABLE IF EXISTS `workflow_node_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workflow_node_rel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `workflow_id` bigint(20) NOT NULL COMMENT '工作流id',
  `current_node_id` bigint(20) NOT NULL COMMENT '当前节点id',
  `parent_node_id` bigint(20) DEFAULT NULL COMMENT '父节点id（工作流首节点为null）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_current_parent_node` (`current_node_id`,`parent_node_id`) USING BTREE,
  KEY `nor_workflow_current_node` (`workflow_id`,`current_node_id`) USING BTREE,
  KEY `nor_workflow_parent_node` (`workflow_id`,`parent_node_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作流节点关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflow_node_rel`
--

LOCK TABLES `workflow_node_rel` WRITE;
/*!40000 ALTER TABLE `workflow_node_rel` DISABLE KEYS */;
/*!40000 ALTER TABLE `workflow_node_rel` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-09 18:44:27
