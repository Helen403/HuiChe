package com.huiche.bean;

/**
 * Created by Administrator on 2016/10/14.
 */
public class PasswordBean {
    String code;
    String pass;
    String user;

    public PasswordBean(String user) {
        this.user = user;
    }

    public PasswordBean(String user, String pass, String code) {
        this.code = code;
        this.pass = pass;
        this.user = user;
    }
}
