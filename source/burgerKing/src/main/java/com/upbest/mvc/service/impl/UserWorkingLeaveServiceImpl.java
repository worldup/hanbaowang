package com.upbest.mvc.service.impl;

import com.upbest.mvc.entity.UserWorkingLeave;
import com.upbest.mvc.repository.factory.UserWorkingLeaveRespository;
import com.upbest.mvc.service.IUserWorkingLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lili on 2015/2/1.
 */
@Service
public class UserWorkingLeaveServiceImpl implements IUserWorkingLeaveService {
    @Autowired
    private UserWorkingLeaveRespository respository;

    @Override
    public UserWorkingLeave addUserWorkingLeave(UserWorkingLeave userWorkingLeave) {
        UserWorkingLeave result=   respository.save(userWorkingLeave);
       return result;
    }

    @Override
    public List<UserWorkingLeave> queryUserWorkingLeave(Integer userId, Date beginTime, Date endTime) {
         return   respository.findByUserIdAndDayBetween(userId, beginTime, endTime);
    }

    @Override
    public void updateUserWorkingLeave(UserWorkingLeave userWorkingLeave) {
        respository.saveAndFlush(userWorkingLeave);
    }

    @Override
    public void delUserWorkingLeave(Integer id) {
        respository.delete(id);
    }
    @Override
    public void delUserWorkingLeaveBatch(List<Integer> ids){
        List<UserWorkingLeave> leaves=new ArrayList();
        for(Integer id:ids){
            UserWorkingLeave leave=new UserWorkingLeave();
            leave.setId(id);
            leaves.add(leave);
        }
        respository.deleteInBatch(leaves);
    }
}
