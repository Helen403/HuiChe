package com.huiche.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.huiche.lib.lib.Utils.CipherUtils;
import com.huiche.lib.lib.Utils.DateUtils;
import com.huiche.utils.SystemBarUtil;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


@SuppressLint("NewApi")
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {
    /**
     * whether to allow Full Screen
     */
    public boolean isAllowFullScreen;
    /**
     * Is there a menu display。
     */
    public boolean hasMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);

        dealLogicBeforeFindView();
        if (isAllowFullScreen) {
            setFullScreen(true);
        } else {
            setFullScreen(false);
        }
        // 软件盘弹出问题
        // 处理 edittext与 软键盘 重叠。
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // 沉浸式状态栏
        SystemBarUtil.initSystemBarElse(this);
        findViews();
        initData();
        setListeners();
    }

    /**
     * Examples logical layout before treatment Execution order:----1----
     */
    public abstract void dealLogicBeforeFindView();

    /**
     * find views Execution:----2----
     */
    public abstract void findViews();

    /**
     * Execution:----3----
     */
    public abstract void initData();

    /**
     * Execution:----4----
     */
    public abstract void setListeners();

    /**
     * Whether full-screen and display the title, true is set full screen and
     * Untitled, false is no title, before findViews () method call
     *
     * @param fullScreen
     */
    public void setFullScreen(boolean fullScreen) {
        if (fullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }

    private InputMethodManager manager;

    /**
     * hideKeyboard
     */
    public void hideKeyboard() {
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getWindow().getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getWindow().getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 添加点击事件
     */
    protected void setOnListeners(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    onClick click;

    public void setOnClick(onClick click) {
        this.click = click;
    }

    public interface onClick {
        void onClick(View v, int id);
    }

    @Override
    public void onClick(View v) {
        click.onClick(v, v.getId());
    }


    /*********************************************************************/
    public void T(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void T(float msg) {
        Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(double msg) {
        Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(int msg) {
        Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(boolean msg) {
        Toast.makeText(this, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void L(String msg) {
        Log.d("Helen", msg);
    }

    public void L(float msg) {
        Log.d("Helen", msg + "");
    }

    public void L(double msg) {
        Log.d("Helen", msg + "");
    }

    public void L(int msg) {
        Log.d("Helen", msg + "");
    }

    public void L(boolean msg) {
        Log.d("Helen", msg + "");
    }


    /*************************************************************************/
    /***********************************************************************/
    /**
     * 跳转到另一个Activity，不携带数据，不设置flag
     */
    public void goToActivityByClass(Context context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
    }

    /**
     * 跳转到另一个Activity，携带数据
     */
    public void goToActivityByClass(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    /**
     * 延迟去往新的Activity
     */
    public void delayToActivity(final Context context, final Class<?> cls, long delay) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                context.startActivity(new Intent(context, cls));
            }
        }, delay);
    }

    /*********************************************************************************************/
    //生成密匙
    public String getMd5Password(String str) {

        //第几天
        int i = DateUtils.getDayOfWeek();

        String week = "";
        //星期一
        if (i == 1) {
            week = "Mon";
        } else if (i == 2) {
            week = "Tue";
        } else if (i == 3) {
            week = "Wed";
        } else if (i == 4) {
            week = "Thu";
        } else if (i == 5) {
            week = "Fri";
        } else if (i == 6) {
            week = "Sat";
        } else if (i == 7) {
            week = "Sun";
        }
        //获取当前年
        int month = Integer.parseInt(DateUtils.formatDataMonth(System.currentTimeMillis()));
        String mon = "";
        switch (month) {
            case 1:
                mon = "Jan";
                break;
            case 2:
                mon = "Feb";
                break;
            case 3:
                mon = "Mar";
                break;
            case 4:
                mon = "Apr";
                break;
            case 5:
                mon = "May";
                break;
            case 6:
                mon = "Jun";
                break;
            case 7:
                mon = "Jul";
                break;
            case 8:
                mon = "Aug";
                break;
            case 9:
                mon = "Sep";
                break;
            case 10:
                mon = "Oct";
                break;
            case 11:
                mon = "Nov";
                break;
            case 12:
                mon = "Dec";
                break;
        }

        String time = getTime();
        if (time.length() == 1) {
            time = "0" + time;
        }

        //先弄D和H的和
        String password = CipherUtils.md5L(CipherUtils.md5L(week + time) + str + CipherUtils.md5L(DateUtils.formatDataYear(System.currentTimeMillis()) + mon));
        return password;
    }

    /**
     * getTime 获取系统时间
     */
    public static String getTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        return cal.get(Calendar.HOUR_OF_DAY) + "";
    }
}
