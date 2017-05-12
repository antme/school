package com.wx.school.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.eweblib.annotation.role.LoginRequired;
import com.eweblib.annotation.role.Permission;
import com.eweblib.bean.BaseEntity;
import com.eweblib.controller.AbstractController;
import com.eweblib.exception.ResponseException;
import com.eweblib.util.DateUtil;
import com.eweblib.util.EWeblibThreadLocal;
import com.eweblib.util.EweblibUtil;
import com.eweblib.util.ImgUtil;
import com.wx.school.bean.SearchVO;
import com.wx.school.bean.user.Student;
import com.wx.school.bean.user.User;
import com.wx.school.service.IUserService;
import com.wx.school.service.impl.UserServiceImpl;

@Controller
@RequestMapping("/user")
@Permission()
@LoginRequired()
public class UserController extends AbstractController {

	public static final String IMG_CODE = "imgCode_User";
	public static Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	private IUserService userService;

	public static Map<String, String> safariLoginData = new HashMap<String, String>();

	@RequestMapping("/logout.do")
	@LoginRequired(required = false)
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		clearLoginSession(request, response);
		// response.setContentType("text/html;charset=UTF-8");
		// response.addHeader("Accept-Encoding", "gzip, deflate");
		// response.addHeader("Location", "index.jsp");

		Cookie cookie = new Cookie("baiHuaId", "");
		cookie.setMaxAge(0 * 60);
		response.addCookie(cookie);

