package com.huiche.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.huiche.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 沉浸式状态栏工具类，注意：系统4.4以上有效
 *
 * @author Administrator
 */
public  class SystemBarUtil {

    private static SystemBarTintManager tintManager;
    private static SystemBarTintManager tintManagers;
    private static SystemBarTintManager tintManagerwhite;

    public static void initSystemBar(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            setTranslucentStatus(activity, true);
            // 透明状态栏
            activity.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            activity.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if (null == tintManager) {
            tintManager = new SystemBarTintManager(activity);
        }
        tintManager.setStatusBarTintEnabled(true);

        tintManager.setStatusBarTintResource(R.color.status_color);
    }

    /**
     * @param activity
     * @param color    参数为R.color.形式
     */
    public static void initSystemBar(Activity activity, int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

			setTranslucentStatus(activity, true);

        }

        SystemBarTintManager tintManager = new SystemBarTintManager(activity);

        tintManager.setStatusBarTintEnabled(true);

        // 使用颜色资源
        tintManager.setStatusBarTintResource(color);

    }

    public static void changeColor() {
        tintManager.setStatusBarTintResource(R.color.status_color);
    }

    public static void changeColorLuncy() {
        tintManager.setStatusBarTintResource(R.color.lucency);
    }

    /***
     * 设置状态栏为 蓝色 (大多数状态栏颜色)
     *
     * @param activity
     */
    public static void initSystemBarElse(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            setTranslucentStatus(activity, true);

        }

        tintManagers = new SystemBarTintManager(activity);


        tintManagers.setStatusBarTintEnabled(true);

        tintManagers.setStatusBarTintResource(R.color.status_color);

    }

    /***
     * 设置状态栏为 白色 (数量少)
     *
     * @param activity
     */
    public static void initSystemBarWhite(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            setTranslucentStatus(activity, true);

        }

        tintManagerwhite = new SystemBarTintManager(activity);


        tintManagerwhite.setStatusBarTintEnabled(true);

//        tintManagerwhite.setStatusBarTintResource();
        tintManagerwhite.setStatusBarTintColor(Color.parseColor("#f4f4f4"));
    }

    public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean darkmode) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @TargetApi(19)
    private static void setTranslucentStatus(Activity activity, boolean on) {

        Window win = activity.getWindow();

        WindowManager.LayoutParams winParams = win.getAttributes();

        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

        if (on) {

            winParams.flags |= bits;

        } else {

            winParams.flags &= ~bits;

        }

        win.setAttributes(winParams);

    }


}
