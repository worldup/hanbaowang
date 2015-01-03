package com.upbest.mvc.vo;

import java.io.Serializable;
import java.util.Date;

public class BeaconVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    
    private String uuid;
    
    private String majorId;
    
    private String minorId;
    
    private String shopName;
    
    private Integer shopId;
    
    private Integer beaconShopId;
    
    private String name;
    
    private String mac;
    
    private String measurrdPower;
    
    private String signalStrength;
    
    private String remainingPower;
    
    private Date lastDate;
    
    private String company;
    
    private String model;
    
    private String mold;
    
    private String distance;
    
    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getMinorId() {
        return minorId;
    }

    public void setMinorId(String minorId) {
        this.minorId = minorId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getBeaconShopId() {
        return beaconShopId;
    }

    public void setBeaconShopId(Integer beaconShopId) {
        this.beaconShopId = beaconShopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMeasurrdPower() {
        return measurrdPower;
    }

    public void setMeasurrdPower(String measurrdPower) {
        this.measurrdPower = measurrdPower;
    }

    public String getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(String signalStrength) {
        this.signalStrength = signalStrength;
    }

    public String getRemainingPower() {
        return remainingPower;
    }

    public void setRemainingPower(String remainingPower) {
        this.remainingPower = remainingPower;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMold() {
        return mold;
    }

    public void setMold(String mold) {
        this.mold = mold;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
