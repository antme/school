package com.wx.school.service.message.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eweblib.dao.IQueryDao;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.dbhelper.DataBaseQueryOpertion;
import com.eweblib.exception.ResponseException;
import com.wx.school.bean.user.SMS;
import com.wx.school.bean.user.User;
import com.wx.school.service.message.IMessageService;

@Service
public class MessageServiceImpl implements IMessageService {

	@Autowired
	private IQueryDao dao;

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
}
