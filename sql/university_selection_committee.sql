/*
Navicat MySQL Data Transfer

Source Server         : test_connection
Source Server Version : 50710
Source Host           : localhost:3306
Source Database       : university_selection_committee

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2017-09-21 21:22:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ban
-- ----------------------------
DROP TABLE IF EXISTS `ban`;
CREATE TABLE `ban` (
  `userId` int(11) NOT NULL,
  KEY `userId` (`userId`),
  CONSTRAINT `ban_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ban
-- ----------------------------
INSERT INTO `ban` VALUES ('18');
INSERT INTO `ban` VALUES ('19');

-- ----------------------------
-- Table structure for cities
-- ----------------------------
DROP TABLE IF EXISTS `cities`;
CREATE TABLE `cities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cities
-- ----------------------------
INSERT INTO `cities` VALUES ('1', 'Kharkov');
INSERT INTO `cities` VALUES ('4', 'userCity');
INSERT INTO `cities` VALUES ('5', 'Грозный');
INSERT INTO `cities` VALUES ('6', 'city');
INSERT INTO `cities` VALUES ('7', 'test');
INSERT INTO `cities` VALUES ('8', 't');
INSERT INTO `cities` VALUES ('9', 'Мой');
INSERT INTO `cities` VALUES ('10', 'York');
INSERT INTO `cities` VALUES ('11', 'New-York');
INSERT INTO `cities` VALUES ('15', '');

-- ----------------------------
-- Table structure for departments
-- ----------------------------
DROP TABLE IF EXISTS `departments`;
CREATE TABLE `departments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `place_government` int(11) NOT NULL,
  `place_total` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of departments
-- ----------------------------
INSERT INTO `departments` VALUES ('1', 'КИ', '101', '300');
INSERT INTO `departments` VALUES ('9', 'updated name', '10', '10');
INSERT INTO `departments` VALUES ('10', 'Department 10', '53', '112');
INSERT INTO `departments` VALUES ('11', 'Направление', '20', '101');
INSERT INTO `departments` VALUES ('12', 'JKD', '2', '2');
INSERT INTO `departments` VALUES ('13', 'Poplavsky dep', '100', '99');
INSERT INTO `departments` VALUES ('14', 'Философии', '10', '100');

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `document_path` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file
-- ----------------------------

-- ----------------------------
-- Table structure for need_subject_for_department
-- ----------------------------
DROP TABLE IF EXISTS `need_subject_for_department`;
CREATE TABLE `need_subject_for_department` (
  `id_dep` int(11) NOT NULL,
  `id_sub` int(11) NOT NULL,
  `scale` double NOT NULL,
  KEY `id_dep` (`id_dep`),
  KEY `id_sub` (`id_sub`),
  CONSTRAINT `need_subject_for_department_ibfk_1` FOREIGN KEY (`id_dep`) REFERENCES `departments` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `need_subject_for_department_ibfk_2` FOREIGN KEY (`id_sub`) REFERENCES `subject` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of need_subject_for_department
-- ----------------------------
INSERT INTO `need_subject_for_department` VALUES ('10', '5', '100');
INSERT INTO `need_subject_for_department` VALUES ('9', '4', '1');
INSERT INTO `need_subject_for_department` VALUES ('9', '2', '10');
INSERT INTO `need_subject_for_department` VALUES ('9', '1', '5');
INSERT INTO `need_subject_for_department` VALUES ('11', '6', '10');
INSERT INTO `need_subject_for_department` VALUES ('11', '7', '5');
INSERT INTO `need_subject_for_department` VALUES ('1', '1', '10');
INSERT INTO `need_subject_for_department` VALUES ('12', '8', '2');
INSERT INTO `need_subject_for_department` VALUES ('13', '9', '10');
INSERT INTO `need_subject_for_department` VALUES ('14', '10', '10');

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('1', 'ADMIN');
INSERT INTO `roles` VALUES ('2', 'USER');
INSERT INTO `roles` VALUES ('3', 'SUPERADMIN');

-- ----------------------------
-- Table structure for student_department
-- ----------------------------
DROP TABLE IF EXISTS `student_department`;
CREATE TABLE `student_department` (
  `id_student` int(11) NOT NULL,
  `id_department` int(11) NOT NULL,
  KEY `id_stedent` (`id_student`),
  KEY `id_departmennt` (`id_department`),
  CONSTRAINT `student_department_ibfk_1` FOREIGN KEY (`id_student`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_department_ibfk_2` FOREIGN KEY (`id_department`) REFERENCES `departments` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_department
-- ----------------------------
INSERT INTO `student_department` VALUES ('1', '1');
INSERT INTO `student_department` VALUES ('16', '10');
INSERT INTO `student_department` VALUES ('16', '1');
INSERT INTO `student_department` VALUES ('16', '9');
INSERT INTO `student_department` VALUES ('1', '9');
INSERT INTO `student_department` VALUES ('18', '9');
INSERT INTO `student_department` VALUES ('19', '9');
INSERT INTO `student_department` VALUES ('19', '1');
INSERT INTO `student_department` VALUES ('19', '10');
INSERT INTO `student_department` VALUES ('19', '11');
INSERT INTO `student_department` VALUES ('22', '1');
INSERT INTO `student_department` VALUES ('23', '10');
INSERT INTO `student_department` VALUES ('16', '14');

-- ----------------------------
-- Table structure for student_mark
-- ----------------------------
DROP TABLE IF EXISTS `student_mark`;
CREATE TABLE `student_mark` (
  `id_stud` int(11) NOT NULL,
  `id_subject` int(11) NOT NULL,
  `mark` double NOT NULL,
  KEY `id_stud` (`id_stud`),
  KEY `id_subject` (`id_subject`),
  CONSTRAINT `student_mark_ibfk_1` FOREIGN KEY (`id_stud`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_mark_ibfk_2` FOREIGN KEY (`id_subject`) REFERENCES `subject` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_mark
-- ----------------------------
INSERT INTO `student_mark` VALUES ('16', '5', '3');
INSERT INTO `student_mark` VALUES ('16', '4', '80');
INSERT INTO `student_mark` VALUES ('16', '2', '88');
INSERT INTO `student_mark` VALUES ('1', '4', '10');
INSERT INTO `student_mark` VALUES ('1', '2', '80');
INSERT INTO `student_mark` VALUES ('1', '1', '88');
INSERT INTO `student_mark` VALUES ('18', '4', '100');
INSERT INTO `student_mark` VALUES ('18', '2', '60');
INSERT INTO `student_mark` VALUES ('18', '1', '60');
INSERT INTO `student_mark` VALUES ('19', '4', '1');
INSERT INTO `student_mark` VALUES ('19', '2', '1');
INSERT INTO `student_mark` VALUES ('19', '1', '1');
INSERT INTO `student_mark` VALUES ('19', '5', '2');
INSERT INTO `student_mark` VALUES ('19', '6', '22');
INSERT INTO `student_mark` VALUES ('19', '7', '1');
INSERT INTO `student_mark` VALUES ('22', '1', '10');
INSERT INTO `student_mark` VALUES ('23', '5', '66');
INSERT INTO `student_mark` VALUES ('16', '1', '1');
INSERT INTO `student_mark` VALUES ('16', '10', '66');
INSERT INTO `student_mark` VALUES ('24', '1', '11.1');
INSERT INTO `student_mark` VALUES ('24', '4', '10');

-- ----------------------------
-- Table structure for subject
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES ('1', 'Math');
INSERT INTO `subject` VALUES ('2', 'ZNO');
INSERT INTO `subject` VALUES ('3', 'Programming');
INSERT INTO `subject` VALUES ('4', 'PE');
INSERT INTO `subject` VALUES ('5', 'Kvant Science');
INSERT INTO `subject` VALUES ('6', 'Мова');
INSERT INTO `subject` VALUES ('7', 'Дисциплина');
INSERT INTO `subject` VALUES ('8', 'JK');
INSERT INTO `subject` VALUES ('9', 'Subject');
INSERT INTO `subject` VALUES ('10', 'Философия');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `secondName` varchar(30) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `id_role` int(11) NOT NULL,
  `id_city` int(11) DEFAULT NULL,
  `area` varchar(50) DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`) USING HASH,
  KEY `idCity` (`id_city`),
  KEY `id_role` (`id_role`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`id_city`) REFERENCES `cities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `users_ibfk_2` FOREIGN KEY (`id_role`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'Павел', 'Лазарев', 'email@gmail.com', '1234', '2', '1', 'can be ampty');
INSERT INTO `users` VALUES ('2', 'AdminName', 'AdminSecondName', 'admin@admin.com', 'admin', '1', '1', '');
INSERT INTO `users` VALUES ('16', 'user', 'userNme', 'user@user.com', 'useruser', '2', '4', 'CityArea');
INSERT INTO `users` VALUES ('17', 'Иван', 'Иванов', 'inan1917@gmail.com', '19171917', '2', '5', 'Ленинский');
INSERT INTO `users` VALUES ('18', 'Пасленыш', 'Пасленый', 'Пасленок@gghsd.com', 'asdfasdf', '2', '6', '');
INSERT INTO `users` VALUES ('19', 'Глуповат', 'Очень', 'Ггг@gg.com', 'uuuuuuuu', '2', '9', '');
INSERT INTO `users` VALUES ('22', 'hj', 'hj', 'hj@hj.cjk', 'qweqweqwe', '2', '11', '');
INSERT INTO `users` VALUES ('23', 'ghfj', 'asdf', 'asdf@asdf.asdf', 'asdfasdf', '2', '15', '');
INSERT INTO `users` VALUES ('24', 'user 11', 'userS 11', 'user11@gmail.com', 'useruser', '2', '15', '');

-- ----------------------------
-- Procedure structure for countBallForUserForDepartment
-- ----------------------------
DROP PROCEDURE IF EXISTS `countBallForUserForDepartment`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `countBallForUserForDepartment`(IN `id_student` int,IN `id_dep` int)
BEGIN
SELECT student_mark.id_stud, need_subject_for_department.id_dep, SUM(need_subject_for_department.scale*student_mark.mark)
FROM need_subject_for_department , student_mark
WHERE need_subject_for_department.id_sub = student_mark.id_subject and need_subject_for_department.id_dep=id_dep  and student_mark.id_stud=id_student;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getMyPosition
-- ----------------------------
DROP PROCEDURE IF EXISTS `getMyPosition`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getMyPosition`(IN `dep_id` int,IN `stud_id` int)
BEGIN
SELECT
#(SELECT SUM(need_subject_for_department.scale*student_mark.mark)
#FROM need_subject_for_department , student_mark
#WHERE need_subject_for_department.id_sub = student_mark.id_subject and need_subject_for_department.id_dep=std.id_department  and student_mark.id_stud=std.id_student) as "bigger mark",
count(1) as "my position"
FROM student_department std
where std.id_department=dep_id and 

(SELECT SUM(need_subject_for_department.scale*student_mark.mark)
FROM need_subject_for_department , student_mark
WHERE need_subject_for_department.id_sub = student_mark.id_subject and need_subject_for_department.id_dep=std.id_department  and student_mark.id_stud=stud_id
)
<=
ANY
(SELECT SUM(need_subject_for_department.scale*student_mark.mark)
FROM need_subject_for_department , student_mark
WHERE need_subject_for_department.id_sub = student_mark.id_subject and need_subject_for_department.id_dep=std.id_department and student_mark.id_stud=std.id_student
);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getUsersPassedDockForDep
-- ----------------------------
DROP PROCEDURE IF EXISTS `getUsersPassedDockForDep`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getUsersPassedDockForDep`(IN `departmnet_id` int)
BEGIN
SELECT u.`name`, u.secondName,u.id,
(
SELECT SUM(need_subject_for_department.scale*student_mark.mark)
FROM need_subject_for_department , student_mark
WHERE need_subject_for_department.id_sub = student_mark.id_subject and need_subject_for_department.id_dep=sd.id_department and student_mark.id_stud=sd.id_student
) as "mark"
FROM student_department sd, users u WHERE sd.id_student=u.id and  sd.id_department=departmnet_id
ORDER BY (
SELECT SUM(need_subject_for_department.scale*student_mark.mark)
FROM need_subject_for_department , student_mark
WHERE need_subject_for_department.id_sub = student_mark.id_subject and need_subject_for_department.id_dep=sd.id_department and student_mark.id_stud=sd.id_student
) DESC;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for student_enter
-- ----------------------------
DROP PROCEDURE IF EXISTS `student_enter`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `student_enter`(IN `stud_id` int,IN `dep_id` int)
BEGIN

delete from student_department where id_student=stud_id and id_department=dep_id;

insert into student_department (id_student,id_department) values(stud_id,dep_id);

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for student_mark_enter
-- ----------------------------
DROP PROCEDURE IF EXISTS `student_mark_enter`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `student_mark_enter`(IN `student_id` int,IN `mark_id` int,IN `markVal` double)
BEGIN

delete from student_mark where id_stud=student_id and id_subject=mark_id;

insert into student_mark VALUES(student_id,mark_id,markVal);

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for totalInfo
-- ----------------------------
DROP PROCEDURE IF EXISTS `totalInfo`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `totalInfo`()
BEGIN
SELECT d.id,d.`name`,(SELECT COUNT(1) FROM student_department where student_department.id_department=d.id)as "total info"  FROM departments d;
END
;;
DELIMITER ;
