package com.upbest.mvc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 门店统计
 * @author QunZheng
 *
 */
@Entity
@Table(name = "bk_shop_statistic")
public class BShopStatistic implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id; 
	
	@Column(name = "shop_id")
	private String shopId;
	
	@Column(name = "shop_name")
	private String shopName;
	
	@Column(name = "sales")
	private Double sales;
	
	/**
	 * 人流量
	 */
	@Column(name = "tc")
	private Long tc;
	
	@Column(name = "MONTH")
	private Date month;
	
	@Column(name="GT_NPS")
	private String gtNps;
	
	@Column(name="RANK")
	private String rank;
	
	@Column(name="REV_BS")
	private String revBs;
	
	@Column(name="REV_FS")
	private String revFs;
	
	@Column(name="CASH_AUDIT")
	private String cashAudit;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Double getSales() {
		return sales;
	}

	public void setSales(Double sales) {
		this.sales = sales;
	}

	public Long getTc() {
		return tc;
	}

	public void setTc(Long tc) {
		this.tc = tc;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
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

}
