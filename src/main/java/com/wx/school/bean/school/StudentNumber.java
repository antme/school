package com.wx.school.bean.school;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = StudentNumber.TABLE_NAME)
public class StudentNumber extends BaseEntity {
	public static final String SCHOOL_ID = "schoolId";

	public static final String PLAN_ID = "planId";

	public static final String TABLE_NAME = "StudentNumber";

	public static final String OWER_ID = "ownerId";

	public static final String STUDENT_ID = "studentId";

	public static final String NUMBER = "number";

	@Column(name = STUDENT_ID)
	public String studentId;

	@Column(name = OWER_ID)
	public String ownerId;

	@Column(name = NUMBER)
	public Integer number;

	@Column(name = PLAN_ID)
	public String planId;

	@Column(name = SCHOOL_ID)
	public String schoolId;

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

}
