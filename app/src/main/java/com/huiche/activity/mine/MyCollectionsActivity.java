package com.huiche.activity.mine;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huiche.R;
import com.huiche.base.BaseActivity;
import com.huiche.fragment.MyCollectionGoodsFragment;
import com.huiche.fragment.MyCollectionShopFragment;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.HttpConstantChc;
import com.huiche.utils.TitleUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 我的收藏
 */

public class MyCollectionsActivity extends BaseActivity implements OnClickListener {

    private List<Fragment> fragmentList = new ArrayList<Fragment>();
//    private FrameLayout my_fragment;
    private static TextView tv_wait;
    private static TextView tv_overdue;
//    private Context mContext;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private LinearLayout view_left, view_right;
    private int indexFragment = 0;
    private final String ACTION_NAME = "UPDATE_UI";
    // 用于msg.what值
    private static final int CHANGED = 0x0010;
    private SharedPreferences share;
    private String menberId;

    @Override
    public void dealLogicBeforeFindView() {


    }

    @Override
    public void findViews() {
        //其item布局为 item_my_collection
        setContentView(R.layout.activity_my_collections);
//        mContext = this;
        TitleUtils.setInfo(MyCollectionsActivity.this, "我的订单");
        tv_wait = (TextView) findViewById(R.id.tv_shop);
        tv_wait.setOnClickListener(this);
        tv_overdue = (TextView) findViewById(R.id.tv_goods);
        tv_overdue.setOnClickListener(this);
//        my_fragment = (FrameLayout) findViewById(id.my_fragment);
        view_left = (LinearLayout) findViewById(R.id.view_left);
        view_right = (LinearLayout) findViewById(R.id.view_right);
        fm = getSupportFragmentManager();
        share = getSharedPreferences("user_data", MODE_PRIVATE);
        menberId = share.getString("id", "0");
        //纯粹是为了显示商品数量的请求 日
        getUserGoods();

    }

    @Override
    public void initData() {
        MyCollectionShopFragment shopFragment = new MyCollectionShopFragment();
        MyCollectionGoodsFragment goodsFragment = new MyCollectionGoodsFragment();
        fragmentList.add(shopFragment);
        fragmentList.add(goodsFragment);
        ChangeShopFragment();
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_shop:
                //切换到商家页面
                ChangeShopFragment();


                break;

            case R.id.tv_goods:
                ChangeGoodsFragment();
                //切换到商品页面

                break;
        }
    }

    private void ChangeGoodsFragment() {
        view_left.setVisibility(View.INVISIBLE);
        view_right.setVisibility(View.VISIBLE);
        tv_wait.setTextColor(getResources().getColor(R.color.text_color_black));
        tv_overdue.setTextColor(getResources().getColor(R.color.bule_title));
//		ft=fm.beginTransaction();
//		MyCollectionGoodsFragment goods=new MyCollectionGoodsFragment();
//		ft.replace(id.my_fragment, goods);
//		ft.commit();
        switchCollectionFragment(1);
    }

    private void ChangeShopFragment() {
        view_left.setVisibility(View.VISIBLE);
        view_right.setVisibility(View.INVISIBLE);
        tv_wait.setTextColor(getResources().getColor(R.color.bule_title));
        tv_overdue.setTextColor(getResources().getColor(R.color.text_color_black));
//		ft=fm.beginTransaction();
//		MyCollectionShopFragment shop=new MyCollectionShopFragment();
//		ft.replace(id.my_fragment, shop);
//		ft.commit();
        switchCollectionFragment(0);

    }

    public void switchCollectionFragment(int checkIndex) {
        ft = fm.beginTransaction();
        Fragment currentFragment = fragmentList.get(indexFragment);
        Fragment targetFragment = fragmentList.get(checkIndex);
        if (currentFragment != targetFragment) {
            if (!targetFragment.isAdded()) {
                ft.hide(currentFragment).add(
                        R.id.my_fragment, targetFragment);
            } else {
                ft.hide(currentFragment).show(targetFragment);
            }
        } else {
            if (!targetFragment.isAdded()) {
                ft.add(R.id.my_fragment, targetFragment)
                        .show(targetFragment);
            }
        }
        ft.commit();
        indexFragment = checkIndex;

    }

    //当商家数据改变时，更新UI
    public static void upDateShop(String number) {
        tv_wait.setText("商家 " + number);
    }


    //当商家数据改变时，更新UI
    public static void upDateGoods(String number) {
        tv_overdue.setText("商品 " + number);
    }

    //根据设计图要求，需要显示商家和商品的数量，只好再累赘的请求一次商品收藏获取数量
    //获取商品数据
    private void getUserGoods() {
        RequestParams params = new RequestParams();
        params.put("menberId", menberId);
        params.put("page", 1);
        params.put("rows", 10);
        params.put("token", share.getString("token", ""));

        AsyncHttp.posts(HttpConstantChc.COLLECTION_GOODS, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
//                super.onSuccess(statusCode, headers, response);

                String status = response.optString("status");
                String msg = response.optString("msg");
                if (status.equals("0")) {
                    String number = response.optString("total");
                    if (!number.equals("0")) {
                        tv_overdue.setText("商品 " + number);
                    }

                } else {
                    Toast.makeText(MyCollectionsActivity.this, msg, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

//                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(MyCollectionsActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
