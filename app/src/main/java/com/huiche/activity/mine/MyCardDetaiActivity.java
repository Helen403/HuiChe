package com.huiche.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huiche.PostResult.CardDetailResult;
import com.huiche.R;
import com.huiche.activity.BusinessDetailActivity;
import com.huiche.activity.PayActivity;
import com.huiche.base.BaseActivity;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.utils.ImagLoadUtils;
import com.huiche.utils.SystemBarUtil;
import com.huiche.utils.TitleUtils;
import com.huiche.utils.ToastUtils;
import com.huiche.view.BufferCircleDialog;
import com.huiche.view.CircleImageView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;




/**
 * Created by Administrator on 2016/7/19.
 * 卡券详情页面
 */
public class MyCardDetaiActivity extends BaseActivity implements View.OnClickListener {
    private String cardId;
    private int cardStatus;
    private SharedPreferences share;
    private LinearLayout ll_title_bar, ll_head, ll_sell;
    private ImageView card_type;
    private String couponsTypeId;
    private String businessStoreImage;
    private TextView card_name, card_money, use_type, tv_star_time, tv_end_time;
    private LinearLayout card_userule, ll_store;
    private CardDetailResult cardData;
    private CircleImageView img_store;
    private String userule;
    private String businessStoreName;
    private ImageButton imageRigth_titil_all;
    private String businessStoreId;
    //1是待使用  2是已过期，3是已使用
    private int enterType;

    /*****************************************************************/
    View dialog;
    TextView tv_confirmNormal;


    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        /******************************************************************/
        //总布局
        RelativeLayout content = new RelativeLayout(this);
        content.setClipToPadding(true);
        content.setFitsSystemWindows(true);
        LinearLayout.LayoutParams rl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        content.setLayoutParams(rl);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_useful_card_detail, null);
        dialog = inflater.inflate(R.layout.dialog_delete_card_helen, content, false);
        dialog.setVisibility(View.GONE);
        dialog.findViewById(R.id.tv_cancelNormal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setVisibility(View.GONE);
            }
        });
        tv_confirmNormal = (TextView) dialog.findViewById(R.id.tv_confirmNormal);
        tv_confirmNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setVisibility(View.GONE);
                deleCard();
            }
        });
        dialog.setLayoutParams(rl);
        content.addView(view);
        content.addView(dialog);
        setContentView(content);


        /******************************************************************/
        TitleUtils.setInfoImg(MyCardDetaiActivity.this, "卡券详情", R.drawable.delete_white);
        share = getSharedPreferences("user_data", MODE_PRIVATE);
        ll_title_bar = (LinearLayout) findViewById(R.id.rl_title_mainActivity);
        ll_head = (LinearLayout) findViewById(R.id.ll_head);
        card_type = (ImageView) findViewById(R.id.card_status);
        card_money = (TextView) findViewById(R.id.card_money);
        use_type = (TextView) findViewById(R.id.use_type);
        tv_star_time = (TextView) findViewById(R.id.tv_star_time);
        tv_end_time = (TextView) findViewById(R.id.tv_end_time);
        card_name = (TextView) findViewById(R.id.card_name);
        ll_sell = (LinearLayout) findViewById(R.id.ll_sell);
        img_store = (CircleImageView) findViewById(R.id.img_store);
        View line_view = findViewById(R.id.line_view);
        card_userule = (LinearLayout) findViewById(R.id.card_userule);
        ll_store = (LinearLayout) findViewById(R.id.ll_store);
        imageRigth_titil_all = (ImageButton) findViewById(R.id.imageRigth_titil_all);
        TextView tv_detail = (TextView) findViewById(R.id.tv_detail);
        ScrollView scroll_view = (ScrollView) findViewById(R.id.scroll_view);
        Intent intent = getIntent();
        cardId = intent.getStringExtra("id");
        cardStatus = intent.getIntExtra("status", 0);
        couponsTypeId = intent.getStringExtra("couponsTypeId");
        businessStoreImage = intent.getStringExtra("businessStoreImage");
        userule = intent.getStringExtra("userule");
        businessStoreName = intent.getStringExtra("businessStoreName");
        businessStoreId = intent.getStringExtra("businessStoreId");
        //默认值5是错误
        enterType = intent.getIntExtra("enterType", 5);
        if (!userule.equals("")) {
            scroll_view.setVisibility(View.VISIBLE);
            line_view.setVisibility(View.GONE);
            tv_detail.setText(userule);
        }
    }

    @Override
    public void initData() {
        getCardDetail();
        //初始化删除卡券弹窗
//        initDialog();
        if (cardStatus == 2) {
            card_type.setVisibility(View.VISIBLE);
        } else if (cardStatus == 1 || cardStatus == 3) {
            card_type.setVisibility(View.GONE);
        }
        //买单按钮
        if (cardStatus == 2 || cardStatus == 3) {
            ll_sell.setVisibility(View.GONE);
        }

        //背景色与头像
        if (couponsTypeId.equals("4")) {
            ll_head.setBackgroundColor(getResources().getColor(R.color.text_color_pink));
            ll_title_bar.setBackgroundColor(getResources().getColor(R.color.text_color_pink));
            ll_store.setVisibility(View.GONE);
            ll_sell.setVisibility(View.GONE);
            // 沉浸式状态栏
            SystemBarUtil.initSystemBar(this, R.color.text_color_pink);

        } else {
            ll_head.setBackgroundColor(getResources().getColor(R.color.bule_title));
            ll_title_bar.setBackgroundColor(getResources().getColor(R.color.bule_title));
            // 沉浸式状态栏
            SystemBarUtil.initSystemBar(this, R.color.bule_title);
            //商家头像
            img_store.setVisibility(View.VISIBLE);
            ImagLoadUtils.setImage(businessStoreImage, img_store, this);
            ll_store.setVisibility(View.VISIBLE);
        }
    }

    //获取卡券详情
    private void getCardDetail() {
        RequestParams params = new RequestParams();
        params.put("token", share.getString("token", ""));
        params.put("id", cardId);
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(MyCardDetaiActivity.this);
        bufferCircleDialog.show("正在加载", false, null);
        AsyncHttp.posts(HttpConstantChc.GET_CARD_DETAIL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                try {
                    cardData = new CardDetailResult();
                    bufferCircleDialog.dialogcancel();
                    String status = response.getString("status");
//                    String msg = response.getString("msg");
                    if (status.equals("0")) {
                        JSONObject obj = response.getJSONObject("rows");
                        String result = obj.toString();
                        cardData = JSON.parseObject(result, CardDetailResult.class);
                        if (couponsTypeId.equals("4")) {
                            card_name.setText("代金券");
                        } else {
                            card_name.setText(businessStoreName);
                        }
                        card_money.setText(cardData.getPrice());
                        use_type.setText("消费满" + (cardData.getDeduction()) + "元可以使用");
                        String starTime = cardData.getStartTime();
                        tv_star_time.setText(starTime.substring(0, 10));
                        tv_end_time.setText((cardData.getEndTime()).substring(0, 10));
                        businessStoreImage = cardData.getBusinessStoreImage();
                        ImagLoadUtils.setImage(businessStoreImage, img_store, MyCardDetaiActivity.this);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(MyCardDetaiActivity.this, "请检查网络");
                bufferCircleDialog.dialogcancel();


            }
        });

    }

    @Override
    public void setListeners() {
        card_userule.setOnClickListener(this);
        imageRigth_titil_all.setOnClickListener(this);
        ll_store.setOnClickListener(this);
        ll_sell.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            //卡券使用规则
            case R.id.card_userule:
                intent.setClass(MyCardDetaiActivity.this, CardUseRuleActivity.class);
                intent.putExtra("userule", userule);
                startActivity(intent);
                break;
            //删除卡券
            case R.id.imageRigth_titil_all:
                //删除卡券弹窗
//               showDialog();
                dialog.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_cancelNormal:
//                dimissDialog();
                break;
            case R.id.tv_confirmNormal:

//                dimissDialog();
//                deleCard();
                break;
            //商家详情
            case R.id.ll_store:
                intent.setClass(MyCardDetaiActivity.this, BusinessDetailActivity.class);
                intent.putExtra("businessStoreId", businessStoreId);
                intent.putExtra("empty", "0");
                startActivity(intent);
                break;
            //跳到买单界面
            case R.id.ll_sell:
                intent.setClass(MyCardDetaiActivity.this, PayActivity.class);
                intent.putExtra("businessName", businessStoreName);
                intent.putExtra("businessID", businessStoreId);
                startActivity(intent);


                break;
        }
    }

