package com.upbest.mvc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bk_shop_report")
public class BShopReport implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;
	
	@Column(name="shop_id")
	private Integer shopId;
	
	@Column(name="report")
	private String report;
	
	@Column(name="user_id")
    private Integer userId;
	

	@Column(name="create_time")
    private Date createTime;
	
	
	@Column(name="mon")
	private Date mon;
	
	@Column(name="REPORT_TYPE")
	private String reportType;
	
	@Column(name="random_name")
	private String randomName;
	
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public Date getMon() {
        return mon;
    }

    public void setMon(Date mon) {
        this.mon = mon;
    }

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

    public String getRandomName() {
        return randomName;
    }

    public void setRandomName(String randomName) {
        this.randomName = randomName;
    }
	
	
}
