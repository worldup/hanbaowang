package com.upbest.mvc.report;

import net.sf.jxls.processor.RowProcessor;
import net.sf.jxls.transformer.Row;

import java.util.Map;

/**
 * Created by li_li on 2015/3/21.
 */
public class ReportRowProcessor implements RowProcessor {
    @Override
    public void processRow(Row row, Map map) {
        System.out.println(row);
        System.out.println(map);
    }
}
