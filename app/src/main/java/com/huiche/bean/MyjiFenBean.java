package com.huiche.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */
public class MyjiFenBean {


    /**
     * status : 10028
     * msg : 获取数据成功
     * data : [{"up_id":"1","up_date":"1973-07-22","up_itme":"兑换商品","up_int":"-10"},{"up_id":"2","up_date":"1973-11-28","up_itme":"预约买单","up_int":"10"}]
     */

    public int status;
    public String msg;
    /**
     * up_id : 1
     * up_date : 1973-07-22
     * up_itme : 兑换商品
     * up_int : -10
     */

    public List<DataBean> data;

    public static class DataBean {
        public String up_id;
        public String up_date;
        public String up_itme;
        public String up_int;
    }
}
