package com.wx.school.bean;

import java.util.Date;

import com.eweblib.bean.BaseEntity;

public class StudentPlanInfo extends BaseEntity {

	public String name;

	public String sex;

	public String birthday;

	public String parentName;

	public String mobileNumber;

	public String studentRegDate;

	public String schoolName;

	public Integer number;

	public String sexCn;

	public Boolean isVip;

	public String isVipStr;

	public String remark;

	public String planId;

	public String studentId;

	public String baomingInfo;

	public String originSchoolName;

	public String getOriginSchoolName() {
		return originSchoolName;
	}

	public void setOriginSchoolName(String originSchoolName) {
		this.originSchoolName = originSchoolName;
	}

	public String getBaomingInfo() {
		return baomingInfo;
	}

	public void setBaomingInfo(String baomingInfo) {
		this.baomingInfo = baomingInfo;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsVipStr() {
		return isVipStr;
	}

	public void setIsVipStr(String isVipStr) {
		this.isVipStr = isVipStr;
	}

	public Boolean getIsVip() {
		return isVip;
	}

	public void setIsVip(Boolean isVip) {
		this.isVip = isVip;
	}

	public String getSexCn() {
		return sexCn;
	}

	public void setSexCn(String sexCn) {
		this.sexCn = sexCn;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getStudentRegDate() {
		return studentRegDate;
	}

	public void setStudentRegDate(String studentRegDate) {
		this.studentRegDate = studentRegDate;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

}
