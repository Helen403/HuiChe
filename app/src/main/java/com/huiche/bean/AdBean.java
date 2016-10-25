package com.huiche.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */
public class AdBean {

    /**
     * status : 10028
     * msg : 获取数据成功
     * data : [{"ad_id":"7","ad_imgurl":"http://dealer2.autoimg.cn/dealerdfs/g8/M0C/AA/19/620x0_1_q87_autohomedealer__wKjBz1YdwVeAemgTAAJ77g3Zbuc855.jpg","ad_title":"5折商品","ad_company":"3"},{"ad_id":"13","ad_imgurl":"http://dealer2.autoimg.cn/dealerdfs/g8/M0C/AA/19/620x0_1_q87_autohomedealer__wKjBz1YdwVeAemgTAAJ77g3Zbuc855.jpg","ad_title":"intel","ad_company":"3"},{"ad_id":"14","ad_imgurl":"http://dealer2.autoimg.cn/dealerdfs/g8/M0C/AA/19/620x0_1_q87_autohomedealer__wKjBz1YdwVeAemgTAAJ77g3Zbuc855.jpg","ad_title":"苹果","ad_company":"5"}]
     */

    public int status;
    public String msg;
    /**
     * ad_id : 7
     * ad_imgurl : http://dealer2.autoimg.cn/dealerdfs/g8/M0C/AA/19/620x0_1_q87_autohomedealer__wKjBz1YdwVeAemgTAAJ77g3Zbuc855.jpg
     * ad_title : 5折商品
     * ad_company : 3
     */

    public List<DataBean> data;

    public static class DataBean {
        public String ad_id;
        public String ad_imgurl;
        public String ad_title;
        public String ad_company;
    }
}
