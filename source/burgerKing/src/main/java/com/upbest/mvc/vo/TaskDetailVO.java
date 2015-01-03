package com.upbest.mvc.vo;

import java.util.List;

import com.upbest.mvc.entity.BArea;

public class TaskDetailVO {
	
	private String id;
	private String storeName;
	private String taskName;
	private long finishRealTime;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public long getFinishRealTime() {
        return finishRealTime;
    }
    public void setFinishRealTime(long finishRealTime) {
        this.finishRealTime = finishRealTime;
    }
	
	
	
}
