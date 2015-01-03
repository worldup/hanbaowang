package com.upbest.mvc.report;

import java.util.ArrayList;
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
 * Rev Self生成 excel
 * @author QunZheng
 *
 */
public class RevSelfExamTestReport extends ExamTestReport {
	
	public RevSelfExamTestReport(IExamService service) {
		super(service);
		// TODO Auto-generated constructor stub
	}

	public static final String FIELD_GETSCORE = "得分";
	public static final String FIELD_SCORE = "可能性";
	public static final String FIELD_ADAPT = "不适用";
	public static final String FIELD_DESC = "描述问题";
	
	public static final String FIELD_Y = "Y";
	public static final String FIELD_N = "N";
	
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
		result.put(FIELD_GETSCORE, 1);
		result.put(FIELD_SCORE, 2);
		result.put(FIELD_ADAPT, 3);
		result.put(FIELD_DESC, 4);
		
		result.put(FIELD_Y, 11);
		result.put(FIELD_N, 12);
		
		return result;
	}
	
	/**
	 * 由于测试数据中没有可能性字段
	 * 所以在这添加可能性字段 ---》对应的分值
	 * 添加可能性字段信息
	 */
	@Override
	protected List<Field> doWrapFieldInfo(List<Field> fieldInfos, Question ques,
			Module module) {
		Field scoreField = null;
		Set<Field> fields = module.getFields();
		if(!CollectionUtils.isEmpty(fields)){
			for (Field field : fields) {
				if(FIELD_SCORE.equals(field.getFieldName())){
					scoreField = field;
					break;
				}
			}
			
			if(scoreField != null){
				if(fieldInfos == null){
					fieldInfos = new ArrayList<Field>();
				}
				fieldInfos.add(new Field(scoreField.getId(), scoreField.getFieldName(), scoreField.getFieldValue()));
			}
		}
		
		return fieldInfos;
	}
	
	/**
	 * 为模块添加描述问题头
	 */
	@Override
	protected Set<Field> doWrapModuleFields(Module module, Set<Field> fields) {
		boolean hasDescField = false;
		
		if(!CollectionUtils.isEmpty(fields)){
			for (Field field : fields) {
				if(FIELD_DESC.equals(field.getFieldName())){
					hasDescField = true;
					break;
				}
			}
		}
		/**
		 * 如果还没有描述问题头，则添加一个
		 */
		if(!hasDescField){
			//问题评测中找到描述问题的id
			List<Question> questions = module.getQuestions();
			if(!CollectionUtils.isEmpty(questions)){
				outer:
				for (Question question : questions) {
					QuestionTestDetailInfo testDetailInfo = question.getTestDetailInfo();
					if(testDetailInfo != null){
						List<Field> quesFieldInfos = testDetailInfo.getFieldInfos();
						if(!CollectionUtils.isEmpty(quesFieldInfos)){
							for (Field field : quesFieldInfos) {
								if(FIELD_DESC.equals(field.getFieldName())){
									fields.add(new Field(field.getId(), field.getFieldName()));
									break outer;
								}
							}
						}
					}
				}
			}
		}
		return fields;
	}
	
	protected String getCellValue(Field field,int score) {
		String fieldName = field.getFieldName();
		String fieldValue = field.getFieldValue();
		
		String cellValue = "";
		if(FIELD_ADAPT.equals(fieldName) || FIELD_Y.equals(fieldName) || FIELD_N.equals(fieldName)){
			cellValue = "1".equals(fieldValue) ? "√" : "";
		}else if(FIELD_SCORE.equals(fieldName)){
			cellValue = score + "";
		}else{
			cellValue = fieldValue;
		}
		
		return cellValue;
	}

}
