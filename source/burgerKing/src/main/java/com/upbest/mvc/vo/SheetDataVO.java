package com.upbest.mvc.vo;

import java.util.List;

public abstract class SheetDataVO<T> {
	private String[] heads;
	private List<T> dataList;
	private String sheetName;
	
	public SheetDataVO(String sheetName,List<T> dataList){
		this.dataList = dataList;
		this.sheetName = sheetName;
	}
	
	public String[] getHeads() {
		return heads;
	}
	
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * 获取行数
	 * @return
	 */
	public int getNumOfRows(){
		return dataList.size();
	}
	
	protected List<T> getDataList() {
		return dataList;
	}

	protected void setHeads(String[] heads) {
		this.heads = heads;
	}

	protected void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public String getCellVal(int row,int col){
		if(row > getDataList().size() - 1){
			throw new IllegalArgumentException("row:" + row + "超过请求行数");
		}
		if(col > getHeads().length - 1){
			throw new IllegalArgumentException("col:" + col + "超过请求列数");
		}
		
		T vo = getDataList().get(row);
		return getValue(vo, col);
	}

	protected abstract String getValue(T vo, int col);
	
}
