package com.upbest.mvc.repository.factory;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.upbest.mvc.entity.BSignInfo;
public interface SingRespository extends PagingAndSortingRepository<BSignInfo, Integer>{
   /* Page<BSingIfno> findBySingnameLike(String singName, Pageable pageable);*/
    
    /*BSingIfno findBySingname(String name);*/
	List<BSignInfo> findByUserid(Integer userid, Pageable pageable);
	List<BSignInfo> findByUseridAndShopId(Integer userid,Integer shopId, Pageable pageable);
	
	@Query("select r from BSignInfo r where r.userid =?1 order by r.signintime desc  ")
	List<BSignInfo> findByUserid(Integer userid);
	
	@Query("select r from BSignInfo r where r.userid in( select u from Buser u where u.pid=?1 ) order by r.signintime desc ")
    List<BSignInfo> findByUseridOM(Integer userid);
	
	@Query("select r from BSignInfo r where r.userid in( ?1 ) order by r.signintime desc ")
    List<BSignInfo> findByUseridOMPlus(List<Integer> userids);
	
}
