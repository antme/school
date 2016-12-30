package com.wx.school.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

@Service
public class CacheListener implements ApplicationListener<ContextRefreshedEvent> {
	public static Logger logger = LogManager.getLogger(CacheListener.class);

	@Autowired
	private ICacheService cs;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		cs.refreshCach();
	}

}
