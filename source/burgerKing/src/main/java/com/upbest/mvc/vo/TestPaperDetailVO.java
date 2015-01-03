package com.upbest.mvc.vo;

import com.upbest.mvc.entity.BTestPaperDetail;

public class TestPaperDetailVO extends BTestPaperDetail {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//试题内容
	private String questionContent;
	//试题原始分值
	private int originalScore;
	
	private String questionType;
	
	//问题所属模块
	private String moduleName;

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	public int getOriginalScore() {
		return originalScore;
	}

	public void setOriginalScore(int originalScore) {
		this.originalScore = originalScore;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	
	
	
	
}
