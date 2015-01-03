package com.upbest.mvc.scheduler;

import org.springframework.stereotype.Component;

@Component
public class PmCheckTodayTask extends CheckTodayTask {
	@Override
	protected String getTitleTip() {
		return "今天还有以下任务没有完成";
	}
}
