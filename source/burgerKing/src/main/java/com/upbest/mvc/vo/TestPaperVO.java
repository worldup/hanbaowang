package com.upbest.mvc.vo;

import com.upbest.mvc.entity.BTestPaper;

/**
 * 用户考卷评测情况
 * @author QunZheng
 *
 */
public class TestPaperVO extends BTestPaper{
	private String username;
	private String stateDesc;
	private String shopname;
	private String examName;
	
	

	public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStateDesc() {
		return stateDesc;
	}

	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }
	
	
	
}
