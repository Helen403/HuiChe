package com.huiche.lib.lib.base;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.huiche.bean.LoginResultBean;
import com.huiche.lib.lib.Exception.CrashHandler;


/**
 * Created by SNOY on 2016/8/5.
 */
public final class BaseApplication extends Application {

    public static Context context;
    public static BaseActivity activity;

    /**************************************************************/

    //用户的个人信息
    public static LoginResultBean loginResultBean;
    //用户刚注册成功的电话号码
    public static String phone;

    /**************************************************************/

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        // 在使用SDK各组件之前初始化context信息，传入ApplicationContext
        // 百度地图全局context 的初始化
        SDKInitializer.initialize(this);
        //捕获全局异常
        CrashHandler.getInstance().init();
    }
}
