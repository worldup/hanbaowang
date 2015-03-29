package com.upbest.mvc.report;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
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

/**
 * 现金稽核 生成excel
 * @author QunZheng
 *
 */
public class CashAuditExamTestReport extends ExamTestReport {
	
	public static final String FIELD_SCORE = "分数";
	public static final String FIELD_Y = "Y";
	public static final String FIELD_N = "N";
	public static final String FIELD_GETSCORE = "得分";
	public static final String FIELD_DESC = "描述问题";
	
	public static final String KEY_ITEM = "关键项评估";
	public static final String SAFE_MANAGER = "保险箱管理";
	public static final String TABLE_ITEM = "转桌管理";
	public static final String STOCK_ITEM = "存货管理";
	
	//关键项评估 字段
	public static final String KEY_ITEM_FIELD_Y = KEY_ITEM  + "-" + FIELD_Y;
	public static final String KEY_ITEM_FIELD_N = KEY_ITEM  + "-" + FIELD_N;
	public static final String KEY_ITEM_FIELD_DESC = KEY_ITEM  + "-" + FIELD_DESC;
	
	//保险箱管理
	public static final String SAFE_MANAGER_FIELD_Y = SAFE_MANAGER  + "-" + FIELD_Y;
	public static final String SAFE_MANAGER_FIELD_N = SAFE_MANAGER  + "-" + FIELD_N;
	public static final String SAFE_MANAGER_FIELD_DESC = SAFE_MANAGER  + "-" + FIELD_DESC;
	public static final String SAFE_MANAGER_FIELD_SCORE = SAFE_MANAGER  + "-" + FIELD_SCORE;
	public static final String SAFE_MANAGER_FIELD_GETSCORE = SAFE_MANAGER  + "-" + FIELD_GETSCORE;
	
	//转桌管理
	public static final String TABLE_ITEM_FIELD_Y = TABLE_ITEM  + "-" + FIELD_Y;
	public static final String TABLE_ITEM_FIELD_N = TABLE_ITEM  + "-" + FIELD_N;
	public static final String TABLE_ITEM_FIELD_DESC = TABLE_ITEM  + "-" + FIELD_DESC;
	public static final String TABLE_ITEM_FIELD_SCORE = TABLE_ITEM  + "-" + FIELD_SCORE;
	public static final String TABLE_ITEM_FIELD_GETSCORE = TABLE_ITEM  + "-" + FIELD_GETSCORE;
	
	//存货管理
	public static final String STOCK_ITEM_FIELD_Y = STOCK_ITEM  + "-" + FIELD_Y;
	public static final String STOCK_ITEM_FIELD_N = STOCK_ITEM  + "-" + FIELD_N;
	public static final String STOCK_ITEM_FIELD_DESC = STOCK_ITEM  + "-" + FIELD_DESC;
	public static final String STOCK_ITEM_FIELD_SCORE = STOCK_ITEM  + "-" + FIELD_SCORE;
	public static final String STOCK_ITEM_FIELD_GETSCORE = STOCK_ITEM  + "-" + FIELD_GETSCORE;
	
	
	public static final String FIELD_RESOURCE = "根源";
	public static final String FIELD_RESOLVE = "行动计划";
	public static final String FIELD_RESOLVE1 = "行动方案";
	public static final String FIELD_SCORENUM = "对应失分项编号";
	
	public static final String FIELD_TACK = "行动步骤";
	public static final String FIELD_BEGINTIME = "开始日期";
	public static final String FIELD_EXPENDTIME = "预计结束日期";
	public static final String FIELD_REALENDTIME = "实际结束日期";
	public static final String FIELD_USERNAME = "负责人";
	public static final String FIELD_EXPRESULT = "预期达成结果";
	
	public static final String HEAD_FIELD_DATE = "日期";
	public static final String HEAD_FIELD_SHOP_MANAGER = "餐厅经理";
	public static final String HEAD_FIELD_JIHEREN = "稽核人";
	public static final String HEAD_FIELD_SHOP = "餐厅";
	public static final String HEAD_FIELD_LAST_JIHE_SCORE = "上次稽核成绩";
	public static final String HEAD_FIELD_JIHE_SCORE = "本次稽核成绩";
	public static final String HEAD_FIELD_CUR_MANAGER = "值班经理";
	public static final String HEAD_FIELD_REPEAT_LOST_ITEM = "重复扣分项编号";
	
	public static final String SHOP_EXECUTE = "餐厅执行情况";
	public static final String JIHE_DATE = "稽核日期";
	
	public static final int RED = 0;
	public static final int GREEN = 1;
	public static final int YELLOW = 2;
	
	public static final String STATISTIC_LABEL = "statistic_label";
	public static final String STATISTIC_VAL = "statistic_val";
	public static final String STATISTIC_STANDARD_LABEL = "statistic_standard_label";
	public static final String STATISTIC_STANDARD_VAL = "statistic_standard_val";
	
	public static final String HEAD_VALUE_SUBFFIC = "_value";
	
	
	/**
	 * 取证图片的id
	 */
	public static final int ENVICE_ID = 10051;
	
	
	public CashAuditExamTestReport(IExamService service) {
		super(service);
	}
	/**
	 * 字段的权重
	 */
	public static final Map<String, Integer> FIELD_WEIGHT = buildFieldScore();
	public static final Map<String, CellInfo> ADDITIONAL_FIELD_MAP = buildAdditionalFieldMap();
	
	
	//=================================================
	/**
	 * 字段的权重
	 */
	public byte[] generateReport(String fullServerPath, int testPaperId) throws Exception{
		ExamDetailInfoVO examDetailInfo = examService.findAfterTestExamDetaiInfo(testPaperId);
		return generateExcel( fullServerPath,examDetailInfo);
	}

