package com.wx.school.bean.plan;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.eweblib.annotation.column.FloatColumn;
import com.eweblib.annotation.column.IntegerColumn;
import com.eweblib.bean.BaseEntity;

@Table(name = SchoolBaoMingPlan.TABLE_NAME)
public class SchoolBaoMingPlan extends BaseEntity {

	public static final String MIDDAY_REST_HOURS = "middayRestHours";

	public static final String REST_HOURS = "restHours";

	public static final String LAST_MERGE_SIGN_UP_COUNT = "lastMergeSignUpCount";

	public static final String PLACE = "place";

	public static final String SIGN_UP_COUNT = "signUpCount";

	public static final String SKIP_MINUTES = "skipMinutes";

	public static final String KEEP_ON_MINUTES = "keepOnMinutes";

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

	@Column(name = KEEP_ON_MINUTES)
	@IntegerColumn
	public Integer keepOnMinutes;

	@Column(name = SKIP_MINUTES)
	@IntegerColumn
	public Integer skipMinutes;

	@Column(name = SIGN_UP_COUNT)
	public Integer signUpCount;

	@Column(name = LAST_MERGE_SIGN_UP_COUNT)
	public Integer lastMergeSignUpCount;

	@Column(name = MIDDAY_REST_HOURS)
	@FloatColumn
	public Float middayRestHours;

	@Column(name = PLACE)
	public String place;

	public String name;

	public Float getMiddayRestHours() {
		return middayRestHours;
	}

	public void setMiddayRestHours(Float middayRestHours) {
		this.middayRestHours = middayRestHours;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public Integer getKeepOnMinutes() {
		return keepOnMinutes;
	}

	public void setKeepOnMinutes(Integer keepOnMinutes) {
		this.keepOnMinutes = keepOnMinutes;
	}

	public Integer getSkipMinutes() {
		return skipMinutes;
	}

	public void setSkipMinutes(Integer skipMinutes) {
		this.skipMinutes = skipMinutes;
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
