package com.wx.school;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.eweblib.dao.IQueryDao;
import com.eweblib.dao.QueryDaoImpl;
import com.eweblib.util.DateUtil;
import com.wx.school.bean.school.School;
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
		Student p = new Student();
		p.setName("刘德华");
//		us.submitPersonInfo(p);
	}
	
	
	public void testInsertSchool(){
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
		
	}

}
