package com.huiche.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.huiche.R;
import com.huiche.activity.LocationCityActivity;
import com.huiche.activity.MoreActivity;
import com.huiche.adapter.HomeAdapter;
import com.huiche.adapter.HomeGVAdapter;
import com.huiche.bean.AdBean;
import com.huiche.bean.CityBean;
import com.huiche.bean.HomeBean;
import com.huiche.bean.HomeGVBean;
import com.huiche.bean.ProvinceBean;
import com.huiche.constant.Constants;
import com.huiche.lib.lib.LRecyclerView.interfaces.OnLoadMoreListener;
import com.huiche.lib.lib.LRecyclerView.interfaces.OnRefreshListener;
import com.huiche.lib.lib.LRecyclerView.recyclerview.LRecyclerView;
import com.huiche.lib.lib.LRecyclerView.recyclerview.LRecyclerViewAdapter;
import com.huiche.lib.lib.Utils.ControlUtils;
import com.huiche.lib.lib.Utils.LocationUtils;
import com.huiche.lib.lib.Utils.Param;
import com.huiche.lib.lib.base.BaseFragment;
import com.huiche.lib.lib.custemview.MyAdverView;
import com.huiche.lib.lib.custemview.MyGridView;

import org.json.JSONArray;
import org.json.JSONObject;

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

    View headView;
    MyAdverView myAdverView;

    MyGridView mygridview;
    ImageView iv1;
    ImageView imageRigth_titil_allScanCode;
    TextView tv1;

    LRecyclerView myRecycleView;
    LRecyclerViewAdapter lRecyclerViewAdapter;
    LinearLayout ll;
    LinearLayout ll_cityName_titleBar;
    Double lat;
    Double lng;
    String ci_id;


    @Override
    public int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void findViews() {
        myRecycleView = (LRecyclerView) content.findViewById(R.id.myrecycleview);
        ll = (LinearLayout) content.findViewById(R.id.ll);
        ll_cityName_titleBar = (LinearLayout) content.findViewById(R.id.ll_cityName_titleBar);
        imageRigth_titil_allScanCode = (ImageView) content.findViewById(R.id.imageRigth_titil_allScanCode);

        //设置标题栏的透明度
        ll.setAlpha(0);
    }

    @Override
    public void initData() {
    }

    @Override
    public void setListeners() {
        setOnListeners(ll_cityName_titleBar, imageRigth_titil_allScanCode);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id) {
                    //城市定位
                    case R.id.ll_cityName_titleBar:
                        goToActivityByClass(getActivity(), LocationCityActivity.class);
                        break;
                    //更多
                    case R.id.imageRigth_titil_allScanCode:
                        goToActivityByClass(getActivity(), MoreActivity.class);
                        break;
                }
            }
        });
    }

    @Override
    protected void onAttachMyRecycleViewAdapter() {
        super.onAttachMyRecycleViewAdapter();

        lRecyclerViewAdapter = new LRecyclerViewAdapter(getActivity(), HomeAdapter.class, myRecycleView);
        myRecycleView.setAdapter(lRecyclerViewAdapter);
        myRecycleView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {

                Param param = new Param();
                param.put("city", ci_id);
                param.put("lat", lat);
                param.put("lng", lng);
                StringBuffer sb = new StringBuffer();
                sb.append("{").append("\"city\":\"").append(ci_id).append("\",\"lat\":\"").append(lat).append("\",\"lng\":\"").append(lng).append("\"}");
                param.put("key", getMd5Password(sb.toString()));
                ControlUtils.posts(Constants.Helen.SHOPPING, param, HomeBean.class, new ControlUtils.OnControlUtils<HomeBean>() {
                    @Override
                    public void onSuccess(String url, HomeBean homeBean, ArrayList<HomeBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                        lRecyclerViewAdapter.setRefresh(homeBean.data);
                    }

                    @Override
                    public void onFailure(String url) {

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

        myRecycleView.setLScrollListener(new LRecyclerView.LScrollListener() {
            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {
            }

            @Override
            public void onScrolled(int distanceX, int distanceY) {

            }

            @Override
            public void onScrollStateChanged(int state) {

            }
        });
        //设置RecycleView头部View的数据
        setHeadViewData();
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
        //定位
        location();
        /***************************************************************/

    }

    /***************************************************************/
    //定位
    private void location() {

        LocationUtils.getBDLocation(new LocationUtils.OnLocationUtils() {
            @Override
            public void onSuccess(BDLocation location) {
                if (location != null) {
                    tv1.setText(location.getAddrStr());
                    //获取当前省
                    String str = location.getProvince();
                    //赋值定位
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    getProvince(str, location.getCity());


                } else {
                    T("无法定位");
                }
            }
        });

    }


    //获取省
    private void getProvince(final String province, String city) {
        //获取市
        final String tmpCity = city.substring(0, 2);
        final String tmpProvince = province.substring(0, 2);

        ControlUtils.postsForever(Constants.Helen.PROVINCE, null, ProvinceBean.class, new ControlUtils.OnControlUtils<ProvinceBean>() {
            @Override
            public void onSuccess(String url, ProvinceBean provinceBean, ArrayList<ProvinceBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                int count = provinceBean.data.size();
                for (int i = 0; i < count; i++) {
                    String str = provinceBean.data.get(i).ci_name;
                    if (str.equals(tmpProvince)) {
                        getCity(tmpCity, provinceBean.data.get(i).ci_id);
                        break;
                    }
                }
            }

            @Override
            public void onFailure(String url) {
                T("请检测网络");
            }
        });
    }

    //获取当前市
    private void getCity(final String tmp, String id) {
        Param param = new Param();
        param.put("pid", id);
        ControlUtils.postsForever(Constants.Helen.CITY, param, CityBean.class, new ControlUtils.OnControlUtils<CityBean>() {
            @Override
            public void onSuccess(String url, CityBean cityBean, ArrayList<CityBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                int count = cityBean.data.size();
                for (int i = 0; i < count; i++) {
                    if (tmp.equals(cityBean.data.get(i).ci_name)) {
                        //设置广告图片
                        setAdverView(cityBean.data.get(i).ci_id);
                        //设置商品
                        setShopping(cityBean.data.get(i).ci_id);
                        break;
                    }
                }
            }

            @Override
            public void onFailure(String url) {
                T("请检测网络");
            }
        });
    }

    /***************************************************************/
    //广告
    private void setAdverView(String ci_id) {
        Param param = new Param();
        param.put("city", ci_id);
        ControlUtils.posts(Constants.Helen.AD, param, AdBean.class, new ControlUtils.OnControlUtils<AdBean>() {
            @Override
            public void onSuccess(String url, AdBean adBean, ArrayList<AdBean> list, String result, JSONObject jsonObject, JSONArray jsonArray) {
                setAd(adBean);
            }

            @Override
            public void onFailure(String url) {

            }
        });


    }

    //设置广告图片
    public void setAd(AdBean adBean) {
        ArrayList<String> data = new ArrayList<>();
        int count = adBean.data.size();
        for (int i = 0; i < count; i++) {
            data.add(adBean.data.get(i).ad_imgurl);
        }
        myAdverView.setImageResources(data, new MyAdverView.ImageCycleViewListener() {
            @Override
            public void displayImage(String imageURL, ImageView imageView) {
                setImageByUrl(imageURL, imageView);
            }

            @Override
            public void onImageClick(View imageView, int position) {

            }
        });
        //开启广告轮播
        myAdverView.startImageCycle();
    }


    //Fragment从后台进入前面的时候再开启轮播  节约资源
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //不可见
            if (myAdverView != null)
                myAdverView.pushImageCycle();
        } else {
            //当前可见
            if (myAdverView != null)
                myAdverView.startImageCycle();
        }
    }


    /***************************************************************/
    //设置商品
    public void setShopping(String ci_id) {
        this.ci_id = ci_id;
        //刷新数据
        myRecycleView.setRefreshing(true);
    }


}
