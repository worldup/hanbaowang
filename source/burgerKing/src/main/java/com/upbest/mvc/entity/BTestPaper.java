package com.upbest.mvc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.upbest.mvc.handler.JsonDateSerializer;


@Entity
@Table(name = "bk_test_paper")
public class BTestPaper implements Serializable{
private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name="id")
    @GeneratedValue
    private Integer id;
	
	@Column(name="user_id")
	private Integer userid;
	
	@Column(name="e_id")
	private Integer eid;
	
	@Column(name="t_begin")
	private Date tbegin;
	
	@Column(name="t_end")
	private Date tend;
	
	@Column(name="t_total")
	private Integer ttotal;
	
	@Column(name="t_state")
	private Integer tstate;

	@Column(name="store_id")
	private Integer storeid;
	
	@Column(name="remark")
	@Size(max=1000)
	private String remark;
	
	@Column(name="inviter")
	@Size(max=50)
	private String inviter;
	
	@Column(name="manager")
	@Size(max=50)
	private String manager;
	
	@Column(name="level")
	private Integer level;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getEid() {
		return eid;
	}

	public void setEid(Integer eid) {
		this.eid = eid;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getTbegin() {
		return tbegin;
	}

	public void setTbegin(Date tbegin) {
		this.tbegin = tbegin;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getTend() {
		return tend;
	}

	public void setTend(Date tend) {
		this.tend = tend;
	}

	public Integer getTtotal() {
		return ttotal;
	}

	public void setTtotal(Integer ttotal) {
		this.ttotal = ttotal;
	}

	public Integer getTstate() {
		return tstate;
	}

	public void setTstate(Integer tstate) {
		this.tstate = tstate;
	}

	public Integer getStoreid() {
		return storeid;
	}

	public void setStoreid(Integer storeid) {
		this.storeid = storeid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInviter() {
		return inviter;
	}

	public void setInviter(String inviter) {
		this.inviter = inviter;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}
	

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "BTestPaper [id=" + id + ", userid=" + userid + ", eid=" + eid
				+ ", tbegin=" + tbegin + ", tend=" + tend + ", ttotal="
				+ ttotal + ", tstate=" + tstate + ", storeid=" + storeid
				+ ", remark=" + remark + ", inviter=" + inviter + ", manager="
				+ manager + "]";
	}
	
	
	
}
