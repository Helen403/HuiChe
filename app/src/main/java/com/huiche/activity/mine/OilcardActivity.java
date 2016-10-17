package com.huiche.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.BaseActivity;
import com.huiche.view.BufferjihuoViewDialog;


/**
 * Created by Administrator on 2016/9/26.
 */
public class OilcardActivity extends BaseActivity {
    ImageButton imageLeft_titil_all;

    TextView tv1, tv_4, tv_5;

    BufferjihuoViewDialog bufferjihuoViewDialog;


    RelativeLayout rl1, rl2;
    Context context;

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
        bufferjihuoViewDialog = new BufferjihuoViewDialog(context);
        bufferjihuoViewDialog.setVisibility(View.GONE);
//        bufferjihuoViewDialog.setVisibility(View.GONE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_oilcard, content, false);
        content.addView(view);
        content.addView(bufferjihuoViewDialog);
        setContentView(content);


        imageLeft_titil_all = (ImageButton) findViewById(R.id.imageLeft_titil_all);
        tv1 = (TextView) findViewById(R.id.tv_1);
        tv_4 = (TextView) findViewById(R.id.tv_4);
        tv_5 = (TextView) findViewById(R.id.tv_5);

        rl1 = (RelativeLayout) findViewById(R.id.rl_1);
        rl2 = (RelativeLayout) findViewById(R.id.rl_2);

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bufferjihuoViewDialog.setVisibility(View.VISIBLE);
            }
        });
        imageLeft_titil_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bufferjihuoViewDialog.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bufferjihuoViewDialog.setVisibility(View.GONE);
                rl1.setVisibility(View.VISIBLE);
                rl2.setVisibility(View.VISIBLE);
                tv1.setText("苏打绿");
                tv_4.setText("3000元");
                tv_5.setText("5278254151456");
            }
        });


        //油卡充值
        rl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OilcardRechargeActivity.class);
                startActivity(intent);

            }
        });


    }
}
