package com.wx.school.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import com.wx.school.bean.SearchVO;
import com.wx.school.bean.StudentPlanInfo;
import com.wx.school.bean.school.School;
import com.wx.school.bean.school.SchoolPlan;
import com.wx.school.bean.school.StudentNumber;
import com.wx.school.bean.school.StudentSchoolInfo;
import com.wx.school.bean.user.Student;
import com.wx.school.bean.user.User;
import com.wx.school.service.ISchoolService;

@Service(value = "school")
public class SchoolServiceImpl extends AbstractService implements ISchoolService {

	@Override
	public List<SchoolPlan> listSchoolPlan() {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(SchoolPlan.TABLE_NAME);
		query.limitColumns(new String[] { SchoolPlan.ID, SchoolPlan.NAME, SchoolPlan.ONLY_FOR_VIP,
				SchoolPlan.TAKE_NUMBER_DATE, SchoolPlan.START_TIME, SchoolPlan.END_TIME });
		query.and(DataBaseQueryOpertion.IS_TRUE, SchoolPlan.IS_DISPLAY_FOR_WX);
		List<SchoolPlan> list = this.dao.listByQuery(query, SchoolPlan.class);
		updateStatus(list);

		return list;
	}

	private void updateStatus(List<SchoolPlan> list) {

		if (list != null) {
			for (SchoolPlan school : list) {
				Date date = new Date();

				String date2 = DateUtil.getDateString(school.getTakeNumberDate()) + " " + school.getStartTime() + ":00";
				Date startDate = DateUtil.getDateTime(date2);
				String date3 = DateUtil.getDateString(school.getTakeNumberDate()) + " " + school.getEndTime() + ":00";
				Date endDate = DateUtil.getDateTime(date3);

				if (date.getTime() < startDate.getTime()) {
					//未开始
					school.setTakeStatus(0);
				} else if (date.getTime() < endDate.getTime()) {
					//正在取号
					school.setTakeStatus(1);
				} else {
					//已结束
					school.setTakeStatus(2);
				}

			}
		}
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

		if (EweblibUtil.isEmpty(sn.getPlanId())) {
			throw new ResponseException("请选择可以取号校区");
		}
		if (!this.dao.exists(Student.ID, sn.getStudentId(), Student.TABLE_NAME)) {
			throw new ResponseException("此学生不存在");
		}
		if (this.dao.exists(StudentNumber.STUDENT_ID, sn.getStudentId(), StudentNumber.TABLE_NAME)) {
			throw new ResponseException("此学生已经选择校区");
		}

		SchoolPlan plan = this.dao.findById(sn.getPlanId(), SchoolPlan.TABLE_NAME, SchoolPlan.class);

		if (plan == null) {
			throw new ResponseException("此取号批次不存在");
		}

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(StudentNumber.TABLE_NAME);
		query.and(StudentNumber.SCHOOL_ID, plan.getSchoolId());

		sn.setSchoolId(plan.getSchoolId());
		// FIXME
		int count = this.dao.count(query);

		sn.setOwnerId(EWeblibThreadLocal.getCurrentUserId());
		sn.setNumber(count + 1);
		sn.setIsSmsSent(false);
		this.dao.insert(sn);

		StudentNumber tmp = new StudentNumber();
		tmp.setNumber(sn.getNumber());
		return tmp;
	}

	@Override
	public List<StudentSchoolInfo> listMyStudentSchools() {

		List<SchoolPlan> planList = this.dao.listByQuery(new DataBaseQueryBuilder(SchoolPlan.TABLE_NAME),
				SchoolPlan.class);
		Map<String, String> planMap = new HashMap<String, String>();
		for (SchoolPlan plan : planList) {
			planMap.put(plan.getId(), plan.getSchoolId());
		}

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

			info.setSchool(loadSchool(planMap.get(sn.getPlanId())));
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

		List<Student> slist = this.dao.listByQuery(squery, Student.class);
		for (Student s : slist) {
			if (ids.contains(s.getId())) {
				s.setHasNumber(true);
			}
		}

		return slist;

	}

	public EntityResults<SchoolPlan> listSchoolPlanForAdmin(SchoolPlan school) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(SchoolPlan.TABLE_NAME);

		if (EweblibUtil.isValid(school.getName())) {
			query.and(DataBaseQueryOpertion.LIKE, SchoolPlan.NAME, school.getName());
		}

