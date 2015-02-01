package com.upbest.mvc.service;

import com.upbest.mvc.entity.UserWorkingTime;

import java.util.Date;
import java.util.List;

/**
 * Created by lili on 2015/2/1.
 */
public interface IUserWorkingTimeService {
     public  List<UserWorkingTime>  addUserWorkingTime(List<UserWorkingTime> userWorkingTimeList);
     public List<UserWorkingTime>  queryUserWorkingTime(Integer userId,Date beginTime,Date endTime);

}
