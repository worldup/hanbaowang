package com.upbest.mvc.vo;

import java.io.Serializable;

public class SelectionVO implements Serializable {
    private static final long serialVersionUID = -7808643349076959832L;
    private String value;
    private String name;
    private String related;
    
    
    public SelectionVO() {
	}
    
	public SelectionVO(String name,String value,String related) {
		this.value = value;
		this.name = name;
		this.related=related;
	}
	
	public SelectionVO(String name,String value) {
        this.value = value;
        this.name = name;
    }


	public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getRelated() {
        return related;
    }

    public void setRelated(String related) {
        this.related = related;
    }
    

}
