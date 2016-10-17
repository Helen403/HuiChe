package com.huiche.activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.BaseActivity;

/**
 * 支付订单
 *
 * @author Administrator
 */
public class PayOrderActivity extends BaseActivity implements OnClickListener {
    private ImageButton imb_titleBack;
    private TextView tv_titleText;

    @Override
    public void dealLogicBeforeFindView() {
    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_pay_order);
        imb_titleBack = (ImageButton) findViewById(R.id.imb_titleBack);
        tv_titleText = (TextView) findViewById(R.id.tv_titleText);
    }

    @Override
    public void initData() {
        tv_titleText.setText("支付订单");
    }

    @Override
    public void setListeners() {
        imb_titleBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imb_titleBack:
                finish();
                break;
            default:
                break;
        }

    }


}
