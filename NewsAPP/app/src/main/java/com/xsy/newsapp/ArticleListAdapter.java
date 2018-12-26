package com.xsy.newsapp;

/**
 * Created by xsy on 2018/1/15.
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class ArticleListAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    int myposi;

    public ArticleListAdapter(Context context, List<Map<String, Object>> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }

    public final class Group{
        public TextView title;
        public TextView subtitle;
        public TextView reporter;
        public TextView hits;
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
            convertView=layoutInflater.inflate(R.layout.article_list_item, null);
            group.title=(TextView)convertView.findViewById(R.id.article_list_title);
            group.subtitle=(TextView)convertView.findViewById(R.id.article_list_subtitle);
            group.reporter=(TextView)convertView.findViewById(R.id.article_list_reporter);
            group.hits=(TextView)convertView.findViewById(R.id.article_list_hits);
            group.body = (LinearLayout) convertView.findViewById(R.id.article_item);
            convertView.setTag(group);
        }else{
            group=(Group) convertView.getTag();
        }
        //绑定数据
        group.subtitle.setText((String)data.get(position).get("subtitle"));
        group.title.setText((String)data.get(position).get("title"));
        group.reporter.setText((String)data.get(position).get("reporter"));
        group.body.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            Bundle bundle = new Bundle();
                            bundle.putString("userName",(String)data.get(position).get("userName"));
                            bundle.putInt("userID",(int)data.get(position).get("userID"));
                            bundle.putInt("newsID", (int)data.get(position).get("id"));
                            bundle.putInt("authority", (int)data.get(position).get("authority"));
                            bundle.putInt("pass", (int)data.get(position).get("Pass"));
                            Intent intent = new Intent();
                            intent.putExtras(bundle);
                            intent.setClass(context, ArticlePageActivity.class);
                            context.startActivity(intent);

                            Log.i("my","click "+(int)data.get(position).get("id"));
                        }
                    });
        return convertView;
    }

}