package com.huiche.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiche.R;
import com.huiche.base.BaseActivity;
import com.huiche.lib.view.LoopView;
import com.huiche.lib.view.OnItemSelectedListener;
import com.huiche.view.BufferbaoxianViewDialog;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/26.
 */
public class InsuranceActivity extends BaseActivity {
    Context context;
    BufferbaoxianViewDialog bufferbaoxianViewDialog;
    TextView tv_11, tv_12, tv_6;
    ImageButton imageLeft_titil_all;
    View viewWhell;
    ArrayList<String> list;

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
        bufferbaoxianViewDialog = new BufferbaoxianViewDialog(this);
        bufferbaoxianViewDialog.setVisibility(View.GONE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_insurance, content, false);


        viewWhell = inflater.inflate(R.layout.view_whell, content, false);
        viewWhell.setVisibility(View.GONE);
        content.addView(view);
        content.addView(bufferbaoxianViewDialog);
        content.addView(viewWhell);


        setContentView(content);

        tv_12 = (TextView) findViewById(R.id.tv_12);

        tv_11 = (TextView) findViewById(R.id.tv_11);
        findViewById(R.id.iv_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tv_6 = (TextView) findViewById(R.id.tv_6);
        imageLeft_titil_all = (ImageButton) findViewById(R.id.imageLeft_titil_all);

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


        LoopView loopView = (LoopView) viewWhell.findViewById(R.id.loopview);
        list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add("平安保险" + i);
        }
        //设置是否循环播放
        //loopView.setNotLoop();
        //滚动监听
        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                Toast.makeText(InsuranceActivity.this, list.get(index), Toast.LENGTH_SHORT).show();
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
        tv_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bufferbaoxianViewDialog.setVisibility(View.VISIBLE);
            }
        });

        //礼品卡
        tv_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsuranceActivity.this, DiscountActivity.class);
                startActivity(intent);
            }
        });

        //保险公司
        tv_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewWhell.setVisibility(View.VISIBLE);
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
