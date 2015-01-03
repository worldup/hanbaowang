package com.upbest.utils;

import java.util.HashMap;
import java.util.Map;

public class RoleFactory {
	public static final Map<String, String> roles = new HashMap<String, String>();

	public static final String ADMIN = "0";
	public static final String OM = "1";
	public static final String OC = "2";
	public static final String OFFICER = "3";
	
	public static final String ROLE_NAME_OC = "OC";
	public static final String ROLE_NAME_OM = "OM";
	public static final String ROLE_NAME_OM_P = "OM+";
	public static final String ROLE_NAME_ADMIN = "超级管理员";
	
	static{
		roles.put(ADMIN, ROLE_NAME_ADMIN);
		roles.put(OM, ROLE_NAME_OM);
		roles.put(OC, ROLE_NAME_OC);
		roles.put(OFFICER, ROLE_NAME_OM_P);
		
		roles.put("超级管理员", ADMIN);
		roles.put("OM", OM);
		roles.put("OC", OC);
		roles.put("OM+", OFFICER);
	}
	
	public static String getRoleName(String roleId){
		return roles.get(roleId);
	}
	
	public static String getRoleId(String roleName){
		return roles.get(roleName.toUpperCase());
	}
}
