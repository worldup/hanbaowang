package com.upbest.mvc.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.entity.BShopStatistic;
import com.upbest.mvc.repository.factory.StoreRespository;
import com.upbest.mvc.repository.factory.StoreStatisticRespository;
import com.upbest.utils.DataType;

@Component
public class StoreStatisticHandler implements Handler {
	
	@Autowired
	private StoreStatisticRespository respository;
	
	@Autowired
	private StoreRespository shopRespository;
	
	@Override
	public boolean handerRow(String[] rows) throws Exception {
		if(rows != null){
			BShopStatistic statistic = buildStatistic(rows);
			if(statistic == null){
				return false;
			}
			respository.save(statistic);
		}
		
		return true;
	}

	private BShopStatistic buildStatistic(String[] rows) throws ParseException {
		if(rows == null || rows.length < 7){
			rows = fillRows(rows,7);
		}
		String shopId = DataType.getAsString(rows[0]);
		if(shopId.startsWith("0")){
			shopId = shopId.substring(1);
		}
		BShopInfo shopInfo = shopRespository.findByShopnum(shopId);
		if(shopInfo == null){
			return null;
		}
		
		Date month = parseMonth(rows[1]);
		
		BShopStatistic shopStatistic = getShopStatistic(shopId, month);
		
		shopStatistic.setShopId(shopId);
		if(!StringUtils.isEmpty(rows[2])){
			shopStatistic.setGtNps(rows[2]);
		}
		if(!StringUtils.isEmpty(rows[3])){
			shopStatistic.setRank(rows[3]);
		}
		if(!StringUtils.isEmpty(rows[4])){
			shopStatistic.setRevBs(rows[4]);
		}
		if(!StringUtils.isEmpty(rows[5])){
			shopStatistic.setRevFs(rows[5]);
		}
		if(!StringUtils.isEmpty(rows[6])){
			shopStatistic.setCashAudit(rows[6]);
		}
		
		return shopStatistic;
	}

	private String[] fillRows(String[] rows, int l) {
		String[] filledRows = new String[l];
		int ol = rows.length;
		for(int i = 0;i < l;i++){
			if(i < ol){
				filledRows[i] = rows[i];
			}else{
				filledRows[i] = "";
			}
		}
		return filledRows;
	}

	private Date parseMonth(String monStr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		
		return sdf.parse(monStr);
	}

	private BShopStatistic getShopStatistic(String shopId, Date month) {
		BShopStatistic statistic = respository.findByShopIdAndMonth(shopId, month);
		if(statistic == null){
			statistic = new BShopStatistic();
		}
		return statistic;
	}
}
