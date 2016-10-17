package com.huiche.activity.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiche.R;
import com.huiche.base.BaseActivity;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.utils.IsMobileNumber;
import com.huiche.utils.TitleUtils;
import com.huiche.utils.ToastUtils;
import com.huiche.view.BufferCircleDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

public class AddAddressActivity extends BaseActivity implements OnClickListener {
    private EditText edit_name, edit_phone, edit_address;
    TextView edit_area;
    private CheckBox check_mr;
    private String name, phone, area, address;
    private boolean isDefault = false;
    private SharedPreferences share;
    private String provinceName;
    private String cityName;
    private String area_address;
    //1为设置成默认，0为 未选中
//    private int checked = 0;

    @Override
    public void dealLogicBeforeFindView() {
    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_add_address);
        share = getSharedPreferences("user_data", MODE_PRIVATE);
        TitleUtils.setInfoText(this, "添加地址", "添加");
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_area = (TextView) findViewById(R.id.edit_area);
        edit_address = (EditText) findViewById(R.id.edit_address);
        LinearLayout ll_check = (LinearLayout) findViewById(R.id.ll_check);
        check_mr = (CheckBox) findViewById(R.id.check_mr);
        ll_check.setOnClickListener(this);
        check_mr.setOnClickListener(this);
        TextView tv_right_title = (TextView) findViewById(R.id.tv_right_title);
        tv_right_title.setOnClickListener(this);

    }

    @Override
    public void initData() {
    }

    @Override
    public void setListeners() {
        edit_area.setOnClickListener(this);

    }

    //返回数据所走方法
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ll_check:
                if (!isDefault) {
                    isDefault = true;
                    check_mr.setChecked(true);
                } else if (isDefault) {
                    isDefault = false;
                    check_mr.setChecked(false);
                }

                break;
            //选择省份城市 地区
            case R.id.edit_area:
                intent.setClass(AddAddressActivity.this, ChooseCityList.class);
                startActivityForResult(intent, 1);

                break;
            //保存新增地址
            case R.id.tv_right_title:
                name = edit_name.getText().toString().trim();
                phone = edit_phone.getText().toString().trim();
                area = edit_area.getText().toString().trim();
                address = edit_address.getText().toString().trim();
                if (name.equals("")) {
                    Toast("收货人姓名不能为空");
                    return;
                }
                if (phone.equals("")) {
                    Toast("收货人号码不能为空");
                    return;
                }
                if (area.equals("")) {
                    Toast("收货人地区不能为空");
                    return;
                }
                if (address.equals("")) {
                    Toast("收货人地址不能为空");
                    return;
                }
                //判断手机号码格式
                boolean isPhone = IsMobileNumber.isMobileNO(phone);
                if (isPhone) {
                    saveAddress();
                } else {
                    ToastUtils.ToastShowShort(this, "请输入正确手机号码");
                    return;
                }

                break;
        }

    }

    private void Toast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    private void saveAddress() {
        RequestParams params = new RequestParams();
        params.setContentEncoding(HTTP.UTF_8);
        params.put("token", share.getString("token", ""));
        params.put("province", provinceName);
        params.put("city", cityName);
        params.put("area", area_address);
        params.put("address", address);
        params.put("tel", phone);
        params.put("isDefault", isDefault);
        params.put("name", name);
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(AddAddressActivity.this);
        bufferCircleDialog.show("正在加载", false, null);
        AsyncHttp.post(HttpConstantChc.ADDNEWADDRESS, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
//				super.onFailure(statusCode, headers, throwable, errorResponse);
                bufferCircleDialog.dialogcancel();
                Toast("请检查网络");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
//				super.onSuccess(statusCode, headers, response);
                try {
                    bufferCircleDialog.dialogcancel();
                    String status = response.getString("status");
                    boolean success = response.getBoolean("success");
                    String msg = response.getString("msg");
                    if (status.equals("0")) {
                        if (success) {
                            Toast(msg);
                            Intent intent = new Intent();
                            setResult(1101, intent);
                            finish();
                        } else {
                            Toast(msg);
                        }
                    } else {
                        Toast(msg);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            cityName = data.getStringExtra("cityName");
            provinceName = data.getStringExtra("provinceName");
            area_address = data.getStringExtra("area");
            //过滤信息
            area_address = area_address.replaceAll("(县级市|地级市|、|)", "");
            cityName = cityName.replaceAll("(县级市|地级市|、|)", "");
            edit_area.setText(provinceName + cityName + area_address);
        }

    }


}
