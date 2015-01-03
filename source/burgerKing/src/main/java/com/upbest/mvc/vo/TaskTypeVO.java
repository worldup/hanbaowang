package com.upbest.mvc.vo;

import java.util.List;

import com.upbest.mvc.entity.BArea;
import com.upbest.mvc.entity.BWorkType;

public class TaskTypeVO {
    private BWorkType workType;
	
	private int doneTasks;
	
	private int shouldDoneTasks;

	
    public BWorkType getWorkType() {
        return workType;
    }

    public void setWorkType(BWorkType workType) {
        this.workType = workType;
    }

    public int getDoneTasks() {
        return doneTasks;
    }

    public void setDoneTasks(int doneTasks) {
        this.doneTasks = doneTasks;
    }

    public int getShouldDoneTasks() {
        return shouldDoneTasks;
    }

    public void setShouldDoneTasks(int shouldDoneTasks) {
        this.shouldDoneTasks = shouldDoneTasks;
    }

	
	
	
	
}
