package com.wx.school.service.message;

import java.util.HashMap;
import java.util.Map;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.QuerySmsFailByPageRequest;
import com.aliyuncs.sms.model.v20160927.QuerySmsFailByPageResponse;
import com.aliyuncs.sms.model.v20160927.QuerySmsStatisticsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import com.eweblib.util.EweblibUtil;

public class SmsHelp {

	public static void sendRegSms(String code, Integer time, String mobileNumber) throws ClientException {
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4dB7XA1Qk15K",
				"JEOke3hiPiZQC6y37ewVOg1QtvGWhf");
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms", "sms.aliyuncs.com");
		IAcsClient client = new DefaultAcsClient(profile);

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

	}

	public static void sendSchoolNoticeSms(String date, String time1, String time2, String name, String place,
			String mobileNumers) throws ClientException {
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4dB7XA1Qk15K",
				"JEOke3hiPiZQC6y37ewVOg1QtvGWhf");
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms", "sms.aliyuncs.com");
		IAcsClient client = new DefaultAcsClient(profile);

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

	}
	
	public static void main(String[] args){
		
	}

}
