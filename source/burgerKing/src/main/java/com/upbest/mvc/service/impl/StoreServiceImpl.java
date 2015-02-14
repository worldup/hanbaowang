package com.upbest.mvc.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.upbest.mvc.constant.Constant.BrandExtension;
import com.upbest.mvc.constant.Constant.ShopState;
import com.upbest.mvc.entity.BArea;
import com.upbest.mvc.entity.BExBaseInfo;
import com.upbest.mvc.entity.BExaminationPaper;
import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.entity.BShopInfoTar;
import com.upbest.mvc.entity.BShopReport;
import com.upbest.mvc.entity.BShopStatistic;
import com.upbest.mvc.entity.BShopStatisticTemp;
import com.upbest.mvc.entity.BTestPaper;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.handler.ShopHandler;
import com.upbest.mvc.repository.factory.BArearespository;
import com.upbest.mvc.repository.factory.ExamBaseinfoRespository;
import com.upbest.mvc.repository.factory.ExamnationPaperRespository;
import com.upbest.mvc.repository.factory.StoreReportRespository;
import com.upbest.mvc.repository.factory.StoreRespository;
import com.upbest.mvc.repository.factory.StoreStatisticRespository;
import com.upbest.mvc.repository.factory.StoreStatisticTempRespository;
import com.upbest.mvc.repository.factory.StoreTarRespository;
import com.upbest.mvc.repository.factory.StoreUserRespository;
import com.upbest.mvc.repository.factory.TestPaperRespository;
import com.upbest.mvc.repository.factory.UserRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.DBChooser;
import com.upbest.mvc.service.IBuserService;
import com.upbest.mvc.service.IStoreService;
import com.upbest.mvc.vo.BShopInfoVO;
import com.upbest.mvc.vo.BshopStatisticVO;
import com.upbest.mvc.vo.BuserVO;
import com.upbest.mvc.vo.SimpleSignInfoVO;
import com.upbest.mvc.vo.TaskDetailVO;
import com.upbest.utils.DataType;
import com.upbest.utils.ExcelUtils;

@Service
public class StoreServiceImpl implements IStoreService {
    @Autowired
    private DBChooser dbChooser;
    @Autowired
    private JdbcTemplate coreJdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Inject
    protected StoreRespository storeRepository;

    @Inject
    protected StoreReportRespository storeReportRespository;

    @Inject
    protected StoreUserRespository storeUserRespository;

    @Inject
    protected TestPaperRespository testPaperRespository;

    @Inject
    protected ExamnationPaperRespository examPaperRespository;

    @Inject
    protected ExamBaseinfoRespository examBaseinfoRespository;

    @Inject
    protected UserRespository usrRes;

    @Autowired
    private CommonDaoCustom<Object[]> common;

    @Autowired
    private ShopHandler shopHandler;

    @Autowired
    private IBuserService userService;

    @Autowired
    private StoreTarRespository storeTarRespository;

    @Autowired
    private StoreStatisticTempRespository sStsTempRes;

    @Autowired
    private StoreStatisticRespository sStsRes;

    @Inject
    private BArearespository areaRes;

    @Override
    public BShopInfoVO findById(Integer id) {
        BShopInfo shopInfo = storeRepository.findOne(id);
        BShopInfoVO vo = new BShopInfoVO();
        BeanUtils.copyProperties(shopInfo, vo);

        vo.setId(shopInfo.getId());
        vo.setCreateTime(shopInfo.getCreateTime());
        vo.setShopaddress(shopInfo.getShopaddress());
        vo.setShopbusinessarea(shopInfo.getShopbusinessarea());
        vo.setShopbusinesstime(shopInfo.getShopbusinesstime());
        vo.setShopimage(shopInfo.getShopimage());
        vo.setShopname(shopInfo.getShopname());
        vo.setShopnum(shopInfo.getShopnum());
        vo.setShopopentime(shopInfo.getShopopentime());
        vo.setShopphone(shopInfo.getShopphone());
        vo.setShopseatnum(shopInfo.getShopseatnum());
        vo.setShopsize(shopInfo.getShopsize());
        vo.setRegional(shopInfo.getRegional());
        vo.setProvince(shopInfo.getProvince());
        vo.setPrefecture(shopInfo.getPrefecture());
        vo.setCityGrade(shopInfo.getCityGrade());
        vo.setDistrict(shopInfo.getDistrict());
        vo.setMold(shopInfo.getMold());
        vo.setBusinessCircle(shopInfo.getBusinessCircle());
        vo.setCircleSize(shopInfo.getCircleSize());
        vo.setSubwayNumber(shopInfo.getSubwayNumber());
        vo.setAnExit(shopInfo.getAnExit());
        vo.setMeters(shopInfo.getMeters());
        vo.setBusNumber(shopInfo.getBusNumber());
        vo.setCompetitor(shopInfo.getCompetitor());
        vo.setMainCompetitors(shopInfo.getMainCompetitors());
        vo.setRivalsName(shopInfo.getRivalsName());
        vo.setKfcNumber(shopInfo.getKfcNumber());
        vo.setMnumber(shopInfo.getMnumber());
        vo.setPizzaNumber(shopInfo.getPizzaNumber());
        vo.setStarbucksNumber(shopInfo.getStarbucksNumber());
        vo.setUpdateTime(shopInfo.getUpdateTime());
        vo.setChineseName(shopInfo.getChineseName());
        vo.setEnglishName(shopInfo.getEnglishName());
        vo.setChineseAddress(shopInfo.getChineseAddress());
        vo.setEnglishAddress(shopInfo.getEnglishAddress());
        vo.setStraightJointJoin(shopInfo.getStraightJointJoin());
        vo.setEmail(shopInfo.getEmail());
        vo.setStoreInfo1(shopInfo.getStoreInfo1());
        vo.setStoreInfo2(shopInfo.getStoreInfo2());
        List<String> urList = getUserIds(DataType.getAsString(id));
        if (!CollectionUtils.isEmpty(urList)) {
            vo.setUserIds(urList.get(0));
            vo.setUserNames(urList.get(1));
        }
        return vo;
    }

