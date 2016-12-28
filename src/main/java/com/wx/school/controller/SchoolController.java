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
import com.wx.school.bean.SearchVO;
import com.wx.school.bean.school.School;
import com.wx.school.bean.school.SchoolPlan;
import com.wx.school.bean.school.StudentNumber;
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

		responseWithListData(schoolService.listSchoolPlan(), request, response);

	}

	@RequestMapping("/initial.do")
	@LoginRequired(required = false)
	public void intialSchool(HttpServletRequest request, HttpServletResponse response) {
		dao.deleteAllByTableName(School.TABLE_NAME);
		School school = new School();
		school.setName("徐汇校区");
		schoolService.addSchool(school);

		SchoolPlan plan = new SchoolPlan();
		plan.setOnlyForVip(true);
		plan.setTakeNumberDate(DateUtil.getDate("2017-01-01", DateUtil.DATE_FORMAT));
		plan.setStartTime("09:10:00");
		plan.setEndTime("10:10:00");
		plan.setName(school.getName());
		plan.setSchoolId(school.getId());
		this.dao.insert(plan);

		school = new School();
		school.setName("陆家嘴校区");
		schoolService.addSchool(school);

		plan = new SchoolPlan();
		plan.setOnlyForVip(true);
		plan.setTakeNumberDate(DateUtil.getDate("2016-12-28", DateUtil.DATE_FORMAT));
		plan.setStartTime("09:10:00");
		plan.setEndTime("17:10:00");
		plan.setName(school.getName());
		plan.setSchoolId(school.getId());
		this.dao.insert(plan);

		school = new School();
		school.setName("普陀校区");
		schoolService.addSchool(school);

		plan = new SchoolPlan();
		plan.setOnlyForVip(false);
		plan.setTakeNumberDate(DateUtil.getDate("2017-01-03", DateUtil.DATE_FORMAT));
		plan.setStartTime("14:00:00");
		plan.setEndTime("15:00:00");
		plan.setName(school.getName());
		plan.setSchoolId(school.getId());
		this.dao.insert(plan);

		responseWithEntity(null, request, response);

	}

	@RequestMapping("/book.do")
	@LoginRequired(required = false)
	public void bookSchool(HttpServletRequest request, HttpServletResponse response) {
		StudentNumber sn = (StudentNumber) parserJsonParameters(request, true, StudentNumber.class);
		//前端传递参数为schoolId,其实是plan id
		sn.setPlanId(sn.getSchoolId());
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

	@RequestMapping("/admin/plan/list.do")
	@LoginRequired(required = false)
	public void listSchoolPlanForAdmin(HttpServletRequest request, HttpServletResponse response) {
		SchoolPlan school = (SchoolPlan) parserJsonParameters(request, false, SchoolPlan.class);

		responseWithDataPagnation(schoolService.listSchoolPlanForAdmin(school), request, response);
	}

	@RequestMapping("/admin/add.do")
	@LoginRequired(required = false)
	public void addSchool(HttpServletRequest request, HttpServletResponse response) {
		School school = (School) parserJsonParameters(request, false, School.class);
		schoolService.addSchool(school);
		responseWithEntity(null, request, response);

	}

	@RequestMapping("/admin/select.do")
	@LoginRequired(required = false)
	public void listSchoolsForAdmin(HttpServletRequest request, HttpServletResponse response) {

		responseWithListData(schoolService.listSchoolsForAdmin(), request, response);
	}
	
	
	@RequestMapping("/admin/plan/add.do")
	@LoginRequired(required = false)
	public void addSchoolPlan(HttpServletRequest request, HttpServletResponse response) {
		SchoolPlan plan = (SchoolPlan) parserJsonParameters(request, false, SchoolPlan.class);
		schoolService.addSchoolPlan(plan);
		responseWithEntity(null, request, response);

	}
	
	
	
	@RequestMapping("/admin/plan/delete.do")
	@LoginRequired(required = false)
	public void deleteSchoolPlan(HttpServletRequest request, HttpServletResponse response) {
		SchoolPlan plan = (SchoolPlan) parserJsonParameters(request, false, SchoolPlan.class);
		schoolService.deleteSchoolPlan(plan);
		responseWithEntity(null, request, response);

	}
	
	
	
	@RequestMapping("/admin/plan/delivery.do")
	@LoginRequired(required = false)
	public void deliverySchoolPlan(HttpServletRequest request, HttpServletResponse response) {
		SchoolPlan plan = (SchoolPlan) parserJsonParameters(request, false, SchoolPlan.class);
		schoolService.deliverySchoolPlan(plan);
		responseWithEntity(null, request, response);

	}
	
	@RequestMapping("/admin/plan/cancel.do")
	@LoginRequired(required = false)
	public void cancelSchoolPlan(HttpServletRequest request, HttpServletResponse response) {
		SchoolPlan plan = (SchoolPlan) parserJsonParameters(request, false, SchoolPlan.class);
		schoolService.cancelSchoolPlan(plan);
		responseWithEntity(null, request, response);

	}
	
	@RequestMapping("/admin/plan/load.do")
	@LoginRequired(required = false)
	public void loadSchoolPlan(HttpServletRequest request, HttpServletResponse response) {
		SchoolPlan plan = (SchoolPlan) parserJsonParameters(request, false, SchoolPlan.class);

		responseWithEntity(schoolService.loadSchoolPlan(plan), request, response);

	}
	
	
	@RequestMapping("/admin/plan/update.do")
	@LoginRequired(required = false)
	public void updateSchoolPlan(HttpServletRequest request, HttpServletResponse response) {
		SchoolPlan plan = (SchoolPlan) parserJsonParameters(request, false, SchoolPlan.class);
		schoolService.updateSchoolPlan(plan);
		responseWithEntity(null, request, response);

	}
	
	@RequestMapping("/student/plan/list.do")
	@LoginRequired(required = false)
	public void listStudentPlanForAdmin(HttpServletRequest request, HttpServletResponse response) {
		SearchVO svo = (SearchVO) parserJsonParameters(request, false, SearchVO.class);

		responseWithDataPagnation(schoolService.listStudentPlanForAdmin(svo), request, response);
	}
	
	


}
