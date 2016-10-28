package com.huiche.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/28.
 */
public class NearCardInfoBean {


    /**
     * status : 10028
     * msg : 获取数据成功
     * data : [{"vo_id":"14","vo_company":"2","vo_money":"500.00","co_name":"中山市代理","co_address":"中山市富华总站"}]
     */

    public int status;
    public String msg;
    /**
     * vo_id : 14
     * vo_company : 2
     * vo_money : 500.00
     * co_name : 中山市代理
     * co_address : 中山市富华总站
     */

    public List<DataBean> data;

    public static class DataBean {
        public String vo_id;
        public String vo_company;
        public String vo_money;
        public String co_name;
        public String co_address;
    }
}
