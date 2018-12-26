package com.xsy.newsapp;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.content.Intent;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {


  private TabLayout mTabTl;
  private ViewPager mContentVp;
  RelativeLayout user_page,add_article;
  TextView add_label,review_news;

  private List<String> tabIndicators;
  private List<Fragment> tabFragments;
  private ContentPagerAdapter contentAdapter;
  CircleImageView head_pic;
  TextView mypage,mycollection,mynews,add_news,nickname;

  List<String> labellist;
  SearchView searchView;

  String userName = "未登录";
  int userID = -1,authority = 1;
  MyApplication application;



  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mTabTl = (TabLayout) findViewById(R.id.tabLayout);
    mContentVp = (ViewPager) findViewById(R.id.viewPager);
    application = (MyApplication)this.getApplicationContext();

    Bundle bundle = getIntent().getExtras();
    if(bundle != null) {
      userName = bundle.getString("userName");
      userID = bundle.getInt("userID");
      authority = bundle.getInt("authority");
      Log.i("my",userID+"|"+userName);

    }

    //    initContent();
    //   getlabels();
    initTab();


    Log.i("my",userID+"|"+userName+"|"+authority);

    add_label =(TextView) findViewById(R.id.add_label_btn);
    add_label.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View arg0) {
        if(userID!=-1) {
          Bundle bundle = new Bundle();
          bundle.putInt("typ", 0);
          bundle.putInt("userID", userID);
          bundle.putString("userName", userName);
          bundle.putInt("authority",authority);
          Intent intent = new Intent();
          intent.putExtras(bundle);
          intent.setClass(MainActivity.this, AddLabelActivity.class);
          startActivity(intent);
        }else{
          Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_LONG).show();

        }
      }
    });
