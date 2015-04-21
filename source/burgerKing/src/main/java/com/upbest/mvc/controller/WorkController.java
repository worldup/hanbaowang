package com.upbest.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.entity.BWorkInfo;
import com.upbest.mvc.entity.BWorkType;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.repository.factory.WorkRespository;
import com.upbest.mvc.service.IWorkService;
import com.upbest.mvc.vo.BSingIfnoVO;
import com.upbest.mvc.vo.WorkTypeVO;
import com.upbest.utils.DataType;
import com.upbest.utils.PageModel;

@Controller
@RequestMapping(value = "/work/")
public class WorkController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    IWorkService workService;
    @Inject
    protected WorkRespository workRespository;

    @RequestMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("current", "work");
        return "/work/workList";
    }

    @RequestMapping(value = "/create")
    public String createForm(String id, Model model) {
        if (StringUtils.isNotBlank(id)) {
            BWorkType work = workService.findById(Integer.parseInt(id));
            model.addAttribute("work", work);
        }
        return "/work/addWork";
    }

    @ResponseBody
    @RequestMapping("/add")
    public void list(@RequestParam(value = "jsons", required = false) String vo, Model model, HttpSession session, HttpServletResponse response) {
        Gson gson=new Gson();
        boolean result=true;
        try{
            BWorkType worktype = new BWorkType();
            JSONObject jso = JSONObject.parseObject(vo);
            worktype = (BWorkType) JSONObject.toJavaObject(jso, BWorkType.class);
            Integer maxSortNum = workService.getMaxSortNum();
            if (worktype.getId() == null) {
                // 添加
                if (maxSortNum.intValue() != 0) {
                    worktype.setSortNum(workService.getMaxSortNum() + 1);
                } else {
                    worktype.setSortNum(0);
                }
            }
            workService.saveBWorkType(worktype);

        }catch (Exception e){
            e.printStackTrace();
            result=false;
        }


        outPrint(gson.toJson(result),response);
    }

    @ResponseBody
    @RequestMapping("/list")
    public void list(@RequestParam(value = "typeName", required = false) String name, @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "rows", required = false) Integer pageSize, Model model, HttpSession session, HttpServletResponse response) {
        if (pageSize == null) {
            pageSize = 10;
        }
        PageRequest requestPage = new PageRequest(page != null ? page.intValue() - 1 : 0, pageSize, new Sort(Direction.ASC, new String[] { "sort_num" }));
        Page<Object[]> work = workService.findWorkList(name, requestPage);
        PageModel result = new PageModel();
        result.setPage(page);
        result.setRows(getWorkInfo(work.getContent()));
        result.setPageSize(pageSize);
        result.setRecords(NumberUtils.toInt(ObjectUtils.toString(work.getTotalElements())));
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(result, "yyyy-mm-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json, response);
    }

    private List<WorkTypeVO> getWorkInfo(List<Object[]> list) {
        List<WorkTypeVO> result = new ArrayList<WorkTypeVO>();
        if (!CollectionUtils.isEmpty(list)) {
            WorkTypeVO entity = null;
            for (Object[] obj : list) {
                entity = new WorkTypeVO();
                entity.setId(DataType.getAsString(obj[0]));
                entity.setTypename(DataType.getAsString(obj[1]));
                entity.setTyperole(DataType.getAsString(obj[2]));
                entity.setFrequency(DataType.getAsString(obj[3]));
                entity.setIsToStore(DataType.getAsInt(obj[4]));
                entity.setIsExamType(DataType.getAsInt(obj[5]));
                entity.setExamType(DataType.getAsInt(obj[6]));
                result.add(entity);
            }
        }
        return result;
    }

    private void outPrint(String json, HttpServletResponse response) {
        try {
            PrintWriter writer = response.getWriter();
            writer.write(json);
            writer.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/del")
    public void del(@RequestParam(value = "id", required = false) String id, Model model, HttpServletResponse response, HttpServletRequest request) {
        workService.deleteById(Integer.parseInt(id));
        outPrint("0", response);
    }

    @RequestMapping(value = "get")
    public String get(String id, Model model, HttpSession session) {
        BWorkType work = workService.findById(Integer.parseInt(id));
        model.addAttribute("work", work);
        return "/work/getWork";
    }

    /**
     * 
     * @Title 		   	函数名称：	changeLocation
     * @Description   	功能描述：	位置调整
     * @param 		   	参          数：	
     * @return          返  回   值：	void  
     * @throws
     */
    @ResponseBody
    @RequestMapping("/changeLocation")
    public void changeLocation(@RequestParam(value = "preId", required = false) String preId, @RequestParam(value = "currentId", required = false) String currentId,HttpServletResponse response) {
        List<BWorkType> list=new ArrayList<BWorkType>();
         if(StringUtils.isNotBlank(preId)&&StringUtils.isNotBlank(currentId)){
             BWorkType preEntity = workService.findById(Integer.parseInt(preId));
             Integer preSortNum=preEntity.getSortNum();
             BWorkType currentEntity = workService.findById(Integer.parseInt(currentId));
             Integer currentSortNum=currentEntity.getSortNum();
             preEntity.setSortNum(currentSortNum);
             list.add(preEntity);
             currentEntity.setSortNum(preSortNum);
             list.add(currentEntity);
         }
         workService.saveBWorkType(list);
         outPrint("0", response);
    }
    @ResponseBody
    @RequestMapping("/sendMail")
    public void sendMail(String userId,String month){
        workService.sendWorkPlanMailByUserId(userId,month);
    }
    @ResponseBody
    @RequestMapping("/workplan2Excel")
    public String genWorkplan2Excel(String omUserId,String ocUserId,String month){
      return   workService.getWorkPlan4Excel(omUserId,ocUserId,month);
    }
}
