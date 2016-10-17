package com.huiche.activity.mine;

import android.view.View;
import android.widget.ImageButton;

import com.huiche.R;
import com.huiche.base.BaseActivity;


/**
 * Created by Administrator on 2016/9/28.
 */
public class PayHelenActivity extends BaseActivity {

    ImageButton imageLeft_titil_all;

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_pay_helen);
        imageLeft_titil_all = (ImageButton) findViewById(R.id.imageLeft_titil_all);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {
        imageLeft_titil_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
