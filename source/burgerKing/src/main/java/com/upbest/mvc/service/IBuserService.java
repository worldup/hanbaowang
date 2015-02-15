package com.upbest.mvc.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.vo.BuserVO;
import com.upbest.mvc.vo.SelectionVO;
import com.upbest.mvc.vo.ShopVO;
import com.upbest.mvc.vo.TreeVO;

public interface IBuserService {
    //获取消息发送用户
    List<Map<String,Object>> getMessageUserList(Integer userId);
    Buser findByName(String userName);
    Buser findById(Integer id);

    Page<Object[]> findUserListByParams(String name,String role,String pidName,String region, Pageable requestPage);
    Page<Object[]> findUserList(String shopName, Pageable requestPage);
    
    Page<Buser> findUsers(Pageable pageable);

    Buser insertBuser(Buser user);

    Buser upBuser(Buser user);

    void delBuser(Integer id);

    List<SelectionVO> getUserList(String role);
    
    List<Buser> findAllUser();

    List<TreeVO> getUrVOList(Integer userid);
    
    List<TreeVO> getUrVOListMessage(Integer userid);
    
    Buser findByUsernameAndPassword(String username,String password);
    
    /**
     * 根据用户名查询门店信息
     * @param username
     * @return
     */
    List<ShopVO> findShopInfosByUsername(String username);
    
    List<BuserVO> getBUserList(Integer userId,String name,String flag);
    
    public void addUserFromExcel(File file) throws Exception;
 
	void updateHeadImage(Integer userId,String headPath);

    public void addUserFromExcel(Resource byteArrayResource) throws Exception;
    
    List<BuserVO> getTreeUserList(Integer userid);
    
    /**
     * 获取词用户下所有的OC用户
     * @param userId
     * @return
     */
    List<Buser> getOCUserList(Integer userId);
    
    /**
     * 查询上级用户
     * @param userId
     * @return
     */
    List<Buser> getSuperiories(Integer userId);
    
    /**
     * 查询最近的一个上级用户
     * @param userId
     * @return
     */
    Buser getNearestSuper(Integer userId);
    
    int findUserCountByName(String name);
    
    int findUserCountByEmp(String emp);
    
   String getOCUserListInOMPlus(Integer userId);

}
