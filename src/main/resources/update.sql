
delete from  SmsLog;
delete from  StudentNumber;
delete from SchoolPlan;
delete from SchoolBaoMingPlan;

alter table SchoolBaoMingPlan add column middayRestHours float default 0;


update Student set remark=null;
update Student set signUpSchoolId=null;
update Student set signUpPlace=null;
update Student set schoolId=null;



