package com.huiche.activity.mine;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshSwipeListView;
import com.handmark.pulltorefresh.library.SwipeListView;
import com.huiche.R;
import com.huiche.adapter.Adapter_CarManagerFister;
import com.huiche.base.CarManagerFisterBean;
import com.huiche.base.MyApplication;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.Param;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class CarManagerFisterActivity extends com.huiche.lib.lib.base.BaseActivity {

    TextView tv_1;


    Adapter_CarManagerFister adapter_carManagerFister;
    private PullToRefreshSwipeListView pull_listview;
    private SwipeListView listview;


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


        Param param = new Param();
        param.put("us_id", MyApplication.loginResultBean.data.id);
        StringBuffer sb = new StringBuffer();
        sb.append("{").append("\"us_id\":\"").append(MyApplication.loginResultBean.data.id).append("\"}");
        param.put("key", getMd5Password(sb.toString()));
        ControlUtils.postsEveryTime(Constants.Helen.CARMANAGER, param, CarManagerFisterBean.class, new ControlUtils.OnControlUtils<CarManagerFisterBean>() {
            @Override
            public void onSuccess(String url, CarManagerFisterBean carManagerFisterBean, ArrayList<CarManagerFisterBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                T(carManagerFisterBean.msg);
                adapter_carManagerFister = new Adapter_CarManagerFister(carManagerFisterBean.data.car, listview.getRightViewWidth(), new Adapter_CarManagerFister.IOnItemRightClickListener() {
                    @Override
                    public void onRightClick(View v, int position) {
                        T("暂时不提供删除");
                    }
                });
                listview.setAdapter(adapter_carManagerFister);
            }

            @Override
            public void onFailure(String url) {

            }
        });



        pull_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<SwipeListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<SwipeListView> refreshView) {


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<SwipeListView> refreshView) {

            }


        });


    }

    @Override
    public void setListeners() {
        tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarManagerFisterActivity.this, CarManagerActivity.class);
                startActivity(intent);
            }
        });
    }
}
