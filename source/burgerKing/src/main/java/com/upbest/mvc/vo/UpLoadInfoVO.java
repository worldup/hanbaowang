package com.upbest.mvc.vo;

import java.io.Serializable;
import java.util.Date;

public class UpLoadInfoVO implements Serializable {
    private static final long serialVersionUID = -7808643349076959832L;
    private Integer id;
    private String originalName;
    private String path;
    private Date createTime;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getOriginalName() {
        return originalName;
    }
    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    
}
