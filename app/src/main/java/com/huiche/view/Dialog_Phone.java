package com.huiche.view;


import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.huiche.R;


public class Dialog_Phone {
    public Dialog dialog;
    public TextView tv_photo, tv_camera, tv_cancel;


    public Dialog_Phone(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_photo, null);
        dialog = new Dialog(context, R.style.customDialog);
        tv_photo = (TextView) view.findViewById(R.id.tv_photo);
        tv_camera = (TextView) view.findViewById(R.id.tv_camera);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);

        Window diawindow = dialog.getWindow();

        diawindow.setGravity(Gravity.BOTTOM);


        WindowManager m = dialog.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.2); // 高度设置为屏幕的0.2
        p.width = (int) (d.getWidth() * 1); // 宽度设置为屏幕的1
        diawindow.setAttributes(p);

//		// 窗口大小
//		lp.dimAmount = 0.3f;
//		lp.y=15;
//		dialog.getWindow().setAttributes(lp);


    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

}
