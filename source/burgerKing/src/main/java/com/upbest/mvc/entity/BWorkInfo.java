package com.upbest.mvc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "bk_work_info")
public class BWorkInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;
	
//	 @Column(name = "name")
//	 @Size(max=100)
//	 private String  name;
	 
	 @Column(name = "content")
	 @Size(max=500)
	 private String  content;
	 
	 @Column(name="start_time")
	 private Date starttime;
	 
	 @Column(name="finish_pre_time")
	 private Date finishpretime;
	 
	 @Column(name="finish_real_time")
	 private Date finishrealtime;
	 
	 @Column(name="pre_result")
	 @Size(max=500)
	 private String preresult;
	 
	 @Column(name="real_result")
	 @Size(max=500)
	 private String realresult;
	 
	 @Column(name="user_id")
	 private Integer userid;
	 
	 @Column(name="state")
	 @Size(max=100)
	 private String state;
	 
	 @Column(name="store_id")
     private Integer storeid;
	 
	 @Column(name="work_type_id")
     private Integer worktypeid;
    @Column(name="work_type_name")
    private String worktypename;
	 @Column(name="execute_id")
	 private Integer executeid;
	 
	 @Column(name="is_self_create")//0:自主，1：委派
	 private String isSelfCreate;
	 
	 @Column(name="pid")//针对90天计划任务
	 private Integer pid;

	 @Column(name="quarter")//季度
	 private String quarter;

    public String getWorktypename() {
        return worktypename;
    }

    public void setWorktypename(String worktypename) {
        this.worktypename = worktypename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getFinishpretime() {
        return finishpretime;
    }

    public void setFinishpretime(Date finishpretime) {
        this.finishpretime = finishpretime;
    }

    public Date getFinishrealtime() {
        return finishrealtime;
    }

    public void setFinishrealtime(Date finishrealtime) {
        this.finishrealtime = finishrealtime;
    }

    public String getPreresult() {
        return preresult;
    }

    public void setPreresult(String preresult) {
        this.preresult = preresult;
    }

    public String getRealresult() {
        return realresult;
    }

    public void setRealresult(String realresult) {
        this.realresult = realresult;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getStoreid() {
        return storeid;
    }

    public void setStoreid(Integer storeid) {
        this.storeid = storeid;
    }

    public Integer getWorktypeid() {
        return worktypeid;
    }

    public void setWorktypeid(Integer worktypeid) {
        this.worktypeid = worktypeid;
    }

    public Integer getExecuteid() {
        return executeid;
    }

    public void setExecuteid(Integer executeid) {
        this.executeid = executeid;
    }

    public String getIsSelfCreate() {
        return isSelfCreate;
    }

    public void setIsSelfCreate(String isSelfCreate) {
        this.isSelfCreate = isSelfCreate;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    @Override
    public String toString() {
        return "BWorkInfo [id=" + id + ", content=" + content + ", starttime=" + starttime + ", finishpretime=" + finishpretime + ", finishrealtime=" + finishrealtime + ", preresult=" + preresult
                + ", realresult=" + realresult + ", userid=" + userid + ", state=" + state + ", storeid=" + storeid + ", worktypeid=" + worktypeid + ", executeid=" + executeid + ", isSelfCreate="
                + isSelfCreate + ", pid=" + pid + ", quarter=" + quarter + "]";
    }

}
