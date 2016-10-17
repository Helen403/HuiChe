package com.huiche.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiche.R;


/**
 * Created by Administrator on 2016/7/12.
 */
public class RebackMoneyDialog {
    public Dialog dialog;
    public TextView tv_confirm;
    public ImageView img_close;
    public EditText ed_content;

    public RebackMoneyDialog(Context mContext) {
        super();
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_reback, null);
        dialog = new Dialog(mContext, R.style.customDialog);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        img_close = (ImageView) view.findViewById(R.id.img_close);
        ed_content = (EditText) view.findViewById(R.id.ed_content);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        Window dwindow = dialog.getWindow();
        dwindow.setGravity(Gravity.CENTER);
        WindowManager windowManager = dwindow.getWindowManager();
        Display d = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.4
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        dwindow.setAttributes(p);
    }

    public void showDialog() {
        dialog.show();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }

}
