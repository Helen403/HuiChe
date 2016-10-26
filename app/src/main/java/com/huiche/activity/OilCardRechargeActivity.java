package com.huiche.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.huiche.R;
import com.huiche.adapter.Adapter_oil;
import com.huiche.bean.OilBaen;
import com.huiche.lib.lib.base.BaseActivity;
import com.huiche.view.BufferpayViewDialog;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/26.
 */
public class OilCardRechargeActivity extends BaseActivity {

    ImageButton imageLeft_titil_all;
    GridView gv;
    Adapter_oil adapter_oil;
    Context context;
    BufferpayViewDialog bufferpayViewDialog;


    @Override
    public int getContentView() {
        return R.layout.activity_oilcard_recharge;
    }


    @Override
    protected void onShowMessage(RelativeLayout relativeLayout) {
        super.onShowMessage(relativeLayout);

        bufferpayViewDialog = new BufferpayViewDialog(context);
        bufferpayViewDialog.setVisibility(View.GONE);
        relativeLayout.addView(bufferpayViewDialog);
    }

    @Override
    public void findViews() {
        gv = (GridView) findViewById(R.id.gv);
    }

    @Override
    public void initData() {
        //保留一位小数

        ArrayList<OilBaen> data = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            OilBaen oilbean = new OilBaen();
            oilbean.money = i * 50 + "";

            oilbean.price = i * 50 - 2 + "";
            data.add(oilbean);
        }

        adapter_oil = new Adapter_oil(data);
        gv.setAdapter(adapter_oil);
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
                Intent intent = new Intent(context, OilPayActivity.class);
                startActivity(intent);
            }
        });
        bufferpayViewDialog.tv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OilPayActivity.class);
                startActivity(intent);
            }
        });

    }
}
