package com.upbest.mvc.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.upbest.mvc.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.entity.BShopUser;
import com.upbest.mvc.entity.BWorkType;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.repository.factory.StoreRespository;
import com.upbest.mvc.repository.factory.StoreUserRespository;
import com.upbest.mvc.repository.factory.UserRespository;
import com.upbest.mvc.repository.factory.WorkRespository;
import com.upbest.mvc.vo.BStatistic;
import com.upbest.mvc.vo.BStsVO;
import com.upbest.mvc.vo.BuserVO;
import com.upbest.mvc.vo.StsStoreVO;
import com.upbest.mvc.vo.TaskTypeVO;
import com.upbest.utils.ComparatorSts;
import com.upbest.utils.DataType;

import javax.inject.Inject;

/**
 * 
 * 
 * @ClassName   类  名   称：	StatisticTaskServiceImpl.java
 * @Description 功能描述：	任务定时统计或计划完成率
 * @author      创  建   者：	<A HREF="jhzhao@upbest-china.com">jhzhap</A>	
 * @date        创建日期：	2014年9月24日下午1:55:59
 */
@Service
public class StatisticTaskServiceImpl implements IStatisticTaskService {
    @Autowired
    private DBChooser dbChooser;
    @Autowired
    private CommonDaoCustom<Object[]> common;

    /* @Autowired
     private StatisticRespository stsRes2;*/

    @Autowired
    private UserRespository urRes;

    @Autowired
    private WorkRespository wkRes;

    @Autowired
    private StoreUserRespository suRes;

    @Autowired
    private StoreRespository storeRes;
    @Autowired
    private IBuserService userService;

    @Autowired
    private IWorkService workService;

    // 定时统计事件 或计划完成率
    // 一个事件相当于一次评测问卷，由于评测问卷还未做，先做计划完成率的统计
    public void autoSts() {
        // 首先清除之前统计的旧数据
        deleteSts();
        // 统计完成计划情况
        // 月度
        doStsTaskOperation("0");
        // 季度
        doStsTaskOperation("1");
    }

