package com.wx.school.service.message;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import com.eweblib.exception.ResponseException;
import com.eweblib.util.EweblibUtil;
import com.wx.school.service.impl.UserServiceImpl;

public class SmsHelp {

	private static IAcsClient client = null;
	public static Logger logger = LogManager.getLogger(UserServiceImpl.class);

	static {
		try {
			loadClient();
		} catch (ClientException e) {
			logger.fatal("get aliyun client failed", e);
		}
	}

	public static void sendRegSms(String code, Integer time, String mobileNumber) throws ClientException {

		if (client != null) {
			SingleSendSmsRequest request = new SingleSendSmsRequest();
			request.setSignName("百花学习塾");
			request.setTemplateCode("SMS_35740141");
			Map<String, String> map = new HashMap<String, String>();
			map.put("code", code);
			map.put("time", time.toString());
			request.setParamString(EweblibUtil.toJson(map));
			request.setRecNum(mobileNumber);
			SingleSendSmsResponse httpResponse = client.getAcsResponse(request);

			System.out.println(httpResponse.getRequestId());
		} else {
			loadClient();
			throw new ResponseException("短信发送失败，请稍后再试");
		}

	}

	private static void loadClient() throws ClientException {
		if (client == null) {

			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4dB7XA1Qk15K",
					"JEOke3hiPiZQC6y37ewVOg1QtvGWhf");
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms", "sms.aliyuncs.com");
			client = new DefaultAcsClient(profile);
		}

	}

	public static void sendSchoolNoticeSms(String date, String time1, String time2, String name, String place,
			String mobileNumers) throws ClientException {
		if (client != null) {

			SingleSendSmsRequest request = new SingleSendSmsRequest();
			request.setSignName("百花学习塾");
			request.setTemplateCode("SMS_36350142");
			Map<String, String> map = new HashMap<String, String>();
			map.put("date", date);
			map.put("time1", time1);
			map.put("time2", time2);
			map.put("name", name);
			map.put("place", place);
			request.setParamString(EweblibUtil.toJson(map));
			request.setRecNum(mobileNumers);
			SingleSendSmsResponse httpResponse = client.getAcsResponse(request);

			System.out.println(httpResponse.getRequestId());
		} else {
			loadClient();
			throw new ResponseException("短信发送失败，请稍后再试");
		}

	}

	public static void main(String[] args) {

		try {
			SmsHelp.sendSchoolNoticeSms("2017-01-18", "09:10", "09:30", "徐汇校区", "上海市徐汇区天钥桥路30号", "18516692298");
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
