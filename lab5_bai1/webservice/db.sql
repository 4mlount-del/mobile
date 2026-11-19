-- Lab 5 - Bai 1: Database cho chuc nang Dang nhap bang Retrofit
-- Chay file nay trong phpMyAdmin (http://localhost/phpmyadmin) de tao DB + bang Users

CREATE DATABASE IF NOT EXISTS lab5 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE lab5;

CREATE TABLE IF NOT EXISTS Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(32) NOT NULL COMMENT 'Mat khau ma hoa MD5 (32 ky tu hex)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tai khoan mau (password goc -> MD5):
-- minhtien / 1234  -> 81dc9bdb52d04dc20036dbd8313ed055
-- admin    / admin -> 21232f297a57a5a743894a0e4a801fc
INSERT INTO Users (username, password) VALUES
('minhtien', MD5('1234')),
('admin', MD5('admin'))
ON DUPLICATE KEY UPDATE password = VALUES(password);
