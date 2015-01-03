package com.upbest.mvc.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.druid.Constants;
import com.upbest.mvc.constant.Constant;
import com.upbest.mvc.entity.BWorkType;
import com.upbest.mvc.entity.Buser;

public class BStsVO implements Serializable {
    private static final long serialVersionUID = 1L;
    

    private Buser user;

    //自主计划（展现形式：n/N(已完成/任务总数)）
    private String selfCreate;
    
    private double selfCreateD;
    //上司委派（展现形式：m/M(已完成/任务总数)）
    private String assign;
    
    private double assignD;
    //T=N+M;t=n+m
    private String total;
    
    private double totalD;

    //月度
    private String month;
    
    //季度
    private String quarter;
    
    //年度
    private String year;
    
    //任务类型
    private BWorkType workType;

    
    
    public double getTotalD() {
        return totalD;
    }

    public double getSelfCreateD() {
        return selfCreateD;
    }

    public void setSelfCreateD(double selfCreateD) {
        this.selfCreateD = selfCreateD;
    }

    public double getAssignD() {
        return assignD;
    }

    public void setAssignD(double assignD) {
        this.assignD = assignD;
    }

    public void setTotalD(double totalD) {
        this.totalD = totalD;
    }

    public BWorkType getWorkType() {
        return workType;
    }

    public void setWorkType(BWorkType workType) {
        this.workType = workType;
    }

    public Buser getUser() {
        return user;
    }

    public void setUser(Buser user) {
        this.user = user;
    }

    public String getSelfCreate() {
        return selfCreate;
    }

    public void setSelfCreate(String selfCreate) {
        this.selfCreate = selfCreate;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAssign() {
        return assign;
    }

    public void setAssign(String assign) {
        this.assign = assign;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(!(obj instanceof BStsVO)){
            return false;
        }
        BStsVO temp = (BStsVO)obj;
        if(this.getWorkType() == null || temp.getWorkType() == null){
            return false;
         }
        return this.getWorkType().getId() == temp.getWorkType().getId();
    }
    
    @Override
    public int hashCode() {
        return this.getWorkType() == null ? ((int)(new Date().getTime() / 65535)): this.getWorkType().getId();
    }
    
}
