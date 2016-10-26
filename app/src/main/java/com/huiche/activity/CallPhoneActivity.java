package com.huiche.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.huiche.R;
import com.huiche.bean.CallPhoneBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.base.BaseActivity;
import com.huiche.view.CallPhoneView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/18.
 */
public class CallPhoneActivity extends BaseActivity {

    RelativeLayout rl1;
    CallPhoneView callPhoneView;
    String phone;

    @Override
    public int getContentView() {
        return R.layout.activity_call_phone;
    }

    @Override
    public void findViews() {
        setTitle("联系客服");
        rl1 = (RelativeLayout) findViewById(R.id.rl1);
    }

    @Override
    protected void onShowMessage(RelativeLayout relativeLayout) {
        super.onShowMessage(relativeLayout);
        callPhoneView = new CallPhoneView(CallPhoneActivity.this);
        callPhoneView.setVisibility(View.GONE);
        relativeLayout.addView(callPhoneView);
    }

    @Override
    public void initData() {


    }

    private void getCallPhone() {

        bufferCircleView.show();
        ControlUtils.postsEveryTime(Constants.Helen.CALLPHONE, null, CallPhoneBean.class, new ControlUtils.OnControlUtils<CallPhoneBean>() {
            @Override
            public void onSuccess(String url, CallPhoneBean callPhoneBean, ArrayList<CallPhoneBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                bufferCircleView.hide();
                T(callPhoneBean.msg);
                callPhoneView.setVisibility(View.VISIBLE);
                callPhoneView.tv1.setText(callPhoneBean.data.kf_tel);
                phone = callPhoneBean.data.kf_tel;
            }

            @Override
            public void onFailure(String url) {
                bufferCircleView.hide();
                T("请检测网络");
            }
        });
    }

    @Override
    public void setListeners() {
        setOnListeners(rl1, callPhoneView.tvCall);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id) {
                    case R.id.rl1:
                        //获取客服电话
                        getCallPhone();
                        break;
                    case R.id.tv_2:
                        callPhoneView.setVisibility(View.GONE);
                        call();
                        break;
                }
            }
        });
    }

    //拨打电话
    private void call() {
        if (TextUtils.isEmpty(phone))
            return;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        // 开启系统拨号器
        startActivity(intent);
    }


}
