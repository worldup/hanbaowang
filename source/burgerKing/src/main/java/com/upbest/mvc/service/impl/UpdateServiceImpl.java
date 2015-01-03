package com.upbest.mvc.service.impl;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.upbest.mvc.entity.BUpdate;
import com.upbest.mvc.handler.ExcelWriter;
import com.upbest.mvc.repository.factory.UpdateRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.IUpdateService;
import com.upbest.mvc.vo.ExamExportVO;
import com.upbest.mvc.vo.ExamSheetVO;
import com.upbest.mvc.vo.SheetDataVO;
import com.upbest.utils.DataType;

@Service
public class UpdateServiceImpl implements IUpdateService {

    @Inject
    private CommonDaoCustom<Object[]> common;
    
    @Inject
    private UpdateRespository updateRespository;
    
    @Override
    public Page<Object[]> findUpdateList(Pageable requestPage, String versionNum) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("   select u.id,                ");
        sql.append("          u.apk_name,          ");
        sql.append("          u.apk_size,          ");
        sql.append("          u.version_num,       ");
        sql.append("          u.remark,            ");
        sql.append("          u.url,               ");
        sql.append("          u.update_time        ");
        sql.append("          from bk_update u     ");
        sql.append("          where 1 = 1          ");
        if(StringUtils.isNotEmpty(versionNum)){
            sql.append("      and u.version_num like ?        ");
            params.add("%" + versionNum + "%");
        }
        return common.queryBySql(sql.toString(), params, requestPage);
    }

    @Override
    public BUpdate findById(Integer id) {
        return updateRespository.findOne(id);
    }

    @Override
    public void saveAPkInfo(BUpdate update) {
        updateRespository.save(update);
    }

    @Override
    public void delAPKInfoById(Integer id) {
        updateRespository.delete(id);
    }

    @Override
    public BUpdate findLatest() {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("   select u.id,                ");
        sql.append("          u.apk_name,          ");
        sql.append("          u.apk_size,          ");
        sql.append("          u.version_num,       ");
        sql.append("          u.remark,            ");
        sql.append("          u.url,               ");
        sql.append("          u.update_time        ");
        sql.append("          from bk_update u     ");
        sql.append("          where 1 = 1          ");
        sql.append("          order by  ");
        sql.append("          u.update_time desc   ");
        List<Object[]> listObj = common.queryBySql(sql.toString(), params);
        List<BUpdate> updateList = getShopInfo(listObj);
        return updateList.get(0);
    }
    
    private List<BUpdate> getShopInfo(List<Object[]> list){
        List<BUpdate> result=new ArrayList<BUpdate>();
        if(!CollectionUtils.isEmpty(list)){
            BUpdate entity=null;
            for(Object[] obj:list){
                entity=new BUpdate();
                entity.setId(DataType.getAsInt(obj[0]));
                entity.setApkName(DataType.getAsString(obj[1]));
                entity.setApkSize(DataType.getAsString(obj[2]));
                entity.setVersionNum(DataType.getAsString(obj[3]));
                entity.setRemark(DataType.getAsString(obj[4]));
                entity.setUrl(DataType.getAsString(obj[5]));
                entity.setUpdateTime(DataType.getAsDate(obj[6]));
                result.add(entity);
            }
        }
        return result;
    }

  /*  @Override
    public void addUpdateToAPK(Integer[] updateId, OutputStream out) throws Exception {
        
        List<upda> page = queryFacility(examid);
        SheetDataVO<ExamSheetVO> sheetData = new ExamExportVO("exam", page);
        ExcelWriter writer = new ExcelWriter<SheetDataVO>(sheetData);
        writer.write(out);
    }*/

}
