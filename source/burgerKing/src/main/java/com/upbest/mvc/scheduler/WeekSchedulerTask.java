package com.upbest.mvc.scheduler;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("weekSchedulerTask")
public class WeekSchedulerTask extends DefaultSchedulerTask {
	
	@Override
	protected int getWorkType() {
		return 1;
	}

	@Override
	protected Date getStartTime() {
		DateTime curDate = new DateTime();
		return curDate.withDayOfWeek(7).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDate();
	}

	@Override
	protected Date getEndTime() {
		DateTime curDate = new DateTime();
		DateTime nextWeek = curDate.plusWeeks(1);
		return nextWeek.withDayOfWeek(7).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDate();
	}

}
