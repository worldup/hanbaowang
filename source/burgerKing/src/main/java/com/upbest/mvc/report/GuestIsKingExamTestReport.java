package com.upbest.mvc.report;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.upbest.mvc.service.IExamService;
import com.upbest.mvc.vo.ExamDetailInfoVO.Field;

/**
 * Guest is King excel
 * @author QunZheng
 *
 */
public class GuestIsKingExamTestReport extends ExamTestReport {
	
	public static final String FIELD_ADAPT = "不适用";
	public static final String FIELD_SCORE = "分值";
	
	public GuestIsKingExamTestReport(IExamService service) {
		super(service);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 字段的权重
	 */
	public static final Map<String, Integer> FIELD_WEIGHT = buildFieldScore();
	
	@Override
	protected Map<String, Integer> getFieldWeight() {
		return FIELD_WEIGHT;
	}
	
	
	/**
	 * 给字段分配权重
	 * @return
	 */
	private static Map<String, Integer> buildFieldScore() {
		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put(FIELD_ADAPT, 1);
		result.put(FIELD_SCORE, 2);
		
		return result;
	}
	
	protected String getCellValue(Field field,int score) {
		String fieldName = field.getFieldName();
		String fieldValue = field.getFieldValue();
		
		String cellValue = "";
		if(FIELD_ADAPT.equals(fieldName)){
			cellValue = "1".equals(fieldValue) ? "√" : "";
		}else if(FIELD_SCORE.equals(fieldName)){
			cellValue = score + "";
		}else{
			cellValue = fieldValue;
		}
		
		return cellValue;
	}

}
