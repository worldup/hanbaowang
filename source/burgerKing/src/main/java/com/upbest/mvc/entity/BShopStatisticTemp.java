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
 * @author zhaojinhuavfp@qq.com
 */
@Entity
@Table(name = "bk_shop_statistic_TEMP")
public class BShopStatisticTemp implements Serializable{
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
	
	@Column(name = "month")
	private String month;

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

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
	
}
