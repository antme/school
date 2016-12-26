package com.wx.school.service;

import java.util.List;

import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.vo.EntityResults;
import com.wx.school.bean.school.School;
import com.wx.school.bean.school.StudentNumber;
import com.wx.school.bean.school.StudentSchoolInfo;
import com.wx.school.bean.user.Student;

public interface ISchoolService {

	List<School> listSchools();
	
	public void addSchool(School school);

	StudentNumber bookSchool(StudentNumber sn);

	List<StudentSchoolInfo> listMyStudentSchools();

	List<Student> listMyAvaliableStudentForSchool();

	EntityResults<School> listSchoolsForAdmin(School school);

}
