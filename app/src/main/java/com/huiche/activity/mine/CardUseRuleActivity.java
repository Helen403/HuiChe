package com.huiche.activity.mine;

import android.content.Intent;
import android.widget.TextView;

import com.huiche.R;
import com.huiche.base.BaseActivity;
import com.huiche.utils.TitleUtils;


/**
 * Created by Administrator on 2016/7/19.
 */
public class CardUseRuleActivity extends BaseActivity {
    private String detail;
    private TextView tv_rule;

    @Override
    public void dealLogicBeforeFindView() {
    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_card_userule);
        TitleUtils.setInfo(this, "使用规则");
        Intent intent = getIntent();
        detail = intent.getStringExtra("userule");
        tv_rule = (TextView) findViewById(R.id.tv_rule);
    }

    @Override
    public void initData() {
        tv_rule.setText(detail);
    }

    @Override
    public void setListeners() {

    }
}
