package com.upbest.mvc.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.upbest.mvc.entity.BFacility;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.handler.ExcelWriter;
import com.upbest.mvc.handler.FacilityHandler;
import com.upbest.mvc.repository.factory.FacilityRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.IFacilityService;
import com.upbest.mvc.vo.BFacilityVO;
import com.upbest.mvc.vo.FacilitySheetDataVO;
import com.upbest.mvc.vo.SheetDataVO;
import com.upbest.utils.DataType;
import com.upbest.utils.ExcelUtils;
import com.upbest.utils.PageModel;

@Service
public class FacilityServiceImpl implements IFacilityService {

	@Inject
	private FacilityRespository repository;
	
	@Autowired
    private CommonDaoCustom<Object[]> common;
    
    @Autowired
    private FacilityHandler handler;
	
	@Override
	public void saveFacility(BFacility facility) {
		repository.save(facility);
	}

	@Override
	public void deleteFacility(int facilityId) {
		repository.delete(facilityId);
	}
	
	@Override
	public void deleteFacilities(List<Integer> facilityIds) {
		if(CollectionUtils.isEmpty(facilityIds)){
			return;
		}
		for (Integer id : facilityIds) {
			repository.delete(id);
		}
	}

	@Override
	public List<BFacility> queryFacility() {
		List<BFacility> result = new ArrayList<BFacility>();
		Iterable<BFacility> iterator = repository.findAll();
		if(iterator != null){
			for (BFacility bFacility : iterator) {
				result.add(bFacility);
			}
		}
		return result;
	}
	
	@Override
	public BFacility queryFacility(Integer facilityId) {
		return repository.findOne(facilityId);
	}

	@Override
	public PageModel<BFacilityVO> queryFacility(String facilityName,Pageable pageable) {
		StringBuffer sql=new StringBuffer();
        List<Object> params=new ArrayList<Object>();
        sql.append("  SELECT t.id,                       ");
        sql.append("         t.Device_version,             ");
        sql.append("         t.Device_id,       ");
        sql.append("         t.Device_brand,       ");
        sql.append("         t.Device_model,               ");
        sql.append("         t.Device_os,                ");
        sql.append("         t.Device_os_version,                 ");
        sql.append("         t.Device_resolution,           ");
        sql.append("         u.real_name              ");
        sql.append("    FROM BK_FACILITY t  left join BK_USER u on  t.user_id = u.id           ");
        sql.append("   where 1 = 1                       ");
        sql.append("   and u.is_del='1'                       ");
        if(StringUtils.isNotBlank(facilityName)){
            sql.append(" and t.Device_brand like ?");
            params.add("%" + facilityName + "%");
        }
        
        Page<Object[]> page = common.queryBySql(sql.toString(), params,pageable);
        
        PageModel<BFacilityVO> result = new PageModel<BFacilityVO>();
        result.setPage(page.getNumber() + 1);
        result.setRows(getFacilityInfo(page.getContent()));
        result.setPageSize(page.getSize());
        result.setRecords((int)page.getTotalElements());
        
        return result;
		
	}

	private List<BFacilityVO> getFacilityInfo(List<Object[]> list) {
		 List<BFacilityVO> result = new ArrayList<BFacilityVO>();
        if (!CollectionUtils.isEmpty(list)) {
        	BFacilityVO entity = null;
            for (Object[] obj : list) {
                entity = new BFacilityVO();
                entity.setId(DataType.getAsInt(obj[0]));
                entity.setDeviceversion(DataType.getAsString(obj[1]));
                entity.setDeviceid(DataType.getAsString(obj[2]));
                entity.setDevicebrand(DataType.getAsString(obj[3]));
                entity.setDevicemodel(DataType.getAsString(obj[4]));
                entity.setDeviceos(DataType.getAsString(obj[5]));
                entity.setDeviceosversion(DataType.getAsInt(obj[6]));
                entity.setDeviceresolution(DataType.getAsString(obj[7]));
                entity.setUserName(DataType.getAsString(obj[8]));
                
                result.add(entity);
            }
        }
        return result;
	}

	@Override
	public void updateFacility(BFacility facility) {
		repository.save(facility);
	}
	
	@Override
	public BFacility queryByDevIdAndUser(String devid, Buser user) {
		return repository.findByDeviceidAndUserid(devid, user.getId());
	}
	
	@Override
	public void addFacilityFromExcel(File file) throws Exception {
//		ExcelUtils.handExcel(1, file, handler);
	}
	
	@Override
	public void addFacilityFromExcel(Resource resource) throws Exception {
		ExcelUtils.handExcel(1, resource, handler);
	}
	
	@Override
	public void addFacilituToExcel() throws Exception {
		PageModel<BFacilityVO> page = queryFacility("", new PageRequest(0, 10));
		
		File file = new File("C:/Users/QunZheng/Documents/test.xlsx");
		FileUtils.createNewFile(file);
		
		SheetDataVO<BFacilityVO> sheetData = new FacilitySheetDataVO("facility", page.getRows());
		ExcelWriter writer = new ExcelWriter<SheetDataVO>(sheetData);
		writer.write(new FileOutputStream(file));
		
	}

}
 