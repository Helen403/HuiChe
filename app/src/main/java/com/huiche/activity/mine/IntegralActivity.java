package com.huiche.activity.mine;

/***
 * 积分余额
 */

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiche.R;
import com.huiche.base.BaseActivity;
import com.huiche.utils.TitleUtils;

public class IntegralActivity extends BaseActivity implements OnClickListener {
//    private ListView mlistview;
    private PullToRefreshListView integralListview;

    @Override
    public void onClick(View v) {


    }

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_integral);
        TitleUtils.setInfo(IntegralActivity.this, "积分余额");
        integralListview = (PullToRefreshListView) findViewById(R.id.lv_intergral);
        integralListview.setMode(Mode.BOTH);
//        mlistview = integralListview.getRefreshableView();

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {
        integralListview.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新数据的操作
                if (refreshView.isHeaderShown()) {
//					//下拉刷新


                } else {
                    //上拉加载


                }

            }
        });
    }

}
