package com.huiche.activity.mine;


import com.huiche.R;
import com.huiche.base.BaseActivity;
import com.huiche.utils.TitleUtils;

public class CardDetailActivity extends BaseActivity {

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_card_detail);
        TitleUtils.setInfo(CardDetailActivity.this, "卡券详情");

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {

    }

}
