package com.upbest.utils;

import java.util.Comparator;

import com.upbest.mvc.vo.ExamSheetVO;

public class ComparatorExamSts implements Comparator {
    private String sord;
    public ComparatorExamSts(String sord){
        this.sord=sord;
    }
    public int compare(Object arg0, Object arg1) {
        ExamSheetVO btsVO0 = (ExamSheetVO) arg0;
        ExamSheetVO btsVO1 = (ExamSheetVO) arg1;
        if("desc".equalsIgnoreCase(sord)){
            return (btsVO1.getLosingD()>btsVO0.getLosingD()?1:(btsVO1.getLosingD()<btsVO0.getLosingD()?-1:0));
        }else{
            return (btsVO1.getLosingD()>btsVO0.getLosingD()?-1:(btsVO1.getLosingD()<btsVO0.getLosingD()?1:0));
        }
    }

}
