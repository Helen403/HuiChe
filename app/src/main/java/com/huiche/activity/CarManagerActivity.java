package com.huiche.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiche.R;
import com.huiche.lib.view.LoopView;
import com.huiche.lib.view.OnItemSelectedListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class CarManagerActivity extends com.huiche.lib.lib.base.BaseActivity {

    View viewWhell;
    ArrayList<String> list;

    TextView tv_11, tv_12, tv_6, tv_15, edit_3;
    TextView tv3;

    @Override
    public int getContentView() {
        return R.layout.activity_car_manager;
    }


    @Override
    protected void onShowMessage(RelativeLayout relativeLayout) {
        super.onShowMessage(relativeLayout);
        viewWhell = inflater.inflate(R.layout.view_whell, content, false);
        viewWhell.setVisibility(View.GONE);
        content.addView(viewWhell);

    }

    @Override
    public void findViews() {
        setTitle("车辆管理");
        getRightBtn().setText("保存");


        //设置滑轮
        setLoopView();
    }


    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {

        getRightBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CarManagerActivity.this, "保存成功", Toast.LENGTH_SHORT);
            }
        });

//        setOnListeners();
//        setOnClick(new onClick() {
//            @Override
//            public void onClick(View v, int id) {
//                switch (id) {
//
//                }
//            }
//        });


    }


    private void setLoopView() {

        tv_11 = (TextView) findViewById(R.id.tv_11);
        viewWhell.findViewById(R.id.iv_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        tv_15 = (TextView) findViewById(R.id.tv_15);
        tv_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewWhell.setVisibility(View.VISIBLE);
            }
        });
        edit_3 = (TextView) findViewById(R.id.edit_3);
        edit_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewWhell.setVisibility(View.VISIBLE);
            }
        });


        TextView tv = (TextView) viewWhell.findViewById(R.id.tv_2);
        tv3 = (TextView) viewWhell.findViewById(R.id.tv_3);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewWhell.setVisibility(View.GONE);
            }
        });


        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewWhell.setVisibility(View.GONE);
            }
        });


        LoopView loopView = (LoopView) viewWhell.findViewById(R.id.loopview);
        list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add("法拉利" + i);
        }
        //滚动监听
        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                Toast.makeText(CarManagerActivity.this, list.get(index), Toast.LENGTH_SHORT).show();
            }
        });
        //设置原始数据
        loopView.setItems(list);
        //设置初始位置
        loopView.setInitPosition(5);
        //设置字体大小
        loopView.setTextSize(30);
    }

}
