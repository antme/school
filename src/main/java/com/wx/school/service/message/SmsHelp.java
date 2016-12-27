package com.wx.school.service.message;

import java.util.HashMap;
import java.util.Map;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import com.eweblib.util.EweblibUtil;

public class SmsHelp {

	public static void sendRegSms(String code, Integer time) throws ClientException {
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4dB7XA1Qk15K",
				"JEOke3hiPiZQC6y37ewVOg1QtvGWhf");
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms", "sms.aliyuncs.com");
		IAcsClient client = new DefaultAcsClient(profile);
		SingleSendSmsRequest request = new SingleSendSmsRequest();
		try {
			request.setSignName("百花");
			request.setTemplateCode("SMS_35740141");
			Map<String, String> map = new HashMap<String, String>();
			map.put("code", code);
			map.put("time", time.toString());
			request.setParamString(EweblibUtil.toJson(map));
			request.setRecNum("18516692298");
			SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
			System.out.println(httpResponse.getRequestId());
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
}
