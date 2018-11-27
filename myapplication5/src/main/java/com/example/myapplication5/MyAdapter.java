package com.example.myapplication5;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private List<List<String>> mList2;

    public MyAdapter(Context mContext, List<List<String>> mList2) {
        this.mContext = mContext;
        this.mList2 = mList2;
    }

    @Override
    public int getCount() {
        return mList2.size();
    }

    @Override
    public Object getItem(int position) {
        return mList2.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder viewholder=null;
         if (convertView==null){
             viewholder=new ViewHolder();
             convertView = View.inflate(mContext, R.layout.geshi, null);
             viewholder.textviewhol = convertView.findViewById(R.id.TextViewGeshi);
             convertView.setTag(viewholder);
         }else {
            viewholder= (ViewHolder) convertView.getTag();
         }
         viewholder.textviewhol.setText(mList2.get(position).toString());
        return null;
    }
    class ViewHolder{
        private  TextView textviewhol;
    }
}
