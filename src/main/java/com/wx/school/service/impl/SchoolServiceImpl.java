package com.wx.school.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.eweblib.util.DateUtil;
import com.eweblib.util.EWeblibThreadLocal;
import com.eweblib.util.EweblibUtil;
import com.eweblib.util.ExcelUtil;
import com.wx.school.bean.SearchVO;
import com.wx.school.bean.StudentPlanInfo;
import com.wx.school.bean.plan.SchoolBaoMingPlan;
import com.wx.school.bean.school.School;
import com.wx.school.bean.school.SchoolPlan;
import com.wx.school.bean.school.StudentNumber;
import com.wx.school.bean.school.StudentSchoolInfo;
import com.wx.school.bean.user.SmsLog;
import com.wx.school.bean.user.Student;
import com.wx.school.bean.user.User;
import com.wx.school.service.ICacheService;
import com.wx.school.service.ISchoolService;
import com.wx.school.service.message.IMessageService;

@Service(value = "school")
public class SchoolServiceImpl extends AbstractService implements ISchoolService {
	@Autowired
	private ICacheService cs;
	public static Logger logger = LogManager.getLogger(SchoolServiceImpl.class);

	public static Set<String> processMap = new HashSet<String>();

	public static Map<String, Integer> lastCountMap = new HashMap<String, Integer>();

	@Autowired
	private IMessageService ms;

	@Override
	public List<SchoolPlan> listSchoolPlan() {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Student.TABLE_NAME);
		query.and(Student.OWNER_ID, EWeblibThreadLocal.getCurrentUserId());

		List<Student> studentList = this.dao.listByQuery(query, Student.class);

		List<SchoolPlan> list = CacheServiceImpl.list;

		List<SchoolPlan> finalList = new ArrayList<SchoolPlan>();

