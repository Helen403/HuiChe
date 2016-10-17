package com.huiche.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */
public class MyCommitBean {


    /**
     * status : 10028
     * msg : 获取数据成功
     * data : {"headerimg":"20161012/57fdef448815e.png","us_name":"zeros","evaconut":"4","evaluate":[{"ev_id":"4","ev_mark":"1.0","ev_text":"相当不错","ev_date":"2016.10.10","co_id":"1","co_name":"总公司","us_name":"zeros","headerimg":"20161012/57fdef448815e.png"},{"ev_id":"5","ev_mark":"5.0","ev_text":"价格不贵","ev_date":"2016.08.29","co_id":"1","co_name":"总公司","us_name":"zeros","headerimg":"20161012/57fdef448815e.png"}]}
     */

    public int status;
    public String msg;
    /**
     * headerimg : 20161012/57fdef448815e.png
     * us_name : zeros
     * evaconut : 4
     * evaluate : [{"ev_id":"4","ev_mark":"1.0","ev_text":"相当不错","ev_date":"2016.10.10","co_id":"1","co_name":"总公司","us_name":"zeros","headerimg":"20161012/57fdef448815e.png"},{"ev_id":"5","ev_mark":"5.0","ev_text":"价格不贵","ev_date":"2016.08.29","co_id":"1","co_name":"总公司","us_name":"zeros","headerimg":"20161012/57fdef448815e.png"}]
     */

    public DataBean data;

    public static class DataBean {
        public String headerimg;
        public String us_name;
        public String evaconut;
        /**
         * ev_id : 4
         * ev_mark : 1.0
         * ev_text : 相当不错
         * ev_date : 2016.10.10
         * co_id : 1
         * co_name : 总公司
         * us_name : zeros
         * headerimg : 20161012/57fdef448815e.png
         */

        public List<EvaluateBean> evaluate;

        public static class EvaluateBean {
            public String ev_id;
            public String ev_mark;
            public String ev_text;
            public String ev_date;
            public String co_id;
            public String co_name;
            public String us_name;
            public String headerimg;
        }
    }
}
