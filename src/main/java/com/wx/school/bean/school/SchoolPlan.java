package com.wx.school.bean.school;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = SchoolPlan.TABLE_NAME)
public class SchoolPlan extends BaseEntity {

	public static final String SCHOOL_NAME = "schoolName";

	public static final String SCHOOL_ID = "schoolId";

	public static final String IS_DISPLAY_FOR_WX = "isDisplayForWx";

	public static final String TAKE_STATUS = "takeStatus";

	public static final String END_TIME = "endTime";

	public static final String START_TIME = "startTime";

	public static final String TAKE_NUMBER_DATE = "takeNumberDate";

	public static final String ONLY_FOR_VIP = "onlyForVip";

	public static final String TABLE_NAME = "SchoolPlan";

	public static final String NAME = "name";

	@Column(name = NAME)
	public String name;

	@Column(name = ONLY_FOR_VIP)
	public Boolean onlyForVip;

	@Column(name = TAKE_NUMBER_DATE)
	public Date takeNumberDate;

	@Column(name = START_TIME)
	public String startTime;

	@Column(name = END_TIME)
	public String endTime;

	@Column(name = TAKE_STATUS)
	public Integer takeStatus;

	@Column(name = IS_DISPLAY_FOR_WX)
	public Boolean isDisplayForWx;

	@Column(name = SCHOOL_ID)
	public String schoolId;
	
	@Column(name = SCHOOL_NAME)
	public String schoolName;
	

	// @Column(name = TAKE_NUMBER_DATE)
	// public Date startDate;
	//
	// @Column(name = TAKE_NUMBER_DATE)
	// public Date endDate;
	

	public Boolean getIsDisplayForWx() {
		return isDisplayForWx;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public void setIsDisplayForWx(Boolean isDisplayForWx) {
		this.isDisplayForWx = isDisplayForWx;
	}

	public Integer getTakeStatus() {
		return takeStatus;
	}

	public void setTakeStatus(Integer takeStatus) {
		this.takeStatus = takeStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getOnlyForVip() {
		return onlyForVip;
	}

	public void setOnlyForVip(Boolean onlyForVip) {
		this.onlyForVip = onlyForVip;
	}

	public Date getTakeNumberDate() {
		return takeNumberDate;
	}

	public void setTakeNumberDate(Date takeNumberDate) {
		this.takeNumberDate = takeNumberDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
