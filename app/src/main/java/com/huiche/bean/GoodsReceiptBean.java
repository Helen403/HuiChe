package com.huiche.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/30.
 */
public class GoodsReceiptBean {


    /**
     * status : 10028
     * msg : 获取数据成功
     * data : [{"ar_id":"1","ar_user":"168","ar_address":"西区富华道43号","ar_sex":"1","ar_province":"6","ar_city":"95","ar_town":"844","province_name":"广东","city_name":"中山","town_name":"石岐街道","defalut":"0","ar_time":"2016-10-18"},{"ar_id":"2","ar_user":"168","ar_address":"东区兴中道10好","ar_sex":"1","ar_province":"6","ar_city":"95","ar_town":"845","province_name":"广东","city_name":"中山","town_name":"东区街道","defalut":"0","ar_time":"2016-10-18"}]
     */

    public int status;
    public String msg;
    /**
     * ar_id : 1
     * ar_user : 168
     * ar_address : 西区富华道43号
     * ar_sex : 1
     * ar_province : 6
     * ar_city : 95
     * ar_town : 844
     * province_name : 广东
     * city_name : 中山
     * town_name : 石岐街道
     * defalut : 0
     * ar_time : 2016-10-18
     */

    public List<DataBean> data;

    public static class DataBean {
        public String ar_id;
        public String ar_user;
        public String ar_address;
        public String ar_sex;
        public String ar_province;
        public String ar_city;
        public String ar_town;
        public String province_name;
        public String city_name;
        public String town_name;
        public String defalut;
        public String ar_time;
    }
}
