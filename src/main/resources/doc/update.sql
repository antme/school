use school;
alter table Student add column remark text default null;
alter table User add column isVip tinyint(1) DEFAULT '0';
update User set isVip=false;



alter table StudentNumber add column remark text default null;

alter table User add index `mobileNumber` (`mobileNumber`);
alter table User add index `password` (`password`);
alter table StudentNumber add index `studentId` (`studentId`);
alter table StudentNumber add index `schoolId` (`schoolId`);