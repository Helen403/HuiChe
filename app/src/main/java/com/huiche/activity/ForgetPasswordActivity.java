package com.huiche.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.huiche.R;
import com.huiche.lib.lib.base.BaseActivity;

/**
 * 忘记密码页面
 *
 * @author Administrator
 */
public class ForgetPasswordActivity extends BaseActivity implements TextWatcher {

    private EditText et_phoneInput;
    private EditText et_securityCodeInput;
    private EditText et_newPasswdInput;
    private EditText et_confirmPasswdInput;
    private Button btn_sendSecurity;
    private Button btn_changePasswd;


    @Override
    public int getContentView() {
        return R.layout.activity_forgetpasswd;
    }

    @Override
    public void findViews() {
        setTitle("忘记密码");
        et_phoneInput = (EditText) findViewById(R.id.et_phoneInput);
        et_securityCodeInput = (EditText) findViewById(R.id.et_securityCodeInput);
        et_newPasswdInput = (EditText) findViewById(R.id.et_newPasswdInput);
        et_confirmPasswdInput = (EditText) findViewById(R.id.et_confirmPasswdInput);
        btn_sendSecurity = (Button) findViewById(R.id.btn_sendSecurity);
        btn_changePasswd = (Button) findViewById(R.id.btn_changePasswd);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {
        btn_sendSecurity.setOnClickListener(this);
        btn_changePasswd.setOnClickListener(this);
        et_phoneInput.addTextChangedListener(this);
        et_securityCodeInput.addTextChangedListener(this);
        et_newPasswdInput.addTextChangedListener(this);
        et_confirmPasswdInput.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //获取验证码
            case R.id.btn_sendSecurity:
                break;
            //修改密码
            case R.id.btn_changePasswd:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String phoneString = et_phoneInput.getText().toString().trim();
        String securiString = et_securityCodeInput.getText().toString().trim();
        String newPasswdString = et_newPasswdInput.getText().toString().trim();
        String confirmPasswdString = et_confirmPasswdInput.getText().toString().trim();
        if (!TextUtils.isEmpty(phoneString) && !TextUtils.isEmpty(securiString)
                && !TextUtils.isEmpty(newPasswdString) && !TextUtils.isEmpty(confirmPasswdString)) {
            btn_changePasswd.setEnabled(true);
        } else {
            btn_changePasswd.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

}
