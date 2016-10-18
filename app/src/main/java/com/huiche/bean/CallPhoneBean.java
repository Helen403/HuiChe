package com.huiche.bean;

/**
 * Created by Administrator on 2016/10/18.
 */
public class CallPhoneBean {


    /**
     * status : 10028
     * msg : 获取数据成功
     * data : {"kf_tel":"0760-88888888"}
     */

    public int status;
    public String msg;
    /**
     * kf_tel : 0760-88888888
     */

    public DataBean data;

    public static class DataBean {
        public String kf_tel;
    }
}
