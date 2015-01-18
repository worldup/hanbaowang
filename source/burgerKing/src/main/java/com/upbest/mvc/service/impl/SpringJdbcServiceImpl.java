package com.upbest.mvc.service.impl;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.service.ISpringJdbcService;
import com.upbest.mvc.vo.BShopInfoVO;
import com.upbest.mvc.vo.BuserVO;
import com.upbest.utils.DataType;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by lili on 15-1-15.
 */
@Service
public class SpringJdbcServiceImpl implements ISpringJdbcService {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    private  void listChildren(List<BuserVO> buserList,final String role,final String userId,List<BuserVO> resultList){
        if("0".equals(role)){
            resultList.addAll(buserList);
        }
        else{
            if (!CollectionUtils.isEmpty(buserList)) {
                if (StringUtils.isNotBlank(userId)) {

                    Collection<BuserVO> result = Collections2.filter(buserList, new Predicate<BuserVO>() {
                        @Override
                        public boolean apply(BuserVO buser) {
                            if(StringUtils.isNotBlank(role)){
                                return userId.equals(ConvertUtils.convert(buser.getPid()))&&role.equals(buser.getRole());
                            }
                            else{
                                return userId.equals(ConvertUtils.convert(buser.getPid()));
                            }
                        }
                    });
                    if(!CollectionUtils.isEmpty(result))
                    {
                        resultList.addAll(result);
                        for(BuserVO vo:result){
                            listChildren(buserList,"",vo.getId(),resultList);
                        }
                    }

                } else {
                    if (StringUtils.isNotBlank(role)) {
                        Collection<BuserVO> result = Collections2.filter(buserList, new Predicate<BuserVO>() {
                            @Override
                            public boolean apply(BuserVO buser) {
                                return role.equals(buser.getRole());
                            }
                        });
                        if(!CollectionUtils.isEmpty(result))
                        {
                            resultList.addAll(result);
                            for(BuserVO vo:result){
                                listChildren(buserList,"",vo.getId(),resultList);
                            }

                        }
                    }
                    else{
                        resultList.addAll(buserList) ;
                    }
                }
            }
        }

    }
    public List<BuserVO> listUsersByParentUserId(final String role, final String userId) {
        List<BuserVO> buserList = jdbcTemplate.query("select u.* from bk_user u where is_del=1", new HashMap(), new BeanPropertyRowMapper(BuserVO.class));
        List<BuserVO> result=new ArrayList();
        listChildren(buserList,role,userId,result);
        return result;
    }

    public List<BShopInfoVO> listShopInfo(String regional , String chineseName,  String queryUserRole ,String queryUserId  ) {

        StringBuilder sql = new StringBuilder();

        List<BuserVO> paramsBuser=listUsersByParentUserId(queryUserRole,queryUserId);
        List<String>  idLists=Lists.transform(paramsBuser, new Function<BuserVO, String>() {
            @Override
            public String apply(BuserVO buserVO) {
                return buserVO.getId();
            }
        });
        Map<String,Object> paramsMap=new HashMap();

        sql.append("  SELECT distinct t.*   FROM bk_shop_info t    " );
        if(!CollectionUtils.isEmpty(idLists)){
            paramsMap.put("idLists",idLists);
           sql.append("left join bk_shop_user    u on t.id=u.shop_id where u.user_id in(:idLists)       ") ;
        }
        else{
            sql.append("where 1=1 ");
        }
        if(StringUtils.isNotBlank(regional)){
            paramsMap.put("regional",regional);
            sql.append("and t.regional=:regional");
        }
        if(StringUtils.isNotBlank(chineseName)){
            paramsMap.put("chineseName",chineseName);
            sql.append("and t.chinese_name like '%:chineseName%'");
        }
        sql.append(" order by t.shop_num  ");
        return   jdbcTemplate.query(sql.toString(), paramsMap, new BeanPropertyRowMapper(BShopInfoVO.class));

    }
}
