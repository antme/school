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
			if (generateRandom > 7) {
				sn.setPlanId("373db98e-b500-4b6f-830f-2db5af2a6b64");
			} else if (generateRandom > 4) {
				sn.setPlanId("ac484752-9751-4bf0-be40-7a8a9f74f137");
			} else {
				sn.setPlanId("ff929aa2-a5aa-402e-ab37-b2980b820a05");
			}
			scs.bookSchool(sn);
		}
	}
}
