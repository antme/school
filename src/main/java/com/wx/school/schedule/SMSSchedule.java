package com.wx.school.schedule;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wx.school.service.message.IMessageService;

@Service
public class SMSSchedule {
	private static Logger log = LogManager.getLogger(SMSSchedule.class);

	@Autowired
	private IMessageService smsService;

	@Scheduled(cron = "0 0 0/1 * * ?")
	@Async
	public void run() {
		smsService.sendSchoolTakeNumberNotice();
		log.info("SMSSchedule called");
	}
}
