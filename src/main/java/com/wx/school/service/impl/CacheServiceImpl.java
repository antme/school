package com.wx.school.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.dbhelper.DataBaseQueryOpertion;
import com.eweblib.service.AbstractService;
import com.wx.school.bean.school.SchoolPlan;
import com.wx.school.service.CacheListener;
import com.wx.school.service.ICacheService;

@Service("cacheService")
public class CacheServiceImpl extends AbstractService implements ICacheService {
	public static Logger logger = LogManager.getLogger(CacheListener.class);

	public static Map<String, SchoolPlan> shcoolPlanMap = new HashMap<String, SchoolPlan>();


	public static List<SchoolPlan> list = null;

	@Override
	public void refreshCach() {
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(SchoolPlan.TABLE_NAME);
		query.limitColumns(new String[] { SchoolPlan.ID, SchoolPlan.NAME, SchoolPlan.ONLY_FOR_VIP,
				SchoolPlan.TAKE_NUMBER_DATE, SchoolPlan.START_TIME, SchoolPlan.END_TIME });
		query.and(DataBaseQueryOpertion.IS_TRUE, SchoolPlan.IS_DISPLAY_FOR_WX);

		List<SchoolPlan> datalist = this.dao.listByQuery(query, SchoolPlan.class);

		list = datalist;

		List<SchoolPlan> planlist = this.dao.listByQuery(new DataBaseQueryBuilder(SchoolPlan.TABLE_NAME),
				SchoolPlan.class);
		for (SchoolPlan plan : planlist) {
			shcoolPlanMap.put(plan.getId(), plan);
		}

		logger.info("cache refresh called");

	}

}
