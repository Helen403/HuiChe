package com.huiche.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huiche.R;
import com.huiche.activity.mine.MyCardQuanActivity;
import com.huiche.base.BaseActivity;
import com.huiche.base.MyApplication;
import com.huiche.bean.PayOrderResult;
import com.huiche.constant.HttpConstant;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.TitleUtils;
import com.huiche.utils.ToastUtils;
import com.huiche.utils.WindowUtils;
import com.huiche.view.BufferCircleDialog;
import com.huiche.view.Red_pack_cDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.text.DecimalFormat;

/**
 * 支付成功后，显示的订单信息
 *
 * @author Administrator
 */
public class PayOrderResultActivity extends BaseActivity implements OnClickListener {
    private Context mContext;
    private ImageButton imb_titleBack;
    private TextView tv_titleText;
    private LinearLayout integral_arrival, now_cash, get_integral, red_bao;
    private LinearLayout discount;
    ImageView redbao;
    private TextView tv_pay;
    private TextView tv_red_money;
    //积分优惠返回的红包
    private double quota;
    //1为随机积分红包，2为双倍积分红包
    private int inred;
    //领取卡券id
    private int cardID;


    //积分抵现
    private TextView integral_arrival_tv;
    //优惠详情
    private TextView discount_tv, now_cash_tv, get_integral_tv, cash, name, no, time;
    private View dialogVV;
    private TextView tv_confirmNormalasd, tv_red_type, tv_inter_red;
    private Red_pack_cDialog dialog;
    private LinearLayout ll_card_pay;
    private TextView tv_cardStock_price;
    private TextView tv_productName_cardQuan;
    private TextView tv_cardStock_useLimit;
    private TextView tv_usable_time;
    private LinearLayout ll_clickGet;
    private PayOrderResult payOrderResult;
    private DecimalFormat df;