    @Override
    public BShopInfoVO findByAreaId(Integer id) {
        BShopInfo shopInfo = storeRepository.findOne(id);
        BShopInfoVO vo = new BShopInfoVO();

        BeanUtils.copyProperties(shopInfo, vo);

        vo.setId(shopInfo.getId());
        vo.setCreateTime(shopInfo.getCreateTime());
        vo.setShopaddress(shopInfo.getShopaddress());
        vo.setShopbusinessarea(shopInfo.getShopbusinessarea());
        vo.setShopbusinesstime(shopInfo.getShopbusinesstime());
        vo.setShopimage(shopInfo.getShopimage());
        vo.setShopname(shopInfo.getShopname());
        vo.setShopnum(shopInfo.getShopnum());
        vo.setCircleSize(shopInfo.getCircleSize());
        vo.setShopopentime(shopInfo.getShopopentime());
        vo.setShopphone(shopInfo.getShopphone());
        vo.setShopseatnum(shopInfo.getShopseatnum());
        vo.setShopsize(shopInfo.getShopsize());
        vo.setRegional(shopInfo.getRegional());
        vo.setProvince(shopInfo.getProvince());
        vo.setPrefecture(shopInfo.getPrefecture());
        vo.setCityGrade(shopInfo.getCityGrade());
        vo.setDistrict(shopInfo.getDistrict());
        vo.setMold(shopInfo.getMold());
        vo.setBusinessCircle(shopInfo.getBusinessCircle());
        vo.setSubwayNumber(shopInfo.getSubwayNumber());
        vo.setAnExit(shopInfo.getAnExit());
        vo.setMeters(shopInfo.getMeters());
        vo.setBusNumber(shopInfo.getBusNumber());
        vo.setCompetitor(shopInfo.getCompetitor());
        vo.setMainCompetitors(shopInfo.getMainCompetitors());
        vo.setRivalsName(shopInfo.getRivalsName());
        vo.setKfcNumber(shopInfo.getKfcNumber());
        vo.setMnumber(shopInfo.getMnumber());
        vo.setPizzaNumber(shopInfo.getPizzaNumber());
        vo.setStarbucksNumber(shopInfo.getStarbucksNumber());
        vo.setUpdateTime(shopInfo.getUpdateTime());
        vo.setChineseName(shopInfo.getChineseName());
        vo.setEnglishName(shopInfo.getEnglishName());
        vo.setChineseAddress(shopInfo.getChineseAddress());
        vo.setEnglishAddress(shopInfo.getEnglishAddress());
        vo.setStraightJointJoin(shopInfo.getStraightJointJoin());
        vo.setEmail(shopInfo.getEmail());
        vo.setStoreInfo1(shopInfo.getStoreInfo1());
        vo.setStoreInfo2(shopInfo.getStoreInfo2());
        vo.setStatus(ShopState.getChName(shopInfo.getStatus()));
        String brandExtension = shopInfo.getBrandExtension();
        if (!StringUtils.isEmpty(brandExtension)) {
            String[] brandAry = brandExtension.split(",");
            StringBuilder brandDesc = new StringBuilder();
            for (String b : brandAry) {
                brandDesc.append(BrandExtension.getName(Integer.valueOf(b))).append(",");
            }
            if (brandDesc.length() > 0) {
                vo.setBrandExtensionChDesc(brandDesc.substring(0, brandDesc.length() - 1));
            }
        }

        List<String> urList = getUserIds(DataType.getAsString(id));
        if (!CollectionUtils.isEmpty(urList)) {
            String userIds = urList.get(0);
            vo.setUserIds(urList.get(0));
            vo.setUserNames(urList.get(1));

            if (!StringUtils.isEmpty(userIds)) {
                StringBuilder om = new StringBuilder();
                String[] userAry = userIds.split(",");
                for (String userId : userAry) {
                    Buser user = userService.getNearestSuper(Integer.parseInt(userId));
                    if (user != null) {
                        om.append(user.getRealname() + ",");
                    }
                }
                if (om.length() > 0) {
                    vo.setOm(om.substring(0, om.length() - 1));
                }
            }
        }
        return vo;
    }

    @Override
    public Page<Object[]> findShopList(String shopName, Integer userId, String userRole, String regional, Pageable pageable) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  SELECT distinct t.id,                       ");
        sql.append("         t.shop_address,             ");
        sql.append("         t.shop_business_area,       ");
        sql.append("         t.shop_business_time,       ");
        sql.append("         t.shop_image,               ");
        sql.append("         t.shop_name,                ");
        sql.append("         t.shop_num,                 ");
        sql.append("         t.shop_open_time,           ");
        sql.append("         t.shop_phone,               ");
        sql.append("         t.shop_seat_num,            ");
        sql.append("         t.shop_size,                ");
        sql.append("         t.create_time,               ");
        sql.append("         t.regional,                  ");
        sql.append("         t.province,                  ");
        sql.append("         t.prefecture,                  ");
        sql.append("         t.city_grade,                  ");
        sql.append("         t.district,                  ");
        sql.append("         t.mold,                  ");
        sql.append("         t.business_circle,                  ");
        sql.append("         t.circle_size,                  ");
        sql.append("         t.subway_number,                  ");
        sql.append("         t.an_exit,                  ");
        sql.append("         t.meters,                  ");
        sql.append("         t.bus_number,                  ");
        sql.append("         t.competitor,                  ");
        sql.append("         t.main_competitors,                  ");
        sql.append("         t.rivals_name,                  ");
        sql.append("         t.kfc_number,                  ");
        sql.append("         t.m_number,                  ");
        sql.append("         t.pizza_number,                  ");
        sql.append("         t.starbucks_number,                  ");
        sql.append("         t.update_time,                  ");
        sql.append("         t.chinese_name,                  ");
        sql.append("         t.english_name,                  ");
        sql.append("         t.chinese_address,                  ");
        sql.append("         t.english_address,                  ");
        sql.append("         t.straight_joint_join,                  ");
        sql.append(" t.longitude,   ");
        sql.append(" t.latitude,     ");
        sql.append(" t.store_info1, ");
        sql.append(" t.store_info2  ");
        sql.append("    FROM bk_shop_info t              ");
        if ("1".equals(userRole)) {
            // OM,查询OM及OC下所能巡检的门店
            sql.append(" left join bk_shop_user su on su.shop_id=t.id ");
            sql.append(" left join bk_user ur on ur.id=su.user_id ");
            sql.append("   where 1 = 1                       ");
            sql.append(" and ur.pid=? ");

            params.add(userId);

        } else if ("2".equals(userRole)) {
            // OC
            sql.append(" left join bk_shop_user su on su.shop_id=t.id ");
            sql.append(" left join bk_user ur on ur.id=su.user_id ");
            sql.append("   where 1 = 1                       ");
            sql.append(" and ur.id=? ");
            params.add(userId);


        } else if ("3".equals(userRole)) {
            // OM+
            sql.append(" left join bk_shop_user su on su.shop_id=t.id ");
            sql.append(" where 1=1 and su.user_id in(" + userService.getOCUserListInOMPlus(userId) + ") ");

        } else {
            // 超级管理员，不受限制

            sql.append("   where 1 = 1                       ");

        }

