package com.upbest.mvc.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

public class BSingIfnoVO implements Serializable{
	private static final long serialVersionUID = 1L;
	String id;
	String name;
	String signintime;
	String signouttime;
	String signinlongitude;
	String signinlatitude;
	String location;
    String shopName;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSignintime() {
        return signintime;
    }
    public void setSignintime(String signintime) {
        this.signintime = signintime;
    }
    public String getSignouttime() {
        return signouttime;
    }
    public void setSignouttime(String signouttime) {
        this.signouttime = signouttime;
    }
    public String getSigninlongitude() {
        return signinlongitude;
    }
    public void setSigninlongitude(String signinlongitude) {
        this.signinlongitude = signinlongitude;
    }
    public String getSigninlatitude() {
        return signinlatitude;
    }
    public void setSigninlatitude(String signinlatitude) {
        this.signinlatitude = signinlatitude;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
   
	
	
}
