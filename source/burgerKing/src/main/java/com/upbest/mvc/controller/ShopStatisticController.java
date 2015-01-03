package com.upbest.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.service.IShopStatisticService;
import com.upbest.mvc.vo.BshopStatisticVO;
import com.upbest.pageModel.Json;
import com.upbest.utils.CommonUtils;
import com.upbest.utils.DataType;
import com.upbest.utils.DownloadFileUtils;
import com.upbest.utils.PageModel;

@Controller
@RequestMapping("/shopStatistic")
public class ShopStatisticController {

	@Inject
	private IShopStatisticService service;

	private static final String TEMPLATE_FILE_PATH = "/excelTemp/shopStatistic.xlsx";

	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("current", "shopStatistic");
		return "shopStatistic/shopStatistic";
	}

	@RequestMapping("/import")
	public String importExcel() {
		return "shopStatistic/uploadShopStatistic";
	}

	@ResponseBody
	@RequestMapping("/list")
	public void list(
			@RequestParam(value = "sales", required = false) String name,
			@RequestParam(value = "month", required = false) String mon,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord,
			@RequestParam(value = "rows", required = false) Integer pageSize,
			Model model, HttpSession session, HttpServletResponse response) {
		if (pageSize == null) {
			pageSize = 10;
		}
		Order or = null;
		List<Order> orders = new ArrayList<Order>();
		if (StringUtils.isNotBlank(sord) && StringUtils.isNotBlank(sidx)) {
			if (sord.equalsIgnoreCase("desc")) {
				or = new Order(Direction.DESC, sidx);
			} else {
				or = new Order(Direction.ASC, sidx);
			}
			orders.add(or);
		} else {
			Order or1 = new Order(Direction.DESC, "s.sales");
			Order or2 = new Order(Direction.ASC, "s.tc");
			orders.add(or1);
			orders.add(or2);
		}
		Sort so = new Sort(orders);
		PageRequest requestPage = new PageRequest(
				page != null ? page.intValue() - 1 : 0, pageSize, so);
		Buser buser = (Buser) session.getAttribute("buser");
		Page<Object[]> sta = service.findShopStatistic(buser,name,mon,requestPage);
		PageModel result = new PageModel();
		result.setPage(page);
		result.setRows(getShopInfo(sta.getContent()));
		result.setPageSize(pageSize);
		result.setRecords(NumberUtils.toInt(ObjectUtils.toString(sta
				.getTotalElements())));
		String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(
				result, "yyyy-mm-dd HH:mm:ss",
				SerializerFeature.WriteMapNullValue);
		outPrint(json, response);
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

	private List<BshopStatisticVO> getShopInfo(List<Object[]> list) {
		List<BshopStatisticVO> result = new ArrayList<BshopStatisticVO>();
		if (!CollectionUtils.isEmpty(list)) {
			BshopStatisticVO entity = null;
			for (Object[] obj : list) {
				entity = new BshopStatisticVO();
				entity.setShopName(DataType.getAsString(obj[1]));
				entity.setShopId(DataType.getAsString(obj[0]));
				entity.setSales(DataType.getAsString(obj[2]));
				entity.setTc(DataType.getAsString(obj[3]));
				entity.setMonth(formatData(DataType.getAsDate(obj[4],"yyyy-MM-dd")));
				result.add(entity);
			}
		}
		return result;
	}

	private String formatData(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		return date == null ? "" : format.format(date);
	}

	@RequestMapping("/toImport")
	@ResponseBody
	  public void toImport(@RequestParam("shopStatisticFile") MultipartFile file,
	            String filename,HttpServletResponse response) throws Exception {
	        Json json = new Json();
	        try {
	            json.setObj(service.addShopStatisticFromExcel(new ByteArrayResource(file.getBytes())));
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

}
