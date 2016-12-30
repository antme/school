package com.wx.school.bean.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = Student.TABLE_NAME)
public class Student extends BaseEntity {

	public static final String REMARK = "remark";

	public static final String SEX = "sex";

	public static final String OWNER_ID = "ownerId";

	public static final String BIRTH_DAY = "birthday";

	public static final String TABLE_NAME = "Student";

	public static final String NAME = "name";

	@Column(name = NAME)
	public String name;

	@Column(name = BIRTH_DAY)
	public Date birthday;

	@Column(name = OWNER_ID)
	public String ownerId;

	@Column(name = SEX)
	public String sex;

	@Column(name = REMARK)
	public String remark;

	public Boolean hasNumber = false;

	public String parentName;

	public String parentMobileNumber;

	public Date parentCreatedOn;

	public String sexCn;

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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthDay) {
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
