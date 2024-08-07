# 1.创建数据表
CREATE TABLE `t_user_batch` (
                                `uid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
                                `account` varchar(30) NOT NULL DEFAULT '' COMMENT '用户账号(邮箱/手机号/昵称)',
                                `mobile` varchar(11) NOT NULL DEFAULT '' COMMENT '中国境内手机号',
                                `add_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '注册时间',
                                PRIMARY KEY (`uid`)
) ENGINE=InnoDB COMMENT='用户表';
CREATE INDEX idx_mob_time ON `t_user_batch` (`add_time`,`mobile`);

show VARIABLES like 'innodb_flush_log_at_trx_commit';
show VARIABLES like 'bulk_insert_buffer_size';
show VARIABLES like 'UNIQUE_CHECKS';
show VARIABLES like 'AUTOCOMMIT';


# 1.导入数据前设置优化配置
set global innodb_flush_log_at_trx_commit  = 0; # 调整刷盘方式
set bulk_insert_buffer_size = 16 * 1024 * 1024; # 调整为16M
SET UNIQUE_CHECKS= OFF;  # 关闭唯一性校验
SET  AUTOCOMMIT = OFF;   # 关闭自动提交


DROP PROCEDURE IF EXISTS `p_insert_t_user_batch`;

DELIMITER ;;
CREATE PROCEDURE `p_insert_t_user_batch`()
BEGIN
    DECLARE i INT DEFAULT 1;
    WHILE i<= 1000000
        DO
            # 插入语句
            INSERT INTO `t_user_batch` (`uid`, `account`, `mobile`, `add_time`)  VALUES (NULL, SUBSTRING(MD5(RAND()),1,10), SUBSTRING(RAND()*1000000000000,1,11), UNIX_TIMESTAMP());
            SET i=i+1;
        END WHILE ;
    COMMIT;
END;;
DELIMITER;

# 2.批量导入
call p_insert_t_user_batch(); # 批量导入存储过程内部已经设定了关闭自动提交，因此此处也可无需额外设定AUTOCOMMIT参数

commit;

# 3.导入数据后恢复默认配置
set global innodb_flush_log_at_trx_commit  = 1; # 默认刷盘方式为1
set bulk_insert_buffer_size = 8 * 1024 * 1024;  # 默认为8M
SET UNIQUE_CHECKS = ON;                         # 默认是开启唯一性校验
SET  AUTOCOMMIT = ON;                           # 默认是自动提交

UPDATE t_user_batch
SET add_time=1723041318
WHERE uid BETWEEN 1 AND 1000000;


SELECT * FROM t_user_batch WHERE `add_time`=1723041318;
SELECT `add_time`, mobile FROM t_user_batch WHERE `add_time`=1723041318;
# 50w条数据基本在5-10倍左右










