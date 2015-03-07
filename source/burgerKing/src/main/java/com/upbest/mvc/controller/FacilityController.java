package com.upbest.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.upbest.mvc.entity.BFacility;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.service.IBuserService;
import com.upbest.mvc.service.IFacilityService;
import com.upbest.mvc.vo.BFacilityVO;
import com.upbest.mvc.vo.SelectionVO;
import com.upbest.pageModel.Json;
import com.upbest.utils.CommonUtils;
import com.upbest.utils.DownloadFileUtils;
import com.upbest.utils.PageModel;

@Controller
@RequestMapping("/facility")
public class FacilityController {

	@Inject
	private IFacilityService service;

	@Inject
	private IBuserService userService;

	private static final String TEMPLATE_FILE_PATH = "excelTemp/facility.xlsx";

	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("current", "facility");
		return "facility/facilityManager";
	}

	@RequestMapping("/addOrUpdatePage")
	public String addOrUpdatePage(
			@RequestParam(value = "id", required = false) Integer id,
			Model model) {
		if (id != null) {
			BFacility facility = service.queryFacility(id);
			model.addAttribute("facility", facility);
		}
		model.addAttribute("users", getUsersInfo());

		return "facility/addOrUpdateFacility";
	}

	@RequestMapping("/import")
	public String importExcel() {
		return "facility/uploadFacility";
	}

	@RequestMapping("/add")
	@ResponseBody
	public void addFacility(BFacility facility,HttpServletResponse response) throws Exception{
        Json json = new Json();
        boolean result=true;
        try{
            service.saveFacility(facility);
        }catch (Exception e){
            e.printStackTrace();
            result=false;
        }
        json.setSuccess(result);
        PrintWriter writer = response.getWriter();
        writer.print(CommonUtils.toJson(json));
        writer.close();

	}

	@RequestMapping("/delete")
	@ResponseBody
	public void deleteFacility(@RequestParam("id") int facilityId,HttpServletResponse response) throws  Exception{
        Json json = new Json();
        boolean result=true;
        try{
            service.deleteFacility(facilityId);
        }catch (Exception e){
            e.printStackTrace();
            result=false;
        }
        json.setSuccess(result);
        PrintWriter writer = response.getWriter();
        writer.print(CommonUtils.toJson(json));
        writer.close();

	}

	@RequestMapping("/update")
	@ResponseBody
	public void updateFacility(BFacility facility,HttpServletResponse response) throws  Exception{
        Json json = new Json();
        boolean result=true;
        try{
            service.saveFacility(facility);
        }catch (Exception e){
            e.printStackTrace();
            result=false;
        }
	    json.setSuccess(result);
        PrintWriter writer = response.getWriter();
        writer.print(CommonUtils.toJson(json));
        writer.close();
	}

	@RequestMapping("/list")
	@ResponseBody
	public PageModel<BFacilityVO> queryFacilities(
			@RequestParam("facilityName") String facilityName,
			@RequestParam("page") int page, @RequestParam("rows") int size,
			HttpServletResponse response) {
		Pageable pageable = new PageRequest(page - 1, size);
		return service.queryFacility(facilityName, pageable);
	}

	@RequestMapping("/detail")
	@ResponseBody
	public BFacility queryFacilitie(@RequestParam("id") int id) {
		return service.queryFacility(id);
	}

	@RequestMapping("/toImport")
	@ResponseBody
	 public void toImport(@RequestParam("facilityFile") MultipartFile file,
	            String filename,HttpServletResponse response) throws Exception {
	        Json json = new Json();
	        try {
	            service.addFacilityFromExcel(new ByteArrayResource(file.getBytes()));
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

	private List<SelectionVO> getUsersInfo() {
		List<SelectionVO> usersInfo = new ArrayList<SelectionVO>();

		List<Buser> users = userService.findAllUser();
		for (Buser buser : users) {
		    if(null!=buser){
		        if(buser.getRole().equals("0")){
	                continue;
	            }
	            usersInfo.add(new SelectionVO(buser.getName(), String.valueOf(buser
	                    .getId())));
		    }
		}
		return usersInfo;
	}

}
