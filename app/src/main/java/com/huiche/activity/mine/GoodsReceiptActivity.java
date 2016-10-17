package com.huiche.activity.mine;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshSwipeListView;
import com.handmark.pulltorefresh.library.SwipeListView;
import com.huiche.R;
import com.huiche.adapter.Adapter_GoodsReceipt;
import com.huiche.base.BaseActivity;
import com.huiche.bean.GoodsReceiptBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/30.
 */
public class GoodsReceiptActivity extends BaseActivity {


    TextView tv_1;

    ImageButton imageLeft_titil_all;

    Adapter_GoodsReceipt adapter_goodsReceipt;
    private PullToRefreshSwipeListView pull_listview;
    private SwipeListView listview;

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_goods_receipt);
        pull_listview = (PullToRefreshSwipeListView) findViewById(R.id.listview);
        pull_listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview = pull_listview.getRefreshableView();

        tv_1 = (TextView) findViewById(R.id.tv_1);
        imageLeft_titil_all = (ImageButton) findViewById(R.id.imageLeft_titil_all);
    }

    @Override
    public void initData() {
        ArrayList<GoodsReceiptBean> data = new ArrayList<>();
        data.add(new GoodsReceiptBean());
        adapter_goodsReceipt = new Adapter_GoodsReceipt(data, listview.getRightViewWidth(), new Adapter_GoodsReceipt.IOnItemRightClickListener() {
            @Override
            public void onRightClick(View v, int position) {
                Toast.makeText(GoodsReceiptActivity.this, "暂时不提供删除功能", Toast.LENGTH_SHORT).show();
            }
        });
        listview.setAdapter(adapter_goodsReceipt);

    }

    @Override
    public void setListeners() {
        tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodsReceiptActivity.this, AddGoodsReceiptActivity.class);
                startActivity(intent);
            }
        });


        imageLeft_titil_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
