package com.wx.school.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.dbhelper.DataBaseQueryOpertion;
import com.eweblib.exception.ResponseException;
import com.eweblib.service.AbstractService;
import com.eweblib.util.DataEncrypt;
import com.eweblib.util.EWeblibThreadLocal;
import com.eweblib.util.EweblibUtil;
import com.wx.school.bean.user.Person;
import com.wx.school.bean.user.User;
import com.wx.school.service.IUserService;

@Service(value = "userService")
public class UserServiceImpl extends AbstractService implements IUserService {
	public static Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Override
	public User login(User user) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(User.PASSWORD, DataEncrypt.generatePassword(user.getPassword()));
		builder.and(User.USER_NAME, user.getUserName());

		if (!dao.exists(builder)) {
			throw new ResponseException("用户名或密码错误");
		}

		builder.limitColumns(new String[] { user.ID, User.USER_NAME, User.STATUS });
		User u = (User) dao.findOneByQuery(builder, User.class);

		// if (u.getStatus()!=null && u.getStatus() == 0) {
		// throw new ResponseException("你没有登录权限，请联系管理员");
		// }

		// if (fromApp) {
		// sys.createMsgLog(u.getId(), "从手机端登录");
		// } else {
		// sys.createMsgLog(u.getId(), "从后台登录");
		// }
		EWeblibThreadLocal.set(User.ID, u.getId());

		return u;
	}

	@Override
	public void logout() {

		System.out.println("logout");
		// sys.createMsgLog(EWeblibThreadLocal.getCurrentUserId(), "登出后台系统");
	}

	public boolean isCloudAdmin() {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(User.TABLE_NAME);
		query.and(User.ID, EWeblibThreadLocal.getCurrentUserId());
		query.limitColumns(new String[] { User.USER_NAME });

		return isAdmin(EWeblibThreadLocal.getCurrentUserId())
				&& ((User) this.dao.findOneByQuery(query, User.class)).getUserName().equalsIgnoreCase("winasdaqcloud");
	}

	public boolean isAdmin(String userId) {

		if (EweblibUtil.isEmpty(userId)) {
			return false;
		}

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(User.TABLE_NAME);
		query.and(User.ID, userId);
		query.and(DataBaseQueryOpertion.IS_TRUE, User.IS_ADMIN);
		if (this.dao.exists(query)) {
			return true;
		}
		return false;
	}

	public void validateRegUser(String userName) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(User.TABLE_NAME);
		query.and(User.USER_NAME, userName);

		if (this.dao.exists(query)) {
			throw new ResponseException("用户已经存在");
		}
	}

	public List<User> selectWifiCloudUsers() {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(User.TABLE_NAME);
		query.limitColumns(new String[] { User.ID, User.USER_NAME });
		// query.and(DataBaseQueryOpertion.NOT_NULL, User.WCUID);

		return this.dao.listByQuery(query, User.class);
	}

	public User loadUserForAdmin(User user) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(User.TABLE_NAME);
		query.limitColumns(new String[] { User.USER_NAME, User.NAME, User.ID, User.CREATED_ON });
		query.and(User.ID, user.getId());
		return (User) this.dao.findOneByQuery(query, User.class);
	}

	public void editUserForAdmin(User user) {

	}

	public User submitPersonInfo(Person person) {

		if (EweblibUtil.isEmpty(person.getName())) {
			throw new ResponseException("姓名不能为空");
		}

		if (EweblibUtil.isEmpty(person.getName())) {
			throw new ResponseException("手机号不能为空");
		}

		if (this.dao.exists(Person.MOBILE_NUMBER, person.getMobileNumber(), Person.TABLE_NAME)) {
			throw new ResponseException("此手机号已经注册");
		}

		User user = new User();
		user.setMobileNumber(person.getMobileNumber());
		user.setName(person.getName());
		user.setUserName(person.getMobileNumber());
		this.dao.insert(user);

		person.setPersonType(Person.PERSON_TYPE_PARENT);
		person.setOwnerId(user.getId());
		this.dao.insert(person);
		
		return user;

	}
	
	public Person loadMyPersonInfo() {
		String uid = EWeblibThreadLocal.getCurrentUserId();
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Person.TABLE_NAME);
		query.limitColumns(new String[] { Person.MOBILE_NUMBER, Person.NAME });
		query.and(Person.OWNER_ID, uid);

		return this.dao.findOneByQuery(query, Person.class);
	}

	@Override
	public User regWifiCloudUser(String wcuname, String name, String serverIp) {
		// TODO Auto-generated method stub
		return null;
	}

}
