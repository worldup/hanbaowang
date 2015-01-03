package com.upbest.mvc.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.upbest.mvc.entity.BShopStatistic;

@JsonIgnoreProperties("examScoreMap")
public class ShopStatisticVO extends BShopStatistic {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * key:	问卷类型
	 * value : 问卷类型对应的分值
	 */
	private Map<Integer, ExamTypeScoreVO> examScoreMap;
	
	private List<ExamTypeScoreVO> examScoreList = null;
	
	private String tcComp;
	private String salesComp;
	
	public Map<Integer, ExamTypeScoreVO> getExamScoreMap() {
		return examScoreMap;
	}
	public void setExamScoreMap(Map<Integer, ExamTypeScoreVO> examScoreMap) {
		this.examScoreMap = examScoreMap;
	}
	public List<ExamTypeScoreVO> getExamScoreList() {
		if(examScoreMap != null){
			return new ArrayList<ExamTypeScoreVO>(examScoreMap.values());
		}
		return null;
	}
	
	public String getTcComp() {
		return tcComp;
	}
	public void setTcComp(String tcComp) {
		this.tcComp = tcComp;
	}
	public String getSalesComp() {
		return salesComp;
	}
	public void setSalesComp(String salesComp) {
		this.salesComp = salesComp;
	}
	
}
