package com.huiche.activity.mine;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiche.R;
import com.huiche.base.BaseActivity;
import com.huiche.base.MyApplication;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.utils.ImagLoadUtils;
import com.huiche.utils.SaveBitMapToSD;
import com.huiche.utils.TitleUtils;
import com.huiche.utils.ToastUtils;
import com.huiche.view.BufferCircleDialog;
import com.huiche.view.CircleImageView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PersionalMessageActivity extends BaseActivity implements OnClickListener {
    private CircleImageView head_img;
    private LinearLayout ll_select_photo;
    private RelativeLayout ll_phone;
    //	private Dialog_Phone mDialog;
    private TextView tv_right_title, tv_date, tv_myPhone, tv_cardNo, tv_address;
    private EditText tv_name, tv_userName, edit_email;
    private SharedPreferences share;
    private SharedPreferences.Editor editor;
    private String photoPath = "";
    //昵称，姓名，卡号，手机，邮箱
    private String name, userName, cardNo, phoneNumber, email, birthday, province, city;
    private File file;
    private RelativeLayout rl_date, ll_address;
    //是否有修改个人信息
//    private boolean isModify = false;
    private static final int FLAG_MODIFY_FINISH = 7;
    private static final int FLAG_CHOOSE_IMG = 0x11;
    private static final int FLAG_CHOOSE_CAMERA = 0x17;
    private final static String TAG = "TimeDate";
    private RadioButton radio_man, radio_women;
    private String myyear, mymonth, myday;
    private String sex = "1";
    private RadioGroup group;
    //是否修改了头像
    private boolean isChangePhoto = false;
    //获取日期格式器对象
    private SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
    //获取一个日历对象
    Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
    private TextView tv_outlogin;
    /********************************************/
    View dialogVV;


    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        /********************************************************/


        //总布局
        RelativeLayout content = new RelativeLayout(this);
        content.setClipToPadding(true);
        content.setFitsSystemWindows(true);
        LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        content.setLayoutParams(rl);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_persional_message, content, false);
        dialogVV = inflater.inflate(R.layout.dialog_photo_helen, content, false);
        dialogVV.setVisibility(View.GONE);
        dialogVV.findViewById(R.id.tv_photo).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogVV.setVisibility(View.GONE);
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, FLAG_CHOOSE_IMG);
            }
        });
        dialogVV.findViewById(R.id.tv_camera).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogVV.setVisibility(View.GONE);
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 指定调用相机拍照后的照片存储的路径
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                        Environment.getExternalStorageDirectory(), "test.jpg")));
                startActivityForResult(intent2, FLAG_CHOOSE_CAMERA);

            }
        });

        dialogVV.findViewById(R.id.tv_cancel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogVV.setVisibility(View.GONE);
            }
        });


        dialogVV.setLayoutParams(rl);
        content.addView(view);
        content.addView(dialogVV);
        setContentView(content);


        /*********************************************************/
//		setContentView(R.layout.activity_persional_message);
        share = getSharedPreferences("user_data", MODE_PRIVATE);
        editor = share.edit();
        TitleUtils.setInfoText(PersionalMessageActivity.this, "个人信息", "完成");
