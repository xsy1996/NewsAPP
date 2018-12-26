package com.xsy.database;


import java.sql.*;

public class DBUtils {
    private Connection conn;
    private String url = "jdbc:mysql://localhost:3306/newsapp?useUnicode=true&characterEncoding=UTF-8"; // 指定连接数据库的URL
    private String user = "root"; // 指定连接数据库的用户名
    private String password = "april29"; // 指定连接数据库的密码

    private Statement sta;
    private ResultSet rs;

    // 打开数据库连接
    public void openConnect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
//            System.out.println("cannot load driver ERROR: " + e.getMessage());
        }
        try {
            // 加载数据库驱动

            conn = DriverManager.getConnection(url, user, password);// 创建数据库连接
            if (!conn.isClosed()) {
//                System.out.println("database successful"); // 连接成功的提示信息
            }
        } catch (Exception e) {
//            System.out.println("connect fall ERROR: " + e.getMessage());
        }
    }
    //获得查询user表后的数据集
    public ResultSet getUser() {
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from User");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    public ResultSet getNews() {
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from News");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int getRating(int uid,int nid) {
        // 创建 statement对象
        int rating = -1;
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
           // rs = sta.executeQuery("select * from User_News_Manager WHERE UserID = "+uid+" AND NewsID = "+nid);
             rs = sta.executeQuery("select * from Records WHERE UserID = "+uid+" AND NewsID = "+nid+" AND Kind = 'rating'");

            if(rs != null)
            {
                if(rs.next())
                    rating = (int)(rs.getFloat("Rating")+0.1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rating;
    }

    public int getLove(int uid,int nid) {
        // 创建 statement对象
        int rating = 0;
//        System.out.println("select * from User_News_Manager WHERE UserID = "+uid+" AND NewsID = "+nid);
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from User_News_Manager WHERE UserID = "+uid+" AND NewsID = "+nid);
            if(rs != null && rs.next())
            {
                    rating = rs.getInt("Love");
            }else
                insertUserNewsManagerToDB(uid,nid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rating;
    }

    public int getFollow(int uid,int otheruid) {
        // 创建 statement对象
        int rating = 0;
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from User_User_Manager WHERE UserID = "+uid+" AND OtherUserID = "+otheruid);
            if(rs != null)
            {
                if(rs.next())
                    rating = rs.getInt("Follow");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rating;
    }

    public int getView(int uid,int nid) {
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from Records WHERE UserID = "+uid+" AND NewsID = "+nid+" AND Kind = 'view'");
            if(rs != null)
            {
                if(rs.next())
                    return 1;
                else
                    return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 判断数据库中是否存在某个用户名及其密码,注册和登录的时候判断
    public boolean UserIsExistInDB(String account) {
        boolean isFlag = false;
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from User WHERE Account = "+"'"+account+"'");//获得结果集
            if (rs != null) {
                while (rs.next()) {  //遍历结果集
                    if (rs.getString("Account").equals(account)) {
                            isFlag = true;
                            break;
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
            isFlag = false;
        }

        return isFlag;
    }



    //判断
    public boolean UserIsCorrectInDB(String account, String password) {
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from User WHERE Account = "+"'"+account+"' AND Password = "+"'"+password+"'");//获得结果集
           // System.out.println("select * from User WHERE Account = "+"'"+account+"' AND Password = "+"'"+password+"'");
            if(rs.next()==false) {
//                System.out.println("空集");
                return false;
            }
            if (rs != null) {
                do{  //遍历结果集
//                    System.out.println(rs.getString("ID")+"  "+rs.getString("Account")+" "+rs.getString("Password"));
                }while (rs.next());
                return true;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean NewsIsExistInDB(String title) {
        boolean isFlag = false;
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from News WHERE Title = "+"'"+title+"'");//获得结果集
            if (rs != null) {
                while (rs.next()) {  //遍历结果集
                    if (rs.getString("Title").equals(title)) {
                        isFlag = true;
                        break;
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
            isFlag = false;
        }

        return isFlag;
    }



    public boolean LabelIsExistInDB(String label) {
        boolean isFlag = false;
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from Label WHERE Text = "+"'"+label+"'");//获得结果集
            if (rs != null) {
                while (rs.next()) {  //遍历结果集
                    if (rs.getString("Text").equals(label)) {
                        isFlag = true;
                        break;
                    }
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
            isFlag = false;
        }

        return isFlag;
    }

    public ResultSet getUserFromDB(String account, String password) {
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from User WHERE Account = "+"'"+account+"' AND Password = "+"'"+password+"'");//获得结果集
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public ResultSet getRecordsFromDB() {
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from Records");//获得结果集
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public int getUserNewsIDFromDB(int uid,int nid)
    {
        String sql = "select * from User_News_Manager WHERE UserID = "+uid+" AND NewsID = "+nid;
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery(sql);//获得结果集
            if(rs != null)
            {
                if(rs.next()) {
                    return rs.getInt("ID");
                }else
                    return -1;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getUserUserIDFromDB(int uid,int otheruid)
    {
        String sql = "select * from User_User_Manager WHERE UserID = "+uid+" AND OtherUserID = "+otheruid;
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery(sql);//获得结果集
            if(rs != null)
            {
                if(rs.next()) {
                    return rs.getInt("ID");
                }else
                {
                    insertUserUserManagerToDB(uid,otheruid);
                    return -1;
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String  getUserNameFromDBByID(int id) {
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from User WHERE ID = "+id);//获得结果集
            if(rs != null){
                if( rs.next())
                    return rs.getString("Nickname");
                else
                    return "";
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return "";
    }

    public int  getUserIDFromDBByName(String name) {
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from User WHERE Nickname = '"+name+"'");//获得结果集
            if(rs != null){
                if(rs.next())
                    return rs.getInt("ID");
                else
                    return -1;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public ResultSet getNewsFromDBBYTitle(String title) {
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from News WHERE Title = "+"'"+title+"'");//获得结果集
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public ResultSet getNewsFromDBBYID(int id) {
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from News WHERE ID = "+"'"+id+"'");//获得结果集
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public ResultSet getUserFromDBBYID(int id) {
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from User WHERE ID = "+"'"+id+"'");//获得结果集
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public ResultSet getNewsListFromDB() {
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from News");//获得结果集
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public ResultSet getNewsListFromDBBYReporterID(int uid) {
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from News WHERE ReporterID = "+uid);//获得结果集
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getLoveNewsListFromDBBYUserID(int uid) {
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from News n WHERE n.Pass = 1 AND EXISTS(SELECT * FROM User_News_Manager WHERE NewsID =n.ID AND UserID = "+uid+" AND Love = 1) ");//获得结果集
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    public ResultSet getNewsListFromDBBYKeyword(String Keyword) {
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from News WHERE Title LIKE binary '%"+Keyword+"%'");//获得结果集
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    public ResultSet getNoJudgeNewsListFromDB() {
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from News WHERE Pass = 0 ORDER BY Create_time DESC ");//获得结果集
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }


    public ResultSet getNewsListFromDBBYReporterIDANDStatus(int uid,int statuscode) {
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
//            System.out.println("uid = "+uid+" | status = "+statuscode +" | select * from News WHERE ReporterID = "+uid+" AND Pass = "+statuscode);
            rs = sta.executeQuery("select * from News WHERE ReporterID = "+uid+" AND Pass = "+statuscode);//获得结果集
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getNewsListFromDBBYLabelID(int lid) {
        // 创建 statement对象
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
//           System.out.println("select * from News  WHERE ID IN (SELECT NewsID FROM News_Label_Manager WHERE LabelID ="+lid +") AND Pass=1");
            rs = sta.executeQuery("select * from News  WHERE ID IN (SELECT NewsID FROM News_Label_Manager WHERE LabelID ="+lid +") AND Pass=1 ORDER BY Create_time DESC " );//获得结果集
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public ResultSet getUserListFromDBBYUserIDANDTyp(int uid,String typ) {
        // 创建 statement对象
        String sql="";
        if(typ.equals("follow"))
            sql="select * from User u  WHERE ID IN (SELECT OtherUserID FROM User_User_Manager WHERE UserID ="+uid +" AND Follow = 1)";
        else if(typ.equals("followed"))
            sql="select * from User u  WHERE ID IN (SELECT UserID FROM User_User_Manager WHERE OtherUserID ="+uid +" AND Follow = 1)";

//        System.out.println(sql);

        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery(sql);//获得结果集
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public int getUserNumFromDBBYUserIDANDTyp(int uid,String typ) {
        // 创建 statement对象
        String sql="";
        if(typ.equals("follow"))
            sql="select COUNT(*) AS Num from User u  WHERE ID IN (SELECT OtherUserID FROM User_User_Manager WHERE UserID ="+uid +" AND Follow = 1)";
        else if(typ.equals("followed"))
            sql="select COUNT(*) AS Num from User u  WHERE ID IN (SELECT UserID FROM User_User_Manager WHERE OtherUserID ="+uid +" AND Follow = 1)";

//        System.out.println(sql);

        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery(sql);//获得结果集
            if(rs!=null)
            {
                if(rs.next())
                {
//                    System.out.println(rs.getInt("Num"));
                    return rs.getInt("Num");
                }
                else
                    return 0;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getLabelIDFromDBBYLabel(String label)
    {
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from Label WHERE Text = '"+label+"'" );//获得结果集
            if(rs != null)
            {
                if(rs.next())
                {
//                    System.out.println("labelid = "+rs.getInt("ID"));
                    return rs.getInt("ID");
                }
                else
                    return -1;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public ResultSet getLabelFromDBBYUserID(int uid)
    {
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from Label WHERE ID IN (SELECT LabelID FROM User_Label_Manager WHERE UserID = "+uid+")" );//获得结果集

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getChosenLabelFromDBBYNewsID(int nid)
    {
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from Label WHERE ID IN (SELECT LabelID FROM News_Label_Manager WHERE NewsID = "+nid+")" );//获得结果集

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getfreeLabelFromDBBYNewsID(int nid)
    {
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            if(nid == -1)
                rs = sta.executeQuery("select * from Label LIMIT 10" );//获得结果集
            else
                rs = sta.executeQuery("select * from Label WHERE ID NOT IN (SELECT LabelID FROM News_Label_Manager WHERE NewsID = "+nid +") LIMIT 10" );//获得结果集
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getChosenLabelFromDBBYUserID(int uid)
    {
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery("select * from Label WHERE ID IN (SELECT LabelID FROM User_Label_Manager WHERE UserID = "+uid+")" );//获得结果集

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getfreeLabelFromDBBYUserID(int uid)
    {
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            if(uid == -1)
                rs = sta.executeQuery("select * from Label LIMIT 10" );//获得结果集
            else
                rs = sta.executeQuery("select * from Label WHERE ID NOT IN (SELECT LabelID FROM User_Label_Manager WHERE UserID = "+uid +") LIMIT 10" );//获得结果集
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public boolean setLabeltoNews(int newsid,int labelid){

        String sql = " insert into News_Label_Manager (NewsID, LabelID) values ( " +newsid +","+ labelid+" )";
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            return sta.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    //注册  将用户名和密码插入到数据库(id设置的是自增长的，因此不需要插入)
    public boolean insertUserToDB(String username, String password,int authority){
        String sql = " insert into User ( Account , Password , Authority) values ( " + "'" + username
                + "', '" + password + "',"+authority+" )";
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            return sta.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertReporterToDB(String name){
        String sql = " insert into User ( Account , Password , Authority, Nickname) values ( " + "'" + name
                + "', '12345678',2 ,'" +name+ "')";
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            return sta.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertNewsToDB(String title, String subtitle,String text, int reporterid){
        String sql = " insert into News ( Title, Subtitle, Text, ReporterID) values ( " + "'" + title
                + "', '" + subtitle + "', '" + text + "', " + reporterid +" )";
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            return sta.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean insertLabelToDB(String label){
        if(LabelIsExistInDB(label))
            return false;
//        System.out.println(" insert into Label (Text) values ( '" + label+"' )");
        String sql = " insert into Label (Text) values ( '" + label+"' )";
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            return sta.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertRatingRecToDB(int uid, int nid, float rating){
        String sql = " insert into  Records(Kind,UserID,NewsID,Rating) values ( 'rating'," + uid + "," + nid + ","+rating+" )";
//        System.out.println("sql");
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            updateUserNewsRatingToDB(uid,nid,rating);
            return sta.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateReviewToDB(int nid,int pass){
        if(nid < 0)
        {
//            System.out.println("review nid = -1");
            return true;
        }
        String sql = " UPDATE News SET Pass = "+pass+" WHERE ID = "+nid;
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            return sta.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertViewRecToDB(int uid, int nid){
        String sql = " insert into  Records(Kind,UserID,NewsID) values ( 'view'," + uid + "," + nid + " )";
//        System.out.println("sql");
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            return sta.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertUserNewsManagerToDB(int uid, int nid){
        String sql = " insert into  User_News_Manager(UserID,NewsID) values ( " + uid + "," + nid + " )";
//        System.out.println(sql);
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            return sta.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertUserUserManagerToDB(int uid, int otheruid){
        String sql = " insert into  User_User_Manager(UserID,OtherUserID) values ( " + uid + "," + otheruid + " )";
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            return sta.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean updateUserNewsRatingToDB(int uid, int nid,float rating){
        int id = getUserNewsIDFromDB(uid,nid);
        if(id < 0)
        {
            insertUserNewsManagerToDB(uid,nid);
            id = getUserNewsIDFromDB(uid,nid);
        }
        String sql = " UPDATE User_News_Manager SET Rating = "+rating+" WHERE ID = "+id;
//        System.out.println(sql);
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            return sta.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUserNewsLoveToDB(int uid, int nid,int love){
        int id = getUserNewsIDFromDB(uid,nid);
        if(id < 0)
        {
            insertUserNewsManagerToDB(uid,nid);
            id = getUserNewsIDFromDB(uid,nid);
//            System.out.println(id);
        }
        String sql = " UPDATE User_News_Manager SET Love = "+love+" WHERE ID = "+id;
        System.out.println(sql);
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            return sta.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUserFollowToDB(int uid, int otheruid,int follow){
        int id = getUserUserIDFromDB(uid,otheruid);
        if(id < 0)
        {
            insertUserUserManagerToDB(uid,otheruid);
            id = getUserUserIDFromDB(uid,otheruid);
        }
        String sql = " UPDATE User_User_Manager SET Follow = "+follow+" WHERE ID = "+id;
//        System.out.println(sql);
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            return sta.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUserNameToDB(int uid, String name){
        String sql = " UPDATE User SET Nickname = '"+name+"' WHERE ID = "+uid;
//        System.out.println(sql);
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            return sta.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertUserLabelManagerToDB(String label, int uid){
        if(insertLabelToDB(label)) {
//            System.out.println("insert label error"+" | "+label);
            return true;
        }
        int lid = getLabelIDFromDBBYLabel(label);
        if(lid<0){
            System.out.println("label id <0 "+" | "+label);
            return true;
        }
//        System.out.println(" insert into User_Label_Manager(UserID, LabelID) values ( "+uid+", "+lid+")");
        String sql = " insert into User_Label_Manager(UserID, LabelID) values ( "+uid+", "+lid+")";
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            return sta.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeLabelManagerFromBDBYUserID(int uid){
        String sql = " DELETE FROM User_Label_Manager WHERE UserID = "+uid;
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            return sta.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 关闭数据库连接
    public void closeConnect() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (sta != null) {
                sta.close();
            }
            if (conn != null) {
                conn.close();
            }
//            System.out.println("close database");
        } catch (SQLException e) {
//            System.out.println("Error: " + e.getMessage());
        }
    }

    public ResultSet executesqlquery(String sql) {
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            rs = sta.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    public boolean executesql(String sql) {
//        System.out.println(sql);
        try {
            sta = conn.createStatement();
            // 执行SQL查询语句
            return sta.execute(sql);//获得结果集
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}