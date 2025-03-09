/*
 Navicat Premium Data Transfer

 Source Server         : huabai
 Source Server Type    : MySQL
 Source Server Version : 80028 (8.0.28)
 Source Host           : localhost:3306
 Source Schema         : hua_anime

 Target Server Type    : MySQL
 Target Server Version : 80028 (8.0.28)
 File Encoding         : 65001

 Date: 09/03/2025 20:47:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for anime_index
-- ----------------------------
DROP TABLE IF EXISTS `anime_index`;
CREATE TABLE `anime_index`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '动漫id索引',
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '动漫名',
  `intro` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '简介',
  `issue_time` datetime NULL DEFAULT NULL COMMENT '发行时间',
  `month` tinyint NULL DEFAULT NULL COMMENT '几月番： 1 4 7 10',
  `is_new` tinyint NOT NULL DEFAULT 0 COMMENT '是否是新番（未完结是：1 完结不是 0）',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'isNew = 1 时 的状态',
  `act_role` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主演',
  `director` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '导演',
  `language` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '语言',
  `score` decimal(3, 1) NULL DEFAULT NULL COMMENT '评分 一位小数',
  `number` int NULL DEFAULT NULL COMMENT '评分次数',
  `type` tinyint(1) NULL DEFAULT 1 COMMENT '类型 0-剧场版 1-TV',
  `folder` bigint NULL DEFAULT NULL COMMENT '该动漫的文件夹id',
  `image` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（收集时间）',
  `update_Time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '动漫表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of anime_index
-- ----------------------------
INSERT INTO `anime_index` VALUES (2, '喜羊羊与灰太狼', '《喜羊羊与灰太狼》是一部非常受欢迎的中国原创动画系列，由广东原创动力文化传播有限公司制作。该系列讲述了在羊历3513年，青青草原上羊羊族群的幸福生活以及与灰太狼一家的趣事。动画中，喜羊羊是羊村里最聪明、跑得最快的羊，多次运用智慧赶跑灰太狼，是羊村的小英雄。美羊羊爱美、爱打扮，精通所有与“美丽”有关的东西。懒羊羊则因为贪吃贪睡，被灰太狼称为小胖子。灰太狼一家住在森林的黑色古堡里，他每天都要在老婆平底锅的敲打下前往羊村抓羊，但总是被机智的小羊们挫败', '2005-08-03 00:00:00', 7, 0, '已完结', '[\"祖晴\",\"邓玉婷\",\"梁颖\",\"刘红韵\",\"张琳\",\"赵娜\",\"高全胜\"]', '黄伟明', '普通话', NULL, NULL, 1, 2, '/dongman/2/2.webp', '2024-08-28 19:39:58', '2024-09-07 12:19:16');
INSERT INTO `anime_index` VALUES (3, '哆啦a梦第4季', '大雄有一天打开自己的课桌，一只猫型机器人突然从抽屉里跳了出来，而这就是哆啦A梦。它是由大雄的后代从22世纪里给送来的，目的是帮助大雄解决许多他暂时无法解决的问题，并且尽可能地满足大雄的愿望。', '1969-01-01 00:00:00', 1, 0, '已完结', '[\"\"]', '内详', '日语', NULL, NULL, 1, 3, '/dongman/3/3.webp', '2024-08-29 10:51:08', '2024-09-05 15:27:14');
INSERT INTO `anime_index` VALUES (4, '机动战士高达 水星的魔女', '星元122年――在众多企业扩展到宇宙并构筑巨大经济圈的时代，有一名少女自边境地区·水星转来MS产业的最大企业“贝纳里特集团”旗下所经营的“阿斯提卡西亚高等专门学园”。她名为苏莱塔·墨丘利。内心纯真的少女随着胸口鲜红的闪烁亮光逐步迈向新世界。', '2022-10-02 00:00:00', 10, 0, '已完结', '[\"田头真理恵（主人物设定）\",\"戸井田珠里\",\"高谷浩利\",\"安藤良\",\"冈田有章\"]', '小林寛', '日语', 4.0, 1, 1, 4, '/dongman/4/4.jpg', '2024-08-29 22:44:09', '2024-08-30 19:43:47');
INSERT INTO `anime_index` VALUES (5, '机动战士高达 水星的魔女 第二季', 'A.S. 122年―― 这是许多企业进出宇宙，建构起巨大经济圈的时代。 从水星转学到阿斯提卡西亚高等专门学园的斯莱塔·墨丘利， 作为米奥莉奈的新郎，作为股份有限公司高达的一员，度过了充满邂逅与刺激的学园生活。 自奎达工厂发生的事件至今已过了两个星期。 斯莱塔身处学园，日日衷心期待米奥莉奈再会之日的到来。 而米奥莉奈则置身贝纳里特集团本部，紧盯着父亲的状态。 新的困难袭向两人，并且将被迫做出决断。 少女们将各自的思想心怀于胸，前行面对高达所带来的强大诅咒。', '2023-04-09 00:00:00', 4, 0, '已完结', '[\"田头真理恵（主人物设定）、戸井田珠里、高谷浩利、安藤良、冈田有章\"]', '小林寛', '日语', NULL, NULL, 1, 22, '/dongman/5/5.webp', '2024-09-01 20:58:23', '2024-09-01 22:05:13');
INSERT INTO `anime_index` VALUES (6, '心灵感应', '憧憬宇宙、不擅长表达的少女小之星海果，遇见了自称外星人、有心灵感应能力的少女明内幽。想去太空的海果和想回太空的小幽，想要用自己的双手建造可以飞去宇宙的火箭，故事就此展开。', '2023-10-09 00:00:00', 10, 0, '已完结', '[\"船户百合绘 / 深川芹亚 / 永牟田萌 / 青木志贵 / 羊宫妃那 / 高森奈津美 / 大野柚布子 / 白砂沙帆 / 川井田夏海 / 伊藤美来 / 黑木穗乃香/东城日沙子\"]', '小森香织', '日语', NULL, NULL, 1, 23, '/dongman/6/6.webp', '2024-09-01 21:04:19', '2024-09-01 22:21:24');
INSERT INTO `anime_index` VALUES (7, 'NOIR(黑色天使)', '失去记忆的主人公夕丛雾香与以欧洲黑社会为舞台靠“隐秘的工作”生存的杀手蜜丽优·布可，因某个事件为契机组成了搭档，在黑社会作为杀手开始工作。\n两人的组合名为“NOIR”。实际上，其名字蕴涵着着她们意想不到的深意。\n拥有天生的战士才能的雾香和流着科西嘉家族的血的孤独专业杀手蜜丽优，在欧洲、中东、亚洲执行各种“任务”时，彼此不熟的雾香和蜜丽优，逐渐认可了对方为搭档。然而，神秘组织“索达”正悄然接近两人。\n索达的真面目和目的是什么？然后“NOIR”的名字又有着什么意思？\n在与共同的敌人拼死战斗的过程中，雾香和蜜丽优之间萌生了真正的搭档友情。但是，一个冲击的事实却袭向两人。取回失去的过去的雾香、发誓为家族报仇的蜜丽优，她们的战斗从现在开始。', '2001-04-06 00:00:00', 4, 0, '已完结', '[\"桑岛法子\",\"三石琴乃\",\"Tarako\",\"久川绫\"]', '真下耕一', '日语', NULL, NULL, 1, 49, '/dongman/7/7.webp', '2024-09-02 21:46:02', '2024-09-02 21:46:33');
INSERT INTO `anime_index` VALUES (8, '终末的伊泽塔', '——如果公主殿下与我约定的话，我便会为了公主殿下而战。身为最后的魔女——\n公元1939年，帝国主义国家日耳曼尼亚帝国突然开始了对邻国的侵略。战火瞬间蔓延到整个欧洲，时代被卷入了大战的漩涡之中。之后，1940年，日耳曼尼亚将其矛头指向了青山绿水的阿尔卑斯小国——埃尔施塔特大公国。 [4]', '2016-10-01 00:00:00', 10, 0, '已完结', '[\"茜屋日海夏、早见沙织、花泽香菜、诹访部顺一、花江夏树、内田彩\"]', '藤森雅也、根岸宏树（助监督）', '日语', NULL, NULL, 1, 77, '/dongman/8/8.webp', '2024-09-03 11:53:32', '2024-09-03 11:54:03');
INSERT INTO `anime_index` VALUES (9, '关于我转生变成史莱姆这档事 第三季', '利姆路击败克雷曼，並正式成为魔王。魔王盛宴（Walpurgis）结束后，利姆路支配的地区已经扩大至整个朱拉大森林。由於已经能预见各种族的代表受此影响將蜂拥而至前来致意，利姆路灵机一动，决定乾脆举行让魔王利姆路正式亮相，同时吸引新居民搬入坦派斯特的「开国祭」。 另一方面，在將魔物视为敌人的鲁米纳斯教大本营・神圣法皇国鲁贝利欧斯，圣骑士团长「日向」收到了来自利姆路的讯息。然而，那却是遭到不明人士窜改的开战宣言。在收到日向正朝著坦派斯特接近的报告后，利姆路做出的决定会是ーー！？　 为了看清真正的敌人和盟友，並追求「人魔共荣圈」的理想，利姆路的新挑战即將开始。　', '2024-04-05 00:00:00', 4, 1, '连载中', '[\"江畑谅真\"]', '中山敦史', '日语', NULL, NULL, 1, 90, '/dongman/9/9.jpg', '2024-09-03 12:03:40', '2024-09-03 12:07:23');
INSERT INTO `anime_index` VALUES (10, '神之塔 第二季', '衝撃的なラストシーンで幕を闭じた第１期から6年后、 选別者にとって狭き门である20阶に居たのは、 何度脱落しても「塔の王になる」と挑戦を続ける男・王野 成おうじ なる。 莫大な受験料のために借金を抱える王野は、 最后のチャンスとして再び试験に挑戦し、 そこで圧倒的な强さを持つ谜めいた长髪の男・ビオレと出会うが、 ビオレは塔の支配者であるザハードに対抗する犯罪组织 「ＦＵＧ」のスレイヤー候补であった。', '2024-07-07 00:00:00', 7, 1, '连载中', '[\"谷野美穂、北泽精吾、鹿岛巧光\"]', '铃木慧 ', '日语', NULL, NULL, 1, 92, '/dongman/10/10.jpg', '2024-09-03 12:11:53', '2024-09-03 12:13:05');
INSERT INTO `anime_index` VALUES (11, '食灵-零-', '相信爱、能将所爱之人抹杀吗？\n为憎恶摇晃的灵魂使之觉醒了的是谁？那徬徨黑暗的街，悲哀入暮而迷惑人。被命运的羁绊所维系的二人——\n切断宿命的锁链，讨伐恶灵。', '2008-10-05 00:00:00', 10, 0, '已完结', '[\"茅原实里、白石稔、高桥伸也、水原薰\"]', '青木英', '日语', NULL, NULL, 1, 94, '/dongman/11/11.webp', '2024-09-03 21:14:15', '2024-09-03 21:14:46');
INSERT INTO `anime_index` VALUES (12, '魔女之旅', '某个地方有一位旅人，她的名字是伊蕾娜。是一位年纪轻轻就成了魔法使中最上位「魔女」的天才。因为向往着幼时读过的旅行故事，随意地进行着漫长的旅行。在这个广阔的世界里自由地漫步，接触着形形色色有趣的人，体味着人们美好的日常生活，她作为一名旅人，不带有任何目的地接触着各种国家的各色人群。还有同样数量的——「不必理会我。我只是一介旅人罢了。接下来还得继续前往下一个地方呢。」由魔女伊蕾娜所连接的，关于相遇和离别的故事……。', '2020-10-02 00:00:00', 10, 0, '已完结', '[\"本渡枫\",\"花泽香菜\",\"黑泽朋世\",\"日笠阳子\"]', '洼冈俊之', '日语', NULL, NULL, 1, 107, '/dongman/12/12.webp', '2024-09-04 22:34:14', '2024-09-04 22:54:17');
INSERT INTO `anime_index` VALUES (13, '莉可丽丝', '安宁的日常——背后却暗藏秘密将犯罪防患于未然的秘密组织——“DA（Direct Attack）”隶属于DA的少女特工——“莉可丽丝”理所当然的日常，都要归功于她们。咖啡厅“莉可莉可” 作为DA支部，员工有号称史上最强莉可丽丝的精英·锦木千束、优秀却暗藏隐情的莉可丽丝·井上泷奈。这里供应的不光是咖啡和甜品，还有照顾孩子、代为购物、教外国人日语等服务，全都不像是“莉可丽丝”会做的事。自由随性又乐天的和平主义者·千束和效率至上的泷奈，反差巨大的两人组成搭档，开始了忙忙碌碌的每一天。', '2022-07-02 00:00:00', 7, 0, '已完结', '[\"锦木千束: 安济知佳 井上泷奈: 若山诗音 中原瑞希: 小清水亚美 胡桃: 久野美咲 米卡: 榊　孝辅\"]', '足立慎吾', '日语', NULL, NULL, 1, 119, '/dongman/13/13.webp', '2024-09-04 22:55:55', '2024-09-04 22:56:13');
INSERT INTO `anime_index` VALUES (14, '花园里的吸血鬼', '一个冬天的时代。人类在和吸血鬼的战争中失败，在地球上失去了几乎所有的居住区。幸存下来的人们在小城市里筑起了光之墙，在保护自身的同时，希望能再扩大生存圈。在压抑的生活中，主人公桃子希望和作为敌人的吸血鬼共存。吸血鬼女王菲尔曾经热爱人类，从战场上消失了。在战火蔓延的都市中，两人实现了命运般的相遇。很久以前，有一个吸血鬼和人类共同生活的乐园。这是一个为了寻求乐园而旅行的少女和吸血鬼的故事。', '2022-05-16 00:00:00', 4, 0, '已完结', '[\"潘惠美 / 小林优 / 小林千晃 / 深见梨加 / 东地宏树\"]', '牧原亮太郎', '日语', NULL, NULL, 1, 134, '/dongman/14/14.webp', '2024-09-05 11:51:34', '2024-09-05 11:52:02');
INSERT INTO `anime_index` VALUES (15, '失忆煽动WIXOSS', '池袋——在这条街道度过幼年时期的高中2年生穗村铃子回来了。\n“要一直做朋友喔！”和自幼便很要好的森川千夏一同发过誓的重要记忆。说不定还能和千夏见面……仅仅这件事，使铃子感到心潮澎湃。\n铃子在转学去的高中也没能很快融入集体，马上就成为不合群的存在，就在这样的某一天，\n能够学会“WIXOSS”的话说不定就能交到朋友……这样想着，她在放学路上到附近的卡牌商店购入了卡组。回到家将卡组开封之后，描绘在卡牌上的少女开始动起来，并开口说话了。\n“欢迎你，selector”被选为selector的人类，必须赌上装满自己全部记忆的5枚硬币来参加战斗。取回所有的硬币并赢得胜利的话，即可从游戏当中逃脱。相反，失去全部的硬币，并惨遭败北的话，其惩罚是——。\n“Lostorage”——被卷入这毫无条理的游戏中的铃子会迎来怎样的命运！？\n以及，与铃子同时，千夏的手中也…… ', '2016-10-07 00:00:00', 10, 0, '已完结', '[\"穗村铃子：桥本千波 森川千夏：井口裕香 莉露：伊藤静 梅露：金元寿子 娜娜希：井泽诗织 御影阪奈：久保由利香 鸣海胜：兴津和幸 白井翔平：菅原慎介 阿雅：山冈百合 多娜：洲崎绫 古滋子：后藤沙绪里 墨田壮：间岛淳司 小柴莉绪：日冈夏美 雪野篝：西明日香 雪目：生天目仁美 里见红：中村悠一\"]', '樱美胜志', '日语', NULL, NULL, 1, 141, '/dongman/15/15.webp', '2024-09-06 17:30:49', '2024-09-06 17:32:14');
INSERT INTO `anime_index` VALUES (16, 'MADLAX(玛德莱克丝)', '故事围绕着两位表面上看来没有共同点也互不了解对方存在的年轻女性展开。女主角玛德莱克丝是一名活跃于内战中的国家盖萨索尼卡(Gazth-Sonika)里的传奇的雇佣兵和暗杀特务；另一位女主角玛格丽特・巴顿，则是宁静的欧洲国家那芙勒斯一个富裕贵族家庭的唯一继承人。在故事开篇的12年前，玛格丽特和她的母亲乘坐的飞机在盖萨索尼卡坠毁。那次事件之后，飞机上的乘客以及担任救援人员指挥工作的玛格丽特的父亲一直行踪不明。独自一人回到了那芙列斯的玛格丽特，失去了全部的记忆，唯一能回想起的只有“玛德莱克丝”这个词。\n居住的环境与性格截然不同的两名少女，为了追寻真相而踏上了各自的旅程，在与庞大的犯罪集团“安纺”的周旋中，两个人的命运渐渐开始产生交集。', '2004-04-05 00:00:00', 4, 0, '已完结', '[\"玛德莱克丝：小林沙苗，玛格丽特·巴顿：桑岛法子，艾莉诺雅·贝克：内川蓝维\"]', '真下耕一', '日语', NULL, NULL, 1, 154, '/dongman/16/16.webp', '2024-09-06 17:39:55', '2024-09-06 17:40:12');
INSERT INTO `anime_index` VALUES (17, '失忆融合WIXOSS', '在一段短暂的和平岁月过后，selector battle的黑影再次悄然逼近——\n最先察觉到异变的水岛清衣，为了给黑暗的连锁画上句号，而采取了行动。\n这次的对战中，加入了一张新卡牌“钥匙”，\n还被编入了一条和以往完全不同的规则，\n主谋及其目的依然笼罩着谜团，黑暗不断地加深。\n穗村铃子、森川千夏、御影坂奈、小凑露子、红林游月、植村一衣、苍井晶…\n再次集结的少女们，\n“愿望”和“记忆”，再加上“钥匙”，\n赌上一切的最后的对战，现在开始…！', '2018-04-06 00:00:00', 4, 0, '已完结', '[\"大西沙织、桥本千波、加隈亚衣和大和田仁美\"]', '吉田里纱子', '日语', NULL, NULL, 1, 182, '/dongman/17/17.jpg', '2024-09-07 18:19:35', '2024-09-07 18:19:53');
INSERT INTO `anime_index` VALUES (18, '选择感染者WIXOSS', '希望、愿望、欲望……怀着各种心情的少女们渐渐地被吞噬进危险游戏的漩涡之中。自从搬家到城市，虽然没有朋友但对生活却也没有特别不满的女国中生小奏露子。露子的哥哥看不惯奶奶替露子感到忧心，于是买了一套很受初高中男女生欢迎的卡片游戏WICOSS给露子。露子打开哥哥给的卡片游戏，看着代表玩家分身的LRIG卡片时，绘制在卡片上的LRIG卡片少女突然动了起来。那少女不在意惊讶不已的露子，只一味的对露子说想要对战。露子将少女命名为小玉。露子对这不可思议的事情感到困惑，就在此时，露子的同学红林游月，突然称呼露子为Selector，并且要求进行对战。根据游月的说法，拿到具有自我意识的LRIG卡片的人，都是被选上的少女，被称为Selector。Selector少女彼此对战，持续获胜的一方最终能成为实现自己理想的“梦限少女”……小玉是谁? Selector是什么? 梦限少女又到底是什么…..? ', '2014-04-04 00:00:00', 4, 0, '已完结', '[\"加隈亚衣、久野美咲、佐仓绫音、茅野爱衣、赤崎千夏\"]', '佐藤卓哉', '日语', NULL, NULL, 1, 195, '/dongman/18/18.jpg', '2024-09-07 18:29:47', '2024-09-07 18:30:23');
INSERT INTO `anime_index` VALUES (19, '选择扩散者WIXOSS', '希望、愿望、欲望。\n怀着各种心情，\n少女们渐渐地被吞噬进危险游戏的漩涡之中——\n\n电视动画的第二季。以初高中生为主要受众的大人气卡牌游戏“WIXOSS”中，有着“拥有意志的少女角色卡牌”，即分身卡。只有特别的少女才能听到分身的声音，而拥有分身的selector们为了实现各自的“愿望”，在场上展开卡牌对战。初次接触WIXOSS时就听到了分身声音的小凑露子，收到了同校的selector红林游月发起的挑战。露子将自己的分身命名为小玉，成为一名selector，踏上战斗之路……。[1]', '2014-10-04 00:00:00', 10, 0, '已完结', '[\"加隈亚衣 久野美咲 佐仓绫音 川澄绫子 茅野爱衣 高桥未奈美 赤崎千夏 大西沙织 濑户麻沙美 钉宫理惠 杜野真子 新井里美 石谷春贵 久保田民绘 小林裕介 巽悠衣子 种田梨沙\"]', '佐藤卓哉', '日语', NULL, NULL, 1, 209, '/dongman/19/19.jpg', '2024-09-07 18:40:40', '2024-09-07 18:41:01');
INSERT INTO `anime_index` VALUES (20, '柑橘味香气', '虽是辣妹却未经历初恋的女高中生柚子，由于父母再婚的原因而进入了女子高中。因无法交到男友而不满爆发的转学第一天，她与黑发的美人学生会长芽衣以最糟糕的形式相遇了。不仅如此，她与成为义理姐妹的芽衣开始在同一个房间里生活……', '2018-01-06 00:00:00', 1, 0, '已完结', '[\"竹达彩奈、津田美波、井泽诗织、久保由利香、藤井雪代\"]', '高桥丈夫', '日语', NULL, NULL, 1, 223, '/dongman/20/20.jpg', '2024-09-07 19:50:43', '2024-09-07 19:51:36');
INSERT INTO `anime_index` VALUES (21, '幸腹涂鸦', '该作描绘了初中二年级的女主角町子凉，因父母身在海外，自从奶奶过世后就独自一人生活。有一天她在阿姨的拜托下，开始于周末款待森野麒麟到家中暂住，以便来往彼此都有入读的美术预科学校。之后他们遇上志趣相合的同学椎名，一同开始了用烹调的美食带来幸福的治愈向故事。', '2015-01-08 00:00:00', 1, 0, '已完结', '[\"町子凉：佐藤利奈 森野麒麟：大龟明日香 椎名：小松未可子 内木由纪：井口裕香 露子：小林优 町子明：野中蓝\"]', '新房昭之（总监督）、龙轮直征', '日语', NULL, NULL, 1, 236, '/dongman/21/21.webp', '2024-09-07 19:57:51', '2024-09-07 19:58:12');
INSERT INTO `anime_index` VALUES (22, '战翼的希格德莉法', '某日地球上突然出现了威胁各种生命的谜样敌人“柱”，对其束手无策的人类被逼入了绝境。此时向人类伸出援手的，是自称为“奥丁”的神，奥丁将战斗少女“女武神”以及能成为她们翅膀的英灵机赐予人类，并展开反击。数年后，身披战翼的女武神们和支持着女武神的男人们，翱翔于天际与柱持续激战着。日本也无法幸免，三名女武神与伫立于灵峰富士山的强大对手对峙，就在这个时候，出现了一名来自欧洲的王牌飞行员……', '2020-10-03 00:00:00', 10, 0, '已完结', '[\"山村响、稗田宁宁、M·A·O、菊池纱矢香\"]', '德田大贵', '日语', NULL, NULL, 1, 237, '/dongman/22/22.webp', '2024-09-07 20:00:44', '2024-09-07 20:01:33');
INSERT INTO `anime_index` VALUES (23, '青春纪行', '19岁的多田万里（古川慎 配音）只身赴东京读大学，入学当日遇到同样是法学系新生的柳泽光央（石川界人 配音），二人随即成为朋友。此时，一个光芒万丈、引人瞩目的漂亮大小姐出现在二人回校路上，她手持一束玫瑰，突然殴打柳泽。原来她叫加贺香子（崛江由衣 配音），是柳泽的青梅竹马， 她为人强势，有自己一套完美的“人生剧本“，而剧本的男主角，就是她狂热喜欢着的柳泽。柳泽视她为洪水猛兽，唯恐避之不及，然而还是逃不过她的追随。面对这么一位耀眼的大小姐，看起来平平凡凡的万里，又会与之碰撞出怎么样的青春故事？\n　　新大学，新朋友，新住所，对于万里来说，大学生活显得如此新鲜，值得期待。未来的一切，闪着金色的光芒。', '2013-10-03 00:00:00', 10, 0, '已完结', '[\"堀江由衣 / 古川慎 / 茅野爱衣 / 石川界人 / 木户衣吹 /\"]', '今千秋', '日语', NULL, NULL, 1, 262, '/dongman/23/23.webp', '2024-09-08 11:19:42', '2024-09-08 11:20:01');
INSERT INTO `anime_index` VALUES (24, '通往夏天的隧道，再见的出口', '我去见那天的你。\n浦岛隧道——传闻只要进入那个隧道，就可以得到任何想要的东西。但是，作为交换……\n看似个性捉摸不定，其实是因过去的意外内心怀有伤痛的高中生塔野薰，与坚定自我的态度，私下则因和自己的人生理想不同而苦恼的转学生花城杏子，他们两人为了调查隧道，并实现各自的愿望，而缔结了合作关系……\n这是一个发生在某个偏僻乡村的令人难忘的夏日乡愁和疾驰的故事。', '2022-09-09 00:00:00', 10, 0, '已完结', '[\"铃鹿央士、饭丰万理江、畠中祐、小宫有纱\"]', '田口智久', '日语', NULL, NULL, 0, 287, '/dongman/24/24.webp', '2024-09-08 11:56:58', '2024-09-08 11:57:27');
INSERT INTO `anime_index` VALUES (25, '紧扣的星星', '第一段，恋爱\n第二段，混乱', '2012-04-21 00:00:00', 4, 0, '已完结', '[\"今井麻美，一色みく\"]', ' ', '英语', NULL, NULL, 1, 289, '/dongman/25/25.webp', '2024-09-08 16:26:25', '2024-09-08 16:27:01');
INSERT INTO `anime_index` VALUES (26, '惊爆草莓', '阿斯特拉艾亚的山丘上，建有三家女子学校。圣米雅特尔女子学园（圣ミアトル女学园）、圣斯毕卡女子学院（圣スピカ女学院）、圣露·莉姆（圣ル·リム女学校）女子学校。而且，在那片土地的尽头是三家学校共用的宿舍----草莓宿舍。阿斯特拉艾亚的山丘…那里是不允许男子踏入的神圣领域…\n因为某些事情而被编入圣米雅特尔女子学园四年', '2006-04-03 00:00:00', 4, 0, '已完结', '[\"生天目仁美、中原麻衣、清水爱、斋藤千和\"]', '迫井政行', '日语', NULL, NULL, 1, 292, '/dongman/26/26.webp', '2024-09-08 16:28:58', '2024-09-08 16:29:54');
INSERT INTO `anime_index` VALUES (27, '花吻在上', '川村玲绪是个娇生惯养、性格固执的转学生，也从来不愿结交任何朋友，由此陷入了被孤立的境地。身为“班级协调者”的泽口麻衣决定寻找机会使玲绪与班上同学打成一片。可是玲绪并不是那么配合，反而总是躲避着麻衣。出于兴趣，麻衣不断用死缠烂打的方式来捉弄玲绪。于是，玲绪再也抑制不住自己的心情：“我喜欢你！”她就这样向麻衣告白了。这时，麻衣也不得不面对自己的心意。\n', '2010-07-30 00:00:00', 7, 0, '已完结', '[\"杏花（川村玲绪 役）和泉あやか（泽口麻衣 役）\"]', ' ', '日语', NULL, NULL, 1, 319, '/dongman/27/27.webp', '2024-09-08 17:11:16', '2024-09-08 17:11:49');
INSERT INTO `anime_index` VALUES (28, '樱Trick', '高山春香与园田优两位好友，一起升学至3年后即将面临合并废校的美里西高中的故事。', '2014-01-09 00:00:00', 1, 0, '已完结', '[\"户松遥、井口裕香、相坂优歌、五十岚裕美\"]', '石仓贤一', '日语', NULL, NULL, 1, 321, '/dongman/28/28.webp', '2024-09-08 17:13:13', '2024-09-08 17:13:26');
INSERT INTO `anime_index` VALUES (29, '恶魔之迷', '故事舞台设定在一个名为“10年黑组”的全住宿制女子高中的班级，这个班级里的12名学生都是由暗杀者构成的，她们全都瞄准了她们的同学——一之濑晴的性命。即使她的性命被众多人觊觎，一之濑晴依然发誓一定要活着毕业。另一方面，同样也是准备夺取一之濑晴性命的暗杀者——转校生东兔角却在一次次接近对方时，渐渐地被晴所吸引。在同学们对晴展开攻击之时，兔角则以保护者的姿态与她们开始了战斗。\n', '2014-04-03 00:00:00', 4, 0, '已完结', '[\"诹访彩花、金元寿子\"]', '草川启造', '日语', NULL, NULL, 1, 334, '/dongman/29/29.webp', '2024-09-10 13:28:11', '2024-09-10 13:28:39');
INSERT INTO `anime_index` VALUES (30, '圣洁天使', '某天，世界连结到了一起。突然打开的门Highlow，将5个世界连结。\n我们所居住的蓝色世界——地球\n夜与魔法所支配的黑色世界——Darkness Embrace\n祈祷与诸神所守护的红色世界——Terra Rubri Aurora\n科学与电脑所管理的白色世界——System White Egma\n武器与军队所统治的绿色世界——Gruneschild\n天使、恶魔、妖精、魔女、女神。仅在童话中出现的存在，以现实之物现身。\n与此同时，觉醒了特殊能力——Excceed的少女们，开始出现在各个世界。\n她们被称作“Progress”，并被集中到对她们展开保护、育成的巨大学园都市“青兰学园”。\n在这所学园中，少女们相遇了。\n与一同编织起牵绊的伙伴们一起。面对5个世界共同的敌人，将世界导向终焉的“Uroboros”。\n这个世界，将要失去。能将其拯救的，唯有被选中的少女Progress们——。', '2016-07-10 00:00:00', 7, 0, '已完结', '[\"寿美菜子、原由实、丰崎爱生、立花理香、布里德卡特·塞拉·惠美、田村由香里\"]', '田村正文', '日语', NULL, NULL, 1, 348, '/dongman/30/30.webp', '2024-09-10 23:16:19', '2024-09-10 23:16:30');
INSERT INTO `anime_index` VALUES (31, '红壳的潘多拉', '自幼患难治之症，六年前遭遇事故失去双亲，自己也身受重伤的少女——七转福音，变成了除了大脑全身都是义体的孤独少女。被远亲拜托而前往人工岛“赛男克鲁岛”的她，在那里遇到了猫耳女仆装的美少女型杀人机器克拉里昂…百合与羞耻PLAY的故事就此展开……', '2016-01-08 00:00:00', 1, 0, '已完结', '[\"福沙奈惠、沼仓爱美、田中敦子、森田顺平、松田飒水、村川梨衣\"]', '名和宗则', '日语', NULL, NULL, 1, 361, '/dongman/31/31.webp', '2024-09-11 22:41:25', '2024-09-11 22:42:11');

-- ----------------------------
-- Table structure for anime_videos
-- ----------------------------
DROP TABLE IF EXISTS `anime_videos`;
CREATE TABLE `anime_videos`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '动漫拥有视频的id',
  `anime_id` int UNSIGNED NOT NULL COMMENT '动漫id',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频标题',
  `rank` int NULL DEFAULT 1 COMMENT '排序',
  `file` bigint NULL DEFAULT NULL COMMENT '文件id',
  `crate_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `image` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片url',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `anime_videos_anime_id_index`(`anime_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 328 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of anime_videos
-- ----------------------------
INSERT INTO `anime_videos` VALUES (1, 2, '灰太狼进攻', 1, NULL, '2024-08-28 21:29:08', NULL);
INSERT INTO `anime_videos` VALUES (2, 4, '第一集', 1, 5, '2024-08-29 22:47:56', NULL);
INSERT INTO `anime_videos` VALUES (3, 4, '第二集', 2, 9, '2024-08-30 00:31:13', NULL);
INSERT INTO `anime_videos` VALUES (4, 4, '第三集', 3, 10, '2024-08-30 13:40:25', NULL);
INSERT INTO `anime_videos` VALUES (5, 4, '第四集', 4, 12, '2024-08-30 14:47:43', NULL);
INSERT INTO `anime_videos` VALUES (6, 4, '第五集', 5, 14, '2024-08-30 16:09:29', NULL);
INSERT INTO `anime_videos` VALUES (7, 4, '第6集', 6, 15, '2024-08-30 22:36:33', NULL);
INSERT INTO `anime_videos` VALUES (8, 4, '第7集', 7, 16, '2024-08-30 22:37:35', NULL);
INSERT INTO `anime_videos` VALUES (9, 4, '第8集', 8, 17, '2024-08-30 22:39:27', '/dongman/4/4_9.png');
INSERT INTO `anime_videos` VALUES (10, 4, '第9集', 9, 20, '2024-08-30 22:39:36', NULL);
INSERT INTO `anime_videos` VALUES (11, 4, '第10集', 10, 18, '2024-08-30 22:53:33', NULL);
INSERT INTO `anime_videos` VALUES (12, 4, '第11集', 11, 19, '2024-08-30 22:53:36', NULL);
INSERT INTO `anime_videos` VALUES (13, 4, '第12集', 12, 21, '2024-08-30 22:53:39', NULL);
INSERT INTO `anime_videos` VALUES (15, 5, '第1集', 1, 24, '2024-09-01 22:05:27', NULL);
INSERT INTO `anime_videos` VALUES (16, 5, '第2集', 2, 25, '2024-09-01 22:05:28', NULL);
INSERT INTO `anime_videos` VALUES (17, 5, '第3集', 3, 26, '2024-09-01 22:05:30', NULL);
INSERT INTO `anime_videos` VALUES (18, 5, '第4集', 4, 27, '2024-09-01 22:05:32', NULL);
INSERT INTO `anime_videos` VALUES (19, 5, '第5集', 5, 28, '2024-09-01 22:05:35', NULL);
INSERT INTO `anime_videos` VALUES (20, 5, '第6集', 6, 30, '2024-09-01 22:05:36', NULL);
INSERT INTO `anime_videos` VALUES (21, 5, '第7集', 7, 29, '2024-09-01 22:05:37', NULL);
INSERT INTO `anime_videos` VALUES (22, 5, '第8集', 8, 31, '2024-09-01 22:05:39', NULL);
INSERT INTO `anime_videos` VALUES (23, 5, '第9集', 9, 32, '2024-09-01 22:05:41', NULL);
INSERT INTO `anime_videos` VALUES (24, 5, '第10集', 10, 33, '2024-09-01 22:05:42', NULL);
INSERT INTO `anime_videos` VALUES (25, 5, '第11集', 11, 34, '2024-09-01 22:05:44', NULL);
INSERT INTO `anime_videos` VALUES (26, 5, '第12集', 12, 48, '2024-09-01 22:05:46', NULL);
INSERT INTO `anime_videos` VALUES (27, 6, '第1集', 1, 36, '2024-09-01 22:21:30', NULL);
INSERT INTO `anime_videos` VALUES (28, 6, '第2集', 2, 37, '2024-09-01 22:21:32', NULL);
INSERT INTO `anime_videos` VALUES (29, 6, '第3集', 3, 38, '2024-09-01 22:27:28', NULL);
INSERT INTO `anime_videos` VALUES (30, 6, '第4集', 4, 39, '2024-09-01 22:27:39', NULL);
INSERT INTO `anime_videos` VALUES (31, 6, '第5集', 5, 40, '2024-09-01 22:27:41', NULL);
INSERT INTO `anime_videos` VALUES (32, 6, '第6集', 6, 41, '2024-09-01 22:27:42', NULL);
INSERT INTO `anime_videos` VALUES (33, 6, '第7集', 7, 42, '2024-09-01 22:27:44', NULL);
INSERT INTO `anime_videos` VALUES (34, 6, '第8集', 8, 43, '2024-09-01 22:27:46', NULL);
INSERT INTO `anime_videos` VALUES (35, 6, '第9集', 9, 44, '2024-09-01 22:27:48', NULL);
INSERT INTO `anime_videos` VALUES (36, 6, '第10集', 10, 45, '2024-09-01 22:27:50', NULL);
INSERT INTO `anime_videos` VALUES (37, 6, '第11集', 11, 47, '2024-09-01 22:27:52', NULL);
INSERT INTO `anime_videos` VALUES (38, 6, '第12集', 12, 46, '2024-09-01 22:27:54', NULL);
INSERT INTO `anime_videos` VALUES (40, 7, '第1集', 1, 76, '2024-09-02 21:46:37', '/dongman/7/7_40.jpg');
INSERT INTO `anime_videos` VALUES (41, 7, '第2集', 2, 51, '2024-09-02 21:46:39', '/dongman/7/7_41.jpg');
INSERT INTO `anime_videos` VALUES (42, 7, '第3集', 3, 52, '2024-09-02 21:46:40', '/dongman/7/7_42.jpg');
INSERT INTO `anime_videos` VALUES (43, 7, '第4集', 4, 54, '2024-09-02 21:46:42', '/dongman/7/7_43.jpg');
INSERT INTO `anime_videos` VALUES (44, 7, '第5集', 5, 53, '2024-09-02 21:46:43', '/dongman/7/7_44.jpg');
INSERT INTO `anime_videos` VALUES (45, 7, '第6集', 6, 55, '2024-09-02 21:46:44', '/dongman/7/7_45.jpg');
INSERT INTO `anime_videos` VALUES (46, 7, '第7集', 7, 56, '2024-09-02 21:46:46', '/dongman/7/7_46.jpg');
INSERT INTO `anime_videos` VALUES (47, 7, '第8集', 8, 57, '2024-09-02 21:46:47', '/dongman/7/7_47.jpg');
INSERT INTO `anime_videos` VALUES (48, 7, '第9集', 9, 58, '2024-09-02 21:46:49', '/dongman/7/7_48.jpg');
INSERT INTO `anime_videos` VALUES (49, 7, '第10集', 10, 59, '2024-09-02 21:46:51', '/dongman/7/7_49.jpg');
INSERT INTO `anime_videos` VALUES (50, 7, '第11集', 11, 60, '2024-09-02 21:46:53', '/dongman/7/7_50.jpg');
INSERT INTO `anime_videos` VALUES (51, 7, '第12集', 12, 61, '2024-09-02 21:46:54', '/dongman/7/7_51.jpg');
INSERT INTO `anime_videos` VALUES (52, 7, '第13集', 13, 62, '2024-09-02 21:46:56', '/dongman/7/7_52.jpg');
INSERT INTO `anime_videos` VALUES (53, 7, '第14集', 14, 64, '2024-09-02 21:46:58', '/dongman/7/7_53.jpg');
INSERT INTO `anime_videos` VALUES (54, 7, '第15集', 15, 63, '2024-09-02 21:46:59', '/dongman/7/7_54.jpg');
INSERT INTO `anime_videos` VALUES (55, 7, '第16集', 16, 65, '2024-09-02 21:47:01', '/dongman/7/7_55.jpg');
INSERT INTO `anime_videos` VALUES (56, 7, '第17集', 17, 66, '2024-09-02 21:47:03', '/dongman/7/7_56.jpg');
INSERT INTO `anime_videos` VALUES (57, 7, '第18集', 18, 67, '2024-09-02 21:47:04', '/dongman/7/7_57.jpg');
INSERT INTO `anime_videos` VALUES (58, 7, '第19集', 19, 68, '2024-09-02 21:47:06', '/dongman/7/7_58.jpg');
INSERT INTO `anime_videos` VALUES (59, 7, '第20集', 20, 69, '2024-09-02 21:47:07', '/dongman/7/7_59.jpg');
INSERT INTO `anime_videos` VALUES (60, 7, '第21集', 21, 70, '2024-09-02 21:47:09', '/dongman/7/7_60.jpg');
INSERT INTO `anime_videos` VALUES (61, 7, '第22集', 22, 71, '2024-09-02 21:47:11', '/dongman/7/7_61.jpg');
INSERT INTO `anime_videos` VALUES (62, 7, '第23集', 23, 72, '2024-09-02 21:47:13', '/dongman/7/7_62.jpg');
INSERT INTO `anime_videos` VALUES (63, 7, '第24集', 24, 73, '2024-09-02 21:47:15', '/dongman/7/7_63.jpg');
INSERT INTO `anime_videos` VALUES (64, 7, '第25集', 25, 74, '2024-09-02 21:47:16', '/dongman/7/7_64.jpg');
INSERT INTO `anime_videos` VALUES (65, 7, '第26集', 26, 75, '2024-09-02 21:47:18', '/dongman/7/7_65.jpg');
INSERT INTO `anime_videos` VALUES (66, 8, '第1集', 1, 78, '2024-09-03 11:54:07', '/dongman/8/8_66.jpg');
INSERT INTO `anime_videos` VALUES (67, 8, '第2集', 2, 79, '2024-09-03 11:54:09', '/dongman/8/8_67.jpg');
INSERT INTO `anime_videos` VALUES (68, 8, '第3集', 3, 80, '2024-09-03 11:54:10', '/dongman/8/8_68.jpg');
INSERT INTO `anime_videos` VALUES (69, 8, '第4集', 4, 81, '2024-09-03 11:54:11', '/dongman/8/8_69.jpg');
INSERT INTO `anime_videos` VALUES (70, 8, '第5集', 5, 82, '2024-09-03 11:54:12', '/dongman/8/8_70.jpg');
INSERT INTO `anime_videos` VALUES (71, 8, '第6集', 6, 83, '2024-09-03 11:54:13', '/dongman/8/8_71.jpg');
INSERT INTO `anime_videos` VALUES (72, 8, '第7集', 7, 85, '2024-09-03 11:54:14', '/dongman/8/8_72.jpg');
INSERT INTO `anime_videos` VALUES (73, 8, '第8集', 8, 84, '2024-09-03 11:54:16', '/dongman/8/8_73.jpg');
INSERT INTO `anime_videos` VALUES (74, 8, '第9集', 9, 86, '2024-09-03 11:54:17', '/dongman/8/8_74.jpg');
INSERT INTO `anime_videos` VALUES (75, 8, '第10集', 10, 87, '2024-09-03 11:54:19', '/dongman/8/8_75.jpg');
INSERT INTO `anime_videos` VALUES (76, 8, '第11集', 11, 88, '2024-09-03 11:54:21', '/dongman/8/8_76.jpg');
INSERT INTO `anime_videos` VALUES (77, 8, '第12集', 12, 89, '2024-09-03 11:54:22', '/dongman/8/8_77.jpg');
INSERT INTO `anime_videos` VALUES (78, 9, '第20集', 20, 91, '2024-09-03 12:03:51', '/dongman/9/9_78.jpg');
INSERT INTO `anime_videos` VALUES (79, 10, '第9集', 9, 93, '2024-09-03 12:13:18', '/dongman/10/10_79.jpg');
INSERT INTO `anime_videos` VALUES (80, 11, '第1集', 1, 96, '2024-09-03 21:14:50', '/dongman/11/11_80.jpg');
INSERT INTO `anime_videos` VALUES (81, 11, '第2集', 2, 95, '2024-09-03 21:14:52', '/dongman/11/11_81.jpg');
INSERT INTO `anime_videos` VALUES (82, 11, '第3集', 3, 97, '2024-09-03 21:14:53', '/dongman/11/11_82.jpg');
INSERT INTO `anime_videos` VALUES (83, 11, '第4集', 4, 98, '2024-09-03 21:14:54', '/dongman/11/11_83.jpg');
INSERT INTO `anime_videos` VALUES (84, 11, '第5集', 5, 99, '2024-09-03 21:14:55', '/dongman/11/11_84.jpg');
INSERT INTO `anime_videos` VALUES (85, 11, '第6集', 6, 100, '2024-09-03 21:14:56', '/dongman/11/11_85.jpg');
INSERT INTO `anime_videos` VALUES (86, 11, '第7集', 7, 101, '2024-09-03 21:14:58', '/dongman/11/11_86.jpg');
INSERT INTO `anime_videos` VALUES (87, 11, '第8集', 8, 102, '2024-09-03 21:14:59', '/dongman/11/11_87.jpg');
INSERT INTO `anime_videos` VALUES (88, 11, '第9集', 9, 103, '2024-09-03 21:15:00', '/dongman/11/11_88.jpg');
INSERT INTO `anime_videos` VALUES (89, 11, '第10集', 10, 104, '2024-09-03 21:15:02', '/dongman/11/11_89.jpg');
INSERT INTO `anime_videos` VALUES (90, 11, '第11集', 11, 105, '2024-09-03 21:15:04', '/dongman/11/11_90.jpg');
INSERT INTO `anime_videos` VALUES (91, 11, '第12集', 12, 106, '2024-09-03 21:15:06', '/dongman/11/11_91.jpg');
INSERT INTO `anime_videos` VALUES (92, 12, '见习魔女伊蕾娜', 1, 122, '2024-09-04 22:34:33', '/dongman/12/12_92.jpg');
INSERT INTO `anime_videos` VALUES (93, 12, '魔法使之国', 2, 112, '2024-09-04 22:34:43', '/dongman/12/12_93.jpg');
INSERT INTO `anime_videos` VALUES (94, 12, '貌美如花的她 / 瓶装的幸福', 3, 111, '2024-09-04 22:34:45', '/dongman/12/12_94.jpg');
INSERT INTO `anime_videos` VALUES (95, 12, '无民之国的王女', 4, 118, '2024-09-04 22:34:47', '/dongman/12/12_95.jpg');
INSERT INTO `anime_videos` VALUES (96, 12, '王立赛勒斯提利亚', 5, 117, '2024-09-04 22:34:48', '/dongman/12/12_96.jpg');
INSERT INTO `anime_videos` VALUES (97, 12, '诚实之国', 6, 108, '2024-09-04 22:34:50', '/dongman/12/12_97.jpg');
INSERT INTO `anime_videos` VALUES (98, 12, '旅行者刻出的高墙 / 踩葡萄的少女', 7, 114, '2024-09-04 22:35:24', '/dongman/12/12_98.jpg');
INSERT INTO `anime_videos` VALUES (99, 12, '杀人魔', 8, 116, '2024-09-04 22:35:47', '/dongman/12/12_99.jpg');
INSERT INTO `anime_videos` VALUES (100, 12, '逆流的悲伤', 9, 115, '2024-09-04 22:35:57', '/dongman/12/12_100.jpg');
INSERT INTO `anime_videos` VALUES (101, 12, '两个师父', 10, 109, '2024-09-04 22:36:10', '/dongman/12/12_101.jpg');
INSERT INTO `anime_videos` VALUES (102, 12, '两个徒弟', 11, 110, '2024-09-04 22:36:12', '/dongman/12/12_102.jpg');
INSERT INTO `anime_videos` VALUES (103, 12, '灰之魔女各种平凡的故事', 12, 113, '2024-09-04 22:37:57', '/dongman/12/12_103.jpg');
INSERT INTO `anime_videos` VALUES (104, 13, '慢慢来', 1, 120, '2024-09-04 22:56:29', '/dongman/13/13_104.jpg');
INSERT INTO `anime_videos` VALUES (105, 13, '多多益善', 2, 121, '2024-09-04 22:56:40', '/dongman/13/13_105.jpg');
INSERT INTO `anime_videos` VALUES (106, 13, '欲速则不达', 3, 123, '2024-09-04 22:56:52', '/dongman/13/13_106.jpg');
INSERT INTO `anime_videos` VALUES (107, 13, '无所求则无所获', 4, 124, '2024-09-04 22:57:11', '/dongman/13/13_107.jpg');
INSERT INTO `anime_videos` VALUES (108, 13, '目前状态良好', 5, 125, '2024-09-04 22:57:20', '/dongman/13/13_108.jpg');
INSERT INTO `anime_videos` VALUES (109, 13, '不是冤家不聚头', 6, 126, '2024-09-04 22:57:37', '/dongman/13/13_109.jpg');
INSERT INTO `anime_videos` VALUES (110, 13, '时间会证明一切', 7, 133, '2024-09-04 22:57:53', '/dongman/13/13_110.jpg');
INSERT INTO `anime_videos` VALUES (111, 13, '得过且过', 8, 127, '2024-09-04 22:58:03', '/dongman/13/13_111.jpg');
INSERT INTO `anime_videos` VALUES (112, 13, '无可挽回', 9, 128, '2024-09-04 22:58:11', '/dongman/13/13_112.jpg');
INSERT INTO `anime_videos` VALUES (113, 13, '以恶报恶', 10, 129, '2024-09-04 22:58:22', '/dongman/13/13_113.jpg');
INSERT INTO `anime_videos` VALUES (114, 13, '棋逢对手', 11, 130, '2024-09-04 22:58:30', '/dongman/13/13_114.jpg');
INSERT INTO `anime_videos` VALUES (115, 13, '先天之才与后天之能', 12, 131, '2024-09-04 22:58:50', '/dongman/13/13_115.jpg');
INSERT INTO `anime_videos` VALUES (116, 13, '铳动彼岸花', 13, 132, '2024-09-04 22:59:14', '/dongman/13/13_116.jpg');
INSERT INTO `anime_videos` VALUES (117, 14, '第1集', 1, 135, '2024-09-05 11:52:05', '/dongman/14/14_117.jpg');
INSERT INTO `anime_videos` VALUES (118, 14, '第2集', 2, 136, '2024-09-05 11:52:06', '/dongman/14/14_118.jpg');
INSERT INTO `anime_videos` VALUES (119, 14, '第3集', 3, 137, '2024-09-05 11:52:07', '/dongman/14/14_119.jpg');
INSERT INTO `anime_videos` VALUES (120, 14, '第4集', 4, 138, '2024-09-05 11:52:08', '/dongman/14/14_120.jpg');
INSERT INTO `anime_videos` VALUES (121, 14, '第5集', 5, 222, '2024-09-05 11:52:09', '/dongman/14/14_121.jpg');
INSERT INTO `anime_videos` VALUES (122, 15, '第1集', 1, 142, '2024-09-06 17:32:29', '/dongman/15/15_122.jpg');
INSERT INTO `anime_videos` VALUES (123, 15, '第2集', 2, 143, '2024-09-06 17:32:30', '/dongman/15/15_123.jpg');
INSERT INTO `anime_videos` VALUES (124, 15, '第3集', 3, 144, '2024-09-06 17:32:32', '/dongman/15/15_124.jpg');
INSERT INTO `anime_videos` VALUES (125, 15, '第4集', 4, 145, '2024-09-06 17:32:33', '/dongman/15/15_125.jpg');
INSERT INTO `anime_videos` VALUES (126, 15, '第5集', 5, 146, '2024-09-06 17:32:34', '/dongman/15/15_126.jpg');
INSERT INTO `anime_videos` VALUES (127, 15, '第6集', 6, 147, '2024-09-06 17:32:35', '/dongman/15/15_127.jpg');
INSERT INTO `anime_videos` VALUES (128, 15, '第7集', 7, 148, '2024-09-06 17:32:36', '/dongman/15/15_128.jpg');
INSERT INTO `anime_videos` VALUES (129, 15, '第8集', 8, 151, '2024-09-06 17:32:37', '/dongman/15/15_129.jpg');
INSERT INTO `anime_videos` VALUES (130, 15, '第9集', 9, 149, '2024-09-06 17:32:39', '/dongman/15/15_130.jpg');
INSERT INTO `anime_videos` VALUES (131, 15, '第10集', 10, 150, '2024-09-06 17:32:42', '/dongman/15/15_131.jpg');
INSERT INTO `anime_videos` VALUES (132, 15, '第11集', 11, 152, '2024-09-06 17:32:44', '/dongman/15/15_132.jpg');
INSERT INTO `anime_videos` VALUES (133, 15, '第12集', 12, 153, '2024-09-06 17:32:45', '/dongman/15/15_133.jpg');
INSERT INTO `anime_videos` VALUES (134, 16, '第1集', 1, 155, '2024-09-06 18:25:19', '/dongman/16/16_134.jpg');
INSERT INTO `anime_videos` VALUES (135, 16, '第2集', 2, 156, '2024-09-06 18:25:55', '/dongman/16/16_135.jpg');
INSERT INTO `anime_videos` VALUES (136, 16, '第3集', 3, 157, '2024-09-06 18:26:20', '/dongman/16/16_136.jpg');
INSERT INTO `anime_videos` VALUES (137, 16, '第4集', 4, 158, '2024-09-06 18:26:40', '/dongman/16/16_137.jpg');
INSERT INTO `anime_videos` VALUES (138, 16, '第5集', 5, 159, '2024-09-06 18:26:42', '/dongman/16/16_138.jpg');
INSERT INTO `anime_videos` VALUES (139, 16, '第6集', 6, 160, '2024-09-06 18:26:43', '/dongman/16/16_139.jpg');
INSERT INTO `anime_videos` VALUES (140, 16, '第7集', 7, 161, '2024-09-06 18:26:44', '/dongman/16/16_140.jpg');
INSERT INTO `anime_videos` VALUES (141, 16, '第8集', 8, 162, '2024-09-06 18:26:46', '/dongman/16/16_141.jpg');
INSERT INTO `anime_videos` VALUES (142, 16, '第9集', 9, 163, '2024-09-06 18:26:48', '/dongman/16/16_142.jpg');
INSERT INTO `anime_videos` VALUES (143, 16, '第10集', 10, 164, '2024-09-06 18:26:50', '/dongman/16/16_143.jpg');
INSERT INTO `anime_videos` VALUES (144, 16, '第11集', 11, 165, '2024-09-06 18:26:53', '/dongman/16/16_144.jpg');
INSERT INTO `anime_videos` VALUES (145, 16, '第12集', 12, 166, '2024-09-06 18:26:55', '/dongman/16/16_145.jpg');
INSERT INTO `anime_videos` VALUES (146, 16, '第13集', 13, 167, '2024-09-06 18:26:57', '/dongman/16/16_146.jpg');
INSERT INTO `anime_videos` VALUES (147, 16, '第14集', 14, 168, '2024-09-06 18:26:59', '/dongman/16/16_147.jpg');
INSERT INTO `anime_videos` VALUES (148, 16, '第15集', 15, 169, '2024-09-06 18:27:01', '/dongman/16/16_148.jpg');
INSERT INTO `anime_videos` VALUES (149, 16, '第16集', 16, 170, '2024-09-06 18:27:03', '/dongman/16/16_149.jpg');
INSERT INTO `anime_videos` VALUES (150, 16, '第17集', 17, 171, '2024-09-06 18:27:04', '/dongman/16/16_150.jpg');
INSERT INTO `anime_videos` VALUES (151, 16, '第18集', 18, 173, '2024-09-06 18:27:06', '/dongman/16/16_151.jpg');
INSERT INTO `anime_videos` VALUES (152, 16, '第19集', 19, 174, '2024-09-06 18:27:08', '/dongman/16/16_152.jpg');
INSERT INTO `anime_videos` VALUES (153, 16, '第20集', 20, 175, '2024-09-06 18:27:10', '/dongman/16/16_153.jpg');
INSERT INTO `anime_videos` VALUES (154, 16, '第21集', 21, 176, '2024-09-06 18:27:11', '/dongman/16/16_154.jpg');
INSERT INTO `anime_videos` VALUES (155, 16, '第22集', 22, 177, '2024-09-06 18:27:14', '/dongman/16/16_155.jpg');
INSERT INTO `anime_videos` VALUES (156, 16, '第23集', 23, 178, '2024-09-06 18:27:15', '/dongman/16/16_156.jpg');
INSERT INTO `anime_videos` VALUES (157, 16, '第24集', 24, 179, '2024-09-06 18:27:17', '/dongman/16/16_157.jpg');
INSERT INTO `anime_videos` VALUES (158, 16, '第25集', 25, 180, '2024-09-06 18:27:19', '/dongman/16/16_158.jpg');
INSERT INTO `anime_videos` VALUES (159, 16, '第26集', 26, 181, '2024-09-06 18:27:21', '/dongman/16/16_159.jpg');
INSERT INTO `anime_videos` VALUES (160, 9, '第21集', 21, 172, '2024-09-07 00:20:33', '/dongman/9/9_160.jpg');
INSERT INTO `anime_videos` VALUES (161, 17, '第1集', 1, 196, '2024-09-07 18:20:03', '/dongman/17/17_161.jpg');
INSERT INTO `anime_videos` VALUES (162, 17, '第2集', 2, 186, '2024-09-07 18:20:04', '/dongman/17/17_162.jpg');
INSERT INTO `anime_videos` VALUES (163, 17, '第3集', 3, 184, '2024-09-07 18:20:06', '/dongman/17/17_163.jpg');
INSERT INTO `anime_videos` VALUES (164, 17, '第4集', 4, 185, '2024-09-07 18:20:08', '/dongman/17/17_164.jpg');
INSERT INTO `anime_videos` VALUES (165, 17, '第5集', 5, 187, '2024-09-07 18:20:09', '/dongman/17/17_165.jpg');
INSERT INTO `anime_videos` VALUES (166, 17, '第6集', 6, 188, '2024-09-07 18:20:11', '/dongman/17/17_166.jpg');
INSERT INTO `anime_videos` VALUES (167, 17, '第7集', 7, 189, '2024-09-07 18:20:13', '/dongman/17/17_167.jpg');
INSERT INTO `anime_videos` VALUES (168, 17, '第8集', 8, 190, '2024-09-07 18:21:35', '/dongman/17/17_168.jpg');
INSERT INTO `anime_videos` VALUES (170, 17, '第9集', 9, 191, '2024-09-07 18:22:29', '/dongman/17/17_170.jpg');
INSERT INTO `anime_videos` VALUES (171, 17, '第10集', 10, 192, '2024-09-07 18:22:37', '/dongman/17/17_171.jpg');
INSERT INTO `anime_videos` VALUES (172, 17, '第11集', 11, 193, '2024-09-07 18:22:46', '/dongman/17/17_172.jpg');
INSERT INTO `anime_videos` VALUES (173, 17, '第12集', 12, 194, '2024-09-07 18:22:53', '/dongman/17/17_173.jpg');
INSERT INTO `anime_videos` VALUES (174, 18, '第1集', 1, 198, '2024-09-07 18:30:32', '/dongman/18/18_174.jpg');
INSERT INTO `anime_videos` VALUES (175, 18, '第2集', 2, 197, '2024-09-07 18:30:34', '/dongman/18/18_175.jpg');
INSERT INTO `anime_videos` VALUES (176, 18, '第3集', 3, 200, '2024-09-07 18:30:35', '/dongman/18/18_176.jpg');
INSERT INTO `anime_videos` VALUES (177, 18, '第4集', 4, 199, '2024-09-07 18:30:36', '/dongman/18/18_177.jpg');
INSERT INTO `anime_videos` VALUES (178, 18, '第5集', 5, 204, '2024-09-07 18:30:37', '/dongman/18/18_178.jpg');
INSERT INTO `anime_videos` VALUES (179, 18, '第6集', 6, 201, '2024-09-07 18:30:38', '/dongman/18/18_179.jpg');
INSERT INTO `anime_videos` VALUES (180, 18, '第7集', 7, 203, '2024-09-07 18:30:40', '/dongman/18/18_180.jpg');
INSERT INTO `anime_videos` VALUES (181, 18, '第8集', 8, 202, '2024-09-07 18:30:42', '/dongman/18/18_181.jpg');
INSERT INTO `anime_videos` VALUES (182, 18, '第9集', 9, 205, '2024-09-07 18:30:43', '/dongman/18/18_182.jpg');
INSERT INTO `anime_videos` VALUES (183, 18, '第10集', 10, 206, '2024-09-07 18:30:45', '/dongman/18/18_183.jpg');
INSERT INTO `anime_videos` VALUES (184, 18, '第11集', 11, 207, '2024-09-07 18:30:46', '/dongman/18/18_184.jpg');
INSERT INTO `anime_videos` VALUES (185, 18, '第12集', 12, 208, '2024-09-07 18:30:48', '/dongman/18/18_185.jpg');
INSERT INTO `anime_videos` VALUES (186, 19, '第1集', 1, 211, '2024-09-07 18:41:16', '/dongman/19/19_186.jpg');
INSERT INTO `anime_videos` VALUES (187, 19, '第2集', 2, 210, '2024-09-07 18:41:17', '/dongman/19/19_187.jpg');
INSERT INTO `anime_videos` VALUES (188, 19, '第3集', 3, 212, '2024-09-07 18:41:18', '/dongman/19/19_188.jpg');
INSERT INTO `anime_videos` VALUES (189, 19, '第4集', 4, 213, '2024-09-07 18:41:19', '/dongman/19/19_189.jpg');
INSERT INTO `anime_videos` VALUES (190, 19, '第5集', 5, 214, '2024-09-07 18:41:20', '/dongman/19/19_190.jpg');
INSERT INTO `anime_videos` VALUES (191, 19, '第6集', 6, 215, '2024-09-07 18:41:21', '/dongman/19/19_191.jpg');
INSERT INTO `anime_videos` VALUES (192, 19, '第7集', 7, 216, '2024-09-07 18:41:22', '/dongman/19/19_192.jpg');
INSERT INTO `anime_videos` VALUES (193, 19, '第8集', 8, 217, '2024-09-07 18:41:24', '/dongman/19/19_193.jpg');
INSERT INTO `anime_videos` VALUES (194, 19, '第9集', 9, 218, '2024-09-07 18:41:25', '/dongman/19/19_194.jpg');
INSERT INTO `anime_videos` VALUES (195, 19, '第10集', 10, 219, '2024-09-07 18:41:27', '/dongman/19/19_195.jpg');
INSERT INTO `anime_videos` VALUES (196, 19, '第11集', 11, 220, '2024-09-07 18:41:28', '/dongman/19/19_196.jpg');
INSERT INTO `anime_videos` VALUES (197, 19, '第12集', 12, 221, '2024-09-07 18:41:30', '/dongman/19/19_197.jpg');
INSERT INTO `anime_videos` VALUES (198, 20, '第1集', 1, 224, '2024-09-07 19:51:41', '/dongman/20/20_198.jpg');
INSERT INTO `anime_videos` VALUES (199, 20, '第2集', 2, 225, '2024-09-07 19:51:42', '/dongman/20/20_199.jpg');
INSERT INTO `anime_videos` VALUES (200, 20, '第3集', 3, 226, '2024-09-07 19:51:46', '/dongman/20/20_200.jpg');
INSERT INTO `anime_videos` VALUES (201, 20, '第4集', 4, 227, '2024-09-07 19:51:47', '/dongman/20/20_201.jpg');
INSERT INTO `anime_videos` VALUES (202, 20, '第5集', 5, 228, '2024-09-07 19:51:48', '/dongman/20/20_202.jpg');
INSERT INTO `anime_videos` VALUES (203, 20, '第6集', 6, 229, '2024-09-07 19:51:49', '/dongman/20/20_203.jpg');
INSERT INTO `anime_videos` VALUES (204, 20, '第7集', 7, 230, '2024-09-07 19:51:50', '/dongman/20/20_204.jpg');
INSERT INTO `anime_videos` VALUES (205, 20, '第8集', 8, 231, '2024-09-07 19:51:52', '/dongman/20/20_205.jpg');
INSERT INTO `anime_videos` VALUES (206, 20, '第9集', 9, 232, '2024-09-07 19:51:53', '/dongman/20/20_206.jpg');
INSERT INTO `anime_videos` VALUES (207, 20, '第10集', 10, 233, '2024-09-07 19:51:55', '/dongman/20/20_207.jpg');
INSERT INTO `anime_videos` VALUES (208, 20, '第11集', 11, 234, '2024-09-07 19:51:57', '/dongman/20/20_208.jpg');
INSERT INTO `anime_videos` VALUES (209, 20, '第12集', 12, 235, '2024-09-07 19:51:58', '/dongman/20/20_209.jpg');
INSERT INTO `anime_videos` VALUES (210, 22, '第1集', 1, 241, '2024-09-07 20:40:40', '/dongman/22/22_210.jpg');
INSERT INTO `anime_videos` VALUES (211, 22, '第2集', 2, 238, '2024-09-07 20:40:41', '/dongman/22/22_211.jpg');
INSERT INTO `anime_videos` VALUES (212, 22, '第3集', 3, 239, '2024-09-07 20:40:42', '/dongman/22/22_212.jpg');
INSERT INTO `anime_videos` VALUES (213, 22, '第4集', 4, 240, '2024-09-07 20:40:43', '/dongman/22/22_213.jpg');
INSERT INTO `anime_videos` VALUES (214, 22, '第5集', 5, 242, '2024-09-07 20:40:44', '/dongman/22/22_214.jpg');
INSERT INTO `anime_videos` VALUES (215, 22, '第6集', 6, 243, '2024-09-07 20:40:45', '/dongman/22/22_215.jpg');
INSERT INTO `anime_videos` VALUES (216, 22, '第7集', 7, 245, '2024-09-07 20:40:47', '/dongman/22/22_216.jpg');
INSERT INTO `anime_videos` VALUES (217, 22, '第8集', 8, 244, '2024-09-07 20:40:48', '/dongman/22/22_217.jpg');
INSERT INTO `anime_videos` VALUES (218, 22, '第9集', 9, 247, '2024-09-07 20:40:49', '/dongman/22/22_218.jpg');
INSERT INTO `anime_videos` VALUES (219, 22, '第10集', 10, 246, '2024-09-07 20:40:51', '/dongman/22/22_219.jpg');
INSERT INTO `anime_videos` VALUES (220, 22, '第11集', 11, 248, '2024-09-07 20:40:52', '/dongman/22/22_220.jpg');
INSERT INTO `anime_videos` VALUES (221, 22, '第12集', 12, 249, '2024-09-07 20:40:54', '/dongman/22/22_221.jpg');
INSERT INTO `anime_videos` VALUES (222, 21, '第1集', 1, 250, '2024-09-07 20:43:51', '/dongman/21/21_222.jpg');
INSERT INTO `anime_videos` VALUES (223, 21, '第2集', 2, 251, '2024-09-07 20:43:53', '/dongman/21/21_223.jpg');
INSERT INTO `anime_videos` VALUES (224, 21, '第3集', 3, 252, '2024-09-07 20:43:54', '/dongman/21/21_224.jpg');
INSERT INTO `anime_videos` VALUES (225, 21, '第4集', 4, 253, '2024-09-07 20:43:55', '/dongman/21/21_225.jpg');
INSERT INTO `anime_videos` VALUES (226, 21, '第5集', 5, 254, '2024-09-07 20:43:56', '/dongman/21/21_226.jpg');
INSERT INTO `anime_videos` VALUES (227, 21, '第6集', 6, 255, '2024-09-07 20:43:57', '/dongman/21/21_227.jpg');
INSERT INTO `anime_videos` VALUES (228, 21, '第7集', 7, 256, '2024-09-07 20:43:58', '/dongman/21/21_228.jpg');
INSERT INTO `anime_videos` VALUES (229, 21, '第8集', 8, 257, '2024-09-07 20:43:59', '/dongman/21/21_229.jpg');
INSERT INTO `anime_videos` VALUES (230, 21, '第9集', 9, 261, '2024-09-07 20:44:00', '/dongman/21/21_230.jpg');
INSERT INTO `anime_videos` VALUES (231, 21, '第10集', 10, 258, '2024-09-07 20:44:02', '/dongman/21/21_231.jpg');
INSERT INTO `anime_videos` VALUES (232, 21, '第11集', 11, 259, '2024-09-07 20:44:04', '/dongman/21/21_232.jpg');
INSERT INTO `anime_videos` VALUES (233, 21, '第12集', 12, 260, '2024-09-07 20:44:05', '/dongman/21/21_233.jpg');
INSERT INTO `anime_videos` VALUES (234, 23, '第1集', 1, 263, '2024-09-08 11:20:06', '/dongman/23/23_234.jpg');
INSERT INTO `anime_videos` VALUES (235, 23, '第2集', 2, 264, '2024-09-08 11:47:37', '/dongman/23/23_235.jpg');
INSERT INTO `anime_videos` VALUES (237, 23, '第3集', 3, 265, '2024-09-08 11:48:13', '/dongman/23/23_237.jpg');
INSERT INTO `anime_videos` VALUES (238, 23, '第4集', 4, 266, '2024-09-08 11:48:14', '/dongman/23/23_238.jpg');
INSERT INTO `anime_videos` VALUES (239, 23, '第5集', 5, 267, '2024-09-08 11:48:15', '/dongman/23/23_239.jpg');
INSERT INTO `anime_videos` VALUES (240, 23, '第6集', 6, 268, '2024-09-08 11:48:16', '/dongman/23/23_240.jpg');
INSERT INTO `anime_videos` VALUES (241, 23, '第7集', 7, 269, '2024-09-08 11:48:17', '/dongman/23/23_241.jpg');
INSERT INTO `anime_videos` VALUES (242, 23, '第8集', 8, 270, '2024-09-08 11:48:19', '/dongman/23/23_242.jpg');
INSERT INTO `anime_videos` VALUES (243, 23, '第9集', 9, 271, '2024-09-08 11:48:20', '/dongman/23/23_243.jpg');
INSERT INTO `anime_videos` VALUES (244, 23, '第10集', 10, 272, '2024-09-08 11:48:22', '/dongman/23/23_244.jpg');
INSERT INTO `anime_videos` VALUES (245, 23, '第11集', 11, 273, '2024-09-08 11:48:24', '/dongman/23/23_245.jpg');
INSERT INTO `anime_videos` VALUES (246, 23, '第12集', 12, 274, '2024-09-08 11:48:25', '/dongman/23/23_246.jpg');
INSERT INTO `anime_videos` VALUES (247, 23, '第13集', 13, 275, '2024-09-08 11:48:27', '/dongman/23/23_247.jpg');
INSERT INTO `anime_videos` VALUES (248, 23, '第14集', 14, 276, '2024-09-08 11:48:28', '/dongman/23/23_248.jpg');
INSERT INTO `anime_videos` VALUES (249, 23, '第15集', 15, 278, '2024-09-08 11:48:30', '/dongman/23/23_249.jpg');
INSERT INTO `anime_videos` VALUES (250, 23, '第16集', 16, 277, '2024-09-08 11:48:31', '/dongman/23/23_250.jpg');
INSERT INTO `anime_videos` VALUES (251, 23, '第17集', 17, 279, '2024-09-08 11:48:32', '/dongman/23/23_251.jpg');
INSERT INTO `anime_videos` VALUES (252, 23, '第18集', 18, 281, '2024-09-08 11:48:34', '/dongman/23/23_252.jpg');
INSERT INTO `anime_videos` VALUES (253, 23, '第19集', 19, 280, '2024-09-08 11:48:35', '/dongman/23/23_253.jpg');
INSERT INTO `anime_videos` VALUES (254, 23, '第20集', 20, 282, '2024-09-08 11:48:37', '/dongman/23/23_254.jpg');
INSERT INTO `anime_videos` VALUES (255, 23, '第21集', 21, 283, '2024-09-08 11:48:39', '/dongman/23/23_255.jpg');
INSERT INTO `anime_videos` VALUES (256, 23, '第22集', 22, 284, '2024-09-08 11:48:40', '/dongman/23/23_256.jpg');
INSERT INTO `anime_videos` VALUES (257, 23, '第23集', 23, 285, '2024-09-08 11:48:42', '/dongman/23/23_257.jpg');
INSERT INTO `anime_videos` VALUES (258, 23, '第24集', 24, 286, '2024-09-08 11:48:43', '/dongman/23/23_258.jpg');
INSERT INTO `anime_videos` VALUES (259, 24, '通往夏天的隧道，再见的出口', 1, 288, '2024-09-08 11:57:48', '/dongman/24/24_259.jpg');
INSERT INTO `anime_videos` VALUES (260, 25, '第1集', 1, 290, '2024-09-08 16:27:03', '/dongman/25/25_260.jpg');
INSERT INTO `anime_videos` VALUES (261, 25, '第2集', 2, 291, '2024-09-08 16:27:04', '/dongman/25/25_261.jpg');
INSERT INTO `anime_videos` VALUES (262, 26, '第1集', 1, 293, '2024-09-08 16:30:04', '/dongman/26/26_262.jpg');
INSERT INTO `anime_videos` VALUES (263, 26, '第2集', 2, 294, '2024-09-08 16:30:06', '/dongman/26/26_263.jpg');
INSERT INTO `anime_videos` VALUES (264, 26, '第3集', 3, 295, '2024-09-08 16:30:08', '/dongman/26/26_264.jpg');
INSERT INTO `anime_videos` VALUES (265, 26, '第4集', 4, 296, '2024-09-08 16:30:10', '/dongman/26/26_265.jpg');
INSERT INTO `anime_videos` VALUES (266, 26, '第5集', 5, 297, '2024-09-08 16:30:12', '/dongman/26/26_266.jpg');
INSERT INTO `anime_videos` VALUES (267, 26, '第6集', 6, 298, '2024-09-08 16:30:14', '/dongman/26/26_267.jpg');
INSERT INTO `anime_videos` VALUES (268, 26, '第7集', 7, 299, '2024-09-08 16:30:16', '/dongman/26/26_268.jpg');
INSERT INTO `anime_videos` VALUES (269, 26, '第8集', 8, 300, '2024-09-08 16:30:17', '/dongman/26/26_269.jpg');
INSERT INTO `anime_videos` VALUES (270, 26, '第9集', 9, 301, '2024-09-08 16:30:19', '/dongman/26/26_270.jpg');
INSERT INTO `anime_videos` VALUES (271, 26, '第10集', 10, 302, '2024-09-08 16:30:21', '/dongman/26/26_271.jpg');
INSERT INTO `anime_videos` VALUES (272, 26, '第11集', 11, 303, '2024-09-08 16:30:23', '/dongman/26/26_272.jpg');
INSERT INTO `anime_videos` VALUES (273, 26, '第12集', 12, 304, '2024-09-08 16:30:26', '/dongman/26/26_273.jpg');
INSERT INTO `anime_videos` VALUES (274, 26, '第13集', 13, 305, '2024-09-08 16:30:28', '/dongman/26/26_274.jpg');
INSERT INTO `anime_videos` VALUES (275, 26, '第14集', 14, 306, '2024-09-08 16:30:30', '/dongman/26/26_275.jpg');
INSERT INTO `anime_videos` VALUES (276, 26, '第15集', 15, 307, '2024-09-08 16:30:32', '/dongman/26/26_276.jpg');
INSERT INTO `anime_videos` VALUES (277, 26, '第16集', 16, 308, '2024-09-08 16:30:35', '/dongman/26/26_277.jpg');
INSERT INTO `anime_videos` VALUES (278, 26, '第17集', 17, 309, '2024-09-08 16:30:37', '/dongman/26/26_278.jpg');
INSERT INTO `anime_videos` VALUES (279, 26, '第18集', 18, 310, '2024-09-08 16:30:39', '/dongman/26/26_279.jpg');
INSERT INTO `anime_videos` VALUES (280, 26, '第19集', 19, 311, '2024-09-08 16:30:41', '/dongman/26/26_280.jpg');
INSERT INTO `anime_videos` VALUES (281, 26, '第20集', 20, 312, '2024-09-08 16:30:43', '/dongman/26/26_281.jpg');
INSERT INTO `anime_videos` VALUES (282, 26, '第21集', 21, 313, '2024-09-08 16:30:46', '/dongman/26/26_282.jpg');
INSERT INTO `anime_videos` VALUES (283, 26, '第22集', 22, 314, '2024-09-08 16:30:48', '/dongman/26/26_283.jpg');
INSERT INTO `anime_videos` VALUES (284, 26, '第23集', 23, 315, '2024-09-08 16:30:51', '/dongman/26/26_284.jpg');
INSERT INTO `anime_videos` VALUES (285, 26, '第24集', 24, 316, '2024-09-08 16:30:53', '/dongman/26/26_285.jpg');
INSERT INTO `anime_videos` VALUES (286, 26, '第25集', 25, 317, '2024-09-08 16:30:55', '/dongman/26/26_286.jpg');
INSERT INTO `anime_videos` VALUES (287, 26, '第26集', 26, 318, '2024-09-08 16:30:57', '/dongman/26/26_287.jpg');
INSERT INTO `anime_videos` VALUES (288, 27, '恋人的羁绊', 1, 320, '2024-09-08 17:11:53', '/dongman/27/27_288.jpg');
INSERT INTO `anime_videos` VALUES (289, 28, '第1集', 1, 322, '2024-09-08 17:13:33', '/dongman/28/28_289.jpg');
INSERT INTO `anime_videos` VALUES (290, 28, '第2集', 2, 323, '2024-09-08 17:13:34', '/dongman/28/28_290.jpg');
INSERT INTO `anime_videos` VALUES (291, 28, '第3集', 3, 324, '2024-09-08 17:13:35', '/dongman/28/28_291.jpg');
INSERT INTO `anime_videos` VALUES (292, 28, '第4集', 4, 325, '2024-09-08 17:13:36', '/dongman/28/28_292.jpg');
INSERT INTO `anime_videos` VALUES (293, 28, '第5集', 5, 326, '2024-09-08 17:13:37', '/dongman/28/28_293.jpg');
INSERT INTO `anime_videos` VALUES (294, 28, '第6集', 6, 327, '2024-09-08 17:13:38', '/dongman/28/28_294.jpg');
INSERT INTO `anime_videos` VALUES (295, 28, '第7集', 7, 328, '2024-09-08 17:13:39', '/dongman/28/28_295.jpg');
INSERT INTO `anime_videos` VALUES (296, 28, '第8集', 8, 329, '2024-09-08 17:13:41', '/dongman/28/28_296.jpg');
INSERT INTO `anime_videos` VALUES (297, 28, '第9集', 9, 330, '2024-09-08 17:13:42', '/dongman/28/28_297.jpg');
INSERT INTO `anime_videos` VALUES (298, 28, '第10集', 10, 331, '2024-09-08 17:13:48', '/dongman/28/28_298.jpg');
INSERT INTO `anime_videos` VALUES (299, 28, '第11集', 11, 332, '2024-09-08 17:13:50', '/dongman/28/28_299.jpg');
INSERT INTO `anime_videos` VALUES (300, 28, '第12集', 12, 333, '2024-09-08 17:13:52', '/dongman/28/28_300.jpg');
INSERT INTO `anime_videos` VALUES (301, 29, '第1集', 1, 335, '2024-09-10 13:28:50', '/dongman/29/29_301.jpg');
INSERT INTO `anime_videos` VALUES (302, 29, '第2集', 2, 336, '2024-09-10 13:28:51', '/dongman/29/29_302.jpg');
INSERT INTO `anime_videos` VALUES (303, 29, '第3集', 3, 337, '2024-09-10 13:28:52', '/dongman/29/29_303.jpg');
INSERT INTO `anime_videos` VALUES (304, 29, '第4集', 4, 338, '2024-09-10 13:28:53', '/dongman/29/29_304.jpg');
INSERT INTO `anime_videos` VALUES (305, 29, '第5集', 5, 339, '2024-09-10 13:28:54', '/dongman/29/29_305.jpg');
INSERT INTO `anime_videos` VALUES (306, 29, '第6集', 6, 340, '2024-09-10 13:28:55', '/dongman/29/29_306.jpg');
INSERT INTO `anime_videos` VALUES (307, 29, '第7集', 7, 341, '2024-09-10 13:28:56', '/dongman/29/29_307.jpg');
INSERT INTO `anime_videos` VALUES (308, 29, '第8集', 8, 342, '2024-09-10 13:28:58', '/dongman/29/29_308.jpg');
INSERT INTO `anime_videos` VALUES (309, 29, '第9集', 9, 343, '2024-09-10 13:29:00', '/dongman/29/29_309.jpg');
INSERT INTO `anime_videos` VALUES (310, 29, '第10集', 10, 344, '2024-09-10 13:29:01', '/dongman/29/29_310.jpg');
INSERT INTO `anime_videos` VALUES (311, 29, '第11集', 11, 345, '2024-09-10 13:29:03', '/dongman/29/29_311.jpg');
INSERT INTO `anime_videos` VALUES (312, 29, '第12集', 12, 346, '2024-09-10 13:29:04', '/dongman/29/29_312.jpg');
INSERT INTO `anime_videos` VALUES (313, 29, '第13集', 13, 347, '2024-09-10 13:29:05', '/dongman/29/29_313.jpg');
INSERT INTO `anime_videos` VALUES (314, 5, '第13集', 13, NULL, '2024-09-10 18:31:12', NULL);
INSERT INTO `anime_videos` VALUES (315, 30, '第1集', 1, 349, '2024-09-10 23:16:36', '/dongman/30/30_315.jpg');
INSERT INTO `anime_videos` VALUES (316, 30, '第2集', 2, 350, '2024-09-10 23:42:58', '/dongman/30/30_316.jpg');
INSERT INTO `anime_videos` VALUES (317, 30, '第3集', 3, 351, '2024-09-10 23:43:04', '/dongman/30/30_317.jpg');
INSERT INTO `anime_videos` VALUES (318, 30, '第4集', 4, 352, '2024-09-10 23:43:05', '/dongman/30/30_318.jpg');
INSERT INTO `anime_videos` VALUES (319, 30, '第5集', 5, 353, '2024-09-10 23:43:06', '/dongman/30/30_319.jpg');
INSERT INTO `anime_videos` VALUES (320, 30, '第6集', 6, 354, '2024-09-10 23:43:07', '/dongman/30/30_320.jpg');
INSERT INTO `anime_videos` VALUES (321, 30, '第7集', 7, 355, '2024-09-10 23:43:08', '/dongman/30/30_321.jpg');
INSERT INTO `anime_videos` VALUES (322, 30, '第8集', 8, 356, '2024-09-10 23:43:09', '/dongman/30/30_322.jpg');
INSERT INTO `anime_videos` VALUES (323, 30, '第9集', 9, 357, '2024-09-10 23:43:10', '/dongman/30/30_323.jpg');
INSERT INTO `anime_videos` VALUES (324, 30, '第10集', 10, 358, '2024-09-10 23:43:12', '/dongman/30/30_324.jpg');
INSERT INTO `anime_videos` VALUES (325, 30, '第11集', 11, 359, '2024-09-10 23:43:13', '/dongman/30/30_325.jpg');
INSERT INTO `anime_videos` VALUES (326, 30, '第12集', 12, 360, '2024-09-10 23:43:14', '/dongman/30/30_326.jpg');
INSERT INTO `anime_videos` VALUES (327, 31, '第1集', 1, 362, '2024-09-11 22:42:14', '/dongman/31/31_327.jpg');

-- ----------------------------
-- Table structure for file_nodes
-- ----------------------------
DROP TABLE IF EXISTS `file_nodes`;
CREATE TABLE `file_nodes`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` int UNSIGNED NULL DEFAULT NULL COMMENT '上级节点ID，根文件夹为NULL',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `node_type` enum('folder','file') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'folder' COMMENT '节点类型：文件夹或文件',
  `full_path` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '完整路径',
  `size` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '大小，对于文件夹默认为0',
  `file_path` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件存储路径，对于文件不为空',
  `file_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件类型，仅文件类型时不为空',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` tinyint UNSIGNED NOT NULL DEFAULT 0 COMMENT '逻辑删除标记，1表示已删除，0表示未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 363 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件夹/文件信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_nodes
-- ----------------------------
INSERT INTO `file_nodes` VALUES (1, NULL, 'dongman', 'folder', '/dongman', 0, NULL, NULL, '2024-08-28 19:01:37', '2024-08-28 19:01:37', 0);
INSERT INTO `file_nodes` VALUES (2, 1, '喜羊羊与灰太狼', 'folder', '/dongman/喜羊羊与灰太狼', 0, NULL, NULL, '2024-08-28 19:39:58', '2024-08-29 10:44:56', 0);
INSERT INTO `file_nodes` VALUES (3, NULL, '哆啦a梦第4季', 'folder', '/dongman/哆啦a梦第4季', 0, NULL, NULL, '2024-08-29 10:51:08', '2024-08-29 12:24:21', 0);
INSERT INTO `file_nodes` VALUES (4, 1, '机动战士高达 水星的魔女', 'folder', '/dongman/机动战士高达 水星的魔女', 0, NULL, NULL, '2024-08-29 22:44:09', '2024-08-29 23:20:36', 0);
INSERT INTO `file_nodes` VALUES (5, 4, '01', 'file', '/dongman/机动战士高达 水星的魔女/01.mp4', 695914, '/dongman/机动战士高达 水星的魔女/01.mp4', 'mp4', '2024-08-29 23:17:13', '2024-08-29 23:20:37', 0);
INSERT INTO `file_nodes` VALUES (9, 4, '2', 'file', '/dongman/机动战士高达 水星的魔女/2.mp4', 357485, '/dongman/机动战士高达 水星的魔女/2.mp4', 'mp4', '2024-08-30 13:36:44', '2024-08-30 13:36:47', 0);
INSERT INTO `file_nodes` VALUES (10, 4, '3', 'file', '/dongman/机动战士高达 水星的魔女/3.mp4', 588397, '/dongman/机动战士高达 水星的魔女/3.mp4', 'mp4', '2024-08-30 13:56:50', '2024-08-30 13:56:55', 0);
INSERT INTO `file_nodes` VALUES (12, 4, '4', 'file', '/dongman/机动战士高达 水星的魔女/4.mp4', 385224, '/dongman/机动战士高达 水星的魔女/4.mp4', 'mp4', '2024-08-30 15:09:53', '2024-08-30 15:09:54', 0);
INSERT INTO `file_nodes` VALUES (13, 4, '5', 'file', '/dongman/机动战士高达 水星的魔女/1.txt', 0, '/dongman/机动战士高达 水星的魔女/1.txt', NULL, '2024-08-30 16:09:07', '2024-08-30 16:10:34', 1);
INSERT INTO `file_nodes` VALUES (14, 4, '5', 'file', '/dongman/机动战士高达 水星的魔女/5.mp4', 619156, '/dongman/机动战士高达 水星的魔女/5.mp4', 'mp4', '2024-08-30 22:07:34', '2024-08-30 22:07:35', 0);
INSERT INTO `file_nodes` VALUES (15, 4, '6', 'file', '/dongman/机动战士高达 水星的魔女/6.mp4', 567075, '/dongman/机动战士高达 水星的魔女/6.mp4', 'mp4', '2024-08-30 23:02:20', '2024-08-30 23:02:24', 0);
INSERT INTO `file_nodes` VALUES (16, 4, '7', 'file', '/dongman/机动战士高达 水星的魔女/7.mp4', 373077, '/dongman/机动战士高达 水星的魔女/7.mp4', 'mp4', '2024-08-30 23:02:32', '2024-08-30 23:02:34', 0);
INSERT INTO `file_nodes` VALUES (17, 4, '8', 'file', '/dongman/机动战士高达 水星的魔女/8.mp4', 341156, '/dongman/机动战士高达 水星的魔女/8.mp4', 'mp4', '2024-08-30 23:02:50', '2024-08-30 23:02:51', 0);
INSERT INTO `file_nodes` VALUES (18, 4, '10', 'file', '/dongman/机动战士高达 水星的魔女/10.mp4', 333150, '/dongman/机动战士高达 水星的魔女/10.mp4', 'mp4', '2024-08-30 23:06:01', '2024-08-30 23:06:01', 0);
INSERT INTO `file_nodes` VALUES (19, 4, '11', 'file', '/dongman/机动战士高达 水星的魔女/11.mp4', 403290, '/dongman/机动战士高达 水星的魔女/11.mp4', 'mp4', '2024-08-30 23:06:12', '2024-08-30 23:06:13', 0);
INSERT INTO `file_nodes` VALUES (20, 4, '9', 'file', '/dongman/机动战士高达 水星的魔女/9.mp4', 639020, '/dongman/机动战士高达 水星的魔女/9.mp4', 'mp4', '2024-08-30 23:06:16', '2024-08-30 23:06:20', 0);
INSERT INTO `file_nodes` VALUES (21, 4, '12', 'file', '/dongman/机动战士高达 水星的魔女/12.mp4', 669823, '/dongman/机动战士高达 水星的魔女/12.mp4', 'mp4', '2024-08-30 23:06:38', '2024-08-30 23:06:40', 0);
INSERT INTO `file_nodes` VALUES (22, 1, '机动战士高达 水星的魔女 第二季', 'folder', '/dongman/机动战士高达 水星的魔女 第二季', 0, NULL, NULL, '2024-09-01 20:58:23', '2024-09-01 21:13:38', 0);
INSERT INTO `file_nodes` VALUES (23, 1, '心灵感应', 'folder', '/dongman/心灵感应', 0, NULL, NULL, '2024-09-01 21:04:19', '2024-09-01 21:14:09', 0);
INSERT INTO `file_nodes` VALUES (24, 22, '1', 'file', '/dongman/机动战士高达 水星的魔女 第二季/1.mp4', 430307, '/dongman/机动战士高达 水星的魔女 第二季/1.mp4', 'mp4', '2024-09-01 22:06:29', '2024-09-01 22:06:30', 0);
INSERT INTO `file_nodes` VALUES (25, 22, '2', 'file', '/dongman/机动战士高达 水星的魔女 第二季/2.mp4', 550197, '/dongman/机动战士高达 水星的魔女 第二季/2.mp4', 'mp4', '2024-09-01 22:07:14', '2024-09-01 22:07:15', 0);
INSERT INTO `file_nodes` VALUES (26, 22, '3', 'file', '/dongman/机动战士高达 水星的魔女 第二季/3.mp4', 417961, '/dongman/机动战士高达 水星的魔女 第二季/3.mp4', 'mp4', '2024-09-01 22:07:41', '2024-09-01 22:07:45', 0);
INSERT INTO `file_nodes` VALUES (27, 22, '4', 'file', '/dongman/机动战士高达 水星的魔女 第二季/4.mp4', 367120, '/dongman/机动战士高达 水星的魔女 第二季/4.mp4', 'mp4', '2024-09-01 22:15:45', '2024-09-01 22:15:51', 0);
INSERT INTO `file_nodes` VALUES (28, 22, '5', 'file', '/dongman/机动战士高达 水星的魔女 第二季/5.mp4', 441882, '/dongman/机动战士高达 水星的魔女 第二季/5.mp4', 'mp4', '2024-09-01 22:15:52', '2024-09-01 22:15:57', 0);
INSERT INTO `file_nodes` VALUES (29, 22, '7', 'file', '/dongman/机动战士高达 水星的魔女 第二季/7.mp4', 377345, '/dongman/机动战士高达 水星的魔女 第二季/7.mp4', 'mp4', '2024-09-01 22:15:58', '2024-09-01 22:16:00', 0);
INSERT INTO `file_nodes` VALUES (30, 22, '6', 'file', '/dongman/机动战士高达 水星的魔女 第二季/6.mp4', 380716, '/dongman/机动战士高达 水星的魔女 第二季/6.mp4', 'mp4', '2024-09-01 22:16:00', '2024-09-01 22:16:02', 0);
INSERT INTO `file_nodes` VALUES (31, 22, '8', 'file', '/dongman/机动战士高达 水星的魔女 第二季/8.mp4', 377345, '/dongman/机动战士高达 水星的魔女 第二季/8.mp4', 'mp4', '2024-09-01 22:16:18', '2024-09-01 22:16:20', 0);
INSERT INTO `file_nodes` VALUES (32, 22, '9', 'file', '/dongman/机动战士高达 水星的魔女 第二季/9.mp4', 400196, '/dongman/机动战士高达 水星的魔女 第二季/9.mp4', 'mp4', '2024-09-01 22:16:27', '2024-09-01 22:16:34', 0);
INSERT INTO `file_nodes` VALUES (33, 22, '10', 'file', '/dongman/机动战士高达 水星的魔女 第二季/10.mp4', 511331, '/dongman/机动战士高达 水星的魔女 第二季/10.mp4', 'mp4', '2024-09-01 22:16:42', '2024-09-01 22:16:48', 0);
INSERT INTO `file_nodes` VALUES (34, 22, '11', 'file', '/dongman/机动战士高达 水星的魔女 第二季/11.mp4', 508223, '/dongman/机动战士高达 水星的魔女 第二季/11.mp4', 'mp4', '2024-09-01 22:16:46', '2024-09-01 22:16:50', 0);
INSERT INTO `file_nodes` VALUES (36, 23, '星灵01', 'file', '/dongman/心灵感应/星灵01.mp4', 507063, '/dongman/心灵感应/星灵01.mp4', 'mp4', '2024-09-01 22:29:31', '2024-09-01 22:29:42', 0);
INSERT INTO `file_nodes` VALUES (37, 23, '星灵02', 'file', '/dongman/心灵感应/星灵02.mp4', 469403, '/dongman/心灵感应/星灵02.mp4', 'mp4', '2024-09-01 22:29:38', '2024-09-01 22:29:47', 0);
INSERT INTO `file_nodes` VALUES (38, 23, '星灵03', 'file', '/dongman/心灵感应/星灵03.mp4', 429960, '/dongman/心灵感应/星灵03.mp4', 'mp4', '2024-09-01 22:29:43', '2024-09-01 22:29:50', 0);
INSERT INTO `file_nodes` VALUES (39, 23, '星灵04', 'file', '/dongman/心灵感应/星灵04.mp4', 451688, '/dongman/心灵感应/星灵04.mp4', 'mp4', '2024-09-01 22:29:59', '2024-09-01 22:30:07', 0);
INSERT INTO `file_nodes` VALUES (40, 23, '星灵05', 'file', '/dongman/心灵感应/星灵05.mp4', 446265, '/dongman/心灵感应/星灵05.mp4', 'mp4', '2024-09-01 22:30:07', '2024-09-01 22:30:19', 0);
INSERT INTO `file_nodes` VALUES (41, 23, '星灵06', 'file', '/dongman/心灵感应/星灵06.mp4', 437928, '/dongman/心灵感应/星灵06.mp4', 'mp4', '2024-09-01 22:30:12', '2024-09-01 22:30:28', 0);
INSERT INTO `file_nodes` VALUES (42, 23, '星灵07', 'file', '/dongman/心灵感应/星灵07.mp4', 454160, '/dongman/心灵感应/星灵07.mp4', 'mp4', '2024-09-01 22:30:19', '2024-09-01 22:30:38', 0);
INSERT INTO `file_nodes` VALUES (43, 23, '星灵08', 'file', '/dongman/心灵感应/星灵08.mp4', 451914, '/dongman/心灵感应/星灵08.mp4', 'mp4', '2024-09-01 22:30:24', '2024-09-01 22:30:43', 0);
INSERT INTO `file_nodes` VALUES (44, 23, '星灵09', 'file', '/dongman/心灵感应/星灵09.mp4', 412082, '/dongman/心灵感应/星灵09.mp4', 'mp4', '2024-09-01 22:30:26', '2024-09-01 22:30:43', 0);
INSERT INTO `file_nodes` VALUES (45, 23, '星灵10', 'file', '/dongman/心灵感应/星灵10.mp4', 458370, '/dongman/心灵感应/星灵10.mp4', 'mp4', '2024-09-01 22:30:37', '2024-09-01 22:30:52', 0);
INSERT INTO `file_nodes` VALUES (46, 23, '星灵12', 'file', '/dongman/心灵感应/星灵12.mp4', 444911, '/dongman/心灵感应/星灵12.mp4', 'mp4', '2024-09-01 22:30:39', '2024-09-01 22:30:54', 0);
INSERT INTO `file_nodes` VALUES (47, 23, '星灵11', 'file', '/dongman/心灵感应/星灵11.mp4', 463438, '/dongman/心灵感应/星灵11.mp4', 'mp4', '2024-09-01 22:30:40', '2024-09-01 22:30:54', 0);
INSERT INTO `file_nodes` VALUES (48, 22, '12', 'file', '/dongman/机动战士高达 水星的魔女 第二季/12.mp4', 461231, '/dongman/机动战士高达 水星的魔女 第二季/12.mp4', 'mp4', '2024-09-02 11:19:30', '2024-09-02 11:19:30', 0);
INSERT INTO `file_nodes` VALUES (49, 1, 'NOIR(黑色天使)', 'folder', '/dongman/NOIR(黑色天使)', 0, NULL, NULL, '2024-09-02 21:46:02', '2024-09-02 21:46:02', 0);
INSERT INTO `file_nodes` VALUES (51, 49, '2', 'file', '/dongman/NOIR(黑色天使)/2.mp4', 445624, '/dongman/NOIR(黑色天使)/2.mp4', 'mp4', '2024-09-02 22:43:19', '2024-09-02 22:43:28', 0);
INSERT INTO `file_nodes` VALUES (52, 49, '3', 'file', '/dongman/NOIR(黑色天使)/3.mp4', 422072, '/dongman/NOIR(黑色天使)/3.mp4', 'mp4', '2024-09-02 22:43:19', '2024-09-02 22:43:28', 0);
INSERT INTO `file_nodes` VALUES (53, 49, '5', 'file', '/dongman/NOIR(黑色天使)/5.mp4', 443582, '/dongman/NOIR(黑色天使)/5.mp4', 'mp4', '2024-09-02 22:43:26', '2024-09-02 22:43:34', 0);
INSERT INTO `file_nodes` VALUES (54, 49, '4', 'file', '/dongman/NOIR(黑色天使)/4.mp4', 522906, '/dongman/NOIR(黑色天使)/4.mp4', 'mp4', '2024-09-02 22:43:32', '2024-09-02 22:43:38', 0);
INSERT INTO `file_nodes` VALUES (55, 49, '6', 'file', '/dongman/NOIR(黑色天使)/6.mp4', 495856, '/dongman/NOIR(黑色天使)/6.mp4', 'mp4', '2024-09-02 22:43:32', '2024-09-02 22:43:38', 0);
INSERT INTO `file_nodes` VALUES (56, 49, '7', 'file', '/dongman/NOIR(黑色天使)/7.mp4', 420763, '/dongman/NOIR(黑色天使)/7.mp4', 'mp4', '2024-09-02 22:43:40', '2024-09-02 22:43:44', 0);
INSERT INTO `file_nodes` VALUES (57, 49, '8', 'file', '/dongman/NOIR(黑色天使)/8.mp4', 406307, '/dongman/NOIR(黑色天使)/8.mp4', 'mp4', '2024-09-02 22:43:47', '2024-09-02 22:43:51', 0);
INSERT INTO `file_nodes` VALUES (58, 49, '9', 'file', '/dongman/NOIR(黑色天使)/9.mp4', 440718, '/dongman/NOIR(黑色天使)/9.mp4', 'mp4', '2024-09-02 22:44:09', '2024-09-02 22:44:28', 0);
INSERT INTO `file_nodes` VALUES (59, 49, '10', 'file', '/dongman/NOIR(黑色天使)/10.mp4', 380811, '/dongman/NOIR(黑色天使)/10.mp4', 'mp4', '2024-09-02 22:44:14', '2024-09-02 22:44:36', 0);
INSERT INTO `file_nodes` VALUES (60, 49, '11', 'file', '/dongman/NOIR(黑色天使)/11.mp4', 398339, '/dongman/NOIR(黑色天使)/11.mp4', 'mp4', '2024-09-02 22:44:37', '2024-09-02 22:44:48', 0);
INSERT INTO `file_nodes` VALUES (61, 49, '12', 'file', '/dongman/NOIR(黑色天使)/12.mp4', 420847, '/dongman/NOIR(黑色天使)/12.mp4', 'mp4', '2024-09-02 22:44:52', '2024-09-02 22:45:02', 0);
INSERT INTO `file_nodes` VALUES (62, 49, '13', 'file', '/dongman/NOIR(黑色天使)/13.mp4', 485712, '/dongman/NOIR(黑色天使)/13.mp4', 'mp4', '2024-09-02 22:45:04', '2024-09-02 22:45:12', 0);
INSERT INTO `file_nodes` VALUES (63, 49, '15', 'file', '/dongman/NOIR(黑色天使)/15.mp4', 427158, '/dongman/NOIR(黑色天使)/15.mp4', 'mp4', '2024-09-02 22:45:08', '2024-09-02 22:45:15', 0);
INSERT INTO `file_nodes` VALUES (64, 49, '14', 'file', '/dongman/NOIR(黑色天使)/14.mp4', 455849, '/dongman/NOIR(黑色天使)/14.mp4', 'mp4', '2024-09-02 22:45:09', '2024-09-02 22:45:16', 0);
INSERT INTO `file_nodes` VALUES (65, 49, '16', 'file', '/dongman/NOIR(黑色天使)/16.mp4', 445028, '/dongman/NOIR(黑色天使)/16.mp4', 'mp4', '2024-09-02 22:45:22', '2024-09-02 22:45:29', 0);
INSERT INTO `file_nodes` VALUES (66, 49, '17', 'file', '/dongman/NOIR(黑色天使)/17.mp4', 458232, '/dongman/NOIR(黑色天使)/17.mp4', 'mp4', '2024-09-02 22:45:37', '2024-09-02 22:45:48', 0);
INSERT INTO `file_nodes` VALUES (67, 49, '18', 'file', '/dongman/NOIR(黑色天使)/18.mp4', 430964, '/dongman/NOIR(黑色天使)/18.mp4', 'mp4', '2024-09-02 22:45:52', '2024-09-02 22:45:56', 0);
INSERT INTO `file_nodes` VALUES (68, 49, '19', 'file', '/dongman/NOIR(黑色天使)/19.mp4', 437873, '/dongman/NOIR(黑色天使)/19.mp4', 'mp4', '2024-09-02 22:45:57', '2024-09-02 22:46:00', 0);
INSERT INTO `file_nodes` VALUES (69, 49, '20', 'file', '/dongman/NOIR(黑色天使)/20.mp4', 461876, '/dongman/NOIR(黑色天使)/20.mp4', 'mp4', '2024-09-02 22:46:09', '2024-09-02 22:46:12', 0);
INSERT INTO `file_nodes` VALUES (70, 49, '21', 'file', '/dongman/NOIR(黑色天使)/21.mp4', 463110, '/dongman/NOIR(黑色天使)/21.mp4', 'mp4', '2024-09-02 22:46:17', '2024-09-02 22:46:19', 0);
INSERT INTO `file_nodes` VALUES (71, 49, '22', 'file', '/dongman/NOIR(黑色天使)/22.mp4', 512959, '/dongman/NOIR(黑色天使)/22.mp4', 'mp4', '2024-09-02 22:46:23', '2024-09-02 22:46:27', 0);
INSERT INTO `file_nodes` VALUES (72, 49, '23', 'file', '/dongman/NOIR(黑色天使)/23.mp4', 451075, '/dongman/NOIR(黑色天使)/23.mp4', 'mp4', '2024-09-02 22:46:25', '2024-09-02 22:46:28', 0);
INSERT INTO `file_nodes` VALUES (73, 49, '24', 'file', '/dongman/NOIR(黑色天使)/24.mp4', 467594, '/dongman/NOIR(黑色天使)/24.mp4', 'mp4', '2024-09-02 22:46:34', '2024-09-02 22:46:37', 0);
INSERT INTO `file_nodes` VALUES (74, 49, '25', 'file', '/dongman/NOIR(黑色天使)/25.mp4', 474032, '/dongman/NOIR(黑色天使)/25.mp4', 'mp4', '2024-09-02 22:46:41', '2024-09-02 22:46:42', 0);
INSERT INTO `file_nodes` VALUES (75, 49, '26', 'file', '/dongman/NOIR(黑色天使)/26.mp4', 412061, '/dongman/NOIR(黑色天使)/26.mp4', 'mp4', '2024-09-02 22:46:41', '2024-09-02 22:46:45', 0);
INSERT INTO `file_nodes` VALUES (76, 49, '01', 'file', '/dongman/NOIR(黑色天使)/01.mp4', 421518, '/dongman/NOIR(黑色天使)/01.mp4', 'mp4', '2024-09-02 22:51:42', '2024-09-02 22:51:42', 0);
INSERT INTO `file_nodes` VALUES (77, 1, '终末的伊泽塔', 'folder', '/dongman/终末的伊泽塔', 0, NULL, NULL, '2024-09-03 11:53:32', '2024-09-03 11:53:32', 0);
INSERT INTO `file_nodes` VALUES (78, 77, '1', 'file', '/dongman/终末的伊泽塔/1.mp4', 456060, '/dongman/终末的伊泽塔/1.mp4', 'mp4', '2024-09-03 11:55:26', '2024-09-03 11:55:27', 0);
INSERT INTO `file_nodes` VALUES (79, 77, '2', 'file', '/dongman/终末的伊泽塔/2.mp4', 476594, '/dongman/终末的伊泽塔/2.mp4', 'mp4', '2024-09-03 11:55:40', '2024-09-03 11:55:41', 0);
INSERT INTO `file_nodes` VALUES (80, 77, '3', 'file', '/dongman/终末的伊泽塔/3.mp4', 422684, '/dongman/终末的伊泽塔/3.mp4', 'mp4', '2024-09-03 11:55:41', '2024-09-03 11:55:42', 0);
INSERT INTO `file_nodes` VALUES (81, 77, '4', 'file', '/dongman/终末的伊泽塔/4.mp4', 267519, '/dongman/终末的伊泽塔/4.mp4', 'mp4', '2024-09-03 11:55:45', '2024-09-03 11:55:46', 0);
INSERT INTO `file_nodes` VALUES (82, 77, '5', 'file', '/dongman/终末的伊泽塔/5.mp4', 348073, '/dongman/终末的伊泽塔/5.mp4', 'mp4', '2024-09-03 11:55:59', '2024-09-03 11:55:59', 0);
INSERT INTO `file_nodes` VALUES (83, 77, '6', 'file', '/dongman/终末的伊泽塔/6.mp4', 283532, '/dongman/终末的伊泽塔/6.mp4', 'mp4', '2024-09-03 11:56:00', '2024-09-03 11:56:01', 0);
INSERT INTO `file_nodes` VALUES (84, 77, '8', 'file', '/dongman/终末的伊泽塔/8.mp4', 276540, '/dongman/终末的伊泽塔/8.mp4', 'mp4', '2024-09-03 11:56:09', '2024-09-03 11:56:10', 0);
INSERT INTO `file_nodes` VALUES (85, 77, '7', 'file', '/dongman/终末的伊泽塔/7.mp4', 379645, '/dongman/终末的伊泽塔/7.mp4', 'mp4', '2024-09-03 11:56:10', '2024-09-03 11:56:10', 0);
INSERT INTO `file_nodes` VALUES (86, 77, '9', 'file', '/dongman/终末的伊泽塔/9.mp4', 314435, '/dongman/终末的伊泽塔/9.mp4', 'mp4', '2024-09-03 11:56:19', '2024-09-03 11:56:19', 0);
INSERT INTO `file_nodes` VALUES (87, 77, '10', 'file', '/dongman/终末的伊泽塔/10.mp4', 316579, '/dongman/终末的伊泽塔/10.mp4', 'mp4', '2024-09-03 11:56:25', '2024-09-03 11:56:25', 0);
INSERT INTO `file_nodes` VALUES (88, 77, '11', 'file', '/dongman/终末的伊泽塔/11.mp4', 305697, '/dongman/终末的伊泽塔/11.mp4', 'mp4', '2024-09-03 11:56:33', '2024-09-03 11:56:33', 0);
INSERT INTO `file_nodes` VALUES (89, 77, '12', 'file', '/dongman/终末的伊泽塔/12.mp4', 427937, '/dongman/终末的伊泽塔/12.mp4', 'mp4', '2024-09-03 11:56:44', '2024-09-03 11:56:44', 0);
INSERT INTO `file_nodes` VALUES (90, 1, '关于我转生变成史莱姆这档事 第三季', 'folder', '/dongman/关于我转生变成史莱姆这档事 第三季', 0, NULL, NULL, '2024-09-03 12:03:40', '2024-09-03 12:03:40', 0);
INSERT INTO `file_nodes` VALUES (91, 90, '史莱姆20', 'file', '/dongman/关于我转生变成史莱姆这档事 第三季/史莱姆20.mp4', 362832, '/dongman/关于我转生变成史莱姆这档事 第三季/史莱姆20.mp4', 'mp4', '2024-09-03 12:07:53', '2024-09-03 12:07:54', 0);
INSERT INTO `file_nodes` VALUES (92, 1, '神之塔 第二季', 'folder', '/dongman/神之塔 第二季', 0, NULL, NULL, '2024-09-03 12:11:53', '2024-09-03 12:11:53', 0);
INSERT INTO `file_nodes` VALUES (93, 92, '神之塔09', 'file', '/dongman/神之塔 第二季/神之塔09.mp4', 361869, '/dongman/神之塔 第二季/神之塔09.mp4', 'mp4', '2024-09-03 12:13:47', '2024-09-03 12:13:48', 0);
INSERT INTO `file_nodes` VALUES (94, 1, '食灵-零-', 'folder', '/dongman/食灵-零-', 0, NULL, NULL, '2024-09-03 21:14:15', '2024-09-03 21:14:15', 0);
INSERT INTO `file_nodes` VALUES (95, 94, '2', 'file', '/dongman/食灵-零-/2.mp4', 341064, '/dongman/食灵-零-/2.mp4', 'mp4', '2024-09-03 21:16:06', '2024-09-03 21:16:07', 0);
INSERT INTO `file_nodes` VALUES (96, 94, '1', 'file', '/dongman/食灵-零-/1.mp4', 390752, '/dongman/食灵-零-/1.mp4', 'mp4', '2024-09-03 21:16:06', '2024-09-03 21:16:08', 0);
INSERT INTO `file_nodes` VALUES (97, 94, '3', 'file', '/dongman/食灵-零-/3.mp4', 332447, '/dongman/食灵-零-/3.mp4', 'mp4', '2024-09-03 21:16:09', '2024-09-03 21:16:12', 0);
INSERT INTO `file_nodes` VALUES (98, 94, '4', 'file', '/dongman/食灵-零-/4.mp4', 363550, '/dongman/食灵-零-/4.mp4', 'mp4', '2024-09-03 21:16:12', '2024-09-03 21:16:14', 0);
INSERT INTO `file_nodes` VALUES (99, 94, '5', 'file', '/dongman/食灵-零-/5.mp4', 349878, '/dongman/食灵-零-/5.mp4', 'mp4', '2024-09-03 21:16:24', '2024-09-03 21:16:25', 0);
INSERT INTO `file_nodes` VALUES (100, 94, '6', 'file', '/dongman/食灵-零-/6.mp4', 347790, '/dongman/食灵-零-/6.mp4', 'mp4', '2024-09-03 21:16:31', '2024-09-03 21:16:31', 0);
INSERT INTO `file_nodes` VALUES (101, 94, '7', 'file', '/dongman/食灵-零-/7.mp4', 333438, '/dongman/食灵-零-/7.mp4', 'mp4', '2024-09-03 21:16:34', '2024-09-03 21:16:35', 0);
INSERT INTO `file_nodes` VALUES (102, 94, '8', 'file', '/dongman/食灵-零-/8.mp4', 378830, '/dongman/食灵-零-/8.mp4', 'mp4', '2024-09-03 21:16:41', '2024-09-03 21:16:48', 0);
INSERT INTO `file_nodes` VALUES (103, 94, '9', 'file', '/dongman/食灵-零-/9.mp4', 335100, '/dongman/食灵-零-/9.mp4', 'mp4', '2024-09-03 21:16:43', '2024-09-03 21:16:48', 0);
INSERT INTO `file_nodes` VALUES (104, 94, '10', 'file', '/dongman/食灵-零-/10.mp4', 347323, '/dongman/食灵-零-/10.mp4', 'mp4', '2024-09-03 21:16:48', '2024-09-03 21:16:50', 0);
INSERT INTO `file_nodes` VALUES (105, 94, '11', 'file', '/dongman/食灵-零-/11.mp4', 345754, '/dongman/食灵-零-/11.mp4', 'mp4', '2024-09-03 21:16:53', '2024-09-03 21:16:55', 0);
INSERT INTO `file_nodes` VALUES (106, 94, '12', 'file', '/dongman/食灵-零-/12.mp4', 363928, '/dongman/食灵-零-/12.mp4', 'mp4', '2024-09-03 21:17:01', '2024-09-03 21:17:02', 0);
INSERT INTO `file_nodes` VALUES (107, 1, '魔女之旅', 'folder', '/dongman/魔女之旅', 0, NULL, NULL, '2024-09-04 22:34:14', '2024-09-04 22:34:14', 0);
INSERT INTO `file_nodes` VALUES (108, 107, '诚实之国', 'file', '/dongman/魔女之旅/诚实之国.mp4', 612335, '/dongman/魔女之旅/诚实之国.mp4', 'mp4', '2024-09-04 22:48:42', '2024-09-04 22:48:48', 0);
INSERT INTO `file_nodes` VALUES (109, 107, '两个师父', 'file', '/dongman/魔女之旅/两个师父.mp4', 601653, '/dongman/魔女之旅/两个师父.mp4', 'mp4', '2024-09-04 22:48:49', '2024-09-04 22:48:55', 0);
INSERT INTO `file_nodes` VALUES (110, 107, '两个徒弟', 'file', '/dongman/魔女之旅/两个徒弟.mp4', 515790, '/dongman/魔女之旅/两个徒弟.mp4', 'mp4', '2024-09-04 22:48:53', '2024-09-04 22:48:57', 0);
INSERT INTO `file_nodes` VALUES (111, 107, '貌美如花的她   瓶装的幸福', 'file', '/dongman/魔女之旅/貌美如花的她   瓶装的幸福.mp4', 565370, '/dongman/魔女之旅/貌美如花的她   瓶装的幸福.mp4', 'mp4', '2024-09-04 22:49:16', '2024-09-04 22:49:16', 0);
INSERT INTO `file_nodes` VALUES (112, 107, '魔法使之国', 'file', '/dongman/魔女之旅/魔法使之国.mp4', 656271, '/dongman/魔女之旅/魔法使之国.mp4', 'mp4', '2024-09-04 22:50:31', '2024-09-04 22:50:32', 0);
INSERT INTO `file_nodes` VALUES (113, 107, '灰之魔女各种平凡的故事', 'file', '/dongman/魔女之旅/灰之魔女各种平凡的故事.mp4', 600227, '/dongman/魔女之旅/灰之魔女各种平凡的故事.mp4', 'mp4', '2024-09-04 22:50:46', '2024-09-04 22:50:51', 0);
INSERT INTO `file_nodes` VALUES (114, 107, '旅行者刻出的高墙   踩葡萄的少女', 'file', '/dongman/魔女之旅/旅行者刻出的高墙   踩葡萄的少女.mp4', 679097, '/dongman/魔女之旅/旅行者刻出的高墙   踩葡萄的少女.mp4', 'mp4', '2024-09-04 22:51:07', '2024-09-04 22:51:21', 0);
INSERT INTO `file_nodes` VALUES (115, 107, '逆流的悲伤', 'file', '/dongman/魔女之旅/逆流的悲伤.mp4', 587520, '/dongman/魔女之旅/逆流的悲伤.mp4', 'mp4', '2024-09-04 22:51:30', '2024-09-04 22:51:38', 0);
INSERT INTO `file_nodes` VALUES (116, 107, '杀人魔', 'file', '/dongman/魔女之旅/杀人魔.mp4', 601085, '/dongman/魔女之旅/杀人魔.mp4', 'mp4', '2024-09-04 22:51:39', '2024-09-04 22:51:47', 0);
INSERT INTO `file_nodes` VALUES (117, 107, '王立赛勒斯提利亚', 'file', '/dongman/魔女之旅/王立赛勒斯提利亚.mp4', 688847, '/dongman/魔女之旅/王立赛勒斯提利亚.mp4', 'mp4', '2024-09-04 22:52:08', '2024-09-04 22:52:11', 0);
INSERT INTO `file_nodes` VALUES (118, 107, '无民之国的王女', 'file', '/dongman/魔女之旅/无民之国的王女.mp4', 765242, '/dongman/魔女之旅/无民之国的王女.mp4', 'mp4', '2024-09-04 22:52:16', '2024-09-04 22:52:19', 0);
INSERT INTO `file_nodes` VALUES (119, 1, '莉可丽丝', 'folder', '/dongman/莉可丽丝', 0, NULL, NULL, '2024-09-04 22:55:55', '2024-09-04 22:55:55', 0);
INSERT INTO `file_nodes` VALUES (120, 119, '慢慢来', 'file', '/dongman/莉可丽丝/慢慢来.mp4', 333241, '/dongman/莉可丽丝/慢慢来.mp4', 'mp4', '2024-09-04 23:00:05', '2024-09-04 23:00:06', 0);
INSERT INTO `file_nodes` VALUES (121, 119, '多多益善', 'file', '/dongman/莉可丽丝/多多益善.mp4', 364403, '/dongman/莉可丽丝/多多益善.mp4', 'mp4', '2024-09-04 23:00:18', '2024-09-04 23:00:24', 0);
INSERT INTO `file_nodes` VALUES (122, 107, '见习魔女伊蕾娜', 'file', '/dongman/魔女之旅/见习魔女伊蕾娜.mp4', 1000660, '/dongman/魔女之旅/见习魔女伊蕾娜.mp4', 'mp4', '2024-09-04 23:00:28', '2024-09-04 23:00:41', 0);
INSERT INTO `file_nodes` VALUES (123, 119, '欲速则不达', 'file', '/dongman/莉可丽丝/欲速则不达.mp4', 370821, '/dongman/莉可丽丝/欲速则不达.mp4', 'mp4', '2024-09-04 23:00:44', '2024-09-04 23:00:45', 0);
INSERT INTO `file_nodes` VALUES (124, 119, '无所求则无所获', 'file', '/dongman/莉可丽丝/无所求则无所获.mp4', 319078, '/dongman/莉可丽丝/无所求则无所获.mp4', 'mp4', '2024-09-04 23:00:54', '2024-09-04 23:00:54', 0);
INSERT INTO `file_nodes` VALUES (125, 119, '目前情况良好', 'file', '/dongman/莉可丽丝/目前情况良好.mp4', 380408, '/dongman/莉可丽丝/目前情况良好.mp4', 'mp4', '2024-09-04 23:01:02', '2024-09-04 23:01:07', 0);
INSERT INTO `file_nodes` VALUES (126, 119, '不是冤家不聚头', 'file', '/dongman/莉可丽丝/不是冤家不聚头.mp4', 363086, '/dongman/莉可丽丝/不是冤家不聚头.mp4', 'mp4', '2024-09-04 23:01:11', '2024-09-04 23:01:16', 0);
INSERT INTO `file_nodes` VALUES (127, 119, '得过且过', 'file', '/dongman/莉可丽丝/得过且过.mp4', 325058, '/dongman/莉可丽丝/得过且过.mp4', 'mp4', '2024-09-04 23:01:30', '2024-09-04 23:01:30', 0);
INSERT INTO `file_nodes` VALUES (128, 119, '无可挽回', 'file', '/dongman/莉可丽丝/无可挽回.mp4', 320848, '/dongman/莉可丽丝/无可挽回.mp4', 'mp4', '2024-09-04 23:01:32', '2024-09-04 23:01:32', 0);
INSERT INTO `file_nodes` VALUES (129, 119, '以恶报恶', 'file', '/dongman/莉可丽丝/以恶报恶.mp4', 319759, '/dongman/莉可丽丝/以恶报恶.mp4', 'mp4', '2024-09-04 23:01:36', '2024-09-04 23:01:37', 0);
INSERT INTO `file_nodes` VALUES (130, 119, '棋逢对手', 'file', '/dongman/莉可丽丝/棋逢对手.mp4', 390021, '/dongman/莉可丽丝/棋逢对手.mp4', 'mp4', '2024-09-04 23:01:49', '2024-09-04 23:01:53', 0);
INSERT INTO `file_nodes` VALUES (131, 119, '先天之才与后天之能', 'file', '/dongman/莉可丽丝/先天之才与后天之能.mp4', 377322, '/dongman/莉可丽丝/先天之才与后天之能.mp4', 'mp4', '2024-09-04 23:02:06', '2024-09-04 23:02:07', 0);
INSERT INTO `file_nodes` VALUES (132, 119, '铳动彼岸花', 'file', '/dongman/莉可丽丝/铳动彼岸花.mp4', 366085, '/dongman/莉可丽丝/铳动彼岸花.mp4', 'mp4', '2024-09-04 23:02:14', '2024-09-04 23:02:15', 0);
INSERT INTO `file_nodes` VALUES (133, 119, '时间会证明一切', 'file', '/dongman/莉可丽丝/时间会证明一切.mp4', 343799, '/dongman/莉可丽丝/时间会证明一切.mp4', 'mp4', '2024-09-04 23:04:45', '2024-09-04 23:04:48', 0);
INSERT INTO `file_nodes` VALUES (134, 1, '花园里的吸血鬼', 'folder', '/dongman/花园里的吸血鬼', 0, NULL, NULL, '2024-09-05 11:51:34', '2024-09-05 11:51:34', 0);
INSERT INTO `file_nodes` VALUES (135, 134, '1', 'file', '/dongman/花园里的吸血鬼/1.mp4', 767281, '/dongman/花园里的吸血鬼/1.mp4', 'mp4', '2024-09-05 11:52:55', '2024-09-05 11:52:57', 0);
INSERT INTO `file_nodes` VALUES (136, 134, '2', 'file', '/dongman/花园里的吸血鬼/2.mp4', 418641, '/dongman/花园里的吸血鬼/2.mp4', 'mp4', '2024-09-05 11:52:58', '2024-09-05 11:52:59', 0);
INSERT INTO `file_nodes` VALUES (137, 134, '3', 'file', '/dongman/花园里的吸血鬼/3.mp4', 546654, '/dongman/花园里的吸血鬼/3.mp4', 'mp4', '2024-09-05 11:53:16', '2024-09-05 11:53:17', 0);
INSERT INTO `file_nodes` VALUES (138, 134, '4', 'file', '/dongman/花园里的吸血鬼/4.mp4', 523760, '/dongman/花园里的吸血鬼/4.mp4', 'mp4', '2024-09-05 11:53:27', '2024-09-05 11:53:28', 0);
INSERT INTO `file_nodes` VALUES (141, 1, '失忆煽动WIXOSS', 'folder', '/dongman/失忆煽动WIXOSS', 0, NULL, NULL, '2024-09-06 17:30:49', '2024-09-06 17:30:49', 0);
INSERT INTO `file_nodes` VALUES (142, 141, '1', 'file', '/dongman/失忆煽动WIXOSS/1.mp4', 465847, '/dongman/失忆煽动WIXOSS/1.mp4', 'mp4', '2024-09-06 17:33:45', '2024-09-06 17:33:53', 0);
INSERT INTO `file_nodes` VALUES (143, 141, '2', 'file', '/dongman/失忆煽动WIXOSS/2.mp4', 477418, '/dongman/失忆煽动WIXOSS/2.mp4', 'mp4', '2024-09-06 17:33:49', '2024-09-06 17:33:55', 0);
INSERT INTO `file_nodes` VALUES (144, 141, '3', 'file', '/dongman/失忆煽动WIXOSS/3.mp4', 352923, '/dongman/失忆煽动WIXOSS/3.mp4', 'mp4', '2024-09-06 17:33:57', '2024-09-06 17:34:03', 0);
INSERT INTO `file_nodes` VALUES (145, 141, '4', 'file', '/dongman/失忆煽动WIXOSS/4.mp4', 322860, '/dongman/失忆煽动WIXOSS/4.mp4', 'mp4', '2024-09-06 17:34:02', '2024-09-06 17:34:05', 0);
INSERT INTO `file_nodes` VALUES (146, 141, '5', 'file', '/dongman/失忆煽动WIXOSS/5.mp4', 350359, '/dongman/失忆煽动WIXOSS/5.mp4', 'mp4', '2024-09-06 17:34:07', '2024-09-06 17:34:11', 0);
INSERT INTO `file_nodes` VALUES (147, 141, '6', 'file', '/dongman/失忆煽动WIXOSS/6.mp4', 390006, '/dongman/失忆煽动WIXOSS/6.mp4', 'mp4', '2024-09-06 17:34:24', '2024-09-06 17:34:32', 0);
INSERT INTO `file_nodes` VALUES (148, 141, '7', 'file', '/dongman/失忆煽动WIXOSS/7.mp4', 368895, '/dongman/失忆煽动WIXOSS/7.mp4', 'mp4', '2024-09-06 17:34:29', '2024-09-06 17:34:36', 0);
INSERT INTO `file_nodes` VALUES (149, 141, '9', 'file', '/dongman/失忆煽动WIXOSS/9.mp4', 321413, '/dongman/失忆煽动WIXOSS/9.mp4', 'mp4', '2024-09-06 17:34:35', '2024-09-06 17:34:38', 0);
INSERT INTO `file_nodes` VALUES (150, 141, '10', 'file', '/dongman/失忆煽动WIXOSS/10.mp4', 332158, '/dongman/失忆煽动WIXOSS/10.mp4', 'mp4', '2024-09-06 17:34:40', '2024-09-06 17:34:44', 0);
INSERT INTO `file_nodes` VALUES (151, 141, '8', 'file', '/dongman/失忆煽动WIXOSS/8.mp4', 511241, '/dongman/失忆煽动WIXOSS/8.mp4', 'mp4', '2024-09-06 17:34:41', '2024-09-06 17:34:47', 0);
INSERT INTO `file_nodes` VALUES (152, 141, '11', 'file', '/dongman/失忆煽动WIXOSS/11.mp4', 364582, '/dongman/失忆煽动WIXOSS/11.mp4', 'mp4', '2024-09-06 17:35:00', '2024-09-06 17:35:01', 0);
INSERT INTO `file_nodes` VALUES (153, 141, '12', 'file', '/dongman/失忆煽动WIXOSS/12.mp4', 485464, '/dongman/失忆煽动WIXOSS/12.mp4', 'mp4', '2024-09-06 17:35:10', '2024-09-06 17:35:10', 0);
INSERT INTO `file_nodes` VALUES (154, 1, 'MADLAX(玛德莱克丝)', 'folder', '/dongman/MADLAX(玛德莱克丝)', 0, NULL, NULL, '2024-09-06 17:39:55', '2024-09-06 17:39:55', 0);
INSERT INTO `file_nodes` VALUES (155, 154, '1', 'file', '/dongman/MADLAX(玛德莱克丝)/1.mp4', 202984, '/dongman/MADLAX(玛德莱克丝)/1.mp4', 'mp4', '2024-09-06 18:25:51', '2024-09-06 18:25:51', 0);
INSERT INTO `file_nodes` VALUES (156, 154, '2', 'file', '/dongman/MADLAX(玛德莱克丝)/2.mp4', 169034, '/dongman/MADLAX(玛德莱克丝)/2.mp4', 'mp4', '2024-09-06 18:26:07', '2024-09-06 18:26:07', 0);
INSERT INTO `file_nodes` VALUES (157, 154, '3', 'file', '/dongman/MADLAX(玛德莱克丝)/3.mp4', 157464, '/dongman/MADLAX(玛德莱克丝)/3.mp4', 'mp4', '2024-09-06 18:26:38', '2024-09-06 18:26:38', 0);
INSERT INTO `file_nodes` VALUES (158, 154, '4', 'file', '/dongman/MADLAX(玛德莱克丝)/4.mp4', 178576, '/dongman/MADLAX(玛德莱克丝)/4.mp4', 'mp4', '2024-09-06 19:27:58', '2024-09-06 19:27:59', 0);
INSERT INTO `file_nodes` VALUES (159, 154, '5', 'file', '/dongman/MADLAX(玛德莱克丝)/5.mp4', 165794, '/dongman/MADLAX(玛德莱克丝)/5.mp4', 'mp4', '2024-09-06 19:28:08', '2024-09-06 19:28:09', 0);
INSERT INTO `file_nodes` VALUES (160, 154, '6', 'file', '/dongman/MADLAX(玛德莱克丝)/6.mp4', 161078, '/dongman/MADLAX(玛德莱克丝)/6.mp4', 'mp4', '2024-09-06 19:28:19', '2024-09-06 19:28:19', 0);
INSERT INTO `file_nodes` VALUES (161, 154, '7', 'file', '/dongman/MADLAX(玛德莱克丝)/7.mp4', 148262, '/dongman/MADLAX(玛德莱克丝)/7.mp4', 'mp4', '2024-09-06 19:28:25', '2024-09-06 19:28:25', 0);
INSERT INTO `file_nodes` VALUES (162, 154, '8', 'file', '/dongman/MADLAX(玛德莱克丝)/8.mp4', 171464, '/dongman/MADLAX(玛德莱克丝)/8.mp4', 'mp4', '2024-09-06 19:28:31', '2024-09-06 19:28:31', 0);
INSERT INTO `file_nodes` VALUES (163, 154, '9', 'file', '/dongman/MADLAX(玛德莱克丝)/9.mp4', 162840, '/dongman/MADLAX(玛德莱克丝)/9.mp4', 'mp4', '2024-09-06 19:28:35', '2024-09-06 19:28:35', 0);
INSERT INTO `file_nodes` VALUES (164, 154, '10', 'file', '/dongman/MADLAX(玛德莱克丝)/10.mp4', 175749, '/dongman/MADLAX(玛德莱克丝)/10.mp4', 'mp4', '2024-09-06 19:28:42', '2024-09-06 19:28:42', 0);
INSERT INTO `file_nodes` VALUES (165, 154, '11', 'file', '/dongman/MADLAX(玛德莱克丝)/11.mp4', 164549, '/dongman/MADLAX(玛德莱克丝)/11.mp4', 'mp4', '2024-09-06 19:28:49', '2024-09-06 19:28:50', 0);
INSERT INTO `file_nodes` VALUES (166, 154, '12', 'file', '/dongman/MADLAX(玛德莱克丝)/12.mp4', 162612, '/dongman/MADLAX(玛德莱克丝)/12.mp4', 'mp4', '2024-09-06 19:28:54', '2024-09-06 19:28:54', 0);
INSERT INTO `file_nodes` VALUES (167, 154, '13', 'file', '/dongman/MADLAX(玛德莱克丝)/13.mp4', 150675, '/dongman/MADLAX(玛德莱克丝)/13.mp4', 'mp4', '2024-09-06 19:28:58', '2024-09-06 19:28:58', 0);
INSERT INTO `file_nodes` VALUES (168, 154, '14', 'file', '/dongman/MADLAX(玛德莱克丝)/14.mp4', 191313, '/dongman/MADLAX(玛德莱克丝)/14.mp4', 'mp4', '2024-09-06 19:29:05', '2024-09-06 19:29:05', 0);
INSERT INTO `file_nodes` VALUES (169, 154, '15', 'file', '/dongman/MADLAX(玛德莱克丝)/15.mp4', 170685, '/dongman/MADLAX(玛德莱克丝)/15.mp4', 'mp4', '2024-09-06 19:29:08', '2024-09-06 19:29:08', 0);
INSERT INTO `file_nodes` VALUES (170, 154, '16', 'file', '/dongman/MADLAX(玛德莱克丝)/16.mp4', 167582, '/dongman/MADLAX(玛德莱克丝)/16.mp4', 'mp4', '2024-09-06 19:29:14', '2024-09-06 19:29:14', 0);
INSERT INTO `file_nodes` VALUES (171, 154, '17', 'file', '/dongman/MADLAX(玛德莱克丝)/17.mp4', 179599, '/dongman/MADLAX(玛德莱克丝)/17.mp4', 'mp4', '2024-09-06 19:29:19', '2024-09-06 19:29:19', 0);
INSERT INTO `file_nodes` VALUES (172, 90, '21', 'file', '/dongman/关于我转生变成史莱姆这档事 第三季/21.mp4', 534600, '/dongman/关于我转生变成史莱姆这档事 第三季/21.mp4', 'mp4', '2024-09-07 00:22:55', '2024-09-07 00:22:56', 0);
INSERT INTO `file_nodes` VALUES (173, 154, '18', 'file', '/dongman/MADLAX(玛德莱克丝)/18.mp4', 156214, '/dongman/MADLAX(玛德莱克丝)/18.mp4', 'mp4', '2024-09-07 12:15:22', '2024-09-07 12:15:22', 0);
INSERT INTO `file_nodes` VALUES (174, 154, '19', 'file', '/dongman/MADLAX(玛德莱克丝)/19.mp4', 174470, '/dongman/MADLAX(玛德莱克丝)/19.mp4', 'mp4', '2024-09-07 12:15:36', '2024-09-07 12:15:36', 0);
INSERT INTO `file_nodes` VALUES (175, 154, '20', 'file', '/dongman/MADLAX(玛德莱克丝)/20.mp4', 175203, '/dongman/MADLAX(玛德莱克丝)/20.mp4', 'mp4', '2024-09-07 12:15:42', '2024-09-07 12:15:43', 0);
INSERT INTO `file_nodes` VALUES (176, 154, '21', 'file', '/dongman/MADLAX(玛德莱克丝)/21.mp4', 207995, '/dongman/MADLAX(玛德莱克丝)/21.mp4', 'mp4', '2024-09-07 12:15:55', '2024-09-07 12:15:55', 0);
INSERT INTO `file_nodes` VALUES (177, 154, '22', 'file', '/dongman/MADLAX(玛德莱克丝)/22.mp4', 193620, '/dongman/MADLAX(玛德莱克丝)/22.mp4', 'mp4', '2024-09-07 12:16:29', '2024-09-07 12:16:30', 0);
INSERT INTO `file_nodes` VALUES (178, 154, '23', 'file', '/dongman/MADLAX(玛德莱克丝)/23.mp4', 181138, '/dongman/MADLAX(玛德莱克丝)/23.mp4', 'mp4', '2024-09-07 12:16:34', '2024-09-07 12:16:35', 0);
INSERT INTO `file_nodes` VALUES (179, 154, '24', 'file', '/dongman/MADLAX(玛德莱克丝)/24.mp4', 160953, '/dongman/MADLAX(玛德莱克丝)/24.mp4', 'mp4', '2024-09-07 12:16:41', '2024-09-07 12:16:41', 0);
INSERT INTO `file_nodes` VALUES (180, 154, '25', 'file', '/dongman/MADLAX(玛德莱克丝)/25.mp4', 216881, '/dongman/MADLAX(玛德莱克丝)/25.mp4', 'mp4', '2024-09-07 12:16:49', '2024-09-07 12:16:49', 0);
INSERT INTO `file_nodes` VALUES (181, 154, '26', 'file', '/dongman/MADLAX(玛德莱克丝)/26.mp4', 237776, '/dongman/MADLAX(玛德莱克丝)/26.mp4', 'mp4', '2024-09-07 12:16:58', '2024-09-07 12:16:59', 0);
INSERT INTO `file_nodes` VALUES (182, 1, '失忆融合WIXOSS', 'folder', '/dongman/失忆融合WIXOSS', 0, NULL, NULL, '2024-09-07 18:19:35', '2024-09-07 18:19:35', 0);
INSERT INTO `file_nodes` VALUES (184, 182, '3', 'file', '/dongman/失忆融合WIXOSS/3.mp4', 314132, '/dongman/失忆融合WIXOSS/3.mp4', 'mp4', '2024-09-07 18:26:02', '2024-09-07 18:26:06', 0);
INSERT INTO `file_nodes` VALUES (185, 182, '4', 'file', '/dongman/失忆融合WIXOSS/4.mp4', 356713, '/dongman/失忆融合WIXOSS/4.mp4', 'mp4', '2024-09-07 18:26:17', '2024-09-07 18:26:23', 0);
INSERT INTO `file_nodes` VALUES (186, 182, '2', 'file', '/dongman/失忆融合WIXOSS/2.mp4', 604208, '/dongman/失忆融合WIXOSS/2.mp4', 'mp4', '2024-09-07 18:26:20', '2024-09-07 18:26:27', 0);
INSERT INTO `file_nodes` VALUES (187, 182, '5', 'file', '/dongman/失忆融合WIXOSS/5.mp4', 389547, '/dongman/失忆融合WIXOSS/5.mp4', 'mp4', '2024-09-07 18:26:24', '2024-09-07 18:26:28', 0);
INSERT INTO `file_nodes` VALUES (188, 182, '6', 'file', '/dongman/失忆融合WIXOSS/6.mp4', 418616, '/dongman/失忆融合WIXOSS/6.mp4', 'mp4', '2024-09-07 18:26:46', '2024-09-07 18:26:52', 0);
INSERT INTO `file_nodes` VALUES (189, 182, '7', 'file', '/dongman/失忆融合WIXOSS/7.mp4', 323581, '/dongman/失忆融合WIXOSS/7.mp4', 'mp4', '2024-09-07 18:26:48', '2024-09-07 18:26:53', 0);
INSERT INTO `file_nodes` VALUES (190, 182, '8', 'file', '/dongman/失忆融合WIXOSS/8.mp4', 310041, '/dongman/失忆融合WIXOSS/8.mp4', 'mp4', '2024-09-07 18:26:53', '2024-09-07 18:26:55', 0);
INSERT INTO `file_nodes` VALUES (191, 182, '9', 'file', '/dongman/失忆融合WIXOSS/9.mp4', 330667, '/dongman/失忆融合WIXOSS/9.mp4', 'mp4', '2024-09-07 18:26:59', '2024-09-07 18:27:01', 0);
INSERT INTO `file_nodes` VALUES (192, 182, '10', 'file', '/dongman/失忆融合WIXOSS/10.mp4', 459684, '/dongman/失忆融合WIXOSS/10.mp4', 'mp4', '2024-09-07 18:27:24', '2024-09-07 18:27:28', 0);
INSERT INTO `file_nodes` VALUES (193, 182, '11', 'file', '/dongman/失忆融合WIXOSS/11.mp4', 447661, '/dongman/失忆融合WIXOSS/11.mp4', 'mp4', '2024-09-07 18:27:31', '2024-09-07 18:27:43', 0);
INSERT INTO `file_nodes` VALUES (194, 182, '12', 'file', '/dongman/失忆融合WIXOSS/12.mp4', 444988, '/dongman/失忆融合WIXOSS/12.mp4', 'mp4', '2024-09-07 18:27:40', '2024-09-07 18:27:55', 0);
INSERT INTO `file_nodes` VALUES (195, 1, '选择感染者WIXOSS', 'folder', '/dongman/选择感染者WIXOSS', 0, NULL, NULL, '2024-09-07 18:29:47', '2024-09-07 18:29:47', 0);
INSERT INTO `file_nodes` VALUES (196, 182, '1', 'file', '/dongman/失忆融合WIXOSS/1.mp4', 405188, '/dongman/失忆融合WIXOSS/1.mp4', 'mp4', '2024-09-07 18:33:22', '2024-09-07 18:33:22', 0);
INSERT INTO `file_nodes` VALUES (197, 195, '2', 'file', '/dongman/选择感染者WIXOSS/2.mp4', 685686, '/dongman/选择感染者WIXOSS/2.mp4', 'mp4', '2024-09-07 18:34:55', '2024-09-07 18:35:04', 0);
INSERT INTO `file_nodes` VALUES (198, 195, '1', 'file', '/dongman/选择感染者WIXOSS/1.mp4', 849638, '/dongman/选择感染者WIXOSS/1.mp4', 'mp4', '2024-09-07 18:34:55', '2024-09-07 18:35:05', 0);
INSERT INTO `file_nodes` VALUES (199, 195, '4', 'file', '/dongman/选择感染者WIXOSS/4.mp4', 782079, '/dongman/选择感染者WIXOSS/4.mp4', 'mp4', '2024-09-07 18:35:15', '2024-09-07 18:35:21', 0);
INSERT INTO `file_nodes` VALUES (200, 195, '3', 'file', '/dongman/选择感染者WIXOSS/3.mp4', 918972, '/dongman/选择感染者WIXOSS/3.mp4', 'mp4', '2024-09-07 18:35:16', '2024-09-07 18:35:22', 0);
INSERT INTO `file_nodes` VALUES (201, 195, '6', 'file', '/dongman/选择感染者WIXOSS/6.mp4', 694775, '/dongman/选择感染者WIXOSS/6.mp4', 'mp4', '2024-09-07 18:37:00', '2024-09-07 18:37:10', 0);
INSERT INTO `file_nodes` VALUES (202, 195, '8', 'file', '/dongman/选择感染者WIXOSS/8.mp4', 694275, '/dongman/选择感染者WIXOSS/8.mp4', 'mp4', '2024-09-07 18:37:03', '2024-09-07 18:37:11', 0);
INSERT INTO `file_nodes` VALUES (203, 195, '7', 'file', '/dongman/选择感染者WIXOSS/7.mp4', 776124, '/dongman/选择感染者WIXOSS/7.mp4', 'mp4', '2024-09-07 18:37:05', '2024-09-07 18:37:13', 0);
INSERT INTO `file_nodes` VALUES (204, 195, '5', 'file', '/dongman/选择感染者WIXOSS/5.mp4', 1382329, '/dongman/选择感染者WIXOSS/5.mp4', 'mp4', '2024-09-07 18:37:38', '2024-09-07 18:38:11', 0);
INSERT INTO `file_nodes` VALUES (205, 195, '9', 'file', '/dongman/选择感染者WIXOSS/9.mp4', 591971, '/dongman/选择感染者WIXOSS/9.mp4', 'mp4', '2024-09-07 18:37:55', '2024-09-07 18:38:05', 0);
INSERT INTO `file_nodes` VALUES (206, 195, '10', 'file', '/dongman/选择感染者WIXOSS/10.mp4', 542791, '/dongman/选择感染者WIXOSS/10.mp4', 'mp4', '2024-09-07 18:37:57', '2024-09-07 18:38:07', 0);
INSERT INTO `file_nodes` VALUES (207, 195, '11', 'file', '/dongman/选择感染者WIXOSS/11.mp4', 649323, '/dongman/选择感染者WIXOSS/11.mp4', 'mp4', '2024-09-07 18:38:22', '2024-09-07 18:38:31', 0);
INSERT INTO `file_nodes` VALUES (208, 195, '12', 'file', '/dongman/选择感染者WIXOSS/12.mp4', 623699, '/dongman/选择感染者WIXOSS/12.mp4', 'mp4', '2024-09-07 18:38:56', '2024-09-07 18:38:58', 0);
INSERT INTO `file_nodes` VALUES (209, 1, '选择扩散者WIXOSS', 'folder', '/dongman/选择扩散者WIXOSS', 0, NULL, NULL, '2024-09-07 18:40:40', '2024-09-07 18:40:40', 0);
INSERT INTO `file_nodes` VALUES (210, 209, '2', 'file', '/dongman/选择扩散者WIXOSS/2.mp4', 188621, '/dongman/选择扩散者WIXOSS/2.mp4', 'mp4', '2024-09-07 18:42:10', '2024-09-07 18:42:10', 0);
INSERT INTO `file_nodes` VALUES (211, 209, '1', 'file', '/dongman/选择扩散者WIXOSS/1.mp4', 265555, '/dongman/选择扩散者WIXOSS/1.mp4', 'mp4', '2024-09-07 18:42:11', '2024-09-07 18:42:12', 0);
INSERT INTO `file_nodes` VALUES (212, 209, '3', 'file', '/dongman/选择扩散者WIXOSS/3.mp4', 205201, '/dongman/选择扩散者WIXOSS/3.mp4', 'mp4', '2024-09-07 18:42:13', '2024-09-07 18:42:14', 0);
INSERT INTO `file_nodes` VALUES (213, 209, '4', 'file', '/dongman/选择扩散者WIXOSS/4.mp4', 248429, '/dongman/选择扩散者WIXOSS/4.mp4', 'mp4', '2024-09-07 18:42:18', '2024-09-07 18:42:18', 0);
INSERT INTO `file_nodes` VALUES (214, 209, '5', 'file', '/dongman/选择扩散者WIXOSS/5.mp4', 230318, '/dongman/选择扩散者WIXOSS/5.mp4', 'mp4', '2024-09-07 18:42:57', '2024-09-07 18:42:58', 0);
INSERT INTO `file_nodes` VALUES (215, 209, '6', 'file', '/dongman/选择扩散者WIXOSS/6.mp4', 203852, '/dongman/选择扩散者WIXOSS/6.mp4', 'mp4', '2024-09-07 18:43:01', '2024-09-07 18:43:01', 0);
INSERT INTO `file_nodes` VALUES (216, 209, '7', 'file', '/dongman/选择扩散者WIXOSS/7.mp4', 179086, '/dongman/选择扩散者WIXOSS/7.mp4', 'mp4', '2024-09-07 18:43:03', '2024-09-07 18:43:03', 0);
INSERT INTO `file_nodes` VALUES (217, 209, '8', 'file', '/dongman/选择扩散者WIXOSS/8.mp4', 198728, '/dongman/选择扩散者WIXOSS/8.mp4', 'mp4', '2024-09-07 18:43:07', '2024-09-07 18:43:07', 0);
INSERT INTO `file_nodes` VALUES (218, 209, '9', 'file', '/dongman/选择扩散者WIXOSS/9.mp4', 201439, '/dongman/选择扩散者WIXOSS/9.mp4', 'mp4', '2024-09-07 18:43:13', '2024-09-07 18:43:13', 0);
INSERT INTO `file_nodes` VALUES (219, 209, '10', 'file', '/dongman/选择扩散者WIXOSS/10.mp4', 217531, '/dongman/选择扩散者WIXOSS/10.mp4', 'mp4', '2024-09-07 18:43:21', '2024-09-07 18:43:22', 0);
INSERT INTO `file_nodes` VALUES (220, 209, '11', 'file', '/dongman/选择扩散者WIXOSS/11.mp4', 291082, '/dongman/选择扩散者WIXOSS/11.mp4', 'mp4', '2024-09-07 18:43:29', '2024-09-07 18:43:30', 0);
INSERT INTO `file_nodes` VALUES (221, 209, '12', 'file', '/dongman/选择扩散者WIXOSS/12.mp4', 268200, '/dongman/选择扩散者WIXOSS/12.mp4', 'mp4', '2024-09-07 18:43:33', '2024-09-07 18:43:35', 0);
INSERT INTO `file_nodes` VALUES (222, 134, '5', 'file', '/dongman/花园里的吸血鬼/5.mp4', 658149, '/dongman/花园里的吸血鬼/5.mp4', 'mp4', '2024-09-07 18:46:02', '2024-09-07 18:46:03', 0);
INSERT INTO `file_nodes` VALUES (223, 1, '柑橘味香气', 'folder', '/dongman/柑橘味香气', 0, NULL, NULL, '2024-09-07 19:50:43', '2024-09-07 19:50:43', 0);
INSERT INTO `file_nodes` VALUES (224, 223, '1', 'file', '/dongman/柑橘味香气/1.mp4', 388848, '/dongman/柑橘味香气/1.mp4', 'mp4', '2024-09-07 19:52:47', '2024-09-07 19:52:47', 0);
INSERT INTO `file_nodes` VALUES (225, 223, '2', 'file', '/dongman/柑橘味香气/2.mp4', 341923, '/dongman/柑橘味香气/2.mp4', 'mp4', '2024-09-07 19:52:47', '2024-09-07 19:52:48', 0);
INSERT INTO `file_nodes` VALUES (226, 223, '3', 'file', '/dongman/柑橘味香气/3.mp4', 317342, '/dongman/柑橘味香气/3.mp4', 'mp4', '2024-09-07 19:52:48', '2024-09-07 19:52:49', 0);
INSERT INTO `file_nodes` VALUES (227, 223, '4', 'file', '/dongman/柑橘味香气/4.mp4', 320988, '/dongman/柑橘味香气/4.mp4', 'mp4', '2024-09-07 19:52:52', '2024-09-07 19:52:57', 0);
INSERT INTO `file_nodes` VALUES (228, 223, '5', 'file', '/dongman/柑橘味香气/5.mp4', 317226, '/dongman/柑橘味香气/5.mp4', 'mp4', '2024-09-07 19:53:35', '2024-09-07 19:53:37', 0);
INSERT INTO `file_nodes` VALUES (229, 223, '6', 'file', '/dongman/柑橘味香气/6.mp4', 331356, '/dongman/柑橘味香气/6.mp4', 'mp4', '2024-09-07 19:53:44', '2024-09-07 19:53:44', 0);
INSERT INTO `file_nodes` VALUES (230, 223, '7', 'file', '/dongman/柑橘味香气/7.mp4', 318691, '/dongman/柑橘味香气/7.mp4', 'mp4', '2024-09-07 19:53:47', '2024-09-07 19:53:47', 0);
INSERT INTO `file_nodes` VALUES (231, 223, '8', 'file', '/dongman/柑橘味香气/8.mp4', 318027, '/dongman/柑橘味香气/8.mp4', 'mp4', '2024-09-07 19:53:50', '2024-09-07 19:53:50', 0);
INSERT INTO `file_nodes` VALUES (232, 223, '9', 'file', '/dongman/柑橘味香气/9.mp4', 301802, '/dongman/柑橘味香气/9.mp4', 'mp4', '2024-09-07 19:54:23', '2024-09-07 19:54:24', 0);
INSERT INTO `file_nodes` VALUES (233, 223, '10', 'file', '/dongman/柑橘味香气/10.mp4', 320742, '/dongman/柑橘味香气/10.mp4', 'mp4', '2024-09-07 19:54:30', '2024-09-07 19:54:31', 0);
INSERT INTO `file_nodes` VALUES (234, 223, '11', 'file', '/dongman/柑橘味香气/11.mp4', 307520, '/dongman/柑橘味香气/11.mp4', 'mp4', '2024-09-07 19:54:32', '2024-09-07 19:54:33', 0);
INSERT INTO `file_nodes` VALUES (235, 223, '12', 'file', '/dongman/柑橘味香气/12.mp4', 304864, '/dongman/柑橘味香气/12.mp4', 'mp4', '2024-09-07 19:54:39', '2024-09-07 19:54:39', 0);
INSERT INTO `file_nodes` VALUES (236, 1, '幸腹涂鸦', 'folder', '/dongman/幸腹涂鸦', 0, NULL, NULL, '2024-09-07 19:57:51', '2024-09-07 19:57:51', 0);
INSERT INTO `file_nodes` VALUES (237, 1, '战翼的希格德莉法', 'folder', '/dongman/战翼的希格德莉法', 0, NULL, NULL, '2024-09-07 20:00:44', '2024-09-07 20:00:44', 0);
INSERT INTO `file_nodes` VALUES (238, 237, '2', 'file', '/dongman/战翼的希格德莉法/2.mp4', 417955, '/dongman/战翼的希格德莉法/2.mp4', 'mp4', '2024-09-07 20:41:41', '2024-09-07 20:41:44', 0);
INSERT INTO `file_nodes` VALUES (239, 237, '3', 'file', '/dongman/战翼的希格德莉法/3.mp4', 301700, '/dongman/战翼的希格德莉法/3.mp4', 'mp4', '2024-09-07 20:41:41', '2024-09-07 20:41:42', 0);
INSERT INTO `file_nodes` VALUES (240, 237, '4', 'file', '/dongman/战翼的希格德莉法/4.mp4', 426038, '/dongman/战翼的希格德莉法/4.mp4', 'mp4', '2024-09-07 20:41:49', '2024-09-07 20:41:53', 0);
INSERT INTO `file_nodes` VALUES (241, 237, '1', 'file', '/dongman/战翼的希格德莉法/1.mp4', 939555, '/dongman/战翼的希格德莉法/1.mp4', 'mp4', '2024-09-07 20:41:50', '2024-09-07 20:42:01', 0);
INSERT INTO `file_nodes` VALUES (242, 237, '5', 'file', '/dongman/战翼的希格德莉法/5.mp4', 375240, '/dongman/战翼的希格德莉法/5.mp4', 'mp4', '2024-09-07 20:42:25', '2024-09-07 20:42:25', 0);
INSERT INTO `file_nodes` VALUES (243, 237, '6', 'file', '/dongman/战翼的希格德莉法/6.mp4', 417030, '/dongman/战翼的希格德莉法/6.mp4', 'mp4', '2024-09-07 20:42:32', '2024-09-07 20:42:33', 0);
INSERT INTO `file_nodes` VALUES (244, 237, '8', 'file', '/dongman/战翼的希格德莉法/8.mp4', 360513, '/dongman/战翼的希格德莉法/8.mp4', 'mp4', '2024-09-07 20:42:36', '2024-09-07 20:42:39', 0);
INSERT INTO `file_nodes` VALUES (245, 237, '7', 'file', '/dongman/战翼的希格德莉法/7.mp4', 437894, '/dongman/战翼的希格德莉法/7.mp4', 'mp4', '2024-09-07 20:42:37', '2024-09-07 20:42:43', 0);
INSERT INTO `file_nodes` VALUES (246, 237, '10', 'file', '/dongman/战翼的希格德莉法/10.mp4', 388244, '/dongman/战翼的希格德莉法/10.mp4', 'mp4', '2024-09-07 20:43:06', '2024-09-07 20:43:10', 0);
INSERT INTO `file_nodes` VALUES (247, 237, '9', 'file', '/dongman/战翼的希格德莉法/9.mp4', 639034, '/dongman/战翼的希格德莉法/9.mp4', 'mp4', '2024-09-07 20:43:17', '2024-09-07 20:43:35', 0);
INSERT INTO `file_nodes` VALUES (248, 237, '11', 'file', '/dongman/战翼的希格德莉法/11.mp4', 598242, '/dongman/战翼的希格德莉法/11.mp4', 'mp4', '2024-09-07 20:43:30', '2024-09-07 20:43:41', 0);
INSERT INTO `file_nodes` VALUES (249, 237, '12', 'file', '/dongman/战翼的希格德莉法/12.mp4', 573475, '/dongman/战翼的希格德莉法/12.mp4', 'mp4', '2024-09-07 20:43:32', '2024-09-07 20:43:41', 0);
INSERT INTO `file_nodes` VALUES (250, 236, '1', 'file', '/dongman/幸腹涂鸦/1.mp4', 287530, '/dongman/幸腹涂鸦/1.mp4', 'mp4', '2024-09-07 20:44:37', '2024-09-07 20:44:38', 0);
INSERT INTO `file_nodes` VALUES (251, 236, '2', 'file', '/dongman/幸腹涂鸦/2.mp4', 380135, '/dongman/幸腹涂鸦/2.mp4', 'mp4', '2024-09-07 20:44:46', '2024-09-07 20:44:48', 0);
INSERT INTO `file_nodes` VALUES (252, 236, '3', 'file', '/dongman/幸腹涂鸦/3.mp4', 305871, '/dongman/幸腹涂鸦/3.mp4', 'mp4', '2024-09-07 20:44:47', '2024-09-07 20:44:49', 0);
INSERT INTO `file_nodes` VALUES (253, 236, '4', 'file', '/dongman/幸腹涂鸦/4.mp4', 334974, '/dongman/幸腹涂鸦/4.mp4', 'mp4', '2024-09-07 20:44:52', '2024-09-07 20:44:55', 0);
INSERT INTO `file_nodes` VALUES (254, 236, '5', 'file', '/dongman/幸腹涂鸦/5.mp4', 362275, '/dongman/幸腹涂鸦/5.mp4', 'mp4', '2024-09-07 20:44:57', '2024-09-07 20:45:03', 0);
INSERT INTO `file_nodes` VALUES (255, 236, '6', 'file', '/dongman/幸腹涂鸦/6.mp4', 277562, '/dongman/幸腹涂鸦/6.mp4', 'mp4', '2024-09-07 20:44:57', '2024-09-07 20:45:02', 0);
INSERT INTO `file_nodes` VALUES (256, 236, '7', 'file', '/dongman/幸腹涂鸦/7.mp4', 292342, '/dongman/幸腹涂鸦/7.mp4', 'mp4', '2024-09-07 20:45:12', '2024-09-07 20:45:18', 0);
INSERT INTO `file_nodes` VALUES (257, 236, '8', 'file', '/dongman/幸腹涂鸦/8.mp4', 297894, '/dongman/幸腹涂鸦/8.mp4', 'mp4', '2024-09-07 20:45:17', '2024-09-07 20:45:22', 0);
INSERT INTO `file_nodes` VALUES (258, 236, '10', 'file', '/dongman/幸腹涂鸦/10.mp4', 293479, '/dongman/幸腹涂鸦/10.mp4', 'mp4', '2024-09-07 20:45:29', '2024-09-07 20:45:35', 0);
INSERT INTO `file_nodes` VALUES (259, 236, '11', 'file', '/dongman/幸腹涂鸦/11.mp4', 265178, '/dongman/幸腹涂鸦/11.mp4', 'mp4', '2024-09-07 20:45:40', '2024-09-07 20:45:41', 0);
INSERT INTO `file_nodes` VALUES (260, 236, '12', 'file', '/dongman/幸腹涂鸦/12.mp4', 278024, '/dongman/幸腹涂鸦/12.mp4', 'mp4', '2024-09-07 20:45:44', '2024-09-07 20:45:45', 0);
INSERT INTO `file_nodes` VALUES (261, 236, '9', 'file', '/dongman/幸腹涂鸦/9.mp4', 677388, '/dongman/幸腹涂鸦/9.mp4', 'mp4', '2024-09-07 20:45:45', '2024-09-07 20:45:53', 0);
INSERT INTO `file_nodes` VALUES (262, 1, '青春纪行', 'folder', '/dongman/青春纪行', 0, NULL, NULL, '2024-09-08 11:19:42', '2024-09-08 11:19:42', 0);
INSERT INTO `file_nodes` VALUES (263, 262, '1', 'file', '/dongman/青春纪行/1.mp4', 365063, '/dongman/青春纪行/1.mp4', 'mp4', '2024-09-08 11:49:10', '2024-09-08 11:49:11', 0);
INSERT INTO `file_nodes` VALUES (264, 262, '2', 'file', '/dongman/青春纪行/2.mp4', 315692, '/dongman/青春纪行/2.mp4', 'mp4', '2024-09-08 11:49:30', '2024-09-08 11:49:36', 0);
INSERT INTO `file_nodes` VALUES (265, 262, '3', 'file', '/dongman/青春纪行/3.mp4', 304860, '/dongman/青春纪行/3.mp4', 'mp4', '2024-09-08 11:49:34', '2024-09-08 11:49:39', 0);
INSERT INTO `file_nodes` VALUES (266, 262, '4', 'file', '/dongman/青春纪行/4.mp4', 295592, '/dongman/青春纪行/4.mp4', 'mp4', '2024-09-08 11:49:36', '2024-09-08 11:49:40', 0);
INSERT INTO `file_nodes` VALUES (267, 262, '5', 'file', '/dongman/青春纪行/5.mp4', 292733, '/dongman/青春纪行/5.mp4', 'mp4', '2024-09-08 11:49:38', '2024-09-08 11:49:42', 0);
INSERT INTO `file_nodes` VALUES (268, 262, '6', 'file', '/dongman/青春纪行/6.mp4', 323391, '/dongman/青春纪行/6.mp4', 'mp4', '2024-09-08 11:49:51', '2024-09-08 11:49:54', 0);
INSERT INTO `file_nodes` VALUES (269, 262, '7', 'file', '/dongman/青春纪行/7.mp4', 284597, '/dongman/青春纪行/7.mp4', 'mp4', '2024-09-08 11:49:58', '2024-09-08 11:50:01', 0);
INSERT INTO `file_nodes` VALUES (270, 262, '8', 'file', '/dongman/青春纪行/8.mp4', 301746, '/dongman/青春纪行/8.mp4', 'mp4', '2024-09-08 11:50:03', '2024-09-08 11:50:06', 0);
INSERT INTO `file_nodes` VALUES (271, 262, '9', 'file', '/dongman/青春纪行/9.mp4', 258507, '/dongman/青春纪行/9.mp4', 'mp4', '2024-09-08 11:50:03', '2024-09-08 11:50:06', 0);
INSERT INTO `file_nodes` VALUES (272, 262, '10', 'file', '/dongman/青春纪行/10.mp4', 257373, '/dongman/青春纪行/10.mp4', 'mp4', '2024-09-08 11:50:18', '2024-09-08 11:50:19', 0);
INSERT INTO `file_nodes` VALUES (273, 262, '11', 'file', '/dongman/青春纪行/11.mp4', 292159, '/dongman/青春纪行/11.mp4', 'mp4', '2024-09-08 11:50:24', '2024-09-08 11:50:25', 0);
INSERT INTO `file_nodes` VALUES (274, 262, '12', 'file', '/dongman/青春纪行/12.mp4', 313354, '/dongman/青春纪行/12.mp4', 'mp4', '2024-09-08 11:50:34', '2024-09-08 11:50:37', 0);
INSERT INTO `file_nodes` VALUES (275, 262, '13', 'file', '/dongman/青春纪行/13.mp4', 346198, '/dongman/青春纪行/13.mp4', 'mp4', '2024-09-08 11:50:43', '2024-09-08 11:50:48', 0);
INSERT INTO `file_nodes` VALUES (276, 262, '14', 'file', '/dongman/青春纪行/14.mp4', 305974, '/dongman/青春纪行/14.mp4', 'mp4', '2024-09-08 11:50:47', '2024-09-08 11:50:52', 0);
INSERT INTO `file_nodes` VALUES (277, 262, '16', 'file', '/dongman/青春纪行/16.mp4', 233673, '/dongman/青春纪行/16.mp4', 'mp4', '2024-09-08 11:50:50', '2024-09-08 11:50:53', 0);
INSERT INTO `file_nodes` VALUES (278, 262, '15', 'file', '/dongman/青春纪行/15.mp4', 373301, '/dongman/青春纪行/15.mp4', 'mp4', '2024-09-08 11:50:56', '2024-09-08 11:51:01', 0);
INSERT INTO `file_nodes` VALUES (279, 262, '17', 'file', '/dongman/青春纪行/17.mp4', 258982, '/dongman/青春纪行/17.mp4', 'mp4', '2024-09-08 11:51:02', '2024-09-08 11:51:03', 0);
INSERT INTO `file_nodes` VALUES (280, 262, '19', 'file', '/dongman/青春纪行/19.mp4', 256308, '/dongman/青春纪行/19.mp4', 'mp4', '2024-09-08 11:51:11', '2024-09-08 11:51:13', 0);
INSERT INTO `file_nodes` VALUES (281, 262, '18', 'file', '/dongman/青春纪行/18.mp4', 340656, '/dongman/青春纪行/18.mp4', 'mp4', '2024-09-08 11:51:13', '2024-09-08 11:51:15', 0);
INSERT INTO `file_nodes` VALUES (282, 262, '20', 'file', '/dongman/青春纪行/20.mp4', 302253, '/dongman/青春纪行/20.mp4', 'mp4', '2024-09-08 11:51:23', '2024-09-08 11:51:27', 0);
INSERT INTO `file_nodes` VALUES (283, 262, '21', 'file', '/dongman/青春纪行/21.mp4', 286253, '/dongman/青春纪行/21.mp4', 'mp4', '2024-09-08 11:51:29', '2024-09-08 11:51:33', 0);
INSERT INTO `file_nodes` VALUES (284, 262, '22', 'file', '/dongman/青春纪行/22.mp4', 278446, '/dongman/青春纪行/22.mp4', 'mp4', '2024-09-08 11:51:33', '2024-09-08 11:51:38', 0);
INSERT INTO `file_nodes` VALUES (285, 262, '23', 'file', '/dongman/青春纪行/23.mp4', 294457, '/dongman/青春纪行/23.mp4', 'mp4', '2024-09-08 11:51:40', '2024-09-08 11:51:42', 0);
INSERT INTO `file_nodes` VALUES (286, 262, '24', 'file', '/dongman/青春纪行/24.mp4', 288655, '/dongman/青春纪行/24.mp4', 'mp4', '2024-09-08 11:51:43', '2024-09-08 11:51:44', 0);
INSERT INTO `file_nodes` VALUES (287, 1, '通往夏天的隧道，再见的出口', 'folder', '/dongman/通往夏天的隧道，再见的出口', 0, NULL, NULL, '2024-09-08 11:56:58', '2024-09-08 11:56:58', 0);
INSERT INTO `file_nodes` VALUES (288, 287, '1通往夏天的隧道', 'file', '/dongman/通往夏天的隧道，再见的出口/1通往夏天的隧道.mp4', 1479049, '/dongman/通往夏天的隧道，再见的出口/1通往夏天的隧道.mp4', 'mp4', '2024-09-08 11:59:23', '2024-09-08 11:59:34', 0);
INSERT INTO `file_nodes` VALUES (289, 1, '紧扣的星星', 'folder', '/dongman/紧扣的星星', 0, NULL, NULL, '2024-09-08 16:26:25', '2024-09-08 16:26:25', 0);
INSERT INTO `file_nodes` VALUES (290, 289, '1', 'file', '/dongman/紧扣的星星/1.mp4', 185025, '/dongman/紧扣的星星/1.mp4', 'mp4', '2024-09-08 16:27:26', '2024-09-08 16:27:26', 0);
INSERT INTO `file_nodes` VALUES (291, 289, '2', 'file', '/dongman/紧扣的星星/2.mp4', 201576, '/dongman/紧扣的星星/2.mp4', 'mp4', '2024-09-08 16:27:47', '2024-09-08 16:27:47', 0);
INSERT INTO `file_nodes` VALUES (292, 1, '惊爆草莓', 'folder', '/dongman/惊爆草莓', 0, NULL, NULL, '2024-09-08 16:28:58', '2024-09-08 16:28:58', 0);
INSERT INTO `file_nodes` VALUES (293, 292, '1', 'file', '/dongman/惊爆草莓/1.mp4', 194661, '/dongman/惊爆草莓/1.mp4', 'mp4', '2024-09-08 16:31:19', '2024-09-08 16:31:19', 0);
INSERT INTO `file_nodes` VALUES (294, 292, '2', 'file', '/dongman/惊爆草莓/2.mp4', 163852, '/dongman/惊爆草莓/2.mp4', 'mp4', '2024-09-08 16:31:37', '2024-09-08 16:31:37', 0);
INSERT INTO `file_nodes` VALUES (295, 292, '3', 'file', '/dongman/惊爆草莓/3.mp4', 179985, '/dongman/惊爆草莓/3.mp4', 'mp4', '2024-09-08 16:31:51', '2024-09-08 16:31:52', 0);
INSERT INTO `file_nodes` VALUES (296, 292, '4', 'file', '/dongman/惊爆草莓/4.mp4', 196812, '/dongman/惊爆草莓/4.mp4', 'mp4', '2024-09-08 16:32:07', '2024-09-08 16:32:08', 0);
INSERT INTO `file_nodes` VALUES (297, 292, '5', 'file', '/dongman/惊爆草莓/5.mp4', 163801, '/dongman/惊爆草莓/5.mp4', 'mp4', '2024-09-08 16:32:19', '2024-09-08 16:32:19', 0);
INSERT INTO `file_nodes` VALUES (298, 292, '6', 'file', '/dongman/惊爆草莓/6.mp4', 177159, '/dongman/惊爆草莓/6.mp4', 'mp4', '2024-09-08 16:32:35', '2024-09-08 16:32:35', 0);
INSERT INTO `file_nodes` VALUES (299, 292, '7', 'file', '/dongman/惊爆草莓/7.mp4', 199609, '/dongman/惊爆草莓/7.mp4', 'mp4', '2024-09-08 16:32:51', '2024-09-08 16:32:51', 0);
INSERT INTO `file_nodes` VALUES (300, 292, '8', 'file', '/dongman/惊爆草莓/8.mp4', 206568, '/dongman/惊爆草莓/8.mp4', 'mp4', '2024-09-08 16:33:05', '2024-09-08 16:33:05', 0);
INSERT INTO `file_nodes` VALUES (301, 292, '9', 'file', '/dongman/惊爆草莓/9.mp4', 150277, '/dongman/惊爆草莓/9.mp4', 'mp4', '2024-09-08 16:33:17', '2024-09-08 16:33:17', 0);
INSERT INTO `file_nodes` VALUES (302, 292, '10', 'file', '/dongman/惊爆草莓/10.mp4', 183356, '/dongman/惊爆草莓/10.mp4', 'mp4', '2024-09-08 16:33:31', '2024-09-08 16:33:32', 0);
INSERT INTO `file_nodes` VALUES (303, 292, '11', 'file', '/dongman/惊爆草莓/11.mp4', 169212, '/dongman/惊爆草莓/11.mp4', 'mp4', '2024-09-08 16:33:48', '2024-09-08 16:33:49', 0);
INSERT INTO `file_nodes` VALUES (304, 292, '12', 'file', '/dongman/惊爆草莓/12.mp4', 171410, '/dongman/惊爆草莓/12.mp4', 'mp4', '2024-09-08 16:34:00', '2024-09-08 16:34:00', 0);
INSERT INTO `file_nodes` VALUES (305, 292, '13', 'file', '/dongman/惊爆草莓/13.mp4', 176298, '/dongman/惊爆草莓/13.mp4', 'mp4', '2024-09-08 16:34:12', '2024-09-08 16:34:12', 0);
INSERT INTO `file_nodes` VALUES (306, 292, '14', 'file', '/dongman/惊爆草莓/14.mp4', 168368, '/dongman/惊爆草莓/14.mp4', 'mp4', '2024-09-08 16:34:24', '2024-09-08 16:34:25', 0);
INSERT INTO `file_nodes` VALUES (307, 292, '15', 'file', '/dongman/惊爆草莓/15.mp4', 161747, '/dongman/惊爆草莓/15.mp4', 'mp4', '2024-09-08 16:34:38', '2024-09-08 16:34:38', 0);
INSERT INTO `file_nodes` VALUES (308, 292, '16', 'file', '/dongman/惊爆草莓/16.mp4', 177109, '/dongman/惊爆草莓/16.mp4', 'mp4', '2024-09-08 16:34:50', '2024-09-08 16:34:50', 0);
INSERT INTO `file_nodes` VALUES (309, 292, '17', 'file', '/dongman/惊爆草莓/17.mp4', 176027, '/dongman/惊爆草莓/17.mp4', 'mp4', '2024-09-08 16:35:02', '2024-09-08 16:35:02', 0);
INSERT INTO `file_nodes` VALUES (310, 292, '18', 'file', '/dongman/惊爆草莓/18.mp4', 261705, '/dongman/惊爆草莓/18.mp4', 'mp4', '2024-09-08 16:35:21', '2024-09-08 16:35:21', 0);
INSERT INTO `file_nodes` VALUES (311, 292, '19', 'file', '/dongman/惊爆草莓/19.mp4', 172511, '/dongman/惊爆草莓/19.mp4', 'mp4', '2024-09-08 16:35:34', '2024-09-08 16:35:34', 0);
INSERT INTO `file_nodes` VALUES (312, 292, '20', 'file', '/dongman/惊爆草莓/20.mp4', 179714, '/dongman/惊爆草莓/20.mp4', 'mp4', '2024-09-08 16:35:49', '2024-09-08 16:35:50', 0);
INSERT INTO `file_nodes` VALUES (313, 292, '21', 'file', '/dongman/惊爆草莓/21.mp4', 182186, '/dongman/惊爆草莓/21.mp4', 'mp4', '2024-09-08 16:36:09', '2024-09-08 16:36:10', 0);
INSERT INTO `file_nodes` VALUES (314, 292, '22', 'file', '/dongman/惊爆草莓/22.mp4', 201888, '/dongman/惊爆草莓/22.mp4', 'mp4', '2024-09-08 16:36:29', '2024-09-08 16:36:30', 0);
INSERT INTO `file_nodes` VALUES (315, 292, '23', 'file', '/dongman/惊爆草莓/23.mp4', 172125, '/dongman/惊爆草莓/23.mp4', 'mp4', '2024-09-08 16:36:46', '2024-09-08 16:36:47', 0);
INSERT INTO `file_nodes` VALUES (316, 292, '24', 'file', '/dongman/惊爆草莓/24.mp4', 187236, '/dongman/惊爆草莓/24.mp4', 'mp4', '2024-09-08 16:37:01', '2024-09-08 16:37:02', 0);
INSERT INTO `file_nodes` VALUES (317, 292, '25', 'file', '/dongman/惊爆草莓/25.mp4', 164723, '/dongman/惊爆草莓/25.mp4', 'mp4', '2024-09-08 16:37:18', '2024-09-08 16:37:19', 0);
INSERT INTO `file_nodes` VALUES (318, 292, '26', 'file', '/dongman/惊爆草莓/26.mp4', 202144, '/dongman/惊爆草莓/26.mp4', 'mp4', '2024-09-08 16:37:36', '2024-09-08 16:37:36', 0);
INSERT INTO `file_nodes` VALUES (319, 1, '花吻在上', 'folder', '/dongman/花吻在上', 0, NULL, NULL, '2024-09-08 17:11:16', '2024-09-08 17:11:16', 0);
INSERT INTO `file_nodes` VALUES (320, 319, 'OAV', 'file', '/dongman/花吻在上/OAV.mp4', 248961, '/dongman/花吻在上/OAV.mp4', 'mp4', '2024-09-08 17:12:18', '2024-09-08 17:12:18', 0);
INSERT INTO `file_nodes` VALUES (321, 1, '樱Trick', 'folder', '/dongman/樱Trick', 0, NULL, NULL, '2024-09-08 17:13:13', '2024-09-08 17:13:13', 0);
INSERT INTO `file_nodes` VALUES (322, 321, '1', 'file', '/dongman/樱Trick/1.mp4', 153181, '/dongman/樱Trick/1.mp4', 'mp4', '2024-09-08 17:14:04', '2024-09-08 17:14:04', 0);
INSERT INTO `file_nodes` VALUES (323, 321, '2', 'file', '/dongman/樱Trick/2.mp4', 153169, '/dongman/樱Trick/2.mp4', 'mp4', '2024-09-08 17:14:12', '2024-09-08 17:14:12', 0);
INSERT INTO `file_nodes` VALUES (324, 321, '3', 'file', '/dongman/樱Trick/3.mp4', 153272, '/dongman/樱Trick/3.mp4', 'mp4', '2024-09-08 17:14:15', '2024-09-08 17:14:15', 0);
INSERT INTO `file_nodes` VALUES (325, 321, '4', 'file', '/dongman/樱Trick/4.mp4', 153245, '/dongman/樱Trick/4.mp4', 'mp4', '2024-09-08 17:14:22', '2024-09-08 17:14:22', 0);
INSERT INTO `file_nodes` VALUES (326, 321, '5', 'file', '/dongman/樱Trick/5.mp4', 153206, '/dongman/樱Trick/5.mp4', 'mp4', '2024-09-08 17:14:25', '2024-09-08 17:14:25', 0);
INSERT INTO `file_nodes` VALUES (327, 321, '6', 'file', '/dongman/樱Trick/6.mp4', 153142, '/dongman/樱Trick/6.mp4', 'mp4', '2024-09-08 17:14:29', '2024-09-08 17:14:29', 0);
INSERT INTO `file_nodes` VALUES (328, 321, '7', 'file', '/dongman/樱Trick/7.mp4', 153255, '/dongman/樱Trick/7.mp4', 'mp4', '2024-09-08 17:14:31', '2024-09-08 17:14:32', 0);
INSERT INTO `file_nodes` VALUES (329, 321, '8', 'file', '/dongman/樱Trick/8.mp4', 153192, '/dongman/樱Trick/8.mp4', 'mp4', '2024-09-08 17:14:35', '2024-09-08 17:14:35', 0);
INSERT INTO `file_nodes` VALUES (330, 321, '9', 'file', '/dongman/樱Trick/9.mp4', 153213, '/dongman/樱Trick/9.mp4', 'mp4', '2024-09-08 17:14:42', '2024-09-08 17:14:43', 0);
INSERT INTO `file_nodes` VALUES (331, 321, '10', 'file', '/dongman/樱Trick/10.mp4', 153113, '/dongman/樱Trick/10.mp4', 'mp4', '2024-09-08 17:14:47', '2024-09-08 17:14:47', 0);
INSERT INTO `file_nodes` VALUES (332, 321, '11', 'file', '/dongman/樱Trick/11.mp4', 153117, '/dongman/樱Trick/11.mp4', 'mp4', '2024-09-08 17:14:52', '2024-09-08 17:14:52', 0);
INSERT INTO `file_nodes` VALUES (333, 321, '12', 'file', '/dongman/樱Trick/12.mp4', 150174, '/dongman/樱Trick/12.mp4', 'mp4', '2024-09-08 17:14:55', '2024-09-08 17:14:56', 0);
INSERT INTO `file_nodes` VALUES (334, 1, '恶魔之迷', 'folder', '/dongman/恶魔之迷', 0, NULL, NULL, '2024-09-10 13:28:11', '2024-09-10 13:28:11', 0);
INSERT INTO `file_nodes` VALUES (335, 334, '1', 'file', '/dongman/恶魔之迷/1.mp4', 131664, '/dongman/恶魔之迷/1.mp4', 'mp4', '2024-09-10 13:29:20', '2024-09-10 13:29:20', 0);
INSERT INTO `file_nodes` VALUES (336, 334, '2', 'file', '/dongman/恶魔之迷/2.mp4', 134657, '/dongman/恶魔之迷/2.mp4', 'mp4', '2024-09-10 13:31:10', '2024-09-10 13:31:11', 0);
INSERT INTO `file_nodes` VALUES (337, 334, '3', 'file', '/dongman/恶魔之迷/3.mp4', 156943, '/dongman/恶魔之迷/3.mp4', 'mp4', '2024-09-10 13:31:27', '2024-09-10 13:31:27', 0);
INSERT INTO `file_nodes` VALUES (338, 334, '4', 'file', '/dongman/恶魔之迷/4.mp4', 137672, '/dongman/恶魔之迷/4.mp4', 'mp4', '2024-09-10 13:31:40', '2024-09-10 13:31:40', 0);
INSERT INTO `file_nodes` VALUES (339, 334, '5', 'file', '/dongman/恶魔之迷/5.mp4', 130383, '/dongman/恶魔之迷/5.mp4', 'mp4', '2024-09-10 13:32:00', '2024-09-10 13:32:00', 0);
INSERT INTO `file_nodes` VALUES (340, 334, '6', 'file', '/dongman/恶魔之迷/6.mp4', 125200, '/dongman/恶魔之迷/6.mp4', 'mp4', '2024-09-10 13:32:10', '2024-09-10 13:32:10', 0);
INSERT INTO `file_nodes` VALUES (341, 334, '7', 'file', '/dongman/恶魔之迷/7.mp4', 141906, '/dongman/恶魔之迷/7.mp4', 'mp4', '2024-09-10 13:32:21', '2024-09-10 13:32:21', 0);
INSERT INTO `file_nodes` VALUES (342, 334, '8', 'file', '/dongman/恶魔之迷/8.mp4', 134800, '/dongman/恶魔之迷/8.mp4', 'mp4', '2024-09-10 13:33:16', '2024-09-10 13:33:16', 0);
INSERT INTO `file_nodes` VALUES (343, 334, '9', 'file', '/dongman/恶魔之迷/9.mp4', 158373, '/dongman/恶魔之迷/9.mp4', 'mp4', '2024-09-10 17:40:04', '2024-09-10 17:40:04', 0);
INSERT INTO `file_nodes` VALUES (344, 334, '10', 'file', '/dongman/恶魔之迷/10.mp4', 154801, '/dongman/恶魔之迷/10.mp4', 'mp4', '2024-09-10 17:40:15', '2024-09-10 17:40:15', 0);
INSERT INTO `file_nodes` VALUES (345, 334, '11', 'file', '/dongman/恶魔之迷/11.mp4', 128278, '/dongman/恶魔之迷/11.mp4', 'mp4', '2024-09-10 17:40:25', '2024-09-10 17:40:25', 0);
INSERT INTO `file_nodes` VALUES (346, 334, '12', 'file', '/dongman/恶魔之迷/12.mp4', 137860, '/dongman/恶魔之迷/12.mp4', 'mp4', '2024-09-10 17:40:35', '2024-09-10 17:40:35', 0);
INSERT INTO `file_nodes` VALUES (347, 334, '13', 'file', '/dongman/恶魔之迷/13.mp4', 231892, '/dongman/恶魔之迷/13.mp4', 'mp4', '2024-09-10 17:40:50', '2024-09-10 17:40:51', 0);
INSERT INTO `file_nodes` VALUES (348, 1, '圣洁天使', 'folder', '/dongman/圣洁天使', 0, NULL, NULL, '2024-09-10 23:16:19', '2024-09-10 23:16:19', 0);
INSERT INTO `file_nodes` VALUES (349, 348, 'DL', 'file', '/dongman/圣洁天使/DL.mp4', 337100, '/dongman/圣洁天使/DL.mp4', 'mp4', '2024-09-10 23:17:35', '2024-09-10 23:17:37', 0);
INSERT INTO `file_nodes` VALUES (350, 348, 'DL(1)', 'file', '/dongman/圣洁天使/DL(1).mp4', 337295, '/dongman/圣洁天使/DL(1).mp4', 'mp4', '2024-09-10 23:43:38', '2024-09-10 23:43:38', 0);
INSERT INTO `file_nodes` VALUES (351, 348, 'DL(2)', 'file', '/dongman/圣洁天使/DL(2).mp4', 337260, '/dongman/圣洁天使/DL(2).mp4', 'mp4', '2024-09-10 23:59:01', '2024-09-10 23:59:03', 0);
INSERT INTO `file_nodes` VALUES (352, 348, 'DL(3)', 'file', '/dongman/圣洁天使/DL(3).mp4', 337514, '/dongman/圣洁天使/DL(3).mp4', 'mp4', '2024-09-10 23:59:46', '2024-09-10 23:59:47', 0);
INSERT INTO `file_nodes` VALUES (353, 348, 'DL(4)', 'file', '/dongman/圣洁天使/DL(4).mp4', 337240, '/dongman/圣洁天使/DL(4).mp4', 'mp4', '2024-09-11 00:05:03', '2024-09-11 00:05:04', 0);
INSERT INTO `file_nodes` VALUES (354, 348, 'DL(5)', 'file', '/dongman/圣洁天使/DL(5).mp4', 337385, '/dongman/圣洁天使/DL(5).mp4', 'mp4', '2024-09-11 00:05:25', '2024-09-11 00:05:26', 0);
INSERT INTO `file_nodes` VALUES (355, 348, 'DL(6)', 'file', '/dongman/圣洁天使/DL(6).mp4', 337461, '/dongman/圣洁天使/DL(6).mp4', 'mp4', '2024-09-11 00:05:28', '2024-09-11 00:05:29', 0);
INSERT INTO `file_nodes` VALUES (356, 348, 'DL(7)', 'file', '/dongman/圣洁天使/DL(7).mp4', 337468, '/dongman/圣洁天使/DL(7).mp4', 'mp4', '2024-09-11 00:05:31', '2024-09-11 00:05:32', 0);
INSERT INTO `file_nodes` VALUES (357, 348, 'DL(8)', 'file', '/dongman/圣洁天使/DL(8).mp4', 337382, '/dongman/圣洁天使/DL(8).mp4', 'mp4', '2024-09-11 00:05:37', '2024-09-11 00:05:39', 0);
INSERT INTO `file_nodes` VALUES (358, 348, 'DL(9)', 'file', '/dongman/圣洁天使/DL(9).mp4', 337293, '/dongman/圣洁天使/DL(9).mp4', 'mp4', '2024-09-11 00:05:51', '2024-09-11 00:05:52', 0);
INSERT INTO `file_nodes` VALUES (359, 348, 'DL(10)', 'file', '/dongman/圣洁天使/DL(10).mp4', 337519, '/dongman/圣洁天使/DL(10).mp4', 'mp4', '2024-09-11 00:05:56', '2024-09-11 00:05:56', 0);
INSERT INTO `file_nodes` VALUES (360, 348, 'DL(11)', 'file', '/dongman/圣洁天使/DL(11).mp4', 337263, '/dongman/圣洁天使/DL(11).mp4', 'mp4', '2024-09-11 00:05:59', '2024-09-11 00:06:00', 0);
INSERT INTO `file_nodes` VALUES (361, 1, '红壳的潘多拉', 'folder', '/dongman/红壳的潘多拉', 0, NULL, NULL, '2024-09-11 22:41:25', '2024-09-11 22:41:25', 0);
INSERT INTO `file_nodes` VALUES (362, 361, 'DL', 'file', '/dongman/红壳的潘多拉/DL.mp4', 138329, '/dongman/红壳的潘多拉/DL.mp4', 'mp4', '2024-09-11 22:42:39', '2024-09-11 22:42:39', 0);

-- ----------------------------
-- Table structure for hua_anime_type
-- ----------------------------
DROP TABLE IF EXISTS `hua_anime_type`;
CREATE TABLE `hua_anime_type`  (
  `anime_id` int UNSIGNED NOT NULL COMMENT '动漫id',
  `type_id` int NOT NULL COMMENT '类型id',
  PRIMARY KEY (`anime_id`, `type_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '动漫-类型关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hua_anime_type
-- ----------------------------
INSERT INTO `hua_anime_type` VALUES (2, 1);
INSERT INTO `hua_anime_type` VALUES (2, 2);
INSERT INTO `hua_anime_type` VALUES (3, 1);
INSERT INTO `hua_anime_type` VALUES (3, 12);
INSERT INTO `hua_anime_type` VALUES (4, 3);
INSERT INTO `hua_anime_type` VALUES (4, 4);
INSERT INTO `hua_anime_type` VALUES (4, 5);
INSERT INTO `hua_anime_type` VALUES (4, 7);
INSERT INTO `hua_anime_type` VALUES (5, 3);
INSERT INTO `hua_anime_type` VALUES (5, 4);
INSERT INTO `hua_anime_type` VALUES (5, 5);
INSERT INTO `hua_anime_type` VALUES (5, 6);
INSERT INTO `hua_anime_type` VALUES (5, 7);
INSERT INTO `hua_anime_type` VALUES (6, 2);
INSERT INTO `hua_anime_type` VALUES (6, 6);
INSERT INTO `hua_anime_type` VALUES (6, 7);
INSERT INTO `hua_anime_type` VALUES (7, 4);
INSERT INTO `hua_anime_type` VALUES (8, 7);
INSERT INTO `hua_anime_type` VALUES (8, 8);
INSERT INTO `hua_anime_type` VALUES (8, 9);
INSERT INTO `hua_anime_type` VALUES (8, 13);
INSERT INTO `hua_anime_type` VALUES (8, 40);
INSERT INTO `hua_anime_type` VALUES (8, 45);
INSERT INTO `hua_anime_type` VALUES (9, 10);
INSERT INTO `hua_anime_type` VALUES (9, 11);
INSERT INTO `hua_anime_type` VALUES (10, 12);
INSERT INTO `hua_anime_type` VALUES (10, 13);
INSERT INTO `hua_anime_type` VALUES (10, 44);
INSERT INTO `hua_anime_type` VALUES (11, 5);
INSERT INTO `hua_anime_type` VALUES (11, 7);
INSERT INTO `hua_anime_type` VALUES (11, 40);
INSERT INTO `hua_anime_type` VALUES (12, 7);
INSERT INTO `hua_anime_type` VALUES (12, 11);
INSERT INTO `hua_anime_type` VALUES (12, 13);
INSERT INTO `hua_anime_type` VALUES (13, 4);
INSERT INTO `hua_anime_type` VALUES (13, 5);
INSERT INTO `hua_anime_type` VALUES (13, 7);
INSERT INTO `hua_anime_type` VALUES (14, 7);
INSERT INTO `hua_anime_type` VALUES (14, 13);
INSERT INTO `hua_anime_type` VALUES (14, 40);
INSERT INTO `hua_anime_type` VALUES (15, 7);
INSERT INTO `hua_anime_type` VALUES (15, 16);
INSERT INTO `hua_anime_type` VALUES (16, 7);
INSERT INTO `hua_anime_type` VALUES (16, 8);
INSERT INTO `hua_anime_type` VALUES (16, 17);
INSERT INTO `hua_anime_type` VALUES (16, 18);
INSERT INTO `hua_anime_type` VALUES (17, 7);
INSERT INTO `hua_anime_type` VALUES (17, 16);
INSERT INTO `hua_anime_type` VALUES (18, 7);
INSERT INTO `hua_anime_type` VALUES (18, 16);
INSERT INTO `hua_anime_type` VALUES (19, 7);
INSERT INTO `hua_anime_type` VALUES (19, 11);
INSERT INTO `hua_anime_type` VALUES (19, 16);
INSERT INTO `hua_anime_type` VALUES (20, 7);
INSERT INTO `hua_anime_type` VALUES (20, 15);
INSERT INTO `hua_anime_type` VALUES (20, 19);
INSERT INTO `hua_anime_type` VALUES (20, 20);
INSERT INTO `hua_anime_type` VALUES (20, 45);
INSERT INTO `hua_anime_type` VALUES (21, 7);
INSERT INTO `hua_anime_type` VALUES (21, 19);
INSERT INTO `hua_anime_type` VALUES (21, 45);
INSERT INTO `hua_anime_type` VALUES (22, 7);
INSERT INTO `hua_anime_type` VALUES (22, 8);
INSERT INTO `hua_anime_type` VALUES (22, 13);
INSERT INTO `hua_anime_type` VALUES (23, 15);
INSERT INTO `hua_anime_type` VALUES (23, 20);
INSERT INTO `hua_anime_type` VALUES (23, 45);
INSERT INTO `hua_anime_type` VALUES (24, 11);
INSERT INTO `hua_anime_type` VALUES (24, 15);
INSERT INTO `hua_anime_type` VALUES (24, 40);
INSERT INTO `hua_anime_type` VALUES (25, 7);
INSERT INTO `hua_anime_type` VALUES (25, 20);
INSERT INTO `hua_anime_type` VALUES (25, 45);
INSERT INTO `hua_anime_type` VALUES (26, 7);
INSERT INTO `hua_anime_type` VALUES (26, 20);
INSERT INTO `hua_anime_type` VALUES (27, 7);
INSERT INTO `hua_anime_type` VALUES (28, 7);
INSERT INTO `hua_anime_type` VALUES (29, 7);
INSERT INTO `hua_anime_type` VALUES (29, 20);
INSERT INTO `hua_anime_type` VALUES (30, 7);
INSERT INTO `hua_anime_type` VALUES (30, 16);
INSERT INTO `hua_anime_type` VALUES (31, 5);
INSERT INTO `hua_anime_type` VALUES (31, 6);
INSERT INTO `hua_anime_type` VALUES (31, 7);

-- ----------------------------
-- Table structure for hua_type
-- ----------------------------
DROP TABLE IF EXISTS `hua_type`;
CREATE TABLE `hua_type`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '类型id',
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `hua_type_pk`(`type` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '动漫类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hua_type
-- ----------------------------
INSERT INTO `hua_type` VALUES (42, '乙女');
INSERT INTO `hua_type` VALUES (40, '催泪');
INSERT INTO `hua_type` VALUES (17, '冒险');
INSERT INTO `hua_type` VALUES (30, '动作');
INSERT INTO `hua_type` VALUES (36, '励志');
INSERT INTO `hua_type` VALUES (16, '卡牌');
INSERT INTO `hua_type` VALUES (9, '历史');
INSERT INTO `hua_type` VALUES (4, '原创');
INSERT INTO `hua_type` VALUES (31, '喜剧');
INSERT INTO `hua_type` VALUES (13, '奇幻');
INSERT INTO `hua_type` VALUES (45, '少女');
INSERT INTO `hua_type` VALUES (10, '异世界');
INSERT INTO `hua_type` VALUES (2, '快乐');
INSERT INTO `hua_type` VALUES (15, '恋爱');
INSERT INTO `hua_type` VALUES (37, '恐怖');
INSERT INTO `hua_type` VALUES (18, '悬疑');
INSERT INTO `hua_type` VALUES (8, '战争');
INSERT INTO `hua_type` VALUES (5, '战斗');
INSERT INTO `hua_type` VALUES (43, '推理');
INSERT INTO `hua_type` VALUES (47, '搞笑');
INSERT INTO `hua_type` VALUES (19, '日常');
INSERT INTO `hua_type` VALUES (39, '智斗');
INSERT INTO `hua_type` VALUES (3, '机甲');
INSERT INTO `hua_type` VALUES (46, '架空');
INSERT INTO `hua_type` VALUES (20, '校园');
INSERT INTO `hua_type` VALUES (34, '治愈');
INSERT INTO `hua_type` VALUES (48, '游戏改');
INSERT INTO `hua_type` VALUES (12, '漫画改');
INSERT INTO `hua_type` VALUES (44, '热血');
INSERT INTO `hua_type` VALUES (7, '百合');
INSERT INTO `hua_type` VALUES (6, '科幻');
INSERT INTO `hua_type` VALUES (1, '童年');
INSERT INTO `hua_type` VALUES (41, '美食');
INSERT INTO `hua_type` VALUES (33, '超能力');
INSERT INTO `hua_type` VALUES (11, '轻小说改');
INSERT INTO `hua_type` VALUES (38, '音乐');
INSERT INTO `hua_type` VALUES (32, '魔法少女');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `user_account` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '账号',
  `user_avatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `profile` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个人简介',
  `gender` tinyint NULL DEFAULT 2 COMMENT '性别',
  `user_password` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `phone` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `user_status` tinyint NOT NULL DEFAULT 0 COMMENT '用户状态 0-正常 1-异常',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除  0-未删除 1-删除',
  `user_role` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'user' COMMENT '用户角色 user-用户 ban-被禁用 admin-管理员 super-超级管理员 .....',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '花白', 'huabai', NULL, '11', 2, '3cf82e9573b021336ff68b4dd88a3b19', NULL, '3026278076@qq.com', 0, '2024-08-28 16:31:21', '2024-08-29 16:02:25', 0, 'admin');
INSERT INTO `user` VALUES (2, '海棠', 'haitang', 'http://8.137.78.53/touxiang/1007/1007.jpg', NULL, 0, '3cf82e9573b021336ff68b4dd88a3b19', NULL, NULL, 0, '2024-09-01 22:57:22', '2024-09-01 22:57:22', 0, 'user');

-- ----------------------------
-- Table structure for user_rating
-- ----------------------------
DROP TABLE IF EXISTS `user_rating`;
CREATE TABLE `user_rating`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评分表id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `anime_id` bigint NULL DEFAULT NULL COMMENT '动漫id',
  `rating_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '开始/更新 评分时间',
  `score` decimal(3, 1) NULL DEFAULT NULL COMMENT '评分 1-10',
  `old_score` decimal(3, 1) NULL DEFAULT NULL COMMENT '旧的评分 默认为null',
  `comment` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论 - 不超过100字',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户评分表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_rating
-- ----------------------------

-- ----------------------------
-- Table structure for video_record
-- ----------------------------
DROP TABLE IF EXISTS `video_record`;
CREATE TABLE `video_record`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `anime_id` int UNSIGNED NOT NULL COMMENT '动漫id',
  `video_id` int UNSIGNED NOT NULL COMMENT '视频id',
  `user_Id` bigint NOT NULL COMMENT '用户id',
  `seek` double NULL DEFAULT NULL COMMENT '进度 （秒)',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video_record
-- ----------------------------
INSERT INTO `video_record` VALUES (18, 15, 133, 1, 1358.597087, '2024-09-07 11:46:17');
INSERT INTO `video_record` VALUES (19, 9, 160, 1, 1442.644677, '2024-09-10 18:32:32');
INSERT INTO `video_record` VALUES (20, 11, 82, 1, 3.829769, '2024-09-07 00:43:58');
INSERT INTO `video_record` VALUES (21, 5, 16, 1, 1.00166, '2024-09-11 21:50:31');
INSERT INTO `video_record` VALUES (23, 16, 155, 1, 1370.764401, '2024-09-12 01:24:16');
INSERT INTO `video_record` VALUES (24, 14, 121, 1, 1354.561218, '2024-09-07 18:46:56');
INSERT INTO `video_record` VALUES (25, 17, 164, 1, 601.269617, '2024-09-08 00:49:10');
INSERT INTO `video_record` VALUES (26, 18, 185, 1, 1.805971, '2024-09-07 18:42:30');
INSERT INTO `video_record` VALUES (27, 19, 197, 1, 1.801845, '2024-09-07 18:44:08');
INSERT INTO `video_record` VALUES (28, 8, 77, 1, 1241.234494, '2024-09-07 18:47:31');
INSERT INTO `video_record` VALUES (29, 13, 104, 1, 464.886502, '2024-09-07 18:48:09');
INSERT INTO `video_record` VALUES (30, 20, 209, 1, 1401.837667, '2024-09-08 15:58:25');
INSERT INTO `video_record` VALUES (31, 21, 228, 1, 1337.948877, '2024-09-10 12:17:07');
INSERT INTO `video_record` VALUES (32, 22, 221, 1, 0.733069, '2024-09-07 20:46:46');
INSERT INTO `video_record` VALUES (33, 23, 244, 1, 936.938186, '2024-09-10 18:33:33');
INSERT INTO `video_record` VALUES (34, 24, 259, 1, 5.793633, '2024-09-10 18:33:12');
INSERT INTO `video_record` VALUES (35, 26, 287, 1, 1435.559375, '2024-09-10 01:05:55');
INSERT INTO `video_record` VALUES (36, 25, 261, 1, 1401.501383, '2024-09-08 17:57:05');
INSERT INTO `video_record` VALUES (37, 28, 300, 1, 1359.112911, '2024-09-08 22:28:30');
INSERT INTO `video_record` VALUES (38, 29, 313, 1, 0, '2024-09-10 17:41:27');
INSERT INTO `video_record` VALUES (39, 10, 79, 1, 3.64936, '2024-09-10 18:32:13');
INSERT INTO `video_record` VALUES (40, 30, 315, 1, 5.544042, '2024-09-11 22:39:36');
INSERT INTO `video_record` VALUES (41, 12, 92, 1, 19.188922, '2024-09-11 19:17:23');

SET FOREIGN_KEY_CHECKS = 1;
