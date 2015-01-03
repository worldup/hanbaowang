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
@Table(name = "bk_action_plan")
public class BActionPlan implements Serializable{
	 private static final long serialVersionUID = 1L;

	    @Id
	    @Column(name = "id")
	    @GeneratedValue
	    private Integer id;
	    
	    @Column(name="t_id")
	    private Integer tId;
	    
	    @Column(name="tack")
	    @Size(max=500)
	    private String tack;
	    
	    @Column(name="begin_time")
	    private Date beginTime;
	    
	    @Column(name="exp_end_time")
	    private Date expEndTime;
	    
	    @Column(name="real_end_time")
        private Date realEndTime;
	    
	    @Column(name="user_name")
	    @Size(max=100)
        private String userName;
	    
	    @Column(name="exp_result")
        @Size(max=100)
        private String expResult;

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

        public String getTack() {
            return tack;
        }

        public void setTack(String tack) {
            this.tack = tack;
        }

        public Date getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(Date beginTime) {
            this.beginTime = beginTime;
        }

        public Date getExpEndTime() {
            return expEndTime;
        }

        public void setExpEndTime(Date expEndTime) {
            this.expEndTime = expEndTime;
        }

        public Date getRealEndTime() {
            return realEndTime;
        }

        public void setRealEndTime(Date realEndTime) {
            this.realEndTime = realEndTime;
        }

        
        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getExpResult() {
            return expResult;
        }

        public void setExpResult(String expResult) {
            this.expResult = expResult;
        }

	    
	    
     
	  
}
