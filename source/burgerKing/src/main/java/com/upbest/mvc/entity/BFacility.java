package com.upbest.mvc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name ="bk_facility")
public class BFacility implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;
	
	@Column(name="device_version")
	@Size(max=100)
	private String deviceversion;
	
	@Column(name="device_id")
	@Size(max=100)
	private String deviceid;
	
	@Column(name="device_brand")
	@Size(max=100)
	private String devicebrand;
	
	@Column(name="device_model")
	@Size(max=100)
	private String devicemodel;
	
	@Column(name="device_os")
	@Size(max=100)
	private String deviceos;
	
	@Column(name="device_os_version")
	private Integer deviceosversion;
	
	@Column(name="device_resolution")
	@Size(max=100)
	private String deviceresolution;
	
	@Column(name="user_id")
	private Integer userid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeviceversion() {
		return deviceversion;
	}

	public void setDeviceversion(String deviceversion) {
		this.deviceversion = deviceversion;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getDevicebrand() {
		return devicebrand;
	}

	public void setDevicebrand(String devicebrand) {
		this.devicebrand = devicebrand;
	}

	public String getDevicemodel() {
        return devicemodel;
    }

    public void setDevicemodel(String devicemodel) {
        this.devicemodel = devicemodel;
    }

    public String getDeviceos() {
        return deviceos;
    }

    public void setDeviceos(String deviceos) {
        this.deviceos = deviceos;
    }

    public Integer getDeviceosversion() {
		return deviceosversion;
	}

	public void setDeviceosversion(Integer deviceosversion) {
		this.deviceosversion = deviceosversion;
	}

	public String getDeviceresolution() {
		return deviceresolution;
	}

	public void setDeviceresolution(String deviceresolution) {
		this.deviceresolution = deviceresolution;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

    @Override
    public String toString() {
        return "BFacility [id=" + id + ", deviceversion=" + deviceversion + ", deviceid=" + deviceid + ", devicebrand=" + devicebrand + ", devicemodel=" + devicemodel + ", deviceos=" + deviceos
                + ", deviceosversion=" + deviceosversion + ", deviceresolution=" + deviceresolution + ", userid=" + userid + "]";
    }
}
