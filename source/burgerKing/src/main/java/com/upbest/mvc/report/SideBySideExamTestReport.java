package com.upbest.mvc.report;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import net.sf.jett.transform.ExcelTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.core.io.ClassPathResource;
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
    public byte[]   generateExcel(String fullServerPath,ExamDetailInfoVO examDetailInfo){
        ClassPathResource classPathResource=new ClassPathResource("template\\sidebyside.xlsx");

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
    private Map<String,String> moduleMaps(){
        Map<String,String> moduleMap=new HashMap();
        moduleMap.put("肩并肩拜访","sbsModule");

        return moduleMap;
    }
    private Map transJXlsMap(String fullServerPath,ExamDetailInfoVO vo){
        Map jxlsMap=new HashMap();
        Map heads=new HashMap();
        Map modules=new HashMap();
        Map addModules=new HashMap();
        jxlsMap.put("addModules",addModules);
        jxlsMap.put("heads",heads);
        jxlsMap.put("modules",modules);
        List<Field> headList=vo.getHeads();
        //转换标头
        if(CollectionUtils.isNotEmpty(headList)){
            for(Field item:headList){
                if("日期".equals(item.getFieldName())){
                    String datestr=item.getFieldValue();
                    if (StringUtils.isNotEmpty(datestr)) {
                        datestr=DateFormatUtils.format(Long.parseLong(datestr),"yyyy-MM-dd");
                    }
                    heads.put("date", datestr) ;
                }
                else if("区域经理".equals(item.getFieldName())){
                    heads.put("manager", item.getFieldValue()) ;
                }
                else if("肩并肩".equals(item.getFieldName())){
                    heads.put("sidebyside", "1".equals(item.getFieldValue())?"√":"") ;
                }
                else if(" 月度 进展 回顾".equals(item.getFieldName())){
                    heads.put("monthfeedback", "1".equals(item.getFieldValue())?"√":"") ;
                }
                else if("其他".equals(item.getFieldName())){
                    heads.put("other", "1".equals(item.getFieldValue())?"√":"") ;
                }
                else  if("RGM/ MUM  姓名".equals(item.getFieldName())){
                    heads.put("mumName", item.getFieldValue()) ;
                }
                else if("BK#".equals(item.getFieldName())){
                    heads.put("restName", item.getFieldValue()) ;
                }
            }
        }
        List<AdditionalModule> additionalModuleList=vo.getAdditionalModules();
        List<Map<String,Object>> testResultList=new ArrayList();

        if(CollectionUtils.isNotEmpty(additionalModuleList)){

            testResultList =additionalModuleList.get(0).getTestResult();
             if(CollectionUtils.isNotEmpty(testResultList)){
                    for(Map<String,Object> map:testResultList){
                        String dateStr= MapUtils.getString(map, "beginTime");
                        map.get("tack");
//                      map.get("expResult");
                        if(StringUtils.isNotEmpty(dateStr)){
                            dateStr=DateFormatUtils.format(Long.parseLong(dateStr),"yyyy-MM-dd");
                            map.put("beginTime",dateStr);
                            }
                    }
             }
        }
        addModules.put("testResultList",testResultList);
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

                            if("餐厅今日重点目标".equals(question.getQuesDesc())){
                                qingjiewaibuquestions.put("ctjrzdmb",C01);
                            }
                            else if("活动内容".equals(question.getQuesDesc())){
                                qingjiewaibuquestions.put("hdnr",C01);
                            } else if("重点".equals(question.getQuesDesc())){
                                qingjiewaibuquestions.put("zd",C01);
                            } else if("我最欣赏的是".equals(question.getQuesDesc())){
                                qingjiewaibuquestions.put("wzxsds",C01);
                            } else if("可以做的更好得是".equals(question.getQuesDesc())){
                                qingjiewaibuquestions.put("kyzdghds",C01);
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
