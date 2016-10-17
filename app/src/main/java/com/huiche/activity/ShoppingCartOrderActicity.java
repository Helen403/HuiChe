package com.huiche.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.huiche.R;
import com.huiche.adapter.ShoppingSuperOrderAdapter;
import com.huiche.base.BaseActivity;
import com.huiche.base.MyApplication;
import com.huiche.bean.OrderData;
import com.huiche.bean.PayOrderInfo;
import com.huiche.constant.HttpConstant;
import com.huiche.constant.MyRequestCode;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.PayUtil;
import com.huiche.utils.ToastUtils;
import com.huiche.view.BufferCircleDialog;
import com.huiche.view.MyListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

import alipay.PayResult;


/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class ShoppingCartOrderActicity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private Context mContext;
    private List<String> offerIdList;//生成的订单id数组
    private OrderData orderInfo;
    private ImageButton imb_titleBack;
    private TextView tv_titleText;
    private MyListView lv_orderParentMsg;
    private CheckBox check_save_money;
    private CheckBox checkBox_alipay;
    private CheckBox checkBox_wechat;
    private LinearLayout ll_checkBox_wechat;
    private LinearLayout ll_checkBox_alipay;
    private TextView tv_allIntegral;
    private TextView tv_menberSave;
    private TextView tv_integralExMoney;
    private TextView tv_needPay;
    private TextView tv_returnIntegral;
    private TextView tv_payOrder;
    private RelativeLayout rl_saveMoney;
    private int payType = 2;
    private DecimalFormat formater1;//保留一位小数（用于积分）
    private DecimalFormat formater2;//保留两位小数（用于金额）
    private double menberIntegral;
    private String dixianIntegral;
    private double saveMoney;
    private double allMoney;
    private double payMoney;
    private PayOrderInfo payOrderInfo;
    private static final int SDK_PAY_FLAG = 1;
    private int serviceCost;
    //兑换时取消支付
    private final String DUIHUANFAIL = "DUIHUANFAIL";

    @Override
    public void dealLogicBeforeFindView() {
        mContext = this;
    }

    @Override
    public void findViews() {

        setContentView(R.layout.activity_shopping_order);
        imb_titleBack = (ImageButton) findViewById(R.id.imb_titleBack);
        tv_titleText = (TextView) findViewById(R.id.tv_titleText);
        lv_orderParentMsg = (MyListView) findViewById(R.id.lv_orderParentMsg);
        check_save_money = (CheckBox) findViewById(R.id.check_save_money);
        tv_allIntegral = (TextView) findViewById(R.id.tv_allIntegral_exchangeOrder);
        tv_menberSave = (TextView) findViewById(R.id.tv_menberSave_exchangeOrder);
        tv_integralExMoney = (TextView) findViewById(R.id.tv_integralExMoney);
        tv_needPay = (TextView) findViewById(R.id.tv_needPay_exchangeOrder);
        tv_returnIntegral = (TextView) findViewById(R.id.tv_returnIntegral_exchangeOrder);
        tv_payOrder = (TextView) findViewById(R.id.tv_payOrder_exchangeOrder);
        checkBox_alipay = (CheckBox) findViewById(R.id.checkBox_alipay);
        checkBox_wechat = (CheckBox) findViewById(R.id.checkBox_wechat);
        rl_saveMoney = (RelativeLayout) findViewById(R.id.rl_saveMoney_exchagneOrder);
        ll_checkBox_wechat = (LinearLayout) findViewById(R.id.ll_checkBox_wechat);
        ll_checkBox_alipay = (LinearLayout) findViewById(R.id.ll_checkBox_alipay);
    }

    @Override
    public void initData() {
        formater1 = new DecimalFormat("#0.0");
        formater2 = new DecimalFormat("#0.00");
        tv_titleText.setText("订单支付");
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(MyRequestCode.FINISH);
        registerReceiver(receiver, filter);
        offerIdList = getIntent().getStringArrayListExtra("offerIdList");
        //根据id获取
        if (offerIdList != null) {
            getOrderMessage();
        }
    }

    /**
     * 接收微信支付成功广播，销毁当前activity
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //支付完成后刷新
            if (MyRequestCode.FINISH.equals(intent.getAction())) {
                Intent oktent = new Intent();
                oktent.setClass(ShoppingCartOrderActicity.this, ExchangeSuccessActivity.class);
                startActivity(oktent);
                finish();
            }

        }
    };

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        super.onDestroy();
    }

    @Override
    public void setListeners() {
        imb_titleBack.setOnClickListener(this);
        check_save_money.setOnCheckedChangeListener(this);
        checkBox_alipay.setOnCheckedChangeListener(this);
        checkBox_wechat.setOnCheckedChangeListener(this);
        rl_saveMoney.setOnClickListener(this);
        ll_checkBox_alipay.setOnClickListener(this);
        ll_checkBox_wechat.setOnClickListener(this);
        tv_payOrder.setOnClickListener(this);
    }

    /**
     * 获取订单信息
     */
    private void getOrderMessage() {
        RequestParams params = new RequestParams();
        params.put("offlineOrderId", offerIdList);
        final BufferCircleDialog dialog = new BufferCircleDialog(mContext);
        dialog.show("正在加载", true, null);
        AsyncHttp.post(HttpConstant.ORDER_MSG, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                dialog.dialogcancel();
                ToastUtils.ToastShowShort(mContext, "网络异常");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                dialog.dialogcancel();
//                Log.d("ss", response.toString());
                String status = response.optString("status");
                if (status.equals("0")) {
                    JSONObject obj = response.optJSONObject("rows");
                    orderInfo = JSON.parseObject(obj.toString(), OrderData.class);
                    if (orderInfo != null)
                        setOrderMsg();

                }
            }
        });
    }

    /**
     * 设置订单信息
     */
    private void setOrderMsg() {
        lv_orderParentMsg.setAdapter(new ShoppingSuperOrderAdapter(mContext, orderInfo.getData()));
        tv_allIntegral.setText("订单总额" + orderInfo.getAllIntegral() + "积分");
        double allIntegral = orderInfo.getAllIntegral();//订单总额
        double maxDiIntegral = allIntegral * 0.9;//最多抵现订单的90%
        double diIntegral = 0;//最多抵现订单的90%
        menberIntegral = orderInfo.getMenberIntegral();
        if (maxDiIntegral > menberIntegral)
            diIntegral = menberIntegral;
        else
            diIntegral = maxDiIntegral;
        dixianIntegral = formater1.format(diIntegral);
        tv_integralExMoney.setText("-" + dixianIntegral + "积分/" + menberIntegral + "积分");
        saveMoney = diIntegral / 10;
        allMoney = allIntegral / 10;
        payMoney = 0;
        tv_menberSave.setText("-" + formater2.format(saveMoney) + "元");//换比是10:1
        if (check_save_money.isChecked())
            payMoney = allMoney - saveMoney;
        else
            payMoney = allMoney;
        String relPay = formater2.format(payMoney);
        tv_needPay.setText("还需支付:" + relPay + "元");
        tv_payOrder.setText("确认支付" + relPay + "元");
        //先用第一个返回积分比例算
        serviceCost = orderInfo.getData().get(0).getBusinessStore().getServiceCost();
        double integral = Double.parseDouble(relPay) * serviceCost * 0.01;
        tv_returnIntegral.setText("返还" + formater1.format(integral) + "积分");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imb_titleBack:
                finish();
                break;
            case R.id.ll_checkBox_wechat:
                if (checkBox_wechat.isChecked())
                    checkBox_wechat.setChecked(false);
                else
                    checkBox_wechat.setChecked(true);
                break;
            case R.id.ll_checkBox_alipay:
                if (checkBox_alipay.isChecked())
                    checkBox_alipay.setChecked(false);
                else
                    checkBox_alipay.setChecked(true);
                break;
            case R.id.rl_saveMoney_exchagneOrder:
                if (check_save_money.isChecked())
                    check_save_money.setChecked(false);
                else
                    check_save_money.setChecked(true);
                break;
            //确认支付
            case R.id.tv_payOrder_exchangeOrder:
                if (!checkBox_alipay.isChecked() && !checkBox_wechat.isChecked()) {
                    ToastUtils.ToastShowShort(mContext, "请选择支付方式");
                    return;
                }
                if (checkBox_wechat.isChecked())
                    payType = 1;
                if (checkBox_alipay.isChecked())
                    payType = 2;
                comfirmPayOrder();
                break;
        }
    }

    /**
     * 确认支付
     */
    private void comfirmPayOrder() {
        RequestParams params = new RequestParams();
        params.put("orderId", offerIdList);
        if (check_save_money.isChecked())
            params.put("useIntegralAmount", dixianIntegral);
        else
            params.put("useIntegralAmount", "0");
        params.put("paymentType", payType);
//        params.put("cautionId",);
        params.put("useWebPay", 1);
        final BufferCircleDialog dialog = new BufferCircleDialog(mContext);
        dialog.show("", true, null);
        AsyncHttp.posts(HttpConstant.SETTLE_PAY, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                dialog.dialogcancel();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                dialog.dialogcancel();
                String status = response.optString("status");
                String msg = response.optString("msg");
                if (status.equals("0")) {
                    JSONObject rows = response.optJSONObject("rows");
                    String code = rows.optString("code");
                    JSONObject value = rows.optJSONObject("value");

                    JSONObject obj = value.optJSONObject("obj");
                    payOrderInfo = JSON.parseObject(obj.toString(), PayOrderInfo.class);
                    if (payType == 1)//微信支付
                        //兑换进入的微信支付
                        MyApplication.payTyPe = 2;
                    PayUtil.wechatPay(mContext, payOrderInfo);

                    if (payType == 2)//支付宝支付
                        alipay();
                }
            }
        });

    }

    private void alipay() {
        final String payInfo = PayUtil.getOrderInfo(payOrderInfo);
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(ShoppingCartOrderActicity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝回调
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
//                    Log.i("alipay", "resultStatus:" + resultStatus);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                        //跳转到支付成功页面
                        Intent intent = new Intent(mContext, PaySuccessActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(mContext, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.equals(resultStatus, "6001")) {
                            // 用户主动取消支付
//                            Toast.makeText(mContext, "取消支付", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.check_save_money:
                if (b)
                    payMoney = allMoney - saveMoney;
                else
                    payMoney = allMoney;
                String relPay = formater2.format(payMoney);
                tv_needPay.setText("还需支付:" + relPay + "元");
                tv_payOrder.setText("确认支付" + relPay + "元");
                double integral = Double.parseDouble(relPay) * serviceCost * 0.01;
                tv_returnIntegral.setText("返还" + formater1.format(integral) + "积分");
                break;
            case R.id.checkBox_alipay:
                if (checkBox_alipay.isChecked())
                    checkBox_wechat.setChecked(false);
                break;
            case R.id.checkBox_wechat:
                if (checkBox_wechat.isChecked())
                    checkBox_alipay.setChecked(false);
                break;

        }
    }


}
