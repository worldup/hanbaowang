package com.upbest.mvc.service;

import java.util.List;

import com.upbest.mvc.entity.BProblemAnalysis;

/**
 * 
 * @ClassName   类  名   称：	IBProblemAnalysisService.java
 * @Description 功能描述：	问题分析
 * @author      创  建   者：	<A HREF="jhzhao@upbest-china.com">jhzhap</A>	
 * @date        创建日期：	2014年10月9日上午9:45:21
 */
public interface IBProblemAnalysisService {
  
    /**
     * 
     * @Title 		   	函数名称：	saveOrUpdate
     * @Description   	功能描述：	保存问题分析
     * @param 		   	参          数：	
     * @return          返  回   值：	BProblemAnalysis  
     * @throws
     */
    BProblemAnalysis saveOrUpdate(BProblemAnalysis entity);
    
    /**
     * 
     * @Title 		   	函数名称：	queryByTId
     * @Description   	功能描述：	根据测评表Id查询问题分析
     * @param 		   	参          数：	
     * @return          返  回   值：	List<BProblemAnalysis>  
     * @throws
     */
    List<BProblemAnalysis> queryByTId(Integer tId);
    
    
}
