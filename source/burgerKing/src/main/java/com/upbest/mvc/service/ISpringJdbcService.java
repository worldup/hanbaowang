package com.upbest.mvc.service;

import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.vo.BShopInfoVO;
import com.upbest.mvc.vo.BuserVO;

import java.util.List;

/**
 * Created by lili on 15-1-15.
 */
public interface ISpringJdbcService {

    public List<BShopInfoVO> listShopInfo(BShopInfoVO params, String realName, BuserVO user);
    /**
     *获取所有角色下管理的用户
     * @param role 查询的用户角色
     * @param userId 查询的用户ID
     */
    public List<BuserVO> listUsersByParentUserId(String role,String userId);
}
