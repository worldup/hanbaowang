package com.upbest.mvc.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.upbest.mvc.entity.BWorkType;

public class StsStoreVO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    //门店ID
    private String shopId;
    
    //门店名称
    private String shopName;
    
    //类型ID
    private String workTypeId;
    
    //类型名称
    private String workTypeName;
    
    //已安排的任务数(分子展示)
    private int doneTasks;
    
    //据《OC个人完成度》计算分母
    private int shouldDoTasks;
    
    private List<TaskTypeVO> list=new ArrayList<TaskTypeVO>();
    
    

    public List<TaskTypeVO> getList() {
        return list;
    }

    public void setList(List<TaskTypeVO> list) {
        this.list = list;
    }

    public int getShouldDoTasks() {
        return shouldDoTasks;
    }

    public void setShouldDoTasks(int shouldDoTasks) {
        this.shouldDoTasks = shouldDoTasks;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getWorkTypeId() {
        return workTypeId;
    }

    public void setWorkTypeId(String workTypeId) {
        this.workTypeId = workTypeId;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    public int getDoneTasks() {
        return doneTasks;
    }

    public void setDoneTasks(int doneTasks) {
        this.doneTasks = doneTasks;
    }
    
    
    
    
    
}
