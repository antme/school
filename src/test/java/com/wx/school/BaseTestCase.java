package com.wx.school;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.eweblib.dao.IQueryDao;
import com.eweblib.dao.QueryDaoImpl;
import com.wx.school.bean.user.Person;
import com.wx.school.service.IUserService;
import com.wx.school.service.impl.UserServiceImpl;

import junit.framework.TestCase;

public class BaseTestCase extends TestCase {
	protected static ApplicationContext ac;

	public IQueryDao dao;
	
	public IUserService us;

	public BaseTestCase() {

		if (ac == null) {
			ac = new FileSystemXmlApplicationContext("/src/main/resources/WEB-INF/applicationContext.xml");
		}

		dao = ac.getBean(QueryDaoImpl.class);
		us = ac.getBean(UserServiceImpl.class);
	
	}

	public void testSubmitPersonInfo() {
		Person p = new Person();
		p.setName("刘德华");
		p.setMobileNumber("13818638561");
//		us.submitPersonInfo(p);
	}

}