//    private Dialog mDialog;
//    private TextView dialog_content;
//    private TextView tv_cancel;
//    private TextView tv_confirm;

//    private void initDialog() {
//        View view= LayoutInflater.from(this).inflate(R.layout.dialog_delete_card,null);
//        dialog_content=(TextView)view.findViewById(R.id.tv_dialogNormal_content);
//        tv_cancel=(TextView)view.findViewById(R.id.tv_cancelNormal);
//        tv_confirm=(TextView)view.findViewById(R.id.tv_confirmNormal);
//        mDialog =new Dialog(this,R.style.customDialog);
//        mDialog.setContentView(view);
//        tv_cancel.setOnClickListener(this);
//        tv_confirm.setOnClickListener(this);
//        Window diawindow=mDialog.getWindow();
//
//        WindowManager m = mDialog.getWindow().getWindowManager();
//        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
//        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
//        p.height = (int) (d.getHeight() * 0.2); // 高度设置为屏幕的0.2
//        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的1
//        diawindow.setAttributes(p);
//    }
//    private void showDialog(){
//        mDialog.show();
//    }
//    private void dimissDialog(){
//        mDialog.dismiss();
//    }

    private void deleCard() {
        //删除卡券
        //获取待使用卡券数据
        RequestParams params = new RequestParams();
        params.put("token", share.getString("token", ""));
        params.put("id", cardId);
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(MyCardDetaiActivity.this);
        bufferCircleDialog.show("正在加载", false, null);
        AsyncHttp.posts(HttpConstantChc.DELETE_CARD, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                try {
                    bufferCircleDialog.dialogcancel();
                    String status = response.getString("status");
                    String msg = response.getString("msg");
                    if (status.equals("0")) {
                        //待使用
                        if (enterType == 1) {
                            setResult(1089);
                            ToastUtils.ToastShowShort(MyCardDetaiActivity.this, msg);
                            finish();
                        }
                        //已过期
                        else if (enterType == 2) {
                            setResult(1542);
                            ToastUtils.ToastShowShort(MyCardDetaiActivity.this, msg);
                            finish();
                        }
                        //已使用
                        else if (enterType == 3) {
                            setResult(1026);
                            ToastUtils.ToastShowShort(MyCardDetaiActivity.this, msg);
                            finish();
                        }


                    } else {
                        ToastUtils.ToastShowShort(MyCardDetaiActivity.this, msg);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    bufferCircleDialog.dialogcancel();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
                ToastUtils.ToastShowShort(MyCardDetaiActivity.this, "请检查网络");
                bufferCircleDialog.dialogcancel();
            }
        });
    }

    //删除弹窗

}
