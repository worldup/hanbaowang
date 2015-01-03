package com.upbest.mvc.scheduler;

public class TaskInfo {
	private Integer userId;
	private Integer shopId;
	private String shopName;
	private Integer taskType;
	private String taskTypeName;
	private boolean shopRelated;
	private int frequence;
	
	public TaskInfo() {
		super();
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public String getTaskTypeName() {
		return taskTypeName;
	}

	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public boolean isShopRelated() {
		return shopRelated;
	}

	public void setShopRelated(boolean shopRelated) {
		this.shopRelated = shopRelated;
	}
	
	
	public int getFrequence() {
		return frequence;
	}

	public void setFrequence(int frequence) {
		this.frequence = frequence;
	}

	public String getContent(){
		StringBuilder sb = new StringBuilder();
		if(shopRelated){
			sb.append("门店[" + shopName + "]");
		}
		String f = "";
		switch (frequence) {
			case 1:
				f = "周";
				break;
			case 2:
				f = "月";
				break;
			case 3:
				f = "季度";
				break;
			default:
				break;
		}
		sb.append("没创建任务[" + taskTypeName +"("+f+")]");
		
		return sb.toString();
	}
	
	
	
}
