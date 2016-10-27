package com.huiche.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.huiche.R;
import com.huiche.adapter.OilAdapter;
import com.huiche.bean.OilBaen;
import com.huiche.lib.lib.base.BaseActivity;
import com.huiche.view.BufferpayViewDialog;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/26.
 */
public class OilCardRechargeActivity extends BaseActivity {

    GridView gv;
    OilAdapter oilAdapter;
    BufferpayViewDialog bufferpayViewDialog;


    @Override
    public int getContentView() {
        return R.layout.activity_oilcard_recharge;
    }


    @Override
    protected void onShowMessage(RelativeLayout relativeLayout) {
        super.onShowMessage(relativeLayout);
        bufferpayViewDialog = new BufferpayViewDialog(OilCardRechargeActivity.this);
        bufferpayViewDialog.setVisibility(View.GONE);
        relativeLayout.addView(bufferpayViewDialog);
    }

    @Override
    public void findViews() {
        setTitle("油卡充值");
        gv = (GridView) findViewById(R.id.gv);
    }

    @Override
    public void initData() {
        ArrayList<OilBaen> data = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            OilBaen oilbean = new OilBaen();
            oilbean.money = i * 50 + "";

            oilbean.price = i * 50 - 2 + "";
            data.add(oilbean);
        }

        oilAdapter = new OilAdapter(data);
        gv.setAdapter(oilAdapter);
    }

    @Override
    public void setListeners() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bufferpayViewDialog.setVisibility(View.VISIBLE);
            }
        });

        bufferpayViewDialog.tv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivityByClass(OilPayActivity.class);
            }
        });
        bufferpayViewDialog.tv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivityByClass(OilPayActivity.class);
            }
        });

    }
}
