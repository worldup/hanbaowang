package com.upbest.mvc.vo;

import com.upbest.mvc.entity.BExaminationPaper;

public class ExaminationPaperVO extends BExaminationPaper {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String examTypeName;

	public String getExamTypeName() {
		return examTypeName;
	}

	public void setExamTypeName(String examTypeName) {
		this.examTypeName = examTypeName;
	}
	
}
