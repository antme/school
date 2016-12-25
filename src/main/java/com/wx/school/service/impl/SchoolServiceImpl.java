package com.wx.school.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.service.AbstractService;
import com.eweblib.util.DateUtil;
import com.wx.school.bean.school.School;
import com.wx.school.service.ISchoolService;

@Service(value = "school")
public class SchoolServiceImpl extends AbstractService implements ISchoolService {

	@Override
	public List<School> listSchools() {

		DataBaseQueryBuilder query = new DataBaseQueryBuilder(School.TABLE_NAME);
		query.limitColumns(new String[] { School.ID, School.NAME, School.ONLY_FOR_VIP, School.TAKE_NUMBER_DATE,
				School.START_TIME, School.END_TIME });

		List<School> list = this.dao.listByQuery(query, School.class);
		for (School school : list) {
			Date date = new Date();

			Date startDate = DateUtil
					.getDateTime(DateUtil.getDateString(school.getTakeNumberDate()) + " " + school.getStartTime());
			Date endDate = DateUtil
					.getDateTime(DateUtil.getDateString(school.getTakeNumberDate()) + " " + school.getEndTime());

			if (date.getTime() < startDate.getTime()) {
				school.setTakeStatus(0);
			} else if (date.getTime() < endDate.getTime()) {
				school.setTakeStatus(1);
			} else {
				school.setTakeStatus(2);
			}

		}

		return list;
	}

	public void addSchool(School school) {
		this.dao.insert(school);
	}

}
