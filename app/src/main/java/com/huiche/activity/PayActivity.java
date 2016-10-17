package com.huiche.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huiche.R;
import com.huiche.activity.mine.ChangePhoneActivity;
import com.huiche.base.BaseActivity;
import com.huiche.base.MyApplication;
import com.huiche.bean.PayInfo;
import com.huiche.constant.HttpConstant;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.IsMoneyUtils;
import com.huiche.utils.TitleUtils;
import com.huiche.utils.ToastUtils;
import com.huiche.utils.TransformUtil;
import com.huiche.view.BufferCircleDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 买单页面（买单流程里第一个页面）
 *
 * @author Administrator
 */
public class PayActivity extends BaseActivity implements OnClickListener {
    private Context mContext;
    private TextView tv_no_yh, tv_pay_type, tv_pay_content, tv_pay;
    private EditText edit_pay_money, edit_no_yh;
    private LinearLayout ll;
    private PayInfo payInfo;
    //商家ID
    private String businessID;
    //记录用户有可能修改的数据
    private String Tmp;
    //用来规定只能按一次确认支付
    private Boolean once = true;
    private SharedPreferences share;

    @Override
    public void dealLogicBeforeFindView() {
        mContext = this;
    }

    @Override
    public void findViews() {
        //原布局activity_pay
        setContentView(R.layout.activity_new_pay);
        TitleUtils.setInfo(PayActivity.this, "");
        tv_no_yh = (TextView) findViewById(R.id.tv_no_yh);
        tv_pay_type = (TextView) findViewById(R.id.tv_pay_type);
        tv_pay_content = (TextView) findViewById(R.id.tv_pay_content);
        tv_pay = (TextView) findViewById(R.id.tv_pay);
        edit_pay_money = (EditText) findViewById(R.id.edit_pay_money);
        edit_no_yh = (EditText) findViewById(R.id.edit_no_yh);
        ll = (LinearLayout) findViewById(R.id.ll);
        ll.setVisibility(View.GONE);

        //标记是第一次
        tv_pay.setTag("1");
        ll.setTag("1");
        share = getSharedPreferences("user_data", MODE_PRIVATE);
        tv_pay.setBackgroundColor(Color.parseColor("#E82418"));
        edit_pay_money.setFocusable(true);
        edit_pay_money.setFocusableInTouchMode(true);
        edit_pay_money.requestFocus();
    }

