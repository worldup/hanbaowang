package com.upbest.mvc.vo;

import java.util.Date;

public class ShopReVO {

    private String id;
    private String shopName;
    private String report;
    private String name;
    private Date createTime;
    private Date mon;
    private String reportType;
    private String randomName;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    public String getReport() {
        return report;
    }
    public void setReport(String report) {
        this.report = report;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getMon() {
        return mon;
    }
    public void setMon(Date mon) {
        this.mon = mon;
    }
    public String getShopName() {
        return shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
