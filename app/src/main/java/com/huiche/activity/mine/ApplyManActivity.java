package com.huiche.activity.mine;

import android.content.Context;
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
 * Created by Administrator on 2016/9/28.
 */
public class ApplyManActivity extends BaseActivity {

    BufferjihuoViewDialog bufferjihuoViewDialog;
    ImageButton imageLeft_titil_all;
    TextView tv_1;

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
        bufferjihuoViewDialog = new BufferjihuoViewDialog(this);
        bufferjihuoViewDialog.setVisibility(View.GONE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_apply_man, content, false);
        content.addView(view);
        content.addView(bufferjihuoViewDialog);
        setContentView(content);


        imageLeft_titil_all= (ImageButton) view.findViewById(R.id.imageLeft_titil_all);
        tv_1 = (TextView) findViewById(R.id.tv_1);

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {
        tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bufferjihuoViewDialog.setVisibility(View.VISIBLE);
                bufferjihuoViewDialog.tv_1.setText("等待审核中...");
            }
        });

        bufferjihuoViewDialog.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bufferjihuoViewDialog.setVisibility(View.GONE);
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
