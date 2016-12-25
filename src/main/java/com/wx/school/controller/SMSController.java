package com.wx.school.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eweblib.annotation.role.LoginRequired;
import com.eweblib.annotation.role.Permission;
import com.eweblib.controller.AbstractController;
import com.eweblib.util.ImgUtil;
import com.wx.school.bean.user.SMS;
import com.wx.school.bean.user.User;
import com.wx.school.service.IUserService;

@Controller
@RequestMapping("/sms")
@Permission()
@LoginRequired()
public class SMSController extends AbstractController {

	
	@Autowired
	private IUserService us;
	
	
	@RequestMapping("/parent/reg.do")
	@LoginRequired(required = false)
	public void sendParentRegSms(HttpServletRequest request, HttpServletResponse response) {
		SMS sms = (SMS) parserJsonParameters(request, false, SMS.class);
		String word = ImgUtil.getRandomWord(4);
		setSessionValue(request, "SMS_REG", word);
		responseWithEntity(null, request, response);

	}
	
	@RequestMapping("/user/password/forgot.do")
	@LoginRequired(required = false)
	public void sendUserForgotPasswordRegSms(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, false, User.class);
		us.valideMobileNumber(user);
		String word = ImgUtil.getRandomWord(4);
		setSessionValue(request, "FORGOT_PASSWORD_SMS", word);
		responseWithEntity(null, request, response);

	}

}
