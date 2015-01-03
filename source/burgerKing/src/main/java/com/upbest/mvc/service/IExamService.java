package com.upbest.mvc.service;

import java.io.OutputStream;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upbest.mvc.entity.BExHeadingRela;
import com.upbest.mvc.vo.ExamDetailInfoVO;
import com.upbest.mvc.vo.ExamInfoVO;
import com.upbest.mvc.vo.ExamSheetVO;
import com.upbest.mvc.vo.ExamTypeVO;
import com.upbest.mvc.vo.QuestionVO;
import com.upbest.mvc.vo.SelectionVO;
import com.upbest.utils.PageModel;

public interface IExamService {
	/**
	 * 查询问卷类型
	 * @return
	 */
	public List<SelectionVO> queryExamType();
	
	/**
	 * 
	 * @Title 		   	函数名称：	saveExamAssign
	 * @Description   	功能描述：	下发考卷任务
	 * @param 		   	参          数：	
	 * @return          返  回   值：	void  
	 * @throws
	 */
	public void saveExamAssign(Integer examId,List<String> userIds);
	
	/**
	 * 添加考卷
	 * @param examInfoVO
	 */
	public void addExamInfo(ExamInfoVO examInfoVO);
	
	/**
	 * 查询考卷信息
	 * @param pageable
	 * @return
	 */
	public PageModel<ExamInfoVO> findExamInfo(Pageable pageable,Integer userId);
	
	/**
	 * 查询考卷的全部信息
	 * @param examId
	 * @return
	 */
	public ExamInfoVO findExamInfo(Integer examId);
	
	/**
	 * 查询问卷详细信息
	 * @param examId
	 * @return
	 */
	public ExamDetailInfoVO findExamDetailInfo(Integer examId);
	
	/**
	 * 查询用户详细测评结果信息
	 * @param userid
	 * @param examId
	 * @return
	 */
	public ExamDetailInfoVO findAfterTestExamDetaiInfo(Integer testPaperId);
	
	/**
	 * 查询考卷基本信息
	 * @param examId
	 * @return
	 */
	public ExamInfoVO findExamBasicInfo(Integer examId); 
	
	/**
	 * 删除问卷
	 * @param examId
	 */
	public void deleteExamPaper(int examId);
	
	/**
	 * 判断问卷是否已经下发
	 * @param examId
	 * @return
	 */
	public boolean hasAssign(int examId);
	
	public void saveExHeadRel(BExHeadingRela rela);
	
	public void saveExHeadRel(List<BExHeadingRela> rela);
	
	public void deleteExHeadRelByModulerId(Integer modulerId);
	
	public List<SelectionVO> findHeadInfo(int type);
	
	public List<Integer> findModelType(int moduleId);
	
	public List<QuestionVO> findQuestions(Integer examId);
	
    List<ExamSheetVO> queryFacility(Integer[] examid);

    void addEaxmToExcel(Integer[] examid, OutputStream out) throws Exception;

	public List<Integer> findHeadValues(int examId);

	public double findLastExamScore(Integer shopId, Integer examType);

	public List<String> findRepeatLostScoreItem(Integer shopId);

	public List<SelectionVO> queryFilterExamType();
	
	public List<ExamTypeVO> queryExamType(String storeId);
	
	public Page<Object[]> queryLoseSts(String examType,String storeName,Pageable requestPage);
	
	public Page<Object[]> queryLoseStsDetail(String id,String daqu,String sheng,String shi,String quxian,
            String userIds,String storeName,Pageable requestPage);
	
    public String queryDescByTid(String tid,String isKey);
    
    public String queryKeyRela(String moduleId,String qId,String yOrN);
    
    public void generateExcelAndEmail(int testPaperId,String... emails)  throws Exception ;
    
    void addEaxmToExcelLose(String examid,OutputStream out) throws Exception;
    
    void addEaxmToExcelLoseDetail(String id,String ids, OutputStream out) throws Exception;
    
    void delExamResult(String id);
}

