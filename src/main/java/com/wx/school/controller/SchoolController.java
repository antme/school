package com.wx.school.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eweblib.annotation.role.LoginRequired;
import com.eweblib.annotation.role.Permission;
import com.eweblib.controller.AbstractController;
import com.eweblib.dao.IQueryDao;
import com.eweblib.util.DateUtil;
import com.wx.school.bean.school.School;
import com.wx.school.bean.school.StudentNumber;
import com.wx.school.bean.user.User;
import com.wx.school.service.ISchoolService;

@Controller
@RequestMapping("/sch")
@Permission()
@LoginRequired()
public class SchoolController extends AbstractController {

	@Autowired
	private ISchoolService schoolService;

	@Autowired
	private IQueryDao dao;

	@RequestMapping("/list.do")
	@LoginRequired(required = false)
	public void listSchools(HttpServletRequest request, HttpServletResponse response) {

		responseWithListData(schoolService.listSchools(), request, response);

	}

	@RequestMapping("/initial.do")
	@LoginRequired(required = false)
	public void intialSchool(HttpServletRequest request, HttpServletResponse response) {
		dao.deleteAllByTableName(School.TABLE_NAME);
		School school = new School();
		school.setName("徐汇校区");
		school.setOnlyForVip(true);
		school.setTakeNumberDate(DateUtil.getDate("2017-01-01", DateUtil.DATE_FORMAT));
		school.setStartTime("09:10:00");
		school.setEndTime("10:10:00");
		schoolService.addSchool(school);

		school = new School();
		school.setName("陆家嘴校区");
		school.setOnlyForVip(true);
		school.setTakeNumberDate(DateUtil.getDate("2016-12-28", DateUtil.DATE_FORMAT));
		school.setStartTime("09:10:00");
		school.setEndTime("17:10:00");
		schoolService.addSchool(school);

		school = new School();
		school.setName("普陀校区");
		school.setOnlyForVip(false);
		school.setTakeNumberDate(DateUtil.getDate("2017-01-03", DateUtil.DATE_FORMAT));
		school.setStartTime("14:00:00");
		school.setEndTime("15:00:00");
		schoolService.addSchool(school);

		responseWithEntity(null, request, response);

	}

	@RequestMapping("/book.do")
	@LoginRequired(required = false)
	public void bookSchool(HttpServletRequest request, HttpServletResponse response) {
		StudentNumber sn = (StudentNumber) parserJsonParameters(request, true, StudentNumber.class);
		sn = schoolService.bookSchool(sn);
		responseWithEntity(sn, request, response);

	}

	@RequestMapping("/student_school/mine.do")
	@LoginRequired(required = false)
	public void listMyStudentSchools(HttpServletRequest request, HttpServletResponse response) {

		responseWithListData(schoolService.listMyStudentSchools(), request, response);

	}

	@RequestMapping("/student/avaliable/mine.do")
	@LoginRequired(required = false)
	public void listMyAvaliableStudentForSchool(HttpServletRequest request, HttpServletResponse response) {
		responseWithListData(schoolService.listMyAvaliableStudentForSchool(), request, response);
	}

	@RequestMapping("/admin/list.do")
	@LoginRequired(required = false)
	public void listSchoolsForAdmin(HttpServletRequest request, HttpServletResponse response) {
		School school = (School) parserJsonParameters(request, false, School.class);

		responseWithDataPagnation(schoolService.listSchoolsForAdmin(school), request, response);
	}
	
	
	@RequestMapping("/admin/add.do")
	@LoginRequired(required = false)
	public void addSchool(HttpServletRequest request, HttpServletResponse response) {
		School school = (School) parserJsonParameters(request, false, School.class);
		schoolService.addSchool(school);
		responseWithEntity(null, request, response);

	}

}