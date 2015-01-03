package com.upbest.mvc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "bk_exam_paper_detail")
public class BExamPaperDetail implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name="id")
    @GeneratedValue
    private Integer id;
	
	@Column(name="e_id")
	private Integer eid;
	
	@Column(name="q_id")
	private Integer qid;
	
	@Column(name="q_seq")
	private Integer qseq;

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

	public Integer getQid() {
		return qid;
	}

	public void setQid(Integer qid) {
		this.qid = qid;
	}

	public Integer getQseq() {
		return qseq;
	}

	public void setQseq(Integer qseq) {
		this.qseq = qseq;
	}

	@Override
	public String toString() {
		return "BExamPaperDetail [id=" + id + ", eid=" + eid + ", qid=" + qid
				+ ", qseq=" + qseq + "]";
	}
	
	
}
