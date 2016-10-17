package com.huiche.lib.lib.base;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;


public abstract class BaseView extends RelativeLayout implements View.OnClickListener {
    //配置一下
    protected Context contextAppliction = BaseApplication.context;
    //基本View
    protected View view;

    public BaseView(Context context) {
        super(context);
        view = View.inflate(context, getContentView(), this);
        view.setDrawingCacheEnabled(true);
        findViews();
        initData();
        setListeners();
    }

    public abstract int getContentView();

    public abstract void findViews();
    /**
     * 初始化数据
     */
    public abstract void initData();


    public abstract void setListeners();



    /*********************************************************************/
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

    public void onClick(View v) {
        click.onClick(v, v.getId());
    }


    /*********************************************************************/
    public void T(String msg) {
        Toast.makeText(contextAppliction, msg, Toast.LENGTH_SHORT).show();
    }

    public void T(float msg) {
        Toast.makeText(contextAppliction, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(double msg) {
        Toast.makeText(contextAppliction, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(int msg) {
        Toast.makeText(contextAppliction, msg + "", Toast.LENGTH_SHORT).show();
    }

    public void T(boolean msg) {
        Toast.makeText(contextAppliction, msg + "", Toast.LENGTH_SHORT).show();
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

    /*********************************************************************/

    /**
     * 跳转到另一个Activity，不携带数据，不设置flag
     */
    public void goToActivityByClass(Context context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
    }

}
