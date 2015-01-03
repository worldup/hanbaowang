package com.upbest.mvc.vo;

import java.util.List;

import com.upbest.mvc.constant.Constant.QuestionType;

public class ExamExportVO extends SheetDataVO<ExamSheetVO> {
    
    public ExamExportVO(String sheetName, List<ExamSheetVO> dataList) {
        super(sheetName, dataList);
        setHeads(new String[]{"编号","问卷名称","试题编号","试题描述","试题类型","试题所属模块名称","总得分","总分","失分","失分率"});
        
}
    @Override
    protected String getValue(ExamSheetVO vo, int col) {
        String val = "";
        switch (col) {
            case 0:
                val = vo.getId();
                break;
            case 1:
                val = vo.getEname();
                break;
            case 2:
                val = vo.getNum();
                break;
            case 3:
                val = vo.getDescription();
                break;
            case 4:
                val = vo.getQtype();
                break;
            case 5:
                val = vo.getModel();
                break;
            case 6:
                val =vo.getScore();
                break;
            case 7:
                val = vo.getAggregate();
                break;
            case 8:
                val=vo.getPoints();
                break;
            case 9:
                val = vo.getLosing();
                break;
            default:
                val = "";
                break;
        }
        return val;
    }

}
