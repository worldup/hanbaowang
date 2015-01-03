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
@Table(name = "bk_sign_info")
public class BSignInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;
	
	@Column(name="user_id")
	private Integer userid;
	
	@Column(name="shop_id")
	private Integer shopId;
	
	@Column(name="sign_in_time")
	private Date signintime;
	
	@Column(name="sign_out_time")
	private Date signouttime;
	
	@Column(name="sign_in_longitude")
	@Size(max=100)
	private String signinlongitude;
	
	
	@Column(name="sign_in_latitude")
	@Size(max=100)
	private String signinlatitude;
	
	@Column(name="location")
	@Size(max=100)
	private String location;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getUserid() {
        return userid;
    }


    public void setUserid(Integer userid) {
        this.userid = userid;
    }


    public Date getSignintime() {
        return signintime;
    }


    public void setSignintime(Date signintime) {
        this.signintime = signintime;
    }


    public Date getSignouttime() {
        return signouttime;
    }


    public void setSignouttime(Date signouttime) {
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
    
    public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}


	@Override
    public String toString() {
        return "BSignInfo [id=" + id + ", userid=" + userid + ", signintime=" + signintime + ", signouttime=" + signouttime + ", signinlongitude=" + signinlongitude + ", signinlatitude="
                + signinlatitude + ", location=" + location + "]";
    }
    



	
}
