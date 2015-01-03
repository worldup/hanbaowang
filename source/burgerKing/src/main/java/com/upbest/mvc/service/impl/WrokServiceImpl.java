package com.upbest.mvc.service.impl;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.upbest.mvc.entity.BWorkType;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.repository.factory.UserRespository;
import com.upbest.mvc.repository.factory.WorkRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.IWorkService;
import com.upbest.utils.DataType;
import com.upbest.utils.RoleFactory;

@Service
public class WrokServiceImpl implements IWorkService{
    
    @Inject
    protected WorkRespository workRepository;
    
    @Inject
    protected UserRespository userRepository;
    
    @Autowired
    private CommonDaoCustom<Object[]> common;


    @Override
    public void saveBWorkType(BWorkType entity) {
        workRepository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        BWorkType entity=workRepository.findOne(id);
        workRepository.delete(entity);
    }

    @Override
    public BWorkType findById(Integer id) {
        return workRepository.findOne(id);
    }
    
    @Override
    public Page<Object[]> findWorkList(String typeName, Pageable pageable) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  SELECT s.id,                       ");
        sql.append("         s.type_name,       ");
        sql.append("         s.type_role,       ");
        sql.append("         s.frequency,       ");
        sql.append("         s.is_to_store ,s.is_exam_type      ");
        sql.append("    FROM bk_work_type s  ");
        sql.append("   where 1=1                       ");
        if (StringUtils.isNotBlank(typeName)) {
            sql.append(" and s.type_name like ?");
            params.add("%" + typeName + "%");
        }
        return common.queryBySql(sql.toString(), params, pageable);
    }

    @Override
    public List<BWorkType> findWork(String userId) {
        List<BWorkType> typeList= workRepository.findByTypenameNotOrderBySortNumAsc("其它");
        List<BWorkType> result=new ArrayList<BWorkType>();
        Buser buser=userRepository.findOne(DataType.getAsInt(userId));
        String roleName=RoleFactory.getRoleName(buser.getRole());
        if(StringUtils.isNotBlank(userId)){
            for(BWorkType type:typeList){
                String typeRole=type.getTyperole();
                String[] typeRoleAry=typeRole.split(",");
                for(int i=0;i<typeRoleAry.length;i++){
                    if(roleName.equals(typeRoleAry[i])){
                        result.add(type);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<BWorkType> findWorkWithOutNeeded(String userId) {
        List<BWorkType> typeList= workRepository.findWorkTypeWithOutNeeded();
        List<BWorkType> result=new ArrayList<BWorkType>();
        Buser buser=userRepository.findOne(DataType.getAsInt(userId));
        String roleName=RoleFactory.getRoleName(buser.getRole());
        if(StringUtils.isNotBlank(userId)){
            for(BWorkType type:typeList){
                String typeRole=type.getTyperole();
                String[] typeRoleAry=typeRole.split(",");
                for(int i=0;i<typeRoleAry.length;i++){
                    if(roleName.equals(typeRoleAry[i])){
                        result.add(type);
                    }
                }
            }
        }
        return result;
    }
    
    public List<BWorkType> findWorkWithOutNeededAndQuarter(String userId) {
        List<BWorkType> typeList= workRepository.findWorkTypeWithOutNeededAndQuarter();
        List<BWorkType> result=new ArrayList<BWorkType>();
        Buser buser=userRepository.findOne(DataType.getAsInt(userId));
        String roleName=RoleFactory.getRoleName(buser.getRole());
        if(StringUtils.isNotBlank(userId)){
            for(BWorkType type:typeList){
                String typeRole=type.getTyperole();
                String[] typeRoleAry=typeRole.split(",");
                for(int i=0;i<typeRoleAry.length;i++){
                    if(roleName.equals(typeRoleAry[i])){
                        result.add(type);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public int getMaxSortNum() {
        //获取最大排序
        return workRepository.getMaxSortNum();
    }

    @Override
    public void saveBWorkType(List<BWorkType> list) {
        workRepository.save(list);
    }
    
    
    
    
}
