SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for survey_paper
-- ----------------------------
DROP TABLE IF EXISTS `survey_paper`;
CREATE TABLE `survey_paper` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id 问卷id',
  `title` varchar(128) DEFAULT NULL COMMENT '问卷名称',
  `description` varchar(1500) DEFAULT NULL COMMENT '问卷描述',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '问卷状态 字典 lib_survey_status',
  `question_num` int(3) NOT NULL DEFAULT '1' COMMENT '包含问题数量',
  `record_num` int(3) NOT NULL DEFAULT '0' COMMENT '已录入数量',
  `create_time` varchar(14) DEFAULT NULL COMMENT '创建时间',
  `del_flg` int(1) DEFAULT '0' COMMENT '删除 0：否 1：是',
  PRIMARY KEY (`id`),
  KEY `status` (`status`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for survey_question
-- ----------------------------
DROP TABLE IF EXISTS `survey_question`;
CREATE TABLE `survey_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id 问题id',
  `survey_id` int(11) NOT NULL COMMENT '对应问卷id',
  `title` varchar(128) DEFAULT NULL COMMENT '问题',
  `description` varchar(1500) DEFAULT NULL COMMENT '问题描述',
  `position` int(2) NOT NULL DEFAULT '1' COMMENT '题号',
  `type` int(1) NOT NULL DEFAULT '1' COMMENT '问题类型 1：单选 2：多选  3：其他',
  `option` varchar(512) NOT NULL DEFAULT '[]' COMMENT '问题选项 数组字符串格式 key为下标值',
  `del_flg` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0:未删除 1:已删除',
  PRIMARY KEY (`id`),
  KEY `pid` (`survey_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for survey_record_answer
-- ----------------------------
DROP TABLE IF EXISTS `survey_record_answer`;
CREATE TABLE `survey_record_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id 问卷id',
  `survey_id` int(11) NOT NULL COMMENT '问题id',
  `question_id` int(11) NOT NULL COMMENT '问题id',
  `position` int(2) NOT NULL DEFAULT '1' COMMENT '题号',
  `answer` int(2) NOT NULL COMMENT '答案',
  `create_time` varchar(14) DEFAULT NULL COMMENT '回答时间',
  PRIMARY KEY (`id`),
  KEY `qid` (`question_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;
