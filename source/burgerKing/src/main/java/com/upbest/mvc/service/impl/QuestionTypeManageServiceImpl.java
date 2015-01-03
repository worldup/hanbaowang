package com.upbest.mvc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.upbest.mvc.constant.Constant.QuestionType;
import com.upbest.mvc.entity.BExHeading;
import com.upbest.mvc.entity.BQuestion;
import com.upbest.mvc.repository.factory.QuestionTypeRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.IQuestionTypeManageService;
import com.upbest.mvc.vo.SelectionVO;
import com.upbest.utils.DataType;
import com.upbest.utils.PageModel;

@Service
public class QuestionTypeManageServiceImpl implements IQuestionTypeManageService {
	
	@Autowired
	private QuestionTypeRespository respository;
	
	
	@Autowired
    private CommonDaoCustom<Object[]> common;

	@Override
	public void saveOrUpdateQuestion(BExHeading question) {
		respository.save(question);
	}

	@Override
	public void deleteQuestion(int questionId) {
		respository.delete(questionId);
	}

	@Override
	public PageModel<BExHeading> queryQuestions(Integer quesType,String hValue, Pageable pageable) {
		PageModel<BExHeading> result = new PageModel<BExHeading>();
		StringBuffer sql=new StringBuffer();
        List<Object> params=new ArrayList<Object>();
        sql.append("  select t.id, t.h_type, t.h_value, t.type from bk_exam_heading t  ");
        sql.append("    where 1 = 1                     ");
        if(quesType != null){
            sql.append("    and t.type = ?          ");
            params.add(quesType);
        }
        if(StringUtils.isNotBlank(hValue)){
            sql.append("    and t.h_value like '%"+hValue+"%'          ");
        }
        Page<Object[]> page = common.queryBySql(sql.toString(), params, pageable);
        result.setPage(page.getNumber() + 1);
        result.setRows(getQuestionInfo(page.getContent()));
        result.setPageSize(page.getSize());
        result.setRecords((int)page.getTotalElements());
		return result;
	}
	
	private List<BExHeading> getQuestionInfo(List<Object[]> content) {
		List<BExHeading> questions = new ArrayList<BExHeading>();
		for (Object[] objAry : content) {
		    BExHeading vo = new BExHeading();
			vo.setId(DataType.getAsInt(objAry[0]));
			vo.sethType(DataType.getAsInt(objAry[1]));
			vo.sethValue(DataType.getAsString(objAry[2]));
			vo.setType(DataType.getAsInt(objAry[3]));
			questions.add(vo);
		}
		return questions;
	}
	
	public BExHeading queryQuestion(int questionId) {
	    return respository.findOne(questionId);
	}
}
