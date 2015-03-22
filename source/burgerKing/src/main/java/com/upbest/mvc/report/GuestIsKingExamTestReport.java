package com.upbest.mvc.report;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upbest.mvc.vo.ExamDetailInfoVO;
import net.sf.jett.transform.ExcelTransformer;
import org.apache.commons.collections.CollectionUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

import org.springframework.core.io.ClassPathResource;
import com.upbest.mvc.service.IExamService;
import com.upbest.mvc.vo.ExamDetailInfoVO.Field;

/**
 * Guest is King excel
 * @author QunZheng
 *
 */
public class GuestIsKingExamTestReport extends ExamTestReport {
    public byte[]   generateExcel(String fullServerPath,ExamDetailInfoVO examDetailInfo){
        ClassPathResource classPathResource=new ClassPathResource("template\\Guest Is King.xlsx");

        Map beans=transJXlsMap(fullServerPath,examDetailInfo);
        ExcelTransformer transformer = new ExcelTransformer();
      //  XLSTransformer transformer = new XLSTransformer();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try{

            Workbook workbook= transformer.transform(classPathResource.getInputStream(), beans);

//		 FileOutputStream outputStream = new FileOutputStream("c:/poi/side_by_side.xlsx");
            workbook.write(outputStream);
        }catch (Exception e){
             e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
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
    private Map<String,String> moduleMaps(){
        Map<String,String> moduleMap=new HashMap();
        moduleMap.put("清洁-外部","qingjie_waibu");
        moduleMap.put("清洁-大堂","qingjie_datang");
        moduleMap.put("清洁-洗手间和游乐区域","qingjie_xishoujian");
        moduleMap.put("品质","pinzhi");
        moduleMap.put("服务-团队形象","fuwu_tuandui");
        moduleMap.put("服务-大堂","fuwu_datang");
        moduleMap.put("服务-柜台","fuwu_guitai");
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
        if(CollectionUtils.isNotEmpty(headList)){
            for(Field item:headList){
                if("日期".equals(item.getFieldName())){
                    heads.put("date", item.getFieldValue()) ;
                }
                else if("值班经理".equals(item.getFieldName())){
                    heads.put("manager", item.getFieldValue()) ;
                }
                else if("成绩".equals(item.getFieldName())){
                    heads.put("score", item.getFieldValue()) ;
                }
                else if("地区".equals(item.getFieldName())){
                    heads.put("region", item.getFieldValue()) ;
                }
                else if("回馈提供者".equals(item.getFieldName())){
                    heads.put("feedback", item.getFieldValue()) ;
                }
               else  if("时间".equals(item.getFieldName())){
                    heads.put("time", item.getFieldValue()) ;
                }
               else if("餐厅名字".equals(item.getFieldName())){
                    heads.put("restName", item.getFieldValue()) ;
                }
            }
        }
        List<ExamDetailInfoVO.Module> moduleList= vo.getModules();
        if(CollectionUtils.isNotEmpty(moduleList)){
            Map<String,String> moduleMaps=moduleMaps();
            for(ExamDetailInfoVO.Module module:moduleList){
                   // module.getFields();
                    Map qingjiewaibu=new HashMap();
                    modules.put(moduleMaps.get(module.getName()),qingjiewaibu);
                    Map qingjiewaibuquestions=new HashMap();
                     qingjiewaibu.put("questions",qingjiewaibuquestions);
                   List<ExamDetailInfoVO.Question> questions=  module.getQuestions();
                    if(CollectionUtils.isNotEmpty(questions)){
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
                                if(CollectionUtils.isNotEmpty(fieldList)){
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
                                        else if("分值".equals(field.getFieldName())){
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
}
