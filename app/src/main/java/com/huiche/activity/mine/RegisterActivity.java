package com.huiche.activity.mine;

import android.graphics.Color;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.bean.VerificationBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.Utils.CipherUtils;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.Param;
import com.huiche.lib.lib.Utils.VerificationUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class RegisterActivity extends com.huiche.lib.lib.base.BaseActivity {

    private LinearLayout rlTitleMainActivity;
    private TextView textTitilAll;
    private ImageButton imageRigthTitilAll;
    private TextView tvRightTitle;
    private TextView tv1;
    private EditText edittext;
    private ImageView iv1;
    private TextView tv3;
    private EditText edit1;
    private TextView tv2;
    private TextView tv6;
    private EditText edit4;
    private ImageView iv3;
    private TextView tv12;
    private TextView tv7;
    private ImageView iv4;
    private TextView tv8;

    Handler handler = new Handler();


    @Override
    public int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    public void findViews() {
        setTitle("账户注册");
        rlTitleMainActivity = (LinearLayout) findViewById(R.id.rl_title_mainActivity);
        textTitilAll = (TextView) findViewById(R.id.text_titil_all);
        imageRigthTitilAll = (ImageButton) findViewById(R.id.imageRigth_titil_all);
        tvRightTitle = (TextView) findViewById(R.id.tv_right_title);
        tv1 = (TextView) findViewById(R.id.tv_1);
        edittext = (EditText) findViewById(R.id.edittext);
        iv1 = (ImageView) findViewById(R.id.iv_1);
        tv3 = (TextView) findViewById(R.id.tv_3);
        edit1 = (EditText) findViewById(R.id.edit_1);
        tv2 = (TextView) findViewById(R.id.tv_2);
        tv6 = (TextView) findViewById(R.id.tv_6);
        edit4 = (EditText) findViewById(R.id.edit_4);
        iv3 = (ImageView) findViewById(R.id.iv_3);
        tv12 = (TextView) findViewById(R.id.tv_12);
        tv7 = (TextView) findViewById(R.id.tv_7);
        iv4 = (ImageView) findViewById(R.id.iv_4);
        tv8 = (TextView) findViewById(R.id.tv_8);

    }

    @Override
    public void initData() {
    }

    @Override
    public void setListeners() {
        edittext.addTextChangedListener(watcher);
        edit1.addTextChangedListener(watcher);
        edit4.addTextChangedListener(watcher);

        setOnListeners(tv12, iv1, iv3, tv2);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id) {
                    case R.id.tv_12:
                        //提交数据
                        commit();
                        break;
                    //清除手机号
                    case R.id.iv_1:
                        edittext.setText("");
                        break;
                    //清除密码
                    case R.id.iv_3:
                        edit4.setText("");
                        break;
                    //发送验证码
                    case R.id.tv_2:
                        sendVerificationNum();
                        break;
                }
            }
        });
    }

    //发送验证码
    private void sendVerificationNum() {
        //先获取手机号
        String phone = edittext.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            T("手机号不能为空");
            return;
        }
        //判断手机格式
        if (!VerificationUtils.matcherPhoneNum(phone)) {
            T("手机号不对");
            return;
        }

        //发送后不能再点击
        tv2.setEnabled(false);

        bufferCircleView.show();
        //改变发送验证码的样式
        startImageTimerTask();

        Param param = new Param();
        param.put("user", phone);
        StringBuffer sb = new StringBuffer();

        sb.append("{").append("\"user\":\"").append(phone).append("\"}");
        param.put("key", getMd5Password(sb.toString()));
        ControlUtils.postsEveryTime(Constants.Helen.VERIFICATION, param, VerificationBean.class, new ControlUtils.OnControlUtils<VerificationBean>() {
            @Override
            public void onSuccess(String url, VerificationBean verificationBean, ArrayList<VerificationBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                bufferCircleView.hide();
                T(verificationBean.msg);
                switch (verificationBean.status) {
                    //10015:获取验证码失败
                    case 10015:
                        tv2.setText("获取验证码");
                        tv2.setTextColor(Color.parseColor("#DDAE92"));
                        tv2.setEnabled(true);
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
                bufferCircleView.hide();
            }
        });

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
                tv2.setText("获取验证码");
                tv2.setTextColor(Color.parseColor("#DDAE92"));
                tv2.setEnabled(true);
                time = 60;
                stopImageTimerTask();
            } else {
                handler.postDelayed(runnable, 1000);
                String str = String.format("已验证%ds后重新发送", time);
                tv2.setText(str);
                tv2.setTextColor(Color.parseColor("#CCCCCC"));
                time--;
            }
        }
    };


    //提交数据
    private void commit() {
        String phone = edittext.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            T("手机号不能为空");
            return;
        }
        String VerificationNum = edit1.getText().toString();
        if (TextUtils.isEmpty(VerificationNum)) {
            T("验证码不能为空");
            return;
        }
        String password = edit4.getText().toString();
        if (TextUtils.isEmpty(password)) {
            T("密码不能为空");
            return;
        }

        bufferCircleView.show();
        //加密发送
        Param param = new Param();
        param.put("user", phone);
        param.put("pass", CipherUtils.md5L(password));
        param.put("code", VerificationNum);
        StringBuffer sb = new StringBuffer();

        sb.append("{").append("\"user\":\"").append(phone).append("\",\"pass\":\"").append(CipherUtils.md5L(password)).append("\",\"code\":\"").append(VerificationNum).append("\"}");
        L(sb.toString());
        param.put("key", getMd5Password(sb.toString()));
        ControlUtils.postsEveryTime(Constants.Helen.REGISTER, param, VerificationBean.class, new ControlUtils.OnControlUtils<VerificationBean>() {
            @Override
            public void onSuccess(String url, VerificationBean verificationBean, ArrayList<VerificationBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                bufferCircleView.hide();
                T(verificationBean.msg);
                //注册成功
                if (10012 == verificationBean.status) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);
                }


                if (10015 == verificationBean.status) {
                    tv2.setText("获取验证码");
                    tv2.setTextColor(Color.parseColor("#DDAE92"));
                    tv2.setEnabled(true);
                    time = 60;
                    stopImageTimerTask();
                }

            }

            @Override
            public void onFailure(String url) {
                T("请检测网络");
                bufferCircleView.hide();
            }
        });


    }


    //2、描述监听
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s)) {
                tv12.setBackgroundResource(R.drawable.bg_register);
            } else {
                tv12.setBackgroundResource(R.drawable.bg_register_press);
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
}
