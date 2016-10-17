package com.huiche.activity.mine;


import com.huiche.R;
import com.huiche.base.BaseActivity;
import com.huiche.utils.TitleUtils;

/**
 * Created by Administrator on 2016/8/9.
 * 查询物流
 */
public class LogisticsActivity extends BaseActivity {

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_logistics);
        TitleUtils.setInfo(LogisticsActivity.this, "查询物流");
    }

    @Override
    public void initData() {
        getGoodsAddress();
    }

    //获取商品物流信息
    private void getGoodsAddress() {

    }

    @Override
    public void setListeners() {

    }
}
