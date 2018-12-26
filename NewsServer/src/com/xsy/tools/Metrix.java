package com.xsy.tools;

import com.xsy.database.DBUtils;
import com.xsy.object.NewsBean;
import com.xsy.object.UserBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

class NewsBeanCamparator implements Comparator<NewsBean>
{
    public int compare(NewsBean a,NewsBean b)
    {
        if(a.getRating()<b.getRating())
            return 1;
        else
            return -1;
    }
}
public class Metrix {

//    static ArrayList<ArrayList<Float>> rating_rec;
//    static ArrayList<ArrayList<Integer>> view_rec;
    double[][] view_rec;
    double[][] rating_rec;
    ArrayList<Integer> userlist;
    ArrayList<Integer> newslist;
    Map<Integer,Integer> user_map ;
    Map<Integer,Integer> news_map ;
    int[][] user_news_view ;
    int[][] user_news_rating ;
    DBUtils dbUtils = new DBUtils();
//    static ArrayList<UserBean> user;
    static ArrayList<NewsBean> news;


    void getuser(){
        ResultSet rs;
    }
    void getnews(){

    }

    public void update_metrix(){
        dbUtils.openConnect();
        ResultSet rs1,rs2;
        rs1 = dbUtils.getUser();
        rs2 = dbUtils.getNews();
        userlist = new ArrayList<Integer>();
        newslist = new ArrayList<Integer>();
        user_map = new HashMap<Integer,Integer>();
        news_map = new HashMap<Integer,Integer>();
        news = new ArrayList<NewsBean>();
        try {
            if (rs1 != null) {
                while (rs1.next()) {  //遍历结果集
                    if(rs1.getInt("Authority")!=2){
                        int uid = rs1.getInt("ID");
                        userlist.add(uid);
                        user_map.put(uid,userlist.size()-1);
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs2 != null) {
                while (rs2.next()) {  //遍历结果集
                    int nid = rs2.getInt("ID");
                    newslist.add(nid);
                    news_map.put(nid,newslist.size()-1);
                    NewsBean n1= new NewsBean();
                    n1.setId(rs2.getInt("ID"));
                    n1.setReporterid(rs2.getInt("ReporterID"));
                    n1.setTitle(rs2.getString("Title"));
                    n1.setSubtitle(rs2.getString("Subtitle"));
                    n1.setPass(rs2.getInt("Pass"));
                    news.add(n1);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(userlist.size()+"|"+newslist.size());

        user_news_view = new int[userlist.size()][newslist.size()];
        user_news_rating = new int[userlist.size()][newslist.size()];
        for(int i=0;i<userlist.size();i++)
            for(int j=0;j<newslist.size();j++){
                user_news_view[i][j]=0;
                user_news_rating[i][j]=-1;
            }
        System.out.println("init");
        ResultSet rs3 = dbUtils.getRecordsFromDB();
        try {
            if (rs3 != null) {
                while (rs3.next()) {  //遍历结果集
                    int tuid = user_map.get(rs3.getInt("UserID"));
                    int tnid = news_map.get(rs3.getInt("NewsID"));
                    if(rs3.getString("Kind").equals("view")){
                        user_news_view[tuid][tnid]=1;
                    }else if (rs3.getString("Kind").equals("rating")){
                        user_news_rating[tuid][tnid]=(int)(rs3.getFloat("Rating")+0.1);
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("rec finish");

//        rating_rec = new ArrayList<ArrayList<Float>>();
//        view_rec = new ArrayList<ArrayList<Integer>>();
        rating_rec = new double[userlist.size()][userlist.size()];
        view_rec = new double[userlist.size()][userlist.size()];
        for(int i=0;i<userlist.size();i++)
        {
    //            rating_rec.add(new ArrayList<Float>());
    //            view_rec.add(new ArrayList<Integer>());
            for (int j = i + 1; j < userlist.size(); j++) {
                int comb=0,inter=0;
                for(int k=0;k<newslist.size();k++) {
                    int viewa=user_news_view[i][k];
                    int viewb=user_news_view[j][k];
                    if (viewa==1 && viewb==1)
                        inter++;
                    if (viewa==1 || viewb==1)
                        comb++;
                }
                if(comb != 0)
                    view_rec[i][j] = (double)inter/(double)comb;
                else
                    view_rec[i][j]=0;
                view_rec[j][i]=view_rec[i][j];
//                System.out.println(i+" "+j+" "+ view_rec[i][j]);
            }
        }

        for(int i=0;i<userlist.size();i++)
        {
            for (int j = i + 1; j < userlist.size(); j++) {
                double sim=0;
                int totala=0,totalb=0,comm=0;
                for(int k=0;k<newslist.size();k++) {
                    int ratinga=user_news_rating[i][k];
                    int ratingb = user_news_rating[j][k];
                    if(ratinga>=0 && ratingb>=0) {
                        sim += Math.pow(ratinga - ratingb, 2);
                        comm++;
                    }if(ratinga>=0)
                        totala++;
                    if(ratingb>=0)
                        totalb++;
                }
                if(comm == 0)
                    sim=0;
                else{
                    sim = Math.sqrt(sim/(double)comm);
                    sim = 1.0d-Math.tanh(sim);
                    sim *= (double)comm/(double)Math.min(totala,totalb);
                    rating_rec[i][j]=sim;
                    rating_rec[j][i]=sim;
                   // System.out.println(i+" "+j+" "+ rating_rec[i][j]);
                }
            }
        }

        dbUtils.closeConnect();
    }



    public ArrayList<UserBean> get_rec_user_by_rating(int uid){
        ArrayList<UserBean> userlist = new ArrayList<UserBean>();

        return userlist;
    }

    public ArrayList<NewsBean> get_rec_news_by_rating(int uid){
        ArrayList<NewsBean> reclist = new ArrayList<NewsBean>();
        int u1,u2;
        int rating_times = 0;
        dbUtils.openConnect();
        u1=user_map.get(uid);
        for(int i=0;i<newslist.size();i++) {
            if(user_news_rating[u1][i]>=0)
                rating_times++;
            if(user_news_view[u1][i]==0){
                double predictsum=0,simsum=0;
                for(int j=0;j<userlist.size();j++){
                    u2=j;
                    int rating2 =user_news_rating[u2][i];
                    if(j!=u1 && rating2>=0){
                        predictsum += rating2*rating_rec[u1][u2];
                        simsum+=rating_rec[u1][u2];
                    }
                }
                NewsBean n1 = news.get(i);
                if(simsum==0)
                    n1.setRating(0);
                else
                    n1.setRating((float)(predictsum/simsum));
                if(n1.getRating()>=3)
                    reclist.add(n1);
//                System.out.println(n1.getTitle()+" | "+n1.getRating());
            }
        }
        Random rand =new Random();
        dbUtils.closeConnect();
//        System.out.println("mi"+reclist.size());

        for(int i=0;i<=reclist.size();i++)
            for(int j=i+1;j<reclist.size();j++)
                if(reclist.get(i).getRating()<reclist.get(j).getRating())
                {
                    NewsBean k = reclist.get(i);
                    reclist.set(i,reclist.get(j));
                    reclist.set(j,k);
                }

//        System.out.println(reclist.size());
        for(int i=0;i<reclist.size() && i<15;i++)
            System.out.println(reclist.get(i).getRating()+"        |   "+reclist.get(i).getTitle());
//        System.out.println("finish"+reclist.size());
        return reclist;
    }

    public ArrayList<UserBean> get_rec_user_by_view(int uid){
        ArrayList<UserBean> userlist = new ArrayList<UserBean>();
        return userlist;
    }

    public ArrayList<NewsBean> get_rec_news_by_view(int uid){
//        System.out.println("view_rec "+newslist.size());
        ArrayList<NewsBean> reclist = new ArrayList<NewsBean>();
        int u1=-1,u2;
        int view_times = 0;
        dbUtils.openConnect();
//        for(int i=0;i<userlist.size();i++)
//            if(userlist.get(i)==uid)
//            {
//                u1=i;
//                break;
//            }
        u1=user_map.get(uid);
//        System.out.println("u1 = "+u1);
        for(int i=0;i<newslist.size();i++)
            if(user_news_view[u1][i]==1)
                view_times++;
        for(int i=0;i<newslist.size() && reclist.size()<30;i++) {
//            System.out.println("view_rec "+newslist.get(i));
            if(user_news_view[u1][i]==0){
//                System.out.println("view_rec "+newslist.get(i));
                double predictsum=0,simsum=0;
                for(int j=0;j<userlist.size();j++){
                    u2=j;
                    int rating2 =user_news_view[u2][i];
                    if(j!=u1 && rating2>=0){
                        predictsum += rating2*view_rec[u1][u2];
                        simsum+=view_rec[u1][u2];
                    }
                }
                NewsBean n1 = news.get(i);
                n1.setReporter(dbUtils.getUserNameFromDBByID(n1.getReporterid()));
                if(simsum==0)
                    n1.setRating(0);
                else
                    n1.setRating((float)(predictsum/simsum));
                reclist.add(n1);
//                System.out.println("view_rec "+n1.getTitle()+" | "+n1.getRating());
            }
        }
//        System.out.println("view times = " + view_times);
        Random rand =new Random();
        dbUtils.closeConnect();

        for(int i=0;i<=reclist.size();i++)
            for(int j=i+1;j<reclist.size();j++)
                if(reclist.get(i).getRating()<reclist.get(j).getRating())
                {
                    NewsBean k = reclist.get(i);
                    reclist.set(i,reclist.get(j));
                    reclist.set(j,k);
                }

//        System.out.println(reclist.size());
//        for(int i=0;i<reclist.size();i++)
//            System.out.println(reclist.get(i).getTitle()+"|"+ reclist.get(i).getRating());

        for(int i=0;i<reclist.size() && i<15;i++)
            System.out.println((reclist.get(i).getRating()+0.1)+"\t|   "+reclist.get(i).getTitle());

        return reclist;
    }
}

