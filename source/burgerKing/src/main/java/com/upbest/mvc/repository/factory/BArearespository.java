package com.upbest.mvc.repository.factory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.upbest.mvc.entity.BArea;

public interface BArearespository extends JpaRepository<BArea, Integer> {
	List<BArea> findByParent(BArea parent);
	
	List<BArea> findByAreaLike(String area);
	
	List<BArea> findByArea(String area);
	
	/**
	 * 查找某个区域下的子区域
	 * @param parent
	 * @param area
	 * @return
	 */
	@Query("select a from BArea a where a.parent = ?1 and a.area like ?2")
	List<BArea> findByAreaWithContext(BArea parent,String area);
	
	/**
	 * 查找某个区域下的子区域
	 * @param parent
	 * @param area
	 * @return
	 */
	@Query("select a from BArea a where a.parent is null and a.area like ?1")
	List<BArea> findByAreaWithTopContext(String area);
}
