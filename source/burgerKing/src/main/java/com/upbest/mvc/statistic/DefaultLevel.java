package com.upbest.mvc.statistic;

import java.util.ArrayList;
import java.util.List;

public class DefaultLevel {
	private static List<ScoreLevel> level = buildLevel();
	
	public static String getLevel(int percent){
		for (ScoreLevel scoreLevel : level) {
			if(percent >= scoreLevel.getMinPercent() && percent <= scoreLevel.getMaxPercent()){
				return scoreLevel.getLevel();
			}
		}
		return "";
	}

	private static List<ScoreLevel> buildLevel() {
		List<ScoreLevel> list = new ArrayList<ScoreLevel>();
		list.add(new ScoreLevel("A", 85, 100));
		list.add(new ScoreLevel("B", 70, 84));
		list.add(new ScoreLevel("C", 55, 69));
		list.add(new ScoreLevel("D", 40, 54));
		list.add(new ScoreLevel("F", 0, 39));
		return list;
	}
}
