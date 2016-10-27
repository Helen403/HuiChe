package com.huiche.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.bean.InsuranceBean;
import com.huiche.bean.InsuranceResultBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.Param;
import com.huiche.lib.lib.base.BaseActivity;
import com.huiche.lib.view.LoopView;
import com.huiche.lib.view.OnItemSelectedListener;
import com.huiche.view.BufferbaoxianViewDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/26.
 */
public class InsuranceActivity extends BaseActivity {
    BufferbaoxianViewDialog bufferbaoxianViewDialog;
    View viewWhell;
    LoopView loopView;


    private TextView tv1;
    private ImageView iv1;
    private TextView tv2;
    private ImageView iv2;
    private TextView tv3;
    private TextView tv4;
    private View view1;
    private EditText edit1;
    private TextView tv5;
    private View view2;
    private EditText edit2;
    private TextView tv6;
    private TextView tv13;
    private TextView tv7;
    private View view3;
    private EditText edit3;
    private TextView tv8;
    private View view4;
    private EditText edit4;
    private TextView tv9;
    private TextView tv12;
    private TextView tv10;
    private View view5;
    private EditText edit5;
    private TextView tv11;
    RelativeLayout rl_1;
    InsuranceBean insuranceBeanTmp;


    @Override
    public int getContentView() {
        return R.layout.activity_insurance;
    }

    @Override
    protected void onShowMessage(RelativeLayout relativeLayout) {
        super.onShowMessage(relativeLayout);
        bufferbaoxianViewDialog = new BufferbaoxianViewDialog(this);
        bufferbaoxianViewDialog.setVisibility(View.GONE);
        relativeLayout.addView(bufferbaoxianViewDialog);
        viewWhell = inflater.inflate(R.layout.view_whell, content, false);
        viewWhell.setVisibility(View.GONE);
        relativeLayout.addView(viewWhell);
    }

    @Override
    public void findViews() {
        setTitle("购买保险");
        tv1 = (TextView) findViewById(R.id.tv_1);
        iv1 = (ImageView) findViewById(R.id.iv_1);
        tv2 = (TextView) findViewById(R.id.tv_2);
        iv2 = (ImageView) findViewById(R.id.iv_2);
        tv3 = (TextView) findViewById(R.id.tv_3);
        tv4 = (TextView) findViewById(R.id.tv_4);
        view1 = findViewById(R.id.view_1);
        edit1 = (EditText) findViewById(R.id.edit_1);
        tv5 = (TextView) findViewById(R.id.tv_5);
        view2 = findViewById(R.id.view_2);
        edit2 = (EditText) findViewById(R.id.edit_2);
        tv6 = (TextView) findViewById(R.id.tv_6);
        tv13 = (TextView) findViewById(R.id.tv_13);
        tv7 = (TextView) findViewById(R.id.tv_7);
        view3 = findViewById(R.id.view_3);
        edit3 = (EditText) findViewById(R.id.edit_3);
        tv8 = (TextView) findViewById(R.id.tv_8);
        view4 = findViewById(R.id.view_4);
        edit4 = (EditText) findViewById(R.id.edit_4);
        tv9 = (TextView) findViewById(R.id.tv_9);
        tv12 = (TextView) findViewById(R.id.tv_12);
        tv10 = (TextView) findViewById(R.id.tv_10);
        view5 = findViewById(R.id.view_5);
        edit5 = (EditText) findViewById(R.id.edit_5);
        tv11 = (TextView) findViewById(R.id.tv_11);
        loopView = (LoopView) viewWhell.findViewById(R.id.loopview);
        rl_1 = (RelativeLayout) findViewById(R.id.rl_1);

        //线下购买
        iv2.setSelected(false);
    }

    @Override
    public void initData() {

        //请求保险公司
        setInsurance();

    }

    //请求保险公司
    private void setInsurance() {
        ControlUtils.posts(Constants.Helen.INSURANCE, null, InsuranceBean.class, new ControlUtils.OnControlUtils<InsuranceBean>() {
            @Override
            public void onSuccess(String url, InsuranceBean insuranceBean, ArrayList<InsuranceBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                insuranceBeanTmp = insuranceBean;
                setLoop(insuranceBean);
            }

            @Override
            public void onFailure(String url) {

            }
        });

    }

