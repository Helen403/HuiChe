package com.huiche.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.adapter.MyCommitAdapter;
import com.huiche.adapter.MyJiFenAdapter;
import com.huiche.bean.MyCommitBean;
import com.huiche.bean.MyjiFenBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.LRecyclerView.interfaces.OnLoadMoreListener;
import com.huiche.lib.lib.LRecyclerView.interfaces.OnRefreshListener;
import com.huiche.lib.lib.LRecyclerView.recyclerview.LRecyclerView;
import com.huiche.lib.lib.LRecyclerView.recyclerview.LRecyclerViewAdapter;
import com.huiche.lib.lib.MyRecycleView.MyLinearLayoutManager;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.Param;
import com.huiche.lib.lib.base.BaseApplication;
import com.huiche.lib.lib.base.MyBaseRecycleAdapter;
import com.huiche.lib.lib.custemview.CircleImageView;
import com.huiche.lib.lib.custemview.MyRecycleView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class MyCommentActivity extends com.huiche.lib.lib.base.BaseActivity {


    LRecyclerView myRecycleView;
    LRecyclerViewAdapter lRecyclerViewAdapter;

    CircleImageView cir;
    TextView tv_1;
    TextView tv_2;


    @Override
    public int getContentView() {
        return R.layout.activity_my_commit;
    }

    @Override
    public void findViews() {
        setTitle("我的评论");
        myRecycleView = (LRecyclerView) findViewById(R.id.myrecycle_view);
    }

    @Override
    public void initData() {
        //设置RecyclerView
        setRecycleView();
        //添加头部View
        addHeadView();
        if (BaseApplication.loginResultBean == null) {
            T("请登录");
            return;
        } else {
            // 刷新
            myRecycleView.setRefreshing(true);
        }
    }

    //添加头部View
    private void addHeadView() {
        View head = inflater.inflate(R.layout.view_my_commit_head, contentView, false);
        lRecyclerViewAdapter.addHeaderView(head);
        cir = (CircleImageView) head.findViewById(R.id.cir);
        cir.setBorderColor(Color.parseColor("#00000000"));
        cir.setBorderWidth(0);
        tv_1 = (TextView) head.findViewById(R.id.tv_1);
        tv_2 = (TextView) head.findViewById(R.id.tv_2);
    }

    //设置RecyclerView
    private void setRecycleView() {
        lRecyclerViewAdapter = new LRecyclerViewAdapter(MyCommentActivity.this, MyCommitAdapter.class, myRecycleView);
        myRecycleView.setAdapter(lRecyclerViewAdapter);
        myRecycleView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                bufferCircleView.show();
                if (BaseApplication.loginResultBean == null) {
                    T("请登录");
                    return;
                }
                Param param = new Param();
                param.put("us_id", BaseApplication.loginResultBean.data.id);
                StringBuffer sb = new StringBuffer();
                sb.append("{").append("\"us_id\":\"").append(BaseApplication.loginResultBean.data.id).append("\"}");
                param.put("key", getMd5Password(sb.toString()));

                ControlUtils.postsEveryTime(Constants.Helen.MYCOMMIT, param, MyCommitBean.class, new ControlUtils.OnControlUtils<MyCommitBean>() {
                    @Override
                    public void onSuccess(String url, MyCommitBean myCommitBean, ArrayList<MyCommitBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                        bufferCircleView.hide();
                        //设置数据
                        setInfo(myCommitBean);
                        lRecyclerViewAdapter.setRefresh(myCommitBean.data.evaluate);
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

    //设置数据
    private void setInfo(MyCommitBean myCommitBean) {
        //设置头像
        setImageByUrl(myCommitBean.data.headerimg, cir);
        //设置昵称
        tv_1.setText(myCommitBean.data.us_name);

        //设置评论数
        tv_2.setText("已贡献" + myCommitBean.data.evaconut + "条评论");
    }

    @Override
    public void setListeners() {
    }
}
