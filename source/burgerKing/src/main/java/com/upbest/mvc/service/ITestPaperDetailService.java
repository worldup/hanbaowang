package com.upbest.mvc.service;

import java.util.List;

import com.upbest.mvc.entity.BActionPlan;
import com.upbest.mvc.entity.BProblemAnalysis;
import com.upbest.mvc.entity.BTestHeadingRela;
import com.upbest.mvc.entity.BTestPaper;
import com.upbest.mvc.entity.BTestPaperDetail;
import com.upbest.mvc.vo.TestPaperDetailVO;

public interface ITestPaperDetailService {

    /**
     * 
     * @Title 		   	函数名称：	saveTestPaperDetail
     * @Description   	功能描述：	保存问卷信息
     * @param 		   	参          数：	
     * @return          返  回   值：	void  
     * @throws
     */
    void saveTestPaperDetail(List<BTestPaperDetail> list, BTestPaper testPaper, List<BTestHeadingRela> testRela, List<BProblemAnalysis> proAnaList, List<BActionPlan> actionPlList);

    /**
     * 查询测评详细结果
     * @param userId
     * @param examId
     * @return
     */
    List<TestPaperDetailVO> queryTestPaperDetails(Integer userId, Integer examId);
}
