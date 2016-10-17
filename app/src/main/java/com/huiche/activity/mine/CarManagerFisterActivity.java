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
import com.huiche.adapter.Adapter_CarManagerFister;
import com.huiche.base.BaseActivity;
import com.huiche.base.CarManagerFisterBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class CarManagerFisterActivity extends BaseActivity {

    TextView tv_1;

    ImageButton imageLeft_titil_all;

    Adapter_CarManagerFister adapter_carManagerFister;
    private PullToRefreshSwipeListView pull_listview;
    private SwipeListView listview;


    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_car_manager_fister);
        pull_listview = (PullToRefreshSwipeListView) findViewById(R.id.listview);
        pull_listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview = pull_listview.getRefreshableView();

        tv_1 = (TextView) findViewById(R.id.tv_1);
        imageLeft_titil_all = (ImageButton) findViewById(R.id.imageLeft_titil_all);

    }

    @Override
    public void initData() {
        ArrayList<CarManagerFisterBean> data = new ArrayList<>();
        data.add(new CarManagerFisterBean());
        adapter_carManagerFister = new Adapter_CarManagerFister(data, listview.getRightViewWidth(), new Adapter_CarManagerFister.IOnItemRightClickListener() {
            @Override
            public void onRightClick(View v, int position) {
                Toast.makeText(CarManagerFisterActivity.this, "暂时不提供删除功能", Toast.LENGTH_SHORT).show();
            }
        });
        listview.setAdapter(adapter_carManagerFister);


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


        imageLeft_titil_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
