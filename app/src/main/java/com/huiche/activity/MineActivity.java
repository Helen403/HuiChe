package com.huiche.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.base.BaseActivity;
import com.huiche.lib.lib.base.BaseApplication;
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
    private TextView tv_12;


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
        tv_12 = (TextView) findViewById(R.id.tv_12);
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
        if (BaseApplication.loginResultBean == null) {
            T("请登录");
            return;
        }
        //设置名称
        tv1.setText(BaseApplication.loginResultBean.data.username);
        //设置生日
        tv4.setText(BaseApplication.loginResultBean.data.birthday);
        //设置头像
        setImageByUrl(BaseApplication.loginResultBean.data.headerimg, icon);
        //设置男或者女
        if ("0".equals(BaseApplication.loginResultBean.data.sex)) {
            //男
            iv3.setTag(1);
            iv2.setTag(0);
            iv2.setImageResource(R.drawable.black_circle);
            iv3.setImageResource(R.drawable.blue_button_confirmation);
        } else {
            //女
            iv3.setTag(0);
            iv2.setTag(1);
            iv2.setImageResource(R.drawable.blue_button_confirmation);
            iv3.setImageResource(R.drawable.black_circle);
        }
    }


    @Override
    public void setListeners() {

        setOnListeners(iv2, iv3, tv_12);
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

                    //退出当前帐号
                    case R.id.tv_12:
                        cancelAccount();

                        break;

                }
            }
        });

    }

    //退出当前帐号
    private void cancelAccount() {
        if (BaseApplication.loginResultBean == null) {
            T("请登录");
            return;
        }
        //清除当前帐号数据
        BaseApplication.loginResultBean = null;
        BaseApplication.phone = null;
        setResult(Constants.startActivityForResult.CANCELLATIONRESULT);
        T("成功退出当前帐号");
        //回到上一个界面
        finish();
    }
}
