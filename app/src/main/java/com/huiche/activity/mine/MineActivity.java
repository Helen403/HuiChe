package com.huiche.activity.mine;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.MyApplication;
import com.huiche.lib.lib.base.BaseActivity;
import com.huiche.lib.lib.custemview.CircleImageView;

/**
 * Created by Administrator on 2016/10/17.
 */
public class MineActivity extends BaseActivity {

    private ImageView iv1;
    private CircleImageView icon;
    private TextView tv1;
    private TextView tv2;
    private ImageView iv2;
    private TextView tv3;
    private ImageView iv3;
    private ImageView iv5;
    private TextView tv4;


    @Override
    public int getContentView() {
        return R.layout.activity_mine;
    }

    @Override
    public void findViews() {
        setTitle("个人信息");
        getRightBtn().setText("确定");

        iv1 = (ImageView) findViewById(R.id.iv_1);
        icon = (CircleImageView) findViewById(R.id.icon);
        tv1 = (TextView) findViewById(R.id.tv_1);
        tv2 = (TextView) findViewById(R.id.tv_2);
        iv2 = (ImageView) findViewById(R.id.iv_2);
        tv3 = (TextView) findViewById(R.id.tv_3);
        iv3 = (ImageView) findViewById(R.id.iv_3);
        iv5 = (ImageView) findViewById(R.id.iv_5);
        tv4 = (TextView) findViewById(R.id.tv_4);
        icon.setBorderWidth(0);
        icon.setBorderColor(Color.parseColor("#00000000"));
        //设置男或者女
        //默认为男
        iv3.setTag(1);
    }

    @Override
    public void initData() {
        setInfo();
    }

    //设置个人信息
    private void setInfo() {
        if (MyApplication.loginResultBean != null) {
            //设置名称
            tv1.setText(MyApplication.loginResultBean.data.username);
            //设置生日
            tv4.setText(MyApplication.loginResultBean.data.birthday);
            //设置头像
            setImageByUrl(MyApplication.loginResultBean.data.headerimg, icon);
            //设置男或者女
            if ("0".equals(MyApplication.loginResultBean.data.sex)){
                //男
                iv3.setTag(1);
                iv2.setTag(0);
                iv2.setImageResource(R.drawable.black_circle);
                iv3.setImageResource(R.drawable.blue_button_confirmation);
            }else {
                //女
                iv3.setTag(0);
                iv2.setTag(1);
                iv2.setImageResource(R.drawable.blue_button_confirmation);
                iv3.setImageResource(R.drawable.black_circle);
            }
        }
    }


    @Override
    public void setListeners() {

        setOnListeners(iv2, iv3);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id) {
                    //女
                    case R.id.iv_2:
                        iv2.setTag(1);
                        iv3.setTag(0);
                        iv2.setImageResource(R.drawable.blue_button_confirmation);
                        iv3.setImageResource(R.drawable.black_circle);
                        break;
                    //男
                    case R.id.iv_3:
                        iv2.setTag(0);
                        iv3.setTag(1);
                        iv2.setImageResource(R.drawable.black_circle);
                        iv3.setImageResource(R.drawable.blue_button_confirmation);
                        break;
                }
            }
        });

    }
}
