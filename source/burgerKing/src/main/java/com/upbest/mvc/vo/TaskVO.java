package com.upbest.mvc.vo;

import java.io.Serializable;
import java.util.Date;

public class TaskVO implements Serializable {
    private static final long serialVersionUID = -7808643349076959832L;
    private String id;
    private String name;
    private String content;
    private Date starttime;
    private Date finishpretime;
    private Date finishrealtime;

    private Long starttimeLong;
    private Long finishpretimeLong;
    private Long finishrealtimeLong;
    private String preresult;
    private String realresult;
    private String userName;
    private String state;
    private String storeName;
    private String workTypeName;
    private String executeName;
    private String isSelfCreate;
    private String pid;
    private String quarter;
    private int storeId;
    private int taskTypeId;
    private String ishidden ;
    private String useText;
    private String useImg;
    private int isToStore;
    private String storeNum;
    private String examType;
    private int testPaperId;

    public int getTestPaperId() {
        return testPaperId;
    }

    public void setTestPaperId(int testPaperId) {
        this.testPaperId = testPaperId;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public String getIshidden() {
        return ishidden;
    }

    public void setIshidden(String ishidden) {
        this.ishidden = ishidden;
    }

    public String getUseText() {
        return useText;
    }

    public void setUseText(String useText) {
        this.useText = useText;
    }

    public String getUseImg() {
        return useImg;
    }

    public void setUseImg(String useImg) {
        this.useImg = useImg;
    }

    public int getIsToStore() {
        return isToStore;
    }

    public void setIsToStore(int isToStore) {
        this.isToStore = isToStore;
    }

    public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(int taskTypeId) {
		this.taskTypeId = taskTypeId;
	}

	public Long getStarttimeLong() {
        return starttimeLong;
    }

    public void setStarttimeLong(Long starttimeLong) {
        this.starttimeLong = starttimeLong;
    }

    public Long getFinishpretimeLong() {
        return finishpretimeLong;
    }

    public void setFinishpretimeLong(Long finishpretimeLong) {
        this.finishpretimeLong = finishpretimeLong;
    }

    public Long getFinishrealtimeLong() {
        return finishrealtimeLong;
    }

    public void setFinishrealtimeLong(Long finishrealtimeLong) {
        this.finishrealtimeLong = finishrealtimeLong;
    }

    public String getExecuteName() {
        return executeName;
    }

    public void setExecuteName(String executeName) {
        this.executeName = executeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getFinishpretime() {
        return finishpretime;
    }

    public void setFinishpretime(Date finishpretime) {
        this.finishpretime = finishpretime;
    }

    public Date getFinishrealtime() {
        return finishrealtime;
    }

    public void setFinishrealtime(Date finishrealtime) {
        this.finishrealtime = finishrealtime;
    }

    public String getPreresult() {
        return preresult;
    }

    public void setPreresult(String preresult) {
        this.preresult = preresult;
    }

    public String getRealresult() {
        return realresult;
    }

    public void setRealresult(String realresult) {
        this.realresult = realresult;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    public String getIsSelfCreate() {
        return isSelfCreate;
    }

    public void setIsSelfCreate(String isSelfCreate) {
        this.isSelfCreate = isSelfCreate;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

}