    public List<Object[]> getMonthList(String userIds, String year, String month, String flag) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  select t.execute_id,                 ");
        sql.append("         YEAR(t.start_time) as yr,     ");
        sql.append("         MONTH(t.start_time) as mth,   ");
        sql.append("         t.is_self_create,             ");
        sql.append("         t.state,                      ");
        sql.append("         count(1) as cnt,              ");
        sql.append("            t.work_type_id             ");
        sql.append("    from bk_work_info t                ");
        sql.append(" right join bk_work_type tp on tp.id=t.work_type_id ");
        // 月度不排除季度
        sql.append("   where 1 = 1 and tp.frequency!='3'           ");
        if ("1".equals(flag)) {
            sql.append(" and tp.type_name!='顾客至上Guest Is King' ");
        }
        if (StringUtils.isNotBlank(year)) {
            if(dbChooser.isSQLServer()){
                sql.append(" and DATEDIFF(yy,t.start_time, ?)=0 ");
                params.add(year);
            }
           else{
                sql.append(" and TIMESTAMPDIFF(year,t.start_time, ?)=0 ");
                params.add(year+"-01-01");
            }

        }
        if (StringUtils.isNotBlank(month)) {
            sql.append(" and MONTH(t.start_time)=? ");
            params.add(month);
        }
        if (StringUtils.isNotBlank(userIds)) {
            if (userIds.length() > 0) {
                sql.append(" and t.execute_id in(" + userIds + ") ");
            }
        }
        sql.append("   group by t.execute_id,              ");
        sql.append("            YEAR(t.start_time),        ");
        sql.append("            MONTH(t.start_time),       ");
        sql.append("            t.is_self_create,          ");
        sql.append("            t.state,                   ");
        sql.append("            t.work_type_id,             ");
        sql.append("            tp.frequency                ");
        sql.append("          order by tp.frequency         ");
        return common.queryBySql(sql.toString(), params);
    }

    public List<Object[]> getYearList(String userIds, String year, String quarter, String flag) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  select t.execute_id,                  ");
        sql.append("         YEAR(t.start_time) as yr,      ");
        sql.append("         t.quarter,                     ");
        sql.append("         t.is_self_create,              ");
        sql.append("         t.state,                       ");
        sql.append("         count(1) as cnt,               ");
        sql.append("         t.work_type_id                 ");
        sql.append("    from bk_work_info t                 ");
        sql.append("  join bk_work_type tp on tp.id=t.work_type_id ");
        sql.append("   where 1 = 1                          ");
        if ("1".equals(flag)) {
            sql.append(" and tp.type_name!='顾客至上Guest Is King' ");
        }
        if (StringUtils.isNotBlank(year)) {
            if(dbChooser.isSQLServer()){
                sql.append(" and DATEDIFF(yy,t.start_time, ?)=0 ");
                params.add(year);
            }
            else{
                sql.append(" and TIMESTAMPDIFF(year,t.start_time, ?)=0 ");
                params.add(year+"-01-01");
            }


        }
        if (StringUtils.isNotBlank(quarter)) {
            sql.append(" and t.quarter=? ");
            params.add(quarter);
        }
        if (StringUtils.isNotBlank(userIds)) {
            if (userIds.length() > 0) {
                sql.append(" and t.execute_id in(" + userIds + ") ");
            }
        }
        sql.append("   group by t.execute_id,               ");
        sql.append("            YEAR(t.start_time),         ");
        sql.append("            t.quarter,                  ");
        sql.append("            t.is_self_create,           ");
        sql.append("            t.state,                    ");
        sql.append("            t.work_type_id,             ");
        sql.append("            tp.frequency                ");
        sql.append("          order by tp.frequency         ");
        return common.queryBySql(sql.toString(), params);
    }

    /**
     * 
     * @Title 		   	函数名称：	doStsTaskOperation
     * @Description   	功能描述：	统计计划完成率
     * @param 		   	参          数：	
     * @return          返  回   值：	void  
     * @throws
     */
    public void doStsTaskOperation(String sord) {
        List<Object[]> list = null;
        if (StringUtils.isNotBlank(sord)) {
            if ("0".equals(sord)) {
                // 月度
                list = getMonthList("", "", "", "");
            } else {
                // 季度
                list = getYearList("", "", "", "");
            }
        }
        if (!CollectionUtils.isEmpty(list)) {
            BStatistic sts = null;
            for (Object[] obj : list) {
                sts = new BStatistic();
                sts.setUserId(DataType.getAsInt(obj[0]));
                sts.setYear(DataType.getAsString(obj[1]));
                if (StringUtils.isNotBlank(sord)) {
                    if ("0".equals(sord)) {
                        // 月度
                        sts.setMonth(DataType.getAsInt(obj[2]));
                    } else {
                        // 季度
                        sts.setQuarter(DataType.getAsString(obj[2]));
                    }
                }
                sts.setIsSelfCreate(DataType.getAsInt(obj[3]));
                sts.setState(DataType.getAsInt(obj[4]));
                sts.setCount(DataType.getAsInt(obj[5]));
                sts.setSord(DataType.getAsInt(sord));
                // 0:事件 1:计划
                sts.setType(1);
                sts.setWorkTypeId(DataType.getAsInt(obj[6]));
                // stsRes.save(sts);
            }
        }
    }

    /**
     * 
     * @Title 		   	函数名称：	doDeleteSts
     * @Description   	功能描述：	清除旧统计数据
     * @param 		   	参          数：	
     * @return          返  回   值：	void  
     * @throws
     */
    @Transactional(readOnly = false)
    public void deleteSts() {
        // stsRes.deleteAll();
    }

    @Override
    public List<BStsVO> queryStsRs(Integer userId, String year, String type, String sord, String month, String quarter) {
        List<BStsVO> result = new ArrayList<BStsVO>();
        // 获取所有OC(OM+或者OM下)
        Buser user = userService.findById(userId);
        String userIds = getUserIds(userId);
        if ("3".equals(user.getRole())) {
            // OM+
            userIds=userService.getOCUserListInOMPlus(userId);
        }
        if (StringUtils.isNotBlank(type)) {
            if ("1".equals(type)) {
                // 计划
                result = doStsTask(userIds, year, sord, month, quarter);
                result = doFormatTask(userIds, result, year, sord, month, quarter);
                ComparatorSts comparator = new ComparatorSts();
                Collections.sort(result, comparator);
            } else if ("0".equals(type)) {
                // 事件
                result = doStsEvent(userId + "", year, sord, month, quarter);
            }

        }
        return result;
    }

    public List<BStsVO> doFormatTask(String userIds, List<BStsVO> list, String year, String sord, String month, String quarter) {
        List<BStsVO> result = new ArrayList<BStsVO>();
        if (StringUtils.isNotBlank(userIds)) {
            String[] usrAry = userIds.split(",");
            for (int i = 0; i < usrAry.length; i++) {
                boolean flag = false;
                if (!CollectionUtils.isEmpty(list)) {
                    for (BStsVO vo : list) {
                        if (vo.getUser().getId() == DataType.getAsInt(usrAry[i])) {
                            flag = true;
                            result.add(vo);
                            break;
                        }
                    }
                }
                if (!flag) {
                    Buser user = userService.findById(DataType.getAsInt(usrAry[i]));
                    BStsVO stsVO = new BStsVO();
                    stsVO.setUser(user);
                    // 获取除其他类型外的所有任务类型
                    List<BWorkType> ls = wkRes.findByTypenameNotOrderBySortNumAsc("其它");
                    List<BStsVO> re = new ArrayList<BStsVO>();
                    Set<BStsVO> set = new HashSet<BStsVO>(result);
                    result = new ArrayList<BStsVO>(set);
                    if (!CollectionUtils.isEmpty(ls)) {
                        for (BWorkType wt : ls) {
                            re.add(getBstsVO(wt, result, DataType.getAsInt(usrAry[i]), 1));
                        }
                    }

                    stsVO.setSelfCreate("0/" + getSelfCreateTotal(re));
                    stsVO.setTotal("0/" + getSelfCreateTotal(re));
                    stsVO.setAssign("0/0");
                    result.add(stsVO);
                }
            }
        }
        return result;
    }

    /**
     * 
     * @Title 		   	函数名称：	getBstsVO
     * @Description   	功能描述：	获取统计VO，如果存在任务类型则返回，如果不存在任务类型则新建个
     * @param 		   	参          数：	
     * @return          返  回   值：	BStsVO  
     * @throws
     */
    public BStsVO getBstsVO(BWorkType workType, List<BStsVO> list, Integer userId, int cnt) {
        BStsVO result = null;
        // 默认不存在
        if (!CollectionUtils.isEmpty(list)) {
            for (BStsVO vo : list) {
                if (vo.getWorkType() != null) {
                    if (vo.getWorkType().getId() == workType.getId()) {
                        result = vo;
                    }
                }
            }
        }
        if (null == result) {
            result = new BStsVO();
            result.setWorkType(workType);
            int finishedCnt0 = 0;
            int total0 = 0;
            int finishedCnt1 = 0;
            int total1 = 0;
            int storeCnt = 1;
            if (1 == workType.getIsToStore()) {
                // 针对门店,得到OC下管理门店数据
                storeCnt = suRes.findCntByUserId(userId);
            }
            // 1:每周 2:月度 3:季度 4:需要时
            if (workType.getFrequency() == 2) {
                // 2:月度,分母为storeCnt*1
                // 自主
                total0 = storeCnt * cnt * 1;
            } else if (workType.getFrequency() == 1) {
                // 1:每周,分母为storeCnt*4
                // 自主
                total0 = storeCnt * cnt * 4;
            } else if (workType.getFrequency() == 3) {
                // 3:季度,月度统计时不统计，只有季度统计时才会统计
                // 自主
                total0 = storeCnt * 1;
            }
            if (workType.getFrequency() == 4) {
                // 需要时
                result.setTotal(DataType.getAsString((finishedCnt0 + finishedCnt1) + "/" + (finishedCnt0 + finishedCnt1)));
                result.setSelfCreate(DataType.getAsString(finishedCnt0 + "/" + finishedCnt0));
                result.setAssign(DataType.getAsString(finishedCnt1 + "/" + finishedCnt1));
            } else {
                result.setTotal(DataType.getAsString((finishedCnt0 + finishedCnt1) + "/" + (total0 + total1)));
                result.setSelfCreate(DataType.getAsString(finishedCnt0 + "/" + total0));
                result.setAssign(DataType.getAsString(finishedCnt1 + "/" + total1));
            }
        }
        return result;
    }

    public List<BStsVO> doStsTask(String userIds, String year, String sord, String month, String quarter) {
        if (StringUtils.isNotBlank(sord)) {
            if ("0".equals(sord)) {
                // 月度
                return doStsMonthTask(userIds, year, month);
            } else if ("1".equals(sord)) {
                // 季度
                return doStsQuarterTask(userIds, year, quarter);
            }
        }
        return null;
    }

    public List<BStsVO> doStsMonthTask(String userIds, String year, String month) {
        List<BStsVO> result = new ArrayList<BStsVO>();
        List<BStatistic> stsList = new ArrayList<BStatistic>();
        List<Object[]> list = getMonthList(userIds, year, month, "1");
        if (!CollectionUtils.isEmpty(list)) {
            BStatistic sts = null;
            for (Object[] obj : list) {
                sts = new BStatistic();
                sts.setUserId(DataType.getAsInt(obj[0]));
                sts.setYear(DataType.getAsString(obj[1]));
                sts.setMonth(DataType.getAsInt(obj[2]));
                sts.setIsSelfCreate(DataType.getAsInt(obj[3]));
                sts.setState(DataType.getAsInt(obj[4]));
                sts.setCount(DataType.getAsInt(obj[5]));
                sts.setSord(DataType.getAsInt(0));
                // 0:事件 1:计划
                sts.setType(1);
                stsList.add(sts);
            }
        }
        List<Integer> voList = new ArrayList<Integer>();
        if (!CollectionUtils.isEmpty(stsList)) {
            BStsVO vo = null;
            for (BStatistic obj : stsList) {
                vo = new BStsVO();
                if (!voList.contains(obj.getUserId())) {
                    vo.setUser(urRes.findOne(obj.getUserId()));
                    vo.setMonth(month);
                    vo.setYear(year);
                    Map<String, Object> map = getMapByUserId(obj.getUserId(), stsList, year, "0", month, "");
                    vo.setAssign(DataType.getAsString(map.get("assign")));
                    vo.setSelfCreate(DataType.getAsString(map.get("selfCreate")));
                    vo.setTotal(DataType.getAsString(map.get("total")));
                    vo.setAssignD(DataType.getAsDouble(map.get("assignD")));
                    vo.setSelfCreateD(DataType.getAsDouble(map.get("selfCreateD")));
                    vo.setTotalD(DataType.getAsDouble(map.get("totalD")));
                    result.add(vo);
                    voList.add(obj.getUserId());
                }
            }
        }
        return result;
    }

    public Map<String, Object> getMapByUserId(Integer userId, List<BStatistic> list, String year, String sord, String month, String quarter) {
        Map<String, Object> map = new HashMap<String, Object>();
        int finishedCnt0 = 0;
        int unfinishedCnt0 = 0;
        int total0 = 0;
        int finishedCnt1 = 0;
        int unfinishedCnt1 = 0;
        int total1 = 0;
        if (!CollectionUtils.isEmpty(list)) {
            for (BStatistic obj : list) {
                if (userId.equals(obj.getUserId())) {
                    if (obj.getIsSelfCreate() == 0) {
                        // 自主
                        if (0 == obj.getState()) {
                            // 未完成
                            unfinishedCnt0 += obj.getCount();
                        } else {
                            // 已完成
                            finishedCnt0 += obj.getCount();
                        }
                    } else {
                        // 委派
                        if (0 == obj.getState()) {
                            // 未完成
                            unfinishedCnt1 += obj.getCount();
                        } else {
                            // 已完成
                            finishedCnt1 += obj.getCount();
                        }
                    }
                }
            }
        }
        total0 = finishedCnt0 + unfinishedCnt0;
        total1 = finishedCnt1 + unfinishedCnt1;

        List<BStsVO> result = new ArrayList<BStsVO>();
        ;
        // 获取除其他类型外的所有任务类型
        List<BWorkType> ls = wkRes.findByTypenameNotOrderBySortNumAsc("其它");
        List<BStsVO> re = new ArrayList<BStsVO>();
        Set<BStsVO> set = new HashSet<BStsVO>(result);
        result = new ArrayList<BStsVO>(set);
        if (!CollectionUtils.isEmpty(ls)) {
            for (BWorkType wt : ls) {
                re.add(getBstsVO(wt, result, DataType.getAsInt(userId), 1));
            }
        }

        // List<BStsVO> stsList = doStsEvent(userId + "", year, sord, month, quarter);
        total0 = getSelfCreateTotal(re);
        map.put("selfCreate", DataType.getAsString(finishedCnt0 + "/" + total0));
        if (total0 > 0) {
            map.put("selfCreateD", DataType.getAsDouble((double) finishedCnt0 / total0));
        } else {
            map.put("selfCreateD", 0);
        }

        map.put("assign", DataType.getAsString(finishedCnt1 + "/" + total1));
        if (total1 > 0) {
            map.put("assignD", DataType.getAsDouble((double) finishedCnt1 / total1));
        } else {
            map.put("assignD", 0);
        }
        map.put("total", DataType.getAsString((finishedCnt0 + finishedCnt1) + "/" + (total0 + total1)));
        if ((total0 + total1) > 0) {
            map.put("totalD", DataType.getAsDouble((double) (finishedCnt0 + finishedCnt1) / (total0 + total1)));
        } else {
            map.put("totalD", 0);
        }
        return map;
    }

    /**
     * 
     * @Title 		   	函数名称：	getSelfCreateTotal
     * @Description   	功能描述：	获取某OC下的所有自主任务
     * @param 		   	参          数：	
     * @return          返  回   值：	int  
     * @throws
     */
    public int getSelfCreateTotal(List<BStsVO> stsList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(stsList)) {
            for (BStsVO vo : stsList) {
                String selfCreate = vo.getSelfCreate();
                if (StringUtils.isNotBlank(selfCreate)) {
                    String[] selfCreateArray = selfCreate.split("/");
                    if (selfCreateArray.length > 1) {
                        if (vo.getWorkType() != null) {
                            if (!vo.getWorkType().getTypename().equals("顾客至上Guest Is King")) {
                                result += DataType.getAsInt(selfCreateArray[1]);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public Map<String, String> getMapByUserIdAndTypeId(List<BStatistic> list, Integer userId, Integer workTypeId, int cnt) {
        Map<String, String> map = new HashMap<String, String>();
        int finishedCnt0 = 0;
        int unfinishedCnt0 = 0;
        int total0 = 0;
        int finishedCnt1 = 0;
        int unfinishedCnt1 = 0;
        int total1 = 0;
        if (!CollectionUtils.isEmpty(list)) {
            for (BStatistic obj : list) {
                if (userId.equals(obj.getUserId()) && workTypeId.equals(obj.getWorkTypeId())) {
                    if (obj.getIsSelfCreate() == 0) {
                        // 自主
                        if (0 == obj.getState()) {
                            // 未完成
                            unfinishedCnt0 += obj.getCount();
                        } else {
                            // 已完成
                            finishedCnt0 += obj.getCount();
                        }
                    } else {
                        // 委派
                        if (0 == obj.getState()) {
                            // 未完成
                            unfinishedCnt1 += obj.getCount();
                        } else {
                            // 已完成
                            finishedCnt1 += obj.getCount();
                        }
                    }
                }
            }
        }

        total0 = finishedCnt0 + unfinishedCnt0;
        total1 = finishedCnt1 + unfinishedCnt1;
        BWorkType workType = wkRes.findOne(workTypeId);
        int storeCnt = 1;
        if (1 == workType.getIsToStore()) {
            // 针对门店,得到OC下管理门店数据
            storeCnt = suRes.findCntByUserId(userId);
        }
        // 1:每周 2:月度 3:季度 4:需要时
        if (workType.getFrequency() == 2) {
            // 2:月度,分母为storeCnt*1
            // 自主
            total0 = storeCnt * cnt * 1;
        } else if (workType.getFrequency() == 1) {
            // 1:每周,分母为storeCnt*4
            // 自主
            total0 = storeCnt * cnt * 4;
        } else if (workType.getFrequency() == 3) {
            // 3:季度,月度统计时不统计，只有季度统计时才会统计
            // 自主
            total0 = storeCnt * 1;
        }
        if (workType.getFrequency() == 4) {
            // 需要时
            map.put("total", DataType.getAsString((finishedCnt0 + finishedCnt1) + "/" + (finishedCnt0 + finishedCnt1)));
            map.put("selfCreate", DataType.getAsString(finishedCnt0 + "/" + finishedCnt0));
            map.put("assign", DataType.getAsString(finishedCnt1 + "/" + finishedCnt1));
        } else {
            map.put("total", DataType.getAsString((finishedCnt0 + finishedCnt1) + "/" + (total0 + total1)));
            map.put("selfCreate", DataType.getAsString(finishedCnt0 + "/" + total0));
            map.put("assign", DataType.getAsString(finishedCnt1 + "/" + total1));
        }
        return map;
    }

    public List<BStsVO> doStsQuarterTask(String userIds, String year, String quarter) {
        List<BStsVO> result = new ArrayList<BStsVO>();
        List<BStatistic> stsList = new ArrayList<BStatistic>();
        List<Object[]> list = getYearList(userIds, year, quarter, "1");
        if (!CollectionUtils.isEmpty(list)) {
            BStatistic sts = null;
            for (Object[] obj : list) {
                sts = new BStatistic();
                sts.setUserId(DataType.getAsInt(obj[0]));
                sts.setYear(DataType.getAsString(obj[1]));
                sts.setQuarter(DataType.getAsString(obj[2]));
                sts.setIsSelfCreate(DataType.getAsInt(obj[3]));
                sts.setState(DataType.getAsInt(obj[4]));
                sts.setCount(DataType.getAsInt(obj[5]));
                sts.setSord(DataType.getAsInt(1));
                // 0:事件 1:计划
                sts.setType(1);
                stsList.add(sts);
            }
        }
        List<Integer> voList = new ArrayList<Integer>();
        if (!CollectionUtils.isEmpty(stsList)) {
            BStsVO vo = null;
            for (BStatistic obj : stsList) {
                vo = new BStsVO();
                if (!voList.contains(obj.getUserId())) {
                    vo.setUser(urRes.findOne(obj.getUserId()));
                    vo.setQuarter(quarter);
                    vo.setYear(year);
                    Map<String, Object> map = getMapByUserId(obj.getUserId(), stsList, year, "1", "", quarter);
                    vo.setAssign(DataType.getAsString(map.get("assign")));
                    vo.setSelfCreate(DataType.getAsString(map.get("selfCreate")));
                    vo.setTotal(DataType.getAsString(map.get("total")));
                    vo.setAssignD(DataType.getAsDouble(map.get("assignD")));
                    vo.setSelfCreateD(DataType.getAsDouble(map.get("selfCreateD")));
                    vo.setTotalD(DataType.getAsDouble(map.get("totalD")));
                    result.add(vo);
                    voList.add(obj.getUserId());
                }
            }
        }
        return result;
    }

    public List<BStsVO> doStsEvent(String userIds, String year, String sord, String month, String quarter) {
        List<BStsVO> result = null;
        // 事件完成率统计
        if (StringUtils.isNotBlank(sord)) {
            if ("0".equals(sord)) {
                // 月度
                result = doStsMonthEvent(userIds, year, month);
                // 获取除其他类型外的所有任务类型
                List<BWorkType> list = wkRes.findByTypenameNotOrderBySortNumAsc("其它");
                List<BStsVO> re = new ArrayList<BStsVO>();
                Set<BStsVO> set = new HashSet<BStsVO>(result);
                result = new ArrayList<BStsVO>(set);
                if (!CollectionUtils.isEmpty(list)) {
                    for (BWorkType wt : list) {
                        re.add(getBstsVO(wt, result, DataType.getAsInt(userIds), 1));
                    }
                }
                result = re;
            } else if ("1".equals(sord)) {
                // 季度
                result = doStsQuarterEvent(userIds, year, quarter);
                // 获取除其他类型外的所有任务类型
                List<BWorkType> list = wkRes.findByTypenameNotOrderBySortNumAsc("其它");
                List<BStsVO> re = new ArrayList<BStsVO>();
                Set<BStsVO> set = new HashSet<BStsVO>(result);
                result = new ArrayList<BStsVO>(set);
                if (!CollectionUtils.isEmpty(list)) {
                    for (BWorkType wt : list) {
                        re.add(getBstsVO(wt, result, DataType.getAsInt(userIds), 3));
                    }
                }
                result = re;
            }
        }
        return result;
    }

    public List<BStsVO> doStsMonthEvent(String userIds, String year, String month) {
        List<BStsVO> result = new ArrayList<BStsVO>();
        List<BStatistic> stsList = new ArrayList<BStatistic>();
        List<Object[]> list = getMonthList(userIds, year, month, "0");
        if (!CollectionUtils.isEmpty(list)) {
            BStatistic sts = null;
            for (Object[] obj : list) {
                sts = new BStatistic();
                sts.setUserId(DataType.getAsInt(obj[0]));
                sts.setYear(DataType.getAsString(obj[1]));
                sts.setMonth(DataType.getAsInt(obj[2]));
                sts.setIsSelfCreate(DataType.getAsInt(obj[3]));
                sts.setState(DataType.getAsInt(obj[4]));
                sts.setCount(DataType.getAsInt(obj[5]));
                sts.setSord(DataType.getAsInt(0));
                // 0:事件 1:计划
                sts.setType(0);
                sts.setWorkTypeId(DataType.getAsInt(obj[6]));
                stsList.add(sts);
            }
        }
        Map<Integer, Integer> wkTypeMap = new HashMap<Integer, Integer>();
        if (!CollectionUtils.isEmpty(stsList)) {
            BStsVO vo = null;
            for (BStatistic obj : stsList) {
                if (null != wkTypeMap.get(obj.getUserId())) {
                    if (!wkTypeMap.get(obj.getUserId()).equals(obj.getWorkTypeId())) {
                        vo = new BStsVO();
                        vo.setUser(urRes.findOne(obj.getUserId()));
                        vo.setMonth(month);
                        vo.setYear(year);
                        vo.setWorkType(wkRes.findOne(obj.getWorkTypeId()));
                        Map<String, String> map = getMapByUserIdAndTypeId(stsList, obj.getUserId(), obj.getWorkTypeId(), 1);
                        vo.setAssign(map.get("assign"));
                        vo.setSelfCreate(map.get("selfCreate"));
                        vo.setTotal(map.get("total"));
                        wkTypeMap.put(obj.getUserId(), obj.getWorkTypeId());
                        result.add(vo);
                    }
                } else {
                    vo = new BStsVO();
                    vo.setUser(urRes.findOne(obj.getUserId()));
                    vo.setMonth(month);
                    vo.setYear(year);
                    vo.setWorkType(wkRes.findOne(obj.getWorkTypeId()));
                    Map<String, String> map = getMapByUserIdAndTypeId(stsList, obj.getUserId(), obj.getWorkTypeId(), 1);
                    vo.setAssign(map.get("assign"));
                    vo.setSelfCreate(map.get("selfCreate"));
                    vo.setTotal(map.get("total"));
                    wkTypeMap.put(obj.getUserId(), obj.getWorkTypeId());
                    result.add(vo);
                }
            }
        }
        return result;
    }

    public List<BStsVO> doStsQuarterEvent(String userIds, String year, String quarter) {
        List<BStsVO> result = new ArrayList<BStsVO>();
        List<BStatistic> stsList = new ArrayList<BStatistic>();
        List<Object[]> list = getYearList(userIds, year, quarter, "0");
        if (!CollectionUtils.isEmpty(list)) {
            BStatistic sts = null;
            for (Object[] obj : list) {
                sts = new BStatistic();
                sts.setUserId(DataType.getAsInt(obj[0]));
                sts.setYear(DataType.getAsString(obj[1]));
                sts.setQuarter(DataType.getAsString(obj[2]));
                sts.setIsSelfCreate(DataType.getAsInt(obj[3]));
                sts.setState(DataType.getAsInt(obj[4]));
                sts.setCount(DataType.getAsInt(obj[5]));
                sts.setSord(DataType.getAsInt(1));
                // 0:事件 1:计划
                sts.setType(0);
                sts.setWorkTypeId(DataType.getAsInt(obj[6]));
                stsList.add(sts);
            }
        }
        Map<Integer, Integer> wkTypeMap = new HashMap<Integer, Integer>();
        if (!CollectionUtils.isEmpty(stsList)) {
            BStsVO vo = null;
            for (BStatistic obj : stsList) {
                if (null != wkTypeMap.get(obj.getUserId())) {
                    if (!wkTypeMap.get(obj.getUserId()).equals(obj.getWorkTypeId())) {
                        vo = new BStsVO();
                        vo.setUser(urRes.findOne(obj.getUserId()));
                        vo.setQuarter(quarter);
                        vo.setYear(year);
                        vo.setWorkType(wkRes.findOne(obj.getWorkTypeId()));
                        Map<String, String> map = getMapByUserIdAndTypeId(stsList, obj.getUserId(), obj.getWorkTypeId(), 3);
                        vo.setAssign(map.get("assign"));
                        vo.setSelfCreate(map.get("selfCreate"));
                        vo.setTotal(map.get("total"));
                        result.add(vo);
                    }
                } else {
                    vo = new BStsVO();
                    vo.setUser(urRes.findOne(obj.getUserId()));
                    vo.setQuarter(quarter);
                    vo.setYear(year);
                    vo.setWorkType(wkRes.findOne(obj.getWorkTypeId()));
                    Map<String, String> map = getMapByUserIdAndTypeId(stsList, obj.getUserId(), obj.getWorkTypeId(), 3);
                    vo.setAssign(map.get("assign"));
                    vo.setSelfCreate(map.get("selfCreate"));
                    vo.setTotal(map.get("total"));
                    result.add(vo);
                }
            }
        }
        return result;
    }

    public String getUserIds(Integer userId) {
        String result = "";
        List<BuserVO> userVO = userService.getTreeUserList(userId);
        if (!CollectionUtils.isEmpty(userVO)) {
            for (BuserVO obj : userVO) {
                if (null != obj) {
                    if (obj.getRole().equals("2")) {
                        result += obj.getId() + ",";
                    }
                }
            }
        }
        if (result.endsWith(",")) {
            result = result.substring(0, result.lastIndexOf(","));
        }
        return result = (result.length() > 0 ? result : "''");
    }

    public List<Object[]> queryStsStoreObj(List<BWorkType> workTypeList, Integer userId, String year, String quarter, String month) {
        StringBuffer sql = new StringBuffer();
        sql.append("   select sh.id as shopId,                                                     ");
        sql.append("          sh.shop_name,                                                        ");
        sql.append("          wi.work_type_id as workTypeId,                                       ");
        sql.append("          typ.type_name as typeName,                                           ");
        sql.append("          count(sh.id) as doneTasks,                                            ");
        sql.append("  sh.shop_num,sh.chinese_name ");
        if (StringUtils.isNotBlank(month)) {
            sql.append(" ,MONTH(wi.start_time) as mnth ");
        }
        sql.append("     from bk_shop_user su                                                      ");
        sql.append("     join bk_shop_info sh                                                      ");
        sql.append("       on sh.id = su.shop_id                                                   ");
        sql.append("     join bk_user ur                                                           ");
        sql.append("       on ur.id = su.user_id                                                   ");
        sql.append("     join bk_work_info wi                                                      ");
        sql.append("       on wi.store_id = sh.id                                                  ");
        sql.append("      and wi.execute_id = ur.id                                                ");
        if(Integer.valueOf(userId)!=0){
            sql.append(" and wi.execute_id='"+userId+"' ");
        }
        sql.append("      and wi.is_self_create = '0'                                              ");
        sql.append("    right join bk_work_type typ                                                ");
        sql.append("       on typ.id = wi.work_type_id                                             ");
        if (StringUtils.isNotBlank(month)) {
            sql.append("      and typ.frequency != '4' and typ.frequency!='3'                      ");
        } else {
            sql.append("      and typ.frequency != '4'                                                 ");
        }
        sql.append("    where 1 = 1                                                                ");
        /* List<BWorkType> workTypeList = null;
         if (StringUtils.isNotBlank(month)) {
             workTypeList = workService.findWorkWithOutNeededAndQuarter(DataType.getAsString(userId));
         } else {
             workTypeList = workService.findWorkWithOutNeeded(DataType.getAsString(userId));
         }*/
        String workTypeIds = getWorkTypeIds(workTypeList);
        sql.append("         and wi.work_type_id in(" + workTypeIds + ")                                            ");
        sql.append("      and wi.quarter = '" + quarter + "'                                              ");
        /* if (StringUtils.isNotBlank(month)) {
             sql.append(" and  MONTH(wi.start_time)='" + month + "' ");
         }*/
        if(dbChooser.isSQLServer()){
            sql.append("      and DATEDIFF(yy, wi.start_time, '" + year + "') = 0  group by                            ");
        }
        else{
            sql.append("      and TIMESTAMPDIFF(year, wi.start_time, '" + year+"-01-01" + "') = 0  group by                            ");
        }

        if (StringUtils.isNotBlank(month)) {
            sql.append(" MONTH(wi.start_time), ");
        }
        sql.append("   sh.id, sh.shop_name,sh.shop_num,sh.chinese_name, wi.work_type_id, typ.type_name               ");
        List<Object[]> list = common.queryBySql(sql.toString(), new ArrayList());
        return list;
    }

    public List<StsStoreVO> doformatToStsStoreVO(List<Object[]> list, List<BWorkType> workTypeList, Integer userId, String year, String quarter, String month) {
        List<StsStoreVO> result = new ArrayList<StsStoreVO>();
        if (!CollectionUtils.isEmpty(list)) {
            StsStoreVO stsVO = null;
            for (Object[] obj : list) {
                if (StringUtils.isNotBlank(month)) {
                    if (DataType.getAsString(obj[7]).equals(month)) {
                        stsVO = new StsStoreVO();
                        stsVO.setShopId(DataType.getAsString(obj[0]));
                        stsVO.setShopName(DataType.getAsString(obj[5]) + "-" + DataType.getAsString(obj[6]));
                        stsVO.setWorkTypeId(DataType.getAsString(obj[2]));
                        stsVO.setWorkTypeName(DataType.getAsString(obj[3]));
                        // 分子(已经安排的任务)
                        stsVO.setDoneTasks(DataType.getAsInt(obj[4]));
                        // 分母
                        if (StringUtils.isNotBlank(month)) {
                            stsVO.setShouldDoTasks(getShouldDoTasksByWorkTypeId(getWorkTypeByList(workTypeList, DataType.getAsInt(obj[2])), 1));
                        } else {
                            stsVO.setShouldDoTasks(getShouldDoTasksByWorkTypeId(getWorkTypeByList(workTypeList, DataType.getAsInt(obj[2])), 3));
                        }
                        result.add(stsVO);
                    }
                } else {
                    stsVO = new StsStoreVO();
                    stsVO.setShopId(DataType.getAsString(obj[0]));
                    stsVO.setShopName(DataType.getAsString(obj[5]) + "-" + DataType.getAsString(obj[6]));
                    stsVO.setWorkTypeId(DataType.getAsString(obj[2]));
                    stsVO.setWorkTypeName(DataType.getAsString(obj[3]));
                    // 分子(已经安排的任务)
                    stsVO.setDoneTasks(DataType.getAsInt(obj[4]));
                    // 分母
                    if (StringUtils.isNotBlank(month)) {
                        stsVO.setShouldDoTasks(getShouldDoTasksByWorkTypeId(getWorkTypeByList(workTypeList, DataType.getAsInt(obj[2])), 1));
                    } else {
                        stsVO.setShouldDoTasks(getShouldDoTasksByWorkTypeId(getWorkTypeByList(workTypeList, DataType.getAsInt(obj[2])), 3));
                    }
                    result.add(stsVO);
                }

            }
        }
        return result;
    }

    @Override
    public List<StsStoreVO> queryStsStore(Integer userId, String year, String quarter, String month) {
        List<BWorkType> workTypeList = null;
        if (StringUtils.isNotBlank(month)) {
            workTypeList = workService.findWorkWithOutNeededAndQuarter(DataType.getAsString(userId));
        } else {
            workTypeList = workService.findWorkWithOutNeeded(DataType.getAsString(userId));
        }
        List<Object[]> list = queryStsStoreObj(workTypeList, userId, year, quarter, month);
        List<StsStoreVO> result = doformatToStsStoreVO(list, workTypeList, userId, year, quarter, month);
        return getListWithAllStores(result, workTypeList, month, userId);
    }

    public List<StsStoreVO> getListWithAllStores(List<StsStoreVO> result, List<BWorkType> workTypeList, String month, Integer userId) {
        result = doFormatWorkTypeList(result, workTypeList, month);
        List<StsStoreVO> re = new ArrayList<StsStoreVO>();
        List<BShopUser> shopUserList = suRes.findByUserId(userId);
        if (!CollectionUtils.isEmpty(shopUserList)) {
            for (BShopUser bshopUser : shopUserList) {
                boolean flag = false;// 默认不存在
                for (StsStoreVO vo : result) {
                    if (vo.getShopId().equals(DataType.getAsString(bshopUser.getShopId()))) {
                        flag = true;
                        re.add(vo);
                        break;
                    }
                }
                if (!flag) {
                    // 不存在，则新建个
                    StsStoreVO entity = new StsStoreVO();
                    List<TaskTypeVO> taskList = new ArrayList<TaskTypeVO>();
                    if (result.size() > 0) {
                        for (TaskTypeVO taskType : result.get(0).getList()) {
                            TaskTypeVO taskType2 = new TaskTypeVO();
                            BeanUtils.copyProperties(taskType, taskType2);
                            taskType2.setDoneTasks(0);
                            taskList.add(taskType2);
                        }
                    } else {
                        for (BWorkType workType : workTypeList) {
                            TaskTypeVO taskTypeVO = new TaskTypeVO();
                            taskTypeVO.setDoneTasks(0);
                            if (StringUtils.isNotBlank(month)) {
                                taskTypeVO.setShouldDoneTasks(getShouldDoTasksByWorkTypeId(workType, 1));
                            } else {
                                taskTypeVO.setShouldDoneTasks(getShouldDoTasksByWorkTypeId(workType, 3));
                            }
                            taskTypeVO.setWorkType(workType);
                            taskList.add(taskTypeVO);
                        }
                    }
                    entity.setList(taskList);
                    entity.setShopId(bshopUser.getShopId() + "");
                    BShopInfo shopInfo = storeRes.findOne(bshopUser.getShopId());
                    if (null != shopInfo) {
                        entity.setShopName(shopInfo.getShopnum() + "-" + shopInfo.getChineseName());
                    }
                    re.add(entity);
                }
            }
        }
        return re;
    }

    public BWorkType getWorkTypeByList(List<BWorkType> workTypeList, Integer workTypeId) {
        if (!CollectionUtils.isEmpty(workTypeList)) {
            for (BWorkType workType : workTypeList) {
                if (workTypeId == workType.getId()) {
                    return workType;
                }
            }
        }
        return null;
    }

    public List<StsStoreVO> doFormatWorkTypeList(List<StsStoreVO> list, List<BWorkType> workTypeList, String month) {
        list = rebuildStsStore(list);
        List<StsStoreVO> result = new ArrayList<StsStoreVO>();
        List<TaskTypeVO> taskTypeList = new ArrayList<TaskTypeVO>();
        if (!CollectionUtils.isEmpty(list)) {
            for (StsStoreVO obj : list) {
                taskTypeList = getTaskTypeVO(workTypeList, obj, month);
                obj.setList(taskTypeList);
                result.add(obj);
            }
        }
        return result;
    }

    public List<TaskTypeVO> getTaskTypeVO(List<BWorkType> workTypeList, StsStoreVO obj, String month) {
        List<TaskTypeVO> taskTypeList = new ArrayList<TaskTypeVO>();
        if (!CollectionUtils.isEmpty(workTypeList)) {
            for (BWorkType workType : workTypeList) {
                boolean isExist = false;
                if (!CollectionUtils.isEmpty(obj.getList())) {
                    for (TaskTypeVO vo : obj.getList()) {
                        if (vo.getWorkType().getId() == workType.getId()) {
                            // 相同
                            isExist = true;
                            taskTypeList.add(vo);
                            break;
                        }
                    }
                    if (!isExist) {
                        // 不存在，则新建个
                        TaskTypeVO typeEntity = new TaskTypeVO();
                        typeEntity.setDoneTasks(0);
                        if (StringUtils.isNotBlank(month)) {
                            typeEntity.setShouldDoneTasks(getShouldDoTasksByWorkTypeId(workType, 1));
                        } else {
                            typeEntity.setShouldDoneTasks(getShouldDoTasksByWorkTypeId(workType, 3));
                        }
                        typeEntity.setWorkType(workType);
                        taskTypeList.add(typeEntity);
                    }
                }
            }
        }
        return taskTypeList;

    }

    public List<StsStoreVO> rebuildStsStore(List<StsStoreVO> list) {
        List<StsStoreVO> result = new ArrayList<StsStoreVO>();
        Map<String, List<TaskTypeVO>> map = new HashMap<String, List<TaskTypeVO>>();
        if (!CollectionUtils.isEmpty(list)) {
            StsStoreVO entity = null;
            for (StsStoreVO vo : list) {
                if (map.get(vo.getShopId()) == null) {
                    entity = new StsStoreVO();
                    entity.setShopId(vo.getShopId());
                    entity.setShopName(vo.getShopName());
                    List<TaskTypeVO> taskTypeVOList = findTaskTypeListByShopId(list, vo.getShopId());
                    map.put(vo.getShopId(), taskTypeVOList);
                    entity.setList(taskTypeVOList);
                    result.add(entity);
                }

            }
        }
        return result;
    }

    public List<TaskTypeVO> findTaskTypeListByShopId(List<StsStoreVO> list, String shopId) {
        List<TaskTypeVO> result = new ArrayList<TaskTypeVO>();
        if (StringUtils.isNotBlank(shopId)) {
            if (!CollectionUtils.isEmpty(list)) {
                for (StsStoreVO vo : list) {
                    if (vo.getShopId().equals(shopId)) {
                        TaskTypeVO typeVo = new TaskTypeVO();
                        typeVo.setDoneTasks(vo.getDoneTasks());
                        typeVo.setShouldDoneTasks(vo.getShouldDoTasks());
                        typeVo.setWorkType(workService.findById(DataType.getAsInt(vo.getWorkTypeId())));
                        result.add(typeVo);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 
     * @Title 		   	函数名称：	getShouldDoTasksByWorkTypeId
     * @Description   	功能描述：	获取分母值，季度查询：cnt=1;月度查询:cnt=3
     * @param 		   	参          数：	
     * @return          返  回   值：	int  
     * @throws
     */
    public int getShouldDoTasksByWorkTypeId(BWorkType workType, int cnt) {
        // BWorkType workType = workService.findById(DataType.getAsInt(workTypeId));
        int total0 = 0;
        int storeCnt = 1;
        // 1:每周 2:月度 3:季度 4:需要时
        if (workType.getFrequency() == 2) {
            // 2:月度,分母为storeCnt*1
            // 自主
            total0 = storeCnt * cnt * 1;
        } else if (workType.getFrequency() == 1) {
            // 1:每周,分母为storeCnt*4
            // 自主
            total0 = storeCnt * cnt * 4;
        } else if (workType.getFrequency() == 3) {
            // 3:季度,月度统计时不统计，只有季度统计时才会统计
            // 自主
            total0 = storeCnt * 1;
        }
        return total0;
    }

    /**
     * 
     * @Title 		   	函数名称：	getWorkTypeIds
     * @Description   	功能描述：	获取某个用户下可执行的所有任务类型
     * @param 		   	参          数：	
     * @return          返  回   值：	String  
     * @throws
     */
    public String getWorkTypeIds(List<BWorkType> workTypeList) {
        String result = "";
        if (!CollectionUtils.isEmpty(workTypeList)) {
            for (BWorkType obj : workTypeList) {
                if (workTypeList.indexOf(obj) == workTypeList.size() - 1) {
                    result += obj.getId();
                } else {
                    result += obj.getId() + ",";
                }
            }
        }
        return result;
    }

}
