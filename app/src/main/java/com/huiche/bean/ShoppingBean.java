package com.huiche.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/6.
 */
public class ShoppingBean {


    /**
     * status : 10028
     * msg : 获取数据成功
     * data : [{"goods_id":"17","goods_name":"苹果i5s","goods_img":"http://hyh2.281.com.cn/Public/Uploads/20161008/57f8c1978b1c6.jpg","area":"西区街道 远洋广场","distance":"4.097千米"},{"goods_id":"12","goods_name":"XIAOMI电视","goods_img":"http://hyh2.281.com.cn/Public/Uploads/20161008/57f891c1e5872.jpg","area":"环城街道 京华","distance":"4.579千米"}]
     */

    public int status;
    public String msg;
    /**
     * goods_id : 17
     * goods_name : 苹果i5s
     * goods_img : http://hyh2.281.com.cn/Public/Uploads/20161008/57f8c1978b1c6.jpg
     * area : 西区街道 远洋广场
     * distance : 4.097千米
     */

    public List<DataBean> data;

    public static class DataBean {
        public String goods_id;
        public String goods_name;
        public String goods_img;
        public String area;
        public String distance;
    }
}
