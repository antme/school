
delete from  SmsLog;

delete from  StudentNumber;

delete from SchoolPlan;

delete from SchoolBaoMingPlan;

alter table SchoolBaoMingPlan add column middayRestHours float default 0;

