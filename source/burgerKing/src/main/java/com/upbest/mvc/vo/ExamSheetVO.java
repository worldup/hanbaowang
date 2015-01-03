package com.upbest.mvc.vo;

import java.io.Serializable;

public class ExamSheetVO implements Serializable {

    private static final long serialVersionUID = 1L;
    //问题Id
    private String id;
    private String eid;
    //问卷名称
    private String ename;
    //试题描述
    private String Description;
    //试题类型
    private String qtype;
    //试题所属模块名称
    private String model;
    //总得分
    private String score;
    //总分
    private String aggregate;
    //失分
    private String points;
    //失分率
    private String losing;
    //试题编号
    private String num;
    
    //模块ID
    private String moduleId;
    
    private double losingD;
    
    
    
    public double getLosingD() {
        return losingD;
    }
    public void setLosingD(double losingD) {
        this.losingD = losingD;
    }
    public String getModuleId() {
        return moduleId;
    }
    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNum() {
        return num;
    }
    public void setNum(String num) {
        this.num = num;
    }
    public String getEid() {
        return eid;
    }
    public void setEid(String eid) {
        this.eid = eid;
    }
    public String getEname() {
        return ename;
    }
    public void setEname(String ename) {
        this.ename = ename;
    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }
    public String getQtype() {
        return qtype;
    }
    public void setQtype(String qtype) {
        this.qtype = qtype;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getScore() {
        return score;
    }
    public void setScore(String score) {
        this.score = score;
    }
    public String getAggregate() {
        return aggregate;
    }
    public void setAggregate(String aggregate) {
        this.aggregate = aggregate;
    }
    public String getPoints() {
        return points;
    }
    public void setPoints(String points) {
        this.points = points;
    }
    public String getLosing() {
        return losing;
    }
    public void setLosing(String losing) {
        this.losing = losing;
    }
   
    
}
