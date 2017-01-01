package com.wx.school.bean.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = SmsLog.TABLE_NAME)
public class SmsLog extends BaseEntity {

	public static final String FAILED_COUNT = "failedCount";

	public static final String SUCCESS_COUNT = "successCount";

	public static final String PLACE = "place";

	public static final String END_TIME = "endTime";

	public static final String START_TIME = "startTime";

	public static final String SIGN_DATE = "signDate";

	public static final String SUCCESS_MOBILE_NUMBERS = "successMobileNumbers";

	public static final String FAILED_MOBILE_NUMBERS = "failedMobileNumbers";

	public static final String TOTAL_SEND = "totalSend";

	public static final String END_NUMBER = "endNumber";

	public static final String START_NUMBER = "startNumber";

	public static final String SCHOOL_ID = "schoolId";

	public static final String SCHOOL_NAME = "schoolName";

	public static final String TABLE_NAME = "SmsLog";

	@Column(name = SCHOOL_NAME)
	public String schoolName;

	@Column(name = SCHOOL_ID)
	public String schoolId;

	@Column(name = START_NUMBER)
	public Integer startNumber;

	@Column(name = END_NUMBER)
	public Integer endNumber;

	@Column(name = TOTAL_SEND)
	public Integer totalSend;

	@Column(name = SUCCESS_COUNT)
	public Integer successCount;

	@Column(name = FAILED_COUNT)
	public Integer failedCount;

	@Column(name = FAILED_MOBILE_NUMBERS)
	public String failedMobileNumbers;

	@Column(name = SUCCESS_MOBILE_NUMBERS)
	public String mobileNumbers;

	@Column(name = SIGN_DATE)
	public Date signDate;

	@Column(name = START_TIME)
	public String startTime;

	@Column(name = END_TIME)
	public String endTime;

	@Column(name = PLACE)
	public String place;

	public String parentName;

	public Integer number;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public Integer getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(Integer failedCount) {
		this.failedCount = failedCount;
	}

	public String getMobileNumbers() {
		return mobileNumbers;
	}

	public void setMobileNumbers(String mobileNumbers) {
		this.mobileNumbers = mobileNumbers;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
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

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
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

	public Integer getStartNumber() {
		return startNumber;
	}

	public void setStartNumber(Integer startNumber) {
		this.startNumber = startNumber;
	}

	public Integer getEndNumber() {
		return endNumber;
	}

	public void setEndNumber(Integer endNumber) {
		this.endNumber = endNumber;
	}

	public Integer getTotalSend() {
		return totalSend;
	}

	public void setTotalSend(Integer totalSend) {
		this.totalSend = totalSend;
	}

	public String getFailedMobileNumbers() {
		return failedMobileNumbers;
	}

	public void setFailedMobileNumbers(String failedMobileNumbers) {
		this.failedMobileNumbers = failedMobileNumbers;
	}

}
