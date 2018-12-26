package com.xsy.servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xsy.database.DBUtils;
import com.xsy.object.LabelBean;
import com.xsy.object.LabelListBean;
import com.xsy.object.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "EditLabelServlet")
public class EditLabelServlet extends HttpServlet {

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
        List<LabelBean> labelBeans = gson.fromJson(json, new TypeToken<List<LabelBean>>() {}.getType());
        int typ = labelBeans.get(0).getTyp();
        int uid = labelBeans.get(0).getUserid();


        System.out.println("json = "+json);
        System.out.println("length = "+labelBeans.size()+"typ = "+labelBeans.get(0).getTyp()+", uid = "+labelBeans.get(0).getUserid());


         System.out.println("typ = "+typ);

        // 请求数据库
        DBUtils dbUtils = new DBUtils();
        dbUtils.openConnect(); // 打开数据库连接
        ResultSet rs;

        if(typ == 1)
        {


        }else if(typ == 0) {
            LabelBean label;
            System.out.println("用户添加标签");

            if(dbUtils.removeLabelManagerFromBDBYUserID(uid)){
                System.out.println("remove label error | " +uid);
                return;
            }
            for(int i=0;i<labelBeans.size();i++){
                if(dbUtils.insertUserLabelManagerToDB(labelBeans.get(i).getText(),uid)){
                    System.out.println("insert user & label error | " + uid +"|"+labelBeans.get(i).getText());
                    return;
                }
            }

        }

        dbUtils.closeConnect(); // 关闭数据库连接
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