    @Override
    public void initData() {
        // 获取上一个页面传递的数据
        Intent dataIntent = getIntent();
        String businessName = dataIntent.getStringExtra("businessName");
        //把商家名称添加到头部部分
        TitleUtils.setInfo(PayActivity.this, businessName);
        String businessID = dataIntent.getStringExtra("businessID");
        this.businessID = businessID;
        RequestParams params = new RequestParams();
        //搜索关键字（不传返回全部）
        //从上一个Activity中获取当前商家的ID
        params.put("id", businessID);
        if (!MyApplication.token.equals("")) {
            params.put("token", share.getString("token", ""));
        }
        final BufferCircleDialog dialog = new BufferCircleDialog(mContext);
        if (dialog != null)
            dialog.show("", true, null);
        AsyncHttp.posts(HttpConstant.GET_OREDER_ID, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (dialog != null)
                    dialog.dialogcancel();
                try {
                    String success = response.getString("msg");
                    String status = response.getString("status");
                    if ("success".equals(success) && "0".equals(status)) {
                        payInfo = JSON.parseObject(response.toString(),
                                PayInfo.class);
                        if (payInfo == null) return;
                        if (payInfo.getRows() == null) return;
                        //把商家名称添加到头部部分
                        TitleUtils.setInfo(PayActivity.this, payInfo.getRows().getBusinessName());
                        tv_pay_content.setText("消费100送" + payInfo.getRows().getIntegralScale() + "积分");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (dialog != null)
                        dialog.dialogcancel();
                }

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (dialog != null)
                    dialog.dialogcancel();
                ToastUtils.ToastShowShort(MyApplication.getInstance(), "请检测网络");
            }
        });


    }

    @Override
    public void setListeners() {
        tv_pay.setOnClickListener(this);
        tv_pay_type.setOnClickListener(this);
        //1、给EditText追加ChangedListener
        edit_pay_money.addTextChangedListener(watcher2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //确认支付
            case R.id.tv_pay:
                if (edit_pay_money.getText().toString().isEmpty()) {
                    ToastUtils.ToastShowLong(MyApplication.getInstance(), "请输入买单金额");
                    tv_pay.setTag("1");
                    return;
                }
                //判断买单金额
                if (!IsMoneyUtils.isMoney(edit_pay_money.getText().toString().trim())) {
                    ToastUtils.ToastShowLong(MyApplication.getInstance(), "请输入正确金额，最多保留1位小数");
                    return;
                }
                Tmp = edit_pay_money.getText().toString();
                //不享优惠金额
                final String freeMoney = edit_no_yh.getText().toString();
                if (!freeMoney.equals("")) {
                    if (!IsMoneyUtils.isMoney(freeMoney)) {
                        ToastUtils.ToastShowLong(MyApplication.getInstance(), "请输入正确金额，最多保留1位小数");
                        return;
                    }
                    if ((Double.parseDouble(Tmp)) < (Double.parseDouble(freeMoney))) {
                        ToastUtils.ToastShowLong(MyApplication.getInstance(), "买单金额不能小于免服务费金额");
                        return;
                    }
                }
                final String businessMoney = edit_pay_money.getText().toString();
                RequestParams params = new RequestParams();
                //搜索关键字（不传返回全部）
                //从上一个Activity中获取当前商家的ID
                params.put("businessStoreId", businessID);
                //不享优惠金额
                params.put("freeServiceQuota", freeMoney);
                //买单金额
                params.put("quota", edit_pay_money.getText().toString());
                if (!MyApplication.token.equals("")) {
                    params.put("token", share.getString("token", ""));
                }
                final BufferCircleDialog dialog = new BufferCircleDialog(mContext);
                if (dialog != null)
                    dialog.show("", true, null);
                AsyncHttp.posts(HttpConstant.GET_PAY_ADD, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                            super.onSuccess(statusCode, headers, response);
//                        Log.d("Helen",response.toString());

                        if (dialog != null)
                            dialog.dialogcancel();
                        try {
                            String status = response.getString("status");
                            String rows = response.getString("rows");
                            //正常通过
                            if ("0".equals(status)) {

//                                    if (once) {
//                                    ToastUtils.ToastShowShort(mContext, "正常通过");
                                Intent intent = new Intent(MyApplication.getInstance(), PayDetailActivity.class);
                                //下单成功
                                intent.putExtra("rows", rows.toString());
                                //商家的名字
                                intent.putExtra("BusinessName", payInfo.getRows().getBusinessName().toString());
                                //商家的ID
                                intent.putExtra("businessID", businessID.toString());

                                intent.putExtra("businessMoney", businessMoney.toString());

                                intent.putExtra("freeMoney", freeMoney.toString());
                                startActivity(intent);
                                once = false;
//                                    }
//                                    else {
//                                        ToastUtils.ToastShowShort(mContext,"已支付");
//                                    }
                            }

                            //未通过
                            if ("1".equals(status)) {

                                String errorCode = response.getString("errorCode");
                                if (errorCode.equals("2")) {
                                    //用户未绑手机
                                    Intent intent = new Intent(mContext, ChangePhoneActivity.class);
                                    startActivity(intent);
                                }

                                if (errorCode.equals("5")) {
                                    ToastUtils.ToastShowShort(MyApplication.getInstance(), "商家已限单，无法买单");
                                }

                                if (errorCode.equals("3")) {
                                    ToastUtils.ToastShowShort(MyApplication.getInstance(), "会员状态异常，无法买单。");
                                }

                                if (errorCode.equals("4")) {
                                    ToastUtils.ToastShowShort(MyApplication.getInstance(), "商家未上架，无法买单。");
                                }

//                                    errorCode：错误id
//                                    row:下单订单id(带到结算页面去当做参数用)
//                                若status返回1则判断errorCode为几
//                                switch(data.errorCode){
//                                    case 2://绑定手机
//                                        返回2则用户未绑定手机，跳转到绑定手机页面让用户绑定手机。绑定手机成功后，直接在绑定手机页面下单成功，跳转到结算页面。						return;
//                                    break;
//                                    case 5://是否限单
//                                        直接alert提示；alert("商家已限单，无法买单。");
//                                        return;
//                                    break;
//                                    case 3:
//                                        直接alert提示；	alert("会员状态异常，无法买单。");
//                                        return;
//                                    break;
//                                    case 4:
//                                        直接alert提示；	alert("商家未上架，无法买单。");
//                                        return;
//                                    break;
//                                    default:
//                                        break;

                            }
                            //用户未登录，跳转到登录页面让用户登录。登陆成功后，直接在登录页面下单成功，跳转到结算页面
                            //触发为香兰阁
                            if ("10".equals(status)) {
                                ToastUtils.ToastShowShort(MyApplication.getInstance(), "用户未登录");
                                Intent intent1 = new Intent(mContext, LoginActivity.class);

                                intent1.putExtra("rows", rows.toString());

                                //商家的名字
                                intent1.putExtra("BusinessName", payInfo.getRows().getBusinessName() + "");
                                //商家的ID
                                intent1.putExtra("businessID", businessID + "");

                                intent1.putExtra("businessMoney", businessMoney + "");

                                intent1.putExtra("freeMoney", freeMoney + "");
                                startActivity(intent1);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (dialog != null)
                                dialog.dialogcancel();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        if (dialog != null)
                            dialog.dialogcancel();
                        ToastUtils.ToastShowShort(MyApplication.getInstance(), "请检测网络");
                    }
                });
                break;
            //输入不参与优惠金额
            case R.id.tv_pay_type:
                Drawable bdrawable = getResources().getDrawable(R.drawable.add_b);
                Drawable gdrawable = getResources().getDrawable(R.drawable.add_g);
                if ("1".equals(ll.getTag())) {
                    ll.setVisibility(View.VISIBLE);
                    tv_pay_type.setTextColor(getResources().getColor(R.color.ssmall_grey));
                    gdrawable.setBounds(0, 0, gdrawable.getMinimumWidth(), gdrawable.getMinimumHeight());
                    tv_pay_type.setCompoundDrawables(gdrawable, null, null, null);
                    tv_pay_type.setCompoundDrawablePadding(TransformUtil.dip2px(this, 5));
                    ll.setTag("2");
                    return;
                }
                if ("2".equals(ll.getTag())) {
                    ll.setVisibility(View.GONE);
                    ll.setTag("1");
                    tv_pay_type.setTextColor(getResources().getColor(R.color.status_color));
                    bdrawable.setBounds(0, 0, bdrawable.getMinimumWidth(), bdrawable.getMinimumHeight());
                    tv_pay_type.setCompoundDrawables(bdrawable, null, null, null);
                    tv_pay_type.setCompoundDrawablePadding(TransformUtil.dip2px(this, 5));
                }
                break;

            default:
                break;
        }

    }


    //2、描述监听
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String sumMoney = edit_pay_money.getText().toString().trim();
            if (sumMoney.equals("")) {
                tv_pay.setText("确定买单");
            } else {
                tv_pay.setText("确定买单 ￥" + sumMoney);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            String text = s.toString();
            int len = s.toString().length();
            if (len == 1 && text.equals("0")) {
                s.clear();
            }


        }
    };


    //2、描述监听
    private TextWatcher watcher2 = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s)) {
                tv_pay.setText("确认买单");
                tv_pay.setBackgroundColor(Color.parseColor("#E82418"));
            } else {
                tv_pay.setText("确认买单￥" + s);
                tv_pay.setBackgroundColor(Color.parseColor("#E82418"));
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

//            String text = s.toString();
//            int len = s.toString().length();
//            if (len == 1 && text.equals("0")) {
//                s.clear();
//            }


        }
    };


    @Override
    protected void onResume() {
        super.onResume();
//        KeyBoardUtils.openKeybord(edit_pay_money);
        String text = edit_pay_money.getText().toString();
        String text2 = edit_no_yh.getText().toString();
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(text2)) {
            tv_pay.setBackgroundColor(Color.parseColor("#E82418"));
        } else {
            tv_pay.setBackgroundColor(Color.parseColor("#E82418"));
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


}
