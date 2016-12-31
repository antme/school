use school;
alter table Student add column remark text default null;
alter table User add column isVip tinyint(1) DEFAULT '0';
update User set isVip=false;
