package com.upbest.mvc.handler;

import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.entity.BShopUser;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.repository.factory.StoreRespository;
import com.upbest.mvc.repository.factory.StoreUserRespository;
import com.upbest.mvc.repository.factory.UserRespository;
import com.upbest.mvc.service.IBuserService;
import com.upbest.utils.DataType;
import com.upbest.utils.PasswordUtil;
import com.upbest.utils.RoleFactory;

@Component
public class UserHandler implements Handler {

    @Inject
    protected UserRespository buserRepository;
    @Autowired
    private IBuserService userService;
    @Autowired
    private StoreRespository storeRes;
    
    @Autowired
    private StoreUserRespository storeUserRes;

    @Override
    public boolean handerRow(String[] rows) throws Exception {
        if (rows != null) {
            Buser user = buildUser(rows);
            buserRepository.save(user);
            //门店编号
            String shopNums = DataType.getAsString(rows[8]);
            shopNums=shopNums.replace("，",",");
            String[] shopNumsAry=shopNums.split(",");
            for(String str:shopNumsAry){
                BShopInfo shopInfo=storeRes.findByShopnum(str);
                if(shopInfo!=null){
                    BShopUser shopUser=storeUserRes.findByUserIdAndShopId(user.getId(),shopInfo.getId());
                    if(shopUser==null){
                        shopUser=new BShopUser();
                        shopUser.setShopId(shopInfo.getId());
                        shopUser.setUserId(user.getId());
                        storeUserRes.save(shopUser);
                    }
                }
            }
            if(StringUtils.isBlank(user.getEmp())){
                //职工号为空，则认主键ID为职工号
                user.setEmp(user.getId()+"");
                buserRepository.save(user);    
            }
        }
        return true;
    }

    private Buser buildUser(String[] rows) {
        String emp = DataType.getAsString(rows[6]);
        Buser user = new Buser();
        if(StringUtils.isNotBlank(DataType.getAsString(rows[1]))){
            user = userService.findByName(DataType.getAsString(rows[1]));    
        }
        String pname = DataType.getAsString(rows[5]);
        Buser puser=new Buser();
        if(StringUtils.isNotBlank(pname)){
            puser = userService.findByName(pname);
        }
        if(null!=puser.getId()){
            user.setPid(puser.getId());
        }else{
            user.setPid(null);
        }
        user.setRole(RoleFactory.getRoleId(rows[0]));
        user.setName(DataType.getAsString(rows[1]));
        user.setPwd(PasswordUtil.genPassword(DataType.getAsString(rows[2])));
        user.setRealname(DataType.getAsString(rows[3]));
        if(StringUtils.isNotBlank(DataType.getAsString(rows[4]))){
            user.setIsdel(DataType.getAsString(rows[4]));
        }else{
            user.setIsdel("1");
        }
        if (StringUtils.isNotBlank(emp)) {
            user.setEmp(DataType.getAsString(rows[6]));
        }
        user.setTelephone(DataType.getAsString(rows[7]));

        
        Date date = new Date();
        user.setCreatedate(date);
        user.setModifydate(date);

        return user;
    }

    private Buser getUserByEmp(String emp) {
        Buser user = buserRepository.findByEmp(emp);
        if (user == null) {
            user = new Buser();
        }
        return user;
    }
}
