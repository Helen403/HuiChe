package com.huiche.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiche.R;
import com.huiche.bean.ChangePhoneBean;
import com.huiche.bean.VerificationBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.Param;
import com.huiche.lib.lib.Utils.VerificationUtils;
import com.huiche.lib.lib.base.BaseActivity;
import com.huiche.lib.lib.base.BaseApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChangePhoneActivity extends BaseActivity implements OnClickListener {
    private Button btn_confirm;
    private EditText et_number, et_code;
    private ImageButton delete_number, delete_code;
    private TextView tv_code;
    private String phone, code;
    Handler handler = new Handler();


    @Override
    public int getContentView() {
        return R.layout.activity_change_phone;
    }

    @Override
    public void findViews() {
        setTitle("更换绑定手机");
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        et_number = (EditText) findViewById(R.id.et_number);
        et_code = (EditText) findViewById(R.id.et_code);
        et_number.addTextChangedListener(watcher);
        et_code.addTextChangedListener(watcher);
        delete_number = (ImageButton) findViewById(R.id.delete_number);
        delete_code = (ImageButton) findViewById(R.id.delete_code);
        tv_code = (TextView) findViewById(R.id.tv_code);
        LinearLayout ll_callphone = (LinearLayout) findViewById(R.id.ll_callphone);
        ll_callphone.setOnClickListener(this);
    }

    @Override
    public void initData() {


    }

    public void BindUserPhone() {
        phone = et_number.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            T("请输入新的手机号");
            return;
        }
        if (BaseApplication.phone != null) {
            if (phone.equals(BaseApplication.phone)) {
                T("新手机号与旧手机号相同");
            }
        } else {
            T("请登录");
            return;
        }

        code = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            T("请输入验证码");
            return;
        }

        bufferCircleView.show();
        Param param = new Param();
        param.put("us_id", BaseApplication.loginResultBean.data.id);
        param.put("mobile", phone);
        param.put("code", code);
        StringBuffer sb = new StringBuffer();

        sb.append("{").append("\"us_id\":\"").append(BaseApplication.loginResultBean.data.id).append("\",\"").append("mobile").append("\":\"").append(phone).append("\",\"").append("code").append("\":\"").append(code).append("\"}");
        param.put("key", getMd5Password(sb.toString()));
        ControlUtils.postsEveryTime(Constants.Helen.CHANGEPHONE, param, ChangePhoneBean.class, new ControlUtils.OnControlUtils<ChangePhoneBean>() {
            @Override
            public void onSuccess(String url, ChangePhoneBean changePhoneBean, ArrayList<ChangePhoneBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                T(changePhoneBean.msg);
                bufferCircleView.hide();
            }

            @Override
            public void onFailure(String url) {
                bufferCircleView.hide();
                T("请检查网络");
            }
        });

    }

    @Override
    public void setListeners() {
        delete_number.setOnClickListener(this);
        delete_code.setOnClickListener(this);
        tv_code.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);


    }

    //EditText监听事件
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String number = et_number.getText().toString().trim();
            if (!number.equals("")) {
                delete_number.setVisibility(View.VISIBLE);
            } else {
                delete_number.setVisibility(View.INVISIBLE);
            }
            String code = et_code.getText().toString().trim();
            if (!code.equals("")) {
                delete_code.setVisibility(View.VISIBLE);
            } else {
                delete_code.setVisibility(View.INVISIBLE);
            }
            if (!number.equals("") && !code.equals("")) {
                btn_confirm.setBackgroundResource(R.drawable.buton_blue_shape);
                btn_confirm.setEnabled(true);
            } else {
                btn_confirm.setBackgroundResource(R.drawable.buton_grey_shape);
                btn_confirm.setEnabled(false);
            }


        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.delete_code:
                et_code.setText("");
                break;
            case R.id.delete_number:
                et_number.setText("");
                break;
            case R.id.tv_code:
                String number = et_number.getText().toString();
                if (number.equals("")) {
                    Toast.makeText(ChangePhoneActivity.this, "请输入新的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                //需要判断输入号码是否为手机号码
                boolean isPhone = VerificationUtils.matcherPhoneNum(number);
                if (isPhone) {
                    //发送后不能再点击
                    tv_code.setEnabled(false);
                    //获取验证码
                    getCode(number);
                } else {
                    T("请输入正确手机号码");
                    return;
                }
                break;
            case R.id.btn_confirm:

                //修改手机
                BindUserPhone();
                break;

            //拨打热线电话
            case R.id.ll_callphone:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + "4000088769"));
                // 开启系统拨号器
                startActivity(intent);
                break;
        }
    }

    int time = 60;

    /**
     * 开始图片滚动任务
     */
    private void startImageTimerTask() {
        stopImageTimerTask();
        handler.postDelayed(runnable, 1000);
    }

    /**
     * 停止图片滚动任务
     */
    private void stopImageTimerTask() {
        handler.removeCallbacks(runnable);
    }


    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if (time == 0) {
                tv_code.setText("获取验证码");
                tv_code.setEnabled(true);
                time = 60;
                stopImageTimerTask();
            } else {
                handler.postDelayed(runnable, 1000);
                String str = String.format("重新发送(%ds)", time);
                tv_code.setText(str);
                time--;
            }
        }
    };


    //获取验证码
    public void getCode(String phone) {
        //改变发送验证码的样式
        startImageTimerTask();

        Param param = new Param();
        param.put("user", phone);
        StringBuffer sb = new StringBuffer();

        sb.append("{").append("\"user\":\"").append(phone).append("\"}");
        L(sb.toString());
        param.put("key", getMd5Password(sb.toString()));
        ControlUtils.postsEveryTime(Constants.Helen.VERIFICATION, param, VerificationBean.class, new ControlUtils.OnControlUtils<VerificationBean>() {
            @Override
            public void onSuccess(String url, VerificationBean verificationBean, ArrayList<VerificationBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                T(verificationBean.msg);
                switch (verificationBean.status) {
                    //10015:获取验证码失败
                    case 10015:
                        tv_code.setText("获取验证码");
                        tv_code.setTextColor(Color.parseColor("#DDAE92"));
                        tv_code.setEnabled(true);
                        time = 60;
                        stopImageTimerTask();
                        break;

                    //10016:验证码已成功发送
                    case 10016:


                        break;
                }

            }

            @Override
            public void onFailure(String url) {
                T("系统繁忙，请稍后发送");
            }
        });
    }

}
