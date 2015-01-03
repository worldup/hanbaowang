package com.upbest.mvc.service.impl;

import junit.framework.Assert;

import org.aspectj.apache.bcel.classfile.ClassParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upbest.mvc.service.IBSingIfnoService;
import com.upbest.mvc.service.IFacilityLoginService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class FacilityLoginServiceImplTest {
	
	@Autowired 
	IFacilityLoginService service;
	

	@Test
	public void testVerify() {
		int code = service.verify("你好", "zhengqun", "123");
		Assert.assertEquals(1, code);
	}
	

}
