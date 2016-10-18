package com.huiche.activity.mine;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.MyApplication;
import com.huiche.bean.OilCardJiHuoBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.Param;
import com.huiche.view.BufferjihuoViewDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/9/26.
 */
public class OilcardActivity extends com.huiche.lib.lib.base.BaseActivity {

    TextView tv1, tv_4, tv_5;

    BufferjihuoViewDialog bufferjihuoViewDialog;

    RelativeLayout rl1, rl2, rl;
    Handler handler = new Handler();


    @Override
    public int getContentView() {
        return R.layout.activity_oilcard;
    }

    @Override
    protected void onShowMessage(RelativeLayout relativeLayout) {
        super.onShowMessage(relativeLayout);
        bufferjihuoViewDialog = new BufferjihuoViewDialog(OilcardActivity.this);
        bufferjihuoViewDialog.setVisibility(View.GONE);
        relativeLayout.addView(bufferjihuoViewDialog);

    }

    @Override
    public void findViews() {
        setTitle("我的油卡");
        tv1 = (TextView) findViewById(R.id.tv_1);
        tv_4 = (TextView) findViewById(R.id.tv_4);
        tv_5 = (TextView) findViewById(R.id.tv_5);

        rl1 = (RelativeLayout) findViewById(R.id.rl_1);
        rl2 = (RelativeLayout) findViewById(R.id.rl_2);
        rl = (RelativeLayout) findViewById(R.id.rl);
        //设置为未激活
        rl.setTag("0");

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {
        setOnListeners(rl, rl2);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id) {
                    //点击激活
                    case R.id.rl:

                        oilCardJiHuo();
                        break;
                    //点击充值油卡
                    case R.id.rl_2:
                        Intent intent = new Intent(OilcardActivity.this, OilcardRechargeActivity.class);
                        startActivity(intent);
                        break;

                }
            }
        });


    }

    //油卡激活
    private void oilCardJiHuo() {

        if ("1".equals(rl.getTag())) {
            T("已激活");
            return;
        }
        if (MyApplication.loginResultBean == null) {
            T("请登录");
            return;
        }
        bufferCircleView.show();
        Param param = new Param();
        param.put("us_id", MyApplication.loginResultBean.data.id);
        StringBuffer sb = new StringBuffer();
        sb.append("{").append("\"us_id\":\"").append(MyApplication.loginResultBean.data.id).append("\"}");
        param.put("key", getMd5Password(sb.toString()));

        ControlUtils.postsEveryTime(Constants.Helen.MYOIlCARD, param, OilCardJiHuoBean.class, new ControlUtils.OnControlUtils<OilCardJiHuoBean>() {
            @Override
            public void onSuccess(String url, OilCardJiHuoBean oilCardJiHuoBean, ArrayList<OilCardJiHuoBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                bufferCircleView.hide();
                T(oilCardJiHuoBean.msg);

                //设置数据
                setInfo(oilCardJiHuoBean);
            }

            @Override
            public void onFailure(String url) {
                bufferCircleView.hide();
                T("请检测网络");
            }
        });


    }

    //设置数据
    private void setInfo(final OilCardJiHuoBean oilCardJiHuoBean) {
        rl.setTag("1");
        bufferjihuoViewDialog.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bufferjihuoViewDialog.setVisibility(View.GONE);

                rl1.setVisibility(View.VISIBLE);
                rl2.setVisibility(View.VISIBLE);
                tv1.setText(oilCardJiHuoBean.data.us_name);
                tv_4.setText(oilCardJiHuoBean.data.us_money+"");
                tv_5.setText(oilCardJiHuoBean.data.us_card);
            }
        }, 2000);




        bufferjihuoViewDialog.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bufferjihuoViewDialog.setVisibility(View.GONE);
                rl1.setVisibility(View.VISIBLE);
                rl2.setVisibility(View.VISIBLE);
                tv1.setText(oilCardJiHuoBean.data.us_name);
                tv_4.setText(oilCardJiHuoBean.data.us_money+"");
                tv_5.setText(oilCardJiHuoBean.data.us_card);
            }
        });
    }
}
