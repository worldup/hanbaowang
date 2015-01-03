package com.upbest.mvc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.upbest.mvc.entity.BArea;
import com.upbest.mvc.repository.factory.BArearespository;
import com.upbest.mvc.service.IAreaService;
import com.upbest.mvc.vo.AreaVO;

@Service
public class AreaServiceImpl implements IAreaService {
	
	@Autowired
	private BArearespository respository;
	
	@Override
	public List<BArea> findTopArea() {
		return respository.findByParent(null);
	}

	@Override
	public List<BArea> findByParent(Integer pId) {
		BArea parent = respository.findOne(pId);
		return respository.findByParent(parent);
	}
	
	@Override
	public List<AreaVO> findAllAreaInfo() {
		List<AreaVO> result = new ArrayList<AreaVO>();
		List<BArea> areaList = respository.findAll();
		
		if(!CollectionUtils.isEmpty(areaList)){
			List<AreaVO> topAreaInfo = findByParent(areaList,null);
			if(!CollectionUtils.isEmpty(topAreaInfo)){
				buildChildren(topAreaInfo,areaList);
			}
			
			result = topAreaInfo;
		}
		
		return result;
	}

	private void buildChildren(List<AreaVO> topAreaInfo, List<BArea> areaList) {
		if(!CollectionUtils.isEmpty(topAreaInfo)){
			for (AreaVO vo : topAreaInfo) {
				List<AreaVO> children = findByParent(areaList,vo.getId());
				vo.setChildren(children);
				
				buildChildren(children,areaList);
				
			}
		}
	}

	private List<AreaVO> findByParent(List<BArea> areaList, Integer pId) {
		List<AreaVO> result = new ArrayList<AreaVO>();
		if(!CollectionUtils.isEmpty(areaList)){
			for (BArea area : areaList) {
				Integer parentAreaId = area.getParent() == null ? null : area.getParent().getId();
				if(pId == parentAreaId){
					AreaVO vo = new AreaVO();
					BeanUtils.copyProperties(area, vo);
					
					result.add(vo);
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(null == null);
	}

}
