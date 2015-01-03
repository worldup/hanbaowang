package com.upbest.mvc.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.upbest.mvc.service.IExamService;
import com.upbest.mvc.vo.ExamDetailInfoVO.Field;
import com.upbest.mvc.vo.ExamDetailInfoVO.Module;
import com.upbest.mvc.vo.ExamDetailInfoVO.Question;
import com.upbest.mvc.vo.ExamDetailInfoVO.QuestionTestDetailInfo;

/**
 * Guest is King excel
 * @author QunZheng
 *
 */
public class DayangExamTestReport extends ExamTestReport {
	
	public static final String FIELD_Y = "Y";
	public static final String FIELD_N = "N";
	public static final String FIELD_DESC = "描述问题";
	
	
	public DayangExamTestReport(IExamService service) {
		super(service);
	}
	/**
	 * 字段的权重
	 */
	public static final Map<String, Integer> FIELD_WEIGHT = buildFieldScore();
	
	@Override
	protected Map<String, Integer> getFieldWeight() {
		return FIELD_WEIGHT;
	}
	
	@Override
	protected Set<Field> doWrapModuleFields(Module module, Set<Field> fields) {
		String fieldName = "描述问题";
		Set<Field> result = super.doWrapModuleFields(module, fields);
		if("评估总结".equals(module.getName())){
			if(contains(result,fieldName)){
				return result;
			}else{
				Field field = new Field(fieldName);
				//从测试数据中获取 "描述问题" 的id
				List<Question> questions = module.getQuestions();
				
				found:
				if(!CollectionUtils.isEmpty(questions)){
					for (Question question : questions) {
						QuestionTestDetailInfo testDetailInfo = question.getTestDetailInfo();
						if(testDetailInfo != null){
							List<Field> testFields = testDetailInfo.getFieldInfos();
							if(!CollectionUtils.isEmpty(testFields)){
								for (Field f : testFields) {
									if(fieldName.equals(f.getFieldName())){
										field.setId(f.getId());
										break found;
									}
								}
							}
						}
					}
				}
				result.add(field);
			}
		}
		return result;
	}

	/**
	 * 给字段分配权重
	 * @return
	 */
	private static Map<String, Integer> buildFieldScore() {
		Map<String, Integer> result = new HashMap<String, Integer>();
		result.put(FIELD_Y, 2);
		result.put(FIELD_N, 3);
		result.put(FIELD_DESC, 5);
		
		return result;
	}
	
	
	protected String getCellValue(Field field,int score) {
		String fieldName = field.getFieldName();
		String fieldValue = field.getFieldValue();
		
		String cellValue = "";
		if(FIELD_Y.equals(fieldName) || FIELD_N.equals(fieldName)){
			cellValue = "1".equals(fieldValue) ? "√" : "";
		}else{
			cellValue = fieldValue;
		}
		
		return cellValue;
	}

}
