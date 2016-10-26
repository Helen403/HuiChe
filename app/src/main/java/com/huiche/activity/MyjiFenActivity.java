package com.huiche.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.adapter.Adapter_MyjiFen;
import com.huiche.bean.MyPointBean;
import com.huiche.bean.MyjiFenBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.MyRecycleView.MyLinearLayoutManager;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.Param;
import com.huiche.lib.lib.base.BaseApplication;
import com.huiche.lib.lib.base.MyBaseRecycleAdapter;
import com.huiche.lib.lib.custemview.MyRecycleView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/27.
 */
public class MyjiFenActivity extends com.huiche.lib.lib.base.BaseActivity {

    Adapter_MyjiFen adapter_myjiFen;
    MyRecycleView recycleView;


    private LinearLayout rlTitleMainActivity;
    private ImageButton imageLeftTitilAll;
    private TextView textTitilAll;
    private ImageButton imageRigthTitilAll;
    private TextView tvRightTitle;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private ListView listview;


    @Override
    public int getContentView() {
        return R.layout.activity_my_jifen_helen;
    }

    @Override
    public void findViews() {
        setTitle("我的积分");

        recycleView = (MyRecycleView) findViewById(R.id.myrecycle_view);
    }

    @Override
    public void initData() {


        //设置RecyclerView
        setRecycleView();
        //添加头部View
        addHeadView();
        //获取用户现有的积分
        getUsePoint();

    }


    //添加头部View
    private void addHeadView() {
        View head = inflater.inflate(R.layout.activity_my_jifen, contentView, false);
        recycleView.addHeaderView(head);
        rlTitleMainActivity = (LinearLayout) head.findViewById(R.id.rl_title_mainActivity);
        imageLeftTitilAll = (ImageButton) head.findViewById(R.id.imageLeft_titil_all);
        textTitilAll = (TextView) head.findViewById(R.id.text_titil_all);
        imageRigthTitilAll = (ImageButton) head.findViewById(R.id.imageRigth_titil_all);
        tvRightTitle = (TextView) head.findViewById(R.id.tv_right_title);
        tv1 = (TextView) head.findViewById(R.id.tv_1);
        tv2 = (TextView) head.findViewById(R.id.tv_2);
        tv3 = (TextView) head.findViewById(R.id.tv_3);
        listview = (ListView) head.findViewById(R.id.listview);

    }


    //设置RecyclerView
    private void setRecycleView() {
        // 使用重写后的线性布局管理器
        MyLinearLayoutManager manager = new MyLinearLayoutManager(MyjiFenActivity.this);
        recycleView.setLayoutManager(manager);
        adapter_myjiFen = new Adapter_MyjiFen(MyjiFenActivity.this, recycleView);
        recycleView.setAdapter(adapter_myjiFen);
        adapter_myjiFen.setOnRefresh(new MyBaseRecycleAdapter.OnRefresh() {
            @Override
            public void onRefresh() {

                bufferCircleView.show();
                Param param = new Param();
                param.put("id", BaseApplication.loginResultBean.data.id);
                StringBuffer sb = new StringBuffer();
                sb.append("{").append("\"id\":\"").append(BaseApplication.loginResultBean.data.id).append("\"}");
                param.put("key", getMd5Password(sb.toString()));

                ControlUtils.postsEveryTime(Constants.Helen.USERPOINT, param, MyjiFenBean.class, new ControlUtils.OnControlUtils<MyjiFenBean>() {
                    @Override
                    public void onSuccess(String url, MyjiFenBean myjiFenBean, ArrayList<MyjiFenBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                        bufferCircleView.hide();
                        adapter_myjiFen.setRefresh(myjiFenBean.data);

                    }

                    @Override
                    public void onFailure(String url) {
                        bufferCircleView.hide();
                        T("请检测网络");
                        adapter_myjiFen.setRefresh(null);
                    }
                });


            }

            @Override
            public void onAddData() {
                adapter_myjiFen.setAddData(null);
            }
        });

        if (BaseApplication.loginResultBean == null) {
            T("请登录");
            return;
        } else {
            // 刷新
            recycleView.setRefresh(true);
        }


    }

    //设置数据
    private void setInfo(MyPointBean us_points) {
        //设置当前积分
        tv2.setText(us_points.data.us_points);
    }


    private void getUsePoint() {
        if (BaseApplication.loginResultBean == null) {
            T("请登录");
            return;
        }
        bufferCircleView.show();
        Param param = new Param();
        param.put("id", BaseApplication.loginResultBean.data.id);
        StringBuffer sb = new StringBuffer();
        sb.append("{").append("\"id\":\"").append(BaseApplication.loginResultBean.data.id).append("\"}");
        param.put("key", getMd5Password(sb.toString()));
        ControlUtils.postsEveryTime(Constants.Helen.GETUSERPOINT, param, MyPointBean.class, new ControlUtils.OnControlUtils<MyPointBean>() {
            @Override
            public void onSuccess(String url, MyPointBean myPointBean, ArrayList<MyPointBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                bufferCircleView.hide();
                T(myPointBean.msg);
                setInfo(myPointBean);

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
