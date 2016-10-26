package com.huiche.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.huiche.R;
import com.huiche.adapter.Adapter_MyPartner;
import com.huiche.bean.MyPartnerBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.MyRecycleView.MyLinearLayoutManager;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.LocationUtils;
import com.huiche.lib.lib.Utils.Param;
import com.huiche.lib.lib.base.BaseApplication;
import com.huiche.lib.lib.base.MyBaseRecycleAdapter;
import com.huiche.lib.lib.custemview.MyRecycleView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/8.
 */
public class MyPartnerActivity extends com.huiche.lib.lib.base.BaseActivity {


    Adapter_MyPartner adapter_myPartner;
    MyRecycleView recycleView;
    TextView tv_1;

    @Override
    public int getContentView() {
        return R.layout.activity_my_partner_recycleview;
    }

    @Override
    public void findViews() {
        setTitle("我的合伙人");
        setRightRes(R.drawable.classify_01);
        recycleView = (MyRecycleView) findViewById(R.id.myrecycle_view);
    }

    @Override
    public void initData() {

        //设置RecyclerView
        setRecycleView();
        //添加头部View
        addHeadView();
    }

    private void addHeadView() {
        View head = inflater.inflate(R.layout.activity_my_partner, contentView, false);
        recycleView.addHeaderView(head);
        tv_1 = (TextView) head.findViewById(R.id.tv_1);
    }

    private void setRecycleView() {
        // 使用重写后的线性布局管理器
        MyLinearLayoutManager manager = new MyLinearLayoutManager(MyPartnerActivity.this);
        recycleView.setLayoutManager(manager);
        adapter_myPartner = new Adapter_MyPartner(MyPartnerActivity.this, recycleView);
        recycleView.setAdapter(adapter_myPartner);
        adapter_myPartner.setOnRefresh(new MyBaseRecycleAdapter.OnRefresh() {
            @Override
            public void onRefresh() {
                if (BaseApplication.loginResultBean == null) {
                    T("请登录");
                    return;
                }
                LocationUtils.getBDLocation(new LocationUtils.OnLocationUtils() {
                    @Override
                    public void onSuccess(BDLocation location) {
                        //获取定位数据
                        setDataRefresh(location.getLatitude(), location.getLongitude());
                    }
                });
            }

            @Override
            public void onAddData() {
                adapter_myPartner.setAddData(null);
            }
        });
        if (BaseApplication.loginResultBean == null) {
            T("请登录");
            return;
        } else {
            // 刷新
            recycleView.setRefresh(true);
        }
    }

    private void setDataRefresh(double lat, double lng) {
        bufferCircleView.show();
        Param param = new Param();
        param.put("us_id", BaseApplication.loginResultBean.data.id);
        //为纬度
        param.put("lat", lat);
        //经度
        param.put("lng", lng);

        StringBuffer sb = new StringBuffer();
        sb.append("{").append("\"us_id\":\"").append(BaseApplication.loginResultBean.data.id).append("\",\"lat\":\"").append(lat).append("\",\"lng\":\"").append(lng).append("\"}");
        param.put("key", getMd5Password(sb.toString()));

        ControlUtils.postsEveryTime(Constants.Helen.ADDPARTERADD, param, MyPartnerBean.class, new ControlUtils.OnControlUtils<MyPartnerBean>() {
            @Override
            public void onSuccess(String url, MyPartnerBean myPartnerBean, ArrayList<MyPartnerBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                bufferCircleView.hide();

                //设置数据
                setInfo(myPartnerBean);
                adapter_myPartner.setRefresh(myPartnerBean.data.partner);
            }

            @Override
            public void onFailure(String url) {
                bufferCircleView.hide();
                T("请检测网络");
                adapter_myPartner.setRefresh(null);
            }
        });
    }

    //设置数据
    private void setInfo(MyPartnerBean myPartnerBean) {
        tv_1.setText(myPartnerBean.data.commission);
    }

    @Override
    public void setListeners() {

        getRightBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPartnerActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });

    }
}
