package com.upbest.mvc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upbest.exception.FacilityNotMatchException;
import com.upbest.exception.UsernameAndPasswordNotMatchException;
import com.upbest.mvc.entity.BFacility;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.service.IBuserService;
import com.upbest.mvc.service.IFacilityLoginService;
import com.upbest.mvc.service.IFacilityService;
import com.upbest.mvc.service.IMessageService;
import com.upbest.mvc.vo.ShopVO;
import com.upbest.utils.RoleFactory;

@Service
public class FacilityLoginServiceImpl implements IFacilityLoginService {
	
	@Autowired
	private IBuserService userService;
	
	@Autowired
    private IMessageService messageService;
	
	@Autowired
	private IFacilityService facilityService;
	
	public static final String USERNAME = "name";
	public static final String USER_REALNAME = "realname";
	public static final String USERID = "userId";
	public static final String EMP_NO = "emp";
	public static final String ROLE = "role";
	public static final String ROLE_ID = "roleId";
	public static final String SHOP = "shop";
	public static final String MESSAGE = "message";
	public static final String HEADIMAGE = "pic";
	public static final String TELEPHONE = "telephone";
	
	@Override
	public int verify(String facilityId, String username, String password)
			throws UsernameAndPasswordNotMatchException,
			FacilityNotMatchException {
		Buser user = userService.findByUsernameAndPassword(username, password);
		if(user == null){
			throw new UsernameAndPasswordNotMatchException();
		}
       boolean match=  userService.isFacilityMatch(facilityId,user.getId());
        if(!match){
            throw new FacilityNotMatchException();
        }
		/* BFacility facility = facilityService.queryByDevIdAndUser(facilityId, user);
		if(facility == null){
			throw new FacilityNotMatchException();
		}*/
		return SUCCESS;
	}
	
	@Override
	public Object findFacilityLoginInfo(String userName) {
		Map<String, Object> map = new HashMap<String, Object>();
		Buser user = userService.findByName(userName);
		
		List<ShopVO> shopList = userService.findShopInfosByUsername(userName);
		List<String> shaopNameList = new ArrayList<String>();
		for (ShopVO shopInfo : shopList) {
			shaopNameList.add(shopInfo.getShopname());
		}
		
		map.put(USERID,user.getId());
		map.put(USERNAME, user.getName());
		map.put(USER_REALNAME, user.getRealname());
		map.put(ROLE_ID,user.getRole());
		map.put(ROLE, RoleFactory.getRoleName(user.getRole()));
		map.put(EMP_NO, user.getEmp());
		map.put(MESSAGE, messageService.getCountByUserId(user.getId()));
		map.put(HEADIMAGE, user.getPic());
		map.put(SHOP, shopList);
		map.put(TELEPHONE, user.getTelephone());
		return map;
	}
}
