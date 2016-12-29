drop database school;
create database school;
use school;

CREATE TABLE `User` (
  `id` varchar(36) NOT NULL,
  `userName` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(36) DEFAULT NULL,
  `mobileNumber` varchar(36) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `isAdmin` tinyint(1) DEFAULT '0',
  `email` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `userType` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;   

CREATE TABLE `Student` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sex` varchar(36) DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `ownerId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;  

 


CREATE TABLE `School` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;  



CREATE TABLE `SchoolPlan` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `onlyForVip` tinyint(1) DEFAULT '0',
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  `takeNumberDate` date DEFAULT NULL,
  `startTime` varchar(255)  DEFAULT NULL,
  `endTime` varchar(255)  DEFAULT NULL,
  `takeStatus` int default 2,
  `isDisplayForWx` tinyint(1) DEFAULT '0',
  `schoolId` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;  




CREATE TABLE `StudentNumber` (
  `id` varchar(36) NOT NULL,
  `number` int DEFAULT '0',
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  `planId` varchar(36) DEFAULT NULL,
  `ownerId` varchar(36) DEFAULT NULL,
  `studentId` varchar(36) DEFAULT NULL,
  `schoolId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;  



CREATE TABLE `SMS` (
  `id` varchar(36) NOT NULL,
  `validCode` varchar(6) DEFAULT '0',
  `smsType` int DEFAULT '0',
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  `mobileNumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;  



CREATE TABLE `SmsLog` (
  `id` varchar(36) NOT NULL,
  `schoolName` varchar(255) DEFAULT '0',
  `schoolId` varchar(36) DEFAULT NULL,
  `startNumber` int DEFAULT '0',
  `endNumber` int DEFAULT '0',
  `totalSend` int DEFAULT '0',
  `successCount` int DEFAULT '0',
  `failedCount` int DEFAULT '0',
  `failedMobileNumbers` TEXT DEFAULT NULL,
  `mobileNumbers` TEXT DEFAULT NULL,
  `signDate` date DEFAULT NULL,
  `startTime` varchar(36) DEFAULT NULL,
  `endTime` varchar(36) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `creatorId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;  



alter table StudentNumber add column `isSmsSent` tinyint(1) DEFAULT '0';



