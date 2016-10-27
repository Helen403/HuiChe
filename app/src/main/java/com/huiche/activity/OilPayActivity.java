package com.huiche.activity;

import android.view.View;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.lib.lib.base.BaseActivity;

/**
 * Created by Administrator on 2016/9/26.
 */
public class OilPayActivity extends BaseActivity {

    TextView tv_3;


    @Override
    public int getContentView() {
        return R.layout.activity_oil_pay;
    }

    @Override
    public void findViews() {
        setTitle("油卡支付");
        tv_3 = (TextView) findViewById(R.id.tv_3);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {

        setOnListeners(tv_3);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id) {
                    case R.id.tv_3:
                        goToActivityByClass(OilPayResultActivity.class);
                        break;
                }
            }
        });

    }
}
