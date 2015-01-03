package com.upbest.mvc.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upbest.exception.BurgerKingException;
import com.upbest.mvc.vo.BSignInfoVO;

public interface IBSingIfnoService {
    Page<Object[]> findSingList(String singName, Pageable requestPage);

	void signIn(int shopId, int userid, String lng, String lat, String location) throws BurgerKingException;

	void signOut(int shopId, int userid)  throws BurgerKingException;

	List<BSignInfoVO> getSignInfo(int userid,String isLatest);
    
}