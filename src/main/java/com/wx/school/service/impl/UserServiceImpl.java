package com.wx.school.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eweblib.bean.vo.EntityResults;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.dbhelper.DataBaseQueryOpertion;
import com.eweblib.exception.ResponseException;
import com.eweblib.service.AbstractService;
import com.eweblib.util.DataEncrypt;
import com.eweblib.util.EWeblibThreadLocal;
import com.eweblib.util.EweblibUtil;
import com.wx.school.bean.SearchVO;
import com.wx.school.bean.school.StudentNumber;
import com.wx.school.bean.user.Student;
import com.wx.school.bean.user.User;
import com.wx.school.service.IUserService;
import com.wx.school.service.message.IMessageService;

@Service(value = "userService")
public class UserServiceImpl extends AbstractService implements IUserService {
	public static Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	private IMessageService ms;

	@Override
	public User login(User user, boolean back) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(User.TABLE_NAME);
		builder.and(User.PASSWORD, DataEncrypt.generatePassword(user.getPassword()));
		String userName = user.getUserName();
		if (EweblibUtil.isValid(user.getMobileNumber())) {
			userName = user.getMobileNumber();
		}
		builder.and(User.USER_NAME, userName);

		if (back) {
			builder.and(DataBaseQueryOpertion.IS_TRUE, User.IS_ADMIN);
		} else {
			builder.and(DataBaseQueryOpertion.IS_FALSE, User.IS_ADMIN);
		}

		if (!dao.exists(builder)) {
			throw new ResponseException("用户名或密码错误");
		}

		builder.limitColumns(new String[] { User.ID, User.USER_NAME, User.STATUS });
		return (User) dao.findOneByQuery(builder, User.class);

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

