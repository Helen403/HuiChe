package com.huiche.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.huiche.R;
import com.huiche.activity.MainActivity;
import com.huiche.base.MyApplication;
import com.huiche.bean.LoginInfo;
import com.huiche.constant.HttpConstant;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.ToastUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.Header;
import org.json.JSONObject;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    //    private String access_token;
//    private String secret = "";//应用密钥AppSecret，在微信开放平台提交应用审核通过后获得。
    private String expires_in;
    private String refresh_token;
    //    private String openid;
    private String scope;
    private String unionid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String privilege;
    private String openids;
    private SharedPreferences sp;
    private LoginInfo loginInfo;
    private String ACTION_NAME="UPDATE_MINE_MESSAGE";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题并且全屏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wxentry);
        sp = getSharedPreferences("user_data", MODE_PRIVATE);
        api = WXAPIFactory.createWXAPI(this, MyApplication.appid);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
//        Log.i("======", req.toString());

    }
    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
//        Log.i("======", resp.errCode + "");
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if(MyApplication.isWXlogin){
                    String code = ((SendAuth.Resp) resp).code;
//                    Log.i("code", code);
                    //获得access_token
//                String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
//                        "appid=" + MyApplication.appid + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
//                getAccessToken(url);

//                setCode(code);
                    Intent intent = new Intent();
                    intent.setAction("wechat_login");
                    intent.putExtra("code", code);
                    sendBroadcast(intent);
                }
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                finish();
                // result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                finish();
                // result = R.string.errcode_deny;
                break;
            default:
                // result = R.string.errcode_unknown;
                break;
        }


    }

    /**
     * 获得access_token
     */
    private void getAccessToken(String url) {
        AsyncHttp.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                String str = response.toString();
//                Log.i("======", str);
                String access_token = response.optString("access_token");
                String expires_in = response.optString("expires_in");
                String refresh_token = response.optString("refresh_token");
                String openid = response.optString("openid");
                String scope = response.optString("scope");
                String unionid = response.optString("unionid");
                //提交服务器
//                getUserMesg(access_token, openid);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getApplicationContext(), "获取token失败，请检查网络", Toast.LENGTH_SHORT).show();
            }


        });


    }

    /**
     * 发送授权码，后台去获取用户资料
     */
    private void setCode(String code) {
        RequestParams params = new RequestParams();
        params.put("code", code);
//        params.put("state","");
        AsyncHttp.posts(HttpConstant.WECHAT_LOGIN, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                ToastUtils.ToastShowShort(WXEntryActivity.this, "网络异常");

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                response.toString();
                String status = response.optString("status");
                SharedPreferences.Editor editor = sp.edit();
                if(status.equals("0")){
                   JSONObject obj = response.optJSONObject("rows");
                    loginInfo = JSON.parseObject(obj.toString(), LoginInfo.class);
                    if (loginInfo != null) {
                        editor.putString("token", loginInfo.token);
                        editor.putString("status", loginInfo.status);
                        editor.putString("freezeIntegral", loginInfo.freezeIntegral + "");
                        editor.putString("cardNo", loginInfo.cardNo + "");
                        editor.putString("city", loginInfo.city);
                        editor.putString("id", loginInfo.id + "");
                        editor.putString("name", loginInfo.name);
                        editor.putString("phone", loginInfo.phone);
                        editor.commit();
                        MyApplication.token = loginInfo.token;
                        Intent intent = new Intent();
                        intent.setClass(WXEntryActivity.this, MainActivity.class);
                        startActivity(intent);
                        Intent mIntent = new Intent(ACTION_NAME);
                        sendBroadcast(mIntent);
                        finish();
                    }
                }

            }
        });
    }

    /**
     * 获取微信的个人信息
     *
     * @param access_token
     * @param openid
     */
    private void getUserMesg(String access_token, String openid) {
        String path = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
        AsyncHttp.get(path, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getApplicationContext(), "获取用户信息失败，请检查网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                Log.i("微信用户信息", response.toString());
                openids = response.optString("openid");
                nickname = response.optString("nickname");
                sex = response.optInt("sex");
                language = response.optString("language");
                city = response.optString("city");
                province = response.optString("province");
                country = response.optString("country");
                headimgurl = response.optString("headimgurl");
                privilege = response.optString("privilege");
                unionid = response.optString("unionid");
                //提交信息
                commitMeg();
            }
        });
    }

    private void commitMeg() {
        RequestParams params = new RequestParams();
        params.put("unionid", unionid);
        params.put("sex", sex);
        params.put("openid", openids);
        params.put("nickname", nickname);
        params.put("province", province);
        params.put("city", city);
        params.put("country", country);
        params.put("headimgurl", headimgurl);
        AsyncHttp.posts(HttpConstant.WECHAT_LOGIN, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                finish();
                Toast.makeText(getApplicationContext(), "登录失败，请检查网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String ss = response.toString();
//                Log.i("ss",response.toString());
                finish();
            }
        });

    }

}