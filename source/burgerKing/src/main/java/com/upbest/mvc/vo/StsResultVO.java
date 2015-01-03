package com.upbest.mvc.vo;

import java.util.ArrayList;
import java.util.List;

public class StsResultVO {
    private String month;

    private List<StsStoreVO> result = new ArrayList<StsStoreVO>();

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<StsStoreVO> getResult() {
        return result;
    }

    public void setResult(List<StsStoreVO> result) {
        this.result = result;
    }

}
