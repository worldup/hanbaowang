package com.upbest.mvc.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.upbest.mvc.constant.Constant.QuestionType;
import com.upbest.mvc.entity.BActionPlan;
import com.upbest.mvc.entity.BProblemAnalysis;
import com.upbest.mvc.entity.BTestHeadingRela;
import com.upbest.mvc.entity.BTestPaper;
import com.upbest.mvc.entity.BTestPaperDetail;
import com.upbest.mvc.repository.factory.BActionPlanRespository;
import com.upbest.mvc.repository.factory.BProblemAnalysisRespository;
import com.upbest.mvc.repository.factory.ExaminationAssignRespository;
import com.upbest.mvc.repository.factory.TestHeadingRelaRespository;
import com.upbest.mvc.repository.factory.TestPaperDetailRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.ITestPaperDetailService;
import com.upbest.mvc.service.ITestPaperService;
import com.upbest.mvc.vo.TestPaperDetailVO;
import com.upbest.utils.ConfigUtil;
import com.upbest.utils.DataType;

@Service
public class TestPaperDetailServiceImpl implements ITestPaperDetailService {
    @Autowired
    private TestPaperDetailRespository tpRespository;

    @Autowired
    private ExaminationAssignRespository eaRespository;
    
    @Autowired
    private TestHeadingRelaRespository tstRespository;
    
    @Autowired
    private BProblemAnalysisRespository problemRespository;
    
    @Autowired
    private BActionPlanRespository actionPlanRespository;

    @Autowired
    private ITestPaperService testPaperService;

    @Autowired
    private CommonDaoCustom<Object[]> common;

    @Override
    public void saveTestPaperDetail(List<BTestPaperDetail> list, BTestPaper testPaper, List<BTestHeadingRela> testRela, List<BProblemAnalysis> proAnaList, List<BActionPlan> actionPlList) {
        /*  BExaminationAssign examAssign = eaRespository.findByUseridAndEid(testPaper.getUserid(), testPaper.getEid());
          examAssign.setState(1);
          eaRespository.save(examAssign);*/
        BTestPaper entity = testPaperService.saveTestPaper(testPaper);
        //保存问卷详细信息
        if (!CollectionUtils.isEmpty(list)) {
            for (BTestPaperDetail obj : list) {
                obj.setTid(entity.getId());
                tpRespository.save(obj);
                if(!CollectionUtils.isEmpty(obj.getTstHeadingRela())){
                    for(BTestHeadingRela tstRela:obj.getTstHeadingRela()){
                        tstRela.setTid(entity.getId());
                        /*if(tstRela.getFile()!=null){
                            //上传图片
                            uploadQuestionFile(tstRela.getFile(),tstRela.getValue());
                        }*/
                        tstRespository.save(tstRela);
                    }
                }
            }
        }
        //保存问卷台头信息
        if (!CollectionUtils.isEmpty(testRela)) {
            for (BTestHeadingRela obj : testRela) {
                obj.setTid(entity.getId());
                tstRespository.save(obj);
            }
        }
        //保存问题分析信息
        if (!CollectionUtils.isEmpty(proAnaList)) {
            for (BProblemAnalysis obj : proAnaList) {
                obj.settId(entity.getId());
                problemRespository.save(obj);
            }
        }
        //保存行动计划信息
        if (!CollectionUtils.isEmpty(actionPlList)) {
            for (BActionPlan obj : actionPlList) {
                obj.settId(entity.getId());
                actionPlanRespository.save(obj);
            }
        }
    }

    public void uploadQuestionFile(File file,String fileName){
        String headPath = ConfigUtil.get("questionPicPath");
        String filePath = headPath + fileName;
        try {
            FileUtils.copyFile(file, new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    @Override
    public List<TestPaperDetailVO> queryTestPaperDetails(Integer userId, Integer examId) {

        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  select t.q_value,                       ");
        sql.append("         t.q_content,             ");
        sql.append("         i.b_value,       ");
        sql.append("         t.t_answer,      ");
        sql.append("         t.t_value,       ");
        sql.append("         t.q_evidence,       ");
        sql.append("         t.q_resource,       ");
        sql.append("         t.q_resolve,     ");
        sql.append("         t.remark,     ");
        sql.append("         t.q_type,     ");
        sql.append("         t.q_id     ");
        sql.append("    from            ");
        sql.append("    (select q.q_value,q.q_content,q.q_type,q.q_module,t.*         ");
        sql.append("  	 	from (          ");
        sql.append("    		select d.* from            ");
        sql.append("    			(select t.id from BK_TEST_PAPER t where t.user_id = ? and e_id = ?)	t          ");
        sql.append("   				 left join BK_TEST_PAPER_DETAIL d          ");
        sql.append("    			 on t.id = d.t_id ) t         ");
        sql.append("    		left join       ");
        sql.append("    		BK_QUESTION q       ");
        sql.append("    		on t.q_id = q.id) t       ");
        sql.append("    left join BK_EX_BASE_INFO i on t.q_module = t.id      ");

        params.add(userId);
        params.add(examId);

        List<Object[]> page = common.queryBySql(sql.toString(), params);

        return buildTestPaperDetails(page);
    }

	private List<TestPaperDetailVO> buildTestPaperDetails(List<Object[]> page) {
		List<TestPaperDetailVO> result = new ArrayList<TestPaperDetailVO>();
		if(!CollectionUtils.isEmpty(page)){
			for (Object[] objAry : page) {
				TestPaperDetailVO vo = new TestPaperDetailVO();
				vo.setOriginalScore(DataType.getAsInt(objAry[0]));
				vo.setQuestionContent(DataType.getAsString(objAry[1]));
				vo.setModuleName(DataType.getAsString(objAry[2]));
				vo.setTanswer(DataType.getAsString(objAry[3]));
				vo.setTvalue(DataType.getAsInt(objAry[4]));
				vo.setQevidence(DataType.getAsString(objAry[5]));
				vo.setQresource(DataType.getAsString(objAry[6]));
				vo.setQresolve(DataType.getAsString(objAry[7]));
				vo.setRemark(DataType.getAsString(objAry[8]));
				vo.setQuestionType(QuestionType.getName(DataType.getAsInt(objAry[9])));
				vo.setQid(DataType.getAsInt(objAry[10]));
				
				result.add(vo);
			}
		}
		return result;
	}

}