	/**
	 * 生成excel
	 * @throws Exception 
	 */
	protected byte[] generateExcel(String fullServerPath,ExamDetailInfoVO examDetailInfo) throws Exception {
		 Map<String, CellStyle> styles = createStyles(wb);
		 
		 String examNam = examDetailInfo.getBasicInfo().getExamName();
		 Sheet sheet = wb.createSheet(examDetailInfo.getBasicInfo().getExamName());
		 
		 Map<String, CellInfo> fieldCellInfoMap = buildFieldCellInfo();
		 //生成问卷信息之前对excel做一些处理(eg：设置列宽)
		 setExamInfoSheetStyle(sheet);
		//turn off gridlines
        /* sheet.setDisplayGridlines(false);
         sheet.setPrintGridlines(false);
         sheet.setFitToPage(true);
         sheet.setHorizontallyCenter(true);
         PrintSetup printSetup = sheet.getPrintSetup();
         printSetup.setLandscape(true);*/
		 
		 Integer firstRow = 0;
		 int lastRow = buildTitleInfo(sheet,styles,examNam,firstRow,fieldCellInfoMap);
//		 lastRow = setSpace(sheet,lastRow);
		 lastRow = buildHeadInfo(examDetailInfo,sheet,styles,lastRow,fieldCellInfoMap);
		 lastRow = setSpace(sheet,lastRow);
		 lastRow = buildModulerInfo(fullServerPath,examDetailInfo,sheet,styles,lastRow,wb.getCreationHelper(),fieldCellInfoMap);
		 buildAdditionalModulerInfo(examDetailInfo,sheet,styles,lastRow,fieldCellInfoMap);
		 
		 //如果问卷需要统计信息，则再创建个sheet
		 buildExamStatisticSheet(examDetailInfo,styles);
		 
		 ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		 FileOutputStream outputStream = new FileOutputStream("c:/poi/side_by_side.xlsx");
		 wb.write(outputStream);
		 
		 return outputStream.toByteArray();
	}

	/**
	 * 
	 * @param sheet
	 * @param lastRow
	 */
	private int setSpace(Sheet sheet, int lastRow) {
		// TODO Auto-generated method stub
		Row row = sheet.createRow(lastRow);
		row.setHeightInPoints(10);
		return lastRow + 1;
	}

	/**
	 * 
	 * @param sheet
	 */
	protected void setExamInfoSheetStyle(Sheet sheet) {
		for(int i = 0;i < 18;i++){
			sheet.setColumnWidth(i, 256*8);
		}
		int width = 256*5;
		sheet.setColumnWidth(0, 256*3);
		sheet.setColumnWidth(10, width);
		sheet.setColumnWidth(11, width);
		sheet.setColumnWidth(12, width);
		sheet.setColumnWidth(13, width);
		sheet.setColumnWidth(14, 25 * 256);
		sheet.setColumnWidth(15, 16 * 256);
		sheet.setColumnWidth(16, 10 * 256);
		sheet.setColumnWidth(17, 10 * 256);
	}

	private void buildExamStatisticSheet(ExamDetailInfoVO examDetailInfo, Map<String, CellStyle> styles) {
		Sheet sheet = wb.createSheet();
		//生成问卷统计信息之前对excel做一些处理(eg：设置列宽)
		setExamStatisticSheetStyle(sheet);
		
		String[] basicFields = new String[]{
				HEAD_FIELD_SHOP,HEAD_FIELD_DATE,HEAD_FIELD_JIHEREN,SHOP_EXECUTE
		};
		
		String[] moduleStatisticFields = new String[]{
				KEY_ITEM,SAFE_MANAGER,TABLE_ITEM,STOCK_ITEM
		};
		
		List<KeyValue> basic = getBasicFieldsVal(basicFields,examDetailInfo);
		List<KeyValue> module = getModuleStatisticFieldsVal(moduleStatisticFields,examDetailInfo);
		
		//
		showStatisticInfo(sheet,basic,module,styles);
	}