//        add_article =(RelativeLayout) findViewById(R.id.add_article_btn);
//        add_article.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, AddArticleActivity.class);
//                startActivity(intent);
//            }
//        });
//        user_page =(RelativeLayout) findViewById(R.id.userpage_btn);
//        user_page.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });
    nickname = (TextView) findViewById(R.id.nickname);
    nickname.setText(userName);

    mypage = (TextView) findViewById(R.id.my_page);
    mypage.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if(userID!=-1) {
          Bundle bundle = new Bundle();
          bundle.putString("typ", "1");
          bundle.putInt("userID", userID);
          bundle.putInt("viewID", userID);
          bundle.putInt("authority",authority);
          bundle.putString ("userName", userName);
          Intent intent = new Intent();
          intent.putExtras(bundle);
          intent.setClass(MainActivity.this,UserPageActivity.class);
          startActivity(intent);
        }else{
          Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_LONG).show();

        }
      }
    });

    mynews = (TextView) findViewById(R.id.my_news);
    mynews.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if(userID!=-1) {
          Bundle bundle = new Bundle();
          bundle.putInt("userID", userID);
          bundle.putInt("typ",2);
          bundle.putInt("authority",authority);
          bundle.putString ("userName", userName);
          Intent intent = new Intent();
          intent.putExtras(bundle);
          intent.setClass(MainActivity.this,MyArticleActivity.class);
          startActivity(intent);
        }else{
          Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_LONG).show();

        }
      }
    });

    mycollection = (TextView) findViewById(R.id.my_collection);
    mycollection.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if(userID!=-1) {
          Log.i("my","collection");
          Bundle bundle = new Bundle();
          bundle.putInt("userID", userID);
          bundle.putString ("userName", userName);
          bundle.putInt("typ", 1);
          bundle.putInt("authority",authority);
          bundle.putString ("label", ""+userID);
          Intent intent = new Intent();
          intent.putExtras(bundle);
          intent.setClass(MainActivity.this,ArticleListActivity.class);
          startActivity(intent);
        }else{
          Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_LONG).show();

        }
      }
    });

    review_news = (TextView) findViewById(R.id.review_news);
    if(authority == 0){
      review_news.setVisibility(View.VISIBLE);
      review_news.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          if (userID != -1 && authority == 0) {
            Bundle bundle = new Bundle();
            bundle.putInt("userID", userID);
            bundle.putString("userName", userName);
            bundle.putInt("typ", 5);
            bundle.putInt("authority", authority);
            bundle.putString("label", "" + userID);
            Intent intent = new Intent();
            intent.putExtras(bundle);
            intent.setClass(MainActivity.this, ArticleListActivity.class);
            startActivity(intent);
          } else {
            Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_LONG).show();

          }
        }
      });
    }

    add_news =(TextView) findViewById(R.id.add_news);
    add_news.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View arg0) {
        if(userID!=-1) {
          Bundle bundle = new Bundle();
          bundle.putInt("userID", userID);
          bundle.putString("userName", userName);
          bundle.putInt("authority",authority);
          Intent intent = new Intent();
          intent.putExtras(bundle);
          intent.setClass(MainActivity.this, AddArticleActivity.class);
          startActivity(intent);
        }else{
          Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_LONG).show();
          Log.i("my",userID+"|"+userName);
        }
      }
    });

    head_pic = (CircleImageView)findViewById(R.id.profile_image);
    head_pic.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if(userID==-1) {
          Bundle bundle = new Bundle();
          bundle.putInt("userID", userID);
          bundle.putString ("userName", userName);
          bundle.putInt("authority",authority);
          Intent intent = new Intent();
          intent.putExtras(bundle);
          intent.setClass(MainActivity.this, LoginActivity.class);
          startActivity(intent);
        }else{
          //Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_LONG).show();

        }
      }
    });


    searchView = (SearchView)findViewById(R.id.search_view);

    searchView.setIconified(false);
    searchView.setOnCloseListener(new SearchView.OnCloseListener() {
      @Override
      public boolean onClose() {
        Toast.makeText(MainActivity.this, "Close", Toast.LENGTH_SHORT).show();
        return false;
      }
    });

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String s) {
        Bundle bundle = new Bundle();
        bundle.putInt("userID", userID);
        bundle.putString("userName", userName);
        bundle.putInt("typ", 4);
        bundle.putInt("authority", authority);
        bundle.putString("label", "" + s);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(MainActivity.this, ArticleListActivity.class);
        startActivity(intent);
        return false;
      }

      @Override
      public boolean onQueryTextChange(String s) {
        Log.e("CSDN_LQR", "TextChange --> " + s);
        return false;
      }
    });


  }

  private void initTab(){
    mTabTl.setTabMode(TabLayout.MODE_SCROLLABLE);
    mTabTl.setTabTextColors(ContextCompat.getColor(this,R.color.black), ContextCompat.getColor(this, R.color.colorAccent));
    mTabTl.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorAccent));
    ViewCompat.setElevation(mTabTl, 10);
    mTabTl.setupWithViewPager(mContentVp);
  }

  private void initContent(){
    tabIndicators = new ArrayList<>();
    for (int i = 0; i < labellist.size(); i++) {
      tabIndicators.add(labellist.get(i));
    }
    tabFragments = new ArrayList<>();
    for (String s : tabIndicators) {
      tabFragments.add(ArticleListFragment.newInstance(0,s,userID,userName,authority));
    }
    contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
    mContentVp.setAdapter(contentAdapter);
  }


  class ContentPagerAdapter extends FragmentPagerAdapter{

    public ContentPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      return tabFragments.get(position);
    }

    @Override
    public int getCount() {
      return tabIndicators.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return tabIndicators.get(position);
    }
  }




  List<String> getlabels()
  {

    labellist = new ArrayList();
    labellist.add("推荐");

    if(userID == -1) {
      labellist.add("社会");
      labellist.add("时尚");
      labellist.add("历史");
      labellist.add("财经");
      labellist.add("军事");
      labellist.add("教育");
      initContent();
    }else {

      AsyncHttpClient client = new AsyncHttpClient();
      final String url = application.getIPAdd() + "getlabel";
      System.out.println("url");
      JSONObject jsonObject = new JSONObject();
      try {
        jsonObject.put("typ", 0);
        jsonObject.put("userid", userID);

      } catch (JSONException e) {
        e.printStackTrace();
      }
      // Toast.makeText(view.getContext(), password.getText().toString(), Toast.LENGTH_LONG).show();

      StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

      client.post(MainActivity.this, url, stringEntity, "application/json", new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
          // TODO Auto-generated method stub
          super.onSuccess(statusCode, headers, response);
          Log.i("my", "arrayleagth = " + response.length());
          for (int i = 0; i < response.length(); i++) {
            try {
              JSONObject obj = response.getJSONObject(i);
              String labeltext = obj.getString("text");
              if (obj.getInt("chose") == 1)
                labellist.add(labeltext);
              Log.i("main label",obj.getString("text")+"|"+obj.getInt("chose"));
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }
          initContent();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
          super.onFailure(statusCode, headers, throwable, errorResponse);
          Log.i("my", "receive sth3");
        }
      });
    }

    return labellist;
  }


  @Override
  public void onResume() {
    super.onResume();  // Always call the superclass method first
    // Get the Camera instance as the activity achieves full user focus
    getlabels();

  }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        TextView label = (TextView) findViewById(R.id.label1);
//        label.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                }
//        });
//
//    }

}
