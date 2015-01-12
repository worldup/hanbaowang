package com.upbest.mvc.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import com.upbest.mvc.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.upbest.mvc.entity.BMessage;
import com.upbest.mvc.entity.BWorkInfo;
import com.upbest.mvc.entity.BWorkType;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.repository.factory.TaskRespository;
import com.upbest.mvc.repository.factory.UserRespository;
import com.upbest.mvc.repository.factory.WorkRespository;
import com.upbest.mvc.vo.BuserVO;
import com.upbest.mvc.vo.SelectionVO;
import com.upbest.mvc.vo.TaskVO;
import com.upbest.utils.DataType;
import com.upbest.utils.PageModel;

@Service
public class TaskServiceImpl implements ITaskService {
    @Autowired
    private DBChooser dbChooser;
    @Inject
    protected TaskRespository taskRepository;

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private WorkRespository workRespository;

    @Autowired
    private CommonDaoCustom<Object[]> common;

    @Autowired
    private IBuserService userService;

    @Override
    public BWorkInfo findById(Integer id) {
        return taskRepository.findOne(id);
    }

    @Override
    public Page<Object[]> findWorkList(Buser user, String typeName,String uName,String sDate, Pageable pageable) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  select t.id,                           ");
        sql.append("         t.content,                      ");
        sql.append("         t.finish_pre_time,              ");
        sql.append("         t.finish_real_time,             ");
        // sql.append("         t.name,                         ");
        sql.append("         t.pre_result,                   ");
        sql.append("         t.real_result,                  ");
        sql.append("         t.start_time,                   ");
        sql.append("         t.state,                        ");
        sql.append("         ur1.real_name as real_name,                  ");
        sql.append("         ur2.real_name as execute_name,                  ");
        sql.append("         typ.type_name,                  ");
        sql.append("         shop.shop_name,                  ");
        sql.append("         t.is_self_create,                  ");
        sql.append("         t.quarter                  ");
        sql.append("    from bk_work_info t                  ");
        sql.append("    left join bk_work_type typ           ");
        sql.append("      on t.work_type_id = typ.id         ");
        sql.append("    left join bk_user ur1                ");
        sql.append("      on ur1.id = t.user_id              ");
        sql.append("    left join bk_user ur2                ");
        sql.append("      on ur2.id = t.execute_id           ");
        sql.append("    left join bk_shop_info shop          ");
        sql.append("      on shop.id = t.store_id            ");
        sql.append("    where 1 = 1                          ");
        if (!"0".equals(user.getRole())) {
            sql.append("    and (t.user_id = ?                    ");
            sql.append("    or t.execute_id = ?)                  ");
            params.add(user.getId());
            params.add(user.getId());
        }
        if (StringUtils.isNotBlank(typeName)) {
            sql.append(" and typ.type_name like ?");
            params.add("%" + typeName + "%");
            /*sql.append(" or ur1.real_name like ?");
            params.add("%" + typeName + "%");
            sql.append(" or ur2.real_name like ?");
            params.add("%" + typeName + "%");*/
        }
        if(StringUtils.isNotBlank(uName))
        {
            sql.append(" and ur2.real_name like ?");
            params.add("%" + uName + "%");
        }
        if(StringUtils.isNotBlank(sDate))
        {
            Date   date = DataType.getAsDate(sDate,"yyyy-MM-dd");
            if(dbChooser.isSQLServer()){
                sql.append("and datediff(day,t.start_time,?)=0");
            }
           else{
                sql.append("and TIMESTAMPDIFF(day,t.start_time,?)=0");
            }
                params.add(date);
        }
        sql.append(" order by t.start_time desc ");
        return common.queryBySql(sql.toString(), params, pageable);
    }

    @Override
    public List<Object[]> findWorkInfoList(String id) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  select t.id,                           ");
        sql.append("         t.content,                      ");
        sql.append("         t.finish_pre_time,              ");
        sql.append("         t.finish_real_time,             ");
        // sql.append("         t.name,                         ");
        sql.append("         t.pre_result,                   ");
        sql.append("         t.real_result,                  ");
        sql.append("         t.start_time,                   ");
        sql.append("         t.state,                        ");
        sql.append("         ur1.real_name as real_name,                   ");
        sql.append("         ur2.real_name as execute_name,                   ");
        sql.append("         typ.type_name,                  ");
        sql.append("         shop.shop_name,                  ");
        sql.append("         t.is_self_create,                  ");
        sql.append("         t.quarter                  ");
        sql.append("    from bk_work_info t                  ");
        sql.append("    left join bk_work_type typ           ");
        sql.append("      on t.work_type_id = typ.id         ");
        sql.append("    left join bk_user ur1                 ");
        sql.append("      on ur1.id = t.user_id               ");
        sql.append("    left join bk_user ur2                 ");
        sql.append("      on ur2.id = t.execute_id               ");
        sql.append("    left join bk_shop_info shop          ");
        sql.append("      on shop.id = t.store_id            ");
        sql.append("   where 1 = 1                           ");
        if (StringUtils.isNotBlank(id)) {
            sql.append(" and t.id = ?");
            params.add(id);
        }
        return common.queryBySql(sql.toString(), params);
    }

    @Override
    public void saveBWorkInfo(BWorkInfo entity) {
        BWorkInfo taskInfo= taskRepository.save(entity);
        //将任务信息创建到消息表中
      /*  BMessage message = new BMessage();
        message.setMessageType("3");
        message.setPushTitle(workRespository.findOne(taskInfo.getWorktypeid()).getTypename());
        message.setPushContent(taskInfo.getContent());
        message.setCreateTime(new Date());
//        message.setState("1");
        message.setIsRead("0");
        message.setUserId(taskInfo.getUserid());
        message.setPushTime(new Date());
        message.setSenderId(taskInfo.getUserid());
        message.setReceiverId(taskInfo.getExecuteid());
        message.setTaskId(taskInfo.getId());
        messageService.saveBMessage(message);*/
    }

    @Override
    public void deleteById(Integer id) {
        BWorkInfo entity = taskRepository.findOne(id);
        taskRepository.delete(entity);
    }

    @Override
    public List<SelectionVO> getStoreList(Integer id) {
        List<SelectionVO> result = new ArrayList<SelectionVO>();
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append(" select t.id,t.shop_name from bk_shop_info t                       ");
        sql.append(" left join bk_shop_user s on s.shop_id=t.id");
        sql.append(" left join bk_user u on u.id=s.user_id");
        sql.append("  where 1=1");
        if(id!=null)
        {
            sql.append("  and u.id=?");
            params.add(id);
        }
        List<Object[]> list = common.queryBySql(sql.toString(), params);
        if (!CollectionUtils.isEmpty(list)) {
            SelectionVO vo = null;
            for (Object[] obj : list) {
                vo = new SelectionVO();
                vo.setValue(DataType.getAsString(obj[0]));
                vo.setName(DataType.getAsString(obj[1]));
                result.add(vo);
            }
        }
        return result;
    }

    @Override
    public List<SelectionVO> getTaskList() {
        List<SelectionVO> result = new ArrayList<SelectionVO>();
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append(" select t.id,t.type_name,t.is_to_store from bk_work_type t                       ");
        List<Object[]> list = common.queryBySql(sql.toString(), params);
        if (!CollectionUtils.isEmpty(list)) {
            SelectionVO vo = null;
            for (Object[] obj : list) {
                vo = new SelectionVO();
                vo.setValue(DataType.getAsString(obj[0]));
                vo.setName(DataType.getAsString(obj[1]));
                vo.setRelated(DataType.getAsString(obj[2]));
                result.add(vo);
            }
        }
        return result;
    }

    @Override
    public List<SelectionVO> getUserList(Buser buser) {
        Integer id = buser.getId();
        List<SelectionVO> result = new ArrayList<SelectionVO>();
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        List<Object[]> list = new ArrayList<Object[]>();
        if ("0".equals(buser.getRole())) {
            // admin权限，可以选择添加所有任务人
            sql.append(" select t.id, t.real_name from bk_user t where t.role!=0 ");
            list = common.queryBySql(sql.toString(), params);
        } else if ("1".equals(buser.getRole())) {
            // OM
            sql.append(" select t.id, t.real_name from bk_user t where t.pid=? or t.id=?");
            params.add(id);
            params.add(id);
            list = common.queryBySql(sql.toString(), params);
        } else if ("3".equals(buser.getRole())) {
            // 总监下（OM或者OC）
            List<BuserVO> buserVO = getOCUserListInOMPlus(id);
            list = new ArrayList<Object[]>();
            if (!CollectionUtils.isEmpty(buserVO)) {
                for (BuserVO br : buserVO) {
                    Object[] obj = new Object[2];
                    obj[0] = br.getId();
                    obj[1] = br.getRealname();
                    list.add(obj);
                }
            }
        }
        if (!CollectionUtils.isEmpty(list)) {
            SelectionVO vo = null;
            for (Object[] obj : list) {
                vo = new SelectionVO();
                vo.setValue(DataType.getAsString(obj[0]));
                vo.setName(DataType.getAsString(obj[1]));
                result.add(vo);
            }
        }
        return result;
    }

    public List<BuserVO> getOCUserListInOMPlus(Integer userId) {
        List<BuserVO> result=new ArrayList<BuserVO>();
        StringBuffer sql= new StringBuffer();
        sql.append("    select t.id, t.name, t.role ,t.real_name                       ");
        sql.append("      from bk_user t                                   ");
        sql.append("     where 1=1                        ");
        /*sql.append(" and t.pid in (select ur.id ");
        sql.append("                       from bk_user ur                 ");
        sql.append("                      where ur.pid = '"+userId+"'      ");
        sql.append("                        and ur.is_del = '1')           ");*/
        sql.append("       and t.is_del = '1'                              ");
        sql.append("       and t.role = '2'                                ");
        List<Object[]> list=common.queryBySql(sql.toString(), new ArrayList<>());
        if(!CollectionUtils.isEmpty(list)){
            BuserVO vo=null;
            for(Object[] obj:list){
                vo= new BuserVO();
                vo.setId(DataType.getAsString(obj[0]));
                vo.setRealname(DataType.getAsString(obj[1]));
                result.add(vo);
            }
        }
        return result;
        
    }

    
    @Override
    public void updateTaskStatus(int taskId, String status) {
        BWorkInfo info = taskRepository.findOne(taskId);
        info.setState(status);

        taskRepository.save(info);
    }

    @Override
    public PageModel<Object> findWorkListByUserId(int userId, Long date, Pageable pageable) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("  select t.id,                           ");
        sql.append("         t.content,                      ");
        sql.append("         t.finish_pre_time,              ");
        sql.append("         t.finish_real_time,             ");
        sql.append("         t.name as taskName,                         ");
        sql.append("         t.pre_result,                   ");
        sql.append("         t.real_result,                  ");
        sql.append("         t.start_time,                   ");
        sql.append("         t.state,                        ");
        sql.append("         ur1.real_name as real_name,                   ");
        sql.append("         ur2.real_name as execute_name,                   ");
        sql.append("         typ.type_name,                  ");
        sql.append("         shop.shop_name,                  ");
        sql.append("         t.is_self_create,                  ");
        sql.append("         t.quarter,                  ");
        sql.append("         shop.id as shopId,                  ");
        sql.append("         typ.id as taskTypeId, typ.is_to_store as isToStore                 ");
        sql.append("    from bk_work_info t                  ");
        sql.append("    left join bk_work_type typ           ");
        sql.append("      on t.work_type_id = typ.id         ");
        sql.append("    left join bk_user ur1                 ");
        sql.append("      on ur1.id = t.user_id               ");
        sql.append("    left join bk_user ur2                 ");
        sql.append("      on ur2.id = t.execute_id               ");
        sql.append("    left join bk_shop_info shop          ");
        sql.append("      on shop.id = t.store_id            ");
        sql.append("    where 1=1                  ");
        if (0 != userId) {
            sql.append(" and (t.execute_id = ?) ");
            params.add(userId);
        }
        if (0 != date) {
            sql.append(" and convert(char(10),t.start_time,120)=convert(char(10),'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DataType.getAsDate(date)) + "',120) ");
        }
        Page<Object[]> pageResult = common.queryBySql(sql.toString(), params, pageable);
        PageModel<Object> result = new PageModel<Object>();
        result.setPage(pageResult.getNumber() + 1);
        result.setRows(getRows(pageResult.getContent()));
        result.setPageSize(pageResult.getSize());
        result.setRecords((int) pageResult.getTotalElements());
        return result;
    }
    @Autowired
    IMessageService messageService;
    @Override
    public List<BWorkInfo> saveTasks(List<BWorkInfo> list) {
        List<BWorkInfo> result= new ArrayList<BWorkInfo>();
        if(!CollectionUtils.isEmpty(list)){
            for(BWorkInfo taskInfo:list){
                taskInfo=  taskRepository.save(taskInfo);
                result.add(taskInfo);
                //将任务信息创建到消息表中
              /*  BMessage message = new BMessage();
                message.setMessageType("3");
                message.setPushTitle(workRespository.findOne(taskInfo.getWorktypeid()).getTypename());
                message.setPushContent(taskInfo.getContent());
                message.setCreateTime(new Date());
//                message.setState("1");
                message.setIsRead("0");
                message.setUserId(taskInfo.getUserid());
                message.setPushTime(new Date());
                message.setSenderId(taskInfo.getUserid());
                message.setReceiverId(taskInfo.getExecuteid());
                message.setTaskId(taskInfo.getId());
                messageService.saveBMessage(message);*/
            }
        }
        return result;
    }

    private List getRows(List<Object[]> content) {
        List<TaskVO> result = new ArrayList<TaskVO>();
        if (!CollectionUtils.isEmpty(content)) {
            TaskVO vo = null;
            for (Object[] obj : content) {
                vo = new TaskVO();
                vo.setId(DataType.getAsString(obj[0]));
                vo.setContent(DataType.getAsString(obj[1]));
                if (null != DataType.getAsDate(obj[2])) {
                    vo.setFinishpretime(DataType.getAsDate(obj[2]));
                    vo.setFinishpretimeLong(DataType.getAsDate(obj[2]).getTime());
                }
                if (null != DataType.getAsDate(obj[3])) {
                    vo.setFinishrealtime(DataType.getAsDate(obj[3]));
                    vo.setFinishrealtimeLong(DataType.getAsDate(obj[3]).getTime());
                }
                vo.setName(DataType.getAsString(obj[4]));
                vo.setPreresult(DataType.getAsString(obj[5]));
                vo.setRealresult(DataType.getAsString(obj[6]));
                if (null != DataType.getAsDate(obj[7])) {
                    vo.setStarttime(DataType.getAsDate(obj[7]));
                    vo.setStarttimeLong(DataType.getAsDate(obj[7]).getTime());
                }
                vo.setState(DataType.getAsString(obj[8]));
                vo.setUserName(DataType.getAsString(obj[9]));
                vo.setExecuteName(DataType.getAsString(obj[10]));
                vo.setWorkTypeName(DataType.getAsString(obj[11]));
                vo.setStoreName(DataType.getAsString(obj[12]));
                vo.setIsSelfCreate(DataType.getAsString(obj[13]));
                vo.setQuarter(DataType.getAsString(obj[14]));
                vo.setStoreId(DataType.getAsInt(obj[15]));
                vo.setTaskTypeId(DataType.getAsInt(obj[16]));
                vo.setIsToStore(DataType.getAsInt(obj[17]));
                result.add(vo);
            }
        }
        return result;
    }

    @Override
    public List<TaskVO> getTaskResult(Integer userId, String month, String year) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  select t.id,                           ");
        sql.append("         t.content,                      ");
        sql.append("         t.finish_pre_time,              ");
        sql.append("         t.finish_real_time,             ");
        sql.append("         t.name as taskName,                         ");
        sql.append("         t.pre_result,                   ");
        sql.append("         t.real_result,                  ");
        sql.append("         t.start_time,                   ");
        sql.append("         t.state,                        ");
        sql.append("         ur1.real_name as real_name,                   ");
        sql.append("         ur2.real_name as execute_name,                   ");
        sql.append("         typ.type_name,                  ");
        sql.append("         shop.shop_name,                  ");
        sql.append("         t.is_self_create,                  ");
        sql.append("         t.quarter,                  ");
        sql.append("         shop.id as shopId,                  ");
        sql.append("         t.id as taskTypeId,typ.is_to_store as isToStore                  ");
        sql.append("    from bk_work_info t                  ");
        sql.append("    left join bk_work_type typ           ");
        sql.append("      on t.work_type_id = typ.id         ");
        sql.append("    left join bk_user ur1                 ");
        sql.append("      on ur1.id = t.user_id               ");
        sql.append("    left join bk_user ur2                 ");
        sql.append("      on ur2.id = t.execute_id               ");
        sql.append("    left join bk_shop_info shop          ");
        sql.append("      on shop.id = t.store_id            ");
        sql.append("    where 1=1                  ");
        if (0 != userId) {
            //sql.append(" and (t.user_id = ? or t.execute_id = ?) ");
        	sql.append(" and ( t.execute_id = ?) ");
            params.add(userId);
        }
        if (StringUtils.isNotBlank(month)) {
            sql.append(" and MONTH(t.start_time)=? ");
            params.add(month);
        }
        if (StringUtils.isNotBlank(year)) {
            if(dbChooser.isSQLServer()){
                sql.append(" and DATEDIFF(yy,t.start_time, ?)=0 ");
            }
            else{
                sql.append(" and TIMESTAMPDIFF(year,t.start_time, ?)=0 ");
            }

            params.add(year);
        }
        sql.append(" order by t.id desc ");
        return getRows(common.queryBySql(sql.toString(), params));
    } 
    
    public int countWorkInfo(Integer userId, Integer workTypeId,Date beginTime,int frequency){
        int result=0;
        StringBuffer sql=new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("   SELECT t.id,t.execute_id                                                                 ");
        sql.append("     FROM bk_work_info t   where 1=1                                                 ");
        if(null!=beginTime){
            if(1==frequency){
                //执行周内搜索
                sql.append("    and (DATEPART(wk, convert(char(10),'" + new SimpleDateFormat("yyyy-MM-dd").format(DataType.getAsDate(beginTime)) + "',120)) = DATEPART(wk, t.start_time))         ");
                sql.append("      AND (DATEPART(yy, convert(char(10),'" + new SimpleDateFormat("yyyy-MM-dd").format(DataType.getAsDate(beginTime)) + "',120)) = DATEPART(yy, t.start_time))         ");
            }else if(2==frequency){
                //执行月内搜索
                sql.append("  and (DATEPART(yy, convert(char(10),'" + new SimpleDateFormat("yyyy-MM-dd").format(DataType.getAsDate(beginTime)) + "',120)) = DATEPART(yy, t.start_time))      ");
                sql.append("    AND (DATEPART(mm, convert(char(10),'" + new SimpleDateFormat("yyyy-MM-dd").format(DataType.getAsDate(beginTime)) + "',120)) = DATEPART(mm, t.start_time))      ");
                
            }else if(3==frequency){
                //执行季度搜索
                sql.append("  and DATEPART(qq, convert(char(10),'" + new SimpleDateFormat("yyyy-MM-dd").format(DataType.getAsDate(beginTime)) + "',120)) = DATEPART(qq, t.start_time)    ");
                sql.append("    and DATEPART(yy, convert(char(10),'" + new SimpleDateFormat("yyyy-MM-dd").format(DataType.getAsDate(beginTime)) + "',120)) = DATEPART(yy, t.start_time)    ");
            }
        }
        if(null!=userId){
            sql.append(" and t.execute_id = ? ");
            params.add(userId);
        }
        if(null!=workTypeId){
            sql.append(" and t.work_type_id = ? ");
            params.add(workTypeId);
        }
        List<Object[]> list=common.queryBySql(sql.toString(), params);
        if(CollectionUtils.isEmpty(list)){
            result=0;
        }else{
            result=list.size();
        }
        return result;
    }
    
    @Override
    public boolean isExistSameTask(Integer userId, Integer workTypeId, Date beginTime) {
        BWorkType workType = null;
        // 1:每周       2:月度        3:季度       4:需要时
        Integer frequency = 0;
        if (null != workTypeId) {
            // 获取任务类型
            workType = workRespository.findOne(workTypeId);
            frequency=workType.getFrequency();
        }
        if(frequency==4){
            //需要时
            return false;
        }
       int count= countWorkInfo(userId,workTypeId,beginTime,frequency);
       if(count>0){
           //存在相同的任务
           return true;
       }
        return false;
    }
}
