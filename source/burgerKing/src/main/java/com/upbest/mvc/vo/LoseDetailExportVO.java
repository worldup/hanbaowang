package com.upbest.mvc.vo;

import java.util.List;

import com.upbest.utils.DataType;

public class LoseDetailExportVO extends SheetDataVO<LoseDetailVO> {
    
    public LoseDetailExportVO(String sheetName, List<LoseDetailVO> dataList) {
        super(sheetName, dataList);
        setHeads(new String[]{"编号","日期","人员名称","所属角色","门店编号","门店名称","问题描述","图片地址","得分","分值"});
        
}
    @Override
    protected String getValue(LoseDetailVO vo, int col) {
        String val = "";
        switch (col) {
            case 0:
                val = vo.getId();
                break;
            case 1:
                val = DataType.getAsString(vo.getBeginTime());
                break;
            case 2:
                val = vo.getRealName();
                break;
            case 3:
                val = vo.getRole();
                break;
            case 4:
                val = vo.getShopNum();
                break;
            case 5:
                val = vo.getShopName();
                break;
            case 6:
                val =vo.getDesc();
                break;
            case 7:
                val = vo.getPic();
                break;
            case 8:
                val=vo.getTestValue();
                break;
            case 9:
                val = vo.getQuesValue();
                break;
            default:
                val = "";
                break;
        }
        return val;
    }

}
