CREATE DATABASE IF NOT EXISTS `carapi` /*!40100 DEFAULT CHARACTER SET utf8 */;
GRANT ALL ON carapi.* TO 'carapi_user'@'%';

USE carapi;

CREATE TABLE `advert` (
  `id` int(11) NOT NULL,
  `title` text NOT NULL,
  `fuel` varchar(45) NOT NULL,
  `price` int(11) NOT NULL,
  `new` char(1) NOT NULL,
  `mileage` int(11) DEFAULT NULL,
  `first_registration` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
