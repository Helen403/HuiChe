package com.huiche.base;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */
public class CarManagerFisterBean {


    /**
     * status : 10028
     * msg : 获取数据成功
     * data : {"us_name":"zeros","us_id":"160","car":[{"ca_id":"7","ca_number":"粤T JQ891","ca_brand":"日产(进口)","ca_model":"途乐","ca_buy":"1472659200","ca_inspect":"1473868800","ca_lube":"1473955200"},{"ca_id":"15","ca_number":"粤T T0760","ca_brand":"奥迪","ca_model":"Q7","ca_buy":"1476115200","ca_inspect":"1476115200","ca_lube":"1476201600"}]}
     */

    public int status;
    public String msg;
    /**
     * us_name : zeros
     * us_id : 160
     * car : [{"ca_id":"7","ca_number":"粤T JQ891","ca_brand":"日产(进口)","ca_model":"途乐","ca_buy":"1472659200","ca_inspect":"1473868800","ca_lube":"1473955200"},{"ca_id":"15","ca_number":"粤T T0760","ca_brand":"奥迪","ca_model":"Q7","ca_buy":"1476115200","ca_inspect":"1476115200","ca_lube":"1476201600"}]
     */

    public DataBean data;

    public static class DataBean {
        public String us_name;
        public String us_id;
        /**
         * ca_id : 7
         * ca_number : 粤T JQ891
         * ca_brand : 日产(进口)
         * ca_model : 途乐
         * ca_buy : 1472659200
         * ca_inspect : 1473868800
         * ca_lube : 1473955200
         */

        public List<CarBean> car;

        public static class CarBean {
            public String ca_id;
            public String ca_number;
            public String ca_brand;
            public String ca_model;
            public String ca_buy;
            public String ca_inspect;
            public String ca_lube;
        }
    }
}
