package com.example.myapplication4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    public static String get(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        //由一个有效的网址服务返回这个对象
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //设置请求方式
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        InputStream stream = connection.getInputStream();
        String inputStr = getInputStr(stream);
        return inputStr;
    }

    //把流转成字符串
    private static String getInputStr(InputStream stream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        StringBuffer sb = new StringBuffer();
        String str = null;
        while ((str = br.readLine()) != null) {
            sb.append(str);
        }
        return sb.toString();
    }
}
