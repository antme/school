package com.wx.school.service.message;

import com.wx.school.bean.user.SMS;
import com.wx.school.bean.user.User;

public interface IMessageService {

	void addRegSms(SMS sms);
	
	void checkSms(User user, int smsType);
	
	
}
