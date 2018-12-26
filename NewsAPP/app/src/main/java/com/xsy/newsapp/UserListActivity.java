package com.xsy.newsapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


public class UserListActivity extends AppCompatActivity {

    private ListView listView;
    String userName = "未登录";
    int userID = -1,authority=1;
    UserListAdapter adapter;
    String typ;
    MyApplication application;
    List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);


        application = (MyApplication)this.getApplicationContext();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {

            userID = bundle.getInt("userID");
            userName = bundle.getString("userName");
            authority = bundle.getInt("authority");
        }

        listView = findViewById(R.id.user_list);
        adapter = new UserListAdapter(this, list);
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                Bundle bundle = new Bundle();
//                bundle.putString("typ", "1");
//                bundle.putInt("userID", userID);
//                bundle.putInt("viewID", userID);
//                bundle.putInt("authority",authority);
//                bundle.putString ("userName", userName);
//                Intent intent = new Intent();
//                intent.putExtras(bundle);
//                intent.setClass(UserListActivity.this, UserPageActivity.class);
//                startActivity(intent);
//
//            }
//        });
        AsyncHttpClient client = new AsyncHttpClient();
        final String url = application.getIPAdd()+"userlist";
        System.out.println("url");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("typ", typ);
            jsonObject.put("id", userID);
            Log.i("my",""+userID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Toast.makeText(view.getContext(), password.getText().toString(), Toast.LENGTH_LONG).show();

        StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

        client.post(UserListActivity.this, url, stringEntity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // TODO Auto-generated method stub
                super.onSuccess(statusCode, headers, response);
                list = new ArrayList<Map<String, Object>>();
                Log.i("my","arrayleagth = "+response.length());
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Map<String, Object> map=new HashMap<String, Object>();
                        Log.i("my","nickname = "+obj.getString("nickname"));
                        map.put("viewuserid",obj.getString("id"));
                        map.put("nickname",obj.getString("nickname"));
                        map.put("signature",obj.getString("signature"));
                        map.put("userName",userName);
                        map.put("userID",userID);
                        map.put("authority",authority);
                        list.add(map);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.setData(list);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("my","receive sth3");
            }
        });
    }

//
//    public List<Map<String, Object>> getData() {
//        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
//
//        for(int i=0;i<10;i++){
//            Map<String, Object> map=new HashMap<String, Object>();
//            map.put("name","仰望星空派");
//            map.put("intro","这个人很懒，什么也没留下。");
//            list.add(map);
//        }
//        return list;
//    }


}
