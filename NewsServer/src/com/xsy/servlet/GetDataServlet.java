package com.xsy.servlet;

import com.xsy.database.DBUtils;
import com.xsy.object.NewsBean;
import com.xsy.tools.Crawler;
import com.xsy.tools.Metrix;
import org.omg.PortableInterceptor.INACTIVE;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "GetDataServlet")

public class GetDataServlet extends HttpServlet {

    Metrix metrix = new Metrix();
    Crawler crawler = new Crawler();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void init() {

        System.out.println("这样在web容器启动的时候，就会执行这句话了！");


// =====================测试代码===========================
        DBUtils dbUtils = new DBUtils();
        dbUtils.openConnect();
//        for(int i=1;i<=10;i++)
//            dbUtils.executesql("INSERT INTO User (Account,Password,Authority,Nickname) VALUES ('A"+i+"','11111111',1,'A"+i+"')");
//        for(int i=1;i<=10;i++)
//            dbUtils.executesql("INSERT INTO User (Account,Password,Authority,Nickname) VALUES ('B"+i+"','11111111',1,'B"+i+"')");
//        for(int i=1;i<=10;i++)
//            dbUtils.executesql("INSERT INTO User (Account,Password,Authority,Nickname) VALUES ('C"+i+"','11111111',1,'C"+i+"')");
//        for(int i=1;i<=10;i++)
//            dbUtils.executesql("INSERT INTO User (Account,Password,Authority,Nickname) VALUES ('D"+i+"','11111111',1,'D"+i+"')");
//=============================================================
//        Random rand = new Random();
//        int total1,total2,viewnum;
//        int[] A = new int[11],B = new int[11],C = new int[11],D = new int[11];
//        for(int i=1;i<=10;i++) {
//            A[i]=527+i;
//            B[i]=537+i;
//            C[i]=547+i;
//            D[i]=557+i;
//        }
//======================================================
//        ArrayList<Integer> nidlist1 = new ArrayList<Integer>();
//        ArrayList<Integer> nidlist2 = new ArrayList<Integer>();
//        ArrayList<Integer> nidlist3 = new ArrayList<Integer>();
//        ArrayList<Integer> nidlist4 = new ArrayList<Integer>();
////        System.out.println(total1+"|"+total2);
//        ResultSet rs = dbUtils.executesqlquery("select * from News n WHERE EXISTS (SELECT * FROM Label l Where " +
//                "(l.Text = '动漫' OR l.Text = '军事' OR l.Text = '财经')" +
//                "AND EXISTS(SELECT * FROM News_Label_Manager nl WHERE nl.LabelID = l.ID AND nl.NewsID = n.ID));");
//        try {
//            if (rs != null) {
//                while (rs.next()) {  //遍历结果集
//                    nidlist1.add(rs.getInt("ID"));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        rs = dbUtils.executesqlquery("select * from News n WHERE EXISTS (SELECT * FROM Label l Where " +
//                "(l.Text = '时尚' OR l.Text = '科技' OR l.Text = '旅游')" +
//                "AND EXISTS(SELECT * FROM News_Label_Manager nl WHERE nl.LabelID = l.ID AND nl.NewsID = n.ID));");
//        try {
//            if (rs != null) {
//                while (rs.next()) {  //遍历结果集
//                    nidlist2.add(rs.getInt("ID"));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        rs = dbUtils.executesqlquery("select * from News n WHERE EXISTS (SELECT * FROM Label l Where " +
//                "(l.Text = '教育' OR l.Text = '美食' OR l.Text = '文化')" +
//                "AND EXISTS(SELECT * FROM News_Label_Manager nl WHERE nl.LabelID = l.ID AND nl.NewsID = n.ID));");
//        try {
//            if (rs != null) {
//                while (rs.next()) {  //遍历结果集
//                    nidlist3.add(rs.getInt("ID"));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        rs = dbUtils.executesqlquery("select * from News n WHERE EXISTS (SELECT * FROM Label l Where " +
//                "(l.Text = '社会' OR l.Text = '游戏' OR l.Text = '历史')" +
//                "AND EXISTS(SELECT * FROM News_Label_Manager nl WHERE nl.LabelID = l.ID AND nl.NewsID = n.ID));");
//        try {
//            if (rs != null) {
//                while (rs.next()) {  //遍历结果集
//                    nidlist4.add(rs.getInt("ID"));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println(nidlist1.size()+"|"+nidlist2.size()+"|"+nidlist3.size()+"|"+nidlist4.size());
//
//        total1 = (int)(nidlist1.size()*0.2+0.5);
//        total2 = (int)(total1*0.1);
//        for(int i=1;i<=10;i++){
//            for(int j=1;j<=total1;j++) {
//                viewnum=rand.nextInt(nidlist1.size());
//                dbUtils.executesql("insert into Records(Kind,UserID,NewsID) values ('view',"+A[i]+","+nidlist1.get(viewnum)+")");
//                int rrand = rand.nextInt(5);
//                if(rrand==0)
//                    dbUtils.executesql("insert into Records(Kind,UserID,NewsID,Rating) values ('rating',"+A[i]+","+nidlist1.get(viewnum)+",5)");
//                else if(rrand==1)
//                    dbUtils.executesql("insert into Records(Kind,UserID,NewsID,Rating) values ('rating',"+A[i]+","+nidlist1.get(viewnum)+",4)");
//            }
//            for(int j=1;j<=total2;j++) {
//                viewnum=rand.nextInt(nidlist2.size());
//                dbUtils.executesql("insert into Records(Kind,UserID,NewsID) values ('view',"+A[i]+","+nidlist2.get(viewnum)+")");
//            }
//        }
//        System.out.println(total1+" "+total2);
//        total1 = (int)(nidlist2.size()*0.2+0.5);
//        total2 = (int)(total1*0.1);
//        for(int i=1;i<=10;i++){
//            for(int j=1;j<=total1;j++) {
//                viewnum=rand.nextInt(nidlist2.size());
//                dbUtils.executesql("insert into Records(Kind,UserID,NewsID) values ('view',"+B[i]+","+nidlist2.get(viewnum)+")");
//                int rrand = rand.nextInt(5);
//                if(rrand==0)
//                    dbUtils.executesql("insert into Records(Kind,UserID,NewsID,Rating) values ('rating',"+B[i]+","+nidlist2.get(viewnum)+",5)");
//                else if(rrand==1)
//                    dbUtils.executesql("insert into Records(Kind,UserID,NewsID,Rating) values ('rating',"+B[i]+","+nidlist2.get(viewnum)+",4)");
//            }
//            for(int j=1;j<=total2;j++) {
//                viewnum=rand.nextInt(nidlist3.size());
//                dbUtils.executesql("insert into Records(Kind,UserID,NewsID) values ('view',"+B[i]+","+nidlist3.get(viewnum)+")");
//            }
//        }
//        System.out.println(total1+" "+total2);
//        total1 = (int)(nidlist3.size()*0.2+0.5);
//        total2 = (int)(total1*0.1);
//        for(int i=1;i<=10;i++){
//            for(int j=1;j<=total1;j++) {
//                viewnum=rand.nextInt(nidlist3.size());
//                dbUtils.executesql("insert into Records(Kind,UserID,NewsID) values ('view',"+C[i]+","+nidlist3.get(viewnum)+")");
//                int rrand = rand.nextInt(5);
//                if(rrand==0)
//                    dbUtils.executesql("insert into Records(Kind,UserID,NewsID,Rating) values ('rating',"+C[i]+","+nidlist3.get(viewnum)+",5)");
//                else if(rrand==1)
//                    dbUtils.executesql("insert into Records(Kind,UserID,NewsID,Rating) values ('rating',"+C[i]+","+nidlist3.get(viewnum)+",4)");
//            }
//            for(int j=1;j<=total2;j++) {
//                viewnum=rand.nextInt(nidlist4.size());
//                dbUtils.executesql("insert into Records(Kind,UserID,NewsID) values ('view',"+C[i]+","+nidlist4.get(viewnum)+")");
//            }
//        }
//        System.out.println(total1+" "+total2);
//        total1 = (int)(nidlist4.size()*0.2+0.5);
//        total2 = (int)(total1*0.1);
//        for(int i=1;i<=10;i++){
//            for(int j=1;j<=total1;j++) {
//                viewnum=rand.nextInt(nidlist4.size());
//                dbUtils.executesql("insert into Records(Kind,UserID,NewsID) values ('view',"+D[i]+","+nidlist4.get(viewnum)+")");
//                int rrand = rand.nextInt(5);
//                if(rrand==0)
//                    dbUtils.executesql("insert into Records(Kind,UserID,NewsID,Rating) values ('rating',"+D[i]+","+nidlist4.get(viewnum)+",5)");
//                else if(rrand==1)
//                    dbUtils.executesql("insert into Records(Kind,UserID,NewsID,Rating) values ('rating',"+D[i]+","+nidlist4.get(viewnum)+",4)");
//            }
//            for(int j=1;j<=total2;j++) {
//                viewnum=rand.nextInt(nidlist1.size());
//                dbUtils.executesql("insert into Records(Kind,UserID,NewsID) values ('view',"+D[i]+","+nidlist1.get(viewnum)+")");
//            }
//        }
//        System.out.println(total1+" "+total2);

//=================================================

//        metrix.update_metrix();
//        for(int i=1;i<=10;i++){
//            System.out.println("============A"+i+"=============");
//            List<NewsBean> ans = metrix.get_rec_news_by_view(A[i]);
//            for(int j=0;j<ans.size() && j<15;j++){
//                System.out.println(ans.get(j).getTitle()+" | "+ans.get(j).getRating());
//            }
//        }
//        for(int i=1;i<=10;i++){
//            System.out.println("============B"+i+"=============");
//            List<NewsBean> ans = metrix.get_rec_news_by_view(B[i]);
//            for(int j=0;j<ans.size() && j<15;j++){
//                System.out.println(ans.get(j).getTitle()+" | "+ans.get(j).getRating());
//            }
//        }
//        for(int i=1;i<=10;i++){
//            System.out.println("============C"+i+"=============");
//            List<NewsBean> ans = metrix.get_rec_news_by_view(C[i]);
//            for(int j=0;j<ans.size() && j<15;j++){
//                System.out.println(ans.get(j).getTitle()+" | "+ans.get(j).getRating());
//            }
//        }
//        for(int i=1;i<=10;i++){
//            System.out.println("============D"+i+"=============");
//            List<NewsBean> ans = metrix.get_rec_news_by_view(D[i]);
//            for(int j=0;j<ans.size() && j<15;j++){
//                System.out.println(ans.get(j).getTitle()+" | "+ans.get(j).getRating());
//            }
//        }


// ================================================
        dbUtils.closeConnect();
// =====================测试结束===========================

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {

                System.out.println("自动获取新闻");
                crawler.getbiznews();
                crawler.gettravelnews();
                crawler.getfashionnews();
                crawler.getitnews();
                crawler.getacgnews();
                crawler.getchihenews();
                crawler.getculnews();
                crawler.getgamenews();
                crawler.gethistorynews();
                crawler.getlearningnews();
                crawler.getmilnews();
                crawler.getsocietynews();
            }
        }, 0, 60 * 60 * 1000);// 这里设定将延时固定执行

        timer=new Timer();

//        timer.scheduleAtFixedRate(new TimerTask() {
//            public void run() {
//                metrix.update_metrix();
//
//            }
//        }, 0, 60 * 1);// 这里设定将延时每天固定执行
    }
}
