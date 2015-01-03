package com.upbest.mvc.vo;

import java.io.Serializable;

public class BshopStatisticVO implements Serializable{
    private static final long serialVersionUID = 1L;
    String id;
    String shopName;
    String shopId;
    String sales;
    String tc;
    String month;
    private String shopNum;
	private String gtNps;
	private String rank;
	private String revBs;
	private String revFs;
	private String cashAudit;
	private String tcComp;
	private String salesComp;
	private String salesRank;
	private String tcRank;
	
    public String getShopName() {
        return shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getShopId() {
        return shopId;
    }
    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
    public String getSales() {
        return sales;
    }
    public void setSales(String sales) {
        this.sales = sales;
    }
    public String getTc() {
        return tc;
    }
    public void setTc(String tc) {
        this.tc = tc;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getGtNps() {
		return gtNps;
	}
	public void setGtNps(String gtNps) {
		this.gtNps = gtNps;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getRevBs() {
		return revBs;
	}
	public void setRevBs(String revBs) {
		this.revBs = revBs;
	}
	public String getRevFs() {
		return revFs;
	}
	public void setRevFs(String revFs) {
		this.revFs = revFs;
	}
	public String getCashAudit() {
		return cashAudit;
	}
	public void setCashAudit(String cashAudit) {
		this.cashAudit = cashAudit;
	}
	public String getTcComp() {
		return tcComp;
	}
	public void setTcComp(String tcComp) {
		this.tcComp = tcComp;
	}
	public String getSalesComp() {
		return salesComp;
	}
	public void setSalesComp(String salesComp) {
		this.salesComp = salesComp;
	}
	public String getSalesRank() {
		return salesRank;
	}
	public void setSalesRank(String salesRank) {
		this.salesRank = salesRank;
	}
	public String getTcRank() {
		return tcRank;
	}
	public void setTcRank(String tcRank) {
		this.tcRank = tcRank;
	}
	public String getShopNum() {
		return shopNum;
	}
	public void setShopNum(String shopNum) {
		this.shopNum = shopNum;
	}
    
}
