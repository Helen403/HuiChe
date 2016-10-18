package com.huiche.activity.mine;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.MyApplication;
import com.huiche.bean.ApplyManBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.Param;
import com.huiche.view.BufferjihuoViewDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/9/28.
 */
public class ApplyManActivity extends com.huiche.lib.lib.base.BaseActivity {

    BufferjihuoViewDialog bufferjihuoViewDialog;
    TextView tv_1;
    EditText edit1;
    EditText edit2;
    EditText edit3;


    @Override
    public int getContentView() {
        return R.layout.activity_apply_man;
    }

    @Override
    protected void onShowMessage(RelativeLayout relativeLayout) {
        super.onShowMessage(relativeLayout);
        bufferjihuoViewDialog = new BufferjihuoViewDialog(this);
        bufferjihuoViewDialog.setVisibility(View.GONE);
        relativeLayout.addView(bufferjihuoViewDialog);
    }

    @Override
    public void findViews() {
        setTitle("申请合伙人");


        tv_1 = (TextView) findViewById(R.id.tv_1);
        edit1 = (EditText) findViewById(R.id.edit_1);
        edit2 = (EditText) findViewById(R.id.edit_2);
        edit3 = (EditText) findViewById(R.id.edit_3);

    }

    @Override
    public void initData() {
        if (MyApplication.loginResultBean == null) {
            T("请登录");
            return;
        }
        if ("1".equals(MyApplication.loginResultBean.data.partner_state)) {
            bufferjihuoViewDialog.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setListeners() {
        setOnListeners(tv_1);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id) {
                    case R.id.tv_1:

                        //申请合伙人
                        appInfo();

                        break;
                }
            }
        });

    }

    private void appInfo() {
        String name = edit1.getText().toString();
        if (TextUtils.isEmpty(name)) {
            T("姓名不能为空");
            return;
        }
        String phone = edit2.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            T("手机号不能为空");
            return;
        }
        String pay = edit3.getText().toString();
        if (TextUtils.isEmpty(pay)) {
            T("支付宝号码不能为空");
            return;
        }
        if (MyApplication.loginResultBean == null) {
            T("请登录");
            return;
        }

        bufferjihuoViewDialog.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bufferjihuoViewDialog.setVisibility(View.GONE);
            }
        });


        bufferjihuoViewDialog.setVisibility(View.VISIBLE);
        bufferjihuoViewDialog.tv_1.setText("等待审核中...");
        Param param = new Param();
        param.put("us_id", MyApplication.loginResultBean.data.id);
        param.put("mobile", phone);
        param.put("realname", name);
        param.put("alipay", pay);
        StringBuffer sb = new StringBuffer();
        sb.append("{").append("\"us_id\":\"").append(MyApplication.loginResultBean.data.id).append("\",\"mobile\":\"").append(phone).append("\",\"realname\":\"").append(name).append("\",\"alipay\":\"").append(pay).append("\"}");
        param.put("key", getMd5Password(sb.toString()));
        ControlUtils.postsEveryTime(Constants.Helen.ADDPARTER, param, ApplyManBean.class, new ControlUtils.OnControlUtils<ApplyManBean>() {
            @Override
            public void onSuccess(String url, ApplyManBean applyManBean, ArrayList<ApplyManBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                T(applyManBean.msg);
                //10044:申请提交成功
                if (10044 == applyManBean.status) {
                    MyApplication.loginResultBean.data.partner_state = "1";
                    finish();
                }
            }

            @Override
            public void onFailure(String url) {
                bufferjihuoViewDialog.setVisibility(View.GONE);
                T("请检测网络");
            }
        });
    }
}