    @Override
    public void dealLogicBeforeFindView() {
        mContext = this;
    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_pay_order_result);
        imb_titleBack = (ImageButton) findViewById(R.id.imb_titleBack);
        integral_arrival = (LinearLayout) findViewById(R.id.integral_arrival);
        integral_arrival_tv = (TextView) findViewById(R.id.integral_arrival_tv);
        now_cash_tv = (TextView) findViewById(R.id.now_cash_tv);
        get_integral_tv = (TextView) findViewById(R.id.get_integral_tv);
        discount = (LinearLayout) findViewById(R.id.discount);
        now_cash = (LinearLayout) findViewById(R.id.now_cash);
        red_bao = (LinearLayout) findViewById(R.id.red_bao);
        get_integral = (LinearLayout) findViewById(R.id.get_integral);
        cash = (TextView) findViewById(R.id.cash);
        name = (TextView) findViewById(R.id.name);
        no = (TextView) findViewById(R.id.no);
        time = (TextView) findViewById(R.id.time);
        redbao = (ImageView) findViewById(R.id.redbao);
        tv_pay = (TextView) findViewById(R.id.tv_pay);
        discount_tv = (TextView) findViewById(R.id.discount_tv);
        tv_red_money = (TextView) findViewById(R.id.tv_red_money);
        ll_card_pay = (LinearLayout) findViewById(R.id.ll_card_pay);
        tv_cardStock_price = (TextView) findViewById(R.id.tv_cardStock_price);
        tv_productName_cardQuan = (TextView) findViewById(R.id.tv_productName_cardQuan);
        tv_cardStock_useLimit = (TextView) findViewById(R.id.tv_cardStock_useLimit);
        tv_usable_time = (TextView) findViewById(R.id.tv_usable_time);
        ll_clickGet = (LinearLayout) findViewById(R.id.ll_clickGet);
        windowUtils = new WindowUtils(mContext);
        df = new DecimalFormat("######0.0");
    }

    @Override
    public void initData() {
        TitleUtils.setInfo(this, "买单成功");
        RequestParams params = new RequestParams();
        AsyncHttp.post("http://test.51ujf.cn/offlineOrders!paySuccess2.do", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                int status = response.optInt("status");
                String msg = response.optString("msg");
                boolean success = response.optBoolean("success");
                if (status == 0) {
                    if (success) {
                        payOrderResult = JSON.parseObject(response.toString(),
                                PayOrderResult.class);
                        cash.setText("买单金额￥" + (payOrderResult.getRows().getAllPrice()) + "元");
                        JSONObject rows = response.optJSONObject("rows");

                        // integralDeductible>0展示积分抵现
                        if (payOrderResult.getRows().getIntegralDeductible() > 0) {
                            discount.setVisibility(View.VISIBLE);
                            discount_tv.setText("-" + df.format(payOrderResult.getRows().getIntegralDeductible() * 0.1) + "元(" + df.format(payOrderResult.getRows().getIntegralDeductible()) + ")积分");
                        }
                        //orderRebate.type==1额外获得一个积分红包奖励
                        JSONObject orderRebate = rows.optJSONObject("orderRebate");
                        if (orderRebate != null) {
                            if ((orderRebate.optInt("type")) == 1) {
                                red_bao.setVisibility(View.VISIBLE);
                                tv_red_money.setText("恭喜你，获得一个额外红包奖励!");
                                quota = orderRebate.optDouble("quota");
                                dialog = new Red_pack_cDialog(PayOrderResultActivity.this, "获得一个随机红包", quota + "");
                                dialog.img_finish.setOnClickListener(PayOrderResultActivity.this);
                                inred = 1;
                            } else {
                                //orderRebate.type!=1 获得双倍积分
                                red_bao.setVisibility(View.VISIBLE);
                                tv_red_money.setText("恭喜你，获得双倍积分红包奖励!");
                                quota = orderRebate.optDouble("quota");
                                dialog = new Red_pack_cDialog(PayOrderResultActivity.this, "获得一个双倍红包", quota + "");
                                dialog.img_finish.setOnClickListener(PayOrderResultActivity.this);
                                inred = 2;
                            }
                        }

                        // haveCoupon>0展示优惠详情     //卡券对象: coupons
                        if (payOrderResult.getRows().isHaveCoupon()) {
                            integral_arrival.setVisibility(View.VISIBLE);
                            integral_arrival_tv.setText(payOrderResult.getRows().getCouponQuota() + "元(满" + payOrderResult.getRows().getDeduction() + "-" + payOrderResult.getRows().getCouponQuota() + ")");
                        }
                        JSONObject coupon = rows.optJSONObject("coupons");
                        if (coupon != null) {
                            ll_card_pay.setVisibility(View.VISIBLE);
                            //显示卡券
                            tv_productName_cardQuan.setText(coupon.optString("businessName"));
                            tv_cardStock_price.setText(coupon.optString("price"));
                            String starTime = coupon.optString("startTime");
                            String endTime = coupon.optString("endTime");
                            tv_usable_time.setText(starTime.substring(0, 10) + "至" + endTime.substring(0, 10));
                            String deduction = coupon.optString("deduction");
                            tv_cardStock_useLimit.setText("满" + deduction + "可使用");
                            cardID = coupon.optInt("id");
                        }
                        //是否展现现金支付
                        if ((payOrderResult.getRows().getCashQuota()) > 0) {
                            now_cash.setVisibility(View.VISIBLE);
                            now_cash_tv.setText(payOrderResult.getRows().getCashQuota() + "元");
                        }
                        //sendIntegral>0 展示赠送积分
                        if ((payOrderResult.getRows().getSendIntegral()) > 0) {
                            get_integral.setVisibility(View.VISIBLE);
                            get_integral_tv.setText(df.format(payOrderResult.getRows().getSendIntegral()) + "积分");
                        }
                        //获得额外红包奖励与双倍积分：orderRebate.quota

                        /*******************************************************************/


                        /*******************************************************************/


                        //设置商家名称
                        name.setText(payOrderResult.getRows().getBusinessName());
                        //设置订单编号
                        no.setText(payOrderResult.getRows().getNo());
                        //设置买单时间
                        time.setText(payOrderResult.getRows().getPayTime());
                        //设置买单金额
                        tv_pay.setText(payOrderResult.getRows().getAllPrice() + "元");
                        cash.setText("买单金额￥" + payOrderResult.getRows().getAllPrice() + "元");

                    }
                } else {
                    ToastUtils.ToastShowShort(PayOrderResultActivity.this, msg);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                ToastUtils.ToastShowShort(mContext, "请检测网络");
            }
        });
    }

    @Override
    public void setListeners() {
        red_bao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //拆红包
                dialog.showDialog();
                //隐藏原红包
                red_bao.setVisibility(View.GONE);
                double integ;
                if ((payOrderResult.getRows().getSendIntegral()) > 0) {
                    get_integral.setVisibility(View.VISIBLE);
                    //假如没赠送积分，但是有红包积分
                    if ((payOrderResult.getRows().getSendIntegral()) > 0) {
                        get_integral_tv.setText(payOrderResult.getRows().getSendIntegral() + "积分" + "+" + df.format(quota) + "积分");
                    } else {
                        get_integral_tv.setText(df.format(quota) + "积分");
                    }

//如果又没红包积分，又没赠送积分，隐藏调
                } else {
                    integ = quota;
                    get_integral.setVisibility(View.GONE);

                }
                dialog.tv_inter_red.setText(df.format(quota) + "");
                get_integral.setVisibility(View.VISIBLE);
                get_integral_tv.setText(payOrderResult.getRows().getSendIntegral() + "积分" + "+" + df.format(quota) + "积分");

            }
        });
