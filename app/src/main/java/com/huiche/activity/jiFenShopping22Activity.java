package com.huiche.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.lib.lib.base.BaseActivity;
import com.huiche.view.Bufferjifenshopping22ViewDialog;
import com.huiche.view.Bufferjifenshopping23ViewDialog;


/**
 * Created by Administrator on 2016/9/27.
 */
public class jiFenShopping22Activity extends BaseActivity {

    Bufferjifenshopping22ViewDialog bufferjifenshopping22ViewDialog;
    Bufferjifenshopping23ViewDialog bufferjifenshopping23ViewDialog;

    TextView tv_6;


    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_jifenshopping22;
    }

    @Override
    protected void onShowMessage(RelativeLayout relativeLayout) {
        super.onShowMessage(relativeLayout);
        bufferjifenshopping22ViewDialog = new Bufferjifenshopping22ViewDialog(this);
        bufferjifenshopping22ViewDialog.setVisibility(View.GONE);
        relativeLayout.addView(bufferjifenshopping22ViewDialog);


        bufferjifenshopping23ViewDialog = new Bufferjifenshopping23ViewDialog(this);
        bufferjifenshopping23ViewDialog.setVisibility(View.GONE);
        relativeLayout.addView(bufferjifenshopping23ViewDialog);

    }

    @Override
    public void findViews() {


        tv_6 = (TextView) findViewById(R.id.tv_6);

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {
        bufferjifenshopping22ViewDialog.tv_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bufferjifenshopping22ViewDialog.setVisibility(View.GONE);
                bufferjifenshopping23ViewDialog.setVisibility(View.VISIBLE);
            }
        });

        bufferjifenshopping23ViewDialog.tv_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bufferjifenshopping23ViewDialog.setVisibility(View.GONE);
            }
        });

        setOnListeners(tv_6);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id) {
                    case R.id.tv_6:
                        bufferjifenshopping22ViewDialog.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }
}
