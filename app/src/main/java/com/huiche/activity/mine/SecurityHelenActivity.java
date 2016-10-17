package com.huiche.activity.mine;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.MyApplication;
import com.huiche.bean.SecurityInfoBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.Param;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class SecurityHelenActivity extends com.huiche.lib.lib.base.BaseActivity {

    ImageView iv_2;
    TextView tv_6;
    ImageButton imageLeft_titil_all;
    RelativeLayout rl1;
    RelativeLayout rl2;

    @Override
    public int getContentView() {
        return R.layout.activity_security_helen;
    }

    @Override
    public void findViews() {
        setTitle("安全设置");
        iv_2 = (ImageView) findViewById(R.id.iv_2);
        tv_6 = (TextView) findViewById(R.id.tv_6);
        rl1 = (RelativeLayout) findViewById(R.id.rl1);
        rl2 = (RelativeLayout) findViewById(R.id.rl2);
    }

    @Override
    public void initData() {
        //获取用户信息
        getInfo();
    }

    private void getInfo() {

        Param param = new Param();
        if (MyApplication.loginResultBean != null) {
            param.put("us_id", MyApplication.loginResultBean.data.id);
            StringBuffer sb = new StringBuffer();
            sb.append("{").append("\"us_id\":\"").append(MyApplication.loginResultBean.data.id).append("\"}");
            param.put("key", getMd5Password(sb.toString()));
        } else {
            return;
        }
        bufferCircleView.show();
        ControlUtils.postsEveryTime(Constants.Helen.SECURITY, param, SecurityInfoBean.class, new ControlUtils.OnControlUtils<SecurityInfoBean>() {
            @Override
            public void onSuccess(String url, SecurityInfoBean securityInfoBean, ArrayList<SecurityInfoBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                bufferCircleView.hide();
                T(securityInfoBean.msg);
                setPersonInfo(securityInfoBean);
            }

            @Override
            public void onFailure(String url) {
                bufferCircleView.hide();
                T("请检测网络");
            }
        });
    }

    //设置个人信息
    private void setPersonInfo(SecurityInfoBean securityInfoBean) {

        StringBuffer sb = new StringBuffer();
        sb.append(securityInfoBean.data.us_mobile.charAt(0)).append(securityInfoBean.data.us_mobile.charAt(1))
                .append(securityInfoBean.data.us_mobile.charAt(2)).append("xxxx").append(securityInfoBean.data.us_mobile.charAt(7)).append(securityInfoBean.data.us_mobile.charAt(8)).append(securityInfoBean.data.us_mobile.charAt(9)).append(securityInfoBean.data.us_mobile.charAt(10));
        tv_6.setText("已绑定手机号" + sb.toString());
    }


    @Override
    public void setListeners() {

        setOnListeners(rl1, rl2);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id) {
                    //修改密码
                    case R.id.rl1:
                        Intent intent = new Intent(SecurityHelenActivity.this, SecurityCenterActivity.class);
                        startActivity(intent);
                        break;
                    //改变手机
                    case R.id.rl2:
                        Intent intent1 = new Intent(SecurityHelenActivity.this, ChangePhoneActivity.class);
                        startActivity(intent1);
                        break;

                }
            }
        });


    }
}
