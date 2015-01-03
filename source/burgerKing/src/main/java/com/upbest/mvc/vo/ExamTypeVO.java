package com.upbest.mvc.vo;


public class ExamTypeVO{
	
	private static final long serialVersionUID = 1L;
    //模板ID
	private String eType;
	//模板名称
	private String bValue;
	//得分
	private Integer total;
	
	//问卷开始时间
	private Long beginTime;
	
	private String  realName;
	
	public ExamTypeVO(String eType,String bValue,Integer total,Long beginTime,String realName){
	    this.eType=eType;
	    this.bValue=bValue;
	    this.total=total;
	    this.beginTime=beginTime;
	    this.realName=realName;
	}
	
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String geteType() {
        return eType;
    }
    public void seteType(String eType) {
        this.eType = eType;
    }
    public String getbValue() {
        return bValue;
    }
    public void setbValue(String bValue) {
        this.bValue = bValue;
    }
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public Long getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }
	
	
}
