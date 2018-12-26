package com.xsy.servlet;

import com.google.gson.Gson;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import com.xsy.database.DBUtils;
import com.xsy.object.NewsBean;
import com.xsy.object.NewsListInputBean;
import com.xsy.object.UserBean;
import com.xsy.tools.Metrix;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "UserListServlet")
public class UserListServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String account = request.getParameter("account"); // 获取客户端传过来的参数
//        String password = request.getParameter("password");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        /**
         * 接收json
         */
        // System.out.println("receive post request");
        BufferedReader reader = request.getReader();
        String json = reader.readLine();
        Gson gson = new Gson();
        UserBean input = gson.fromJson(json,UserBean.class);
        String  typ = input.getTyp();
        int uid = input.getId();

        //typ == 0 表示从mainactivity标签选择来。typ=1表示从我的收藏来，typ=2表示myarticle来，typ=3表示userpage来,typ=4表示从关键字搜索来typ=5 表示审核
        //typ=0,label=标签,typ = 1,label=userid,typ=2,label = 审核过没过（1，0，-1）,typ=3,label = viewuserid,typ=4,label = keyword


        //  System.out.println("account = "+account+", password = "+password);


        UserBean userBean = new UserBean();
        List<UserBean> output = new ArrayList<UserBean>();



        // 请求数据库
        DBUtils dbUtils = new DBUtils();
        dbUtils.openConnect(); // 打开数据库连接
        ResultSet rs =null;
        System.out.println("typ = "+typ+",userid = "+uid);

        if(uid == -1 || (!typ.equals("follow") && !typ.equals("followed"))){
            System.out.println("uid = -1 or typ = "+typ);
            return;
        }else{
            rs =dbUtils.getUserListFromDBBYUserIDANDTyp(uid,typ);
        }

        try {
            if (rs != null) {
                while (rs.next()) {  //遍历结果集
                    userBean = new UserBean();
                    userBean.setId(rs.getInt("ID"));
                    userBean.setNickname(rs.getString("Nickname"));
                    userBean.setSignature(rs.getString("Signature"));
                    output.add(userBean);

                }
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }


        for(int i=0;i<output.size() && i<=10;i++)
            System.out.println(output.get(i).getNickname());

        gson = new Gson();
        json = gson.toJson(output);  //将对象转化成json字符串

        try {
            response.getWriter().println(json); // 将json数据传给客户端
            // System.out.println("msg = "+data.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.getWriter().close(); // 关闭这个流，不然会发生错误的
        }
        dbUtils.closeConnect(); // 关闭数据库连接
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
