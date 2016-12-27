package com.wx.school.bean.user;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = SMS.TABLE_NAME)
public class SMS extends BaseEntity {

	public static final String SMS_TYPE = "smsType";

	public static final String TABLE_NAME = "SMS";

	public static final String VALID_CODE = "validCode";

	public static final String MOBILE_NUMBER = "mobileNumber";

	@Column(name = MOBILE_NUMBER)
	public String mobileNumber;

	@Column(name = VALID_CODE)
	public String validCode;

	@Column(name = SMS_TYPE)
	public Integer smsType;

	public Integer getSmsType() {
		return smsType;
	}

	public void setSmsType(Integer smsType) {
		this.smsType = smsType;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
