package com.upbest.mvc.service.impl;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.upbest.mvc.constant.Constant.HeadType;
import com.upbest.mvc.constant.Constant.QuestionType;
import com.upbest.mvc.entity.BActionPlan;
import com.upbest.mvc.entity.BExBaseInfo;
import com.upbest.mvc.entity.BExHeading;
import com.upbest.mvc.entity.BExHeadingRela;
import com.upbest.mvc.entity.BExamPaperDetail;
import com.upbest.mvc.entity.BExaminationAssign;
import com.upbest.mvc.entity.BExaminationPaper;
import com.upbest.mvc.entity.BProblemAnalysis;
import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.entity.BTestHeadingRela;
import com.upbest.mvc.entity.BTestPaper;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.handler.ExcelWriter;
import com.upbest.mvc.report.ExamTestReport;
import com.upbest.mvc.report.ExamTestReportFactory;
import com.upbest.mvc.repository.factory.BActionPlanRespository;
import com.upbest.mvc.repository.factory.BExBaseInfoRespository;
import com.upbest.mvc.repository.factory.BProblemAnalysisRespository;
import com.upbest.mvc.repository.factory.ExHeadingRelaRespository;
import com.upbest.mvc.repository.factory.ExamBaseinfoRespository;
import com.upbest.mvc.repository.factory.ExamHeadingRespository;
import com.upbest.mvc.repository.factory.ExaminationAssignRespository;
import com.upbest.mvc.repository.factory.ExamnationPaperDetailRespository;
import com.upbest.mvc.repository.factory.ExamnationPaperRespository;
import com.upbest.mvc.repository.factory.StoreRespository;
import com.upbest.mvc.repository.factory.TestHeadingRelaRespository;
import com.upbest.mvc.repository.factory.TestPaperRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.IBuserService;
import com.upbest.mvc.service.IExamService;
import com.upbest.mvc.vo.ExamDetailInfoVO;
import com.upbest.mvc.vo.ExamDetailInfoVO.AdditionalModule;
import com.upbest.mvc.vo.ExamDetailInfoVO.AdditionalModuleTestResult;
import com.upbest.mvc.vo.ExamDetailInfoVO.BasicInfo;
import com.upbest.mvc.vo.ExamDetailInfoVO.Field;
import com.upbest.mvc.vo.ExamDetailInfoVO.Module;
import com.upbest.mvc.vo.ExamDetailInfoVO.Question;
import com.upbest.mvc.vo.ExamDetailInfoVO.QuestionTestDetailInfo;
import com.upbest.mvc.vo.ExamExportVO;
import com.upbest.mvc.vo.ExamInfoVO;
import com.upbest.mvc.vo.ExamSheetVO;
import com.upbest.mvc.vo.ExamTypeVO;
import com.upbest.mvc.vo.LoseDetailExportVO;
import com.upbest.mvc.vo.LoseDetailVO;
import com.upbest.mvc.vo.QuestionVO;
import com.upbest.mvc.vo.SelectionVO;
import com.upbest.mvc.vo.SheetDataVO;
import com.upbest.utils.ComparatorExamSts;
import com.upbest.utils.DataType;
import com.upbest.utils.EmailUtils;
import com.upbest.utils.PageModel;
import com.upbest.utils.RoleFactory;

@Service
public class ExamServiceImpl implements IExamService {
    @Inject
    private BExBaseInfoRespository baseInfoRes;
    @Autowired
    private ExamBaseinfoRespository respository;

    @Autowired
    protected BExBaseInfoRespository baseRepository;

    @Autowired
    private ExaminationAssignRespository examAssignRes;

    @Autowired
    private ExamnationPaperRespository basicRespository;
    @Autowired
    private ExamnationPaperDetailRespository paperDetailRespository;

    @Autowired
    private ExHeadingRelaRespository headingRelaRespository;

    @Autowired
    private ExamHeadingRespository headingRespository;

    @Autowired
    private TestPaperRespository testPaperRespository;

    @Autowired
    private TestHeadingRelaRespository testHeadRelaRespository;

    @Autowired
    private BActionPlanRespository actionPlanRespository;

    @Autowired
    private BProblemAnalysisRespository bproblemAnalysisRespository;
    
    @Autowired
	protected StoreRespository storeRepository;
    
    @Autowired
    private JavaMailSenderImpl emailSender;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private IBuserService userService;

    @Autowired
    private CommonDaoCustom<Object[]> common;
    
    private static final String ADDITIONAL_MODEL_NAME = "问题分析及行动";

    public static final List<AdditionalModule> CASH_AUDIT_MODULE_INFO = buildCashAuditModuleInfo();
    public static final List<AdditionalModule> SIDE_BY_SIDE_MODULE_INFO = buildSideBySideModuleInfo();
    private static final String[] PARENT_EXAM = new String[] { "P类", "G类", "报告" };

    private static final Logger logger = LoggerFactory.getLogger(ExamServiceImpl.class);

    public  List<BExaminationPaper>  getAllExamPaper(){
      return       basicRespository.findAll();
    }
    @Override
    public List<SelectionVO> queryExamType() {
        List<SelectionVO> result = new ArrayList<SelectionVO>();

        StringBuilder sql = new StringBuilder();
        sql.append("	select i.id,i.b_value from (select * from	 ").append("	bk_ex_base_info i	").append("	where i.b_value in ('P类','G类','报告')) t 		").append("	left join bk_ex_base_info i			")
                .append("	on t.id = i.b_pid			");
        List<Object[]> list = common.queryBySql(sql.toString(), new ArrayList<Object>());
        if (!CollectionUtils.isEmpty(list)) {
            for (Object[] objAry : list) {
                result.add(new SelectionVO(DataType.getAsString(objAry[1]), DataType.getAsString(objAry[0])));
            }
        }
        return result;
    }

    @Override
    public List<ExamTypeVO> queryExamType(String storeId) {

        List<ExamTypeVO> result = new ArrayList<ExamTypeVO>();

        StringBuilder sql = new StringBuilder();

        sql.append("    select ppr.e_type, rr.b_value, tpr.t_total,tpr.t_begin,ur.real_name                      ");
        sql.append("      from bk_test_paper tpr                                                     ");
        sql.append(" join bk_user ur on ur.id=tpr.user_id ");
        sql.append("      join bk_examitnation_paper ppr                                             ");
        sql.append("        on tpr.e_id = ppr.id                                                     ");
        sql.append("       and tpr.t_state = '1'                                                     ");
        sql.append("      join (select tt.b_value, MAX(tp.t_begin) as maxTime                        ");
        sql.append("              from bk_test_paper tp                                              ");
        sql.append("              join bk_examitnation_paper pp                                      ");
        sql.append("                on pp.id = tp.e_id                                               ");
        sql.append("              join (select i.id, i.b_value                                       ");
        sql.append("                     from (select *                                              ");
        sql.append("                             from bk_ex_base_info i                              ");
        sql.append("                            where i.b_value in ('P类', 'G类', '报告')) t         ");
        sql.append("                     left join bk_ex_base_info i                                 ");
        sql.append("                       on t.id = i.b_pid) tt                                     ");
        sql.append("                on pp.e_type = tt.id                                             ");
        sql.append("             where 1=1                                           ");
        if (!StringUtils.isEmpty(storeId)) {
            sql.append(" and  tp.store_id = '" + storeId + "'  ");
        }
        sql.append("             group by tt.b_value) rr                                             ");
        sql.append("        on rr.maxTime = tpr.t_begin                                              ");

        List<Object[]> list = common.queryBySql(sql.toString(), new ArrayList<Object>());
        if (!CollectionUtils.isEmpty(list)) {
            for (Object[] objAry : list) {
                result.add(new ExamTypeVO(DataType.getAsString(objAry[0]), DataType.getAsString(objAry[1]), DataType.getAsInt(objAry[2]), DataType.getAsDate(objAry[3]).getTime(), DataType.getAsString(objAry[4])));
            }
        }
        return result;
    }

