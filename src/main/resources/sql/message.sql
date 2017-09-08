USE wenda;

CREATE TABLE `message` (
  `id`              INT(11)     NOT NULL AUTO_INCREMENT,
  `from_id`         INT(11)     NOT NULL,
  `to_id`           INT(11)     NOT NULL,
  `content`         TEXT        NOT NULL,
  `has_read`        TINYINT(1)  NOT NULL DEFAULT 0
  COMMENT '0为没有阅读,1为已经阅读过了的',
  `conversation_id` VARCHAR(50) NOT NULL DEFAULT 0,
  `create_date`     DATETIME    NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  DEFAULT CHARSET = utf8;

-- 模拟数据
INSERT INTO message (from_id, to_id, content, conversation_id, create_date) VALUES (
  10000, 100001, "你在吗", "1", CURRENT_TIMESTAMP),
  (10001, 100000, "你在吗", "1", CURRENT_TIMESTAMP()),
  (10000, 100001, "我很好啊", "1", CURRENT_TIMESTAMP()),
  (10001, 100000, "我也很好", "1", CURRENT_TIMESTAMP()),
  (10000, 100003, "爱", "1", CURRENT_TIMESTAMP()),
  (10003, 100000, "我也是", "1", CURRENT_TIMESTAMP()),
  (10000, 100003, "太好了", "1", CURRENT_TIMESTAMP()),
  (10000, 100004, "海螺", "1", CURRENT_TIMESTAMP()),
  (10000, 100005, "借钱呀", "1", CURRENT_TIMESTAMP()),
  (10000, 100006, "交个朋友罗", "1", CURRENT_TIMESTAMP()
  );