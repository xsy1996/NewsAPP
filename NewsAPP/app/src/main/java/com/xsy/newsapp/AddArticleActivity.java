package com.xsy.newsapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;


public class AddArticleActivity extends AppCompatActivity {


  Button submit;
  EditText title,subtitle,text;
  TextView add_label;
  String userName = "未登录";
  int userID = -1,authority=1;
  MyApplication application;
  ArrayList<String> label_pool = new ArrayList<String>();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_article);

    application = (MyApplication)this.getApplicationContext();
    Bundle bundle = getIntent().getExtras();
    if(bundle != null) {

      userID = bundle.getInt("userID");
      userName = bundle.getString("userName");
      authority =  bundle.getInt("authority");
      Log.i("my",userID+"|"+userName);
    }

    submit = (Button)findViewById(R.id.submit_article_btn);
    title = (EditText) findViewById(R.id.input_title);
    subtitle = (EditText) findViewById(R.id.input_subtitle);
    text = (EditText) findViewById(R.id.input_article_text);
    add_label = (TextView)findViewById(R.id.article_add_label_btn);
    add_label.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View arg0) {
        Bundle bundle = new Bundle();
        bundle.putInt("typ", 1);
        bundle.putInt("userID", userID);
        bundle.putString ("userName", userName);
        bundle.putInt("authority",authority);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(AddArticleActivity.this, AddLabelActivity.class);
        startActivityForResult(intent,0);//此处的requestCode应与下面结果处理函中调用的requestCode一致

      }
    });

    submit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        if (title.getText().toString().isEmpty()||subtitle.getText().toString().isEmpty()||text.getText().toString().isEmpty()) {
          Toast.makeText(view.getContext(), "标题，副标题和文本内容不能为空", Toast.LENGTH_LONG).show();
        }
        else {
          String ntitle = title.getText().toString();
          String nsubtitle = subtitle.getText().toString();
          String ntext = text.getText().toString();
          Log.i("my",ntitle+"|"+ntext);
          AsyncHttpClient client = new AsyncHttpClient();
          final String url = application.getIPAdd()  + "addarticle";
          JSONObject jsonObject = new JSONObject();
          String labels = "";
          for(int i=0;i<label_pool.size();i++)
            labels = labels+"|"+label_pool.get(i);
          System.out.print("labels = ["+labels+"]");
          try {
            jsonObject.put("title", ntitle);
            jsonObject.put("subtitle", nsubtitle);
            jsonObject.put("text", ntext);
            jsonObject.put("reporterid", userID);
            jsonObject.put("labels",labels);
          } catch (JSONException e) {
            e.printStackTrace();
          }
          // Toast.makeText(view.getContext(), password.getText().toString(), Toast.LENGTH_LONG).show();

          ByteArrayEntity entity = null;
          StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

          client.post(AddArticleActivity.this, url, stringEntity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
              // TODO Auto-generated method stub
              super.onSuccess(statusCode, headers, response);
              String msg="fail";
              int  code = -1;
              try {
                msg=response.getString("msg");
                code = response.getInt("code");
              } catch (JSONException e) {
                e.printStackTrace();
              }
              if(code==3)
              {
                Toast.makeText(AddArticleActivity.this, "标题，副标题和文本输入格式有误", Toast.LENGTH_LONG).show();
              }else if(code ==2){
                Toast.makeText(AddArticleActivity.this, "标题已存在", Toast.LENGTH_LONG).show();
              }else if(code ==1){
                Toast.makeText(AddArticleActivity.this, "数据库错误", Toast.LENGTH_LONG).show();
              }else{
                Bundle bundle = new Bundle();
                bundle.putInt("userID", userID);
                bundle.putString("userName",userName);
                bundle.putInt("authority",authority);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(AddArticleActivity.this, MainActivity.class);
                startActivity(intent);
              }
                         /*   if (statusCode == 200) {
                                //遍历json数组
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        // 获取具体的一个JSONObject对象
                                        JSONObject obj = response.getJSONObject(i);
                                        //JSONObject对象get(“属性名”)，getString(“属性名”),getInt(“属性名”)等方法来获取指定属性名的值

                                        Log.i("my",obj.getString("msg"));
                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }*/

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
              super.onFailure(statusCode, headers, throwable, errorResponse);
              Log.i("my","receive sth3");
            }
          });

          Log.i("my","request over");
        }
      }
    });

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode==0 && resultCode==RESULT_OK){
      Bundle bundle = data.getExtras();
      if(bundle!=null)
        label_pool = bundle.getStringArrayList("label_pool");
      Log.i("my",label_pool.get(0));
    }
  }

}
