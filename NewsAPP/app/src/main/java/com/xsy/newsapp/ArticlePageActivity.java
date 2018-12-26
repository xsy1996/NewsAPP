package com.xsy.newsapp;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;



public class ArticlePageActivity extends AppCompatActivity {

    TextView title,text,love_btn,pass_btn,nopass_btn,reporter, rating_value;
    RatingBar rating_bar;
    String userName = "未登录";
    int userID = -1,nlove,authority=1;
    int newsID,reporterID,npass;
    String ntitle,nsubtitle,ntext,nreporter;
    MyApplication application;
    float nrating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_page);

        application = (MyApplication)this.getApplicationContext();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {

            userID = bundle.getInt("userID");
            userName = bundle.getString("userName");
            newsID = bundle.getInt("newsID");
            authority = bundle.getInt("authority");
            npass = bundle.getInt("pass");

        }

        Log.i("my",""+newsID);


        title =(TextView) findViewById(R.id.news_title);
      //  title.setText("清朝时，为何出嫁的格格大多不能生育？");
        reporter=(TextView) findViewById(R.id.reporter_name);
    //    reporter.setText("悟空问答");


        text =(TextView) findViewById(R.id.news_text);
//        text.setText("经常看到一些有关清朝的电视剧，格格们都是貌美如花，心高气盛，可以自己选自己的驸马，过着恩恩爱爱的生活，那么真实的历史又是如何呢？格格们过着怎么样的生活呢？\n\n" +
//                "清朝有一个奇怪的现象：清代的格格极少有生儿育女者，并且十之有九得了相思病而死亡，因为她们平日很难见到自己的驸马一面，即便在晚上也很难与驸马同床共枕，随意行房。那么，为什么会出现这样有悖天伦常理的现象呢?\n\n"+
//                "每当格格出嫁，都由皇帝赐给专门府第，驸马只能住在府第外舍。如果格格不召幸的话，驸马是不能随便进府与公主同床共枕的。但格格每召幸一次驸马需要花费很大的周折，要花许多银钱贿赂管家婆奶妈，才能如愿以偿。如果格格不贿赂奶妈，即使格格宣召，奶妈必寻找借口多方阻拦，甚至责以耻笑。\n\n"+
//                "但有道光的大格格得到了夫妻自由团圆、随意行房的权利。据说大格格刚刚结婚的时候，妈妈也是多方阻拦，以致一年多时间大格格不能与驸马相见。一天，大格格进宫拜见皇阿玛道光皇帝，含着泪水跪在父皇面前说：“父皇究竟将臣女嫁给哪个人了?”道光惊诧地问道：“难道符珍不是你的夫婿吗?”大格格说:“符珍是什么样子，臣女已嫁给他一年了，还从未见过一面。”道光皇帝问道：“为何不能见面?”大格格回答说：“奶妈不让臣女与符珍见面。”道光皇帝气愤地说：“岂有此理?你们夫妻间的事，奶妈怎么能管呢，你可以自己做主嘛!\n\n"+
//                "大格格得了父皇的这句圣旨如获至宝，回到府中立即将奶妈训斥一顿，遂自己做主随时召见驸马符珍。日后夫妻感情甚笃，先后生子女八人。也成了格格中生育最多的一个。\n\n"+
//                "经常看到一些有关清朝的电视剧，格格们都是貌美如花，心高气盛，可以自己选自己的驸马，过着恩恩爱爱的生活，那么真实的历史又是如何呢？格格们过着怎么样的生活呢？\n\n" +
//                "清朝有一个奇怪的现象：清代的格格极少有生儿育女者，并且十之有九得了相思病而死亡，因为她们平日很难见到自己的驸马一面，即便在晚上也很难与驸马同床共枕，随意行房。那么，为什么会出现这样有悖天伦常理的现象呢?\n\n"+
//                "每当格格出嫁，都由皇帝赐给专门府第，驸马只能住在府第外舍。如果格格不召幸的话，驸马是不能随便进府与公主同床共枕的。但格格每召幸一次驸马需要花费很大的周折，要花许多银钱贿赂管家婆奶妈，才能如愿以偿。如果格格不贿赂奶妈，即使格格宣召，奶妈必寻找借口多方阻拦，甚至责以耻笑。\n\n"+
//                "但有道光的大格格得到了夫妻自由团圆、随意行房的权利。据说大格格刚刚结婚的时候，妈妈也是多方阻拦，以致一年多时间大格格不能与驸马相见。一天，大格格进宫拜见皇阿玛道光皇帝，含着泪水跪在父皇面前说：“父皇究竟将臣女嫁给哪个人了?”道光惊诧地问道：“难道符珍不是你的夫婿吗?”大格格说:“符珍是什么样子，臣女已嫁给他一年了，还从未见过一面。”道光皇帝问道：“为何不能见面?”大格格回答说：“奶妈不让臣女与符珍见面。”道光皇帝气愤地说：“岂有此理?你们夫妻间的事，奶妈怎么能管呢，你可以自己做主嘛!\n\n"+
//                "大格格得了父皇的这句圣旨如获至宝，回到府中立即将奶妈训斥一顿，遂自己做主随时召见驸马符珍。日后夫妻感情甚笃，先后生子女八人。也成了格格中生育最多的一个。"
//        );

        rating_bar = (RatingBar) findViewById(R.id.ratingBar);
        rating_bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(userID>=0) {
                    AsyncHttpClient client = new AsyncHttpClient();
                    final String url = application.getIPAdd() + "articlepage";
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("userid", userID);
                        jsonObject.put("id", newsID);
                        jsonObject.put("kind", "rating_news");
                        jsonObject.put("rating", rating);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(view.getContext(), password.getText().toString(), Toast.LENGTH_LONG).show();

                    ByteArrayEntity entity = null;
                    StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

                    client.post(ArticlePageActivity.this, url, stringEntity, "application/json", new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            // TODO Auto-generated method stub
                            super.onSuccess(statusCode, headers, response);
                            String msg = "fail";
                            int code = -1;
                            try {
                                msg = response.getString("msg");
                                code = response.getInt("code");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Log.i("my", msg);

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Log.i("my", "rating fail");
                        }
                    });
                }
            }
        });
        love_btn =(TextView) findViewById(R.id.love_btn);
        love_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(userID>0){
                    nlove=(nlove+1)%2;
                    if(nlove == 0)
                        love_btn.setBackgroundResource(R.mipmap.love_empty);
                    else if(nlove == 1)
                        love_btn.setBackgroundResource(R.mipmap.love);
                    AsyncHttpClient client = new AsyncHttpClient();
                    final String url = application.getIPAdd()+"articlepage";
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("userid",userID);
                        jsonObject.put("id", newsID);
                        jsonObject.put("kind", "love_news");
                        jsonObject.put("love", nlove);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(view.getContext(), password.getText().toString(), Toast.LENGTH_LONG).show();

                    ByteArrayEntity entity = null;
                    StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

                    client.post(ArticlePageActivity.this, url, stringEntity, "application/json", new JsonHttpResponseHandler() {
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

                            Log.i("my",msg);

                        }
                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Log.i("my","love fail");
                        }
                    });
                }
            }
        });
        pass_btn =(TextView) findViewById(R.id.pass_check_btn);
        if(authority == 0 && npass == 0) {
            pass_btn.setVisibility(View.VISIBLE);
            pass_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    AsyncHttpClient client = new AsyncHttpClient();
                    final String url = application.getIPAdd()+"articlepage";
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("userid",userID);
                        jsonObject.put("id", newsID);
                        jsonObject.put("kind", "review_pass");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(view.getContext(), password.getText().toString(), Toast.LENGTH_LONG).show();

                    ByteArrayEntity entity = null;
                    StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

                    client.post(ArticlePageActivity.this, url, stringEntity, "application/json", new JsonHttpResponseHandler() {
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

                            Log.i("my",msg);

                        }
                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Log.i("my","love fail");
                        }
                    });
                }
            });
        }
        nopass_btn =(TextView) findViewById(R.id.no_pass_btn);
        if(authority == 0 && npass == 0) {
            nopass_btn.setVisibility(View.VISIBLE);
            nopass_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    AsyncHttpClient client = new AsyncHttpClient();
                    final String url = application.getIPAdd()+"articlepage";
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("userid",userID);
                        jsonObject.put("id", newsID);
                        jsonObject.put("kind", "review_nopass");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(view.getContext(), password.getText().toString(), Toast.LENGTH_LONG).show();

                    ByteArrayEntity entity = null;
                    StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

                    client.post(ArticlePageActivity.this, url, stringEntity, "application/json", new JsonHttpResponseHandler() {
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

                            Log.i("my",msg);

                        }
                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Log.i("my","love fail");
                        }
                    });
                }
            });
        }


        AsyncHttpClient client = new AsyncHttpClient();
        final String url = application.getIPAdd()+"articlepage";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid",userID);
            jsonObject.put("id", newsID);
            jsonObject.put("kind", "get_news");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Toast.makeText(view.getContext(), password.getText().toString(), Toast.LENGTH_LONG).show();

        ByteArrayEntity entity = null;
        StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

        client.post(ArticlePageActivity.this, url, stringEntity, "application/json", new JsonHttpResponseHandler() {
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

                Log.i("my",msg);
                if(code==3)
                {
                    Toast.makeText(ArticlePageActivity.this, "标题，副标题和文本输入格式有误", Toast.LENGTH_LONG).show();
                }else if(code ==1){
                    Toast.makeText(ArticlePageActivity.this, "数据库错误", Toast.LENGTH_LONG).show();
                }else{
                    try {
                        ntitle = response.getString("title");
                        nreporter = response.getString("reporter");
                        nsubtitle = response.getString("subtitle");
                        ntext = response.getString("text");
                        reporterID = response.getInt("reporterid");
                        nrating = (float)response.getDouble("rating");
                        nlove = response.getInt("love");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("my",ntitle);
                    title.setText(ntitle);
                    text.setText(ntext);
                    reporter.setText(nreporter);
                    reporter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
            //                Intent intent = new Intent();
            //                intent.setClass(ArticlePageActivity.this, MainActivity.class);
            //                startActivity(intent);

                        }
                    });
                    if(nrating<0)
                        rating_bar.setRating(3);
                    else
                        rating_bar.setRating(nrating);

                    if(nlove == 0)
                        love_btn.setBackgroundResource(R.mipmap.love_empty);
                    else if(nlove == 1)
                        love_btn.setBackgroundResource(R.mipmap.love);
                    reporter.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            Bundle bundle = new Bundle();
                            bundle.putString("userName",userName);
                            bundle.putInt("userID",userID);
                            bundle.putInt("viewID",reporterID);
                            bundle.putInt("authority", authority);
                            bundle.putInt("typ",1);
                            Intent intent = new Intent();
                            intent.putExtras(bundle);
                            intent.setClass(ArticlePageActivity.this, UserPageActivity.class);
                            startActivity(intent);
                        }
                    });
                }


            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("my","view fail");
            }
        });

    }

}