	private void showStatisticInfo(Sheet sheet, List<KeyValue> basic,
			List<KeyValue> module, Map<String, CellStyle> styles) {
		int startRow = 1;
		int startCol = 1;
		
		List<KeyValue> result = new ArrayList<ExamTestReport.KeyValue>();
		result.addAll(basic);
		result.addAll(module);
		
		
		Map<Integer, URL> resources = null;
		try {
			resources = getImageResource();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int nextRow = startRow;
		for (KeyValue keyValue : result) {
			int nextCol = startCol;
			
			Row row = sheet.createRow(nextRow);
			
			if(HEAD_FIELD_DATE.equals(keyValue.getKey())){
				keyValue.setKey("稽核日期");
				keyValue.setValue(parseDate(Long.valueOf(keyValue.getValue())));
			}
			
			Cell lableCell = row.createCell(nextCol++);
			lableCell.setCellValue(keyValue.getKey());
			lableCell.setCellStyle(styles.get(STATISTIC_LABEL));
			
			Cell valCell = row.createCell(nextCol++);
			
			if(SHOP_EXECUTE.equals(keyValue.getKey()) && resources != null){
				row.setHeightInPoints(20);
				Integer intKey = Integer.parseInt(keyValue.getValue());
				if(resources.get(intKey) != null){
					 try {
						new AddDimensionedImage().addImageToSheet(valCell.getColumnIndex(),row.getRowNum(), sheet, sheet.createDrawingPatriarch(),
								 resources.get(intKey), 10, 10,
								 AddDimensionedImage.EXPAND_ROW_AND_COLUMN);
					} catch (Exception e) {
						e.printStackTrace();
					}
				 }
			}else{
				valCell.setCellValue(keyValue.getValue());
				if(KEY_ITEM.equals(keyValue.getKey())){
					if(!StringUtils.isEmpty(keyValue.getValue())){
						row.setHeightInPoints(20);
					}
				}
			}
			valCell.setCellStyle(styles.get(STATISTIC_VAL));
			
			nextRow++;
		}
		
		showStatisticStandard(sheet,nextRow,styles,resources);
		
	}

	/**
	 * 显示统计标准
	 * @param sheet
	 * @param nextRow
	 * @param styles 
	 * @param resources 
	 */
	private void showStatisticStandard(Sheet sheet, int nextRow, Map<String, CellStyle> styles, Map<Integer, URL> resources) {
		nextRow++;
		
		List<KeyValue> list = new ArrayList<ExamTestReport.KeyValue>();
		list.add(new KeyValue(RED + "", "关键项评估有扣分，或者≥15个项目以上扣分"));
		list.add(new KeyValue(YELLOW + "", "6到15个项目扣分"));
		list.add(new KeyValue(GREEN + "", "≤5个项目扣分"));
		
		int startCol = 1;
		
		for (KeyValue keyValue : list) {
			int nextCol = startCol;
			
			Row row = sheet.createRow(nextRow);
			
			Cell lableCell = row.createCell(nextCol++);
			lableCell.setCellStyle(styles.get(STATISTIC_STANDARD_LABEL));
			
			if(resources != null){
				row.setHeightInPoints(20);
				Integer intKey = Integer.parseInt(keyValue.getKey());
				if(resources.get(intKey) != null){
					 try {
						new AddDimensionedImage().addImageToSheet(lableCell.getColumnIndex(),row.getRowNum(), sheet, sheet.createDrawingPatriarch(),
								 resources.get(intKey), 10, 10,
								 AddDimensionedImage.EXPAND_ROW_AND_COLUMN);
					} catch (Exception e) {
						e.printStackTrace();
					}
				 }
			}else{
				lableCell.setCellValue(keyValue.getKey());
			}
			
			Cell valCell = row.createCell(nextCol++);
			valCell.setCellStyle(styles.get(STATISTIC_STANDARD_VAL));
			valCell.setCellValue(keyValue.getValue());
			
			nextRow++;
		}
	}

	private String parseDate(Long l) {
		String result = null;
		
		Date date = new Date();
		date.setTime(l);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		result = sdf.format(date);
		return result;
	}

	private List<KeyValue> getModuleStatisticFieldsVal(
			String[] moduleStatisticFields, ExamDetailInfoVO examDetailInfo) {
		List<KeyValue> list = new ArrayList<ExamTestReport.KeyValue>();
		
		List<Module> moduleList = examDetailInfo.getModules();
		Map<String, Module> moduleMap = new HashMap<String, ExamDetailInfoVO.Module>();
		if(!CollectionUtils.isEmpty(moduleList)){
			for (Module module : moduleList) {
				moduleMap.put(module.getName(), module);
			}
		}
		
		for (String field : moduleStatisticFields) {
			KeyValue keyVal = new KeyValue();
			keyVal.setKey(field);
			list.add(keyVal);
			
			Module module = moduleMap.get(field);
			//关键项评估
			if(KEY_ITEM.equals(field)){
				keyVal.setValue(getKeyItem(module));
				continue;
			}
			
			int count = module == null ? 0 : getModuleWithNFieldCount(module);
			
			keyVal.setValue("有" + count + "个机会点");
			
		}
		return list;
	}

	/**
	 * 显示问题编号、问题标题、问题描述内容
	 * @param module
	 * @return
	 */
	private String getKeyItem(Module module) {
		if(module == null){
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		List<Question> questions = module.getQuestions();
		if(!CollectionUtils.isEmpty(questions)){
			for (Question question : questions) {
				sb.append(question.getSerialNumber())
					.append(".");
				String quesDesc = question.getQuesDesc();
				sb.append(quesDesc.split("\r\n")[0]).append(":");
				
				QuestionTestDetailInfo testDetailInfo = question.getTestDetailInfo();
				sb.append(getDescField(testDetailInfo)).append("\r\n");
			}
		}
		return sb.toString();
	}

	/**
	 * 获取问题描述字段的值
	 * @param testDetailInfo
	 * @return
	 */
	private String getDescField(QuestionTestDetailInfo testDetailInfo) {
		if(testDetailInfo == null){
			return "";
		}
		
		List<Field> fields = testDetailInfo.getFieldInfos();
		if(!CollectionUtils.isEmpty(fields)){
			for (Field field : fields) {
				if(FIELD_DESC.equals(field.getFieldName())){
					return field.getFieldValue() == null ? "" : field.getFieldValue();
				}
			}
		}
		return "";
	}

	private List<KeyValue> getBasicFieldsVal(String[] basicFields,
			ExamDetailInfoVO examDetailInfo) {
		List<KeyValue> keyValList = new ArrayList<ExamTestReport.KeyValue>();
		
		List<Field> heads = examDetailInfo.getHeads();
		Map<String, Field> headMap = new HashMap<String, ExamDetailInfoVO.Field>();
		for (Field field : heads) {
			headMap.put(field.getFieldName(), field);
		}
		for (String bfield : basicFields) {
			KeyValue keyVal = new KeyValue();
			keyVal.setKey(bfield);
			keyValList.add(keyVal);
			
			//餐厅执行情况字段
			if(bfield.equals(SHOP_EXECUTE)){
				keyVal.setValue(examDetailInfo.getBasicInfo().getLevel() + "");
				continue;
			}
			
			Field head = headMap.get(bfield);
			if(head != null){
				String fieldVal = head.getFieldValue();
				keyVal.setValue(fieldVal == null ? "" : fieldVal);
			}
			
		}
		return keyValList;
	}
	
	private int getModuleWithNFieldCount(Module module) {
		int count = 0;
		List<Question> questions = module.getQuestions();
		if(!CollectionUtils.isEmpty(questions)){
			for (Question question : questions) {
				QuestionTestDetailInfo testDetailInfo = question.getTestDetailInfo();
				if(testDetailInfo != null){
					List<Field> fields = testDetailInfo.getFieldInfos();
					if(!CollectionUtils.isEmpty(fields)){
						for (Field field : fields) {
							if("N".equals(field.getFieldName())){
								String fieldVal = field.getFieldValue();
								if(!StringUtils.isEmpty(fieldVal) && Integer.parseInt(fieldVal) == 1){
									count++;
								}
								break;
							}
						}
					}
				}
			}
		}
		
		return count;
	}


	private void setExamStatisticSheetStyle(Sheet sheet) {
		 sheet.setDisplayGridlines(false);
         sheet.setPrintGridlines(false);
         sheet.setFitToPage(true);
         sheet.setHorizontallyCenter(true);
         PrintSetup printSetup = sheet.getPrintSetup();
         printSetup.setLandscape(true);
         
		sheet.setColumnWidth(1, 16 * 256);
		sheet.setColumnWidth(2, 40 * 256);
	}

	private int buildTitleInfo(Sheet sheet,Map<String, CellStyle> styles, String examNam,Integer firstRow, Map<String, CellInfo> fieldCellInfoMap) {
		Row titleRow = sheet.createRow(firstRow);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(examNam);
		titleCell.setCellStyle(styles.get(TITLE));
//		String region = "A"+ (firstRow+1) +":L"+ (firstRow+1);
		sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), 0, 17));
		/*try {
			new AddDimensionedImage().addImageToSheet("L" + (firstRow+1), sheet, sheet.createDrawingPatriarch(),
					 new ClassPathResource(LOGO_URI).getURL(), 10, 10,
					 AddDimensionedImage.EXPAND_ROW_AND_COLUMN);
		} catch (Exception e) {
			e.printStackTrace();
			//TODO logger
		}*/
		return firstRow + 1;
	}

	/**
	 * 生成头信息
	 * @param examDetailInfo
	 * @param sheet
	 * @param styles
	 * @param firstRow
	 * @param fieldCellInfoMap 
	 */
	protected int buildHeadInfo(ExamDetailInfoVO examDetailInfo, Sheet sheet, Map<String, CellStyle> styles,Integer firstRow, Map<String, CellInfo> fieldCellInfoMap)throws Exception {
		//生成头信息
		 List<Field> heads = examDetailInfo.getHeads();
		 if(!CollectionUtils.isEmpty(heads)){
			 sortField(heads);
			 
			 int headCount = 3;
			 Row header = sheet.createRow(firstRow++);
			 int cellIndex = 0;
			 for(int i = 0;i < heads.size();i++){
				 Field field = heads.get(i);
				 
				 if(i != 0 && (i % headCount) == 0){
					 header = sheet.createRow(firstRow++);
					 cellIndex = 0;
				 }
				 String fieldName = field.getFieldName();
				 CellInfo cellInfo = fieldCellInfoMap.get(fieldName);
				 CellInfo cellValInfo = fieldCellInfoMap.get(fieldName + HEAD_VALUE_SUBFFIC);
				 //TODO 条件判断待删除
				 if(cellInfo == null || cellValInfo == null){
					 System.out.println("没有为Head(" + fieldName + ")设置cellinfo信息");
					 throw new Exception("没有为Head(" + fieldName + ")设置cellinfo信息");
				 }
				 Cell head_label = header.createCell(cellIndex);
				 Cell head_value = header.createCell(cellIndex + cellInfo.getColSpan());
				 
				 head_label.setCellValue(fieldName);
				 head_label.setCellStyle(styles.get(HEAD_LABEL));
				 sheet.addMergedRegion(new CellRangeAddress(header.getRowNum(), header.getRowNum(), head_label.getColumnIndex(), head_label.getColumnIndex() + cellInfo.getColSpan() - 1));
				 
				 head_value.setCellValue(getHeadValue(field));
				 head_value.setCellStyle(styles.get(HEAD_VALUE));
				 sheet.addMergedRegion(new CellRangeAddress(header.getRowNum(), header.getRowNum(), head_value.getColumnIndex(), head_value.getColumnIndex() + cellValInfo.getColSpan() - 1));
				 
				 //补充边框
				 for(int k = head_label.getColumnIndex() + 1;k < head_label.getColumnIndex() + cellInfo.getColSpan();k++){
					 Cell temp = header.createCell(k);
					 temp.setCellStyle(styles.get(HEAD_VALUE));
				 }
				 for(int m = head_value.getColumnIndex() + 1;m < head_value.getColumnIndex() + cellValInfo.getColSpan();m++){
					 Cell temp = header.createCell(m);
					 temp.setCellStyle(styles.get(HEAD_VALUE));
				 }
				 
				 cellIndex += cellInfo.getColSpan() + cellValInfo.getColSpan();
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
	 * @param fieldCellInfoMap 
	 */
	protected int buildModulerInfo(String fullServerPath,ExamDetailInfoVO examDetailInfo, Sheet sheet,
			Map<String, CellStyle> styles,Integer firstRow, CreationHelper creationHelper, Map<String, CellInfo> fieldCellInfoMap) {
		Integer nextRow = firstRow;
				
		List<Module> modules = examDetailInfo.getModules();
		if(!CollectionUtils.isEmpty(modules)){
			for (Module module : modules) {
				//显示模块描述
				if(!StringUtils.isEmpty(module.getDesc())){
					Row moduleDesc = sheet.createRow(nextRow++);
					Cell titleCell = moduleDesc.createCell(0);
					titleCell.setCellValue(module.getDesc());
					titleCell.setCellStyle(styles.get(MODULER_DESC));
					int regionRow = moduleDesc.getRowNum() + 1;
					String region = "$A$"+ regionRow +":$N$"+ regionRow;
					sheet.addMergedRegion(CellRangeAddress.valueOf(region));
				}
				//显示模块头
				int col = 0,colSpan = 9;
				String moduleName = module.getName();
				Row moduleHead = sheet.createRow(nextRow++);
				Cell moduleNameCel = moduleHead.createCell(col);
				moduleNameCel.setCellValue(moduleName);
				moduleNameCel.setCellStyle(styles.get(MODULER_NAME));
				sheet.addMergedRegion(new CellRangeAddress(moduleHead.getRowNum(), moduleHead.getRowNum(), 0, colSpan));
				
				col = colSpan + 1;
				Map<Integer, CellInfo> fieldIdCellInfoMap = new HashMap<Integer, CellInfo>();
				Set<Field> fields = module.getFields();
				fields = doWrapModuleFields(module,fields);
				if(!CollectionUtils.isEmpty(fields)){
					List<Field> sortFields = sortField(fields);
					for (Field field : sortFields) {
						String fieldKey = moduleName + "-" + field.getFieldName();
						CellInfo cellInfo = fieldCellInfoMap.get(fieldKey);
						
						Cell fieldCell = moduleHead.createCell(col);
						fieldCell.setCellValue(field.getFieldName());
						fieldCell.setCellStyle(styles.get(MODULER_HEAD));
						sheet.addMergedRegion(new CellRangeAddress(moduleHead.getRowNum(), moduleHead.getRowNum(), 
								fieldCell.getColumnIndex(), fieldCell.getColumnIndex() + cellInfo.getColSpan() - 1));
						
						fieldIdCellInfoMap.put(field.getId(), 
								new CellInfo(col, cellInfo.getColSpan(), field.getFieldName()));
						
						col += cellInfo.getColSpan();
					}
					
					//为模块头添加图片描述字段
					Cell enviceHeadCel = moduleHead.createCell(col);
					enviceHeadCel.setCellValue(ENVICE_NAME);
					enviceHeadCel.setCellStyle(styles.get(MODULER_NAME));
					sheet.addMergedRegion(new CellRangeAddress(moduleHead.getRowNum(), moduleHead.getRowNum(), 
							enviceHeadCel.getColumnIndex(), enviceHeadCel.getColumnIndex() + 1));
					
					fieldCellInfoMap.put(moduleName + "-" + ENVICE_NAME, new CellInfo(2));
				}
				
				//显示模块下的问题描述
				List<Question> questions = module.getQuestions();
				if(!CollectionUtils.isEmpty(questions)){
					for (int i = 0;i < questions.size();i++) {
						int quesCol = 0;
						
						Question ques = questions.get(i);
						Row questionRow = sheet.createRow(nextRow);
						
						QuestionTestDetailInfo testDetailInfo = ques.getTestDetailInfo();
						//添加取证信息
						String evidence = testDetailInfo.getEvidence();
						String[] evidences = null;
						
						Map<Integer, Row> rowMap = new HashMap<Integer, Row>();
						rowMap.put(questionRow.getRowNum(), questionRow);
						
						if(StringUtils.isNotEmpty(evidence)){
							evidences = evidence.split(",");
							if(!ArrayUtils.isEmpty(evidences)){
								int deviceCol = col;
								Row curRow = questionRow;
                                int imageIdx=0;
								for (String ed : evidences) {
									if(StringUtils.isNotEmpty(ed)){
										Cell edCell = curRow.createCell(col);
										//edCell.setCellValue(ed);
										edCell.setCellValue("图片"+(++imageIdx));
										edCell.setCellStyle(styles.get(HTTP_HREF));
										
										Hyperlink link = creationHelper.createHyperlink(Hyperlink.LINK_URL);  
										link.setAddress(fullServerPath+ed);
										edCell.setHyperlink((org.apache.poi.ss.usermodel.Hyperlink) link); 
										
										int startCol = edCell.getColumnIndex();
										int endCol = edCell.getColumnIndex() + 1;
										sheet.addMergedRegion(new CellRangeAddress(curRow.getRowNum(), curRow.getRowNum(), 
												edCell.getColumnIndex(), edCell.getColumnIndex() + 1));
										
										//补充样式
										for(int j = startCol + 1;j <= endCol;j++){
											Cell cell = curRow.createCell(j);
											cell.setCellStyle(styles.get(HTTP_HREF));
										}
										
										curRow = sheet.createRow(curRow.getRowNum() + 1);
										rowMap.put(curRow.getRowNum(), curRow);
										
									}
								}
							}
						}
						
						//设置行高
						if(ArrayUtils.isEmpty(evidences)){
							questionRow.setHeightInPoints(100);
						}else{
							int height = 100 / evidences.length;
							for (Entry<Integer, Row> entry : rowMap.entrySet()) {
								entry.getValue().setHeightInPoints(height);
							}
						}
						
						
						//获取这个问题块 占的行数
						int rowSpan = ArrayUtils.isEmpty(evidences) ? 1 : evidences.length;
						int quesStartRow = questionRow.getRowNum(),
							quesEndRow =  questionRow.getRowNum() + rowSpan - 1;
						nextRow += rowSpan;
						//设置问题序列号
						Cell numCell = questionRow.createCell(quesCol++);
						numCell.setCellValue(ques.getSerialNumber());
						numCell.setCellStyle(styles.get(QUESTION_NUM));
						sheet.addMergedRegion(new CellRangeAddress(quesStartRow, quesEndRow, 
								numCell.getColumnIndex(), numCell.getColumnIndex()));
						
						//问题描述
						Cell quesDescCell = questionRow.createCell(quesCol);
						quesDescCell.setCellValue(ques.getQuesDesc());
						sheet.addMergedRegion(new CellRangeAddress(quesStartRow, quesEndRow, 
								quesDescCell.getColumnIndex(), colSpan));
						quesDescCell.setCellStyle(styles.get(QUESTION_DESC));
						for(int rowIndex = quesStartRow;rowIndex <= quesEndRow;rowIndex++){
							Row tempRow = (rowIndex == quesStartRow) ? questionRow : rowMap.get(rowIndex);
							if(tempRow == null){
								tempRow = sheet.createRow(rowIndex);
							}
							int startCol = (rowIndex == quesStartRow) ? quesDescCell.getColumnIndex() + 1 : quesDescCell.getColumnIndex();
							for(int j = startCol;j<=colSpan;j++){
								Cell cell = tempRow.createCell(j);
								cell.setCellStyle(styles.get(QUESTION_DESC));
							}
						}
						
						//问题块的字段
						List<Field> fieldInfos = testDetailInfo.getFieldInfos();
						fieldInfos = doWrapFieldInfo(fieldInfos,ques,module);
						if(!CollectionUtils.isEmpty(fieldInfos)){
							for (Field field : fieldInfos) {
								CellInfo cellCol = fieldIdCellInfoMap.get(field.getId());
								if(cellCol == null){
									continue;
								}
								Cell fieldCell = questionRow.createCell(cellCol.getColNum());
								fieldCell.setCellValue(getCellValue(field,ques.getScore()));
								fieldCell.setCellStyle(styles.get(FIELD_VALUE));
								
								int startCol = fieldCell.getColumnIndex();
								int endCol = fieldCell.getColumnIndex() + cellCol.getColSpan() - 1;
								sheet.addMergedRegion(new CellRangeAddress(quesStartRow, quesEndRow, 
										startCol, endCol));
								
								for(int rowIndex = quesStartRow;rowIndex <= quesEndRow;rowIndex++){
									Row tempRow = (rowIndex == quesStartRow) ? questionRow : rowMap.get(rowIndex);
									if(tempRow == null){
										tempRow = sheet.createRow(rowIndex);
									}
									int startColTemp = (rowIndex == quesStartRow) ? startCol + 1 : startCol;
									for(int j = startColTemp;j <= endCol;j++){
										Cell cell = tempRow.createCell(j);
										cell.setCellStyle(styles.get(FIELD_VALUE));
									}
								}
							}
						}
						
						//给其他没有值得field设置样式
						for(int fieldIndex = colSpan + 1;fieldIndex <= col;){
							Cell curCell = questionRow.getCell(fieldIndex);
							
							Cell headFieldCell = moduleHead.getCell(fieldIndex);
							CellInfo cellInfo = fieldCellInfoMap.get(moduleName + "-" + headFieldCell.getStringCellValue());
							
							if(curCell == null){
								Cell fieldCell = questionRow.createCell(fieldIndex);
								
								//为分数字段设置值
								if(FIELD_SCORE.equals(headFieldCell.getStringCellValue())){
									fieldCell.setCellValue(ques.getScore());
								}
								fieldCell.setCellStyle(styles.get(FIELD_VALUE));
								int startCol = fieldCell.getColumnIndex(),
									endCol = fieldCell.getColumnIndex() + cellInfo.getColSpan() - 1;
								sheet.addMergedRegion(new CellRangeAddress(quesStartRow, quesEndRow, 
										startCol, endCol));
								
								//补充样式
								for(int rowIndex = quesStartRow;rowIndex <= quesEndRow;rowIndex++){
									Row tempRow = rowMap.get(rowIndex);
									if(tempRow == null){
										tempRow = sheet.createRow(rowIndex);
									}
									for(int j = startCol;j <= endCol;j++){
										Cell cell = tempRow.getCell(j);
										if(cell == null){
											cell = tempRow.createCell(j);
										}
										cell.setCellStyle(styles.get(FIELD_VALUE));
									}
								}
							}
							
							fieldIndex += cellInfo.getColSpan();
						}
						
					}
				}
				 
			}
		}
		
		return nextRow;
	}

	
	/**
	 * 生成附加项信息
	 * @param examDetailInfo
	 * @param sheet
	 * @param styles
	 * @param firstRow
	 * @param fieldCellInfoMap 
	 */
	protected void buildAdditionalModulerInfo(ExamDetailInfoVO examDetailInfo,
			Sheet sheet, Map<String, CellStyle> styles,Integer firstRow, Map<String, CellInfo> fieldCellInfoMap) {
		List<AdditionalModule> additionalModule = examDetailInfo.getAdditionalModules();
		
		if(!CollectionUtils.isEmpty(additionalModule)){
			firstRow+=2;
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
				String region = "$A$"+ regionRow +":$R$"+ regionRow;
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
        style.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
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
        quesFont.setFontHeightInPoints((short)9);
        quesFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        quesFont.setFontName("宋体");
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
//        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        setBorder(style);
        style.setFont(quesFont);
        styles.put(QUESTION_NUM, style);
        
        Font quesDesc = wb.createFont();
//        quesDesc.setFontHeightInPoints((short)14);
//        quesFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
//        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
//        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        setBorder(style);
        style.setFont(quesDesc);
        style.setWrapText(true);
        styles.put(QUESTION_DESC, style);
        
        Font headFont = wb.createFont();
//        quesFont.setFontHeightInPoints((short)14);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
//        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
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
        
        Font statisticLabelFont = wb.createFont();
//      quesFont.setFontHeightInPoints((short)14); 
        statisticLabelFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	    style = wb.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	    setBorder(style);
	    style.setFont(statisticLabelFont);
	    styles.put(STATISTIC_LABEL, style);
	      
	    style = wb.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    style.setWrapText(true);
	    setBorder(style);
	    styles.put(STATISTIC_VAL, style);
	    
	    style = wb.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_LEFT);
	    setWeightBorder(style);
	    styles.put(STATISTIC_STANDARD_LABEL, style);
	    
	    style = wb.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_CENTER);
	    style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	    setWeightBorder(style);
	    styles.put(STATISTIC_STANDARD_VAL, style);
        
        return styles;
	}
	
	private void setWeightBorder(CellStyle style) {
		short border = CellStyle.BORDER_MEDIUM;
		style.setBorderLeft(border);
        style.setBorderTop(border);
        style.setBorderRight(border);
        style.setBorderBottom(border);
        style.setBottomBorderColor(IndexedColors.BLACK.index);
	}
	
	private void setBorder(CellStyle style) {
		style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
//	        style.setLeftBorderColor(borderColor);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.index);
	}

	private List<Field> sortField(Collection<Field> fields) {
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
        else if(5==field.getFieldType()){
            value= getShopNumByShopId(value);
        }
		return (null == value || "null".equals(value)) ? "" : value;
	}
	
	protected boolean hasExamStatistic() {
		return false;
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

	
	
	protected List<Field> doWrapFieldInfo(List<Field> fieldInfos, Question ques, Module module) {
		return fieldInfos;
	}
	
	protected AdditionalModule doWrapModule(AdditionalModule module) {
		return module;
	}

	//=================================================
	
	@Override
	protected Map<String, CellInfo> getAdditionalFieldMap() {
		return ADDITIONAL_FIELD_MAP;
	}
	
	@Override
	protected Map<String, Integer> getFieldWeight() {
		return FIELD_WEIGHT;
	}
	
	@Override
	protected void addExtraHeads(ExamDetailInfoVO examDetailInfo, Row row,
			int cellIndex,Sheet sheet,Map<String, CellStyle> styles) throws Exception {
		Map<Integer, Short> resources = getResource();
		Row scoreRow = sheet.getRow(2);
		int level = examDetailInfo.getBasicInfo().getLevel();
		CellStyle style = getExecuteStype(resources.get(level));
		
		for(int i = 13;i < 17;i++){
			Cell scoreValCell = scoreRow.getCell(i);
            if(scoreValCell!=null){
                scoreValCell.setCellStyle(style);
            }

		}
		
	}
	
	/**
	 * 为模块添加得分列
	 */
	@Override
	protected Set<Field> doWrapModuleFields(Module module, Set<Field> fields) {
		Set<Field> results = super.doWrapModuleFields(module, fields);
		if("关键项评估".equals(module.getName())){
			return results;
		}else{
			if(contains(results,"得分")){
				return results;
			}else{
				results.add(new Field("得分"));
			}
		}
		return results;
	}
	
	private CellStyle getExecuteStype(Short color) {
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(color);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		return style;
	}

	private Map<Integer, Short> getResource() throws Exception {
		Map<Integer, Short> resources = new HashMap<Integer, Short>();
		
		resources.put(RED, IndexedColors.RED.index);
		resources.put(GREEN, IndexedColors.GREEN.index);
		resources.put(YELLOW, IndexedColors.YELLOW.index);
		return resources;
	}
	
	private Map<Integer, URL> getImageResource() throws Exception {
		Map<Integer, URL> resources = new HashMap<Integer, URL>();
		URL redURL = new ClassPathResource("images/light_red_small.png").getURL();
		URL greenURL = new ClassPathResource("images/light_green_small.png").getURL();
		URL yellowURL = new ClassPathResource("images/light_yellow_small.png").getURL();
		
		resources.put(RED, redURL);
		resources.put(GREEN, greenURL);
		resources.put(YELLOW, yellowURL);
		return resources;
	}
	
	protected Map<String, CellInfo> buildFieldCellInfo() {
		Map<String, CellInfo> map = new HashMap<String, ExamTestReport.CellInfo>();
		map.put(HEAD_FIELD_DATE, newCellInfo(2));
		map.put(HEAD_FIELD_DATE + HEAD_VALUE_SUBFFIC, newCellInfo(3));
		map.put(HEAD_FIELD_SHOP_MANAGER, newCellInfo(2));
		map.put(HEAD_FIELD_SHOP_MANAGER + HEAD_VALUE_SUBFFIC, newCellInfo(4));
		map.put(HEAD_FIELD_JIHEREN, newCellInfo(3));
		map.put(HEAD_FIELD_JIHEREN + HEAD_VALUE_SUBFFIC, newCellInfo(4));
		map.put(HEAD_FIELD_SHOP, newCellInfo(2));
		map.put(HEAD_FIELD_SHOP + HEAD_VALUE_SUBFFIC, newCellInfo(3));
		map.put(HEAD_FIELD_LAST_JIHE_SCORE, newCellInfo(2));
		map.put(HEAD_FIELD_LAST_JIHE_SCORE + HEAD_VALUE_SUBFFIC, newCellInfo(4));
		map.put(HEAD_FIELD_JIHE_SCORE, newCellInfo(3));
		map.put(HEAD_FIELD_JIHE_SCORE + HEAD_VALUE_SUBFFIC, newCellInfo(4));
		map.put(HEAD_FIELD_CUR_MANAGER, newCellInfo(2));
		map.put(HEAD_FIELD_CUR_MANAGER + HEAD_VALUE_SUBFFIC, newCellInfo(3));
		map.put(HEAD_FIELD_REPEAT_LOST_ITEM, newCellInfo(2));
		map.put(HEAD_FIELD_REPEAT_LOST_ITEM + HEAD_VALUE_SUBFFIC, newCellInfo(11));
		
		map.put(KEY_ITEM_FIELD_Y, newCellInfo(2));
		map.put(KEY_ITEM_FIELD_N, newCellInfo(2));
		map.put(KEY_ITEM_FIELD_DESC, newCellInfo(2));
		
		map.put(SAFE_MANAGER_FIELD_Y, newCellInfo(1));
		map.put(SAFE_MANAGER_FIELD_N, newCellInfo(1));
		map.put(SAFE_MANAGER_FIELD_DESC, newCellInfo(2));
		map.put(SAFE_MANAGER_FIELD_SCORE, newCellInfo(1));
		map.put(SAFE_MANAGER_FIELD_GETSCORE, newCellInfo(1));
		
		map.put(TABLE_ITEM_FIELD_Y, newCellInfo(1));
		map.put(TABLE_ITEM_FIELD_N, newCellInfo(1));
		map.put(TABLE_ITEM_FIELD_DESC, newCellInfo(2));
		map.put(TABLE_ITEM_FIELD_SCORE, newCellInfo(1));
		map.put(TABLE_ITEM_FIELD_GETSCORE, newCellInfo(1));
		
		map.put(STOCK_ITEM_FIELD_Y, newCellInfo(1));
		map.put(STOCK_ITEM_FIELD_N, newCellInfo(1));
		map.put(STOCK_ITEM_FIELD_DESC, newCellInfo(2));
		map.put(STOCK_ITEM_FIELD_SCORE, newCellInfo(1));
		map.put(STOCK_ITEM_FIELD_GETSCORE, newCellInfo(1));
		
		return map;
	}

	private CellInfo newCellInfo(int colSpan) {
		return new CellInfo(colSpan);
	}

	/**
	 * 给字段分配权重
	 * @return
	 */
	private static Map<String, Integer> buildFieldScore() {
		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put(FIELD_SCORE, 1);
		result.put(FIELD_Y, 2);
		result.put(FIELD_N, 3);
		result.put(FIELD_GETSCORE, 4);
		result.put(FIELD_DESC, 5);
		
		result.put(FIELD_RESOURCE, 6);
		result.put(FIELD_RESOLVE, 7);
		result.put(FIELD_RESOLVE1, 8);
		result.put(FIELD_SCORENUM, 9);
		
		result.put(FIELD_TACK, 20);
		result.put(FIELD_BEGINTIME, 21);
		result.put(FIELD_EXPENDTIME, 22);
		result.put(FIELD_REALENDTIME, 23);
		result.put(FIELD_USERNAME, 24);
		result.put(FIELD_EXPRESULT, 25);
		
		/* HEAD_FIELD_DATE = "日期";
			public static final String HEAD_FIELD_SHOP_MANAGER = "餐厅经理";
			public static final String HEAD_FIELD_JIHEREN = "稽核人";
			public static final String HEAD_FIELD_SHOP = "餐厅";
			public static final String HEAD_FIELD_LAST_JIHE_SCORE = "上次稽核成绩";
			public static final String HEAD_FIELD_JIHE_SCORE = "本次稽核成绩";
			public static final String HEAD_FIELD_CUR_MANAGER = "值班经理";
			public static final String HEAD_FIELD_REPEAT_LOST_ITEM = "重复扣分项编号";*/
		
		result.put(HEAD_FIELD_DATE, 30);
		result.put(HEAD_FIELD_SHOP_MANAGER, 31);
		result.put(HEAD_FIELD_JIHEREN, 32);
		result.put(HEAD_FIELD_SHOP, 33);
		result.put(HEAD_FIELD_LAST_JIHE_SCORE, 34);
		result.put(HEAD_FIELD_JIHE_SCORE, 35);
		result.put(HEAD_FIELD_CUR_MANAGER, 36);
		result.put(HEAD_FIELD_REPEAT_LOST_ITEM, 37);
		
		return result;
	}
	
	private static Map<String, CellInfo> buildAdditionalFieldMap() {
		Map<String, CellInfo> result = new HashMap<String, CellInfo>();
		
		result.put(FIELD_RESOURCE, new CellInfo(7,"resource"));
		result.put(FIELD_RESOLVE, new CellInfo(7,"resolve"));
		result.put(FIELD_RESOLVE1, new CellInfo(7,"resolve"));
		result.put(FIELD_SCORENUM, new CellInfo(4,"scoreNum"));
		
		result.put(FIELD_TACK, new CellInfo(4,"tack"));
		result.put(FIELD_BEGINTIME, new CellInfo(2,"beginTime"));
		result.put(FIELD_EXPENDTIME, new CellInfo(1,"expEndTime"));
		result.put(FIELD_REALENDTIME, new CellInfo(1,"realEndTime"));
		result.put(FIELD_USERNAME, new CellInfo(2,"userName"));
		result.put(FIELD_EXPRESULT, new CellInfo(4,"expResult"));
		
		return result;
	}

	
	protected String getCellValue(Field field,int score) {
		String fieldName = field.getFieldName();
		String fieldValue = field.getFieldValue();
        if(5==field.getFieldType()){
            fieldValue= getShopNumByShopId(fieldValue);
        }
		String cellValue = "";
		if(FIELD_Y.equals(fieldName) || FIELD_N.equals(fieldName)){
			cellValue = "1".equals(fieldValue) ? "√" : "";
		}else if(FIELD_SCORE.equals(fieldName)){
			cellValue = score + "";
		}else{
			cellValue = fieldValue;
		}

		return cellValue;
	}

}
