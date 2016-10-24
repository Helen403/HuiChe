package com.huiche.activity.mine;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshSwipeListView;
import com.handmark.pulltorefresh.library.SwipeListView;
import com.huiche.R;
import com.huiche.adapter.Adapter_GoodsReceipt;
import com.huiche.base.MyApplication;
import com.huiche.bean.GoodsReceiptBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.Param;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/30.
 */
public class GoodsReceiptActivity extends com.huiche.lib.lib.base.BaseActivity {


    TextView tv_1;


    Adapter_GoodsReceipt adapter_goodsReceipt;
    private PullToRefreshSwipeListView pull_listview;
    private SwipeListView listview;


    @Override
    public int getContentView() {
        return R.layout.activity_goods_receipt;
    }

    @Override
    public void findViews() {
        setTitle("收货地址");

        pull_listview = (PullToRefreshSwipeListView) findViewById(R.id.listview);
        pull_listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview = pull_listview.getRefreshableView();

        tv_1 = (TextView) findViewById(R.id.tv_1);
    }

    @Override
    public void initData() {
        //设置listview数据
        setListView();
    }

    private void setListView() {
        if (MyApplication.loginResultBean == null) {
            T("请登录");
        }
        bufferCircleView.show();
        Param param = new Param();
        param.put("us_id", MyApplication.loginResultBean.data.id);
        StringBuffer sb = new StringBuffer();
        sb.append("{").append("\"us_id\":\"").append(MyApplication.loginResultBean.data.id).append("\"}");
        param.put("key", getMd5Password(sb.toString()));

        ControlUtils.postsEveryTime(Constants.Helen.GOODSRECEIPT, param, GoodsReceiptBean.class, new ControlUtils.OnControlUtils<GoodsReceiptBean>() {
            @Override
            public void onSuccess(String url, GoodsReceiptBean goodsReceiptBean, ArrayList<GoodsReceiptBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                bufferCircleView.hide();
                adapter_goodsReceipt = new Adapter_GoodsReceipt(goodsReceiptBean.data, listview.getRightViewWidth(), new Adapter_GoodsReceipt.IOnItemRightClickListener() {
                    @Override
                    public void onRightClick(View v, int position) {
                        Toast.makeText(GoodsReceiptActivity.this, "暂时不提供删除功能", Toast.LENGTH_SHORT).show();
                        delect(position);
                    }
                });
                listview.setAdapter(adapter_goodsReceipt);
            }


            @Override
            public void onFailure(String url) {
                bufferCircleView.hide();
                T("请检测网络");
            }
        });

    }

    private void delect(int position) {



    }

    @Override
    public void setListeners() {


        setOnListeners(tv_1);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id) {
                    //添加地址
                    case R.id.tv_1:
                        Intent intent = new Intent(GoodsReceiptActivity.this, AddGoodsReceiptActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });


    }
}
