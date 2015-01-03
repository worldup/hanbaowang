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
@Table(name = "bk_examitnation_paper")
public class BExaminationPaper implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	
	@Id
    @Column(name="id")
    @GeneratedValue
    private Integer id;
	
	@Column(name="e_name")
	@Size(max=50)
	private String ename;
	
	@Column(name="e_type")
	private Integer etype;
	
	@Column(name="e_timer")
	private Integer etimer;
	
	@Column(name="e_passvalue")
	private Integer epassvalue;
	
	@Column(name="e_total")
	private Integer etotal;
	
	@Column(name="create_time",updatable=false)
	private Date createtime;
	
	@Column(name="update_time")
	private Date updateTime;
	
	@Column(name="description")
	private String desc;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public Integer getEtype() {
		return etype;
	}

	public void setEtype(Integer etype) {
		this.etype = etype;
	}

	public Integer getEtimer() {
		return etimer;
	}

	public void setEtimer(Integer etimer) {
		this.etimer = etimer;
	}

	public Integer getEpassvalue() {
		return epassvalue;
	}

	public void setEpassvalue(Integer epassvalue) {
		this.epassvalue = epassvalue;
	}

	public Integer getEtotal() {
		return etotal;
	}

	public void setEtotal(Integer etotal) {
		this.etotal = etotal;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "BExaminationPaper [id=" + id + ", ename=" + ename + ", etype="
				+ etype + ", etimer=" + etimer + ", epassvalue=" + epassvalue
				+ ", etotal=" + etotal + ", createtime=" + createtime + "]";
	}
	
	
	
}
