package com.xsy.object;
import com.google.gson.Gson;

public class UserBean {
    private int id;
    private int authority;
    private int status;

    private int otheruid;

    private String account;
    private String password;
    private String nickname;

    private String signature;
    private String typ;
    private int code;
    private String msg;
    private int follow;
    private int follow_num;
    private int followed_num;

    public int getFollow_num() {
        return follow_num;
    }

    public void setFollow_num(int follow_num) {
        this.follow_num = follow_num;
    }

    public int getFollowed_num() {
        return followed_num;
    }

    public void setFollowed_num(int followed_num) {
        this.followed_num = followed_num;
    }


    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }



    public int getOtheruid() {
        return otheruid;
    }

    public void setOtheruid(int otheruid) {
        this.otheruid = otheruid;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }


    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAuthority(int authority) {
        this.authority = authority;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getStatus() {
        return status;
    }

    public String getNickname() {
        return nickname;
    }

    public int getAuthority() {
        return authority;
    }
    public String getAccount() {
        return account;
    }
    public String getPassword() {
        return password;
    }
    public int getId() {
        return id;
    }
}
