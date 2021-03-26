SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for u_user_role
-- ----------------------------
DROP TABLE IF EXISTS `u_user_role`;
CREATE TABLE `u_user_role` (
                               `id` int(11) NOT NULL,
                               `user_id` int(11) NOT NULL,
                               `role_id` int(11) NOT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of u_user_role
-- ----------------------------
BEGIN;
INSERT INTO `u_user_role` VALUES (1, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for u_user
-- ----------------------------
DROP TABLE IF EXISTS `u_user`;
CREATE TABLE `u_user` (
                          `id` int(11) NOT NULL,
                          `name` varchar(255) NOT NULL,
                          `password` varchar(255) NOT NULL,
                          `enabled` bit(1) NOT NULL,
                          `create_time` datetime NOT NULL,
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of u_user
-- ----------------------------
BEGIN;
INSERT INTO `u_user` VALUES (1, 'Chenglong', '{bcrypt}$2a$10$kqtIQjFFJ8cae1E54dh37OPcsaCxiXVkPsv9Fea5tz38IyH22oCKO', b'1', '2021-03-10 14:16:58');
COMMIT;

-- ----------------------------
-- Table structure for u_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `u_role_resource`;
CREATE TABLE `u_role_resource` (
                                   `id` int(11) NOT NULL,
                                   `role_id` int(11) DEFAULT NULL,
                                   `resource_id` int(11) DEFAULT NULL,
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of u_role_resource
-- ----------------------------
BEGIN;
INSERT INTO `u_role_resource` VALUES (1, 1, 1);
INSERT INTO `u_role_resource` VALUES (2, 2, 1);
COMMIT;

-- ----------------------------
-- Table structure for u_role
-- ----------------------------
DROP TABLE IF EXISTS `u_role`;
CREATE TABLE `u_role` (
                          `id` int(11) NOT NULL,
                          `name` varchar(255) DEFAULT NULL,
                          `description` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of u_role
-- ----------------------------
BEGIN;
INSERT INTO `u_role` VALUES (1, 'ADMIN', 'ADMIN');
INSERT INTO `u_role` VALUES (2, 'USER', 'USER');
COMMIT;

-- ----------------------------
-- Table structure for u_resource
-- ----------------------------
DROP TABLE IF EXISTS `u_resource`;
CREATE TABLE `u_resource` (
                              `id` int(11) NOT NULL,
                              `name` varchar(255) DEFAULT NULL,
                              `resource` varchar(255) DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of u_resource
-- ----------------------------
BEGIN;
INSERT INTO `u_resource` VALUES (1, 'admin查询', '/query');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
