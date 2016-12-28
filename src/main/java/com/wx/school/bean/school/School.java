package com.wx.school.bean.school;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = School.TABLE_NAME)
public class School extends BaseEntity {


	public static final String TABLE_NAME = "School";

	public static final String NAME = "name";

	@Column(name = NAME)
	public String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

}
