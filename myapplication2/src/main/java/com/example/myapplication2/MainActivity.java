package com.example.myapplication2;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button getData_butt;
    private Button getImage_butt;
    private Button getDataPost_butt;
    private TextView MyText;
    private ImageView tupian;
    private String mUrl = "http://api.expoon.com/AppNews/getNewsList/type/1/p/1";
    private String mImageUrl = "http://img.my.csdn.net/uploads/201407/26/1406383265_8550.jpg";
    private String mPostUrl = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm";
    @SuppressLint("HandlerLeak")
    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String s = msg.obj.toString();
                    MyText.setText(s);
                    break;
                case 2:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    tupian.setImageBitmap(bitmap);
                    break;
                case 3:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        getData_butt = (Button) findViewById(R.id.getData_butt);
        getImage_butt = (Button) findViewById(R.id.getImage_butt);
        getDataPost_butt = (Button) findViewById(R.id.getDataPost_butt);
        MyText = (TextView) findViewById(R.id.MyText);
        tupian = (ImageView) findViewById(R.id.tupian);

        getData_butt.setOnClickListener(this);
        getImage_butt.setOnClickListener(this);
        getDataPost_butt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getData_butt:
                getData();
                break;
            case R.id.getImage_butt:
                getImage();
                break;
            case R.id.getDataPost_butt:
                geDataPost();
                break;
        }
    }

    public void getData() {
        //在子线程中
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //路径
                    URL url = new URL(mUrl);
                    // //由一个有效的网址服务返回这个对象
                    HttpURLConnection MyConnectinon = (HttpURLConnection) url.openConnection();
                    //设置请求方式
                    MyConnectinon.setRequestMethod("GET");
                    MyConnectinon.setConnectTimeout(5000);
                    //获取输入流
                    InputStream inputStream = MyConnectinon.getInputStream();
                    //调用io流
                    String myStream = getMyStream(inputStream);

                    Message message = new Message();
                    message.what = 1;
                    message.obj = myStream;
                    handler2.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }).start();

    }

    public void getImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(mImageUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    if (urlConnection.getResponseCode() == 200) {
                        InputStream inputStream = urlConnection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Message message = new Message();
                        message.what = 2;
                        message.obj = bitmap;
                        handler2.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void geDataPost() {

    }


    //获取流的方法
    private String getMyStream(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String strr = null;
        while ((strr = bufferedReader.readLine()) != null) {
            stringBuffer.append(strr);
        }
        return stringBuffer.toString();
    }
}
