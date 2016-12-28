package com.wx.school.service;

import java.util.List;

import com.eweblib.bean.vo.EntityResults;
import com.wx.school.bean.SearchVO;
import com.wx.school.bean.StudentPlanInfo;
import com.wx.school.bean.school.School;
import com.wx.school.bean.school.SchoolPlan;
import com.wx.school.bean.school.StudentNumber;
import com.wx.school.bean.school.StudentSchoolInfo;
import com.wx.school.bean.user.Student;

public interface ISchoolService {

	List<SchoolPlan> listSchoolPlan();
	
	public void addSchool(School school);

	StudentNumber bookSchool(StudentNumber sn);

	List<StudentSchoolInfo> listMyStudentSchools();

	List<Student> listMyAvaliableStudentForSchool();

	EntityResults<SchoolPlan> listSchoolPlanForAdmin(SchoolPlan school);

	List<School> listSchoolsForAdmin();

	void addSchoolPlan(SchoolPlan plan);

	void deleteSchoolPlan(SchoolPlan plan);

	void deliverySchoolPlan(SchoolPlan plan);

	void cancelSchoolPlan(SchoolPlan plan);

	SchoolPlan loadSchoolPlan(SchoolPlan plan);

	void updateSchoolPlan(SchoolPlan plan);

	EntityResults<StudentPlanInfo> listStudentPlanForAdmin(SearchVO svo);

}
