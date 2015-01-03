package com.upbest.mvc.vo;

/**
 * 门店地区信息
 * @author QunZheng
 *
 */
public class ShopAreaVO {
	/**
	 * 区域
	 */
	private String regional;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 地市
	 */
	private String city;
	/**
	 * 区县
	 */
	private String district;
	/**
	 * 门店号
	 */
	private String shopNum;
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getShopNum() {
		return shopNum;
	}
	public void setShopNum(String shopNum) {
		this.shopNum = shopNum;
	}
	
	
	
}
