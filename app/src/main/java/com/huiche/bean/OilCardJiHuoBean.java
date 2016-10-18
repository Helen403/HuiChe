package com.huiche.bean;

/**
 * Created by Administrator on 2016/10/18.
 */
public class OilCardJiHuoBean {

    /**
     * status : 10036
     * msg : 激活成功
     * data : {"us_name":"用户昵称已经存在","us_card":"2999644761","us_money":0}
     */

    public int status;
    public String msg;
    /**
     * us_name : 用户昵称已经存在
     * us_card : 2999644761
     * us_money : 0
     */

    public DataBean data;

    public static class DataBean {
        public String us_name;
        public String us_card;
        public int us_money;
    }
}
