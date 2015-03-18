package com.upbest;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by li_li on 2015/3/16.
 */
public class JxlsTest {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        ClassPathResource classPathResource=new ClassPathResource("template\\REV.xlsx");
        String path= classPathResource.getFile().getAbsolutePath();

        Map beans = new HashMap();
        Map title=new HashMap();
        title.put("date",new Date().toString());
        title.put("manager","lili");
        title.put("ev","lili");
        title.put("restNo","1234");
        Map body=new HashMap();
        Map b01=new HashMap();
        b01.put("score","22");
        b01.put("Y","11");
        body.put("b01",b01);
        beans.put("title",title);
        beans.put("body",body);
        XLSTransformer transformer = new XLSTransformer();

        transformer.transformXLS(path, beans, "c:\\develop\\ccc1.xlsx");
    }
}