//        mDialog = new Dialog_Phone(this);
        head_img = (CircleImageView) findViewById(R.id.head_img);
        ll_select_photo = (LinearLayout) findViewById(R.id.ll_select_photo);
        tv_name = (EditText) findViewById(R.id.tv_name);
        tv_cardNo = (TextView) findViewById(R.id.tv_cardNo);
        tv_myPhone = (TextView) findViewById(R.id.tv_myPhone);
        tv_userName = (EditText) findViewById(R.id.tv_userName);
        tv_right_title = (TextView) findViewById(R.id.tv_right_title);
        rl_date = (RelativeLayout) findViewById(R.id.rl_date);
        tv_date = (TextView) findViewById(R.id.tv_date);
        ll_phone = (RelativeLayout) findViewById(R.id.ll_phone);
        ll_address = (RelativeLayout) findViewById(R.id.ll_address);
        tv_address = (TextView) findViewById(R.id.tv_address);
        edit_email = (EditText) findViewById(R.id.edit_email);
        radio_man = (RadioButton) findViewById(R.id.radio_man);
        radio_women = (RadioButton) findViewById(R.id.radio_women);
        group = (RadioGroup) findViewById(R.id.radioGroup1);
        tv_outlogin = (TextView) findViewById(R.id.tv_outlogin);

    }

    @Override
    public void initData() {
        name = share.getString("name", "");
        cardNo = share.getString("cardNo", "");
        phoneNumber = share.getString("phone", "");

        //用户头像
        photoPath = share.getString("wxHeadimage", "");
        ImagLoadUtils.setImage(photoPath, head_img, this);
        //邮箱
        email = share.getString("email", "");
        edit_email.setText(email);
        tv_name.setText(name);
        tv_cardNo.setText(cardNo);
        if (phoneNumber.length() >= 3) {
            tv_myPhone.setText(phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, phoneNumber.length()));
        } else {
            tv_myPhone.setText(phoneNumber);
        }
        sex = share.getString("sex", "0");
        //sex 0是女  1是男
        if (sex.equals("0")) {
            radio_women.setChecked(true);
            radio_man.setChecked(false);
        } else if (sex.equals("1")) {
            radio_man.setChecked(true);
            radio_women.setChecked(false);

        }
        myyear = share.getString("year", "");
        mymonth = share.getString("month", "");
        myday = share.getString("day", "");
        String date = share.getString("year", "") + "-" + share.getString("month", "") + "-" + share.getString("day", "");
        //生日
        birthday = share.getString("birthday", "");
        tv_date.setText(date);
        //地址
        provinceName = share.getString("province", "");
        cityName = share.getString("city", "");
        area_address = share.getString("area", "");
        if (cityName.equals("")) {
            tv_address.setText("");
        } else {
            tv_address.setText(area_address);
        }


    }

    @Override
    public void setListeners() {
        ll_select_photo.setOnClickListener(this);
        tv_right_title.setOnClickListener(this);
        rl_date.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        ll_phone.setOnClickListener(this);
        tv_outlogin.setOnClickListener(this);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radio_man.isChecked()) {
                    sex = "1";
                } else if (radio_women.isChecked()) {
                    sex = "0";
                }


            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {

            case R.id.ll_select_photo:
                dialogVV.setVisibility(View.VISIBLE);
//                mDialog.show();
//                mDialog.tv_photo.setOnClickListener(this);
//                mDialog.tv_camera.setOnClickListener(this);
//                mDialog.tv_cancel.setOnClickListener(this);
                break;
            //修改绑定手机
            case R.id.ll_phone:
                intent.setClass(PersionalMessageActivity.this, ChangePhoneActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, 1688);
                break;

            //选择手机相册
            case R.id.tv_photo:
//                mDialog.dismiss();
//                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
//                intent1.setDataAndType(
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                startActivityForResult(intent1, FLAG_CHOOSE_IMG);
                break;
            //调用系统相机
            case R.id.tv_camera:
//                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                // 指定调用相机拍照后的照片存储的路径
//                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
//                        Environment.getExternalStorageDirectory(), "test.jpg")));
//                startActivityForResult(intent2, FLAG_CHOOSE_CAMERA);
//                mDialog.dismiss();
                break;

            case R.id.tv_cancel:
//                mDialog.dismiss();
                break;

            //保存修改信息
            case R.id.tv_right_title:
                //name,userName,cardNo,phoneNumber,email,birthday,province,city;
                name = tv_name.getText().toString().trim();
                cardNo = tv_cardNo.getText().toString().trim();
                phoneNumber = tv_myPhone.getText().toString().trim();
                email = edit_email.getText().toString().trim();
                birthday = tv_date.getText().toString().trim();
                area_address = tv_address.getText().toString().trim();
                if (name.equals("")) {
                    ToastUtils.ToastShowShort(this, "请输入您的昵称");
                    return;
                } else if (cardNo.equals("")) {
                    ToastUtils.ToastShowShort(this, "请输入您的卡号");
                    return;
                } else if (phoneNumber.equals("")) {
                    ToastUtils.ToastShowShort(this, "请输入您的手机号码");
                    return;
                } else if (email.equals("")) {
                    ToastUtils.ToastShowShort(this, "请输入您的邮箱");
                    return;
                } else if (birthday.equals("")) {
                    ToastUtils.ToastShowShort(this, "请选择您的生日");
                    return;
                } else if (area_address.equals("")) {
                    ToastUtils.ToastShowShort(this, "请选择您的地址");
                    return;
                }
                //判断有无修改头像，若有则先上传图片,isChangePhoto  true为修改了
                if (isChangePhoto) {
                    if (file != null) {
                        saveImageFile(file);
                    }
                } else {
                    saveUserMessage();
                }
                break;
            case R.id.rl_date:
                DatePickerDialog dateDlg = new DatePickerDialog(PersionalMessageActivity.this,
                        d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH));

                dateDlg.show();
                break;

            //选择城市
            case R.id.ll_address:
                intent.setClass(PersionalMessageActivity.this, ChooseCityList.class);
                startActivityForResult(intent, 1);
                break;
            //注销
            case R.id.tv_outlogin:
                exitLogin();

                break;
        }
    }

    //保存用户修改后的信息
    private void saveUserMessage() {
        RequestParams params = new RequestParams();
        params.put("name", name);
        params.put("email", email);
        params.put("sex", sex);
        params.put("wxHeadimage", photoPath);
        params.put("birthday", birthday);
        params.put("province", provinceName);
        params.put("city", cityName);
        params.put("area", area_address);
        params.put("id", share.getString("id", ""));
        params.put("token", share.getString("token", ""));
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(PersionalMessageActivity.this);
        bufferCircleDialog.show("正在加载", false, null);
        AsyncHttp.posts(HttpConstantChc.SAVE_USER_MESSAGE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                try {
                    bufferCircleDialog.dialogcancel();
                    String status = response.getString("status");
                    String msg = response.getString("msg");
                    boolean success = response.getBoolean("success");
                    if (status.equals("0")) {
                        if (success) {
                            ToastUtils.ToastShowShort(PersionalMessageActivity.this, msg);
                            //重新提交本地的用户信息
                            editor.putString("email", email);
                            editor.putString("wxHeadimage", photoPath);
                            editor.putString("sex", sex);
                            editor.putString("province", provinceName);
                            editor.putString("city", cityName);
                            editor.putString("year", myyear);
                            editor.putString("month", mymonth);
                            editor.putString("day", myday);
                            editor.putString("area", area_address);
                            editor.putString("name", name);
                            editor.putString("phone", phoneNumber);
                            editor.commit();
                            //回传信息
                            Intent intent = new Intent();
                            intent.putExtra("name", name);
                            intent.putExtra("phone", phoneNumber);
                            intent.putExtra("wxHeadimage", photoPath);
                            setResult(1011, intent);
                            finish();
                        }
                    } else {
                        ToastUtils.ToastShowShort(PersionalMessageActivity.this, msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(PersionalMessageActivity.this, "请检查网络");
                bufferCircleDialog.dialogcancel();
            }
        });


    }

    private String cityName, provinceName, area_address;

    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data) {

        super.onActivityResult(requestcode, resultcode, data);

        if (requestcode == 1 && resultcode == RESULT_OK) {
            cityName = data.getStringExtra("cityName");
            provinceName = data.getStringExtra("provinceName");
            area_address = data.getStringExtra("area");
            //过滤信息
            area_address = area_address.replaceAll("(县级市|地级市|、|)", "");
            cityName = cityName.replaceAll("(县级市|地级市|、|)", "");
            tv_address.setText(provinceName + cityName + area_address);
        }

        //调用系统相册回调
        if (requestcode == FLAG_CHOOSE_IMG && resultcode == RESULT_OK) {
            if (data != null) {
                imageCut(data.getData());
            }

        }
        //调用系统相机回调
        else if (requestcode == FLAG_CHOOSE_CAMERA && resultcode == RESULT_OK) {
            File temp = new File(Environment.getExternalStorageDirectory()
                    + "/test.jpg");

            imageCut(Uri.fromFile(temp));
        }

        //完成后回调
        else if (requestcode == FLAG_MODIFY_FINISH && resultcode == RESULT_OK) {
            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    head_img.setImageBitmap(photo);
                    //需要上传的File头像文件
                    file = SaveBitMapToSD.saveBitmap(photo, this);
                    //true为修改了头像图片
                    isChangePhoto = true;


                }
            }

        }

        //修改手机号码后回调
        else if (requestcode == 1688 && resultcode == 1689) {
            phoneNumber = data.getStringExtra("newPhone");
            tv_myPhone.setText(phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, phoneNumber.length()));
        }

    }


    private void saveImageFile(File file) {
        RequestParams params = new RequestParams();
        try {
            params.put("file", file);
            params.put("modelType", 6);
            String token = share.getString("token", "");
            params.put("token", token);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        AsyncHttp.posts(HttpConstantChc.POST_IMAGE, params, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

//                super.onFailure(statusCode, headers, throwable, errorResponse);
                toast("请检查网络");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

//                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("status");
                    String msg = response.getString("msg");
                    if (status.equals("0")) {
                        JSONObject obj = response.getJSONObject("rows");
                        String imagePrefix = obj.getString("imagePrefix");
                        String imageSrc = obj.getString("imageSrc");
                        //返回服务器头像图片路径
                        photoPath = imagePrefix + imageSrc;
                        //保存用户信息
                        saveUserMessage();
                    } else {
                        toast(msg);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //裁剪图片
    private void imageCut(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //开启裁剪功能
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, FLAG_MODIFY_FINISH);
    }

    //日期
    //当点击DatePickerDialog控件的设置按钮时，调用该方法
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            //修改日历控件的年，月，日  
            //这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致  
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            tv_date.setText(fmtDate.format(dateAndTime.getTime()));
            myyear = year + "";
            mymonth = monthOfYear + 1 + "";
            myday = dayOfMonth + "";
        }
    };


    //退出登录
    private void exitLogin() {
        RequestParams params = new RequestParams();
        params.put("token", share.getString("token", ""));
        params.put("id", share.getString("id", ""));
        AsyncHttp.posts(HttpConstantChc.EXIT_LOGIN, params,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {

//                        super.onFailure(statusCode, headers, throwable,
//                                errorResponse);
                        Toast.makeText(PersionalMessageActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {

//                        super.onSuccess(statusCode, headers, response);
                        try {
                            String status = response.getString("status");
                            String success = response.getString("msg");
                            if (status.equals("0")) {
                                Toast.makeText(PersionalMessageActivity.this, success, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                //intent.setClass(PersionalMessageActivity.this, LoginActivity.class);

                                //将本地记录的密码清空,以及登录token清空
                                editor.putString("passwd", "");
                                editor.putString("token", "");
                                MyApplication.token = "";
                                editor.commit();
//								startActivity(intent);
                                setResult(1008, intent);
                                finish();

                            } else {
                                Toast.makeText(PersionalMessageActivity.this, success, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();

                        }
                    }
                });

    }


}
