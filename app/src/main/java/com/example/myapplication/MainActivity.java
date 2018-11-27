package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1;
    private TextView textview1;
    private String lujing;
    @SuppressLint("HandlerLeak")
    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String s = msg.obj.toString();
                    textview1.setText(s);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button11);
        textview1 = findViewById(R.id.textview11);
        lujing = "http://api.expoon.com/AppNews/getNewsList/type/1/p/1";
        button1.setOnClickListener(this);

    }

    public void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(lujing);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.setRequestMethod("GET");
                    InputStream inputStream = urlConnection.getInputStream();
                    String inputData = getInputData(inputStream);
                    Message message = new Message();
                    message.what = 1;
                    message.obj = inputData;
                    handler1.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private String getInputData(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String strr = null;
        while ((strr=bufferedReader.readLine()) != null) {
            stringBuffer.append(strr);
        }
        return stringBuffer.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button11:
                getData();
                break;
        }
    }
}
