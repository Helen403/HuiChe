package com.huiche.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.activity.mine.RegisterActivity;
import com.huiche.base.MyApplication;
import com.huiche.bean.LoginResultBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.Utils.CipherUtils;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.DButils;
import com.huiche.lib.lib.Utils.Param;
import com.huiche.lib.lib.base.BaseActivity;
import com.huiche.utils.SetSizeUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 登录页面
 *
 * @author Administrator
 */
public class LoginActivity extends BaseActivity implements
        TextWatcher {

    private EditText et_accountInput;// 账户输入
    private EditText et_passwordInput;// 密码输入
    private TextView tv_forgetPasswd, tv_22, forget;// 忘记密码
    //    private IWXAPI api;
    private TextView tv_login;// 登录
    private LinearLayout ll_wechat_login;// 微信登录

    // 把应用注册到微信
//        api = WXAPIFactory.createWXAPI(this, MyApplication.appid);
//        api.registerApp(MyApplication.appid);

    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }




    @Override
    public void findViews() {
        setTitle("账号登录");

        tv_login = (TextView) findViewById(R.id.tv_login);
        ll_wechat_login = (LinearLayout) findViewById(R.id.ll_wechat_login);
        et_accountInput = (EditText) findViewById(R.id.et_accountInput);
        et_passwordInput = (EditText) findViewById(R.id.et_passwordInput);
        tv_forgetPasswd = (TextView) findViewById(R.id.tv_forgetPasswd);
        //设置按钮比例
        SetSizeUtils.setSizeOnlyWidthOf(LoginActivity.this, tv_login, 6, 5);
        SetSizeUtils.setSizeOnlyWidthOf(LoginActivity.this, ll_wechat_login, 6, 5);

        tv_22 = (TextView) findViewById(R.id.tv_22);
        forget = (TextView) findViewById(R.id.forget);
    }

    @Override
    public void initData() {
        if (MyApplication.loginResultBean != null) {
            //自动填充数据
            String user = DButils.getString("user");
            String pass = DButils.getString("pass");
            if (!(TextUtils.isEmpty(user) && TextUtils.isEmpty(pass))) {
                et_accountInput.setText(user);
                et_passwordInput.setText(pass);
                tv_login.setEnabled(true);
                return;
            }
        } else {
            //自动登录
            String user = DButils.getString("user");
            String pass = DButils.getString("pass");
            if (!(TextUtils.isEmpty(user) && TextUtils.isEmpty(pass))) {
                et_accountInput.setText(user);
                et_passwordInput.setText(pass);
                login();
            }
        }
    }

    @Override
    public void setListeners() {
        et_accountInput.addTextChangedListener(this);
        et_passwordInput.addTextChangedListener(this);
        tv_22.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        setOnListeners(tv_22, forget, ll_wechat_login, tv_forgetPasswd, tv_login);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id) {
                    case R.id.tv_22:
                        goToActivityByClass(LoginActivity.this, RegisterActivity.class);
                        break;
                    case R.id.forget:
                        goToActivityByClass(LoginActivity.this, ForgetPasswordActivity.class);
                        break;
                    // 登录
                    case R.id.tv_login:
                        login();
                        break;
                    // 忘记密码
                    case R.id.tv_forgetPasswd:
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, ForgetPasswordActivity.class);
                        startActivity(intent);
                        break;
                    // 微信登录
                    case R.id.ll_wechat_login:
                        break;
                }
            }
        });


    }


    /**
     * 用户登录
     */

    private void login() {

        final String account = et_accountInput.getText().toString().trim();
        if (account.equals("")) {
            T("账号不能空");
            return;
        }
        final String passwd = et_passwordInput.getText().toString().trim();
        if (passwd.equals("")) {
            T("密码不能空");
            return;
        }


        bufferCircleView.show();
        Param param = new Param();
        param.put("user", account);
        param.put("pass", CipherUtils.md5L(passwd));
        StringBuffer sb = new StringBuffer();
        sb.append("{").append("\"user\":\"").append(account).append("\",\"pass\":\"").append(CipherUtils.md5L(passwd)).append("\"}");

        param.put("key", getMd5Password(sb.toString()));
        ControlUtils.postsEveryTime(Constants.Helen.LOGIN, param, LoginResultBean.class, new ControlUtils.OnControlUtils<LoginResultBean>() {
            @Override
            public void onSuccess(String url, LoginResultBean loginResultBean, ArrayList<LoginResultBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                bufferCircleView.hide();
                T(loginResultBean.msg);
                switch (loginResultBean.status) {
                    //登录成功
                    case 10017:
                        MyApplication.loginResultBean = loginResultBean;
                        MyApplication.phone = account;
                        setResult(Constants.startActivityForResult.LOGINRESULT);
                        //存储用户信息到数据库
                        setInfoToSQL(account, passwd);

                        finish();
                        break;
                }
            }

            @Override
            public void onFailure(String url) {
                bufferCircleView.hide();
                T("请检测网络");
            }
        });


    }

    //存储用户信息到数据库
    private void setInfoToSQL(String account, String passwd) {
        DButils.putString("user", account);
        DButils.putString("pass", passwd);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String account = et_accountInput.getText().toString().trim();
        String passwd = et_passwordInput.getText().toString().trim();
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(passwd)) {
            tv_login.setEnabled(true);
        } else {
            tv_login.setEnabled(false);
        }




    }

    @Override
    public void afterTextChanged(Editable s) {


    }

