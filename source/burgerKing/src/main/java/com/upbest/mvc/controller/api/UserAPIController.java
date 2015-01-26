package com.upbest.mvc.controller.api;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.upbest.mvc.vo.BuserVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.upbest.mvc.constant.Constant.Code;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.repository.factory.UserRespository;
import com.upbest.mvc.service.IBuserService;
import com.upbest.pageModel.Json;
import com.upbest.utils.ConfigUtil;
import com.upbest.utils.Constant;
import com.upbest.utils.PasswordUtil;

/**
 * 用户接口
 * @author QunZheng
 *
 */
@Controller
@RequestMapping("/api/user")
public class UserAPIController {

    @Autowired
    private IBuserService service;

    @Inject
    private UserRespository urRes;

    public static final String SUCCESS = "操作成功";
    public static final String ERROR = "操作失败";

    private static final Logger logger = LoggerFactory.getLogger(UserAPIController.class);

    /**
     * 更新用户头像接口
     * @param req
     * @return
     */
    @RequestMapping(value = "/securi_updateHeadImg")
    @ResponseBody
    public Json updateHead(@RequestParam("headFile") MultipartFile file, @RequestParam(value = "userId") int userId, HttpServletRequest req) {
        Json result = new Json();

        String headPath = ConfigUtil.get("userHeadImagePath");
        String fileName = "headimage" + userId + new Date().getTime() + getSuffic(file.getOriginalFilename());
        String filePath = headPath + fileName;
        String rootPath = req.getSession().getServletContext().getRealPath("/");

        try {
            File picSaveDir = new File(headPath);
            if (!picSaveDir.exists())
                picSaveDir.mkdirs();
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File( filePath));
            URL url=new URL(req.getRequestURL().toString());
            String   urlPath=  "/upload/user_head_image/"+fileName;

            System.out.println("hello test!");
            System.out.println(urlPath);
            
            service.updateHeadImage(userId, urlPath);
            
            result.setObj(urlPath);
            result.setCode(Code.SUCCESS_CODE);
            result.setSuccess(true);
            result.setMsg(SUCCESS);
        } catch (Exception e) {
            logger.error(e.getMessage());

            result.setCode(Code.ILLEGAL_CODE);
            result.setSuccess(false);
            result.setMsg(ERROR);
        }

        return result;
    }

    /**
     * 获得文件后缀名
     * @param filename
     * @return
     */
    private String getSuffic(String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }

    @RequestMapping(value = "/securi_modifyPwd")
    @ResponseBody
    public Json modifyPwd(HttpServletRequest req) {
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        // 登录名
        String name = o.getString("name");
        // 密码
        String pwd = o.getString("pwd");
        if (StringUtils.isBlank(name) || StringUtils.isBlank(pwd)) {
            result.setCode(Code.NULL_CODE);
            result.setMsg("相关参数为空");
            result.setSuccess(false);
            result.setObj(null);
            return result;
        }
        Buser buser = urRes.queryUserByName(name);
        if (buser != null) {
            // 用户存在
            buser.setPwd(PasswordUtil.genPassword(pwd));
            urRes.saveAndFlush(buser);
        }else{
            //不存在
            result.setObj(null);
            result.setCode(1003);
            result.setSuccess(false);
            result.setMsg("用户不存在!");
            return result;
        }
        result.setObj(null);
        result.setCode(Code.SUCCESS_CODE);
        result.setSuccess(true);
        result.setMsg("操作成功！");
        return result;
    }
    @RequestMapping(value = "/securi_listChildrenUser")
    @ResponseBody
    public Json listChildrenUser(HttpServletRequest req){
        Json result = new Json();
        Json j = Constant.convertJson(req);
        JSONObject o = (JSONObject) j.getObj();
        Integer userId = o.getInteger("userId");
        List<BuserVO> buserVOList=service.getBUserList(userId,null,"1");
        if(CollectionUtils.isNotEmpty(buserVOList)){
            result.setObj(null);
            result.setCode(1003);
            result.setSuccess(false);
            result.setMsg("下级员工不存在!");
            return result;
        }
        else{
            result.setObj(buserVOList);
            result.setCode(Code.SUCCESS_CODE);
            result.setSuccess(true);
            result.setMsg("查询成功！");
        }
        return result;
    }
}
