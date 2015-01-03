package com.upbest.mvc.vo;

public class UserStatisticVO {
	private int userId;
	private String userName;
	/**
	 * 门店数
	 */
	private int shopNum;
	/**
	 * 任务数
	 */
	private int taskNum;
	
	private MessageCountInfoVO messageCountInfo;
	

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getShopNum() {
		return shopNum;
	}

	public void setShopNum(int shopNum) {
		this.shopNum = shopNum;
	}

	public int getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}

	public MessageCountInfoVO getMessageCountInfo() {
		return messageCountInfo;
	}

	public void setMessageCountInfo(MessageCountInfoVO messageCountInfo) {
		if(messageCountInfo == null){
			messageCountInfo = new MessageCountInfoVO();
		}
		this.messageCountInfo = messageCountInfo;
	}
	
}
