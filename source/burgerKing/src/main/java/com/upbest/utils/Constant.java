package com.upbest.utils;

import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.upbest.pageModel.Json;

public class Constant {
    
    /**
     * 将传递过来的base64参数转jsonObject
     * @param req
     * @return
     */
    @SuppressWarnings("static-access")
    public static Json convertJson(HttpServletRequest req) {
        Json json = new Json();
        BaseCode64 base64 = new BaseCode64();
        try {
            String sign = req.getParameter("sign");
            System.out.println(sign + "  .......................aa");
            byte[] decodeStr = base64.decode(sign);
            sign = URLDecoder.decode(new String(decodeStr), "UTF-8");
            JSONObject o = JSONObject.parseObject(sign);
            json.setObj(o);
            json.setSuccess(true);
        } catch (Exception ex) {
            json.setMsg("非法参数..." + ex.getMessage());
            json.setSuccess(false);
            json.setCode(1002);
            ex.printStackTrace();
        }
        return json;
    }
    @SuppressWarnings("static-access")
    public static Json convertJsonWithOutEncode(HttpServletRequest req) {
        Json json = new Json();
        BaseCode64 base64 = new BaseCode64();
        try {
            String sign = req.getParameter("sign");
            System.out.println(sign + "  .......................aa");
           // byte[] decodeStr = base64.decode(sign);
           // sign = URLDecoder.decode(new String(decodeStr), "UTF-8");
            JSONObject o = JSONObject.parseObject(sign);
            json.setObj(o);
            json.setSuccess(true);
        } catch (Exception ex) {
            json.setMsg("非法参数..." + ex.getMessage());
            json.setSuccess(false);
            json.setCode(1002);
            ex.printStackTrace();
        }
        return json;
    }
    public static String getimageId(int len)
    {
        Calendar calendar = Calendar.getInstance();
        Long time = new Date().getTime()+(calendar.get(Calendar.ZONE_OFFSET) + calendar.get(Calendar.DST_OFFSET)) / (60 * 1000);
        Constant c = new Constant();
        return time+c.randomString(len);
    }
    
    public String randomString(int length) {
        Random randGen = null;
        char[] numbersAndLetters = null;
        if (length < 1) {
            return null;
        }
        if (randGen == null) {
               randGen = new Random();
               numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" +
                  "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
                }
        char [] randBuffer = new char[length];
        for (int i=0; i<randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }
}
