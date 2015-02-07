package com.upbest.mvc.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.upbest.mvc.constant.*;
import com.upbest.mvc.service.*;
import com.upbest.mvc.vo.*;
import com.upbest.utils.*;
import com.upbest.utils.Constant;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.codehaus.jackson.map.ObjectMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.upbest.mvc.constant.Constant.BrandExtension;
import com.upbest.mvc.constant.Constant.ShopState;
import com.upbest.mvc.entity.BArea;
import com.upbest.mvc.entity.BShopInfo;
import com.upbest.mvc.entity.BShopReport;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.repository.factory.BArearespository;
import com.upbest.pageModel.Json;

/**
 * 
 * @ClassName 类 名 称： StoreController.java
 * @Description 功能描述： 门店信息维护
 * @author 创 建 者： <A HREF="jhzhao@upbest-china.com">jhzhap</A>
 * @date 创建日期： 2014年9月16日下午12:06:44
 */
@Controller
@RequestMapping(value = "/store")
public class StoreController {
    private static final Logger logger = LoggerFactory.getLogger(StoreController.class);
    @Autowired
    IStoreService storeService;

    @Autowired
    IStoreUserService storeUserService;

    @Autowired
    IStoreReportService storeReportService;

    @Autowired
    IBuserService userService;

    @Autowired
    IBeaconService beaconService;

    @Autowired
    BeaconShopService beaconShopService;

    @Autowired
    BArearespository areaRes;

    @Autowired
    IAreaService areaService;

    @Autowired
    protected IBSingIfnoService singSeriver;

    private static final String TEMPLATE_FILE_PATH = "excelTemp/shopinfo.xlsx";

