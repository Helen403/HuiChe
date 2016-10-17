package com.huiche.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huiche.R;
import com.huiche.base.BaseActivity;
import com.huiche.bean.PayOrderResult;
import com.huiche.constant.HttpConstant;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.ToastUtils;
import com.huiche.utils.TwoCodeUtil;
import com.huiche.view.BufferCircleDialog;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 兑换成功
 *
 * @author Administrator
 */
public  class ExchangeSuccessActivity extends BaseActivity implements OnClickListener {
    private Context mContext;
    private TextView tv_titleText;
    //private ImageButton imb_titleBack;
    private TextView tv_returnIntegral;//返回积分
    private ImageView iv_twoCode;
    private TextView tv_storeName;
    private TextView tv_orderNumber;
    private TextView tv_checkCode;
    private TextView tv_payMoney;
    private RelativeLayout rl_returnHome;
    private TextView tv_pay_money;
    private List<PayOrderResult.RowsBean> resultList = new ArrayList<>();
    @Override
    public void dealLogicBeforeFindView() {
        mContext = this;
    }
    @Override
    public void findViews() {
        setContentView(R.layout.activity_exchange_success);
        rl_returnHome = (RelativeLayout) findViewById(R.id.rl_returnHome);
        tv_titleText = (TextView) findViewById(R.id.tv_titleText);
        tv_returnIntegral = (TextView) findViewById(R.id.tv_returnIntegral_exchangeSuccess);
        tv_storeName = (TextView) findViewById(R.id.tv_storeName_exchangeSuccess);
        tv_payMoney = (TextView) findViewById(R.id.tv_payMoney);
        tv_orderNumber = (TextView) findViewById(R.id.tv_orderNumber);
        tv_checkCode = (TextView) findViewById(R.id.tv_checkCode);
        iv_twoCode = (ImageView) findViewById(R.id.iv_twoCode_exchangeSuccess);
        tv_pay_money=(TextView)findViewById(R.id.tv_pay_money);
    }
    @Override
    public void initData() {
        tv_titleText.setText("支付成功");
        getResult();
    }
    @Override
    public void setListeners() {
        rl_returnHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_returnHome:
                //跳转回首页
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();

                break;

            default:
                break;
        }

    }

    /**
     * 请求支付成功信息
     */
    private void getResult() {
        final BufferCircleDialog dialog = new BufferCircleDialog(mContext);
        dialog.show("", true, null);
        AsyncHttp.post(HttpConstant.PAY_SUCCESS, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                ToastUtils.ToastShowShort(mContext, "网络异常");
                dialog.dialogcancel();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                dialog.dialogcancel();
//                response.toString();
                String status = response.optString("status");
                String msg = response.optString("msg");
                if (status.equals("0")) {
                    JSONArray array = response.optJSONArray("rows");
                    resultList = JSON.parseArray(array.toString(), PayOrderResult.RowsBean.class);
                    //设置数据
                    setData();
                } else {
                    ToastUtils.ToastShowShort(mContext, msg);
                }
            }
        });
    }
    private void setData() {
        if(resultList.size()==1){
           PayOrderResult.RowsBean result = resultList.get(0);
            tv_checkCode.setText(result.getCheckNo());
            tv_orderNumber.setText(result.getNo()+"");
            tv_storeName.setText(result.getBusinessName());
            tv_payMoney.setText(result.getAllPrice()+"元");
            tv_pay_money.setText(result.getAllPrice()+"元");
            TwoCodeUtil.setQRImage(tv_checkCode.getText() + "", iv_twoCode);
        }

    }


}