		for (SchoolPlan plan : list) {
			for (Student s : studentList) {
				if (s.getSchoolId() != null) {

					if (EweblibUtil.isValid(s.getSignUpSchoolId())) {
						if (plan.getSchoolId().equalsIgnoreCase(s.getSignUpSchoolId())) {
							finalList.add(plan);
							break;
						}
					} else {
						if (plan.getSchoolId().equalsIgnoreCase(s.getSchoolId())) {
							finalList.add(plan);
							break;
						}
					}
				}

			}
		}
		updateStatus(finalList);
		return finalList;
	}

	private void updateStatus(List<SchoolPlan> list) {

		if (list != null) {
			for (SchoolPlan school : list) {
				updateStatus(school);

			}
		}
	}

	private void updateStatus(SchoolPlan school) {
		Date date = new Date();

		String date2 = DateUtil.getDateString(school.getTakeNumberDate()) + " " + school.getStartTime() + ":00";
		Date startDate = DateUtil.getDateTime(date2);
		String date3 = DateUtil.getDateString(school.getTakeNumberDate()) + " " + school.getEndTime() + ":00";
		Date endDate = DateUtil.getDateTime(date3);

		if (date.getTime() < startDate.getTime()) {
			// 未开始
			school.setTakeStatus(0);
		} else if (date.getTime() < endDate.getTime()) {
			// 正在取号
			school.setTakeStatus(1);
		} else {
			// 已结束
			school.setTakeStatus(2);
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

		if (sn.getStudentId().length() != 36) {
			throw new ResponseException("参数异常");
		}

		if (sn.getPlanId().length() != 36) {
			throw new ResponseException("参数异常");
		}
		
		if (EweblibUtil.isEmpty(EWeblibThreadLocal.getCurrentUserId())) {
			throw new ResponseException("请先登录");
		}

		if (processMap.contains(sn.getStudentId())) {
			throw new ResponseException("你的取号已经再处理中，请稍后在我的取号信息里查询结果");
		}
		
		

		// if (!this.dao.exists(Student.ID, sn.getStudentId(),
		// Student.TABLE_NAME)) {
		// throw new ResponseException("此学生不存在");
		// }

		SchoolPlan plan = CacheServiceImpl.shcoolPlanMap.get(sn.getPlanId());

		if (plan == null) {
			throw new ResponseException("此取号批次不存在");
		}
		
		updateStatus(plan);

		if (plan.getTakeStatus() != null && plan.getTakeStatus() != 1) {
			throw new ResponseException("取号已经结束或者还未开始");
		}

		processMap.add(sn.getStudentId());
		StudentNumber p = null;
		try {

			p = bookPlan(sn, plan);
		} catch (Exception e) {
			processMap.remove(sn.getStudentId());
			throw new ResponseException("取号失败，请稍后再试");
		}

		return p;

	}

	private StudentNumber bookPlan(StudentNumber sn, SchoolPlan plan) {

		if (this.dao.exists(StudentNumber.STUDENT_ID, sn.getStudentId(), StudentNumber.TABLE_NAME)) {
			throw new ResponseException("此学生已经选择校区");
		}

		setBookNumber(sn, plan);
		sn.setOwnerId(EWeblibThreadLocal.getCurrentUserId());
		sn.setIsSmsSent(false);
		this.dao.insert(sn);

		logger.info(sn.toString());
		StudentNumber tmp = new StudentNumber();
		tmp.setNumber(sn.getNumber());

		processMap.remove(sn.getStudentId());
		return tmp;
	}

	private synchronized void setBookNumber(StudentNumber sn, SchoolPlan plan) {
		String schoolId = plan.getSchoolId();

		int count = 1;

		if (lastCountMap.get(schoolId) == null) {

			DataBaseQueryBuilder query = new DataBaseQueryBuilder(StudentNumber.TABLE_NAME);
			query.and(StudentNumber.SCHOOL_ID, schoolId);
			query.orderBy(StudentNumber.NUMBER, false);
			query.limitColumns(new String[] { StudentNumber.NUMBER });
			StudentNumber last = this.dao.findOneByQuery(query, StudentNumber.class);

			if (last != null) {
				count = last.getNumber() + 1;
			}

		} else {
			count = lastCountMap.get(schoolId);
			count = count + 1;
		}

		lastCountMap.put(schoolId, count);
		sn.setNumber(count);

		sn.setSchoolId(schoolId);
	}

	@Override
	public List<StudentSchoolInfo> listMyStudentSchools() {

		List<SchoolPlan> planList = this.dao.listByQuery(new DataBaseQueryBuilder(SchoolPlan.TABLE_NAME),
				SchoolPlan.class);
		Map<String, SchoolPlan> planMap = new HashMap<String, SchoolPlan>();
		for (SchoolPlan plan : planList) {
			planMap.put(plan.getId(), plan);
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

			SchoolPlan plan = planMap.get(sn.getPlanId());
			School loadSchool = loadSchool(plan.getSchoolId());
			if (loadSchool != null) {
				loadSchool.setName(plan.getName());
			}
			info.setSchool(loadSchool);
			info.setStudent(loadStudentInfo(sn.getStudentId()));
			info.setBaomingMsg(ms.loadNoticeMsg(info, false));
			results.add(info);

		}

		return results;
	}

	private School loadSchool(String id) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(School.TABLE_NAME);
		query.and(School.ID, id);
		query.limitColumns(new String[] { School.NAME, School.ID });

		return this.dao.findOneByQuery(query, School.class);
	}

	private Student loadStudentInfo(String id) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Student.TABLE_NAME);
		query.and(Student.ID, id);
		query.limitColumns(new String[] { Student.NAME, Student.BIRTH_DAY, Student.SEX, Student.SIGN_UP_SCHOOL_ID,
				Student.SIGN_UP_PLACE, Student.SCHOOL_ID, Student.SIGN_UP_SCHOOL_ID });

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
		plan.setSchoolName(s.getName());

		this.dao.insert(plan);
		cs.refreshCach();
	}

	public void deleteSchoolPlan(SchoolPlan plan) {
		plan = this.dao.findById(plan.getId(), SchoolPlan.TABLE_NAME, SchoolPlan.class);
		if (plan != null) {
			ms.deleteSmsLog(plan.getSchoolId());
		}

		DataBaseQueryBuilder delQuery = new DataBaseQueryBuilder(StudentNumber.TABLE_NAME);
		delQuery.and(StudentNumber.PLAN_ID, plan.getId());

		this.dao.deleteByQuery(delQuery);

		this.dao.deleteById(plan);
		lastCountMap.clear();
		cs.refreshCach();
	}

	public void deliverySchoolPlan(SchoolPlan plan) {
		plan.setIsDisplayForWx(true);
		this.dao.updateById(plan, new String[] { SchoolPlan.IS_DISPLAY_FOR_WX });
		cs.refreshCach();
	}

	public void cancelSchoolPlan(SchoolPlan plan) {
		plan.setIsDisplayForWx(false);
		this.dao.updateById(plan, new String[] { SchoolPlan.IS_DISPLAY_FOR_WX });
		cs.refreshCach();
	}

	public SchoolPlan loadSchoolPlan(SchoolPlan plan) {
		return this.dao.findById(plan.getId(), SchoolPlan.TABLE_NAME, SchoolPlan.class);
	}

	public void updateSchoolPlan(SchoolPlan plan) {

		this.dao.updateById(plan,
				new String[] { SchoolPlan.TAKE_NUMBER_DATE, SchoolPlan.START_TIME, SchoolPlan.END_TIME });
		cs.refreshCach();
	}

	public EntityResults<StudentPlanInfo> listStudentPlanForAdmin(SearchVO svo) {
		DataBaseQueryBuilder query = getNumberQuery(svo);

		EntityResults<StudentPlanInfo> infoList = this.dao.listByQueryWithPagnation(query, StudentPlanInfo.class);
		List<StudentPlanInfo> list = infoList.getEntityList();

		mergeStudentPlanInfo(list);

		return infoList;

	}

	private void mergeStudentPlanInfo(List<StudentPlanInfo> list) {
		List<SchoolPlan> planList = this.dao.listByQuery(new DataBaseQueryBuilder(SchoolPlan.TABLE_NAME),
				SchoolPlan.class);
		Map<String, String> planMap = new HashMap<String, String>();
		for (SchoolPlan plan : planList) {
			planMap.put(plan.getId(), plan.getSchoolId());
		}
		for (StudentPlanInfo i : list) {

			StudentSchoolInfo info = new StudentSchoolInfo();
			info.setNumber(i.getNumber());
			info.setSchool(loadSchool(planMap.get(i.getPlanId())));
			Student loadStudentInfo = loadStudentInfo(i.getStudentId());
			info.setStudent(loadStudentInfo);

			i.setOriginSchoolName(loadSchool(loadStudentInfo.getSchoolId()).getName());
			i.setBaomingInfo(ms.loadNoticeMsg(info, true));

		}
	}

	private DataBaseQueryBuilder getNumberQuery(SearchVO svo) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(StudentNumber.TABLE_NAME);
		query.leftJoin(StudentNumber.TABLE_NAME, Student.TABLE_NAME, StudentNumber.STUDENT_ID, Student.ID);
		query.leftJoin(StudentNumber.TABLE_NAME, School.TABLE_NAME, StudentNumber.SCHOOL_ID, School.ID);
		query.leftJoin(StudentNumber.TABLE_NAME, User.TABLE_NAME, StudentNumber.OWER_ID, User.ID);

		query.joinColumns(Student.TABLE_NAME, new String[] { Student.NAME, Student.SEX, Student.BIRTH_DAY,
				Student.CREATED_ON + " as studentRegDate", Student.IS_VIP });
		query.joinColumns(School.TABLE_NAME, new String[] { School.NAME + " as schoolName" });
		query.joinColumns(User.TABLE_NAME, new String[] { User.NAME + " as parentName", User.MOBILE_NUMBER });
		query.limitColumns(new String[] { StudentNumber.CREATED_ON, StudentNumber.NUMBER, StudentNumber.ID,
				StudentNumber.REMARK, StudentNumber.PLAN_ID, StudentNumber.STUDENT_ID });

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

		if (EweblibUtil.isValid(svo.getIsVip())) {
			if (svo.getIsVip()) {
				query.and(DataBaseQueryOpertion.IS_TRUE, Student.TABLE_NAME + "." + Student.IS_VIP);
			} else {
				query.and(DataBaseQueryOpertion.IS_FALSE, Student.TABLE_NAME + "." + Student.IS_VIP);
			}
		}

		if (EweblibUtil.isValid(svo.getSchoolId())) {
			query.and(Student.TABLE_NAME + "." + Student.SCHOOL_ID, svo.getSchoolId());
		}

		if (EweblibUtil.isValid(svo.getNumber())) {
			query.and(StudentNumber.TABLE_NAME + "." + StudentNumber.NUMBER, svo.getNumber());
		}

		if (EweblibUtil.isValid(svo.getRemark())) {
			query.and(DataBaseQueryOpertion.LIKE, StudentNumber.TABLE_NAME + "." + StudentNumber.REMARK,
					svo.getRemark());
		}

		if (EweblibUtil.isValid(svo.getTargetSchoolId())) {
			query.and(StudentNumber.TABLE_NAME + "." + StudentNumber.SCHOOL_ID, svo.getTargetSchoolId());

		}
		query.orderBy(StudentNumber.NUMBER, false);
		return query;
	}

	public String exportStudentNumberForAdmin(SearchVO svo) {

		DataBaseQueryBuilder query = getNumberQuery(svo);
		List<StudentPlanInfo> list = this.dao.listByQuery(query, StudentPlanInfo.class);
		for (StudentPlanInfo s : list) {
			if (s.getSex().equalsIgnoreCase("m")) {
				s.setSexCn("男性");
			} else {
				s.setSexCn("女性");
			}

			if (s.getIsVip()) {
				s.setIsVipStr("是");
			} else {
				s.setIsVipStr("否");
			}

		}

		mergeStudentPlanInfo(list);

		String dowload_path = "取号_" + DateUtil.getDateString(new Date()) + ".xls";
		String f = ConfigManager.getProperty("download_path") + dowload_path;
		String[] colunmTitleHeaders = new String[] { "号数", "姓名", "性别", "出生日期", "家长姓名", "家长手机", "学生注册时间", "取号时间", "校区",
				"是否会员", "备注", "报名校区", "报名时间" };
		String[] colunmHeaders = new String[] { "number", "name", "sexCn", "birthday", "parentName", "mobileNumber",
				"studentRegDate", "createdOn", "originSchoolName", "isVipStr", "remark", "schoolName", "baomingInfo" };
		ExcelUtil.createExcelListFileByEntity(list, colunmTitleHeaders, colunmHeaders, f);

		return dowload_path;
	}

	public int sumStudentVip(SearchVO svo) {
		DataBaseQueryBuilder query = getNumberQuery(svo);
		query.and(DataBaseQueryOpertion.IS_TRUE, Student.TABLE_NAME + "." + Student.IS_VIP);

		return this.dao.count(query);
	}

	public StudentNumber loadStudentPlanRemark(StudentNumber number) {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(StudentNumber.TABLE_NAME);
		query.and(StudentNumber.ID, number.getId());
		query.limitColumns(new String[] { StudentNumber.ID, StudentNumber.REMARK });
		return this.dao.findOneByQuery(query, StudentNumber.class);

	}

	public void updateStudentPlanRemark(StudentNumber number) {
		this.dao.updateById(number, new String[] { StudentNumber.REMARK });
	}

	public void addSchoolBaomingPlan(SchoolBaoMingPlan plan) {

		if (EweblibUtil.isValid(plan.getId())) {
			updateSchoolBaomingPlan(plan);
		} else {
			this.dao.insert(plan);
		}
	}

	public void updateSchoolBaomingPlan(SchoolBaoMingPlan plan) {

		String[] updateFields = new String[] { SchoolBaoMingPlan.SIGN_UP_COUNT, SchoolBaoMingPlan.KEEP_ON_MINUTES,
				SchoolBaoMingPlan.PLACE, SchoolBaoMingPlan.SIGN_UP_DATE, SchoolBaoMingPlan.SKIP_MINUTES,
				SchoolBaoMingPlan.START_TIME, SchoolBaoMingPlan.LAST_MERGE_SIGN_UP_COUNT, SchoolBaoMingPlan.MIDDAY_REST_HOURS};
		this.dao.updateById(plan, updateFields);

	}

	public void renewBaomingPlanItem() {

		List<SchoolPlan> planList = this.dao.listByQuery(new DataBaseQueryBuilder(SchoolPlan.TABLE_NAME),
				SchoolPlan.class);

		for (SchoolPlan plan : planList) {

			SmsLog last = null;
			int startCount = 1;

			String schoolId = plan.getSchoolId();
			ms.deleteSmsLog(schoolId);

			SchoolBaoMingPlan baoming = loadSchoolBaomingPlan(schoolId);

			if (baoming != null) {
				DataBaseQueryBuilder studentNumberQuery = new DataBaseQueryBuilder(StudentNumber.TABLE_NAME);
				studentNumberQuery.and(StudentNumber.SCHOOL_ID, schoolId);

				int newStudentCount = this.dao.count(studentNumberQuery);
				studentNumberQuery.orderBy(StudentNumber.CREATED_ON, false);
				StudentNumber lastSn = this.dao.findOneByQuery(studentNumberQuery, StudentNumber.class);

				if (newStudentCount > 0) {

					newStudentCount = lastSn.getNumber();
					int signUpCount = baoming.getSignUpCount();

					int length = newStudentCount / signUpCount;
					int remaining = newStudentCount % signUpCount;

					Date startDate = DateUtil
							.getDateTime(DateUtil.getDateString(baoming.getSignUpDate()) + " " + baoming.getStartTime() + ":00");
					Calendar c = Calendar.getInstance();
					c.setTime(startDate);

					for (int i = 0; i < length; i++) {

						last = createSmsLog(last, startCount, baoming, signUpCount, c);
						startCount = last.getEndNumber() + 1;
					}

					if (remaining > 0) {
						Date date = DateUtil.getDateTime(
								DateUtil.getDateString(plan.getTakeNumberDate()) + " " + plan.getEndTime() + ":00");

						if (date.getTime() < new Date().getTime()) {
							if (remaining < baoming.getLastMergeSignUpCount()) {

								if (last != null) {
									int total = last.getEndNumber() + remaining;
									last.setEndNumber(total);
									last.setSuccessCount(total);
									last.setTotalSend(total);
									this.dao.updateById(last, new String[] { SmsLog.END_NUMBER, SmsLog.SUCCESS_COUNT,
											SmsLog.TOTAL_SEND });
								} else {
									createSmsLog(last, startCount, baoming, remaining, c);
								}
							} else {
								createSmsLog(last, startCount, baoming, remaining, c);
							}
						}

					}

				}

			}

		}

	}

	private SchoolBaoMingPlan loadSchoolBaomingPlan(String schoolId) {
		DataBaseQueryBuilder baomingQuery = new DataBaseQueryBuilder(SchoolBaoMingPlan.TABLE_NAME);
		baomingQuery.and(SchoolBaoMingPlan.SCHOOL_ID, schoolId);
		baomingQuery.orderBy(SchoolBaoMingPlan.CREATED_ON, false);
		SchoolBaoMingPlan baoming = this.dao.findOneByQuery(baomingQuery, SchoolBaoMingPlan.class);
		return baoming;
	}

	/**
	 * 没调用一次 c都会加上相应的时间
	 * @param last
	 * @param startCount
	 * @param baoming
	 * @param signUpCount
	 * @param c
	 * @return
	 */
	private SmsLog createSmsLog(SmsLog last, int startCount, SchoolBaoMingPlan baoming, int signUpCount, Calendar c) {
		SmsLog log = new SmsLog();
		log.setBaomingPlanId(baoming.getId());
		log.setStartNumber(startCount);
		log.setEndNumber(startCount - 1 + signUpCount);
		log.setTotalSend(signUpCount);
		log.setSuccessCount(signUpCount);
		log.setPlace(baoming.getPlace());
		log.setSchoolId(baoming.getSchoolId());
		log.setSignDate(baoming.getSignUpDate());
		
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minutes = c.get(Calendar.MINUTE);
		
		if ((hour == 11 && minutes >= 45) || (hour == 12)) {
			//午休时间
			int skipMinutes = (int) (baoming.getMiddayRestHours() * 60);		
			c.add(Calendar.MINUTE, skipMinutes);

		}else if (last != null) {
			// 如果已经有批次了，要加上 skipMinutes
			c.add(Calendar.MINUTE, baoming.getSkipMinutes());
		}
		
		
		hour = c.get(Calendar.HOUR_OF_DAY);
		minutes = c.get(Calendar.MINUTE);

		log.setStartTime(mergeHoursDisplay(hour, minutes));

		// if (last != null) {
		// c.add(Calendar.MINUTE, baoming.getKeepOnMinutes()- 1);
		// } else {
		c.add(Calendar.MINUTE, baoming.getKeepOnMinutes());
		// }

		hour = c.get(Calendar.HOUR_OF_DAY);
		minutes = c.get(Calendar.MINUTE);

		log.setEndTime(mergeHoursDisplay(hour, minutes));
		this.dao.insert(log);
		return log;
	}

	private String mergeHoursDisplay(int hour, int mites) {

		String hourstr = hour + "";

		if (hour < 10) {
			hourstr = "0" + hour + "";
		}

		String mtr = mites + "";

		if (mites < 10) {
			mtr = "0" + mites + "";
		}

		return hourstr + ":" + mtr;
	}

	public EntityResults<SchoolBaoMingPlan> listSchoolBaomingPlanForAdmin(SearchVO svo) {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(SchoolBaoMingPlan.TABLE_NAME);

		query.leftJoin(SchoolBaoMingPlan.TABLE_NAME, School.TABLE_NAME, SchoolBaoMingPlan.SCHOOL_ID, School.ID);
		query.joinColumns(School.TABLE_NAME, new String[] { School.NAME });
		query.limitColumns(new String[] { SchoolBaoMingPlan.ID, SchoolBaoMingPlan.KEEP_ON_MINUTES,
				SchoolBaoMingPlan.LAST_MERGE_SIGN_UP_COUNT, SchoolBaoMingPlan.PLACE, SchoolBaoMingPlan.SIGN_UP_COUNT,
				SchoolBaoMingPlan.SIGN_UP_DATE, SchoolBaoMingPlan.SKIP_MINUTES, SchoolBaoMingPlan.START_TIME, SchoolBaoMingPlan.MIDDAY_REST_HOURS });
		if (svo.getSignUpDate() != null) {
			query.and(SchoolBaoMingPlan.SIGN_UP_DATE, svo.getSignUpDate());
		}

		if (EweblibUtil.isValid(svo.getSchoolId())) {
			query.and(SchoolBaoMingPlan.SCHOOL_ID, svo.getSchoolId());
		}
		return this.dao.listByQueryWithPagnation(query, SchoolBaoMingPlan.class);
	}

	public void deleteSchoolBaomingPlan(SchoolBaoMingPlan plan) {

		plan = this.dao.findById(plan.getId(), SchoolBaoMingPlan.TABLE_NAME, SchoolBaoMingPlan.class);
		if (plan != null) {
			ms.deleteSmsLog(plan.getSchoolId());
		}

		this.dao.deleteById(plan);
	}

	public SchoolBaoMingPlan loadSchoolBaomingPlan(SchoolBaoMingPlan plan) {

		return this.dao.findById(plan.getId(), SchoolBaoMingPlan.TABLE_NAME, SchoolBaoMingPlan.class);
	}
	
	
	public Map<String, Object> getNoticeInfo(){
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(Student.TABLE_NAME);
		
		query.and(Student.OWNER_ID, EWeblibThreadLocal.getCurrentUserId());
		
		Student student = this.dao.findOneByQuery(query, Student.class);
		
		if(student!=null) {
			
			//
			DataBaseQueryBuilder baomingQuery = new DataBaseQueryBuilder(SchoolPlan.TABLE_NAME);
			baomingQuery.and(SchoolPlan.SCHOOL_ID, student.getSignUpSchoolId());
			baomingQuery.orderBy(SchoolPlan.CREATED_ON, false);
			SchoolPlan plan = this.dao.findOneByQuery(baomingQuery, SchoolPlan.class);
			
			if (plan != null) {

				Date endDate = DateUtil.getDateTime(
						DateUtil.getDateString(plan.getTakeNumberDate()) + " " + plan.getEndTime() + ":00");

				if (new Date().getTime() > endDate.getTime()) {

					SchoolBaoMingPlan sp = loadSchoolBaomingPlan(student.getSignUpSchoolId());
					if (sp != null) {
						SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
						result.put("takeDate", f.format(sp.getSignUpDate()));
					}

				}

			}
		}
		
		return result;
		
	}

}
