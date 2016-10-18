package com.huiche.bean;

/**
 * Created by Administrator on 2016/10/18.
 */
public class MyPointBean {

    /**
     * status : 10028
     * msg : 获取数据成功
     * data : {"us_points":"0"}
     */

    public int status;
    public String msg;
    /**
     * us_points : 0
     */

    public DataBean data;

    public static class DataBean {
        public String us_points;
    }
}
