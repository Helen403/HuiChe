package com.huiche.activity.mine;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.huiche.R;
import com.huiche.adapter.AreaChooseAdapter;
import com.huiche.base.BaseActivity;
import com.huiche.utils.TitleUtils;

import java.util.ArrayList;
import java.util.List;


public class AreaChooseActivity extends BaseActivity {
    private ListView mListView;
    private List<String> areaList = new ArrayList<String>();
    private AreaChooseAdapter adapter;

    @Override
    public void dealLogicBeforeFindView() {


    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_area_choose);
        mListView = (ListView) findViewById(R.id.lv_area);
        TitleUtils.setInfo(this, "选择地区");
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        areaList = intent.getStringArrayListExtra("areaList");
        adapter = new AreaChooseAdapter(this, areaList);
        mListView.setAdapter(adapter);
    }

    @Override
    public void setListeners() {
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String area = areaList.get(position);
                Intent intent = new Intent();
                intent.putExtra("area", area);
                setResult(RESULT_OK, intent);
                finish();

            }
        });

    }

}
