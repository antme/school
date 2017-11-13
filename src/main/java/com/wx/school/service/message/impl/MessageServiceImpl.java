package com.wx.school.service.message.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.sms.model.v20160927.QuerySmsFailByPageResponse.stat;
import com.eweblib.bean.vo.EntityResults;
import com.eweblib.cfg.ConfigManager;
import com.eweblib.dao.IQueryDao;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.dbhelper.DataBaseQueryOpertion;
import com.eweblib.exception.ResponseException;
import com.eweblib.util.DateUtil;
import com.eweblib.util.EweblibUtil;
import com.eweblib.util.ExcelUtil;
import com.wx.school.bean.school.School;
import com.wx.school.bean.school.SchoolPlan;
import com.wx.school.bean.school.StudentNumber;
import com.wx.school.bean.school.StudentSchoolInfo;
import com.wx.school.bean.user.SMS;
import com.wx.school.bean.user.SmsLog;
import com.wx.school.bean.user.Student;
import com.wx.school.bean.user.User;
import com.wx.school.service.ISchoolService;
import com.wx.school.service.message.IMessageService;
import com.wx.school.service.message.SmsHelp;

@Service
public class MessageServiceImpl implements IMessageService {

	@Autowired
	private IQueryDao dao;

	@Autowired
	private ISchoolService schoolService;
	public static Logger logger = LogManager.getLogger(MessageServiceImpl.class);

	public void addRegSms(SMS sms) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(SMS.TABLE_NAME);
		Calendar c1 = Calendar.getInstance();
		c1.add(Calendar.MINUTE, -1);
		query.and(DataBaseQueryOpertion.LARGER_THAN_EQUALS, SMS.CREATED_ON, c1.getTime());
		query.and(SMS.MOBILE_NUMBER, sms.getMobileNumber());
		if (this.dao.exists(query)) {
			throw new ResponseException("发送验证码失败，请稍后再试");
		}

