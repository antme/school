package com.wx.school.schedule;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wx.school.service.ICacheService;

@Service
public class CacheSchedule {
	private static Logger log = LogManager.getLogger(CacheSchedule.class);

	@Autowired
	private ICacheService cs;

	@Scheduled(cron = "0 0/5 * * * ?")
	@Async
	public void run() {
		cs.refreshCach();
		log.debug("CacheSchedule called");
	}
}
