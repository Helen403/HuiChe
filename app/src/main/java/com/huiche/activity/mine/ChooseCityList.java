package com.huiche.activity.mine;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.huiche.PostResult.City;
import com.huiche.PostResult.Province;
import com.huiche.R;
import com.huiche.adapter.ProvinceListAdapter;
import com.huiche.base.BaseActivity;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.TitleUtils;
import com.huiche.utils.ToastUtils;
import com.huiche.view.BufferCircleDialog;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/***
 * 个人信息修改
 */

public class ChooseCityList extends BaseActivity {
    public ListView mListView;
//    public List<Map<String, String>> allList = new ArrayList<Map<String, String>>();
    public ProvinceListAdapter adapter;
//    private SharedPreferences share;
    private List<Province> provinceList = new ArrayList<Province>();
    private String provinceName;
    //省份
//    private List<String> pList = new ArrayList<String>();

    @Override
    public void dealLogicBeforeFindView() {


    }

    @Override
    public void findViews() {
        setContentView(R.layout.activity_choose_city);
        TitleUtils.setInfo(this, "省份");
//        share = getSharedPreferences("user_data", MODE_PRIVATE);
        mListView = (ListView) findViewById(R.id.lv_address);

    }

    @Override
    public void initData() {
        getAddressList();

    }

    @Override
    public void setListeners() {
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//				Intent intent=new Intent(ChooseCityList.this,ChooseTCityListActivity.class);
//				intent.putExtra("provincelist", (Serializable)provinceList);
//				startActivityForResult(intent, 1);
                List<City> cityList;
                cityList = provinceList.get(position).cityList;
                if (position > 0) {
                    cityList.remove(0);
                }


                provinceName = provinceList.get(position).name;
                Intent intent = new Intent(ChooseCityList.this, ChooseTCityListActivity.class);
                intent.putExtra("cityList", (Serializable) cityList);
                intent.putExtra("select_position", position);
                startActivityForResult(intent, 1);
                getAddressList();
            }
        });

    }

    private void getAddressList() {
        //缓冲小菊花
        final BufferCircleDialog bufferCircleDialog = new BufferCircleDialog(ChooseCityList.this);
        bufferCircleDialog.show("正在加载", false, null);
        AsyncHttp.get("http://test.51ujf.cn/js/province_bas.json", new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONArray errorResponse) {

                super.onFailure(statusCode, headers, throwable, errorResponse);
                bufferCircleDialog.dialogcancel();
                ToastUtils.ToastShowShort(ChooseCityList.this, "请检查网络");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONArray response) {

                super.onSuccess(statusCode, headers, response);
                bufferCircleDialog.dialogcancel();
                provinceList.clear();
                String str = response.toString();
                provinceList = com.alibaba.fastjson.JSONArray.parseArray(str, Province.class);
                provinceList.remove(0);
                adapter = new ProvinceListAdapter(ChooseCityList.this, provinceList);
                mListView.setAdapter(adapter);

            }


        });

    }

    protected void toast(String success) {
        Toast.makeText(this, success, Toast.LENGTH_SHORT).show();

    }

    //选择回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String area = data.getStringExtra("area");
            String cityName = data.getStringExtra("cityName");
            Intent intent = new Intent();
            intent.putExtra("area", area);
            intent.putExtra("cityName", cityName);
            intent.putExtra("provinceName", provinceName);
            setResult(RESULT_OK, intent);
            this.finish();
        }
    }
}
