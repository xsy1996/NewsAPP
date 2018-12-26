package com.xsy.newsapp;

/**
 * Created by xsy on 2018/1/19.
 */


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

public class SignupFragment extends Fragment {

    private Context mContext;

    Button signup;
    EditText account,password;
    String userName = "未登录";
    int userID = -1,authority=1;
    MyApplication application;


    public static SignupFragment newInstance(int typ, String label) {
//        Bundle args = new Bundle();
//        args.putInt("typ", typ);
//        args.putString("label",label);
        SignupFragment fragment = new SignupFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        application = (MyApplication)this.getActivity().getApplicationContext();
//        Bundle args = getArguments();
//        if (args != null) {
//            typ = (int)args.get("typ");
//            label = args.get("label").toString();
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_fragment, container, false);



        signup = (Button) view.findViewById(R.id.signup_button);
        account = (EditText) view.findViewById(R.id.signup_userId);
        password = (EditText)  view.findViewById(R.id.signup_passwd);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (account.getText().toString().isEmpty()||password.getText().toString().isEmpty()) {
                    Toast.makeText(view.getContext(), "用户名不能为空", Toast.LENGTH_LONG).show();
                }
                else {
                    String uname = account.getText().toString();
                    String upass = password.getText().toString();
                    AsyncHttpClient client = new AsyncHttpClient();
                    final String url = application.getIPAdd()+"signup";
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("account", uname);
                        jsonObject.put("password", upass);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Toast.makeText(view.getContext(), password.getText().toString(), Toast.LENGTH_LONG).show();
                    Log.i("my","signp begin");

                    ByteArrayEntity entity = null;
                    try {
                        StringEntity stringEntity = new StringEntity(jsonObject.toString());

                      //  Log.i("my","receive sth1");
                        client.post(mContext, url, stringEntity, "application/json", new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                // TODO Auto-generated method stub
                                super.onSuccess(statusCode, headers, response);
                                String msg="fail";
                                int code = -1;
                              //  Log.i("my","receive sth2");
                                try {
                                    msg=response.getString("msg");
                                    code = response.getInt("code");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if(code == 3)
                                {
                                    Toast.makeText(getView().getContext(), "用户名密码不能为空", Toast.LENGTH_LONG).show();
                                }
                                else if(code ==2)
                                {
                                    Toast.makeText(getView().getContext(), "该用户已存在", Toast.LENGTH_LONG).show();
                                }
                                else if(code == 1)
                                {
                                    Toast.makeText(getView().getContext(), "数据库错误", Toast.LENGTH_LONG).show();
                                }
                                else if(code == 0)
                                {
                                    Bundle bundle = new Bundle();
                                    try {
                                        bundle.putInt("userID", response.getInt("id"));
                                        bundle.putString("userName",response.getString("nickname"));
                                        bundle.putInt("authority",authority);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(getView().getContext(), bundle.getString("userName"), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent();
                                    intent.putExtras(bundle);
                                    intent.setClass(mContext, MainActivity.class);
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

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Log.i("my","request over");
                }
            }
        });
      /*  signup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //   String userid = user_idEt.getText().toString().trim();
                //  String pass = passEt.getText().toString().trim();


//                Bundle bundle = new Bundle();
//                bundle.putString("article_id", "");
                Intent intent = new Intent();
//                intent.putExtras(bundle);
                intent.setClass(mContext, MainActivity.class);
                startActivity(intent);
            }

        });*/

        return view;
    }


}

