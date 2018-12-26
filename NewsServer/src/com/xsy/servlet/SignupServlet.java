package com.xsy.servlet;

import com.google.gson.Gson;
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
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "SignupServlet")
public class SignupServlet extends HttpServlet {

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
        String account = input.getAccount();
        String password = input.getPassword();
      //  System.out.println("account = "+account+", password = "+password);


        UserBean userBean = new UserBean();   //user的对象
        int authority =1;

        if (account == null || account.equals("") || password == null || password.equals("")) {
            System.out.println("password=null");

            userBean.setCode(3);
            userBean.setMsg("null");
            return;
        }

        // 请求数据库
        DBUtils dbUtils = new DBUtils();
        dbUtils.openConnect(); // 打开数据库连接
        ResultSet rs;

        if (dbUtils.UserIsExistInDB(account)){
            userBean.setCode(2);
            userBean.setMsg("repeated");
        }else if(dbUtils.insertUserToDB(account,password,authority)){
            userBean.setCode(1);
            userBean.setMsg("database error");
        }else{
            userBean.setCode(0);
            rs= dbUtils.getUserFromDB(account, password);
            try {
                rs.next();
                userBean.setAccount(rs.getString("Account"));
                userBean.setId(rs.getInt("ID"));
                userBean.setNickname(rs.getString("Nickname"));
                userBean.setAuthority(rs.getInt("Authority"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            userBean.setMsg("successful");
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
