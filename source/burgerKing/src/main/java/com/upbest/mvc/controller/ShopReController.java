package com.upbest.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
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

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.upbest.mvc.service.ShopReService;
import com.upbest.mvc.vo.ShopReVO;
import com.upbest.mvc.vo.WorkTypeVO;
import com.upbest.utils.DataType;
import com.upbest.utils.PageModel;


@Controller
@RequestMapping(value = "/shopRe")
public class ShopReController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    protected ShopReService service;
    
    @RequestMapping(value = "/index")
    public String index(Model model,@RequestParam(value = "shopId") Integer shopId){
        model.addAttribute("current", "shopRe");
        model.addAttribute("id", shopId);
        return "/shopRe/shopReList";
    }
    
    @ResponseBody
    @RequestMapping("/list")
    public void list(@RequestParam(value = "reName", required = false) String name,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "shopId", required = false) Integer shopId,
            @RequestParam(value = "rows", 
            required = false) Integer pageSize, 
            Model model,
            HttpSession session,
            HttpServletResponse response) {
        if (pageSize == null) {
            pageSize = 10;
        }
        PageRequest requestPage = new PageRequest(page != null ? page.intValue() - 1 : 0, pageSize);
        Page<Object[]> shopRe =service.findReList(name,shopId, requestPage);
        PageModel result=new PageModel();
        result.setPage(page);
        result.setRows(getShopRe(shopRe.getContent()));
        result.setPageSize(pageSize);
        result.setRecords(NumberUtils.toInt(ObjectUtils.toString(shopRe.getTotalElements())));
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json,response);
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
    
    private List<ShopReVO> getShopRe(List<Object[]> list){
        List<ShopReVO> result=new ArrayList<ShopReVO>();
        if(!CollectionUtils.isEmpty(list)){
            ShopReVO entity=null;
            for(Object[] obj:list){
                entity=new ShopReVO();
                entity.setId(DataType.getAsString(obj[0]));
                entity.setShopName(DataType.getAsString(obj[1]));
                entity.setReport(DataType.getAsString(obj[2]));
                entity.setName(DataType.getAsString(obj[3]));
                entity.setCreateTime(DataType.getAsDate(obj[4]));
                entity.setMon(DataType.getAsDate(obj[5]));
                entity.setReportType(DataType.getAsString(obj[6]));
                result.add(entity);
            }
        }
        return result;
    }
    
    @ResponseBody
    @RequestMapping("/del")
    public void del(@RequestParam(value = "id", required = false) String id, Model model, HttpServletResponse response, HttpServletRequest request) {
        service.deleteById(Integer.parseInt(id));
        outPrint("0", response);
    }

}
