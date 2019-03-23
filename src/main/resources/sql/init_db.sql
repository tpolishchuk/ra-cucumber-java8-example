CREATE DATABASE IF NOT EXISTS `demo_app`;

DROP USER IF EXISTS 'demo'@'localhost';
CREATE USER 'demo'@'localhost' identified by 'demo';
GRANT ALL on demo_app.* to 'demo'@'localhost';
FLUSH PRIVILEGES;

CREATE TABLE IF NOT EXISTS `users` (
	`id` int NOT NULL AUTO_INCREMENT,
	`nickname` char(255) NOT NULL UNIQUE,
	`active` bool NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
