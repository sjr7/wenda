-- 使用数据库
USE wenda;

DROP TABLE IF EXISTS `login_ticket`;

-- 创建数据库
CREATE TABLE `login_ticket`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL ,
  `ticket` VARCHAR(45) NOT NULL COMMENT '随机生成的时间',
  `expired` DATETIME NOT NULL COMMENT '过期时间',
  `status` INT NULL DEFAULT 0 COMMENT '状态',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `ticket_UNIQE` (`ticket` ASC )
)ENGINE =InnoDB DEFAULT CHARSET=utf8