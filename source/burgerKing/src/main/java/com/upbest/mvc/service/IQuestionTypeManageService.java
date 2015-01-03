package com.upbest.mvc.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.upbest.mvc.entity.BExHeading;
import com.upbest.mvc.entity.BQuestion;
import com.upbest.mvc.vo.QuestionVO;
import com.upbest.mvc.vo.SelectionVO;
import com.upbest.mvc.vo.TreeVO;
import com.upbest.utils.PageModel;

/**
 * 
 * @ClassName   类  名   称：	IQuestionTypeManageService.java
 * @Description 功能描述：	试题类型维护
 * @author      创  建   者：	<A HREF="jhzhao@upbest-china.com">jhzhap</A>	
 * @date        创建日期：	2014年10月8日下午5:26:31
 */
public interface IQuestionTypeManageService {
	/**
	 * 添加或更新试题
	 * @param question
	 */
	public void saveOrUpdateQuestion(BExHeading question);
	/**
	 * 删除试题
	 * @param questionId
	 */
	public void deleteQuestion(int questionId);
	/**
	 * 查询试题库
	 * @param pageable
	 * @return
	 */

	public PageModel<BExHeading> queryQuestions(Integer quesType,String hValue, Pageable pageable);

	
	/**
	 * 
	 * @param questionId
	 * @return
	 */
	public BExHeading queryQuestion(int questionId);

}
