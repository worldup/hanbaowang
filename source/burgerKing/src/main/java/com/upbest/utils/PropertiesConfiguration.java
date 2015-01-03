package com.upbest.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @ClassName   类  名   称：	PropertiesUtils.java
 * @Description 功能描述：	
 * @author      创  建   者：	zhengQun	
 * @date        创建日期：	2013-4-1下午1:42:36
 */
public class PropertiesConfiguration {
    
    private Properties prop = new Properties();
    
    private static final String CLASS_PATH = "classpath:";
    
    private static final Log debugger = LogFactory.getLog(PropertiesConfiguration.class);

    public PropertiesConfiguration(String file) {
        load(file);
    }
    
    public PropertiesConfiguration(URL url) {
        load(url);
    }

    /**
     * @Title 		   	函数名称：	load
     * @Description   	功能描述：	
     * @param 		   	参          数：	
     * @return          返  回   值：	void  
     * @throws
     */
    private void load(URL url) {
        if(null == url)
        {
            debugger.error("指定的资源不存在");
        }
        
        try {
            prop.load(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Title 		   	函数名称：	load
     * @Description   	功能描述：	暂时只支持从classpath上加载
     * @param 		   	参          数：	
     * @return          返  回   值：	void  
     * @throws
     */
    private void load(String fileName) {
        URL url = null;
        if(fileName.startsWith(CLASS_PATH))
        {
            fileName = fileName.substring(CLASS_PATH.length());
            //从类路径下加载资源
            url = this.getClass().getResource("/"+fileName);
        }
        load(url);
    }
    
    public String getString(String key)
    {
        return prop.getProperty(key);
    }
    
    public int getInt(String key)
    {
        return Integer.valueOf(prop.getProperty(key));
    }
    
}
