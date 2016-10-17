package com.huiche.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.huiche.R;


/**
 * Created by Administrator on 2016/7/22.
 */
public class SystemDialog {
    public Dialog payDialog;
    public TextView dialog_content;
    public TextView tv_cancel;
    public TextView tv_confirm;

    public SystemDialog(Context context, String content, String textLeft, String textRight) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_pay, null);
        dialog_content = (TextView) view.findViewById(R.id.tv_dialogNormal_content);

        tv_cancel = (TextView) view.findViewById(R.id.tv_cancelNormal);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirmNormal);
        payDialog = new Dialog(context, R.style.customDialog);
        payDialog.setContentView(view);
        dialog_content.setText(content);
        tv_cancel.setText(textLeft);
        tv_confirm.setText(textRight);
        Window diawindow = payDialog.getWindow();
        WindowManager m = payDialog.getWindow().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = payDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.2); // 高度设置为屏幕的0.2
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的1
        diawindow.setAttributes(p);
    }

    public void showSystemDialog() {
        payDialog.show();
    }

    public void dimissSystemDialog() {
        payDialog.dismiss();
    }


}
