package com.wx.school.bean.user;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.annotation.column.BooleanColumn;
import com.eweblib.bean.BaseEntity;

@Table(name = User.TABLE_NAME)
public class User extends BaseEntity {


	public static final String USER_TYPE = "userType";

	public static final String USER_TYPE_PARENT = "parent";

	public static final String REMARK = "remark";

	public static final String EMAIL = "email";

	public static final String IS_ADMIN = "isAdmin";

	public static final String TABLE_NAME = "User";

	public static final String PASSWORD = "password";

	public static final String USER_NAME = "userName";

	public static final String MOBILE_NUMBER = "mobileNumber";

	public static final String STATUS = "status";

	public static final String NAME = "name";

	public static final String BACKK_LOGIN = "back_login";

	@Column(name = USER_NAME, unique = true)
	public String userName;

	@Column(name = NAME)
	public String name;

	@Column(name = PASSWORD)
	public String password;

	@Column(name = MOBILE_NUMBER)
	public String mobileNumber;

	@Column(name = EMAIL)
	public String email;

	@Column(name = STATUS)
	public Integer status;

	@Column(name = IS_ADMIN)
	@BooleanColumn
	public Boolean isAdmin;

	@Column(name = REMARK)
	public String remark;

	@Column(name = USER_TYPE)
	public String userType;

	@BooleanColumn
	public Boolean ajax_session;

	public String password2;

	public String validCode;

	public String imgCode;


	public Boolean getAjax_session() {
		return ajax_session;
	}

	public void setAjax_session(Boolean ajax_session) {
		this.ajax_session = ajax_session;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String userPassword) {
		this.password2 = userPassword;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

}
