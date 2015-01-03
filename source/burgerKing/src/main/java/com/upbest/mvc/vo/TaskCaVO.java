package com.upbest.mvc.vo;

import java.io.Serializable;
import java.util.List;

public class TaskCaVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long date;
    private List<TaskVO> list;
    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }
    public List<TaskVO> getList() {
        return list;
    }
    public void setList(List<TaskVO> list) {
        this.list = list;
    }
    
    
    
}
