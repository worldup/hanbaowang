package com.upbest.mvc.service.impl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

import javax.inject.Inject;

import com.upbest.mvc.entity.BShopInfo;
import com.upbest.utils.EmailUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.upbest.mvc.entity.BWorkType;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.repository.factory.UserRespository;
import com.upbest.mvc.repository.factory.WorkRespository;
import com.upbest.mvc.service.CommonDaoCustom;
import com.upbest.mvc.service.IWorkService;
import com.upbest.utils.DataType;
import com.upbest.utils.RoleFactory;

@Service
public class WrokServiceImpl implements IWorkService{
    
    @Inject
    protected WorkRespository workRepository;
    @Autowired
    private JavaMailSenderImpl emailSender;
    @Inject
    protected UserRespository userRepository;
    
    @Autowired
    private CommonDaoCustom<Object[]> common;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void saveBWorkType(BWorkType entity) {
        workRepository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
        BWorkType entity=workRepository.findOne(id);
        workRepository.delete(entity);
    }

    @Override
    public BWorkType findById(Integer id) {
        return workRepository.findOne(id);
    }
    
    @Override
    public Page<Object[]> findWorkList(String typeName, Pageable pageable) {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("  SELECT s.id,                       ");
        sql.append("         s.type_name,       ");
        sql.append("         s.type_role,       ");
        sql.append("         s.frequency,       ");
        sql.append("         s.is_to_store ,s.is_exam_type ,s.exam_type     ");
        sql.append("    FROM bk_work_type s  ");
        sql.append("   where 1=1                       ");
        if (StringUtils.isNotBlank(typeName)) {
            sql.append(" and s.type_name like ?");
            params.add("%" + typeName + "%");
        }
        return common.queryBySql(sql.toString(), params, pageable);
    }

