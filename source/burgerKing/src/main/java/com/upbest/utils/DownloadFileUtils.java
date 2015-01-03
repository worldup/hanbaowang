package com.upbest.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

public class DownloadFileUtils {
	/**
	 * 
	 * @param file 要下载的文件
	 * @param outputFilename 文件
	 * @param response
	 * @throws FileNotFoundException 
	 */
	public static void download(File file, String outputFilename,
			HttpServletResponse response) throws IOException {
		String fileName = null;
		if(StringUtils.hasText(outputFilename)){
			fileName = outputFilename;
		}else{
			fileName = file.getName();
			try {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		
		download(new FileInputStream(file), fileName, response);
	}

	public static void download(InputStream inputStream, String filename,
			HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition",
				"attachment;filename=" + filename);
		response.addHeader("Content-Length", inputStream.available() + "");
		
		OutputStream outp = null;
		try {
			outp = response.getOutputStream();
			byte[] b = new byte[1024];
			int i = 0;

			while ((i = inputStream.read(b)) > 0) {
				outp.write(b, 0, i);
			}
			outp.flush();
		} catch (Exception e) {
			// log.error("", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outp != null) {
				try {
					outp.close();
					outp = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
