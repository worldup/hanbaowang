package com.upbest.mvc.repository.factory;

import com.upbest.mvc.entity.UserWorkingLeave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by lili on 2015/2/1.
 */
public interface UserWorkingLeaveRespository extends JpaRepository<UserWorkingLeave,Integer> {
    List<UserWorkingLeave> findByUserId(Integer userId);
    List<UserWorkingLeave> findByUserIdAndDayBetween(Integer userId,Date startDay,Date endDay);
}
