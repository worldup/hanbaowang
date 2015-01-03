package com.upbest.mvc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.upbest.mvc.handler.JsonDateSerializer;

@Entity
@Table(name = "bk_exam_refer")
public class BExamRefer  implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "timer")
    private String timer;
    
    
    @Column(name = "date")
    private Date date;
    
    
    @Column(name = "num")
    private String num;
    
    @Column(name = "type")
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    
    @JsonSerialize(using=JsonDateSerializer.class)
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

    @Override
    public String toString() {
        return "BExamRefer [id=" + id + ", name=" + name + ", timer=" + timer + ", date=" + date + ", num=" + num + ", type=" + type + "]";
    }
    
    
}
