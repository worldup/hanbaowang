package com.upbest.mvc.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upbest.mvc.service.IExamService;

public class SendEmailThread extends Thread{
	
	private IExamService service;
	private String email;
	private int testPaperId;
	private String fullServerPath;
	private static final Logger logger = LoggerFactory.getLogger(SendEmailThread.class);
	
	public SendEmailThread(String fullServerPath,IExamService service, String email, int testPaperId) {
		super();
		this.service = service;
		this.email = email;
		this.testPaperId = testPaperId;
        this.fullServerPath=fullServerPath;
	}

	@Override
	public void run() {
		try {
			service.generateExcelAndEmail(fullServerPath,testPaperId, email);
		} catch (Exception e) {
			logger.error("邮件发送失败:" + e.getMessage());
		}
	}
	
}
