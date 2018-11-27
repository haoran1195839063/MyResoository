package com.example.myapplication5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Myget {
    public static String  gets(String lujing1) throws Exception {


                    URL url = new URL(lujing1);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(5000);
                    InputStream inputStream = urlConnection.getInputStream();
                    String inputStr = getInputStr(inputStream);
                    return  inputStr;

            }

            private static String getInputStr(InputStream inputStream) throws IOException {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer = new StringBuffer();
                String strr=null;
                while ((strr=bufferedReader.readLine())!=null){
                    stringBuffer.append(strr);
                }
                return stringBuffer.toString();
            }

    }