		DataBaseQueryBuilder delQeury = new DataBaseQueryBuilder(SMS.TABLE_NAME);
		delQeury.and(SMS.MOBILE_NUMBER, sms.getMobileNumber());
		delQeury.and(SMS.SMS_TYPE, sms.getSmsType());
		this.dao.deleteByQuery(delQeury);

		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -5);
		DataBaseQueryBuilder delQeury2 = new DataBaseQueryBuilder(SMS.TABLE_NAME);
		delQeury2.and(DataBaseQueryOpertion.LESS_THAN, SMS.CREATED_ON, c.getTime());
		this.dao.deleteByQuery(delQeury2);

		this.dao.insert(sms);

	}

	public void checkSms(User user, int smsType) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, -5);
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(SMS.TABLE_NAME);
		query.and(SMS.MOBILE_NUMBER, user.getMobileNumber());
		query.and(SMS.SMS_TYPE, smsType);
		query.and(SMS.VALID_CODE, user.getValidCode());
		query.and(DataBaseQueryOpertion.LARGER_THAN_EQUALS, SMS.CREATED_ON, c.getTime());

		if (!this.dao.exists(query)) {
			throw new ResponseException("验证码错误或者过期");
		}

	}

	public void sendSchoolNoticeSms(SmsLog smsLog) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(StudentNumber.TABLE_NAME);
		query.and(StudentNumber.SCHOOL_ID, smsLog.getSchoolId());
		query.and(DataBaseQueryOpertion.LARGER_THAN_EQUALS, StudentNumber.NUMBER, smsLog.getStartNumber());
		query.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, StudentNumber.NUMBER, smsLog.getEndNumber());
		// query.and(DataBaseQueryOpertion.IS_FALSE, StudentNumber.IS_SMS_SENT);

		List<StudentNumber> slist = this.dao.listByQuery(query, StudentNumber.class);
		if (smsLog.getPlace().length() > 15) {
			throw new ResponseException("地址长度超过15个字符限制");

		}

		if (slist.isEmpty()) {
			throw new ResponseException("此校区无取号家长需要通知");
		}
		Set<String> uids = new HashSet<String>();
		for (StudentNumber sn : slist) {
			uids.add(sn.getOwnerId());
		}

		List<User> userList = this.dao.listByQuery(new DataBaseQueryBuilder(User.TABLE_NAME), User.class);
		Set<String> mobileSet = new HashSet<String>();
		for (User user : userList) {
			if (uids.contains(user.getId())) {
				mobileSet.add(user.getMobileNumber());
			}
		}

		smsLog.setTotalSend(mobileSet.size());

		smsLog.setMobileNumbers(EweblibUtil.toJson(mobileSet).replace("[", "").replace("]", "").replaceAll("\"", ""));

		// School school = this.dao.findById(smsLog.getSchoolId(),
		// School.TABLE_NAME, School.class);

		Set<String> failedMobiles = new HashSet<String>();

		// Set<String> failedMobiles =
		// SmsHelp.sendSchoolNoticeSms(DateUtil.getDateString(smsLog.getSignDate()),
		// smsLog.getStartTime(), smsLog.getEndTime(), school.getName(),
		// smsLog.getPlace(),
		// smsLog.getMobileNumbers());
		smsLog.setSuccessCount(smsLog.getTotalSend() - failedMobiles.size());
		smsLog.setFailedCount(failedMobiles.size());
		smsLog.setFailedMobileNumbers(
				EweblibUtil.toJson(failedMobiles).replace("[", "").replace("]", "").replaceAll("\"", ""));

		this.dao.insert(smsLog);
		for (StudentNumber sn : slist) {
			sn.setIsSmsSent(true);
			this.dao.updateById(sn, new String[] { StudentNumber.IS_SMS_SENT });
		}
	}

	public EntityResults<SmsLog> listSentSchoolNoticeSms(SmsLog smsLog) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(SmsLog.TABLE_NAME);
		query.leftJoin(SmsLog.TABLE_NAME, School.TABLE_NAME, SmsLog.SCHOOL_ID, School.ID);
		query.joinColumns(School.TABLE_NAME, new String[] { School.NAME + " as schoolName" });
		query.limitColumns(new SmsLog().getColumnList());

		if (EweblibUtil.isValid(smsLog.getSchoolId())) {
			query.and(SmsLog.SCHOOL_ID, smsLog.getSchoolId());
		}

		if (EweblibUtil.isValid(smsLog.getStartNumber())) {
			query.and(DataBaseQueryOpertion.LARGER_THAN_EQUALS, SmsLog.START_NUMBER, smsLog.getStartNumber());
		}

		if (EweblibUtil.isValid(smsLog.getEndNumber())) {
			query.and(DataBaseQueryOpertion.LESS_THAN_EQUAILS, SmsLog.END_NUMBER, smsLog.getEndNumber());
		}

		return this.dao.listByQueryWithPagnation(query, SmsLog.class);
	}

	public void checkNoticeSmsSendStatus() {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(SmsLog.TABLE_NAME);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -1);
		query.and(DataBaseQueryOpertion.LARGER_THAN_EQUALS, SmsLog.CREATED_ON, c.getTime());

		List<SmsLog> logList = this.dao.listByQuery(query, SmsLog.class);
		for (SmsLog log : logList) {
			List<stat> statList = SmsHelp.getFailedMobileNumbers(DateUtil.getDateString(log.getCreatedOn()), 0);
			statList.addAll(SmsHelp.getFailedMobileNumbers(DateUtil.getDateString(log.getCreatedOn()), 2));
			for (stat stat : statList) {
				if (stat.getSmsCode().equals("SMS_36350142")
						&& log.getMobileNumbers().indexOf(stat.getReceiverNum()) != -1)

					if (log.getFailedMobileNumbers().indexOf(stat.getReceiverNum()) != -1) {
						log.setFailedCount(log.getFailedCount() + 1);
						log.setSuccessCount(log.getSuccessCount() + 1);
						log.setFailedMobileNumbers(log.getFailedMobileNumbers() + "," + stat.getReceiverNum());
						this.dao.updateById(log, new String[] { SmsLog.FAILED_MOBILE_NUMBERS, SmsLog.SUCCESS_COUNT,
								SmsLog.FAILED_COUNT });
					}

			}
		}

	}

	public String exportFailedNotice(SmsLog smsLog) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(SmsLog.TABLE_NAME);
		query.leftJoin(SmsLog.TABLE_NAME, School.TABLE_NAME, SmsLog.SCHOOL_ID, School.ID);
		query.joinColumns(School.TABLE_NAME, new String[] { School.NAME + " as schoolName" });
		query.limitColumns(new SmsLog().getColumnList());
		query.and(SmsLog.ID, smsLog.getId());

		SmsLog log = this.dao.findOneByQuery(query, SmsLog.class);

		String dowload_path = "短信通知_" + log.getSchoolName() + "_" + log.getStartNumber() + "_" + log.getEndNumber()
				+ ".xls";
		String f = ConfigManager.getProperty("download_path") + dowload_path;
		String[] colunmTitleHeaders = new String[] { "校区", "号数", "家长姓名", "家长手机", "报名日期", "报名开始时间", "报名结束时间", "报名地址" };
		String[] colunmHeaders = new String[] { "schoolName", "number", "parentName", "mobileNumbers", "signDate",
				"startTime", "endTime", "place" };

		List<SmsLog> logList = new ArrayList<SmsLog>();
		if (log.getFailedMobileNumbers() != null) {
			String[] mobiles = log.getFailedMobileNumbers().split(",");
			for (String mobile : mobiles) {
				SmsLog tmp = (SmsLog) EweblibUtil.toEntity(log.toString(), SmsLog.class);
				tmp.setMobileNumbers(mobile);
				User user = this.dao.findByKeyValue(User.MOBILE_NUMBER, mobile, User.TABLE_NAME, User.class);
				tmp.setParentName(user.getName());

				StudentNumber sn = this.dao.findByKeyValue(StudentNumber.OWER_ID, user.getId(),
						StudentNumber.TABLE_NAME, StudentNumber.class);
				tmp.setNumber(sn.getNumber());
				logList.add(tmp);
			}
		}
		ExcelUtil.createExcelListFileByEntity(logList, colunmTitleHeaders, colunmHeaders, f);

		return dowload_path;
	}

	public String loadNoticeMsg(StudentSchoolInfo info, boolean admin) {

		String msg = "";
		DataBaseQueryBuilder logQuery = new DataBaseQueryBuilder(SmsLog.TABLE_NAME);
		logQuery.and(SmsLog.SCHOOL_ID, info.getSchool().getId());
		logQuery.orderBy(SmsLog.CREATED_ON, false);
		List<SmsLog> logList = this.dao.listByQuery(logQuery, SmsLog.class);
		SmsLog result = null;
		for (SmsLog log : logList) {
			if (info.getNumber() <= log.getEndNumber() && info.getNumber() >= log.getStartNumber()) {
				result = log;
				break;
			}
		}
		if (result != null) {
			School school = this.dao.findById(result.getSchoolId(), School.TABLE_NAME, School.class);

			String name = school.getName().replaceAll("校区", "");

			String place = result.getPlace();

			if (info.getStudent() != null && EweblibUtil.isValid(info.getStudent().getSignUpSchoolId())) {
				if (EweblibUtil.isValid(info.getStudent().getSignUpPlace())) {
					place = info.getStudent().getSignUpPlace();
				}
			}

			if (admin) {
				return DateUtil.getDateString(result.getSignDate()) + "，" + result.getStartTime() + "-"
						+ result.getEndTime() + "(" + place + ")";
			} else {
				msg = "请于" + DateUtil.getDateString(result.getSignDate()) + "，" + result.getStartTime() + "-"
						+ result.getEndTime() + "（地址：" + place + ")前往报名。";

			}
		}

		return msg;

	}

	public void deleteSmsLog(String schoolId) {
		DataBaseQueryBuilder delQuery = new DataBaseQueryBuilder(SmsLog.TABLE_NAME);
		delQuery.and(SmsLog.SCHOOL_ID, schoolId);
		this.dao.deleteByQuery(delQuery);
	}
	
	public void sendSchoolTakeNumberNotice() {
		
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(SchoolPlan.TABLE_NAME);
		query.and(DataBaseQueryOpertion.IS_TRUE, SchoolPlan.IS_DISPLAY_FOR_WX);
		List<SchoolPlan> planList = this.dao.listByQuery(query, SchoolPlan.class);

		for (SchoolPlan plan : planList) {

			Date endDate = DateUtil.getDateTime(
					DateUtil.getDateString(plan.getTakeNumberDate()) + " " + plan.getEndTime() + ":00");
	
			if (new Date().getTime() < endDate.getTime()
					&& (new Date().getTime() + (60 * 60 * 1000) > endDate.getTime())) {

				DataBaseQueryBuilder studentQuery = new DataBaseQueryBuilder(Student.TABLE_NAME);
				studentQuery.and(Student.SIGN_UP_SCHOOL_ID, plan.getSchoolId());

				studentQuery.leftJoin(Student.TABLE_NAME, StudentNumber.TABLE_NAME, Student.ID,
						StudentNumber.STUDENT_ID);
				studentQuery.joinColumns(StudentNumber.TABLE_NAME, new String[] { StudentNumber.NUMBER });

				List<Student> studentList = this.dao.listByQuery(studentQuery, Student.class);
				for (Student student : studentList) {

					if (student.getNumber() == null && student.getOwnerId() != null) {
						User user = this.dao.findById(student.getOwnerId(), User.TABLE_NAME, User.class);

						if (user != null) {
							DataBaseQueryBuilder smsQuery = new DataBaseQueryBuilder(SMS.TABLE_NAME);
							smsQuery.and(SMS.MOBILE_NUMBER, user.getMobileNumber());
							smsQuery.and(SMS.SMS_TYPE, 99);

							SMS sms = this.dao.findOneByQuery(smsQuery, SMS.class);
							if (sms == null) {

								sms = new SMS();
								sms.setMobileNumber(user.getMobileNumber());
								sms.setSmsType(99);
								this.dao.insert(sms);

								try {
									SmsHelp.sendTakeNumberSms(user.getMobileNumber());
								} catch (ClientException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						}
					}
				}

			}
		}
		
		
	}
}
