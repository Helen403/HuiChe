package com.huiche.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiche.R;
import com.huiche.adapter.MyCardAdapter;
import com.huiche.base.BaseActivity;
import com.huiche.bean.PayInfoCard;
import com.huiche.utils.TitleUtils;
import com.huiche.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/20.
 */
public class MyCardActivity extends BaseActivity {

    PullToRefreshListView pull_listview;
    ListView mListView;
    PayInfoCard payInfoCard;
    List<PayInfoCard.RowsBean> rows = new ArrayList<>();
    MyCardAdapter myCardAdapter;

    int page = 1;
    ImageButton imageLeft_titil_all;
    //用户买单金额
    private double payMoney;
    Context context;
    //要对卡券进行排序，可用的放前面
    private List<PayInfoCard.RowsBean> unUse = new ArrayList<PayInfoCard.RowsBean>();
    private List<PayInfoCard.RowsBean> canUse = new ArrayList<PayInfoCard.RowsBean>();

    @Override
    public void dealLogicBeforeFindView() {
        context = this;
    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_mycard);
        TitleUtils.setInfo(MyCardActivity.this, "卡券");
        pull_listview = (PullToRefreshListView) findViewById(R.id.lv_card);
        pull_listview.setMode(PullToRefreshBase.Mode.DISABLED);
        mListView = pull_listview.getRefreshableView();
        imageLeft_titil_all = (ImageButton) findViewById(R.id.imageLeft_titil_all);
    }

    @Override
    public void initData() {
        payInfoCard = this.getIntent().getParcelableExtra("payInfoCard");
        payMoney = this.getIntent().getDoubleExtra("payMoney", 0);
        for (int i = 0; i < payInfoCard.getRows().size(); i++) {
            PayInfoCard.RowsBean rowsBean = payInfoCard.getRows().get(i);
            //可用卡券放在前面
            if (payMoney >= (rowsBean.getDeduction())) {
                canUse.add(rowsBean);
            } else {
                unUse.add(rowsBean);
            }
        }
        //总的
        rows.addAll(canUse);
        rows.addAll(unUse);
        myCardAdapter = new MyCardAdapter(MyCardActivity.this, rows, payMoney);
        mListView.setAdapter(myCardAdapter);
    }

    @Override
    public void setListeners() {

        pull_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int duction = rows.get(position - 1).getDeduction();
                if (payMoney >= duction) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("RowsBean", rows.get(position - 1));
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtils.ToastShowShort(MyCardActivity.this, "该卡券不可用");
                }
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
