package com.upbest.mvc.statistic;

public class ScoreLevel {
	private String level;
	private int minPercent;
	private int maxPercent;
	
	public ScoreLevel(String level, int minPercent, int maxPercent) {
		super();
		this.level = level;
		this.minPercent = minPercent;
		this.maxPercent = maxPercent;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public int getMinPercent() {
		return minPercent;
	}
	public void setMinPercent(int minPercent) {
		this.minPercent = minPercent;
	}
	public int getMaxPercent() {
		return maxPercent;
	}
	public void setMaxPercent(int maxPercent) {
		this.maxPercent = maxPercent;
	}
	
	
}
