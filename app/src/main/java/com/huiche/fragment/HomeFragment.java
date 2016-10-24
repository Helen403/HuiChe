package com.huiche.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.adapter.HomeAdapter;
import com.huiche.adapter.HomeGVAdapter;
import com.huiche.bean.HomeBean;
import com.huiche.bean.HomeGVBean;
import com.huiche.lib.lib.LRecyclerView.interfaces.OnLoadMoreListener;
import com.huiche.lib.lib.LRecyclerView.interfaces.OnRefreshListener;
import com.huiche.lib.lib.LRecyclerView.recyclerview.LRecyclerView;
import com.huiche.lib.lib.LRecyclerView.recyclerview.LRecyclerViewAdapter;
import com.huiche.lib.lib.base.BaseFragment;
import com.huiche.lib.lib.custemview.MyAdverView;
import com.huiche.lib.lib.custemview.MyGridView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/21.
 */
public class HomeFragment extends BaseFragment {
    private int girdImage[] = {R.drawable.home_menu_food, R.drawable.home_menu_entertainment,
            R.drawable.home_menu_hotel, R.drawable.home_menu_beauty, R.drawable.home_menu_fitness,
            R.drawable.home_menu_financial, R.drawable.home_menu_recruitment, R.drawable.home_menu_insurance};
    private String gridText[] = {"美食", "娱乐", "酒店", "汽车美容", "健身美容",
            "金融街", "招聘", "保险"};
    //    MyRecycleView myRecycleView;
    HomeAdapter homeAdapter;

    View headView;
    MyAdverView myAdverView;

    MyGridView mygridview;
    ImageView iv1;
    TextView tv1;

    LRecyclerView myRecycleView;
    LRecyclerViewAdapter lRecyclerViewAdapter;


    @Override
    public int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void findViews() {
        myRecycleView = (LRecyclerView) content.findViewById(R.id.myrecycleview);

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {

    }

    @Override
    protected void onAttachMyRecycleViewAdapter() {
        super.onAttachMyRecycleViewAdapter();

        homeAdapter = new HomeAdapter();
        lRecyclerViewAdapter = new LRecyclerViewAdapter(getActivity(), HomeAdapter.class, myRecycleView);
        setHeadViewData();
        myRecycleView.setAdapter(lRecyclerViewAdapter);
        myRecycleView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayList<HomeBean> data = new ArrayList<HomeBean>();
                for (int i = 0; i < 5; i++) {
                    data.add(new HomeBean());
                }
                lRecyclerViewAdapter.setRefresh(data);
            }
        });
        myRecycleView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                ArrayList<HomeBean> data = new ArrayList<HomeBean>();
                for (int i = 0; i < 1; i++) {
                    data.add(new HomeBean());
                }
                lRecyclerViewAdapter.setAddData(data);
            }
        });

        myRecycleView.setRefreshing(true);

    }

    //设置RecycleView头部View的数据
    private void setHeadViewData() {
        headView = inflater.inflate(R.layout.view_home_head, content, false);
        lRecyclerViewAdapter.addHeaderView(headView);
        myAdverView = (MyAdverView) headView.findViewById(R.id.myadverview);
        mygridview = (MyGridView) headView.findViewById(R.id.mygridview);
        iv1 = (ImageView) headView.findViewById(R.id.iv_1);
        tv1 = (TextView) headView.findViewById(R.id.tv_1);
        /***************************************************************/
        HomeGVAdapter homeGVAdapter = new HomeGVAdapter(getActivity());
        mygridview.setAdapter(homeGVAdapter);
        ArrayList<HomeGVBean> data = new ArrayList<>();
        int count = girdImage.length;
        for (int i = 0; i < count; i++) {
            data.add(new HomeGVBean(gridText[i], girdImage[i]));
        }
        homeGVAdapter.setData(data);
        /***************************************************************/
        setAdverView();
    }

    private void setAdverView() {
        ArrayList<String> data = new ArrayList<>();
        data.add("11");
        myAdverView.setImageResources(data, new MyAdverView.ImageCycleViewListener() {
            @Override
            public void displayImage(String imageURL, ImageView imageView) {
                imageView.setImageResource(R.drawable.ic_launcher);
            }

            @Override
            public void onImageClick(View imageView, int position) {

            }
        });
    }


}
