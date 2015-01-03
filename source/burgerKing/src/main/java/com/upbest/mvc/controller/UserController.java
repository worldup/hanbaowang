package com.upbest.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.repository.factory.UserRespository;
import com.upbest.mvc.service.IBuserService;
import com.upbest.mvc.vo.BuserVO;
import com.upbest.mvc.vo.SelectionVO;
import com.upbest.pageModel.Json;
import com.upbest.utils.CommonUtils;
import com.upbest.utils.DataType;
import com.upbest.utils.DownloadFileUtils;
import com.upbest.utils.PageModel;
import com.upbest.utils.PasswordUtil;

/**
 * 
 * @ClassName   类  名   称：	UserController.java
 * @Description 功能描述：	用户管理模块
 * @author      创  建   者：	<A HREF="jhzhao@upbest-china.com">jhzhap</A>	
 * @date        创建日期：	2014年9月15日下午11:35:15
 */
@Controller
@RequestMapping(value = "/user/")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    IBuserService userService;
    @Inject
    protected UserRespository userRespository;
    
    private static final String TEMPLATE_FILE_PATH = "excelTemp/user.xlsx";
    

    @RequestMapping(value = "/index")
    public String index(Model model){
        model.addAttribute("current", "user");
        return "/user/userList";
    }
    
    
    @ResponseBody
    @RequestMapping("/detail")
    public void detail(@RequestParam(value = "id", required = false) String id,
            Model model,
            HttpServletResponse response, HttpServletRequest request) {
        Buser user=userService.findById(Integer.parseInt(id));
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json,response);
    }
    
    @ResponseBody
    @RequestMapping("/list")
    public void list(@RequestParam(value = "realName", required = false) String name,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "sidx", required = false) String sidx,
            @RequestParam(value = "sord", required = false) String sord,
            @RequestParam(value = "rows", 
            required = false) Integer pageSize, 
            Model model,
            HttpSession session,
            HttpServletResponse response) {
        if (pageSize == null) {
            pageSize = 10;
        }
        Order or=null;
        List<Order> orders=new ArrayList<Order>();
        if("role".equals(sidx)){
            sidx="t.role";
        }
        if(StringUtils.isNotBlank(sord)&&StringUtils.isNotBlank(sidx)){
            if(sord.equalsIgnoreCase("desc")){
                 or=new Order(Direction.DESC,sidx);
            }else{
                 or=new Order(Direction.ASC,sidx);
            }
            orders.add(or);
        }
        Sort so=new Sort(orders);
        PageRequest requestPage = new PageRequest(page != null ? page.intValue() - 1 : 0, pageSize, so);
        Page<Object[]> shop =userService.findUserList(name, requestPage);
        PageModel result=new PageModel();
        result.setPage(page);
        result.setRows(getShopInfo(shop.getContent()));
        result.setPageSize(pageSize);
        result.setRecords(NumberUtils.toInt(ObjectUtils.toString(shop.getTotalElements())));
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json,response);
    }
    
    private List<BuserVO> getShopInfo(List<Object[]> list){
        List<BuserVO> result=new ArrayList<BuserVO>();
        if(!CollectionUtils.isEmpty(list)){ 
            BuserVO entity=null;
            for(Object[] obj:list){
                entity=new BuserVO();
                entity.setId(DataType.getAsString(obj[0]));
                entity.setCreatedate(DataType.getAsString(obj[1]));
                entity.setRole(DataType.getAsString(obj[2]));
                entity.setModifydate(DataType.getAsString(obj[3]));
                entity.setName(DataType.getAsString(obj[4]));
                entity.setPwd(DataType.getAsString(obj[5]));
                entity.setRealname(DataType.getAsString(obj[6]));
                entity.setTelephone(DataType.getAsString(obj[7]));
                entity.setIsdel(DataType.getAsString(obj[8]));
                entity.setPid(DataType.getAsString(obj[9]));
                entity.setEmp(DataType.getAsString(obj[10]));
                result.add(entity);
            }
        }
        return result;
    }
    private void outPrint(String str, HttpServletResponse response) {
        try {
            PrintWriter writer = response.getWriter();
            writer.write(str);
            writer.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/insertBuser", method = RequestMethod.POST)
    public void insertBuser(Buser user) {
        userService.insertBuser(user);
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/upBuser", method = RequestMethod.POST)
    public String upBuser(Buser user) {
        user.setModifydate(new Date());
        userService.upBuser(user);
        return "login";
    }
    
    @ResponseBody
    @RequestMapping(value = "/del")
    public void del(@RequestParam(value = "id", required = false) String id, Model model, HttpServletResponse response, HttpServletRequest request) {
        userService.delBuser(Integer.parseInt(id));
        outPrint("0", response);
        
    }
    
    @RequestMapping(value = "get")
    public String get(String id, Model model, HttpSession session) {
        Buser user = userService.findById(Integer.parseInt(id));
        model.addAttribute("user",user);
        return "/user/getUser";
    }
    @RequestMapping(value = "/create")
    public String createForm(String id, Model model) {
        if(StringUtils.isNotBlank(id)){
            Buser user = userService.findById(Integer.parseInt(id));
            model.addAttribute("user",user);
        }
        return "/user/addUser";
    }
    @ResponseBody
    @RequestMapping("/addUser")
    public void list(@RequestParam(value = "jsons", required = false) String vo,
            @RequestParam(value = "oldPwd", required = false) String oldPwd,
            Model model, HttpSession session, HttpServletResponse response) {
        Buser user = new Buser();
        JSONObject jso = JSONObject.parseObject(vo);
        user = (Buser) JSONObject.toJavaObject(jso,
                Buser.class);
        
        if(oldPwd.equals(user.getPwd()))
        {
            user.setPwd(oldPwd);
        }
        else
        {
            user.setPwd(PasswordUtil.genPassword(user.getPwd()));
        }
        if(user.getId()!=null){
            user.setModifydate(new Date());
        }else{
            user.setCreatedate(new Date());
        }
        user.setIsdel("1");
        userService.insertBuser(user);
    }   

    @RequestMapping(value = "up")
    public String up(String id, Model model, HttpSession session) {
        Buser user = userService.findById(Integer.parseInt(id));
        model.addAttribute("user",user);
        return "/user/upUser";
    }
    @ResponseBody
    @RequestMapping("/loadUser")
    public void loadUser(@RequestParam(value = "role", required = false) String role,HttpServletResponse response) {
        List<SelectionVO> storeList=userService.getUserList(role);
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(storeList, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json, response);
    }
    
    @RequestMapping("/import")
    public String importExcel(){
        return "user/uploadUser";
    }
    @RequestMapping("/toImport")
    @ResponseBody
    public void toImport(@RequestParam("userFile") MultipartFile file,
            String filename,HttpServletResponse response) throws Exception {
        Json json = new Json();
        try {
            userService.addUserFromExcel(new ByteArrayResource(file.getBytes()));
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(true);
        }
        PrintWriter writer = response.getWriter();
        writer.print(CommonUtils.toJson(json));
        writer.close();

    }
    @RequestMapping("/downloadTemp")
    public void getdownloadTemp(
            @RequestParam(value = "filename", required = false) String filename,
            HttpServletResponse response, HttpServletRequest request)
            throws IOException {
        String rootPath = request.getSession().getServletContext()
                .getRealPath("/");
        String filePath = rootPath + TEMPLATE_FILE_PATH;
        DownloadFileUtils.download(new File(filePath), filename, response);

    }
    
    @ResponseBody
    @RequestMapping("/countUser")
    public int countUser(@RequestParam(value = "username") String username) {
        return userService.findUserCountByName(username);
    }
    @ResponseBody
    @RequestMapping("/empUser")
    public int empUser(@RequestParam(value = "emp") String emp) {
        return userService.findUserCountByEmp(emp);
    }
    
    
    
}

