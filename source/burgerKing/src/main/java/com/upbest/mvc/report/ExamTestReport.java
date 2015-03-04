package com.upbest.mvc.report;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.upbest.utils.ConfigUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;

import com.upbest.mvc.service.IExamService;
import com.upbest.mvc.vo.ExamDetailInfoVO;
import com.upbest.mvc.vo.ExamDetailInfoVO.AdditionalModule;
import com.upbest.mvc.vo.ExamDetailInfoVO.Field;
import com.upbest.mvc.vo.ExamDetailInfoVO.Module;
import com.upbest.mvc.vo.ExamDetailInfoVO.Question;
import com.upbest.mvc.vo.ExamDetailInfoVO.QuestionTestDetailInfo;
import com.upbest.utils.AddDimensionedImage;

public class ExamTestReport {
	protected IExamService examService;
	protected Workbook wb;
	
	public static final String HEAD_LABEL = "headLabel";
	public static final String HEAD_VALUE = "headValue";
	public static final String MODULER_DESC = "moduler_desc";
	public static final String MODULER_HEAD = "moduler_head";
	public static final String MODULER_NAME = "moduler_name";
	public static final String QUESTION_NUM = "question_num";
	public static final String QUESTION_DESC = "question_desc";
	public static final String MODULER_ADDITIONAL_NAME = "moduler_additional_name";
	public static final String HTTP_HREF = "http_href";
	public static final String CELL_MIDDLE = "cell_middle";
	public static final String CELL_RIGHT = "cell_right";
	public static final String FIELD_VALUE = "field_val";
	public static final String ENVICE_NAME = "取证图片";
	public static final String TITLE = "title";
	
	public static final String LOGO_URI = "images/logo.jpg";
	
	public ExamTestReport(IExamService service){
		this.examService = service;
		wb = new XSSFWorkbook();
	}
	
	/**
	 * 字段的权重
	 */
	public byte[] generateReport(String fullServerPath,int testPaperId) throws Exception{
		ExamDetailInfoVO examDetailInfo = examService.findAfterTestExamDetaiInfo(testPaperId);
		return generateExcel(fullServerPath,examDetailInfo);
	}

	/**
	 * 生成excel
	 * @throws Exception 
	 */
	protected byte[] generateExcel(String fullServerPath,ExamDetailInfoVO examDetailInfo) throws Exception {
		 Map<String, CellStyle> styles = createStyles(wb);
		 
		 String examNam = examDetailInfo.getBasicInfo().getExamName();
		 Sheet sheet = wb.createSheet(examDetailInfo.getBasicInfo().getExamName());
		//turn off gridlines
         sheet.setDisplayGridlines(false);
         sheet.setPrintGridlines(false);
         sheet.setFitToPage(true);
         sheet.setHorizontallyCenter(true);
         PrintSetup printSetup = sheet.getPrintSetup();
         printSetup.setLandscape(true);
		 
		 Integer firstRow = 0;
		 int lastRow = buildTitleInfo(sheet,styles,examNam,firstRow);
		 lastRow = buildHeadInfo(examDetailInfo,sheet,styles,lastRow);
		 lastRow = buildModulerInfo(fullServerPath,examDetailInfo,sheet,styles,lastRow,wb.getCreationHelper());
		 buildAdditionalModulerInfo(examDetailInfo,sheet,styles,lastRow);
		 
		 ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		 FileOutputStream outputStream = new FileOutputStream("c:/poi/side_by_side.xlsx");
		 wb.write(outputStream);
		 
		 return outputStream.toByteArray();
	}

