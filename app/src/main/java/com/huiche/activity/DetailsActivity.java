package com.huiche.activity;

import com.huiche.R;
import com.huiche.adapter.DetailAdapter;
import com.huiche.bean.DetailHelenBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.LRecyclerView.interfaces.OnLoadMoreListener;
import com.huiche.lib.lib.LRecyclerView.interfaces.OnRefreshListener;
import com.huiche.lib.lib.LRecyclerView.recyclerview.LRecyclerView;
import com.huiche.lib.lib.LRecyclerView.recyclerview.LRecyclerViewAdapter;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.Param;
import com.huiche.lib.lib.base.BaseApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/28.
 */
public class DetailsActivity extends com.huiche.lib.lib.base.BaseActivity {

    LRecyclerView myRecycleView;
    LRecyclerViewAdapter lRecyclerViewAdapter;

    @Override
    public int getContentView() {
        return R.layout.activity_detail_helen;
    }

    @Override
    public void findViews() {
        setTitle("明细");
        myRecycleView = (LRecyclerView) findViewById(R.id.my_recycleview);
    }

    @Override
    public void initData() {

        setRecycleView();
        if (BaseApplication.loginResultBean == null) {
            T("请登录");
            return;
        } else {
            // 刷新
            myRecycleView.setRefreshing(true);
        }
    }


    private void setRecycleView() {
        lRecyclerViewAdapter = new LRecyclerViewAdapter(DetailsActivity.this, DetailAdapter.class, myRecycleView);
        myRecycleView.setAdapter(lRecyclerViewAdapter);
        myRecycleView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
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

                ControlUtils.postsEveryTime(Constants.Helen.MYCOLLECTIONDETAIL, param, DetailHelenBean.class, new ControlUtils.OnControlUtils<DetailHelenBean>() {
                    @Override
                    public void onSuccess(String url, DetailHelenBean detailHelenBean, ArrayList<DetailHelenBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                        bufferCircleView.hide();
                        T(detailHelenBean.msg);
                        lRecyclerViewAdapter.setRefresh(detailHelenBean.data.commission);
                    }

                    @Override
                    public void onFailure(String url) {
                        bufferCircleView.hide();
                        T("请检测网络");
                        lRecyclerViewAdapter.setRefresh(null);
                    }
                });
            }
        });
        myRecycleView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                lRecyclerViewAdapter.setAddData(null);
            }
        });
    }

    @Override
    public void setListeners() {
    }
}
