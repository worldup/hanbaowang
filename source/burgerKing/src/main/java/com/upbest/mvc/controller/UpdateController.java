package com.upbest.mvc.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.upbest.mvc.entity.BUpdate;
import com.upbest.mvc.entity.Buser;
import com.upbest.mvc.service.IUpdateService;
import com.upbest.utils.ConfigUtil;
import com.upbest.utils.DataType;
import com.upbest.utils.PageModel;

@Controller
@RequestMapping(value = "/update")
public class UpdateController {
	private static final Logger logger = LoggerFactory
			.getLogger(UpdateController.class);
	private static String percent_to_upload = "";

	@Inject
	IUpdateService updateService;

	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("current", "update");
		return "/update/updateList";
	}

	@RequestMapping("/list")
	public void queryUpdateList(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer pageSize,
			HttpServletResponse response,
			@RequestParam("versionNum") String versionNum) {
		if (pageSize == null) {
			pageSize = 10;
		}
		PageRequest requestPage = new PageRequest(
				page != null ? page.intValue() - 1 : 0, pageSize);
		Page<Object[]> updateList = updateService.findUpdateList(requestPage,
				versionNum);
		PageModel result = new PageModel();
		result.setPage(page);
		result.setRows(getShopInfo(updateList.getContent()));
		result.setPageSize(pageSize);
		result.setRecords(NumberUtils.toInt(ObjectUtils.toString(updateList
				.getTotalElements())));
		String json = com.alibaba.fastjson.JSON.toJSONStringWithDateFormat(
				result, "yyyy-MM-dd HH:mm:ss",
				SerializerFeature.WriteMapNullValue);
		outPrint(json, response);
	}

	@RequestMapping(value = "/edite")
	public String editeApkInfo(String id, Model model) {
		if (StringUtils.isNotBlank(id)) {
			BUpdate update = updateService.findById(Integer.parseInt(id));
			model.addAttribute("update", update);
			// return "/update/UpdateApkInfo";
		}
		return "/update/addApkInfo";
	}

	@ResponseBody
	@RequestMapping(value = "/updateApkInfo")
	public void updateApkInfo(BUpdate update, HttpServletRequest req,
			HttpSession session) {
		update.setUpdateTime(new Date());
		// 重新上传apk文件
		if (StringUtils.isEmpty(update.getApkSize())) {
			// 删除指定文件
			String filepath = ConfigUtil.get("filePath");
			String path = "apk-version/";
			File folder = new File(filepath + path);
			File[] files = folder.listFiles();
			if (null != files)
				for (File file : files) {
					if (file.getName().equals(update.getUrl())) {
						file.delete();
					}
				}
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
			MultipartFile file = multipartRequest.getFile("apkfile");
			String fileName = new Date().getTime() + ".apk";
			BufferedInputStream bufferedInputStream = null;
			int fileSize = 0;
			try {
				bufferedInputStream = new BufferedInputStream(
						file.getInputStream());
				fileSize = bufferedInputStream.available();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// String path="apk-version/";
			uploadFile(session, file, fileName, path);
			update.setUrl(fileName);
			update.setApkSize(fileSize + "");
		}

		updateService.saveAPkInfo(update);
	}

	@ResponseBody
	@RequestMapping(value = "/addApkInfo")
	public void addApkinfo(HttpServletRequest req, HttpSession session) {
		String versionNum = req.getParameter("versionNum");
		String apkName = req.getParameter("apkName");
		String remark = req.getParameter("remark");
		BUpdate update = new BUpdate();
		update.setApkName(apkName);
		update.setRemark(remark);
		update.setVersionNum(versionNum);
		update.setUpdateTime(new Date());

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		MultipartFile file = multipartRequest.getFile("apkfile");
		String fileName = new Date().getTime() + ".apk";
		BufferedInputStream bufferedInputStream = null;
		int fileSize = 0;
		try {
			bufferedInputStream = new BufferedInputStream(file.getInputStream());
			fileSize = bufferedInputStream.available();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String path = "/apk-version/";
		uploadFile(session, file, fileName, path);
		update.setUrl(fileName);
		update.setApkSize(fileSize + "");
		updateService.saveAPkInfo(update);
	}

	@ResponseBody
	@RequestMapping(value = "/del")
	public void delApkInfo(@RequestParam("id") Integer id,
			HttpServletResponse response) {
		BUpdate update = updateService.findById(id);
		// 删除指定文件
		String filepath = ConfigUtil.get("filePath");
		String path = "/apk-version/";
		File folder = new File(filepath + path);
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.getName().equals(update.getUrl())) {
					file.delete();
				}
			}
		}
		updateService.delAPKInfoById(id);
		outPrint("0", response);
	}

	private List<BUpdate> getShopInfo(List<Object[]> list) {
		List<BUpdate> result = new ArrayList<BUpdate>();
		if (!CollectionUtils.isEmpty(list)) {
			BUpdate entity = null;
			for (Object[] obj : list) {
				entity = new BUpdate();
				entity.setId(DataType.getAsInt(obj[0]));
				entity.setApkName(DataType.getAsString(obj[1]));
				entity.setApkSize(DataType.getAsString(obj[2]));
				entity.setVersionNum(DataType.getAsString(obj[3]));
				entity.setRemark(DataType.getAsString(obj[4]));
				entity.setUrl(DataType.getAsString(obj[5]));
				entity.setUpdateTime(DataType.getAsDate(obj[6]));
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

	/**
	 * 
	 * @Title 函数名称： uploadFile
	 * @Description 功能描述： 上传apk文件
	 * @param 参
	 *            数：
	 * @return 返 回 值： void
	 * @throws
	 */
	public void uploadFile(HttpSession session, MultipartFile file,
			String filename, String path) {
		logger.debug("file:" + filename + path);
		String rootPath = session.getServletContext().getRealPath("/");
		BufferedInputStream bufferedInputStream = null;
		int fileSize = 0;
		try {
			bufferedInputStream = new BufferedInputStream(file.getInputStream());
			fileSize = bufferedInputStream.available();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String file_path = ConfigUtil.get("filePath") + path;
		File picSaveDir = new File(file_path);
		if (!picSaveDir.exists())
			picSaveDir.mkdirs();
		StringBuffer stringBuffer = new StringBuffer(ConfigUtil.get("filePath"));
		stringBuffer.append(path);
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
			bufferedOutputStream = new BufferedOutputStream(
					new FileOutputStream(new File(stringBuffers + filename)));
			byte[] bytt = new byte[1024];
			int count = 0;
			int upcount = 0;
			try {
				while ((count = bufferedInputStream.read(bytt)) != -1) {
					bufferedOutputStream.write(bytt, 0, count);
					bufferedOutputStream.flush();
					if (session.getAttribute("uploadProgress") != null) {
						session.removeAttribute("uploadProgress");
					}
					upcount += count;
					Double per = (double) upcount / fileSize * 100;
					java.math.BigDecimal big = new java.math.BigDecimal(per);
					percent_to_upload = big.setScale(2,
							java.math.BigDecimal.ROUND_HALF_UP) + "";
				}
				bufferedOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	@RequestMapping("/examport")
	public String FileDownload(HttpServletRequest req, HttpSession session,
			HttpServletResponse response) {
		String result = null;

		String filename = req.getParameter("filename");
		String ser = req.getSession().getServletContext().getRealPath("/");
		/*
		 * String serverPath = UpdateController.class.getResource("/")
		 * .getPath(); String serverPath = ConfigUtil.get("filePath"); String
		 * serverNewPath = ""; for (int i = 0; i < 3; i++) { if (i > 0) {
		 * serverPath = serverNewPath; } serverNewPath = serverPath
		 * .substring(0, serverPath.lastIndexOf("/")); }
		 * System.out.println(serverNewPath);
		 * 
		 * try { serverPath = URLDecoder.decode(serverNewPath, "UTF-8"); } catch
		 * (UnsupportedEncodingException e1) { // TODO Auto-generated catch
		 * block e1.printStackTrace(); }
		 */
		String serverPath = ConfigUtil.get("filePath");
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(ser);
		stringBuffer.append(serverPath);
		stringBuffer.append("apk-version/");
		InputStream fis;
		byte[] bytt = new byte[1024];
		int count = 0;
		try {
			String stringBuffers = "";
			try {
				stringBuffers = URLDecoder.decode(stringBuffer.toString(),
						"UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			fis = new BufferedInputStream(new FileInputStream(stringBuffers
					+ filename));
			File file = new File(stringBuffers + filename);
			try {

				// 清空response
				response.reset();
				// 设置response的Header
				response.setHeader(
						"Content-Disposition",
						"attachment; filename="
								+ new String(filename.getBytes("gb2312"),
										"ISO8859-1"));
				response.addHeader("Content-Length", "" + file.length());
				OutputStream toClient = new BufferedOutputStream(
						response.getOutputStream());
				response.setContentType("application/octet-stream");
				while ((count = fis.read(bytt)) != -1) {
					toClient.write(bytt, 0, bytt.length);
					toClient.flush();
				}
				toClient.close();

				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			result = "/resourceNotFound/index";
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "get")
	public String get(String id, Model model, HttpSession session) {
		BUpdate up = updateService.findById(Integer.parseInt(id));
		model.addAttribute("up", up);
		return "/update/getUpdate";
	}

}