    @Override
    public Page<Object[]> queryLoseSts(String examType,String storeName,Pageable requestPage) {
        StringBuffer sql = new StringBuffer();
        sql.append("  select tpd.q_id,                                                                                               ");
        sql.append("         t.e_name,                                                                                              ");
        sql.append("         p.serial_number,                                                                                       ");
        sql.append("         p.q_content,                                                                                           ");
        sql.append("         i.b_value,                                                                                             ");
        sql.append("         tt.b_value as qType,                                                                                              ");
        sql.append("         sum(q.q_value - tpd.t_value) as loseTotalScore,                                                        ");
        sql.append("         sum(q.q_value) as oldTotalScore,                                                                       ");
        sql.append("         sum(tpd.t_value) as getTotalScore,                                                                     ");
        /*sql.append("         CAST(sum(q.q_value - tpd.t_value) AS FLOAT) / sum(q.q_value) * 100 as losePercent,                                  ");*/
        sql.append(" tp.e_id as eId,i.id as moduleId ");
        sql.append("    from bk_test_paper tp                                                                                       ");
        sql.append("    join bk_test_paper_detail tpd                                                                               ");
        sql.append("      on tpd.t_id = tp.id                                                                                       ");
        sql.append("    join bk_question q                                                                                          ");
        sql.append("      on q.id = tpd.q_id                                                                                        ");
        sql.append("    join bk_examitnation_paper t                                                                                ");
        sql.append("      on t.id = tp.e_id                                                                                         ");
        sql.append("    join bk_question p                                                                                          ");
        sql.append("      on p.id = tpd.q_id                                                                                        ");
        sql.append("    join bk_ex_base_info i                                                                                      ");
        sql.append("      on i.id = p.q_module                                                                                      ");
        sql.append("    join (select i.id, i.b_value                                                                                ");
        sql.append("                   from (select *                                                                               ");
        sql.append("                           from bk_ex_base_info i                                                               ");
        sql.append("                          where i.b_value in ('P类', 'G类', '报告')) t                                          ");
        sql.append("                   left join bk_ex_base_info i                                                                  ");
        sql.append("                     on t.id = i.b_pid) tt on tt.id=t.e_type                                                    ");
        sql.append(" join bk_shop_info shop on shop.id=tp.store_id ");
        sql.append("   where 1 = 1                                                                                                  ");
        if(!StringUtils.isEmpty(storeName)){
            sql.append(" and shop.shop_name like '%"+storeName+"%'");
        }
        sql.append("     and (q.q_value - tpd.t_value > 0 or q.q_value=0)                                                                        ");
        sql.append(" and tt.b_value not in('肩并肩拜访','餐厅拜访报告') ");
        if(org.apache.commons.lang.StringUtils.isNotBlank(examType)){
            sql.append("     and tt.id in('"+examType+"')                                                                                            ");
        }
        sql.append("   group by tt.b_value, tp.e_id, tpd.q_id, t.e_name,p.serial_number, p.q_content, p.q_type,i.id, i.b_value                      ");
        /*sql.append("   order by losePercent desc                                                                                    ");*/
        return  common.queryBySql(sql.toString(), new ArrayList(), requestPage);
    }

    @Override
    public List<SelectionVO> queryFilterExamType() {
        List<SelectionVO> result = new ArrayList<SelectionVO>();

        List<BExBaseInfo> list = baseRepository.findByBrushElection(1);
        if (!CollectionUtils.isEmpty(list)) {
            for (BExBaseInfo bExBaseInfo : list) {
                result.add(new SelectionVO(bExBaseInfo.getBvalue(), bExBaseInfo.getId() + ""));
            }
        }
        return result;
    }

    @Override
    public void saveExamAssign(Integer examId, List<String> userIds) {
        BExaminationAssign entity = null;
        if (null != examId) {
            if (!CollectionUtils.isEmpty(userIds)) {
                for (String userId : userIds) {
                    entity = new BExaminationAssign();
                    entity.setAssigntimer(new Date());
                    entity.setEid(examId);
                    entity.setUserid(DataType.getAsInt(userId));
                    examAssignRes.save(entity);
                }
            }
        }
    }

    @Override
    public void addExamInfo(ExamInfoVO examInfoVO) {
        // 如果是更新操作，先把数据库中的相关记录删除
        Integer examId = examInfoVO.getId();
        if (examId != null) {
            clearExamRelateInfo(examId);
        }

        BExaminationPaper paperBasicInfo = buildExamPaperBasicInfo(examInfoVO);
        BExaminationPaper paper = basicRespository.save(paperBasicInfo);

        List<BExamPaperDetail> paperQuestions = buildExamPaperQuestions(examInfoVO.getQuestionList(), paper);
        paperDetailRespository.save(paperQuestions);

        List<BExHeadingRela> headingRelas = buildHeadingRelas(examInfoVO, paper);
        headingRelaRespository.save(headingRelas);

    }

    /**
     * 删除跟问卷相关的试题，抬头信息
     * @param examId
     */
    private void clearExamRelateInfo(Integer examId) {
        paperDetailRespository.deleteByEid(examId);
        headingRelaRespository.deleteByEid(examId);
    }

    private List<BExHeadingRela> buildHeadingRelas(ExamInfoVO examInfoVO, BExaminationPaper paper) {
        List<BExHeadingRela> result = new ArrayList<BExHeadingRela>();
        String heads = examInfoVO.getHeads();
        if (StringUtils.hasText(heads)) {
            String[] headAry = heads.split(",");
            Integer paperId = paper.getId();

            for (String head : headAry) {
                BExHeadingRela rela = new BExHeadingRela();
                rela.seteId(paperId);
                rela.sethId(Integer.valueOf(head));
                result.add(rela);
            }
        }
        return result;
    }

    @Override
    public PageModel<ExamInfoVO> findExamInfo(Pageable pageable, Integer userId) {

        PageModel<ExamInfoVO> result = new PageModel<ExamInfoVO>();
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("   SELECT DISTINCT t.id,                               ");
        sql.append("          t.create_time,                      ");
        sql.append("          t.e_name,                           ");
        sql.append("          u.b_value,                          ");
        sql.append("          t.e_passvalue,                      ");
        sql.append("          t.e_timer,                          ");
        sql.append("          t.e_total,                           ");
        sql.append("          t.description,                           ");
        sql.append("          t.e_type                           ");
        sql.append("     FROM BK_EXAMITNATION_PAPER t             ");
        sql.append("     left join BK_EX_BASE_INFO u              ");
        sql.append("       on t.e_type = u.id                     ");
       // sql.append("     left join bk_examination_assign asign         ");
       // sql.append("       on asign.e_id = t.id                   ");
        sql.append("    where 1 = 1                               ");
       // if (0 != userId) {
          //  sql.append("      and asign.user_id = ?               ");
          //  params.add(userId);
       // }
        Page<Object[]> page = common.queryBySql(sql.toString(), params, pageable);
        result.setPage(page.getNumber() + 1);
        result.setRows(getExamInfo(page.getContent()));
        result.setPageSize(page.getSize());
        result.setRecords((int) page.getTotalElements());
        return result;
    }

    @Override
    public ExamInfoVO findExamInfo(Integer examId) {
        ExamInfoVO examInfoVO = findExamBasicInfo(examId);
        examInfoVO.setQuestionList(findQuestions(examId));

        return examInfoVO;
    }

    @Override
    public ExamDetailInfoVO findExamDetailInfo(Integer examId) {
        ExamDetailInfoVO detailInfo = new ExamDetailInfoVO();
        ExamInfoVO examInfoVO = findExamBasicInfo(examId);
        detailInfo.setBasicInfo(buildBasicInfo(examInfoVO));
        detailInfo.setHeads(buildHeads(examId));
        detailInfo.setModules(buildModules(examId));
        detailInfo.setAdditionalModules(buildAdditionalModules(examId));

        return detailInfo;
    }

