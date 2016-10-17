package com.huiche.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.huiche.R;
import com.huiche.activity.mine.MyCardActivity;
import com.huiche.base.BaseActivity;
import com.huiche.base.MyApplication;
import com.huiche.bean.PayDetailOrder;
import com.huiche.bean.PayInfoCard;
import com.huiche.bean.PayOrderInfo;
import com.huiche.constant.HttpConstant;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.PayUtil;
import com.huiche.utils.ToastUtils;
import com.huiche.view.BufferCircleDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import alipay.PayResult;

/**
 * 买单页面（买单流程里第二个页面）
 *
 * @author Administrator
 */
public class PayDetailActivity extends BaseActivity implements OnClickListener {
    private Context mContext;
    private ImageButton imb_titleBack;
    private TextView tv_titleText;
    private TextView tv_payOrder_payDetail;
    private TextView businessname;
    private TextView pay_money;
    private TextView tv_kq_money;
    private TextView useintegral;
    private TextView integral;
    private TextView menber_money;
    private TextView need;
    private TextView return1;
    private TextView nonparticipation;
    //积分抵现
    private ImageView checkBox_reachCash_payDetail;
    //卡卷抵现
    private ImageView checkBox_cardStock_payDetail;
    //微信
    private ImageView wechat;
    //支付宝
    private ImageView paybao;
    //卡卷抵现的布局
    private RelativeLayout rl;
    private PayDetailOrder payDetailOrder;
    //上一个Activity商家的ID
    private String businessID;
    //上一个Activity传来的买单的金额
    private String businessMoney = "0";
    private PayInfoCard payInfoCard;
    //积分抵现比例
    private float scaleBack = 0.1f;
    //积分兑换比例
    private float scaleExchange = 10f;
    //最终支付
    private float finalPay = 0;
    //判断代金卷是否点击 默认没点击
    private Boolean flag = false;
    //买单的ID
    private String rows;
    //不参加优惠金额
    private String freeMoney;
    //用来规定只能按一次确认支付
    private int payType = 1;
    private PayOrderInfo payOrderInfo;
    private static final int SDK_PAY_FLAG = 1;
    //代金卷的ID
    private int CardID;
    private double buyMoney;
    private double cmoney;
    private boolean firstEnter = true;
    private double MenberIntegral;
    //最后需要支付的金额(使用微信或支付宝)
    private double resultMoney = 0.0;
    //积分抵扣数量，当使用卡券抵现时可以传0过去
    private String useIntegralAmount = "0";
    private final String ACTION_NAME = "UPDATE";
    //订单是否已经支付过，不可重复结算订单
    private boolean isHasPay = false;

