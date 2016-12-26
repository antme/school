package com.wx.school.bean.school;

import com.eweblib.bean.BaseEntity;

public class StudentNumber extends BaseEntity {

	
	public String studentId;
	
	public String owerId;
	
	public Integer number;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getOwerId() {
		return owerId;
	}

	public void setOwerId(String owerId) {
		this.owerId = owerId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	
}