		responseWithEntity(null, request, response);

	}

	@RequestMapping("/web/logout.do")
	@LoginRequired(required = false)
	public void logoutForWeb(HttpServletRequest request, HttpServletResponse response) {
		clearLoginSession(request, response);
		response.setContentType("text/html;charset=UTF-8");
		response.addHeader("Accept-Encoding", "gzip, deflate");
		response.addHeader("Location", "index.jsp");

		try {
			response.sendRedirect("/index.jsp");
		} catch (IOException e) {
		}

	}

	@RequestMapping("/login.do")
	@LoginRequired(required = false)
	public void login(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, false, User.class);
		Boolean ajax_session = user.getAjax_session();

		user = userService.login(user, false);
		setLoginSessionInfo(request, response, user);

		String loginKey = "";
		// if (ajax_session != null && ajax_session) {
		loginKey = UUID.randomUUID().toString() + "___" + new Date().getTime();

		safariLoginData.put(loginKey, user.getId());

		// }
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("s_key", loginKey);
		responseWithMapData(data, request, response);

	}

	@RequestMapping("/back/login.do")
	@LoginRequired(required = false)
	public void loginBack(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, false, User.class);

		String imgCode = getSessionValue(request, IMG_CODE);
		if (imgCode != null && user.getImgCode() != null && user.getImgCode().equalsIgnoreCase(imgCode)) {
			user = userService.login(user, true);
			setLoginSessionInfo(request, response, user);
			setSessionValue(request, User.BACKK_LOGIN, user.getUserName());
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
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());
		User user = (User) parserJsonParameters(request, true, User.class);
		responseWithEntity(userService.loadUserForAdmin(user), request, response);
	}

	@RequestMapping("/admin/edit.do")
	public void editUserForAdmin(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());
		User user = (User) parserJsonParameters(request, true, User.class);
		userService.editUserForAdmin(user);
		responseWithEntity(null, request, response);
	}

	@RequestMapping("/parent/submit.do")
	public void submitPersonInfo(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) parserJsonParameters(request, true, User.class);
		Student s = (Student) parserJsonParameters(request, true, Student.class);

		String birthDay = request.getParameter("birthDay");
		if (EweblibUtil.isValid(birthDay)) {
			s.setBirthday(birthDay);
		}

		user = userService.submitPersonInfo(user, s);
		setLoginSessionInfo(request, response, user);
		responseWithEntity(null, request, response);
	}

	@RequestMapping("/parent/mine.do")
	public void loadMyPersonInfo(HttpServletRequest request, HttpServletResponse response) {
		User user = userService.loadMyPersonInfo();
		responseWithEntity(user, request, response);
	}

	@RequestMapping("/parent/submitStudent.do")
	public void submitStudentInfo(HttpServletRequest request, HttpServletResponse response) {
		//Student person = (Student) parserJsonParameters(request, true, Student.class);

		throw new ResponseException("请在注册的时候填写学生信息");
//		userService.submitStudentInfo(person, false);
//		responseWithEntity(null, request, response);
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

	@RequestMapping("/admin/listStudent.do")
	public void listStudentsForAdmin(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());
		SearchVO uvo = (SearchVO) parserJsonParameters(request, true, SearchVO.class);

		responseWithDataPagnation(userService.listStudentsForAdmin(uvo), request, response);
	}

	@RequestMapping("/admin/student/delete.do")
	public void deleteStudentInfo(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());
		Student student = (Student) parserJsonParameters(request, true, Student.class);

		userService.deleteStudentInfo(student);
		responseWithEntity(null, request, response);

	}

	@RequestMapping("/admin/student/load.do")
	public void loadStudentInfo(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());
		Student student = (Student) parserJsonParameters(request, true, Student.class);

		responseWithEntity(userService.loadStudentInfo(student), request, response);

	}

	@RequestMapping("/admin/student/update.do")
	public void updateStudentInfo(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());
		Student student = (Student) parserJsonParameters(request, true, Student.class);
		userService.updateStudentInfo(student);
		responseWithEntity(null, request, response);
	}

	@RequestMapping("/admin/user/sum.do")
	public void sumtUserInfo(HttpServletRequest request, HttpServletResponse response) {

		responseWithMapData(userService.sumtUserInfo(), request, response);

	}

	@RequestMapping("/admin/student/export.do")
	public void exportStudentInfo(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());
		SearchVO uvo = (SearchVO) parserJsonParameters(request, true, SearchVO.class);
		String path = userService.exportStudentInfo(uvo);

		responseWithKeyValue("path", path, request, response);
	}

	@RequestMapping("/admin/import.do")
	public void importParentInfo(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile uploadFile = multipartRequest.getFile("file");
		String name = uploadFile.getOriginalFilename().toLowerCase().trim().replaceAll(" ", "");
		int index = name.lastIndexOf(".");
		if (index == -1) {
			throw new ResponseException("请上传xls和xlsx格式的文件");
		}
		String fType = name.substring(index, name.length());
		if (!fType.equals(".xls") && !fType.equals(".xlsx")) {
			throw new ResponseException("文件格式仅支持 xls和xlsx");
		}

		try {
			userService.importParentInfo(uploadFile.getInputStream());
		} catch (IOException e) {
			logger.fatal("文件导入失败", e);
			throw new ResponseException("文件导入失败，请稍后再试");
		}

		responseWithEntity(null, request, response);

	}

	@RequestMapping("/admin/student/import.do")
	public void importStudentInfo(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile uploadFile = multipartRequest.getFile("studentfile");
		String name = uploadFile.getOriginalFilename().toLowerCase().trim().replaceAll(" ", "");
		int index = name.lastIndexOf(".");
		if (index == -1) {
			throw new ResponseException("请上传xls或者xlsx格式的文件");
		}
		String fType = name.substring(index, name.length());
		if (!fType.equals(".xls") && !fType.equals(".xlsx")) {
			throw new ResponseException("文件格式仅支持xls和xlsx");
		}

		try {
			userService.importStudentInfo(uploadFile.getInputStream());
		} catch (IOException e) {
			logger.fatal("文件导入失败", e);
			throw new ResponseException("文件导入失败，请稍后再试");
		}

		responseWithEntity(null, request, response);

	}

	protected void setLoginSessionInfo(HttpServletRequest request, HttpServletResponse response, User user) {
		removeSessionInfo(request);
		EWeblibThreadLocal.set(BaseEntity.ID, user.getId());
		setSessionValue(request, User.USER_NAME, user.getUserName());
		setSessionValue(request, BaseEntity.ID, user.getId());

		Cookie cookie = new Cookie("baiHuaId", user.getId());
		cookie.setMaxAge(30 * 60);
		response.addCookie(cookie);
	}

}
