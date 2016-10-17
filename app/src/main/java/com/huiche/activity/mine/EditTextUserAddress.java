package com.huiche.activity.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.BaseActivity;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.utils.TitleUtils;
import com.huiche.utils.ToastUtils;
import com.huiche.view.BufferCircleDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/7/28.
 */
public class EditTextUserAddress extends BaseActivity implements View.OnClickListener {
    private EditText edit_name, edit_phone, edit_address;
    TextView tv_address;
    private LinearLayout ll_check, ll_chose;
    private CheckBox check_mr;
    private TextView tv_right_title;
    private String name, phone, province, city, address, area;
    //是否为默认地址
    private String isDefault;
    private boolean isMoren = false;
    private SharedPreferences share;
    private String addressId;

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_edit_user_address);
        TitleUtils.setInfoText(EditTextUserAddress.this, "编辑地址", "保存");
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        tv_address = (TextView) findViewById(R.id.edit_area);
        edit_address = (EditText) findViewById(R.id.edit_address);
        ll_check = (LinearLayout) findViewById(R.id.ll_check);
        ll_chose = (LinearLayout) findViewById(R.id.ll_choose);
        check_mr = (CheckBox) findViewById(R.id.check_mr);
        tv_right_title = (TextView) findViewById(R.id.tv_right_title);
    }

    @Override
    public void initData() {
        share = getSharedPreferences("user_data", MODE_PRIVATE);
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        name = intent.getStringExtra("name");
        isDefault = intent.getStringExtra("isDefault");
        province = intent.getStringExtra("province");
        city = intent.getStringExtra("city");
        area = intent.getStringExtra("area");
        address = intent.getStringExtra("address");
        addressId = intent.getStringExtra("id");
        //填充数据
        edit_name.setText(name);
        edit_phone.setText(phone);
        edit_address.setText(address);

        tv_address.setText(province + city + area);
        if (isDefault.equals("true")) {
            check_mr.setChecked(true);
        } else {
            check_mr.setChecked(false);
        }
    }

    @Override
    public void setListeners() {
        ll_chose.setOnClickListener(this);
        ll_check.setOnClickListener(this);
        tv_right_title.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            //选择地址
            case R.id.ll_choose:
                intent.setClass(EditTextUserAddress.this, ChooseCityList.class);
                startActivityForResult(intent, 1);
                break;
            //设置默认地址
            case R.id.ll_check:
                if (check_mr.isChecked()) {
                    check_mr.setChecked(false);
                    isMoren = false;

                } else {
                    check_mr.setChecked(true);
                    isMoren = true;
                }
                break;
            //保存修改后的地址信息
            case R.id.tv_right_title:
                name = edit_name.getText().toString();
                phone = edit_phone.getText().toString();
                address = edit_address.getText().toString();
                if (name.equals("")) {
                    ToastUtils.ToastShowShort(this, "请输入姓名");
                    return;
                }
                if (phone.equals("")) {
                    ToastUtils.ToastShowShort(this, "请输入联系方式");
                    return;
                }
                if (area.equals("")) {
                    ToastUtils.ToastShowShort(this, "请输入详细地址");
                    return;
                }
                saveEditAddress();
                break;
        }
    }

    private void saveEditAddress() {
        RequestParams params = new RequestParams();
        params.put("token", share.getString("token", ""));
        params.put("id", addressId);
        params.put("province", province);
        params.put("city", city);
        params.put("area", area);
        params.put("address", address);
        params.put("tel", phone);
        params.put("isDefault", isMoren);
        params.put("name", name);
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(EditTextUserAddress.this);
        bufferCircleDialog.show("正在加载", false, null);
        AsyncHttp.posts(HttpConstantChc.EDIT_ADDRESS, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

                super.onFailure(statusCode, headers, throwable, errorResponse);
                bufferCircleDialog.dialogcancel();
                ToastUtils.ToastShowShort(EditTextUserAddress.this, "请检查网络");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                super.onSuccess(statusCode, headers, response);
                try {
                    bufferCircleDialog.dialogcancel();
                    String status = response.getString("status");
                    boolean success = response.getBoolean("success");
                    String msg = response.getString("msg");
                    if (status.equals("0")) {
                        if (success) {
                            ToastUtils.ToastShowShort(EditTextUserAddress.this, msg);
                            Intent intent = new Intent();
                            setResult(1132, intent);
                            finish();
                        } else {
                            ToastUtils.ToastShowShort(EditTextUserAddress.this, msg);
                        }
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }
        });
    }
    //选择地址回调

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            city = data.getStringExtra("cityName");
            province = data.getStringExtra("provinceName");
            area = data.getStringExtra("area");
            //过滤信息
            area = area.replaceAll("(县级市|地级市|、|)", "");
            city = city.replaceAll("(县级市|地级市|、|)", "");
            tv_address.setText(province + city + area);
        }
    }
}
