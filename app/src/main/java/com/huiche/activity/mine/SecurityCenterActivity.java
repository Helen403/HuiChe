package com.huiche.activity.mine;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.huiche.R;
import com.huiche.base.MyApplication;
import com.huiche.bean.SecurityChangePasswordBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.Utils.CipherUtils;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.Param;
import com.huiche.lib.lib.base.BaseActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SecurityCenterActivity extends BaseActivity implements
        OnClickListener {
    private EditText edit_old, edit_newf, edit_newt;
    private Button btn_confirm;
    private String pwd_old, pwd_newf, pwd_newt;
    private CheckBox delete_new1, delete_new2;


    @Override
    public int getContentView() {
        return R.layout.activity_security_center_helen;
    }

    @Override
    public void findViews() {
        setTitle("安全中心");
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        edit_old = (EditText) findViewById(R.id.edit_old);
        edit_newf = (EditText) findViewById(R.id.edit_newf);
        edit_newt = (EditText) findViewById(R.id.edit_newt);
        delete_new1 = (CheckBox) findViewById(R.id.delete_new1);
        delete_new2 = (CheckBox) findViewById(R.id.delete_new2);
        delete_new1.setOnClickListener(this);
        delete_new2.setOnClickListener(this);
        edit_newt.addTextChangedListener(new MyTextWatch());
        edit_newf.addTextChangedListener(new MyTextWatch());
    }

    @Override
    public void initData() {


    }

    @Override
    public void setListeners() {
        btn_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                /*************************************************************/

                pwd_old = edit_old.getText().toString().trim();
                if (TextUtils.isEmpty(pwd_old)) {
                    T("旧密码不能为空");
                    return;
                }
                pwd_newf = edit_newf.getText().toString().trim();
                if (TextUtils.isEmpty(pwd_newf)) {
                    T("新密码不能为空");
                    return;
                }
                pwd_newt = edit_newt.getText().toString().trim();
                if (TextUtils.isEmpty(pwd_newt)) {
                    T("确认密码不能为空");
                    return;
                }

                if (pwd_old.equals(pwd_newf)) {
                    T("旧密码不能和新密码一样");
                    return;
                }
                if (!pwd_newf.equals(pwd_newt)) {
                    T("新密码和确认密码不一致");
                    return;
                }
                if (MyApplication.loginResultBean.data.id == null) {
                    return;
                }
                /*************************************************************/

                //修改密码
                savePassWord();

                break;

            //密码显示与隐藏
            case R.id.delete_new1:

                break;
            case R.id.delete_new2:

                break;
        }
    }

    // 保存修改密码
    private void savePassWord() {
        bufferCircleView.show();
        Param param = new Param();
        param.put("us_id", MyApplication.loginResultBean.data.id);
        param.put("oldpass", CipherUtils.md5L(pwd_old));
        param.put("newpass", CipherUtils.md5L(pwd_newf));
        StringBuffer sb = new StringBuffer();
        sb.append("{").append("\"us_id\":\"").append(MyApplication.loginResultBean.data.id).append("\",\"oldpass\":\"").append(CipherUtils.md5L(pwd_old)).append("\",\"").append("newpass\":\"").append(CipherUtils.md5L(pwd_newf)).append("\"}");
        param.put("key", getMd5Password(sb.toString()));

        ControlUtils.postsEveryTime(Constants.Helen.CHANGEPASSWORD, param, SecurityChangePasswordBean.class, new ControlUtils.OnControlUtils<SecurityChangePasswordBean>() {
            @Override
            public void onSuccess(String url, SecurityChangePasswordBean securityChangePasswordBean, ArrayList<SecurityChangePasswordBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                T(securityChangePasswordBean.msg);
                bufferCircleView.hide();


            }

            @Override
            public void onFailure(String url) {
                T("请检测网络");
                bufferCircleView.hide();
            }
        });


    }


    class MyTextWatch implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            pwd_old = edit_old.getText().toString();
            pwd_newf = edit_newf.getText().toString();
            pwd_newt = edit_newt.getText().toString();
            if (TextUtils.isEmpty(pwd_old) || TextUtils.isEmpty(pwd_newf) || TextUtils.isEmpty(pwd_newt)) {
                btn_confirm.setBackgroundResource(R.drawable.buton_grey_shape);
            } else {
                btn_confirm.setBackgroundResource(R.drawable.buton_blue_shape);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    }


}
