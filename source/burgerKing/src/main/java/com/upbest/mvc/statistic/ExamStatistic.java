package com.upbest.mvc.statistic;

/**
 * 问卷统计接口
 * @author QunZheng
 *
 */
public interface ExamStatistic {
	/**
	 * 对问卷进行统计
	 * @param userId
	 * @param examId
	 * @return
	 */
	Object statistic(int userId,int examId);
}
