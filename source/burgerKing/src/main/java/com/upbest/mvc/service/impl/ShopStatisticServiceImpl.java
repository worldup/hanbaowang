package com.upbest.mvc.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.Column;

import com.upbest.mvc.service.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.entity.BShopStatistic;
import com.upbest.mvc.entity.BShopUser;
import com.upbest.mvc.entity.BTestPaper;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.handler.StoreStatisticHandler;
import com.upbest.mvc.repository.factory.ExamnationPaperRespository;
import com.upbest.mvc.repository.factory.StoreStatisticRespository;
import com.upbest.mvc.repository.factory.StoreUserRespository;
import com.upbest.mvc.vo.ExamTypeScoreVO;
import com.upbest.mvc.vo.ExaminationPaperVO;
import com.upbest.mvc.vo.SelectionVO;
import com.upbest.mvc.vo.ShopAreaVO;
import com.upbest.mvc.vo.ShopStatisticVO;
import com.upbest.utils.DataType;
import com.upbest.utils.ExcelUtils;

@Service
public class ShopStatisticServiceImpl implements IShopStatisticService {
	@Inject
	private DBChooser dbChooser;
	@Inject
	private StoreStatisticRespository repository;

	@Inject
	private StoreUserRespository shopUserRepository;

	@Inject
	private ExamnationPaperRespository examPaperRespository;

	@Inject
	private IExamService examService;

	@Inject
	private IStoreService shopService;

	@Inject
	private IBuserService userService;

	@Autowired
	private StoreStatisticHandler handler;

	@Autowired
	private CommonDaoCustom<Object[]> common;

	@Override
	public Object addShopStatisticFromExcel(Resource resource) throws Exception {
		int failedCount = (int) ExcelUtils.handExcel(1, resource, handler);
		String result = "";
		if (failedCount > 0) {
			result = failedCount + "条记录导入失败,可能是由于门店号不存在";
		}

		return result;
	}

