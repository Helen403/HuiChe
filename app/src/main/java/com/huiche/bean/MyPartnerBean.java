package com.huiche.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/8.
 */
public class MyPartnerBean {


    /**
     * status : 10044
     * msg : 申请提交成功
     * data : {"commission":"0.00","partner":[{"co_name":"总公司","co_score":"0.0","co_img":"http://hyh2.281.com.cn/Public/header.jpg","co_area":"中山西区","distance":"2038.649千米"},{"co_name":"中山市代理","co_score":"0.0","co_img":"http://hyh2.281.com.cn/Public/header.jpg","co_area":"中山西区","distance":"1880.741千米"},{"co_name":"好好洗车美容","co_score":"0.0","co_img":"http://hyh2.281.com.cn/Public/header.jpg","co_area":"中山西区","distance":"17米"}]}
     */

    public int status;
    public String msg;
    /**
     * commission : 0.00
     * partner : [{"co_name":"总公司","co_score":"0.0","co_img":"http://hyh2.281.com.cn/Public/header.jpg","co_area":"中山西区","distance":"2038.649千米"},{"co_name":"中山市代理","co_score":"0.0","co_img":"http://hyh2.281.com.cn/Public/header.jpg","co_area":"中山西区","distance":"1880.741千米"},{"co_name":"好好洗车美容","co_score":"0.0","co_img":"http://hyh2.281.com.cn/Public/header.jpg","co_area":"中山西区","distance":"17米"}]
     */

    public DataBean data;

    public static class DataBean {
        public String commission;
        /**
         * co_name : 总公司
         * co_score : 0.0
         * co_img : http://hyh2.281.com.cn/Public/header.jpg
         * co_area : 中山西区
         * distance : 2038.649千米
         */

        public List<PartnerBean> partner;

        public static class PartnerBean {
            public String co_name;
            public String co_score;
            public String co_img;
            public String co_area;
            public String distance;
        }
    }
}
