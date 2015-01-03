package com.upbest.mvc.repository.factory;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.upbest.mvc.entity.Buser;

public interface UserRespository extends JpaRepository<Buser, Integer> {
    Page<Buser> findByNameLikeAndIsdel(String name, String isdel, Pageable pageable);
    Buser findByNameAndIsdel(String name,String isDel);
    
    Buser findByNameAndPwdAndRoleNot(String name,String pwd,String role);
    Buser findByRealnameAndIsdel(String name,String isDel);
    Buser findByName(String name);
    List<Buser> findByNameInAndIsdel(String[] names,String isdel);
    Buser findByEmp(String emp);
    
    /**
     * 根据父Id查询子用户
     * @param pids
     * @return
     */
    List<Buser> findByPidInAndIsdel(List<Integer> pids,String isdel);
    
    /**
     * 查询用户集
     * @param pids
     * @return
     */
    List<Buser> findByIdIn(List<Integer> ids);
    
    @Query("select u from Buser u where u.isdel=1")
    List<Buser> findAllBuser();
    
    @Query("select COUNT(*) from Buser u where u.name = ?1")
    int queryCountByUsername(String name);
    
    @Query("select COUNT(*) from Buser u where u.emp = ?1")
    int queryCountByUseremp(String emp);
    
    @Query("select u from Buser u where u.name=?1 and u.isdel=1")
    Buser queryUserByName(String name);
    
    
    List<Buser> findByRoleAndIsdel(String role,String isdel);
    
    
}
