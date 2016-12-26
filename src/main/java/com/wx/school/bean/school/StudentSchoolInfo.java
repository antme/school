package com.wx.school.bean.school;

import com.eweblib.bean.BaseEntity;
import com.wx.school.bean.user.Student;
import com.wx.school.bean.user.User;

public class StudentSchoolInfo extends BaseEntity {

	public Integer number;

	public School school;

	public User parent;

	public Student student;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public User getParent() {
		return parent;
	}

	public void setParent(User parent) {
		this.parent = parent;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