		EntityResults<SchoolPlan> result = this.dao.listByQueryWithPagnation(query, SchoolPlan.class);
		List<SchoolPlan> list = result.getEntityList();

		updateStatus(list);
		return result;

	}

	public List<School> listSchoolsForAdmin() {

		return this.dao.listByQuery(new DataBaseQueryBuilder(School.TABLE_NAME), School.class);
	}

	public void addSchoolPlan(SchoolPlan plan) {
		School s = loadSchool(plan.getSchoolId());
		plan.setName(s.getName());

		this.dao.insert(plan);
	}

	public void deleteSchoolPlan(SchoolPlan plan) {
		DataBaseQueryBuilder delQuery = new DataBaseQueryBuilder(StudentNumber.TABLE_NAME);
		delQuery.and(StudentNumber.PLAN_ID, plan.getId());
		
		this.dao.deleteByQuery(delQuery);
		
		this.dao.deleteById(plan);
	}

	public void deliverySchoolPlan(SchoolPlan plan) {
		plan.setIsDisplayForWx(true);
		this.dao.updateById(plan, new String[] { SchoolPlan.IS_DISPLAY_FOR_WX });
	}

	public void cancelSchoolPlan(SchoolPlan plan) {
		plan.setIsDisplayForWx(false);
		this.dao.updateById(plan, new String[] { SchoolPlan.IS_DISPLAY_FOR_WX });
	}

	public SchoolPlan loadSchoolPlan(SchoolPlan plan) {
		return this.dao.findById(plan.getId(), SchoolPlan.TABLE_NAME, SchoolPlan.class);
	}

	public void updateSchoolPlan(SchoolPlan plan) {

		this.dao.updateById(plan,
				new String[] { SchoolPlan.TAKE_NUMBER_DATE, SchoolPlan.START_TIME, SchoolPlan.END_TIME });
	}

	public EntityResults<StudentPlanInfo> listStudentPlanForAdmin(SearchVO svo) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(StudentNumber.TABLE_NAME);
		query.leftJoin(StudentNumber.TABLE_NAME, Student.TABLE_NAME, StudentNumber.STUDENT_ID, Student.ID);
		query.leftJoin(StudentNumber.TABLE_NAME, School.TABLE_NAME, StudentNumber.SCHOOL_ID, School.ID);
		query.leftJoin(StudentNumber.TABLE_NAME, User.TABLE_NAME, StudentNumber.OWER_ID, User.ID);

		query.joinColumns(Student.TABLE_NAME, new String[] { Student.NAME, Student.SEX, Student.BIRTH_DAY,
				Student.CREATED_ON + " as studentRegDate" });
		query.joinColumns(School.TABLE_NAME, new String[] { School.NAME + " as schoolName" });
		query.joinColumns(User.TABLE_NAME, new String[] { User.NAME + " as parentName", User.MOBILE_NUMBER });
		query.limitColumns(new String[] { StudentNumber.CREATED_ON, StudentNumber.NUMBER });

		if (EweblibUtil.isValid(svo.getName())) {
			query.and(DataBaseQueryOpertion.LIKE, Student.TABLE_NAME + "." + Student.NAME, svo.getName());
		}

		if (EweblibUtil.isValid(svo.getParentName())) {
			query.and(DataBaseQueryOpertion.LIKE, User.TABLE_NAME + "." + User.NAME, svo.getParentName());
		}

		if (EweblibUtil.isValid(svo.getMobileNumber())) {
			query.and(DataBaseQueryOpertion.LIKE, User.TABLE_NAME + "." + User.MOBILE_NUMBER, svo.getMobileNumber());
		}

		if (EweblibUtil.isValid(svo.getSchoolName())) {
			query.and(DataBaseQueryOpertion.LIKE, School.TABLE_NAME + "." + School.NAME, svo.getSchoolName());
		}

		if (EweblibUtil.isValid(svo.getSchoolId())) {
			query.and(School.TABLE_NAME + "." + School.ID, svo.getSchoolId());
		}
		
		if(EweblibUtil.isValid(svo.getNumber())){
			query.and(StudentNumber.TABLE_NAME + "." + StudentNumber.NUMBER, svo.getNumber());
		}

		return this.dao.listByQueryWithPagnation(query, StudentPlanInfo.class);

	}
}
