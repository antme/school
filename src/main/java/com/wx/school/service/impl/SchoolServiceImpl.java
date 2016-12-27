package com.wx.school.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.eweblib.bean.vo.EntityResults;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.dbhelper.DataBaseQueryOpertion;
import com.eweblib.exception.ResponseException;
import com.eweblib.service.AbstractService;
import com.eweblib.util.DateUtil;
import com.eweblib.util.EWeblibThreadLocal;
import com.eweblib.util.EweblibUtil;
import com.wx.school.bean.school.School;
import com.wx.school.bean.school.StudentNumber;
import com.wx.school.bean.school.StudentSchoolInfo;
import com.wx.school.bean.user.Student;
import com.wx.school.bean.user.User;
import com.wx.school.service.ISchoolService;

@Service(value = "school")
public class SchoolServiceImpl extends AbstractService implements ISchoolService {

	@Override
	public List<School> listSchools() {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(School.TABLE_NAME);
		query.limitColumns(new String[] { School.ID, School.NAME, School.ONLY_FOR_VIP, School.TAKE_NUMBER_DATE,
				School.START_TIME, School.END_TIME });

		List<School> list = this.dao.listByQuery(query, School.class);
		for (School school : list) {
			Date date = new Date();

			Date startDate = DateUtil
					.getDateTime(DateUtil.getDateString(school.getTakeNumberDate()) + " " + school.getStartTime());
			Date endDate = DateUtil
					.getDateTime(DateUtil.getDateString(school.getTakeNumberDate()) + " " + school.getEndTime());

			if (date.getTime() < startDate.getTime()) {
				school.setTakeStatus(0);
			} else if (date.getTime() < endDate.getTime()) {
				school.setTakeStatus(1);
			} else {
				school.setTakeStatus(2);
			}

		}

		return list;
	}

	public void addSchool(School school) {

		if (EweblibUtil.isEmpty(school.getName())) {
			throw new ResponseException("校区名字不能为空");
		}

		if (this.dao.exists(School.NAME, school.getName(), School.TABLE_NAME)) {
			throw new ResponseException("此校区已经存在");
		}
		this.dao.insert(school);
	}

	public StudentNumber bookSchool(StudentNumber sn) {
		if (EweblibUtil.isEmpty(sn.getStudentId())) {
			throw new ResponseException("请选择选号学生");
		}

		if (EweblibUtil.isEmpty(sn.getSchoolId())) {
			throw new ResponseException("请选择校区");
		}

		if (this.dao.exists(StudentNumber.STUDENT_ID, sn.getStudentId(), StudentNumber.TABLE_NAME)) {
			throw new ResponseException("此学生已经选择校区");
		}

		if (!this.dao.exists(Student.ID, sn.getStudentId(), Student.TABLE_NAME)) {
			throw new ResponseException("此学生不存在");
		}

		if (!this.dao.exists(School.ID, sn.getSchoolId(), School.TABLE_NAME)) {
			throw new ResponseException("此学校不存在");
		}

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(StudentNumber.TABLE_NAME);
		query.and(StudentNumber.SCHOOL_ID, sn.getSchoolId());

		int count = this.dao.count(query);
		sn.setOwnerId(EWeblibThreadLocal.getCurrentUserId());
		sn.setNumber(count + 1);
		this.dao.insert(sn);

		StudentNumber tmp = new StudentNumber();
		tmp.setNumber(sn.getNumber());
		return tmp;
	}

	@Override
	public List<StudentSchoolInfo> listMyStudentSchools() {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(StudentNumber.TABLE_NAME);
		query.and(StudentNumber.OWER_ID, EWeblibThreadLocal.getCurrentUserId());

		List<StudentNumber> list = this.dao.listByQuery(query, StudentNumber.class);
		List<StudentSchoolInfo> results = new ArrayList<StudentSchoolInfo>();
		DataBaseQueryBuilder userQuery = new DataBaseQueryBuilder(User.TABLE_NAME);
		userQuery.and(User.ID, EWeblibThreadLocal.getCurrentUserId());
		userQuery.limitColumns(new String[] { User.NAME, User.MOBILE_NUMBER });
		User user = this.dao.findOneByQuery(userQuery, User.class);
		for (StudentNumber sn : list) {
			StudentSchoolInfo info = new StudentSchoolInfo();
			info.setNumber(sn.getNumber());
			info.setParent(user);
			info.setCreatedOn(sn.getCreatedOn());

			info.setSchool(loadSchool(sn.getSchoolId()));
			info.setStudent(loadStudentInfo(sn.getStudentId()));
			results.add(info);

		}

		return results;
	}

	private School loadSchool(String id) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(School.TABLE_NAME);
		query.and(School.ID, id);
		query.limitColumns(new String[] { School.NAME });

		return this.dao.findOneByQuery(query, School.class);
	}

	private Student loadStudentInfo(String id) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Student.TABLE_NAME);
		query.and(Student.ID, id);
		query.limitColumns(new String[] { Student.NAME, Student.BIRTH_DAY, Student.SEX });

		return this.dao.findOneByQuery(query, Student.class);
	}

	public List<Student> listMyAvaliableStudentForSchool() {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(StudentNumber.TABLE_NAME);
		query.and(StudentNumber.OWER_ID, EWeblibThreadLocal.getCurrentUserId());

		List<StudentNumber> list = this.dao.listByQuery(query, StudentNumber.class);
		Set<String> ids = new HashSet<String>();
		for (StudentNumber sn : list) {
			ids.add(sn.getStudentId());
		}

		DataBaseQueryBuilder squery = new DataBaseQueryBuilder(Student.TABLE_NAME);
		squery.and(Student.OWNER_ID, EWeblibThreadLocal.getCurrentUserId());
		squery.limitColumns(new String[] { Student.NAME, Student.BIRTH_DAY, Student.SEX, Student.ID });

		List<Student> slist =  this.dao.listByQuery(squery, Student.class);
		for(Student s: slist){
			if(ids.contains(s.getId())){
				s.setHasNumber(true);
			}
		}
		
		return slist;

	}

	public EntityResults<School> listSchoolsForAdmin(School school) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(School.TABLE_NAME);

		if (EweblibUtil.isValid(school.getName())) {
			query.and(DataBaseQueryOpertion.LIKE, School.NAME, school.getName());
		}

		return this.dao.listByQueryWithPagnation(query, School.class);

	}

}
