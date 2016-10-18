package com.huiche.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/28.
 */
public class DetailHelenBean {


    /**
     * status : 10028
     * msg : 获取数据成功
     * data : {"commission":[{"com_id":"3","com_user":"168","com_company":"好好洗车美容","com_money":"100.00","com_balance":"100.00","com_type":"0","com_time":"2016-10-18"},{"com_id":"4","com_user":"168","com_company":"4s店","com_money":"500.00","com_balance":"600.00","com_type":"0","com_time":"2016-10-18"},{"com_id":"5","com_user":"168","com_company":"佣金提现","com_money":"-200.00","com_balance":"400.00","com_type":"1","com_time":"2016-10-18"}]}
     */

    public int status;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * com_id : 3
         * com_user : 168
         * com_company : 好好洗车美容
         * com_money : 100.00
         * com_balance : 100.00
         * com_type : 0
         * com_time : 2016-10-18
         */

        public List<CommissionBean> commission;

        public static class CommissionBean {
            public String com_id;
            public String com_user;
            public String com_company;
            public String com_money;
            public String com_balance;
            public String com_type;
            public String com_time;
        }
    }
}
