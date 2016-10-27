package com.huiche.activity;

import android.view.View;

import com.baidu.location.BDLocation;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshSwipeListView;
import com.handmark.pulltorefresh.library.SwipeListView;
import com.huiche.R;
import com.huiche.adapter.MyCollectionAdapter;
import com.huiche.bean.MyCollectionBean;
import com.huiche.bean.MyCollectionDelectBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.LocationUtils;
import com.huiche.lib.lib.Utils.Param;
import com.huiche.lib.lib.base.BaseApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author 我的收藏
 */

public class MyCollectionsActivity extends com.huiche.lib.lib.base.BaseActivity {

    private PullToRefreshSwipeListView pull_listview;
    private SwipeListView listview;
    MyCollectionBean myCollectionBeanTmp;
    MyCollectionAdapter adapter;


    @Override
    public int getContentView() {
        return R.layout.activity_my_collections;
    }

    @Override
    public void findViews() {
        setTitle("我的收藏");

        pull_listview = (PullToRefreshSwipeListView) findViewById(R.id.listview);
        pull_listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview = pull_listview.getRefreshableView();
    }

    @Override
    public void initData() {

        //获取收藏的数据
        setCollection();
    }


    private void setCollection() {
        //获取收藏的数据
        getCollection();


        pull_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<SwipeListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<SwipeListView> refreshView) {
                //获取收藏的数据
                getCollection();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<SwipeListView> refreshView) {

            }


        });

    }

    //获取收藏的数据
    private void getCollection() {
        if (BaseApplication.loginResultBean == null) {
            T("请登录");
            return;
        }
        LocationUtils.getBDLocation(new LocationUtils.OnLocationUtils() {
            @Override
            public void onSuccess(BDLocation location) {

                setData(location.getLatitude(), location.getLongitude());
            }
        });



    }

    private void setData(double latitude, double longitude) {
        bufferCircleView.show();
        Param param = new Param();
        param.put("us_id", BaseApplication.loginResultBean.data.id);
        //为纬度
        param.put("lat", latitude);
        //经度
        param.put("lng", longitude);
        StringBuffer sb = new StringBuffer();
        sb.append("{").append("\"us_id\":\"").append(BaseApplication.loginResultBean.data.id).append("\",\"lat\":\"").append(latitude).append("\",\"lng\":\"").append(longitude).append("\"}");
        param.put("key", getMd5Password(sb.toString()));

        ControlUtils.postsEveryTime(Constants.Helen.MYCOLLECTION, param, MyCollectionBean.class, new ControlUtils.OnControlUtils<MyCollectionBean>() {
            @Override
            public void onSuccess(String url, MyCollectionBean myCollectionBean, ArrayList<MyCollectionBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                bufferCircleView.hide();

                myCollectionBeanTmp = myCollectionBean;
                adapter = new MyCollectionAdapter(myCollectionBean.data, listview.getRightViewWidth(), new MyCollectionAdapter.IOnItemRightClickListener() {
                    @Override
                    public void onRightClick(View v, int position) {
                        //删除车辆
                        delectCar(position);
                    }
                });
                listview.setAdapter(adapter);
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
        if (myCollectionBeanTmp == null) {
            T("暂不提供删除功能");
            return;
        }
        if (BaseApplication.loginResultBean==null){
            T("请登录");
            return;
        }

        bufferCircleView.show();
        Param param = new Param();
        param.put("us_id", BaseApplication.loginResultBean.data.id);
        param.put("goods_id", myCollectionBeanTmp.data.get(position).goods_id);

        StringBuffer sb = new StringBuffer();
        sb.append("{").append("\"us_id\":\"").append(BaseApplication.loginResultBean.data.id).append("\",\"goods_id\":\"").append(myCollectionBeanTmp.data.get(position).goods_id).append("\"}");
        param.put("key", getMd5Password(sb.toString()));

        ControlUtils.postsEveryTime(Constants.Helen.DELECTMYCOLLECTION, param, MyCollectionDelectBean.class, new ControlUtils.OnControlUtils<MyCollectionDelectBean>() {
            @Override
            public void onSuccess(String url, MyCollectionDelectBean myCollectionDelectBean, ArrayList<MyCollectionDelectBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                bufferCircleView.hide();

                //获取收藏的数据
                getCollection();
            }

            @Override
            public void onFailure(String url) {
                bufferCircleView.hide();
                T("请检测网络");
            }
        });
    }


    @Override
    public void setListeners() {

    }
}
