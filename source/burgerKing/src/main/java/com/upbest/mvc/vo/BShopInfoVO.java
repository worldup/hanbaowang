package com.upbest.mvc.vo;

import java.io.Serializable;
import java.util.Date;
public class BShopInfoVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String shopname;

    private String shopphone;

    private String shopaddress;

    private String shopnum;

    private String shopimage;

    private String shopsize;

    private Date shopopentime;

    private String shopbusinesstime;

    private Integer shopseatnum;

    private String shopbusinessarea;

    private Date createTime;
    private String userIds;
    private String userNames;
    
    private String regional;
    private String province;
    private String prefecture;
    private String cityGrade;
    private String district;
    private String mold;
    private String businessCircle;
    private String circleSize;
    private String subwayNumber;
    private String anExit;
    private String meters;
    private String busNumber;
    private String competitor;
    private String mainCompetitors;
    private String rivalsName;
    private String kfcNumber;
    private String mnumber;
    private String pizzaNumber;
    private String starbucksNumber;
    private Date updateTime;
    private String chineseName;
    private String englishName;
    private String chineseAddress;
    private String englishAddress;
    private String straightJointJoin;
    private String longitude;
    private String latitude;
    private String email;
    private String storeInfo1;
    private String storeInfo2;
    
    /**
     * 价格组
     */
    private String tiers;
    
    /**
     * 厨房面积
     */
    private Double kitchenArea;
    
    /**
     * 营业状态
     */
    private String status;
    
    /**
     * 重装开业
     */
    private Date reOpenTime;
    
    /**
     * 餐厅租期到期日
     */
    private Date expireTime;
    
    /**
     * 租金信息
     */
    private String currentRent;
    
    /**
     * 设备折旧
     */
    private String equipment;
    
    /**
     * 装修摊销
     */
    private String lhi;
    
    /**
     * 品牌延伸
     * 1,2,3
     */
    private String brandExtension;
    
    /**
     * 品牌延伸
     * 对应的中文描述
     */
    private String brandExtensionChDesc;
    
    /**
     * 最后更新人
     */
    private String lastOperator;
    
    /**
     * 保本营业额
     */
    private Double ebitda;
    
	private String om;
    
    
    public String getOm() {
		return om;
	}
	public void setOm(String om) {
		this.om = om;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
    public String getUserIds() {
        return userIds;
    }
    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }
    public String getUserNames() {
        return userNames;
    }
    public void setUserNames(String userNames) {
        this.userNames = userNames;
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
    
    public String getMnumber() {
        return mnumber;
    }
    public void setMnumber(String mnumber) {
        this.mnumber = mnumber;
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
	public String getBrandExtensionChDesc() {
		return brandExtensionChDesc;
	}
	public void setBrandExtensionChDesc(String brandExtensionChDesc) {
		this.brandExtensionChDesc = brandExtensionChDesc;
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
    public Double getEbitda() {
		return ebitda;
	}
	public void setEbitda(Double ebitda) {
		this.ebitda = ebitda;
	}
}
