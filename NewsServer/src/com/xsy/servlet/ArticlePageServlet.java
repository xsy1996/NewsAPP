package com.xsy.servlet;

import com.google.gson.Gson;
import com.xsy.database.DBUtils;
import com.xsy.object.NewsBean;
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

@WebServlet(name = "ArticlePageServlet")
public class ArticlePageServlet extends HttpServlet {

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
        int newsid = newsdata.getId();
         System.out.println("userid = "+newsdata.getUserid()+", kind = "+newsdata.getKind());



        if (newsid <=0) {
            System.out.println("sth=null");
            newsdata.setCode(3);
            newsdata.setMsg("" + newsid);
            return;
        }

        // 请求数据库
        DBUtils dbUtils = new DBUtils();
        dbUtils.openConnect(); // 打开数据库连接
        if(newsdata.getUserid()<0)
        {
            ResultSet rs = dbUtils.getNewsFromDBBYID(newsid);
            if (rs == null) {
                newsdata.setCode(1);
                newsdata.setMsg("database error");
            } else {
                newsdata.setCode(0);
                try {
                    rs.next();
                    newsdata.setTitle(rs.getString("Title"));
                    newsdata.setSubtitle(rs.getString("Subtitle"));
                    newsdata.setText(rs.getString("Text"));
                    newsdata.setId(rs.getInt("ID"));
                    newsdata.setReporterid(rs.getInt("ReporterID"));
                    newsdata.setReporter(dbUtils.getUserNameFromDBByID(newsdata.getReporterid()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                newsdata.setMsg("successful");
            }
        }else if(newsdata.getKind().equals("love_news")){
            newsdata.setCode(0);
            newsdata.setMsg("getlovemsg "+newsdata.getLove());
            if(dbUtils.updateUserNewsLoveToDB(newsdata.getUserid(),newsid,newsdata.getLove()))
            {
                newsdata.setCode(1);
                newsdata.setMsg("rating error");
            }
        }else if(newsdata.getKind().equals("rating_news")) {
            newsdata.setCode(0);
            newsdata.setMsg("rating = "+newsdata.getRating());
            if(dbUtils.insertRatingRecToDB(newsdata.getUserid(),newsid,newsdata.getRating()))
            {
                newsdata.setCode(1);
                newsdata.setMsg("rating error");
            }
        }else if(newsdata.getKind().equals("review_pass")) {
            newsdata.setCode(0);
            newsdata.setMsg("pass ");
            if(dbUtils.updateReviewToDB(newsid,1))
            {
                newsdata.setCode(1);
                newsdata.setMsg("pass error");
            }
        }else if(newsdata.getKind().equals("review_nopass")) {
            newsdata.setCode(0);
            newsdata.setMsg("nopass");
            if(dbUtils.updateReviewToDB(newsid,-1))
            {
                newsdata.setCode(1);
                newsdata.setMsg("nopass error");
            }
        }else if(newsdata.getKind().equals("get_news")){
            System.out.println("view");
            dbUtils.insertViewRecToDB(newsdata.getUserid(),newsid);
            newsdata.setLove(dbUtils.getLove(newsdata.getUserid(),newsid));
            newsdata.setRating(dbUtils.getRating(newsdata.getUserid(),newsid));
            ResultSet rs = dbUtils.getNewsFromDBBYID(newsid);
            if (rs == null) {
                newsdata.setCode(1);
                newsdata.setMsg("database error");
            } else {
                newsdata.setCode(0);
                try {
                    rs.next();
                    newsdata.setTitle(rs.getString("Title"));
                    newsdata.setSubtitle(rs.getString("Subtitle"));
                    newsdata.setText(rs.getString("Text"));
                    newsdata.setId(rs.getInt("ID"));
                    newsdata.setReporterid(rs.getInt("ReporterID"));
                    newsdata.setReporter(dbUtils.getUserNameFromDBByID(newsdata.getReporterid()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                newsdata.setMsg("successful");
            }

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
