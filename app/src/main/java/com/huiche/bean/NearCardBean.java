package com.huiche.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/27.
 */
public class NearCardBean {


    /**
     * status : 10028
     * msg : 获取数据成功
     * data : [{"vo_id":"14","vo_company":"2","vo_money":"500.00","lat":"22.533608","lng":"113.348317"}]
     */

    public int status;
    public String msg;
    /**
     * vo_id : 14
     * vo_company : 2
     * vo_money : 500.00
     * lat : 22.533608
     * lng : 113.348317
     */

    public List<DataBean> data;

    public static class DataBean {
        public String vo_id;
        public String vo_company;
        public String vo_money;
        public String lat;
        public String lng;
    }
}
