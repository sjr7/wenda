DROP TABLE IF EXISTS `question`;

-- 先选择数据库,数据库名字不一致需要更改
USE wenda;

-- 建立问题表
CREATE TABLE `question` (
  `id`            INT          NOT NULL AUTO_INCREMENT,
  `title`         VARCHAR(255) NOT NULL,
  `content`       TEXT         NULL,
  `user_id`       INT          NOT NULL,
  `created_date`  DATETIME     NOT NULL,
  `comment_count` INT          NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `date_index` (`created_date` ASC)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8 COMMENT '问题表';

-- 模拟数据
INSERT INTO question (id, title, content, user_id, created_date, comment_count)
VALUES (
  NULL, "如何看待知乎的行为", "无法看待", 10000, CURRENT_TIMESTAMP(), 55),
  (NULL, "如何看待知乎的行为1", '无法看待', 10000, CURRENT_TIMESTAMP, 55),
  (NULL, "如何看待知乎的行为2", '无法看待', 10000, CURRENT_TIMESTAMP, 55),
  (NULL, "如何看待知乎的行为3", '无法看待', 10000, CURRENT_TIMESTAMP, 55),
  (NULL, "如何看待知乎的行为4", '无法看待', 10000, CURRENT_TIMESTAMP, 55),
  (NULL, "如何看待知乎的行为5", '无法看待', 10000, CURRENT_TIMESTAMP, 55),
  (NULL, "如何看待知乎的行为6", '无法看待', 10000, CURRENT_TIMESTAMP, 55),
  (NULL, "如何看待知乎的行为7", '无法看待', 10000, CURRENT_TIMESTAMP, 55),
  (NULL, "如何看待知乎的行为8", '无法看待', 10000, CURRENT_TIMESTAMP, 55),
  (NULL, "如何看待知乎的行为9", '无法看待', 10000, CURRENT_TIMESTAMP, 55);
