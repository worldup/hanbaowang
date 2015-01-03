package com.upbest.mvc.service;

import java.util.List;

import com.upbest.mvc.entity.BActionPlan;
/**
 * 
 * @ClassName   类  名   称：	IBActionPlanService.java
 * @Description 功能描述：	行动计划
 * @author      创  建   者：	<A HREF="jhzhao@upbest-china.com">jhzhap</A>	
 * @date        创建日期：	2014年10月9日上午9:44:01
 */
public interface IBActionPlanService {
   
    /**
     * 
     * @Title 		   	函数名称：	saveOrUpdate
     * @Description   	功能描述：	保存行动计划
     * @param 		   	参          数：	
     * @return          返  回   值：	BActionPlan  
     * @throws
     */
    BActionPlan saveOrUpdate(BActionPlan entity);
    
    /**
     * 
     * @Title 		   	函数名称：	queryByTId
     * @Description   	功能描述：	根据测评问卷ID查询行动计划
     * @param 		   	参          数：	
     * @return          返  回   值：	List<BActionPlan>  
     * @throws
     */
    List<BActionPlan> queryByTId(Integer tId);
    
}