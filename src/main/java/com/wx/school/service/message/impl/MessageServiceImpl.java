package com.wx.school.service.message.impl;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.exceptions.ClientException;
import com.eweblib.bean.vo.EntityResults;
import com.eweblib.dao.IQueryDao;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.dbhelper.DataBaseQueryOpertion;
import com.eweblib.exception.ResponseException;
import com.eweblib.util.DateUtil;
import com.eweblib.util.EweblibUtil;
import com.wx.school.bean.school.School;
import com.wx.school.bean.school.StudentNumber;
import com.wx.school.bean.user.SMS;
import com.wx.school.bean.user.SmsLog;
import com.wx.school.bean.user.User;
import com.wx.school.service.ISchoolService;
import com.wx.school.service.impl.UserServiceImpl;
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
		query.and(DataBaseQueryOpertion.IS_FALSE, StudentNumber.IS_SMS_SENT);

		List<StudentNumber> slist = this.dao.listByQuery(query, StudentNumber.class);
		
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
		smsLog.setFailedCount(0);
		smsLog.setSuccessCount(mobileSet.size());

		smsLog.setMobileNumbers(EweblibUtil.toJson(mobileSet).replace("[", "").replace("]", "").replaceAll("\"", ""));

		School school = this.dao.findById(smsLog.getSchoolId(), School.TABLE_NAME, School.class);
		try {
			SmsHelp.sendSchoolNoticeSms(DateUtil.getDateString(smsLog.getSignDate()), smsLog.getStartTime(),
					smsLog.getEndTime(), school.getName(), smsLog.getPlace(), smsLog.getMobileNumbers());
		} catch (ClientException e) {
			logger.fatal("send msg failed", e);
			throw new ResponseException("发送短信失败，请稍后再试");
		}
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

		return this.dao.listByQueryWithPagnation(query, SmsLog.class);
	}
}
