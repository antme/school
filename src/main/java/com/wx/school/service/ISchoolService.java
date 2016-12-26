package com.wx.school.service;

import java.util.List;

import com.wx.school.bean.school.School;
import com.wx.school.bean.school.StudentNumber;
import com.wx.school.bean.school.StudentSchoolInfo;

public interface ISchoolService {

	List<School> listSchools();
	
	public void addSchool(School school);

	StudentNumber bookSchool(StudentNumber sn);

	List<StudentSchoolInfo> listMyStudentSchools();

}
