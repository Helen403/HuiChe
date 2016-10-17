package com.huiche.activity.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiche.R;
import com.huiche.base.BaseActivity;
import com.huiche.lib.view.LoopView;
import com.huiche.lib.view.OnItemSelectedListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class CarManagerActivity extends BaseActivity {

    TextView tv_right_title;
    ListView listView;
    ImageButton imageLeft_titil_all;

    View viewWhell;
    ArrayList<String> list;

    TextView tv_11, tv_12, tv_6,tv_15,edit_3;

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        RelativeLayout content = new RelativeLayout(this);
        content.setClipToPadding(true);
        content.setFitsSystemWindows(true);
        LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        content.setLayoutParams(rl);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_car_manager, content, false);


        viewWhell = inflater.inflate(R.layout.view_whell, content, false);
        viewWhell.setVisibility(View.GONE);
        content.addView(view);
        content.addView(viewWhell);
        setContentView(content);

        tv_11 = (TextView) findViewById(R.id.tv_11);
        viewWhell.findViewById(R.id.iv_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        tv_15= (TextView) findViewById(R.id.tv_15);
        tv_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewWhell.setVisibility(View.VISIBLE);
            }
        });
        edit_3= (TextView) findViewById(R.id.edit_3);
        edit_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewWhell.setVisibility(View.VISIBLE);
            }
        });


        tv_right_title = (TextView) findViewById(R.id.tv_right_title);
        TextView tv = (TextView) viewWhell.findViewById(R.id.tv_2);
        TextView tv3 = (TextView) viewWhell.findViewById(R.id.tv_3);
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

        imageLeft_titil_all= (ImageButton) findViewById(R.id.imageLeft_titil_all);

        LoopView loopView = (LoopView) viewWhell.findViewById(R.id.loopview);
        list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add("法拉利" + i);
        }
        //设置是否循环播放
        //loopView.setNotLoop();
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

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {
        tv_right_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CarManagerActivity.this, "保存成功", Toast.LENGTH_SHORT);
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