//点击领取卡券
        ll_clickGet.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_finish:
                dialog.dimissDialog();
                break;
            //领取卡券
            case R.id.ll_clickGet:
                getCardQuan(cardID);
                break;
        }
    }

    /**
     * 会员获取卡券
     */
    private void getCardQuan(int id) {
        if (MyApplication.token.equals("")) {
            ToastUtils.ToastShowShort(mContext, "请先登录");
            /*************************************************************/
            Intent intent1 = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent1);
            /*************************************************************/
            return;
        }
        final RequestParams params = new RequestParams();
        params.put("id", id);
        final BufferCircleDialog dialog = new BufferCircleDialog(mContext);
        dialog.show("", true, null);
        AsyncHttp.post(HttpConstant.GET_CARD_QUAN, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(mContext, "请检查网络");
                dialog.dialogcancel();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                dialog.dialogcancel();
                String msg = response.optString("msg");
                String status = response.optString("status");
                boolean success = response.optBoolean("success");
                if (status.equals("0")) //领取成功
                {
                    if (success) {
                        showDialog(msg, -1);
                    } else {
                        showDialog(msg, 1);
                    }

                } else {
                    showDialog(msg, 1);
                }


            }
        });
    }


    /**
     * 显示结果弹框
     */

    private AlertDialog notifyDialog;
    private TextView tv_dialogNormal_title;
    private TextView tv_cancelNormal;
    private TextView tv_confirmNormal;
    private TextView tv_dialogNormal_content;
    private WindowUtils windowUtils;

    private void showDialog(String msg, int errorCode) {
        if (notifyDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            View dialogView = View.inflate(mContext, R.layout.dialog_normal, null);
            tv_dialogNormal_title = (TextView) dialogView.findViewById(R.id.tv_dialogNormal_title);
            tv_cancelNormal = (TextView) dialogView.findViewById(R.id.tv_cancelNormal);
            tv_dialogNormal_content = (TextView) dialogView.findViewById(R.id.tv_dialogNormal_content);
            tv_confirmNormal = (TextView) dialogView.findViewById(R.id.tv_confirmNormal);
            notifyDialog = builder.show();
            Window dialogWindow = notifyDialog.getWindow();
            dialogWindow.setContentView(dialogView);
            int width = windowUtils.getdisplayWidth();
            dialogWindow.setContentView(dialogView);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            //显示比例宽度5/7
            lp.width = width * 5 / 7;
//            lp.height = lp.width*10/16;
            dialogWindow.setAttributes(lp);
        } else {
            notifyDialog.show();
        }
        if (errorCode == -1) {
            //领取成功
            tv_dialogNormal_title.setText(msg);
            tv_dialogNormal_content.setText("您的卡券已经放入卡包");
            tv_cancelNormal.setText("关闭");
            tv_confirmNormal.setText("点击查看");
            tv_confirmNormal.setVisibility(View.VISIBLE);
        } else {
            tv_dialogNormal_title.setText("领取失败");
            tv_dialogNormal_content.setText(msg);
            tv_confirmNormal.setVisibility(View.GONE);
            tv_cancelNormal.setText("关闭");
        }
        tv_cancelNormal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDialog.dismiss();
            }
        });
        tv_confirmNormal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MyCardQuanActivity.class);
                mContext.startActivity(intent);
                notifyDialog.dismiss();
                ll_card_pay.setVisibility(View.GONE);

            }
        });
    }
}
