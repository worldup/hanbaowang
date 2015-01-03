//package com.upbest.mvc.handler;
//
//import java.io.OutputStream;
//import java.util.List;
//
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//
//import com.upbest.mvc.vo.BFacilityVO;
//
//public class FacilityExcelWriter extends ExcelWriter<BFacilityVO> {
//
//	public FacilityExcelWriter(OutputStream outputStream) {
//		super(outputStream);
//	}
//
//	public FacilityExcelWriter(OutputStream outputStream, Workbook workbook,
//			String sheetName) {
//		super(outputStream, workbook, sheetName);
//	}
//
//
//
//	@Override
//	protected void doWrite(List<? extends BFacilityVO> items, Workbook workbook)
//			throws Exception {
//		Sheet sheet = workbook.getSheetAt(0);
//		 
//        for (BFacilityVO data : items) {
//            Row row = sheet.createRow(getCurrRow());
//            createStringCell(row, data.getDevicebrand(), 0);
//            createStringCell(row, data.getDeviceid(), 1);
//            createStringCell(row, data.getDevicemodel(), 2);
//            createStringCell(row, data.getDeviceos(), 3);
//            
//            nextRow();
//        }
//
//	}
//
//	@Override
//	protected String[] getHeads() {
//		return new String[]{"设备品牌","设备标识号","设备型号","设备系统"};
//	}
//	
//}
