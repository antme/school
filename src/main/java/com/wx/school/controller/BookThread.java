package com.wx.school.controller;

import java.util.UUID;

import com.eweblib.util.EweblibUtil;
import com.wx.school.bean.school.StudentNumber;
import com.wx.school.service.ISchoolService;

public class BookThread extends Thread {

	private ISchoolService scs;

	public BookThread(ISchoolService scs) {
		this.scs = scs;
	}

	public void run() {
		while (true) {
			StudentNumber sn = new StudentNumber();
			sn.setSchoolId(UUID.randomUUID().toString());
			sn.setStudentId(UUID.randomUUID().toString());
			int generateRandom = EweblibUtil.generateRandom(1, 10);
			if (generateRandom > 5) {
				sn.setPlanId("68a97dfc-c838-4d8d-8e27-84f5b5b1d8e6");
			} else if (generateRandom > 0) {
				sn.setPlanId("c4b503d7-c42b-4658-b0c8-684ee1ff705d");
			} 
			scs.bookSchool(sn);
		}
	}
}
