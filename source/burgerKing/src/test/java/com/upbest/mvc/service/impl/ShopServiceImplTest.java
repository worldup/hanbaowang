package com.upbest.mvc.service.impl;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.upbest.mvc.repository.factory.StoreRespository;
import com.upbest.mvc.service.IStoreService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ShopServiceImplTest {
    
    @Autowired
    private IStoreService service;
    
    @Autowired
    private StoreRespository dao;

    @Test
    public void testAddShopFromExcel() throws IOException, Exception {
        long  oldCount = dao.count();
        service.addShopFromExcel(new ClassPathResource("shopinfo.xlsx").getFile());
        
        long  newCount = dao.count();
        Assert.assertEquals(oldCount + 2, newCount);
    }
}
