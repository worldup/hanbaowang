package com.upbest.mvc.service;

import com.upbest.exception.FacilityNotMatchException;
import com.upbest.exception.UsernameAndPasswordNotMatchException;

public interface IFacilityLoginService {
	public static final int SUCCESS = 1;
	public static final int ERROR = 0;
	
	/**
	 * 验证设备登陆信息
	 * @param facilityId
	 * @param username
	 * @param password
	 * @return
	 * @throws UsernameAndPasswordNotMatchException
	 * @throws FacilityNotMatchException
	 */
	public int verify(String facilityId,String username,String password) throws UsernameAndPasswordNotMatchException,FacilityNotMatchException;

	public Object findFacilityLoginInfo(String userName);
}
