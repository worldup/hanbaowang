package com.upbest.mvc.statistic;

public class QuestionResultInfo {
	private int quesId;
	private int totalScore;
	private int score;
	
	public QuestionResultInfo() {
		super();
	}
	
	public QuestionResultInfo(int quesId, int totalScore, int score) {
		super();
		this.quesId = quesId;
		this.totalScore = totalScore;
		this.score = score;
	}


	public int getQuesId() {
		return quesId;
	}
	public void setQuesId(int quesId) {
		this.quesId = quesId;
	}
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	
}
