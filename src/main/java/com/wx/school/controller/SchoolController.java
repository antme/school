package com.wx.school.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eweblib.annotation.role.LoginRequired;
import com.eweblib.annotation.role.Permission;
import com.eweblib.controller.AbstractController;
import com.eweblib.util.EWeblibThreadLocal;
import com.wx.school.bean.SearchVO;
import com.wx.school.bean.plan.SchoolBaoMingPlan;
import com.wx.school.bean.school.School;
import com.wx.school.bean.school.SchoolPlan;
import com.wx.school.bean.school.StudentNumber;
import com.wx.school.service.ISchoolService;
import com.wx.school.service.IUserService;

@Controller
@RequestMapping("/sch")
@Permission()
@LoginRequired()
public class SchoolController extends AbstractController {

	@Autowired
	private ISchoolService schoolService;

	@Autowired
	private IUserService userService;

	@RequestMapping("/list.do")
	@LoginRequired(required = false)
	public void listSchools(HttpServletRequest request, HttpServletResponse response) {

		responseWithListData(schoolService.listSchoolPlan(), request, response);

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
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());

		SchoolPlan school = (SchoolPlan) parserJsonParameters(request, false, SchoolPlan.class);

		responseWithDataPagnation(schoolService.listSchoolPlanForAdmin(school), request, response);
	}

	@RequestMapping("/admin/add.do")
	@LoginRequired(required = false)
	public void addSchool(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());

		School school = (School) parserJsonParameters(request, false, School.class);
		schoolService.addSchool(school);
		responseWithEntity(null, request, response);

	}

	@RequestMapping("/admin/select.do")
	@LoginRequired(required = false)
	public void listSchoolsForAdmin(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());

		responseWithListData(schoolService.listSchoolsForAdmin(), request, response);
	}
	
	
	@RequestMapping("/admin/plan/add.do")
	@LoginRequired(required = false)
	public void addSchoolPlan(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());

		SchoolPlan plan = (SchoolPlan) parserJsonParameters(request, false, SchoolPlan.class);
		schoolService.addSchoolPlan(plan);
		responseWithEntity(null, request, response);

	}
	
	
	
	@RequestMapping("/admin/plan/delete.do")
	@LoginRequired(required = false)
	public void deleteSchoolPlan(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());

		SchoolPlan plan = (SchoolPlan) parserJsonParameters(request, false, SchoolPlan.class);
		schoolService.deleteSchoolPlan(plan);
		responseWithEntity(null, request, response);

	}
	
	
	
	@RequestMapping("/admin/plan/delivery.do")
	@LoginRequired(required = false)
	public void deliverySchoolPlan(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());

		SchoolPlan plan = (SchoolPlan) parserJsonParameters(request, false, SchoolPlan.class);
		schoolService.deliverySchoolPlan(plan);
		responseWithEntity(null, request, response);

	}
	
	@RequestMapping("/admin/plan/cancel.do")
	@LoginRequired(required = false)
	public void cancelSchoolPlan(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());

		SchoolPlan plan = (SchoolPlan) parserJsonParameters(request, false, SchoolPlan.class);
		schoolService.cancelSchoolPlan(plan);
		responseWithEntity(null, request, response);

	}
	
	@RequestMapping("/admin/plan/load.do")
	@LoginRequired(required = false)
	public void loadSchoolPlan(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());

		SchoolPlan plan = (SchoolPlan) parserJsonParameters(request, false, SchoolPlan.class);

		responseWithEntity(schoolService.loadSchoolPlan(plan), request, response);

	}
	
	
	@RequestMapping("/admin/plan/update.do")
	@LoginRequired(required = false)
	public void updateSchoolPlan(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());

		SchoolPlan plan = (SchoolPlan) parserJsonParameters(request, false, SchoolPlan.class);
		schoolService.updateSchoolPlan(plan);
		responseWithEntity(null, request, response);

	}
	
	@RequestMapping("/student/plan/list.do")
	@LoginRequired(required = false)
	public void listStudentPlanForAdmin(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());

		SearchVO svo = (SearchVO) parserJsonParameters(request, true, SearchVO.class);

		responseWithDataPagnation(schoolService.listStudentPlanForAdmin(svo), request, response);
	}
	
	@RequestMapping("/admin/plan/baoming/add.do")
	@LoginRequired(required = false)
	public void addSchoolBaomingPlan(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());

		SchoolBaoMingPlan plan = (SchoolBaoMingPlan) parserJsonParameters(request, false, SchoolBaoMingPlan.class);
		schoolService.addSchoolBaomingPlan(plan);
		responseWithEntity(null, request, response);

	}
	
	@RequestMapping("/admin/plan/baoming/update.do")
	@LoginRequired(required = false)
	public void updateSchoolBaomingPlan(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());

		SchoolBaoMingPlan plan = (SchoolBaoMingPlan) parserJsonParameters(request, false, SchoolBaoMingPlan.class);
		schoolService.updateSchoolBaomingPlan(plan);
		responseWithEntity(null, request, response);

	}
	
	@RequestMapping("/admin/plan/baoming/list.do")
	@LoginRequired(required = false)
	public void listSchoolBaomingPlanForAdmin(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());

		SearchVO svo = (SearchVO) parserJsonParameters(request, true, SearchVO.class);

		responseWithDataPagnation(schoolService.listSchoolBaomingPlanForAdmin(svo), request, response);
	}
	
	@RequestMapping("/admin/plan/baoming/delete.do")
	@LoginRequired(required = false)
	public void deleteSchoolBaomingPlan(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());

		SchoolBaoMingPlan plan = (SchoolBaoMingPlan) parserJsonParameters(request, false, SchoolBaoMingPlan.class);
		schoolService.deleteSchoolBaomingPlan(plan);
		responseWithEntity(null, request, response);

	}
	

	@RequestMapping("/admin/plan/baoming/load.do")
	@LoginRequired(required = false)
	public void loadSchoolBaomingPlan(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());

		SchoolBaoMingPlan plan = (SchoolBaoMingPlan) parserJsonParameters(request, false, SchoolBaoMingPlan.class);;

		responseWithEntity(schoolService.loadSchoolBaomingPlan(plan), request, response);

	}
	
	
	
	
	@RequestMapping("/student/number/export.do")
	@LoginRequired(required = false)
	public void exportStudentNumberForAdmin(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());

		SearchVO svo = (SearchVO) parserJsonParameters(request, true, SearchVO.class);

		String path = schoolService.exportStudentNumberForAdmin(svo);

		responseWithKeyValue("path", path, request, response);

	}
	
	
	
	@RequestMapping("/student/vip/sum.do")
	@LoginRequired(required = false)
	public void sumStudentVip(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());

		SearchVO svo = (SearchVO) parserJsonParameters(request, true, SearchVO.class);

		int count =  schoolService.sumStudentVip(svo);

		responseWithKeyValue("count", count, request, response);

	}
	
	@RequestMapping("/admin/student/plan/remark.do")
	@LoginRequired(required = false)
	public void loadStudentPlanRemark(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());
		StudentNumber number = (StudentNumber) parserJsonParameters(request, false, StudentNumber.class);

		responseWithEntity(schoolService.loadStudentPlanRemark(number), request, response);

	}
	
	@RequestMapping("/admin/student/plan/remark/update.do")
	@LoginRequired(required = false)
	public void updateStudentPlanRemark(HttpServletRequest request, HttpServletResponse response) {
		userService.validAdmin(EWeblibThreadLocal.getCurrentUserId());
		StudentNumber number = (StudentNumber) parserJsonParameters(request, false, StudentNumber.class);
		schoolService.updateStudentPlanRemark(number);
		responseWithEntity(null, request, response);

	}
	
	
	


}
