package com.upbest.mvc.vo;

import java.io.Serializable;

public class ShopVO implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String shopname;
    
    private String shopaddress;
    
    private String shopbusinesstime;
    
    //经度
    private String longitude;
    //纬度
    private String latitude;

    private String shopnum;
    
    public String getShopnum() {
		return shopnum;
	}

	public void setShopnum(String shopnum) {
		this.shopnum = shopnum;
	}

	public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getShopaddress() {
        return shopaddress;
    }

    public void setShopaddress(String shopaddress) {
        this.shopaddress = shopaddress;
    }

    public String getShopbusinesstime() {
        return shopbusinesstime;
    }

    public void setShopbusinesstime(String shopbusinesstime) {
        this.shopbusinesstime = shopbusinesstime;
    }
    
    
}
