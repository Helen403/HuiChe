package com.huiche.activity;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huiche.R;
import com.huiche.base.BaseActivity;
import com.huiche.utils.SystemBarUtil;


public class SearchActivity extends BaseActivity {
    /**
     * 取消按键 TextView
     */
    private TextView cancle;
    /**
     * 输入框
     */
    private EditText edit;
    /**
     * 商家，商品选择的按键，左上角
     */
    private LinearLayout lv;
    /**
     * 刚进来默认加载的，显示搜索热词的布局，搜出结果后gone掉
     */
    private LinearLayout first_lv;
    /**
     * 搜索热词布局消失后，如果没有数据显示的时候 显示的布局。 例如，可有网络。
     */
    private LinearLayout second_lv;
    /**
     * 显示数据的listView
     */
    private PullToRefreshListView listView;

    @Override
    public void dealLogicBeforeFindView() {


    }

    @Override
    public void findViews() {

        SystemBarUtil.initSystemBarElse(this);
        setContentView(R.layout.activity_search);
        cancle = (TextView) findViewById(R.id.cancle_searchActivity);
        edit = (EditText) findViewById(R.id.edit_searchActivity);
        lv = (LinearLayout) findViewById(R.id.lv_searchActivity);
        first_lv = (LinearLayout) findViewById(R.id.first_lv_searchActivity);
        second_lv = (LinearLayout) findViewById(R.id.second_lv_searchActivity);
        listView = (PullToRefreshListView) findViewById(R.id.listView_searchActivity);
    }

    @Override
    public void initData() {


    }

    @Override
    public void setListeners() {


    }

}
