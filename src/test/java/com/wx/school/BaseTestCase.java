package com.wx.school;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.eweblib.dao.IQueryDao;
import com.eweblib.dao.QueryDaoImpl;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.wx.school.bean.user.User;
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

	public void testEmpty() {
		
//		us.logout();
		
		List<User> list = dao.listByQuery(new DataBaseQueryBuilder(User.TABLE_NAME), User.class);
		System.out.println(list.size());
	}

}