    @RequestMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("current", "store");
        return "/store/storeList";
    }

    /**
     * 
     * @Title 		   	函数名称：	mapIndex
     * @Description   	功能描述：	门店信息地图展示
     * @param 		   	参          数：	
     * @return          返  回   值：	String  
     * @throws
     */
    @RequestMapping(value = "/mapIndex")
    public String mapIndex(Model model, HttpSession session) {
        model.addAttribute("current", "storeMap");
        return "/store/storeMap";
    }

    @RequestMapping(value = "/getLngLat")
    public String getLngLat(Model model, HttpSession session) {
        model.addAttribute("current", "getLngLat");
        return "/store/getLngLat";
    }

    @ResponseBody
    @RequestMapping("/mapLoad")
    public void mapLoad(Model model, HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        Buser buser = (Buser) session.getAttribute("buser");
        List<BShopInfoVO> storeList = storeService.getShopInfoVOList(buser.getId(), "");
        // 查询某个用户下对应的所有门店
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(doFormatToMapList(storeList, buser), "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json, response);
    }

    @ResponseBody
    @RequestMapping("/getAllStoreInfo")
    public void getAllStoreInfo(Model model, HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        List<BShopInfo> storeList = storeService.findAllShop();
        // 查询某个用户下对应的所有门店
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(storeList, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json, response);
    }

    /**
     * 
     * @Title 		   	函数名称：	saveLngLats
     * @Description   	功能描述：	保存门店经纬度
     * @param 		   	参          数：	
     * @return          返  回   值：	void  
     * @throws
     */
    @ResponseBody
    @RequestMapping("/saveLngLats")
    public void saveLngLats(@RequestParam(value = "lngLats", required = false) String lngLats,@RequestParam(value = "flag", required = false) String flag,HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        if (StringUtils.isNotBlank(lngLats)) {
            String[] lngLatAry = lngLats.split(";");
            for (int i = 0; i < lngLatAry.length; i++) {
                String[] lngLatItems = lngLatAry[i].split(",");
                // 门店编号
                String shopNum = lngLatItems[0];
                // 经度
                String lng = lngLatItems[1];
                // 纬度
                String lat = lngLatItems[2];
                if(StringUtils.isNotBlank(shopNum)){
                    BShopInfo entity=storeService.findByShopNum(shopNum);
                    if(null!=entity){
                        entity.setLongitude(lng);
                        entity.setLatitude(lat);
                        storeService.saveBShop(entity);
                    }
                }
            }
        }

    }

    /**
     * 
     * @Title 		   	函数名称：	doFormatToMapList
     * @Description   	功能描述：	生成mapList
     * @param 		   	参          数：	
     * @return          返  回   值：	List<MapVO>  
     * @throws
     */
    public List<MapVO> doFormatToMapList(List<BShopInfoVO> storeList, Buser buser) {
        List<MapVO> result = new ArrayList<MapVO>();
        List<BSignInfoVO> signInfoList = singSeriver.getSignInfo(buser.getId(), "");
        List<BshopStatisticVO> shopStss = storeService.queryStatisticInfo(buser.getId());
        if (!CollectionUtils.isEmpty(storeList)) {
            MapVO mapVO = null;
            for (BShopInfoVO vo : storeList) {
                mapVO = new MapVO();
                    NoNullBeanCopy.copyProperties(vo, mapVO);
                List<BSignInfoVO> latestSignInfo = getLatestSignInfo(vo.getId(), signInfoList, vo.getUserIds());
                mapVO.setLatestSignInfo(latestSignInfo);
                mapVO.setLatestSts(getLatestSts(vo.getShopnum(), shopStss));
                result.add(mapVO);
            }
        }
        return result;
    }

    /**
     * 
     * @Title 		   	函数名称：	getLatestSts
     * @Description   	功能描述：	获取某个门店最近一次运营数据
     * @param 		   	参          数：	
     * @return          返  回   值：	BshopStatisticVO  
     * @throws
     */
    public BshopStatisticVO getLatestSts(String shopNum, List<BshopStatisticVO> shopStss) {
        if (!CollectionUtils.isEmpty(shopStss)) {
            for (BshopStatisticVO vo : shopStss) {
                if (vo.getShopNum().equals(shopNum)) {
                    DecimalFormat df = new DecimalFormat("#.0");
                    double d = DataType.getAsDouble(vo.getGtNps()) * 100;
                    String st = df.format(d);
                    vo.setGtNps(st);
                    return vo;
                }
            }
        }
        return new BshopStatisticVO();
    }

    /**
     * 
     * @Title 		   	函数名称：	getLatestSignInfo
     * @Description   	功能描述：	对应某个用户最近一次签到信息
     * @param 		   	参          数：	
     * @return          返  回   值：	BSignInfoVO  
     * @throws
     */
    public List<BSignInfoVO> getLatestSignInfo(Integer shopId, List<BSignInfoVO> signInfoList, String userId) {
        int count = 0;
        List<BSignInfoVO> result = new ArrayList<BSignInfoVO>();
        if (!CollectionUtils.isEmpty(signInfoList)) {
            for (BSignInfoVO vo : signInfoList) {
                if (vo.getShopId().intValue() == shopId.intValue() && vo.getUserid().intValue() == Integer.valueOf(DataType.getAsInt(userId))) {
                    // 用户在某门店下签到
                    result.add(vo);
                    count++;
                    // 取最近前三次签到信息
                    if (count == 3) {
                        break;
                    }
                }
            }

        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/detail")
    public void detail(@RequestParam(value = "id", required = false) String id, Model model, HttpServletResponse response, HttpServletRequest request) {
        BShopInfoVO shopVo = storeService.findById(Integer.parseInt(id));
        List<String> urList = storeService.getUserIds(id);
        if (!CollectionUtils.isEmpty(urList)) {
            shopVo.setUserIds(urList.get(0));
            shopVo.setUserNames(urList.get(1));
        }
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(shopVo, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json, response);
    }

    @ResponseBody
    @RequestMapping("/del")
    public void del(@RequestParam(value = "id", required = false) String id, Model model, HttpServletResponse response, HttpServletRequest request) {
        storeService.deleteById(Integer.parseInt(id));
        outPrint("0", response);
    }
@Autowired
private ISpringJdbcService jdbcService;
    @ResponseBody
    @RequestMapping("/list")
    public void list(@RequestParam(value = "shopName", required = false) String name,
                     @RequestParam(value = "OMID", required = false) Integer OMID,
                     @RequestParam(value = "OCID", required = false) Integer OCID,
                     @RequestParam(value = "regional", required = false) String regional,
                     @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "rows", required = false) Integer pageSize, @RequestParam(value = "sidx", required = false) String sidx, @RequestParam(value = "sord", required = false) String sord,
            @RequestParam(value = "realName", required = false) String realName, Model model, HttpSession session,
                     HttpServletRequest request,
                     HttpServletResponse response) {
        Buser user = (Buser) request.getSession().getAttribute("buser");
        if (pageSize == null) {
            pageSize = 10;
        }

        Order or = null;
        if (sord.equalsIgnoreCase("desc")) {
            or = new Order(Direction.DESC, sidx);
        } else {
            or = new Order(Direction.ASC, sidx);
        }
        Sort so = new Sort(or);
        // 排序 end
        PageRequest requestPage = new PageRequest(page != null ? page.intValue() - 1 : 0, pageSize, so);

        String queryUserRole="";
        Integer queryUserId=0;
        if(OCID!=null){
            queryUserId=OCID;
            queryUserRole="2";
        }
        else if(OMID!=null){
            queryUserId=OMID;
            queryUserRole="1";
        }
        else{
            Buser buser = (Buser) session.getAttribute("buser");
            queryUserRole=buser.getRole();
            queryUserId=buser.getId();
        }
        Page<Object[]> shop = storeService.findShopList(name, queryUserId,queryUserRole, regional, requestPage);

        PageModel result = new PageModel();
        result.setPage(page);
        result.setRows(getShopInfo(shop.getContent()));
        result.setPageSize(pageSize);
        result.setRecords(NumberUtils.toInt(ObjectUtils.toString(shop.getTotalElements())));
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd", SerializerFeature.WriteMapNullValue);
        outPrint(json, response);
    }

    private List<BShopInfoVO> getShopInfo(List<Object[]> list) {
        List<BShopInfoVO> result = new ArrayList<BShopInfoVO>();
        if (!CollectionUtils.isEmpty(list)) {
            BShopInfoVO entity = null;
            for (Object[] obj : list) {
                entity = new BShopInfoVO();
                entity.setId(DataType.getAsInt(obj[0]));
                entity.setShopaddress(DataType.getAsString(obj[1]));
                entity.setShopbusinessarea(DataType.getAsString(obj[2]));
                entity.setShopbusinesstime(DataType.getAsString(obj[3]));
                entity.setShopimage(DataType.getAsString(obj[4]));
                entity.setShopname(DataType.getAsString(obj[5]));
                entity.setShopnum(DataType.getAsString(obj[6]));
                entity.setShopopentime(DataType.getAsDate(obj[7]));
                entity.setShopphone(DataType.getAsString(obj[8]));
                entity.setShopseatnum(DataType.getAsInt(obj[9]));
                entity.setShopsize(DataType.getAsString(obj[10]));
                entity.setCreateTime(DataType.getAsDate(obj[11]));
                entity.setRegional(DataType.getAsString(obj[12]));
                entity.setProvince(DataType.getAsString(obj[13]));
                entity.setPrefecture(DataType.getAsString(obj[14]));
                entity.setCityGrade(DataType.getAsString(obj[15]));
                entity.setDistrict(DataType.getAsString(obj[16]));
                entity.setMold(DataType.getAsString(obj[17]));
                entity.setBusinessCircle(DataType.getAsString(obj[18]));
                entity.setCircleSize(DataType.getAsString(obj[19]));
                entity.setSubwayNumber(DataType.getAsString(obj[20]));
                entity.setAnExit(DataType.getAsString(obj[21]));
                entity.setMeters(DataType.getAsString(obj[22]));
                entity.setBusNumber(DataType.getAsString(obj[23]));
                entity.setCompetitor(DataType.getAsString(obj[24]));
                entity.setMainCompetitors(DataType.getAsString(obj[25]));
                entity.setRivalsName(DataType.getAsString(obj[26]));
                entity.setKfcNumber(DataType.getAsString(obj[27]));
                entity.setMnumber(DataType.getAsString(obj[28]));
                entity.setPizzaNumber(DataType.getAsString(obj[29]));
                entity.setStarbucksNumber(DataType.getAsString(obj[30]));
                entity.setUpdateTime(DataType.getAsDate(obj[31]));
                entity.setChineseName(DataType.getAsString(obj[32]));
                entity.setEnglishName(DataType.getAsString(obj[33]));
                entity.setChineseAddress(DataType.getAsString(obj[34]));
                entity.setEnglishAddress(DataType.getAsString(obj[35]));
                entity.setStraightJointJoin(DataType.getAsString(obj[36]));
                entity.setLongitude(DataType.getAsString(obj[37]));
                entity.setLatitude(DataType.getAsString(obj[38]));
                List<String> urList = storeService.getUserIds(DataType.getAsString(obj[0]));
                if (!CollectionUtils.isEmpty(urList)) {
                    entity.setUserIds(urList.get(0));
                    entity.setUserNames(urList.get(1));
                }
                result.add(entity);
            }
        }
        return result;
    }

    @RequestMapping(value = "/create")
    public String createForm(String id, Model model, HttpSession session) {
        Buser buser = (Buser) session.getAttribute("buser");
        if (StringUtils.isNotBlank(id)) {
            BShopInfoVO shop = storeService.findById(Integer.parseInt(id));
            model.addAttribute("shop", shop);
        }
            model.addAttribute("shopStatus", getShopStatus());
            model.addAttribute("brandExtesions", getBrandExtesions());
            try {
                model.addAttribute("areaInfo", toJson(areaService.findAllAreaInfo()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        if (null != buser) {
            model.addAttribute("role", buser.getRole());
        }
        return "/store/addStore";
    }

    private List<SelectionVO> getBrandExtesions() {
        List<SelectionVO> result = new ArrayList<SelectionVO>();
        BrandExtension[] brandExtesions = BrandExtension.values();
        for (BrandExtension brandExtesion : brandExtesions) {
            result.add(new SelectionVO(brandExtesion.getName(), brandExtesion.getValue() + ""));
        }
        return result;
    }

    private List<SelectionVO> getShopStatus() {
        List<SelectionVO> result = new ArrayList<SelectionVO>();
        ShopState[] states = ShopState.values();
        for (ShopState shopState : states) {
            result.add(new SelectionVO(shopState.getChName(), shopState.getEnName()));
        }
        return result;
    }

    private String getAreaId(String areaInfo) {
        /*
         * if (StringUtils.isEmpty(areaInfo)) { return ""; }
         * 
         * return areaInfo.split(":")[0];
         */
        List<BArea> area = areaRes.findByArea(areaInfo);
        if (CollectionUtils.isEmpty(area)) {
            return "";
        }
        return area.get(0).getId() + "";
    }

    @RequestMapping(value = "get")
    public String get(String id, Model model, HttpSession session) {
        BShopInfoVO shopVo = storeService.findByAreaId(Integer.parseInt(id));
        model.addAttribute("shop", shopVo);
        return "/store/getStore";
    }

    @RequestMapping(value = "upload")
    public String upload(@RequestParam("shopId") String shopId, Model model, HttpSession session) {
        model.addAttribute("id", shopId);
        return "/store/uploadStore";
    }

    @RequestMapping(value = "bind")
    public String bind(String id, Model model, HttpSession session) {
        model.addAttribute("shopid", id);
        return "/store/bindBeacon";
    }

    @ResponseBody
    @RequestMapping("/addStore")
    public void addStore(HttpServletRequest req, @RequestParam(value = "jsons", required = false) String vo, @RequestParam(value = "ids", required = false) String ids, Model model,
            HttpSession session, HttpServletResponse response, @RequestParam(value = "shopimages", required = false) String shopImages) throws MalformedURLException {
        Buser buser = (Buser) session.getAttribute("buser");
        BShopInfo shopInfo = new BShopInfo();
        JSONObject jso = JSONObject.parseObject(vo);
        shopInfo = (BShopInfo) JSONObject.toJavaObject(jso, BShopInfo.class);
        shopInfo.setCreateTime(new Date());
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        Map<String, String> map = getPicPath(multipartRequest, "storeImage", req);
        String shop_image = map.get("randomName");
        String storeInfo1 = getPicPaths(multipartRequest, "storeInfo", req, "store_Info1");
        String storeInfo2 = getPicPaths(multipartRequest, "storeInfo", req, "store_Info2");
        URL url = new URL(req.getRequestURL().toString());
        String urlPath = "";
        if (StringUtils.isNotBlank(shop_image)) {
            String imgName=shop_image;
            if (StringUtils.isNotBlank(shopImages)) {
                imgName=shopImages + "," + shop_image;
            }
            shopInfo.setShopimage(imgName);

        } else {
            shopInfo.setShopimage(shopImages);
        }
        if (StringUtils.isNotBlank(storeInfo1)) {

                urlPath =   "/upload/" + "storeInfo/" + storeInfo1;

            shopInfo.setStoreInfo1(urlPath);
        }
        if (StringUtils.isNotBlank(storeInfo2)) {

                urlPath =  "/upload/" + "storeInfo/" + storeInfo2;

            shopInfo.setStoreInfo2(urlPath);
        }

        /*
         * MultipartFile file = null; multipartRequest =
         * (MultipartHttpServletRequest) req; String path = "storeImage/"; file
         * = multipartRequest.getFile("shopImage"); if (file != null) { String f
         * = getFileSuffix(file); String filename = "pic_" + new
         * Date().getTime() + "." + f; uploadFile(session, file, filename,
         * path); shopInfo.setShopimage(filename); }
         */
        Integer shopId = shopInfo.getId();
        BShopInfo srcEntity = new BShopInfo();
        if (null != shopId) {
            srcEntity = storeService.queryEntityById(shopInfo.getId());
        }
        NoNullBeanCopy.copyProperties(shopInfo, srcEntity);
        BShopInfo entity = storeService.saveBShop(srcEntity);
        storeService.deleteUserId(shopInfo.getId());
        if (null != buser) {
            if ("2".equals(buser.getRole())) {
                // OC
                ids = buser.getId() + "";
            }
        }
        storeUserService.saveBShopUser(ids, DataType.getAsString(entity.getId()));
    }


    @ResponseBody
    @RequestMapping("/bindShop")
    public void pushMessage(@RequestParam(value = "id", required = false) String id, @RequestParam(value = "ids", required = false) String ids, Model model, HttpSession session,
            HttpServletResponse response) {
        beaconShopService.saveBeaconShop(ids, DataType.getAsString(id));
    }

    /**
     * 
     * @Title 函数名称： getPicPaths
     * @Description 功能描述： 获取图片集路径
     * @param 参
     *            数：
     * @return 返 回 值： String
     * @throws
     */
    public String getPicPaths(MultipartHttpServletRequest multipartRequest, String path, HttpServletRequest req, String fileKey) {
        String pattern = "yyyyMM";
        String result = "";
        String muluid = "";
        String file_path = "";
        String rootPath = req.getSession().getServletContext().getRealPath("/");
        MultipartFile picFile = multipartRequest.getFile(fileKey);
        if (picFile != null) {
            muluid = DataType.formatDate(new Date(), pattern);
            if (StringUtils.isNotEmpty(picFile.getOriginalFilename())) {
                String originalFileName = picFile.getOriginalFilename().substring(0, picFile.getOriginalFilename().lastIndexOf("."));
                String f = picFile.getOriginalFilename().substring(picFile.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
                if (!picFile.isEmpty()) {
                    file_path = ConfigUtil.get("filePath") + path;
                    File picSaveDir = new File(file_path);
                    if (!picSaveDir.exists())
                        picSaveDir.mkdirs();
                    String fileName = com.upbest.utils.Constant.getimageId(6) + "_" + muluid + "." + f;
                    result += fileName + ",";

                    String origFilePath = file_path + "/" + fileName;
                    /* if (!new File(file_path).exists()) {
                         new File(file_path).mkdirs();
                     }*/

                    try {
                        picFile.transferTo(new File(origFilePath));
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (result.endsWith(",")) {
                result = result.substring(0, result.lastIndexOf(","));
            }
        }
        return result;
    }

    public Map<String, String> getPicPath(MultipartHttpServletRequest multipartRequest, String path, HttpServletRequest req) {
        String pattern = "yyyyMM";
        String result = "";
        String muluid = "";
        String file_path = "";
        Map<String, String> map = new HashMap<String, String>();
        String resultDis = "";
        String saveFileName = "";
        for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
            String key = (String) it.next();
            MultipartFile picFile = multipartRequest.getFile(key);
            if (!picFile.isEmpty() && !"store_Info1".equals(key) && !"store_Info2".equals(key)) {
                muluid = DataType.formatDate(new Date(), pattern);
                String originalFileName = picFile.getOriginalFilename().substring(0, picFile.getOriginalFilename().lastIndexOf("."));
                String f = picFile.getOriginalFilename().substring(picFile.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
                file_path = ConfigUtil.get("filePath") + path;
                File picSaveDir = new File(file_path);
                if (!picSaveDir.exists())
                    picSaveDir.mkdirs();
                String fileName = originalFileName + "_" + muluid + "." + f;
                result += fileName + ",";
                saveFileName = com.upbest.utils.Constant.getimageId(6) + "." + f;
                resultDis += saveFileName + ",";

                String origFilePath = file_path + "/" + saveFileName;
                try {
                    picFile.transferTo(new File(origFilePath));
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (result.endsWith(",")) {
            result = result.substring(0, result.lastIndexOf(","));
        }
        map.put("realName", result);
        if (resultDis.endsWith(",")) {
            resultDis = resultDis.substring(0, resultDis.lastIndexOf(","));
        }
        map.put("randomName", resultDis);
        return map;
    }
    @ResponseBody
    @RequestMapping("/getOCTreeList")
    public void getOCTreeList(@RequestParam(value = "role", required = false) String role, Model model, HttpSession session, HttpServletResponse response) {
        Buser buser = (Buser) session.getAttribute("buser");

        List<TreeVO> list = userService.getUrVOList(buser.getId());
        String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue);
        outPrint(json, response);
    }

    @ResponseBody
    @RequestMapping("/getUserTreeList")
    public void getUserTreeList(@RequestParam(value = "role", required = false) String role, Model model, HttpSession session, HttpServletResponse response) {
        Buser buser = (Buser) session.getAttribute("buser");
        String jsonString = new String();
        List<TreeVO> list = userService.getUrVOList(buser.getId());
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        String regEx = "[''”“\"\"]";
        Pattern pat = Pattern.compile(regEx);
        if (!CollectionUtils.isEmpty(list)) {
            for (TreeVO tree : list) {
                sb.append("{");
                sb.append("\"id\":\"" + tree.getId() + "\"");
                /*
                 * Matcher m = pat.matcher(tree.getName()); String userName =
                 * m.replaceAll("").trim(); if
                 * (pat.matcher(tree.getName()).find()) {
                 * sb.append(",\"name\":'" + userName + "'"); } else {
                 * sb.append(",\"name\":\"" + tree.getName() + "\""); }
                 */
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
    @RequestMapping("/getBeaconTreeList")
    public void getBeaconTreeList(Model model, HttpSession session, HttpServletResponse response) {
        // Buser buser = (Buser) session.getAttribute("buser");

        String jsonString = new String();
        // List<TreeVO> list = userService.getUrVOList(buser.getId());
        List<TreeVO> list = beaconService.getUrVOList();
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        String regEx = "[''”“\"\"]";
        Pattern pat = Pattern.compile(regEx);
        if (!CollectionUtils.isEmpty(list)) {
            for (TreeVO tree : list) {
                sb.append("{");
                sb.append("\"id\":\"" + tree.getId() + "\"");
                /*
                 * Matcher m = pat.matcher(tree.getName()); String userName =
                 * m.replaceAll("").trim(); if
                 * (pat.matcher(tree.getName()).find()) {
                 * sb.append(",\"name\":'" + userName + "'"); } else {
                 * sb.append(",\"name\":\"" + tree.getName() + "\""); }
                 */
                sb.append(",\"name\":\"" + tree.getName() + "\"");
                // sb.append(",\"pId\":\"" + tree.getPid() +
                // "\",checked:false");
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

    private void outPrint(String str, HttpServletResponse response) {
        try {
            PrintWriter writer = response.getWriter();
            writer.write(str);
            writer.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * @Description 上传门店报表
     * @param req
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/uploadStore")
    public void uploadStore(HttpServletRequest req, HttpSession session, HttpServletResponse response) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        String id = req.getParameter("id");
        String reportType = req.getParameter("reportType");
        Buser buser = (Buser) session.getAttribute("buser");
        multipartRequest = (MultipartHttpServletRequest) req;
        Map<String, String> map = getPicPath(multipartRequest, "report", req);
        String shopfile = map.get("realName");
        String randomName = map.get("randomName");
        if (StringUtils.isNotBlank(shopfile)) {
            String[] sfile = shopfile.split(",");
            String[] rfile = randomName.split(",");
            List<BShopReport> list = new ArrayList<BShopReport>();
            for (int i = 0; i < sfile.length; i++) {
                BShopReport entity = new BShopReport();
                entity.setShopId(DataType.getAsInt(id));
                entity.setUserId(buser.getId());
                entity.setCreateTime(new Date());
                entity.setMon(DataType.getAsDate(req.getParameter("mon"), "yyyy-MM"));
                String sFile = sfile[i].substring(0, sfile[i].lastIndexOf("_")) + "_" + DataType.formatDate(DataType.getAsDate(req.getParameter("mon"), "yyyy-MM"), "yyyyMM")
                        + sfile[i].substring(sfile[i].indexOf("."));
                entity.setReport(sFile);
                entity.setReportType(reportType);
                entity.setRandomName(rfile[i]);
                list.add(entity);
            }
            storeReportService.saveBShop(list);
        }
        Map<String, Object> re = new HashMap<String, Object>();
        re.put("msg", "文件上传成功");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(toJson(re));

    }

    private String toJson(Object re) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(re);
    }

    // 图片后缀
    private String getFileSuffix(MultipartFile originalpic) {
        String f = originalpic.getOriginalFilename().substring(originalpic.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
        return f;
    }

    /**
     * 
     * @Title 函数名称： uploadFile
     * @Description 功能描述： 上传文件
     * @param 参
     *            数：
     * @return 返 回 值： void
     * @throws
     * @author 作者：zhaojinhua
     */
    public void uploadFile(HttpSession session, MultipartFile file, String filename, String path) {
        logger.debug("file:" + filename + path);
        BufferedInputStream bufferedInputStream = null;
        int fileSize = 0;
        try {
            bufferedInputStream = new BufferedInputStream(file.getInputStream());
            fileSize = bufferedInputStream.available();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuffer stringBuffer = new StringBuffer(ConfigUtil.get("filePath"));
        stringBuffer.append("report/" + path);
        String stringBuffers = null;
        try {
            stringBuffers = URLDecoder.decode(stringBuffer.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedOutputStream bufferedOutputStream;
        try {
            File ff = new File(stringBuffers);
            if (!ff.exists()) {
                try {
                    ff.mkdirs();
                    File fff = new File(stringBuffers + filename);
                    if (!fff.exists()) {
                        fff.createNewFile();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(stringBuffers + filename)));
            byte[] bytt = new byte[1024];
            int count = 0;
            int upcount = 0;
            try {
                while ((count = bufferedInputStream.read(bytt)) != -1) {
                    bufferedOutputStream.write(bytt, 0, count);
                    bufferedOutputStream.flush();
                }
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    @RequestMapping("/import")
    public String importExcel() {
        return "store/uploadStores";
    }

    @RequestMapping("/toImport")
    @ResponseBody
    public void toImport(@RequestParam("shopFile") MultipartFile file, String filename, HttpServletResponse response) throws Exception {
        Json json = new Json();
        try {
            storeService.addShopFromExcel(new ByteArrayResource(file.getBytes()));
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
    public void getdownloadTemp(@RequestParam(value = "filename", required = false) String filename, HttpServletResponse response, HttpServletRequest request) throws IOException {
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        String filePath = rootPath + TEMPLATE_FILE_PATH;
        DownloadFileUtils.download(new File(filePath), filename, response);

    }

    @ResponseBody
    @RequestMapping("/loadArea")
    public List<BArea> loadStore(HttpServletResponse response) {
        return areaService.findTopArea();

    }

    @ResponseBody
    @RequestMapping("/loadAreas")
    public List<BArea> loadStore(@RequestParam(value = "id", required = false) Integer pid, HttpServletResponse response) {
        return areaService.findByParent(pid);

    }
    @RequestMapping(value="/getShopBaseInfo")
    @ResponseBody
    public Json getShopBaseInfo(HttpServletRequest req,String shopId){
        Json result = new Json();
        Map<String ,Object > map= storeService.findStoreMapBaseInfo(shopId);
        result.setObj(map);
        result.setCode(com.upbest.mvc.constant.Constant.Code.SUCCESS_CODE);
        result.setSuccess(true);
        return result;
    }
}
