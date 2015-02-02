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
@Table(name = "bk_shop_info")
public class BShopInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;
	
	@Column(name="shop_name")
	@Size(max=100)
	private String shopname;
	
	@Column(name="shop_phone")
	@Size(max=100)
	private String shopphone;
	
	@Column(name="shop_address")
	@Size(max=1000)
	private String shopaddress;
	
	@Column(name="shop_num")
	@Size(max=1000)
	private String shopnum;
	
	@Column(name="shop_image")
	@Size(max=1000)
	private String shopimage;
	
	@Column(name="shop_size")
	@Size(max=100)
	private String shopsize;

	@Column(name="shop_open_time")
	private Date shopopentime;
	
	@Column(name="shop_business_time")
	private String shopbusinesstime;
	
	@Column(name="shop_seat_num")
	private Integer shopseatnum;
	
	@Column(name="shop_business_area")
	private String shopbusinessarea;
	
	@Column(name="create_time")
    private Date createTime;
	
	@Column(name="regional")
	@Size(max=100)
	private String regional;
	
	@Column(name="province")
	@Size(max=100)
	private String province;
	
	@Column(name="prefecture")
    @Size(max=100)
    private String prefecture;
	
	@Column(name="city_grade")
    @Size(max=100)
    private String cityGrade;
	
	@Column(name="district")
    @Size(max=100)
    private String district;
	
	@Column(name="mold")
    @Size(max=100)
    private String mold;
	
	@Column(name="business_circle")
    @Size(max=100)
    private String businessCircle;
	
	@Column(name="circle_size")
    @Size(max=100)
    private String circleSize;
	
	@Column(name="subway_number")
    @Size(max=100)
    private String subwayNumber;
	
	@Column(name="an_exit")
    @Size(max=100)
    private String anExit;
	
	@Column(name="meters")
    @Size(max=100)
    private String meters;
	
	@Column(name="bus_number")
    @Size(max=100)
    private String busNumber;
	
	@Column(name="competitor")
    @Size(max=100)
    private String competitor;
	
	@Column(name="main_competitors")
    @Size(max=100)
    private String mainCompetitors;
	
	@Column(name="rivals_name")
    @Size(max=100)
    private String rivalsName;
	
	@Column(name="kfc_number")
    @Size(max=100)
    private String kfcNumber;
	
	@Column(name="m_number")
    @Size(max=100)
    private String mnumber;
	
	@Column(name="pizza_number")
    @Size(max=100)
    private String pizzaNumber;
	
	@Column(name="starbucks_number")
    @Size(max=100)
    private String starbucksNumber;
	
	@Column(name="update_time")
    private Date updateTime;
	
	
	@Column(name="chinese_name")
	@Size(max=100)
    private String chineseName;
	
	@Column(name="english_name")
    @Size(max=100)
    private String englishName;
	
	@Column(name="chinese_address")
    @Size(max=100)
    private String chineseAddress;
	
	@Column(name="english_address")
    @Size(max=100)
    private String englishAddress;
	
	
	
	@Column(name="straight_joint_join")
    private String straightJointJoin;
	
	@Column(name="longitude")
    @Size(max=100)
    private String longitude;
    
    @Column(name="latitude")
    @Size(max=100)
    private String latitude;
    
    @Column(name="email")
    private String email;
    
    @Column(name="storeInfo1")
    private String storeInfo1;
    
    @Column(name="storeInfo2")
    private String storeInfo2;
    
    /**
     * 价格组
     */
    @Column(name="tiers")
    private String tiers;
    
    /**
     * 厨房面积
     */
    @Column(name="kitchen_area")
    private Double kitchenArea;
    
    /**
     * 营业状态
     */
    @Column(name="status")
    private String status;
    
    /**
     * 重装开业
     */
    @Column(name="reopen_time")
    private Date reOpenTime;
    
    /**
     * 餐厅租期到期日
     */
    @Column(name="expire_time")
    private Date expireTime;
    
    /**
     * 租金信息
     */
    @Column(name="current_rent")
    private String currentRent;
    
    /**
     * 设备折旧
     */
    @Column(name="equipment")
    private String equipment;
    
    /**
     * 装修摊销
     */
    @Column(name="lhi")
    private String lhi;
    
    /**
     * 品牌延伸
     */
    @Column(name="brand_extension")
    private String brandExtension;
    
    /**
     * 最后更新人
     */
    @Column(name="last_operator")
    private String lastOperator;
    
    /**
     * 保本营业额
     */
    @Column(name="ebitda")
    private Double ebitda;

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

    public String getShopphone() {
        return shopphone;
    }

    public void setShopphone(String shopphone) {
        this.shopphone = shopphone;
    }

    public String getShopaddress() {
        return shopaddress;
    }

    public void setShopaddress(String shopaddress) {
        this.shopaddress = shopaddress;
    }

    public String getShopnum() {
        return shopnum;
    }

    public void setShopnum(String shopnum) {
        this.shopnum = shopnum;
    }

    public String getShopimage() {
        return shopimage;
    }

    public void setShopimage(String shopimage) {
        this.shopimage = shopimage;
    }

    public String getShopsize() {
        return shopsize;
    }

    public void setShopsize(String shopsize) {
        this.shopsize = shopsize;
    }

    public Date getShopopentime() {
        return shopopentime;
    }

    public void setShopopentime(Date shopopentime) {
        this.shopopentime = shopopentime;
    }

    public String getShopbusinesstime() {
        return shopbusinesstime;
    }

    public void setShopbusinesstime(String shopbusinesstime) {
        this.shopbusinesstime = shopbusinesstime;
    }

    public Integer getShopseatnum() {
        return shopseatnum;
    }

    public void setShopseatnum(Integer shopseatnum) {
        this.shopseatnum = shopseatnum;
    }

    public String getShopbusinessarea() {
        return shopbusinessarea;
    }

    public void setShopbusinessarea(String shopbusinessarea) {
        this.shopbusinessarea = shopbusinessarea;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getCityGrade() {
        return cityGrade;
    }

    public void setCityGrade(String cityGrade) {
        this.cityGrade = cityGrade;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getMold() {
        return mold;
    }

    public void setMold(String mold) {
        this.mold = mold;
    }

    public String getBusinessCircle() {
        return businessCircle;
    }

    public void setBusinessCircle(String businessCircle) {
        this.businessCircle = businessCircle;
    }

    public String getCircleSize() {
        return circleSize;
    }

    public void setCircleSize(String circleSize) {
        this.circleSize = circleSize;
    }

    public String getSubwayNumber() {
        return subwayNumber;
    }

    public void setSubwayNumber(String subwayNumber) {
        this.subwayNumber = subwayNumber;
    }

    public String getAnExit() {
        return anExit;
    }

    public void setAnExit(String anExit) {
        this.anExit = anExit;
    }

    public String getMeters() {
        return meters;
    }

    public void setMeters(String meters) {
        this.meters = meters;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getCompetitor() {
        return competitor;
    }

    public void setCompetitor(String competitor) {
        this.competitor = competitor;
    }

    public String getMainCompetitors() {
        return mainCompetitors;
    }

    public void setMainCompetitors(String mainCompetitors) {
        this.mainCompetitors = mainCompetitors;
    }

    public String getRivalsName() {
        return rivalsName;
    }

    public void setRivalsName(String rivalsName) {
        this.rivalsName = rivalsName;
    }

    public String getKfcNumber() {
        return kfcNumber;
    }

    public void setKfcNumber(String kfcNumber) {
        this.kfcNumber = kfcNumber;
    }

   
    public String getPizzaNumber() {
        return pizzaNumber;
    }

    public void setPizzaNumber(String pizzaNumber) {
        this.pizzaNumber = pizzaNumber;
    }

    public String getStarbucksNumber() {
        return starbucksNumber;
    }

    public void setStarbucksNumber(String starbucksNumber) {
        this.starbucksNumber = starbucksNumber;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseAddress() {
        return chineseAddress;
    }

    public void setChineseAddress(String chineseAddress) {
        this.chineseAddress = chineseAddress;
    }

    public String getEnglishAddress() {
        return englishAddress;
    }

    public void setEnglishAddress(String englishAddress) {
        this.englishAddress = englishAddress;
    }

    public String getStraightJointJoin() {
        return straightJointJoin;
    }

    public void setStraightJointJoin(String straightJointJoin) {
        this.straightJointJoin = straightJointJoin;
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
    
    public String getMnumber() {
        return mnumber;
    }

    public void setMnumber(String mnumber) {
        this.mnumber = mnumber;
    }
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public String getStoreInfo1() {
        return storeInfo1;
    }

    public void setStoreInfo1(String storeInfo1) {
        this.storeInfo1 = storeInfo1;
    }

    public String getStoreInfo2() {
        return storeInfo2;
    }

    public void setStoreInfo2(String storeInfo2) {
        this.storeInfo2 = storeInfo2;
    }

    public String getTiers() {
		return tiers;
	}

	public void setTiers(String tiers) {
		this.tiers = tiers;
	}


	public Double getKitchenArea() {
		return kitchenArea;
	}

	public void setKitchenArea(Double kitchenArea) {
		this.kitchenArea = kitchenArea;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getReOpenTime() {
		return reOpenTime;
	}

	public void setReOpenTime(Date reOpenTime) {
		this.reOpenTime = reOpenTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public String getCurrentRent() {
		return currentRent;
	}

	public void setCurrentRent(String currentRent) {
		this.currentRent = currentRent;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getLhi() {
		return lhi;
	}

	public void setLhi(String lhi) {
		this.lhi = lhi;
	}

	public String getBrandExtension() {
		return brandExtension;
	}

	public void setBrandExtension(String brandExtension) {
		this.brandExtension = brandExtension;
	}

	public String getLastOperator() {
		return lastOperator;
	}

	public void setLastOperator(String lastOperator) {
		this.lastOperator = lastOperator;
	}

    public Double getEbitda() {
		return ebitda;
	}

	public void setEbitda(Double ebitda) {
		this.ebitda = ebitda;
	}

	@Override
    public String toString() {
        return "BShopInfo [id=" + id + ", shopname=" + shopname + ", shopphone=" + shopphone + ", shopaddress=" + shopaddress + ", shopnum=" + shopnum + ", shopimage=" + shopimage + ", shopsize="
                + shopsize + ", shopopentime=" + shopopentime + ", shopbusinesstime=" + shopbusinesstime + ", shopseatnum=" + shopseatnum + ", shopbusinessarea=" + shopbusinessarea + ", createTime="
                + createTime + ", regional=" + regional + ", province=" + province + ", prefecture=" + prefecture + ", cityGrade=" + cityGrade + ", district=" + district + ", mold=" + mold
                + ", businessCircle=" + businessCircle + ", circleSize=" + circleSize + ", subwayNumber=" + subwayNumber + ", anExit=" + anExit + ", meters=" + meters + ", busNumber=" + busNumber
                + ", competitor=" + competitor + ", mainCompetitors=" + mainCompetitors + ", rivalsName=" + rivalsName + ", kfcNumber=" + kfcNumber + ", mnumber=" + mnumber + ", pizzaNumber="
                + pizzaNumber + ", starbucksNumber=" + starbucksNumber + ", updateTime=" + updateTime + ", chineseName=" + chineseName + ", englishName=" + englishName + ", chineseAddress="
                + chineseAddress + ", englishAddress=" + englishAddress + ", straightJointJoin=" + straightJointJoin + ", longitude=" + longitude + ", latitude=" + latitude + ", email=" + email
                + ", storeInfo1=" + storeInfo1 + ", storeInfo2=" + storeInfo2 + ", tiers=" + tiers + ", kitchenArea=" + kitchenArea + ", status=" + status + ", reOpenTime=" + reOpenTime
                + ", expireTime=" + expireTime + ", currentRent=" + currentRent + ", equipment=" + equipment + ", lhi=" + lhi + ", brandExtension=" + brandExtension + ", lastOperator=" + lastOperator
                + "]";
    }
}
