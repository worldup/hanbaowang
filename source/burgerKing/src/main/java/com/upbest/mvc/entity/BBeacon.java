package com.upbest.mvc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bk_beacon")
public class BBeacon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;
    
    @Column(name = "uuid")
    private String uuid;
    
    @Column(name = "major_id")
    private String majorId;
    
    @Column(name = "minor_id")
    private String minorId;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "mac")
    private String mac;
    
    @Column(name = "measurrd_power")
    private String measurrdPower;
    
    @Column(name = "signal_strength")
    private String signalStrength;
    
    @Column(name = "remaining_power")
    private String remainingPower;
    
    @Column(name = "last_date")
    private Date lastDate;
    
    @Column(name = "company")
    private String company;
    
    @Column(name = "model")
    private String model;
    
    @Column(name = "mold")
    private String mold;
    
    @Column(name = "distance")
    private String distance;
    
    @Column(name = "remarks")
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

    @Override
    public String toString() {
        return "BBeacon [id=" + id + ", uuid=" + uuid + ", majorId=" + majorId + ", minorId=" + minorId + ", name=" + name + ", mac=" + mac + ", measurrdPower=" + measurrdPower + ", signalStrength="
                + signalStrength + ", remainingPower=" + remainingPower + ", lastDate=" + lastDate + ", company=" + company + ", model=" + model + ", mold=" + mold + ", distance=" + distance
                + ", remarks=" + remarks + "]";
    }
}