	private int buildTitleInfo(Sheet sheet,Map<String, CellStyle> styles, String examNam,Integer firstRow) {
		Row titleRow = sheet.createRow(firstRow);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(examNam);
		titleCell.setCellStyle(styles.get(TITLE));
//		String region = "A"+ (firstRow+1) +":L"+ (firstRow+1);
		sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), 0, 11));
		try {
			new AddDimensionedImage().addImageToSheet("L" + (firstRow+1), sheet, sheet.createDrawingPatriarch(),
					 new ClassPathResource(LOGO_URI).getURL(), 10, 10,
					 AddDimensionedImage.EXPAND_ROW_AND_COLUMN);
		} catch (Exception e) {
			e.printStackTrace();
			//TODO logger
		}
		return firstRow + 1;
	}

	/**
	 * 生成头信息
	 * @param examDetailInfo
	 * @param sheet
	 * @param styles
	 * @param firstRow
	 */
	protected int buildHeadInfo(ExamDetailInfoVO examDetailInfo, Sheet sheet, Map<String, CellStyle> styles,Integer firstRow)throws Exception {
		//生成头信息
		 List<Field> heads = examDetailInfo.getHeads();
		 if(!CollectionUtils.isEmpty(heads)){
//			 Row headerTitle = sheet.createRow(firstRow++);
			 /*Cell titleCell = headerTitle.createCell(0);
			 titleCell.setCellValue("抬头描述");
			 titleCell.setCellStyle(styles.get(MODULER_DESC));
			 sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$N$1"));*/
			 
			 int headCount = 3;
			 Row header = sheet.createRow(firstRow++);
			 int cellIndex = 0;
			 for(int i = 0;i < heads.size();i++){
				 Field field = heads.get(i);
				 
				 if(i != 0 && (i % headCount) == 0){
					 header = sheet.createRow(firstRow++);
					 cellIndex = 0;
				 }
				 Cell head_label = header.createCell(cellIndex * 4);
				 Cell head_label_1 = header.createCell(cellIndex * 4 + 1);
				 Cell head_value = header.createCell(cellIndex * 4 + 2);
				 Cell head_value_1 = header.createCell(cellIndex * 4 + 3);
				 
				 head_label.setCellValue(field.getFieldName());
				 head_label.setCellStyle(styles.get(HEAD_LABEL));
				 head_label_1.setCellStyle(styles.get(HEAD_LABEL));
				 sheet.addMergedRegion(new CellRangeAddress(header.getRowNum(), header.getRowNum(), head_label.getColumnIndex(), head_label.getColumnIndex() + 1));
				 
				 head_value.setCellValue(getHeadValue(field));
				 head_value.setCellStyle(styles.get(HEAD_VALUE));
				 head_value_1.setCellStyle(styles.get(HEAD_LABEL));
				 sheet.addMergedRegion(new CellRangeAddress(header.getRowNum(), header.getRowNum(), head_value.getColumnIndex(), head_value.getColumnIndex() + 1));
				 
				 cellIndex++;
				 
			 }
			 
			 addExtraHeads(examDetailInfo,header,cellIndex,sheet,styles);
		 }
		 return firstRow;
	}
	

	/**
	 * 生成模块信息
	 * @param examDetailInfo
	 * @param sheet
	 * @param styles
	 * @param firstRow
	 * @param creationHelper 
	 */
	protected int buildModulerInfo(String fullServerPath,ExamDetailInfoVO examDetailInfo, Sheet sheet,
			Map<String, CellStyle> styles,Integer firstRow, CreationHelper creationHelper) {
		List<Module> modules = examDetailInfo.getModules();
		if(!CollectionUtils.isEmpty(modules)){
			for (Module module : modules) {
				//显示模块描述
				if(StringUtils.isEmpty(module.getDesc())){
					Row moduleDesc = sheet.createRow(firstRow++);
					Cell titleCell = moduleDesc.createCell(0);
					titleCell.setCellValue(module.getDesc());
					titleCell.setCellStyle(styles.get(MODULER_DESC));
					int regionRow = moduleDesc.getRowNum() + 1;
					String region = "$A$"+ regionRow +":$N$"+ regionRow;
					sheet.addMergedRegion(CellRangeAddress.valueOf(region));
				}
				//显示模块头
				int col = 0,colSpan = 6;
				Row moduleHead = sheet.createRow(firstRow++);
				Cell moduleNameCel = moduleHead.createCell(col++);
				moduleNameCel.setCellValue(module.getName());
				moduleNameCel.setCellStyle(styles.get(MODULER_NAME));
				sheet.addMergedRegion(new CellRangeAddress(moduleHead.getRowNum(), moduleHead.getRowNum(), 0, colSpan));
				
				col = colSpan + 1;
				Map<Integer, Integer> fieldColMap = new HashMap<Integer, Integer>();
				Set<Field> fields = module.getFields();
				fields = doWrapModuleFields(module,fields);
				if(!CollectionUtils.isEmpty(fields)){
					List<Field> sortFields = sortField(fields);
					for (Field field : sortFields) {
						Cell fieldCell = moduleHead.createCell(col++);
						fieldCell.setCellValue(field.getFieldName());
						fieldCell.setCellStyle(styles.get(MODULER_HEAD));
						
						fieldColMap.put(field.getId(), fieldCell.getColumnIndex());
					}
				}
				
				//显示模块下的问题描述
				List<Question> questions = module.getQuestions();
				if(!CollectionUtils.isEmpty(questions)){
					for (int i = 0;i < questions.size();i++) {
						int quesCol = 0;
						
						Question ques = questions.get(i);
						Row questionRow = sheet.createRow(firstRow++);
						questionRow.setHeightInPoints(100);
						
						Cell numCell = questionRow.createCell(quesCol++);
						numCell.setCellValue(i+1);
						numCell.setCellStyle(styles.get(QUESTION_NUM));
						
						Cell quesDescCell = questionRow.createCell(quesCol++);
						quesDescCell.setCellValue(ques.getQuesDesc());
						sheet.addMergedRegion(new CellRangeAddress(questionRow.getRowNum(), questionRow.getRowNum(), quesDescCell.getColumnIndex(), colSpan));
						quesDescCell.setCellStyle(styles.get(QUESTION_DESC));
						for(int j = quesDescCell.getColumnIndex() + 1;j<=colSpan;j++){
							Cell cell = questionRow.createCell(j);
							cell.setCellStyle(styles.get(QUESTION_DESC));
						}
						
						QuestionTestDetailInfo testDetailInfo = ques.getTestDetailInfo();
						List<Field> fieldInfos = testDetailInfo.getFieldInfos();
						fieldInfos = doWrapFieldInfo(fieldInfos,ques,module);
						if(!CollectionUtils.isEmpty(fieldInfos)){
							for (Field field : fieldInfos) {
								Integer cellCol = fieldColMap.get(field.getId());
								if(cellCol == null){
									continue;
								}
								Cell fieldCell = questionRow.createCell(cellCol);
								fieldCell.setCellValue(getCellValue(field,ques.getScore()));
								fieldCell.setCellStyle(styles.get(FIELD_VALUE));
							}
						}
						//给其他没有值得field设置样式
						for(int fieldIndex = quesCol + colSpan - 1;fieldIndex < col;fieldIndex++){
							Cell curCell = questionRow.getCell(fieldIndex);
							if(curCell == null){
								Cell fieldCell = questionRow.createCell(fieldIndex);
								fieldCell.setCellStyle(styles.get(FIELD_VALUE));
							}
						}
						
						//添加取证信息
						String evidence = testDetailInfo.getEvidence();
						if(StringUtils.isNotEmpty(evidence)){
							String[] evidences = evidence.split(",");
							if(!ArrayUtils.isEmpty(evidences)){
								int deviceCol = col;
								for (String ed : evidences) {
									if(StringUtils.isNotEmpty(ed)){

										Cell edCell = questionRow.createCell(col);
										edCell.setCellValue(ed);
										edCell.setCellStyle(styles.get(HTTP_HREF));
										
										Hyperlink link = creationHelper.createHyperlink(Hyperlink.LINK_URL);  
										link.setAddress(fullServerPath+ed);
										edCell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) link); 

                                   /* try {
                                        String questionPath = ConfigUtil.get("questionPicPath");
                                        int endIdx=ed.lastIndexOf("/");
                                        String picName=ed.substring(endIdx+1);

                                        InputStream my_banner_image = new FileInputStream(questionPath+picName);
                                        *//* Convert Image to byte array *//*
                                        byte[] bytes = IOUtils.toByteArray(my_banner_image);
                                         *//* Add Picture to workbook and get a index for the picture *//*

                                        int my_picture_id = sheet.getWorkbook().addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
                                          *//* Close Input Stream *//*
                                        my_banner_image.close();
                                       *//* Create the drawing container *//*
                                        Drawing drawing = sheet.createDrawingPatriarch();
                                          *//* Create an anchor point *//*
                                        ClientAnchor my_anchor = new XSSFClientAnchor();
                                          *//* Define top left corner, and we can resize picture suitable from there *//*
                                        my_anchor.setCol1(col);
                                        my_anchor.setRow1(firstRow-1);
                                       *//* Invoke createPicture and pass the anchor point and ID *//*
                                        Picture my_picture = drawing.createPicture(my_anchor, my_picture_id);
                                          *//* Call resize method, which resizes the image *//*
                                        my_picture.resize();
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }*/
                                        ///////////////////////////////////////////////
										Cell deviceHeadCel = moduleHead.createCell(col);
										deviceHeadCel.setCellValue(ENVICE_NAME + (col - deviceCol + 1));
										deviceHeadCel.setCellStyle(styles.get(MODULER_NAME));
										
										col += 1;
									}
								}
							}
						}
						
						
					}
				}
				 
			}
		}
		
		return firstRow;
	}

	protected Set<Field> doWrapModuleFields(Module module, Set<Field> fields) {
		return fields;
	}
	
	/**
	 * 添加额外的头信息
	 * @param examDetailInfo
	 * @param header
	 * @param cellIndex
	 * @param sheet 
	 * @param styles 
	 */
	protected void addExtraHeads(ExamDetailInfoVO examDetailInfo, Row header,
			int cellIndex, Sheet sheet, Map<String, CellStyle> styles)throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 生成附加项信息
	 * @param examDetailInfo
	 * @param sheet
	 * @param styles
	 * @param firstRow
	 */
	protected void buildAdditionalModulerInfo(ExamDetailInfoVO examDetailInfo,
			Sheet sheet, Map<String, CellStyle> styles,Integer firstRow) {
		List<AdditionalModule> additionalModule = examDetailInfo.getAdditionalModules();
		
		if(!CollectionUtils.isEmpty(additionalModule)){
			firstRow++;
			for (AdditionalModule module : additionalModule) {
				module = doWrapModule(module);
				//显示模块描述
				if(StringUtils.isEmpty(module.getDesc())){
					Row moduleDesc = sheet.createRow(firstRow++);
					Cell titleCell = moduleDesc.createCell(0);
					titleCell.setCellValue(module.getDesc());
					titleCell.setCellStyle(styles.get(MODULER_DESC));
					int regionRow = moduleDesc.getRowNum() + 1;
					String region = "$A$"+ regionRow +":$L$"+ regionRow;
					sheet.addMergedRegion(CellRangeAddress.valueOf(region));
				}
				//显示模块头
				Row moduleHead = sheet.createRow(firstRow++);
				Cell moduleNameCel = moduleHead.createCell(0);
				moduleNameCel.setCellValue(module.getName());
				moduleNameCel.setCellStyle(styles.get(MODULER_ADDITIONAL_NAME));
				int regionRow = moduleHead.getRowNum() + 1;
				String lastCol = "行动计划".equals(module.getName()) ? "N" : "L";
				String region = "$A$"+ regionRow +":$"+lastCol+"$"+ regionRow;
				sheet.addMergedRegion(CellRangeAddress.valueOf(region));
				
				int col = 0;
				Map<String, CellInfo> fieldColMap = new HashMap<String, CellInfo>();
				Set<Field> fields = module.getFields();
				if(!CollectionUtils.isEmpty(fields)){
					List<Field> sortFields = sortField(fields);
					Row additionalModuleHead = sheet.createRow(firstRow++);
					for (Field field : sortFields) {
						Cell fieldCell = additionalModuleHead.createCell(col);
						String fieldName = field.getFieldName();
						
						fieldCell.setCellValue(fieldName);
						fieldCell.setCellStyle(styles.get(MODULER_HEAD));
						CellInfo cellInfo = getAdditionalFieldMap().get(fieldName);
						cellInfo.setColNum(fieldCell.getColumnIndex());
						sheet.addMergedRegion(new CellRangeAddress(additionalModuleHead.getRowNum(), additionalModuleHead.getRowNum(), cellInfo.getColNum(), cellInfo.getColNum() + cellInfo.getColSpan() - 1));
						
						fieldColMap.put(cellInfo.getFieldName(), cellInfo);
						col += cellInfo.getColSpan();
					}
				}
				
				//显示附加项信息
				List<Map<String, Object>> testResult = module.getTestResult();
				if(CollectionUtils.isEmpty(testResult)){
					testResult = new ArrayList<Map<String,Object>>();
					//如果附加项为空的话，默认给附加项5个空值
					for(int num = 0;num < 5;num++){
						Map<String, Object> emptyValMap = new HashMap<String, Object>();
						for (Entry<String, CellInfo> entry : fieldColMap.entrySet()) {
							emptyValMap.put(entry.getKey(), "");
						}
						
						testResult.add(emptyValMap);
					}
				}
				for (int i = 0;i < testResult.size();i++) {
					Map<String, Object> map = testResult.get(i);
					if(!CollectionUtils.isEmpty(map)){
						Row resultRow = sheet.createRow(firstRow++);
						for (Entry<String, Object> entry : map.entrySet()) {
							CellInfo cellInfo = fieldColMap.get(entry.getKey());
							if(cellInfo == null){
								continue;
							}
							Cell fieldCell = resultRow.createCell(cellInfo.getColNum());
							fieldCell.setCellValue(getAdditionalCellValue(entry.getValue(),cellInfo));
							fieldCell.setCellStyle(styles.get(HEAD_VALUE));
							
							sheet.addMergedRegion(new CellRangeAddress(resultRow.getRowNum(), resultRow.getRowNum(), cellInfo.getColNum(), cellInfo.getColNum() + cellInfo.getColSpan() - 1));
							for(int tempIndex = cellInfo.getColNum() + 1;tempIndex < cellInfo.getColNum() + cellInfo.getColSpan();tempIndex++ ){
								Cell tempCell = resultRow.createCell(tempIndex);
								tempCell.setCellStyle(styles.get(HEAD_VALUE));
							}
						}
					}
					
				}
			}
		}
	}

	private Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();

		short borderColor = IndexedColors.GREY_50_PERCENT.getIndex();

        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)18);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(titleFont);
        styles.put(TITLE, style);
        
        Font modulerDescFont = wb.createFont();
        modulerDescFont.setFontHeightInPoints((short)12);
        modulerDescFont.setColor(IndexedColors.BLACK.getIndex());
        modulerDescFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//        style.setFillForegroundColor(IndexedColors.BLACK.getIndex());
