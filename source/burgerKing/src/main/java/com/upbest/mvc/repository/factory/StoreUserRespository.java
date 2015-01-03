package com.upbest.mvc.repository.factory;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.upbest.mvc.entity.BShopUser;

public interface StoreUserRespository extends PagingAndSortingRepository<BShopUser, Serializable> {
    
   /* Page<BShopUser> findByShopnameLike(String shopName, Pageable pageable);
    
    BShopUser findByShopname(String name);*/
	List<BShopUser> findByShopIdIn(Collection<Integer> shopIds);
	
	List<BShopUser> findByShopId(int shopId);
	
	@Query("select COUNT(*) from BShopUser r where r.userId=?1 ")
	int findCntByUserId(int userId);
	
	@Query("select r from BShopUser r where r.userId=?1 ")
	List<BShopUser> findByUserId(int userId);
	
	BShopUser findByUserIdAndShopId(int userId,int shopId);
	
}
