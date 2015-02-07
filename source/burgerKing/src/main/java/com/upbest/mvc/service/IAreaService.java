package com.upbest.mvc.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.upbest.mvc.entity.BArea;
import com.upbest.mvc.vo.AreaVO;

public interface IAreaService {
	List<BArea> findTopArea();
	List<BArea> findByParent(Integer pId);
	List<AreaVO> findAllAreaInfo();
      Collection<Map<String,String>> findAllRegion ();
}
