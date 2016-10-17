package com.huiche.activity.citychoice;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.huiche.R;
import com.huiche.activity.MainActivity;
import com.huiche.base.BaseActivity;
import com.huiche.bean.CityBean;
import com.huiche.constant.HttpConstantHao;
import com.huiche.constant.MyRequestCode;
import com.huiche.listener.GridViewFromNearByListener;
import com.huiche.utils.AsyncHttp;
import com.huiche.utils.DBHelper;
import com.huiche.utils.DatabaseHelper;
import com.huiche.utils.SharedPreferenceHelper;
import com.huiche.utils.SystemBarUtil;
import com.huiche.utils.ToastUtils;
import com.huiche.view.MyLetterListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

/***
 * 城市选择界面
 * 
 * @author zane
 * 
 */
public class CityChoice extends BaseActivity implements
		MyLetterListView.OnTouchingLetterChangedListener, GridViewFromNearByListener,
		OnClickListener {
	/***
	 * 关闭的按钮
	 */
	private ImageView finishImage;
	/***
	 * titlebar，显示当前的城市
	 */
	private TextView cityName;
	/***
	 * 加载城市数据的listview
	 */
	private ListView listView;
	/***
	 * 最近定位的城市 Gridview
	 */
	private GridView LocCity_GridView;
	/***
	 * 热门城市 Gridview
	 */
	private GridView hotCity_GridView;
	/***
	 * 最近访问的城市 Gridview
	 */
	private GridView rencenyCity_GridView;
	/***
	 * 热门城市数据集合。
	 */
	private List<String> listHotCity = new ArrayList<>();
	/***
	 * 空集合
	 */
	private List<String> listEmpty = new ArrayList<>();
	/***
	 * 所有城市集合
	 */
	private List<City> listCityInfo = new ArrayList<>();
	/***
	 * 当前定位城市集合
	 */
	private List<String> listLocalCity = new ArrayList<>();
	/***
	 * 最近使用的城市数据集合
	 */
	private List<String> listRecentCity = new ArrayList<>();
	/***
	 * 旁边的 A-Z 字母选择栏
	 */
	private MyLetterListView letterView;

	private String[] letterAll = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
	/***
	 * 百度地图定位
	 */
	public LocationClient mLocationClient = null;
	/**
	 * 位置回调
	 */

	public BDLocationListener myListener = new MyLocationListener();
	/**
	 * 热门城市的adapter
	 */
	private HotCityAdapter adapterGridviewHot;
	/**
	 * 最近访问城市的adapter
	 */
	private RecentUsingCityAdapter adapterGridviewRecent;
	/**
	 * 最近定位城市的adapter
	 */
	private LocationCityAdapter adapterGridviewLocal;

	@Override
	public void dealLogicBeforeFindView() {

	}

	/**
	 * 是否禁用回退键
	 */
	public boolean cancleFlag = false;
	/**
	 * 是否禁用返回键
	 */
	public boolean canBackFlag = false;
	public Context mContext;
	private Intent itent;
	Comparator comparator = new Comparator<City>() {
		@Override
		public int compare(City lhs, City rhs) {
			String a = lhs.getPinyi().substring(0, 1);
			String b = rhs.getPinyi().substring(0, 1);
			int flag = a.compareTo(b);
			if (flag == 0) {
				return a.compareTo(b);
			} else {
				return flag;
			}
		}
	};

	@Override
	public void findViews() {
		setContentView(R.layout.activity_citychoice);
		SystemBarUtil.initSystemBarWhite(this);
		SystemBarUtil.setMiuiStatusBarDarkMode(this, true);
		mContext = this;

		finishImage = (ImageView) findViewById(R.id.finishImage_cityChoice_Activity);
		cityName = (TextView) findViewById(R.id.cityName_cityChoice_Activity);
		listView = (ListView) findViewById(R.id.listView_cityChoice_Activity);
		View v = LayoutInflater.from(this).inflate(
				R.layout.headview_listview_citychoiceactivity, null);
		LocCity_GridView = (GridView) v
				.findViewById(R.id.LocCity_HeadView_CityChoiceActivity);
		hotCity_GridView = (GridView) v
				.findViewById(R.id.hotCity_HeadView_CityChoiceActivity);
		rencenyCity_GridView = (GridView) v
				.findViewById(R.id.rencenyCity_HeadView_CityChoiceActivity);
		listView.addHeaderView(v);
		listView.setAdapter(null);
		letterView = (MyLetterListView) findViewById(R.id.letterView_CityChoice_Activity);

	}

	@Override
	public void initData() {
		itent = getIntent();
		String city = itent.getStringExtra("city");
		if(city!=null)
			cityName.setText("当前城市-" + city);
		//listLocalCity.add(city);
		if (itent.getStringExtra("error") != null) {
			// 取消回退键
			cancleFlag = true;
			canBackFlag = true;
		}
		requreHotCity();
		// getCityInfo();
		getCityList();
		// locate();
		requreHotCity();
		listRecentCity.clear();
		int aaa= SharedPreferenceHelper.getCityRecentInfo(this).size();
		listRecentCity.addAll(SharedPreferenceHelper.getCityRecentInfo(this));
		setGridViewSize(rencenyCity_GridView, listRecentCity.size());
		adapterGridviewHot = new HotCityAdapter(this, listEmpty, this);
		hotCity_GridView.setAdapter(adapterGridviewHot);
		adapterGridviewRecent = new RecentUsingCityAdapter(this,
				listRecentCity, this);
		rencenyCity_GridView.setAdapter(adapterGridviewRecent);
		adapterGridviewLocal = new LocationCityAdapter(this, listLocalCity,
				this);
		LocCity_GridView.setAdapter(adapterGridviewLocal);
		locate();
	}

	/***
	 * 请求热门城市
	 */
	private void requreHotCity() {

		RequestParams params = new RequestParams();
		AsyncHttp.posts(HttpConstantHao.GET_HOT_CITY, params,
				new JsonHttpResponseHandler() {
					@Override
					public void onFailure(int statusCode, Header[] headers,
										  Throwable throwable, JSONObject errorResponse) {

//						super.onFailure(statusCode, headers, throwable,
//								errorResponse);
						ToastUtils.ToastShowShort(CityChoice.this,
								"数据加载失败，请检查网络");
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
										  JSONObject response) {

//						super.onSuccess(statusCode, headers, response);
//						Log.d("zane11", response.toString());
						try {
							if (response.getString("status").equals("0")) {
								JSONArray rows = response.getJSONArray("rows");
								for (int i = 0; i < rows.length(); i++) {
									JSONObject obj = rows.getJSONObject(i);
									listHotCity.add(obj.getString("city"));
								}
							}
							setGridViewSize(hotCity_GridView,
									listHotCity.size());
							adapterGridviewHot.reflishData(listHotCity);
//							Log.d("zane11", listLocalCity.size() + "=========");

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	public void setListeners() {
		letterView.setOnTouchingLetterChangedListener(this);
		finishImage.setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				//ToastUtils.ToastShowLong(this, );
				SharedPreferenceHelper.setCityRecentInfo(mContext,
						listCityInfo.get(position-1).getName()+"市");
				Intent itentss = new Intent(getApplicationContext(), MainActivity.class);
				itentss.putExtra("cityName", listCityInfo.get(position-1).getName());
				setResult(MyRequestCode.CityChoice_ResultCode, itentss);
				finish();
			}
		});
	}
	/***
	 * 动态计算GridView的大小,此时每个item的高度为屏幕的12分之1
	 */
	public void setGridViewSize(View v, int coutItem) {
		int height = 0;
		WindowManager wm = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		int h1 = display.getHeight() / 12;
		if (coutItem % 3 == 0) {
			height = (coutItem / 3) * h1 + h1 / 2;
		} else {
			height = (coutItem / 3 + 1) * h1 + h1 / 2;
		}
		LayoutParams params = (LayoutParams) v.getLayoutParams();
		params.height = height;
		v.setLayoutParams(params);
	}

	@Override
	public void onTouchingLetterChanged(String s) {
		ToastUtils.ToastShowShort(this, s);
		for (int i = 0; i < listCityInfo.size(); i++) {
			if (s.equals(getAlpha(listCityInfo.get(i).getPinyi()))) {
				int a = i;
				listView.setSelection(a);
				break;
			}
		}

	}

	/***
	 * 获取城市信息
	 */
	public void getCityInfo() {
		AsyncHttp.get("http://test.51ujf.cn/js/province_bas.json",
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							JSONArray response) {

						super.onSuccess(statusCode, headers, response);
						for (int i = 0; i < response.length(); i++) {

							JSONObject obj = response.optJSONObject(i);
							JSONArray arr = obj.optJSONArray("cityList");
							for (int j = 0; j < arr.length(); j++) {
								CityBean bean = new CityBean();
								try {
									String nameCity = arr.getJSONObject(i)
											.getString("name");
									if (!nameCity.equals("地级市")) {
										bean.setName(nameCity);
										// listCityInfo.add(bean);
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						}
						CityInfoAdapterCityChoice adapterListView = new CityInfoAdapterCityChoice(
								getApplicationContext(), listCityInfo);
						listView.setAdapter(adapterListView);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {

						super.onFailure(statusCode, headers, responseString,
								throwable);

					}

					@Override
					public void onFinish() {

						super.onFinish();
					}
				});
	}

	/***
	 * 百度地图定位
	 */
	public void locate() {
		mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 1000;
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
		mLocationClient.start();
		mLocationClient.requestLocation();
	}

	class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation arg0) {


			if (arg0 != null) {
				mLocationClient.stop();
				listLocalCity.clear();
				listLocalCity.add(arg0.getCity().split("市")[0]);
				// adapterGridviewLocal.reflishData(listLocalCity);
			} else {
				listLocalCity.add("定位失败,请单击重试");
				ToastUtils.ToastShowShort(CityChoice.this, "定位失败");
			}
			if (arg0.getCity() == null) {
				listLocalCity.add("定位失败,请单击重试");
			} else {
				String str = arg0.getCity();
				String[] arr = str.split("市");

			}

		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (cancleFlag) {
			if (event.getAction() == KeyEvent.ACTION_DOWN
					&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
				ToastUtils.ToastShowLong(this, "请选择一个城市");
				return true;// 消费掉后退键
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 返回附近的碎片中
	 */
	@Override
	public void changColorSetData(String str, int position) {

		SharedPreferenceHelper.setCityRecentInfo(mContext,
				str+"市");
		ToastUtils.ToastShowLong(this, str);
		Intent itentss = new Intent(this, MainActivity.class);
		itentss.putExtra("cityName", str);
		this.setResult(MyRequestCode.CityChoice_ResultCode, itentss);
		finish();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.finishImage_cityChoice_Activity:
			if (canBackFlag) {
				ToastUtils.ToastShowLong(this, "请选择一个城市");
			} else {
				finish();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 获取城市列表
	 */
	@SuppressWarnings("unchecked")
	public void getCityList() {

		ArrayList<City> list = new ArrayList<City>();
		try {
			DatabaseHelper helper = new DatabaseHelper(this);
			DBHelper dbHelper = new DBHelper(this);
			dbHelper.createDataBase();
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			Cursor cursor = db.rawQuery("select * from city", null);
			City city = null;
			while (cursor.moveToNext()) {
				city = new City(cursor.getString(1), cursor.getString(2));
				list.add(city);
			}
			cursor.close();
			db.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Collections.sort(list, comparator);
		listCityInfo.addAll(list);
		CityInfoAdapterCityChoice adapterListView = new CityInfoAdapterCityChoice(
				getApplicationContext(), listCityInfo);
		listView.setAdapter(adapterListView);
	}

	private String getAlpha(String str) {
		if (str == null) {
			return "#";
		}
		if (str.trim().length() == 0) {
			return "#";
		}
		char c = str.trim().substring(0, 1).charAt(0);
		// 正则表达式，判断首字母是否是英文字母
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase();
		} else if (str.equals("0")) {
			return "定位";
		} else if (str.equals("1")) {
			return "最近";
		} else if (str.equals("2")) {
			return "热门";
		} else if (str.equals("3")) {
			return "全部";
		} else {
			return "#";
		}
	}

}
