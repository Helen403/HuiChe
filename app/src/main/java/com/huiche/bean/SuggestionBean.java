package com.huiche.bean;

/**
 * Created by Administrator on 2016/10/18.
 */
public class SuggestionBean {

    /**
     * status : 10042
     * msg : 反馈成功
     * data : {"su_title":"app闪退","su_content":"app闪退，ios版","su_mobile":"13258212345"}
     */

    public int status;
    public String msg;
    /**
     * su_title : app闪退
     * su_content : app闪退，ios版
     * su_mobile : 13258212345
     */

    public DataBean data;

    public static class DataBean {
        public String su_title;
        public String su_content;
        public String su_mobile;
    }
}
