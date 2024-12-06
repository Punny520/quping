/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80040 (8.0.40)
 Source Host           : localhost:3306
 Source Schema         : quping

 Target Server Type    : MySQL
 Target Server Version : 80040 (8.0.40)
 File Encoding         : 65001

 Date: 07/12/2024 00:35:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `like_count` int NULL DEFAULT NULL,
  `user_id` bigint NULL DEFAULT NULL,
  `rating_id` bigint NULL DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `deleted` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 122 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (118, '好吃', 2, 1013, 1116, '2024-12-06 20:48:27', '2024-12-06 20:49:26', 0);
INSERT INTO `comment` VALUES (119, '一般般吧', 1, 1014, 1116, '2024-12-06 20:49:24', '2024-12-07 00:20:11', 0);
INSERT INTO `comment` VALUES (120, '66666', 0, 1014, 1117, '2024-12-06 20:50:23', '2024-12-06 20:50:23', 0);
INSERT INTO `comment` VALUES (121, '好用', 1, 1014, 1216, '2024-12-06 20:53:16', '2024-12-06 20:53:28', 0);

-- ----------------------------
-- Table structure for comment_like_info
-- ----------------------------
DROP TABLE IF EXISTS `comment_like_info`;
CREATE TABLE `comment_like_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NULL DEFAULT NULL,
  `comment_id` bigint NULL DEFAULT NULL,
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '点赞状态，0未点赞，1已经点赞',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `deleted` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment_like_info
-- ----------------------------
INSERT INTO `comment_like_info` VALUES (5, 1013, 118, 1, '2024-12-06 20:48:33', '2024-12-06 20:48:36', 0);
INSERT INTO `comment_like_info` VALUES (6, 1014, 118, 1, '2024-12-06 20:49:26', '2024-12-06 20:49:26', 0);
INSERT INTO `comment_like_info` VALUES (7, 1014, 121, 1, '2024-12-06 20:53:28', '2024-12-06 20:53:28', 0);
INSERT INTO `comment_like_info` VALUES (8, 1013, 119, 1, '2024-12-07 00:20:11', '2024-12-07 00:20:11', 0);

-- ----------------------------
-- Table structure for rating
-- ----------------------------
DROP TABLE IF EXISTS `rating`;
CREATE TABLE `rating`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `score` float NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `count` int NULL DEFAULT NULL,
  `create_by` int NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `deleted` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1217 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of rating
-- ----------------------------
INSERT INTO `rating` VALUES (1116, 'http://video.thma67.biz/ClothingShoesandJewelry', 4, '傅記食品有限责任公司', '饮食业', 2, NULL, '2024-12-06 09:00:00', '2024-12-06 20:53:53', 0);
INSERT INTO `rating` VALUES (1117, 'http://image.aoiwada.co.jp/VideoGames', 2, '璐工程有限责任公司', '工程业', 1, NULL, '2024-12-06 09:00:00', '2024-12-06 20:58:55', 0);
INSERT INTO `rating` VALUES (1118, 'https://auth.martin131.biz/CellPhonesAccessories', 3, '钱記电讯有限责任公司', '电讯業', 1, NULL, '2024-12-06 09:00:00', '2024-12-06 20:50:11', 0);
INSERT INTO `rating` VALUES (1119, 'http://image.sanchez3.info/Beauty', 0, '震南系统有限责任公司', '资讯科技业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1120, 'http://auth.katohina1960.cn/AppsGames', 0, '孟有限责任公司', '房地产业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1121, 'https://image.murakamiren.co.jp/HouseholdKitchenAppliances', 0, '钱記系统有限责任公司', '资讯科技业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1122, 'http://image.llyuen610.org/VideoGames', 0, '石有限责任公司', '制药业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1123, 'https://video.ruidi.us/ToysGames', 0, '云熙食品有限责任公司', '饮食业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1124, 'https://auth.nakagawa10.com/BeautyPersonalCare', 0, '叶記通讯有限责任公司', '电讯業', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1125, 'http://image.lhy.com/HouseholdKitchenAppliances', 0, '黎有限责任公司', '物流业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1126, 'http://auth.nakamori3.info/BeautyPersonalCare', 0, '子异通讯有限责任公司', '电讯業', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1127, 'http://drive.sarahaw.co.jp/PetSupplies', 0, '致远有限责任公司', '工业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1128, 'https://drive.garyedwards8.net/BeautyPersonalCare', 0, '震南物业代理有限责任公司', '房地产业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1129, 'https://auth.shino3.co.jp/Appliances', 0, '睿有限责任公司', '咨询业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1130, 'http://www.chowsw919.us/Others', 0, '廖有限责任公司', '房地产业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1131, 'https://auth.flores1953.com/Beauty', 0, '蒋有限责任公司', '制药业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1132, 'https://auth.fordm.co.jp/BaggageTravelEquipment', 0, '杨有限责任公司', '房地产业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1133, 'https://drive.xiuyingzhang.info/ClothingShoesandJewelry', 0, '云熙顾问有限责任公司', '咨询业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1134, 'http://image.xiuxiao.xyz/AutomotivePartsAccessories', 0, '董記有限责任公司', '贸易行业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1135, 'https://video.tinstone52.biz/ClothingShoesandJewelry', 0, '常贸易有限责任公司', '金融服务业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1136, 'https://video.edwinh10.com/CDsVinyl', 0, '秀英有限责任公司', '电讯業', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1137, 'http://image.kojimmai.cn/AutomotivePartsAccessories', 0, '夏有限责任公司', '制造业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1138, 'https://www.holernes7.org/Handcrafts', 0, '石系统有限责任公司', '资讯科技业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1139, 'http://auth.kathphill.biz/ComputersElectronics', 0, '郑記顾问有限责任公司', '咨询业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1140, 'https://video.diana5.cn/SportsOutdoor', 0, '阎有限责任公司', '电讯業', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1141, 'http://www.fujiisara89.xyz/AppsGames', 0, '夏有限责任公司', '物流业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1142, 'https://video.lillianand5.org/CellPhonesAccessories', 0, '子韬制药有限责任公司', '制药业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1143, 'https://auth.kenneth46.com/ToysGames', 0, '子异电脑有限责任公司', '电子行业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1144, 'http://drive.ikfujiw.us/Appliances', 0, '睿顾问有限责任公司', '咨询业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1145, 'https://www.thomas326.net/BaggageTravelEquipment', 0, '宇宁电子有限责任公司', '电子行业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1146, 'https://video.yincl1218.us/Books', 0, '致远有限责任公司', '电子行业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1147, 'https://image.ngcw4.info/CenturionGardenOutdoor', 0, '任記有限责任公司', '电讯業', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1148, 'https://video.cll.co.jp/CDsVinyl', 0, '姜記工程有限责任公司', '工程业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1149, 'https://drive.shihany.us/Appliances', 0, '致远有限责任公司', '电子行业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1150, 'http://video.xl1952.us/BaggageTravelEquipment', 0, '孙有限责任公司', '物流业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1151, 'http://drive.th10.com/AutomotivePartsAccessories', 0, '张記工业有限责任公司', '工业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1152, 'https://www.aiwasaki703.net/ToysGames', 0, '安琪有限责任公司', '物流业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1153, 'https://auth.hyl.co.jp/CollectiblesFineArt', 0, '高記食品有限责任公司', '饮食业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1154, 'http://www.renishi.org/ComputersElectronics', 0, '杰宏有限责任公司', '工程业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1155, 'http://video.wingszeche.biz/HealthBabyCare', 0, '周記制药有限责任公司', '制药业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1156, 'https://image.hstephanie.jp/VideoGames', 0, '杰宏食品有限责任公司', '饮食业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1157, 'https://video.seikoaoki3.info/CenturionGardenOutdoor', 0, '璐物业代理有限责任公司', '房地产业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1158, 'https://video.mwaihan.com/Food', 0, '胡有限责任公司', '咨询业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1159, 'http://image.jimmybrook.biz/Baby', 0, '杰宏发展贸易有限责任公司', '贸易行业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1160, 'http://auth.sasakiyu423.co.jp/IndustrialScientificSupplies', 0, '云熙工程有限责任公司', '工程业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1161, 'http://auth.anx7.us/Others', 0, '詩涵有限责任公司', '制造业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1162, 'http://drive.songru118.com/Baby', 0, '宇宁有限责任公司', '资讯科技业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1163, 'https://image.zhennan7.cn/BaggageTravelEquipment', 0, '秀英食品有限责任公司', '饮食业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1164, 'http://www.ishiimom.cn/AppsGames', 0, '程記有限责任公司', '金融服务业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1165, 'https://video.millst89.org/SportsOutdoor', 0, '刘記工业有限责任公司', '工业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1166, 'https://image.yrui.com/Others', 0, '杨工业有限责任公司', '工业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1167, 'http://auth.wingsze6.xyz/ToolsHomeDecoration', 0, '岚有限责任公司', '贸易行业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1168, 'https://video.lukaling106.biz/ToysGames', 0, '萧記工业有限责任公司', '工业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1169, 'https://auth.lanzhong.org/ToolsHomeDecoration', 0, '莫玩具有限责任公司', '制造业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1170, 'http://www.jialx.jp/CellPhonesAccessories', 0, '薛贸易有限责任公司', '金融服务业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1171, 'http://image.aot.info/BeautyPersonalCare', 0, '杰宏有限责任公司', '贸易行业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1172, 'https://video.wingszeyuen.com/CenturionGardenOutdoor', 0, '詩涵发展贸易有限责任公司', '贸易行业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1173, 'https://www.akina10.net/CellPhonesAccessories', 0, '唐电讯有限责任公司', '电讯業', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1174, 'https://image.minatotan.co.jp/PetSupplies', 0, '戴贸易有限责任公司', '金融服务业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1175, 'https://auth.airmura.cn/Food', 0, '夏物业代理有限责任公司', '房地产业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1176, 'https://www.chan418.co.jp/BaggageTravelEquipment', 0, '嘉伦有限责任公司', '咨询业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1177, 'http://image.cwch.info/HouseholdKitchenAppliances', 0, '晓明电子有限责任公司', '电子行业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1178, 'https://drive.chibn415.org/Books', 0, '余系统有限责任公司', '资讯科技业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1179, 'https://video.takuya1018.info/MusicalInstrument', 0, '于記有限责任公司', '资讯科技业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1180, 'https://video.zhiyluo.info/ArtsHandicraftsSewing', 0, '顾玩具有限责任公司', '制造业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1181, 'https://drive.grahamdi2.com/Others', 0, '顾記顾问有限责任公司', '咨询业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1182, 'http://image.jizhiyuan.com/ArtsHandicraftsSewing', 0, '子异制药有限责任公司', '制药业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1183, 'https://drive.sandrthom.cn/AutomotivePartsAccessories', 0, '子异有限责任公司', '贸易行业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1184, 'http://auth.yukoj.co.jp/SportsOutdoor', 0, '震南技术有限责任公司', '资讯科技业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1185, 'https://drive.takeyuito.us/MusicalInstrument', 0, '安琪有限责任公司', '饮食业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1186, 'http://auth.hazukisaito.co.jp/ToysGames', 0, '董制药有限责任公司', '制药业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1187, 'https://video.tr7.co.jp/ClothingShoesandJewelry', 0, '于記技术有限责任公司', '资讯科技业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1188, 'https://auth.sylviawhi.com/CDsVinyl', 0, '安琪顾问有限责任公司', '咨询业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1189, 'http://www.chiehluncho630.xyz/BaggageTravelEquipment', 0, '陈記电脑有限责任公司', '电子行业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1190, 'https://video.otsuka8.us/CenturionGardenOutdoor', 0, '子异发展贸易有限责任公司', '贸易行业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1191, 'https://www.mas02.net/PetSupplies', 0, '向記有限责任公司', '工程业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1192, 'https://image.chiuwaipang.net/ComputersElectronics', 0, '璐顾问有限责任公司', '咨询业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1193, 'https://image.peggywarren.info/CDsVinyl', 0, '璐有限责任公司', '贸易行业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1194, 'http://www.wailamtan.info/CDsVinyl', 0, '陆有限责任公司', '电子行业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1195, 'https://auth.tkoj.xyz/Baby', 0, '沈記有限责任公司', '工程业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1196, 'http://auth.eddie4.biz/Food', 0, '林記工业有限责任公司', '工业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1197, 'https://drive.iknaka.biz/CellPhonesAccessories', 0, '宇宁有限责任公司', '制药业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1198, 'https://auth.clarence521.co.jp/SportsOutdoor', 0, '岚食品有限责任公司', '饮食业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1199, 'http://auth.ayato4.co.jp/VideoGames', 0, '詩涵有限责任公司', '金融服务业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1200, 'http://www.yuziyi80.co.jp/CenturionGardenOutdoor', 0, '宇宁有限责任公司', '房地产业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1201, 'https://video.wlu.com/CollectiblesFineArt', 0, '陶有限责任公司', '咨询业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1202, 'https://image.kftang.co.jp/Handcrafts', 0, '宋工业有限责任公司', '工业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1203, 'https://video.itsuki4.com/BaggageTravelEquipment', 0, '安琪通讯有限责任公司', '电讯業', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1204, 'http://drive.kazumaaoki1214.com/CDsVinyl', 0, '萧記有限责任公司', '贸易行业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1205, 'https://drive.kaneko5.biz/ClothingShoesandJewelry', 0, '子韬顾问有限责任公司', '咨询业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1206, 'https://drive.cazhenn.xyz/CenturionGardenOutdoor', 0, '陈記食品有限责任公司', '饮食业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1207, 'https://www.kelly3.co.jp/AppsGames', 0, '傅記有限责任公司', '金融服务业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1208, 'http://video.meng6.co.jp/ToysGames', 0, '璐有限责任公司', '房地产业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1209, 'http://auth.mme.info/FilmSupplies', 0, '叶記有限责任公司', '物流业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1210, 'http://auth.zding10.us/ClothingShoesandJewelry', 0, '黄記物业代理有限责任公司', '房地产业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1211, 'http://image.konoseiko.biz/CenturionGardenOutdoor', 0, '岚有限责任公司', '饮食业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1212, 'http://www.hsw73.com/MusicalInstrument', 0, '邱記有限责任公司', '电讯業', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1213, 'https://drive.kinoshitaaos.jp/BaggageTravelEquipment', 0, '嘉伦有限责任公司', '饮食业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1214, 'https://www.hoyin2015.com/Beauty', 0, '唐記顾问有限责任公司', '咨询业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1215, 'http://auth.ikedahina912.org/ComputersElectronics', 0, '蒋有限责任公司', '咨询业', 0, NULL, '2024-12-06 09:00:00', '2024-12-06 09:00:00', 0);
INSERT INTO `rating` VALUES (1216, 'http://192.168.154.128:9000/quping-image/2024-12-06-20-53-03-iGMtLS.png', 5, 'docker', '容器化部署工具111111', 1, 1014, '2024-12-06 20:53:03', '2024-12-06 20:53:13', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `phone_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `first_login` tinyint(1) NULL DEFAULT 1 COMMENT '检查是否第一次登录',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `deleted` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1023 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1013, 'Punny', '18070403876', '654321', NULL, 0, '2024-12-06 20:43:15', '2024-12-06 23:49:08', 0);
INSERT INTO `user` VALUES (1014, 'bmJs8zNz9C', '17777777777', '123456', NULL, 1, '2024-12-06 20:49:09', '2024-12-06 20:49:09', 0);
INSERT INTO `user` VALUES (1015, 'Punny11', '18070403875', '111111', '', 0, '2024-12-07 00:15:48', '2024-12-07 00:16:11', 0);
INSERT INTO `user` VALUES (1016, '怕怕怕怕怕', '18888888888', '111111', NULL, 0, '2024-12-07 00:20:42', '2024-12-07 00:21:08', 0);
INSERT INTO `user` VALUES (1017, '问问人情味', '16666666666', 'qwefqwefqwe', NULL, 0, '2024-12-07 00:21:57', '2024-12-07 00:22:04', 0);
INSERT INTO `user` VALUES (1018, '阿道夫大师傅撒旦', '13333333333', '22r12r3fqf32', NULL, 0, '2024-12-07 00:24:52', '2024-12-07 00:24:57', 0);
INSERT INTO `user` VALUES (1019, '3123123', '18998989889', 'sdfwff23e22ef', NULL, 0, '2024-12-07 00:27:45', '2024-12-07 00:27:54', 0);
INSERT INTO `user` VALUES (1020, '223让3都3', '17676766766', '12312312wed23', NULL, 0, '2024-12-07 00:29:42', '2024-12-07 00:29:49', 0);
INSERT INTO `user` VALUES (1021, 'fsdfas', '17676767676', 'afasfwesadf', NULL, 0, '2024-12-07 00:30:35', '2024-12-07 00:30:39', 0);
INSERT INTO `user` VALUES (1022, 'sdfae', '14567657487', 'ewfwefdf3s', NULL, 0, '2024-12-07 00:33:08', '2024-12-07 00:33:12', 0);

-- ----------------------------
-- Table structure for user_rating_mapping
-- ----------------------------
DROP TABLE IF EXISTS `user_rating_mapping`;
CREATE TABLE `user_rating_mapping`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `rating_id` bigint NOT NULL,
  `score` int NOT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `deleted` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1027 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_rating_mapping
-- ----------------------------
INSERT INTO `user_rating_mapping` VALUES (1022, 1013, 1116, 5, '2024-12-06 20:48:23', '2024-12-06 20:48:23', 0);
INSERT INTO `user_rating_mapping` VALUES (1023, 1014, 1116, 3, '2024-12-06 20:49:14', '2024-12-06 20:53:53', 0);
INSERT INTO `user_rating_mapping` VALUES (1024, 1014, 1118, 3, '2024-12-06 20:50:11', '2024-12-06 20:50:11', 0);
INSERT INTO `user_rating_mapping` VALUES (1025, 1014, 1216, 5, '2024-12-06 20:53:13', '2024-12-06 20:53:13', 0);
INSERT INTO `user_rating_mapping` VALUES (1026, 1014, 1117, 2, '2024-12-06 20:58:52', '2024-12-06 20:58:55', 0);

SET FOREIGN_KEY_CHECKS = 1;
