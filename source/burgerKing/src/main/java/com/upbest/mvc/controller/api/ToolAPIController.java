package com.upbest.mvc.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upbest.mvc.constant.Constant.Code;
import com.upbest.mvc.service.IUploadInfoService;
import com.upbest.pageModel.Json;

/**
 * 工具箱接口
 * @author QunZheng
 *
 */
@Controller
@RequestMapping("/api/tool")
public class ToolAPIController {

    @Autowired
    private IUploadInfoService service;

    public static final String SUCCESS = "操作成功";
    public static final String ERROR = "操作失败";

    private static final Logger logger = LoggerFactory.getLogger(ToolAPIController.class);

    /**
     * 查询所有的上传文件信息
     * @param req
     * @return
     */
    @RequestMapping(value = "/securi_findAll")
    @ResponseBody
    public Json securi_findAll() {
        Json result = new Json();
        try {
            result.setObj(service.findAllUploadInfo());
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
}
