package com.upbest.mvc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bk_beacon_shop")
public class BBeaconShop implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;
    
    @Column(name = "shop_id")
    private Integer shopId;
    
    @Column(name = "beacon_id")
    private Integer beaconId;

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

    public Integer getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(Integer beaconId) {
        this.beaconId = beaconId;
    }

    @Override
    public String toString() {
        return "BBeaconShop [id=" + id + ", shopId=" + shopId + ", beaconId=" + beaconId + "]";
    }
    
}
