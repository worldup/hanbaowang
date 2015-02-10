package com.upbest.mvc.service;

import com.upbest.mvc.entity.UserWorkingLeave;

import java.util.Date;
import java.util.List;

/**
 * Created by lili on 2015/2/1.
 */
public interface IUserWorkingLeaveService {
     public   UserWorkingLeave  addUserWorkingLeave( UserWorkingLeave userWorkingLeave);
     public List<UserWorkingLeave>  queryUserWorkingLeave(Integer userId,Date beginTime,Date endTime);
    public  void updateUserWorkingLeave(UserWorkingLeave userWorkingLeave);
    public void delUserWorkingLeave(Integer id);
    public void delUserWorkingLeaveBatch(List<Integer> id);

}
