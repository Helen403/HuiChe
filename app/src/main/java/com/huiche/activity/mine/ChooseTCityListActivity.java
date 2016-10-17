package com.huiche.activity.mine;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.huiche.PostResult.City;
import com.huiche.R;
import com.huiche.adapter.TwoCityAdapter;
import com.huiche.base.BaseActivity;
import com.huiche.utils.TitleUtils;

import java.util.ArrayList;
import java.util.List;


/***
 * @author Administrator
 *         城市选择--二级城市
 */

public class ChooseTCityListActivity extends BaseActivity {
    private ListView mListView;
    private List<String> cityNameList = new ArrayList<String>();
    private List<City> cityList = new ArrayList<City>();
    //    private List<City> allList = new ArrayList<City>();
    private TwoCityAdapter adapter;
    private String cityName;
    private int select_position = 0;
//    private int count = 0;

    @Override
    public void dealLogicBeforeFindView() {

    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_choose_tcity);
        TitleUtils.setInfo(this, "城市");
        mListView = (ListView) findViewById(R.id.lv_city);

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        cityList = (List<City>) intent.getSerializableExtra("cityList");
//        allList = (List<City>) intent.getSerializableExtra("cityList");
        select_position = intent.getIntExtra("select_position", 0);
        if (cityList != null) {
            for (int i = 0; i < cityList.size(); i++) {
                String name = cityList.get(i).name;
                cityNameList.add(name);
            }
            adapter = new TwoCityAdapter(this, cityNameList);
            mListView.setAdapter(adapter);

            //需要将某些值去掉

        }

    }

    @Override
    public void setListeners() {
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                City city = cityList.get(position);
                cityName = city.name;
                ArrayList<String> areaList;
//                areaList = (ArrayList<String>) city.areaList;
                areaList = (ArrayList<String>) city.areaList;
                if (select_position > 3) {
                    String areaF = areaList.get(0);
                    if (areaF.equals("市、县级市、县")) {
                        areaList.remove(0);
                    }
                }
                Intent intent = new Intent(ChooseTCityListActivity.this, AreaChooseActivity.class);
                intent.putStringArrayListExtra("areaList", areaList);
                startActivityForResult(intent, 1);
                //
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String area = data.getStringExtra("area");
            Intent intent = new Intent();
            intent.putExtra("area", area);
            intent.putExtra("cityName", cityName);
            setResult(RESULT_OK, intent);
            this.finish();
        }

    }

}
