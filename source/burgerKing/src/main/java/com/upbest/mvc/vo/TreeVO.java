package com.upbest.mvc.vo;

import java.io.Serializable;

public class TreeVO implements Serializable {
    private static final long serialVersionUID = -7808643349076959832L;
    private String id;
    private String name;
    private String pid;
    private String url;
    private boolean isParent;
    private boolean isBoolean;

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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean isParent) {
        this.isParent = isParent;
    }

    public boolean isBoolean() {
        return isBoolean;
    }

    public void setBoolean(boolean isBoolean) {
        this.isBoolean = isBoolean;
    }

}