//        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(modulerDescFont);
        styles.put(MODULER_DESC, style);
        
        Font modulerHeadFont = wb.createFont();
        modulerHeadFont.setFontHeightInPoints((short)12);
        modulerHeadFont.setColor(IndexedColors.WHITE.getIndex());
        modulerHeadFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(modulerHeadFont);
        styles.put(MODULER_HEAD, style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(modulerHeadFont);
        styles.put(MODULER_NAME, style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(modulerHeadFont);
        styles.put(MODULER_ADDITIONAL_NAME, style);
        
        Font quesFont = wb.createFont();
        quesFont.setFontHeightInPoints((short)14);
        quesFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        setBorder(style);
        style.setFont(quesFont);
        styles.put(QUESTION_NUM, style);
        
        Font quesDesc = wb.createFont();
        quesFont.setFontHeightInPoints((short)14);
//        quesFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        setBorder(style);
        style.setFont(quesDesc);
        style.setWrapText(true);
        styles.put(QUESTION_DESC, style);
        
        Font headFont = wb.createFont();
        quesFont.setFontHeightInPoints((short)14);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        setBorder(style);
        style.setFont(headFont);
        styles.put(HEAD_LABEL, style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
//        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        setBorder(style);
        style.setFont(headFont);
        styles.put(HEAD_VALUE, style);
        
       //超链接
        Font httpFont = wb.createFont();
        style = wb.createCellStyle();
        style.setWrapText(true);
        setBorder(style);
        httpFont.setColor(IndexedColors.BLUE.getIndex());
        httpFont.setUnderline(Font.U_SINGLE);
        style.setFont(httpFont);
        styles.put(HTTP_HREF, style);
        
        style = wb.createCellStyle();
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.index);
        styles.put(CELL_MIDDLE, style);
        
        style = wb.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.index);
        styles.put(CELL_MIDDLE, style);
        
        style = wb.createCellStyle();
        setBorder(style);
        style.setWrapText(true);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        styles.put(FIELD_VALUE, style);
        
        return styles;
	}
	
	private void setBorder(CellStyle style) {
		style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
//	        style.setLeftBorderColor(borderColor);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.index);
	}

	private List<Field> sortField(Set<Field> fields) {
		List<Field> sortFields = new ArrayList<ExamDetailInfoVO.Field>(fields);
		Collections.sort(sortFields,new Comparator<Field>() {
			@Override
			public int compare(Field field1, Field field2) {
				return getScore(field1) - getScore(field2);
			}

			private int getScore(Field field) {
				Map<String, Integer> fieldWeight = getFieldWeight();
				return fieldWeight.get(field.getFieldName()) == null ? 0 : fieldWeight.get(field.getFieldName());
			}
		});
		return sortFields;
	}
	
	private String getHeadValue(Field field) {
		String value = field.getFieldValue();
		if(field.getFieldType() == 4){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			date.setTime(Long.valueOf(value));
			value = sdf.format(date);
		}
		return (null == value || "null".equals(value)) ? "" : value;
	}
	
	
	/**
	 * |获取附加项的值信息
	 * @param value
	 * @param cellInfo
	 * @return
	 */
	protected String getAdditionalCellValue(Object value, CellInfo cellInfo) {
		return String.valueOf(value);
	}

	/**
	 * 附加项的字段信息
	 * @return
	 */
	protected Map<String, CellInfo> getAdditionalFieldMap() {
		return null;
	}
	
	/**
	 * 给字段分配权重
	 * @return
	 */
	protected Map<String, Integer> getFieldWeight() {
		return null;
	}
	
	protected List<Field> doWrapFieldInfo(List<Field> fieldInfos, Question ques, Module module) {
		return fieldInfos;
	}
	
	protected AdditionalModule doWrapModule(AdditionalModule module) {
		return module;
	}

	protected String getCellValue(Field field, int score) {
		return null;
	}

	
	protected boolean contains(Set<Field> results, String fieldName) {
		if(!CollectionUtils.isEmpty(results)){
			for (Field field : results) {
				if(fieldName.equals(field.getFieldName())){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 单元格信息
	 * 描述单元格的位置与占几列
	 * @author QunZheng
	 *
	 */
	public static class CellInfo{
		/**
		 * 
		 */
		private int colNum;
		private int colSpan;
		private String fieldName;
		
		public CellInfo() {
			super();
		}
		
		public CellInfo(int colSpan) {
			super();
			this.colSpan = colSpan;
		}

		public CellInfo(int colSpan, String fieldName) {
			super();
			this.colSpan = colSpan;
			this.fieldName = fieldName;
		}



		public CellInfo(int colNum, int colSpan,String fieldName) {
			super();
			this.colNum = colNum;
			this.colSpan = colSpan;
			this.fieldName = fieldName;
		}


		public int getColNum() {
			return colNum;
		}
		public void setColNum(int colNum) {
			this.colNum = colNum;
		}
		public int getColSpan() {
			return colSpan;
		}
		public void setColSpan(int colSpan) {
			this.colSpan = colSpan;
		}

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
		
		
	}
	
	public static class KeyValue{
		private String key;
		private String value;
		
		
		public KeyValue(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}
		public KeyValue() {
			super();
			// TODO Auto-generated constructor stub
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		
	}
	
}
