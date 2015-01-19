package com.upbest.mvc.controller.api;

import com.upbest.mvc.constant.Constant.Code;
import com.upbest.mvc.entity.BUpdate;
import com.upbest.mvc.service.IUpdateService;
import com.upbest.pageModel.Json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 创  建   者：	<A HREF="bxu@upbest-china.com">jhzhap</A>
 * @ClassName 类  名   称：	ApkInfoAPIController.java
 * @Description 功能描述：	发布更新接口
 * @date 创建日期：	2014年10月9日下午1:55:06
 */
@Controller
@RequestMapping("/api/update")
public class ApkInfoAPIController {

  private static final Logger logger = LoggerFactory.getLogger(ApkInfoAPIController.class);

  @Inject
  private IUpdateService updateService;

  @ResponseBody
  @RequestMapping("/securi_apkInfo")
  public Json apkInfo(HttpServletRequest req) {
    Json result = new Json();
//        Json j = Constant.convertJson(req);
//        JSONObject o = (JSONObject) j.getObj();
//        result.setCode(Code.NULL_CODE);
    try {
      BUpdate apkInfo = updateService.findLatest();
      if (apkInfo == null) {
        logger.error("最新版本的应用不存在！");
        result.setCode(Code.NULL_CODE);
        result.setSuccess(false);
        result.setMsg("数据不存在");
        result.setObj(null);
        return result;
      }
      Map<String, Object> resultMap = new HashMap<String, Object>();
//            String path = "http://"+ConfigUtil.get("SERVERIP")+":"+ConfigUtil.get("SERVERPORT")+""+ConfigUtil.get("PROJECT")+"/";
      //  String path = ConfigUtil.get("filePath");
      URL url = new URL(req.getRequestURL().toString());
      String urlPath = "";
      urlPath = "/upload/apk-version/" + apkInfo.getUrl();
      resultMap.put("version", apkInfo.getVersionNum());
      resultMap.put("apkPath", urlPath);
      result.setObj(resultMap);
      result.setCode(Code.SUCCESS_CODE);
      result.setSuccess(true);
      result.setMsg("获取数据成功！");
    } catch (Exception e) {
      logger.error("获取最新版本的应用出错！");
      result.setCode(Code.NULL_CODE);
      result.setMsg("获取失败!");
      result.setSuccess(false);
      result.setObj(null);
      return result;
    }
    return result;
  }
}
