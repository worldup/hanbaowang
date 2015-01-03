package com.upbest.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.upbest.mvc.service.IBSingIfnoService;
import com.upbest.mvc.vo.BSingIfnoVO;
import com.upbest.utils.DataType;
import com.upbest.utils.PageModel;

@Controller
@RequestMapping(value = "/sing")
public class SingController {
   private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    protected IBSingIfnoService singSeriver;
    
    @RequestMapping(value = "/index")
    public String index(Model model){
        model.addAttribute("current", "sing");
        return "/sing/singList";
    }
    
    @ResponseBody
    @RequestMapping("/list")
    public void list(@RequestParam(value = "singName", required = false) String name,
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
        if("signintime".equals(sidx)){
            sidx="s.sign_in_time";
        }
        if("signouttime".equals(sidx)){
            sidx="s.sign_out_time";
        }
        Order or=null;
        List<Order> orders=new ArrayList<Order>();
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
        Page<Object[]> sing =singSeriver.findSingList(name, requestPage);
        PageModel result=new PageModel();
        result.setPage(page);
        result.setRows(getSingInfo(sing.getContent()));
        result.setPageSize(pageSize);
        result.setRecords(NumberUtils.toInt(ObjectUtils.toString(sing.getTotalElements())));
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

    private List<BSingIfnoVO> getSingInfo(List<Object[]> list){
        List<BSingIfnoVO> result=new ArrayList<BSingIfnoVO>();
        if(!CollectionUtils.isEmpty(list)){
            BSingIfnoVO entity=null;
            for(Object[] obj:list){
                entity=new BSingIfnoVO();
                entity.setId(DataType.getAsString(obj[0]));
                entity.setSignintime(DataType.getAsString(obj[1]));
                entity.setSignouttime(DataType.getAsString(obj[2]));
                entity.setSigninlongitude(DataType.getAsString(obj[3]));
                entity.setSigninlatitude(DataType.getAsString(obj[4]));
                entity.setName(DataType.getAsString(obj[5]));
                entity.setLocation(DataType.getAsString(obj[6]));
                result.add(entity);
            }
        }
        return result;
    }

}
