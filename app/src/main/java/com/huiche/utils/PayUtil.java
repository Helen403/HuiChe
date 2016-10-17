package com.huiche.utils;

import android.content.Context;

import com.huiche.bean.PayOrderInfo;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016/8/23 0023.
 * 支付工具类
 */
public  class PayUtil {
    /**
     * create the order info. 支付宝 创建订单信息
     */
    public static String getOrderInfo(PayOrderInfo info) {


        // 签约合作者身份ID
        String orderInfo = "partner=" + info.partner;

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + info.seller_id;

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + info.out_trade_no;

        // 商品名称
        orderInfo += "&subject=" + info.subject;

        // 商品详情
        // orderInfo += "&body=" + "\""+"商品名称"+"\"" ;
        orderInfo += "&body=" + info.body;

        // 商品金额
        // orderInfo += "&total_fee=" +"\""+ price+"\"";
        orderInfo += "&total_fee=" + info.total_fee;

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + info.notify_url;

        // 服务接口名称， 固定值
        orderInfo += "&service=" + info.service;

        // 支付类型， 固定值
        orderInfo += "&payment_type=" + info.payment_type;

        // 参数编码， 固定值
        orderInfo += "&_input_charset=" + info._input_charset;
        // orderInfo += "&_input_charset="+"\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        // orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        // orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";
        String sign="";
        try {
            /**
             * 仅需对sign 做URL编码
             */
           sign = URLEncoder.encode(info.sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        orderInfo = orderInfo + "&sign=\"" + sign + "\"&" + "sign_type=" + info.sign_type;
//        Log.i("alipay", "orderInfo:" + orderInfo);
        return orderInfo;

    }
    public static void wechatPay(Context context,PayOrderInfo payOrderInfo) {
        IWXAPI api= WXAPIFactory.createWXAPI(context, payOrderInfo.appid);
        PayReq req = new PayReq();
        req.appId = payOrderInfo.appid;
        req.partnerId = payOrderInfo.mch_id;
        req.nonceStr = payOrderInfo.noncestr;
        req.packageValue = payOrderInfo.packages;
        // req.packageValue="Sign=WXPay";
        req.prepayId = payOrderInfo.prepayid;
        req.sign = payOrderInfo.sign;
        req.timeStamp = payOrderInfo.timestamp;
        api.registerApp(payOrderInfo.appid);
        //检查是否安装微信或微信版本是否支持微信支付
        if(api.isWXAppInstalled()){
            if(api.isWXAppSupportAPI()){
                api.sendReq(req);
            }else{
                ToastUtils.ToastShowShort(context, "当前微信版本不支持微信支付，请更新版本！");
            }
        }else{
            ToastUtils.ToastShowShort(context, "请安装微信客户端！");
        }

    }

}