	@Override
	public Page<Object[]> findShopStatistic(Buser buser, String sales,
			String mon, Pageable pageable) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select s.shop_id,");
		sql.append("s.shop_name,");
		sql.append("s.sales,");
		sql.append("s.tc,  ");
		sql.append("s.month  ");
		sql.append("from bk_shop_statistic s ");
		sql.append("  where 1=1  ");
		if (StringUtils.isNotBlank(mon)) {
			Date date = DataType.getAsDate(mon, "yyyy-MM");
			if(dbChooser.isSQLServer()){
				sql.append("and datediff(day,s.month,?)=0");
			}
			else
			{
				sql.append("and datediff(day,s.month,?)=0");
			}
			params.add(date);
		}
		if (StringUtils.isNotBlank(sales)) {
			sql.append("and s.shop_id like ?");
			params.add("%" + sales + "%");
		}
		if (null != buser && !buser.getRole().equals("0")) {
			List<BShopInfo> shopInfos = shopService.queryShopInfoByUserId(buser
					.getId());
			StringBuilder shopNums = new StringBuilder();
			if (!CollectionUtils.isEmpty(shopInfos)) {
				for (BShopInfo shop : shopInfos) {
					shopNums.append(shop.getShopnum()).append(",");
				}
			}
			sql.append(" and s.shop_id in ("
					+ (shopNums.length() == 0 ? "''" : shopNums.substring(0,
							shopNums.length() - 1)) + ")");
		}
		return common.queryBySql(sql.toString(), params, pageable);
	}

	@Override
	public BShopStatistic queryShopStatistic(String shopId) {
		return repository.findByShopId(shopId);
	}

	@Override
	public List<ShopStatisticVO> queryShopStatistic(Integer userId,
			Integer[] examType, ShopAreaVO shopArea, Date startTime,
			Date endTime, String sortField) {
		List<BShopInfo> shopInfoList = queryShopInfo(userId, shopArea);

		if (CollectionUtils.isEmpty(shopInfoList)) {
			return new ArrayList<ShopStatisticVO>();
		}

		List<ShopStatisticVO> basicInfo = queryStatisticInfo(shopInfoList,
				startTime, endTime, sortField);
		Map<String, Map<Integer, ExamTypeScoreVO>> scoreStatisticInfo = queryScoreStatisticInfo(
				shopInfoList, examType, startTime, endTime);

		List<ShopStatisticVO> list = merge(basicInfo, scoreStatisticInfo,
				shopInfoList, startTime);
		sortStatistic(list, sortField);
		doDecoration(list, examType);
		return list;
	}

	private void doDecoration(List<ShopStatisticVO> list, Integer[] examType) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		List<SelectionVO> examTypeInfo = examService.queryExamType();
		if (!CollectionUtils.isEmpty(examTypeInfo)) {
			for (SelectionVO selectionVO : examTypeInfo) {
				map.put(Integer.valueOf(selectionVO.getValue()),
						selectionVO.getName());
			}

			if (!CollectionUtils.isEmpty(list)) {
				for (ShopStatisticVO statisticVO : list) {
					Map<Integer, ExamTypeScoreVO> scoreMap = statisticVO
							.getExamScoreMap();
					if (examType != null) {
						if (scoreMap == null) {
							scoreMap = new HashMap<Integer, ExamTypeScoreVO>();
							statisticVO.setExamScoreMap(scoreMap);
						}
						for (Integer type : examType) {
							if (scoreMap.get(type) == null) {
								ExamTypeScoreVO vo = new ExamTypeScoreVO(type,
										map.get(type));
								scoreMap.put(type, vo);
							}
						}
					}
				}
			}

		}

	}

	private void sortStatistic(List<ShopStatisticVO> list,
			final String sortField) {
		if (sortField.equals("tc") || sortField.equals("sales")
				|| sortField.equals("sales_comp")
				|| sortField.equals("tc_comp")) {
			return;
		}

		Collections.sort(list, new Comparator<ShopStatisticVO>() {
			@Override
			public int compare(ShopStatisticVO vo1, ShopStatisticVO vo2) {
				double s1 = getScore(vo1);
				double s2 = getScore(vo2);
				return s1 - s2 == 0 ? 0 : (s1 - s2 > 0 ? -1 : 1);
			}

			public double getScore(ShopStatisticVO vo1) {
				Map<Integer, ExamTypeScoreVO> map = vo1.getExamScoreMap();
				if (map == null) {
					return 0;
				}
				ExamTypeScoreVO scoreVO = map.get(Integer.valueOf(sortField));
				return scoreVO == null ? 0 : scoreVO.getSocre();
			}
		});
	}

	private List<ShopStatisticVO> merge(List<ShopStatisticVO> basicInfo,
			Map<String, Map<Integer, ExamTypeScoreVO>> scoreStatisticInfo,
			List<BShopInfo> shopInfoList, Date startTime) {
		List<ShopStatisticVO> result = new ArrayList<ShopStatisticVO>();

		Map<String, Integer> shopNumIdMap = getShopNumIdMap(shopInfoList);
		Map<String, ShopStatisticVO> shopNumStatisticMap = new HashMap<String, ShopStatisticVO>();
		if (!CollectionUtils.isEmpty(basicInfo)) {
			for (BShopStatistic info : basicInfo) {
				ShopStatisticVO vo = new ShopStatisticVO();
				BeanUtils.copyProperties(info, vo);
				vo.setExamScoreMap(scoreStatisticInfo.get(vo.getShopId()));
				vo.setId(shopNumIdMap.get(vo.getShopId()));
				
				shopNumStatisticMap.put(vo.getShopId(), vo);
				result.add(vo);
			}
		}
		if (!CollectionUtils.isEmpty(scoreStatisticInfo)) {
			Map<String, BShopInfo> shopNumInfoMap = buildShopNumInfoMap(shopInfoList);
			for (Entry<String, Map<Integer, ExamTypeScoreVO>> entry : scoreStatisticInfo
					.entrySet()) {
				String shopNum = entry.getKey();
				ShopStatisticVO vo = shopNumStatisticMap.get(shopNum);
				if (vo == null) {
					ShopStatisticVO stsVO = new ShopStatisticVO();
					stsVO.setShopName(shopNumInfoMap.get(shopNum).getShopname());
					stsVO.setExamScoreMap(entry.getValue());
					stsVO.setMonth(startTime);
					stsVO.setShopId(shopNum);

					result.add(stsVO);

				}
			}
		}
		return result;
	}

	private Map<String, Integer> getShopNumIdMap(List<BShopInfo> shopInfoList) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		if(!CollectionUtils.isEmpty(shopInfoList)){
			for (BShopInfo bShopInfo : shopInfoList) {
				map.put(bShopInfo.getShopnum(), bShopInfo.getId());
			}
		}
		return map;
	}

	private Map<String, BShopInfo> buildShopNumInfoMap(
			List<BShopInfo> shopInfoList) {
		Map<String, BShopInfo> result = new HashMap<String, BShopInfo>();
		if (!CollectionUtils.isEmpty(shopInfoList)) {
			for (BShopInfo bShopInfo : shopInfoList) {
				result.put(bShopInfo.getShopnum(), bShopInfo);
			}
		}
		return result;
	}

	/**
	 * 查询门店的问卷类型的分值
	 * 
	 * @param shopInfoList
	 * @return
	 * 
	 */
	private Map<String, Map<Integer, ExamTypeScoreVO>> queryScoreStatisticInfo(
			List<BShopInfo> shopInfoList, Integer[] examType, Date startTime,
			Date endTime) {
		Map<String, Map<Integer, ExamTypeScoreVO>> result = new HashMap<String, Map<Integer, ExamTypeScoreVO>>();
		if (ArrayUtils.isEmpty(examType)) {
			return result;
		}

		// 获取门店id-》门店号
		Map<Integer, String> shopIdNumMap = getShopIdNumMap(shopInfoList);
		Set<Integer> userIds = getUserIds(shopInfoList);
		// 获取问卷id->问卷类型的map
		Map<Integer, ExamTypeScoreVO> etypeExamMap = getExamIdTypeMap(examType);
		// 查询用户测评结果信息
		List<BTestPaper> testPaperInfo = queryTestPaperInfo(userIds,
				etypeExamMap.keySet(), startTime, endTime);

		if (!CollectionUtils.isEmpty(testPaperInfo)) {
			// 门店(问卷类型->数量的映射)
			Map<String, Map<Integer, Integer>> shopNum_examTypeNumMap = new HashMap<String, Map<Integer, Integer>>();
			for (BTestPaper bTestPaper : testPaperInfo) {
				String shopNum = shopIdNumMap.get(bTestPaper.getStoreid());
				if (shopNum == null) {
					continue;
				}
				Map<Integer, ExamTypeScoreVO> examScoreInfo = result
						.get(shopNum);
				Map<Integer, Integer> examTypeNumMap = shopNum_examTypeNumMap
						.get(shopNum);
				if (examScoreInfo == null) {
					examScoreInfo = new HashMap<Integer, ExamTypeScoreVO>();
					result.put(shopNum, examScoreInfo);
				}
				if (examTypeNumMap == null) {
					examTypeNumMap = new HashMap<Integer, Integer>();
					shopNum_examTypeNumMap.put(shopNum, examTypeNumMap);
				}
				int eType = etypeExamMap.get(bTestPaper.getEid()).getExamType();
				ExamTypeScoreVO etypeScore = examScoreInfo.get(eType);
				if (etypeScore == null) {
					etypeScore = new ExamTypeScoreVO();
					BeanUtils.copyProperties(
							etypeExamMap.get(bTestPaper.getEid()), etypeScore);
					examScoreInfo.put(eType, etypeScore);

				}
				etypeScore.setSocre(etypeScore.getSocre()
						+ bTestPaper.getTtotal());

				examTypeNumMap.put(eType, examTypeNumMap.get(eType) == null ? 1
						: examTypeNumMap.get(eType) + 1);
			}

			// 计算平均值
			for (java.util.Map.Entry<String, Map<Integer, ExamTypeScoreVO>> entry : result
					.entrySet()) {
				Map<Integer, ExamTypeScoreVO> scoreInfo = entry.getValue();
				Map<Integer, Integer> examTypeNumMap = shopNum_examTypeNumMap
						.get(entry.getKey());
				for (java.util.Map.Entry<Integer, ExamTypeScoreVO> bTestPaper : scoreInfo
						.entrySet()) {
					Integer examType1 = bTestPaper.getKey();
					ExamTypeScoreVO vo = bTestPaper.getValue();
					vo.setSocre(vo.getSocre() / examTypeNumMap.get(examType1));
				}
			}

		}
		return result;
	}

	private Map<Integer, String> getShopIdNumMap(List<BShopInfo> shopInfoList) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		if (!CollectionUtils.isEmpty(shopInfoList)) {
			for (BShopInfo bShopInfo : shopInfoList) {
				map.put(bShopInfo.getId(), bShopInfo.getShopnum());
			}
		}
		return map;
	}

	private Set<Integer> getUserIds(List<BShopInfo> shopInfoList) {
		Set<Integer> userIds = new HashSet<Integer>();
		if (!CollectionUtils.isEmpty(shopInfoList)) {
			Set<Integer> shopIds = new HashSet<Integer>();
			for (BShopInfo shopInfo : shopInfoList) {
				shopIds.add(shopInfo.getId());
			}
			List<BShopUser> shopUserList = shopUserRepository
					.findByShopIdIn(shopIds);
			if (!CollectionUtils.isEmpty(shopUserList)) {
				for (BShopUser bShopUser : shopUserList) {
					userIds.add(bShopUser.getUserId());
				}
			}
		}
		return userIds;
	}

	private List<BTestPaper> queryTestPaperInfo(Set<Integer> userIds,
			Set<Integer> examIds, Date startTime, Date endTime) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("	select DISTINCT p.e_id,p.user_id,p.t_total,p.store_id 	");
		sql.append("			from BK_TEST_PAPER p 	");
		sql.append("			where 1=1 	");
		if (!CollectionUtils.isEmpty(examIds)) {
			sql.append(" and p.e_id in ( ");
			addInCondition(examIds.toArray(new Integer[examIds.size()]), sql);
			sql.append("	)	");
		}
		sql.append("			and p.user_id in (   ");
		sql.append("				select p1.user_id ");
		sql.append("					from BK_TEST_PAPER p1 ");
		sql.append("				where 1=1  ");
		if (!CollectionUtils.isEmpty(userIds)) {
			sql.append(" and p1.user_id in (  ");
			addInCondition(userIds.toArray(new Integer[userIds.size()]), sql);
			sql.append("	)	");
		}
		sql.append("	)	");

		if (startTime != null) {
			if(dbChooser.isSQLServer()){
				sql.append(" and DATEDIFF(day,?,p.t_end) >= 0 ");
			}
			else{
				sql.append(" and TIMESTAMPDIFF(day,?,p.t_end) >= 0 ");
			}
			params.add(startTime);
		}
		if (endTime != null) {
			if(dbChooser.isSQLServer()){
				sql.append(" and DATEDIFF(day,?,p.t_end) < 0 ");
			}
			else{
				sql.append(" and TIMESTAMPDIFF(day,?,p.t_end) < 0 ");
			}

			params.add(endTime);
		}

		List<Object[]> list = common.queryBySql(sql.toString(), params);
		List<BTestPaper> result = new ArrayList<BTestPaper>();
		if (!CollectionUtils.isEmpty(list)) {
			for (Object[] objAry : list) {
				BTestPaper paper = new BTestPaper();
				paper.setEid(DataType.getAsInt(objAry[0]));
				paper.setUserid(DataType.getAsInt(objAry[1]));
				paper.setTtotal(DataType.getAsInt(objAry[2]));
				paper.setStoreid(DataType.getAsInt(objAry[3]));

				result.add(paper);
			}
		}
		return result;
	}

	private Map<Integer, ExamTypeScoreVO> getExamIdTypeMap(Integer[] examType) {
		Map<Integer, ExamTypeScoreVO> map = new HashMap<Integer, ExamTypeScoreVO>();

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("	select  p.id, 	");
		sql.append("			i.b_value,	");
		sql.append("			p.e_type	");
		sql.append("			from (select s.id,s.e_type	");
		sql.append("				from BK_EXAMITNATION_PAPER s   ");
		sql.append("				where s.e_type in ( ");
		addInCondition(examType, sql);
		sql.append("	)) p	left join BK_EX_BASE_INFO i on p.e_type = i.id  ");

		List<Object[]> list = common.queryBySql(sql.toString(), params);
		List<ExaminationPaperVO> paperInfo = new ArrayList<ExaminationPaperVO>();
		for (Object[] objAry : list) {
			ExaminationPaperVO vo = new ExaminationPaperVO();
			vo.setId(DataType.getAsInt(objAry[0]));
			vo.setExamTypeName(DataType.getAsString(objAry[1]));
			vo.setEtype(DataType.getAsInt(objAry[2]));

			paperInfo.add(vo);
		}

		if (!CollectionUtils.isEmpty(paperInfo)) {
			for (ExaminationPaperVO paper : paperInfo) {
				map.put(paper.getId(), new ExamTypeScoreVO(paper.getEtype(),
						paper.getExamTypeName()));
			}
		}
		return map;
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
	 * 获取用户门店的关系映射
	 * 
	 * @param shopInfoList
	 * @return
	 */
	private Map<Integer, Set<String>> getUserShopMap(
			List<BShopInfo> shopInfoList) {
		Map<Integer, Set<String>> shopUserMap = new HashMap<Integer, Set<String>>();
		if (!CollectionUtils.isEmpty(shopInfoList)) {
			Collection<Integer> shopIds = new ArrayList<Integer>();
			Map<Integer, String> shopIdNumMap = new HashMap<Integer, String>();
			for (BShopInfo bShopInfo : shopInfoList) {
				shopIds.add(bShopInfo.getId());
				shopIdNumMap.put(bShopInfo.getId(), bShopInfo.getShopnum());
			}
			List<BShopUser> shopUserList = shopUserRepository
					.findByShopIdIn(shopIds);
			if (!CollectionUtils.isEmpty(shopUserList)) {
				for (BShopUser bShopUser : shopUserList) {
					Integer userId = bShopUser.getUserId();
					Set<String> shopNums = shopUserMap.get(userId);
					if (shopNums == null) {
						shopNums = new HashSet<String>();
						shopUserMap.put(userId, shopNums);
					}
					shopNums.add(shopIdNumMap.get(bShopUser.getShopId()));
				}
			}
		}

		return shopUserMap;
	}

	private List<ShopStatisticVO> queryStatisticInfo(
			List<BShopInfo> shopInfoList, Date startTime, Date endTime,
			String sortField) {

		if (CollectionUtils.isEmpty(shopInfoList)) {
			return new ArrayList<ShopStatisticVO>();
		}

		List<String> shopNums = new ArrayList<String>();
		for (BShopInfo bShopInfo : shopInfoList) {
			shopNums.add(bShopInfo.getShopnum());
		}

		StringBuilder sql = new StringBuilder();
		sql.append("	select sv.id,")
			.append("			sv.sales,")
			.append("			sv.shop_id,")
			.append("			sv.shop_name,")
			.append("			sv.tc,")
			.append("			sv.month,")
			.append("			sv.tc_comp_tc  as tc_comp,")
			.append("			sv.comp as sales_comp")
			.append(" 	from (	");
		sql.append("	select s.*,v.tc_comp_tc,v.comp 		")
			.append("		from (		");
		sql.append(
				"	select s.* from bk_shop_statistic s	").append(" where 1=1 ");

		List<Object> params = new ArrayList<Object>();
		if (startTime != null) {
			if(dbChooser.isSQLServer()){
				sql.append(" and DATEDIFF(day,?,s.month) >= 0 ");
			}
			else{
				sql.append(" and TIMESTAMPDIFF(day,?,s.month) >= 0 ");
			}
			params.add(startTime);
		}
		if (endTime != null) {
			if(dbChooser.isSQLServer()){
				sql.append(" and DATEDIFF(day,?,s.month) < 0 ");
			}
			else{
				sql.append(" and TIMESTAMPDIFF(day,?,s.month) < 0 ");
			}

			params.add(endTime);
		}
		if (!CollectionUtils.isEmpty(shopNums)) {
			sql.append("	and s.shop_id in (	");
			int size = shopNums.size();
			for (int i = 0; i < size; i++) {
				sql.append(shopNums.get(i));
				if (i < size - 1) {
					sql.append(",");
				}
			}
			sql.append("	)	");
		}
		sql.append("	) s 	")
			.append("	left join V_TF_Daily_Sales_MONTH_LAST_DAY v		")
			.append("	on s.shop_id = v.storeid and datediff(m,v.salesdate,s.month)=0	");
		
		sql.append("	)sv	");
		
		if (org.springframework.util.StringUtils.hasText(sortField)) {
			String sf = null;
			if((sortField.equals("sales") || sortField.equals("tc"))){
				sf = "sv." + sortField;
			}else if(sortField.equals("tc_comp")){
				sf = "sv.tc_comp_tc";
			}else if(sortField.equals("sales_comp")){
				sf = "sv.comp";
			}
			
			if(sf != null){
				sql.append("  order by " + sf + "	desc");
			}
		}
		List<Object[]> list = common.queryBySql(sql.toString(), params);

		return buildShopStatisticList(list);
	}

	private List<ShopStatisticVO> buildShopStatisticList(List<Object[]> list) {
		List<ShopStatisticVO> result = new ArrayList<ShopStatisticVO>();
		if (!CollectionUtils.isEmpty(list)) {
			for (Object[] objAry : list) {
				ShopStatisticVO ss = new ShopStatisticVO();
				ss.setId(DataType.getAsInt(objAry[0]));
				ss.setSales(DataType.getAsDouble(objAry[1]));
				ss.setShopId(DataType.getAsString(objAry[2]));
				ss.setShopName(DataType.getAsString(objAry[3]));
				ss.setTc(DataType.getAsLong(objAry[4]));
				ss.setMonth(DataType.getAsDate(objAry[5]));
				
				Double tcComp = DataType.getAsDouble(objAry[6]);
                Double tcVal = scaleDouble(tcComp, 2);
                ss.setTcComp(tcVal == null ? null : (tcVal.doubleValue() + ""));

                Double salesComp = DataType.getAsDouble(objAry[7]);
                Double d = scaleDouble(salesComp, 2);
                ss.setSalesComp(d == null ? null : (d.doubleValue() + ""));

				result.add(ss);
			}
		}
		return result;
	}

	private Double scaleDouble(Double val, int i) {
		if (val == null) {
            return null;
        }
        BigDecimal d = new BigDecimal(val);
        d = d.setScale(2, BigDecimal.ROUND_FLOOR);

        return d.doubleValue();
	}

	private List<BShopInfo> queryShopInfo(Integer userId, ShopAreaVO shopArea) {
		List<BShopInfo> result = new ArrayList<BShopInfo>();

		List<BShopInfo> childShopList = shopService
				.queryShopInfoByUserId(userId);
		if (CollectionUtils.isEmpty(childShopList)) {
			return result;
		}

		List<Integer> shopIds = new ArrayList<Integer>();
		for (BShopInfo shopInfo : childShopList) {
			shopIds.add(shopInfo.getId());
		}

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("	select s.id,		").append("		s.shop_name,	")
				.append("		s.shop_num		").append("	from BK_SHOP_INFO s ")
				.append("	where 1=1 ");
		if (shopArea != null) {
			addCondition(shopArea.getCity(), "s.prefecture", params, sql);
			addCondition(shopArea.getDistrict(), "s.district", params, sql);
			addCondition(shopArea.getProvince(), "s.province", params, sql);
			addCondition(shopArea.getRegional(), "s.regional", params, sql);
			addCondition(shopArea.getShopNum(), "s.shop_num", params, sql);
		}
		sql.append(" and s.id in ( ");
		addInCondition(shopIds.toArray(new Integer[shopIds.size()]), sql);
		sql.append("	)	");

		List<Object[]> list = common.queryBySql(sql.toString(), params);
		if (!CollectionUtils.isEmpty(list)) {
			for (Object[] objAry : list) {
				BShopInfo shopInfo = new BShopInfo();
				shopInfo.setId(DataType.getAsInt(objAry[0]));
				shopInfo.setShopname(DataType.getAsString(objAry[1]));
				shopInfo.setShopnum(DataType.getAsString(objAry[2]));

				result.add(shopInfo);
			}
		}

		return result;
	}

	private void addCondition(String value, String field, List<Object> params,
			StringBuffer sql) {
		if (org.springframework.util.StringUtils.hasText(value)) {
			sql.append("	and " + field + "	like ?	");
			params.add("%" + value + "%");
		}
	}

	private List<BShopStatistic> buildList(List<Object[]> page,
			String[] examType) {
		List<BShopStatistic> result = new ArrayList<BShopStatistic>();

		for (Object[] objAry : page) {
			BShopStatistic statistic = new BShopStatistic();
			statistic.setShopId(DataType.getAsString(objAry[0]));
			statistic.setShopName(DataType.getAsString(objAry[1]));
			statistic.setSales(DataType.getAsDouble(objAry[2]));
			statistic.setTc(DataType.getAsLong(objAry[3]));

			result.add(statistic);
		}
		return result;
	}

	@Override
	public List<ShopStatisticVO> queryShopStatistic(Integer shopId, String year) {
		List<ShopStatisticVO> result = new ArrayList<ShopStatisticVO>();
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("   select t.id, t.shop_id, t.sales, t.tc ,t.month,"
				+ "			t.GT_NPS,t.RANK,t.REV_BS,t.REV_FS,t.CASH_AUDIT           ");
		sql.append("     from bk_shop_statistic t                       ");
		sql.append(" where 1=1 ");
		if (null != shopId) {
			sql.append("    and t.shop_id = ?                       ");
			params.add(shopId);
		}
		if (!org.apache.commons.lang.StringUtils.isBlank(year)) {
			if(dbChooser.isSQLServer()){
				sql.append("      and DATEDIFF(yy, t.month, ?) = 0    ");
				params.add(year);
			}
			else{
				sql.append("      and TIMESTAMPDIFF(year, t.month, ?) = 0    ");
				params.add(year+"-01-01");
			}

		}
		List<Object[]> list = common.queryBySql(sql.toString(), params);
		for (Object[] objAry : list) {
			ShopStatisticVO statistic = new ShopStatisticVO();
			statistic.setShopId(DataType.getAsString(objAry[1]));
			DecimalFormat df=new DecimalFormat("#.00");
			double d=DataType.getAsDouble(objAry[2]);
			String st=df.format(d);
			statistic.setSales(DataType.getAsDouble(st));
			statistic.setTc(DataType.getAsLong(objAry[3]));
			statistic.setMonth(DataType.getAsDate(objAry[4], "yyyy-MM-dd"));
			statistic.setGtNps(DataType.getAsString(objAry[5]));
			statistic.setRank(DataType.getAsString(objAry[6]));
			statistic.setRevBs(DataType.getAsString(objAry[7]));
			statistic.setRevFs(DataType.getAsString(objAry[8]));
			statistic.setCashAudit(DataType.getAsString(objAry[9]));
			result.add(statistic);
		}
		List<ShopStatisticVO> re = new ArrayList<ShopStatisticVO>();
		for (int i = 1; i <= 12; i++) {
			boolean flag = false;
			for (ShopStatisticVO vo : result) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(vo.getMonth());
				int month = calendar.get(Calendar.MONTH) + 1;
				if (month == i) {
					flag = true;
					re.add(vo);
					break;
				}
			}
			if (!flag) {
				// 不存在
				ShopStatisticVO entity = new ShopStatisticVO();
				entity.setSales(0.00);
				entity.setTc(0L);
				entity.setShopId(DataType.getAsString(shopId));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					if (i < 10) {
						date = sdf.parse(year + "-0" + i + "-01");
					} else {
						date = sdf.parse(year + "-" + i + "-01");
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				entity.setMonth(date);
				re.add(entity);
			}
		}
		return re;
	}

}
