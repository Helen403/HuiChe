package com.huiche.activity.mine;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.huiche.R;
import com.huiche.adapter.Adapter_oil;
import com.huiche.base.BaseActivity;
import com.huiche.bean.OilBaen;
import com.huiche.view.BufferpayViewDialog;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/26.
 */
public class OilcardRechargeActivity extends BaseActivity {

    ImageButton imageLeft_titil_all;
    GridView gv;
    Adapter_oil adapter_oil;
    Context context;
    BufferpayViewDialog bufferpayViewDialog;

    @Override
    public void dealLogicBeforeFindView() {
        context = this;
    }

    @Override
    public void findViews() {


        RelativeLayout content = new RelativeLayout(this);
        content.setClipToPadding(true);
        content.setFitsSystemWindows(true);
        LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        content.setLayoutParams(rl);
        bufferpayViewDialog = new BufferpayViewDialog(context);
        bufferpayViewDialog.setVisibility(View.GONE);
//        bufferjihuoViewDialog.setVisibility(View.GONE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_oilcard_recharge, content, false);
        content.addView(view);
        content.addView(bufferpayViewDialog);
        setContentView(content);


        gv = (GridView) findViewById(R.id.gv);
        imageLeft_titil_all = (ImageButton) findViewById(R.id.imageLeft_titil_all);


    }

    @TargetApi(24)
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
        imageLeft_titil_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
