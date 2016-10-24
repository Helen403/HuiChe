package com.huiche.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */
public class ProvinceBean {

    /**
     * status : 10028
     * msg : 获取数据成功
     * data : [{"ci_id":"2","ci_name":"北京"},{"ci_id":"3","ci_name":"安徽"},{"ci_id":"4","ci_name":"福建"},{"ci_id":"5","ci_name":"甘肃"},{"ci_id":"6","ci_name":"广东"},{"ci_id":"7","ci_name":"广西"},{"ci_id":"8","ci_name":"贵州"},{"ci_id":"9","ci_name":"海南"},{"ci_id":"10","ci_name":"河北"},{"ci_id":"11","ci_name":"河南"},{"ci_id":"12","ci_name":"黑龙江"},{"ci_id":"13","ci_name":"湖北"},{"ci_id":"14","ci_name":"湖南"},{"ci_id":"15","ci_name":"吉林"},{"ci_id":"16","ci_name":"江苏"},{"ci_id":"17","ci_name":"江西"},{"ci_id":"18","ci_name":"辽宁"},{"ci_id":"19","ci_name":"内蒙古"},{"ci_id":"20","ci_name":"宁夏"},{"ci_id":"21","ci_name":"青海"},{"ci_id":"22","ci_name":"山东"},{"ci_id":"23","ci_name":"山西"},{"ci_id":"24","ci_name":"陕西"},{"ci_id":"25","ci_name":"上海"},{"ci_id":"26","ci_name":"四川"},{"ci_id":"27","ci_name":"天津"},{"ci_id":"28","ci_name":"西藏"},{"ci_id":"29","ci_name":"新疆"},{"ci_id":"30","ci_name":"云南"},{"ci_id":"31","ci_name":"浙江"},{"ci_id":"32","ci_name":"重庆"},{"ci_id":"33","ci_name":"香港"},{"ci_id":"34","ci_name":"澳门"},{"ci_id":"35","ci_name":"台湾"}]
     */

    public int status;
    public String msg;
    /**
     * ci_id : 2
     * ci_name : 北京
     */

    public List<DataBean> data;

    public static class DataBean {
        public String ci_id;
        public String ci_name;
    }
}
