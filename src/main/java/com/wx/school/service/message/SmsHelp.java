package com.wx.school.service.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
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
			SendSmsRequest request = new SendSmsRequest();
			// 使用post提交
			request.setMethod(MethodType.POST);

			request.setSignName("百花学习塾");
			request.setTemplateCode("SMS_35740141");
			Map<String, String> map = new HashMap<String, String>();
			map.put("code", code);
			map.put("time", time.toString());
			request.setTemplateParam(EweblibUtil.toJson(map));
			request.setPhoneNumbers(mobileNumber);

			SendSmsResponse sendSmsResponse = client.getAcsResponse(request);

			System.out.println(sendSmsResponse.getRequestId());
		} else {
			loadClient();
			throw new ResponseException("短信发送失败，请稍后再试");
		}

	}

	public static void sendTakeNumberSms(String mobileNumber) throws ClientException {

		if (client != null) {

			SendSmsRequest request = new SendSmsRequest();
			// 使用post提交
			request.setMethod(MethodType.POST);

			request.setSignName("百花学习塾");
			request.setTemplateCode("SMS_109530319");
			Map<String, String> map = new HashMap<String, String>();

			request.setTemplateParam(EweblibUtil.toJson(map));

			request.setPhoneNumbers(mobileNumber);

			SendSmsResponse sendSmsResponse = client.getAcsResponse(request);

			System.out.println(sendSmsResponse.getRequestId());
		} else {
			loadClient();
			throw new ResponseException("短信发送失败，请稍后再试");
		}

	}

	private static void loadClient() throws ClientException {
		if (client == null) {

			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIw1tMO36vAKd5",
					"WyjUK3q8aXBjfEjp5vdg1lMECD6rBn");

			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
			client = new DefaultAcsClient(profile);
		}

	}

	public static Set<String> sendSchoolNoticeSms(String date, String time1, String time2, String name, String place,
			String mobileNumers) {
		Set<String> failedMobiles = new HashSet<String>();
		if (client != null) {

			Map<String, String> map = new HashMap<String, String>();
			map.put("date", date);
			map.put("time1", time1);
			map.put("time2", time2);
			map.put("name", name);
			map.put("place", place);

			String[] mobiles = mobileNumers.split(",");
			for (String mobile : mobiles) {
				if (EweblibUtil.isValid(mobile)) {
					SendSmsRequest request = new SendSmsRequest();
					// 使用post提交
					request.setMethod(MethodType.POST);

					request.setSignName("百花学习塾");

					request.setTemplateCode("SMS_36350142");
					request.setTemplateParam(EweblibUtil.toJson(map));

					request.setPhoneNumbers(mobile);
					SendSmsResponse httpResponse = null;
					try {
						httpResponse = client.getAcsResponse(request);
					} catch (ClientException e) {
						failedMobiles.add(mobile);
						logger.fatal("send SMS_36350142 sms failed", e);
					}

					if (httpResponse != null) {
						logger.debug("SMS_36350142 " + mobile + " " + httpResponse.getRequestId());
					}
				}
			}
		} else {
			try {
				loadClient();
			} catch (ClientException e) {
				logger.fatal("load aliyun client failed", e);
			}
			throw new ResponseException("短信发送失败，请稍后再试");
		}

		return failedMobiles;

	}

	// public static List<stat> getFailedMobileNumbers(String date, int smsType) {
	//
	// QuerySmsFailByPageRequest qs = new QuerySmsFailByPageRequest();
	// qs.setSmsType(smsType);
	// qs.setQueryTime(date);
	//
	// if (client != null) {
	// QuerySmsFailByPageResponse response = null;
	// try {
	// response = client.getAcsResponse(qs);
	// } catch (ClientException e) {
	// logger.fatal("query failed mobile number failed", e);
	// }
	// if (response != null) {
	// return response.getdata();
	// }
	// } else {
	// try {
	// loadClient();
	// } catch (ClientException e) {
	// logger.fatal("load aliyun client failed", e);
	// }
	// }
	//
	// return new ArrayList<stat>();
	//
	// }

	public static void main(String[] args) throws ServerException, ClientException {

		// QuerySmsFailByPageRequest qs = new QuerySmsFailByPageRequest();
		// qs.setSmsType(0);
		// qs.setQueryTime("2017-01-02");
		//
		// QuerySmsFailByPageResponse response = client.getAcsResponse(qs);
		// List<stat> list = response.getdata();
		//
		// for (stat stat : list) {
		// System.out.println(stat.getReceiverNum() + " " + stat.getSmsCode() +
		// " " + stat.getResultCode());
		//
		// }
		//
		// System.out.println("done");
		// // System.out.println(response.getdata());

		SmsHelp.sendTakeNumberSms("18516692298");

		// System.out.println("徐汇区天钥桥路30号15楼15".length());
	}

}
