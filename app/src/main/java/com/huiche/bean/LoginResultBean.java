package com.huiche.bean;

/**
 * Created by Administrator on 2016/10/14.
 */
public class LoginResultBean  {


    /**
     * status : 10017
     * msg : 登录成功
     * data : {"id":"160","username":"zeros","sex":"1","birthday":"2015-04-05","headerimg":"http://hyh2.281.com.cn/Public/header.jpg","points":"0","vol":"0","collect":0,"partner_state":"0"}
     */

    public int status;
    public String msg;
    /**
     * id : 160
     * username : zeros
     * sex : 1
     * birthday : 2015-04-05
     * headerimg : http://hyh2.281.com.cn/Public/header.jpg
     * points : 0
     * vol : 0
     * collect : 0
     * partner_state : 0
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
        public String partner_state;
    }
}