	public User loadUserForAdmin(User user) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(User.TABLE_NAME);
		query.limitColumns(new String[] { User.USER_NAME, User.NAME, User.ID, User.CREATED_ON });
		query.and(User.ID, user.getId());
		return (User) this.dao.findOneByQuery(query, User.class);
	}

	public void editUserForAdmin(User user) {

	}

	public User submitPersonInfo(User user) {

		if (EweblibUtil.isEmpty(user.getName())) {
			throw new ResponseException("姓名不能为空");
		}

		if (EweblibUtil.isEmpty(user.getMobileNumber())) {
			throw new ResponseException("手机号不能为空");
		}

		if (this.dao.exists(User.MOBILE_NUMBER, user.getMobileNumber(), User.TABLE_NAME)) {
			throw new ResponseException("此手机号已经注册");
		}

		if (EweblibUtil.isEmpty(user.getValidCode())) {
			throw new ResponseException("验证码不能为空");
		}

		checkPassword(user);
		ms.checkSms(user, 0);

		user.setUserName(user.getMobileNumber());
		user.setUserType(User.USER_TYPE_PARENT);
		user.setIsAdmin(false);
		
		this.dao.insert(user);

		return user;

	}

	private void checkPassword(User user) {
		if (EweblibUtil.isEmpty(user.getPassword())) {
			throw new ResponseException("密码不能为空");
		}

		if (!user.getPassword().equals(user.getPassword2())) {
			throw new ResponseException("两次密码不一致");
		}

		user.setPassword(DataEncrypt.generatePassword(user.getPassword()));
	}

	public User loadMyPersonInfo() {
		String uid = EWeblibThreadLocal.getCurrentUserId();
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(User.TABLE_NAME);
		query.limitColumns(new String[] { User.MOBILE_NUMBER, User.NAME });
		query.and(User.ID, uid);

		return this.dao.findOneByQuery(query, User.class);
	}

	public void submitStudentInfo(Student student) {
		if (EweblibUtil.isEmpty(student.getName())) {
			throw new ResponseException("姓名不能为空");
		}

		if (EweblibUtil.isEmpty(student.getSex())) {
			throw new ResponseException("性别不能为空");
		}

		if (EweblibUtil.isEmpty(student.getBirthday())) {
			throw new ResponseException("出生日期不能为空");
		}

		if (!student.getSex().equals("f") && !student.getSex().equals("m")) {
			throw new ResponseException("性别参数错误");
		}

		if (EweblibUtil.isEmpty(EWeblibThreadLocal.getCurrentUserId())) {
			throw new ResponseException("请先登录");
		}

		checkStudent(student, true);

		student.setOwnerId(EWeblibThreadLocal.getCurrentUserId());
		this.dao.insert(student);

	}

	private void checkStudent(Student student, boolean isNew) {
		DataBaseQueryBuilder checkQuery = new DataBaseQueryBuilder(Student.TABLE_NAME);
		checkQuery.and(Student.NAME, student.getName());
		checkQuery.and(Student.SEX, student.getSex());
		checkQuery.and(Student.BIRTH_DAY, student.getBirthday());
		if (!isNew) {
			checkQuery.and(DataBaseQueryOpertion.NOT_EQUALS, student.id, student.getId());

			if (EweblibUtil.isEmpty(student.getId())) {
				throw new ResponseException("非法参数");
			}
		}

		if (isNew) {
			DataBaseQueryBuilder checkCountQuery = new DataBaseQueryBuilder(Student.TABLE_NAME);
			checkCountQuery.and(Student.OWNER_ID, EWeblibThreadLocal.getCurrentUserId());

			if (this.dao.exists(checkCountQuery)) {
				throw new ResponseException("一个家长只能创建一个学生");
			}

		}
		if (this.dao.exists(checkQuery)) {
			throw new ResponseException("此学生信息已经提交");
		}
	}

	public List<Student> listStudentInfo() {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Student.TABLE_NAME);
		query.and(Student.OWNER_ID, EWeblibThreadLocal.getCurrentUserId());
		query.limitColumns(new String[] { Student.NAME, Student.BIRTH_DAY, Student.SEX, Student.ID });

		return this.dao.listByQuery(query, Student.class);
	}

	public void updateUserPassword(User user) {
		if (EweblibUtil.isEmpty(EWeblibThreadLocal.getCurrentUserId())) {
			throw new ResponseException("请先登录");
		}
		user.setId(EWeblibThreadLocal.getCurrentUserId());
		checkPassword(user);
		this.dao.updateById(user, new String[] { User.PASSWORD });

	}

	public void valideMobileNumber(User user) {
		if (EweblibUtil.isEmpty(user.getMobileNumber())) {
			throw new ResponseException("手机号码不能为空");
		}

		if (!this.dao.exists(User.MOBILE_NUMBER, user.getMobileNumber(), User.TABLE_NAME)) {
			throw new ResponseException("此手机号码未注册，请先注册");
		}
	}

	public User updateUserPasswordWhenForgot(User user) {
		valideMobileNumber(user);
		if (EweblibUtil.isEmpty(user.getValidCode())) {
			throw new ResponseException("验证码不能为空");
		}
		checkPassword(user);

		ms.checkSms(user, 1);
		User old = this.dao.findByKeyValue(User.MOBILE_NUMBER, user.getMobileNumber(), User.TABLE_NAME, User.class);
		user.setId(old.getId());
		this.dao.updateById(user, new String[] { User.PASSWORD });

		return user;
	}

	public EntityResults<Student> listStudentsForAdmin(SearchVO uvo) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Student.TABLE_NAME);

		query.leftJoin(Student.TABLE_NAME, User.TABLE_NAME, Student.OWNER_ID, User.ID);
		query.joinColumns(User.TABLE_NAME, new String[] { User.NAME + " as parentName",
				User.MOBILE_NUMBER + " as parentMobileNumber", User.CREATED_ON + " as parentCreatedOn" });

		query.limitColumns(new Student().getColumnList());

		if (EweblibUtil.isValid(uvo.getName())) {
			query.and(DataBaseQueryOpertion.LIKE, Student.NAME, uvo.getName());
		}

		if (EweblibUtil.isValid(uvo.getParentName())) {
			query.and(DataBaseQueryOpertion.LIKE, User.TABLE_NAME + "." + User.NAME, uvo.getParentName());
		}

		if (EweblibUtil.isValid(uvo.getMobileNumber())) {
			query.and(DataBaseQueryOpertion.LIKE, User.TABLE_NAME + "." + User.MOBILE_NUMBER, uvo.getMobileNumber());
		}

		return this.dao.listByQueryWithPagnation(query, Student.class);

	}

	public void deleteStudentInfo(Student student) {

		DataBaseQueryBuilder snQuery = new DataBaseQueryBuilder(StudentNumber.TABLE_NAME);
		snQuery.and(StudentNumber.STUDENT_ID, student.getId());

		this.dao.deleteByQuery(snQuery);

		this.dao.deleteById(student);

	}

	public Student loadStudentInfo(Student student) {

		return this.dao.findById(student.getId(), Student.TABLE_NAME, Student.class);
	}

	public void updateStudentInfo(Student student) {

		checkStudent(student, false);
		this.dao.updateById(student, new String[] { Student.NAME, Student.SEX, Student.BIRTH_DAY });
	}

	public Map<String, Object> sumtUserInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(User.TABLE_NAME);
		query.and(User.USER_TYPE, User.USER_TYPE_PARENT);
		int count = this.dao.count(query);

		map.put("parentCount", count);

		DataBaseQueryBuilder studentQuery = new DataBaseQueryBuilder(Student.TABLE_NAME);
		int scount = this.dao.count(studentQuery);

		map.put("studentCount", scount);

		return map;

	}

}
