package com.wx.school.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eweblib.annotation.role.LoginRequired;
import com.eweblib.annotation.role.Permission;
import com.eweblib.bean.BaseEntity;
import com.eweblib.controller.AbstractController;
import com.eweblib.exception.ResponseException;
import com.eweblib.util.EWeblibThreadLocal;
import com.eweblib.util.ImgUtil;
import com.wx.school.bean.user.Person;
import com.wx.school.bean.user.User;
import com.wx.school.service.IUserService;

@Controller
@RequestMapping("/user")
@Permission()
@LoginRequired()
public class UserController extends AbstractController {

	public static final String IMG_CODE = "imgCode_User";

	@Autowired
	private IUserService userService;

	@RequestMapping("/logout.do")
	@LoginRequired(required = false)
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		clearLoginSession(request, response);
		response.setContentType("text/html;charset=UTF-8");
		response.addHeader("Accept-Encoding", "gzip, deflate");
		response.addHeader("Location", "index.jsp");

		userService.logout();
		try {
			response.sendRedirect("/index.jsp");
		} catch (IOException e) {
		}
	}

	@RequestMapping("/login.do")
	@LoginRequired(required = false)
	public void login(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, false, User.class);

		// String imgCode = getSessionValue(request, IMG_CODE);
		// if (imgCode != null && user.getImgCode() != null &&
		// user.getImgCode().equalsIgnoreCase(imgCode)) {
		user = userService.login(user);
		setLoginSessionInfo(request, response, user);
		responseWithEntity(null, request, response);
		// } else {
		// throw new ResponseException("请输入正确验证码");
		// }
	}
	
	
	@RequestMapping("/back/login.do")
	@LoginRequired(required = false)
	public void loginBack(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, false, User.class);

		String imgCode = getSessionValue(request, IMG_CODE);
		if (imgCode != null && user.getImgCode() != null && user.getImgCode().equalsIgnoreCase(imgCode)) {
			user = userService.login(user);
			setLoginSessionInfo(request, response, user);
			responseWithEntity(null, request, response);
		} else {
			throw new ResponseException("请输入正确验证码");
		}
	}

	@RequestMapping("/login/valid.do")
	@LoginRequired(required = false)
	public void validLoginStatus(HttpServletRequest request, HttpServletResponse response) {

		responseWithMapData(null, request, response);

	}

	@RequestMapping("/img.do")
	@LoginRequired(required = false)
	public void loadLoginImg(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("image/png");
		String word = ImgUtil.getRandomWord(4);
		setSessionValue(request, IMG_CODE, word);
		BufferedImage image = ImgUtil.getCaptchaImage(word, 93, 35);

		try {
			ImageIO.write(image, "png", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/admin/load.do")
	public void loadUserForAdmin(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, true, User.class);
		responseWithEntity(userService.loadUserForAdmin(user), request, response);
	}

	@RequestMapping("/admin/edit.do")
	public void editUserForAdmin(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, true, User.class);
		userService.editUserForAdmin(user);
		responseWithEntity(null, request, response);
	}

	@RequestMapping("/parent/submit.do")
	public void submitPersonInfo(HttpServletRequest request, HttpServletResponse response) {
		Person person = (Person) parserJsonParameters(request, true, Person.class);
		User user = (User) parserJsonParameters(request, true, User.class);

		user = userService.submitPersonInfo(person, user);
		setLoginSessionInfo(request, response, user);
		responseWithEntity(null, request, response);
	}

	@RequestMapping("/parent/mine.do")
	public void loadMyPersonInfo(HttpServletRequest request, HttpServletResponse response) {
		Person person = userService.loadMyPersonInfo();
		responseWithEntity(person, request, response);
	}
	
	@RequestMapping("/parent/submitStudent.do")
	public void submitStudentInfo(HttpServletRequest request, HttpServletResponse response) {
		Person person = (Person) parserJsonParameters(request, true, Person.class);

		userService.submitStudentInfo(person);
		responseWithEntity(null, request, response);
	}
	
	@RequestMapping("/parent/listStudent.do")
	public void listStudentInfo(HttpServletRequest request, HttpServletResponse response) {

		responseWithListData(userService.listStudentInfo(), request, response);
	}
	
	

	@RequestMapping("/password/update.do")
	public void updateUserPassword(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, true, User.class);
		userService.updateUserPassword(user);

		responseWithEntity(null, request, response);

	}
	
	

	@RequestMapping("/password/forgot/update.do")
	public void updateUserPasswordWhenForgot(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, true, User.class);
		user = userService.updateUserPasswordWhenForgot(user);
		setLoginSessionInfo(request, response, user);
		responseWithEntity(null, request, response);

	}
	
	
	

	public void setLoginSessionInfo(HttpServletRequest request, HttpServletResponse response, BaseEntity entity) {
		User user = (User) entity;
		removeSessionInfo(request);
		EWeblibThreadLocal.set(BaseEntity.ID, user.getId());
		setSessionValue(request, User.USER_NAME, user.getUserName());
		setSessionValue(request, BaseEntity.ID, user.getId());

		Cookie cookie = new Cookie("sch_uid", user.getId());
		cookie.setMaxAge(30 * 60);
		response.addCookie(cookie);
	}

}
