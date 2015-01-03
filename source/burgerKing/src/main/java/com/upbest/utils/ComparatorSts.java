package com.upbest.utils;

import java.util.Comparator;

import com.upbest.mvc.vo.BStsVO;

public class ComparatorSts implements Comparator {
    public int compare(Object arg0, Object arg1) {
        BStsVO btsVO0 = (BStsVO) arg0;
        BStsVO btsVO1 = (BStsVO) arg1;
        if (btsVO1.getTotalD() > btsVO0.getTotalD()) {
            return 1;
        }
        if (btsVO1.getTotalD() < btsVO0.getTotalD()) {
            return -1;
        }
        if (btsVO1.getTotalD() == btsVO0.getTotalD()) {
            return 0;
        }
        return 0;
    }

}
