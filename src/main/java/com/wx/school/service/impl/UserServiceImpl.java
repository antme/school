package com.wx.school.service.impl;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eweblib.bean.vo.EntityResults;
import com.eweblib.cfg.ConfigManager;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.dbhelper.DataBaseQueryOpertion;
import com.eweblib.exception.ResponseException;
import com.eweblib.service.AbstractService;
import com.eweblib.util.DataEncrypt;
import com.eweblib.util.DateUtil;
import com.eweblib.util.EWeblibThreadLocal;
import com.eweblib.util.EweblibUtil;
import com.eweblib.util.ExcelUtil;
import com.wx.school.bean.SearchVO;
import com.wx.school.bean.school.School;
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
		}

		builder.limitColumns(new String[] { User.ID, User.USER_NAME, User.STATUS });
		User old = (User) dao.findOneByQuery(builder, User.class);

		if (old == null) {
			throw new ResponseException("用户名或密码错误");
		}

		return old;
	}

	public void validAdmin(String userId) {

		if (EweblibUtil.isEmpty(userId)) {
			throw new ResponseException("非法参数");
		}

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(User.TABLE_NAME);
		query.and(User.ID, userId);
		query.and(DataBaseQueryOpertion.IS_TRUE, User.IS_ADMIN);
		if (!this.dao.exists(query)) {
			throw new ResponseException("无权限操作");
		}

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

	public User submitPersonInfo(User user, Student student) {

		student.setName(student.getStudentName());
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

		Student s = submitStudentInfo(student, true);
		
		
		user.setUserName(user.getMobileNumber());
		user.setUserType(User.USER_TYPE_PARENT);
		user.setIsAdmin(false);
		this.dao.insert(user);
		
		student.setOwnerId(user.getId());
		student.setId(s.getId());
		student.setCreatedOn(new Date());
		this.dao.updateById(student, new String[] { Student.CREATED_ON, Student.BIRTH_DAY, Student.OWNER_ID });

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

	public Student submitStudentInfo(Student student, boolean isReg) {
		if (EweblibUtil.isEmpty(student.getName())) {
			throw new ResponseException("学生姓名不能为空");
		}

		if (EweblibUtil.isEmpty(student.getSex())) {
			throw new ResponseException("学生性别不能为空");
		}

		if (EweblibUtil.isEmpty(student.getBirthday())) {
			throw new ResponseException("出生日期不能为空");
		}

		if (!student.getSex().equals("f") && !student.getSex().equals("m")) {
			throw new ResponseException("性别参数错误");
		}

		if (!isReg) {
			if (EweblibUtil.isEmpty(EWeblibThreadLocal.getCurrentUserId())) {
				throw new ResponseException("请先登录");
			}
		}

		Student s = checkStudent(student, true, false);
		return s;
		
	
		// this.dao.insert(student);
	}

	private Student checkStudent(Student student, boolean isNew, boolean isEdit) {
		DataBaseQueryBuilder checkQuery = new DataBaseQueryBuilder(Student.TABLE_NAME);
		checkQuery.and(Student.NAME, student.getName());
		checkQuery.and(Student.SEX, student.getSex());

		// checkQuery.and(Student.BIRTH_DAY, student.getBirthday());

		Calendar c = Calendar.getInstance();
		c.setTime(student.getBirthday());

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;

		checkQuery.and(Student.BIRDARY_YEAR, year);
		checkQuery.and(Student.BIRDARY_MONTH, month);

		if (isNew) {
			String currentUserId = EWeblibThreadLocal.getCurrentUserId();
			if (EweblibUtil.isValid(currentUserId)) {
				DataBaseQueryBuilder checkCountQuery = new DataBaseQueryBuilder(Student.TABLE_NAME);
				checkCountQuery.and(Student.OWNER_ID, currentUserId);

				if (this.dao.exists(checkCountQuery)) {
					throw new ResponseException("一个家长只能创建一个学生");
				}
			}
		}
		Student s = null;
		if (isEdit) {

			if (EweblibUtil.isEmpty(student.getId())) {
				throw new ResponseException("非法参数");
			}
			checkQuery.and(DataBaseQueryOpertion.NOT_EQUALS, Student.ID, student.getId());

			s = this.dao.findOneByQuery(checkQuery, Student.class);
			if (s != null) {
				throw new ResponseException("提交的学生信息有重复");
			}

		} else {
			s = this.dao.findOneByQuery(checkQuery, Student.class);
			if (s == null) {
				throw new ResponseException("此学生信息不存在");
			}
		}
		return s;
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
		DataBaseQueryBuilder query = getStudentSearchQuery(uvo);

		return this.dao.listByQueryWithPagnation(query, Student.class);

	}

	private DataBaseQueryBuilder getStudentSearchQuery(SearchVO uvo) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Student.TABLE_NAME);

		query.leftJoin(Student.TABLE_NAME, User.TABLE_NAME, Student.OWNER_ID, User.ID);
		query.joinColumns(User.TABLE_NAME, new String[] { User.NAME + " as parentName",
				User.MOBILE_NUMBER + " as parentMobileNumber", User.CREATED_ON + " as parentCreatedOn" });

		query.limitColumns(new Student().getColumnList());

		if (uvo != null) {
			if (EweblibUtil.isValid(uvo.getName())) {
				query.and(DataBaseQueryOpertion.LIKE, Student.NAME, uvo.getName());
			}

			if (EweblibUtil.isValid(uvo.getParentName())) {
				query.and(DataBaseQueryOpertion.LIKE, User.TABLE_NAME + "." + User.NAME, uvo.getParentName());
			}

			if (EweblibUtil.isValid(uvo.getMobileNumber())) {
				query.and(DataBaseQueryOpertion.LIKE, User.TABLE_NAME + "." + User.MOBILE_NUMBER,
						uvo.getMobileNumber());
			}

			if (EweblibUtil.isValid(uvo.getRemark())) {
				query.and(DataBaseQueryOpertion.LIKE, Student.TABLE_NAME + "." + Student.REMARK, uvo.getRemark());
			}
		}
		return query;
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

		checkStudent(student, false, true);
		this.dao.updateById(student, new String[] { Student.NAME, Student.SEX, Student.BIRTH_DAY, Student.REMARK });
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

	public String exportStudentInfo(SearchVO uvo) {
		DataBaseQueryBuilder query = getStudentSearchQuery(uvo);
		List<Student> list = this.dao.listByQuery(query, Student.class);
		for (Student s : list) {
			if (s.getSex().equalsIgnoreCase("m")) {
				s.setSexCn("男性");
			} else {
				s.setSexCn("女性");
			}
		}
		String dowload_path = "学生_" + DateUtil.getDateString(new Date()) + ".xls";
		String f = ConfigManager.getProperty("download_path") + dowload_path;
		String[] colunmTitleHeaders = new String[] { "姓名", "性别", "出生日期", "家长姓名", "家长手机", "家长注册时间", "学生注册时间", "备注" };
		String[] colunmHeaders = new String[] { "name", "sexCn", "birthday", "parentName", "parentMobileNumber",
				"parentCreatedOn", "createdOn", "remark" };
		ExcelUtil.createExcelListFileByEntity(list, colunmTitleHeaders, colunmHeaders, f);

		return dowload_path;
	}

	public void importParentInfo(InputStream inputStream) {

		ExcelUtil excleUtil = new ExcelUtil(inputStream);
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Student.TABLE_NAME);
		query.limitColumns(new String[] { Student.ID, Student.NAME, Student.IS_VIP });

		List<Student> userList = this.dao.listByQuery(query, Student.class);

		for (int index = 0; index < excleUtil.getNumberOfSheets(); index++) {

			List<String[]> list = excleUtil.getAllData(index);

			if (list.isEmpty()) {
				continue;
			}

			for (int i = 0; i < list.size(); i++) {// 从第2行开始读数据

				String[] row = list.get(i);

				String name = row[0].trim();
				for (Student student : userList) {
					if (student.getName().trim().equals(name.trim())) {
						student.setIsVip(true);
						this.dao.updateById(student, new String[] { Student.IS_VIP });
					}
				}

			}
		}

	}

	public void importStudentInfo(InputStream inputStream) {
		ExcelUtil excleUtil = new ExcelUtil(inputStream);
		Map<String, School> schoolMap = new HashMap<String, School>();

		for (int index = 0; index < excleUtil.getNumberOfSheets(); index++) {

			List<String[]> list = excleUtil.getAllData(index);

			if (list.isEmpty()) {
				continue;
			}

			logger.info(excleUtil.getSheetName(index) + ":" + list.size());
			for (int i = 1; i < list.size(); i++) {// 从第2行开始读数据

				String[] row = list.get(i);

				String name = row[0].trim();
				String sex = row[1].trim();
				String birthdaryStr = row[2].trim();
				String schoolName = row[3].trim();
				String remark = null;

				if (row.length > 4) {
					remark = row[4].trim();
				}

				if (EweblibUtil.isEmpty(name)) {
					throw new ResponseException("第" + (i + 1) + "行的学生姓名不能为空");
				}

				if (EweblibUtil.isEmpty(sex)) {
					throw new ResponseException("第" + (i + 1) + "行的学生性别不能为空");
				}

				if (EweblibUtil.isEmpty(birthdaryStr)) {
					throw new ResponseException("第" + (i + 1) + "行的学生出生日期不能为空");
				}

				if (EweblibUtil.isEmpty(schoolName)) {
					throw new ResponseException("第" + (i + 1) + "行的学生校区不能为空");
				}

				Date birthDay = DateUtil.getDate(birthdaryStr, "yyyy/MM/dd");

				if (birthDay == null) {

					birthDay = DateUtil.getDate(birthdaryStr, "dd/MM/yyyy");
				}

				if (birthDay == null) {
					throw new ResponseException("第" + (i + 1) + "行的学生出生日期格式不正确，请确保为yyyy/mm/dd 或者 dd/MM/yyyy");
				}

				Calendar c = Calendar.getInstance();
				c.setTime(birthDay);

				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH) + 1;

				String sexEn = null;
				if (sex.equalsIgnoreCase("男")) {
					sexEn = "m";
				} else if (sex.equalsIgnoreCase("女")) {
					sexEn = "f";
				}

				if (EweblibUtil.isEmpty(sexEn)) {
					throw new ResponseException("第" + (i + 1) + "行的学生性别填写错误");
				}
				School school = schoolMap.get(schoolName);

				if (school == null) {
					school = this.dao.findByKeyValue(School.NAME, schoolName, School.TABLE_NAME, School.class);
					if (school != null) {
						schoolMap.put(schoolName, school);
					}
				}

				if (school == null) {
					if (name.contains("校区")) {
						throw new ResponseException(schoolName + "  不存在,请先创建");
					} else {
						throw new ResponseException(schoolName + "  校区不存在,请先创建");
					}
				}
				Student student = new Student();
				student.setName(name);
				student.setSex(sexEn);
				student.setBirthday(birthDay);
				student.setIsVip(true);
				student.setRemark(remark);
				student.setBirdaryMonth(month);
				student.setBirdaryYear(year);
				student.setSchoolId(school.getId());

				DataBaseQueryBuilder query = new DataBaseQueryBuilder(Student.TABLE_NAME);
				query.and(Student.NAME, name);
				query.and(Student.SEX, sexEn);
				query.and(Student.BIRDARY_YEAR, year);
				query.and(Student.BIRDARY_MONTH, month);

				Student old = this.dao.findOneByQuery(query, Student.class);
				if (old != null) {
					student.setId(old.getId());
					this.dao.updateById(student, new String[] { Student.SCHOOL_ID, Student.BIRTH_DAY, Student.REMARK });
				} else {
					this.dao.insert(student);
				}

			}
		}
	}

}
