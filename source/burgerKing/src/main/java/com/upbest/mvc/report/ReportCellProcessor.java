package com.upbest.mvc.report;

import net.sf.jxls.parser.Cell;
import net.sf.jxls.processor.CellProcessor;

import java.util.Map;

/**
 * Created by li_li on 2015/3/21.
 */
public class ReportCellProcessor implements CellProcessor {
    @Override
    public void processCell(Cell cell, Map map) {
        if(cell.getPoiCellValue().contentEquals(".evidence")){

        }
        System.out.println(cell);
        System.out.println(map);
    }
}
