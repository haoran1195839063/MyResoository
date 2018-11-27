package com.example.myapplication3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyAsyncActivity extends AppCompatActivity implements View.OnClickListener {
    private String mUrl = "http://api.expoon.com/AppNews/getNewsList/type/1/p/1";
    private Button buttonhuoqv;
    private TextView TextView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {
        buttonhuoqv = (Button) findViewById(R.id.buttonhuoqv);
        TextView1 = (TextView) findViewById(R.id.TextView1);

        buttonhuoqv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonhuoqv:
                new MyTask().execute(mUrl);
                break;
        }
    };

    class MyTask extends AsyncTask<String, Void, String> {

        //做后台操作的方法
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    InputStream inputStream = urlConnection.getInputStream();
                    String getstr = getstr(inputStream);
                    Gson gson = new Gson();
                    MyBean myBean = gson.fromJson(getstr, MyBean.class);


                    return myBean.getData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        //后台处理完操作返回的数据
        @Override
        protected void onPostExecute(String s) {

            TextView1.setText(s);
        }


    }

    private String getstr(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String strr = null;
        while ((strr = bufferedReader.readLine()) != null) {
            stringBuffer.append(strr);
        }
        return stringBuffer.toString();
    }

}
