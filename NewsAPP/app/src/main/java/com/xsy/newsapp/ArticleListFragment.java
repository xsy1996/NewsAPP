package com.xsy.newsapp;

/**
 * Created by xsy on 2018/1/15.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

public class ArticleListFragment extends Fragment {

    private int typ;
    private String label;
    private ListView listView;
    ArticleListAdapter adapter;
    private Context mContext;
    MyApplication application;

    List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();

    String userName = "未登录";
    int userID = -1,authority=1,pass;

    //typ == 0 表示从mainactivity标签选择来。typ=1表示从我的收藏来，typ=2表示myarticle来，typ=3表示userpage来,typ=4表示从关键字搜索来,typ = 5，审核
    //typ=0,label=标签,typ = 1,label=userid,typ=2,label = 审核过没过,typ=3,label = userid,typ=4,label = keyword
    public static ArticleListFragment newInstance(int typ, String label, int userID, String userName,int authority) {
        Bundle args = new Bundle();
        args.putInt("typ", typ);
        args.putString("label",label);
        args.putInt("userID",userID);
        args.putString("userName",userName);
        args.putInt("authority",authority);
        ArticleListFragment fragment = new ArticleListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        application = (MyApplication)this.getActivity().getApplicationContext();
        Bundle args = getArguments();
        if (args != null) {
            typ = args.getInt("typ");
            label = args.getString("label");
            userID = args.getInt("userID");
            userName = args.getString("userName");
            authority = args.getInt("authority");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.article_list_fragment, container, false);

        listView = view.findViewById(R.id.article_list);
       // getData();
        adapter = new ArticleListAdapter(getActivity(), list);
        listView.setAdapter(adapter);

        AsyncHttpClient client = new AsyncHttpClient();
        final String url = application.getIPAdd()+"articlelist";
        System.out.println("url");
        JSONObject jsonObject = new JSONObject();
        try {
            if(typ == 0) {
                jsonObject.put("label", label);

            }else if(typ == 1) {
                jsonObject.put("viewuserid", userID);

            }else if(typ == 2) {
                if(label.equals("已发表")){
                    jsonObject.put("pass", 1);
                }else if(label.equals("待审核")){
                    jsonObject.put("pass", 0);
                }else if(label.equals("未通过")){
                    jsonObject.put("pass", -1);
                }
                jsonObject.put("viewuserid", userID);
            }else if(typ == 3) {
                jsonObject.put("viewuserid", label);
            }else if(typ == 4) {
                jsonObject.put("keyword", label);
            }else if(typ == 5) {

            }
            jsonObject.put("typ", typ);
            jsonObject.put("userid", userID);
            Log.i("my",typ+" "+userID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Toast.makeText(view.getContext(), password.getText().toString(), Toast.LENGTH_LONG).show();

            StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

            client.post(mContext, url, stringEntity, "application/json", new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    // TODO Auto-generated method stub
                    super.onSuccess(statusCode, headers, response);
                    list = new ArrayList<Map<String, Object>>();
                    Log.i("my","arrayleagth = "+response.length());
                    for (int i = 1; i < response.length(); i++) {

                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Map<String, Object> map=new HashMap<String, Object>();
//                            Log.i("my",label+" | title = "+obj.getString("title"));
                            map.put("title",obj.getString("title"));
                            map.put("subtitle",obj.getString("subtitle"));
                            map.put("reporter",obj.getString("reporter"));
                            map.put("hits","0");
                            map.put("id",obj.getInt("id"));
                            map.put("userName",userName);
                            map.put("userID",userID);
                            map.put("authority",authority);
                            map.put("Pass",obj.getInt("pass"));
                            list.add(map);
                            Log.i("my","typ "+typ+" title "+obj.get("title"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
//                    listView.setOnItemClickListener(new OnItemClickListener(){
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view,
//                                                int position, long id) {
////                ListView listView = (ListView)parent;
////                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
////                String userid = map.get("userid");
////                String name = map.get("name");
////                String age = map.get("age");
////                Toast.makeText(SQLiteCRUDActivity.this, userid +" , "+ name +" , "+ age ,Toast.LENGTH_LONG).show();
////                Toast.makeText(mContext, "点击成功",
////                        Toast.LENGTH_SHORT).show();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("userName",userName);
//                            bundle.putInt("userID",userID);
//                            bundle.putString("article_id", (String)list.get(position).get("id"));
//                            Intent intent = new Intent();
//                            intent.putExtras(bundle);
//                            intent.setClass(mContext, ArticlePageActivity.class);
//                            startActivity(intent);
//
//                        }
//                    });
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Log.i("my","receive sth3");
                }
            });


            return view;
    }
//
//    public void getData() {
//        list=new ArrayList<Map<String,Object>>();
//        for(int i=0;i<10;i++){
//            Map<String, Object> map=new HashMap<String, Object>();
//            map.put("title","为何出嫁的格格大多未生育？"+"["+label+"]");
//            map.put("subtitle","在清朝时期，很多的格格并没有后代。这究竟是什么原因无人知晓，直到后来在史料记载中看到管家婆的存在。");
//            map.put("reporter","悟空问答");
//            map.put("hits","10342");
//            map.put("id","");
//            list.add(map);
//        }
//    }

}