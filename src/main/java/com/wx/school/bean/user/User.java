package com.wx.school.bean.user;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.annotation.column.BooleanColumn;
import com.eweblib.bean.BaseEntity;

@Table(name = User.TABLE_NAME)
public class User extends BaseEntity {

	public static final String REMARK = "remark";

	public static final String EMAIL = "email";

	public static final String IS_ADMIN = "isAdmin";

	public static final String GROUP_ID = "groupId";

	public static final String TABLE_NAME = "User";

	public static final String PASSWORD = "password";

	public static final String USER_NAME = "userName";

	public static final String MOBILE_NUMBER = "mobileNumber";

	public static final String STATUS = "status";

	public static final String NAME = "name";

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

	@Column(name = GROUP_ID)
	public String groupId;

	@Column(name = IS_ADMIN)
	@BooleanColumn
	public Boolean isAdmin;

	@Column(name = REMARK)
	public String remark;

	public String userPassword;

	public String imgCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

}
