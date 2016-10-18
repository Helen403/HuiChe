package com.huiche.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class MyCollectionBean {

    /**
     * status : 10028
     * msg : 获取数据成功
     * data : [{"col_id":"1","goods_id":"3","goods_name":"1","goods_img":"http://hyh2.281.com.cn/Public/Uploads/20161008/57f8aac20febc.jpg","goods_mark":"3.00000","distance":"17米"},{"col_id":"4","goods_id":"14","goods_name":"123","goods_img":"http://hyh2.281.com.cn/Public/Uploads/20161009/57f9af759a2a9.png","goods_mark":"2.50000","distance":"17千米"}]
     */

    public int status;
    public String msg;
    /**
     * col_id : 1
     * goods_id : 3
     * goods_name : 1
     * goods_img : http://hyh2.281.com.cn/Public/Uploads/20161008/57f8aac20febc.jpg
     * goods_mark : 3.00000
     * distance : 17米
     */

    public List<DataBean> data;

    public static class DataBean {
        public String col_id;
        public String goods_id;
        public String goods_name;
        public String goods_img;
        public String goods_mark;
        public String distance;
    }
}
