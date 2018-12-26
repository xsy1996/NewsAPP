package com.xsy.newsapp;



import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;


public class LoginActivity extends AppCompatActivity {


    private TabLayout mTabTl;
    private ViewPager mContentVp;

    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private LoginActivity.ContentPagerAdapter contentAdapter;

    Context mContext;

    String userName = "未登录";
    int userID = -1,authority=1;
    MyApplication application;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        application = (MyApplication)this.getApplicationContext();

        mContext = this.getApplicationContext();
        mTabTl = (TabLayout) findViewById(R.id.login_tabLayout);
        mContentVp = (ViewPager) findViewById(R.id.login_viewPager);

        initContent();
        initTab();

    }

    private void initTab(){
        mTabTl.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabTl.setTabTextColors(ContextCompat.getColor(this,R.color.black), ContextCompat.getColor(this, R.color.colorAccent));
        mTabTl.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorAccent));
        ViewCompat.setElevation(mTabTl, 15);
        mTabTl.setupWithViewPager(mContentVp);
    }

    private void initContent(){
        tabIndicators = new ArrayList<>();
        List<String> labels = getlabels();
        for (int i = 0; i < labels.size(); i++) {
            tabIndicators.add(labels.get(i));
        }
        tabFragments = new ArrayList<>();
        tabFragments.add(LoginFragment.newInstance(0,tabIndicators.get(0)));
        tabFragments.add(SignupFragment.newInstance(0,tabIndicators.get(1)));
        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mContentVp.setAdapter(contentAdapter);
    }


    class ContentPagerAdapter extends FragmentPagerAdapter {

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
        ArrayList<String> labels = new ArrayList();
        labels.add("登陆");
        labels.add("注册");
        return labels;
    }


}


