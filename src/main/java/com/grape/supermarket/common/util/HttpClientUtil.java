package com.grape.supermarket.common.util;


import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by LXP on 2017/3/6.
 * 用于进行http请求的工具类
 */

public class HttpClientUtil {
    private static final Logger logger = Logger.getLogger(HttpClientUtil.class);

    private static int sock_timeout = 10_000;
    private static int connect_timeout = 10_000;

//    private static String TAG = "HttpClientUtil";

    private static HttpURLConnection openConn(String url) throws MalformedURLException, IOException {
        URL urlOjb = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlOjb.openConnection();
        conn.setConnectTimeout(connect_timeout);
        conn.setReadTimeout(sock_timeout);
        return conn;
    }

    private static String mapToPostParam(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        Set<String> keySet = map.keySet();
        StringBuilder sb = new StringBuilder(64);
        for (String key : keySet) {
            if (sb.length() == 0) {
                sb.append(key).append("=").append(map.get(key));
            } else {
                sb.append('&').append(key).append("=").append(map.get(key));
            }
        }
        return sb.toString();
    }


    /**
     *  post请求数据
     */
    public static HttpResponce doPost(String url, Map<String, String> map, String charset) {
        return getHttpResponce(url, mapToPostParam(map), charset,"POST");
    }

    /**
     *  post请求数据
     */
    public static HttpResponce doPost(String url){
        return doPost(url,"","utf-8");
    }


    /**
     *  post请求数据
     */
    public static HttpResponce doPost(String url, String body, String charset) {
        return getHttpResponce(url, body, charset,"POST");
    }


    /**
     *  get请求数据
     */
    public static HttpResponce doGet(String url){
        return doGet(url,"","utf-8");
    }

    /**
     *  get请求数据
     */
    public static HttpResponce doGet(String url, String body, String charset) {
        return getHttpResponce(url, body, charset,"GET");
    }

    /**
     * GET请求数据
     */
    public static HttpResponce doGet(String url, Map<String, String> map, String charset) {
        return getHttpResponce(url, mapToPostParam(map), charset,"GET");
    }

    private static HttpResponce getHttpResponce(String url, String body, String charset,String method) {
        HttpResponce hr = new HttpResponce();
        StringBuilder sb = new StringBuilder(64);
        HttpURLConnection conn = null;
        BufferedReader br = null;
        try {
            conn = openConn(url);
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            if(body != null && !body.isEmpty()) {
                conn.setDoOutput(true);
            }
            conn.connect();
            if(body != null && !body.isEmpty()) {
                //write data
                try (OutputStream outputStream = conn.getOutputStream()) {
                    outputStream.write(body.getBytes(charset));
                    outputStream.flush();
                }
            }
            hr.setState(conn.getResponseCode());
            InputStream inputStream;
            if (hr.getState() == 200) {
                inputStream = conn.getInputStream();
            } else {
                inputStream = conn.getErrorStream();
            }
            //reader data
            br = new BufferedReader(new InputStreamReader(inputStream, charset));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("url地址不正确");
        } catch (IOException e) {
        }finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        hr.setBody(sb.toString());
        return hr;
    }

    public static HttpByteResponce doGetToByte(String url, Map<String, String> map){
        return requestToByte(url,map,false);
    }

    public static HttpByteResponce doPostToByte(String url, Map<String, String> map){
        return requestToByte(url,map,true);
    }

    public static HttpByteResponce requestToByte(String url, Map<String, String> map,boolean isPost){
        HttpByteResponce hbr = new HttpByteResponce();
        ArrayList<Byte> bList;

        HttpURLConnection conn = null;
        BufferedInputStream bi = null;

        try {
            String param = null;
            if(map != null) {
                param = mapToPostParam(map);
                if(!isPost){
                    url = url+"?"+param;
                }
            }

            conn = openConn(url);
            if(isPost) {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setDoInput(true);
            conn.connect();
            //write data
            if(isPost) {
                if (param != null) {
                    try (OutputStream outputStream = conn.getOutputStream()) {
                        outputStream.write(param.getBytes());
                        outputStream.flush();
                    }
                }
            }

            hbr.setState(conn.getResponseCode());
            logger.debug("url==>"+url+" responce state "+hbr.getState());
            if(conn.getResponseCode() == 200) {
                //reader data
                bi = new BufferedInputStream(conn.getInputStream());
                String line;
                bList = new ArrayList<>(bi.available());
                byte[] temp = new byte[16];
                int len;
                while ((len = bi.read(temp)) != -1) {
                    for(int i = 0;i<len;++i){
                        bList.add(temp[i]);
                    }
                }

                byte[] data = new byte[bList.size()];
                for (int i = 0,z=bList.size(); i < z; i++) {
                    data[i] = bList.get(i);
                }
                hbr.setBody(data);
            }
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }finally {
            if(bi != null){
                try {
                    bi.close();
                } catch (IOException e) {
                }
            }
        }

        return hbr;
    }
}
