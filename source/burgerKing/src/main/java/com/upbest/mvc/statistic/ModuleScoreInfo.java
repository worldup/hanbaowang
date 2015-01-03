package com.upbest.mvc.statistic;

public class ModuleScoreInfo {
	
	/**
	 * 模块名称
	 */
	private String name;
	
	/**
	 * 模块得分
	 */
	private int score;
	
	/**
	 * 模块总分
	 */
	private int totalScore;
	
	/**
	 * 得分百分比
	 */
	private double percent;
	
	/**
	 * 等级
	 */
	private String level;
	
	/**
	 * 失分项的数量
	 */
	private int loseScoreNum;
	
	public ModuleScoreInfo(String name) {
		this.name = name;
	}

	public ModuleScoreInfo(String name, int score, int totalScore) {
		this(name, score, totalScore, 0);
	}
	
	public ModuleScoreInfo(String name, int score, int totalScore,int loseScoreNum) {
		this.name = name;
		this.score = score;
		this.totalScore = totalScore;
		this.percent = totalScore == 0 ? 0 : (score / totalScore * 100);
		this.loseScoreNum = loseScoreNum;
		this.level = DefaultLevel.getLevel((int)percent);
	}
	


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getLoseScoreNum() {
		return loseScoreNum;
	}

	public void setLoseScoreNum(int loseScoreNum) {
		this.loseScoreNum = loseScoreNum;
	}
	
}
