DROP TABLE IF EXISTS 'user';
-- 这里首先先选择数据库,如果数据库名不一样需要更改
USE wenda;

-- 创建用户表
CREATE TABLE `user`(
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` VARCHAR(64) NOT NULL UNIQUE COMMENT '用户名',
  `password` VARCHAR(128) NOT NULL COMMENT '密码',
  `salt` VARCHAR(50) NOT NULL COMMENT '盐值',
  `head_url` VARCHAR(255) NOT NULL  COMMENT '头像地址',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET =utf8 COMMENT '用户表';

-- 模拟用户数据
INSERT INTO user(id, name, password, salt, head_url) VALUES (
    NULL ,'admin','admin','wendasalt','https://www.baidu.com/img/bd_logo1.png',
    NULL ,'hktianya','abc','wendasalt1','https://www.baidu.com/img/bd_logo1.png',
    NULL ,'pause','abc','wendasalt2','https://www.baidu.com/img/bd_logo1.png',
    NULL ,'lfhack','abc','wendasalt3','https://www.baidu.com/img/bd_logo1.png',
    NULL ,'b952658176','abc','wendasalt4','https://www.baidu.com/img/bd_logo1.png',
    NULL ,'Server','abc','wendasalt5','https://www.baidu.com/img/bd_logo1.png',
    NULL ,'52yaowanzi','abc','wendasalt6','https://www.baidu.com/img/bd_logo1.png',
    NULL ,'china-fengbao','abc','wendasalt7','https://www.baidu.com/img/bd_logo1.png',
    NULL ,'david2013','abc','wendasalt8','https://www.baidu.com/img/bd_logo1.png'
);


