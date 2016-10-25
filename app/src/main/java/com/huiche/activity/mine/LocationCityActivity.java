package com.huiche.activity.mine;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.huiche.R;
import com.huiche.adapter.LocationCityHotcityAdapter;
import com.huiche.adapter.LocationCityListAdapter;
import com.huiche.adapter.LocationHotcityAdapter;
import com.huiche.bean.LocationBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.DeviceUtils;
import com.huiche.lib.lib.Utils.LocationUtils;
import com.huiche.lib.lib.Utils.Param;
import com.huiche.lib.lib.base.BaseActivity;
import com.huiche.lib.lib.custemview.MyGridView;
import com.huiche.lib.lib.custemview.MyListView;
import com.huiche.utils.JSONUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */
public class LocationCityActivity extends BaseActivity {


    ImageView iv1;
    TextView tv1;
    TextView textTitilAll;
    TextView tv2;
    ImageView iv2;
    TextView tv3;
    TextView tv4;
    ScrollView scrollview;
    LinearLayout ll;
    MyGridView mygridview1;
    MyGridView mygridview2;
    MyListView myListView;

    //最近访问
    LocationCityHotcityAdapter locationCityHotcityAdapte;
    //热门城市
    LocationHotcityAdapter locationHotcityAdapter;

    //城市列表
    LocationCityListAdapter locationCityListAdapter;

    @Override
    public int getContentView() {
        return R.layout.activity_location_city;
    }

    @Override
    public void findViews() {
        head_view.setVisibility(View.GONE);
        status.setBackgroundColor(Color.parseColor("#f5f5f5"));
        iv1 = (ImageView) findViewById(R.id.iv_1);
        tv1 = (TextView) findViewById(R.id.tv_1);
        textTitilAll = (TextView) findViewById(R.id.text_titil_all);
        tv2 = (TextView) findViewById(R.id.tv_2);
        tv4 = (TextView) findViewById(R.id.tv_4);
        iv2 = (ImageView) findViewById(R.id.iv_2);
        tv3 = (TextView) findViewById(R.id.tv_3);
        scrollview = (ScrollView) findViewById(R.id.scrollview);
        ll = (LinearLayout) findViewById(R.id.ll);
        mygridview1 = (MyGridView) findViewById(R.id.mygridview_1);
        mygridview2 = (MyGridView) findViewById(R.id.mygridview_2);
        myListView = (MyListView) findViewById(R.id.listview);
    }

    @Override
    public void initData() {
        //设置定位
        setLocation();
        //设置最近访问城市
        setLatelyCity();
    }


    @Override
    public void setListeners() {
        setOnListeners(iv1, tv4);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id) {
                    case R.id.iv_1:
                        finish();
                        break;
                    //定位失败，请点击尝试
                    case R.id.tv_4:
                        setLocation();
                        break;
                }
            }
        });
    }

    //设置定位数据
    private void setLocation() {
        LocationUtils.getBDLocation(new LocationUtils.OnLocationUtils() {
            @Override
            public void onSuccess(BDLocation location) {
                if (location == null) {
                    tv1.setText("定位服务未开启");
                    tv2.setText("当前:");
                    tv4.setText("定位失败,请点击尝试");
                } else {
                    tv1.setText("当前城市-" + location.getCity());
                    tv2.setText("当前:" + location.getCity());
                    tv4.setText(location.getCity());
                }
            }
        });

    }

    //设置最近访问城市
    private void setLatelyCity() {
        Param param = new Param();
        param.put("imei", DeviceUtils.getIMEI(LocationCityActivity.this));

        StringBuffer sb = new StringBuffer();
        sb.append("{").append("\"imei\":\"").append(DeviceUtils.getIMEI(LocationCityActivity.this)).append("\"}");
        param.put("key", getMd5Password(sb.toString()));
        ControlUtils.postsEveryTime(Constants.Helen.LOCATION, param, LocationBean.class, new ControlUtils.OnControlUtils<LocationBean>() {
            @Override
            public void onSuccess(String url, LocationBean locationBean, ArrayList<LocationBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {

                //最近访问
                locationCityHotcityAdapte = new LocationCityHotcityAdapter(LocationCityActivity.this);
                mygridview1.setAdapter(locationCityHotcityAdapte);
                locationCityHotcityAdapte.setData(locationBean.data.cityhistory);
                //热门城市
                locationHotcityAdapter = new LocationHotcityAdapter(LocationCityActivity.this);
                mygridview2.setAdapter(locationHotcityAdapter);
                locationHotcityAdapter.setData(locationBean.data.hotcity);

                //整理好的城市列表
                List<LocationBean.DataBean.CitylistBean> dataTmp = JSONUtil.getCitysByJSON(locationBean.data.citylist);
                locationCityListAdapter = new LocationCityListAdapter(LocationCityActivity.this);
                myListView.setAdapter(locationCityListAdapter);
                locationCityListAdapter.setData(dataTmp);

            }

            @Override
            public void onFailure(String url) {
                T("请检测网络");
            }
        });

    }

}
