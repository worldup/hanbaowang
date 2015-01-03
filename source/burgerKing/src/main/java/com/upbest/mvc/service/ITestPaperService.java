package com.upbest.mvc.service;

import org.springframework.data.domain.Pageable;

import com.upbest.mvc.entity.BTestPaper;
import com.upbest.mvc.vo.TestPaperVO;
import com.upbest.utils.PageModel;

public interface ITestPaperService {
    BTestPaper saveTestPaper(BTestPaper testPaper);
    
    /**
     * 根据问卷查询评测信息
     * @param examId
     * @return
     */
    PageModel<TestPaperVO> queryTestPaperInfos(Integer examId,Pageable pageable,String shopName);
    
    BTestPaper queryTestPaper(Integer paperId);
}
