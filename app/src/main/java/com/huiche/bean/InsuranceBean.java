package com.huiche.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/27.
 */
public class InsuranceBean {

    /**
     * status : 10028
     * msg : 获取数据成功
     * data : [{"as_id":"1","as_name":"国寿股份"},{"as_id":"2","as_name":"生命人寿"},{"as_id":"3","as_name":"民生人寿"},{"as_id":"4","as_name":"太平人寿"},{"as_id":"5","as_name":"泰康"},{"as_id":"6","as_name":"新华"},{"as_id":"7","as_name":"平安寿"},{"as_id":"8","as_name":"太保寿"}]
     */

    public int status;
    public String msg;
    /**
     * as_id : 1
     * as_name : 国寿股份
     */

    public List<DataBean> data;

    public static class DataBean {
        public String as_id;
        public String as_name;
    }
}
