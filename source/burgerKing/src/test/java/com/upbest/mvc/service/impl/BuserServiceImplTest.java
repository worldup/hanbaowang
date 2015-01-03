package com.upbest.mvc.service.impl;

import static org.junit.Assert.*;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upbest.mvc.repository.factory.FacilityRespository;
import com.upbest.mvc.repository.factory.UserRespository;
import com.upbest.mvc.service.IBuserService;
import com.upbest.mvc.service.IFacilityService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BuserServiceImplTest {
    
    @Autowired
    private IBuserService service;
    
    @Autowired
    private UserRespository dao;

    @Test
    public void testAddUserFromExcel() throws IOException, Exception {
        long  oldCount = dao.count();
        service.addUserFromExcel(new ClassPathResource("user.xlsx").getFile());
        
        long  newCount = dao.count();
        Assert.assertEquals(oldCount + 6, newCount);
    }

}
