package com.onemeter.mynewcamera.entity;

/**
 * 描述：登陆返回结果实体类
 * 项目名称：MyCamera
 * 时间：2016/6/13 17:44
 * 备注：
 */
public class userInfo {
    private  String  realname;//昵称
    private  String  token  ;//用户token
    private  String  uid;//用户ID
    private  String  password;//密码
    private  String  anyan_username;//安眼登陆账号


    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAnyan_username() {
        return anyan_username;
    }

    public void setAnyan_username(String anyan_username) {
        this.anyan_username = anyan_username;
    }
}
