package com.upbest.mvc.scheduler;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class QuarterSchedulerTask extends DefaultSchedulerTask {
	
	@Override
	protected int getWorkType() {
		return 3;
	}

	@Override
	protected Date getStartTime() {
		DateTime curDate = new DateTime();
		DateTime nextMonth = curDate.plusMonths(1);
		return nextMonth.withDayOfMonth(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDate();
	}

	@Override
	protected Date getEndTime() {
		return new DateTime(getStartTime().getTime()).plusMonths(3).toDate();
	}
    
}
