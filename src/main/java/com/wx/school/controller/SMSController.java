package com.wx.school.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aliyuncs.exceptions.ClientException;
import com.eweblib.annotation.role.LoginRequired;
import com.eweblib.annotation.role.Permission;
import com.eweblib.controller.AbstractController;
import com.eweblib.exception.ResponseException;
import com.eweblib.util.EweblibUtil;
import com.eweblib.util.ImgUtil;
import com.wx.school.bean.user.SMS;
import com.wx.school.bean.user.User;
import com.wx.school.service.IUserService;
import com.wx.school.service.message.IMessageService;
import com.wx.school.service.message.SmsHelp;

@Controller
@RequestMapping("/sms")
@Permission()
@LoginRequired()
public class SMSController extends AbstractController {

	@Autowired
	private IUserService us;

	@Autowired
	private IMessageService ms;

	@RequestMapping("/parent/reg.do")
	@LoginRequired(required = false)
	public void sendParentRegSms(HttpServletRequest request, HttpServletResponse response) {

		SMS sms = (SMS) parserJsonParameters(request, false, SMS.class);

		if (EweblibUtil.isEmpty(sms.getMobileNumber())) {
			throw new ResponseException("手机号码不能为空");
		}

		String word = ImgUtil.getRandomNumber(6);
		sendSms(sms, word, 0);

		responseWithEntity(null, request, response);

	}

	@RequestMapping("/user/password/forgot.do")
	@LoginRequired(required = false)
	public void sendUserForgotPasswordRegSms(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, false, User.class);
		us.valideMobileNumber(user);

		SMS sms = (SMS) parserJsonParameters(request, false, SMS.class);
		String word = ImgUtil.getRandomNumber(6);

		sendSms(sms, word, 1);

		responseWithEntity(null, request, response);

	}

	private void sendSms(SMS sms, String word, int smsType) {
		sms.setValidCode(word);
		sms.setSmsType(smsType);
		ms.addRegSms(sms);
		try {
			SmsHelp.sendRegSms(sms.getValidCode(), 5, sms.getMobileNumber());
		} catch (ClientException e) {
			throw new ResponseException("短信发送失败，请稍后再试");
		}
	}

}
