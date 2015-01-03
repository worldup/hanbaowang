package com.upbest.mvc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.upbest.mvc.constant.Constant.TaskState;
import com.upbest.mvc.entity.BTestPaper;
import com.upbest.mvc.repository.factory.TestPaperRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.ITestPaperService;
import com.upbest.mvc.vo.TestPaperVO;
import com.upbest.utils.DataType;
import com.upbest.utils.PageModel;

@Service
public class TestPaperServiceImpl implements ITestPaperService {
    @Autowired
    private TestPaperRespository tpRespository;
    
    @Autowired
    private CommonDaoCustom<Object[]> common;

    @Override
    public BTestPaper saveTestPaper(BTestPaper testPaper) {
      return  tpRespository.save(testPaper);
    }
    
    
    
    @Override
    public PageModel<TestPaperVO> queryTestPaperInfos(Integer examId,Pageable pageable,String shopName) {
    	PageModel<TestPaperVO> result = new PageModel<TestPaperVO>();
		
		StringBuffer sql=new StringBuffer();
        List<Object> params=new ArrayList<Object>();
       /* sql.append("select t.user_id,  ");                    
        sql.append("t.name, ");           
        sql.append("p.t_begin,");      
        sql.append("p.t_end,   ");    
        sql.append("p.t_total,   ");    
        sql.append("p.t_state,");
        sql.append("t.shop_name  ");    
        sql.append("from    ");    
        sql.append("(select u.id as user_id, u.name,bsi.shop_name from ");
        sql.append("  ( select DISTINCT bsu.user_id,bsu.shop_id from ");  
        sql.append("(select DISTINCT t.user_id     ");      
        sql.append("     from BK_EXAMINATION_ASSIGN t    ");           
        sql.append("        where t.e_id = ?) t "); 
        sql.append("left join bk_shop_user bsu  ");   
        sql.append("      on t.user_id = bsu.user_id) t ");
        sql.append("join bk_user u on t.user_id = u.id ");      
        sql.append("join bk_shop_info bsi on bsi.id = t.shop_id ) t  ");    
        sql.append("left join BK_TEST_PAPER p on t.user_id = p.user_id   and p.e_id =? "); 
        sql.append(" where  1=1   ");*/
        sql.append("  select tp.id,                             ");
        sql.append("         tp.t_begin,                        ");
        sql.append("         tp.t_end,                          ");
        sql.append("         tp.t_total,                        ");
        sql.append("         ur.name as userName,                           ");
        sql.append("         si.shop_name as shopName,                       ");
        sql.append(" ur.id as userId,si.id as shopId,ep.e_name as epName ");
        sql.append("    from bk_test_paper tp                   ");
        sql.append("    join bk_examitnation_paper ep           ");
        sql.append("      on ep.id = tp.e_id                    ");
        sql.append("     and ep.id = ?                       ");
        sql.append("    join bk_user ur                         ");
        sql.append("      on ur.id = tp.user_id                 ");
        sql.append("     and ur.is_del = '1'                    ");
        sql.append("    join bk_shop_info si                    ");
        sql.append("      on si.id = tp.store_id                ");
        sql.append("   where 1 = 1                              ");

        
        //params.add(examId);
        params.add(examId);
        
        if (StringUtils.isNotBlank(shopName)) {
            sql.append(" and si.shop_name like ?");
            params.add("%" + shopName + "%");
        }
        
        Page<Object[]> page = common.queryBySql(sql.toString(), params,pageable);
		
		result.setPage(page.getNumber() + 1);
        result.setRows(getTestPaperInfo(page.getContent()));
        result.setPageSize(page.getSize());
        result.setRecords((int)page.getTotalElements());
        
    	return result;
    }

	private List<TestPaperVO> getTestPaperInfo(List<Object[]> content) {
		List<TestPaperVO> result = new ArrayList<TestPaperVO>();
		if(!CollectionUtils.isEmpty(content)){
			for (Object[] objAry : content) {
				TestPaperVO vo = new TestPaperVO();
				vo.setId(DataType.getAsInt(objAry[0]));
				vo.setTbegin(DataType.getAsDate(objAry[1]));
				vo.setTend(DataType.getAsDate(objAry[2]));
				vo.setTtotal(DataType.getAsInt(objAry[3]));
				vo.setUsername(DataType.getAsString(objAry[4]));
				vo.setShopname(DataType.getAsString(objAry[5]));
				vo.setUserid(DataType.getAsInt(objAry[6]));
				vo.setStoreid(DataType.getAsInt(objAry[7]));
				vo.setExamName(DataType.getAsString(objAry[8]));
				result.add(vo);
			}
		}
		return result;
	}
	
	@Override
	public BTestPaper queryTestPaper(Integer paperId) {
		return tpRespository.findOne(paperId);
	}

}
