package com.upbest.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.upbest.mvc.entity.BExBaseInfo;
import com.upbest.mvc.entity.BExHeadingRela;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.repository.factory.BExBaseInfoRespository;
import com.upbest.mvc.service.IBExBaseInfoService;
import com.upbest.mvc.service.IBuserService;
import com.upbest.mvc.service.IExamService;
import com.upbest.mvc.vo.BExBaseInfoVO;
import com.upbest.mvc.vo.TreeVO;
import com.upbest.utils.DataType;
import com.upbest.utils.PageModel;

@Controller
@RequestMapping(value = "/base/")
public class BExBaseInfoController {
    @Inject
    private IBExBaseInfoService baseService;
    @Autowired
    private IExamService examService;
    @Inject
    protected BExBaseInfoRespository baseRespository;

    private static final Logger logger = LoggerFactory.getLogger(BExBaseInfoController.class);

    @RequestMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("current", "base");
        return "/base/baseList";
    }

    @RequestMapping("/list")
    @ResponseBody
    public void queryBeaconList(@RequestParam("baseName") String baseName, @RequestParam("page") Integer page, @RequestParam("rows") Integer pageSize, HttpServletResponse response) {
        if (pageSize == null) {
            pageSize = 10;
        }
        PageRequest requestPage = new PageRequest(page != null ? page.intValue() - 1 : 0, pageSize);
        Page<Object[]> beaconList = baseService.findBaseList(baseName, requestPage);
        PageModel result = new PageModel();
        result.setPage(page);
        result.setRows(getShopInfo(beaconList.getContent()));
        result.setPageSize(pageSize);
        result.setRecords(NumberUtils.toInt(ObjectUtils.toString(beaconList.getTotalElements())));
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json, response);
    }

    private List<BExBaseInfoVO> getShopInfo(List<Object[]> list) {
        List<BExBaseInfoVO> result = new ArrayList<BExBaseInfoVO>();
        if (!CollectionUtils.isEmpty(list)) {
            BExBaseInfoVO entity = null;
            for (Object[] obj : list) {
                entity = new BExBaseInfoVO();
                entity.setPvalue(DataType.getAsString(obj[0]));
                entity.setId(DataType.getAsInt(obj[1]));
                entity.setBpid(DataType.getAsString(obj[2]));
                entity.setBvalue(DataType.getAsString(obj[3]));
                entity.setBrushElection(DataType.getAsString(obj[4]));
                entity.setBcomments(DataType.getAsString(obj[5]));
                entity.setIsKey(DataType.getAsInt(obj[6]));
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

    @Autowired
    IBuserService userService;

    @RequestMapping(value = "get")
    public String get(String id, Model model, HttpSession session) {
        BExBaseInfoVO base = baseService.findById(Integer.parseInt(id));
        model.addAttribute("base", base);
        return "/base/getBase";
    }

    @RequestMapping(value = "/create")
    public String createForm(String id, Model model) throws Exception {
        if (StringUtils.isNotBlank(id)) {
            BExBaseInfoVO base = baseService.findById(Integer.parseInt(id));
            List<Integer> modelType = examService.findModelType(Integer.valueOf(id));
            model.addAttribute("base", base);
            model.addAttribute("modelType", toJson(modelType));
        }
        model.addAttribute("modelTypes", examService.findHeadInfo(2));
        return "/base/addBase";
    }

    @ResponseBody
    @RequestMapping("/getUserTreeList")
    public void getUserTreeList(@RequestParam(value = "pid", required = false) String role, Model model, HttpSession session, HttpServletResponse response) {
        String jsonString = new String();
        List<TreeVO> list = baseService.getUrVOList();
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        if (!CollectionUtils.isEmpty(list)) {
            for (TreeVO tree : list) {
                sb.append("{");
                sb.append("\"id\":\"" + tree.getId() + "\"");
                sb.append(",\"name\":\"" + tree.getName() + "\"");
                sb.append(",\"pId\":\"" + tree.getPid() + "\",checked:false");
                sb.append(",open:true");
                sb.append("},\n");
            }
            StringBuffer sb2 = new StringBuffer();
            sb2.append(sb.toString().substring(0, sb.toString().length() - 2));
            sb2.append("\n]");
            jsonString = sb2.toString();
        }
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(jsonString, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json, response);
    }
    @ResponseBody
    @RequestMapping("/getExamUserTreeList")
    public void getExamUserTreeList(@RequestParam(value = "pid", required = false) String role, Model model, HttpSession session, HttpServletResponse response) {
        List<TreeVO> list = baseService.getUrVOList();
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json, response);
    }

    @ResponseBody
    @RequestMapping("/addStore")
    public void addStore(HttpServletRequest req, @RequestParam(value = "jsons", required = false) String vo, @RequestParam(value = "ids", required = false) String ids, Model model,
            HttpSession session, HttpServletResponse response) {
    }

    @ResponseBody
    @RequestMapping("/getPaperSignTree")
    public void getPaperSignTree(Model model, HttpSession session, HttpServletResponse response) {
        String jsonString = new String();
        List<TreeVO> list = userService.getUrVOListMessage(((Buser) session.getAttribute("buser")).getId());
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        if (!CollectionUtils.isEmpty(list)) {
            for (TreeVO tree : list) {
                sb.append("{");
                sb.append("\"id\":\"" + tree.getId() + "\"");
                sb.append(",\"name\":\"" + tree.getName() + "\"");
                sb.append(",\"pId\":\"" + tree.getPid() + "\",checked:false");
                sb.append(",open:true");
                sb.append("},\n");
            }
            StringBuffer sb2 = new StringBuffer();
            sb2.append(sb.toString().substring(0, sb.toString().length() - 2));
            sb2.append("\n]");
            jsonString = sb2.toString();
        }
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(jsonString, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json, response);
    }

    @ResponseBody
    @RequestMapping("/paperAssign")
    public void pushMessage(@RequestParam(value = "examId", required = false) Integer examId, @RequestParam(value = "ids", required = false) String ids, Model model, HttpSession session,
            HttpServletResponse response) {
        examService.saveExamAssign(examId, Arrays.asList(ids.split(",")));
    }

    @RequestMapping(value = "/toPaperAssign")
    public String toPaperAssign(String examId, Model model) {
        model.addAttribute("examId", examId);
        return "/exam/paper/paperAssign";
    }

    /**
     * 
     * @Title 		   	函数名称：	addBase
     * @Description   	功能描述：	添加类型
     * @param 		   	参          数：
     * @return          返  回   值：	void
     * @throws
     */
    @ResponseBody
    @RequestMapping("/addBase")
    public void addBase(HttpServletRequest req, @RequestParam(value = "bvalue", required = false) String bvalue, @RequestParam(value = "ids", required = false) String ids,
            @RequestParam(value = "brushElection", required = false) String brushElection,
            @RequestParam(value = "isKey", required = false) int isKey,
            @RequestParam(value = "bcomments", required = false) String bcomments, @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "modelType", required = false) String modelType, Model model, HttpSession session, HttpServletResponse response) {
        BExBaseInfo entity = null;

        if (StringUtils.isNotBlank(id)) {
            // 修改
            entity = new BExBaseInfo();
            entity.setId(DataType.getAsInt(id));
            entity.setBpid(ids);
            entity.setBvalue(bvalue);
            entity.setBrushElection(brushElection);
            entity.setBcomments(bcomments);
            entity.setIsKey(isKey);
            baseService.saveBase(entity);
            examService.deleteExHeadRelByModulerId(Integer.valueOf(id));
        } else {
            entity = baseService.saveBaseInfo(ids, bvalue,bcomments,brushElection,isKey);
        }

        if (!StringUtils.isEmpty(modelType)) {
            String[] types = modelType.split(",");
            if (!ArrayUtils.isEmpty(types)) {
                Integer moduleId = entity.getId();
                List<BExHeadingRela> list = new ArrayList<BExHeadingRela>();
                for (String type : types) {
                    BExHeadingRela rela = new BExHeadingRela();
                    rela.setModuleId(moduleId);
                    rela.sethId(Integer.valueOf(type));
                    list.add(rela);
                }
                examService.saveExHeadRel(list);
            }
            if (!StringUtils.isEmpty(modelType)) {
            }
        }
    }

    @ResponseBody
    @RequestMapping("/del")
    public void del(@RequestParam(value = "id", required = false) String id, Model model, HttpServletResponse response, HttpServletRequest request) {
        baseService.deleteBaseInfo(Integer.parseInt(id));
        outPrint("0", response);
    }

    private String toJson(List<Integer> modelType) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(modelType);
    }
}
