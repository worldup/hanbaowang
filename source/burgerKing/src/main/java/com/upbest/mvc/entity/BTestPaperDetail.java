package com.upbest.mvc.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "bk_test_paper_detail")
public class BTestPaperDetail implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;
	
	@Column(name="t_id")
	private Integer tid;
	
	@Column(name="q_id")
	private Integer qid;
	
	@Column(name="t_answer")
	@Size(max=1000)
	private String tanswer;

	@Column(name="t_value")
	private Integer tvalue;
	
	@Column(name="q_evidence")
	@Size(max=1000)
	private String qevidence;
	
	@Column(name="q_resource")
	@Size(max=500)
	private String qresource;
	
	@Column(name="q_resolve")
	@Size(max=500)
	private String qresolve;
	
	@Column(name="remark")
	@Size(max=500)
	private String remark;
	
	@Transient
	private List<BTestHeadingRela> tstHeadingRela;

	
	public List<BTestHeadingRela> getTstHeadingRela() {
        return tstHeadingRela;
    }

    public void setTstHeadingRela(List<BTestHeadingRela> tstHeadingRela) {
        this.tstHeadingRela = tstHeadingRela;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public String getTanswer() {
		return tanswer;
	}

	public void setTanswer(String tanswer) {
		this.tanswer = tanswer;
	}

	public Integer getTvalue() {
		return tvalue;
	}

	public void setTvalue(Integer tvalue) {
		this.tvalue = tvalue;
	}

	public String getQevidence() {
		return qevidence;
	}

	public void setQevidence(String qevidence) {
		this.qevidence = qevidence;
	}

	public String getQresource() {
		return qresource;
	}

	public void setQresource(String qresource) {
		this.qresource = qresource;
	}

	public String getQresolve() {
		return qresolve;
	}

	public void setQresolve(String qresolve) {
		this.qresolve = qresolve;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "BTestPaperDetail [id=" + id + ", tid=" + tid + ", qid=" + qid
				+ ", tanswer=" + tanswer + ", tvalue=" + tvalue
				+ ", qevidence=" + qevidence + ", qresource=" + qresource
				+ ", qresolve=" + qresolve + ", remark=" + remark + "]";
	}
	
	
	
}
