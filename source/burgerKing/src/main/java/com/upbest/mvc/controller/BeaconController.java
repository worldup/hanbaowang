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
import javax.xml.crypto.Data;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.HttpResponse;
import org.hibernate.metamodel.relational.Datatype;
import org.hibernate.type.DateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.upbest.mvc.entity.BBeacon;
import com.upbest.mvc.entity.BBeaconShop;
import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.service.BeaconShopService;
import com.upbest.mvc.service.IBeaconService;
import com.upbest.mvc.service.IBuserService;
import com.upbest.mvc.service.IStoreService;
import com.upbest.mvc.vo.BShopInfoVO;
import com.upbest.mvc.vo.BeaconVO;
import com.upbest.mvc.vo.SelectionVO;
import com.upbest.pageModel.Json;
import com.upbest.utils.CommonUtils;
import com.upbest.utils.DataType;
import com.upbest.utils.DownloadFileUtils;
import com.upbest.utils.PageModel;


@Controller
@RequestMapping(value = "/beacon")
public class BeaconController {

    @Inject
    private IBeaconService service;
    
    
    private static final String TEMPLATE_FILE_PATH = "excelTemp/beacon.xlsx";

    @Autowired
    IBeaconService beaconService;
    
    @Autowired
    BeaconShopService beaconShopService;
    
    @Inject
    private IBuserService userService;
    
    @Inject
    private IStoreService IStoreService;
    
