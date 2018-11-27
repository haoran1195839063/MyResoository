package com.example.myapplication5;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String lujing = "https://suggest.taobao.com/sug?code=utf-8&q=%E6%89%8B%E6%9C%BA";
    private Button mButtonGet;
    private ListView mListView;
    private List<List<String>> myList;
    private MyAdapter myAdapter1;
    private Handler handler2=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    myList.addAll((Collection<? extends List<String>>) msg.obj);
                    myAdapter1.notifyDataSetChanged();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        myAdapter1 = new MyAdapter(MainActivity.this, myList);
        mListView.setAdapter(myAdapter1);

    }

    private void initView() {
        mButtonGet = (Button) findViewById(R.id.mButtonGet);
        mListView = (ListView) findViewById(R.id.mListView);
        mButtonGet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mButtonGet:
                getData1();
                break;
        }
    }

    private void getData1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String gets = Myget.gets(lujing);
                    Log.d("asdfasdfas++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++",gets);
                    Gson gson = new Gson();
                    MyBean myBean = gson.fromJson(gets, MyBean.class);
                    handler2.sendMessage(handler2.obtainMessage(1,myBean.getResult()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
