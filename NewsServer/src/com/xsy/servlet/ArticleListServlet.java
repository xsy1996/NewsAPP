package com.xsy.servlet;

import com.google.gson.Gson;
import com.xsy.database.DBUtils;
import com.xsy.object.NewsBean;
import com.xsy.object.NewsListInputBean;
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

@WebServlet(name = "ArticleListServlet")
public class ArticleListServlet extends HttpServlet {

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
        NewsListInputBean input = gson.fromJson(json,NewsListInputBean.class);
        int typ = input.getTyp();
        int uid = input.getUserid();

        //typ == 0 表示从mainactivity标签选择来。typ=1表示从我的收藏来，typ=2表示myarticle来，typ=3表示userpage来,typ=4表示从关键字搜索来typ=5 表示审核
        //typ=0,label=标签,typ = 1,label=userid,typ=2,label = 审核过没过（1，0，-1）,typ=3,label = viewuserid,typ=4,label = keyword


        //  System.out.println("account = "+account+", password = "+password);


        NewsBean newsBean = new NewsBean();
        List<NewsBean> output = new ArrayList<NewsBean>();



        // 请求数据库
        DBUtils dbUtils = new DBUtils();
        dbUtils.openConnect(); // 打开数据库连接
        ResultSet rs =null;
        Metrix metrix = new Metrix();

        if(typ==0)
        {
            System.out.println("typ = "+typ+",label = "+input.getLabel());

            int lid = dbUtils.getLabelIDFromDBBYLabel(input.getLabel());
            if(lid == -1){
                if(input.getLabel().equals("推荐")){
                    if(uid>=0)
                    {
                        metrix.update_metrix();
                        output = metrix.get_rec_news_by_view(uid);
                    }
                }
                else{
                    System.out.println("label "+input.getLabel()+" not exist");
                    return;
                }
            }else{
                rs =dbUtils.getNewsListFromDBBYLabelID(lid);
            }

        }else if(typ==1){
           System.out.println("typ = "+typ+",viewid = "+input.getViewuserid());
            rs=dbUtils.getLoveNewsListFromDBBYUserID(uid);
        }else if(typ==2){
           System.out.println("typ = "+typ+",viewid = "+input.getViewuserid()+",pass = "+input.getPass());
           rs=dbUtils.getNewsListFromDBBYReporterIDANDStatus(uid,input.getPass());
        }else if(typ==3){
           System.out.println("typ = "+typ+",userID = "+input.getViewuserid());
            rs=dbUtils.getNewsListFromDBBYReporterIDANDStatus(input.getViewuserid(),1);
        }else if(typ==4){
            System.out.println("typ = "+typ+",keyword = "+input.getKeyword());
            rs=dbUtils.getNewsListFromDBBYKeyword(input.getKeyword());
        }else if(typ == 5)
        {
            rs=dbUtils.getNoJudgeNewsListFromDB();
        }
        try {
            output.add(new NewsBean());
            if ((rs != null) && !(typ==0 && input.getLabel().equals("推荐"))) {
                System.out.println("typ = "+typ);
                while (rs.next() && output.size()<30) {  //遍历结果集
                    newsBean = new NewsBean();
                    newsBean.setId(rs.getInt("ID"));
                    newsBean.setReporterid(rs.getInt("ReporterID"));
                    newsBean.setSubtitle(rs.getString("Subtitle"));
                    newsBean.setTitle(rs.getString("Title"));
                    newsBean.setPass(rs.getInt("Pass"));
                    newsBean.setReporter(dbUtils.getUserNameFromDBByID(newsBean.getReporterid()));
                    output.add(newsBean);
                    System.out.println("typ = "+typ+"  title = "+newsBean.getTitle()+" reporterid "+newsBean.getReporterid()+" reporter "+newsBean.getReporter());

                }
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(input.getLabel()+"|"+output.get(1).getTitle());

        for(int i=0;i<output.size() && i<=10;i++)
            System.out.println(output.get(i).getTitle()+"|"+output.get(i).getReporter());

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
