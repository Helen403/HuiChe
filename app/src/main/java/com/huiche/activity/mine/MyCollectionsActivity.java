package com.huiche.activity.mine;

import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshSwipeListView;
import com.handmark.pulltorefresh.library.SwipeListView;
import com.huiche.R;
import com.huiche.adapter.Adapter_MyCollection;
import com.huiche.base.MyApplication;
import com.huiche.bean.MyCollectionBean;
import com.huiche.bean.MyCollectionDelectBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.Param;

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
    Adapter_MyCollection adapter;


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
        if (MyApplication.loginResultBean == null) {
            T("请登录");
            return;
        }
        bufferCircleView.show();
        Param param = new Param();
        param.put("us_id", MyApplication.loginResultBean.data.id);
        //为纬度
        param.put("lat", 22.5);
        //经度
        param.put("lng", 113.5);
        StringBuffer sb = new StringBuffer();
        sb.append("{").append("\"us_id\":\"").append(MyApplication.loginResultBean.data.id).append("\",\"lat\":\"").append(22.5).append("\",\"lng\":\"").append(113.5).append("\"}");
        param.put("key", getMd5Password(sb.toString()));

        ControlUtils.postsEveryTime(Constants.Helen.MYCOLLECTION, param, MyCollectionBean.class, new ControlUtils.OnControlUtils<MyCollectionBean>() {
            @Override
            public void onSuccess(String url, MyCollectionBean myCollectionBean, ArrayList<MyCollectionBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                bufferCircleView.hide();
                T(myCollectionBean.msg);
                myCollectionBeanTmp = myCollectionBean;
                adapter = new Adapter_MyCollection(myCollectionBean.data, listview.getRightViewWidth(), new Adapter_MyCollection.IOnItemRightClickListener() {
                    @Override
                    public void onRightClick(View v, int position) {
                        //删除车辆
                        delectCar(position);
                    }
                });
                listview.setAdapter(adapter);
            }

            @Override
            public void onFailure(String url) {
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
        if (MyApplication.loginResultBean==null){
            T("请登录");
            return;
        }

        bufferCircleView.show();
        Param param = new Param();
        param.put("us_id", MyApplication.loginResultBean.data.id);
        param.put("goods_id", myCollectionBeanTmp.data.get(position).goods_id);

        StringBuffer sb = new StringBuffer();
        sb.append("{").append("\"us_id\":\"").append(MyApplication.loginResultBean.data.id).append("\",\"goods_id\":\"").append(myCollectionBeanTmp.data.get(position).goods_id).append("\"}");
        param.put("key", getMd5Password(sb.toString()));

        ControlUtils.postsEveryTime(Constants.Helen.DELECTMYCOLLECTION, param, MyCollectionDelectBean.class, new ControlUtils.OnControlUtils<MyCollectionDelectBean>() {
            @Override
            public void onSuccess(String url, MyCollectionDelectBean myCollectionDelectBean, ArrayList<MyCollectionDelectBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                bufferCircleView.hide();
                T(myCollectionDelectBean.msg);
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
