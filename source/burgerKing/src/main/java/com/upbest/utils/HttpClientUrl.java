package com.upbest.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

public class HttpClientUrl {

    public static String urlConnectionPost(String urls) {
        StringBuilder responseBuilder = null;
        BufferedReader reader = null;
        OutputStreamWriter wr = null;

        URL url;
        try {
            System.out.println(urls);
            url = new URL(urls);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(1000 * 10);
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write("");
            wr.flush();

            // Get the response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            responseBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            wr.close();
            reader.close();
            return responseBuilder.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";

    }

    public static String doPost(String urlString, Map<String, Object> map) throws Exception {
        System.out.println(urlString);
        HttpURLConnection httpConn = null;
        try {
            StringBuffer paramData = getQueryString(map);
            // String urlString = "http://localhost:8080/myweb/login.do";

            URL url = new URL(urlString);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            /* String cookieString = getCookieString(request);
             if (cookieString.length() > 0) // 往远程URL传递Cookie值
                 httpConn.setRequestProperty("Cookie", cookieString);*/
            OutputStream os = httpConn.getOutputStream();
            os.write(paramData.toString().getBytes()); // 往远程URL传递参数
            os.flush();
            os.close();

            int code = httpConn.getResponseCode();
            if (code == 200) { // 返回成功
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "utf-8"));
                String line;
                StringBuffer buffer = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }
                return buffer.toString();
            } else { // 访问失败
                // forward error page
                throw new Exception("Error occur when try to visit the url:" + url.getPath() + " using HttpURLConnection");
            }
        } catch (Exception ex) {
            throw new Exception("Error occur execute " + "HttpRemoteProxy.performImpl(), the caused by " + ex);
        } finally {
            if (httpConn != null)
                httpConn.disconnect();

        }
    }

    /*
     * 得到request所有的请求参数，并连接起来
     */
    private static StringBuffer getQueryString(Map<String, Object> map) throws Exception {
        StringBuffer params = new StringBuffer();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry element = (Map.Entry) it.next();
            params.append(element.getKey());
            params.append("=");
            params.append(element.getValue());
            params.append("&");
        }
        if (params.length() > 0) {
            params.deleteCharAt(params.length() - 1);
        }
        return params;
    }
    /*  private static String getCookieString(HttpServletRequest request) throws Exception {
          Cookie[] cookies = request.getCookies();
          StringBuffer buffer = new StringBuffer();
          for (int i = 0; i < cookies.length; i++) {
              Cookie cookie = cookies[i];
              String value = cookie.getValue();
              String name = cookie.getName();
              if (value != null && !value.equals(""))
                  buffer.append(name).append("=").append(value).append(";");
          }
          if (buffer.length() > 0)
              buffer.deleteCharAt(buffer.length() - 1);//delete the last char ';'
          return buffer.toString();
      }
    */
}
