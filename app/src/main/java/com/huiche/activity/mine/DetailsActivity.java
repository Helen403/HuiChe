package com.huiche.activity.mine;

import com.huiche.R;
import com.huiche.adapter.Adapter_Detail_Helen;
import com.huiche.base.MyApplication;
import com.huiche.bean.DetailHelenBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.MyRecycleView.MyLinearLayoutManager;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.Param;
import com.huiche.lib.lib.base.MyBaseRecycleAdapter;
import com.huiche.lib.lib.custemview.MyRecycleView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/28.
 */
public class DetailsActivity extends com.huiche.lib.lib.base.BaseActivity {
    Adapter_Detail_Helen adapter;

    MyRecycleView myRecycleView;


    @Override
    public int getContentView() {
        return R.layout.activity_detail_helen;
    }

    @Override
    public void findViews() {
        setTitle("明细");
        myRecycleView = (MyRecycleView) findViewById(R.id.my_recycleview);
    }

    @Override
    public void initData() {

        setRecycleView();
    }


    private void setRecycleView() {
        // 使用重写后的线性布局管理器
        MyLinearLayoutManager manager = new MyLinearLayoutManager(DetailsActivity.this);
        myRecycleView.setLayoutManager(manager);
        adapter = new Adapter_Detail_Helen(DetailsActivity.this, myRecycleView);
        myRecycleView.setAdapter(adapter);
        adapter.setOnRefresh(new MyBaseRecycleAdapter.OnRefresh() {
            @Override
            public void onRefresh() {
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

                ControlUtils.postsEveryTime(Constants.Helen.MYCOLLECTIONDETAIL, param, DetailHelenBean.class, new ControlUtils.OnControlUtils<DetailHelenBean>() {
                    @Override
                    public void onSuccess(String url, DetailHelenBean detailHelenBean, ArrayList<DetailHelenBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                        bufferCircleView.hide();
                        T(detailHelenBean.msg);
                        adapter.setRefresh(detailHelenBean.data.commission);
                    }

                    @Override
                    public void onFailure(String url) {
                        bufferCircleView.hide();
                        T("请检测网络");
                        adapter.setRefresh(null);
                    }
                });





            }

            @Override
            public void onAddData() {
                adapter.setAddData(null);
            }
        });
        // 刷新
        myRecycleView.setRefresh(true);

    }

    @Override
    public void setListeners() {
    }
}
