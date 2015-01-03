package com.upbest.mvc.vo;

import java.util.List;

public class MapVO extends BShopInfoVO {

	private static final long serialVersionUID = 1L;
	// 对应某个用户最近一次签到信息
	private List<BSignInfoVO> latestSignInfo;
	// 对应某个门店最近一次运营结果
	private BshopStatisticVO latestSts;

	public List<BSignInfoVO> getLatestSignInfo() {
        return latestSignInfo;
    }

    public void setLatestSignInfo(List<BSignInfoVO> latestSignInfo) {
        this.latestSignInfo = latestSignInfo;
    }

    public BshopStatisticVO getLatestSts() {
		return latestSts;
	}

	public void setLatestSts(BshopStatisticVO latestSts) {
		this.latestSts = latestSts;
	}

}
