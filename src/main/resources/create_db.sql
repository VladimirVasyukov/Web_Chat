CREATE TABLE `message` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `UserFrom` varchar(30) NOT NULL,
  `Message` varchar(1000) DEFAULT NULL,
  `TimeStamp` timestamp NULL DEFAULT NULL,
  `StatusID` int NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `UserIndex` (`UserFrom`),
  KEY `StatusIndex` (`StatusID`),
  CONSTRAINT `FK_STATUS` FOREIGN KEY (`StatusID`) REFERENCES `status` (`ID`),
  CONSTRAINT `FK_USER` FOREIGN KEY (`UserFrom`) REFERENCES `user` (`Nick`)
) AUTO_INCREMENT=1;

CREATE TABLE `role` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Title` varchar(20) NOT NULL,
  `Description` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Title` (`Title`)
) AUTO_INCREMENT=1;

CREATE TABLE `status` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Title` varchar(20) NOT NULL,
  `Description` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `Title` (`Title`)
) AUTO_INCREMENT=1;

CREATE TABLE `user` (
  `Nick` varchar(30) NOT NULL,
  `RoleID` int NOT NULL DEFAULT '2',
  PRIMARY KEY (`Nick`),
  KEY `RoleIndex` (`RoleID`),
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`RoleID`) REFERENCES `role` (`ID`)
);