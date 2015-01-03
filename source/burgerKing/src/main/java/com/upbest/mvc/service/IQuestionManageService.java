package com.upbest.mvc.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.upbest.mvc.entity.BQuestion;
import com.upbest.mvc.vo.QuestionVO;
import com.upbest.mvc.vo.SelectionVO;
import com.upbest.mvc.vo.TreeVO;
import com.upbest.utils.PageModel;

/**
 * 试题管理模块
 * @author QunZheng
 *
 */
public interface IQuestionManageService {
	/**
	 * 添加或更新试题
	 * @param question
	 */
	public void saveOrUpdateQuestion(BQuestion question);
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

	public PageModel<QuestionVO> queryQuestions(Integer quesType, Integer ids, Integer preids, Pageable pageable);

	
	/**
	 * 
	 * @param questionId
	 * @return
	 */
	public List<QuestionVO> queryQuestion(int questionId);
	
	/**
	 * 获取所有的试题类型
	 * @return
	 */
	public List<SelectionVO> queryQuestionType();

	
//	List<TreeVO> getUrVOListMessage(Integer userid);

}
