package com.wx.school.bean.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = Student.TABLE_NAME)
public class Student extends BaseEntity {


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
;

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
