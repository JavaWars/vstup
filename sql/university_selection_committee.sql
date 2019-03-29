/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 80015
Source Host           : 127.0.0.1:3306
Source Database       : university_selection_committee

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-03-30 00:25:25
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
-- Table structure for cities
-- ----------------------------
DROP TABLE IF EXISTS `cities`;
CREATE TABLE `cities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for need_subject_for_department
-- ----------------------------
DROP TABLE IF EXISTS `need_subject_for_department`;
CREATE TABLE `need_subject_for_department` (
  `id_dep` int(11) NOT NULL,
  `id_sub` int(11) NOT NULL,
  `scale` double NOT NULL DEFAULT '1',
  `max_posible_value` double NOT NULL,
  `is_user_entered` varchar(255) DEFAULT NULL,
  KEY `id_dep` (`id_dep`),
  KEY `id_sub` (`id_sub`),
  CONSTRAINT `need_subject_for_department_ibfk_1` FOREIGN KEY (`id_dep`) REFERENCES `departments` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `need_subject_for_department_ibfk_2` FOREIGN KEY (`id_sub`) REFERENCES `subject` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for phase
-- ----------------------------
DROP TABLE IF EXISTS `phase`;
CREATE TABLE `phase` (
  `phase_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `is_current_phase` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`phase_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for query
-- ----------------------------
DROP TABLE IF EXISTS `query`;
CREATE TABLE `query` (
  `query_id` int(11) NOT NULL AUTO_INCREMENT,
  `stud_id` int(11) NOT NULL,
  `subj_id` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`query_id`),
  KEY `q_u` (`stud_id`),
  CONSTRAINT `q_u` FOREIGN KEY (`stud_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

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
-- Table structure for student_department
-- ----------------------------
DROP TABLE IF EXISTS `student_department`;
CREATE TABLE `student_department` (
  `id_student` int(11) NOT NULL,
  `id_department` int(11) NOT NULL,
  `priority` int(11) NOT NULL DEFAULT '0',
  KEY `id_stedent` (`id_student`),
  KEY `id_departmennt` (`id_department`),
  CONSTRAINT `student_department_ibfk_1` FOREIGN KEY (`id_student`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_department_ibfk_2` FOREIGN KEY (`id_department`) REFERENCES `departments` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for student_mark
-- ----------------------------
DROP TABLE IF EXISTS `student_mark`;
CREATE TABLE `student_mark` (
  `id_stud` int(11) NOT NULL,
  `id_subject` int(11) NOT NULL,
  `mark` double NOT NULL,
  `is_confirmed` int(1) DEFAULT '0',
  KEY `id_stud` (`id_stud`),
  KEY `id_subject` (`id_subject`),
  CONSTRAINT `student_mark_ibfk_1` FOREIGN KEY (`id_stud`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_mark_ibfk_2` FOREIGN KEY (`id_subject`) REFERENCES `subject` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for subject
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fio` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `secondName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `id_role` int(11) NOT NULL,
  `id_city` int(11) DEFAULT NULL,
  `area` varchar(50) DEFAULT '',
  `diplom` varchar(10) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `idCity` (`id_city`),
  KEY `id_role` (`id_role`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`id_city`) REFERENCES `cities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `users_ibfk_2` FOREIGN KEY (`id_role`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for user_enter_department
-- ----------------------------
DROP TABLE IF EXISTS `user_enter_department`;
CREATE TABLE `user_enter_department` (
  `user_id` int(11) NOT NULL,
  `departmnet_id` int(11) NOT NULL,
  KEY `ch_dep` (`departmnet_id`),
  KEY `ch_user` (`user_id`),
  CONSTRAINT `ch_dep` FOREIGN KEY (`departmnet_id`) REFERENCES `departments` (`id`),
  CONSTRAINT `ch_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Procedure structure for AllUserMarks
-- ----------------------------
DROP PROCEDURE IF EXISTS `AllUserMarks`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `AllUserMarks`(IN `userId` int)
BEGIN
CREATE TEMPORARY TABLE temp
SELECT `subject`.*
FROM users
INNER JOIN student_department ON student_department.id_student = users.id ,
need_subject_for_department
INNER JOIN `subject` ON need_subject_for_department.id_sub = `subject`.id
WHERE
users.id = userId AND
student_department.id_department = need_subject_for_department.id_dep 
GROUP BY `subject`.id;

SELECT temp.*,case when (SELECT student_mark.mark from student_mark where student_mark.id_stud=userId and student_mark.id_subject=temp.id) is null then 0 else (SELECT student_mark.mark from student_mark where student_mark.id_stud=userId and student_mark.id_subject=temp.id) end as "userMark"
from temp;

DROP TEMPORARY TABLE temp;
END
;;
DELIMITER ;

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
-- Procedure structure for delete_from_query
-- ----------------------------
DROP PROCEDURE IF EXISTS `delete_from_query`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_from_query`(IN `queryId` int)
BEGIN

Update student_mark sm set is_confirmed=1 where id_subject=(
SELECT
sm.id_subject
FROM
`query`
WHERE
sm.id_stud = `query`.stud_id AND
sm.id_subject = `query`.subj_id
and query_id=`queryId`
)
and sm.id_stud=
(SELECT
sm.id_stud
FROM
`query`
WHERE
sm.id_stud = `query`.stud_id AND
sm.id_subject = `query`.subj_id
and query_id=`queryId`);

delete from `query` where `query`.query_id=`queryId`;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for fromAnyTo100
-- ----------------------------
DROP PROCEDURE IF EXISTS `fromAnyTo100`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `fromAnyTo100`(IN `youMark` double,IN `youMaxPossibleMark` double)
BEGIN

DECLARE min_possible DOUBLE default 0; 
DECLARE max_possible DOUBLE default youMaxPossibleMark; 

DECLARE min_possible_in100 DOUBLE default 60; 
DECLARE max_possible_in100 DOUBLE default 100; 

Select (((youMark-max_possible)/(max_possible-min_possible))*(max_possible_in100-min_possible_in100)+max_possible_in100);

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
count(1) as "my position"
FROM student_department std
where std.id_department=dep_id and 

(SELECT SUM(case when (need_subject_for_department.max_posible_value=5) then ( from5to100(student_mark.mark)) else student_mark.mark end)
FROM need_subject_for_department , student_mark
WHERE need_subject_for_department.id_sub = student_mark.id_subject and need_subject_for_department.id_dep=std.id_department  and student_mark.id_stud=stud_id
)
<=
ANY
(SELECT SUM(case when (need_subject_for_department.max_posible_value=5) then ( from5to100(student_mark.mark)) else student_mark.mark end)
FROM need_subject_for_department , student_mark
WHERE need_subject_for_department.id_sub = student_mark.id_subject and need_subject_for_department.id_dep=std.id_department and student_mark.id_stud=std.id_student
);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getUniversityMark
-- ----------------------------
DROP PROCEDURE IF EXISTS `getUniversityMark`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getUniversityMark`()
BEGIN

SELECT
`subject`.`name`,
`subject`.id
FROM `subject`
INNER JOIN need_subject_for_department ON need_subject_for_department.id_sub = `subject`.id
where need_subject_for_department.is_user_entered=0
GROUP BY `subject`.id;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getUserPositionWithoutPeoplePassedDockToOtherDepartment
-- ----------------------------
DROP PROCEDURE IF EXISTS `getUserPositionWithoutPeoplePassedDockToOtherDepartment`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserPositionWithoutPeoplePassedDockToOtherDepartment`(IN `dep_id` int,IN `stud_id` int)
BEGIN
#procedure return -1 if user entered to other department else return number 
SELECT case when ((SELECT ued.user_id FROM user_enter_department ued where ued.user_id=`stud_id`  ) is null or ( select ued.user_id FROM user_enter_department ued where ued.departmnet_id=`dep_id` and ued.user_id=`stud_id`  ) is not null) then 
(SELECT
count(1)
FROM student_department std
where std.id_department=dep_id and 

(SELECT SUM(case when (need_subject_for_department.max_posible_value=5) then ( from5to100(student_mark.mark)) else student_mark.mark end)
FROM need_subject_for_department , student_mark
WHERE need_subject_for_department.id_sub = student_mark.id_subject and need_subject_for_department.id_dep=std.id_department  and student_mark.id_stud=stud_id
)
<=
ANY
(SELECT SUM(case when (need_subject_for_department.max_posible_value=5) then ( from5to100(student_mark.mark)) else student_mark.mark end)
FROM need_subject_for_department , student_mark
WHERE need_subject_for_department.id_sub = student_mark.id_subject and need_subject_for_department.id_dep=std.id_department and student_mark.id_stud=std.id_student
))-1
else -1 end  as 'position';

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
ROW_NUMBER() OVER(ORDER BY(
SELECT SUM(case when (need_subject_for_department.max_posible_value='5') then ( from5to100(student_mark.mark)) else student_mark.mark end)
FROM need_subject_for_department , student_mark
WHERE need_subject_for_department.id_sub = student_mark.id_subject and need_subject_for_department.id_dep=sd.id_department and student_mark.id_stud=sd.id_student
)DESC) as 'position',
(
SELECT SUM(case when (need_subject_for_department.max_posible_value='5') then ( from5to100(student_mark.mark)) else student_mark.mark end)#SUM(student_mark.mark)
FROM need_subject_for_department , student_mark
WHERE need_subject_for_department.id_sub = student_mark.id_subject and need_subject_for_department.id_dep=sd.id_department and student_mark.id_stud=sd.id_student
) as "mark"
FROM student_department sd, users u WHERE sd.id_student=u.id and  sd.id_department=departmnet_id
ORDER BY (
SELECT SUM(case when (need_subject_for_department.max_posible_value='5') then ( from5to100(student_mark.mark)) else student_mark.mark end)
FROM need_subject_for_department , student_mark
WHERE need_subject_for_department.id_sub = student_mark.id_subject and need_subject_for_department.id_dep=sd.id_department and student_mark.id_stud=sd.id_student
) DESC;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getUsersRatingForDepartment
-- ----------------------------
DROP PROCEDURE IF EXISTS `getUsersRatingForDepartment`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getUsersRatingForDepartment`(IN `departmnet_id` int)
BEGIN

SELECT u.`name`, u.secondName,u.id, 
#start number processing
ROW_NUMBER() OVER(ORDER BY(
SELECT SUM(case when (need_subject_for_department.max_posible_value='5') then ( from5to100(student_mark.mark)) else student_mark.mark end)
FROM need_subject_for_department , student_mark
WHERE need_subject_for_department.id_sub = student_mark.id_subject and need_subject_for_department.id_dep=sd.id_department and student_mark.id_stud=sd.id_student
)DESC) as 'position',
#end number processing
(
SELECT SUM(case when (need_subject_for_department.max_posible_value='5') then ( from5to100(student_mark.mark)) else student_mark.mark end)
FROM need_subject_for_department , student_mark
WHERE need_subject_for_department.id_sub = student_mark.id_subject and need_subject_for_department.id_dep=sd.id_department and student_mark.id_stud=sd.id_student
) as "mark"
FROM student_department sd, users u WHERE sd.id_student=u.id and  sd.id_department=departmnet_id
and  ((SELECT user_enter_department.user_id FROM user_enter_department where user_enter_department.user_id=u.id ) is null
or ( select user_enter_department.user_id FROM user_enter_department where user_enter_department.departmnet_id=`departmnet_id` and user_enter_department.user_id=u.id ) is not null)
ORDER BY (
SELECT SUM(case when (need_subject_for_department.max_posible_value='5') then ( from5to100(student_mark.mark)) else student_mark.mark end)
FROM need_subject_for_department , student_mark
WHERE need_subject_for_department.id_sub = student_mark.id_subject and need_subject_for_department.id_dep=sd.id_department and student_mark.id_stud=sd.id_student
 
) DESC;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for getWaitListForDepartment
-- ----------------------------
DROP PROCEDURE IF EXISTS `getWaitListForDepartment`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getWaitListForDepartment`(IN `depName` varchar (255))
BEGIN

SELECT
`query`.query_id,
`query`.date,
users.fio,
users.diplom,
users.id AS 'userId',
student_mark.mark,
`subject`.`name`,
need_subject_for_department.max_posible_value
FROM `query`
INNER JOIN users ON `query`.stud_id = users.id
INNER JOIN student_mark ON student_mark.id_stud = users.id
INNER JOIN `subject` ON student_mark.id_subject = `subject`.id
INNER JOIN need_subject_for_department ON need_subject_for_department.id_sub = `subject`.id
INNER JOIN departments ON need_subject_for_department.id_dep = departments.id
INNER JOIN student_department ON student_department.id_student = users.id AND student_department.id_department = departments.id
where is_confirmed=0 and departments.`name` like CONCAT('%',depName,'%')
GROUP BY `query`.query_id
order by date asc
limit 100;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for insertToCheckingQuery
-- ----------------------------
DROP PROCEDURE IF EXISTS `insertToCheckingQuery`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertToCheckingQuery`(IN `userId` int,IN `markId` int)
BEGIN

delete from `query` where `query`.stud_id=userId and `query`.subj_id=markId;

insert into `query` values (default,userId,markId,NOW());

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for insert_confirmed_user_mark
-- ----------------------------
DROP PROCEDURE IF EXISTS `insert_confirmed_user_mark`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_confirmed_user_mark`(IN `userId` int,IN `subjectId` int,IN `mark` double)
BEGIN

delete from student_mark where student_mark.id_stud=userId and student_mark.id_subject=subjectId;

insert into student_mark VALUES (userId,subjectId,mark,1);

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for invalidateUserMarks
-- ----------------------------
DROP PROCEDURE IF EXISTS `invalidateUserMarks`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `invalidateUserMarks`(IN `userId` int)
BEGIN

#step 1
delete from `query` where `query`.stud_id= `userId` ;

#step 2
update student_mark set is_confirmed=0 where id_stud=userId;

#step 3
insert into `query` (subj_id,stud_id,date)
SELECT
need_subject_for_department.id_sub,
student_department.id_student,
NOW()
FROM
need_subject_for_department ,
student_department
WHERE
need_subject_for_department.id_dep = student_department.id_department
and student_department.id_student=`userId`
and need_subject_for_department.is_user_entered=1
GROUP BY need_subject_for_department.id_sub;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for newPhase
-- ----------------------------
DROP PROCEDURE IF EXISTS `newPhase`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `newPhase`(IN `new_phase_name` varchar(255))
BEGIN

update phase set phase.is_current_phase=0 where phase.`is_current_phase`=1;

update phase set phase.is_current_phase=1 where phase.`name`=new_phase_name;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for SELECT_DEPARTMENT_FOR_USER
-- ----------------------------
DROP PROCEDURE IF EXISTS `SELECT_DEPARTMENT_FOR_USER`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `SELECT_DEPARTMENT_FOR_USER`(IN `userId` int)
BEGIN

SELECT departments.* FROM student_department INNER JOIN departments ON student_department.id_department = departments.id where id_student=`userId` 
ORDER BY student_department.priority;

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

insert into student_department (id_student,id_department,priority) values(stud_id,dep_id,
#return next priority for student
(select case when (SELECT Max(sd.priority) FROM student_department sd where sd.id_student=`stud_id` ) is null then 1 
else (SELECT Max(sd2.priority)+1 FROM student_department sd2 where sd2.id_student=`stud_id` ) end)
#end child select
);

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

insert into student_mark VALUES(student_id,mark_id,markVal,0);

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

-- ----------------------------
-- Function structure for from5to100
-- ----------------------------
DROP FUNCTION IF EXISTS `from5to100`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `from5to100`(`markIn5` double) RETURNS double
    NO SQL
BEGIN

return (((`markIn5` -5)/(5-2))*(100-60)+100);

END
;;
DELIMITER ;
