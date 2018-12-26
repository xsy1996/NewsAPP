package com.xsy.servlet;

import com.google.gson.Gson;
import com.xsy.database.DBUtils;
import com.xsy.object.LabelBean;
import com.xsy.object.LabelListBean;
import com.xsy.object.NewsBean;

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

@WebServlet(name = "GetLAbelServlet")
public class GetLabelServlet extends HttpServlet {

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
        LabelListBean labelListBean = gson.fromJson(json,LabelListBean.class);
        int typ = labelListBean.getTyp();
        int uid = labelListBean.getUserid();
      //  System.out.println("account = "+account+", password = "+password);


         System.out.println("typ = "+typ);

        // 请求数据库
        DBUtils dbUtils = new DBUtils();
        dbUtils.openConnect(); // 打开数据库连接
        ResultSet rs;
        List<LabelBean> output = new ArrayList<LabelBean>();

        if(typ == 1)
        {
            LabelBean label;
            int newsid = labelListBean.getNewsid();
            if(newsid != -1) {
                rs = dbUtils.getChosenLabelFromDBBYNewsID(newsid);
                if(rs != null)
                {
                    try{
                        while (rs.next()) {
                            label = new LabelBean();
                            label.setText(rs.getString("Text"));
                            label.setId(rs.getInt("ID"));
                            label.setChose(1);
                            output.add(label);
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            rs = dbUtils.getfreeLabelFromDBBYNewsID(newsid);
            try{
                while (rs.next()) {
                    label = new LabelBean();
                    label.setText(rs.getString("Text"));
                    label.setId(rs.getInt("ID"));
                    label.setChose(0);
                    output.add(label);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }else if(typ == 0)
        {
            LabelBean label;
            int userid = labelListBean.getUserid();
            System.out.println("用户标签");
            if(userid != -1) {
                System.out.println("userid = " + userid);
                rs = dbUtils.getChosenLabelFromDBBYUserID(userid);
                if(rs != null)
                {
                    try{
                        while (rs.next()) {
                            label = new LabelBean();
                            label.setText(rs.getString("Text"));
                            label.setId(rs.getInt("ID"));
                            label.setChose(1);
                            System.out.println("label = " + label.getText()+"|"+label.getChose());
                            output.add(label);
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            rs = dbUtils.getfreeLabelFromDBBYUserID(userid);
            try{
                while (rs.next()) {
                    label = new LabelBean();
                    label.setText(rs.getString("Text"));
                    label.setId(rs.getInt("ID"));
                    label.setChose(0);
                    System.out.println("label = " + label.getText()+"|"+label.getChose());
                    output.add(label);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

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
