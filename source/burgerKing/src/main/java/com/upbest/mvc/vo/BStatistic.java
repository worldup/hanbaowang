package com.upbest.mvc.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

public class BStatistic implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    //执行人
    private Integer userId;
    
    //0:月度 1：季度
    private int sord;
    
    //0:自主  1:委派
    private Integer isSelfCreate;
    
    //数量
    private Integer count;
    
    //月份
    private Integer month;
    
    //年份
    private String year;
    
    //季度
    private String quarter;

    //0:未完成 1:已完成
    private Integer state;
    
    //任务类型ID
    private Integer workTypeId;
    
    //0:事件 1：计划
    private Integer type;
    

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getSord() {
        return sord;
    }

    public void setSord(int sord) {
        this.sord = sord;
    }

    public Integer getIsSelfCreate() {
        return isSelfCreate;
    }

    public void setIsSelfCreate(Integer isSelfCreate) {
        this.isSelfCreate = isSelfCreate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getWorkTypeId() {
        return workTypeId;
    }

    public void setWorkTypeId(Integer workTypeId) {
        this.workTypeId = workTypeId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
    
    
   
}
