package com.huiche.view;

import android.content.Context;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.lib.lib.base.BaseView;

/**
 * Created by Administrator on 2016/10/18.
 */
public class CallPhoneView extends BaseView {
    public TextView tv1;
    public TextView tvCall;


    public CallPhoneView(Context context) {
        super(context);
    }

    @Override
    public int getContentView() {
        return R.layout.view_callphone;
    }

    @Override
    public void findViews() {
        tv1 = (TextView) findViewById(R.id.tv_1);
        tvCall = (TextView) findViewById(R.id.tv_2);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {

    }
}
