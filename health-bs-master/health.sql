/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : localhost:3306
 Source Schema         : health

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 07/03/2024 23:08:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_ask
-- ----------------------------
DROP TABLE IF EXISTS `sys_ask`;
CREATE TABLE `sys_ask`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `consultation_content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '咨询内容',
  `doctor_id` int(11) NULL DEFAULT NULL COMMENT '医生id',
  `reply_content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回复内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间(咨询时间)',
  `replay_time` datetime NULL DEFAULT NULL COMMENT '回复时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户咨询医生记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_ask
-- ----------------------------
INSERT INTO `sys_ask` VALUES (1, 6, '感冒体温多少度要注意呢？', 5, '37.5', '2024-03-07 15:05:25', '2024-03-07 15:17:27');
INSERT INTO `sys_ask` VALUES (2, 6, '发烧了怎么办好一点？', 5, '多喝热水', '2024-03-07 19:03:47', '2024-03-07 19:05:40');

-- ----------------------------
-- Table structure for sys_calculator
-- ----------------------------
DROP TABLE IF EXISTS `sys_calculator`;
CREATE TABLE `sys_calculator`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `weight` int(11) NULL DEFAULT NULL COMMENT '体重kg',
  `height` int(11) NULL DEFAULT NULL COMMENT '身高cm',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄（周岁）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `body_fat_percent` decimal(10, 2) NULL DEFAULT NULL COMMENT '体脂率',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '体脂率（BFR）计算器' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_calculator
-- ----------------------------
INSERT INTO `sys_calculator` VALUES (1, '男', 60, 172, 24, '2024-03-06 15:17:03', 6, 13.66);
INSERT INTO `sys_calculator` VALUES (2, '男', 60, 172, 24, '2024-03-06 16:22:06', 6, 13.66);
INSERT INTO `sys_calculator` VALUES (3, '男', 60, 172, 24, '2024-03-07 19:00:46', 6, 13.66);

-- ----------------------------
-- Table structure for sys_doctor_suggest
-- ----------------------------
DROP TABLE IF EXISTS `sys_doctor_suggest`;
CREATE TABLE `sys_doctor_suggest`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `doctor_id` int(11) NULL DEFAULT NULL COMMENT '诊断人id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `diagnosis_content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分析建议',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '医生诊断建议' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_doctor_suggest
-- ----------------------------
INSERT INTO `sys_doctor_suggest` VALUES (2, 5, 6, '还可以', '2024-03-07 10:59:02');
INSERT INTO `sys_doctor_suggest` VALUES (3, 5, 6, '很好继续维持', '2024-03-07 19:05:08');

-- ----------------------------
-- Table structure for sys_health_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `sys_health_knowledge`;
CREATE TABLE `sys_health_knowledge`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '知识标题',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '知识类型下拉[1-就医篇-jiuyi,2-误区篇-wuqu,3-饮食篇-yinshi,4-生活篇-shenghuo]',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '知识内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '健康知识' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_health_knowledge
-- ----------------------------
INSERT INTO `sys_health_knowledge` VALUES (4, '首次就诊不用挂专家号', '1', '首诊主要是询问病史、开具相关检验检查，挂普通号就可以。', '2024-03-06 14:01:52', 1);

-- ----------------------------
-- Table structure for sys_health_measurements
-- ----------------------------
DROP TABLE IF EXISTS `sys_health_measurements`;
CREATE TABLE `sys_health_measurements`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `systolic_pressure` int(11) NULL DEFAULT NULL COMMENT '收缩压',
  `diastolic_pressure` int(11) NULL DEFAULT NULL COMMENT '舒张压',
  `blood_sugar_level` decimal(5, 2) NULL DEFAULT NULL COMMENT '血糖水平（精确到小数点后两位）',
  `temperature` decimal(4, 2) NULL DEFAULT NULL COMMENT '体温（精确到小数点后两位）',
  `pulse_rate` int(11) NULL DEFAULT NULL COMMENT '脉搏率',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '健康测量记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_health_measurements
