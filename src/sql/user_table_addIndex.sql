CREATE DATABASE IF NOT EXISTS `slow_query_test`;

USE `slow_query_test`;

-- 创建索引
CREATE INDEX idx_username ON `user` (`username`);
CREATE INDEX idx_email ON `user` (`email`);
CREATE INDEX idx_status ON `user` (`status`);
CREATE INDEX idx_role ON `user` (`role`);
