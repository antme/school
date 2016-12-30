package com.wx.school.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.service.AbstractService;
import com.wx.school.bean.school.SchoolPlan;
import com.wx.school.service.CacheListener;
import com.wx.school.service.ICacheService;

@Service("cacheService")
public class CacheServiceImpl extends AbstractService implements ICacheService {
	public static Logger logger = LogManager.getLogger(CacheListener.class);

	public static Map<String, SchoolPlan> shcoolPlanMap = new HashMap<String, SchoolPlan>();

	@Override
	public void refreshCach() {

		List<SchoolPlan> planlist = this.dao.listByQuery(new DataBaseQueryBuilder(SchoolPlan.TABLE_NAME),
				SchoolPlan.class);
		for (SchoolPlan plan : planlist) {
			shcoolPlanMap.put(plan.getId(), plan);
		}
		logger.info("cache refresh called");

	}

}
