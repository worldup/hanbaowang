package com.upbest.test;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.upbest.utils.BaseCode64;
import com.upbest.utils.DataType;
import com.upbest.utils.HttpClientUrl;

public class Test {
    // private static String url = "http://218.94.104.181:8080/dzkhd/";//正式环境
    // private static String url = "http://127.0.0.1:8082/dzkhd/";//正式环境
   // private static String url = "http://localhost:8080/";
//  private static String url = "http://upbest.xicp.net:8087/";
    private static String url = "http://localhost:8088/";

    public static void securi_verify() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("facilityId", "99000467099081");// 设备唯一标识符
        form.put("username", "fengshun@bkchina.cn");
        form.put("password", "111111");
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/facilityLogin/securi_verify?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_addMessage() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("userId", "254");// 用户ID
        form.put("pushTitle", "liyubing");// 消息标题
        form.put("pushContent", "123456");// 消息标题
        form.put("messageType", "4");// 消息类型
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/message/securi_addMessage?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_getStore() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("id", "118");// 消息类型
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/store/securi_get?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_storeList() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("shopName", "");// 门店名称
        form.put("page", 1);// 第几页
        form.put("pageSize", 10);// 一页有多少数据
        form.put("userId", 507);
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/store/securi_list?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_userList() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("name", "");// OC人员名称
        form.put("userId", 507);
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/facilityLogin/securi_list?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_taskList() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("userId", 504);// 登录用户名
        form.put("page", 1);
        form.put("size", 10);
        form.put("date", DataType.getAsDate2("2014-11-28").getTime());
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/task/securi_list?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_taskCaList() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("userId", 297);// 登录用户名
        form.put("month", "10");
        form.put("year", "2014");
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/task/securi_caList?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_delTask() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        // form.put("taskId", 20);// 任务ID
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/task/securi_del?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_update() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("taskId", 146);// 任务ID
        form.put("taskType", 53);// 任务名称
        form.put("desc", "任务详情修改");
        form.put("startTime", DataType.getAsDate2("2014-02-12 12:12:12").getTime());
        form.put("finishTime", DataType.getAsDate2("2014-02-12 12:12:12").getTime());
        form.put("userId", 297);
        form.put("storeId", 118);
        form.put("status", 1);
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/task/securi_update?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_detail() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("taskId", 21);// 任务ID
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/task/securi_detail?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_messageList() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("userId", 299);// 登录用户名
        form.put("page", 1);
        form.put("pageSize", 50);
        form.put("messageType", "4");
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/message/securi_list?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_sts() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
     /* form.put("userId", 297);// 登录用户名
        form.put("year", "2014");
        form.put("type", "1");
        form.put("sord", "0");
        form.put("month", "10");*/
       /* form.put("userId", 297);// 登录用户名
        form.put("year", "2014");
        form.put("type", "1");
        form.put("sord", "1");
        form.put("quarter", "第四季度");
*/           form.put("userId", 495);// 登录用户名
           form.put("year", "2014");
           form.put("type", "0");
           form.put("sord", "1");
           form.put("quarter", "第四季度");
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/sts/securi_sts?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_beanconGet() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("uuid", "蓝牙2");
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/beacon/securi_get?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_signIn() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("username", "zhengqun");// OC人员名称
        form.put("lng", 254);
        form.put("lat", 221);
        form.put("location", "南京仙林大学城");
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/sign/securi_signIn?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_signOut() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("username", "zhengqun");// OC人员名称
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/sign/securi_signOut?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_updateHead1() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(url + "api/exam/securi_HeadImg");

            FileBody bin = new FileBody(new File("c:/logo.png"));
           // StringBody userId = new StringBody("73", ContentType.TEXT_PLAIN);

            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("headFile", bin).build();

            httppost.setEntity(reqEntity);

