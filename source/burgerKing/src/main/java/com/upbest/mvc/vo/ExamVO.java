package com.upbest.mvc.vo;

import java.io.Serializable;
import java.util.Date;

public class ExamVO implements Serializable {
    private static final long serialVersionUID = -7808643349076959832L;
    
    private String id;
    private String name;
    private String timer;
    private Date date;
    private String num;
    private String type;
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
    public String getTimer() {
        return timer;
    }
    public void setTimer(String timer) {
        this.timer = timer;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getNum() {
        return num;
    }
    public void setNum(String num) {
        this.num = num;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
}