-- ----------------------------
INSERT INTO `sys_health_measurements` VALUES (1, 120, 80, 5.70, 36.50, 75, '2024-03-07 08:00:00', 6);
INSERT INTO `sys_health_measurements` VALUES (2, 122, 82, 5.90, 36.70, 76, '2024-03-06 08:00:00', 6);
INSERT INTO `sys_health_measurements` VALUES (3, 118, 78, 5.50, 36.30, 72, '2024-03-05 08:00:00', 6);
INSERT INTO `sys_health_measurements` VALUES (4, 124, 84, 6.10, 36.90, 78, '2024-03-04 08:00:00', 6);
INSERT INTO `sys_health_measurements` VALUES (5, 126, 86, 6.30, 37.10, 80, '2024-03-03 08:00:00', 6);
INSERT INTO `sys_health_measurements` VALUES (6, 128, 88, 6.50, 37.30, 82, '2024-03-02 08:00:00', 6);
INSERT INTO `sys_health_measurements` VALUES (7, 130, 90, 6.70, 37.50, 84, '2024-03-01 08:00:00', 6);
INSERT INTO `sys_health_measurements` VALUES (8, 132, 92, 6.90, 37.70, 86, '2024-02-29 08:00:00', 6);

-- ----------------------------
-- Table structure for sys_note
-- ----------------------------
DROP TABLE IF EXISTS `sys_note`;
CREATE TABLE `sys_note`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `note_content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户备忘录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_note
-- ----------------------------
INSERT INTO `sys_note` VALUES (1, 6, '嚯嚯嚯', '1df5as1', 'http://localhost:9090/common/download/image?type=note-img&name=f2ed9be4-08ee-4221-ad92-2557ce336e76.png', '2024-03-07 11:33:19');
INSERT INTO `sys_note` VALUES (3, 6, 'test', 'fsdaf a', 'http://localhost:9090/common/download/image?type=note-img&name=ad963c52-0759-4471-bd55-b14e923739fb.png', '2024-03-07 11:42:58');

-- ----------------------------
-- Table structure for sys_physical_functions
-- ----------------------------
DROP TABLE IF EXISTS `sys_physical_functions`;
CREATE TABLE `sys_physical_functions`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '个人身体基本信息ID',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `height` decimal(5, 2) NULL DEFAULT NULL COMMENT '身高（精确到小数点后两位）',
  `weight` decimal(5, 2) NULL DEFAULT NULL COMMENT '体重（精确到小数点后两位）',
  `lung_capacity` int(11) NULL DEFAULT NULL COMMENT '肺活量',
  `standing_long_jump_distance` int(11) NULL DEFAULT NULL COMMENT '立定跳远距离',
  `sit_ups_count` int(11) NULL DEFAULT NULL COMMENT '仰卧起坐次数',
  `sprint_fiftym_time` int(11) NULL DEFAULT NULL COMMENT '50米跑时间（精确到小数点后两位）',
  `sprint_thousandm_time` int(11) NULL DEFAULT NULL COMMENT '1000米跑时间（精确到小数点后两位）',
  `sit_and_reach_distance` decimal(8, 2) NULL DEFAULT NULL COMMENT '坐位体前屈距离（精确到小数点后两位）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户身体机能' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_physical_functions
-- ----------------------------
INSERT INTO `sys_physical_functions` VALUES (1, 6, '男', '2024-03-13', 172.00, 60.00, 5000, 230, 50, 10, 280, 14.50);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `role_str` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 1, 'ADMIN');
INSERT INTO `sys_role` VALUES (2, 2, 'STUDENT');
INSERT INTO `sys_role` VALUES (3, 3, 'TEACHER');
INSERT INTO `sys_role` VALUES (9, 5, 'DOCTOR');
INSERT INTO `sys_role` VALUES (10, 6, 'USER');
INSERT INTO `sys_role` VALUES (11, 7, 'USER');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '昵称',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '性别（1-男，0-女）',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '头像',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否启用（1-启用，0-未启用）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_by` int(11) NULL DEFAULT NULL COMMENT '修改人',
  `is_del` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '软删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统-用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', '周某', '1', 'http://localhost:9090/common/download/image?type=user-avatar&name=cdb7f043-7811-4678-925c-b7471c5eda14.png', '1', '2024-02-27 11:01:02', 1, '2024-02-27 11:01:07', 1, '0');
INSERT INTO `sys_user` VALUES (5, 'doctor', 'e10adc3949ba59abbe56e057f20f883e', '周医生', '1', 'http://localhost:9090/common/download/image?type=user-avatar&name=cdb7f043-7811-4678-925c-b7471c5eda14.png', '1', '2024-03-06 09:28:37', 1, '2024-03-06 09:28:37', 1, '0');
INSERT INTO `sys_user` VALUES (6, '50038419991012', 'e10adc3949ba59abbe56e057f20f883e', '周某', '1', 'http://localhost:9090/common/download/image?type=user-avatar&name=cdb7f043-7811-4678-925c-b7471c5eda14.png', '1', '2024-03-06 11:01:10', 1, '2024-03-06 11:01:10', 1, '0');

SET FOREIGN_KEY_CHECKS = 1;
