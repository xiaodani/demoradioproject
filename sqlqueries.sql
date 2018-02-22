

-- --------------------------------------------CREATE THE DATABASE AND FRAMEWORK --------------------------------------------
CREATE DATABASE IF NOT EXISTS `radiostation` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `radiostation`;


CREATE TABLE IF NOT EXISTS `genre` (
`ID` int(11) NOT NULL AUTO_INCREMENT,
`Name` varchar(255) NOT NULL,
PRIMARY KEY (`ID`)
) 


CREATE TABLE IF NOT EXISTS `song` (
`ID` int(11) NOT NULL AUTO_INCREMENT,
`GenreID` int(11) NOT NULL,
`Name` varchar(255) NOT NULL,
PRIMARY KEY (`ID`)
) 

CREATE TABLE IF NOT EXISTS `listener` (
`ID` int(11) NOT NULL AUTO_INCREMENT,
`Name` varchar(255) NOT NULL,
PRIMARY KEY (`ID`)
) 

CREATE TABLE IF NOT EXISTS `listenergenre` (
`ID` int(11) NOT NULL AUTO_INCREMENT,
`GenreID` int(11) NOT NULL,
`Priority` int(11) NOT NULL,
PRIMARY KEY (`ID`, `GenreID`, `Priority` )
) 

-- --------------------------------------------CREATE THE DATABASE AND FRAMEWORK END --------------------------------------------


-- Clear Data
TRUNCATE TABLE `genre`;
TRUNCATE TABLE `song`;
TRUNCATE TABLE `listener`;
TRUNCATE TABLE `listenergenre`;

DROP TABLE `genre`;
DROP TABLE `song`;
DROP TABLE `listener`;
DROP TABLE `listenergenre`;

-- Populate with data
INSERT INTO `genre` (`ID`, `Name`) VALUES
	(1, 'jpop'),
	(2, 'kpop'),
	(3, 'classical'),
	(4, 'rock'),
	(5, 'rap');
	
INSERT INTO `song` (`ID`, `GenreID`, `Name`) VALUES
	(1, 1, 'jpop song'),
	(2, 2, 'kpop song'),
	(3, 3, 'classical song'),
	(4, 4, 'rock song'),
	(5, 5, 'rap song');
	
INSERT INTO `listener` (`ID`, `Name`) VALUES
	(1, 'Albert'),
	(2, 'Bert'),
	(3, 'Cert'),
	(4, 'Derp'),
	(5, 'Elmo');

INSERT INTO `listenergenre` (`ID`, `GenreID`, `Priority`) VALUES
	(1, 1, 0),
	(2, 2, 0),
	(3, 3, 0),
	(4, 4, 0),
	(5, 5, 0),
	(5, 2, 1),
	(5, 3, 2),
	(5, 5, 3);


	
-- Test Selection
SELECT * from `genre`;
SELECT * from `song`;
SELECT * from `listener`;
SELECT * from `listenergenre`;
	