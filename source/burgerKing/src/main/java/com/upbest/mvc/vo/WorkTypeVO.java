package com.upbest.mvc.vo;

import java.io.Serializable;

public class WorkTypeVO implements Serializable {
    private static final long serialVersionUID = -7808643349076959832L;
    private String id;
    private String typename;
    private String typerole;
    private String frequency;
    private Integer isExamType;
    private Integer isToStore;
    private Integer  examType;
    public Integer getExamType() {
        return examType;
    }

    public void setExamType(Integer examType) {
        this.examType = examType;
    }

    public Integer getIsToStore() {
        return isToStore;
    }
    public void setIsToStore(Integer isToStore) {
        this.isToStore = isToStore;
    }
    public Integer getIsExamType() {
        return isExamType;
    }
    public void setIsExamType(Integer isExamType) {
        this.isExamType = isExamType;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTypename() {
        return typename;
    }
    public void setTypename(String typename) {
        this.typename = typename;
    }
    public String getTyperole() {
        return typerole;
    }
    public void setTyperole(String typerole) {
        this.typerole = typerole;
    }
    public String getFrequency() {
        return frequency;
    }
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
