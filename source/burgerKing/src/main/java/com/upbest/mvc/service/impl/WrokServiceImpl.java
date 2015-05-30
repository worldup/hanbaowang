package com.upbest.mvc.service.impl;
import java.io.*;
import java.text.ParseException;
import java.util.*;

import javax.inject.Inject;

import com.upbest.mvc.entity.BShopInfo;
import com.upbest.utils.ConfigUtil;
import com.upbest.utils.EmailUtils;
import net.sf.jett.transform.ExcelTransformer;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
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
    public String  getWorkPlan4Excel(String omUserId,String ocUserId,String month){
        String result=null;
        String sql="SELECT\n" +
                "\tbu.real_name,wi.work_type_name work_type_name,wi.start_time start_time,weekday(wi.start_time)+1 weekidx,DAYOFMONTH(wi.start_time) dayidx, DATE_FORMAT(wi.start_time,'%Y-%m-%d') w_day,DATE_FORMAT(wi.start_time,'%H:%i') w_time,CONCAT(DATE_FORMAT(wi.start_time,'%H:%i') ,' ',ifnull(si.shop_num,''),' ',wi.work_type_name,' ',substr(wi.content,1,10)) w_content,0 is_nowork\n" +
                "FROM\n" +
                "\tbk_work_info wi left join bk_shop_info  si\n" +
                "\ton wi.store_id=si.id\n" +
                "\tleft join bk_user bu on wi.execute_id=bu.id\n" +
                "WHERE\n" +
                "\twi.start_time >= STR_TO_DATE(:month,'%Y-%m-%d')\n" +
                "AND wi.start_time < DATE_ADD(STR_TO_DATE(:month,'%Y-%m-%d'),INTERVAL 1 month)\n" +
                "and wi.execute_id=:user_id\n" +
                "union all \n" +
                "select bu.real_name,''work_type_name,wl.day start_time,weekday(wl.day)+1 weekidx,DAYOFMONTH(wl.day) dayidx,DATE_FORMAT(wl.day,'%Y-%m-%d') w_day ,DATE_FORMAT(wl.day,'%H:%i') w_time,wl.nonworkingtype w_content,1 is_nowork from bk_user_working_leave  wl left join bk_user bu\n" +
                "on wl.userId=bu.id\n" +
                " where wl.day>=STR_TO_DATE(:month,'%Y-%m-%d')\n" +
                "and wl.day<DATE_ADD(STR_TO_DATE(:month,'%Y-%m-%d'),INTERVAL 1 month) \n" +
                "and wl.userId=:user_id ORDER  by start_time\n";
        Map<String ,String> map=new HashMap();
        map.put("user_id",ocUserId);
        map.put("month", month);
        List<Map<String,Object>> lists=  jdbcTemplate.queryForList(sql,map);
        Buser ocUser=userRepository.findOne(DataType.getAsInt(ocUserId));
        Buser omUser=userRepository.findOne(DataType.getAsInt(omUserId));
        String userName=ocUser.getRealname();
        byte[] excelBytes=genWorkplan2Excel(lists, userName, month);
        ByteArrayResource resource = new ByteArrayResource(excelBytes);
           try {
//
//                FileUtils.writeByteArrayToFile(new File("d:\\rili.xls"), resource.getByteArray());
               String y="";
               String m="";
               String[] dayStr= StringUtils.split(month,"-");
               if(dayStr.length==3){
                   y=dayStr[0];
                   m=Integer.parseInt(dayStr[1])+"";
               }
               String subject = userName + y + "年" + m + "月工作计划";
               StringBuilder text = new StringBuilder();
               text.append(omUser.getRealname()+"：\n")
                       .append("   你好，附件是" + userName + y + "年" + m + "月工作计划。\n" +
                               "若有疑问请直接联系" + userName+"。\n")
                       .append("===============请不要直接回复这个邮件，这是由系统生成的邮件===============");
               String filePath = ConfigUtil.get("filePath")+"yueli";
               File picSaveDir = new File(filePath);
               if (!picSaveDir.exists())
                   picSaveDir.mkdirs();

               new EmailUtils(emailSender).sendEmailWithAttachment(omUser.getName(), subject,subject, subject + ".xls", resource);
               result="/"+ DigestUtils.md5Hex(subject)+".xls";
               FileOutputStream fileOutputStream=new FileOutputStream(  new File(filePath+result) );
               fileOutputStream.write(excelBytes);
               fileOutputStream.close();

            } catch (Exception e) {
               e.printStackTrace();
            }
        return "yueli"+ result;
    }
    @Override
    public  List<Map<String,Object>> getAllWorkPlanByUserId(String userId,String month){
       String sql="SELECT\n" +
               "\tDATE_FORMAT(w.start_time, '%Y-%m-%d') day,\n" +
               "\tDATE_FORMAT(w.start_time, '%H:%i:%s') time,\n" +
               "  s.shop_name shop, s.email shopEmail ,( select  u.name from bk_user u where u.id=w.execute_id)userEmail,\n" +
               "               (select PU.name from bk_user pu where PU.id=(select pid from bk_user where id=w.execute_id)  ) puserEmail,\n" +
               "               ( select  u.real_name from bk_user u where u.id=w.execute_id) executeName,\n" +
               "               ( select  u.real_name from bk_user u where u.id=w.user_id) userName," +
               "w.work_type_name taskName,\n" +
               "w.content content ,w.ishidden ishidden ,w.is_self_create isSelfCreate \n" +
               "FROM\n" +
               "\tbk_work_info w LEFT JOIN bk_user u \n" +
               "  on w.execute_id=u.id\n" +
               "  left join bk_shop_info s\n" +
               "  on w.store_id=s.id\n" +
               "  where  w.execute_id=:user_id and  start_time>=STR_TO_DATE(:month,'%Y-%m-%d')\n" +
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
           sendMailRest(month,userId);
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
            sendMailRest(month,userId);
        }
    }
    private  Map<String,List<Map<String,Object>>> listAllTaskGroupByRestName(String month, String userId ){
         String shopTaskSql=  "SELECT  " +
                 " DATE_FORMAT(w.start_time, '%Y-%m-%d') DAY,  " +
                 " DATE_FORMAT(w.start_time, '%H:%i:%s') time,  " +
                 " s.shop_name shop,  " +
                 " s.email shopEmail,  " +
                 " (  " +
                 "  SELECT  " +
                 "   u. NAME  " +
                 "  FROM  " +
                 "   bk_user u  " +
                 "  WHERE  " +
                 "   u.id = w.user_id  " +
                 " ) userEmail,  " +
                 " (  " +
                 "  SELECT  " +
                 "   PU. NAME  " +
                 "  FROM  " +
                 "   bk_user pu  " +
                 "  WHERE  " +
                 "   PU.id = (  " +
                 "    SELECT  " +
                 "     pid  " +
                 "    FROM  " +
                 "     bk_user  " +
                 "    WHERE  " +
                 "     id = w.user_id  " +
                 "   )  " +
                 " ) puserEmail,  " +
                 " (  " +
                 "  SELECT  " +
                 "   u.real_name  " +
                 "  FROM  " +
                 "   bk_user u  " +
                 "  WHERE  " +
                 "   u.id = w.execute_id  " +
                 " ) executeName,  " +
                 " w.execute_id,  " +
                 " (  " +
                 "  SELECT  " +
                 "   u.real_name  " +
                 "  FROM  " +
                 "   bk_user u  " +
                 "  WHERE  " +
                 "   u.id = w.user_id  " +
                 " ) userName,  " +
                 " w.work_type_name taskName,  " +
                 " w.content content,  " +
                 " w.ishidden ishidden,  " +
                 " w.is_self_create isSelfCreate  " +
                 "FROM  " +
                 " bk_work_info w  " +
                 "LEFT JOIN bk_user u ON w.execute_id = u.id  " +
                 "left JOIN bk_shop_info s ON w.store_id = s.id  " +
                 "WHERE  " +
                 " w.store_id<>0 and s.shop_name<>'其他餐厅' and   " +
                 " w.execute_id = :user_id  " +
                 "AND start_time >= STR_TO_DATE(:month, '%Y-%m-%d')  " +
                 "AND start_time < DATE_ADD(  " +
                 " STR_TO_DATE(:month, '%Y-%m-%d'),  " +
                 " INTERVAL 1 MONTH  " +
                 ")  " +
                 "UNION ALL  " +
                 " SELECT  " +
                 "  DATE_FORMAT(w.start_time, '%Y-%m-%d') DAY,  " +
                 "  DATE_FORMAT(w.start_time, '%H:%i:%s') time,  " +
                 "  s.shop_name shop,  " +
                 "  s.email shopEmail,  " +
                 "  (  " +
                 "   SELECT  " +
                 "    u. NAME  " +
                 "   FROM  " +
                 "    bk_user u  " +
                 "   WHERE  " +
                 "    u.id = w.user_id  " +
                 "  ) userEmail,  " +
                 "  (  " +
                 "   SELECT  " +
                 "    PU. NAME  " +
                 "   FROM  " +
                 "    bk_user pu  " +
                 "   WHERE  " +
                 "    PU.id = (  " +
                 "     SELECT  " +
                 "      pid  " +
                 "     FROM  " +
                 "      bk_user  " +
                 "     WHERE  " +
                 "      id = w.user_id  " +
                 "    )  " +
                 "  ) puserEmail,  " +
                 "  (  " +
                 "   SELECT  " +
                 "    u.real_name  " +
                 "   FROM  " +
                 "    bk_user u  " +
                 "   WHERE  " +
                 "    u.id = w.execute_id  " +
                 "  ) executeName,  " +
                 "  w.execute_id,  " +
                 "  (  " +
                 "   SELECT  " +
                 "    u.real_name  " +
                 "   FROM  " +
                 "    bk_user u  " +
                 "   WHERE  " +
                 "    u.id = w.user_id  " +
                 "  ) userName,  " +
                 "  w.work_type_name taskName,  " +
                 "  w.content content,  " +
                 "  w.ishidden ishidden,  " +
                 "  w.is_self_create isSelfCreate  " +
                 " FROM  " +
                 "  bk_work_info w  " +
                 " LEFT JOIN bk_user u ON w.execute_id = u.id  " +
                 "  JOIN bk_shop_info s ON s.id IN (  " +
                 "  SELECT  " +
                 "   u.shop_id  " +
                 "  FROM  " +
                 "   bk_shop_user u  " +
                 "  WHERE  " +
                 "   u.user_id = :user_id  " +
                 " )  " +
                 " WHERE  " +
                 "  w.execute_id = :user_id  " +
                 " AND start_time >= STR_TO_DATE(:month, '%Y-%m-%d')  " +
                 " AND start_time < DATE_ADD(  " +
                 "  STR_TO_DATE(:month, '%Y-%m-%d'),  " +
                 "  INTERVAL 1 MONTH  " +
                 " )  " +
                 " AND ( w.store_id in (select id from bk_shop_info where shop_name='其他餐厅') or w.store_id=0)  ";
        Map<String ,String> queryMap=new HashMap();
        queryMap.put("user_id",userId);
        queryMap.put("month", month);
        List<Map<String,Object> >  mapList=  jdbcTemplate.queryForList(shopTaskSql, queryMap);
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
                        if(!"1".equals(MapUtils.getString(map, "ishidden"))&&!"1".equals(MapUtils.getString(map,"isSelfCreate"))){
                            shopList.add(map);
                        }
                    }
                    else{
                        List<Map<String,Object>> shopList=new ArrayList();
                        if(!"1".equals(MapUtils.getString(map, "ishidden"))&&!"1".equals(MapUtils.getString(map,"isSelfCreate"))){
                            shopList.add(map);
                        }
                        result.put(shop,shopList);
                    }
                }


            }

        }
       /* for(List<Map<String,Object>> values:result.values()){
            for(Map<String,Object> map:mapList){
                String shop= MapUtils.getString(map,"shop");
                if((StringUtils.isEmpty(shop)||"其他餐厅".equals(shop))&&!"1".equals(MapUtils.getString(map, "ishidden"))){
                    values.add(map);
                }
            }
        }*/
        return result;
    }
    private void sendMailRest( String _month,String user_id){
        Map<String,List<Map<String,Object>>> map= listAllTaskGroupByRestName( _month,user_id);
        for(Map.Entry<String,List<Map<String,Object>>> entry:map.entrySet()){
          List<Map<String,Object>> workList=  entry.getValue();
            byte[] attachement=genExcelFromWorkPlanList(workList);
            Map<String,Object> info=workList.get(0);
            String userName= MapUtils.getString(info, "executeName");
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
                System.out.println("---->"+shopEmail);
                new EmailUtils(emailSender).sendEmailWithAttachment(shopEmail, fileName,text.toString(), fileName + ".xlsx", resource);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    private void sendMail(List<Map<String,Object>> workList,String email){
        byte[] attachement=genExcelFromWorkPlanList(workList);
        Map<String,Object> info=workList.get(0);
        String executeName= MapUtils.getString(info, "executeName");
        String day= MapUtils.getString(info,"day");
        String year="";
        String month="";
        String[] dayStr= StringUtils.split(day,"-");
        if(dayStr.length==3){
            year=dayStr[0];
            month=Integer.parseInt(dayStr[1])+"";
        }
        String fileName = executeName + year + "年" + month + "月工作计划";
        StringBuilder text = new StringBuilder();
        text.append("餐厅经理：\n")
                .append("   你好，附件是" + executeName + year + "年" + month + "月工作计划。\n" +
                        "若有疑问请直接联系" + executeName + "。\n")
                .append("===============请不要直接回复这个邮件，这是由系统生成的邮件===============");


        ByteArrayResource resource = new ByteArrayResource(attachement);
        try {
            new EmailUtils(emailSender).sendEmailWithAttachment(email, fileName, text.toString(), fileName + ".xlsx", resource);
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
//        static final String[] weekstr={"Sunday 星期日","Monday  星期一","Tuesday   星期二","Wednesday星期三","Thursday 星期四","Friday  星期五","Saturday 星期六"};
        static final String [] leaveStr={"","节假日","周末" ,"病假","事假"};
        private byte[] genWorkplan2Excel(List<Map<String,Object>> mapList,String userName,String month ){

            ClassPathResource classPathResource=new ClassPathResource("template\\workplan.xls");
            Map beans=new HashMap();
            Map title=new HashMap();
            title.put("userName",userName);
            try {
                Date date=DateUtils.parseDate(month,new String[]{"yyyy-MM-dd"});
                Calendar calendar=GregorianCalendar.getInstance();
                calendar.setTime(date);
                int weekIdx= calendar.get(Calendar.DAY_OF_WEEK);
                //设置天数
                int maxDay=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                for(int i=1;i<=maxDay;i++){
                    Map map=new HashMap();
                    if(CollectionUtils.isNotEmpty(mapList)){
                        int contentIdx=0;
                        for(Map<String,Object> dayWorkMap:mapList){
                            if(MapUtils.getIntValue(dayWorkMap,"dayidx")==i){
                                contentIdx+=1;
                                int is_nowork= MapUtils.getIntValue(dayWorkMap,"is_nowork",0);
                                //设置每日的标头
                                 if(is_nowork!=0){
                                     map.put("title",leaveStr[is_nowork]);
                                 }else if(is_nowork==0&&!map.containsKey("title")){
                                     map.put("title",MapUtils.getString(dayWorkMap,"w_time"));
                                 }
                                //设置每日内容
                                if(is_nowork==0){
                                    String w_time=MapUtils.getString(dayWorkMap,"w_time","");
                                    String work_type_name=MapUtils.getString(dayWorkMap,"work_type_name","");
                                    String w_content=MapUtils.getString(dayWorkMap,"w_content","");
                                    String shop_num=MapUtils.getString(dayWorkMap,"shop_num","");
                                   /* if(w_content.length()>10){
                                       w_content=w_content.substring(0,9);
                                    }*/
                                  //  map.put("content" + contentIdx, new StringBuilder().append(w_time).append(" ").append(shop_num).append(" ").append(work_type_name).append(" ").append(w_content));
                                    map.put("content" + contentIdx,  w_content);

                                }
                            }
                        }
                    }

                    map.put("no",String.valueOf(i));
                    title.put("d"+(i+weekIdx-1),map);
                }
                String titleMonth=  DateFormatUtils.format(date,"yyyy/MM");
                title.put("month",titleMonth);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            beans.put("w",title);
            ExcelTransformer transformer = new ExcelTransformer();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try{
                Workbook workbook= transformer.transform(classPathResource.getInputStream(), beans);
                workbook.write(outputStream);
            }catch (Exception e){
                e.printStackTrace();
            }
//            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
//            try {
//
//                FileUtils.writeByteArrayToFile(new File("d:\\rili.xls"), resource.getByteArray());
//             //   new EmailUtils(emailSender).sendEmailWithAttachment("13636462617@163.com", "日程","日历", "日程" + ".xlsx", resource);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            return outputStream.toByteArray();
        }

}
