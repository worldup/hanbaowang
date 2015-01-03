package com.upbest.mvc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "bk_work_type")
public class BWorkType implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;
    
    
    @Column(name="type_name")
    @Size(max=200)
    private String typename;
    
    @Column(name="type_role")
    private String typerole;
    
    @Column(name="frequency")
    private Integer frequency;
    
    
    @Column(name="is_exam_type")
    private Integer isExamType;
    
    @Column(name="is_to_store")
    private Integer isToStore;
    
    @Column(name="sort_num")
    private Integer sortNum;
    
    
    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    
    

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "BWorkType [id=" + id + ", typename=" + typename + ", typerole=" + typerole + ", frequency=" + frequency + "]";
    }
    
    
}
