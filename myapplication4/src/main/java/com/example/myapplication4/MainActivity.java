package com.example.myapplication4;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String mUrl = "https://suggest.taobao.com/sug?code=utf-8&q=%E6%89%8B%E6%9C%BA";
    private Button Send_Btn;
    private ListView Main_Lv;
    private MyAdapter myAdapter;
    private List<List<String>> li=new ArrayList<>();
    //handlder
    private Handler handler11=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    li.addAll((Collection<? extends List<String>>) msg.obj);
                    myAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //实例化并添加adapter
        myAdapter = new MyAdapter(MainActivity.this,li);
        Main_Lv.setAdapter(myAdapter);
    }

    private void initView() {
        Send_Btn = (Button) findViewById(R.id.Send_Btn);
        Main_Lv = (ListView) findViewById(R.id.Main_Lv);
        Send_Btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Send_Btn:
                getData();
                break;
        }
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = HttpUtils.get(mUrl);
                    Gson gson = new Gson();
                    MyBean myBean = gson.fromJson(s,MyBean.class);
                    handler11.sendMessage(handler11.obtainMessage(1,myBean.getResult()));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
