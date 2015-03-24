package com.upbest.mvc.report;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.upbest.mvc.vo.ExamDetailInfoVO;
import net.sf.jett.transform.ExcelTransformer;
import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
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
                    String datestr=item.getFieldValue();
                    if (StringUtils.isNotEmpty(datestr)) {
                        datestr= DateFormatUtils.format(Long.parseLong(datestr), "yyyy-MM-dd");
                    }
                    heads.put("date", datestr) ;
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
        setStatisticMap(beans);
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
    public void setStatisticMap(Map beans){
        JexlEngine jexl = new JexlEngine();
        // Create an expression object
        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("rev",beans );
        String B01_TScore = "rev.modules.youhao.questions.B01.score";
        String B02_TScore = "rev.modules.youhao.questions.B02.score";
        String B03_TScore = "rev.modules.youhao.questions.B03.score";
        String B04_TScore = "rev.modules.youhao.questions.B04.score";
        String B05_TScore = "rev.modules.youhao.questions.B05.score";
        String B06_TScore = "rev.modules.youhao.questions.B06.score";
        String B07_TScore = "rev.modules.youhao.questions.B07.score";
        String B01_Score="rev.modules.youhao.questions.B01.testDetailInfo.fieldInfos.SCORE";
        String B02_Score="rev.modules.youhao.questions.B02.testDetailInfo.fieldInfos.SCORE";
        String B03_Score="rev.modules.youhao.questions.B03.testDetailInfo.fieldInfos.SCORE";
        String B04_Score="rev.modules.youhao.questions.B04.testDetailInfo.fieldInfos.SCORE";
        String B05_Score="rev.modules.youhao.questions.B05.testDetailInfo.fieldInfos.SCORE";
        String B06_Score="rev.modules.youhao.questions.B06.testDetailInfo.fieldInfos.SCORE";
        String B07_Score="rev.modules.youhao.questions.B07.testDetailInfo.fieldInfos.SCORE";
        String B08_TScore = "rev.modules.sos.questions.B08.score";
        String B09_TScore = "rev.modules.sos.questions.B09.score";
        String B10_TScore = "rev.modules.sos.questions.B10.score";
        String B11_TScore = "rev.modules.sos.questions.B11.score";
        String B12_TScore = "rev.modules.sos.questions.B12.score";
        String B08_Score = "rev.modules.sos.questions.B08.testDetailInfo.fieldInfos.SCORE";
        String B09_Score = "rev.modules.sos.questions.B09.testDetailInfo.fieldInfos.SCORE";
        String B10_Score = "rev.modules.sos.questions.B10.testDetailInfo.fieldInfos.SCORE";
        String B11_Score = "rev.modules.sos.questions.B11.testDetailInfo.fieldInfos.SCORE";
        String B12_Score = "rev.modules.sos.questions.B12.testDetailInfo.fieldInfos.SCORE";
        String B13_TScore = "rev.modules.qingjie.questions.B13.score";
        String B14_TScore = "rev.modules.qingjie.questions.B14.score";
        String B15_TScore = "rev.modules.qingjie.questions.B15.score";
        String B16_TScore = "rev.modules.qingjie.questions.B16.score";
        String B17_TScore = "rev.modules.qingjie.questions.B17.score";
        String B18_TScore = "rev.modules.qingjie.questions.B18.score";
        String B13_Score = "rev.modules.qingjie.questions.B13.testDetailInfo.fieldInfos.SCORE";
        String B14_Score = "rev.modules.qingjie.questions.B14.testDetailInfo.fieldInfos.SCORE";
        String B15_Score = "rev.modules.qingjie.questions.B15.testDetailInfo.fieldInfos.SCORE";
        String B16_Score = "rev.modules.qingjie.questions.B16.testDetailInfo.fieldInfos.SCORE";
        String B17_Score = "rev.modules.qingjie.questions.B17.testDetailInfo.fieldInfos.SCORE";
        String B18_Score = "rev.modules.qingjie.questions.B18.testDetailInfo.fieldInfos.SCORE";
        String B19_TScore ="rev.modules.pinzhi.questions.B19.score";
        String B20_TScore ="rev.modules.pinzhi.questions.B20.score";
        String B21_TScore ="rev.modules.pinzhi.questions.B21.score";
        String B22_TScore ="rev.modules.pinzhi.questions.B22.score";
        String B23_TScore ="rev.modules.pinzhi.questions.B23.score";
        String B24_TScore ="rev.modules.pinzhi.questions.B24.score";
        String B25_TScore ="rev.modules.pinzhi.questions.B25.score";
        String B26_TScore ="rev.modules.pinzhi.questions.B26.score";
        String B27_TScore ="rev.modules.yingli.questions.B27.score";
        String B28_TScore ="rev.modules.yingli.questions.B28.score";
        String B29_TScore ="rev.modules.yingli.questions.B29.score";
        String B30_TScore ="rev.modules.yingli.questions.B30.score";
        String B31_TScore ="rev.modules.yingli.questions.B31.score";
        String B32_TScore ="rev.modules.xunlian.questions.B32.score";
        String B33_TScore ="rev.modules.xunlian.questions.B33.score";
        String B34_TScore ="rev.modules.xunlian.questions.B34.score";
        String B35_TScore ="rev.modules.xunlian.questions.B35.score";
        String B36_TScore ="rev.modules.xunlian.questions.B36.score";
        String B37_TScore ="rev.modules.weihu.questions.B37.score";
        String B38_TScore ="rev.modules.weihu.questions.B38.score";
        String B39_TScore ="rev.modules.weihu.questions.B39.score";
        String B40_TScore ="rev.modules.weihu.questions.B40.score";
        String B41_TScore ="rev.modules.weihu.questions.B41.score";
        String B19_Score ="rev.modules.pinzhi.questions.B19.testDetailInfo.fieldInfos.SCORE";
        String B20_Score ="rev.modules.pinzhi.questions.B20.testDetailInfo.fieldInfos.SCORE";
        String B21_Score ="rev.modules.pinzhi.questions.B21.testDetailInfo.fieldInfos.SCORE";
        String B22_Score ="rev.modules.pinzhi.questions.B22.testDetailInfo.fieldInfos.SCORE";
        String B23_Score ="rev.modules.pinzhi.questions.B23.testDetailInfo.fieldInfos.SCORE";
        String B24_Score ="rev.modules.pinzhi.questions.B24.testDetailInfo.fieldInfos.SCORE";
        String B25_Score ="rev.modules.pinzhi.questions.B25.testDetailInfo.fieldInfos.SCORE";
        String B26_Score ="rev.modules.pinzhi.questions.B26.testDetailInfo.fieldInfos.SCORE";
        String B27_Score ="rev.modules.yingli.questions.B27.testDetailInfo.fieldInfos.SCORE";
        String B28_Score ="rev.modules.yingli.questions.B28.testDetailInfo.fieldInfos.SCORE";
        String B29_Score ="rev.modules.yingli.questions.B29.testDetailInfo.fieldInfos.SCORE";
        String B30_Score ="rev.modules.yingli.questions.B30.testDetailInfo.fieldInfos.SCORE";
        String B31_Score ="rev.modules.yingli.questions.B31.testDetailInfo.fieldInfos.SCORE";
        String B32_Score ="rev.modules.xunlian.questions.B32.testDetailInfo.fieldInfos.SCORE";
        String B33_Score ="rev.modules.xunlian.questions.B33.testDetailInfo.fieldInfos.SCORE";
        String B34_Score ="rev.modules.xunlian.questions.B34.testDetailInfo.fieldInfos.SCORE";
        String B35_Score ="rev.modules.xunlian.questions.B35.testDetailInfo.fieldInfos.SCORE";
        String B36_Score ="rev.modules.xunlian.questions.B36.testDetailInfo.fieldInfos.SCORE";
        String B37_Score ="rev.modules.weihu.questions.B37.testDetailInfo.fieldInfos.SCORE";
        String B38_Score ="rev.modules.weihu.questions.B38.testDetailInfo.fieldInfos.SCORE";
        String B39_Score ="rev.modules.weihu.questions.B39.testDetailInfo.fieldInfos.SCORE";
        String B40_Score ="rev.modules.weihu.questions.B40.testDetailInfo.fieldInfos.SCORE";
        String B41_Score ="rev.modules.weihu.questions.B41.testDetailInfo.fieldInfos.SCORE";
        String A01_N="rev.modules.shipin.questions.A01.testDetailInfo.fieldInfos.N";
        String A02_N="rev.modules.shipin.questions.A02.testDetailInfo.fieldInfos.N";
        String A03_N="rev.modules.shipin.questions.A03.testDetailInfo.fieldInfos.N";
        String A04_N="rev.modules.shipin.questions.A04.testDetailInfo.fieldInfos.N";
        String A05_N="rev.modules.shipin.questions.A05.testDetailInfo.fieldInfos.N";
        String A06_N="rev.modules.shipin.questions.A06.testDetailInfo.fieldInfos.N";
        String A07_N="rev.modules.shipin.questions.A07.testDetailInfo.fieldInfos.N";
        String A08_N="rev.modules.shipin.questions.A08.testDetailInfo.fieldInfos.N";
        String A09_N="rev.modules.shipin.questions.A09.testDetailInfo.fieldInfos.N";
        String A10_N="rev.modules.shipin.questions.A10.testDetailInfo.fieldInfos.N";
        String A11_N="rev.modules.shipin.questions.A11.testDetailInfo.fieldInfos.N";
        String A12_N="rev.modules.shipin.questions.A12.testDetailInfo.fieldInfos.N";
        int safeScore=getSafe(jexl,jc,A01_N) +getSafe(jexl,jc,A02_N)+getSafe(jexl,jc,A03_N)+getSafe(jexl,jc,A04_N)+getSafe(jexl,jc,A05_N)+getSafe(jexl,jc,A06_N)+getSafe(jexl,jc,A07_N)+getSafe(jexl,jc,A08_N)+getSafe(jexl,jc,A09_N)
                +getSafe(jexl,jc,A10_N)+getSafe(jexl,jc,A11_N)+getSafe(jexl,jc,A12_N);

        int youhaoScore=getScore(jexl,jc,B01_Score)+getScore(jexl,jc,B02_Score)+getScore(jexl,jc,B03_Score)+getScore(jexl,jc,B04_Score)+getScore(jexl,jc,B05_Score)+getScore(jexl,jc,B06_Score)+getScore(jexl,jc,B07_Score);
        int sosScore=getScore(jexl,jc,B08_Score)+getScore(jexl,jc,B09_Score)+getScore(jexl,jc,B10_Score)+getScore(jexl,jc,B11_Score)+getScore(jexl,jc,B12_Score);
        int qingjieScore=getScore(jexl,jc,B13_Score)+getScore(jexl,jc,B14_Score)+getScore(jexl,jc,B15_Score)+getScore(jexl,jc,B16_Score)+getScore(jexl,jc,B17_Score)+getScore(jexl,jc,B18_Score);
        int pinzhiScore=getScore(jexl,jc,B19_Score)+getScore(jexl,jc,B20_Score)+getScore(jexl,jc,B21_Score)+getScore(jexl,jc,B22_Score)+getScore(jexl,jc,B23_Score)+getScore(jexl,jc,B24_Score)+getScore(jexl,jc,B25_Score)+getScore(jexl,jc,B26_Score);
        int yingliScore=getScore(jexl,jc,B27_Score)+getScore(jexl,jc,B28_Score)+getScore(jexl,jc,B29_Score)+getScore(jexl,jc,B30_Score)+getScore(jexl,jc,B31_Score);
        int xunlianScore=getScore(jexl,jc,B32_Score)+getScore(jexl,jc,B33_Score)+getScore(jexl,jc,B34_Score)+getScore(jexl,jc,B35_Score)+getScore(jexl,jc,B36_Score);
        int weihuScore=getScore(jexl,jc,B37_Score)+getScore(jexl,jc,B38_Score)+getScore(jexl,jc,B39_Score)+getScore(jexl,jc,B40_Score)+getScore(jexl,jc,B41_Score);
        int totalScore=youhaoScore+sosScore+qingjieScore+pinzhiScore+yingliScore+xunlianScore+weihuScore;

        int weihuTScore=getScore(jexl,jc,B37_TScore)+getScore(jexl,jc,B38_TScore)+getScore(jexl,jc,B39_TScore)+getScore(jexl,jc,B40_TScore)+getScore(jexl,jc,B41_TScore);
        int xunlianTScore=getScore(jexl,jc,B32_TScore)+getScore(jexl,jc,B33_TScore)+getScore(jexl,jc,B34_TScore)+getScore(jexl,jc,B35_TScore)+getScore(jexl,jc,B36_TScore);
        int yingliTScore=getScore(jexl,jc,B27_TScore)+getScore(jexl,jc,B28_TScore)+getScore(jexl,jc,B29_TScore)+getScore(jexl,jc,B30_TScore)+getScore(jexl,jc,B31_TScore);
        int pinzhiTScore=getScore(jexl,jc,B19_TScore)+getScore(jexl,jc,B20_TScore)+getScore(jexl,jc,B21_TScore)+getScore(jexl,jc,B22_TScore)+getScore(jexl,jc,B23_TScore)+getScore(jexl,jc,B24_TScore)+getScore(jexl,jc,B25_TScore)+getScore(jexl,jc,B26_TScore);
        int qingjieTScore=getScore(jexl,jc,B13_TScore)+getScore(jexl,jc,B14_TScore)+getScore(jexl,jc,B15_TScore)+getScore(jexl,jc,B16_TScore)+getScore(jexl,jc,B17_TScore)+getScore(jexl,jc,B18_TScore);
        int sosTScore=getScore(jexl,jc,B08_TScore)+getScore(jexl,jc,B09_TScore)+getScore(jexl,jc,B10_TScore)+getScore(jexl,jc,B11_TScore)+getScore(jexl,jc,B12_TScore);
        int youhaoTScore=getScore(jexl,jc,B01_TScore)+getScore(jexl,jc,B02_TScore)+getScore(jexl,jc,B03_TScore)+getScore(jexl,jc,B04_TScore)+getScore(jexl,jc,B05_TScore)+getScore(jexl,jc,B06_TScore)+getScore(jexl,jc,B07_TScore);
        int totalTScore=youhaoTScore+sosTScore+qingjieTScore+pinzhiTScore+yingliTScore+xunlianTScore+weihuTScore;
        int youhaoP=100*youhaoScore/youhaoTScore;
        int sosP=100*sosScore/sosTScore;
        int qingjieP=100*qingjieScore/qingjieTScore;
        int pinzhiP=100*pinzhiScore/pinzhiTScore;
        int yingliP=100*yingliScore/yingliTScore;
        int xunlianP=100*xunlianScore/xunlianTScore;
        int weihuP=100*weihuScore/weihuTScore;
        int totalP=100*totalScore/totalTScore;
        int safeP=safeScore>4?0:(safeScore==4?20:(safeScore==3?40:(safeScore==2?60:(safeScore==1?80:(safeScore==0?100:0)))));
        int resultP=(totalP+safeP)/2;
        String youhaoStr=youhaoP>85?"A":(youhaoP>70?"B":(youhaoP>55?"C":(youhaoP>40?"D":"F")));
        String sosStr=sosP>85?"A":(sosP>70?"B":(sosP>55?"C":(sosP>40?"D":"F")));
        String qingjieStr=qingjieP>85?"A":(qingjieP>70?"B":(qingjieP>55?"C":(qingjieP>40?"D":"F")));
        String pinzhiStr=pinzhiP>85?"A":(pinzhiP>70?"B":(pinzhiP>55?"C":(pinzhiP>40?"D":"F")));
        String yingliStr=yingliP>85?"A":(yingliP>70?"B":(yingliP>55?"C":(yingliP>40?"D":"F")));
        String xunlianStr=xunlianP>85?"A":(xunlianP>70?"B":(xunlianP>55?"C":(xunlianP>40?"D":"F")));
        String weihuStr=weihuP>85?"A":(weihuP>70?"B":(weihuP>55?"C":(weihuP>40?"D":"F")));
        String totalStr=totalP>85?"A":(totalP>70?"B":(totalP>55?"C":(totalP>40?"D":"F")));
        String safeStr=safeP>85?"A":(safeP>70?"B":(safeP>55?"C":(safeP>40?"D":"F")));
        String resultStr=resultP>85?"A":(resultP>70?"B":(resultP>55?"C":(resultP>40?"D":"F")));

        Map map=new HashMap();
        beans.put("statis",map);
        map.put("resultStr",resultStr);
        map.put("resultP",resultP);
        map.put("safeScore",safeScore);
        map.put("safeStr",safeStr);
        map.put("safeP",safeP);
        map.put("youhaoScore",youhaoScore);
        map.put("youhaoP",youhaoP);
        map.put("youhaoTScore",youhaoTScore);
        map.put("youhaoStr",youhaoStr);
        map.put("sosScore",sosScore);
        map.put("sosP",sosP);
        map.put("sosTScore",sosTScore);
        map.put("sosStr",sosStr);
        map.put("qingjieScore",qingjieScore);
        map.put("qingjieP",qingjieP);
        map.put("qingjieTScore",qingjieTScore);
        map.put("qingjieStr",qingjieStr);
        map.put("pinzhiScore",pinzhiScore);
        map.put("pinzhiP",pinzhiP);
        map.put("pinzhiTScore",pinzhiTScore);
        map.put("pinzhiStr",pinzhiStr);
        map.put("yingliScore",yingliScore);
        map.put("yingliP",yingliP);
        map.put("yingliTScore",yingliTScore);
        map.put("yingliStr",yingliStr);
        map.put("xunlianScore",xunlianScore);
        map.put("xunlianP",xunlianP);
        map.put("xunlianTScore",xunlianTScore);
        map.put("xunlianStr",xunlianStr);
        map.put("weihuScore",weihuScore);
        map.put("weihuP",weihuP);
        map.put("weihuTScore",weihuTScore);
        map.put("weihuStr",weihuStr);
        map.put("totalStr",totalStr);
        map.put("totalTScore",totalTScore);
        map.put("totalScore",totalScore);
        map.put("totalP",totalP);
    }
    private int getSafe(JexlEngine jexl,JexlContext jc,String jexlExp){
        Expression e = jexl.createExpression( jexlExp );
        // Now evaluate the expression, getting the result
        Object o = e.evaluate(jc);
        if("√".equals(o)){
            return 1;
        }
        return 0;
    }
    private int getScore(JexlEngine jexl, JexlContext jc,String jexlExp){
        Expression e = jexl.createExpression( jexlExp );
        // Now evaluate the expression, getting the result
        Object o = e.evaluate(jc);
        return o==null?0:Integer.parseInt(o.toString());
    }
}
