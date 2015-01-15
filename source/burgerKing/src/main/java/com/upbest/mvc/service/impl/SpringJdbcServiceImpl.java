package com.upbest.mvc.service.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.service.ISpringJdbcService;
import com.upbest.mvc.vo.BShopInfoVO;
import com.upbest.mvc.vo.BuserVO;
import com.upbest.utils.DataType;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lili on 15-1-15.
 */
@Service
public class SpringJdbcServiceImpl implements ISpringJdbcService {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    private List<BuserVO> listChildren(List<BuserVO> buserList,final String role,final String userId){
        if (!CollectionUtils.isEmpty(buserList)) {
            if (StringUtils.isNotBlank(userId)) {

                Collection<BuserVO> result = Collections2.filter(buserList, new Predicate<BuserVO>() {
                    @Override
                    public boolean apply(BuserVO buser) {
                        return userId.equals(ConvertUtils.convert(buser.getPid()));
                    }
                });
                if(CollectionUtils.isEmpty(result))
                {
                    return buserList;
                }
                else{
                    listChildren(buserList,role,userId);
                }

            } else {
                if (StringUtils.isNotBlank(role)) {
                    Collection<BuserVO> result = Collections2.filter(buserList, new Predicate<BuserVO>() {
                        @Override
                        public boolean apply(BuserVO buser) {
                            return role.equals(buser.getRole());
                        }
                    });
                    if(CollectionUtils.isEmpty(result))
                    {
                        return buserList;
                    }
                    else{
                        listChildren(buserList,role,userId);
                    }
                }
                else{
                    return buserList;
                }
            }
        }
        return buserList;
    }
    public List<BuserVO> listUsersByParentUserId(final String role, final String userId) {
        List<BuserVO> buserList = jdbcTemplate.queryForList("select * from bk_user where is_del=1", new HashMap(), BuserVO.class);
        return listChildren(buserList,role,userId);
    }

    public List<BShopInfoVO> listShopInfo(BShopInfoVO params, String realName, BuserVO user) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(params);
        StringBuilder sql = new StringBuilder();
        String queryUserRole = user.getRole();
        String queryUserId = user.getId();
//        Buser user = userService.findById(DataType.getAsInt(userId));
        sql.append("  SELECT distinct t.*   FROM bk_shop_info t              ");

        sql.append(" order by t.shop_num  ");
         return null;
    }
}
