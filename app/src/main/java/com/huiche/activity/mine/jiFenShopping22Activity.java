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
import com.huiche.view.Bufferjifenshopping22ViewDialog;
import com.huiche.view.Bufferjifenshopping23ViewDialog;


/**
 * Created by Administrator on 2016/9/27.
 */
public class jiFenShopping22Activity extends BaseActivity {

    Bufferjifenshopping22ViewDialog bufferjifenshopping22ViewDialog;
    Bufferjifenshopping23ViewDialog bufferjifenshopping23ViewDialog;

    TextView tv_6;

    ImageButton imageLeft_titil_all;


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
        bufferjifenshopping22ViewDialog = new Bufferjifenshopping22ViewDialog(this);
        bufferjifenshopping22ViewDialog.setVisibility(View.GONE);

        bufferjifenshopping23ViewDialog = new Bufferjifenshopping23ViewDialog(this);
        bufferjifenshopping23ViewDialog.setVisibility(View.GONE);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_jifenshopping22, content, false);
        content.addView(view);
        content.addView(bufferjifenshopping22ViewDialog);
        content.addView(bufferjifenshopping23ViewDialog);
        setContentView(content);
//        setContentView(R.layout.activity_jifenshopping22);


        tv_6 = (TextView) findViewById(R.id.tv_6);
        imageLeft_titil_all= (ImageButton) findViewById(R.id.imageLeft_titil_all);

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

        tv_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bufferjifenshopping22ViewDialog.setVisibility(View.VISIBLE);
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
