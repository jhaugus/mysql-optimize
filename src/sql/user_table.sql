CREATE DATABASE IF NOT EXISTS `slow_query_test`;

USE `slow_query_test`;

CREATE TABLE IF NOT EXISTS `user` (
                                      `id` INT AUTO_INCREMENT PRIMARY KEY,              -- 用户ID，自增主键
                                      `username` VARCHAR(50) NOT NULL UNIQUE,           -- 用户名，唯一且不为空
                                      `password` VARCHAR(255) NOT NULL,                 -- 密码，使用加密存储
                                      `email` VARCHAR(100) NOT NULL UNIQUE,             -- 电子邮件，唯一且不为空
                                      `first_name` VARCHAR(50) NOT NULL,                -- 名
                                      `last_name` VARCHAR(50) NOT NULL,                 -- 姓
                                      `birth_date` DATE,                                -- 出生日期
                                      `gender` ENUM('Male', 'Female', 'Other'),         -- 性别，枚举类型
                                      `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 创建时间，默认当前时间
                                      `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 更新时间，默认当前时间且每次更新
                                      `last_login` TIMESTAMP NULL DEFAULT NULL,         -- 最后登录时间
                                      `status` ENUM('Active', 'Inactive', 'Suspended') DEFAULT 'Active', -- 用户状态
                                      `role` ENUM('User', 'Admin', 'Moderator') DEFAULT 'User' -- 用户角色
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

