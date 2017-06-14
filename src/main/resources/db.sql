CREATE DATABASE `carapi` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE carapi;

CREATE TABLE `advert` (
  `id` int(11) NOT NULL,
  `title` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
