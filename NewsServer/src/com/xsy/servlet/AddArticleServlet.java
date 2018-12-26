package com.xsy.servlet;

import com.google.gson.Gson;
import com.xsy.database.DBUtils;
import com.xsy.object.BaseBean;
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

@WebServlet(name = "AddArticleServlet")
public class AddArticleServlet extends HttpServlet {

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
        NewsBean newsdata = gson.fromJson(json,NewsBean.class);
        String title = newsdata.getTitle();
        String subtitle = newsdata.getSubtitle();
        String text = newsdata.getText();
        int reporterid = newsdata.getReporterid();
      //  System.out.println("account = "+account+", password = "+password);



        if (title == null || title.equals("") || subtitle == null || subtitle.equals("") || text == null || text.equals("")||  reporterid<=0) {
            System.out.println("sth=null");
            newsdata.setCode(3);
           // data.setData(newsdata);
            newsdata.setMsg(title+"|"+subtitle+"|"+text+"|"+reporterid);
            return;
        }

         System.out.println("1title = "+title);

        // 请求数据库
        DBUtils dbUtils = new DBUtils();
        dbUtils.openConnect(); // 打开数据库连接
        ResultSet rs;
        if (dbUtils.NewsIsExistInDB(title)){
            newsdata.setCode(2);
            //data.setData(userBean);
            newsdata.setMsg("repeated");
            System.out.println("2title = "+title);
        }else if(dbUtils.insertNewsToDB(title,subtitle,text,reporterid)){
            newsdata.setCode(1);
            //data.setData(newsdata);
            newsdata.setMsg("database error");
            System.out.println("3title = "+title);
        }else{
            newsdata.setCode(0);
            rs= dbUtils.getNewsFromDBBYTitle(title);
            try {
                rs.next();
                newsdata.setId(rs.getInt("ID"));
                System.out.println("4title = "+rs.getString("Title"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String[] labels = newsdata.getLabels().split("\\|");
            for(int i=0;i<labels.length;i++)
            {
                dbUtils.insertLabelToDB(labels[i]);
                int lid = dbUtils.getLabelIDFromDBBYLabel(labels[i]);
                dbUtils.setLabeltoNews(newsdata.getId(),lid);
            }
            newsdata.setMsg("successful");
        }

        gson = new Gson();
        json = gson.toJson(newsdata);  //将对象转化成json字符串
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
