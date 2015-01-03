package com.upbest.mvc.vo;

import java.util.Date;

public class LoseDetailVO{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private Date beginTime;
	private String realName;
	private String emp;
	private String role;
	private String value;
	private String pic;
	private String shopName;
	private String shopNum;
	//描述问题
	private String desc;
	
	private String testValue;
	private String quesValue;
	
  
    public String getTestValue() {
        return testValue;
    }
    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }
    public String getQuesValue() {
        return quesValue;
    }
    public void setQuesValue(String quesValue) {
        this.quesValue = quesValue;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Date getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getEmp() {
        return emp;
    }
    public void setEmp(String emp) {
        this.emp = emp;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getPic() {
        return pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
    public String getShopName() {
        return shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getShopNum() {
        return shopNum;
    }
    public void setShopNum(String shopNum) {
        this.shopNum = shopNum;
    }
}
