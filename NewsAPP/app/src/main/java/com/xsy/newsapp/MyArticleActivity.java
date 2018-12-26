package com.xsy.newsapp;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class MyArticleActivity extends AppCompatActivity {


    private TabLayout mTabTl;
    private ViewPager mContentVp;

    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private MyArticleActivity.ContentPagerAdapter contentAdapter;


    String userName = "未登录";
    int userID = -1,typ,authority=1;
    MyApplication application;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myarticle);




        mTabTl = (TabLayout) findViewById(R.id.myarticle_tabLayout);
        mContentVp = (ViewPager) findViewById(R.id.myarticle_viewPager);

        application = (MyApplication)this.getApplicationContext();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            userName = bundle.getString("userName");
            userID = bundle.getInt("userID");
            typ = bundle.getInt("typ");
            authority = bundle.getInt("authority");
        }

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
        for (String s : tabIndicators) {
            //typ == 0 表示从mainactivity标签选择来。typ=1表示从我的收藏来，typ=2表示myarticle来，typ=3表示userpage来,typ=4表示从关键字搜索来,typ = 5，审核
            //typ=0,label=标签,typ = 1,label=userid,typ=2,label = 审核过没过,typ=3,label = userid,typ=4,label = keyword

            tabFragments.add(ArticleListFragment.newInstance(typ,s,userID,userName,authority));
        }
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
        if(typ==2) {
            labels.add("已发表");
            labels.add("待审核");
            labels.add("未通过");
        }
        return labels;
    }


}
