package com.wx.school.bean.school;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = School.TABLE_NAME)
public class School extends BaseEntity {

	public static final String END_TIME = "endTime";

	public static final String START_TIME = "startTime";

	public static final String TAKE_NUMBER_DATE = "takeNumberDate";

	public static final String ONLY_FOR_VIP = "onlyForVip";

	public static final String TABLE_NAME = "School";

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

	public int takeStatus;

	public int getTakeStatus() {
		return takeStatus;
	}

	public void setTakeStatus(int takeStatus) {
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
