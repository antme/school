package com.wx.school.bean.plan;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.bean.BaseEntity;

@Table(name = SchoolBaoMingPlan.TABLE_NAME)
public class SchoolBaoMingPlan extends BaseEntity {

	public static final String LAST_MERGE_SIGN_UP_COUNT = "lastMergeSignUpCount";

	public static final String PLACE = "place";

	public static final String SIGN_UP_COUNT = "signUpCount";

	public static final String SKIP_HOURS = "skipHours";

	public static final String KEEP_ON_HOURS = "keepOnHours";

	public static final String START_TIME = "startTime";

	public static final String SIGN_UP_DATE = "signUpDate";

	public static final String SCHOOL_ID = "schoolId";

	public static final String TABLE_NAME = "SchoolBaoMingPlan";

	@Column(name = SCHOOL_ID)
	public String schoolId;

	@Column(name = SIGN_UP_DATE)
	public Date signUpDate;

	@Column(name = START_TIME)
	public String startTime;

	@Column(name = KEEP_ON_HOURS)
	public Float keepOnHours;

	@Column(name = SKIP_HOURS)
	public Float skipHours;

	@Column(name = SIGN_UP_COUNT)
	public Integer signUpCount;

	@Column(name = LAST_MERGE_SIGN_UP_COUNT)
	public Integer lastMergeSignUpCount;

	@Column(name = PLACE)
	public String place;

	public Integer getLastMergeSignUpCount() {
		return lastMergeSignUpCount;
	}

	public void setLastMergeSignUpCount(Integer lastMergeSignUpCount) {
		this.lastMergeSignUpCount = lastMergeSignUpCount;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public Date getSignUpDate() {
		return signUpDate;
	}

	public void setSignUpDate(Date signUpDate) {
		this.signUpDate = signUpDate;
	}

	public Float getKeepOnHours() {
		return keepOnHours;
	}

	public void setKeepOnHours(Float keepOnHours) {
		this.keepOnHours = keepOnHours;
	}

	public Float getSkipHours() {
		return skipHours;
	}

	public void setSkipHours(Float skipHours) {
		this.skipHours = skipHours;
	}

	public Integer getSignUpCount() {
		return signUpCount;
	}

	public void setSignUpCount(Integer signUpCount) {
		this.signUpCount = signUpCount;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

}
