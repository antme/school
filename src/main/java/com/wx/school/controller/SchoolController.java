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
import com.eweblib.util.ImgUtil;
import com.wx.school.bean.school.School;
import com.wx.school.bean.user.SMS;
import com.wx.school.service.ISchoolService;

@Controller
@RequestMapping("/school")
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
		school.setTakeNumberDate(DateUtil.getDate("2017-01-02", DateUtil.DATE_FORMAT));
		school.setStartTime("09:10:00");
		school.setEndTime("10:10:00");
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

}
