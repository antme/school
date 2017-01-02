package com.wx.school.bean;

import com.eweblib.annotation.column.BooleanColumn;
import com.eweblib.annotation.column.IntegerColumn;
import com.wx.school.bean.user.User;

public class SearchVO extends User {

	public String parentName;

	public String schoolName;

	public String schoolId;

	@IntegerColumn
	public Integer number;

	@BooleanColumn
	public Boolean isVip;

	public Boolean getIsVip() {
		return isVip;
	}

	public void setIsVip(Boolean isVip) {
		this.isVip = isVip;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
