package com.wx.school.bean.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = Person.TABLE_NAME)
public class Person extends BaseEntity {

	public static final String PARENT_ID = "parentId";

	public static final String SEX = "sex";

	private static final String PERSON_TYPE = "personType";

	public static final String OWNER_ID = "ownerId";

	public static final String BIRTH_DAY = "birthday";

	public static final String TABLE_NAME = "Person";

	public static final String MOBILE_NUMBER = "mobileNumber";

	public static final String NAME = "name";

	public static final String PERSON_TYPE_PARENT = "parent";
	public static final String PERSON_TYPE_CHILD = "child";

	@Column(name = NAME)
	public String name;

	@Column(name = MOBILE_NUMBER)
	public String mobileNumber;

	@Column(name = PERSON_TYPE)
	public String personType;

	@Column(name = BIRTH_DAY)
	public Date birthday;

	@Column(name = OWNER_ID)
	public String ownerId;

	@Column(name = SEX)
	public String sex;

	@Column(name = PARENT_ID)
	public String parentId;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
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
