package com.xsy.newsapp;

/**
 * Created by xsy on 2018/1/15.
 */


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


public class UserListAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public UserListAdapter(Context context, List<Map<String, Object>> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }

    public final class Group{
        public TextView name;
        public TextView intro;
        public LinearLayout body;
    }
    @Override

    public int getCount() {
        return data.size();
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Group group=null;
        if(convertView==null){
            group=new Group();
            convertView=layoutInflater.inflate(R.layout.user_list_item, null);
            group.name=(TextView)convertView.findViewById(R.id.user_list_name);
            group.intro=(TextView)convertView.findViewById(R.id.user_list_intro);
            group.body = (LinearLayout) convertView.findViewById(R.id.article_item);
            convertView.setTag(group);
        }else{
            group=(Group) convertView.getTag();
        }
        //绑定数据
        group.name.setText((String)data.get(position).get("nickname"));
        group.intro.setText((String)data.get(position).get("signature"));
        group.body.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle bundle = new Bundle();
                bundle.putString("userName",(String)data.get(position).get("userName"));
                bundle.putInt("userID",(int)data.get(position).get("userID"));
                bundle.putInt("viewID",(int)data.get(position).get("viewuserID"));
                bundle.putInt("authority", (int)data.get(position).get("authority"));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(context, UserPageActivity.class);
                context.startActivity(intent);

                Log.i("my","click "+(int)data.get(position).get("id"));
            }
        });

        return convertView;
    }

}