package com.huiche.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.huiche.R;
import com.huiche.adapter.CarBeautifulAdapter;
import com.huiche.adapter.CarBeautiful2Adapter;
import com.huiche.adapter.CityCar2Adapter;
import com.huiche.adapter.CityCarAdapter;
import com.huiche.bean.CarBeautiful2Bean;
import com.huiche.bean.CarBeautifulBean;
import com.huiche.bean.CityCar2Bean;
import com.huiche.bean.CityCarBean;
import com.huiche.lib.lib.base.BaseActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/26.
 */
public class CarBeautifulActivity extends BaseActivity {

    ListView listView;
    CarBeautifulAdapter carBeautifulAdapter;
    RelativeLayout ll, rl2;


    @Override
    public int getContentView() {
        return R.layout.activity_car_beautiful;
    }

    @Override
    public void findViews() {
        setTitle("汽车美容");
        listView = (ListView) findViewById(R.id.listview);
        ll = (RelativeLayout) findViewById(R.id.ll);
        rl2 = (RelativeLayout) findViewById(R.id.rl2);
    }

    @Override
    public void initData() {
        ArrayList<CarBeautifulBean> data = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            data.add(new CarBeautifulBean());

        }
        carBeautifulAdapter = new CarBeautifulAdapter(CarBeautifulActivity.this);
        listView.setAdapter(carBeautifulAdapter);
        carBeautifulAdapter.setData(data);
    }

    @Override
    public void setListeners() {


        setOnListeners(ll, rl2);
        setOnClick(new onClick() {
            @Override
            public void onClick(View v, int id) {
                switch (id) {
                    case R.id.ll:
                        showPopupWindow(ll);
                        break;
                    case R.id.rl2:
                        showPopupWindow(ll);
                        break;
                }
            }
        });
    }


    private void showPopupWindow(View parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_popwindow_view, null);

        ImageView iv_1 = (ImageView) view.findViewById(R.id.iv_1);


        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();

        final PopupWindow popupWindow = new PopupWindow(view, width, height);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(parent, 0, 0);
        iv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        ListView listview = (ListView) view.findViewById(R.id.listview_1);
        ArrayList<CityCarBean> data = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            data.add(new CityCarBean());
        }
        CityCarAdapter adapter = new CityCarAdapter(data);
        listview.setAdapter(adapter);


        ListView listview_2 = (ListView) view.findViewById(R.id.listview_2);
        ArrayList<CityCar2Bean> data2 = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            data2.add(new CityCar2Bean());
        }
        CityCar2Adapter _cityCar_2Adapter = new CityCar2Adapter(data2);
        listview_2.setAdapter(_cityCar_2Adapter);
    }

    private void showPopupWindow2(View parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_popwindow_view2, null);

        ImageView iv_1 = (ImageView) view.findViewById(R.id.iv_1);


        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();

        final PopupWindow popupWindow = new PopupWindow(view, width, height);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(parent, 0, 0);
        iv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        ListView listview = (ListView) view.findViewById(R.id.listview);
        ArrayList<CarBeautiful2Bean> data = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            data.add(new CarBeautiful2Bean());
        }
        CarBeautiful2Adapter adapter = new CarBeautiful2Adapter(data);
        listview.setAdapter(adapter);
    }


}