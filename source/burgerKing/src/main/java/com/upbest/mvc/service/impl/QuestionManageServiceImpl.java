package com.upbest.mvc.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.upbest.mvc.constant.Constant.QuestionType;
import com.upbest.mvc.entity.BExBaseInfo;
import com.upbest.mvc.entity.BQuestion;
import com.upbest.mvc.repository.factory.BExBaseInfoRespository;
import com.upbest.mvc.repository.factory.QuestionRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.IQuestionManageService;
import com.upbest.mvc.vo.QuestionVO;
import com.upbest.mvc.vo.SelectionVO;
import com.upbest.utils.DataType;
import com.upbest.utils.PageModel;

@Service
public class QuestionManageServiceImpl implements IQuestionManageService {
	
	@Autowired
	private QuestionRespository respository;
	
	@Inject
    protected BExBaseInfoRespository baseRepository;
	
	@Autowired
    private CommonDaoCustom<Object[]> common;

	@Override
	public void saveOrUpdateQuestion(BQuestion question) {
		respository.save(question);
	}

	@Override
	public void deleteQuestion(int questionId) {
		respository.delete(questionId);
	}

	@Override
	public PageModel<QuestionVO> queryQuestions(Integer quesType, Integer ids, Integer preids,Pageable pageable) {

		PageModel<QuestionVO> result = new PageModel<QuestionVO>();
		
		StringBuffer sql=new StringBuffer();
        List<Object> params=new ArrayList<Object>();
        sql.append("  SELECT t.id,                      ");
        sql.append("         t.q_value,                 ");
        sql.append("         u.b_value,                 ");
        sql.append("         t.q_content,               ");
        sql.append("         t.q_answer,                ");
        sql.append("         t.q_standard,              ");
        sql.append("         t.q_remark,                ");
        sql.append("         t.q_type,                   ");
        sql.append("         t.is_upload_pic,            ");
        sql.append("         t.serial_number,            ");
        sql.append(" u.id as moduleId ");
        sql.append("    FROM BK_QUESTION t              ");
        sql.append("    left join BK_EX_BASE_INFO u     ");
        sql.append("    on t.q_module = u.id            ");
        sql.append("    where 1 = 1                     ");
        //add by xubin 2014-9-28
        if(quesType != null && ids != null){
            sql.append("    and t.q_type = ? and t.q_module = ?         ");
            params.add(quesType);
            params.add(ids);
        }else if(quesType != null && ids == null){
            sql.append("    and t.q_type = ?          ");
            params.add(quesType);
        }else if(quesType == null && ids != null){
            sql.append("    and t.q_module = ?          ");
            params.add(ids);
        }
        if(preids != null){
            sql.append("    and t.q_module in ( select b.id from BK_EX_BASE_INFO b where b.b_pid = ? )       ");
            params.add(preids);
        }
        //end
        Page<Object[]> page = common.queryBySql(sql.toString(), params, pageable);
        result.setPage(page.getNumber() + 1);
        result.setRows(getQuestionInfo(page.getContent()));
        result.setPageSize(page.getSize());
        result.setRecords((int)page.getTotalElements());
		return result;
	}
	
	private List<QuestionVO> getQuestionInfo(List<Object[]> content) {
		List<QuestionVO> questions = new ArrayList<QuestionVO>();
		for (Object[] objAry : content) {
			QuestionVO vo = new QuestionVO();
			
			vo.setId(DataType.getAsInt(objAry[0]));
			vo.setQvalue(DataType.getAsInt(objAry[1]));
			vo.setModelName(DataType.getAsString(objAry[2]));
			vo.setQcontent(DataType.getAsString(objAry[3]));
			vo.setQanswer(DataType.getAsString(objAry[4]));
			vo.setQstandard(DataType.getAsString(objAry[5]));
			vo.setQremark(DataType.getAsString(objAry[6]));
			vo.setQuestionType(QuestionType.getName(DataType.getAsInt(objAry[7])));
			//add by xubin 2014-9-28  页面试题类型判断
			vo.setQtype(DataType.getAsInt(objAry[7]));
			vo.setIsUploadPic(DataType.getAsInt(objAry[8]));
			vo.setSerialNumber(DataType.getAsString(objAry[9]));
			vo.setModuleId(DataType.getAsString(objAry[10]));
			questions.add(vo);
		}
		return questions;
	}
	
	@Override
	public List<QuestionVO> queryQuestion(int questionId) {
	    
	    StringBuffer sql=new StringBuffer();
        List<Object> params=new ArrayList<Object>();
        sql.append("  SELECT t.id,                       ");
        sql.append("         t.q_value,             ");
        sql.append("         u.b_value,       ");
        sql.append("         t.q_content,       ");
        sql.append("         t.q_answer,               ");
        sql.append("         t.q_standard,                ");
        sql.append("         t.q_remark,                 ");
        sql.append("         t.q_type,           ");
        sql.append("         t.is_upload_pic,             ");
        sql.append("         t.serial_number,             ");
        sql.append(" u.id as moduleId ");
        sql.append("    FROM BK_QUESTION t  left join BK_EX_BASE_INFO u on  t.q_module = u.id           ");
        sql.append("     where t.id = ?            ");
        params.add(questionId);
        List<Object[]> list = common.queryBySql(sql.toString(), params);
        
		return getQuestionInfo(list);
	}
	
	public List<SelectionVO> queryQuestionType(){
		QuestionType[] types = QuestionType.values();
		List<SelectionVO> list = new ArrayList<SelectionVO>();
		
		for (QuestionType type : types) {
			list.add(new SelectionVO(type.getName(), type.getValue() + ""));
		}
		return list;
	}

}
