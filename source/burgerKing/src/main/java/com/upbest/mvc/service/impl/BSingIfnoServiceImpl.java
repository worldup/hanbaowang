package com.upbest.mvc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.upbest.exception.BurgerKingException;
import com.upbest.mvc.entity.BSignInfo;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.repository.factory.SingRespository;
import com.upbest.mvc.repository.factory.UserRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.IBSingIfnoService;
import com.upbest.mvc.service.IBuserService;
import com.upbest.mvc.vo.BSignInfoVO;
import com.upbest.mvc.vo.BuserVO;
import com.upbest.utils.DataType;

@Service
public class BSingIfnoServiceImpl implements IBSingIfnoService {
    @Value("${database}")
    private String database;
    @Autowired
    protected SingRespository singRepository;
    
    @Autowired
    private UserRespository userRespository;
    @Autowired
    private CommonDaoCustom<Object[]> common;
    
    @Inject
    private IBuserService userService;

    @Override
    public Page<Object[]> findSingList(String singName, Pageable pageable) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  SELECT s.id,                       ");
        if("SQL_SERVER".equalsIgnoreCase(database)){
            sql.append("         CONVERT(varchar(100), s.sign_in_time, 20) as signInTime,       ");
            sql.append("         CONVERT(varchar(100), s.sign_out_time, 20) as signOutTime,       ");
        }
        else{
            sql.append("         str_to_date(s.sign_in_time ,'%Y-%m-%d %H:%i:%s') as signInTime,       ");
            sql.append("         str_to_date(s.sign_out_time, '%Y-%m-%d %H:%i:%s') as signOutTime,       ");
        }
        sql.append("         s.sign_in_longitude,               ");
        sql.append("         s.sign_in_latitude,                ");
        sql.append("         u.name,                 ");
        sql.append("         s.location                ");
        sql.append("    FROM bk_sign_info s left join bk_user u on u.id = s.user_id           ");
        sql.append("   where 1=1                       ");
        sql.append("   and  u.is_del='1'                     ");
        if (StringUtils.isNotBlank(singName)) {
            sql.append(" and u.name like ?");
            params.add("%" + singName + "%");
        }
        return common.queryBySql(sql.toString(), params, pageable);
    }
    
    private List<BSignInfoVO> querySignInfoList(String role,Integer userId){
        List<BSignInfoVO> result=new ArrayList<BSignInfoVO>();
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("   select t1.id,                                                          ");
        sql.append("          t1.location,                                                    ");
        sql.append("          t1.shop_id,                                                     ");
        sql.append("          t1.sign_in_latitude,                                            ");
        sql.append("          t1.sign_in_longitude,                                           ");
        sql.append("          t1.sign_in_time,                                                ");
        sql.append("          t1.sign_out_time,                                               ");
        sql.append("          t1.user_id,                                                      ");
        sql.append(" ur.real_name ");
        sql.append("     from bk_sign_info as t1                                              ");
        sql.append("    inner join (select t.user_id, max(t.sign_in_time) as signInTime       ");
        sql.append("                  from bk_sign_info t                                     ");
        sql.append("                 group by t.user_id) as t2                                ");
        sql.append("       on t1.user_id = t2.user_id                                         ");
        sql.append("      and t1.sign_in_time = t2.signInTime                                 ");
        sql.append(" left join bk_user ur on ur.id=t1.user_id ");
        sql.append("    where 1=1                                                             ");
        if(role.equals("1")){
            //OM
            sql.append(" and t1.user_id in( select u.id from bk_user u where u.pid=? ) ");
            params.add(userId);
        }else if(role.equals("2")){
            //OC
            sql.append(" and t1.user_id =? ");
            params.add(userId);
        }else if(role.equals("3")){
            //OM+
            sql.append(" and  t1.user_id in("+getUserIdsString(userId)+") ");
        }
        List<Object[]> list=common.queryBySql(sql.toString(), params);
        if(!CollectionUtils.isEmpty(list)){
            BSignInfoVO vo=null;
            for(Object[] obj:list){
                vo=new BSignInfoVO();
                vo.setId(DataType.getAsInt(obj[0]));
                vo.setLocation(DataType.getAsString(obj[1]));
                vo.setShopId(DataType.getAsInt(obj[2]));
                vo.setSigninlatitude(DataType.getAsString(obj[3]));
                vo.setSigninlongitude(DataType.getAsString(obj[4]));
                vo.setSignintime(DataType.getAsDate(obj[5]));
                vo.setSignouttime(DataType.getAsDate(obj[6]));
                vo.setUserid(DataType.getAsInt(obj[7]));
                vo.setRealName(DataType.getAsString(obj[8]));
                result.add(vo);
            }
        }
        return result;
    }
    private List<BSignInfoVO> querySignInfo(String role,Integer userId){
        List<BSignInfoVO> result=new ArrayList<BSignInfoVO>();
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("   select t1.id,                                                          ");
        sql.append("          t1.location,                                                    ");
        sql.append("          t1.shop_id,                                                     ");
        sql.append("          t1.sign_in_latitude,                                            ");
        sql.append("          t1.sign_in_longitude,                                           ");
        sql.append("          t1.sign_in_time,                                                ");
        sql.append("          t1.sign_out_time,                                               ");
        sql.append("          t1.user_id,                                                      ");
        sql.append(" ur.real_name ");
        sql.append("     from bk_sign_info as t1                                              ");
        sql.append(" left join bk_user ur on ur.id=t1.user_id ");
        sql.append("    where 1=1                                                             ");
        if(role.equals("1")){
            //OM
            sql.append(" and t1.user_id in( select u.id from bk_user u where u.pid=? ) ");
            params.add(userId);
        }else if(role.equals("2")){
            //OC
            sql.append(" and t1.user_id =? ");
            params.add(userId);
        }else if(role.equals("3")){
            //OM+
            sql.append(" and  t1.user_id in("+getUserIdsString(userId)+") ");
        }
        List<Object[]> list=common.queryBySql(sql.toString(), params);
        if(!CollectionUtils.isEmpty(list)){
            BSignInfoVO vo=null;
            for(Object[] obj:list){
                vo=new BSignInfoVO();
                vo.setId(DataType.getAsInt(obj[0]));
                vo.setLocation(DataType.getAsString(obj[1]));
                vo.setShopId(DataType.getAsInt(obj[2]));
                vo.setSigninlatitude(DataType.getAsString(obj[3]));
                vo.setSigninlongitude(DataType.getAsString(obj[4]));
                vo.setSignintime(DataType.getAsDate(obj[5]));
                vo.setSignouttime(DataType.getAsDate(obj[6]));
                vo.setUserid(DataType.getAsInt(obj[7]));
                vo.setRealName(DataType.getAsString(obj[8]));
                result.add(vo);
            }
        }
        return result;
    }
    /**
     * 每次签到都是新增一条记录
     */
    @Override
    public void signIn(int shopId,int userid, String lng, String lat,String location) throws BurgerKingException {
    	
    	BSignInfo signInfo = new BSignInfo();
    	signInfo.setUserid(userid);
    	signInfo.setShopId(shopId);
    	signInfo.setSignintime(new Date());
    	signInfo.setSigninlatitude(lat);
    	signInfo.setSigninlongitude(lng);
    	signInfo.setLocation(location);
    	
    	singRepository.save(signInfo);
    }
    
    /**
     * 查询此用户最近一次签到的记录，然后作更新处理
     */
    @Override
    public void signOut(int shopId,int userid)  throws BurgerKingException {
    	List<BSignInfo> list = singRepository.findByUseridAndShopId(userid,shopId, new PageRequest(0, 1,new Sort(Direction.DESC, "signintime")));
    	BSignInfo signInfo = CollectionUtils.isEmpty(list) ? new BSignInfo() : list.get(0);
    	signInfo.setUserid(userid);
    	signInfo.setSignouttime(new Date());
    	signInfo.setShopId(shopId);
    	
    	singRepository.save(signInfo);
    }
    public String getUserIdsString(Integer userId){
        String result="";
        List<BuserVO> userVO=userService.getTreeUserList(userId);
        if(!CollectionUtils.isEmpty(userVO)){
            for(BuserVO obj:userVO){
                if(null!=obj){
                    if(obj.getRole().equals("2")){
                        result+=obj.getId()+",";
                    } 
                }
            }
        }
        if (result.endsWith(",")) {
            result = result.substring(0, result.lastIndexOf(","));
        }
        //edit 2014-9-23 ''-sql中为空
        return result=(result.length() > 0 ? result : "''");
    }
    public List<Integer> getUserIds(Integer userId){
        List<Integer> result=new ArrayList<Integer>();
        List<BuserVO> userVO=userService.getTreeUserList(userId);
        if(!CollectionUtils.isEmpty(userVO)){
            for(BuserVO obj:userVO){
                if(null!=obj){
                    if(obj.getRole().equals("2")){
                        result.add(Integer.parseInt(obj.getId()));
                      //  result+=obj.getId()+",";
                    } 
                }
            }
        }
      /*  if (result.endsWith(",")) {
            result = result.substring(0, result.lastIndexOf(","));
        }*/
        //edit 2014-9-23 ''-sql中为空
        //return result=(result.length() > 0 ? result : "''");
        return result;
    }
    @Override
    public List<BSignInfoVO> getSignInfo(int userid,String isLatest) {
        Buser user=userRespository.findOne(userid);
        if(null!=user){
            if(StringUtils.isNotBlank(isLatest)){
                return querySignInfoList(user.getRole(),user.getId());
             }
             return querySignInfo(user.getRole(),user.getId());
        }
    	return null;
    }

}
