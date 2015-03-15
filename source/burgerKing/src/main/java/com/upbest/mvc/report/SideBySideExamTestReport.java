package com.upbest.mvc.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.CollectionUtils;

import com.upbest.mvc.service.IExamService;
import com.upbest.mvc.vo.ExamDetailInfoVO;
import com.upbest.mvc.vo.ExamDetailInfoVO.AdditionalModule;
import com.upbest.mvc.vo.ExamDetailInfoVO.Field;
import com.upbest.mvc.vo.ExamDetailInfoVO.Module;
import com.upbest.mvc.vo.ExamDetailInfoVO.Question;
import com.upbest.mvc.vo.ExamDetailInfoVO.QuestionTestDetailInfo;

/**
 * 肩并肩 生成excel
 * @author QunZheng
 *
 */
public class SideBySideExamTestReport extends ExamTestReport {
	
	public SideBySideExamTestReport(IExamService service) {
		super(service);
	}

	public static final String FIELD_TACK = "行动项目";
	public static final String FIELD_BEGINTIME = "目标日期";
	public static final String FIELD_EXP_RESULT = "跟进完成";
	
	/**
	 * 字段的权重
	 */
	public static final Map<String, Integer> FIELD_WEIGHT = buildFieldScore();
	public static final Map<String, CellInfo> ADDITIONAL_FIELD_MAP = buildAdditionalFieldMap();
	
	@Override
	protected Map<String, CellInfo> getAdditionalFieldMap() {
		return ADDITIONAL_FIELD_MAP;
	}
	
	@Override
	protected Map<String, Integer> getFieldWeight() {
		return FIELD_WEIGHT;
	}
	
	/**
	 * 生成模块信息
	 * @param examDetailInfo
	 * @param sheet
	 * @param styles
	 * @param firstRow
	 */
	@Override
	protected int buildModulerInfo(String fullServerPath,ExamDetailInfoVO examDetailInfo, Sheet sheet,
			Map<String, CellStyle> styles,Integer firstRow, CreationHelper creationHelper) {
		firstRow += 1;
		List<Module> modules = examDetailInfo.getModules();
		if(!CollectionUtils.isEmpty(modules)){
			for (Module module : modules) {
				//显示模块下的问题描述
				List<Question> questions = module.getQuestions();
				if(!CollectionUtils.isEmpty(questions)){
					for (int i = 0;i < questions.size();i++) {
						
						Question ques = questions.get(i);
						Row questionRow = sheet.createRow(firstRow++);
						questionRow.setHeightInPoints(20);
						
						Cell quesDescCell = questionRow.createCell(0);
						quesDescCell.setCellValue(ques.getQuesDesc());
						sheet.addMergedRegion(new CellRangeAddress(questionRow.getRowNum(), questionRow.getRowNum(), quesDescCell.getColumnIndex(), 7));
						quesDescCell.setCellStyle(styles.get(QUESTION_DESC));
						
						QuestionTestDetailInfo testDetailInfo = ques.getTestDetailInfo();
						List<Field> fieldInfos = testDetailInfo.getFieldInfos();
						if(!CollectionUtils.isEmpty(fieldInfos)){
							Field fieldInfo = fieldInfos.get(0);
							Row questionAnsRow = sheet.createRow(firstRow++);
							questionAnsRow.setHeightInPoints(100);
							Cell ansCell = questionAnsRow.createCell(0);
							ansCell.setCellValue(fieldInfo.getFieldValue());
							
							sheet.addMergedRegion(new CellRangeAddress(questionAnsRow.getRowNum(), questionAnsRow.getRowNum(), ansCell.getColumnIndex(), 7));
						}else{
							firstRow += 5;
						}
						
					}
				}
				 
			}
		}
		
		return firstRow;
	}
	
	@Override
	protected AdditionalModule doWrapModule(AdditionalModule module) {
		module.setName("");
		return module;
	}
	
	
	/**
	 * 给字段分配权重
	 * @return
	 */
	private static Map<String, Integer> buildFieldScore() {
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		result.put(FIELD_TACK, 20);
		result.put(FIELD_BEGINTIME, 21);
		result.put(FIELD_EXP_RESULT, 22);
		
		return result;
	}
	
	private static Map<String, CellInfo> buildAdditionalFieldMap() {
		Map<String, CellInfo> result = new HashMap<String, CellInfo>();
		
		
		result.put(FIELD_TACK, new CellInfo(4,"tack"));
		result.put(FIELD_BEGINTIME, new CellInfo(4,"beginTime"));
		result.put(FIELD_EXP_RESULT, new CellInfo(4,"expResult"));
		
		return result;
	}
	
	@Override
	protected String getAdditionalCellValue(Object value, CellInfo cellInfo) {
		String val = super.getAdditionalCellValue(value, cellInfo);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if("beginTime".equals(cellInfo.getFieldName())&& StringUtils.isNotEmpty(val)){
			Date date = new Date();
			date.setTime(Long.valueOf(val));
			val = sdf.format(date);
		}
		
		return val;
	}

	
}
