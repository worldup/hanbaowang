package com.upbest.mvc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bk_examination_assign")
public class BExaminationAssign implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name="id")
    @GeneratedValue
    private Integer id;
	
	@Column(name="e_id")
	private Integer eid;
	
	@Column(name="user_id")
	private Integer userid;
	
	@Column(name="assign_timer")
	private Date assigntimer;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEid() {
		return eid;
	}

	public void setEid(Integer eid) {
		this.eid = eid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Date getAssigntimer() {
		return assigntimer;
	}

	public void setAssigntimer(Date assigntimer) {
		this.assigntimer = assigntimer;
	}

	@Override
	public String toString() {
		return "BExaminationAssign [id=" + id + ", eid=" + eid + ", userid="
				+ userid + ", assigntimer=" + assigntimer + "]";
	}
	

}
