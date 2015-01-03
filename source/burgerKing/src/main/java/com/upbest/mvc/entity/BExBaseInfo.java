package com.upbest.mvc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "bk_ex_base_info")
public class BExBaseInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name="id")
    @GeneratedValue
    private Integer id;
	
	@Column(name="b_id")
	@Size(max=50)
	private String bid;
	
	@Column(name="b_pid")
	@Size(max=50)
	private String bpid;

	@Column(name="b_value")
	@Size(max=50)
	private String bvalue;
	
	@Column(name="b_comments")
	@Size(max=500)
	private String bcomments;
	
	@Column(name="b_brush_election")
	private String brushElection;

	@Column(name="is_key")
    private Integer isKey;
	
	public Integer getIsKey() {
        return isKey;
    }

    public void setIsKey(Integer isKey) {
        this.isKey = isKey;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getBpid() {
		return bpid;
	}

	public void setBpid(String bpid) {
		this.bpid = bpid;
	}

	public String getBvalue() {
		return bvalue;
	}

	public void setBvalue(String bvalue) {
		this.bvalue = bvalue;
	}

	public String getBcomments() {
		return bcomments;
	}

	public void setBcomments(String bcomments) {
		this.bcomments = bcomments;
	}
	
	public String getBrushElection() {
		return brushElection;
	}

	public void setBrushElection(String brushElection) {
		this.brushElection = brushElection;
	}

	@Override
	public String toString() {
		return "Bkexbaseinfo [id=" + id + ", bid=" + bid + ", bpid=" + bpid
				+ ", bvalue=" + bvalue + ", bcomments=" + bcomments + "]";
	}
	
	
	

}