    @Override
    public ExamDetailInfoVO findAfterTestExamDetaiInfo(Integer testPaperId) {

        BTestPaper testPaper = testPaperRespository.findOne(testPaperId);
        ExamDetailInfoVO examDetailInfo = findExamDetailInfo(testPaper.getEid());
        examDetailInfo.getBasicInfo().setLevel(testPaper.getLevel());

        Map<Integer, Object> testHeads = buildTestHeads(testPaperId);
        fillHeadValue(examDetailInfo.getHeads(), testHeads);

        Map<Integer, QuestionTestDetailInfo> questionTestMap = findExamTestDetailInfo(testPaper);
        fillQuestionTestValue(examDetailInfo.getModules(), questionTestMap);

        List<AdditionalModuleTestResult> additionalModuleTestResult = findAdditionalModulesTestResult(testPaper);
        fillAdditionalModuleTestResult(examDetailInfo.getAdditionalModules(), additionalModuleTestResult);

        return examDetailInfo;
    }

    private void fillAdditionalModuleTestResult(List<AdditionalModule> additionalModules, List<AdditionalModuleTestResult> additionalModuleTestResult) {
        if (!CollectionUtils.isEmpty(additionalModules)) {
            Map<String, AdditionalModuleTestResult> map = new HashMap<String, ExamDetailInfoVO.AdditionalModuleTestResult>();
            if (!CollectionUtils.isEmpty(additionalModuleTestResult)) {
                for (AdditionalModuleTestResult additionalModuleTestResult2 : additionalModuleTestResult) {
                    map.put(additionalModuleTestResult2.getName(), additionalModuleTestResult2);
                }
            }
            for (AdditionalModule additionalModule : additionalModules) {
                AdditionalModuleTestResult moduleTestResult = map.get(additionalModule.getName());
                additionalModule.setTestResult(moduleTestResult != null ? moduleTestResult.getTestResult() : null);
            }
        }
    }

    private void fillQuestionTestValue(List<Module> modules, Map<Integer, QuestionTestDetailInfo> questionTestMap) {
        if (CollectionUtils.isEmpty(modules)) {
            return;
        }
        for (Module module : modules) {
            List<Question> questions = module.getQuestions();
            if (!CollectionUtils.isEmpty(questions)) {
                for (Question question : questions) {
                    question.setTestDetailInfo(questionTestMap.get(question.getId()));
                }
            }
        }

    }

    private Object fillHeadValue(List<Field> heads, Map<Integer, Object> testHeads) {
        if (!CollectionUtils.isEmpty(heads)) {
            for (Field field : heads) {
                field.setFieldValue(String.valueOf(testHeads.get(field.getId())));
            }
        }
        return heads;
    }

    @Override
    public double findLastExamScore(Integer shopId, Integer examType) {


      Integer result=   getLastScoreByShopIdAndExamType(examType,shopId);
      if(result!=null){
          return  result.intValue();
      }
        return 0;
    }
    public Integer getLastScoreByShopIdAndExamType(Integer eType,Integer shopId){
        Integer result=null;
        Map map=new HashMap();
        map.put("eType",eType);
        map.put("shopId",shopId);
       String sql="SELECT\n" +
               "\tpaper.t_total\n" +
               "FROM\n" +
               "\tbk_test_paper paper,\n" +
               "\tbk_examitnation_paper type\n" +
               "WHERE\n" +
               "\ttype.e_type=:eType\n" +
               "and \n" +
               "\ttype.id=paper.e_id\n" +
               "AND paper.store_id = :shopId\n" +
               "ORDER BY\n" +
               "\tpaper.t_end DESC\n" +
               "LIMIT 1;\n";
      result=  jdbcTemplate.queryForObject(sql,map,Integer.class);
        return result;
    }
    @Override
    public List<String> findRepeatLostScoreItem(Integer shopId) {
        List<String> result = new ArrayList<String>();
        // 查询现金稽核
        List<BExBaseInfo> list = baseRepository.findByBvalueLike("现金稽核");
        if (CollectionUtils.isEmpty(list)) {
            logger.warn("没有[现金稽核]这个问卷类型");
            return result;
        }
        int examType = list.get(0).getId();
        List<Integer> examIds = getExamIds(examType);
        Integer lastTestPaperId = findLastTestPaperId(shopId, examIds);
        if(lastTestPaperId!=null){
            return findLostItems(lastTestPaperId);
        }
        return new ArrayList();
    }

    private List<String> findLostItems(Integer testPaperId) {
        List<String> lostScoreItems = new ArrayList<String>();

        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();

      /*  sql.append("	select q.serial_number,1  from	").append("		(select d.* from bk_test_paper_detail d	").append("		where d.t_id = ?) d 	")
                .append("	join bk_question q on d.q_id = q.id and d.t_value < q.q_value	");*/
        sql.append("   select q.serial_number,1 from bk_test_heading_rela hr                      ");
        sql.append("   join bk_question q on q.id=hr.q_id and hr.value='1'                        ");
        sql.append("   join bk_exam_heading eh on eh.id=hr.h_id and eh.h_value='N'                ");
        sql.append("   join bk_ex_base_info bi on bi.id=q.q_module                                ");
        sql.append("   join bk_test_paper tp on tp.id=hr.t_id                                     ");
        sql.append("   where 1=1                                                                  ");
        sql.append("   and q.id in(select q.id                                                    ");
        sql.append("     from (select d.* from bk_test_paper_detail d where d.t_id = ?) d       ");
        sql.append("     join bk_question q    on d.q_id = q.id                                                   ");
        sql.append("     join bk_ex_base_info bi                                                  ");
        sql.append("       on bi.id = q.q_module  and bi.is_key='1'               ");
        sql.append("      and ( q.q_value = 0)                                                    ");
        sql.append("   )                                                                          ");
        sql.append("   and tp.id=?                                                            ");
        sql.append("   union all                                                                  ");
        sql.append("   select q.serial_number, 1                                                  ");
        sql.append("     from (select d.* from bk_test_paper_detail d where d.t_id = ?) d       ");
        sql.append("     join bk_question q                                                       ");
        sql.append("       on d.q_id = q.id                                                       ");
        sql.append("      and d.t_value < q.q_value                                               ");

        params.add(testPaperId);
        params.add(testPaperId);
        params.add(testPaperId);

        List<Object[]> page = common.queryBySql(sql.toString(), params);

        if (!CollectionUtils.isEmpty(page)) {
            for (Object[] objAry : page) {
                lostScoreItems.add(DataType.getAsString(objAry[0]));
            }
        }

        return lostScoreItems;
    }

    private Integer findLastTestPaperId(Integer shopId, List<Integer> examIds) {
        Sort sort = new Sort(Direction.DESC, "tend");
        Pageable pageable = new PageRequest(0, 1, sort);
        List<BTestPaper> testPaper = testPaperRespository.findByStoreidAndEidIn(shopId, examIds, pageable);

        return CollectionUtils.isEmpty(testPaper) ? null : testPaper.get(0).getId();
    }

