package com.huiche.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.huiche.R;
import com.huiche.activity.GuideActivity;
import com.huiche.activity.LoginActivity;
import com.huiche.activity.MainActivity;
import com.huiche.base.BaseActivity;
import com.huiche.base.MyApplication;
import com.huiche.bean.LoginInfo;
import com.huiche.constant.HttpConstant;
import com.huiche.utils.ToastUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/10.
 */
public class SplashActivity extends BaseActivity {
    private SharedPreferences.Editor editor;
    private String userName;
    private String userPass;
    private Context mContext;
    private LoginInfo loginInfo;

    @Override
    public void dealLogicBeforeFindView() {

        // Full Screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
// No Titlebar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_splash);
        mContext = this;
        SharedPreferences share = getSharedPreferences("user_data", MODE_PRIVATE);
        editor = share.edit();
        userName = share.getString("account", "");
        userPass = share.getString("passwd", "");
//        String token = share.getString("token", "");
        boolean isFirstLogin = share.getBoolean("isFirstLogin", true);

        //判断是否第一次登陆
        if (isFirstLogin) {
            //跳转到引导页
            Intent intent = new Intent();
            intent.setClass(SplashActivity.this, GuideActivity.class);
            startActivity(intent);
            finish();
        } else {
            //先判断本地保存的token,用户名，密码是否为空，若不为空则自动登录，为空跳转到登录界面
//            if(!userName.equals("") && !userPass.equals("") && !token.equals("")){
//                //自动登录
//                userLogin();
//            }
//            //重新进入登录界面
//            else{
//                Intent intent=new Intent();
//                intent.setClass(SplashActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
            //不用登录直接跳进主页
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void userLogin() {
        //用户自动登录
        //缓冲小菊花
        RequestParams params = new RequestParams();
        params.put("account", userName);
        params.put("passwd", userPass);
        params.put("identityId", "6");
        params.put("deviceType", 3);
        final AsyncHttpClient client = new AsyncHttpClient();
        //保存cookie，自动保存到了shareprefercece
        PersistentCookieStore myCookieStore = new PersistentCookieStore(SplashActivity.this);
        client.setCookieStore(myCookieStore);
        client.post(HttpConstant.USER_LOGIN, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(mContext, "登录失败，请检查网络");
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //测试获得已经保存的cookie
//				Toast.makeText(mContext, "cookie=" + CookieUtil.getCookieText(mContext), Toast.LENGTH_SHORT).show();

                String msg = response.optString("msg");

                if (response.optString("status").equals("0")) {

                    String rowString = response.optString("rows");
                    if (!TextUtils.isEmpty(rowString)) {
                        loginInfo = JSON.parseObject(rowString, LoginInfo.class);
                        if (loginInfo != null) {
                            editor.putString("token", loginInfo.token);
                            editor.putString("status", loginInfo.status);
                            editor.putString("freezeIntegral", loginInfo.freezeIntegral + "");
                            editor.putString("cardNo", loginInfo.cardNo + "");
                            editor.putString("city", loginInfo.city);
                            editor.putString("id", loginInfo.id + "");
                            editor.putString("account", userName);
                            editor.putString("passwd", userPass);
                            editor.putString("name", loginInfo.name);
                            editor.putString("phone", loginInfo.phone);
                            editor.commit();
                            MyApplication.token = loginInfo.token;
                            // ToastUtils.ToastShowShort(mContext, msg);
                            Intent intent = new Intent();
                            intent.setClass(mContext, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                } else {
                    if (!TextUtils.isEmpty(msg)) {
                        ToastUtils.ToastShowShort(mContext, msg);
                    }
                }

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {

    }
}
