package com.upbest.mvc.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.upbest.mvc.entity.BBeacon;
import com.upbest.mvc.entity.BBeaconShop;
import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.handler.BeaconHandler;
import com.upbest.mvc.repository.factory.BeaconRespository;
import com.upbest.mvc.repository.factory.BeaconShopRespository;
import com.upbest.mvc.repository.factory.StoreRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.IBeaconService;
import com.upbest.mvc.vo.BShopInfoVO;
import com.upbest.mvc.vo.BeaconVO;
import com.upbest.mvc.vo.TreeVO;
import com.upbest.utils.ExcelUtils;

@Service
public class BeaconServiceImpl implements IBeaconService {

    @Inject
    private BeaconRespository beaconRespository;

    @Inject
    private StoreRespository storeRespository;

    @Inject
    private BeaconShopRespository beaconShopRespository;

    @Autowired
    private BeaconHandler handler;

    @Autowired
    private CommonDaoCustom<Object[]> common;

    public Page<Object[]> queryBeacon(String beaconName, Pageable pageable) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  SELECT t.id,                       ");
        sql.append("         t.uuid,             ");
        sql.append("         t.major_id,       ");
        sql.append("         t.minor_id,       ");
        sql.append("         s.shop_name,               ");
        sql.append("         s.id as shopId,              ");
        sql.append("         u.id as beaconShopId,              ");
        sql.append("         t.name,       ");
        sql.append("         t.mac,       ");
        sql.append("         t.measurrd_power,       ");
        sql.append("         t.signal_strength,       ");
        sql.append("         t.remaining_power,       ");
        sql.append("         t.last_date,       ");
        sql.append("         t.company,       ");
        sql.append("         t.model,       ");
        sql.append("         t.mold,       ");
        sql.append("         t.distance,       ");
        sql.append("         t.remarks       ");
        sql.append("    FROM BK_BEACON t            ");
        sql.append("    left join BK_BEACON_SHOP u ");
        sql.append("    on  t.id = u.beacon_id           ");
        sql.append("    left join BK_SHOP_INFO s ");
        sql.append("    on  s.id = u.shop_id           ");
        sql.append("    where 1 = 1               ");
        if (StringUtils.isNotBlank(beaconName)) {
            sql.append(" and t.uuid like ?");
            params.add("%" + beaconName + "%");
        }
        return common.queryBySql(sql.toString(), params, pageable);
    }

    @Override
    public List<Object[]> queryBeaconById(Integer id) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  SELECT t.id,                       ");
        sql.append("         t.uuid,             ");
        sql.append("         t.major_id,       ");
        sql.append("         t.minor_id,       ");
        sql.append("         s.shop_name,               ");
        sql.append("         s.id as shopId,              ");
        sql.append("         u.id as beaconShopId,              ");
        sql.append("         t.name,       ");
        sql.append("         t.mac,       ");
        sql.append("         t.measurrd_power,       ");
        sql.append("         t.signal_strength,       ");
        sql.append("         t.remaining_power,       ");
        sql.append("         t.last_date,       ");
        sql.append("         t.company,       ");
        sql.append("         t.model,       ");
        sql.append("         t.mold,       ");
        sql.append("         t.distance,       ");
        sql.append("         t.remarks       ");
        sql.append("    FROM BK_BEACON t            ");
        sql.append("    left join BK_BEACON_SHOP u ");
        sql.append("    on  t.id = u.beacon_id           ");
        sql.append("    left join BK_SHOP_INFO s ");
        sql.append("    on  s.id = u.shop_id           ");
        sql.append("    where 1 = 1               ");
        sql.append("    and t.id = ?        ");
        params.add(id);
        return common.queryBySql(sql.toString(), params);
    }

    @Override
    public void saveBeacon(BBeacon beacon) {
        beaconRespository.save(beacon);
    }

    @Override
    public void deleteBeacon(Integer beaconId) {
        beaconRespository.delete(beaconId);
    }

    //
    @Override
    public List<Object[]> queryBeaconByUuid(String uuid) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  SELECT t.id,                       ");
        sql.append("         t.uuid,             ");
        sql.append("         t.major_id,       ");
        sql.append("         t.minor_id,       ");
        sql.append("    FROM BK_BEACON t            ");
        sql.append("    where t.id = ?        ");
        params.add(uuid);
        return common.queryBySql(sql.toString(), params);
    }

    @Override
    public BBeacon findByUuid(String uuid) {
        return beaconRespository.findByUuid(uuid);
    }

    @Override
    public List<TreeVO> getUrVOList() {
        List<TreeVO> result = new ArrayList<TreeVO>();
        List<BBeacon> beaconList = beaconRespository.findAll();
        result = getTreeList(beaconList);
        return result;
    }

    public List<TreeVO> getTreeList(List<BBeacon> list) {
        List<TreeVO> result = new ArrayList<TreeVO>();
        if (!CollectionUtils.isEmpty(list)) {
            TreeVO vo = null;
            for (BBeacon obj : list) {
                if (null != obj) {
                    vo = new TreeVO();
                    /*if (StringUtils.isNotBlank(obj.getPid())) {
                        vo.setParent(true);
                    } else {
                        vo.setParent(false);
                    }*/
                    vo.setId(obj.getId().toString());
                    vo.setName(obj.getUuid());
                    /*if (obj.getPid() == null) {
                        vo.setPid("0");
                    } else {
                        vo.setPid(obj.getPid());
                    }*/
                    result.add(vo);
                }
            }
        }
        return result;
    }

    public void addBeaconFromExcel(Resource resource) throws Exception {
        ExcelUtils.handExcel(1, resource, handler);
    }

    @Override
    public BShopInfo findStoreByUuid(String uuid) {
        BShopInfo entity = new BShopInfo();
        BBeacon bbeancon = findByUuid(uuid);
        BBeaconShop bshop = new BBeaconShop();
        if (bbeancon != null) {
            bshop = beaconShopRespository.findByBeaconId(bbeancon.getId());
        }
        if(null!=bshop){
            entity=storeRespository.findOne(bshop.getShopId());
        }
        return entity;
    }
}