    private List<String> getRepeatNum(List<String> list) {
        List<String> result = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            for (String num : list) {
                Integer count = map.get(num);
                map.put(num, count == null ? 1 : map.get(num) + 1);
            }

            for (java.util.Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue() > 1) {
                    result.add(entry.getKey());
                }
            }
        }
        return result;
    }

    private List<Integer> getTestPaperIds(Integer shopId) {
        List<Integer> result = new ArrayList<Integer>();

        List<BTestPaper> testPaperList = testPaperRespository.findByStoreid(shopId, null);
        if (!CollectionUtils.isEmpty(testPaperList)) {
            for (BTestPaper bTestPaper : testPaperList) {
                result.add(bTestPaper.getId());
            }
        }
        return result;
    }

    private void addInCondition(Integer[] examType, StringBuffer sql) {
        int size = examType.length;
        for (int i = 0; i < size; i++) {
            sql.append(examType[i]);
            if (i < size - 1) {
                sql.append(",");
            }
        }
    }

    /**
     * 获取上一次考核时间
     * @param shopId
     * @return
     */
    private Date getLastExamDate(Integer shopId) {
        Sort sort = new Sort(Direction.DESC, "tend");
        Pageable pageable = new PageRequest(1, 1, sort);
        List<BTestPaper> testPaper = testPaperRespository.findByStoreid(shopId, pageable);
        if (!CollectionUtils.isEmpty(testPaper)) {
            return testPaper.get(0).getTend();
        }
        return null;
    }

    private List<Integer> getExamIds(Integer examType) {
        List<Integer> examIds = new ArrayList<Integer>();
        List<BExaminationPaper> examPaperList = basicRespository.findByEtype(examType);
        if (!CollectionUtils.isEmpty(examPaperList)) {
            for (BExaminationPaper bExaminationPaper : examPaperList) {
                examIds.add(bExaminationPaper.getId());
            }
        }
        return examIds;
    }

    private List<AdditionalModuleTestResult> findAdditionalModulesTestResult(BTestPaper testPaper) {
        List<AdditionalModuleTestResult> result = new ArrayList<ExamDetailInfoVO.AdditionalModuleTestResult>();

        int testPaperId = testPaper.getId();
        List<BProblemAnalysis> analysisList = bproblemAnalysisRespository.findByTId(testPaperId);
        List<BActionPlan> actionPlanList = actionPlanRespository.findByTId(testPaperId);

        if (!CollectionUtils.isEmpty(analysisList)) {
            result.add(pickAdditionalTestResultFrmAnalysis(analysisList));
        }
        if (!CollectionUtils.isEmpty(actionPlanList)) {
            result.add(pickAdditionalTestResultFrmActionPlan(actionPlanList));
        }

        return result;
    }

    private AdditionalModuleTestResult pickAdditionalTestResultFrmActionPlan(List<BActionPlan> actionPlanList) {
        AdditionalModuleTestResult result = new AdditionalModuleTestResult();
        result.setName("行动计划");
        if (!CollectionUtils.isEmpty(actionPlanList)) {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for (BActionPlan bActionPlan : actionPlanList) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("tack", bActionPlan.getTack());
                map.put("beginTime", bActionPlan.getBeginTime().getTime());
                map.put("expEndTime", bActionPlan.getExpEndTime().getTime());
                map.put("expResult", bActionPlan.getExpResult());
                map.put("realEndTime", bActionPlan.getRealEndTime().getTime());
                map.put("userName", bActionPlan.getUserName());

                list.add(map);
            }

            result.setTestResult(list);
        }
        return result;
    }

    private AdditionalModuleTestResult pickAdditionalTestResultFrmAnalysis(List<BProblemAnalysis> analysisList) {
        AdditionalModuleTestResult result = new AdditionalModuleTestResult();
        result.setName(ADDITIONAL_MODEL_NAME);

        if (!CollectionUtils.isEmpty(analysisList)) {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for (BProblemAnalysis analysis : analysisList) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("resource", analysis.getResource());
                map.put("resolve", analysis.getResolve());
                map.put("scoreNum", analysis.getScoreNum());

                list.add(map);
            }
            result.setTestResult(list);
        }
        return result;
    }

    private Map<Integer, QuestionTestDetailInfo> findExamTestDetailInfo(BTestPaper testPaper) {

        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  SELECT distinct d.T_VALUE,                       ");
        sql.append("         d.Q_ID,             ");
        sql.append("         r.h_id,       ");
        sql.append("         r.h_value,      ");
        sql.append("         r.value,       ");
        sql.append("         d.q_evidence       ");
        sql.append("         from       ");
        sql.append("    (select d.T_VALUE,d.Q_ID,d.q_evidence          ");
        sql.append("   		 from BK_TEST_PAPER_DETAIL d           ");
        sql.append("    	where d.T_ID = ?) d         ");
        sql.append("    left join          ");
        sql.append("    	(select h.h_value,r.h_id,r.value,r.q_id          ");
        sql.append("    		from          ");
        sql.append("    		(select r.*           ");
        sql.append("    		from BK_TEST_HEADING_RELA r          ");
        sql.append("    		where r.T_ID = ?) r          ");
        sql.append("    		left join BK_EXAM_HEADING h          ");
        sql.append("    		on r.h_id = h.id) r          ");
        sql.append("    on d.q_id = r.q_id            ");
        params.add(testPaper.getId());
        params.add(testPaper.getId());

        List<Object[]> list = common.queryBySql(sql.toString(), params);
        return buildQuestionYesyDetail(list);
    }

    private Map<Integer, QuestionTestDetailInfo> buildQuestionYesyDetail(List<Object[]> list) {
        Map<Integer, QuestionTestDetailInfo> map = new HashMap<Integer, ExamDetailInfoVO.QuestionTestDetailInfo>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Object[] objAry : list) {
                Integer quesId = DataType.getAsInt(objAry[1]);
                QuestionTestDetailInfo quesInfo = map.get(quesId);
                if (quesInfo == null) {
                    quesInfo = new QuestionTestDetailInfo();
                    map.put(quesId, quesInfo);

                    quesInfo.setQuesId(quesId);
                    quesInfo.setScore(DataType.getAsInt(objAry[0]));
                }

                quesInfo.setEvidence(DataType.getAsString(objAry[5]));
                quesInfo.addFieldInfo(new Field(DataType.getAsInt(objAry[2]), DataType.getAsString(objAry[3]), DataType.getAsString(objAry[4])));

            }
        }

        return map;
    }

    private List<Module> buildModules(Integer examId) {
        List<QuestionVO> questions = findQuestions(examId);

        Map<Integer, Module> moduleMap = new HashMap<Integer, ExamDetailInfoVO.Module>();
        if (!CollectionUtils.isEmpty(questions)) {
            for (QuestionVO questionVO : questions) {
                Integer moduleId = questionVO.getQmodule();
                BExBaseInfo baseInfo = baseRepository.findOne(moduleId);

                Module module = moduleMap.get(moduleId);
                if (module == null) {
                    String name = baseInfo == null ? "肩并肩拜访" : baseInfo.getBvalue();
                    String desc = baseInfo == null ? "" : baseInfo.getBcomments();
                    module = new Module(name, desc);
                    module.setModuleId(moduleId);
                    moduleMap.put(moduleId, module);

                    module.setFields(getModuleFields(moduleId));

                }

                module.addQuestion(new ExamDetailInfoVO.Question(questionVO.getId(), questionVO.getQcontent(), questionVO.getQvalue(), questionVO.getQtype(), questionVO.getQuestionType(), questionVO
                        .getIsUploadPic() == 1 ? true : false, questionVO.getSerialNumber()));
            }
        }

        List<Module> moduleList = new ArrayList<ExamDetailInfoVO.Module>(moduleMap.values());
        Collections.sort(moduleList, new Comparator<Module>() {
            @Override
            public int compare(Module m1, Module m2) {
                return m1.getModuleId() - m2.getModuleId();
            }
        });
        return moduleList;
    }

    private Set<Field> getModuleFields(Integer moduleId) {
        Set<Field> fields = new HashSet<Field>();

        StringBuilder sql = new StringBuilder();
        sql.append("	select h.h_value as fieldname,h.id from 	").append("	(select r.h_id,r.value from bk_exam_heading_rela r		").append("	where r.module_id = ? ) r		")
                .append("	join bk_exam_heading h on r.h_id = h.id");

        List<Object> params = new ArrayList<Object>();
        params.add(moduleId);

        List<Object[]> page = common.queryBySql(sql.toString(), params);
        if (!CollectionUtils.isEmpty(page)) {
            for (Object[] objAry : page) {
                fields.add(new Field(DataType.getAsInt(objAry[1]), DataType.getAsString(objAry[0])));
            }
        }

        return fields;
    }

    private List<AdditionalModule> buildAdditionalModules(Integer examId) {
        List<AdditionalModule> modules = new ArrayList<ExamDetailInfoVO.AdditionalModule>();

        BExaminationPaper paper = basicRespository.findOne(examId);
        int examType = paper.getEtype();
        // 判断是否是现金稽核
        if (isRightExamType(examType, "现金稽核")) {
            return CASH_AUDIT_MODULE_INFO;
        } else if (isRightExamType(examType, "肩并肩")) {
            return SIDE_BY_SIDE_MODULE_INFO;
        }

        return modules;
    }

    private boolean isRightExamType(int examType, String typeName) {
        BExBaseInfo baseInfo = baseRepository.findOne(examType);
        List<BExBaseInfo> list = baseRepository.findByBvalueLike(typeName + "%");
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        return list.get(0).getId().intValue() == baseInfo.getId().intValue();
    }

    private Map<Integer, Object> buildTestHeads(Integer testPaper) {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        List<BTestHeadingRela> list = testHeadRelaRespository.findByTid(testPaper);

        if (!CollectionUtils.isEmpty(list)) {
            for (BTestHeadingRela bTestHeadingRela : list) {
                map.put(bTestHeadingRela.gethId(), bTestHeadingRela.getValue());
            }
        }
        return map;
    }

    private List<Field> buildHeads(Integer examId) {
        List<Field> heads = new ArrayList<ExamDetailInfoVO.Field>();

        StringBuilder sql = new StringBuilder();
        sql.append("	select r.value as fieldVal,		").append("			h.h_value as fieldname,  ").append("			h.id, 		").append("			h.h_type 		").append("			from  		")
                .append("	(select r.h_id,r.value from bk_exam_heading_rela r		").append("	where r.e_id = ? ) r		").append("	join bk_exam_heading h on r.h_id = h.id");

        List<Object> params = new ArrayList<Object>();
        params.add(examId);

        List<Object[]> page = common.queryBySql(sql.toString(), params);
        if (!CollectionUtils.isEmpty(page)) {
            for (Object[] objAry : page) {
                Integer type = DataType.getAsInt(objAry[3]);
                String typeDesc = type == null ? "" : HeadType.getName(type);
                heads.add(new Field(DataType.getAsInt(objAry[2]), DataType.getAsString(objAry[1]), DataType.getAsString(objAry[0]), type, typeDesc));
            }
        }

        return heads;
    }

    private BasicInfo buildBasicInfo(ExamInfoVO examInfoVO) {
        BasicInfo basicInfo = new BasicInfo();
        basicInfo.setCreateTime(examInfoVO.getCreateTime());
        basicInfo.setExamName(examInfoVO.getExamName());
        basicInfo.setExamTimer(examInfoVO.getExamTimer());
        basicInfo.setExamType(examInfoVO.getExamType());
        basicInfo.setExamTypeName(examInfoVO.getExamTypeName());
        basicInfo.setPassValue(examInfoVO.getPassValue());
        basicInfo.setTotalValue(examInfoVO.getTotalValue());

        return basicInfo;
    }

    @Override
    public ExamInfoVO findExamBasicInfo(Integer examId) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  SELECT t.id,                       ");
        sql.append("         t.create_time,             ");
        sql.append("         t.e_name,       ");
        sql.append("         u.b_value,       ");
        sql.append("         t.e_passvalue,       ");
        sql.append("         t.e_timer,       ");
        sql.append("         t.e_total,       ");
        sql.append("         t.description,       ");
        sql.append("         t.e_type       ");
        sql.append("    FROM BK_EXAMITNATION_PAPER t  left join BK_EX_BASE_INFO u on  t.e_type = u.id           ");
        sql.append("	where t.id = ?");
        params.add(examId);

        List<Object[]> list = common.queryBySql(sql.toString(), params);
        return getExamInfo(list).get(0);
    }

    @Override
    public void deleteExamPaper(int examId) {
        basicRespository.delete(examId);
    }

    @Override
    public boolean hasAssign(int examId) {
        List<BExaminationAssign> list = examAssignRes.findByEid(examId);
        return !CollectionUtils.isEmpty(list);
    }

    @Override
    public void saveExHeadRel(BExHeadingRela rela) {
        headingRelaRespository.save(rela);
    }

    @Override
    public void saveExHeadRel(List<BExHeadingRela> relaList) {
        headingRelaRespository.save(relaList);
    }

    @Override
    public void deleteExHeadRelByModulerId(Integer modulerId) {
        headingRelaRespository.deleteByModuleId(modulerId);
    }

    @Override
    public List<SelectionVO> findHeadInfo(int type) {
        List<SelectionVO> result = new ArrayList<SelectionVO>();

        List<BExHeading> list = headingRespository.findByType(type);
        if (!CollectionUtils.isEmpty(list)) {
            for (BExHeading bExHeading : list) {
                result.add(new SelectionVO(bExHeading.gethValue(), bExHeading.getId() + ""));
            }
        }
        return result;
    }

    @Override
    public List<Integer> findHeadValues(int examId) {
        List<Integer> result = new ArrayList<Integer>();
        List<BExHeadingRela> list = headingRelaRespository.findByEId(examId);

        if (!CollectionUtils.isEmpty(list)) {
            for (BExHeadingRela bExHeadingRela : list) {
                result.add(bExHeadingRela.gethId());
            }
        }
        return result;
    }

    @Override
    public List<Integer> findModelType(int moduleId) {
        List<Integer> result = new ArrayList<Integer>();
        List<BExHeadingRela> list = headingRelaRespository.findByModuleId(moduleId);

        if (!CollectionUtils.isEmpty(list)) {
            for (BExHeadingRela bExHeadingRela : list) {
                result.add(bExHeadingRela.gethId());
            }
        }
        return result;
    }

    private List<ExamInfoVO> getExamInfo(List<Object[]> content) {
        List<ExamInfoVO> result = new ArrayList<ExamInfoVO>();
        if (!CollectionUtils.isEmpty(content)) {
            for (Object[] objAry : content) {
                ExamInfoVO vo = new ExamInfoVO();
                vo.setId(DataType.getAsInt(objAry[0]));
                vo.setCreateTime(DataType.getAsDate(objAry[1]));
                vo.setExamName(DataType.getAsString(objAry[2]));
                vo.setExamTypeName(DataType.getAsString(objAry[3]));
                vo.setPassValue(DataType.getAsInt(objAry[4]));
                vo.setExamTimer(DataType.getAsInt(objAry[5]));
                vo.setTotalValue(DataType.getAsInt(objAry[6]));
                vo.setDescription(DataType.getAsString(objAry[7]));
                vo.setExamType(DataType.getAsInt(objAry[8]));

                result.add(vo);
            }
        }
        return result;
    }

    @Override
    public List<QuestionVO> findQuestions(Integer examId) {

        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  select q.id,                       ");
        sql.append("         q.q_content,             ");
        sql.append("         i.b_value,       ");
        sql.append("         q.q_type,       ");
        sql.append("         q.q_value,       ");
        sql.append("         q.q_module,       ");
        sql.append("         q.is_upload_pic,       ");
        sql.append("         q.serial_number       ");
        sql.append("        	 from      ");
        sql.append("			(select * from bk_question t 	");
        sql.append("			where t.id in		");
        sql.append("			(select d.q_id from bk_exam_paper_detail d where d.e_id = ?)) q		");
        sql.append("			left join 		");
        sql.append("			bk_ex_base_info i on q.q_module = i.id		");

        params.add(examId);
        List<Object[]> list = common.queryBySql(sql.toString(), params);
        return buildQuestionList(list);
    }

    private List<QuestionVO> buildQuestionList(List<Object[]> list) {
        List<QuestionVO> result = new ArrayList<QuestionVO>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Object[] objAry : list) {
                QuestionVO vo = new QuestionVO();

                vo.setId(DataType.getAsInt(objAry[0]));
                vo.setQcontent(DataType.getAsString(objAry[1]));
                vo.setModelName(DataType.getAsString(objAry[2]));
                vo.setQtype(DataType.getAsInt(objAry[3]));
                vo.setQuestionType(QuestionType.getName(DataType.getAsInt(objAry[3])));
                vo.setQvalue(DataType.getAsInt(objAry[4]));
                vo.setQmodule(DataType.getAsInt(objAry[5]));
                vo.setIsUploadPic(DataType.getAsInt(objAry[6]));
                vo.setSerialNumber(DataType.getAsString(objAry[7]));

                result.add(vo);
            }
        }

        return result;
    }

    private BExaminationPaper buildExamPaperBasicInfo(ExamInfoVO examInfoVO) {
        BExaminationPaper basicInfo = new BExaminationPaper();

        basicInfo.setEname(examInfoVO.getExamName());
        basicInfo.setEpassvalue(examInfoVO.getPassValue());
        basicInfo.setEtimer(examInfoVO.getExamTimer());
        basicInfo.setEtotal(examInfoVO.getTotalValue());
        basicInfo.setEtype(examInfoVO.getExamType());
        basicInfo.setDesc(examInfoVO.getDescription());

        Date curTime = new Date();
        basicInfo.setUpdateTime(curTime);

        // 如果是更新操作，那createtime不做修改
        if (examInfoVO.getId() == null) {
            basicInfo.setCreatetime(curTime);
        } else {
            basicInfo.setId(examInfoVO.getId());
        }

        return basicInfo;
    }

    private List<BExamPaperDetail> buildExamPaperQuestions(List<QuestionVO> questionList, BExaminationPaper paper) {
        List<BExamPaperDetail> questionDetails = new ArrayList<BExamPaperDetail>();

        if (!CollectionUtils.isEmpty(questionList)) {
            int paperId = paper.getId();

            for (QuestionVO questionInfo : questionList) {
                BExamPaperDetail paperDetail = new BExamPaperDetail();
                paperDetail.setEid(paperId);
                paperDetail.setQid(questionInfo.getId());

                questionDetails.add(paperDetail);
            }

        }
        return questionDetails;
    }

    private static List<AdditionalModule> buildCashAuditModuleInfo() {
        List<AdditionalModule> list = new ArrayList<ExamDetailInfoVO.AdditionalModule>();

        AdditionalModule problemAnalyse = new AdditionalModule(ADDITIONAL_MODEL_NAME, "以下由稽核人辅导完成");
        problemAnalyse.addField(new Field(1, "根源"));
        problemAnalyse.addField(new Field(2, "行动方案"));
        problemAnalyse.addField(new Field(3, "对应失分项编号"));
        list.add(problemAnalyse);
        return list;
    }

    @Override
    public void addEaxmToExcel(Integer[] examid, OutputStream out) throws Exception {
        List<ExamSheetVO> page = queryFacility(examid);
        SheetDataVO<ExamSheetVO> sheetData = new ExamExportVO("exam", page);
        ExcelWriter writer = new ExcelWriter<SheetDataVO>(sheetData);
        writer.write(out);

    }

    @Override
    public List<ExamSheetVO> queryFacility(Integer[] examid) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append(" select distinct tp.e_id,  t.e_name,                                                                             ");
        sql.append("       p.q_content,                                                                           ");
        sql.append("       i.b_value,                                                                           ");
        sql.append("       p.q_type,                                                                             ");
        sql.append("       sum(q.q_value - tpd.t_value) as loseTotalScore,                                       ");
        sql.append("       sum(q.q_value) as oldTotalScore,                                                      ");
        sql.append("       sum(tpd.t_value) as getTotalScore,                                                    ");
        sql.append("       ( sum(tpd.t_value) / sum(q.q_value)) * 100 as losePercent     ");
        sql.append("  from bk_test_paper tp                                                                      ");
        sql.append("  join bk_test_paper_detail tpd on tpd.t_id = tp.id                                          ");
        sql.append("  join bk_question q on q.id = tpd.q_id                                                      ");
        sql.append("  join bk_examitnation_paper t on t.id = tp.e_id                                             ");
        sql.append("  join bk_question p on p.id = tpd.q_id                                                      ");
        sql.append("  join bk_ex_base_info i on  i.id=p.q_module                                                      ");
        sql.append(" where 1 = 1                                                                                 ");
        sql.append("   and q.q_value - tpd.t_value > 0        and tp.e_id in (                                    ");
        int size = examid.length;
        for (int i = 0; i < size; i++) {
            sql.append(examid[i]);
            if (i < size - 1) {
                sql.append(",");
            }
        }
        sql.append(" ) group by tp.e_id, tpd.q_id, t.e_name, p.q_content, p.q_type,i.b_value                      ");
        sql.append(" order by losePercent desc                                                                   ");
        // StringBuilder sb = new StringBuilder();
        // for (Integer id : examid) {
        // sb.append(id).append(",");
        // }
        //
        // params.add(sb.substring(0, sb.length() - 1));

        List<Object[]> page = common.queryBySql(sql.toString(), params);

        return getFacilityInfo(page);

    }

    private List<ExamSheetVO> getFacilityInfo(List<Object[]> list) {
        List<ExamSheetVO> result = new ArrayList<ExamSheetVO>();
        if (!CollectionUtils.isEmpty(list)) {
            ExamSheetVO entity = null;
            for (Object[] obj : list) {
                entity = new ExamSheetVO();
                entity.setEid(DataType.getAsString(obj[0]));
                entity.setEname(DataType.getAsString(obj[1]));
                entity.setDescription(DataType.getAsString(obj[2]));
                entity.setQtype(DataType.getAsString(obj[3]));
                entity.setModel(DataType.getAsString(obj[4]));
                entity.setScore(DataType.getAsString(obj[5]));
                entity.setAggregate(DataType.getAsString(obj[6]));
                entity.setPoints(DataType.getAsString(obj[7]));
                entity.setLosing(DataType.getAsString(obj[8]));
                result.add(entity);
            }
        }
        return result;
    }

    private static List<AdditionalModule> buildSideBySideModuleInfo() {
        List<AdditionalModule> list = new ArrayList<ExamDetailInfoVO.AdditionalModule>();
        AdditionalModule module = new AdditionalModule("行动计划", "");
        module.addField("行动项目");
        module.addField("目标日期");
        module.addField("跟进完成");

        list.add(module);
        return list;
    }

    @Override
    public Page<Object[]> queryLoseStsDetail(String id,String daqu,String sheng,String shi,String quxian,
            String userIds,String storeName,Pageable requestPage) {
        StringBuffer sql=new StringBuffer();
        sql.append("  select distinct tpd.id,                            ");
        sql.append("         tp.t_begin,                        ");
        sql.append("         ur.real_name,                      ");
        sql.append("         ur.emp,                            ");
        sql.append("         ur.role,                           ");
        sql.append("         tpd.t_value,                       ");
        sql.append("         tpd.q_evidence,                    ");
        sql.append("         shop.shop_name,                    ");
        sql.append("         shop.shop_num,                     ");
        sql.append("         tpd.t_value as tValue,t.q_value  as qValue,                    ");
        sql.append(" t.q_module,i.is_key   ");
        sql.append("    from bk_test_paper_detail tpd           ");
        sql.append("    join bk_question t                      ");
        sql.append("      on tpd.q_id = t.id                    ");
        sql.append("    join bk_test_paper tp                   ");
        sql.append("      on tp.id = tpd.t_id                   ");
      /*  sql.append("    join bk_test_heading_rela hr            ");
        sql.append("      on hr.t_id = tp.id                    ");
        sql.append("  left  join bk_exam_heading hd                  ");
        sql.append("      on hd.id = hr.h_id   and hd.h_value = '描述问题'                          ");*/
        sql.append("   left join bk_user ur                         ");
        sql.append("      on ur.id = tp.user_id                 ");
        sql.append("    join bk_shop_info shop                  ");
        sql.append("      on shop.id = tp.store_id              ");
        sql.append(" join bk_ex_base_info i on i.id = t.q_module ");
        sql.append("   where 1 = 1                              ");
        if(!StringUtils.isEmpty(id)){
            sql.append("      and t.id = '"+id+"'                      "); 
        }
        if(!StringUtils.isEmpty(userIds)){
            //评测人员ID
            sql.append(" and ur.id in ('"+userIds+"') ");
        }
        if(!StringUtils.isEmpty(storeName)){
            //门店名称
            sql.append(" and shop.shop_name like '%"+storeName+"%' ");
        }
        if(!StringUtils.isEmpty(daqu)&&!"--请选择--".equals(daqu)){
            //大区
            sql.append(" and shop.regional like '%"+daqu+"%' ");
        }
        if(!StringUtils.isEmpty(sheng)&&!"--请选择--".equals(sheng)){
            //省
            sql.append(" and shop.province like '%"+sheng+"%' ");
        }
        if(!StringUtils.isEmpty(shi)&&!"--请选择--".equals(shi)){
            //市
            sql.append(" and shop.prefecture like '%"+shi+"%' ");
        }
        if(!StringUtils.isEmpty(quxian)&&!"--请选择--".equals(quxian)){
            //区县
            sql.append(" and shop.district like '%"+quxian+"%' ");
        }
        sql.append(" and (tpd.t_value<t.q_value or t.q_value='0') ");
       // sql.append("    order by tp.t_begin desc               ");
        return common.queryBySql(sql.toString(), new ArrayList(), requestPage);
    }

    @Override
    public String queryDescByTid(String tid,String isKey) {
        String result="";
        StringBuffer sql=new StringBuffer();
        sql.append("  select DISTINCT hr.value,tpd.id               ");
        sql.append("    from bk_test_paper_detail tpd        ");
        sql.append("    join bk_test_paper tp                ");
        sql.append("      on tp.id = tpd.t_id                ");
        sql.append("    left join bk_test_heading_rela hr    ");
        sql.append("      on hr.t_id = tp.id                 ");
        if("1".equals(isKey)){
            sql.append("  and hr.value='1' ");
        }
        sql.append("    join bk_exam_heading hd              ");
        sql.append("      on hd.id = hr.h_id                 ");
        if("1".equals(isKey)){
            sql.append(" and hd.h_value in('N','未通过') ");
        }else{
            sql.append("     and hd.h_value = '描述问题'         ");
        }
        sql.append("   where 1 = 1                           ");
        if(!StringUtils.isEmpty(tid)){
            sql.append("     and tpd.id = '"+tid+"'                 ");  
        }
        List<Object[]> list=common.queryBySql(sql.toString(), new ArrayList());
        if(!CollectionUtils.isEmpty(list)){
            if(list.size()>0){
                result=DataType.getAsString(list.get(0)[0]);
            }
        }
        return result;
    }

    @Override
    public String queryKeyRela(String moduleId, String qId, String yOrN) {
        StringBuffer sql=new StringBuffer();
        sql.append("    select tp.e_id as eId,                                    ");
        sql.append("           q.id as qId,                                       ");
        sql.append("           q.q_content,                                       ");
        sql.append("           hd.h_value,                                        ");
        sql.append("           count(hd.h_value) as cnt                           ");
        sql.append("      from bk_test_heading_rela trl                           ");
        sql.append("      join bk_test_paper tp                                   ");
        sql.append("        on trl.t_id = tp.id                                   ");
        sql.append("      join bk_test_paper_detail tpd                           ");
        sql.append("        on tp.id = tpd.t_id                                   ");
        sql.append("      join bk_question q                                      ");
        sql.append("        on q.id = trl.q_id                                    ");
        sql.append("       and q.id = tpd.q_id                                    ");
        sql.append("      join bk_exam_heading hd                                 ");
        sql.append("        on hd.id = trl.h_id                                   ");
        sql.append("       and trl.value= '1'                                     ");
        sql.append("      join bk_ex_base_info ba                                 ");
        sql.append("        on ba.id = q.q_module                                 ");
        sql.append("      join bk_shop_info shop                                  ");
        sql.append("        on shop.id = tp.store_id                              ");
        sql.append("     where 1 = 1                                              ");
        if(!StringUtils.isEmpty(moduleId)){
            sql.append("       and ba.id = '"+moduleId+"'                                      ");
        }
        if(!StringUtils.isEmpty(qId)){
            sql.append("       and q.id = '"+qId+"'                                        ");
        }
        if(!StringUtils.isEmpty(yOrN)){
            if("Y".equals(yOrN)){
                sql.append("       and hd.h_value in ('"+yOrN+"','通过')                                ");
            }else{
                sql.append("       and hd.h_value in ('"+yOrN+"','未通过')                                ");
            }
        }
        sql.append("     group by tp.e_id, q.id, q_content, h_value               ");
        List<Object[]> list=common.queryBySql(sql.toString(), new ArrayList());
        if(!CollectionUtils.isEmpty(list)){
            if(list.size()>0){
                return DataType.getAsString(list.get(0)[4]);
            }
        }
        return "0";
    }
    
    @Override
    public void generateExcelAndEmail(String fullServerPath,int testPaperId,String... eamils) throws Exception {
    	BTestPaper testPaper = testPaperRespository.findOne(testPaperId);
    	ExamInfoVO examInfoVO = findExamInfo(testPaper.getEid());
    	
    	ExamTestReport report = ExamTestReportFactory.findExamTestReportByExamType(examInfoVO.getExamTypeName(),this);
    	byte[] byteAry = report.generateReport(fullServerPath,testPaperId);
    	
    	BShopInfo shopInfo = storeRepository.findOne(testPaper.getStoreid());
    	String timeStr = formatDate(testPaper.getTend());

    	Buser user = userService.findById(testPaper.getUserid());
        String fileName = getExcelName(user == null ? "":user.getRealname(),timeStr,shopInfo,examInfoVO.getExamTypeName());
    	String userName = user == null ? "" : (user.getRealname() + "(" + user.getName() + ")");
    	StringBuilder text = new StringBuilder();
    	text.append("餐厅经理：\n")
    		.append("\t你好，附件是")
    		.append(timeStr)
    		.append("由"+userName+"完成的"+examInfoVO.getExamTypeName()+"报告，请查收！")
    		.append("\n\n\n")
    		.append("===============请不要直接回复这个邮件，这是由系统生成的邮件===============");
    	
    	
    	ByteArrayResource resource = new ByteArrayResource(byteAry);
    	new EmailUtils(emailSender).sendEmailWithAttachment(eamils, fileName,text.toString(), fileName + ".xlsx", resource);
    }
    
    private String formatDate(Date tend) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
    	String timeStr = sdf.format(tend);
    	
		return timeStr;
	}

	private String getExcelName(String userName,String timeStr, BShopInfo shopInfo,
			String examTypeName) {
		return userName+" "+ timeStr + shopInfo.getShopname() + examTypeName + "报告";
	}

	public void addEaxmToExcelLose(String examid, OutputStream out) throws Exception {
        List<ExamSheetVO> page = queryLoseStsNoPage(examid);
        SheetDataVO<ExamSheetVO> sheetData = new ExamExportVO("失分率统计", page);
        ExcelWriter writer = new ExcelWriter<SheetDataVO>(sheetData);
        writer.write(out);

    }
    public List<ExamSheetVO> queryLoseStsNoPage(String ids) {
        StringBuffer sql = new StringBuffer();
        sql.append("  select tpd.q_id,                                                                                               ");
        sql.append("         t.e_name,                                                                                              ");
        sql.append("         p.serial_number,                                                                                       ");
        sql.append("         p.q_content,                                                                                           ");
        sql.append("         i.b_value,                                                                                             ");
        sql.append("         tt.b_value as qType,                                                                                              ");
        sql.append("         sum(q.q_value - tpd.t_value) as loseTotalScore,                                                        ");
        sql.append("         sum(q.q_value) as oldTotalScore,                                                                       ");
        sql.append("         sum(tpd.t_value) as getTotalScore,                                                                     ");
        /*sql.append("         CAST(sum(q.q_value - tpd.t_value) AS FLOAT) / sum(q.q_value) * 100 as losePercent,                                  ");*/
        sql.append(" tp.e_id as eId,i.id as moduleId ");
        sql.append("    from bk_test_paper tp                                                                                       ");
        sql.append("    join bk_test_paper_detail tpd                                                                               ");
        sql.append("      on tpd.t_id = tp.id                                                                                       ");
        sql.append("    join bk_question q                                                                                          ");
        sql.append("      on q.id = tpd.q_id                                                                                        ");
        sql.append("    join bk_examitnation_paper t                                                                                ");
        sql.append("      on t.id = tp.e_id                                                                                         ");
        sql.append("    join bk_question p                                                                                          ");
        sql.append("      on p.id = tpd.q_id                                                                                        ");
        sql.append("    join bk_ex_base_info i                                                                                      ");
        sql.append("      on i.id = p.q_module                                                                                      ");
        sql.append("    join (select i.id, i.b_value                                                                                ");
        sql.append("                   from (select *                                                                               ");
        sql.append("                           from bk_ex_base_info i                                                               ");
        sql.append("                          where i.b_value in ('P类', 'G类', '报告')) t                                          ");
        sql.append("                   left join bk_ex_base_info i                                                                  ");
        sql.append("                     on t.id = i.b_pid) tt on tt.id=t.e_type                                                    ");
        sql.append(" join bk_shop_info shop on shop.id=tp.store_id ");
        sql.append("   where 1 = 1                                                                                                  ");
        if(!StringUtils.isEmpty(ids)){
            sql.append(" and tpd.q_id in("+ids+") ");
        }
        sql.append("     and (q.q_value - tpd.t_value > 0 or q.q_value=0)                                                                        ");
        sql.append(" and tt.b_value not in('肩并肩拜访','餐厅拜访报告') ");
        sql.append("   group by tt.b_value, tp.e_id, tpd.q_id, t.e_name,p.serial_number, p.q_content, p.q_type,i.id, i.b_value                      ");
        return  doFormatToSheet(common.queryBySql(sql.toString(), new ArrayList()),"desc");
    }
    public List<ExamSheetVO> doFormatToSheet(List<Object[]> list,String sord){
        List<ExamSheetVO> result=new ArrayList<ExamSheetVO>();
        if(!CollectionUtils.isEmpty(list)){
            ExamSheetVO  vo=null;
            for(Object[] obj:list){
                vo=new ExamSheetVO();
                vo.setId(DataType.getAsString(obj[0]));
                vo.setEname(DataType.getAsString(obj[1]));
                vo.setNum(DataType.getAsString(obj[2]));
                vo.setDescription(DataType.getAsString(obj[3]));
                vo.setModel(DataType.getAsString(obj[4]));
                vo.setQtype(DataType.getAsString(obj[5]));
                vo.setEid(DataType.getAsString(obj[9]));
                String moduleId=DataType.getAsString(obj[10]);
              BExBaseInfo baseInfo= baseInfoRes.findOne(DataType.getAsInt(moduleId));
              if(null!=baseInfo){
                  if(baseInfo.getIsKey()==1){
                      //是关键项
                      //统计得到N次
                      String nCnt=queryKeyRela(moduleId,vo.getId(),"N");
                      //统计得到Y次
                      String yCnt=queryKeyRela(moduleId,vo.getId(),"Y");
                      //统计得总问卷数
                      String total=DataType.getAsInt(nCnt)+DataType.getAsInt(yCnt)+"";
                      //失分率
                      double losing=DataType.getAsDouble(DataType.getAsDouble(nCnt)/DataType.getAsDouble(total))*100;
                      vo.setLosingD(losing);
                      //失分
                      vo.setPoints(nCnt);
                      //总分
                      vo.setAggregate(total);
                      //总得分
                      vo.setScore(yCnt);
                      //失分率
                      vo.setLosing(String.format("%.2f", Double.valueOf(DataType.getAsString(losing)))+"%");
                      
                  }else{
                      //失分
                      vo.setPoints(DataType.getAsString(obj[6]));
                      //总分
                      vo.setAggregate(DataType.getAsString(obj[7]));
                      //总得分
                      vo.setScore(DataType.getAsString(obj[8]));
                      double losing=DataType.getAsDouble(DataType.getAsDouble(vo.getPoints())/DataType.getAsDouble(vo.getAggregate()))*100;
                      vo.setLosingD(losing);
                      //失分率
                      vo.setLosing(String.format("%.2f", Double.valueOf(DataType.getAsString(losing)))+"%");
                  }
                  
              }
              vo.setModuleId(DataType.getAsString(obj[10])); 
                result.add(vo);
            }
        }
        ComparatorExamSts comparator = new ComparatorExamSts(sord);
        Collections.sort(result, comparator);
        return result;
    }

    @Override
    public void addEaxmToExcelLoseDetail(String id, String ids, OutputStream out) throws Exception {
        List<LoseDetailVO> page = queryLoseStsDetail( id, ids);
        SheetDataVO<LoseDetailVO> sheetData = new LoseDetailExportVO("失分项查看", page);
        ExcelWriter writer = new ExcelWriter<SheetDataVO>(sheetData);
        writer.write(out);
    }
    
    public List<LoseDetailVO> queryLoseStsDetail(String id,String ids) {
        StringBuffer sql=new StringBuffer();
        sql.append("  select distinct tpd.id,                            ");
        sql.append("         tp.t_begin,                        ");
        sql.append("         ur.real_name,                      ");
        sql.append("         ur.emp,                            ");
        sql.append("         ur.role,                           ");
        sql.append("         tpd.t_value,                       ");
        sql.append("         tpd.q_evidence,                    ");
        sql.append("         shop.shop_name,                    ");
        sql.append("         shop.shop_num,                     ");
        sql.append("         tpd.t_value as tValue,t.q_value  as qValue,                    ");
        sql.append(" t.q_module,i.is_key   ");
        sql.append("    from bk_test_paper_detail tpd           ");
        sql.append("    join bk_question t                      ");
        sql.append("      on tpd.q_id = t.id                    ");
        sql.append("    join bk_test_paper tp                   ");
        sql.append("      on tp.id = tpd.t_id                   ");
      /*  sql.append("    join bk_test_heading_rela hr            ");
        sql.append("      on hr.t_id = tp.id                    ");
        sql.append("  left  join bk_exam_heading hd                  ");
        sql.append("      on hd.id = hr.h_id   and hd.h_value = '描述问题'                          ");*/
        sql.append("   left join bk_user ur                         ");
        sql.append("      on ur.id = tp.user_id                 ");
        sql.append("    join bk_shop_info shop                  ");
        sql.append("      on shop.id = tp.store_id              ");
        sql.append(" join bk_ex_base_info i on i.id = t.q_module ");
        sql.append("   where 1 = 1                              ");
        if(!StringUtils.isEmpty(ids)){
            sql.append(" and tpd.id in("+ids+") ");
        }
        if(!StringUtils.isEmpty(id)){
            sql.append("      and t.id = '"+id+"'                      "); 
        }
        sql.append(" and (tpd.t_value<t.q_value or t.q_value='0') ");
        return doFormatToLoseDetail(common.queryBySql(sql.toString(), new ArrayList()));
    }
    public List<LoseDetailVO> doFormatToLoseDetail(List<Object[]> list){
        List<LoseDetailVO> result=new ArrayList<LoseDetailVO>();
        if(!CollectionUtils.isEmpty(list)){
            LoseDetailVO  vo=null;
            for(Object[] obj:list){
                vo=new LoseDetailVO();
                //是否关键项
                String isKey=DataType.getAsString(obj[12]);
                vo.setDesc(queryDescByTid(DataType.getAsString(obj[0]),isKey));
                if((!StringUtils.isEmpty(vo.getDesc())&&"1".equals(isKey))||"0".equals(isKey)){
                    vo.setId(DataType.getAsString(obj[0]));
                    vo.setBeginTime(DataType.getAsDate(obj[1]));
                    vo.setRealName(DataType.getAsString(obj[2]));
                    vo.setEmp(DataType.getAsString(obj[3]));
                    vo.setRole(RoleFactory.getRoleName(DataType.getAsString(obj[4])));
                    vo.setValue(DataType.getAsString(obj[5]));
                    vo.setPic(DataType.getAsString(obj[6]));
                    vo.setShopName(DataType.getAsString(obj[7]));
                    vo.setShopNum(DataType.getAsString(obj[8]));
                    vo.setTestValue(DataType.getAsString(obj[9]));
                    vo.setQuesValue(DataType.getAsString(obj[10]));
                    result.add(vo);
                }
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public void delExamResult(String id) {
        //删除问卷抬头
        String deleteHeadingRela="delete from bk_test_heading_rela where t_id='"+id+"'";
        common.upadteBySql(deleteHeadingRela, new ArrayList<>());
        //删除问卷详情
        String deleteTestPaperDetail="delete from bk_test_paper_detail where t_id='"+id+"'";
        common.upadteBySql(deleteTestPaperDetail, new ArrayList<>());
        //删除测评问卷
        testPaperRespository.delete(DataType.getAsInt(id));
    }
    @Override
    public  int completeWorkInfo(Integer taskId,Integer testPaperId) {
        Map<String ,Integer> map=new HashMap();
        map.put("testPaperId",testPaperId);
        map.put("taskId",taskId);
       return  jdbcTemplate.update("update bk_work_info set state=1 , testPaperId=:testPaperId where id=:taskId",map);

    }
}