    private void setLoop(InsuranceBean insuranceBean) {
        //设置是否循环播放
        //loopView.setNotLoop();
        final ArrayList<String> data = new ArrayList<>();
        int count = insuranceBean.data.size();
        for (int i = 0; i < count; i++) {
            data.add(insuranceBean.data.get(i).as_name);
        }
        //设置原始数据
        loopView.setItems(data);
        //设置初始位置
        loopView.setInitPosition(5);
        //设置字体大小
        loopView.setTextSize(30);

        //滚动监听
        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {

                if (data.size() > 0)
                    //设置选择的购买保险公司
                    tv13.setText(data.get(index));
            }
        });
    }

    @Override
    public void setListeners() {


        setListener();
        setOnListeners(iv1, iv2, tv2, tv3, tv11, tv12, rl_1);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id) {
                    //点击提交
                    case R.id.tv_11:
                        Commit();

                        break;
                    //礼品卡
                    case R.id.tv_12:
                        goToActivityByClass(DiscountActivity.class);
                        break;
                    //保险公司
                    case R.id.rl_1:
                        if (insuranceBeanTmp==null)
                        {
                            T("请");
                        }
                        viewWhell.setVisibility(View.VISIBLE);
                        break;
                    //点击线上购买
                    case R.id.iv_1:
                        iv1.setSelected(true);
                        iv2.setSelected(false);
                        break;
                    //点击线下购买
                    case R.id.iv_2:
                        iv1.setSelected(false);
                        iv2.setSelected(true);
                        break;
                    //点击线上购买
                    case R.id.tv_2:
                        iv1.setSelected(true);
                        iv2.setSelected(false);
                        break;
                    //点击线下购买
                    case R.id.tv_3:
                        iv1.setSelected(false);
                        iv2.setSelected(true);

                }
            }
        });


    }

    //点击提交
    private void Commit() {
        int type = 0;
        if (iv2.isSelected()) {
            type = 1;
        }
        String price = edit1.getText().toString().trim();
        if (TextUtils.isEmpty(price)) {
            T("购买的保险金额不能为空!");
            return;
        }
        String policy = edit2.getText().toString().trim();
        if (TextUtils.isEmpty(policy)) {
            T("保单号不能为空!");
            return;
        }
        String assurer = tv13.getText().toString().trim();
        if (assurer.equals("请选择保险公司")) {
            T("请选择保险公司!");
            return;
        }
        String realname = edit3.getText().toString().trim();
        if (TextUtils.isEmpty(realname)) {
            T("请输入你的姓名!");
            return;
        }

        String call = edit4.getText().toString().trim();
        if (TextUtils.isEmpty(call)) {
            T("请输入你的姓名!");
            return;
        }

        String partner = edit5.getText().toString().trim();
        if (TextUtils.isEmpty(partner)) {
            T("请输入推荐人的电话号码!");
            return;
        }
        int vol = 1;
        bufferCircleView.show();
        Param param = new Param();
        param.put("type", type);
        param.put("price", price);
        param.put("policy", policy);
        param.put("assurer", assurer);
        param.put("realname", realname);
        param.put("call", call);
        param.put("vol", vol);
        param.put("partner", partner);

        StringBuffer sb = new StringBuffer();

        sb.append("{\"").append("type").append("\":\"").append(type).append("\"");
        sb.append(",\"").append("price").append("\":\"").append(price).append("\"");
        sb.append(",\"").append("policy").append("\":\"").append(policy).append("\"");
        sb.append(",\"").append("assurer").append("\":\"").append(assurer).append("\"");
        sb.append(",\"").append("realname").append("\":\"").append(realname).append("\"");
        sb.append(",\"").append("call").append("\":\"").append(call).append("\"");
        sb.append(",\"").append("vol").append("\":\"").append(vol).append("\"");
        sb.append(",\"").append("partner").append("\":\"").append(partner).append("\"}");
        param.put("key", getMd5Password(sb.toString()));
        ControlUtils.postsEveryTime(Constants.Helen.PAYINSURANCE, param, InsuranceResultBean.class, new ControlUtils.OnControlUtils<InsuranceResultBean>() {
            @Override
            public void onSuccess(String url, InsuranceResultBean insuranceResultBean, ArrayList<InsuranceResultBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                T(insuranceResultBean.msg);
                bufferbaoxianViewDialog.setVisibility(View.VISIBLE);
                bufferCircleView.hide();
            }

            @Override
            public void onFailure(String url) {
                bufferCircleView.hide();
                T("请检测网络");
            }
        });


    }

    private void setListener() {
        TextView tv = (TextView) viewWhell.findViewById(R.id.tv_2);
        TextView tv3 = (TextView) viewWhell.findViewById(R.id.tv_3);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewWhell.setVisibility(View.GONE);
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewWhell.setVisibility(View.GONE);
            }
        });
    }
}
