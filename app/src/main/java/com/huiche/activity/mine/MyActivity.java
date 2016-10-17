package com.huiche.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiche.R;
import com.huiche.adapter.MyPersionalAdapter;
import com.huiche.base.BaseActivity;
import com.huiche.customer_view.MyGridView;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class MyActivity extends BaseActivity implements OnClickListener {
    private MyGridView mygridview;
    private String[] strType;
    private int[] imgData;
    private Context context;
    private LinearLayout lucky_draw, sign_day, ll_recommend;
    private LinearLayout ll_integral_surplus;
    private SharedPreferences share;
    private TextView tv_income, tv_outcome, tv_integral, tv_card, tv_phone,
            tv_name;
    private ImageButton btn_message;

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_my);
        context = this;
        share = getSharedPreferences("user_data", MODE_PRIVATE);
//        editor = share.edit();
        mygridview = (MyGridView) findViewById(R.id.mygridview);
        initRes();
        lucky_draw = (LinearLayout) findViewById(R.id.lucky_draw);
        sign_day = (LinearLayout) findViewById(R.id.sign_day);
        ll_recommend = (LinearLayout) findViewById(R.id.ll_recommend);
        ll_integral_surplus = (LinearLayout) findViewById(R.id.ll_integral_surplus);
        RelativeLayout rl_line_order = (RelativeLayout) findViewById(R.id.rl_line_order);
        rl_line_order.setOnClickListener(this);
        LinearLayout ll_integral_income = (LinearLayout) findViewById(R.id.ll_integral_income);
        ll_integral_income.setOnClickListener(this);
        LinearLayout ll_integral_used = (LinearLayout) findViewById(R.id.ll_integral_used);
        ll_integral_used.setOnClickListener(this);
        tv_integral = (TextView) findViewById(R.id.tv_integral);
        tv_income = (TextView) findViewById(R.id.tv_income);
        tv_outcome = (TextView) findViewById(R.id.tv_outcome);
        LinearLayout ll_message = (LinearLayout) findViewById(R.id.ll_message);
        ll_message.setOnClickListener(this);
        // 线下订单控件
        LinearLayout ll_wait_pay = (LinearLayout) findViewById(R.id.ll_wait_pay);
        LinearLayout ll_un_pay = (LinearLayout) findViewById(R.id.ll_un_pay);
        LinearLayout ll_finish = (LinearLayout) findViewById(R.id.ll_finish);
        LinearLayout ll_wait_evaluate = (LinearLayout) findViewById(R.id.ll_wait_evaluate);
        ll_wait_pay.setOnClickListener(this);
        ll_un_pay.setOnClickListener(this);
        ll_finish.setOnClickListener(this);
        ll_wait_evaluate.setOnClickListener(this);
        tv_card = (TextView) findViewById(R.id.tv_card);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_name = (TextView) findViewById(R.id.tv_name);
        btn_message = (ImageButton) findViewById(R.id.btn_message);
    }

    private void initRes() {
        strType = new String[]{"收货地址", "线上订单", "我的卡券", "我的收藏", "我的评价",
                "安全设置", "意见反馈", "联系客服", ""};
        imgData = new int[]{R.drawable.yellow_book,
                R.drawable.the_online_order, R.drawable.my_card_volume,
                R.drawable.my_collection, R.drawable.my_evaluation,
                R. drawable.security_settings, R.drawable.feedback,
                R. drawable.contact_customerservice, R.drawable.sandian};
    }

    @Override
    public void initData() {
        MyPersionalAdapter adapter = new MyPersionalAdapter(context, strType, imgData);
        mygridview.setAdapter(adapter);
        // 测试时app登录
        //LoginText();
        GetUserIntegral();
        //获取会员信息
        GetUserMessage();
        tv_card.setText("卡号:"
                + share.getString("cardNo", ""));
        String phone = share.getString("phone", "");
        tv_phone.setText(phone.substring(0, 3)
                + "****"
                + phone.substring(7, phone.length()));
        tv_name.setText(share.getString("name", ""));

    }


    //获取会员信息
    private void GetUserMessage() {
        RequestParams params = new RequestParams();
        params.put("token", share.getString("token", ""));
        params.put("id", share.getString("id", ""));
        AsyncHttp.posts(HttpConstantChc.GET_USER_MESSAGE, params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {
                        try {
                            String msg = response.getString("msg");
                            String status = response.getString("status");
                            if (status.equals("0")) {

                            } else {
                                Toast.makeText(getApplicationContext(), msg,
                                        Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
//                        super.onSuccess(statusCode, headers, response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {

//                        super.onFailure(statusCode, headers, throwable,
//                                errorResponse);
                        Toast.makeText(getApplicationContext(), "请检查网络",
                                Toast.LENGTH_SHORT).show();
                    }

                });


    }

    private void GetUserIntegral() {

        RequestParams params = new RequestParams();

        params.put("token", share.getString("token", ""));
        AsyncHttp.posts(HttpConstantChc.USER_INTEGRAL, params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers,
                                          JSONObject response) {
                        try {
                            String msg = response.getString("msg");
                            String status = response.getString("status");
                            if (status.equals("0")) {
                                JSONObject obj = response.getJSONObject("rows");
                                tv_integral.setText(obj.optString("integral"));
                                tv_income.setText("+" + obj.optString("income"));
                                tv_outcome.setText("-"
                                        + obj.optString("expenditure"));
                            } else {
                                Toast.makeText(getApplicationContext(), msg,
                                        Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        super.onSuccess(statusCode, headers, response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          Throwable throwable, JSONObject errorResponse) {

//                        super.onFailure(statusCode, headers, throwable,
//                                errorResponse);
                        Toast.makeText(getApplicationContext(), "请检查网络",
                                Toast.LENGTH_SHORT).show();
                    }

                });

    }

    @Override
    public void setListeners() {
        lucky_draw.setOnClickListener(this);
        sign_day.setOnClickListener(this);
        ll_recommend.setOnClickListener(this);
        ll_integral_surplus.setOnClickListener(this);
        btn_message.setOnClickListener(this);

        mygridview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent();
                switch (position) {

                    case 0:
                        intent.setClass(MyActivity.this, MyGoodsAddress.class);
                        startActivity(intent);

                        break;
                    case 1:
                        intent.setClass(MyActivity.this, OnLineOrderActivity.class);
                        startActivity(intent);

                        break;

                    case 2:
                        intent.setClass(MyActivity.this, CardQuanActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent.setClass(MyActivity.this,
                                MyCollectionsActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent.setClass(MyActivity.this, EvaluateActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent.setClass(MyActivity.this,
                                SecurityCenterActivity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent.setClass(MyActivity.this, FeedBackActivity.class);
                        startActivity(intent);
                        break;
                    // 联系客服
                    case 7:
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + "4000088769"));
                        // 开启系统拨号器
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            // 积分抽奖
            case R.id.lucky_draw:

                break;
            // 日常签到
            case R.id.sign_day:
                intent.setClass(MyActivity.this, CardDetailActivity.class);
                startActivity(intent);
                break;
            // 推荐好友
            case R.id.ll_recommend:
                break;
            case R.id.ll_integral_surplus:
                intent.setClass(MyActivity.this, IntegralActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_line_order:
                intent.setClass(MyActivity.this, LineOrderActivity.class);
                startActivity(intent);
                break;

            // 积分收入
            case R.id.ll_integral_income:
                intent.setClass(MyActivity.this, IntegralInComeActivity.class);
                startActivity(intent);
                break;
            // 积分支出
            case R.id.ll_integral_used:
                intent.setClass(MyActivity.this, IntegralOutComeActivity.class);
                startActivity(intent);
                break;

            // 线下订单--代付款
            case R.id.ll_wait_pay:
                intent.setClass(MyActivity.this, LineWaitPayActivity.class);
                startActivity(intent);
                break;

            // 线下订单--未消费
            case R.id.ll_un_pay:
                intent.setClass(MyActivity.this, LineUnPayActivity.class);
                startActivity(intent);
                break;

            // 线下订单--已完成
            case R.id.ll_finish:
                intent.setClass(MyActivity.this, LineFinishOrderActivity.class);
                startActivity(intent);
                break;

            // 线下订单--待评价
            case R.id.ll_wait_evaluate:
                intent.setClass(MyActivity.this, LineWaitEvaluateActivity.class);
                startActivity(intent);
                break;
            // 个人信息修改
            case R.id.ll_message:
                intent.setClass(MyActivity.this, PersionalMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_message:
                intent.setClass(MyActivity.this, MessageActivity.class);
                startActivity(intent);
                break;
        }

    }

//	// 测试登录
//	public void LoginText() {
//		RequestParams params = new RequestParams();
//		params.put("account", "1234567");
//		params.put("passwd", "123456");
//		params.put("identityId", "6");
//		params.put("deviceType", 3);
//		AsyncHttp.posts(HttpConstantChc.USER_LOGIN, params,
//				new JsonHttpResponseHandler() {
//					@Override
//					public void onSuccess(int statusCode, Header[] headers,
//							JSONObject response) {
//						try {
//							boolean success = response.getBoolean("success");
//							String status = response.getString("status");
//							if (status.equals("0")) {
//								if (success) {
//									JSONObject object = response
//											.getJSONObject("rows");
//									editor.putString("id",
//											object.getString("id"));
//									editor.putString("cardNo",
//											object.getString("cardNo"));
//									editor.putString("freezeIntegral",
//											object.getString("freezeIntegral"));
//									editor.putString("phone",
//											object.getString("phone"));
//									editor.putString("area",
//											object.getString("area"));
//									editor.putString("token",
//											object.getString("token"));
//									editor.putString("name",
//											object.getString("name"));
//									editor.putString("account",
//											object.getString("account"));
//									editor.putString("integral",
//											object.getString("integral"));
//									editor.putString("city",
//											object.getString("city"));
//									editor.putString("status",
//											object.getString("status"));
//									editor.commit();
//									// 获取会员积分记录
//									GetUserIntegral();
//									tv_card.setText("卡号:"
//											+ object.getString("cardNo"));
//									String phone = object.getString("phone");
//									tv_phone.setText(phone.substring(0, 3)
//											+ "****"
//											+ phone.substring(7, phone.length()));
//
//									tv_name.setText(object.getString("name"));
//
//								}
//							}
//
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//						super.onSuccess(statusCode, headers, response);
//					}
//
//					@Override
//					public void onFailure(int statusCode, Header[] headers,
//							Throwable throwable, JSONObject errorResponse) {
//
//						super.onFailure(statusCode, headers, throwable,
//								errorResponse);
//						Toast.makeText(getApplicationContext(), "请检查网络",
//								Toast.LENGTH_SHORT).show();
//					}
//
//				});

//	}

}
