package com.upbest.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 
 * @ClassName   类  名   称：	PasswordUtil.java
 * @Description 功能描述：	
 * @author      创  建   者：	<A HREF="jhzhao@upbest-china.com">jhzhap</A>	
 * @date        创建日期：	2014年9月13日下午9:04:34
 */
public class PasswordUtil {

    public static String genPassword(String password) {
        return DigestUtils.md5Hex(password);
    }

}
