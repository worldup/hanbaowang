package com.upbest.mvc.service.impl;

import java.io.File;
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

import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.handler.UserHandler;
import com.upbest.mvc.repository.factory.UserRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.IBuserService;
import com.upbest.mvc.vo.BuserVO;
import com.upbest.mvc.vo.SelectionVO;
import com.upbest.mvc.vo.ShopVO;
import com.upbest.mvc.vo.TreeVO;
import com.upbest.utils.DataType;
import com.upbest.utils.ExcelUtils;
import com.upbest.utils.PasswordUtil;
import com.upbest.utils.RoleFactory;

@Service
public class BuserServiceImpl implements IBuserService {

    @Inject
    protected UserRespository buserRepository;
    @Autowired
    private CommonDaoCustom<Object[]> common;
    @Autowired
    private UserHandler userHandler;
    
    /*@Autowired
    private IBuserService userService;*/

    public Buser findByName(String name) {
        if(buserRepository.findByNameAndIsdel(name, "1")==null){
            return new Buser();
        }
        return buserRepository.findByNameAndIsdel(name, "1");
    }

    public Page<Buser> findUserList(String name, String isDel, Pageable pageable) {
        return buserRepository.findByNameLikeAndIsdel(name, isDel, pageable);
    }

    public Buser findById(Integer id) {
        return buserRepository.findOne(id);
    }

    @Override
    public Buser insertBuser(Buser user) {
        return buserRepository.save(user);
    }

    @Override
    public Buser upBuser(Buser user) {
        return buserRepository.save(user);
    }

