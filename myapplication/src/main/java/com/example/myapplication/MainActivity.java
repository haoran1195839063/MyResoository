package com.example.myapplication;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String mUrl = "http://api.expoon.com/AppNews/getNewsList/type/1/p/1";
    private String mImageUrl = "http://img.my.csdn.net/uploads/201407/26/1406383265_8550.jpg";
    private String mPostUrl = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm";
    private Button mSendBtn, mSendImage, mPostBtn;
    private TextView mGetText;
    private ImageView mImage;
    //handler
    @SuppressLint("HandlerLeak")
    private Handler handler1=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    String s = msg.obj.toString();
                    mGetText.setText(s);

                    break;
                case 2:
                   Bitmap bitmap= (Bitmap) msg.obj;
                   mImage.setImageBitmap(bitmap);
                   break;
                case 3:
                    String s1 = msg.obj.toString();
                    mGetText.setText(s1);

                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSendBtn = findViewById(R.id.Send_Btn);
        mSendBtn.setOnClickListener(this);
        mGetText = findViewById(R.id.Get_Text);
        mSendImage = findViewById(R.id.Send_Image_Btn);
        mSendImage.setOnClickListener(this);
        mImage = findViewById(R.id.Get_Image);
        mPostBtn = findViewById(R.id.Send_Post_Btn);
        mPostBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Send_Btn:
                getNetData();
                break;
            case R.id.Send_Image_Btn:
                getImageData();

                break;
            case R.id.Send_Post_Btn:
                getPostData();

                break;
        }
    }

    private void getNetData() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(mUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(5000);
                    InputStream inputStream = urlConnection.getInputStream();
                    String inputStr = getInputStr(inputStream);
                    Message message = new Message();
                    message.what=1;
                    message.obj=inputStr;
                    handler1.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    private void getImageData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(mImageUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    if (urlConnection.getResponseCode()==200){
                        InputStream inputStream = urlConnection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Message message = new Message();
                        message.what=2;
                        message.obj=bitmap;
                        handler1.sendMessage(message);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    private void getPostData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(mPostUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");

                    urlConnection.setDoInput(true);
                    OutputStream outputStream = urlConnection.getOutputStream();
                    String canshu="15612973358";
                    outputStream.write(canshu.getBytes());
                    if (urlConnection.getResponseCode()==200){
                        InputStream inputStream = urlConnection.getInputStream();
                        String inputStr = getInputStr(inputStream);
                        Message message = new Message();
                        message.what=3;
                        message.obj=inputStr;
                        handler1.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String getInputStr(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String strr = null;
        while ((strr = bufferedReader.readLine()) != null) {
            stringBuffer.append(strr);
        }
        return stringBuffer.toString();
    }
}
