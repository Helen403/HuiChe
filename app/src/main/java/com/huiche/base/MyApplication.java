package com.huiche.base;

import android.app.Application;
import android.content.SharedPreferences;

import com.baidu.mapapi.SDKInitializer;
import com.huiche.Exception.CrashHandler;
import com.huiche.bean.LoginResultBean;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MyApplication extends Application {
    private static BaseActivity mconterxt;

    /**
     * the list of the selected checkBox which location to the printActivity.
     */
    private List<Integer> checkBoxList = new ArrayList<Integer>();
    private static MyApplication appContext;
    // 应用在微信上申请的app_id
    public static String appid = "wx877b51cc596d6537";
    public static String token = "";

    //2代表是买单页面进入的微信支付
    public static int payTyPe;
    //    public static boolean login_fresh;
//    public static boolean supportImmersive = false;//是否支持沉浸式（系统是否4.4以下不支持）
//微信分享和登录都会回调该方法，为了区分
    public static boolean isWXlogin = false;



    //用户的个人信息
    public static LoginResultBean loginResultBean;
    //用户刚注册成功的电话号码
    public static String phone;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);
        ImageLoader.getInstance().init(configuration);
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(appContext);
        SharedPreferences sp = getSharedPreferences("user_data", MODE_PRIVATE);
        token = sp.getString("token", "");


        //捕获全局异常
        CrashHandler.getInstance().init();

    }

    public static MyApplication getInstance() {
        return appContext;
    }

    /**
     * @param level the index of the checkbox which location to PrintActivity.
     *              add the index to the checkBoxList.
     */
    @Override
    public void onTrimMemory(int level) {

        super.onTrimMemory(level);
    }

    /**
     * @param index the index of the checkbox which location to PrintActivity. add
     *              the index to the checkBoxList.
     */

    public List<Integer> putCheckBoxIndex(int index) {
        if (!checkBoxList.contains(index)) {
            checkBoxList.add(index);
        }
        return checkBoxList;
    }

    /**
     * @param index the index of the checkbox which location to PrintActivity. add
     *              the index to the checkBoxList.
     * @return the checkBoxList
     */
    public List<Integer> deleteCheckBoxIndes(int index) {
        if (checkBoxList.contains(index)) {
            for (int i = 0; i < checkBoxList.size(); i++) {
                if (checkBoxList.get(i) == index) {
                    checkBoxList.remove(i);
                }
            }
        }
        return checkBoxList;
    }

    /**
     * when the PrintActivity is destroy ,clear the checkBoxList。
     */
    public void clearCheckBoxList() {
        checkBoxList.clear();
    }

    /**
     * @return the checkBoxList.
     */
    public List<Integer> returnCheckBox() {
        Collections.sort(checkBoxList);
        return checkBoxList;
    }
}
