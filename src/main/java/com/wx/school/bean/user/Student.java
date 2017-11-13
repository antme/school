package com.wx.school.bean.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.annotation.column.BooleanColumn;
import com.eweblib.bean.BaseEntity;

@Table(name = Student.TABLE_NAME)
public class Student extends BaseEntity {

	public static final String SIGN_UP_SCHOOL_ID = "signUpSchoolId";

	public static final String SIGN_UP_PLACE = "signUpPlace";

	public static final String SCHOOL_ID = "schoolId";

	public static final String BIRDARY_MONTH = "birdaryMonth";

	public static final String BIRDARY_YEAR = "birdaryYear";

	public static final String REMARK = "remark";

	public static final String SEX = "sex";

	public static final String OWNER_ID = "ownerId";

	public static final String BIRTH_DAY = "birthday";

	public static final String TABLE_NAME = "Student";

	public static final String NAME = "name";

	public static final String IS_VIP = "isVip";

	@Column(name = NAME)
	public String name;

	@Column(name = BIRTH_DAY)
	public String birthday;

	@Column(name = OWNER_ID)
	public String ownerId;

	@Column(name = SEX)
	public String sex;

	@Column(name = REMARK)
	public String remark;

	@Column(name = IS_VIP)
	@BooleanColumn
	public Boolean isVip;

	@Column(name = BIRDARY_YEAR)
	public Integer birdaryYear;

	@Column(name = BIRDARY_MONTH)
	public Integer birdaryMonth;

	@Column(name = SCHOOL_ID)
	public String schoolId;

	@Column(name = SIGN_UP_PLACE)
	public String signUpPlace;

	@Column(name = SIGN_UP_SCHOOL_ID)
	public String signUpSchoolId;

	public String studentName;

	public String schoolName;

	public Boolean hasNumber = false;

	public String parentName;

	public String parentMobileNumber;

	public Date parentCreatedOn;

	public String sexCn;
	public Integer number;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSignUpPlace() {
		return signUpPlace;
	}

	public void setSignUpPlace(String signUpPlace) {
		this.signUpPlace = signUpPlace;
	}

	public String getSignUpSchoolId() {
		return signUpSchoolId;
	}

	public void setSignUpSchoolId(String signUpSchoolId) {
		this.signUpSchoolId = signUpSchoolId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getBirdaryYear() {
		return birdaryYear;
	}

	public void setBirdaryYear(Integer birdaryYear) {
		this.birdaryYear = birdaryYear;
	}

	public Integer getBirdaryMonth() {
		return birdaryMonth;
	}

	public void setBirdaryMonth(Integer birdaryMonth) {
		this.birdaryMonth = birdaryMonth;
	}

	public Boolean getIsVip() {
		return isVip;
	}

	public void setIsVip(Boolean isVip) {
		this.isVip = isVip;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSexCn() {
		return sexCn;
	}

	public void setSexCn(String sexCn) {
		this.sexCn = sexCn;
	}

	public Boolean getHasNumber() {
		return hasNumber;
	}

	public void setHasNumber(Boolean hasNumber) {
		this.hasNumber = hasNumber;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentMobileNumber() {
		return parentMobileNumber;
	}

	public void setParentMobileNumber(String parentMobileNumber) {
		this.parentMobileNumber = parentMobileNumber;
	}

	public Date getParentCreatedOn() {
		return parentCreatedOn;
	}

	public void setParentCreatedOn(Date parentCreatedOn) {
		this.parentCreatedOn = parentCreatedOn;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthDay) {
		this.birthday = birthDay;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
