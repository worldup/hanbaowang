package com.upbest.mvc.service.impl;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upbest.mvc.repository.factory.FacilityRespository;
import com.upbest.mvc.service.IFacilityService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class FacilityServiceImplTest {
	
	@Autowired
	private IFacilityService service;
	
	@Autowired
	private FacilityRespository dao;

	@Test
	public void testImportExcel() throws Exception {
		long  oldCount = dao.count();
		service.addFacilityFromExcel(new ClassPathResource("facility.xlsx").getFile());
		
		long  newCount = dao.count();
		Assert.assertEquals(oldCount + 12, newCount);
	}

}
