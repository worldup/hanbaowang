package com.upbest.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.upbest.mvc.entity.Buser;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.upbest.mvc.service.IUploadInfoService;
import com.upbest.mvc.vo.UpLoadInfoVO;
import com.upbest.utils.ConfigUtil;
import com.upbest.utils.DataType;
import com.upbest.utils.PageModel;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping("/toolKit")
public class UploadInfoController {
	
	@Autowired
	private IUploadInfoService service;
	
	@RequestMapping(value = "/index")
    public String index(Model model){
	    model.addAttribute("current", "imge");
        return "/imge/addImge";
    }
	
	@ResponseBody
    @RequestMapping("/addFiles")
    public void addStore(@RequestParam(value = "originalName", required = false) String originalName,
                         @RequestParam(value = "priority", required = false) String priority,
                         HttpServletRequest req) throws IllegalStateException, IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        String rootPath = req.getSession().getServletContext().getRealPath("/");
        //文件上传目录
        String uploadDirectory = ConfigUtil.get("toolKitPath");
        if (!new File(uploadDirectory).exists())
            new File(uploadDirectory).mkdirs();
        String fullDirectory =  uploadDirectory;
        
        MultiValueMap<String, MultipartFile> multiFileMap = multipartRequest.getMultiFileMap();
        if(!CollectionUtils.isEmpty(multiFileMap)){
        	for (Entry<String, List<MultipartFile>> entry : multiFileMap.entrySet()) {
        		List<MultipartFile> files = entry.getValue();
        		if(!CollectionUtils.isEmpty(files)){
        			for (MultipartFile multipartFile : files) {
        				String fileName = multipartFile.getOriginalFilename();
        				String subbfix = getSubffix(fileName);
        				String realName = com.upbest.utils.Constant.getimageId(6)+"." + subbfix;
        				String fullPath = fullDirectory + realName;
        				multipartFile.transferTo(new File(fullPath));
        				 URL url=new URL(req.getRequestURL().toString());
        		            String urlPath="";
        		            if(url.getPort()!=-1){
        		                //urlPath= url.getProtocol()+":"+"//"+url.getHost()+":"+url.getPort()+ "/" + "upload/toolKit/"+realName;
        		                urlPath=  "upload/toolKit/"+realName;
        		            }else{
        		                /*urlPath=  url.getProtocol()+":"+"//"+url.getHost()+ "/" + "upload/toolKit/"+realName;*/
        		                urlPath= "upload/toolKit/"+realName;
        		            }
        				
        				service.saveFile(originalName,urlPath);
					}
        		}
			}
        }
    }
    @ResponseBody
    @RequestMapping("/addToolKitFiles")
    public void addToolKitFiles(
                         @RequestParam(value = "priority", required = false) Integer priority,
                         HttpServletRequest req) throws IllegalStateException, IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
        String rootPath = req.getSession().getServletContext().getRealPath("/");
        Integer createUser=null;
        String createUserName=null;
        String title="";
        Buser u = (Buser)req.getSession().getAttribute("buser");
        if(u!=null){
            createUser= u.getId();
            createUserName=u.getName();
        }
        //文件上传目录
        String uploadDirectory = ConfigUtil.get("toolKitPath");
        if (!new File(uploadDirectory).exists())
            new File(uploadDirectory).mkdirs();
        String fullDirectory =  uploadDirectory;

        MultiValueMap<String, MultipartFile> multiFileMap = multipartRequest.getMultiFileMap();
        if(!CollectionUtils.isEmpty(multiFileMap)){
            for (Entry<String, List<MultipartFile>> entry : multiFileMap.entrySet()) {
                List<MultipartFile> files = entry.getValue();
                if(!CollectionUtils.isEmpty(files)){
                    for (MultipartFile multipartFile : files) {
                        String fileName = multipartFile.getOriginalFilename();

                        String subbfix = getSubffix(fileName);
                        String realName = com.upbest.utils.Constant.getimageId(6)+"." + subbfix;
                        String fullPath = fullDirectory + realName;
                        multipartFile.transferTo(new File(fullPath));
                        URL url=new URL(req.getRequestURL().toString());
                        String urlPath="";
                        if(url.getPort()!=-1){
                            //urlPath= url.getProtocol()+":"+"//"+url.getHost()+":"+url.getPort()+ "/" + "upload/toolKit/"+realName;
                            urlPath=  "upload/toolKit/"+realName;
                        }else{
        		                /*urlPath=  url.getProtocol()+":"+"//"+url.getHost()+ "/" + "upload/toolKit/"+realName;*/
                            urlPath= "upload/toolKit/"+realName;
                        }
                        String fullFileName=((CommonsMultipartFile) multipartFile).getFileItem().getName();
                        if(fullFileName.lastIndexOf(".")>-1){
                            title=fullFileName.substring(0,fullFileName.lastIndexOf(".")) ;
                        }
                        service.saveFile(title,urlPath,priority,createUser,createUserName);
                    }
                }
            }
        }
    }
	private String getSubffix(String fileName) {
		String s = "";
		if(!StringUtils.isEmpty(fileName)){
			s = fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		return s;
	}
	
	@ResponseBody
    @RequestMapping("/list")
    public void list(@RequestParam(value = "imgeName", required = false) String name,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "rows", 
            required = false) Integer pageSize, 
            Model model,
            HttpSession session,
            HttpServletResponse response) {
        if (pageSize == null) {
            pageSize = 10;
        }
        PageRequest requestPage = new PageRequest(page != null ? page.intValue() - 1 : 0, pageSize);
        Page<Object[]> imgeList =service.findImgeList(name, requestPage);
        PageModel result=new PageModel();
        result.setPage(page);
        result.setRows(getImgeList(imgeList.getContent()));
        result.setPageSize(pageSize);
        result.setRecords(NumberUtils.toInt(ObjectUtils.toString(imgeList.getTotalElements())));
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
    
    private List<UpLoadInfoVO> getImgeList(List<Object[]> list){
        List<UpLoadInfoVO> result=new ArrayList<UpLoadInfoVO>();
        if(!CollectionUtils.isEmpty(list)){
            UpLoadInfoVO entity=null;
            for(Object[] obj:list){
                entity=new UpLoadInfoVO();
                entity.setId(DataType.getAsInt(obj[0]));
                entity.setOriginalName(DataType.getAsString(obj[1]));
                entity.setPath(DataType.getAsString(obj[2]));
                entity.setCreateTime(DataType.getAsDate(obj[3]));
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
