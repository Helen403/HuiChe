package com.huiche.bean;

/**
 * Created by Administrator on 2016/10/14.
 */
public class LoginResultBean  {


    /**
     * status : 10017
     * msg : 登录成功
     * data : {"id":"164","username":null,"sex":"0","birthday":"1970-01-01","headerimg":"http://localhost/hyh_svn/Public/header.jpg","points":"0","vol":"0","collect":0}
     */

    public int status;
    public String msg;
    /**
     * id : 164
     * username : null
     * sex : 0
     * birthday : 1970-01-01
     * headerimg : http://localhost/hyh_svn/Public/header.jpg
     * points : 0
     * vol : 0
     * collect : 0
     */

    public DataBean data;

    public static class DataBean {
        public String id;
        public String username;
        public String sex;
        public String birthday;
        public String headerimg;
        public String points;
        public String vol;
        public int collect;
    }
}
