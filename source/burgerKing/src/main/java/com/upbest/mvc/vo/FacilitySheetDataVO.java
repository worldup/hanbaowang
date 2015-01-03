package com.upbest.mvc.vo;

import java.util.List;

public class FacilitySheetDataVO extends SheetDataVO<BFacilityVO> {
	
	public FacilitySheetDataVO(String sheetName,List<BFacilityVO> dataList) {
		super(sheetName,dataList);
		setHeads(new String[]{"设备品牌","设备标识号","设备型号","设备系统"});
	}
	
	@Override
	protected String getValue(BFacilityVO vo, int col) {
		String val = "";
		switch (col) {
			case 0:
				val = vo.getDevicebrand();
				break;
			case 1:
				val = vo.getDeviceid();
				break;
			case 2:
				val = vo.getDevicemodel();
				break;
			case 3:
				val = vo.getDeviceos();
				break;
			default:
				val = "";
				break;
		}
		return val;
	}

}
