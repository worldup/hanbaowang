package com.upbest.mvc.vo;

import java.io.Serializable;
import java.util.List;

public class BExBaseInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String pvalue;
    private Integer id;
    
    private String bid;
       
    private String bpid;
    
    private String bvalue;
    
    private String bcomments;
    
    private String brushElection;
    
    private List<BExBaseInfoVO> childList;
    
    private int isKey;
    
    public int getIsKey() {
        return isKey;
    }

    public void setIsKey(int isKey) {
        this.isKey = isKey;
    }

    public List<BExBaseInfoVO> getChildList() {
        return childList;
    }

    public void setChildList(List<BExBaseInfoVO> childList) {
        this.childList = childList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }


    public String getBpid() {
        return bpid;
    }

    public void setBpid(String bpid) {
        this.bpid = bpid;
    }

    public String getBvalue() {
        return bvalue;
    }

    public void setBvalue(String bvalue) {
        this.bvalue = bvalue;
    }

    public String getBcomments() {
        return bcomments;
    }

    public void setBcomments(String bcomments) {
        this.bcomments = bcomments;
    }

    public String getPvalue() {
        return pvalue;
    }

    public void setPvalue(String pvalue) {
        this.pvalue = pvalue;
    }

    public String getBrushElection() {
        return brushElection;
    }

    public void setBrushElection(String brushElection) {
        this.brushElection = brushElection;
    }
    
    
    
}
