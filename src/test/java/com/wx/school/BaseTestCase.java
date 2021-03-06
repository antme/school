package com.wx.school;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.eweblib.dao.IQueryDao;
import com.eweblib.dao.QueryDaoImpl;
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

	public void testSubmitPersonInfo() throws FileNotFoundException {
//		SmsHelp.sendSchoolNoticeSms("2017-01-18", "09:10", "09:30", "徐汇校区", "徐汇区天钥桥路30号15楼15", "18516692298");

		 InputStream in = new FileInputStream(new
		 File("/Users/clp/downloads/2018年春季名单汇总 （已去重）.xls"));
		
		 us.importParentInfo(in);
		// us.exportStudentInfo(null);
		// schoolService.listStudentPlanForAdmin(null);
	}

	public void testInsertSchool() {
		// dao.deleteAllByTableName(School.TABLE_NAME);
		// dao.deleteAllByTableName(SchoolPlan.TABLE_NAME);
		// School school = new School();
		// school.setName("徐汇校区");
		// schoolService.addSchool(school);
		//
		// SchoolPlan plan = new SchoolPlan();
		// plan.setOnlyForVip(true);
		// plan.setTakeNumberDate(DateUtil.getDate("2017-01-01",
		// DateUtil.DATE_FORMAT));
		// plan.setStartTime("09:10:00");
		// plan.setEndTime("10:10:00");
		// plan.setName(school.getName());
		// plan.setSchoolId(school.getId());
		// this.dao.insert(plan);
		//
		// school = new School();
		// school.setName("陆家嘴校区");
		// schoolService.addSchool(school);
		//
		// plan = new SchoolPlan();
		// plan.setOnlyForVip(true);
		// plan.setTakeNumberDate(DateUtil.getDate("2016-12-28",
		// DateUtil.DATE_FORMAT));
		// plan.setStartTime("09:10:00");
		// plan.setEndTime("17:10:00");
		// plan.setName(school.getName());
		// plan.setSchoolId(school.getId());
		// this.dao.insert(plan);
		//
		// school = new School();
		// school.setName("普陀校区");
		// schoolService.addSchool(school);
		//
		// plan = new SchoolPlan();
		// plan.setOnlyForVip(false);
		// plan.setTakeNumberDate(DateUtil.getDate("2017-01-03",
		// DateUtil.DATE_FORMAT));
		// plan.setStartTime("14:00:00");
		// plan.setEndTime("15:00:00");
		// plan.setName(school.getName());
		// plan.setSchoolId(school.getId());
		// this.dao.insert(plan);

	}

}