    @Override
    public void dealLogicBeforeFindView() {
        mContext = this;
    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_pay_detail);
        imb_titleBack = (ImageButton) findViewById(R.id.imb_titleBack);
        tv_titleText = (TextView) findViewById(R.id.tv_titleText);
        tv_payOrder_payDetail = (TextView) findViewById(R.id.tv_payOrder_payDetail);
        businessname = (TextView) findViewById(R.id.businessname);
        pay_money = (TextView) findViewById(R.id.pay_money);
        checkBox_reachCash_payDetail = (ImageView) findViewById(R.id.checkBox_reachCash_payDetail);
        checkBox_cardStock_payDetail = (ImageView) findViewById(R.id.checkBox_cardStock_payDetail);
        rl = (RelativeLayout) findViewById(R.id.rl);
        tv_kq_money = (TextView) findViewById(R.id.tv_kq_money);
        integral = (TextView) findViewById(R.id.integral);
        useintegral = (TextView) findViewById(R.id.useintegral);
        menber_money = (TextView) findViewById(R.id.menber_money);
        need = (TextView) findViewById(R.id.need);
        return1 = (TextView) findViewById(R.id.return1);
        wechat = (ImageView) findViewById(R.id.wechat);
        paybao = (ImageView) findViewById(R.id.paybao);
        nonparticipation = (TextView) findViewById(R.id.nonparticipation);
        //默认积分底线
        checkBox_reachCash_payDetail.setSelected(true);
        checkBox_cardStock_payDetail.setSelected(false);
        //注册广播
        registerBoradcastReceiver();
    }

    @Override
    public void initData() {
        tv_titleText.setText("买单");
        //第一次设置
        tv_payOrder_payDetail.setTag("1");
        // 获取上一个页面传递的数据
        Intent dataIntent = getIntent();
        rows = dataIntent.getStringExtra("rows");
        if (rows.isEmpty()) return;
        String BusinessName = dataIntent.getStringExtra("BusinessName");
        businessID = dataIntent.getStringExtra("businessID");
        if (businessID.isEmpty()) return;
        businessMoney = dataIntent.getStringExtra("businessMoney").trim();
        if (businessMoney.isEmpty()) return;
        //不享优惠金额
        freeMoney = dataIntent.getStringExtra("freeMoney");
        if (freeMoney.equals("0")) {
            freeMoney = "";
        }
        //设置商家的名称
        if (!BusinessName.isEmpty()) {
            businessname.setText(BusinessName);
        } else {
            businessname.setText("没有商家");
        }
        //默认选择微信支付
        wechat.setSelected(true);
        payType = 1;
        //获取积分优惠
        getIntegralData();
        getCardData();

    }

    private void getCardData() {
        /*********************************************************/
        //读取卡券
        //读取下单数据
        RequestParams params = new RequestParams();
        //搜索关键字（不传返回全部）
        //从上一个Activity中获取当前商家的ID
        params.put("businessStoreId", businessID);
        SharedPreferences share = getSharedPreferences("user_data", MODE_PRIVATE);
        String token = share.getString("token", "");
        params.put("token", share.getString("token", ""));
        final BufferCircleDialog dialog = new BufferCircleDialog(mContext);
        dialog.show("", true, null);
        AsyncHttp.posts(HttpConstant.REQUEST_CARD, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        dialog.dialogcancel();
                        String status = response.optString("status");
                        boolean success = response.optBoolean("success");
                        String msg = response.optString("msg");
                        if (status.equals("0")) {
                            if (success) {
                                JSONArray rows = response.optJSONArray("rows");
                                if (rows.length() > 0) {
                                    payInfoCard = JSON.parseObject(response.toString(),
                                            PayInfoCard.class);
                                    //选择最大一张卡券显示出来
                                    setMaxCard(payInfoCard);
                                } else {
                                    ToastUtils.ToastShowShort(PayDetailActivity.this, "无可用卡券");
                                }
                            }
                        } else {
                            ToastUtils.ToastShowShort(PayDetailActivity.this, msg);
                        }
                        // getCard(payInfoCard);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable
                            throwable, JSONObject errorResponse) {
                        dialog.dialogcancel();
                        ToastUtils.ToastShowShort(MyApplication.getInstance(), "请检测网络");
                    }
                }
        );
        /*********************************************************/

    }

    private void getIntegralData() {
        //获取积分优惠数据
        /*************************************************/
        //读取积分优惠数据
        RequestParams param = new RequestParams();
        //搜索关键字（不传返回全部）
        //从上一个Activity中获取当前商家的ID
        param.put("id", rows);
        SharedPreferences share = getSharedPreferences("user_data", MODE_PRIVATE);
        param.put("token", share.getString("token", ""));

        AsyncHttp.posts(HttpConstant.GET_PAY_READ, param, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (!response.optString("status").equals("0"))
                    return;
                payDetailOrder = JSON.parseObject(response.toString(),
                        PayDetailOrder.class);
                if (payDetailOrder == null) return;
                if (payDetailOrder.getRows() == null) return;
                if (payDetailOrder.getRows().getBusinessStore() == null) return;
                //设置商家的名次
                businessname.setText(payDetailOrder.getRows().getBusinessName());

                //第一次进入默认使用积分优惠
                if (firstEnter) {

                    upDateUIForIntegral();
                    firstEnter = false;

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                ToastUtils.ToastShowShort(MyApplication.getInstance(), "请检测网络");
            }
        });

    }

    //显示最大一张卡券信息
    public void setMaxCard(PayInfoCard payInfoCard) {
        double realprice;
        double buyPrice = Double.parseDouble(businessMoney);
        double cardPrice = 0;
        double cardecution = 0;
        if (buyPrice >= 6) {
            double free;
            if (freeMoney.length() > 0) {
                free = Double.parseDouble(freeMoney);
            } else {
                free = 0;
            }
            //真实付款价格
            realprice = buyPrice - free;
            List<PayInfoCard.RowsBean> cardList = new ArrayList<PayInfoCard.RowsBean>();
            for (int i = 0; i < payInfoCard.getRows().size(); i++) {
                double temp = payInfoCard.getRows().get(i).getDeduction();
                if (realprice >= temp) {
                    cardList.add(payInfoCard.getRows().get(i));
                }
            }
            //可用的最大的
            double maxp = 0;
            int position = 0;
            if (cardList.size() > 0) {
                for (int i = 0; i < cardList.size(); i++) {
                    double temp = cardList.get(i).getPrice();
                    if (temp > maxp) {
                        maxp = temp;
                        position = i;
                    }
                }

                DecimalFormat df = new DecimalFormat("######0");
                cardecution = cardList.get(position).getDeduction();
                tv_kq_money.setText("消费满" + (df.format(cardecution)) + "减" + (cardList.get(position).getPrice()));
            }
        }


    }

    @Override
    public void setListeners() {
        imb_titleBack.setOnClickListener(this);
        tv_payOrder_payDetail.setOnClickListener(this);
        checkBox_cardStock_payDetail.setOnClickListener(this);
        rl.setOnClickListener(this);
        wechat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                wechat.setSelected(true);
                paybao.setSelected(false);
                payType = 1;
            }
        });
        paybao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                wechat.setSelected(false);
                paybao.setSelected(true);
                payType = 2;
            }
        });
        //积分底线
        checkBox_reachCash_payDetail.setOnClickListener(this);


    }

    @Override
    protected void onDestroy() {
        if (MyBroadcast != null) {
            unregisterReceiver(MyBroadcast);
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.imb_titleBack:
                finish();
                break;
            case R.id.rl:
                //当无卡券的时候点击提示无卡券
                if (payInfoCard == null) {
                    ToastUtils.ToastShowShort(PayDetailActivity.this, "暂无卡券");
                } else {
                    updateUIForCard();
                }
                break;


            //积分底线
            case R.id.checkBox_reachCash_payDetail:
                flag = false;
                //原来选中的可以取消掉
                if (checkBox_reachCash_payDetail.isSelected()) {
                    checkBox_reachCash_payDetail.setSelected(false);
                    checkBox_cardStock_payDetail.setSelected(false);
                    //直接使用现金支付
                    upDateUIForCash();
                } else {
                    checkBox_cardStock_payDetail.setSelected(false);
                    checkBox_reachCash_payDetail.setSelected(true);
                    //使用积分来优惠
                    upDateUIForIntegral();
                }
                break;
            //卡券抵现
            case R.id.checkBox_cardStock_payDetail:
                //
                if (checkBox_cardStock_payDetail.isSelected()) {
                    checkBox_cardStock_payDetail.setSelected(false);
                    checkBox_reachCash_payDetail.setSelected(false);
                    //直接使用现金支付
                    upDateUIForCash();
                }
                //原来没选中的 点击后让它仍是取消,但是可以选择卡券
                else {
                    checkBox_cardStock_payDetail.setSelected(false);
                    //没有卡券的时候不能进入选择卡券
                    if (payInfoCard == null) {
                        ToastUtils.ToastShowShort(PayDetailActivity.this, "暂无卡券");
                    } else {
                        updateUIForCard();
                    }

                }

                break;
            //买单
            case R.id.tv_payOrder_payDetail:
                if (isHasPay) {
                    ToastUtils.ToastShowShort(PayDetailActivity.this, "该订单已失效，请返回重新下单");
                    return;
                }
                RequestParams params = new RequestParams();
                //代金券id  判断代金卷
                if (flag) {
                    params.put("cautionId", CardID);
                }
                //买单id
                params.put("orderId", rows);
                //支付平台（默认1）：1 微信，2 支付宝
                params.put("paymentType", payType);
                //抵扣积分数目（默认：使用第三方支付）
                params.put("useIntegralAmount", useIntegralAmount);
                //默认：0，使用web方式支付，1使用app方式支付
                params.put("useWebPay", 1);
                if (!MyApplication.token.equals("")) {
                    params.put("token", MyApplication.token);
                }
                final BufferCircleDialog dialog = new BufferCircleDialog(mContext);
                dialog.show("", false, null);
                AsyncHttp.posts(HttpConstant.SETTLE_PAY, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        dialog.dialogcancel();
                        String status = response.optString("status");
                        if (status.equals("0")) {
                            //表示已经结算过一次，当下次再次结算时提示用户
                            isHasPay = true;
                            JSONObject rows = response.optJSONObject("rows");
                            String code = rows.optString("code");
                            JSONObject value = rows.optJSONObject("value");
                            String msg = value.optString("msg");
                            if (resultMoney > 0.0) {
                                JSONObject obj = value.optJSONObject("obj");
                                payOrderInfo = JSON.parseObject(obj.toString(), PayOrderInfo.class);
                                //暂时屏蔽
                                if (payType == 1)//微信支付
                                {
                                    //买单页面进入的
                                    MyApplication.payTyPe = 1;
                                    PayUtil.wechatPay(mContext, payOrderInfo);

                                }


                                if (payType == 2)//支付宝支付
                                    alipay();
                            } else {
                                boolean obj = value.optBoolean("obj");
                                if (obj) {
                                    //直接跳到支付完成界面
                                    Intent intent = new Intent();
                                    intent.setClass(PayDetailActivity.this, PayOrderResultActivity.class);
                                    startActivity(intent);
                                    finish();
                                    return;
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        ToastUtils.ToastShowShort(MyApplication.getInstance(), "请检测网络");
                        dialog.dialogcancel();
                    }
                });

                break;
            default:
                break;
        }
    }
//    //优惠金额最大的卡券信息  判断准则为price最大就选择那张卡卷
//    private PayInfoCard.RowsBean getCard(PayInfoCard payInfoCard) {
//        if (payInfoCard == null) return null;
//        if (payInfoCard.getRows() == null) return null;
//        if (payInfoCard.getRows().get(0) == null) return null;
//        int count = payInfoCard.getRows().size();
//        if(count>0){
//            double price=0;
//            for (int i = 1; i < count; i++) {
//
//            }
//
//        }else{
//
//        }
//
//        return rowsBean;
//    }

    /**
     * 保存当前时间  比较
     */
    private boolean getBooleanTime(String lastTime) {
        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        return difference(time, lastTime);
    }

    /**
     * 判断当前时间和上一次时间之间的差是否大于规定的时间 大于返回true 小于就返回false
     */
    private boolean difference(String time, String lastTime) {
        boolean result = false;
        if (!lastTime.isEmpty()) {
            try {
                java.util.Date nowdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
                java.util.Date setdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(lastTime);
                long between = (nowdate.getTime() - setdate.getTime());
                result = between < 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 跳转到MyCardActivity返回来
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //选择卡券后返回来的
        if (requestCode == 1785 && resultCode == RESULT_OK) {
            flag = true;
            //抵现的积分
            useIntegralAmount = "0";
            checkBox_cardStock_payDetail.setSelected(true);
            checkBox_reachCash_payDetail.setSelected(false);
            //将积分的数据修改调
            //用户积分
            DecimalFormat df = new DecimalFormat("######0.0");
            DecimalFormat dt = new DecimalFormat("######0.00");
            useintegral.setText("-" + "0积分/" + df.format(MenberIntegral) + "积分");
            //获取选择的卡卷  刷新数据
            Bundle bundle = data.getExtras();
            //rowsBeanTmp是用户选择后返回来的卡券信息集合
            final PayInfoCard.RowsBean rowsBeanTmp = bundle.getParcelable("RowsBean");
            CardID = rowsBeanTmp.getId();
            if (rowsBeanTmp == null) return;
            //显示最大卡卷的信息
            tv_kq_money.setText("满" + rowsBeanTmp.getDeduction() + "元减" + rowsBeanTmp.getPrice() + "元");
            tv_kq_money.setTextColor(Color.parseColor("#FF1B0A"));
            if (payDetailOrder.getRows() == null) return;
            final float menberMoney = payDetailOrder.getRows().getBusinessStore().getIntegralScakeTmp() * scaleBack;
            Boolean flagCard = true;
            //能优惠的卡券金额
            final double cardMoney = rowsBeanTmp.getPrice();
            // 当有不参与优惠金额时效果
            //不参与优惠金额不可用积分抵现、卡券抵现、并且不返还积分。只走现金支付通道
            //有优惠金额
            // double free=Double.parseDouble(freeMoney);
            if (freeMoney.length() > 0) {
                nonparticipation.setVisibility(View.VISIBLE);
//                //设置买单总额
//                pay_money.setText(dt.format(businessMoney) + "元(不参与优惠金额" + freeMoney + "元)");
                //不优惠金额后的金额
                double freeMon = Double.parseDouble(freeMoney);
//                buyMoney = buyMoney - freeMon;
                //设置会员优惠的金额
                menber_money.setText("-" + cardMoney + "元");
                need.setText("还需支付:" + (buyMoney - cardMoney));
                //使用卡券不返回积分
                return1.setText("返回0.0积分");
                //最终买单金额
                resultMoney = buyMoney - cardMoney;
                tv_payOrder_payDetail.setText("确认支付" + (df.format(resultMoney)) + "元");
            }
            //无优惠金额
            else {
                nonparticipation.setVisibility(View.GONE);
                //设置买单总额
                pay_money.setText(dt.format(buyMoney) + "元");
                //设置会员优惠的金额
                menber_money.setText("-" + cardMoney + "元");
                need.setText("还需支付:" + (buyMoney - cardMoney));
                //使用卡券不返回积分
                return1.setText("返回0.0积分");
                //最终买单金额
                resultMoney = buyMoney - cardMoney;
                tv_payOrder_payDetail.setText("确认支付" + (df.format(resultMoney)) + "元");
            }

        }
    }


    private void alipay() {
        final String payInfo = PayUtil.getOrderInfo(payOrderInfo);
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(PayDetailActivity.this);
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
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(MyApplication.getInstance(), "支付成功", Toast.LENGTH_SHORT).show();
                        //跳转到支付成功页面
                        Intent intent = new Intent(PayDetailActivity.this, PayOrderResultActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(MyApplication.getInstance(), "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.equals(resultStatus, "6001")) {
                            // 用户主动取消支付
//                            Toast.makeText(mContext, "取消支付", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(MyApplication.getInstance(), "支付失败", Toast.LENGTH_SHORT)
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


    //总的逻辑判断,使用积分抵扣
    public void upDateUIForIntegral() {
        //不适用卡券
        flag = false;
        //现将卡券抵现的优惠字体颜色改变
        tv_kq_money.setTextColor(getResources().getColor(R.color.deep_grey));
        if (payDetailOrder.getStatus() == 0) {
            if (payDetailOrder.isSuccess()) {
                // 尺/ 会员积分
                MenberIntegral = payDetailOrder.getRows().getMenberIntegral();
                DecimalFormat df = new DecimalFormat("######0.0");
                DecimalFormat dt = new DecimalFormat("######0.00");

                float payMoney = 0.0f;
                //先要判断买单金额是不是大于等于6元，如果小于则不能优惠
                if (!businessMoney.equals("")) {
                    buyMoney = Double.parseDouble(businessMoney);
                }
                //设置买单总额
                pay_money.setText(dt.format(buyMoney) + "元");
                //可以优惠
                if (buyMoney >= 6) {
                    //有无不优惠金额
                    if (freeMoney.length() > 0) {
                        nonparticipation.setVisibility(View.VISIBLE);
                        //设置买单总额
                        pay_money.setText(dt.format(buyMoney) + "元(不参与优惠金额" + freeMoney + "元)");
                        //设置文字为订单满6元可用
                        integral.setText("积分抵现");
                        double freeMon = Double.parseDouble(freeMoney);
                        buyMoney = buyMoney - freeMon;
                        //选择积分底线
                        if (checkBox_reachCash_payDetail.isSelected()) {
                            String userIntegral = df.format(MenberIntegral);
                            if (!userIntegral.equals("")) {
                                double menIntegra = Double.parseDouble(userIntegral);
                                //如果积分除以10大于买单金额则可以全部抵扣
                                if (menIntegra * 0.1 >= buyMoney) {
                                    //优惠
                                    menber_money.setText("-" + buyMoney + "元");
                                    need.setText("还需要支付:" + freeMon);
                                    //用户积分
                                    useintegral.setText("-" + (buyMoney * 10) + "积分/" + df.format(MenberIntegral) + "积分");
                                    //设置返回的积分
                                    double menberExchange = freeMon * (payDetailOrder.getRows().getBusinessStore().getServiceCost()) * 0.01;
                                    return1.setText("返回" + "0.0积分");
                                    integral.setText("积分抵现");
                                    //最终买单金额
                                    resultMoney = freeMon;
                                    tv_payOrder_payDetail.setText("确认支付" + (df.format(resultMoney)) + "元");
                                    //抵现的积分
                                    useIntegralAmount = buyMoney * 10 + "";
                                } else {
                                    integral.setText("积分抵现");
                                    //差额
                                    cmoney = buyMoney - (menIntegra * 0.1);
                                    menber_money.setText("-" + df.format(menIntegra * 0.1) + "元");
                                    need.setText("还需要支付:" + (df.format(cmoney + freeMon)) + "元");
                                    //用户积分
                                    useintegral.setText("-" + df.format(MenberIntegral) + "积分/" + df.format(MenberIntegral) + "积分");

                                    //设置返回的积分
                                    double menberExchange = (cmoney + freeMon) * (payDetailOrder.getRows().getBusinessStore().getServiceCost()) * 0.01;
                                    return1.setText("返回" + df.format(menberExchange) + "积分");
                                    //最终买单金额
                                    resultMoney = cmoney + freeMon;
                                    tv_payOrder_payDetail.setText("确认支付" + (df.format(resultMoney)) + "元");
                                    //抵现的积分
                                    useIntegralAmount = df.format(MenberIntegral);
                                }
                            }
                        }

                    }
                    //无优惠金额
                    else {
                        integral.setText("积分抵现");
                        /////
                        //选择积分底线
                        if (checkBox_reachCash_payDetail.isSelected()) {
                            String userIntegral = df.format(MenberIntegral);
                            if (!userIntegral.equals("")) {
                                double menIntegra = Double.parseDouble(userIntegral);

                                if (menIntegra * 0.1 >= buyMoney) {
                                    //优惠
                                    menber_money.setText("-" + buyMoney + "元");
                                    need.setText("还需要支付:" + "0元");
                                    return1.setText("返回" + "0积分");
                                    //用户积分
                                    useintegral.setText("-" + (buyMoney * 10) + "积分/" + df.format(MenberIntegral) + "积分");
                                    //最终买单金额
                                    resultMoney = (double) 0.0;
                                    tv_payOrder_payDetail.setText("确认支付");
                                    //抵现的积分
                                    useIntegralAmount = buyMoney * 10 + "";

                                } else {
                                    //差额优惠
                                    cmoney = buyMoney - menIntegra * 0.1;
                                    menber_money.setText("-" + (df.format(menIntegra * 0.1)) + "元");
                                    need.setText("还需要支付:" + (df.format(cmoney)) + "元");

                                    //用户积分
                                    useintegral.setText("-" + (df.format(MenberIntegral)) + "积分/" + df.format(MenberIntegral) + "积分");
                                    //最终买单金额
                                    resultMoney = cmoney;
                                    //设置返回的积分
                                    double menberExchange = resultMoney * (payDetailOrder.getRows().getBusinessStore().getServiceCost()) * 0.01;
                                    return1.setText("返回" + df.format(menberExchange) + "积分");
                                    tv_payOrder_payDetail.setText("确认支付" + (df.format(resultMoney)) + "元");
                                    //抵现的积分
                                    useIntegralAmount = df.format(MenberIntegral);
                                }
                            }
                        }

                    }
                }
                //小于6元只能现金,如果有不优惠金额也要计算
                else {
                    rl.setVisibility(View.GONE);
                    if (freeMoney.length() > 0) {
                        nonparticipation.setVisibility(View.VISIBLE);
                        pay_money.setText(dt.format(buyMoney) + "元(不参与优惠金额" + freeMoney + "元)");
                    } else {
                        nonparticipation.setVisibility(View.VISIBLE);
                        pay_money.setText(dt.format(buyMoney) + "元");
                    }
                    checkBox_reachCash_payDetail.setSelected(false);
                    //设置文字为订单满6元可用
                    integral.setText("订单满6元可用");
                    //显示可用的积分
                    if (payDetailOrder.getRows() != null && payDetailOrder != null) {
                        //积分
                        useintegral.setText("可用余额:" + df.format(MenberIntegral) + "积分");
                    }
                    //设置会员优惠的金额
                    menber_money.setText("-0.0元");
                    need.setText("还需要支付:" + buyMoney + "元");

                    //最终买单金额
                    resultMoney = buyMoney;
                    //抵现的积分
                    useIntegralAmount = "0";
                    tv_payOrder_payDetail.setText("确认支付" + resultMoney + "元");


                    //设置返回的积分
                    if (buyMoney < 1) {
                        return1.setText("返回" + "0积分");
                    } else {
                        //设置返回的积分
                        if (freeMoney.length() > 0) {
                            double free = Double.parseDouble(freeMoney);

                            double menberExchange = (buyMoney - free) * (payDetailOrder.getRows().getBusinessStore().getServiceCost()) * 0.01;
                            return1.setText("返回" + df.format(menberExchange) + "积分");
                        } else {
                            double menberExchange = resultMoney * (payDetailOrder.getRows().getBusinessStore().getServiceCost()) * 0.01;
                            return1.setText("返回" + df.format(menberExchange) + "积分");
                        }

                    }

                }

            }

        }
    }


    //总的逻辑判断，使用卡券抵扣，无返回积分
    public void updateUIForCard() {

        //先要判断买单金额是不是大于等于6元，如果小于则不能优惠
        DecimalFormat df = new DecimalFormat("######0.0");
        DecimalFormat dt = new DecimalFormat("######0.00");
        if (!businessMoney.equals("")) {
            buyMoney = Double.parseDouble(businessMoney);
            //大于6元可以使用卡券抵现,当有不优惠金额时需要注意
            double freemon;
            if (!freeMoney.equals("")) {
                freemon = Double.parseDouble(freeMoney);
            } else {
                freemon = 0;
            }
            if (buyMoney >= 6) {
                if (freemon > 0) {
                    buyMoney = buyMoney - freemon;
                }
                if (payInfoCard == null) ToastUtils.ToastShowShort(PayDetailActivity.this, "请重新登录");
                if (payInfoCard.getRows() == null)
                    ToastUtils.ToastShowShort(PayDetailActivity.this, "请重新登录");
                ;
                //有卡卷
                if (payInfoCard.getRows().size() > 0) {
                    //当用户有可用卡券时，满足条件
                    // 卡券抵现默认显示一张优惠金额最大的卡券信息在卡券抵现右侧，
                    // 如图（3）。点击卡券抵现进入卡券页面如图（4），用户可选择自己需要使用的卡券。点击后回到结算页面显示效果为如图（5）
                    // 。卡券抵现复选框勾选，并且右边卡券信息为红色。
                    //当有卡券时，进入选择卡券
                    Intent intent = new Intent(mContext, MyCardActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("payInfoCard", payInfoCard);
                    bundle.putDouble("payMoney", buyMoney);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 1785);
                }
                //无卡券
                else {
                    ToastUtils.ToastShowShort(PayDetailActivity.this, "暂无优惠卡券");
                }
            }

            //没6元隐藏优惠布局
            else {

                rl.setVisibility(View.GONE);
                checkBox_reachCash_payDetail.setSelected(false);
                //设置文字为订单满6元可用
                integral.setText("订单满6元可用");
                //显示可用的积分
                if (payDetailOrder.getRows() != null && payDetailOrder != null) {
                    //积分
                    useintegral.setText("可用余额:" + df.format(MenberIntegral) + "积分");
                }
                //设置会员优惠的金额
                menber_money.setText("-0.0元");

                need.setText("还需要支付:" + dt.format(buyMoney) + "元");
                //最终买单金额
                resultMoney = buyMoney;
                tv_payOrder_payDetail.setText("确认支付" + (dt.format(resultMoney)) + "元");
                //设置返回的积分
                if (buyMoney < 1) {
                    return1.setText("返回" + "0积分");
                } else {
                    //设置返回的积分
                    double menberExchange = resultMoney * (payDetailOrder.getRows().getBusinessStore().getServiceCost()) * 0.01;
                    return1.setText("返回" + df.format(menberExchange) + "积分");
                }
            }
        }

    }

    //直接使用现金支付,需要注意如果有不优惠金额的返回积分
    public void upDateUIForCash() {
        //抵现的积分
        flag = false;
        useIntegralAmount = "0";
        buyMoney = Double.parseDouble(businessMoney);
        //先要判断买单金额是不是大于等于6元，如果小于则不能优惠
        DecimalFormat df = new DecimalFormat("######0.0");
        DecimalFormat dt = new DecimalFormat("######0.00");
        menber_money.setText("-0.0元");
        //用户积分
        useintegral.setText("-" + "0积分/" + df.format(MenberIntegral) + "积分");
        tv_kq_money.setText("暂无卡券可用");
        tv_kq_money.setTextColor(getResources().getColor(R.color.deep_grey));
        need.setText("还需支付:" + buyMoney + "元");
        //设置返回的积分
        double menberExchange;
        if (freeMoney.length() > 0) {
            double freeMon = Double.parseDouble(freeMoney);
            menberExchange = (buyMoney - freeMon) * (payDetailOrder.getRows().getBusinessStore().getServiceCost()) * 0.01;
        } else {
            menberExchange = buyMoney * (payDetailOrder.getRows().getBusinessStore().getServiceCost()) * 0.01;
        }
        return1.setText("返回" + df.format(menberExchange) + "积分");
        resultMoney = buyMoney;
        tv_payOrder_payDetail.setText("确认支付" + dt.format(buyMoney) + "元");
    }

    //刷新数据广播
    private BroadcastReceiver MyBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_NAME)) {
                finish();
            }

        }
    };

    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ACTION_NAME);
//注册广播
        registerReceiver(MyBroadcast, myIntentFilter);
    }


}
