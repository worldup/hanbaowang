package com.upbest.mvc.vo;

public class ExamTypeScoreVO {
	private int examType;
	private String examTypeName;
	private double socre;
	

	public ExamTypeScoreVO() {
		super();
	}

	public ExamTypeScoreVO(int examType, String examTypeName) {
		super();
		this.examType = examType;
		this.examTypeName = examTypeName;
	}

	public int getExamType() {
		return examType;
	}

	public void setExamType(int examType) {
		this.examType = examType;
	}

	public String getExamTypeName() {
		return examTypeName;
	}

	public void setExamTypeName(String examTypeName) {
		this.examTypeName = examTypeName;
	}

	public double getSocre() {
		return socre;
	}

	public void setSocre(double socre) {
		this.socre = socre;
	}

}
