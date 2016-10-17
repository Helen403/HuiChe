package com.huiche.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.adapter.RedBaoDetailUSer_adapter;
import com.huiche.adapter.RedBaoDetail_adapter;
import com.huiche.base.BaseActivity;
import com.huiche.bean.RedBaoDetailBean;
import com.huiche.bean.RedBaoDetailFillBean;
import com.huiche.bean.RedBaoDetailUserBaen;
import com.huiche.utils.LoadImageUtil;
import com.huiche.view.CircleImageView;
import com.huiche.view.MyListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/1.
 */
public  class RedBaoDetailActivity extends BaseActivity {
    MyListView myListView;
    MyListView listview_1;

    RedBaoDetail_adapter adapter;
    RedBaoDetailUSer_adapter adapter1;

    ArrayList<RedBaoDetailBean> data;
    ArrayList<RedBaoDetailUserBaen> data1;
    ImageButton imb_titleBack;
    ImageButton back;

    TextView businessname, content;
    ImageView img;

    private LoadImageUtil loadImageUtil = new LoadImageUtil();

    CircleImageView icon;


    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_redbao);
        myListView = (MyListView) findViewById(R.id.listview);
        listview_1 = (MyListView) findViewById(R.id.listview_1);
        back = (ImageButton) findViewById(R.id.back);
        icon = (CircleImageView) findViewById(R.id.icon);
        businessname = (TextView) findViewById(R.id.businessname);
        img = (ImageView) findViewById(R.id.img);
        content = (TextView) findViewById(R.id.content);
        listview_1.setFocusable(false);
        icon.setFocusable(true);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        RedBaoDetailFillBean redBaoDetailFillBean = intent.getParcelableExtra("redBaoDetailFillBean");
        //设置头像
        loadImageUtil.imageLoader.displayImage(redBaoDetailFillBean.rows.image, icon);
        //设置商家名称
        businessname.setText(redBaoDetailFillBean.rows.businessName);
        //设置对应的大图
        loadImageUtil.imageLoader.displayImage(redBaoDetailFillBean.rows.businessStoreImage, img);
        //设置对应的内容
        content.setText(redBaoDetailFillBean.rows.context);


        data = new ArrayList<>();
        for (int i = 0; i < redBaoDetailFillBean.rows.count; i++) {
            data.add(new RedBaoDetailBean());
        }

        adapter = new RedBaoDetail_adapter(this, data);
        myListView.setAdapter(adapter);


        data1 = new ArrayList<>();
        for (int i = 0; i < redBaoDetailFillBean.rows.getCount; i++) {
            data1.add(new RedBaoDetailUserBaen());
        }
        adapter1 = new RedBaoDetailUSer_adapter(this, data1);
        listview_1.setAdapter(adapter1);
    }

    @Override
    public void setListeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
