package com.huiche.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */
public class LocationBean {


    public int status;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * ci_name : 中山
         */

        public List<CityhistoryBean> cityhistory;
        /**
         * ci_id : 95
         * ci_name : 中山
         */

        public List<HotcityBean> hotcity;
        /**
         * ci_id : 36
         * ci_name : 安庆
         */

        public List<CitylistBean> citylist;

        public static class CityhistoryBean {
            public String ci_name;
        }

        public static class HotcityBean {
            public String ci_id;
            public String ci_name;
        }

        public static class CitylistBean {
            public String ci_id;
            public String ci_name;

            public CitylistBean(String ci_id,String ci_name) {
                this.ci_id = ci_id;
                this.ci_name = ci_name;

            }
        }
    }
}
