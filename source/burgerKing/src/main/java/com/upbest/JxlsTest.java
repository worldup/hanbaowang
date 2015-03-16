package com.upbest;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by li_li on 2015/3/16.
 */
public class JxlsTest {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        Map beans = new HashMap();
        beans.put("name", "aaaaa");
        beans.put("bb", "1111");
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS("d:\\ccc.xlsx", beans, "d:\\ccc1.xlsx");
    }
}
