package com.upbest.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.excel.mapping.PassThroughRowMapper;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import com.upbest.mvc.handler.FacilityHandler;
import com.upbest.mvc.handler.Handler;
import com.upbest.mvc.handler.RowMapperHandler;

public class ExcelUtils {
	
	public static PoiItemReader getPoiItemReader(int lineToSkip,Resource resource) throws Exception{
		PoiItemReader itemReader = new PoiItemReader();
        itemReader.setLinesToSkip(lineToSkip - 1);
        itemReader.setResource(resource);
        itemReader.setRowMapper(new PassThroughRowMapper());
       /* itemReader.setSkippedRowsCallback(new RowCallbackHandler() {

            public void handleRow(final Sheet sheet, final String[] row) {
                System.out.println("Skipping: " + row);
            }
        });*/
        itemReader.afterPropertiesSet();
        itemReader.open(new ExecutionContext());
        
        return itemReader;
	}
	
	public static PoiItemReader getPoiItemReader(int lineToSkip,InputStream inputStream) throws Exception{
        
        return getPoiItemReader(lineToSkip,new InputStreamResource(inputStream));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static PoiItemReader getPoiItemReader(int lineToSkip,File file) throws Exception{
		return getPoiItemReader(lineToSkip,new FileInputStream(file));
	}
	
	@SuppressWarnings("rawtypes")
	public  static <T> Object handExcel(int lineToSkip,InputStream inputStream,RowMapperHandler<T> rowMapperHandler){
		int failedCount = 0;
		PoiItemReader itemReader = null;
		try {
			itemReader = getPoiItemReader(lineToSkip, inputStream);
			itemReader.setRowMapper(rowMapperHandler.getRowMapper());
			
			T obj = null;
			do{
				obj = (T)itemReader.read();
				boolean handlerSuccess = rowMapperHandler.handler(obj);
				failedCount += handlerSuccess ? 0 : 1;
			}while(obj != null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(itemReader);
		}
		
		return failedCount;
	}
	
	@SuppressWarnings("rawtypes")
	public static Object handExcel(int lineToSkip,InputStream inputStream,Handler handler) throws Exception{
		return handExcel(lineToSkip, new InputStreamResource(inputStream), handler);
	}
	
	@SuppressWarnings("rawtypes")
	public  static <T> Object handExcel(int lineToSkip,File file,RowMapperHandler<T> rowMapperHandler) throws FileNotFoundException{
		return handExcel(lineToSkip, new FileInputStream(file), rowMapperHandler);
	}
	
	@SuppressWarnings("rawtypes")
	public static Object handExcel(int lineToSkip,File file,Handler handler) throws Exception{
		return handExcel(lineToSkip, new FileInputStream(file), handler);
	}
	
	public static void close(PoiItemReader reader){
		if(reader != null){
			reader.close();
			
		}
	}

	public static Object handExcel(int lineToSkip, Resource resource,
			Handler handler) {
		int failedCount = 0;
		
		PoiItemReader itemReader = null;
		
		try {
			itemReader = getPoiItemReader(lineToSkip, resource);
			String[] rows = null;
			do{
				rows = (String[]) itemReader.read();
				boolean handlerSuccess = handler.handerRow(rows);
				failedCount += handlerSuccess ? 0 : 1;
			}while(rows != null);
		}catch(Exception e){
		    e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			if(itemReader != null){
				itemReader.close();
			}
		}
		
		return failedCount;
	}
}