            System.out.println("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

    public static void securi_updateHead() throws Exception {
        final String TAG = "uploadFile";
        final int TIME_OUT = 10 * 10000000; // 超时时间
        final String CHARSET = "utf-8"; // 设置编码
        final String SUCCESS = "1";
        final String FAILURE = "0";

        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        String RequestURL = url + "api/user/securi_updateHeadImg";
        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            File file = new File("c:/logo.png");
            if (file != null) {
                /** 
                 * 当文件不为空，把文件包装并且上传 
                 */
                OutputStream outputSteam = conn.getOutputStream();

                DataOutputStream dos = new DataOutputStream(outputSteam);
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /** 
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件 
                 * filename是文件的名字，包含后缀名的 比如:abc.png 
                 */

                sb.append("Content-Disposition: form-data; name=\"headFile\"; filename=\"" + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type:multipart/form-data; charset=" + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                // String content = "userId=73";
                // dos.writeUTF(content);
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                /** 
                 * 获取响应码 200=成功 当响应成功，获取响应的流 
                 */
                int res = conn.getResponseCode();
                System.out.println(res);
                if (res == 200) {
                    System.out.println("success");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void securi_addtask() throws Exception {
        List<Map<String, Object>> taskList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("desc", "desc11111111111111");
        map1.put("starTime", DataType.getAsDate2("2014-10-11 12:12:12").getTime());
        map1.put("taskName", "taskName1");
        map1.put("taskType", 53);
        map1.put("userId", 297);
        map1.put("storeId", 129);
        map1.put("isSelfCreate", 0);
        map1.put("executeId", 297);
        map1.put("state", 0);
        
       /* Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("desc", "desc22222222222222");
        map2.put("starTime", DataType.getAsDate2("2014-10-11 12:12:12").getTime());
        map2.put("taskName", "taskName2");
        map2.put("taskType", 42);
        map2.put("userId", 297);
        map2.put("storeId", 129);

        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("desc", "desc3333333333333333");
        map3.put("starTime", DataType.getAsDate2("2014-10-11 12:12:12").getTime());
        map3.put("taskName", "taskName3");
        map3.put("taskType", 42);
        map3.put("userId", 297);
        map3.put("storeId", 129);*/

        taskList.add(map1);
        //taskList.add(map2);
        //taskList.add(map3);

        Map<String, Object> form = new HashMap<String, Object>();
        form.put("tasks", taskList);
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/task/securi_add?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_queryExamInfo() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("page", "1");
        form.put("pageSize", "10");
        form.put("userId", 254);
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/exam/securi_getExamList?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_findExamInfo() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("examId", "31");
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);   
        url += "api/exam/securi_findExamInfo?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_examtype() throws Exception {
        url += "api/exam/securi_examtype";
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_shopStatistic_detail() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("shopId", "1号店");//
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/store/securi_shopStatistic_detail?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_shopStatistic_list() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        Integer[] examType = new Integer[] { 82 };
        form.put("examType", examType);//
        
        Map<String, String> areaInfo = new HashMap<String, String>();
        areaInfo.put("shopNum", "1号店");
        form.put("shopArea", areaInfo);
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/store/securi_shopStatistic?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static List<Map<String, Object>> getExamHeadingList() {
        List<Map<String, Object>> examHeadingList = new ArrayList<Map<String, Object>>();
        /*Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("hId", 4);
        map1.put("value", "1277106667");
        examHeadingList.add(map1);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("hId", 5);
        map2.put("value", "餐厅经理");
        examHeadingList.add(map2);
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("hId", 6);
        map3.put("value", "稽核人");
        examHeadingList.add(map3);
        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("hId", 7);
        map4.put("value", "餐厅");
        examHeadingList.add(map4);
        Map<String, Object> map5 = new HashMap<String, Object>();
        map5.put("hId", 8);
        map5.put("value", 90);
        examHeadingList.add(map5);
        Map<String, Object> map6 = new HashMap<String, Object>();
        map6.put("hId", 9);
        map6.put("value", 95);
        examHeadingList.add(map6);
        Map<String, Object> map7 = new HashMap<String, Object>();
        map7.put("hId", 10);
        map7.put("value", "值班经理");
        examHeadingList.add(map7);
        Map<String, Object> map8 = new HashMap<String, Object>();
        map8.put("hId", 11);
        map8.put("value", "12");
        examHeadingList.add(map8);*/
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("hId", 4);
        map1.put("value", "1277106667");
        examHeadingList.add(map1);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("hId", 5);
        map2.put("value", "餐厅经理");
        examHeadingList.add(map2);
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("hId", 6);
        map3.put("value", "稽核人");
        examHeadingList.add(map3);
        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("hId", 7);
        map4.put("value", "餐厅");
        examHeadingList.add(map4);
        Map<String, Object> map5 = new HashMap<String, Object>();
        map5.put("hId", 8);
        map5.put("value", 90);
        examHeadingList.add(map5);
        Map<String, Object> map6 = new HashMap<String, Object>();
        map6.put("hId", 9);
        map6.put("value", 95);
        examHeadingList.add(map6);
        Map<String, Object> map7 = new HashMap<String, Object>();
        map7.put("hId", 10);
        map7.put("value", "值班经理");
        examHeadingList.add(map7);
        Map<String, Object> map8 = new HashMap<String, Object>();
        map8.put("hId", 11);
        map8.put("value", null);
        examHeadingList.add(map8);
        return examHeadingList;
    }

    public static List<Map<String, Object>> getProblemAnalysisList() {
        List<Map<String, Object>> examHeadingList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("resource", "根源1");
        map1.put("resolve", "解决方案1");
        map1.put("scorenum", "1");
        examHeadingList.add(map1);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("resource", "根源2");
        map2.put("resolve", "解决方案2");
        map2.put("scorenum", "2");
        examHeadingList.add(map2);
        return examHeadingList;
    }

    public static List<Map<String, Object>> getActionPlanList() {
        List<Map<String, Object>> examHeadingList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("beginTime", "1277106667");
        map1.put("expEndTime", "1277106667");
        map1.put("expResult", "预期结果");
        map1.put("realEndTime", "1277106667");
        map1.put("tack", "行动步骤");
        map1.put("userName", "负责人1");
        examHeadingList.add(map1);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("beginTime", "1277106667");
        map2.put("expEndTime", "1277106667");
        map2.put("expResult", "预期结果2");
        map2.put("realEndTime", "1277106667");
        map2.put("tack", "行动步骤2");
        map2.put("userName", "负责人2");
        examHeadingList.add(map2);
        return examHeadingList;
    }

    public static List<Map<String, Object>> getQuestionList() {
        List<Map<String, Object>> examHeadingList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("qEvidence", "图片1.jpg");
        map1.put("questionId", "55");
        // map1.put("tAnswer","");
        map1.put("tValue", "1");
        List<Map<String, Object>> fieldsList = new ArrayList<Map<String, Object>>();
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("hId", 13);
        fieldMap.put("value", "描述问题");
        fieldsList.add(fieldMap);
        Map<String, Object> fieldMap2 = new HashMap<String, Object>();
        fieldMap2.put("hId", 15);
        fieldMap2.put("value", "1");
        // TODO
        // fieldMap2.put("file", "");
        fieldsList.add(fieldMap2);
        Map<String, Object> fieldMap3 = new HashMap<String, Object>();
        fieldMap3.put("hId", 16);
        fieldMap3.put("value", "1");
        // TODO
        // fieldMap3.put("file", "");
        fieldsList.add(fieldMap3);
        map1.put("attr", fieldsList);
        examHeadingList.add(map1);

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("qEvidence", "图片2.jpg");
        map2.put("questionId", "56");
        // map2.put("tAnswer","");
        map2.put("tValue", "1");
        List<Map<String, Object>> fieldsList2 = new ArrayList<Map<String, Object>>();
        Map<String, Object> fieldMap4 = new HashMap<String, Object>();
        fieldMap4.put("hId", 13);
        fieldMap4.put("value", "描述问题");
        fieldsList2.add(fieldMap4);
        Map<String, Object> fieldMap5 = new HashMap<String, Object>();
        fieldMap5.put("hId", 15);
        fieldMap5.put("value", "1");
        // TODO
        // fieldMap2.put("file", "");
        fieldsList2.add(fieldMap5);
        Map<String, Object> fieldMap6 = new HashMap<String, Object>();
        fieldMap6.put("hId", 16);
        fieldMap6.put("value", "1");
        // TODO
        // fieldMap3.put("file", "");
        fieldsList2.add(fieldMap6);
        map2.put("attr", fieldsList2);
        examHeadingList.add(map2);
        return examHeadingList;
    }

    public static void securi_saveExamInfo() throws Exception {
        List<Map<String, Object>> examHeadingList = getExamHeadingList();
         List<Map<String, Object>> problemAnalysisList = getProblemAnalysisList();
        List<Map<String, Object>> actionPlanList = getActionPlanList();
        List<Map<String, Object>> questionList = getQuestionList();

        Map<String, Object> form = new HashMap<String, Object>();
        form.put("questionList", questionList);
        // 问卷台头
        form.put("examHeading", examHeadingList);
        // 问题分析
         form.put("problemAnalysisList", problemAnalysisList);
        // 行动计划
         form.put("actionPlanList", actionPlanList);
        form.put("examId", 25);
        form.put("total", 90);
        form.put("userId", 254);
        form.put("storeId", 118);
        form.put("beginTime", "1277106667");
        form.put("endTime", "");

       /* BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);*/
        url += "api/exam/securi_saveExamResult";
        // 调用接口
        String res = HttpClientUrl.doPost(url, form);
        System.out.println("res is =====================\n " + res);

    }

    public static void securi_findTestPaperDetail() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("userId", "393");//
        form.put("examId", "30");//
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/exam/securi_findTestPaperDetail?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_apkInfo() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        // url += "api/update/securi_apkInfo?sign=" + param;
        url += "api/update/securi_apkInfo";
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_getMessageCount() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("userId", 299);//
        // form.put("messageType", 1);//1:公告，2:紧急， 3:任务， 4:(考勤)异常信息
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/message/securi_getMessageCount?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }
    
    public static void securi_ocList() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("userId", 254);
        form.put("name", "");
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/facilityLogin/securi_list?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_getExamReferList() {
        url += "api/exam/securi_get";
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }
    
    public static void securi_storeAllList() {
        url += "api/store/securi_listAll";
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }
    public static void securi_getStoreForReport() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("shopId", "15309");
        form.put("year", "2014");
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/store/securi_getStsForReport?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }
    
    public static void securi_signInfo() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("userid", "393");
        form.put("isLatest", "1");
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/sign/securi_signInfo?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }
    public static void securi_modifyPwd() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("name", "yhe@bkchina.cn");
        form.put("pwd", "111111");
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/user/securi_modifyPwd?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }
    public static void securi_getLatestExams() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("storeId", "729");
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/exam/securi_getLatestExams?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }
    public static void securi_getwork() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("userId", "504");
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/work/securi_getwork?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }
    
    public static void securi_getUserList() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("userId", "297");
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/work/securi_getUserList?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_getStsForReport() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("shopId", "15425");
        form.put("year", "2010");
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/store/securi_getStsForReport?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }
    public static void securi_stsStore() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("userId", "509");
        form.put("quarter", "第四季度");
        form.put("year", "2014");
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/sts/securi_stsStore?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }
    public static void securi_getTaskDetail() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("userId", "393");
        form.put("workTypeId", "42");
        form.put("sord", "1");
        form.put("month", "11");
        form.put("quarter", "第四季度");
        form.put("year", "2014");
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/store/securi_getTaskDetail?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }

    public static void securi_queryStatisticInfo() throws Exception {
        Map<String, Object> form = new HashMap<String, Object>();
        form.put("userId", "538");
        BaseCode64 base64 = new BaseCode64();
        // 将参数加密
        String param = JSONObject.toJSONString(form);
        // 将字符串以java.net.URLencoder utf-8字符加密
        param = URLEncoder.encode(param, "UTF-8");
        // 通过base64加密
        param = base64.encode(param);
        url += "api/store/securi_queryStatisticInfo?sign=" + param;
        // 调用接口
        String res = HttpClientUrl.urlConnectionPost(url);
        System.out.println("res is =====================\n " + res);
    }


    public static void main(String[] args) throws Exception {
      // securi_getwork();
         //securi_verify();
        // securi_addMessage();
        // securi_getStore();
      //   securi_storeList();
         //();
        // securi_taskList();
        // securi_delTask();
         //securi_update();
        // securi_detail();
        // securi_messageList();
       //  securi_sts();
        // securi_beanconGet();
        // securi_queryExamInfo();
       //  securi_findExamInfo();
        // securi_findTestPaperDetail();
        // securi_getMessageCount();
        // securi_saveExamInfo();
        // securi_findTestPaperDetail();
      //   securi_apkInfo();
        // securi_ocList();
    //     securi_taskCaList();
        //securi_getExamReferList();
     //  securi_storeAllList();
      //  securi_updateHead1();
        //修改密码
        //securi_modifyPwd();
        //securi_addtask();
        //securi_getStoreForReport();
      //  securi_update();
    //    securi_signInfo();
       // securi_getLatestExams();
        //securi_getUserList();
      //  securi_getStsForReport();
        
      // securi_stsStore();
    //	securi_storeAllList();
       // securi_getTaskDetail();
    	/*DecimalFormat df=new DecimalFormat(".##");
    	double d=1252.2563;
    	String st=df.format(d);
    	System.out.println(DataType.getAsDouble(st));*/
//    	securi_updateHead_likai();
//    	securi_signOut();
        securi_queryStatisticInfo();
    //	securi_shopStatistic_list();
    }

}