    @Override
    public List<BWorkType> findWork(String userId) {
        List<BWorkType> typeList= workRepository.findByTypenameNotOrderBySortNumAsc("其它");
        List<BWorkType> result=new ArrayList<BWorkType>();
        Buser buser=userRepository.findOne(DataType.getAsInt(userId));
        String roleName=RoleFactory.getRoleName(buser.getRole());
        if(StringUtils.isNotBlank(userId)){
            for(BWorkType type:typeList){
                String typeRole=type.getTyperole();
                String[] typeRoleAry=typeRole.split(",");
                for(int i=0;i<typeRoleAry.length;i++){
                    if(roleName.equals(typeRoleAry[i])){
                        result.add(type);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<BWorkType> findWorkWithOutNeeded(String userId) {
        List<BWorkType> typeList= workRepository.findWorkTypeWithOutNeeded();
        List<BWorkType> result=new ArrayList<BWorkType>();
        Buser buser=userRepository.findOne(DataType.getAsInt(userId));
        String roleName=RoleFactory.getRoleName(buser.getRole());
        if(StringUtils.isNotBlank(userId)){
            for(BWorkType type:typeList){
                String typeRole=type.getTyperole();
                String[] typeRoleAry=typeRole.split(",");
                for(int i=0;i<typeRoleAry.length;i++){
                    if(roleName.equals(typeRoleAry[i])){
                        result.add(type);
                    }
                }
            }
        }
        return result;
    }
    
    public List<BWorkType> findWorkWithOutNeededAndQuarter(String userId) {
        List<BWorkType> typeList= workRepository.findWorkTypeWithOutNeededAndQuarter();
        List<BWorkType> result=new ArrayList<BWorkType>();
        Buser buser=userRepository.findOne(DataType.getAsInt(userId));
        String roleName=RoleFactory.getRoleName(buser.getRole());
        if(StringUtils.isNotBlank(userId)){
            for(BWorkType type:typeList){
                String typeRole=type.getTyperole();
                String[] typeRoleAry=typeRole.split(",");
                for(int i=0;i<typeRoleAry.length;i++){
                    if(roleName.equals(typeRoleAry[i])){
                        result.add(type);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public int getMaxSortNum() {
        //获取最大排序
        return workRepository.getMaxSortNum();
    }

    @Override
    public void saveBWorkType(List<BWorkType> list) {
        workRepository.save(list);
    }
    
    @Override
    public  List<Map<String,Object>> getAllWorkPlanByUserId(String userId,String month){
       String sql="SELECT\n" +
               "\tDATE_FORMAT(w.start_time, '%Y-%m-%d') day,\n" +
               "\tDATE_FORMAT(w.start_time, '%H:%i:%s') time,\n" +
               "  s.shop_name shop, s.email shopEmail , u.name userEmail,\n" +
               "\t(select PU.name from bk_user pu where PU.id=u.pid limit 1) puserEmail,\n" +
               "  u.real_name userName,\n" +
               "w.work_type_name taskName,\n" +
               "w.content content ,w.ishidden ishidden \n" +
               "FROM\n" +
               "\tbk_work_info w LEFT JOIN bk_user u \n" +
               "  on w.user_id=u.id\n" +
               "  left join bk_shop_info s\n" +
               "  on w.store_id=s.id\n" +
               "  where w.user_id=:user_id and  start_time>=STR_TO_DATE(:month,'%Y-%m-%d')\n" +
               "and start_time <DATE_ADD(STR_TO_DATE(:month,'%Y-%m-%d'),INTERVAL 1 month) order by w.start_time  ";
        Map<String ,String> map=new HashMap();
        map.put("user_id",userId);
        map.put("month", month);
      return  jdbcTemplate.queryForList(sql,map);
    }

   public void sendWorkPlanMailByUserId(String userId,String month){
       List<Map<String,Object>>  mapList=getAllWorkPlanByUserId( userId,month);

       if(CollectionUtils.isNotEmpty(mapList)){
           Object userEmail=mapList.get(0).get("userEmail");
           Object shopEmail=mapList.get(0).get("shopEmail");
           //发送员工本人及上级工作计划
         //  sendMail(mapList, "13636462617@163.com;xin.feng@bkchina.cn;646312851@qq.com");
           sendMail(mapList, userEmail+";"+shopEmail);
           //发送餐厅工作计划
           sendMailRest(mapList);
       }
    }
    public void sendWorkPlanMailByUserIdExt(String userId,String month,String emails){
        List<Map<String,Object>>  mapList=getAllWorkPlanByUserId( userId,month);
        if(CollectionUtils.isNotEmpty(mapList)){

            //发送员工本人及上级工作计划
            Object userEmail=mapList.get(0).get("userEmail");
            Object puserEmail=mapList.get(0).get("puserEmail");
            if(emails!=null){
              emails=emails.replace(",",";");
            }
            if(emails==null){
                emails="";
            }else
            {
                emails+=";";
            }
            emails=(emails+userEmail+";"+puserEmail);
           // sendMail(mapList,"13636462617@163.com;xin.feng@bkchina.cn;646312851@qq.com");
            sendMail(mapList,emails);
            //发送餐厅工作计划
            sendMailRest(mapList);
        }
    }
    private  Map<String,List<Map<String,Object>>> listAllTaskGroupByRestName(List<Map<String,Object>>  mapList){
        Map<String,List<Map<String,Object>>> result=new HashMap();
        Set<String> showNameSet=new HashSet();
        for(Map<String,Object> map:mapList){
           String shop= MapUtils.getString(map,"shop");
           if(shop!=null&&!shop.equalsIgnoreCase("null")){
               showNameSet.add(shop);
           }
        }
        for(String shop:showNameSet){
            for(Map<String,Object> map:mapList){
                String tempShopName= MapUtils.getString(map,"shop");
                if(shop.equals(tempShopName)){
                    if(result.containsKey(shop)){
                        List<Map<String,Object>> shopList=  result.get(shop);
                        //如果非隐藏
                        if(!"1".equals(MapUtils.getString(map, "ishidden"))){
                            shopList.add(map);
                        }
                    }
                    else{
                        List<Map<String,Object>> shopList=new ArrayList();
                        if(!"1".equals(MapUtils.getString(map, "ishidden"))){
                            shopList.add(map);
                        }
                        result.put(shop,shopList);
                    }
                }


            }

        }
        for(List<Map<String,Object>> values:result.values()){
            for(Map<String,Object> map:mapList){
                String shop= MapUtils.getString(map,"shop");
                if(StringUtils.isEmpty(shop)){
                    values.add(map);
                }
            }
        }
        return result;
    }
    private void sendMailRest( List<Map<String,Object>> allWorkList){
        Map<String,List<Map<String,Object>>> map= listAllTaskGroupByRestName( allWorkList);
        for(Map.Entry<String,List<Map<String,Object>>> entry:map.entrySet()){
          List<Map<String,Object>> workList=  entry.getValue();
            byte[] attachement=genExcelFromWorkPlanList(workList);
            Map<String,Object> info=workList.get(0);
            String userName= MapUtils.getString(info, "userName");
            String day= MapUtils.getString(info,"day");
            String shopEmail=MapUtils.getString(info,"shopEmail");
            String year="";
            String month="";
            String[] dayStr= StringUtils.split(day,"-");
            if(dayStr.length==3){
                year=dayStr[0];
                month=Integer.parseInt(dayStr[1])+"";
            }
            String fileName = userName + year + "年" + month + "月工作计划";
            StringBuilder text = new StringBuilder();
            text.append("餐厅经理：\n")
                    .append("   你好，附件是" + userName + year + "年" + month + "月工作计划。\n" +
                            "若有疑问请直接联系" + userName+"。\n")
                    .append("===============请不要直接回复这个邮件，这是由系统生成的邮件===============");


            ByteArrayResource resource = new ByteArrayResource(attachement);
            try {
             //   shopEmail="13636462617@163.com;xin.feng@bkchina.cn;646312851@qq.com";
                new EmailUtils(emailSender).sendEmailWithAttachment(shopEmail, fileName,text.toString(), fileName + ".xlsx", resource);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    private void sendMail(List<Map<String,Object>> workList,String email){
        byte[] attachement=genExcelFromWorkPlanList(workList);
        Map<String,Object> info=workList.get(0);
        String userName= MapUtils.getString(info, "userName");
        String day= MapUtils.getString(info,"day");
        String year="";
        String month="";
        String[] dayStr= StringUtils.split(day,"-");
        if(dayStr.length==3){
            year=dayStr[0];
            month=Integer.parseInt(dayStr[1])+"";
        }
        String fileName = userName + year + "年" + month + "月工作计划";
        StringBuilder text = new StringBuilder();
        text.append("餐厅经理：\n")
                .append("   你好，附件是" + userName + year + "年" + month + "月工作计划。\n" +
                        "若有疑问请直接联系" + userName+"。\n")
                .append("===============请不要直接回复这个邮件，这是由系统生成的邮件===============");


        ByteArrayResource resource = new ByteArrayResource(attachement);
        try {
            new EmailUtils(emailSender).sendEmailWithAttachment(email, fileName,text.toString(), fileName + ".xlsx", resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private byte[] genExcelFromWorkPlanList(List<Map<String,Object>> workList){
        Workbook wb=new XSSFWorkbook();
        Sheet sheet=  wb.createSheet("任务排程");
        Row title= sheet.createRow(0);
        title.createCell(0).setCellValue("日期");
        title.createCell(1).setCellValue("开始时间");
        title.createCell(2).setCellValue("任务门店");
        title.createCell(3).setCellValue("委派人");
        title.createCell(4).setCellValue("任务名称");
        title.createCell(5).setCellValue("任务详细");
        if(CollectionUtils.isNotEmpty(workList)){
            int i=1;
            for(Map<String,Object> map:workList){
                Row row=sheet.createRow(i);

                row.createCell(0).setCellValue(MapUtils.getString(map, "day"));
                row.createCell(1).setCellValue(MapUtils.getString(map,"time"));
                row.createCell(2).setCellValue(MapUtils.getString(map,"shop"));
                row.createCell(3).setCellValue(MapUtils.getString(map,"userName"));
                row.createCell(4).setCellValue(MapUtils.getString(map,"taskName"));
                row.createCell(5).setCellValue(MapUtils.getString(map,"content"));
              i++;
            }
        }
        ByteArrayOutputStream  byteArrayOutputStream=new ByteArrayOutputStream();
        try {
            wb.write(byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

}
