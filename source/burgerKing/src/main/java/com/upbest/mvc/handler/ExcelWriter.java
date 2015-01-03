package com.upbest.mvc.handler;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.upbest.mvc.vo.SheetDataVO;

/**
 * 
 * @author QunZheng
 *
 * @param <T>
 * 
 * SheetDataVO<BFacilityVO> sheetData = buildSheetDataVO();
	ExcelWriter writer = new ExcelWriter<SheetDataVO>(sheetData);
	writer.write(new FileOutputStream(file));
 */
public class ExcelWriter<T extends SheetDataVO>{
	
    private Workbook workbook;
    private CellStyle dataCellStyle;
    private List<? extends T> sheetDataList;
    
    public ExcelWriter(T item) {
    	List<T> list = new ArrayList<T>();
    	list.add(item);
    	
    	init(list);
	}
    
    public ExcelWriter(List<? extends T> items) {
    	init(items);
	}
    
	/**
     * @param items
     * @param outputStream
     * @throws Exception
     */
    public void write(OutputStream outputStream) throws Exception {
        for (SheetDataVO data : sheetDataList) {
        	Sheet sheet = workbook.getSheet(data.getSheetName());
        	int rows = data.getNumOfRows();
        	int cols = data.getHeads().length;
        	int startRow = 1;
        	for(int rowIndex = startRow;rowIndex <= rows;rowIndex++){
        		Row row = sheet.createRow(rowIndex);
        		
        		for (int col = 0; col < cols; col++) {
        			createStringCell(row, data.getCellVal(rowIndex - startRow, col), col);
				}
        	}
        }
        
        workbook.write(outputStream);
   	 	outputStream.close();
    }
    
	private void init(List<? extends T> items) {
		this.sheetDataList = items;
		
        workbook = new SXSSFWorkbook(100);
        
        for (SheetDataVO sheetData : items) {
        	Sheet sheet = workbook.createSheet(sheetData.getSheetName());
        	sheet.setDefaultColumnWidth(20);
        	addHeaders(sheet,sheetData.getHeads());
		}
        initDataStyle();
	}
	
	private void addHeaders(Sheet sheet, String[] heads) {
		 Workbook wb = sheet.getWorkbook();
		 
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
 
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(font);
 
        Row row = sheet.createRow(0);
        int col = 0;
 
        for (String header : heads) {
            Cell cell = row.createCell(col);
            cell.setCellValue(header);
            cell.setCellStyle(style);
            col++;
        }
	}

	private void initDataStyle() {
        dataCellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
 
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        dataCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        dataCellStyle.setFont(font);
    }
	
	protected void createStringCell(Row row, String val, int col) {
        Cell cell = row.createCell(col);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue(val);
    }
 
	protected void createNumericCell(Row row, Double val, int col) {
        Cell cell = row.createCell(col);
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(val);
    }

}
