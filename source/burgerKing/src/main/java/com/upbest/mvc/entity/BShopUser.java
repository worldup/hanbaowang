package com.upbest.mvc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "bk_shop_user")
public class BShopUser implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;
	
	@Column(name="shop_id")
	private Integer shopId;
	
	@Column(name="user_id")
	private Integer userId;
	
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
}
