package com.huiche.constant;

/**
 * Created by SNOY on 2016/8/24.
 */
public interface Constants {


    /**
     * 海龙
     */
    interface Helen {

        String VERIFICATION = "http://hyh2.281.com.cn/api.php/home/user/verify";
        String REGISTER = "http://hyh2.281.com.cn/api.php/home/user/register";
        String LOGIN = "http://hyh2.281.com.cn/api.php/home/user/login";

        String SECURITY = "http://hyh2.281.com.cn/api.php/home/user/user_safe";
        String CHANGEPASSWORD = "http://hyh2.281.com.cn/api.php/home/user/uppassword";
        String CHANGEPHONE = "http://hyh2.281.com.cn/api.php/home/user/setmobile";
        String MYCOMMIT = "http://hyh2.281.com.cn/api.php/home/evaluate/myevaluate";

        String CARMANAGER = "http://hyh2.281.com.cn/api.php/home/car/allcar";
        String EDITCARMANAGER = "http://hyh2.281.com.cn/api.php/home/car/onecar";
        String DELECTCAR = "http://hyh2.281.com.cn/api.php/home/car/delcar";

        String CALLPHONE = "http://hyh2.281.com.cn/api.php/home/user/contact";

        String GETUSERPOINT = "http://hyh2.281.com.cn/api.php/home/user/get_user_upoint ";

        String USERPOINT = "http://hyh2.281.com.cn/api.php/home/user/user_shoplog";


        String MYOIlCARD = "http://hyh2.281.com.cn/api.php/home/gas/checkgas";


        String SUGGESTION = "http://hyh2.281.com.cn/api.php/home/suggestion/addsuggestion";


        String ADDPARTER = "http://hyh2.281.com.cn/api.php/home/partner/addpartner";
        String ADDPARTERADD = "http://hyh2.281.com.cn/api.php/home/partner/allpartner";


        String MYCOLLECTION = "http://hyh2.281.com.cn/api.php/home/collect/mycollect";
        String DELECTMYCOLLECTION = "http://hyh2.281.com.cn/api.php/home/collect/delcollect";

        String MYCOLLECTIONDETAIL = "http://hyh2.281.com.cn/api.php/home/partner/commissionlist";
    }

    interface startActivityForResult {
        //登录
        int LOGIN = 0X55;
        int LOGINRESULT = 0X54;
        //注销
        int CANCELLATION = 0X53;
        int CANCELLATIONRESULT = 0X52;


    }

    /******************************************************************************************/
    /**
     * 只会加载一次
     * 请求次数只有加载引导页的时候加载一遍，开线程网络请求存放到数据库
     * 把那些请求一遍和请求一百遍不变的json的URL   存放在这里  满足的条件需要  1.不要请求参数
     * URL不管存放的位置---------------随便放
     */
    String[] JSON = new String[]{
            "http://www.ahjmall.com/cloud/get_product_group_v3.json"
    };

    /**
     * 这里就存放特定图片的url  下载完直接存储到SD卡
     * 例如下面的标题栏的图片 或者超大的图片
     * 只会加载一次
     */
    String[] BITMAP = new String[]{
            "http://mmbiz.qpic.cn/mmbiz_jpg/X2Vhfqvibrba9EAlvv5ZMwlgnA5diaGQE6kPgVwpltLQDrdxnYtuXbJvJovQErq9CQC94vFaF4Q2MPR3ib7aiagZ1g/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1"
    };


}
