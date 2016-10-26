package com.huiche.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiche.R;
import com.huiche.lib.lib.base.BaseActivity;
import com.huiche.lib.view.LoopView;
import com.huiche.lib.view.OnItemSelectedListener;
import com.huiche.view.BufferbaoxianViewDialog;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/26.
 */
public class InsuranceActivity extends BaseActivity {
    BufferbaoxianViewDialog bufferbaoxianViewDialog;
    TextView tv_11, tv_12, tv_6;
    View viewWhell;
    ArrayList<String> list;


    @Override
    public int getContentView() {
        return R.layout.activity_insurance;
    }

    @Override
    protected void onShowMessage(RelativeLayout relativeLayout) {
        super.onShowMessage(relativeLayout);
        bufferbaoxianViewDialog = new BufferbaoxianViewDialog(this);
        bufferbaoxianViewDialog.setVisibility(View.GONE);
        relativeLayout.addView(bufferbaoxianViewDialog);
        viewWhell = inflater.inflate(R.layout.view_whell, content, false);
        viewWhell.setVisibility(View.GONE);
        relativeLayout.addView(viewWhell);
    }

    @Override
    public void findViews() {

        tv_12 = (TextView) findViewById(R.id.tv_12);

        tv_11 = (TextView) findViewById(R.id.tv_11);
        findViewById(R.id.iv_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tv_6 = (TextView) findViewById(R.id.tv_6);

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
        setOnListeners(tv_11, tv_12, tv_6);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id) {
                    case R.id.tv_11:
                        bufferbaoxianViewDialog.setVisibility(View.VISIBLE);
                        break;
                    //礼品卡
                    case R.id.tv_12:
//                        Intent intent = new Intent(InsuranceActivity.this, DiscountActivity.class);
//                        startActivity(intent);
                        break;
                    //保险公司
                    case R.id.tv_6:
                        viewWhell.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });


    }
}
