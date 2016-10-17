package com.huiche.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.BaseActivity;

/**
 * Created by Administrator on 2016/9/26.
 */
public class OilPayActivity extends BaseActivity {

    ImageButton imageLeft_titil_all;
    TextView tv_3;
    Context context;

    @Override
    public void dealLogicBeforeFindView() {
        context = this;
    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_oil_pay);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        imageLeft_titil_all = (ImageButton) findViewById(R.id.imageLeft_titil_all);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {
        tv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OilPayResultActivity.class);
                startActivity(intent);
            }
        });
        imageLeft_titil_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
