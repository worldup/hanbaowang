package com.upbest.mvc.report;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.upbest.mvc.vo.ExamDetailInfoVO;
import net.sf.jett.transform.ExcelTransformer;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ClassPathResource;
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
    private Map<String,String> moduleMaps(){
        Map<String,String> moduleMap=new HashMap();
        moduleMap.put("友好","youhao");
        moduleMap.put("sos","sos");
        moduleMap.put("清洁","qingjie");
        moduleMap.put("品质","pinzhi");
        moduleMap.put("盈利值班管理","yingli");
        moduleMap.put("训练","xunlian");
        moduleMap.put("维护保养","weihu");
        moduleMap.put("食品安全","shipin");
        moduleMap.put("评估总结","pingguzongjie");
        return moduleMap;
    }
    private Map transJXlsMap(String fullServerPath,ExamDetailInfoVO vo){
        Map jxlsMap=new HashMap();
        Map heads=new HashMap();
        Map modules=new HashMap();
        jxlsMap.put("heads",heads);
        jxlsMap.put("modules",modules);
        List<Field> headList=vo.getHeads();
        //转换标头
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(headList)){
            for(Field item:headList){
                if("日期".equals(item.getFieldName())){
                    heads.put("date", item.getFieldValue()) ;
                }
                else if("值班经理".equals(item.getFieldName())){
                    heads.put("manager", item.getFieldValue()) ;
                }
                else if("评估人".equals(item.getFieldName())){
                    heads.put("feedback", item.getFieldValue()) ;
                }
                else if("餐厅编号".equals(item.getFieldName())){
                    heads.put("restName", item.getFieldValue()) ;
                }
            }
        }
        List<ExamDetailInfoVO.Module> moduleList= vo.getModules();
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(moduleList)){
            Map<String,String> moduleMaps=moduleMaps();
            for(ExamDetailInfoVO.Module module:moduleList){
                // module.getFields();
                Map qingjiewaibu=new HashMap();
                modules.put(moduleMaps.get(module.getName()),qingjiewaibu);
                Map qingjiewaibuquestions=new HashMap();
                qingjiewaibu.put("questions",qingjiewaibuquestions);
                List<ExamDetailInfoVO.Question> questions=  module.getQuestions();
                if(org.apache.commons.collections.CollectionUtils.isNotEmpty(questions)){
                    for(ExamDetailInfoVO.Question question:questions){
                        Map C01=new HashMap();
                        if("评估总结".equals(module.getName())){
                            if("我最欣赏这家餐厅".equals(question.getQuesDesc())){
                                qingjiewaibuquestions.put("pg_xinshang",C01);
                            }
                            else if("最需关注的问题点".equals(question.getQuesDesc())){
                                qingjiewaibuquestions.put("pg_guanzhu",C01);
                            }

                        }else{
                            qingjiewaibuquestions.put(question.getSerialNumber(),C01);
                        }
                        C01.put("score",question.getScore());
                        ExamDetailInfoVO.QuestionTestDetailInfo questionTestDetailInfo= question.getTestDetailInfo();
                        Map testDetailInfoMap=new HashMap();
                        C01.put("testDetailInfo",testDetailInfoMap);
                        testDetailInfoMap.put("score",questionTestDetailInfo.getScore());
                        List<Map<String,String>> evidenceList=new ArrayList();
                        String evidenceStr=questionTestDetailInfo.getEvidence();
                        testDetailInfoMap.put("evidence", evidenceList);
                        if(StringUtils.isNotEmpty(evidenceStr)){
                            String[] evidenceStrArr=     StringUtils.split(evidenceStr,",");
                            int tempIdx=0;
                            for(String tempEv:evidenceStrArr){
                                Map<String,String> temMap=new HashMap();
                                temMap.put("address", fullServerPath + tempEv);
                                temMap.put("value","图片"+(++tempIdx));
                                evidenceList.add(temMap);
                            }
                        }

                        Map fieldInfosMap=new HashMap();
                        testDetailInfoMap.put("fieldInfos",fieldInfosMap);
                        List<Field> fieldList= questionTestDetailInfo.getFieldInfos();
                        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(fieldList)){
                            for(Field field:fieldList){
                                if("Y".equals(field.getFieldName())){
                                    if("1".equals(field.getFieldValue())){
                                        fieldInfosMap.put("Y","√");
                                    }
                                    else{
                                        fieldInfosMap.put("Y","");
                                    }

                                }else if("N".equals(field.getFieldName())){
                                    if("1".equals(field.getFieldValue())){
                                        fieldInfosMap.put("N","√");
                                    }
                                    else{
                                        fieldInfosMap.put("N","");
                                    }

                                }else if("不适用".equals(field.getFieldName())){
                                    if("1".equals(field.getFieldValue())){
                                        fieldInfosMap.put("NA","√");
                                    }
                                    else{
                                        fieldInfosMap.put("NA","");
                                    }
                                }else if("描述问题".equals(field.getFieldName())){
                                    fieldInfosMap.put("DESC",field.getFieldValue());
                                }
                                else if("得分".equals(field.getFieldName())){
                                    boolean isNa=false;
                                    for(Field temp:fieldList){
                                        if("不适用".equals(temp.getFieldName()) && "1".equals(temp.getFieldValue())){
                                            isNa=true;
                                        }
                                    }
                                    fieldInfosMap.put("SCORE",isNa?"N/A":field.getFieldValue());
                                }
                            }
                        }

                    }
                }

            }
        }
        return jxlsMap;

    }
    public byte[]   generateExcel(String fullServerPath,ExamDetailInfoVO examDetailInfo){
        ClassPathResource classPathResource=new ClassPathResource("template\\REV.xlsx");

        Map beans=transJXlsMap(fullServerPath,examDetailInfo);
        ExcelTransformer transformer = new ExcelTransformer();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try{
            Workbook workbook= transformer.transform(classPathResource.getInputStream(), beans);
            workbook.write(outputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}
