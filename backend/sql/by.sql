/*
 Navicat Premium Dump SQL

 Source Server         : localhost_3306_1
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : localhost:3306
 Source Schema         : by

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 02/03/2026 12:19:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_log
-- ----------------------------
DROP TABLE IF EXISTS `admin_log`;
CREATE TABLE `admin_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `admin_id` bigint NOT NULL COMMENT '管理员ID',
  `admin_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员用户名',
  `operation` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作描述',
  `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_admin_id`(`admin_id` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '管理员操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_log
-- ----------------------------

-- ----------------------------
-- Table structure for browse_history
-- ----------------------------
DROP TABLE IF EXISTS `browse_history`;
CREATE TABLE `browse_history`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '浏览记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `browse_time` datetime NOT NULL COMMENT '浏览时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_product`(`user_id` ASC, `product_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_browse_time`(`browse_time` ASC) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户浏览历史表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of browse_history
-- ----------------------------

-- ----------------------------
-- Table structure for cart_item
-- ----------------------------
DROP TABLE IF EXISTS `cart_item`;
CREATE TABLE `cart_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `sku_id` bigint NOT NULL COMMENT 'SKU ID',
  `quantity` int NOT NULL DEFAULT 1 COMMENT '数量',
  `selected` tinyint NOT NULL DEFAULT 1 COMMENT '是否选中：0-否，1-是',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_sku`(`user_id` ASC, `sku_id` ASC) USING BTREE COMMENT '用户SKU唯一索引',
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE,
  INDEX `idx_sku_id`(`sku_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '购物车表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cart_item
-- ----------------------------

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父分类ID，0表示顶级分类',
  `category_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `category_icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类图标',
  `sort_order` int NULL DEFAULT 0 COMMENT '排序号',
  `level` tinyint NULL DEFAULT 1 COMMENT '分类层级：1-一级，2-二级，3-三级',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE,
  INDEX `idx_level`(`level` ASC) USING BTREE,
  INDEX `idx_sort_order`(`sort_order` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, 0, '新鲜蔬菜', 'https://picsum.photos/seed/cat1/50/50', 1, 1, 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `category` VALUES (2, 0, '新鲜水果', 'https://picsum.photos/seed/cat2/50/50', 2, 1, 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `category` VALUES (3, 0, '肉禽蛋品', 'https://picsum.photos/seed/cat3/50/50', 3, 1, 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `category` VALUES (4, 0, '水产海鲜', 'https://picsum.photos/seed/cat4/50/50', 4, 1, 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `category` VALUES (5, 0, '粮油调味', 'https://picsum.photos/seed/cat5/50/50', 5, 1, 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `category` VALUES (11, 1, '叶菜类', NULL, 1, 2, 1, '2025-12-01 15:58:33', '2025-12-01 15:58:33', 0);
INSERT INTO `category` VALUES (12, 1, '根茎类', NULL, 2, 2, 1, '2025-12-01 15:58:33', '2025-12-01 15:58:33', 0);
INSERT INTO `category` VALUES (13, 1, '茄果类', NULL, 3, 2, 1, '2025-12-01 15:58:33', '2025-12-01 15:58:33', 0);
INSERT INTO `category` VALUES (14, 1, '菌菇类', NULL, 4, 2, 1, '2025-12-01 15:58:33', '2025-12-01 15:58:33', 0);
INSERT INTO `category` VALUES (21, 2, '热带水果', NULL, 1, 2, 1, '2025-12-01 15:58:33', '2025-12-01 15:58:33', 0);
INSERT INTO `category` VALUES (22, 2, '时令水果', NULL, 2, 2, 1, '2025-12-01 15:58:33', '2025-12-01 15:58:33', 0);
INSERT INTO `category` VALUES (23, 2, '进口水果', NULL, 3, 2, 1, '2025-12-01 15:58:33', '2025-12-01 15:58:33', 0);
INSERT INTO `category` VALUES (31, 3, '猪肉', NULL, 1, 2, 1, '2025-12-01 15:58:33', '2025-12-01 15:58:33', 0);
INSERT INTO `category` VALUES (32, 3, '牛肉', NULL, 2, 2, 1, '2025-12-01 15:58:33', '2025-12-01 15:58:33', 0);
INSERT INTO `category` VALUES (33, 3, '禽肉', NULL, 3, 2, 1, '2025-12-01 15:58:33', '2025-12-01 15:58:33', 0);
INSERT INTO `category` VALUES (34, 3, '蛋品', NULL, 4, 2, 1, '2025-12-01 15:58:33', '2025-12-01 15:58:33', 0);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `total_amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '订单总金额',
  `pay_amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '实付金额',
  `freight_amount` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '运费',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '订单状态：0-待支付，1-待发货，2-待收货，3-已完成，4-已取消，5-已关闭',
  `receiver_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人电话',
  `receiver_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货地址',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单备注',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `deliver_time` datetime NULL DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime NULL DEFAULT NULL COMMENT '收货时间',
  `cancel_time` datetime NULL DEFAULT NULL COMMENT '取消时间',
  `cancel_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '取消原因',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  `logistics_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '物流单号',
  `logistics_company` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '物流公司',
  `auto_receive_time` datetime NULL DEFAULT NULL COMMENT '自动确认收货时间',
  `delivered` tinyint NULL DEFAULT 0 COMMENT '是否已送达：0-否，1-是',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_order_no`(`order_no` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE,
  INDEX `idx_auto_receive_time`(`auto_receive_time` ASC) USING BTREE,
  INDEX `idx_delivered`(`delivered` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order
-- ----------------------------

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单明细ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `sku_id` bigint NOT NULL COMMENT 'SKU ID',
  `product_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称（快照）',
  `sku_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'SKU名称（快照）',
  `product_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品图片（快照）',
  `price` decimal(10, 2) NOT NULL COMMENT '单价（快照）',
  `quantity` int NOT NULL COMMENT '数量',
  `total_price` decimal(10, 2) NOT NULL COMMENT '小计金额',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_order_no`(`order_no` ASC) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE,
  INDEX `idx_sku_id`(`sku_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单明细表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_item
-- ----------------------------

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `seller_id` bigint NOT NULL COMMENT '商家ID（关联user表）',
  `product_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `product_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品编码',
  `main_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主图URL',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '商品描述',
  `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '商品详情（富文本）',
  `original_price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '原价',
  `current_price` decimal(10, 2) NOT NULL COMMENT '现价',
  `stock` int NULL DEFAULT 0 COMMENT '总库存',
  `sales_volume` int NULL DEFAULT 0 COMMENT '销量',
  `view_count` int NULL DEFAULT 0 COMMENT '浏览量',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态：0-待审核，1-上架，2-下架，3-审核不通过',
  `is_recommended` tinyint NULL DEFAULT 0 COMMENT '是否推荐：0-否，1-是',
  `is_new` tinyint NULL DEFAULT 0 COMMENT '是否新品：0-否，1-是',
  `is_hot` tinyint NULL DEFAULT 0 COMMENT '是否热卖：0-否，1-是',
  `audit_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核备注',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `product_code`(`product_code` ASC) USING BTREE,
  INDEX `idx_category_id`(`category_id` ASC) USING BTREE,
  INDEX `idx_seller_id`(`seller_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_sales_volume`(`sales_volume` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE,
  INDEX `idx_current_price`(`current_price` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品基础信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, 11, 100, '有机小白菜', 'P001', 'https://picsum.photos/seed/product1/400/400', '新鲜有机小白菜，当日采摘，无农药残留', NULL, 8.00, 6.50, 200, 0, 0, 1, 1, 1, 0, NULL, '2025-12-01 15:58:33', '2026-01-25 14:46:38', 0);
INSERT INTO `product` VALUES (2, 11, 100, '新鲜菠菜', 'P002', 'https://picsum.photos/seed/product2/400/400', '农家自种菠菜，营养丰富', NULL, 7.00, 5.80, 200, 0, 0, 1, 1, 0, 0, NULL, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product` VALUES (3, 12, 100, '土豆', 'P003', 'https://picsum.photos/seed/product3/400/400', '沙地土豆，口感绵软香甜', NULL, 5.00, 3.50, 200, 0, 0, 1, 0, 0, 0, NULL, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product` VALUES (4, 21, 100, '海南芒果', 'P004', 'https://picsum.photos/seed/product4/400/400', '海南特产芒果，香甜多汁', NULL, 15.00, 12.80, 200, 0, 0, 1, 1, 1, 0, NULL, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product` VALUES (5, 22, 100, '红富士苹果', 'P005', 'https://picsum.photos/seed/product5/400/400', '烟台红富士苹果，脆甜可口', NULL, 12.00, 9.90, 200, 0, 0, 1, 1, 0, 0, NULL, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product` VALUES (6, 31, 100, '五花肉', 'P006', 'https://picsum.photos/seed/product6/400/400', '新鲜五花肉，肥瘦相间', NULL, 28.00, 25.80, 200, 0, 0, 1, 0, 0, 0, NULL, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product` VALUES (7, 33, 100, '土鸡', 'P007', 'https://picsum.photos/seed/product7/400/400', '农家散养土鸡，肉质鲜美', NULL, 68.00, 58.00, 100, 0, 0, 1, 1, 1, 0, NULL, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product` VALUES (8, 34, 100, '土鸡蛋', 'P008', 'https://picsum.photos/seed/product8/400/400', '农家土鸡蛋，营养价值高', NULL, 25.00, 22.00, 200, 0, 0, 1, 1, 0, 0, NULL, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product` VALUES (9, 11, 100, '油麦菜', 'P009', 'https://picsum.photos/seed/product9/400/400', '新鲜油麦菜，清脆爽口，富含维生素', NULL, 6.00, 4.80, 100, 0, 0, 1, 0, 1, 0, NULL, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product` VALUES (10, 11, 100, '生菜', 'P010', 'https://picsum.photos/seed/product10/400/400', '水培生菜，口感鲜嫩，适合沙拉', NULL, 5.50, 4.50, 100, 0, 0, 1, 1, 0, 0, NULL, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product` VALUES (11, 12, 100, '胡萝卜', 'P011', 'https://picsum.photos/seed/product11/400/400', '有机胡萝卜，甜脆可口，营养丰富', NULL, 4.50, 3.80, 200, 0, 0, 1, 0, 0, 1, NULL, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product` VALUES (12, 12, 100, '白萝卜', 'P012', 'https://picsum.photos/seed/product12/400/400', '新鲜白萝卜，水分充足，炖汤首选', NULL, 3.00, 2.50, 200, 0, 0, 1, 0, 0, 0, NULL, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product` VALUES (13, 13, 100, '西红柿', 'P013', 'https://picsum.photos/seed/product13/400/400', '自然成熟西红柿，酸甜可口', NULL, 6.00, 5.00, 200, 0, 0, 1, 1, 0, 1, NULL, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product` VALUES (14, 13, 100, '黄瓜', 'P014', 'https://picsum.photos/seed/product14/400/400', '新鲜黄瓜，清脆爽口，可生吃', NULL, 4.00, 3.20, 100, 0, 0, 1, 0, 1, 0, NULL, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product` VALUES (15, 14, 100, '香菇', 'P015', 'https://picsum.photos/seed/product15/400/400', '新鲜香菇，肉厚味香，营养丰富', NULL, 15.00, 12.00, 200, 0, 0, 1, 1, 0, 0, NULL, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product` VALUES (16, 21, 100, '香蕉', 'P016', 'https://picsum.photos/seed/product16/400/400', '进口香蕉，香甜软糯，老少皆宜', NULL, 8.00, 6.80, 200, 0, 0, 1, 1, 0, 1, NULL, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product` VALUES (17, 22, 100, '砂糖橘', 'P017', 'https://picsum.photos/seed/product17/400/400', '广西砂糖橘，皮薄汁多，甜度高', NULL, 10.00, 8.80, 200, 0, 0, 1, 1, 1, 1, NULL, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product` VALUES (18, 32, 100, '牛腩', 'P018', 'https://picsum.photos/seed/product18/400/400', '新鲜牛腩，肉质鲜嫩，适合炖煮', NULL, 55.00, 48.00, 200, 0, 0, 1, 0, 0, 0, NULL, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product` VALUES (19, 31, 100, '排骨', 'P019', 'https://picsum.photos/seed/product19/400/400', '新鲜猪排骨，肉多骨少，炖汤红烧皆宜', NULL, 35.00, 32.00, 200, 0, 0, 1, 1, 0, 1, NULL, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product` VALUES (20, 33, 100, '鸭腿', 'P020', 'https://picsum.photos/seed/product20/400/400', '农家散养鸭腿，肉质紧实，味道鲜美', NULL, 25.00, 22.00, 200, 0, 0, 1, 0, 1, 0, NULL, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product` VALUES (21, 11, 100, '????-????', 'P-F6077A4A', 'https://picsum.photos/seed/product21/400/400', '??????', NULL, 10.00, 8.00, 100, 0, 0, 0, 0, 0, 0, NULL, '2025-12-15 18:01:42', '2026-01-25 13:50:56', 0);

-- ----------------------------
-- Table structure for product_collection
-- ----------------------------
DROP TABLE IF EXISTS `product_collection`;
CREATE TABLE `product_collection`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_product`(`user_id` ASC, `product_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品收藏表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product_collection
-- ----------------------------

-- ----------------------------
-- Table structure for product_image
-- ----------------------------
DROP TABLE IF EXISTS `product_image`;
CREATE TABLE `product_image`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '图片ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图片URL',
  `sort_order` int NULL DEFAULT 0 COMMENT '排序号',
  `image_type` tinyint NULL DEFAULT 1 COMMENT '图片类型：1-商品图，2-详情图',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE,
  INDEX `idx_sort_order`(`sort_order` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品图片表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product_image
-- ----------------------------
INSERT INTO `product_image` VALUES (1, 1, 'https://picsum.photos/seed/img1/800/800', 1, 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product_image` VALUES (2, 1, 'https://picsum.photos/seed/img2/800/800', 2, 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product_image` VALUES (3, 1, 'https://picsum.photos/seed/img3/1200/800', 1, 2, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product_image` VALUES (4, 2, 'https://picsum.photos/seed/img4/800/800', 1, 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product_image` VALUES (5, 2, 'https://picsum.photos/seed/img5/800/800', 2, 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product_image` VALUES (6, 3, 'https://picsum.photos/seed/img6/800/800', 1, 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product_image` VALUES (7, 4, 'https://picsum.photos/seed/img7/800/800', 1, 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product_image` VALUES (8, 4, 'https://picsum.photos/seed/img8/800/800', 2, 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product_image` VALUES (9, 5, 'https://picsum.photos/seed/img9/800/800', 1, 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);

-- ----------------------------
-- Table structure for product_review
-- ----------------------------
DROP TABLE IF EXISTS `product_review`;
CREATE TABLE `product_review`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评价ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `rating` tinyint NOT NULL COMMENT '评分：1-5星',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '评价内容',
  `images` json NULL COMMENT '评价图片（JSON数组）',
  `reply_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '商家回复内容',
  `reply_time` datetime NULL DEFAULT NULL COMMENT '回复时间',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态：0-隐藏，1-显示',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_rating`(`rating` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品评价表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product_review
-- ----------------------------

-- ----------------------------
-- Table structure for product_sku
-- ----------------------------
DROP TABLE IF EXISTS `product_sku`;
CREATE TABLE `product_sku`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'SKU ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `sku_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'SKU名称（如：500g、1kg）',
  `sku_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'SKU编码',
  `sku_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'SKU图片',
  `price` decimal(10, 2) NOT NULL COMMENT 'SKU价格',
  `stock` int NULL DEFAULT 0 COMMENT 'SKU库存',
  `sales_volume` int NULL DEFAULT 0 COMMENT 'SKU销量',
  `spec_info` json NULL COMMENT '规格信息（JSON格式，如：{\"重量\":\"500g\",\"产地\":\"山东\"}）',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sku_code`(`sku_code` ASC) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE,
  INDEX `idx_sku_code`(`sku_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品SKU表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of product_sku
-- ----------------------------
INSERT INTO `product_sku` VALUES (1, 1, '小白菜 500g', 'P001-S1', NULL, 6.50, 100, 0, '{\"重量\": \"500g\"}', 1, '2025-12-01 15:58:33', '2026-01-25 14:46:38', 0);
INSERT INTO `product_sku` VALUES (2, 1, '小白菜 1kg', 'P001-S2', NULL, 12.00, 100, 0, '{\"重量\": \"1kg\"}', 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (3, 2, '菠菜 500g', 'P002-S1', NULL, 5.80, 100, 0, '{\"重量\": \"500g\"}', 1, '2025-12-01 15:58:33', '2026-01-25 14:46:38', 0);
INSERT INTO `product_sku` VALUES (4, 2, '菠菜 1kg', 'P002-S2', NULL, 10.80, 100, 0, '{\"重量\": \"1kg\"}', 1, '2025-12-01 15:58:33', '2025-12-01 15:58:33', 0);
INSERT INTO `product_sku` VALUES (5, 3, '土豆 500g', 'P003-S1', NULL, 3.50, 100, 0, '{\"重量\": \"500g\"}', 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (6, 3, '土豆 2.5kg', 'P003-S2', NULL, 15.00, 100, 0, '{\"重量\": \"2.5kg\"}', 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (7, 4, '芒果 500g(约2个)', 'P004-S1', NULL, 12.80, 100, 0, '{\"数量\": \"约2个\", \"重量\": \"500g\"}', 1, '2025-12-01 15:58:33', '2025-12-01 15:58:33', 0);
INSERT INTO `product_sku` VALUES (8, 4, '芒果 1kg(约4个)', 'P004-S2', NULL, 24.00, 100, 0, '{\"数量\": \"约4个\", \"重量\": \"1kg\"}', 1, '2025-12-01 15:58:33', '2025-12-01 15:58:33', 0);
INSERT INTO `product_sku` VALUES (9, 5, '苹果 500g(约3个)', 'P005-S1', NULL, 9.90, 100, 0, '{\"数量\": \"约3个\", \"重量\": \"500g\"}', 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (10, 5, '苹果 2.5kg(约15个)', 'P005-S2', NULL, 45.00, 100, 0, '{\"数量\": \"约15个\", \"重量\": \"2.5kg\"}', 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (11, 6, '五花肉 500g', 'P006-S1', NULL, 25.80, 100, 0, '{\"重量\": \"500g\"}', 1, '2025-12-01 15:58:33', '2025-12-01 15:58:33', 0);
INSERT INTO `product_sku` VALUES (12, 6, '五花肉 1kg', 'P006-S2', NULL, 50.00, 100, 0, '{\"重量\": \"1kg\"}', 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (13, 7, '土鸡 约2kg/只', 'P007-S1', NULL, 58.00, 100, 0, '{\"单位\": \"只\", \"重量\": \"约2kg\"}', 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (14, 8, '土鸡蛋 10枚', 'P008-S1', NULL, 22.00, 100, 0, '{\"数量\": \"10枚\"}', 1, '2025-12-01 15:58:33', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (15, 8, '土鸡蛋 30枚', 'P008-S2', NULL, 60.00, 100, 0, '{\"数量\": \"30枚\"}', 1, '2025-12-01 15:58:33', '2025-12-01 15:58:33', 0);
INSERT INTO `product_sku` VALUES (16, 9, '油麦菜 500g', 'P009-S1', NULL, 4.80, 100, 0, '{\"重量\": \"500g\"}', 1, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (17, 10, '生菜 500g', 'P010-S1', NULL, 4.50, 100, 0, '{\"重量\": \"500g\"}', 1, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (18, 11, '胡萝卜 500g', 'P011-S1', NULL, 3.80, 100, 0, '{\"重量\": \"500g\"}', 1, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (19, 11, '胡萝卜 1kg', 'P011-S2', NULL, 7.00, 100, 0, '{\"重量\": \"1kg\"}', 1, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (20, 12, '白萝卜 1个(约500g)', 'P012-S1', NULL, 2.50, 100, 0, '{\"重量\": \"约500g\"}', 1, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (21, 12, '白萝卜 2个(约1kg)', 'P012-S2', NULL, 4.50, 100, 0, '{\"重量\": \"约1kg\"}', 1, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (22, 13, '西红柿 500g', 'P013-S1', NULL, 5.00, 100, 0, '{\"重量\": \"500g\"}', 1, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (23, 13, '西红柿 1kg', 'P013-S2', NULL, 9.50, 100, 0, '{\"重量\": \"1kg\"}', 1, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (24, 14, '黄瓜 500g(约3根)', 'P014-S1', NULL, 3.20, 100, 0, '{\"数量\": \"约3根\", \"重量\": \"500g\"}', 1, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (25, 15, '香菇 250g', 'P015-S1', NULL, 12.00, 100, 0, '{\"重量\": \"250g\"}', 1, '2025-12-14 10:00:00', '2025-12-14 10:00:00', 0);
INSERT INTO `product_sku` VALUES (26, 15, '香菇 500g', 'P015-S2', NULL, 22.00, 100, 0, '{\"重量\": \"500g\"}', 1, '2025-12-14 10:00:00', '2025-12-14 10:00:00', 0);
INSERT INTO `product_sku` VALUES (27, 16, '香蕉 500g(约3根)', 'P016-S1', NULL, 6.80, 100, 0, '{\"数量\": \"约3根\", \"重量\": \"500g\"}', 1, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (28, 16, '香蕉 1kg(约6根)', 'P016-S2', NULL, 12.80, 100, 0, '{\"数量\": \"约6根\", \"重量\": \"1kg\"}', 1, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (29, 17, '砂糖橘 500g', 'P017-S1', NULL, 8.80, 100, 0, '{\"重量\": \"500g\"}', 1, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (30, 17, '砂糖橘 2.5kg', 'P017-S2', NULL, 38.00, 100, 0, '{\"重量\": \"2.5kg\"}', 1, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (31, 18, '牛腩 500g', 'P018-S1', NULL, 48.00, 100, 0, '{\"重量\": \"500g\"}', 1, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (32, 18, '牛腩 1kg', 'P018-S2', NULL, 92.00, 100, 0, '{\"重量\": \"1kg\"}', 1, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (33, 19, '排骨 500g', 'P019-S1', NULL, 32.00, 100, 0, '{\"重量\": \"500g\"}', 1, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (34, 19, '排骨 1kg', 'P019-S2', NULL, 60.00, 100, 0, '{\"重量\": \"1kg\"}', 1, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (35, 20, '鸭腿 2只(约500g)', 'P020-S1', NULL, 22.00, 100, 0, '{\"数量\": \"2只\", \"重量\": \"约500g\"}', 1, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (36, 20, '鸭腿 4只(约1kg)', 'P020-S2', NULL, 42.00, 100, 0, '{\"数量\": \"4只\", \"重量\": \"约1kg\"}', 1, '2025-12-14 10:00:00', '2026-01-25 13:50:56', 0);
INSERT INTO `product_sku` VALUES (40, 21, '500g', 'P-F6077A4A-S1', NULL, 8.00, 100, 0, '{\"weight\": \"500g\"}', 1, '2025-12-15 18:01:42', '2025-12-15 18:01:42', 0);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `role_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色编码：CUSTOMER-顾客，SELLER-商家，ADMIN-管理员',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_name`(`role_name` ASC) USING BTREE,
  UNIQUE INDEX `role_code`(`role_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '管理员', 'ADMIN', '系统管理员', '2026-02-23 17:45:31', '2026-02-23 17:45:31');
INSERT INTO `role` VALUES (2, '顾客', 'CUSTOMER', '普通用户', '2026-02-23 17:46:04', '2026-02-23 17:46:04');
INSERT INTO `role` VALUES (3, '商家', 'SELLER', '商家用户', '2026-02-23 17:46:04', '2026-02-23 17:46:04');

-- ----------------------------
-- Table structure for seller_account
-- ----------------------------
DROP TABLE IF EXISTS `seller_account`;
CREATE TABLE `seller_account`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `seller_id` bigint NOT NULL COMMENT '商家用户ID',
  `balance` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '可用余额',
  `frozen_amount` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '冻结金额（待结算）',
  `total_income` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '累计收入',
  `total_withdraw` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '累计提现',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_seller_id`(`seller_id` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商家账户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of seller_account
-- ----------------------------
INSERT INTO `seller_account` VALUES (100, 100, 0.00, 0.00, 0.00, 0.00, '2026-01-25 13:50:56', '2026-01-25 13:50:56');

-- ----------------------------
-- Table structure for seller_info
-- ----------------------------
DROP TABLE IF EXISTS `seller_info`;
CREATE TABLE `seller_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `shop_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '店铺名称',
  `shop_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '店铺Logo',
  `shop_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '店铺描述',
  `business_license` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '营业执照',
  `contact_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `contact_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系地址',
  `audit_status` tinyint NULL DEFAULT 0 COMMENT '审核状态：0-待审核，1-审核通过，2-审核不通过',
  `audit_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核备注',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_audit_status`(`audit_status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商家信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of seller_info
-- ----------------------------
INSERT INTO `seller_info` VALUES (100, 100, '开发者商店', 'https://picsum.photos/200', '开发测试专用商店，提供各类优质商品', 'DEV_LICENSE_001', '13900000100', '开发测试地址', 1, '开发环境自动审核通过', '2026-01-25 13:50:56', '2026-01-25 13:50:56');

-- ----------------------------
-- Table structure for stock_adjustment
-- ----------------------------
DROP TABLE IF EXISTS `stock_adjustment`;
CREATE TABLE `stock_adjustment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `sku_id` bigint NOT NULL COMMENT 'SKU ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `seller_id` bigint NOT NULL COMMENT '商家ID',
  `adjust_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '调整类型：ADD-增加,REDUCE-减少,SET-设置',
  `before_stock` int NOT NULL COMMENT '调整前库存',
  `adjust_quantity` int NOT NULL COMMENT '调整数量',
  `after_stock` int NOT NULL COMMENT '调整后库存',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sku_id`(`sku_id` ASC) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE,
  INDEX `idx_seller_id`(`seller_id` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '库存调整记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stock_adjustment
-- ----------------------------

-- ----------------------------
-- Table structure for transaction_record
-- ----------------------------
DROP TABLE IF EXISTS `transaction_record`;
CREATE TABLE `transaction_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `transaction_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交易流水号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `account_type` tinyint NOT NULL COMMENT '账户类型：1-用户钱包，2-商家账户',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交易类型：RECHARGE-充值，ADMIN_RECHARGE-管理员充值，PAYMENT-支付，REFUND-退款，SETTLEMENT-结算，WITHDRAW-提现，FREEZE-冻结',
  `amount` decimal(12, 2) NOT NULL COMMENT '交易金额',
  `balance_after` decimal(12, 2) NOT NULL COMMENT '交易后余额',
  `related_order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '关联订单号',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '交易描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_transaction_no`(`transaction_no` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_account_type`(`account_type` ASC) USING BTREE,
  INDEX `idx_type`(`type` ASC) USING BTREE,
  INDEX `idx_related_order_no`(`related_order_no` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '交易记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of transaction_record
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码（加密）',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'https://via.placeholder.com/150' COMMENT '头像URL',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `gender` tinyint NULL DEFAULT 0 COMMENT '性别：0-未知，1-男，2-女',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE,
  UNIQUE INDEX `phone`(`phone` ASC) USING BTREE,
  INDEX `idx_phone`(`phone` ASC) USING BTREE,
  INDEX `idx_email`(`email` ASC) USING BTREE,
  INDEX `idx_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 105 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户基础表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (4, 'admin', '15587073025', '123@163.com', '$2a$10$yNgaOQH76FrksJfdMMf5zOxwjJKIvwDFg6H8tFHYK5AD0QuSjE306', 'https://via.placeholder.com/150', 'admin', 0, 1, '2025-12-01 17:10:21', '2025-12-01 17:10:21', 0);
INSERT INTO `user` VALUES (100, 'dev_seller', '13900000100', 'dev@example.com', '$2a$10$yNgaOQH76FrksJfdMMf5zOxwjJKIvwDFg6H8tFHYK5AD0QuSjE306', 'https://picsum.photos/150', '开发者商家', 0, 1, '2026-01-25 13:50:56', '2026-02-23 17:05:20', 0);
INSERT INTO `user` VALUES (101, 'vie', '15500000001', 'vie@example.com', '$2a$10$yNgaOQH76FrksJfdMMf5zOxwjJKIvwDFg6H8tFHYK5AD0QuSjE306', 'https://via.placeholder.com/150', 'VIE管理员', 0, 1, '2026-02-23 17:48:00', '2026-02-23 17:48:00', 0);
INSERT INTO `user` VALUES (102, 'test_user1', '13900000001', NULL, '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'https://via.placeholder.com/150', '测试用户1', 1, 1, '2026-02-25 17:40:39', '2026-02-25 17:40:39', 0);
INSERT INTO `user` VALUES (103, 'test_user2', '13900000002', NULL, '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'https://via.placeholder.com/150', '测试用户2', 2, 1, '2026-02-25 17:40:39', '2026-02-25 17:40:39', 0);
INSERT INTO `user` VALUES (104, 'test_user3', '13900000003', NULL, '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'https://via.placeholder.com/150', '测试用户3', 1, 0, '2026-02-25 17:40:39', '2026-02-25 17:40:39', 0);

-- ----------------------------
-- Table structure for user_address
-- ----------------------------
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '地址ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `receiver_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收货人电话',
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '省份',
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '城市',
  `district` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '区县',
  `detail_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '详细地址',
  `is_default` tinyint NULL DEFAULT 0 COMMENT '是否默认：0-否，1-是',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户地址表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_address
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_role`(`user_id` ASC, `role_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (3, 4, 2, '2025-12-01 17:10:21');
INSERT INTO `user_role` VALUES (8, 101, 1, '2026-02-23 17:48:20');
INSERT INTO `user_role` VALUES (9, 4, 3, '2026-02-23 19:13:05');
INSERT INTO `user_role` VALUES (10, 100, 3, '2026-02-23 19:13:45');
INSERT INTO `user_role` VALUES (11, 100, 2, '2026-02-23 19:13:55');

-- ----------------------------
-- Table structure for user_wallet
-- ----------------------------
DROP TABLE IF EXISTS `user_wallet`;
CREATE TABLE `user_wallet`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `balance` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '可用余额',
  `frozen_amount` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '冻结金额',
  `total_recharge` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '累计充值',
  `total_consume` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '累计消费',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 103 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户钱包表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_wallet
-- ----------------------------
INSERT INTO `user_wallet` VALUES (6, 4, 0.00, 0.00, 0.00, 0.00, '2025-12-01 17:10:21', '2025-12-01 17:10:21');
INSERT INTO `user_wallet` VALUES (100, 100, 0.00, 0.00, 0.00, 0.00, '2026-01-25 13:50:56', '2026-01-25 13:50:56');

SET FOREIGN_KEY_CHECKS = 1;
