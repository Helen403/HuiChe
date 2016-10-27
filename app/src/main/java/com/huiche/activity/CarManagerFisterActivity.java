package com.huiche.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshSwipeListView;
import com.handmark.pulltorefresh.library.SwipeListView;
import com.huiche.R;
import com.huiche.adapter.CarManagerFisterAdapter;
import com.huiche.bean.CarManagerFisterBean;
import com.huiche.bean.DELECTCAR;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.Param;
import com.huiche.lib.lib.base.BaseApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class CarManagerFisterActivity extends com.huiche.lib.lib.base.BaseActivity {

    TextView tv_1;


    CarManagerFisterAdapter _carManagerFisterAdapter;
    private PullToRefreshSwipeListView pull_listview;
    private SwipeListView listview;
    CarManagerFisterBean carManagerFisterBeanTmp;


    @Override
    public int getContentView() {
        return R.layout.activity_car_manager_fister;
    }

    @Override
    public void findViews() {
        setTitle("车辆管理");


        pull_listview = (PullToRefreshSwipeListView) findViewById(R.id.listview);
        pull_listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview = pull_listview.getRefreshableView();

        tv_1 = (TextView) findViewById(R.id.tv_1);

    }

    @Override
    public void initData() {


        //获取车辆管理数据
        setCarManager();
    }

    private void setCarManager() {
       //获取车辆信息
        getCarInfo();


        pull_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<SwipeListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<SwipeListView> refreshView) {
                //获取车辆信息
                getCarInfo();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<SwipeListView> refreshView) {

            }


        });

    }

    //获取车辆信息
    private void getCarInfo() {
        if (BaseApplication.loginResultBean == null) {
            T("请登录");
            return;
        }
        bufferCircleView.show();
        Param param = new Param();
        param.put("us_id", BaseApplication.loginResultBean.data.id);
        StringBuffer sb = new StringBuffer();
        sb.append("{").append("\"us_id\":\"").append(BaseApplication.loginResultBean.data.id).append("\"}");
        param.put("key", getMd5Password(sb.toString()));
        ControlUtils.postsEveryTime(Constants.Helen.CARMANAGER, param, CarManagerFisterBean.class, new ControlUtils.OnControlUtils<CarManagerFisterBean>() {


            @Override
            public void onSuccess(String url, CarManagerFisterBean carManagerFisterBean, ArrayList<CarManagerFisterBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                carManagerFisterBeanTmp = carManagerFisterBean;
                bufferCircleView.hide();
                _carManagerFisterAdapter = new CarManagerFisterAdapter(carManagerFisterBean.data.car, listview.getRightViewWidth(), new CarManagerFisterAdapter.IOnItemRightClickListener() {
                    @Override
                    public void onRightClick(View v, int position) {
                        //删除车辆
                        delectCar(position);
                    }
                });
                listview.setAdapter(_carManagerFisterAdapter);
                pull_listview.onRefreshComplete();
            }

            @Override
            public void onFailure(String url) {
                pull_listview.onRefreshComplete();
                bufferCircleView.hide();
                T("请检测网络");

            }
        });
    }


    //删除车辆
    private void delectCar(int position) {
        if (carManagerFisterBeanTmp == null) {
            T("暂不提供删除功能");
            return;
        }
        bufferCircleView.show();
        Param param = new Param();
        param.put("ca_id", carManagerFisterBeanTmp.data.car.get(position).ca_id);
        StringBuffer sb = new StringBuffer();
        sb.append("{").append("\"ca_id\":\"").append(carManagerFisterBeanTmp.data.car.get(position).ca_id).append("\"}");
        param.put("key", getMd5Password(sb.toString()));
        ControlUtils.postsEveryTime(Constants.Helen.DELECTCAR, param, DELECTCAR.class, new ControlUtils.OnControlUtils<DELECTCAR>() {
            @Override
            public void onSuccess(String url, DELECTCAR delectcar, ArrayList<DELECTCAR> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                bufferCircleView.hide();
                T(delectcar.msg);
                getCarInfo();
            }

            @Override
            public void onFailure(String url) {
                bufferCircleView.hide();
            }
        });

    }

    @Override
    public void setListeners() {
        setOnListeners(tv_1);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id) {
                    case R.id.tv_1:
                        Intent intent = new Intent(CarManagerFisterActivity.this, CarManagerActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

    }
}
