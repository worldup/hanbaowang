package com.upbest.mvc.statistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.upbest.mvc.service.IExamService;
import com.upbest.mvc.service.ITestPaperDetailService;
import com.upbest.mvc.vo.ExamDetailInfoVO;
import com.upbest.mvc.vo.ExamDetailInfoVO.Module;
import com.upbest.mvc.vo.ExamDetailInfoVO.Question;
import com.upbest.mvc.vo.TestPaperDetailVO;

@Component
public class RevExamStatistic implements ExamStatistic {

	@Autowired
	private IExamService service;
	
	@Autowired
	private ITestPaperDetailService paperDetailService;
	
	public static final int STANDARD_RESULT = 1;
	public static final int SUMMARY_RESULT = 2;
	
	public static final String SUMMARY_FIELD_LOSENUM = "loseNum";
	public static final String SUMMARY_FIELD_FOODSAFETY = "foodSafety";
	public static final String SUMMARY_FIELD_FRANCHISING = "franchising";
	
	public static final String MODULE_STANDARD_SUMMARY = "特许经营品牌标准结果";
	
	@Override
	public Object statistic(int userId, int examId) {
		Map<Integer, Object> result = new HashMap<Integer, Object>();
		
		ExamDetailInfoVO examDetailInfo = service.findExamDetailInfo(examId);
		List<Module> modules = examDetailInfo.getModules();
		
		List<ModuleScoreInfo> standarnResult = new ArrayList<ModuleScoreInfo>();
		if(!CollectionUtils.isEmpty(modules)){
			for (Module module : modules) {
				List<Question> questions = module.getQuestions();
				List<QuestionResultInfo> resultInfo = statisticResultSocre(userId,examId,questions);
				
				int resultScore = 0,
					totalScore = 0,
					loseNum = 0;
				if(!CollectionUtils.isEmpty(resultInfo)){
					for (QuestionResultInfo resInfo : resultInfo) {
						resultScore += resInfo.getScore();
						totalScore += resInfo.getTotalScore();
						if(resInfo.getScore() != resInfo.getTotalScore()){
							loseNum++;
						}
					}
				}
				ModuleScoreInfo moduleScoreInfo = new ModuleScoreInfo(module.getName(),resultScore,totalScore,loseNum);
				moduleScoreInfo.setLevel(DefaultLevel.getLevel(resultScore));
				standarnResult.add(moduleScoreInfo);
			}
		}
		
		result.put(STANDARD_RESULT, getSummaryStandarnResult(standarnResult));
		result.put(SUMMARY_RESULT, getSummaryResult(standarnResult));
		return result;
	}

	private List<ModuleScoreInfo> getSummaryStandarnResult(List<ModuleScoreInfo> standarnResult) {
		if(!CollectionUtils.isEmpty(standarnResult)){
			int score = 0,
				totalScore = 0;
			for (ModuleScoreInfo moduleScoreInfo : standarnResult) {
				score += moduleScoreInfo.getScore();
				totalScore += moduleScoreInfo.getTotalScore();
			}
			
			ModuleScoreInfo summary = new ModuleScoreInfo(MODULE_STANDARD_SUMMARY,score,totalScore);
			standarnResult.add(summary);
			
		}
		return standarnResult;
	}

	private Map<String, Object> getSummaryResult(List<ModuleScoreInfo> standarnResult) {
		Map<String, Object> summaryResult = new HashMap<String, Object>();
		summaryResult.put(SUMMARY_FIELD_LOSENUM,  0);
		summaryResult.put(SUMMARY_FIELD_FOODSAFETY, "Success");
		summaryResult.put(SUMMARY_FIELD_FRANCHISING, "");
		
		if(!CollectionUtils.isEmpty(standarnResult)){
			for (ModuleScoreInfo moduleScoreInfo : standarnResult) {
				if(moduleScoreInfo.getName().matches("食品安全.*")){
					int loseNum = moduleScoreInfo.getLoseScoreNum();
					summaryResult.put(SUMMARY_FIELD_LOSENUM,  loseNum);
					summaryResult.put(SUMMARY_FIELD_LOSENUM, loseNum > 0 ? "Fail" : "Success");
				}else if(moduleScoreInfo.getName().matches("特许经营.*")){
					summaryResult.put(SUMMARY_FIELD_FRANCHISING, moduleScoreInfo.getLevel());
				}
				
			}
		}
		return summaryResult;
	}

	private List<QuestionResultInfo> statisticResultSocre(int userId, int examId,
			List<Question> questions) {
		List<QuestionResultInfo> result = new ArrayList<QuestionResultInfo>();
		
		List<TestPaperDetailVO> paperDetailList = paperDetailService.queryTestPaperDetails(userId, examId);
		if(!CollectionUtils.isEmpty(paperDetailList)){
			for (TestPaperDetailVO testPaperDetailVO : paperDetailList) {
				Question ques = getCurrentQuestion(testPaperDetailVO.getQid(),questions);
				if(ques != null){
					QuestionResultInfo resultInfo = new QuestionResultInfo(testPaperDetailVO.getQid(),
							ques.getScore(), testPaperDetailVO.getTvalue());
					result.add(resultInfo);
				}
			}
		}
		return result;
	}

	private Question getCurrentQuestion(Integer qid, List<Question> questions) {
		if(!CollectionUtils.isEmpty(questions)){
			for (Question question : questions) {
				if(question.getId() == qid){
					return question;
				}
			}
		}
		return null;
	}


}
