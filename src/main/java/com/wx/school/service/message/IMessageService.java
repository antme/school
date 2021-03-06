package com.wx.school.service.message;

import com.eweblib.bean.vo.EntityResults;
import com.wx.school.bean.school.StudentSchoolInfo;
import com.wx.school.bean.user.SMS;
import com.wx.school.bean.user.SmsLog;
import com.wx.school.bean.user.User;

public interface IMessageService {

	void addRegSms(SMS sms);
	
	void checkSms(User user, int smsType);

	@Deprecated
	void sendSchoolNoticeSms(SmsLog smsLog);

	EntityResults<SmsLog> listSentSchoolNoticeSms(SmsLog smsLog);

	void checkNoticeSmsSendStatus();

	String exportFailedNotice(SmsLog smsLog);
	
	String loadNoticeMsg(StudentSchoolInfo info, boolean admin);
	
	void deleteSmsLog(String schoolId);

	void sendSchoolTakeNumberNotice();
	
}
