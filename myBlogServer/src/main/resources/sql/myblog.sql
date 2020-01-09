/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.6.44 : Database - myblog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`myblog` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `myblog`;

/*Table structure for table `content_article` */

DROP TABLE IF EXISTS `content_article`;

CREATE TABLE `content_article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(64) DEFAULT NULL,
  `user_img_url` varchar(128) DEFAULT NULL,
  `content` longtext COMMENT '最终展示的内容,html格式的',
  `content_markdown` longtext,
  `title` varchar(128) DEFAULT NULL,
  `brief` varchar(256) DEFAULT NULL,
  `content_img_url` varchar(128) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `views` bigint(20) DEFAULT NULL,
  `zans` bigint(20) DEFAULT NULL,
  `stores` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `content_article` */

/*Table structure for table `content_base` */

DROP TABLE IF EXISTS `content_base`;

CREATE TABLE `content_base` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建或更新时间',
  `title` varchar(128) DEFAULT NULL,
  `brief` varchar(256) DEFAULT NULL,
  `views` bigint(20) DEFAULT NULL COMMENT '浏览数',
  `zans` bigint(20) DEFAULT NULL COMMENT '点赞数',
  `content_img_url` varchar(128) DEFAULT NULL COMMENT '图片地址',
  `content_category_id` bigint(20) DEFAULT NULL COMMENT '内容分类id',
  `content_category_name` varchar(64) DEFAULT NULL COMMENT '内容分类名称',
  `user_img_url` varchar(128) DEFAULT NULL,
  `stores` bigint(20) DEFAULT NULL COMMENT '收藏数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `content_base` */

/*Table structure for table `content_poem_poetry` */

DROP TABLE IF EXISTS `content_poem_poetry`;

CREATE TABLE `content_poem_poetry` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `user_img_url` varchar(128) DEFAULT NULL,
  `content` longtext,
  `content_complex` longtext,
  `title` varchar(128) DEFAULT NULL,
  `brief` varchar(256) DEFAULT NULL,
  `content_img_url` varchar(128) DEFAULT NULL,
  `views` bigint(20) DEFAULT NULL,
  `stores` bigint(20) DEFAULT NULL,
  `zans` bigint(20) DEFAULT NULL,
  `explanation` longtext COMMENT '解析',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `content_poem_poetry` */

/*Table structure for table `dict_category` */

DROP TABLE IF EXISTS `dict_category`;

CREATE TABLE `dict_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(64) DEFAULT NULL,
  `category_img_url` varchar(128) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `dict_category` */

/*Table structure for table `dict_tag` */

DROP TABLE IF EXISTS `dict_tag`;

CREATE TABLE `dict_tag` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tag_name` varchar(64) NOT NULL COMMENT '标签名称',
  `tag_img_url` varchar(128) DEFAULT NULL COMMENT '标签路径',
  `user_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `dict_tag` */

/*Table structure for table `sys_group` */

DROP TABLE IF EXISTS `sys_group`;

CREATE TABLE `sys_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '组名称',
  `description` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '描述',
  `is_valid` int(1) DEFAULT '1' COMMENT '是否有效',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(20) DEFAULT NULL COMMENT '更新者id',
  `update_user_name` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新者用户名',
  `create_user_name` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建者用户名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COMMENT='后台分组表';

/*Data for the table `sys_group` */

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `id` int(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `request_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作IP',
  `type` int(3) DEFAULT NULL COMMENT '操作类型 1 操作记录2异常记录',
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作人',
  `description` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '操作描述',
  `action_method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求方法',
  `action_url` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `params` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求参数',
  `ua` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '浏览器',
  `class_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '类路径',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求方法',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  `consuming_time` bigint(11) DEFAULT NULL COMMENT '消耗时间',
  `ex_desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '异常详情信息',
  `ex_detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '异常描述',
  `user_id` bigint(20) DEFAULT NULL COMMENT '操作人id',
  `group_id` bigint(20) DEFAULT NULL COMMENT '组织id',
  `group_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '组织名称',
  PRIMARY KEY (`id`),
  KEY `index_type` (`type`) USING BTREE COMMENT '日志类型'
) ENGINE=InnoDB AUTO_INCREMENT=1200 DEFAULT CHARSET=utf8mb4 COMMENT='系统日志';

/*Data for the table `sys_log` */

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT '自定id,主要供前端展示权限列表分类排序使用.',
  `menu_code` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '归属菜单,前端判断并展示菜单使用,',
  `menu_name` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '菜单的中文释义',
  `permission_code` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '权限的代码/通配符,对应代码中@RequiresPermissions 的value',
  `permission_name` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '本权限的中文释义',
  `required_permission` tinyint(1) DEFAULT '2' COMMENT '是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='后台权限表';

/*Data for the table `sys_permission` */

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '角色名',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_status` varchar(1) CHARACTER SET utf8 DEFAULT '1' COMMENT '是否有效  1有效  2无效',
  `group_id` bigint(20) DEFAULT NULL COMMENT '组织id',
  `group_name` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '组织名称',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `create_user_name` varchar(64) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='后台角色表';

/*Data for the table `sys_role` */

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_status` varchar(1) CHARACTER SET utf8 DEFAULT '1' COMMENT '是否有效 1有效     2无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='角色-权限关联表';

/*Data for the table `sys_role_permission` */

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '昵称',
  `role_id` int(11) DEFAULT '0' COMMENT '角色ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `delete_status` varchar(1) CHARACTER SET utf8 DEFAULT '1' COMMENT '是否有效  1有效  2无效',
  `group_id` bigint(20) DEFAULT NULL COMMENT '所属组织id',
  `group_name` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '所属组织名称',
  `create_user_id` bigint(20) DEFAULT NULL,
  `create_user_name` varchar(64) CHARACTER SET utf8 DEFAULT NULL,
  `update_user_id` bigint(20) DEFAULT NULL,
  `update_user_name` varchar(64) CHARACTER SET utf8 DEFAULT NULL,
  `gender` int(2) DEFAULT NULL COMMENT '1是男0是女',
  `phone_number` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '电话号码',
  `id_card` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '身份证号码',
  `special_area` varchar(128) CHARACTER SET utf8 DEFAULT NULL COMMENT '专业领域',
  `professional_tile` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '职称',
  `qr_code` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '二维码',
  `user_img_url` varchar(128) DEFAULT NULL,
  `description` longtext COMMENT '简介',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10004 DEFAULT CHARSET=utf8mb4 COMMENT='运营后台用户表';

/*Data for the table `sys_user` */

/*Table structure for table `user_content_mapping` */

DROP TABLE IF EXISTS `user_content_mapping`;

CREATE TABLE `user_content_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(64) DEFAULT NULL,
  `content_id` bigint(20) DEFAULT NULL,
  `map_type` int(11) DEFAULT NULL COMMENT '赞、收藏',
  `create_time` datetime DEFAULT NULL,
  `content_category_id` bigint(11) DEFAULT NULL COMMENT '文章、诗词',
  `content_category_name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_content_mapping` */

/*Table structure for table `user_view_log` */

DROP TABLE IF EXISTS `user_view_log`;

CREATE TABLE `user_view_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(64) DEFAULT NULL,
  `content_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `content_category_id` bigint(20) DEFAULT NULL,
  `content_category_name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_view_log` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
