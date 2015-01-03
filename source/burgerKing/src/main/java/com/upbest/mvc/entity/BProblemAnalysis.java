package com.upbest.mvc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;


@Entity
@Table(name = "bk_problem_analysis")
public class BProblemAnalysis implements Serializable{
	 private static final long serialVersionUID = 1L;

	    @Id
	    @Column(name = "id")
	    @GeneratedValue
	    private Integer id;
	    
	    @Column(name="t_id")
	    private Integer tId;
	    
	    @Column(name="resource")
	    @Size(max=500)
	    private String resource;
	    
	    @Column(name="resolve")
	    @Size(max=500)
	    private String resolve;
	    
	    @Column(name="score_num")
	    private String scoreNum;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer gettId() {
            return tId;
        }

        public void settId(Integer tId) {
            this.tId = tId;
        }

        public String getResource() {
            return resource;
        }

        public void setResource(String resource) {
            this.resource = resource;
        }

        public String getResolve() {
            return resolve;
        }

        public void setResolve(String resolve) {
            this.resolve = resolve;
        }

        public String getScoreNum() {
            return scoreNum;
        }

        public void setScoreNum(String scoreNum) {
            this.scoreNum = scoreNum;
        }

       
	  
}
