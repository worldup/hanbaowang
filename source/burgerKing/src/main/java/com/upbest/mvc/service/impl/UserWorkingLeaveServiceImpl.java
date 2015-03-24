package com.upbest.mvc.service.impl;

import com.upbest.mvc.entity.UserWorkingLeave;
import com.upbest.mvc.repository.factory.UserWorkingLeaveRespository;
import com.upbest.mvc.service.IUserWorkingLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
   @Autowired
   private JdbcTemplate jdbcTemplate;

    @Override
    public UserWorkingLeave addUserWorkingLeave(UserWorkingLeave userWorkingLeave) {
        UserWorkingLeave result=   respository.save(userWorkingLeave);
       return result;
    }

    @Override
    public List<UserWorkingLeave> queryUserWorkingLeave(Integer userId, Date beginTime, Date endTime) {
         return   respository.findByUserIdAndStartTimeBetween(userId, beginTime, endTime);
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
    @Override
      public void deleteUserWorkingLeave(final List<UserWorkingLeave> list){

        String sql = "delete from bk_user_working_leave where userId=? and nonworkingtype=? and  day=? ";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public int getBatchSize() {
                return list.size();    //这个方法设定更新记录数，通常List里面存放的都是我们要更新的，所以返回list.size()；
            }

            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                UserWorkingLeave userWorkingLeave =   list.get(i);
                ps.setInt(1, userWorkingLeave.getUserId());
                ps.setInt(2, userWorkingLeave.getNonworkingType());
                ps.setString(3, userWorkingLeave.getDay());

            }
        });
    }
    @Override
    public void addUserWorkingLeave(final List<UserWorkingLeave> list){

        String sql = "insert into  bk_user_working_leave(userId,nonworkingtype,day) values(?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public int getBatchSize() {
                return list.size();    //这个方法设定更新记录数，通常List里面存放的都是我们要更新的，所以返回list.size()；
            }

            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                UserWorkingLeave userWorkingLeave =   list.get(i);
                ps.setInt(1, userWorkingLeave.getUserId());
                ps.setInt(2, userWorkingLeave.getNonworkingType());
                ps.setString(3, userWorkingLeave.getDay());

            }
        });
    }
}
