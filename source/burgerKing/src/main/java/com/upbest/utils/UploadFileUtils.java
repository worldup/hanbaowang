package com.upbest.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Action;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


public class UploadFileUtils{

    private static final long serialVersionUID = 1L;
   
    @RequestMapping(value = "/uploadFileAction/multiUploadFile")
    public String multiUploadFile(
            @RequestParam("multiUploadifyFileName")String multiUploadifyFileName,
            @RequestParam("multiUploadify")File multiUploadify,
            @RequestParam("fileNameContentType")List<String> fileNameContentType,
            @RequestParam("fileNameFileName")List<String> fileNameFileName,
            HttpServletResponse response) throws Exception {

        String extName = "";// 扩展名

        String newFileName = "";// 新文件名
        String fileName = "";
        String savePath = getDownDir();
        if (!savePath.endsWith(File.separator)) {
            savePath += File.separator;
        }
        response.setCharacterEncoding("utf-8");
        multiUploadifyFileName = multiUploadifyFileName.replaceAll(" ", "");
        if (multiUploadifyFileName.lastIndexOf(".") >= 0) {
            extName = multiUploadifyFileName.substring(multiUploadifyFileName.lastIndexOf("."));
            fileName = multiUploadifyFileName.substring(0, multiUploadifyFileName.lastIndexOf("."));
        } else {
            fileName = multiUploadifyFileName;
        }
        newFileName = fileName + extName;

        multiUploadify.renameTo(new File(savePath + newFileName));

        response.getWriter().print(newFileName);

        return null;

    }

    public String getDownDir() {
        String downDir = new PropertiesConfiguration("classpath:burgerking.properties").getString("filePath");
        return downDir;
    }

}