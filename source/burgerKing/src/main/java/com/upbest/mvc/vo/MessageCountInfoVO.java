package com.upbest.mvc.vo;

public class MessageCountInfoVO {
	/**
	 * 总的发送消息数
	 */
	private int totalSendMsgNum;
	/**
	 * 总的接受消息数
	 */
	private int totalReciveMsgNum;
	/**
	 * 公告消息发送数
	 */
	private int noticeMsgSendNum;
	
	/**
	 * 公告消息接受数
	 */
	private int noticeMsgReciveNum;
	
	private int emergencyMsgReciveNum;
	
	private int emergencyMsgSendNum;
	
	private int taskMsgReciveNum;
	
	private int taskMsgSendNum;
	
	private int exceptionMsgReciveNum;
	
	private int exceptionMsgSendNum;

	public int getTotalSendMsgNum() {
		return totalSendMsgNum;
	}

	public void setTotalSendMsgNum(int totalSendMsgNum) {
		this.totalSendMsgNum = totalSendMsgNum;
	}

	public int getTotalReciveMsgNum() {
		return totalReciveMsgNum;
	}

	public void setTotalReciveMsgNum(int totalReciveMsgNum) {
		this.totalReciveMsgNum = totalReciveMsgNum;
	}

	public int getNoticeMsgSendNum() {
		return noticeMsgSendNum;
	}

	public void setNoticeMsgSendNum(int noticeMsgSendNum) {
		this.noticeMsgSendNum = noticeMsgSendNum;
	}

	public int getNoticeMsgReciveNum() {
		return noticeMsgReciveNum;
	}

	public void setNoticeMsgReciveNum(int noticeMsgReciveNum) {
		this.noticeMsgReciveNum = noticeMsgReciveNum;
	}

	public int getEmergencyMsgReciveNum() {
		return emergencyMsgReciveNum;
	}

	public void setEmergencyMsgReciveNum(int emergencyMsgReciveNum) {
		this.emergencyMsgReciveNum = emergencyMsgReciveNum;
	}

	public int getEmergencyMsgSendNum() {
		return emergencyMsgSendNum;
	}

	public void setEmergencyMsgSendNum(int emergencyMsgSendNum) {
		this.emergencyMsgSendNum = emergencyMsgSendNum;
	}

	public int getTaskMsgReciveNum() {
		return taskMsgReciveNum;
	}

	public void setTaskMsgReciveNum(int taskMsgReciveNum) {
		this.taskMsgReciveNum = taskMsgReciveNum;
	}

	public int getTaskMsgSendNum() {
		return taskMsgSendNum;
	}

	public void setTaskMsgSendNum(int taskMsgSendNum) {
		this.taskMsgSendNum = taskMsgSendNum;
	}

	public int getExceptionMsgReciveNum() {
		return exceptionMsgReciveNum;
	}

	public void setExceptionMsgReciveNum(int exceptionMsgReciveNum) {
		this.exceptionMsgReciveNum = exceptionMsgReciveNum;
	}

	public int getExceptionMsgSendNum() {
		return exceptionMsgSendNum;
	}

	public void setExceptionMsgSendNum(int exceptionMsgSendNum) {
		this.exceptionMsgSendNum = exceptionMsgSendNum;
	}
	
}
