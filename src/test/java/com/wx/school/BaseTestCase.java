package com.wx.school;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.eweblib.dao.IQueryDao;
import com.eweblib.dao.QueryDaoImpl;
import com.eweblib.util.DateUtil;
import com.wx.school.bean.school.School;
import com.wx.school.bean.school.SchoolPlan;
import com.wx.school.bean.user.Student;
import com.wx.school.service.ISchoolService;
import com.wx.school.service.IUserService;
import com.wx.school.service.impl.SchoolServiceImpl;
import com.wx.school.service.impl.UserServiceImpl;

import junit.framework.TestCase;

public class BaseTestCase extends TestCase {
	protected static ApplicationContext ac;

	public IQueryDao dao;
	
	public IUserService us;
	
	public ISchoolService schoolService;

	public BaseTestCase() {

		if (ac == null) {
			ac = new FileSystemXmlApplicationContext("/src/main/resources/WEB-INF/applicationContext.xml");
		}

		dao = ac.getBean(QueryDaoImpl.class);
		us = ac.getBean(UserServiceImpl.class);
		schoolService = ac.getBean(SchoolServiceImpl.class);
	
	}

	public void testSubmitPersonInfo() {
		schoolService.listStudentPlanForAdmin(null);
	}
	
	
	public void testInsertSchool(){
//		dao.deleteAllByTableName(School.TABLE_NAME);
//		dao.deleteAllByTableName(SchoolPlan.TABLE_NAME);
//		School school = new School();
//		school.setName("徐汇校区");
//		schoolService.addSchool(school);
//
//		SchoolPlan plan = new SchoolPlan();
//		plan.setOnlyForVip(true);
//		plan.setTakeNumberDate(DateUtil.getDate("2017-01-01", DateUtil.DATE_FORMAT));
//		plan.setStartTime("09:10:00");
//		plan.setEndTime("10:10:00");
//		plan.setName(school.getName());
//		plan.setSchoolId(school.getId());
//		this.dao.insert(plan);
//
//		school = new School();
//		school.setName("陆家嘴校区");
//		schoolService.addSchool(school);
//
//		plan = new SchoolPlan();
//		plan.setOnlyForVip(true);
//		plan.setTakeNumberDate(DateUtil.getDate("2016-12-28", DateUtil.DATE_FORMAT));
//		plan.setStartTime("09:10:00");
//		plan.setEndTime("17:10:00");
//		plan.setName(school.getName());
//		plan.setSchoolId(school.getId());
//		this.dao.insert(plan);
//
//		school = new School();
//		school.setName("普陀校区");
//		schoolService.addSchool(school);
//
//		plan = new SchoolPlan();
//		plan.setOnlyForVip(false);
//		plan.setTakeNumberDate(DateUtil.getDate("2017-01-03", DateUtil.DATE_FORMAT));
//		plan.setStartTime("14:00:00");
//		plan.setEndTime("15:00:00");
//		plan.setName(school.getName());
//		plan.setSchoolId(school.getId());
//		this.dao.insert(plan);

		
	}

}
