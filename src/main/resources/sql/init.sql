create database exercises_recommendation CHARACTER SET utf8 COLLATE utf8_general_ci;;
use exercises_recommendation;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `class`
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cno` int(11) NOT NULL,
  `gid` int(11) NOT NULL,
  `mid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `gid` (`gid`),
  KEY `mid` (`mid`),
  CONSTRAINT `class_ibfk_1` FOREIGN KEY (`gid`) REFERENCES `grade` (`id`),
  CONSTRAINT `class_ibfk_2` FOREIGN KEY (`mid`) REFERENCES `major` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Table structure for `exam`
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `timelimit` int(11) DEFAULT 60,
  `endtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `status` varchar(10) NOT NULL,
  `points` int(11) NOT NULL,
  `singlepoints` int(11) NOT NULL,
  `multipoints` int(11) NOT NULL,
  `judgepoints` int(11) NOT NULL,
  `tid` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tid` (`tid`),
  CONSTRAINT `exam_ibfk_1` FOREIGN KEY (`tid`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `examinationresult`
-- ----------------------------
DROP TABLE IF EXISTS `examinationresult`;
CREATE TABLE `examinationresult` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eid` int(11) NOT NULL,
  `sid` varchar(256) NOT NULL,
  `point` int(11) NOT NULL,
  `time` datetime NOT NULL,
  `examtitle` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `examinationresult_question`
-- ----------------------------
DROP TABLE IF EXISTS `examinationresult_question`;
CREATE TABLE `examinationresult_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `erid` int(11) NOT NULL,
  `qid` int(11) NOT NULL,
  `isright` bit(1) NOT NULL,
  `wronganswer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `exam_class`
-- ----------------------------
DROP TABLE IF EXISTS `exam_class`;
CREATE TABLE `exam_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eid` int(11) NOT NULL,
  `cid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `eid` (`eid`),
  KEY `cid` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `exam_question`
-- ----------------------------
DROP TABLE IF EXISTS `exam_question`;
CREATE TABLE `exam_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eid` int(11) NOT NULL,
  `qid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `eid` (`eid`),
  KEY `qid` (`qid`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `grade`
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `grade` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `major`
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `manager`
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `modified` bit(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', '');

-- ----------------------------
-- Table structure for `question`
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `optiona` varchar(255) DEFAULT NULL,
  `optionb` varchar(255) DEFAULT NULL,
  `optionc` varchar(255) DEFAULT NULL,
  `optiond` varchar(255) DEFAULT NULL,
  `point` int(11) NOT NULL,
  `type` varchar(10) NOT NULL,
  `answer` varchar(255) NOT NULL,
  `tid` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tid` (`tid`),
  CONSTRAINT `question_ibfk_1` FOREIGN KEY (`tid`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `cid` int(11) NOT NULL,
  `modified` bit(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `cid` (`cid`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `class` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL DEFAULT '0',
  `modified` bit(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `teacher_class`
-- ----------------------------
DROP TABLE IF EXISTS `teacher_class`;
CREATE TABLE `teacher_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` varchar(255) NOT NULL,
  `cid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tid` (`tid`),
  KEY `cid` (`cid`),
  CONSTRAINT `teacher_class_ibfk_1` FOREIGN KEY (`tid`) REFERENCES `teacher` (`id`),
  CONSTRAINT `teacher_class_ibfk_2` FOREIGN KEY (`cid`) REFERENCES `class` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
-- ----------------------------
-- Table structure for `knowledge`
-- ----------------------------
DROP TABLE IF EXISTS `knowledge`;
CREATE TABLE `knowledge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `knowledge_name` varchar(255) NOT NULL,
  `pid`  int(11) NOT NULL DEFAULT -1,
  `tid`  varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------
-- Table structure for `student_knowledge`
-- ----------------------------
DROP TABLE IF EXISTS `student_knowledge`;
CREATE TABLE `student_knowledge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kid` int(11) NOT NULL,
  `sid`  varchar(255) NOT NULL,
  `losing_score` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `kid` (`kid`),
  KEY `sid` (`sid`),
  CONSTRAINT `student_knowledge_ibfk_1` FOREIGN KEY (`kid`) REFERENCES `knowledge` (`id`),
  CONSTRAINT `student_knowledge_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `student` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ----------------------------
-- Table structure for `question_knowledge`
-- ----------------------------
DROP TABLE IF EXISTS `question_knowledge`;
CREATE TABLE `question_knowledge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kid` int(11) NOT NULL,
  `qid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `kid` (`kid`),
  KEY `qid` (`qid`),
  CONSTRAINT `question_knowledge_ibfk_1` FOREIGN KEY (`kid`) REFERENCES `knowledge` (`id`),
  CONSTRAINT `question_knowledge_ibfk_2` FOREIGN KEY (`qid`) REFERENCES `question` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
-- ----------------------------
-- Table structure for `student_question`
-- ----------------------------
DROP TABLE IF EXISTS `student_question`;
CREATE TABLE `student_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` varchar (255) NOT NULL,
  `qid` int(11) NOT NULL,
  `total_count` int(11) NOT NULL DEFAULT 0,
  `fail_count` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `sid` (`sid`),
  KEY `qid` (`qid`),
  CONSTRAINT `student_question_ibfk_1` FOREIGN KEY (`sid`) REFERENCES `student` (`id`),
  CONSTRAINT `student_question_ibfk_2` FOREIGN KEY (`qid`) REFERENCES `question` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Procedure structure for `getExamById`
-- ----------------------------
DROP PROCEDURE IF EXISTS `getExamById`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getExamById`(in id int)
begin
declare examid int;
declare expire datetime;
declare loopFlag boolean default false;
declare idCursor cursor for select e.id, e.endtime from exam e where e.id = id and status = 'RUNNING';
declare continue handler for NOT FOUND set loopFlag = true;
open idCursor;
idCursorLoop:loop
fetch idCursor into examid, expire;
if now() > expire then
update exam e set e.status = 'RUNNED' where e.id = examid;
end if;
if loopFlag then
leave idCursorLoop;
end if;
end loop;
select * from exam e where e.id = id;
end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `getExamByStudent`
-- ----------------------------
DROP PROCEDURE IF EXISTS `getExamByStudent`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getExamByStudent`(in sid varchar(20), in pn int, in ps int)
begin
declare start int;
declare examid int;
declare expire datetime;
declare loopFlag boolean default false;
declare pageCursor cursor for select e.id, e.endtime from exam e where e.id in (select eid from exam_class where cid = (select cid from student where id = sid)) and status = 'RUNNING' limit start, ps;
declare continue handler for NOT FOUND set loopFlag = true;
set start = (pn - 1) * ps;
open pageCursor;
pageCursorLoop:loop
fetch pageCursor into examid, expire;
if now() > expire then
update exam e set e.status = 'RUNNED' where e.id = examid;
end if;
if loopFlag then
leave pageCursorLoop;
end if;
end loop;
select e.* from exam e where e.id in (select eid from exam_class where cid = (select cid from student where id = sid)) limit start, ps;
end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `getExamByTeacher`
-- ----------------------------
DROP PROCEDURE IF EXISTS `getExamByTeacher`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getExamByTeacher`(in pn int, in ps int, in tid varchar(20))
begin
declare start int;
declare examid int;
declare expire datetime;
declare loopFlag boolean default false;
declare pageCursor cursor for select e.id, e.endtime from exam e where e.tid = tid and status = 'RUNNING' limit start, ps;
declare continue handler for NOT FOUND set loopFlag = true;
set start = (pn - 1) * ps;
open pageCursor;
set loopFlag = false;
pageCursorLoop:loop
fetch pageCursor into examid, expire;
if now() > expire then
update exam e set e.status = 'RUNNED' where e.id = examid;
end if;
if loopFlag then
leave pageCursorLoop;
end if;
end loop;
select * from exam e where e.tid = tid limit start, ps;
end
;;
DELIMITER ;
