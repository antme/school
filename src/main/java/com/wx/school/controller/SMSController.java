package com.wx.school.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eweblib.annotation.role.LoginRequired;
import com.eweblib.annotation.role.Permission;
import com.eweblib.controller.AbstractController;
import com.eweblib.util.ImgUtil;
import com.wx.school.bean.user.SMS;

@Controller
@RequestMapping("/sms")
@Permission()
@LoginRequired()
public class SMSController extends AbstractController {

	@RequestMapping("/parent/reg.do")
	@LoginRequired(required = false)
	public void sendParentRegSms(HttpServletRequest request, HttpServletResponse response) {
		SMS sms = (SMS) parserJsonParameters(request, false, SMS.class);
		String word = ImgUtil.getRandomWord(4);
		setSessionValue(request, "SMS_REG", word);
		responseWithEntity(null, request, response);

	}

}
