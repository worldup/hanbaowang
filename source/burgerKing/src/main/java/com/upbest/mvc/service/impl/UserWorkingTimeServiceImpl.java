package com.upbest.mvc.service.impl;

import com.upbest.mvc.entity.UserWorkingTime;
import com.upbest.mvc.repository.factory.UserWorkingTimeRespository;
import com.upbest.mvc.service.IUserWorkingTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by lili on 2015/2/1.
 */
@Service
public class UserWorkingTimeServiceImpl implements IUserWorkingTimeService {
    @Autowired
    private UserWorkingTimeRespository userWorkingTimeRespository;
    @Override
    public List<UserWorkingTime> addUserWorkingTime(List<UserWorkingTime> userWorkingTimeList) {
     return   userWorkingTimeRespository.save(userWorkingTimeList);
    }

    @Override
    public List<UserWorkingTime> queryUserWorkingTime(Integer userId, Date beginTime, Date endTime) {
      return userWorkingTimeRespository.findByUserIdAndDayBetween( userId, beginTime, endTime);
    }
}
