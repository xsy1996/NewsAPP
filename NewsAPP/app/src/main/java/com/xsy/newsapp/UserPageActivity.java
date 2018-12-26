package com.xsy.newsapp;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;


public class UserPageActivity extends AppCompatActivity {

    ArticleListFragment fragment;
    TextView follow,followed,followhim;
    TextView signature;
    EditText nickname;
    int editable=0;
    String userName = "未登录";
    int userID = -1,viewID,authority=1,nfollow,nfollownum,nfollowednum;
    int typ;
    MyApplication application;
    String nname,nsignature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);

        application = (MyApplication)this.getApplicationContext();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {

            userID = bundle.getInt("userID");
            userName = bundle.getString("userName");
            typ = bundle.getInt("typ");
            authority = bundle.getInt("authority");
            viewID = bundle.getInt("viewID");
            System.out.print("viewID = "+viewID);
        }

        fragment = ArticleListFragment.newInstance(3,""+viewID,userID,userName,authority);
        getSupportFragmentManager().beginTransaction().replace(R.id.newslist_content, fragment).commit();
        follow = (TextView)findViewById(R.id.follow);
//        follow.setText("关注:"+"210");
        followed = (TextView)findViewById(R.id.followed);
//        followed.setText("粉丝:"+"134");
        followhim = (TextView)findViewById(R.id.followhim);
        nickname = (EditText) findViewById(R.id.nickname);
        signature = (TextView)findViewById(R.id.self_info);
//        nickname.setText(userName);

        follow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("typ", "follow");
                bundle.putInt("userID", userID);
                bundle.putString ("userName", userName);
                bundle.putInt("authority",authority);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(UserPageActivity.this,UserListActivity.class);
                startActivity(intent);
            }
        });
        followed.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("typ", "followed");
                bundle.putInt("userID", userID);
                bundle.putInt("authority",authority);
                bundle.putString ("userName", userName);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(UserPageActivity.this,UserListActivity.class);
                startActivity(intent);
            }
        });
        if(userID == viewID) {
            nickname.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    editable = (editable + 1) % 2;
                    if (editable == 1) {
                        nickname.setFocusable(true);
                        nickname.setCursorVisible(true);
                        nickname.setFocusableInTouchMode(true);
                        nickname.requestFocus();
                    } else if (editable == 0) {
                        nickname.setCursorVisible(false);
                        nickname.setFocusable(false);
                        nickname.setFocusableInTouchMode(false);
                        userName = nickname.getText().toString();
                        Log.i("my", nickname.getText().toString());
                        AsyncHttpClient client = new AsyncHttpClient();
                        final String url = application.getIPAdd()+"userpage";
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("id", userID);
                            jsonObject.put("otheruid",viewID);
                            jsonObject.put("typ", "edit_name");
                            jsonObject.put("nickname", nickname.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Toast.makeText(view.getContext(), password.getText().toString(), Toast.LENGTH_LONG).show();

                        ByteArrayEntity entity = null;
                        StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

                        client.post(UserPageActivity.this, url, stringEntity, "application/json", new JsonHttpResponseHandler() {
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
                                Log.i("my","follow fail");
                            }
                        });
                    }
                }
            });
        }
        followhim.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                nfollow = (nfollow+1)%2;
                if(nfollow == 0)
                    followhim.setText("+关注");
                else if(nfollow ==1)
                    followhim.setText("已关注");
                AsyncHttpClient client = new AsyncHttpClient();
                final String url = application.getIPAdd()+"userpage";
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id", userID);
                    jsonObject.put("otheruid",viewID);
                    jsonObject.put("typ", "follow_user");
                    jsonObject.put("follow", nfollow);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Toast.makeText(view.getContext(), password.getText().toString(), Toast.LENGTH_LONG).show();

                ByteArrayEntity entity = null;
                StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

                client.post(UserPageActivity.this, url, stringEntity, "application/json", new JsonHttpResponseHandler() {
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
                        Log.i("my","follow fail");
                    }
                });
            }
        });

        AsyncHttpClient client = new AsyncHttpClient();
        final String url = application.getIPAdd()+"userpage";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", userID);
            jsonObject.put("otheruid",viewID);
            jsonObject.put("typ", "view_user");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Toast.makeText(view.getContext(), password.getText().toString(), Toast.LENGTH_LONG).show();

        ByteArrayEntity entity = null;
        StringEntity stringEntity = new StringEntity(jsonObject.toString(), "utf-8");

        client.post(UserPageActivity.this, url, stringEntity, "application/json", new JsonHttpResponseHandler() {
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

                try {
                    nname = response.getString("nickname");
                    nsignature = response.getString("signature");
                    nfollow = response.getInt("follow");
                    nfollowednum = response.getInt("followed_num");
                    nfollownum = response.getInt("follow_num");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                nickname.setText(nname);
                signature.setText(nsignature);
                follow.setText("关注:"+nfollownum);
                followed.setText("粉丝:"+nfollowednum);
                Log.i("my","name "+nname+" | sig "+signature);
                if(nfollow == 0)
                    followhim.setText("+关注");
                else if(nfollow ==1)
                    followhim.setText("已关注");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("my","view fail");
            }
        });
    }

}
