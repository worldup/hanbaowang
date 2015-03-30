package com.upbest.mvc.report;

import org.apache.commons.lang.StringUtils;

import com.upbest.mvc.service.IExamService;

public class ExamTestReportFactory {
	public static ExamTestReport findExamTestReportByExamType(String type,IExamService service){
		ExamTestReport report = null;
		
		if(type.contains("现金稽核")){
			report = new CashAuditExamTestReport(service);
		}else if(StringUtils.containsIgnoreCase(type, "Guest")){
			report = new GuestIsKingExamTestReport(service);
		}else if(StringUtils.containsIgnoreCase(type, "REV")){
			report = new RevSelfExamTestReport(service);
		}else if(type.contains("肩并肩")){
			report = new SideBySideExamTestReport(service);
		}else if(type.contains("打烊")){
			report = new DayangExamTestReport(service);
		}
		else
		report=new DayangExamTestReport(service);
		
		return report;
	}
}
