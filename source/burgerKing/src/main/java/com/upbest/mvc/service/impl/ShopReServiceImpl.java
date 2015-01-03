package com.upbest.mvc.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.upbest.mvc.entity.BShopReport;
import com.upbest.mvc.repository.factory.ShopReRespository;
import com.upbest.mvc.repository.factory.StoreRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.ShopReService;

@Service
public class ShopReServiceImpl implements ShopReService {
    @Autowired
    private CommonDaoCustom<Object[]> common;

    @Inject
    protected ShopReRespository storeRepository;
    
    @Override
    public Page<Object[]> findReList(String reName,Integer shopId, Pageable pageable) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  SELECT s.id,                       ");
        sql.append("  f.shop_name,                       ");
        sql.append(" s.report,                       ");
        sql.append("  u.real_name,                       ");
        sql.append("  s.create_time,                       ");
        sql.append("  s.mon,                      ");
        sql.append("  s.report_type                      ");
        sql.append("  from (select * from bk_shop_report s where s.shop_id = ? ) s                     ");
        sql.append("  left join bk_user u on s.user_id=u.id        ");
        sql.append("  left join bk_shop_info f on s.shop_id=f.id        ");
        sql.append("   where 1=1                       ");
        params.add(shopId);
        
        if (StringUtils.isNotBlank(reName)) {
            sql.append(" and u.real_name like ?");
            params.add("%" + reName + "%");
        }
        return common.queryBySql(sql.toString(), params, pageable);
    }

    @Override
    public void deleteById(Integer id) {
        BShopReport entity=storeRepository.findOne(id);
        storeRepository.delete(entity);
    }
}
