package com.xsy.object;

public class NewsListInputBean {
    int typ;
    String label, keyword;
    int userid,viewuserid,pass;
    //pass = 0 待审核，=1通过 =-1 不通过


    public void setPass(int pass) {
        this.pass = pass;
    }

    public int getPass() {
        return pass;
    }

    public void setViewuserid(int viewuserid) {
        this.viewuserid = viewuserid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUserid() {
        return userid;
    }

    public int getViewuserid() {
        return viewuserid;
    }

    public int getTyp() {
        return typ;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getLabel() {
        return label;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setTyp(int typ) {
        this.typ = typ;
    }
}