    @Override
    public void delBuser(Integer id) {
        Buser user = buserRepository.findOne(id);
        user.setIsdel("0");
        // buserRepository.delete(user);
        buserRepository.save(user);
    }
    @Override
     public  Page<Object[]> findUserListByParams(String name,String role,String pidName,String region, Pageable requestPage){
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  SELECT t.id,                                              ");
        sql.append("          t.create_date,                                    ");
        sql.append("  t.role,                                                   ");
        sql.append("  t.modify_date,                                            ");
        sql.append("  t.name,                                                   ");
        sql.append("  t.pwd,                                                    ");
        sql.append("  t.real_name a,                                              ");
        sql.append("  t.telephone,                                              ");
        sql.append("  t.is_del,                                                 ");
        sql.append("  t2.real_name b,                                             ");
        sql.append("  t.emp                                             ");
        sql.append("   FROM bk_user t left join bk_user t2 on t.pid=t2.id       ");
        sql.append("   where 1 = 1   and t.is_del='1' and t.name!='admin'        ");
        //sql.append(" and t.role!='0' ");
        if (StringUtils.isNotBlank(name)) {
            sql.append(" and t.real_name like ?");
            params.add("%" + name + "%");
        }
        if(StringUtils.isNotBlank(role)&&!"-1".equals(role)){
            sql.append(" and t.role = ?");
            params.add(role);
        }
        if(StringUtils.isNotBlank(region)&&!"0".equals(region)){
            sql.append(" and t.area_id = ?");
            params.add(region);
        }
        if(StringUtils.isNotBlank(pidName)){
            sql.append(" and t.pid IN  (SELECT id FROM bk_user WHERE real_name LIKE ? )");
            params.add("%" + pidName + "%");
        }
        return common.queryBySql(sql.toString(), params, requestPage);
    }
    @Override
    public Page<Object[]> findUserList(String name, Pageable requestPage) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  SELECT t.id,                                              ");
        sql.append("          t.create_date,                                    ");
        sql.append("  t.role,                                                   ");
        sql.append("  t.modify_date,                                            ");
        sql.append("  t.name,                                                   ");
        sql.append("  t.pwd,                                                    ");
        sql.append("  t.real_name a,                                              ");
        sql.append("  t.telephone,                                              ");
        sql.append("  t.is_del,                                                 ");
        sql.append("  t2.real_name b,                                             ");
        sql.append("  t.emp                                             ");
        sql.append("   FROM bk_user t left join bk_user t2 on t.pid=t2.id       ");
        sql.append("   where 1 = 1   and t.is_del='1' and t.name!='admin'        ");
        //sql.append(" and t.role!='0' ");
        if (StringUtils.isNotBlank(name)) {
            sql.append(" and t.real_name like ?");
            params.add("%" + name + "%");
        }
        return common.queryBySql(sql.toString(), params, requestPage);
    }

    public List<SelectionVO> getUserList(String role) {
        List<SelectionVO> result = new ArrayList<SelectionVO>();
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append(" select t.id,t.real_name from bk_user t  where t.role='" + role + "' and t.is_del='1'                    ");
        List<Object[]> list = common.queryBySql(sql.toString(), params);
        SelectionVO vo = null;
        for (Object[] obj : list) {
            vo = new SelectionVO();
            vo.setValue(DataType.getAsString(obj[0]));
            vo.setName(DataType.getAsString(obj[1]));
            result.add(vo);
        }
        return result;
    }

    public List<BuserVO> getBUserList(Integer userId, String name,String flag) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("   select t.id,              ");
        sql.append("          t.create_date,     ");
        sql.append("          t.is_del,          ");
        sql.append("          t.modify_date,     ");
        sql.append("          t.name,            ");
        sql.append("          t.pid,             ");
        sql.append("          t.pwd,             ");
        sql.append("          t.real_name,       ");
        sql.append("          t.role             ");
        sql.append("     from bk_user t          ");
        sql.append("    where 1 = 1              ");
        sql.append("     and t.is_del= '1'            ");
        sql.append(" and t.role !='0'   ");
        Buser user = buserRepository.findOne(userId);
        if("1".equals(user.getRole())){
            //OM
            sql.append(" and t.pid=? ");
            params.add(userId);
        }else if("3".equals(user.getRole())){
            //OM+，加载所有OM及OC
            sql.append(" and ( t.role=1 or t.role=2 ) ");
        }
       
      /*  if("1".equals(flag)){
            sql.append("  and t.role = 2 ");
        }
        
        if (userId != null && !"0".equals(user.getRole())&&!"3".equals(user.getRole())) {
            sql.append(" and (t.pid=? or t.id=?)            ");
            params.add(userId);
            params.add(userId);
        }*/
        if (StringUtils.isNotBlank(name)) {
            sql.append(" and t.name like ? ");
            params.add("%" + name + "%");
        }
        List<Object[]> objList = common.queryBySql(sql.toString(), params);
        List<BuserVO> userList = getUserInfo(objList);
        userList = doGetChildList(userList);
        return userList;
    }
    
    
    public List<BuserVO> getBUser(Integer userId, String name) {
        StringBuffer sql = new StringBuffer();
        Buser buser=buserRepository.findOne(userId);
        List<Object> params = new ArrayList<Object>();
        sql.append("   select t.id,              ");
        sql.append("          t.create_date,     ");
        sql.append("          t.is_del,          ");
        sql.append("          t.modify_date,     ");
        sql.append("          t.name,            ");
        sql.append("          t.pid,             ");
        sql.append("          t.pwd,             ");
        sql.append("          t.real_name,       ");
        sql.append("          t.role             ");
        sql.append("     from bk_user t          ");
        sql.append("    where 1 = 1   and t.is_del='1'           ");
        sql.append(" and t.role !='0' ");
        if("0".equals(buser.getRole())){
            sql.append("   and t.role = '2'     ");
        }
        List<Object[]> objList = common.queryBySql(sql.toString(), params);
        List<BuserVO> userList = getUserInfo(objList);
        userList = doGetChildList(userList);
        return userList;
    }
    public List<BuserVO> getBUserMessage(Integer userId, String name) {
        StringBuffer sql = new StringBuffer();
        Buser buser=buserRepository.findOne(userId);
        List<Object> params = new ArrayList<Object>();
        sql.append("   select t.id,              ");
        sql.append("          t.create_date,     ");
        sql.append("          t.is_del,          ");
        sql.append("          t.modify_date,     ");
        sql.append("          t.name,            ");
        sql.append("          t.pid,             ");
        sql.append("          t.pwd,             ");
        sql.append("          t.real_name,       ");
        sql.append("          t.role             ");
        sql.append("     from bk_user t          ");
        sql.append("    where 1 = 1   and t.is_del='1'           ");
        sql.append("   and t.role in('1','2','3')     ");
        List<Object[]> objList = common.queryBySql(sql.toString(), params);
        List<BuserVO> userList = getUserInfo(objList);
        userList = doGetChildList(userList);
        return userList;
    }

    public List<BuserVO> getTreeUserList(Integer userid){
        List<BuserVO> userList = getBUser(userid, "");
        List<BuserVO> rl = new ArrayList<BuserVO>();
        List<BuserVO> list = new ArrayList<BuserVO>();
        for (BuserVO obj : userList) {
            if (DataType.getAsString(userid).equals(obj.getId())) {
                list.add(obj);
            }
        }
        getByChildList(list, rl);
        return rl;
        
    }
    
    public List<BuserVO> getTreeUserListStore(Integer userid){
        List<BuserVO> userList = getBUser(userid, "");
        List<BuserVO> rl = new ArrayList<BuserVO>();
        List<BuserVO> list = new ArrayList<BuserVO>();
        for (BuserVO obj : userList) {
            if (DataType.getAsString(userid).equals(obj.getId())) {
                list.add(obj);
            }
        }
        getByChildList(list, rl);
        //add by xubin 2014-9-23
        for(int i=0; i<rl.size(); i++){
            //筛除om
            if(rl.get(i) != null){
                if("1".equals(rl.get(i).getRole())){
                    rl.remove(i);
                }
            }
        }
        //end
        return rl;
        
    }
    
    public List<TreeVO> getUrVOList(Integer userid) {
        List<TreeVO> result = new ArrayList<TreeVO>();
        List<BuserVO> userList = getBUser(userid, "admin");
        Buser user = buserRepository.findOne(userid);
        if (userid != null && !"0".equals(user.getRole())) {
            result = getTreeList(getTreeUserListStore(userid));
        } else {
            result = getTreeList(userList);
        }
        return result;
    }
    
    public List<TreeVO> getUrVOListMessage(Integer userid) {
        List<TreeVO> result = new ArrayList<TreeVO>();
        List<BuserVO> userList = getBUserMessage(userid, "");
          Buser user = buserRepository.findOne(userid);
      /*  if (userid != null && !"0".equals(user.getRole())) {

            List<BuserVO> rl = new ArrayList<BuserVO>();
            List<BuserVO> list = new ArrayList<BuserVO>();
            for (BuserVO obj : userList) {
                if (DataType.getAsString(userid).equals(obj.getId())) {
                    list.add(obj);
                }
            }
            getByChildList(list, rl);
            result = getTreeList(rl);
        } else {*/
            result = getTreeList(userList);
        //}
        return result;
    }

    public void getByChildList(List<BuserVO> list, List<BuserVO> result) {
        for (BuserVO vo : list) {
            if (vo != null) {
                if (vo.getChildList() != null) {
                    if (vo.getChildList().size() > 0) {
                        result.addAll(vo.getChildList());
                        getByChildList(vo.getChildList(), result);
                    }
                }
            }
        }
    }

    /**
     * 
     * @Title            函数名称：   getTreeList
     * @Description      功能描述：   获取树状结构用户列表
     * @param            参          数：   
     * @return          返  回   值：    List<TreeVO>  
     * @throws
     */
    public List<TreeVO> getTreeList(List<BuserVO> list) {
        List<TreeVO> result = new ArrayList<TreeVO>();
        if (!CollectionUtils.isEmpty(list)) {
            TreeVO vo = null;
            for (BuserVO obj : list) {
                if (null != obj) {
                    vo = new TreeVO();
                    if (StringUtils.isNotBlank(obj.getPid())) {
                        vo.setParent(true);
                    } else {
                        vo.setParent(false);
                    }
                    vo.setId(obj.getId());
                    vo.setName(obj.getRealname());
                    if (obj.getPid() == null) {
                        vo.setPid("0");
                    } else {
                        vo.setPid(obj.getPid());
                    }
                    result.add(vo);
                }
            }
        }
        return result;
    }

    public List<BuserVO> doGetChildList(List<BuserVO> list) {
        if (!CollectionUtils.isEmpty(list)) {
            for (BuserVO obj : list) {
                String id = DataType.getAsString(obj.getId());
                obj.setChildList(getUrList(list, id));
            }
        }
        return list;
    }

    public List<BuserVO> getUrList(List<BuserVO> list, String id) {
        List<BuserVO> result = new ArrayList<BuserVO>();
        if (!CollectionUtils.isEmpty(list)) {
            for (BuserVO obj : list) {
                if (null == obj.getPid()) {
                    result.add(null);
                } else {
                    if (DataType.getAsString(obj.getPid()).equals(id)) {
                        result.add(obj);
                    }
                }
            }
        }
        return result;
    }

    private List<BuserVO> getUserInfo(List<Object[]> list) {
        List<BuserVO> result = new ArrayList<BuserVO>();
        if (!CollectionUtils.isEmpty(list)) {
            BuserVO entity = null;
            for (Object[] obj : list) {
                entity = new BuserVO();
                entity.setId(DataType.getAsString(obj[0]));
                entity.setCreatedate(DataType.getAsString(obj[1]));
                entity.setIsdel(DataType.getAsString(obj[2]));
                entity.setModifydate(DataType.getAsString(obj[3]));
                entity.setName(DataType.getAsString(obj[4]));
                entity.setPid(DataType.getAsString(obj[5]));
                entity.setPwd(DataType.getAsString(obj[6]));
                entity.setRealname(DataType.getAsString(obj[7]));
                entity.setRole(DataType.getAsString(obj[8]));
                result.add(entity);
            }
        }
        return result;
    }

    @Override
    public List<Buser> findAllUser() {
        List<Buser> users = new ArrayList<Buser>();

        Iterable<Buser> iterator = buserRepository.findAllBuser();
        if (iterator != null) {
            for (Buser buser : iterator) {
                users.add(buser);
            }
        }
        return users;

    }

    @Override
    public Buser findByUsernameAndPassword(String username, String password) {
        return buserRepository.findByNameAndPwdAndRoleNot(username, PasswordUtil.genPassword(password),"0");
    }

    @Override
    public List<ShopVO> findShopInfosByUsername(String username) {
        Buser user = buserRepository.findByName(username);
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("   SELECT * from (                                    ");
        sql.append("   SELECT distinct  t.id,                                    ");
        sql.append("         t.shop_address,             ");
        sql.append("         t.shop_name,                ");
        sql.append("         t.shop_business_time,                ");
        sql.append("   t.latitude,t.longitude, ");
        sql.append("   t.shop_num ");
        sql.append("               from bk_shop_info t                                 ");
        if("1".equals(user.getRole())){
            //OM,查询OM及OC下所能巡检的门店
            sql.append(" left join bk_shop_user su on su.shop_id=t.id ");
            sql.append(" left join bk_user ur on ur.id=su.user_id ");
            sql.append("   where 1 = 1                       ");
            sql.append(" and ur.pid=? ");
            params.add(user.getId());
        }else if("2".equals(user.getRole())){
            //OC
            sql.append(" left join bk_shop_user su on su.shop_id=t.id ");
            sql.append(" left join bk_user ur on ur.id=su.user_id ");
            sql.append("   where 1 = 1                       ");
            sql.append(" and ur.id=? ");
            params.add(user.getId());
        }else if("3".equals(user.getRole())){
            //OM+
             sql.append(" left join bk_shop_user su on su.shop_id=t.id ");
             sql.append(" where 1=1 and su.user_id in("+getOCUserListInOMPlus(user.getId())+") ");
        }else{
            //超级管理员，不受限制
            sql.append("   where 1 = 1                       ");
        }
        /*if(user.getRole().equals(RoleFactory.OM)){
            sql.append("               (select t.shop_id from  bk_shop_user t where t.user_id in                              ");
            sql.append("  (select ur.id from bk_user ur where ur.pid=?)) ");
        }else if(user.getRole().equals(RoleFactory.OC)){
            sql.append("               (select t.shop_id from  bk_shop_user t where t.user_id in(?))                              ");
        }else if(user.getRole().equals(RoleFactory.OFFICER)){
            sql.append("               (select t1.shop_id from  bk_shop_user t1 where t1.user_id in                              ");
            sql.append("               (select t2.id from  bk_user t2 where t2.pid in                              ");
            sql.append("               (select t3.id from  bk_user t3 where t3.pid=?)))                              ");
        }
        params.add(user.getId());*/
        sql.append(" ) t ");
        sql.append(" order by t.shop_num ");
        List<Object[]> list = common.queryBySql(sql.toString(), params);
        return buildShopInfo(list);
    }
    
    public String getUserIds(Integer userId){
        String result="";
        List<BuserVO> userVO=getTreeUserList(userId);
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

    private List<ShopVO> buildShopInfo(List<Object[]> list) {
        List<ShopVO> result = new ArrayList<ShopVO>();
        if (list != null) {
            for (Object[] obj : list) {
                ShopVO entity = new ShopVO();
                entity.setId(DataType.getAsInt(obj[0]));
                entity.setShopaddress(DataType.getAsString(obj[1]));
                entity.setShopname(DataType.getAsString(obj[2]));
                entity.setShopbusinesstime(DataType.getAsString(obj[3]));
                entity.setLatitude(DataType.getAsString(obj[4]));
                entity.setLongitude(DataType.getAsString(obj[5]));
                entity.setShopnum(DataType.getAsString(obj[6]));
                result.add(entity);
            }

        }
        return result;
    }

    @Override
    public void addUserFromExcel(File file) throws Exception {
        ExcelUtils.handExcel(1, file, userHandler);
    }
    
	@Override
	public void updateHeadImage(Integer userId, String headPath) {
		Buser user = buserRepository.findOne(userId);
		user.setPic(headPath);
		
		buserRepository.save(user);
	}

    @Override
    public void addUserFromExcel(Resource resource) throws Exception {
        ExcelUtils.handExcel(1, resource, userHandler);
    }
    
    @Override
    public Page<Buser> findUsers(Pageable pageable) {
    	return buserRepository.findAll(pageable);
    }
    
    @Override
    public List<Buser> getOCUserList(Integer userId) {
    	List<Buser> users = new ArrayList<Buser>();
    	
    	Buser user = buserRepository.findOne(userId);
    	String roleName = RoleFactory.getRoleName(user.getRole());
    	if("OC".equals(roleName)){
    		users.add(user);
    		return users;
    	}else if("OM".equals(roleName)){
    		List<Integer> pids = new ArrayList<Integer>();
    		pids.add(userId);
    		users.addAll(buserRepository.findByPidInAndIsdel(pids,"1"));
    	}else if("OM+".equals(roleName)){
    		/*List<Integer> pids = new ArrayList<Integer>();
    		pids.add(userId);
    		List<Buser> omUsers = buserRepository.findByPidInAndIsdel(pids,"1");*/
    	    users = buserRepository.findByRoleAndIsdel("2", "1");
    		/*if(!CollectionUtils.isEmpty(omUsers)){
    			pids.clear();
    			for (Buser buser : omUsers) {
    				pids.add(buser.getId());
				}
    			List<Buser> ocUsers = buserRepository.findByPidInAndIsdel(pids,"1");
    			if(!CollectionUtils.isEmpty(ocUsers)){
    				users.addAll(ocUsers);
    			}
    		}*/
    	}else{
    		//超级管理员
    		users = buserRepository.findAll();
    		
    	}
    	return users;
    }
    
    @Override
    public List<Buser> getSuperiories(Integer userId) {
    	List<Buser> users = new ArrayList<Buser>();
    	
    	Buser user = buserRepository.findOne(userId);
    	
    	Integer pid = user.getPid();
    	if(pid == null){
    		return null;
    	}
    	
    	List<Integer> pids = new ArrayList<Integer>();
    	pids.add(pid);
    	users.addAll(getSuperiories(pids));
    	return users;
    }

    private List<Buser> getSuperiories(List<Integer> userIds) {
    	List<Buser> users = new ArrayList<Buser>();
    	if(!CollectionUtils.isEmpty(userIds)){
    		List<Buser> curUsers = buserRepository.findByIdIn(userIds);
    		
    		List<Integer> pids = new ArrayList<Integer>();
    		if(!CollectionUtils.isEmpty(curUsers)){
    			users.addAll(curUsers);
    			
    			for (Buser user : curUsers) {
    				Integer pid = user.getPid();
    				if(pid != null){
    					pids.add(user.getPid());
    				}
				}
    			
    			users.addAll(getSuperiories(pids));
    		}
    	}
		return users;
	}
    
    @Override
    public Buser getNearestSuper(Integer userId) {
    	Buser puser = null;
    	Buser user = buserRepository.findOne(userId);
    	Integer pid = user.getPid();
    	if(pid == null){
    		return puser;
    	}
    	
    	puser = buserRepository.findOne(pid);
    	return puser;
    }

	@Override
    public int findUserCountByName(String name) {
        return buserRepository.queryCountByUsername(name);
    }

    @Override
    public int findUserCountByEmp(String emp) {
        return buserRepository.queryCountByUseremp(emp);
    }

    @Override
    public String getOCUserListInOMPlus(Integer userId) {
        String result="";
        StringBuffer sql= new StringBuffer();
        sql.append("    select t.id, t.name, t.role                        ");
        sql.append("      from bk_user t                                   ");
        sql.append("     where 1=1                        ");
        /*sql.append(" and t.pid in (select ur.id ");
        sql.append("                       from bk_user ur                 ");
        sql.append("                      where ur.pid = '"+userId+"'      ");
        sql.append("                        and ur.is_del = '1')           ");*/
        sql.append("       and t.is_del = '1'                              ");
        sql.append("       and t.role = '2'                                ");
        List<Object[]> list=common.queryBySql(sql.toString(), new ArrayList<>());
        if(!CollectionUtils.isEmpty(list)){
            for(Object[] obj:list){
               result+=DataType.getAsInt(obj[0])+",";
            }
        }
        if(result.length()>0){
            result=result.substring(0,result.length()-1);
        }
        return result;
        
    }

}
