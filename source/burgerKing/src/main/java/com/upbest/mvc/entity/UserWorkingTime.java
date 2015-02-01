package com.upbest.mvc.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * Created by lili on 2015/2/1.
 */
@Entity
@Table(name = "bk_user_working_time")
public class UserWorkingTime {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "nonworking")
    private Integer nonworking;
    @Column(name = "day")
    private Date day;
    @Column(name = "am_workingtime_begin")
    private Time startTime;
    @Column(name = "am_workingtime_end")
    private Time amWorkingtimeEnd;
    @Column(name = "  pm_workingtime_begin")
    private Time   pmWorkingtimeBegin;
    @Column(name = "  pm_workingtime_end")
    private Time   endTime;
    @Column(name = "  user_name")
    private String   userName;
    @Column(name = "  update_time")
    private String   updateTime;
    @Column(name = "  update_user_name")
    private String   updateUserName;
    @Column(name = "  update_user_id")
    private Integer   updateUserId;

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

    public Integer getNonworking() {
        return nonworking;
    }

    public void setNonworking(Integer nonworking) {
        this.nonworking = nonworking;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Time getAmWorkingtimeEnd() {
        return amWorkingtimeEnd;
    }

    public void setAmWorkingtimeEnd(Time amWorkingtimeEnd) {
        this.amWorkingtimeEnd = amWorkingtimeEnd;
    }

    public Time getPmWorkingtimeBegin() {
        return pmWorkingtimeBegin;
    }

    public void setPmWorkingtimeBegin(Time pmWorkingtimeBegin) {
        this.pmWorkingtimeBegin = pmWorkingtimeBegin;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }
}