        if (StringUtils.isNotBlank(shopName)) {
            sql.append(" and t.shop_name like ?");
            params.add("%" + shopName + "%");
        }
        if (StringUtils.isNotBlank(regional) && !"0".equals(regional)) {
            sql.append("and t.regional = ?");
            params.add(regional);
        }
        sql.append(" order by t.shop_num  ");
        return common.queryBySql(sql.toString(), params, pageable);
    }

    @Override
    public Page<Object[]> findShopList(String shopName, Integer userId, String realName, Pageable pageable) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        Buser user = userService.findById(DataType.getAsInt(userId));
        sql.append("  SELECT distinct t.id,                       ");
        sql.append("         t.shop_address,             ");
        sql.append("         t.shop_business_area,       ");
        sql.append("         t.shop_business_time,       ");
        sql.append("         t.shop_image,               ");
        sql.append("         t.shop_name ,                ");
        sql.append("         t.shop_num,                 ");
        sql.append("         t.shop_open_time,           ");
        sql.append("         t.shop_phone,               ");
        sql.append("         t.shop_seat_num,            ");
        sql.append("         t.shop_size,                ");
        sql.append("         t.create_time,               ");
        sql.append("         t.regional,                  ");
        sql.append("         t.province,                  ");
        sql.append("         t.prefecture,                  ");
        sql.append("         t.city_grade,                  ");
        sql.append("         t.district,                  ");
        sql.append("         t.mold,                  ");
        sql.append("         t.business_circle,                  ");
        sql.append("         t.circle_size,                  ");
        sql.append("         t.subway_number,                  ");
        sql.append("         t.an_exit,                  ");
        sql.append("         t.meters,                  ");
        sql.append("         t.bus_number,                  ");
        sql.append("         t.competitor,                  ");
        sql.append("         t.main_competitors,                  ");
        sql.append("         t.rivals_name,                  ");
        sql.append("         t.kfc_number,                  ");
        sql.append("         t.m_number,                  ");
        sql.append("         t.pizza_number,                  ");
        sql.append("         t.starbucks_number,                  ");
        sql.append("         t.update_time,                  ");
        sql.append("         t.chinese_name,                  ");
        sql.append("         t.english_name,                  ");
        sql.append("         t.chinese_address,                  ");
        sql.append("         t.english_address,                  ");
        sql.append("         t.straight_joint_join,                  ");
        sql.append(" t.longitude,   ");
        sql.append(" t.latitude,     ");
        sql.append(" t.store_info1, ");
        sql.append(" t.store_info2 ");
        sql.append("    FROM bk_shop_info t              ");
        if ("1".equals(user.getRole())) {
            // OM,查询OM及OC下所能巡检的门店
            sql.append(" left join bk_shop_user su on su.shop_id=t.id ");
            sql.append(" left join bk_user ur on ur.id=su.user_id ");
            sql.append("   where 1 = 1                       ");
            sql.append(" and ur.pid=? ");

            params.add(userId);
            if (!StringUtils.isEmpty(realName)) {
                sql.append(" and ur.real_name like ? ");
                params.add("%" + realName + "%");
            }
        } else if ("2".equals(user.getRole())) {
            // OC
            sql.append(" left join bk_shop_user su on su.shop_id=t.id ");
            sql.append(" left join bk_user ur on ur.id=su.user_id ");
            sql.append("   where 1 = 1                       ");
            sql.append(" and ur.id=? ");
            params.add(userId);

            if (!StringUtils.isEmpty(realName)) {
                sql.append(" and ur.real_name like ? ");
                params.add("%" + realName + "%");
            }
        } else if ("3".equals(user.getRole())) {
            // OM+
            sql.append(" left join bk_shop_user su on su.shop_id=t.id ");

            if (!StringUtils.isEmpty(realName)) {
                sql.append(" left join bk_user ur on su.user_id=ur.id ");
            }
            sql.append(" where 1=1 and su.user_id in(" + userService.getOCUserListInOMPlus(userId) + ") ");

            if (!StringUtils.isEmpty(realName)) {
                sql.append(" and ur.real_name like ? ");
                params.add("%" + realName + "%");
            }
        } else {
            // 超级管理员，不受限制
            if (!StringUtils.isEmpty(realName)) {
                sql.append(" left join bk_shop_user su on su.shop_id=t.id ");
                sql.append(" left join bk_user ur on su.user_id=ur.id ");
            }

            sql.append("   where 1 = 1                       ");

            if (!StringUtils.isEmpty(realName)) {
                sql.append(" and ur.real_name like ? ");
                params.add("%" + realName + "%");
            }
        }
        if (StringUtils.isNotBlank(shopName)) {
            sql.append(" and t.shop_name like ?");
            params.add("%" + shopName + "%");
        }
        sql.append(" order by t.shop_num  ");
        return common.queryBySql(sql.toString(), params, pageable);
    }

    public List<Object[]> findShopAllList(Integer userId, String shopName) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        Buser user = null;
        if (null != userId) {
            user = userService.findById(DataType.getAsInt(userId));
        }
        sql.append("  SELECT distinct t.id,                       ");
        sql.append("         t.shop_address,             ");
        sql.append("         t.shop_business_area,       ");
        sql.append("         t.shop_business_time,       ");
        sql.append("         t.shop_image,               ");
        sql.append("         t.shop_name,                ");
        sql.append("         t.shop_num,                 ");
        sql.append("         t.shop_open_time,           ");
        sql.append("         t.shop_phone,               ");
        sql.append("         t.shop_seat_num,            ");
        sql.append("         t.shop_size,                ");
        sql.append("         t.create_time,               ");
        sql.append("         t.regional,                  ");
        sql.append("         t.province,                  ");
        sql.append("         t.prefecture,                  ");
        sql.append("         t.city_grade,                  ");
        sql.append("         t.district,                  ");
        sql.append("         t.mold,                  ");
        sql.append("         t.business_circle,                  ");
        sql.append("         t.circle_size,                  ");
        sql.append("         t.subway_number,                  ");
        sql.append("         t.an_exit,                  ");
        sql.append("         t.meters,                  ");
        sql.append("         t.bus_number,                  ");
        sql.append("         t.competitor,                  ");
        sql.append("         t.main_competitors,                  ");
        sql.append("         t.rivals_name,                  ");
        sql.append("         t.kfc_number,                  ");
        sql.append("         t.m_number,                  ");
        sql.append("         t.pizza_number,                  ");
        sql.append("         t.starbucks_number,                  ");
        sql.append("         t.update_time,                  ");
        sql.append("         t.chinese_name,                  ");
        sql.append("         t.english_name,                  ");
        sql.append("         t.chinese_address,                  ");
        sql.append("         t.english_address,                  ");
        sql.append("         t.straight_joint_join,                  ");
        sql.append(" t.longitude,   ");
        sql.append(" t.latitude,t.store_info1,t.store_info2     ");
        sql.append("    FROM bk_shop_info t              ");
        if (null != user) {
            if ("1".equals(user.getRole())) {
                // OM,查询OM及OC下所能巡检的门店
                sql.append(" left join bk_shop_user su on su.shop_id=t.id ");
                sql.append(" left join bk_user ur on ur.id=su.user_id ");
                sql.append("   where 1 = 1                       ");
                sql.append(" and ur.pid=? ");
                params.add(userId);
            } else if ("2".equals(user.getRole())) {
                // OC
                sql.append(" left join bk_shop_user su on su.shop_id=t.id ");
                sql.append(" left join bk_user ur on ur.id=su.user_id ");
                sql.append("   where 1 = 1                       ");
                sql.append(" and ur.id=? ");
                params.add(userId);
            } else if ("3".equals(user.getRole())) {
                // OM+
                sql.append(" left join bk_shop_user su on su.shop_id=t.id ");
                sql.append(" where 1=1 and su.user_id in(" + userService.getOCUserListInOMPlus(userId) + ") ");
            } else {
                // 超级管理员，不受限制
                sql.append("   where 1 = 1                       ");
            }
        }
        if (StringUtils.isNotBlank(shopName)) {
            sql.append(" and t.shop_name like ?");
            params.add("%" + shopName + "%");
        }
        sql.append(" order by t.shop_num  ");
        return common.queryBySql(sql.toString(), params);
    }

    public List<BShopInfoVO> getShopInfoVOList(String shopName, Integer userId, Pageable pageable) {
        return getShopInfo(findShopList(shopName, userId, null, pageable).getContent());
    }

    public List<BShopInfoVO> getShopInfoVOList(Integer userId, String shopName) {
        return getShopInfo(findShopAllList(userId, shopName));
    }

    private List<BShopInfoVO> getShopInfo(List<Object[]> list) {
        List<BShopInfoVO> result = new ArrayList<BShopInfoVO>();
        if (!CollectionUtils.isEmpty(list)) {
            BShopInfoVO entity = null;
            for (Object[] obj : list) {
                entity = new BShopInfoVO();
                entity.setId(DataType.getAsInt(obj[0]));
                entity.setShopaddress(DataType.getAsString(obj[1]));
                entity.setShopbusinessarea(DataType.getAsString(obj[2]));
                entity.setShopbusinesstime(DataType.getAsString(obj[3]));
                entity.setShopimage(DataType.getAsString(obj[4]));
                entity.setShopname(DataType.getAsString(obj[5]));
                entity.setShopnum(DataType.getAsString(obj[6]));
                entity.setShopopentime(DataType.getAsDate(obj[7]));
                entity.setShopphone(DataType.getAsString(obj[8]));
                entity.setShopseatnum(DataType.getAsInt(obj[9]));
                entity.setShopsize(DataType.getAsString(obj[10]));
                entity.setCreateTime(DataType.getAsDate(obj[11]));
                entity.setRegional(DataType.getAsString(obj[12]));
                entity.setProvince(DataType.getAsString(obj[13]));
                entity.setPrefecture(DataType.getAsString(obj[14]));
                entity.setCityGrade(DataType.getAsString(obj[15]));
                entity.setDistrict(DataType.getAsString(obj[16]));
                entity.setMold(DataType.getAsString(obj[17]));
                entity.setBusinessCircle(DataType.getAsString(obj[18]));
                entity.setCircleSize(DataType.getAsString(obj[19]));
                entity.setSubwayNumber(DataType.getAsString(obj[20]));
                entity.setAnExit(DataType.getAsString(obj[21]));
                entity.setMeters(DataType.getAsString(obj[22]));
                entity.setBusNumber(DataType.getAsString(obj[23]));
                entity.setCompetitor(DataType.getAsString(obj[24]));
                entity.setMainCompetitors(DataType.getAsString(obj[25]));
                entity.setRivalsName(DataType.getAsString(obj[26]));
                entity.setKfcNumber(DataType.getAsString(obj[27]));
                entity.setMnumber(DataType.getAsString(obj[28]));
                entity.setPizzaNumber(DataType.getAsString(obj[29]));
                entity.setStarbucksNumber(DataType.getAsString(obj[30]));
                entity.setUpdateTime(DataType.getAsDate(obj[31]));
                entity.setChineseName(DataType.getAsString(obj[32]));
                entity.setEnglishName(DataType.getAsString(obj[33]));
                entity.setChineseAddress(DataType.getAsString(obj[34]));
                entity.setEnglishAddress(DataType.getAsString(obj[35]));
                entity.setStraightJointJoin(DataType.getAsString(obj[36]));
                entity.setLongitude(DataType.getAsString(obj[37]));
                entity.setLatitude(DataType.getAsString(obj[38]));
                entity.setStoreInfo1(DataType.getAsString(obj[39]));
                entity.setStoreInfo2(DataType.getAsString(obj[40]));

                List<String> urList = getUserIds(DataType.getAsString(obj[0]));
                if (!CollectionUtils.isEmpty(urList)) {
                    entity.setUserIds(urList.get(0));
                    entity.setUserNames(urList.get(1));
                }
                result.add(entity);
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
        // edit 2014-9-23 ''-sql中为空
        return result = (result.length() > 0 ? result : "''");
    }

    public List<String> getUserIds(String shopId) {
        StringBuffer sql = new StringBuffer();
        List<String> params = new ArrayList<String>();
        sql.append("  select ur.id, ur.real_name          ");
        sql.append("    from bk_shop_user su              ");
        sql.append("    join bk_user ur                   ");
        sql.append("      on ur.id = su.user_id           ");
        sql.append("   where 1 = 1                        ");
        if (StringUtils.isNotBlank(shopId)) {
            sql.append("     and su.shop_id = ?             ");
            params.add(shopId);
        }
        String ids = "";
        String userNames = "";
        List<Object[]> list = common.queryBySql(sql.toString(), params);
        List<String> result = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Object[] obj : list) {
                ids += DataType.getAsString(obj[0]) + ",";
                userNames += DataType.getAsString(obj[1]) + ",";
            }
        }
        if (StringUtils.isNotBlank(ids)) {
            ids = ids.substring(0, ids.length() - 1);
        }
        if (StringUtils.isNotBlank(userNames)) {
            userNames = userNames.substring(0, userNames.length() - 1);
        }

        result.add(ids);
        result.add(userNames);
        return result;
    }

    @Override
    public BShopInfo saveBShop(BShopInfo entity) {
        return storeRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(Integer id) {
        BShopInfo entity = storeRepository.findOne(id);
        storeRepository.delete(entity);
        String sql = "delete from bk_shop_user where shop_id=?";
        common.upadteBySql(sql, Arrays.asList(DataType.getAsInt(id)));
        sql = "delete from bk_shop_report where shop_id=?";
        common.upadteBySql(sql, Arrays.asList(DataType.getAsInt(id)));
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteUserId(Integer id) {
        String sql = "delete from bk_shop_user where shop_id=?";
        common.upadteBySql(sql, Arrays.asList(DataType.getAsInt(id)));
    }

    @Override
    public void addShopFromExcel(File file) throws Exception {
        // ExcelUtils.handExcel(1, file, shopHandler);
    }

    @Override
    public void addShopFromExcel(Resource resource) throws Exception {
        ExcelUtils.handExcel(1, resource, shopHandler);
    }

    @Override
    public List<BShopInfo> findAllShop() {
        List<BShopInfo> shops = new ArrayList<BShopInfo>();

        Iterable<BShopInfo> iterator = storeRepository.findAll();
        if (iterator != null) {
            for (BShopInfo shop : iterator) {
                shops.add(shop);
            }
        }
        return shops;
    }

    @Override
    public void updateLngLat(Integer id, String longitude, String latitude) {
        BShopInfo s = storeRepository.findOne(id);
        s.setLatitude(latitude);
        s.setLongitude(longitude);
        storeRepository.save(s);
    }

    @Override
    public Object queryReportInfo(Integer shopId, Date startTime, Date endTime) {
        List<BShopReport> reportList = null;
        if (startTime != null && endTime != null) {
            reportList = storeReportRespository.findReportInfo(startTime, endTime, shopId);
        } else if (startTime == null && endTime != null) {
            reportList = storeReportRespository.findReportInfoLt(endTime, shopId);
        } else if (startTime != null && endTime == null) {
            reportList = storeReportRespository.findReportInfoGt(startTime, shopId);
        } else if (startTime == null && endTime == null) {
            reportList = storeReportRespository.findReportInfo(shopId);
        }
        String origionalName = "origionalName";
        String path = "path";
        Map<String, List<Map<String, String>>> result = new HashMap<>();
        if (!CollectionUtils.isEmpty(reportList)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            for (BShopReport bShopReport : reportList) {
                String dateStr = sdf.format(bShopReport.getCreateTime());
                List<Map<String, String>> reportInfo = result.get(dateStr);
                if (reportInfo == null) {
                    reportInfo = new ArrayList<>();
                    result.put(dateStr, reportInfo);
                }
                Map<String, String> map = new HashMap<>();
                map.put(origionalName, bShopReport.getReport());
                map.put(path, bShopReport.getRandomName());

                reportInfo.add(map);
            }
        }
        return result;
    }

    @Override
    public Object queryUserTestInfo(Integer shopid, Integer examTypeId, Date startTime, Date endTime) {
        List<BExaminationPaper> examPaperies = examPaperRespository.findByEtype(examTypeId);
        List<Integer> examIds = new ArrayList<Integer>();
        if (!CollectionUtils.isEmpty(examPaperies)) {
            for (BExaminationPaper epaper : examPaperies) {
                examIds.add(epaper.getId());
            }

            List<BTestPaper> testInfo = null;
            if (startTime != null && endTime != null) {
                testInfo = testPaperRespository.findTestInfo(startTime, endTime, shopid, examIds);
            } else if (startTime == null && endTime != null) {
                testInfo = testPaperRespository.findTestInfoLt(endTime, shopid, examIds);
            } else if (startTime != null && endTime == null) {
                testInfo = testPaperRespository.findTestInfoGt(startTime, shopid, examIds);
            } else if (startTime == null && endTime == null) {
                testInfo = testPaperRespository.findTestInfo(shopid, examIds);
            }

            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            if (!CollectionUtils.isEmpty(testInfo)) {
                BShopInfo shopInfo = storeRepository.findOne(shopid);
                BExBaseInfo exBaseInfo = examBaseinfoRespository.findOne(examTypeId);
                String shopName = shopInfo == null ? "" : shopInfo.getShopname();
                String examTypeName = exBaseInfo == null ? "" : exBaseInfo.getBvalue();

                for (BTestPaper testPaper : testInfo) {
                    Map<String, Object> map = new HashMap<String, Object>();

                    map.put("score", testPaper.getTtotal());
                    map.put("shopName", shopName);
                    map.put("examTypeName", examTypeName);
                    map.put("userId", testPaper.getUserid());
                    map.put("examId", testPaper.getId());
                    map.put("time", testPaper.getTend().getTime());
                    map.put("realName", usrRes.findOne(testPaper.getUserid()).getRealname());
                    result.add(map);

                }
            }
            return result;
        }

        return null;
    }

    @Override
    public List<BShopInfo> queryShopInfoByUserId(Integer userId) {
        List<BShopInfo> result = new ArrayList<BShopInfo>();

        List<Buser> users = userService.getOCUserList(userId);
        if (CollectionUtils.isEmpty(users)) {
            return result;
        }
        List<Integer> userIds = new ArrayList<Integer>();
        for (Buser buser : users) {
            userIds.add(buser.getId());
        }

        List<String> params = new ArrayList<String>();
        StringBuilder sql = new StringBuilder();

        sql.append("	select s.id,s.shop_num from  bk_shop_info s	").append("		where s.id in		").append("		(select su.shop_id from  bk_shop_user su where su.user_id in(	");
        addInCondition(userIds.toArray(new Integer[userIds.size()]), sql);
        sql.append("	))	");

        List<Object[]> list = common.queryBySql(sql.toString(), params);
        if (!CollectionUtils.isEmpty(list)) {
            for (Object[] objAry : list) {
                BShopInfo shopInfo = new BShopInfo();
                shopInfo.setId(DataType.getAsInt(objAry[0]));
                shopInfo.setShopnum(DataType.getAsString(objAry[1]));
                result.add(shopInfo);
            }
        }
        return result;
    }

    @Override
    public List<BshopStatisticVO> queryStatisticInfo(Integer userId) {
        List<BshopStatisticVO> result = new ArrayList<BshopStatisticVO>();

        List<BShopInfo> shopInfos = queryShopInfoByUserId(userId);
        List<String> shopNumList = new ArrayList<String>();

        if (!CollectionUtils.isEmpty(shopInfos)) {
            for (BShopInfo shopInfo : shopInfos) {
                shopNumList.add(shopInfo.getShopnum());
            }
            List<String> params = new ArrayList<String>();
            StringBuilder sql = new StringBuilder();
            sql.append("	select f.sales,")
                    .append("			f.tc,")
                    .append("			f.shopId,")
                    .append("			f.month,")
                    .append("			f.cash_audit,")
                    .append("			f.gt_nps,")
                    .append("			f.rank,")
                    .append("			f.rev_bs,")
                    .append("			f.rev_fs,")
                    .append("			f.shop_num,")
                    .append("			v.tc_comp_tc,")
                    .append("			v.comp,")
                    .append("			v.PM,")
                    .append("			v.TC_COMP_PM ");

           /* sql.append("	select f.sales," + "f.tc," + "f.shopId," + "f.month," + "f.cash_audit," + "f.gt_nps," + "f.rank," + "f.rev_bs," + "f.rev_fs,	" + "f.shop_num," + "v.[tc_comp_tc]," + "v.comp,"
                    + "v.pm," + "v.[TC_COMP_PM] ");*/

            sql.append("		from (		");

            sql.append("			select s.*,si.id as shopId,si.shop_num from (	");
            sql.append("				select s.*	")
                    .append("				from		")
                    .append("				(select s.shop_id, MAX(s.month) last_month 	")
                    .append("				from bk_shop_statistic s where s.shop_id in (		");
            addInCondition(shopNumList.toArray(new String[shopNumList.size()]), sql);
            sql.append("				) group by s.shop_id) sm	")
                    .append("			join bk_shop_statistic s on sm.shop_id = s.shop_id and sm.last_month = s.month	");
            sql.append("	)s	").append("	join bk_shop_info si on s.shop_id = si.shop_num ");
            sql.append(" )f		")
                    .append("	left join 			");
            //tc_comp,sales_comp的最近一次的数据
            sql.append("		(	select v.*	")
                    .append("				from		")
                    .append("				(select d.storeid, MAX(d.salesdate) salesdate 	")
                    .append("				from V_TF_Daily_Sales_MONTH_LAST_DAY d where d.storeid in (		");
            addInCondition(shopNumList.toArray(new String[shopNumList.size()]), sql);
            sql.append("				) group by d.storeid) dm	")
                    .append("			join V_TF_Daily_Sales_MONTH_LAST_DAY v on dm.storeid = v.storeid and dm.salesdate = v.salesdate  ) v");
            sql.append("	on f.shop_num = v.storeid ");

            List<Object[]> list = common.queryBySql(sql.toString(), params);

            //获取门店对应的最大排名
            Map<String, String[]> maxRankMap = getMaxRankByStoreId(shopNumList);

            result = buildStatisticInfo(list, maxRankMap);
        }

        return result;
    }

    /**
     * key : 门店号
     * value : [salesRank最大排名数,tcRank最大排名数]
     *
     * @param shopNumList
     * @return
     */
    private Map<String, String[]> getMaxRankByStoreId(List<String> shopNumList) {
        Map<String, String[]> result = new HashMap<String, String[]>();

        StringBuilder sql = new StringBuilder();
        sql.append("	select t.storeid,count(t.salesRank) srCount,count(t.tcRank) trCount	")
                .append("		from 															")
                .append("		(																	");
        if (dbChooser.isSQLServer()) {
            sql.append("		select DISTINCT dm.storeid storeid,  CONVERT(varchar, dm.salesdate, 120 ) month, ");

        } else {
            sql.append("		select DISTINCT dm.storeid storeid,  str_to_date( dm.salesdate, '%Y-%m-%d %H:%i:%s' ) month, ");

        }
        sql.append("		v.PM salesRank,v.TC_COMP_PM tcRank,v.storeid vstoreid 				")
                .append("		from																")
                .append("		(select d.storeid, MAX(d.salesdate) salesdate 						")
                .append("		from V_TF_Daily_Sales_MONTH_LAST_DAY d 								")
                .append("			where d.storeid in (											");
        addInCondition(shopNumList.toArray(new String[shopNumList.size()]), sql);
        sql.append("		) group by d.storeid) dm											");
        if (dbChooser.isSQLServer()) {
            sql.append("		join V_TF_Daily_Sales_MONTH_LAST_DAY v on DATEDIFF(m,dm.salesdate,v.salesdate) = 0 ");

        } else {
            sql.append("		join V_TF_Daily_Sales_MONTH_LAST_DAY v on TIMESTAMPDIFF(month,dm.salesdate,v.salesdate) = 0 ");

        }


        sql.append("	) t group by t.storeid													");

        List<Object[]> list = common.queryBySql(sql.toString(), new ArrayList<Object>());
        if (!CollectionUtils.isEmpty(list)) {
            for (Object[] objAry : list) {
                String storeId = DataType.getAsString(objAry[0]);
                if (org.springframework.util.StringUtils.hasText(storeId)) {
                    String[] rankAry = new String[2];
                    rankAry[0] = DataType.getAsString(objAry[1]);
                    rankAry[1] = DataType.getAsString(objAry[2]);

                    result.put(storeId, rankAry);
                }


            }
        }
        return result;
    }

    private List<BshopStatisticVO> buildStatisticInfo(List<Object[]> list, Map<String, String[]> maxRankMap) {
        List<BshopStatisticVO> result = new ArrayList<BshopStatisticVO>();

        if (!CollectionUtils.isEmpty(list)) {
            long shopCount = storeRepository.count();
            for (Object[] objAry : list) {
                BshopStatisticVO statistic = new BshopStatisticVO();
                statistic.setSales(DataType.getAsString(objAry[0]));
                statistic.setTc(DataType.getAsString(objAry[1]));
                statistic.setShopId(DataType.getAsString(objAry[2]));
                statistic.setMonth(DataType.getAsDate(objAry[3]).getTime() + "");
                statistic.setCashAudit(DataType.getAsString(objAry[4]));
                statistic.setGtNps(DataType.getAsString(objAry[5]));
                statistic.setRank(DataType.getAsString(objAry[6]));
                statistic.setRevBs(DataType.getAsString(objAry[7]));
                statistic.setRevFs(DataType.getAsString(objAry[8]));
                statistic.setShopNum(DataType.getAsString(objAry[9]));

                Double tcComp = DataType.getAsDouble(objAry[10]);
                Double tcVal = scaleDouble(tcComp, 2);
                statistic.setTcComp(tcVal == null ? null : (tcVal.doubleValue() + ""));

                Double salesComp = DataType.getAsDouble(objAry[11]);
                Double d = scaleDouble(salesComp, 2);

                statistic.setSalesComp(d == null ? null : (d.doubleValue() + ""));

                String[] rankAry = maxRankMap.get(statistic.getShopNum());
                String salesRankCount = null;
                String tcRankCount = null;
                if (rankAry == null) {
                    salesRankCount = "0";
                    tcRankCount = "0";
                } else {
                    salesRankCount = StringUtils.isEmpty(rankAry[0]) ? "0" : rankAry[0];
                    tcRankCount = StringUtils.isEmpty(rankAry[1]) ? "0" : rankAry[1];
                }
                statistic.setSalesRank(DataType.getAsInt(objAry[12]) + "/" + salesRankCount);
                statistic.setTcRank(DataType.getAsInt(objAry[13]) + "/" + tcRankCount);


                //获取最近一次的gtnps，等
                if (StringUtils.isEmpty(statistic.getGtNps())
                        && StringUtils.isEmpty(statistic.getRevBs())
                        && StringUtils.isEmpty(statistic.getRevFs())
                        && StringUtils.isEmpty(statistic.getCashAudit())
                        && StringUtils.isEmpty(statistic.getRank())) {
                    StringBuilder sql = new StringBuilder();
                    List<String> params = new ArrayList<String>();
                    if (dbChooser.isSQLServer()) {
                        sql.append("select top 1 s.gt_nps,s.rev_bs,s.rev_fs,s.cash_audit,s.rank from bk_shop_statistic s where s.gt_nps is not null"
                                + " and s.month is not null and s.shop_id=? order by month");
                    } else {
                        sql.append("select  s.gt_nps,s.rev_bs,s.rev_fs,s.cash_audit,s.rank from bk_shop_statistic s where s.gt_nps is not null"
                                + " and s.month is not null and s.shop_id=? order by month limit 1");
                    }

                    params.add(statistic.getShopNum());
                    List<Object[]> objList = common.queryBySql(sql.toString(), params);
                    if (!CollectionUtils.isEmpty(objList)) {
                        for (Object[] ary : objList) {
                            Double gtNps = DataType.getAsDouble(ary[0]);
                            gtNps = scaleDouble(gtNps, 4);
                            statistic.setGtNps(gtNps + "");
                            statistic.setRevBs(DataType.getAsString(ary[1]));
                            statistic.setRevFs(DataType.getAsString(ary[2]));
                            statistic.setCashAudit(DataType.getAsString(ary[3]));
                            statistic.setRank(DataType.getAsString(ary[4]));
                        }

                    }

                }

                result.add(statistic);
            }
        }

        return result;

    }

    private Double scaleDouble(Double salesComp, int scale) {
        if (salesComp == null) {
            return null;
        }
        BigDecimal d = new BigDecimal(salesComp);
        d = d.setScale(scale, BigDecimal.ROUND_FLOOR);

        return d.doubleValue();
    }

    private void addInCondition(Object[] examType, StringBuilder sql) {
        int size = examType.length;
        for (int i = 0; i < size; i++) {
            sql.append(String.valueOf(examType[i]));
            if (i < size - 1) {
                sql.append(",");
            }
        }
    }

    @Override
    public List<TaskDetailVO> queryTaskDetails(String userId, String workTypeId, String year, String sord, String month, String quarter) {
        List<TaskDetailVO> result = new ArrayList<TaskDetailVO>();
        StringBuffer sql = new StringBuffer();
        sql.append("   select wi.id, wi.finish_real_time, typ.type_name, shop.shop_name      ");
        sql.append("     from bk_work_info wi                                                ");
        sql.append("    left join bk_shop_info shop                                              ");
        sql.append("       on shop.id = wi.store_id                                          ");
        sql.append("     join bk_work_type typ                                               ");
        sql.append("       on typ.id = wi.work_type_id                                       ");
        sql.append("     join bk_user ur                                                     ");
        sql.append("       on ur.id = wi.execute_id                                          ");
        sql.append("    where 1 = 1                                                          ");
        if (StringUtils.isNotBlank(year)) {
            if (dbChooser.isSQLServer()) {
                sql.append(" and DATEDIFF(yy,wi.start_time, '" + year + "')=0 ");
            } else {
                sql.append(" and TIMESTAMPDIFF(year,wi.start_time, '" + year + "-01-01" + "')=0 ");
            }
        }
        if (StringUtils.isNotBlank(userId)) {
            sql.append("      and ur.id = '" + userId + "'                                                  ");
        }
        if (StringUtils.isNotBlank(workTypeId)) {
            sql.append("      and wi.work_type_id = '" + workTypeId + "'                                         ");
        }
        if ("0".equals(sord)) {
            // 月度
            sql.append(" and MONTH(wi.start_time)='" + month + "' ");
        } else if ("1".equals(sord)) {
            // 季度
            sql.append(" and wi.quarter='" + quarter + "' ");
        }
        sql.append("      and wi.state = '1'                                                 ");
        List<Object[]> list = common.queryBySql(sql.toString(), new ArrayList());
        if (!CollectionUtils.isEmpty(list)) {
            TaskDetailVO vo = null;
            for (Object[] obj : list) {
                vo = new TaskDetailVO();
                vo.setId(DataType.getAsString(obj[0]));
                vo.setFinishRealTime(DataType.getAsDate(obj[1]).getTime());
                vo.setTaskName(DataType.getAsString(obj[2]));
                vo.setStoreName(DataType.getAsString(obj[3]));
                result.add(vo);
            }

        }
        return result;
    }

    @Transactional(readOnly = false)
    private void updateStoreUser(String shopIds, String shopNums) {
        String[] shopAry = null;
        String[] shopIdAry = null;
        if (shopNums.length() > 0) {
            shopAry = shopNums.split(",");
            shopIdAry = shopIds.split(",");
            for (int i = 0; i < shopAry.length; i++) {
                BShopInfo bshopInfo = storeRepository.findByShopnum(shopAry[i]);
                if (null != bshopInfo) {
                    common.upadteBySql("update bk_shop_user set shop_id='" + bshopInfo.getId() + "' where shop_id='" + shopIdAry[i] + "'", new ArrayList());

                }

            }

        }
    }

    @Override
    @Transactional(readOnly = false)
    public void updateStoreInfo() {
        // 更新门店统计信息
        try {
            updateStoreStsInfo();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 更新门店基本信息
        updateStoreInfos();
    }

    public void updateStoreInfos() {
        Iterable<BShopInfoTar> list = storeTarRespository.findAll();
        for (BShopInfoTar tar : list) {
            BShopInfo shopInfo = storeRepository.findByShopnum(tar.getShopnum());
            if (shopInfo == null) {
                // 新增
                BShopInfo entity = new BShopInfo();
                entity.setShopaddress(tar.getShopaddress());
                entity.setShopbusinessarea(tar.getShopbusinessarea());
                entity.setShopname(tar.getShopname());
                entity.setShopopentime(tar.getShopopentime());
                entity.setShopsize(DataType.getAsString(parseDouble(DataType.getAsDouble(tar.getShopsize()))));
                entity.setPrefecture(tar.getPrefecture());
                entity.setProvince(tar.getProvince());
                entity.setRegional(tar.getRegional());
                entity.setStraightJointJoin(tar.getStraightJointJoin());
                entity.setTiers(tar.getTiers());
                entity.setShopseatnum(DataType.getAsInt(tar.getSeating()));
                entity.setKitchenArea(tar.getKitchenArea());
                entity.setStatus(tar.getStatus());
                entity.setChineseName(tar.getChineseName());
                entity.setUpdateTime(tar.getUpdateTime());
                entity.setShopnum(tar.getShopnum());
                entity.setChineseAddress(tar.getChineseAddress());
                entity.setBusinessCircle(tar.getBusinessCircle());
                entity.setEmail("bk" + tar.getShopnum() + "@bkchina.cn");
                storeRepository.save(entity);
            } //else {
            // 修改
               /* shopInfo.setShopaddress(tar.getShopaddress());
                shopInfo.setShopbusinessarea(tar.getShopbusinessarea());
                shopInfo.setShopname(tar.getShopname());
                shopInfo.setShopopentime(tar.getShopopentime());
                shopInfo.setShopsize(DataType.getAsString(parseDouble(DataType.getAsDouble(tar.getShopsize()))));
                shopInfo.setPrefecture(tar.getPrefecture());
                shopInfo.setProvince(tar.getProvince());
                shopInfo.setRegional(tar.getRegional());
                shopInfo.setStraightJointJoin(tar.getStraightJointJoin());
                shopInfo.setTiers(tar.getTiers());
                shopInfo.setShopseatnum(DataType.getAsInt(tar.getSeating()));
                shopInfo.setKitchenArea(tar.getKitchenArea());
                shopInfo.setStatus(tar.getStatus());
                shopInfo.setChineseName(tar.getChineseName());
                shopInfo.setUpdateTime(tar.getUpdateTime());
                shopInfo.setShopnum(tar.getShopnum());
                shopInfo.setChineseAddress(tar.getChineseAddress());
                shopInfo.setBusinessCircle(tar.getBusinessCircle());*/
            //shopInfo.setEmail("bk"+tar.getShopnum()+"@bkchina.cn");
            //   storeRepository.save(shopInfo);
            // }
            saveArea(tar.getRegional(), tar.getPrefecture());
        }

    }

    private Date parseMonth(String monStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        return sdf.parse(monStr);
    }

    public double parseDouble(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        return DataType.getAsDouble(df.format(d));
    }

    public List<BShopStatisticTemp> buildSStsInfo() {
        List<BShopStatisticTemp> result = new ArrayList<BShopStatisticTemp>();
        StringBuffer sql = new StringBuffer();
        sql.append("  select t.sales,                                     ");
        sql.append("         t.shop_id,                                   ");
        sql.append("         t.shop_name,                                 ");
        sql.append("         t.tc,                                        ");
        if(dbChooser.isSQLServer()){
            sql.append("         convert(datetime, t.month + '01')            ");
        }
        else{
            sql.append("        STR_TO_DATE( CONCAT(t. MONTH,'01')  ,'%Y%m%d')          ");
        }
        sql.append("    from bk_shop_statistic_TEMP t                     ");
        List<Object[]> list = common.queryBySql(sql.toString(), new ArrayList<>());
        if (!CollectionUtils.isEmpty(list)) {
            BShopStatisticTemp entity = null;
            for (Object[] obj : list) {
                entity = new BShopStatisticTemp();
                entity.setSales(DataType.getAsDouble(obj[0]));
                entity.setShopId(DataType.getAsString(obj[1]));
                entity.setShopName(DataType.getAsString(obj[2]));
                entity.setTc(DataType.getAsLong(obj[3]));
                entity.setMonth(DataType.getAsString(obj[4]));
                result.add(entity);
            }
        }
        return result;
    }

    public void updateStoreStsInfo() throws ParseException {
        List<BShopStatisticTemp> list = buildSStsInfo();
        for (BShopStatisticTemp temp : list) {
            BShopStatistic sSts = sStsRes.findByShopIdAndMonth(temp.getShopId(), parseMonth(temp.getMonth()));
            if (sSts == null) {
                // 新增
                BShopStatistic entity = new BShopStatistic();
                entity.setMonth(parseMonth(temp.getMonth()));
                entity.setSales(parseDouble(temp.getSales()));
                entity.setShopId(temp.getShopId());
                entity.setShopName(temp.getShopName());
                entity.setTc(temp.getTc());
                sStsRes.save(entity);
            } else {
                // 修改
                sSts.setMonth(parseMonth(temp.getMonth()));
                sSts.setSales(parseDouble(temp.getSales()));
                sSts.setShopId(temp.getShopId());
                sSts.setShopName(temp.getShopName());
                sSts.setTc(temp.getTc());
                sStsRes.save(sSts);
            }
        }
    }

    @Override
    public BShopInfo queryEntityById(Integer id) {
        return storeRepository.findOne(id);
    }

    @Override
    public BShopInfo findByShopNum(String shopNum) {
        return storeRepository.findByShopnum(shopNum);
    }

    public void saveArea(String regional, String prefecture) {
        if (StringUtils.isNotBlank(regional)) {
            //大区
            List<BArea> areaList = areaRes.findByArea(regional);
            BArea reEntity = new BArea();
            if (!CollectionUtils.isEmpty(areaList)) {
                //存在不做处理
                reEntity = areaList.get(0);
            } else {
                reEntity = new BArea();
                reEntity.setArea(regional);
                reEntity.setParent(null);
                areaRes.save(reEntity);
            }
            if (StringUtils.isNotBlank(prefecture)) {
                //市
                List<BArea> preList = areaRes.findByArea(prefecture);
                BArea preEntity = new BArea();
                if (!CollectionUtils.isEmpty(preList)) {
                    //存在不做处理
                } else {
                    preEntity = new BArea();
                    preEntity.setArea(prefecture);
                    preEntity.setParent(reEntity);
                    areaRes.save(preEntity);
                }
            }

        }
    }

    public List<SimpleSignInfoVO> getLastThreeSignInfoByShopNum(final String shopNum) {
        Map<String, String> params = new HashMap<>();
        params.put("shop_num", shopNum);
        String s = "select si.sign_in_time,u.id user_id ,u.`name` user_name,sp.id shop_id,sp.shop_num\n" +
                "from  bk_sign_info si left join bk_shop_info sp \n" +
                "on sp.id=si.shop_id \n" +
                "left join bk_user u\n" +
                "on si.user_id=u.id\n" +
                "where sp.shop_num= :shop_num\n" +
                "order by sign_in_time DESC\n" +
                "limit 3";
        List<SimpleSignInfoVO> simpleSignInfoVOList = namedParameterJdbcTemplate.query(s, params,
                new BeanPropertyRowMapper<>(SimpleSignInfoVO.class));
        return simpleSignInfoVOList;
    }

    public Map<String, Object> findStoreMapBaseInfo(final String shopNum) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(coreJdbcTemplate);
        Map<String, Object> map = new HashMap<>();
        map.put("i_shop_num", shopNum);
        Map<String, Object> outValues = simpleJdbcCall.withProcedureName("getBaseInfo").declareParameters(
                new SqlParameter("i_shop_num", Types.VARCHAR),
                new SqlOutParameter("o_sale_mtd", Types.VARCHAR),
                new SqlOutParameter("o_sale_comp_mtd", Types.VARCHAR),
                new SqlOutParameter("o_tc_comp_mtd", Types.VARCHAR),
                new SqlOutParameter("o_sale_rank_mtd", Types.VARCHAR),
                new SqlOutParameter("o_tc_rank_mtd", Types.VARCHAR),
                new SqlOutParameter("o_sale_ytd", Types.VARCHAR),
                new SqlOutParameter("o_sale_comp_ytd", Types.VARCHAR),
                new SqlOutParameter("o_tc_ytd", Types.VARCHAR),
                new SqlOutParameter("o_tc_comp_ytd", Types.VARCHAR),
                new SqlOutParameter("o_sale_rank_ytd", Types.VARCHAR),
                new SqlOutParameter("o_tc_rank_ytd", Types.VARCHAR),
                new SqlOutParameter("o_REV_BS", Types.VARCHAR),
                new SqlOutParameter("o_REV_FS", Types.VARCHAR),
                new SqlOutParameter("o_Guest_Trac_NPS", Types.VARCHAR),
                new SqlOutParameter("o_Guest_Trac_RANK", Types.VARCHAR),
                new SqlOutParameter("o_nps_signed", Types.VARCHAR),
                new SqlOutParameter("o_rev_over_grade", Types.VARCHAR),
                new SqlOutParameter("o_CASH_AUDIT", Types.VARCHAR)
        ).execute(map);
        Map<String, Object> returnMap = new HashMap<>();
        if (outValues != null) {
            outValues = Maps.transformEntries(outValues, new Maps.EntryTransformer<String, Object, Object>() {
                @Override
                public Object transformEntry(String s, Object o) {
                    if (o == null || "".equals(o)) {
                        return "N/A";
                    }
                    return o;
                }
            });
            returnMap.putAll(outValues);
        }
        List<SimpleSignInfoVO> lastSignInfos = getLastThreeSignInfoByShopNum(shopNum);

        returnMap.put("lastSignInfos", lastSignInfos);

        return returnMap;
    }
@Override
    public List<Map<String,Object>> listShop4Combobox(){
      String sql="select id,shop_name,shop_num  from bk_shop_info  ";
      return  namedParameterJdbcTemplate.queryForList(sql,new HashMap());
    }
}
