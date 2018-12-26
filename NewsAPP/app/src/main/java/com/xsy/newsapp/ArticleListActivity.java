package com.xsy.newsapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;


public class ArticleListActivity extends AppCompatActivity {

  ArticleListFragment fragment;
  String label;
  int typ;
  String userName = "未登录";
  int userID = -1,authority=1;
  MyApplication application;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_article_list);


    Bundle args = new Bundle();
    args = this.getIntent().getExtras();
    //typ == 0 表示从mainactivity标签选择来。typ=1表示从我的收藏来，typ=2表示myarticle来，typ=3表示userpage来,typ=4表示从关键字搜索来,typ = 5，审核
    //typ=0,label=标签,typ = 1,label=userid,typ=2,label = 审核过没过,typ=3,label = userid,typ=4,label = keyword
//    if (savedInstanceState != null) {
      typ = (int)args.get("typ");
      label = args.get("label").toString();
      userName = args.getString("userName");
      userID = args.getInt("userID");
      authority = args.getInt("authority");
      Log.i("my","articlelist  "+userID+"|"+userName);
//    }
    application = (MyApplication)this.getApplicationContext();

    fragment = ArticleListFragment.newInstance(typ,label,userID,userName,authority);
    getSupportFragmentManager().beginTransaction().replace(R.id.id_newslist_content, fragment).commit();


  }

}
