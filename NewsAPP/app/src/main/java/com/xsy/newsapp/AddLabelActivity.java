package com.xsy.newsapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.donkingliang.labels.LabelsView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


public class AddLabelActivity extends AppCompatActivity {

  LabelsView chosen_label,other_label;
  TextView add_label,submit;
  String userName = "未登录";
  int userID = -1,authority=1;
  MyApplication application;
  EditText self_define;
  int typ,newsID;
  ArrayList<String> label_pool = new ArrayList<String>();
  ArrayList<String> pool1 = new ArrayList<String>();
  ArrayList<String> pool2 = new ArrayList<String>();
  Map<String,Integer> map;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_addlabel);

    application = (MyApplication)this.getApplicationContext();

    //typ = 0, 用户关注标签， typ = 1， 新建新闻设标签, typ=2，修改新闻标签
    Bundle bundle = getIntent().getExtras();
    if(bundle != null) {
      userID = bundle.getInt("userID");
      userName = bundle.getString("userName");
      typ = bundle.getInt("typ");
      newsID = bundle.getInt("newsID");
      authority = bundle.getInt("authority",authority);
      Log.i("my",userID+"|"+userName);
    }

    self_define = (EditText)findViewById(R.id.self_define_label);
    submit = (TextView) findViewById(R.id.submit_label);
    chosen_label = (LabelsView) findViewById(R.id.chosen_labels);
    other_label = (LabelsView) findViewById(R.id.other_label);
    add_label = (TextView) findViewById(R.id.submit_self_define);
    submit = (TextView) findViewById(R.id.submit_label);


    map = new HashMap<String, Integer>();

    AsyncHttpClient client = new AsyncHttpClient();
    final String url = application.getIPAdd()+"getlabel";
    System.out.println("url");
    JSONObject jsonObject = new JSONObject();
    if(typ==1) {
      try {

        jsonObject.put("typ", typ);
        jsonObject.put("userid", userID);
        jsonObject.put("newsid", -1);

      } catch (JSONException e) {
        e.printStackTrace();
      }
    }else if(typ==0){
      try {

        jsonObject.put("typ", typ);
        jsonObject.put("userid", userID);

      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    // Toast.makeText(view.getContext(), password.getText().toString(), Toast.LENGTH_LONG).show();

    StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");


    chosen_label.setLabels(pool1);
    other_label.setLabels(pool2);

    client.post(AddLabelActivity.this, url, stringEntity, "application/json", new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        // TODO Auto-generated method stub
        super.onSuccess(statusCode, headers, response);
        Log.i("my","arrayleagth = "+response.length());
        map = new HashMap<String, Integer>();
        for (int i = 0; i < response.length(); i++) {
          try {
            JSONObject obj = response.getJSONObject(i);
            String labeltext = obj.getString("text");
            map.put(obj.getString("text"),obj.getInt("chose"));
            Log.i("label",obj.getString("text")+"|"+obj.getInt("chose"));

          } catch (JSONException e) {
            e.printStackTrace();
          }
        }
        Iterator<Map.Entry<String,Integer>> it=map.entrySet().iterator();
        while(it.hasNext()){
          Map.Entry<String,Integer> entry=it.next();
          if(entry.getValue() == 0)
            pool2.add(entry.getKey());
          else if(entry.getValue() == 1)
            pool1.add(entry.getKey());
        }
        chosen_label.setLabels(pool1);
        other_label.setLabels(pool2);
      }
      @Override
      public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        Log.i("my","receive sth3");
      }
    });



    chosen_label.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
      @Override
      public void onLabelClick(View label, String labelText, int position) {
        Iterator<String> sListIterator = pool1.iterator();
        while(sListIterator.hasNext()){
          String e = sListIterator.next();
          if(e.equals(labelText)){
            sListIterator.remove();
          }
        }
        pool2.add(labelText);

        chosen_label.setLabels(pool1);
        other_label.setLabels(pool2);
      }
    });

    other_label.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
      @Override
      public void onLabelClick(View label, String labelText, int position) {
        Iterator<String> sListIterator = pool2.iterator();
        while(sListIterator.hasNext()){
          String e = sListIterator.next();
          if(e.equals(labelText)){
            sListIterator.remove();
          }
        }
        pool1.add(labelText);

        chosen_label.setLabels(pool1);
        other_label.setLabels(pool2);
      }
    });

    if(typ==1)
    {
      submit.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          label_pool = pool1;
          Intent intent =getIntent();
          //这里使用bundle绷带来传输数据
          Bundle bundle =new Bundle();
          //传输的内容仍然是键值对的形式
          bundle.putStringArrayList("label_pool",label_pool);
          intent.putExtras(bundle);
          setResult(RESULT_OK,intent);
          finish();

        }
      });

    }else {

//            ArrayList<String> label = new ArrayList<>();
//            label.add("娱乐");
//            label.add("体育");
//            label.add("财经");
//            label.add("军事");
//            label.add("政治");
//            chosen_label.setLabels(label);
//            label = new ArrayList<>();
//            label.add("游戏");
//            label.add("汽车");
//            label.add("明星");
//            label.add("影视");
//            label.add("宠物");
//            label.add("程序员");
//            label.add("建筑师");
//            label.add("摄影");
//            label.add("Cosplay");
//            label.add("阴阳师");
//            label.add("决战平安京");
//            other_label.setLabels(label);

//        chosen_label.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
//            @Override
//            public void onLabelClick(View label, String labelText, int position) {
//                //label是被点击的标签，labelText是标签的文字，position是标签的位置。
//            }
//        });
//
//        label_pool.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
//            @Override
//            public void onLabelClick(View label, String labelText, int position) {
//                //label是被点击的标签，labelText是标签的文字，position是标签的位置。
//            }
//        });
      add_label.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          String text = self_define.getText().toString();
          self_define.setText("");
          if(!map.containsKey(text)){
            map.put(text,1);
            pool1.add(text);
            chosen_label.setLabels(pool1);
          }
        }
      });

      submit.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          label_pool = pool1;


          AsyncHttpClient client = new AsyncHttpClient();
          final String url = application.getIPAdd()+"editlabel";
          System.out.println("url");
          JSONArray jsonArray = new JSONArray();
          JSONObject jsonObject = new JSONObject();

          for(int i=0;i<label_pool.size();i++) {
            try {
              jsonObject = new JSONObject();
              jsonObject.put("typ", typ);
              jsonObject.put("userid", userID);
              jsonObject.put("text", label_pool.get(i));
              jsonArray.put(jsonObject);
              Log.i("my",jsonObject.toString());
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }
          // Toast.makeText(view.getContext(), password.getText().toString(), Toast.LENGTH_LONG).show();

          StringEntity stringEntity = new StringEntity(jsonArray.toString(), "utf-8");


          chosen_label.setLabels(pool1);
          other_label.setLabels(pool2);

          client.post(AddLabelActivity.this, url, stringEntity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
              // TODO Auto-generated method stub
              super.onSuccess(statusCode, headers, response);



            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
              super.onFailure(statusCode, headers, throwable, errorResponse);
              Log.i("my","receive sth3");
            }
          });

          Bundle bundle = new Bundle();
          bundle.putInt("userID", userID);
          bundle.putInt("authority",authority);
          bundle.putString("userName",userName);
          Intent intent = new Intent();
          intent.putExtras(bundle);
          intent.setClass(AddLabelActivity.this, MainActivity.class);
          startActivity(intent);



        }
      });
    }
  }

}
