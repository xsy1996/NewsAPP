package com.xsy.servlet;

import com.google.gson.Gson;
import com.xsy.database.DBUtils;
import com.xsy.object.NewsBean;
import com.xsy.object.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "UserPageServlet")
public class UserPageServlet extends HttpServlet {

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
        UserBean userBean = gson.fromJson(json,UserBean.class);
        int uid = userBean.getId();
//         System.out.println();



        if (uid <=0) {
            System.out.println("sth=null");
            userBean.setCode(3);
            userBean.setMsg("" + uid);
            return;
        }
        // 请求数据库
        DBUtils dbUtils = new DBUtils();
        dbUtils.openConnect(); // 打开数据库连接
        if(userBean.getTyp().equals("follow_user")){
            userBean.setCode(0);
            userBean.setMsg("getfollowmsg");
            if(dbUtils.updateUserFollowToDB(uid,userBean.getOtheruid(),userBean.getFollow()))
            {
                userBean.setCode(1);
                userBean.setMsg("follow error");
            }
        }else if(userBean.getTyp().equals("edit_name")){
            System.out.println("editname");
            userBean.setCode(0);
            userBean.setMsg("editname");
            if(dbUtils.updateUserNameToDB(uid,userBean.getNickname()))
            {
                userBean.setCode(1);
                userBean.setMsg("edit error");
            }
            System.out.println(userBean.getMsg());
        }else if( userBean.getTyp().equals("view_user")){
            System.out.println(userBean.getId()+"| "+userBean.getOtheruid());
            userBean.setFollow(dbUtils.getFollow(uid,userBean.getOtheruid()));
            userBean.setFollow_num(dbUtils.getUserNumFromDBBYUserIDANDTyp(userBean.getOtheruid(),"follow"));
            userBean.setFollowed_num(dbUtils.getUserNumFromDBBYUserIDANDTyp(userBean.getOtheruid(),"followed"));
            System.out.println("follow"+userBean.getFollow_num()+"|"+userBean.getFollowed_num());
            ResultSet rs = dbUtils.getUserFromDBBYID(userBean.getOtheruid());
            if (rs == null) {
                userBean.setCode(1);
                userBean.setMsg("database error");
            } else {
                userBean.setCode(0);
                try {
                    rs.next();
                    userBean.setNickname(rs.getString("Nickname"));
                    userBean.setSignature(rs.getString("Signature"));
                    userBean.setId(rs.getInt("ID"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                userBean.setMsg("successful");
            }

        }
        gson = new Gson();
        json = gson.toJson(userBean);  //将对象转化成json字符串
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
