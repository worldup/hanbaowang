package com.upbest.mvc.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.hibernate.metamodel.relational.Datatype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.upbest.mvc.entity.BExBaseInfo;
import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.repository.factory.BExBaseInfoRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.IBExBaseInfoService;
import com.upbest.mvc.vo.BExBaseInfoVO;
import com.upbest.mvc.vo.BShopInfoVO;
import com.upbest.mvc.vo.BuserVO;
import com.upbest.mvc.vo.TreeVO;
import com.upbest.utils.DataType;

@Service
public class BExBaseInfoSeriverImpl implements IBExBaseInfoService{

    @Inject
    protected BExBaseInfoRespository baseRepository;
    
    @Autowired
    private CommonDaoCustom<Object[]> common;
    
    @Override
    public Page<Object[]> findBaseList(String baseName, Pageable requestPage) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  SELECT t.b_value as pvalue,b.id,                        ");
        sql.append("         b.b_pid,       ");
        sql.append("         b.b_value,      ");
        sql.append("         b.b_brush_election, ");
        sql.append("         b.b_comments,b.is_key       ");
        sql.append("    FROM bk_ex_base_info b            ");
        sql.append("  left join  bk_ex_base_info t  on b.b_pid=t.id         ");
        sql.append("    where 1=1          ");
        if (StringUtils.isNotBlank(baseName)) {
            sql.append(" and b.b_value like ?");
            params.add("%" + baseName + "%");
        }
        return common.queryBySql(sql.toString(), params,requestPage);
    }


    @Override
    public BExBaseInfoVO findById(Integer id) {
        BExBaseInfo base=baseRepository.findOne(id);
        BExBaseInfoVO vo=new BExBaseInfoVO();
        vo.setId(base.getId());
        vo.setBpid(base.getBpid());
        vo.setBvalue(base.getBvalue());
        vo.setBrushElection(base.getBrushElection());
        vo.setBcomments(base.getBcomments());
        vo.setIsKey(base.getIsKey());
        List<String> urList = getBaseId(DataType.getAsString(id));
        if (!CollectionUtils.isEmpty(urList)) {
            vo.setPvalue(urList.get(0));
        }
        return vo;
    }
    
    @Override
    public List<String> getBaseId(String baseId) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  SELECT t.b_value as pvalue,b.id,                        ");
        sql.append("         b.b_pid,       ");
        sql.append("         b.b_value,      ");
        sql.append("         b.b_comments       ");
        sql.append("    FROM bk_ex_base_info b            ");
        sql.append("  left join  bk_ex_base_info t  on b.b_pid=t.id         ");
        sql.append("    where 1=1          ");
        if (StringUtils.isNotBlank(baseId)) {
            sql.append("     and b.id = ?             ");
            params.add(baseId);
        }
        String pvalue = "";
        List<Object[]> list = common.queryBySql(sql.toString(), params);
        List<String> result = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Object[] obj : list) {
                pvalue += (obj[0] == null ? "" : DataType.getAsString(obj[0]) + ",");
            }
        }
        if (StringUtils.isNotBlank(pvalue)) {
            pvalue = pvalue.substring(0, pvalue.length() - 1);
        }

        result.add(pvalue);
        return result;
    }
    
    
    public List<TreeVO> getUrVOList() {
        List<TreeVO> result = new ArrayList<TreeVO>();
        List<BExBaseInfoVO> userList = getBUser();
        result=getTreeList(userList);
        return result;
    }
    public List<TreeVO> getUrVOList(Integer id) {
        List<TreeVO> result = new ArrayList<TreeVO>();
        List<BExBaseInfoVO> userList = getBUser(id);
        result=getTreeList(userList);
        return result;
    }
    
    public List<BExBaseInfoVO> getBUser() {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  SELECT t.b_value as pvalue,b.id,                        ");
        sql.append("         b.b_pid,       ");
        sql.append("         b.b_value,      ");
        sql.append("         b.b_comments       ");
        sql.append("    FROM bk_ex_base_info b            ");
        sql.append("  left join  bk_ex_base_info t  on b.b_pid=t.id         ");
        sql.append("    where 1=1          ");
        List<Object[]> objList = common.queryBySql(sql.toString(), params);
        List<BExBaseInfoVO> shop = getUserInfo(objList);
        return shop;
    }
    public List<BExBaseInfoVO> getBUser(Integer id) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  SELECT t.b_value as pvalue,b.id,                        ");
        sql.append("         b.b_pid,       ");
        sql.append("         b.b_value,      ");
        sql.append("         b.b_comments       ");
        sql.append("    FROM bk_ex_base_info b            ");
        sql.append("  left join  bk_ex_base_info t  on b.b_pid=t.id         ");
        sql.append("    where 1=1             ");
        if(null==id){
            sql.append("  and b.b_pid=''  ");
        }else{
            sql.append("  and b.b_pid=? ");
            params.add(id);
        }
        List<Object[]> objList = common.queryBySql(sql.toString(), params);
        List<BExBaseInfoVO> shop = getUserInfo(objList);
        return shop;
    }
    
    private List<BExBaseInfoVO> getUserInfo(List<Object[]> list) {
        List<BExBaseInfoVO> result = new ArrayList<BExBaseInfoVO>();
        if (!CollectionUtils.isEmpty(list)) {
            BExBaseInfoVO entity = null;
            for (Object[] obj : list) {
                entity = new BExBaseInfoVO();
                entity.setPvalue(DataType.getAsString(obj[0]));
                entity.setId(DataType.getAsInt(obj[1]));
                entity.setBpid(DataType.getAsString(obj[2]));
                entity.setBvalue(DataType.getAsString(obj[3]));
                entity.setBcomments(DataType.getAsString(obj[4]));
                result.add(entity);
            }
        }
        return result;
    }
    
    public List<TreeVO> getTreeList(List<BExBaseInfoVO> list) {
        List<TreeVO> result = new ArrayList<TreeVO>();
        if (!CollectionUtils.isEmpty(list)) {
            TreeVO vo = null;
            for (BExBaseInfoVO obj : list) {
                if (null != obj) {
                    vo = new TreeVO();
                        vo.setParent(true); 
                    vo.setName(obj.getBvalue());
                    vo.setId(DataType.getAsString(obj.getId()));
                    if (obj.getBpid() == null) {
                        vo.setPid("0");
                    } else {
                        vo.setPid(obj.getBpid());
                    }
                    result.add(vo);
                }
            }
        }
        return result;
    }

    @Override
    public BExBaseInfo saveBase(BExBaseInfo entity) {
        return baseRepository.save(entity);
    }


    @Override
    public BExBaseInfo saveBaseInfo(String ids, String bvalue,String bcomments,String brushElection,int isKey) {
        BExBaseInfo entity=new BExBaseInfo();
        entity.setBpid(ids);
        entity.setBvalue(bvalue);
        entity.setBcomments(bcomments);
        entity.setBrushElection(brushElection);
        entity.setIsKey(isKey);
        return saveBase(entity);
    }


    @Override
    @Transactional(readOnly = false)
    public void deleteBaseInfo(Integer id) {
        String sql=" delete from bk_ex_base_info where id=? or b_pid=? ";
        common.upadteBySql(sql, Arrays.asList(new Integer[]{id,id}));
    }

    
}
