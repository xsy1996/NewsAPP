package com.xsy.servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xsy.database.DBUtils;
import com.xsy.object.BaseBean;
import com.xsy.object.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String account = request.getParameter("account"); // 获取客户端传过来的参数
//        String password = request.getParameter("password");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        /**
         * 接收json
         */
        System.out.println("receive post request");
        BufferedReader reader = request.getReader();
        String json = reader.readLine();
        Gson gson = new Gson();
        UserBean input = gson.fromJson(json,UserBean.class);
        String account = input.getAccount();
        String password = input.getPassword();
        System.out.println("account = "+account+", password = "+password);

        int authority =1;

        if (account == null || account.equals("") || password == null || password.equals("")) {
            System.out.println("password=null");
            return;
        }

        // 请求数据库
        DBUtils dbUtils = new DBUtils();
        dbUtils.openConnect(); // 打开数据库连接
        UserBean userBean = new UserBean();   //user的对象
        ResultSet rs;

        if (dbUtils.UserIsCorrectInDB(account,password)) { // 判断账号是否存在
            userBean.setCode(0);
            rs= dbUtils.getUserFromDB(account, password);
            try {
                rs.next();
                userBean.setAccount(rs.getString("Account"));
                userBean.setId(rs.getInt("ID"));
                userBean.setAuthority(rs.getInt("Authority"));
                userBean.setNickname(rs.getString("Nickname"));
                System.out.println("id = "+rs.getInt("ID"));

            } catch (SQLException e) {
                e.printStackTrace();
            }
            userBean.setMsg("successful");
        }else{
            userBean.setCode(1);
            userBean.setMsg("fail");
        }

        /* else if (!dbUtils.insertUserToDB(account, password,authority)) { // 注册成功
            data.setCode(0);
            data.setMsg("successful");
            ResultSet rs = dbUtils.getUser();
            int id = -1;
            if (rs != null) {
                try {
                    while (rs.next()) {
                        if (rs.getString("Acccount").equals(account) && rs.getString("Password").equals(password)) {
                            id = rs.getInt("ID");
                        }
                    }
                    userBean.setId(id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            userBean.setAccount(account);
            userBean.setPassword(password);
            data.setData(userBean);
        } else { // 注册不成功，这里错误没有细分，都归为数据库错误
            data.setCode(500);
            data.setData(userBean);
            data.setMsg("detabase error");
        }*/
        gson = new Gson();
        json = gson.toJson(userBean);  //将对象转化成json字符串
        try {
            response.getWriter().println(json); // 将json数据传给客户端
            System.out.println("msg = "+userBean.getMsg());
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
