package com.huiche.bean;

/**
 * Created by Administrator on 2016/10/17.
 */
public class SecurityInfoBean {

    /**
     * status : 10028
     * msg : 获取数据成功
     * data : {"us_mobile":"13129225731"}
     */

    public int status;
    public String msg;
    /**
     * us_mobile : 13129225731
     */

    public DataBean data;

    public static class DataBean {
        public String us_mobile;
    }
}