    @RequestMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("current","beacon");
        return "/beacon/beaconList";
    }
    
    @RequestMapping("/import")
    public String importExcel() {
        return "beacon/uploadBeacon";
    }
    
    @RequestMapping("/toImport")
    @ResponseBody
    public void toImport(@RequestParam("beaconFile") MultipartFile file,
            String filename,HttpServletResponse response) throws Exception {
        Json json = new Json();
        try {
            service.addBeaconFromExcel(new ByteArrayResource(file.getBytes()));
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
    public void getIcon(
            @RequestParam(value = "filename", required = false) String filename,
            HttpServletResponse response, HttpServletRequest request)
            throws IOException {
        String rootPath = request.getSession().getServletContext()
                .getRealPath("/");
        String filePath = rootPath + TEMPLATE_FILE_PATH;
        DownloadFileUtils.download(new File(filePath), filename, response);

    }
    
    
    @RequestMapping("/list")
    @ResponseBody
    public void queryBeaconList(
            @RequestParam("beaconName") String beaconName,
            @RequestParam("page") Integer page, @RequestParam("rows") Integer pageSize,
            HttpServletResponse response) {
        if (pageSize == null) {
            pageSize = 10;
        }
        PageRequest requestPage = new PageRequest(page != null ? page.intValue() - 1 : 0, pageSize);
        Page<Object[]> beaconList = beaconService.queryBeacon(beaconName, requestPage);
        PageModel result=new PageModel();
        result.setPage(page);
        result.setRows(getShopInfo(beaconList.getContent()));
        result.setPageSize(pageSize);
        result.setRecords(NumberUtils.toInt(ObjectUtils.toString(beaconList.getTotalElements())));
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json,response);
    }
    
    @RequestMapping("/addOrUpdatePage")
    public String addOrUpdatePage(
            @RequestParam(value = "id", required = false) Integer id,
            Model model) {
        if (id != null) {
            List<Object[]> beacon = beaconService.queryBeaconById(id);
            model.addAttribute("beacon", getShopInfo(beacon).get(0));
        }
        model.addAttribute("shopNames", getShopNamesInfo());

        return "beacon/addOrUpdateBeacon";
    }
    
    @RequestMapping("/add")
    @ResponseBody
    public void addFacility(BBeacon beacon, @RequestParam(value = "shopid", required = false) Integer shopid) {
        beacon.setLastDate(new Date());
        beaconService.saveBeacon(beacon);
        BBeacon beaconfi = beaconService.findByUuid(beacon.getUuid());
        
        BBeaconShop beaconShop = new BBeaconShop();
       
        beaconShop.setBeaconId(beaconfi.getId());
        beaconShop.setShopId(shopid);
        beaconShopService.saveBeaconShop(beaconShop);
    }
    
    @RequestMapping("/update")
    @ResponseBody
    public void updateFacility(BBeacon beacon, @RequestParam(value = "shopid", required = false) Integer shopid,
            @RequestParam(value = "beaconShopId", required = false) Integer beaconShopId) {
        beacon.setLastDate(new Date());
        beaconService.saveBeacon(beacon);
        BBeaconShop beaconShop = new BBeaconShop();
        beaconShop.setId(beaconShopId);
        beaconShop.setBeaconId(beacon.getId());
        beaconShop.setShopId(shopid);
        beaconShopService.saveBeaconShop(beaconShop);
    }
    
    @RequestMapping("/delete")
    @ResponseBody
    public void deleteFacility(@RequestParam("id") int beaconId, HttpServletResponse response) {
        beaconService.deleteBeacon(beaconId);
        List<Object[]> beaconShopList = beaconShopService.findByBeaconid(beaconId);
        BBeaconShop beaconShop = getbeaconShopInfo(beaconShopList).get(0);
        beaconShopService.deleteById(beaconShop.getId());
        outPrint("0", response);
    }
    
    private List<BBeaconShop> getbeaconShopInfo(List<Object[]> list){
        List<BBeaconShop> result=new ArrayList<BBeaconShop>();
        if(!CollectionUtils.isEmpty(list)){
            BBeaconShop entity=null;
            for(Object[] obj:list){
                entity=new BBeaconShop();
                entity.setId(DataType.getAsInt(obj[0]));
                entity.setBeaconId(DataType.getAsInt(obj[1]));
                entity.setShopId(DataType.getAsInt(obj[2]));
                result.add(entity);
            }
        }
        return result;
    }
    
    private List<BeaconVO> getShopInfo(List<Object[]> list){
        List<BeaconVO> result=new ArrayList<BeaconVO>();
        if(!CollectionUtils.isEmpty(list)){
            BeaconVO entity=null;
            for(Object[] obj:list){
                entity=new BeaconVO();
                entity.setId(DataType.getAsInt(obj[0]));
                entity.setUuid(DataType.getAsString(obj[1]));
                entity.setMajorId(DataType.getAsString(obj[2]));
                entity.setMinorId(DataType.getAsString(obj[3]));
                entity.setShopName(DataType.getAsString(obj[4]));
                entity.setShopId(DataType.getAsInt(obj[5]));
                entity.setBeaconShopId(DataType.getAsInt(obj[6]));
                entity.setName(DataType.getAsString(obj[7]));
                entity.setMac(DataType.getAsString(obj[8]));
                entity.setMeasurrdPower(DataType.getAsString(obj[9]));
                entity.setSignalStrength(DataType.getAsString(obj[10]));
                entity.setRemainingPower(DataType.getAsString(obj[11]));
                entity.setLastDate(DataType.getAsDate(obj[12]));
                entity.setCompany(DataType.getAsString(obj[13]));
                entity.setModel(DataType.getAsString(obj[14]));
                entity.setMold(DataType.getAsString(obj[15]));
                entity.setDistance(DataType.getAsString(obj[16]));
                entity.setRemarks(DataType.getAsString(obj[17]));
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
    private List<SelectionVO> getShopNamesInfo() {
        List<SelectionVO> usersInfo = new ArrayList<SelectionVO>();

        List<BShopInfo> shopNames = IStoreService.findAllShop();
        for (BShopInfo shop : shopNames) {
            usersInfo.add(new SelectionVO(shop.getShopname(), String.valueOf(shop
                    .getId())));
        }
        return usersInfo;
    }
    
    @RequestMapping("/get")
    public String get(
            @RequestParam(value = "id", required = false) Integer id,
            Model model) {
        if (id != null) {
            List<Object[]> beacon = beaconService.queryBeaconById(id);
            model.addAttribute("beacon", getShopInfo(beacon).get(0));
        }
        model.addAttribute("shopNames", getShopNamesInfo());

        return "beacon/getBeacon";
    }
}
