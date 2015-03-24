package com.upbest.mvc.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * Created by lili on 2015/2/1.
 */
@Entity
@Table(name = "bk_user_working_leave")
public class UserWorkingLeave {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    //1节假日，2 周末 3 病假 4 事假 5
    @Column(name = "nonworkingType")
    private Integer nonworkingType;

    @Column(name = "startTime")
    private Date startTime;

    @Column (name="day")
    private String day;

    public Integer getNonworkingType() {
        return nonworkingType;
    }

    public void setNonworkingType(Integer nonworkingType) {
        this.nonworkingType = nonworkingType;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Column(name = "endTime")
    private Date endTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


}

