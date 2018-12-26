package com.xsy.tools;

import com.xsy.database.DBUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Crawler {

    List<String> weblist = new ArrayList<String>();
    DBUtils dbUtils = new DBUtils();

    public void getbiznews() {
        try {
            Document doc = Jsoup.connect("http://business.sohu.com/").get();
            Element main_news = doc.getElementById("main-news");
            Elements newslist = main_news.select("div[data-role=\"news-item\"]");
            for (Element news : newslist) {

                Element link = news.select("h4").select("a[href]").get(0);
                weblist.add(link.attr("abs:href"));

//                System.out.println(link.attr("abs:href"));
//                System.out.println(link.text());
                getnews(link.attr("abs:href"),"财经");

            }


//                Elements links = doc.select("a[href]");
//                for(Element link : links){
//                    String StrUrl = link.attr("abs:href");
//                    if(StrUrl.startsWith("http://news.sohu.com/"))//以。。。开头的url
//                        System.out.println(StrUrl);
//                }
        } catch (
                IOException e)

        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void gettravelnews() {
        try {
            Document doc = Jsoup.connect("http://travel.sohu.com/").get();
            Element main_news = doc.getElementById("main-news");
            Elements newslist = main_news.select("div[data-role=\"news-item\"]");
            for (Element news : newslist) {

                Element link = news.select("h4").select("a[href]").get(0);
                weblist.add(link.attr("abs:href"));

//                System.out.println(link.attr("abs:href"));
//                System.out.println(link.text());
                getnews(link.attr("abs:href"),"旅游");

            }

        } catch (
                IOException e)

        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getfashionnews() {
        try {
            Document doc = Jsoup.connect("http://fashion.sohu.com/").get();
            Element main_news = doc.getElementById("main-news");
            Elements newslist = main_news.select("div[data-role=\"news-item\"]");
            for (Element news : newslist) {

                Element link = news.select("h4").select("a[href]").get(0);
                weblist.add(link.attr("abs:href"));

//                System.out.println(link.attr("abs:href"));
//                System.out.println(link.text());
                getnews(link.attr("abs:href"),"时尚");

            }

        } catch (
                IOException e)

        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getitnews() {
        try {
            Document doc = Jsoup.connect("http://it.sohu.com/").get();
            Element main_news = doc.getElementById("main-news");
            Elements newslist = main_news.select("div[data-role=\"news-item\"]");
            for (Element news : newslist) {

                Element link = news.select("h4").select("a[href]").get(0);
                weblist.add(link.attr("abs:href"));

//                System.out.println(link.attr("abs:href"));
//                System.out.println(link.text());
                getnews(link.attr("abs:href"),"科技");

            }

        } catch (
                IOException e)

        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getlearningnews() {
        try {
            Document doc = Jsoup.connect("http://learning.sohu.com/").get();
            Element main_news = doc.getElementById("main-news");
            Elements newslist = main_news.select("div[data-role=\"news-item\"]");
            for (Element news : newslist) {

                Element link = news.select("h4").select("a[href]").get(0);
                weblist.add(link.attr("abs:href"));

                System.out.println(link.attr("abs:href"));
                System.out.println(link.text());
                getnews(link.attr("abs:href"),"教育");

            }

        } catch (
                IOException e)

        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getchihenews() {
        try {
            Document doc = Jsoup.connect("http://chihe.sohu.com/").get();
            Element main_news = doc.getElementById("main-news");
            Elements newslist = main_news.select("div[data-role=\"news-item\"]");
            for (Element news : newslist) {

                Element link = news.select("h4").select("a[href]").get(0);
                weblist.add(link.attr("abs:href"));

//                System.out.println(link.attr("abs:href"));
//                System.out.println(link.text());
                getnews(link.attr("abs:href"),"美食");

            }

        } catch (
                IOException e)

        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getculnews() {
        try {
            Document doc = Jsoup.connect("http://cul.sohu.com/").get();
            Element main_news = doc.getElementById("main-news");
            Elements newslist = main_news.select("div[data-role=\"news-item\"]");
            for (Element news : newslist) {

                Element link = news.select("h4").select("a[href]").get(0);
                weblist.add(link.attr("abs:href"));

//                System.out.println(link.attr("abs:href"));
//                System.out.println(link.text());
                getnews(link.attr("abs:href"),"文化");

            }

        } catch (
                IOException e)

        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getsocietynews() {
        try {
            Document doc = Jsoup.connect("http://society.sohu.com/").get();
            Element main_news = doc.getElementById("main-news");
            Elements newslist = main_news.select("div[data-role=\"news-item\"]");
            for (Element news : newslist) {

                Element link = news.select("h4").select("a[href]").get(0);
                weblist.add(link.attr("abs:href"));

//                System.out.println(link.attr("abs:href"));
//                System.out.println(link.text());
                getnews(link.attr("abs:href"),"社会");

            }

        } catch (
                IOException e)

        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getmilnews() {
        try {
            Document doc = Jsoup.connect("http://mil.sohu.com/").get();
            Element main_news = doc.getElementById("main-news");
            Elements newslist = main_news.select("div[data-role=\"news-item\"]");
            for (Element news : newslist) {

                Element link = news.select("h4").select("a[href]").get(0);
                weblist.add(link.attr("abs:href"));

//                System.out.println(link.attr("abs:href"));
//                System.out.println(link.text());
                getnews(link.attr("abs:href"),"军事");

            }

        } catch (
                IOException e)

        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getgamenews() {
        try {
            Document doc = Jsoup.connect("http://game.sohu.com/").get();
            Element main_news = doc.getElementById("main-news");
            Elements newslist = main_news.select("div[data-role=\"news-item\"]");
            for (Element news : newslist) {

                Element link = news.select("h4").select("a[href]").get(0);
                weblist.add(link.attr("abs:href"));

//                System.out.println(link.attr("abs:href"));
//                System.out.println(link.text());
                getnews(link.attr("abs:href"),"游戏");

            }

        } catch (
                IOException e)

        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getacgnews() {
        try {
            Document doc = Jsoup.connect("http://acg.sohu.com/").get();
            Element main_news = doc.getElementById("main-news");
            Elements newslist = main_news.select("div[data-role=\"news-item\"]");
            for (Element news : newslist) {

                Element link = news.select("h4").select("a[href]").get(0);
                weblist.add(link.attr("abs:href"));

//                System.out.println(link.attr("abs:href"));
//                System.out.println(link.text());
                getnews(link.attr("abs:href"),"动漫");

            }

        } catch (
                IOException e)

        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void gethistorynews() {
        try {
            Document doc = Jsoup.connect("http://history.sohu.com/").get();
            Element main_news = doc.getElementById("main-news");
            Elements newslist = main_news.select("div[data-role=\"news-item\"]");
            for (Element news : newslist) {

                Element link = news.select("h4").select("a[href]").get(0);
                weblist.add(link.attr("abs:href"));

//                System.out.println(link.attr("abs:href"));
//                System.out.println(link.text());
                getnews(link.attr("abs:href"),"历史");

            }

        } catch (
                IOException e)

        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getnews(String webadddress,String label) {
        Document doc = null;
        try {
            doc = Jsoup.connect(webadddress).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element text = doc.select("div[class=\"text\"]").get(0);
        String title = text.select("div[class=\"text-title\"]").get(0).select("h1").get(0).text();
        String reporter = text.select("div[class=\"text-title\"]").get(0).select("span[data-role=\"original-link\"]").get(0).text();
        Elements tag = text.select("div[class=\"text-title\"]").get(0).select("span[class=\"tag\"]").get(0).getElementsByTag("a");
        //System.out.println(title+"  "+reporter);
//        for(Element e : tag)
//            System.out.println(e.text());
        Element newsbody = text.select("article[class=\"article\"]").get(0);
        String newstext="";
        for(Element e : newsbody.select("p"))
            newstext = newstext+"\n"+e.text();
//        System.out.println(newstext);
        dbUtils.openConnect(); // 打开数据库连接
        ResultSet rs;
        int  reporterid;
        reporterid=dbUtils.getUserIDFromDBByName(reporter);
        if(reporterid < 0)
        {
            dbUtils.insertReporterToDB(reporter);
            reporterid=dbUtils.getUserIDFromDBByName(reporter);
        }
//        System.out.println("reporterid" + reporterid);
        if (dbUtils.NewsIsExistInDB(title)){
            //data.setData(userBean);

        }else if(dbUtils.insertNewsToDB(title,title,newstext,reporterid)){
            //data.setData(newsdata);
            System.out.println("database error | "+title);
        }else{
            rs= dbUtils.getNewsFromDBBYTitle(title);
            try {
                rs.next();
                System.out.println("successful! title = "+rs.getString("Title"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int newsid=0,labelid=0;
            rs =dbUtils.getNewsFromDBBYTitle(title);
            try {
                if(rs != null){
                    rs.next();
                    newsid = rs.getInt("ID");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            labelid = dbUtils.getLabelIDFromDBBYLabel(label);
            dbUtils.setLabeltoNews(newsid,labelid);
        }

        dbUtils.closeConnect();
        return;
    }

}