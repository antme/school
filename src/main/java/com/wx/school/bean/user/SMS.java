package com.wx.school.bean.user;

import javax.persistence.Column;

import com.eweblib.bean.BaseEntity;

public class SMS extends BaseEntity {

	public static final String MOBILE_NUMBER = "mobileNumber";

	@Column(name = MOBILE_NUMBER)
	public String mobileNumber;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
