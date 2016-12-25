package com.wx.school.service;

import java.util.List;

import com.eweblib.bean.BaseEntity;
import com.wx.school.bean.school.School;

public interface ISchoolService {

	List<School> listSchools();
	
	public void addSchool(School school);

}
