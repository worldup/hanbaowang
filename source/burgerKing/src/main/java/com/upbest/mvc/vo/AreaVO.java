package com.upbest.mvc.vo;

import java.util.List;

import com.upbest.mvc.entity.BArea;

public class AreaVO extends BArea{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<AreaVO> children;

	public List<AreaVO> getChildren() {
		return children;
	}

	public void setChildren(List<AreaVO> children) {
		this.children = children;
	}
	
	
}
