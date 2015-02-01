package com.upbest.mvc.repository.factory;

import com.upbest.mvc.entity.UserWorkingTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by lili on 2015/2/1.
 */
public interface UserWorkingTimeRespository  extends JpaRepository<UserWorkingTime,Integer> {
    List<UserWorkingTime> findByUserId(Integer userId);
    List<UserWorkingTime> findByUserIdAndDayBetween(Integer userId,Date startDay,Date endDay);
}