//    /**
//     * 微信登录
//     */
//    private void wechatLogin() {
//        wechatDialog = new BufferCircleDialog(mContext);
//        wechatDialog.show("", true, null);
//        if (!api.isWXAppInstalled()) {
//            Toast.makeText(mContext, "请先安装微信应用", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (!api.isWXAppSupportAPI()) {
//            Toast.makeText(mContext, "请先更新微信应用", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        final SendAuth.Req req = new SendAuth.Req();
//        req.scope = "snsapi_userinfo";
//        req.state = "wechat_sdk_demo_test";
//        MyApplication.isWXlogin = true;
//        api.sendReq(req);
//
//    }
//
//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (action.equals("wechat_login")) {
//                String code = intent.getStringExtra("code");
//                wxLogin(code);
//            }
//        }
//    };
//
//    /**
//     * 发送授权码，后台去获取用户资料,同时需要保存cookie
//     */
//    private void wxLogin(String code) {
//        RequestParams params = new RequestParams();
//        params.put("code", code);
//        wechatDialog.show("", true, null);
//        final AsyncHttpClient client = new AsyncHttpClient();
//        //保存cookie，自动保存到了shareprefercece
//        PersistentCookieStore clearCookie = new PersistentCookieStore(LoginActivity.this);
//        client.setCookieStore(clearCookie);
////        params.put("state","");
//        client.post(HttpConstant.WECHAT_LOGIN, params, new JsonHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                ToastUtils.ToastShowShort(mContext, "网络异常");
//                if (wechatDialog != null)
//                    wechatDialog.dialogcancel();
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
////                response.toString();
//                String status = response.optString("status");
//                if (status.equals("0")) {
//                    JSONObject obj = response.optJSONObject("rows");
//                    loginInfo = JSON.parseObject(obj.toString(), LoginInfo.class);
//                    if (loginInfo != null) {
//                        editor.putString("token", loginInfo.token);
//                        editor.putString("status", loginInfo.status);
//                        editor.putString("freezeIntegral", loginInfo.freezeIntegral + "");
//                        editor.putString("cardNo", loginInfo.cardNo + "");
//                        editor.putString("city", loginInfo.city);
//                        editor.putString("id", loginInfo.id + "");
//                        editor.putString("name", loginInfo.name);
//                        editor.putString("phone", loginInfo.phone);
//                        editor.commit();
//                        MyApplication.token = loginInfo.token;
//                        Intent intent = new Intent();
//                        intent.setClass(LoginActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        //更新界面
//                        Intent mIntent = new Intent(ACTION_NAME);
//                        sendBroadcast(mIntent);
//                        finish();
//                    }
//                }
//            }
//        });
//    }


}
